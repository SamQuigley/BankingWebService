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

        Customer user1 = new Customer("John", 100,"5 ormond avenue", "samq2011@live.com");
        Customer user2 = new Customer("Sammy", 54,"5 ormond avenue", "samq2011@live.com");
        Customer user3 = new Customer("Ronan", 52,"5 ormond avenue", "samq2011@live.com");
        Customer user4 = new Customer("Eathna", 52,"5 ormond avenue", "samq2011@live.com");
        
        Account acc1 = new Account();
        Account acc2 = new Account();
        Account acc3 = new Account();
       

        entitymanager.persist(user1);
        entitymanager.persist(user2);
        entitymanager.persist(user3);
        entitymanager.persist(user4);

        Transaction trans1 = new Transaction();
        Transaction trans2 = new Transaction();
        Transaction trans3 = new Transaction();
        Transaction trans4 = new Transaction();

        trans1.setType("Debit");
        trans1.setAmount(50);
        trans1.setDesc("Nandos");

        trans2.setType("Credit");
        trans2.setAmount(30);
        trans2.setDesc("Cinema - Star Wars The Last Jedi");

        trans3.setType("Credit");
        trans3.setAmount(9.99);
        trans3.setDesc("Netflix");

        trans4.setType("Debit");
        trans4.setAmount(15.99);
        trans4.setDesc("Spotify Family");
        
        
       

        trans1.setUser(user1);
        trans1.setAcc(acc1);
        trans2.setUser(user1);
        trans2.setAcc(acc1);
        trans3.setUser(user3);
        trans3.setAcc(acc3);
        trans4.setUser(user4);
        trans4.setAcc(acc3);

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
