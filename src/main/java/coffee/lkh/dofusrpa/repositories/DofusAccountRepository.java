package coffee.lkh.dofusrpa.repositories;

import coffee.lkh.dofusrpa.models.entities.DofusAccount;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class DofusAccountRepository {
    @PersistenceContext(unitName = "defaultApplicationPersistenceUnit")
    private EntityManager entityManager;

    public List<DofusAccount> getAll() {
        List<DofusAccount> accounts = new ArrayList<>();
        try{
            final List<DofusAccount> temp_accounts =entityManager
                    .createQuery("SELECT e FROM DofusAccount e", DofusAccount.class)
                    .getResultList();
            if(temp_accounts == null) throw new Exception();
            accounts = temp_accounts;
            System.out.println("\u001B[35m ACCOUNTS HAS BEEN SELECTED \u001B[0m");
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }

        return accounts;
    }

    public DofusAccount getById(Long id) {
        return entityManager.find(DofusAccount.class, id);
    }

    public void save(DofusAccount entity) {
        entityManager.persist(entity);
    }

    public void delete(DofusAccount entity) {
        entityManager.remove(entity);
    }
}
