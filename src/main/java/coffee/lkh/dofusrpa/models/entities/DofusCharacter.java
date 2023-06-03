package coffee.lkh.dofusrpa.models.entities;

import coffee.lkh.dofusrpa.models.DofusCharacterClass;
import coffee.lkh.dofusrpa.models.dto.DofusAccountDto;
import coffee.lkh.dofusrpa.models.dto.DofusCharacterDto;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record DofusCharacter(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        @Column(nullable = false)
        @NotNull String name,
        @Column(nullable = false)
        @NotNull DofusCharacterClass character_class)
{

    @Contract("_ -> new")
    public static @NotNull DofusCharacter fromDto(@NotNull DofusCharacterDto dto) {
        return new DofusCharacter(null, dto.name(), dto.character_class());
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
