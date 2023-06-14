package coffee.lkh.dofusrpa.models.dto;

import coffee.lkh.dofusrpa.models.entities.DofusAccount;
import coffee.lkh.dofusrpa.models.entities.DofusCharacter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@XmlRootElement
@XmlSeeAlso({DofusAccount.class, DofusCharacter.class})
public class DofusAccountDto {

    private String email;
    private String hashed_passwd;
    private List<DofusCharacterDto> characters;

    public DofusAccountDto() {
        this("", "", new ArrayList<>());
    }

    public DofusAccountDto(String email, String hashed_passwd, List<DofusCharacterDto> characters) {
        this.email = email;
        this.hashed_passwd = hashed_passwd;
        this.characters = characters;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashed_passwd() {
        return hashed_passwd;
    }

    public void setHashed_passwd(String hashed_passwd) {
        this.hashed_passwd = hashed_passwd;
    }

    public List<DofusCharacterDto> getCharacters() {
        return characters;
    }

    public void setCharacters(List<DofusCharacterDto> characters) {
        this.characters = characters;
    }
}