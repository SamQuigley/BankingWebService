package com.samquigley.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="ACCOUNT")
@XmlRootElement
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private int accountId;
    @Column(name = "account_number")
    private int acc_no;
    @Column(name = "sort_code")
    private int sort_code;
    @Column(name = "account_balance")
    private double balance;

    public Account() {
        this.acc_no = (int) Math.floor(Math.random() * 100001);
        this.sort_code = (int) Math.floor(Math.random() * 50);
        this.balance = (double) Math.floor(Math.random() * 50000);
    }

    public Account(int acc_no, int sort_code, double balance) {
        this.acc_no = acc_no;
        this.sort_code = sort_code;
        this.balance = balance;
    }
    
    

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(int acc_no) {
        this.acc_no = acc_no;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getSort_code() {
        return sort_code;
    }

    public void setSort_code(int sort_code) {
        this.sort_code = sort_code;
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer user;

    @XmlTransient
    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Account{" + "accountId=" + accountId + ", user=" + user.getName() + '}';
    }

}
