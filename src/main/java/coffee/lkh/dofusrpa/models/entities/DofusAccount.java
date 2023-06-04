package coffee.lkh.dofusrpa.models.entities;


import coffee.lkh.dofusrpa.models.dto.DofusAccountDto;
import coffee.lkh.dofusrpa.models.dto.DofusCharacterDto;
import jakarta.xml.bind.annotation.XmlElement;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public record DofusAccount(
                           Long id,
                           @NotNull String email,
                           @NotNull String password,

                           Optional<List<Optional< DofusCharacter>>> characters
                           ) {

    public DofusAccount(){
        this(0L, "", "", Optional.of(new ArrayList<>()));
    }

    @Contract("_ -> new")
    public static @NotNull DofusCharacter fromDto(@NotNull DofusCharacterDto dto) {
        return new DofusCharacter(null, dto.getName(), dto.getCharacter_class());
    }

    public @NotNull DofusAccountDto toDto() {
        Optional<List<Optional<DofusCharacterDto>>> charactersDto = Optional.of(new ArrayList<>());
        if(characters().isPresent())
            characters().get().forEach(x->{
                x.ifPresent(dofusCharacter -> charactersDto.get().add(Optional.of(dofusCharacter.toDto())));
        });
        return new DofusAccountDto(email(), password(), charactersDto);
    }

    @Override
    public @NotNull Long id() {
        return id;
    }

    @Override
    public @NotNull String email() {
        return email;
    }

    @Override
    public @NotNull String password() {
        return password;
    }

    @Override
    public Optional<List<Optional<DofusCharacter>>> characters() {
        return characters;
    }
}
