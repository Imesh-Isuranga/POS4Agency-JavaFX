package lk.crossorigin.agency.db;

import lk.crossorigin.agency.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.Session;

public class HibernateUtil {
    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();


        Metadata metadata = new MetadataSources(standardRegistry)
                .addAnnotatedClass(Discount.class)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderBook.class)
                .addAnnotatedClass(OrderDetail.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(ReturnStock.class)
                .addAnnotatedClass(Shop.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();

        return metadata.getSessionFactoryBuilder().build();
    }

    public static Session openSesion(){
        return sessionFactory.openSession();
    }

}