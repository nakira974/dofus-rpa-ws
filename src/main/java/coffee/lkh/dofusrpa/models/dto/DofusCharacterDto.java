package coffee.lkh.dofusrpa.models.dto;

import coffee.lkh.dofusrpa.models.DofusCharacterClass;
import coffee.lkh.dofusrpa.webservices.implemantations.DofusAccountService;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public record DofusCharacterDto(@NotNull String name, @NotNull DofusCharacterClass character_class) {

    public DofusCharacterDto(){
        this("", DofusCharacterClass.UNDEFINED);
    }
    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull DofusCharacterClass character_class() {
        return character_class;
    }
}
