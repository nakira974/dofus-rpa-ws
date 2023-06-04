package coffee.lkh.dofusrpa.beans;

import coffee.lkh.dofusrpa.repositories.IDofusAccountRepository;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

public class CdiContainerInitializer {

    public static void initialize() {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        initializer.addBeanClasses(IDofusAccountRepository.class);
        initializer.disableDiscovery();
        try( SeContainer containerInitializer = initializer.initialize()){
            if(!containerInitializer.isRunning()) throw new RuntimeException("CDI container is not running, server stop!");
            System.out.println("\u001B[32m" .concat("CDI container has started correctly").concat("\u001B[0m"));
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }
}