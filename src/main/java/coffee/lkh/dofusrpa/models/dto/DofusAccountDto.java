package coffee.lkh.dofusrpa.models.dto;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "dofus_account")
public record DofusAccountDto(@NotNull String email, @NotNull String hashed_passwd, Optional<List<Optional<DofusCharacterDto>>> characters) {

    public DofusAccountDto() {
        this("", "", Optional.empty());
    }

    @XmlElement(name = "dofus_account_email")
    @Override
    public @NotNull String email() {
        return email;
    }

    @XmlElement(name = "dofus_account_hashed_passwd")
    @Override
    public @NotNull String hashed_passwd() {
        return hashed_passwd;
    }

    @XmlElement(name = "dofus_account_characters")
    @Override
    public Optional<List<Optional<DofusCharacterDto>>> characters() {
        return characters;
    }
}
