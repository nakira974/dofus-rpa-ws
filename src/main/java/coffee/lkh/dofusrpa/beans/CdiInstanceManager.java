package coffee.lkh.dofusrpa.beans;

import jakarta.enterprise.context.spi.CreationalContext;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.inject.spi.CDI;
import org.apache.tomcat.InstanceManager;

import java.lang.reflect.InvocationTargetException;

public class CdiInstanceManager implements InstanceManager {

    private final BeanManager beanManager;

    public CdiInstanceManager() {
        try {
            this.beanManager = CDI.current().getBeanManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object newInstance(Class<?> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Bean<?> bean = beanManager.getBeans(clazz).iterator().next();
        CreationalContext<?> ctx = beanManager.createCreationalContext(bean);
        return beanManager.getReference(bean, clazz, ctx);
    }

    @Override
    public Object newInstance(String className) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        return newInstance(clazz);
    }

    @Override
    public Object newInstance(String fqcn, ClassLoader classLoader) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Class<?> clazz = classLoader.loadClass(fqcn);
        return newInstance(clazz);
    }

    @Override
    public void newInstance(Object o) throws IllegalAccessException, InvocationTargetException {
        throw new UnsupportedOperationException();
    }
    @Override
    public void destroyInstance(Object o) throws IllegalAccessException, InvocationTargetException {
        // TODO: implement if necessary
    }

    /**
     * Called by the component using the InstanceManager periodically to perform
     * any regular maintenance that might be required. By default, this method
     * is a NO-OP.
     */
    @Override
    public void backgroundProcess() {
        InstanceManager.super.backgroundProcess();
    }
}