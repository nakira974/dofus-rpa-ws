package coffee.lkh.dofusrpa.models.dto;

import coffee.lkh.dofusrpa.models.DofusCharacterClass;
import org.jetbrains.annotations.NotNull;

public record DofusCharacterDto(@NotNull String name, @NotNull DofusCharacterClass character_class) {

    @Override
    public @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull DofusCharacterClass character_class() {
        return character_class;
    }
}
