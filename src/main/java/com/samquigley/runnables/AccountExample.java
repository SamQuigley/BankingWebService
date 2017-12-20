package com.samquigley.runnables;

import com.samquigley.models.Transaction;
import com.samquigley.models.Account;
import com.samquigley.models.Customer;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AccountExample {
        public static void main(String[] args) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

       Customer user1 = new Customer("Sam", 21);
       Customer user2 = new Customer("Henry", 24);
       Customer user3 = new Customer("Antonia", 22);
       Customer user4 = new Customer("Rocky", 75);
       Customer user5 = new Customer("James", 24);

        entitymanager.persist(user1);
        entitymanager.persist(user2);
        entitymanager.persist(user3);
        entitymanager.persist(user4);
        entitymanager.persist(user5);

        Account acc1 = new Account();
        Account acc2 = new Account();
        Account acc3 = new Account();
        Account acc4 = new Account();
        Account acc5 = new Account();
      
        acc1.setUser(user1);
        acc2.setUser(user2);
        acc3.setUser(user3);
        acc4.setUser(user4);
        acc5.setUser(user5);
        
        ArrayList<Account> list = new ArrayList<>();
        list.add(acc1);
        list.add(acc2);
        list.add(acc3);
        list.add(acc4);
        list.add(acc5);
        
        user1.setAccounts(list);
        entitymanager.persist(user1);
        user2.setAccounts(list);
        entitymanager.persist(user2);
        user3.setAccounts(list);
        entitymanager.persist(user3);
        user4.setAccounts(list);
        entitymanager.persist(user4);
        user5.setAccounts(list);
        entitymanager.persist(user5);
        
        entitymanager.getTransaction().commit();
        
        // retrive the user from the database
        Customer test = entitymanager.find(Customer.class, 1);

        // the retreived user has a filled arraylist of transfers which belong to this user
        for (Account account : test.getAccounts()) {
            System.out.println(account);
        }
        
        entitymanager.close();
        emfactory.close();
    }
}
