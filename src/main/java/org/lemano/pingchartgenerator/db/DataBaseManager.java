/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lemano.pingchartgenerator.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.Session;

/**
 *
 * @author Kevim
 */
public class DataBaseManager {

    private Session session;
    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    public void setUp(){
        entityManagerFactory = Persistence.createEntityManagerFactory("pingChart");
    }
    
    public DataBaseManager() {
        setUp();
    }

    public Session getSession() {
        if(session==null){
            session = getEntityManager().unwrap(org.hibernate.Session.class);
        }
        return session;
    }

    public EntityManager getEntityManager() {
        if(entityManager==null||!entityManager.isOpen()){
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}
