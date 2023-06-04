package coffee.lkh.dofusrpa.beans;

import coffee.lkh.dofusrpa.webservices.implemantations.DofusAccountService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFServlet;

public class DofusEndpointListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        // Register the CXF servlet with the servlet context
        // Register your endpoint beans with the CXF runtime
        try{
            DofusAccountService dofusEndpointListener = new DofusAccountService();
            JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
            factory.setAddress("/dofus");
            factory.setServiceBean(dofusEndpointListener);
            factory.create();
        }catch (Exception ex){
            System.err.println("Can 't create endpoint!\n"+ex.getMessage());
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // Do any cleanup here
    }
}