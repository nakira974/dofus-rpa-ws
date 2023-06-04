package coffee.lkh.dofusrpa.models.entities;

import coffee.lkh.dofusrpa.models.DofusCharacterClass;
import coffee.lkh.dofusrpa.models.dto.DofusAccountDto;
import coffee.lkh.dofusrpa.models.dto.DofusCharacterDto;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record DofusCharacter(
        Long id,
        @NotNull String name,
        @NotNull DofusCharacterClass character_class)
{
    public DofusCharacter(){
        this(0L, "", DofusCharacterClass.UNDEFINED);
    }
    @Contract("_ -> new")
    public static @NotNull DofusCharacter fromDto(@NotNull DofusCharacterDto dto) {
        return new DofusCharacter(null, dto.getName(), dto.getCharacter_class());
    }

    public @NotNull DofusCharacterDto toDto() {
        return new DofusCharacterDto(name(), character_class());
    }

    @Override
    public @NotNull Long id() {
        return id;
    }

    @Override
    public @NotNull DofusCharacterClass character_class() {
        return character_class;
    }

    @Override
    public @NotNull String name() {
        return name;
    }
}
