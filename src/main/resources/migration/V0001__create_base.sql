CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS players
(
    id                uuid    default uuid_generate_v4(),
    username          VARCHAR(255),
    login             VARCHAR(255),
    registration_date TIMESTAMP WITHOUT TIME ZONE,
    last_online       TIMESTAMP WITHOUT TIME ZONE,
    is_active         BOOLEAN DEFAULT TRUE,
    up_coins          BIGINT,
    session           INT,
    rating            INT,
    CONSTRAINT pk_players PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS udx_players_username ON players (username);

CREATE TABLE IF NOT EXISTS characters
(
    id             INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    character_id   INT,
    character_name VARCHAR(255),
    items          jsonb,
    player_id      uuid,
    CONSTRAINT pk_characters PRIMARY KEY (id),
    CONSTRAINT FK_CHARACTERS_ON_PLAYER FOREIGN KEY (player_id) REFERENCES players (id)
);

CREATE INDEX IF NOT EXISTS idx_characters_player_id ON characters (player_id);

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
    character_id INT NOT NULL,
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
    id                  BIGSERIAL NOT NULL,
    player_id           uuid,
    character_id        INT,
    character_game_id   INT,
    character_params    jsonb,
    level               BIGINT,
    stage               INT,
    shit_coins          INT,
    up_coins            BIGINT,
    enemy               INT,
    world               INT,
    boosts              jsonb,
    consumables         jsonb,
    start_timestamp     timestamptz,
    end_timestamp       timestamptz,
    duration            INT,
    is_finished         BOOLEAN DEFAULT FALSE,
    finished_by_victory BOOLEAN DEFAULT FALSE,
    is_heroic           BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_runs PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_runs_players_id ON runs (player_id);

CREATE VIEW runs_leaderboard AS
WITH peak_levels AS (SELECT player_id, character_game_id, MAX(level) AS max
                     FROM runs
                     WHERE is_finished = true
                     GROUP BY player_id, character_game_id),
     ranked_runs AS (SELECT r.id                                                                           AS run_id,
                            p.username,
                            r.character_game_id,
                            peak_levels.player_id,
                            peak_levels.max,
                            ROW_NUMBER()
                            OVER (PARTITION BY peak_levels.player_id, peak_levels.character_game_id ORDER BY r.id) AS rn
                     FROM peak_levels
                              LEFT JOIN runs r ON peak_levels.player_id = r.player_id
                         AND peak_levels.max = r.level
                         AND peak_levels.character_game_id = r.character
                              LEFT JOIN players p ON p.id = r.player_id)
SELECT run_id, username, character_game_id, player_id, max
FROM ranked_runs
WHERE rn = 1
ORDER BY max DESC;


CREATE VIEW players_rating AS
SELECT RANK() OVER (ORDER BY p.rating DESC) AS rank,
       p.id                                 AS player_id,
       p.username,
       p.rating
FROM players p
WHERE p.rating IS NOT NULL;