package com.samquigley.services;

import com.samquigley.models.Account;
import com.samquigley.models.Transaction;
import com.samquigley.models.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers")
public class CustomerService {

    EntityManager entityManager;

    public CustomerService() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        entityManager = emfactory.createEntityManager();
    }

    /*
    ------------------------------------------------------------------------------------------------
                            **********CRUD FUNCTIONALITY**********
    ------------------------------------------------------------------------------------------------
    GET ALL CUSTOMERS & THEIR TRANSACTIONS
    curl -v -H "Accept: application/json" -H "API_KEY:VALID_KEY" http://localhost:8080/api/customers
    curl -v -H "Accept: application/xml" -H "API_KEY:VALID_KEY" http://localhost:8080/api/customers
    ------------------------------------------------------------------------------------------------
    GET AN INDIVIDUAL USER + TRNX IF NOT NULL
    curl -v -H "Accept: application/json" -H "API_KEY:VALID_KEY" http://localhost:8080/api/customers/1
    curl -v -H "Accept: application/xml" -H "API_KEY:VALID_KEY" http://localhost:8080/api/customers/1
    ------------------------------------------------------------------------------------------------
    GET CUSTOMER AND THEIR TRANSACTIONS  --> does not work
    curl -v -H "Accept: application/json" -H "API_KEY:VALID_KEY" http://localhost:8080/api/customers/9/transactions
    ------------------------------------------------------------------------------------------------
    GET CUSTOMER BY ID AND THEIR TRANSACTIONS BY ID - does not work - 500 internal server error
    curl -v -H "Accept: application/json" -H "API_KEY:VALID_KEY" http://localhost:8080/api/customers/2/transactions/3
    ------------------------------------------------------------------------------------------------
    CREATE NEW CUSTOMER
    curl -v -H "Accept: application/json" -H "API_KEY:VALID_KEY" -H "Content-type: application/json" http://localhost:8080/api/customers -d '{"name":"john o connell", "address":"malahide road", "age":35, "email":"john@gmail.com", "pin":4852}'
    curl -v -H "Accept: application/xml" -H "API_KEY:VALID_KEY" -H "Content-type: application/json" http://localhost:8080/api/customers -d '{"name":"john o connell", "address":"malahide road", "age":35, "email":"john@gmail.com", "pin":4852}'
    ------------------------------------------------------------------------------------------------
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getCustomers() {
        List<Customer> list = allEntries();
        GenericEntity entity = new GenericEntity<List<Customer>>(list) {
        };
        return Response.ok(entity).build();
    }

    private List<Customer> allEntries() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> rootEntry = cq.from(Customer.class);
        CriteriaQuery<Customer> all = cq.select(rootEntry);
        TypedQuery<Customer> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Customer getUser(@PathParam("id") int id) {
        Customer test = entityManager.find(Customer.class, id);
        System.out.println(test);
        return test;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}/transactions")
    public List<Transaction> getTransfers(@PathParam("id") int id) {
        Customer test = entityManager.find(Customer.class, id);
        List<Transaction> transfers = test.getTransactions();
        return transfers;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}/transactions/{tid}")
    public Transaction getTransfer(@PathParam("id") int id, @PathParam("tid") int tid) {
        Customer test = entityManager.find(Customer.class, id);
        List<Transaction> transactions = test.getTransactions();
        Transaction target = transactions.get(id);
        return target;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response save(Customer c) {
        entityManager.getTransaction().begin();
        entityManager.persist(c);
//        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return Response.status(200).entity(c).build();
    }

}
