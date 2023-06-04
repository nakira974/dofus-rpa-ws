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
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Singleton
public class DofusAccountRepository implements IDofusAccountRepository {
    private final HikariDataSource dataSource;

    public DofusAccountRepository() {
        dataSource = DatabaseUtil.getDataSource();
    }

    /**Fetch all {@link DofusAccount} objects along with their associated characters from the materialized view `user_characters`,*/
    public List<DofusAccount> getAll() {
        final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
        final List<DofusAccount> accounts = new ArrayList<>();
        try {
            // Get connection from thread-local variable or create a new one if not present
            Connection connection = connectionHolder.get();
            if (connection == null) {
                connection = dataSource.getConnection();
                connectionHolder.set(connection);
            }

            final String sql = "SELECT DISTINCT email FROM accounts";
            try (final PreparedStatement statement = connection.prepareStatement(sql)) {
                try (final ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        final Long id = -1L; // not used in materialized view query
                        final String email = resultSet.getString("email");
                        final String password = resultSet.getString("password");
                        final List<Optional<DofusCharacter>> characters = new ArrayList<>();

                        // Get characters for user from materialized view
                        final String characterSql = "SELECT name, class FROM user_characters WHERE email = ?";
                        try (final PreparedStatement characterStatement = connection.prepareStatement(characterSql)) {
                            characterStatement.setString(1, email);
                            try (final ResultSet characterResultSet = characterStatement.executeQuery()) {
                                while (characterResultSet.next()) {
                                    final String name = characterResultSet.getString("name");
                                    final DofusCharacterClass characterClass = DofusCharacterClass.valueOf(characterResultSet.getString("class"));
                                    final DofusCharacter character = new DofusCharacter(null, name, characterClass);
                                    characters.add(Optional.of(character));
                                }
                            }
                        }

                        final DofusAccount account = new DofusAccount(id, email, password, Optional.of(characters));
                        accounts.add(account);
                    }
                }
            }
            System.out.printf("\u001B[35m ALL ACCOUNTS AND THEIR CHARACTERS HAS BEEN SELECTED \u001B[0m%n" );
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } finally {
            // Close connection and remove from thread-local variable
            final Connection connection = connectionHolder.get();
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                } finally {
                    connectionHolder.remove();
                }
            }
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
