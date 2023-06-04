package coffee.lkh.dofusrpa.models.dto;

import java.util.List;
import java.util.Optional;

public class DofusAccountDto {

    private String email;
    private String hashed_passwd;
    private Optional<List<Optional<DofusCharacterDto>>> characters;

    public DofusAccountDto() {
        this("", "", Optional.empty());
    }

    public DofusAccountDto(String email, String hashed_passwd, Optional<List<Optional<DofusCharacterDto>>> characters) {
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

    public Optional<List<Optional<DofusCharacterDto>>> getCharacters() {
        return characters;
    }

    public void setCharacters(Optional<List<Optional<DofusCharacterDto>>> characters) {
        this.characters = characters;
    }
}