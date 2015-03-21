package controllers.common;

/* Libraries */
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * TODO: Add JavaDocs for this class.
 */
public class HibernateUtil {

    /** <p>Creates a static JPA session factory</p> **/
    private static final SessionFactory mySessionFactory;

    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure();
            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
            serviceRegistryBuilder.applySettings(configuration.getProperties());
            mySessionFactory = configuration.buildSessionFactory(serviceRegistryBuilder.build());
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * <p>Returns the JPA Hibernate Session Factory.</p>
     *
     * @return The current session factory.
     */
    public static SessionFactory getSessionFactory() {
        return mySessionFactory;
    }

}