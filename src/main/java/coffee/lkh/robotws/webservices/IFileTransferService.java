package coffee.lkh.robotws.webservices;

import jakarta.jws.WebService;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;

@WebService
public interface IFileTransferService {
    /* Advanced usecase of passing an Interface in.  JAX-WS/JAXB does not
     * support interfaces directly.  Special XmlAdapter classes need to
     * be written to handle them
     */
    public SOAPMessage sendFile(byte[] fileData) throws SOAPException;

}
