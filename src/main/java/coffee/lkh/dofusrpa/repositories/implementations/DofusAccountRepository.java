package coffee.lkh.dofusrpa.repositories.implementations;

import coffee.lkh.dofusrpa.models.entities.DofusAccount;
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

import java.util.ArrayList;
import java.util.List;

@Singleton
public class DofusAccountRepository implements IDofusAccountRepository {
    private final HikariDataSource dataSource;

    public DofusAccountRepository() {
        dataSource = DatabaseUtil.getDataSource();
    }

    public List<DofusAccount> getAll() {
        final List<DofusAccount> accounts = new ArrayList<>();
        try{

            System.out.println("\u001B[35m ACCOUNTS HAS BEEN SELECTED \u001B[0m");
        }catch (Exception ex){
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
