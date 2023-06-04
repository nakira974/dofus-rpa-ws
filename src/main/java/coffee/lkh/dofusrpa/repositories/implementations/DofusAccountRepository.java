package coffee.lkh.dofusrpa.repositories.implementations;

import coffee.lkh.dofusrpa.models.DofusCharacterClass;
import coffee.lkh.dofusrpa.models.entities.DofusAccount;
import coffee.lkh.dofusrpa.models.entities.DofusCharacter;
import coffee.lkh.dofusrpa.repositories.DatabaseUtil;
import coffee.lkh.dofusrpa.repositories.IDofusAccountRepository;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.activation.DataSource;
import jakarta.annotation.Priority;
import jakarta.ejb.Singleton;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jdk.jfr.Name;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@Singleton
public class DofusAccountRepository implements IDofusAccountRepository {
    private final HikariDataSource dataSource;

    public DofusAccountRepository() {
        dataSource = DatabaseUtil.getDataSource();
    }

    /**Fetch all {@link DofusAccount} objects along with their associated characters from the materialized view `user_characters`,*/
    public List<DofusAccount> getAll() {
        final List<DofusAccount> accounts = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT email, password, name, class FROM user_characters");
             ResultSet rs = stmt.executeQuery()) {

            // Map of account ids to DofusAccount objects
            Map<Long, DofusAccount> accountMap = new HashMap<>();

            // Iterate over the result set and create DofusAccount and DofusCharacter objects
            while (rs.next()) {
                String email = rs.getString("email");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String characterClass = rs.getString("class");

                // Create a DofusAccount object if it doesn't exist yet
                DofusAccount account = accountMap.computeIfAbsent(rs.getLong("account_id"), id -> {
                    DofusAccount newAccount = new DofusAccount(id, email, password, Optional.of(new ArrayList<>()));
                    accounts.add(newAccount);
                    return newAccount;
                });

                // Add the character to the account's list of characters
                DofusCharacter character = new DofusCharacter(null, name, DofusCharacterClass.valueOf(characterClass));
                account.characters().orElseThrow().add(Optional.of(character));
            }

            System.out.println("\u001B[35m ACCOUNTS AND THEIR CHARACTERS HAVE BEEN SELECTED \u001B[0m");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return accounts;
    }

    public DofusAccount getById(Long id) {
        DofusAccount account = null;
        try{

            System.out.printf("\u001B[35m ACCOUNT %d HAS BEEN CREATED \u001B[0m%n",id );
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return account;
    }

    public void save(DofusAccount entity) {
        try{

            System.out.printf("\u001B[35m ACCOUNT %d HAS BEEN CREATED \u001B[0m%n",entity.id() );
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    public void delete(DofusAccount entity) {
        try{

            System.out.printf("\u001B[35m ACCOUNT %d HAS BEEN DELETED \u001B[0m%n",entity.id() );
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }
}
