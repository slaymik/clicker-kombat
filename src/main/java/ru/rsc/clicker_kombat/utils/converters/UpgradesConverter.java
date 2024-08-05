package ru.rsc.clicker_kombat.utils.converters;

import jakarta.persistence.Converter;
import ru.rsc.clicker_kombat.model.domain.Upgrades;
@Converter(autoApply = true)
public class UpgradesConverter extends JsonBConverter<Upgrades>{
    public UpgradesConverter(){
        super(Upgrades.class);
    }
}