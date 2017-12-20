package com.samquigley.services;

import com.samquigley.models.Transaction;
import com.samquigley.models.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/customers")
public class CustomerService {

    EntityManager entityManager;

    public CustomerService() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        entityManager = emfactory.createEntityManager();
    }
    /*
    GET ALL TRANSACTIONS BY USER
    curl -v -H "Accept: application/json" -H "API_KEY:VALID_KEY" http://localhost:8080/api/customers/1
    */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Customer getUser(@PathParam("id") int id) {
        Customer test = entityManager.find(Customer.class, id);
        System.out.println(test);
        return test;
    }
    /*
    GET CUSTOMER AND THEIR TRANSACTIONS 
    curl -v -H "Accept: application/json" -H "API_KEY:VALID_KEY" http://localhost:8080/api/customers/2/transactions
    */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}/transactions")
    public List<Transaction> getTransfers(@PathParam("id") int id) {
        Customer test = entityManager.find(Customer.class, id);
        List<Transaction> transfers = test.getTransactions();
        return transfers;
    }
    /*
    GET CUSTOMER BY ID AND THEIR TRANSACTIONS BY ID
    curl -v -H "Accept: application/json" -H "API_KEY:VALID_KEY" http://localhost:8080/api/customers/2/transactions/3
    */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}/transactions/{tid}")
    public Transaction getTransfer(@PathParam("id") int id, @PathParam("tid") int tid) {
        Customer test = entityManager.find(Customer.class, id);
        List<Transaction> transactions = test.getTransactions();
        Transaction target = transactions.get(id-1);
        return target;
    }

}
