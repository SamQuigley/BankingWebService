package com.samquigley.services;

import com.samquigley.error_handling.NotFoundException;
import com.samquigley.models.Account;
import com.samquigley.models.Customer;
import com.samquigley.models.Transaction;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
public class AccountService {

    EntityManager entityManager;

    public AccountService() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        entityManager = emfactory.createEntityManager();
    }

    /*
    ------------------------------------------------------------------------------------------------
                            **********CRUD FUNCTIONALITY**********
    ------------------------------------------------------------------------------------------------
    GET ACCOUNTS & LIST OF TRANSACTIONS
    curl -v -H "Accept: application/json" -H "API_KEY:VALID_KEY" http://localhost:8080/api/accounts
    curl -v -H "Accept: application/xml" -H "API_KEY:VALID_KEY" http://localhost:8080/api/accounts
    ------------------------------------------------------------------------------------------------
    GET ACCOUNT BY ID & LIST OF TRANSACTIONS IF NOT NULL
    curl -v -H "Accept: application/json" -H "API_KEY:VALID_KEY" http://localhost:8080/api/accounts/1
    curl -v -H "Accept: application/xml" -H "API_KEY:VALID_KEY" http://localhost:8080/api/accounts/1
    ------------------------------------------------------------------------------------------------
    DELETE ACCOUNT BY ID
    curl -v -X DELETE -H "API_KEY:VALID_KEY" http://localhost:8080/api/accounts/1
    ------------------------------------------------------------------------------------------------
    UPDATE BALANCE BY ID 
    curl -v -X POST -H "API_KEY:VALID_KEY" "http://localhost:8080/api/accounts/2?balance=45.50"
    ------------------------------------------------------------------------------------------------
    CREATE NEW ACCOUNT - nothing passed as it is randomely generated
    curl -v -H "Accept: application/json" -H "API_KEY:VALID_KEY" -H "Content-type: application/json" http://localhost:8080/api/accounts -d '{}'
    ------------------------------------------------------------------------------------------------
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAccounts() {
        List<Account> list = allEntries();
        GenericEntity entity = new GenericEntity<List<Account>>(list) {
        };
        return Response.ok(entity).build();

    }

    private List<Account> allEntries() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> cq = cb.createQuery(Account.class);
        Root<Account> rootEntry = cq.from(Account.class);
        CriteriaQuery<Account> all = cq.select(rootEntry);
        TypedQuery<Account> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Account getAccount(@PathParam("id") int id) {
        Account test = entityManager.find(Account.class, id);
        if (test == null) {
            throw new NotFoundException("Account Not Found");
        }
        return test;
    }

    @DELETE
    @Path("{id}")
    public Response deleteAccount(@PathParam("id") int id) {
        Account Account = entityManager.find(Account.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(Account);
        entityManager.createNativeQuery("ALTER TABLE account AUTO_INCREMENT = 1").executeUpdate();
        entityManager.getTransaction().commit();
        Account.setAccountId(id - 1);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @POST
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Account update(@PathParam("id") int id, @QueryParam("balance") double balance) {
        Account Account = entityManager.find(Account.class, id);
        if (Account == null) {
            throw new NotFoundException("BALANCE HAS NOT BEEN UPDATED -- ERROR OCCURED");
        }
        entityManager.getTransaction().begin();
        Account.setBalance(balance);
        entityManager.getTransaction().commit();
        return Account;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response save(Account c) {
        entityManager.getTransaction().begin();
        entityManager.persist(c);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return Response.status(200).entity(c).build();
    }

}
