package coffee.lkh.dofusrpa.webservices.implemantations;

import coffee.lkh.dofusrpa.models.dto.DofusAccountDto;
import coffee.lkh.dofusrpa.models.entities.DofusAccount;
import coffee.lkh.dofusrpa.repositories.DofusAccountRepository;
import coffee.lkh.dofusrpa.webservices.IDofusAccountService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.soap.SOAPException;

import java.util.List;
import java.util.Optional;

@WebService(endpointInterface = "coffee.lkh.dofusrpa.webservices.IDofusAccountService",
        serviceName = "DofusAccountService")
public class DofusAccountService implements IDofusAccountService {
    @Inject
    private DofusAccountRepository _dofusAccountRepository;

    /**
     * @param limit
     * @return
     */
    @Override
    @WebMethod
    public List<DofusAccountDto> getUntreatedAccounts(Optional<Short> limit) throws SOAPException {
       return _dofusAccountRepository.getAll().stream().map(DofusAccount::toDto).toList();
    }
}
