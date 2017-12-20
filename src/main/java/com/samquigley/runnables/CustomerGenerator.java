package com.samquigley.runnables;

import com.samquigley.models.Customer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CustomerGenerator {

    public static void main(String[] args) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Customer a = new Customer("Mary", (int) Math.floor(Math.random() * 50),"5 ormond avenue", "samq2011@live.com");
        Customer b = new Customer("Harry", (int) Math.floor(Math.random() * 50),"5 ormond avenue", "samq2011@live.com");
        Customer c = new Customer("Bob Geldof", (int) Math.floor(Math.random() * 50),"5 ormond avenue", "samq2011@live.com");
        Customer d = new Customer("Larry Mulllins", (int) Math.floor(Math.random() * 50),"5 ormond avenue", "samq2011@live.com");

        entitymanager.persist(a);
        entitymanager.persist(b);
        entitymanager.persist(c);
        entitymanager.persist(d);

        entitymanager.getTransaction().commit();

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);

        entitymanager.close();
        emfactory.close();
    }
}
