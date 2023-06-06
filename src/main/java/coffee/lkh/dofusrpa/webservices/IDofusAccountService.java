package coffee.lkh.dofusrpa.webservices;

import coffee.lkh.dofusrpa.models.dto.DofusAccountDto;
import coffee.lkh.dofusrpa.models.dto.DofusCharacterDto;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;

import java.util.List;


@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public interface IDofusAccountService {
    @WebMethod
    public List<DofusAccountDto> getAllAccounts(@WebParam(name = "limit") Integer limit) throws SOAPException;
    @WebMethod
    public SOAPMessage createAccount(@WebParam(name = "account") DofusAccountDto account) throws SOAPException;
}
