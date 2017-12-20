package com.samquigley.models;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "CUSTOMER")
@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Transaction.class})

public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private int id;
    @Column(name = "customer_name")
    private String name;
    @Column(name = "customer_address")
    String address;
    @Column(name = "customer_email")
    String email;
    @Column(name = "customer_age")
    private int age;
    @Column(name = "customer_credentials")
    private int pin;

    public Customer(String name, int age, String address, String email) {
        trans = new ArrayList<>();
        this.name = name;
        this.age = age;
        this.address = address;
        this.email = email;
        this.pin=pin;
    }

    public Customer() {
        trans = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
    
    public List<Transaction> getTrans() {
        return trans;
    }

    public void setTrans(List<Transaction> trans) {
        this.trans = trans;
    }

    public List<Account> getAcc() {
        return acc;
    }

    public void setAcc(List<Account> acc) {
        this.acc = acc;
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

    @OneToMany(targetEntity = Account.class, cascade = CascadeType.ALL, mappedBy = "user")
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
        return "Customer{" + "id=" + id + ", name=" + name + ", address=" + address + ", age=" + age + ", email=" + email + ", pin=" + pin+ ", trans=" + trans + ", acc=" + acc + '}';
    }

}
