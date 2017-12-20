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

       Customer user = new Customer("Sam", 21);

        entitymanager.persist(user);

        Account acc1 = new Account();
        Account acc2 = new Account();
        Account acc3 = new Account();
        Account acc4 = new Account();
        Account acc5 = new Account();
      
        acc1.setUser(user);
        acc2.setUser(user);
        acc3.setUser(user);
        acc4.setUser(user);
        acc5.setUser(user);
        
        ArrayList<Account> list = new ArrayList<>();
        list.add(acc1);
        list.add(acc2);
        list.add(acc3);
        list.add(acc4);
        list.add(acc5);
        
        user.setAccounts(list);
        entitymanager.persist(user);
        
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
