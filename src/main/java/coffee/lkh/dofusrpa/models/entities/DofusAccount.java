package coffee.lkh.dofusrpa.models.entities;


import coffee.lkh.dofusrpa.models.dto.DofusAccountDto;
import coffee.lkh.dofusrpa.models.dto.DofusCharacterDto;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public record DofusAccount(@Id
                           @GeneratedValue(strategy = GenerationType.IDENTITY)
                           Long id,
                           @Column(nullable = false)
                           @NotNull String email,
                           @Column(nullable = false)
                           @NotNull String password,

                           @Column(nullable = true)
                           Optional<List<Optional< DofusCharacter>>> characters
                           ) {

    public DofusAccount(){
        this(0L, "", "", Optional.of(new ArrayList<>()));
    }

    @Contract("_ -> new")
    public static @NotNull DofusCharacter fromDto(@NotNull DofusCharacterDto dto) {
        return new DofusCharacter(null, dto.name(), dto.character_class());
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
