CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS players
(
    id                uuid default uuid_generate_v4(),
    username          VARCHAR(255),
    login             VARCHAR(255),
    registration_date TIMESTAMP WITHOUT TIME ZONE,
    last_online       TIMESTAMP WITHOUT TIME ZONE,
    is_active         BOOLEAN,
    up_coins          BIGINT,
    session           INT,
    rating            INT,
    CONSTRAINT pk_players PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS udx_players_username ON players (username);

CREATE TABLE IF NOT EXISTS characters
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    character_name VARCHAR(255),
    player_id      uuid,
    CONSTRAINT pk_characters PRIMARY KEY (id),
    CONSTRAINT FK_CHARACTERS_ON_PLAYER FOREIGN KEY (player_id) REFERENCES players (id)
);

CREATE INDEX IF NOT EXISTS idx_players_id ON characters (player_id);


CREATE TABLE IF NOT EXISTS users
(
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    enabled  BOOLEAN,
    CONSTRAINT pk_users PRIMARY KEY (username)
);

create table if not exists authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    constraint delete_user_cascade foreign key (username) references users (username) on delete cascade
);
create unique index if not exists udx_auth_username on authorities (username, authority);


CREATE TABLE IF NOT EXISTS character_info
(
    character_id BIGINT NOT NULL,
    faction_id   BIGINT,
    faction_name VARCHAR(255),
    is_active    BOOLEAN,
    profit       INTEGER,
    CONSTRAINT pk_character_info PRIMARY KEY (character_id)
);

CREATE TABLE IF NOT EXISTS character_xp
(
    id                  BIGINT NOT NULL,
    character_id        BIGINT,
    level               SMALLINT,
    current_xp          BIGINT,
    all_xp              BIGINT,
    xp_requirement      BIGINT,
    xp_until_next_level BIGINT,
    CONSTRAINT pk_character_xp PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS refresh_token
(
    token       uuid,
    username    VARCHAR(255),
    expiry_date timestamptz,
    CONSTRAINT pk_refresh_token PRIMARY KEY (token)
);

CREATE TABLE IF NOT EXISTS runs
(
    id              BIGSERIAL NOT NULL,
    player_id       uuid,
    character       jsonb,
    level           BIGINT,
    shit_coins      BIGINT,
    up_coins        BIGINT,
    enemy           INT,
    world           INT,
    boosts          jsonb,
    consumables     jsonb,
    start_timestamp timestamptz,
    end_timestamp   timestamptz,
    duration        INT,
    is_ended        BOOLEAN,
    is_heroic       BOOLEAN,
    CONSTRAINT pk_runs PRIMARY KEY (id)
);

CREATE VIEW runs_leaderboard AS
SELECT DISTINCT r.id                        AS run_id,
                p.username,
                r.character ->> 'name'      AS character_name,
                (r.character ->> 'id')::INT AS character_id,
                peak_level.player_id,
                peak_level.max
FROM (SELECT player_id, character ->> 'id' as character_id, MAX(level) AS max
      FROM runs
      WHERE is_ended = true
      group by player_id, character_id) AS peak_level
         LEFT JOIN runs r
                   ON peak_level.player_id = r.player_id
                       AND peak_level.max = r.level AND peak_level.character_id = r.character ->> 'id'
         LEFT JOIN players p ON p.id = r.player_id
ORDER BY peak_level.max DESC;

CREATE INDEX idx_runs_leaderboard_player_id ON runs_leaderboard (player_id);
CREATE INDEX idx_runs_leaderboard_character_id ON runs_leaderboard (character_id);

CREATE VIEW players_rating AS
SELECT RANK() OVER (ORDER BY p.rating DESC) AS rank,
       p.id                                 AS player_id,
       p.username,
       p.rating
FROM players p;

CREATE INDEX idx_players_rating ON players_rating (player_id);