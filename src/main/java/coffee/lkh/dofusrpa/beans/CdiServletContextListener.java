package coffee.lkh.dofusrpa.beans;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.tomcat.InstanceManager;

public class CdiServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try{
            InstanceManager im = new CdiInstanceManager();
            sce.getServletContext().setAttribute(InstanceManager.class.getName(), im);
            CdiContainerInitializer.initialize();
        }catch (Exception ex){
            System.err.println(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // ignore
    }
}
