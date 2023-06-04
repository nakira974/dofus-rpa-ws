package coffee.lkh.dofusrpa.webservices.implemantations;

import coffee.lkh.dofusrpa.beans.CdiUtils;
import coffee.lkh.dofusrpa.models.entities.DofusAccount;
import coffee.lkh.dofusrpa.models.dto.DofusAccountDto;
import coffee.lkh.dofusrpa.repositories.IDofusAccountRepository;
import coffee.lkh.dofusrpa.repositories.implementations.DofusAccountRepository;
import coffee.lkh.dofusrpa.webservices.IDofusAccountService;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.soap.SOAPException;

import java.util.List;
import java.util.Optional;

@RequestScoped
@WebService(endpointInterface = "coffee.lkh.dofusrpa.webservices.IDofusAccountService",
        serviceName = "DofusAccountService")
public class DofusAccountService implements IDofusAccountService {
    public IDofusAccountRepository getDofusAccountRepository() {
        try{
            return CdiUtils.getBean(DofusAccountRepository.class);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    /**
     * @param limit
     * @return
     */
    @Override
    @WebMethod
    public List<DofusAccountDto> getUntreatedAccounts(Optional<Short> limit) throws SOAPException {

        try{
            IDofusAccountRepository repository = getDofusAccountRepository();
            if(repository == null) {
                throw new SOAPException("Database context is null!");
            }
            return getDofusAccountRepository().getAll().stream().map(DofusAccount::toDto).toList();
        }catch (Exception ex){
            throw new SOAPException("Database context injection Exception", ex);
        }
    }

}