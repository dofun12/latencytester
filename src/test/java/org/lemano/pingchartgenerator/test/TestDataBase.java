/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lemano.pingchartgenerator.test;

import java.util.List;
import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.lemano.pingchartgenerator.db.Contact;
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

        // Configure the session factory
        Session session = null;
        Transaction tx = null;

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            session = dataBaseManager.getSession();
            tx = session.beginTransaction();

            // Creating Contact entity that will be save to the sqlite database
            Contact myContact = new Contact(7, "teste", "asdsadads");

            // Saving to the database
            session.save(myContact);
            // Committing the change in the database.

            Contact c = (Contact) session.createQuery("from Contact c where c.id=7").uniqueResult();
            if (c != null) {
                session.delete(c);

            }

            session.flush();
            tx.commit();

            // Fetching saved data
            List<Contact> contactList = session.createQuery("from Contact").list();

            for (Contact contact : contactList) {
                System.out.println("Id: " + contact.getId() + " | Name:" + contact.getName() + " | Email:" + contact.getEmail());
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            // Rolling back the changes to make the data consistent in case of any failure 
            // in between multiple database write operations.
            tx.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
