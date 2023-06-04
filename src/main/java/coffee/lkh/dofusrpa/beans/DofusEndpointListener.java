package coffee.lkh.dofusrpa.beans;

import coffee.lkh.dofusrpa.webservices.implemantations.DofusAccountService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFServlet;

public class DofusEndpointListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        // Register the CXF servlet with the servlet context
        event.getServletContext().addServlet("CXFServlet", new CXFServlet()).addMapping("/services/*");

        // Register your endpoint beans with the CXF runtime
        DofusAccountService dofusEndpointListener = new DofusAccountService();
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setAddress("/dofus");
        factory.setServiceBean(dofusEndpointListener);
        factory.create();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // Do any cleanup here
    }
}