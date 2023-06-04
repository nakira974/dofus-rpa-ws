package coffee.lkh.dofusrpa.repositories;

import coffee.lkh.dofusrpa.models.entities.DofusAccount;
import jakarta.ejb.Local;

import java.util.List;

@Local
public interface IDofusAccountRepository {
    public List<DofusAccount> getAll();
    public DofusAccount getById(Long id);
    public void save(DofusAccount entity);
    public void delete(DofusAccount entity);
}
