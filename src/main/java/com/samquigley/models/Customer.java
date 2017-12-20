package com.samquigley.models;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@Entity
@Table
@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Transaction.class})

public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private int age;

    public Customer(String name, int age) {
        trans = new ArrayList<>();
        this.name = name;
        this.age = age;
    }

    public Customer() {
        trans = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @OneToMany(targetEntity = Transaction.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Transaction> trans;

    public void setTransactions(List<Transaction> customerlist) {
        this.trans = customerlist;
    }

    @XmlElementWrapper(name = "transactions")
    @XmlElementRef()
    public List<Transaction> getTransactions() {
        return trans;
    }
    @OneToMany(targetEntity = Account.class,cascade = CascadeType.ALL,mappedBy="user")
    private List<Account> acc;

    public void setAccounts(List<Account> customerlist) {
        this.acc = customerlist;
    }

    @XmlElementWrapper(name = "accounts")
    @XmlElementRef()
    public List<Account> getAccounts() {
        return acc;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", trans=" + trans + ", acc=" + acc + '}';
    }

    public Iterable<Account> getAccount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
