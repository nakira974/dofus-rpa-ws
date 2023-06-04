package coffee.lkh.dofusrpa.beans;

import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Set;

public class CdiUtils {
    public static BeanManager getBeanManager() {
        try {
            InitialContext initialContext = new InitialContext();
            try {
                return (BeanManager) initialContext.lookup("java:comp/BeanManager");
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        } catch (NamingException e) {
            throw new RuntimeException("Failed to retrieve BeanManager!", e);
        }
    }

    public static <T> T getBean(Class<T> c) {
        T result = null;
        BeanManager bm = getBeanManager();
        Set<Bean<?>> beans = bm.getBeans(c);
        if (! beans.isEmpty()) {
            Bean<?> bean = beans.iterator().next();
            result = c.cast(bm.getReference(bean, c, bm.createCreationalContext(bean)));
        }
        return result;
    }
}