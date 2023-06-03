package coffee.lkh.dofusrpa.webservices;

import coffee.lkh.dofusrpa.models.dto.DofusAccountDto;
import jakarta.jws.WebService;
import jakarta.xml.soap.SOAPException;

import java.util.List;
import java.util.Optional;

@WebService

public interface IDofusAccountService {

    public List<DofusAccountDto> getUntreatedAccounts(Optional<Short> limit) throws SOAPException;
}
