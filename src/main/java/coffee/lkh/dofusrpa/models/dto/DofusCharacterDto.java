package coffee.lkh.dofusrpa.models.dto;

import coffee.lkh.dofusrpa.models.DofusCharacterClass;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DofusCharacterDto {

    private String name;
    private DofusCharacterClass character_class;

    public DofusCharacterDto(){
        this("", DofusCharacterClass.UNDEFINED);
    }

    public DofusCharacterDto(String name, DofusCharacterClass character_class) {
        this.name = name;
        this.character_class = character_class;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public DofusCharacterClass getCharacter_class() {
        return character_class;
    }

    public void setCharacter_class(@NotNull DofusCharacterClass character_class) {
        this.character_class = character_class;
    }
}