package coffee.lkh.dofusrpa.repositories.implementations;

import coffee.lkh.dofusrpa.models.DofusCharacterClass;
import coffee.lkh.dofusrpa.models.entities.DofusAccount;
import coffee.lkh.dofusrpa.models.entities.DofusCharacter;
import coffee.lkh.dofusrpa.repositories.AnkamaGamesDatabaseManager;
import coffee.lkh.dofusrpa.repositories.IDofusAccountRepository;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.ejb.Singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;

@Singleton
public class DofusAccountRepository implements IDofusAccountRepository {
    private final HikariDataSource dataSource;
    private final ExecutorService threadPoolExecutor;

    public DofusAccountRepository() {
        threadPoolExecutor = Executors.newCachedThreadPool();
        dataSource = AnkamaGamesDatabaseManager.getDataSource();
    }

    /**Fetch all {@link DofusAccount} objects along with their associated characters from the materialized view `user_characters`,*/
    public Future<List<DofusAccount>> getAll() {
        final CompletableFuture<List<DofusAccount>> future = new CompletableFuture<>();
        final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

        threadPoolExecutor.submit(() -> {
            final List<DofusAccount> accounts = new ArrayList<>();
            try {
                // Get connection from thread-local variable or create a new one if not present
                Connection connection = connectionHolder.get();
                if (connection == null) {
                    connection = dataSource.getConnection();
                    connectionHolder.set(connection);
                }

                final String sql = "SELECT DISTINCT id, email, password FROM accounts";
                try (final PreparedStatement statement = connection.prepareStatement(sql)) {
                    try (final ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            final Long id = resultSet.getLong("id"); // not used in materialized view query
                            final String email = resultSet.getString("email");
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

                            final DofusAccount account = new DofusAccount(id, email, "password", Optional.of(characters));
                            accounts.add(account);
                        }
                    }
                }
                System.out.printf("\u001B[35m ALL ACCOUNTS AND THEIR CHARACTERS %d HAS BEEN SELECTED \u001B[0m%n" );
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

                future.complete(accounts);
            }
        });

        threadPoolExecutor.shutdown();

        return future;
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

    public Future<Boolean> save(DofusAccount entity) {
        final CompletableFuture<Boolean> future = new CompletableFuture<>();
        final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

        threadPoolExecutor.submit(() -> {
            try {
                // Get connection from thread-local variable or create a new one if not present
                Connection connection = connectionHolder.get();
                if (connection == null) {
                    connection = dataSource.getConnection();
                    connectionHolder.set(connection);
                }

                final String sql = "INSERT INTO accounts(email, password) VALUES (?, ?)";
                try (final PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, entity.email());
                    statement.setString(2, entity.password());
                    statement.executeUpdate();
                }

                // Get account id from email
                final String idSql = "SELECT id FROM accounts WHERE email = ?";
                try (final PreparedStatement idStatement = connection.prepareStatement(idSql)) {
                    idStatement.setString(1, entity.email());
                    try (final ResultSet resultSet = idStatement.executeQuery()) {
                        if (resultSet.next()) {
                            final Long accountId = resultSet.getLong("id");

                            // Insert characters for account
                            final List<Optional<DofusCharacter>> characters = entity.characters().orElse(new ArrayList<>());
                            final String characterSql = "INSERT INTO characters(name, class, owner) VALUES (?, ?, ?)";
                            try (final PreparedStatement characterStatement = connection.prepareStatement(characterSql)) {
                                for (Optional<DofusCharacter> character : characters) {
                                    characterStatement.setString(1, character.get().name());
                                    characterStatement.setString(2, character.get().character_class().name());
                                    characterStatement.setLong(3, accountId);
                                    characterStatement.addBatch();
                                }
                                characterStatement.executeBatch();
                            }
                        }
                    }
                }

                future.complete(true);
                System.out.printf("\u001B[35m ACCOUNT %s HAS BEEN CREATED \u001B[0m%n",entity.email() );
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                future.complete(false);
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
        });

        threadPoolExecutor.shutdown();

        return future;
    }

    public void delete(DofusAccount entity) {
        try{

            System.out.printf("\u001B[35m ACCOUNT %d HAS BEEN DELETED \u001B[0m%n",entity.id() );
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }
}
