package com.samquigley.runnables;

import com.samquigley.models.Transaction;
import com.samquigley.models.Customer;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TransactionExampleOne {
        public static void main(String[] args) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Customer user = new Customer("Dominic", 23);

        entitymanager.persist(user);

        Transaction trans1 = new Transaction();
        Transaction trans2 = new Transaction();
        Transaction trans3 = new Transaction();
        Transaction trans4 = new Transaction();
        
        trans1.setType("Debit");
        trans1.setAmount(200);
        
        trans2.setType("Credit");
        trans2.setAmount(100);
        
        trans3.setType("Credit");
        trans3.setAmount(20);
        
        trans4.setType("Debit");
        trans4.setAmount(1000);
        

        trans1.setUser(user);
        trans2.setUser(user);
        trans3.setUser(user);
        trans4.setUser(user);
        
        ArrayList<Transaction> list = new ArrayList<>();
        list.add(trans1);
        list.add(trans2);
        list.add(trans3);
        list.add(trans4);
        
        user.setTransactions(list);
        entitymanager.persist(user);
        
        entitymanager.getTransaction().commit();
        
        // retrive the user from the database
        Customer test = entitymanager.find(Customer.class, 1);

        // the retreived user has a filled arraylist of transfers which belong to this user
        for (Transaction transaction : test.getTransactions()) {
            System.out.println(transaction);
        }
        
        entitymanager.close();
        emfactory.close();
    }
}
