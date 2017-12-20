package com.samquigley.runnables;

import com.samquigley.models.Transaction;
import com.samquigley.models.Customer;
import com.samquigley.models.Account;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TransactionGenerator {

    public static void main(String[] args) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

        Customer user1 = new Customer("John", 100);
        Customer user2 = new Customer("Sammy", 54);
        Customer user3 = new Customer("Ronan", 52);
        Customer user4 = new Customer("Eathna", 52);
        Account acc = new Account();
       

        entitymanager.persist(user1);
        entitymanager.persist(user2);
        entitymanager.persist(user3);
        entitymanager.persist(user4);

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

        trans1.setUser(user1);
        trans1.setAcc(acc);
        trans2.setUser(user2);
        trans2.setAcc(acc);
        trans3.setUser(user3);
        trans3.setAcc(acc);
        trans4.setUser(user4);
        trans4.setAcc(acc);

        ArrayList<Transaction> list = new ArrayList<>();
        list.add(trans1);
        list.add(trans2);
        list.add(trans3);
        list.add(trans4);

        user1.setTransactions(list);
        entitymanager.persist(user1);
        user2.setTransactions(list);
        entitymanager.persist(user2);
        user3.setTransactions(list);
        entitymanager.persist(user3);
        user4.setTransactions(list);
        entitymanager.persist(user4);
       
        
        entitymanager.getTransaction().commit();

        // retrive the user from the database
        Customer test = entitymanager.find(Customer.class, 2);

        // the retreived user has a filled arraylist of transfers which belong to this user
        for (Transaction transaction : test.getTransactions()) {
            System.out.println(transaction);
        }

        entitymanager.close();
        emfactory.close();
    }
}
