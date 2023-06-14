package coffee.lkh.dofusrpa.webservices.implemantations;

import coffee.lkh.dofusrpa.models.DofusCharacterClass;
import coffee.lkh.dofusrpa.models.dto.DofusCharacterDto;
import coffee.lkh.dofusrpa.models.dto.DofusAccountDto;
import coffee.lkh.dofusrpa.models.entities.DofusAccount;
import coffee.lkh.dofusrpa.models.entities.DofusCharacter;
import coffee.lkh.dofusrpa.repositories.IDofusAccountRepository;
import coffee.lkh.dofusrpa.repositories.implementations.DofusAccountRepository;
import coffee.lkh.dofusrpa.webservices.IDofusAccountService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.soap.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

@WebService(endpointInterface = "coffee.lkh.dofusrpa.webservices.IDofusAccountService",
        serviceName = "DofusAccountService")
public class DofusAccountService implements IDofusAccountService {


    private final DofusAccountRepository dofusAccountRepository;


    @Inject
    public DofusAccountService(IDofusAccountRepository dofusAccountRepository) {
        this.dofusAccountRepository = (DofusAccountRepository) dofusAccountRepository;
    }

    /**
     * @param limit
     * @return
     */
    @Override
    @WebMethod
    public List<DofusAccountDto> getAllAccounts(Integer limit) throws SOAPException {
        try{
            if(dofusAccountRepository == null) {
                throw new SOAPException("Database context is null!");
            }

            return dofusAccountRepository
                    .getAll()
                    .get(10, TimeUnit.SECONDS)
                    .stream()
                    .map(DofusAccount::toDto)
                    .toList();

        }catch (Exception ex){
            throw new SOAPException(ex.getMessage(), ex);
        }
    }

    @WebMethod
    @Override
    public SOAPMessage createAccount(DofusAccountDto account) throws SOAPException{
        final MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
        final SOAPMessage soapMessage = messageFactory.createMessage();

        try{
            if(dofusAccountRepository == null) {
                throw new SOAPException("Database context is null!");
            }

            //final SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
            Optional<List<Optional<DofusCharacter>>> tempCharacters = Optional.of(new ArrayList<>());
            account.getCharacters().forEach(x->tempCharacters.get().add(Optional.of(new DofusCharacter(0L, x.getName(), x.getCharacter_class()))));
            Boolean result = dofusAccountRepository.save(new DofusAccount(0L, account.getEmail(), account.getHashed_passwd(), tempCharacters)).get(3, TimeUnit.SECONDS);
            if(result)
                soapMessage.setProperty("HTTP_RESPONSE_CODE", 201);
            else
                soapMessage.setProperty("HTTP_RESPONSE_CODE", 406);

            soapMessage.saveChanges();

            return soapMessage;

        }catch (Exception ex){
            throw new SOAPException("Database context injection Exception", ex);
        }
    }

}