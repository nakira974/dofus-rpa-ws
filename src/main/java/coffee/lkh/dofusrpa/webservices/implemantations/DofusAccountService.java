package coffee.lkh.dofusrpa.webservices.implemantations;

import coffee.lkh.dofusrpa.models.dto.DofusAccountDto;
import coffee.lkh.dofusrpa.webservices.IDofusAccountService;
import jakarta.jws.WebService;
import jakarta.xml.soap.SOAPException;

import java.util.List;
import java.util.Optional;

@WebService(endpointInterface = "coffee.lkh.dofusrpa.webservices.IDofusAccountService",
        serviceName = "DofusAccountService")
public class DofusAccountService implements IDofusAccountService {
    /**
     * @param limit
     * @return
     */
    @Override
    public List<DofusAccountDto> getUntreatedAccounts(Optional<Short> limit) throws SOAPException {
        return null;
    }
}
