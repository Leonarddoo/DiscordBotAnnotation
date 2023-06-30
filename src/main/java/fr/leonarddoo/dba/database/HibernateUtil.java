package fr.leonarddoo.dba.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        // Créer la configuration Hibernate à partir du fichier de configuration
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        // Créer une fabrique de sessions (SessionFactory) à partir de la configuration
        sessionFactory = configuration.buildSessionFactory();
    }

    public static Session getSession() {
        // Ouvrir une session Hibernate
        return sessionFactory.openSession();
    }

    public static Object saveEntity(Object entity) {
        Transaction transaction = null;
        Session session = null;
        Object savedEntity = null;

        try {
            session = getSession();
            transaction = session.beginTransaction();
            savedEntity = session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return savedEntity;
    }

    public static void updateEntity(Object entity) {
        Transaction transaction = null;
        Session session = null;

        try {
            session = getSession();
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static <T> T getEntityById(Class<T> entityClass, Serializable id) {
        Session session = getSession();
        T entity = null;

        try (session) {
            entity = session.get(entityClass, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entity;
    }

    public static <T> List<T> getAllEntities(Class<T> entityClass) {
        List<T> entities = null;
        Transaction transaction = null;
        Session session = null;

        try {
            session = getSession();
            transaction = session.beginTransaction();

            // Utiliser une requête HQL pour sélectionner tous les éléments de la table
            String queryString = "FROM " + entityClass.getSimpleName();
            Query<T> query = session.createQuery(queryString, entityClass);
            entities = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return entities;
    }

    public static void deleteEntity(Object entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = getSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public static void closeSession(Session session) {
        // Fermer la session Hibernate
        session.close();
    }

    private static void closeSessionFactory() {
        // Fermer la fabrique de sessions
        sessionFactory.close();
    }
}