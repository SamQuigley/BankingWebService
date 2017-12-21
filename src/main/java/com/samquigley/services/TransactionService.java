package com.samquigley.services;

import com.samquigley.models.Account;
import com.samquigley.models.Transaction;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transactions")
public class TransactionService {

    EntityManager entityManager;

    public TransactionService() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("test-connection");
        entityManager = emfactory.createEntityManager();
    }

    /*
    ------------------------------------------------------------------------------------------------
                            **********CRUD FUNCTIONALITY**********
    ------------------------------------------------------------------------------------------------
    GET ALL Transactions
    curl -v -H "Accept: application/json" -H "API_KEY:VALID_KEY" http://localhost:8080/api/transactions
    curl -v -H "Accept: application/xml" -H "API_KEY:VALID_KEY" http://localhost:8080/api/transactions
    ------------------------------------------------------------------------------------------------
    curl -v -H "Accept: application/json" -H "API_KEY:VALID_KEY" http://localhost:8080/api/transactions/1
    curl -v -H "Accept: application/xml" -H "API_KEY:VALID_KEY" http://localhost:8080/api/transactions/1
    ------------------------------------------------------------------------------------------------
    CREATE NEW Transaction
    curl -v -H "Accept: application/json" -H "API_KEY:VALID_KEY" -H "Content-type: application/json" http://localhost:8080/api/transactions -d '{"type":"credit", "amount":45, "desc":"shopping"}'
    ------------------------------------------------------------------------------------------------
    DELETE TRANSACTION BY ID
    curl -v -X DELETE -H "API_KEY:VALID_KEY" http://localhost:8080/api/transactions/1
    ------------------------------------------------------------------------------------------------
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getTrnxs() {
        List<Transaction> list = allEntries();
        GenericEntity entity = new GenericEntity<List<Transaction>>(list) {
        };
        return Response.ok(entity).build();
    }

    private List<Transaction> allEntries() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> cq = cb.createQuery(Transaction.class);
        Root<Transaction> rootEntry = cq.from(Transaction.class);
        CriteriaQuery<Transaction> all = cq.select(rootEntry);
        TypedQuery<Transaction> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Transaction getTrnx(@PathParam("id") int id) {
        Transaction test = entityManager.find(Transaction.class, id);
        System.out.println(test);
        return test;
    }

    @DELETE
    @Path("{id}")
    public String deleteTrnx(@PathParam("id") int id) {
        Transaction trnx = entityManager.find(Transaction.class, id);
        entityManager.getTransaction().begin();
        entityManager.remove(trnx);
        entityManager.createNativeQuery("ALTER TABLE account AUTO_INCREMENT = 1").executeUpdate();
        entityManager.getTransaction().commit();
        trnx.setId(id - 1);
        return "Transaction Deleted";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String createTrnx(Transaction c) {
        entityManager.getTransaction().begin();
        entityManager.persist(c);
//        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return "Transaction Created";
    }

}
