package coffee.lkh.dofusrpa.webservices.implemantations;

import coffee.lkh.dofusrpa.models.DofusCharacterClass;
import coffee.lkh.dofusrpa.models.dto.DofusCharacterDto;
import coffee.lkh.dofusrpa.models.dto.DofusAccountDto;
import coffee.lkh.dofusrpa.models.entities.DofusAccount;
import coffee.lkh.dofusrpa.repositories.IDofusAccountRepository;
import coffee.lkh.dofusrpa.webservices.IDofusAccountService;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.soap.SOAPException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@WebService(endpointInterface = "coffee.lkh.dofusrpa.webservices.IDofusAccountService",
        serviceName = "DofusAccountService")
public class DofusAccountService implements IDofusAccountService {

    @Inject
    private IDofusAccountRepository dofusAccountRepository;

    private final ThreadPoolExecutor threadPoolExecutor;

    public DofusAccountService() {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    @Contract(" -> new")
    private @NotNull ArrayList<DofusAccountDto> getTestUsers(){

        return new ArrayList<>() {{
            add(
                    new DofusAccountDto("maxime.loukhal@gmail.com", "54gff65g4f65",

                                    new ArrayList<>() {{
                                        add(new DofusCharacterDto("Ah_Mon_Gars", DofusCharacterClass.PANDAWA))
                                        ;
                                    }}
                    ));
        } {{
            add(
                    new DofusAccountDto("anthony.mereu77@gmail.com", "54gff65g4f65",

                            new ArrayList<>() {{
                                add(new DofusCharacterDto("Ah_Mon_Soin", DofusCharacterClass.ENIRIPSA))
                                ;
                            }}
                    ));
        }}};
    }


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

            return new ArrayList<>(dofusAccountRepository.getAll().get(10, TimeUnit.SECONDS).stream().map(DofusAccount::toDto).toList());

        }catch (Exception ex){
            throw new SOAPException("Database context injection Exception", ex);
        }
    }

}