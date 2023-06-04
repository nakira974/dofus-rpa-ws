package coffee.lkh.dofusrpa.webservices.implemantations;

import coffee.lkh.dofusrpa.models.entities.DofusAccount;
import coffee.lkh.dofusrpa.models.dto.DofusAccountDto;
import coffee.lkh.dofusrpa.repositories.IDofusAccountRepository;
import coffee.lkh.dofusrpa.webservices.IDofusAccountService;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.soap.SOAPException;

import java.util.List;

@WebService(endpointInterface = "coffee.lkh.dofusrpa.webservices.IDofusAccountService",
        serviceName = "DofusAccountService")
public class DofusAccountService implements IDofusAccountService {

    @Inject
    private IDofusAccountRepository dofusAccountRepository;

    /**
     * @param limit
     * @return
     */
    @Override
    @WebMethod
    public List<DofusAccountDto> getUntreatedAccounts(Short limit) throws SOAPException {

        try{
            if(dofusAccountRepository == null) {
                throw new SOAPException("Database context is null!");
            }
            return dofusAccountRepository.getAll().stream().map(DofusAccount::toDto).toList();
        }catch (Exception ex){
            throw new SOAPException("Database context injection Exception", ex);
        }
    }

}