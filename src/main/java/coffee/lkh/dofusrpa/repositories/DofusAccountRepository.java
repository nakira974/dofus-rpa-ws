package coffee.lkh.dofusrpa.repositories;

import coffee.lkh.dofusrpa.models.entities.DofusAccount;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class DofusAccountRepository {
    @PersistenceContext(unitName = "defaultApplicationPersistenceUnit")
    private EntityManager entityManager;

    public List<DofusAccount> getAll() {
        return entityManager
                .createQuery("SELECT e FROM DofusAccount e", DofusAccount.class)
                .getResultList();
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
