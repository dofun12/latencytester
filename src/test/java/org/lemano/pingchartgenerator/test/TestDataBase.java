/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lemano.pingchartgenerator.test;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import junit.framework.TestCase;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.lemano.pingchartgenerator.model.Contact;
import org.lemano.pingchartgenerator.db.DataBaseManager;

/**
 *
 * @author Kevim
 */
public class TestDataBase extends TestCase {

    public TestDataBase(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    // TODO add test methods here. The name must begin with 'test'. For example:
    public void testDataBase() {
        DataBaseManager dataBaseManager = new DataBaseManager();
        EntityManager em = dataBaseManager.getEntityManager();
        em.getTransaction().begin();
        
        Contact contact = new Contact(1,"Teste","EMAIL");
        em.persist(contact);
        em.flush();
        
        List<Map<String,Object>> listMap;
        Session session = dataBaseManager.getSession();
        Query q = session.createSQLQuery("select * from Contact");
        q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        listMap = q.list();
        for(Map<String,Object> map:listMap){
            System.out.println(map.get("id"));
            System.out.println(map.get("name"));
            System.out.println(map.get("email"));
        }
        
        Contact temp = em.find(Contact.class,1);
        em.remove(temp);
        em.flush();
        
        em.getTransaction().commit();
        em.close();

    }
}
