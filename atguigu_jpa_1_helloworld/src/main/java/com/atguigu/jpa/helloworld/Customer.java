package com.atguigu.jpa.helloworld;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @author CYH
 * @date 2017/11/16
 */
@Entity
@Table(name = "jpa_customer")
public class Customer {

    private Integer id;
    private String lastName;
    private String email;
    private int age;
    private Date createdTime;
    private Date birth;

    @Id
    @GeneratedValue
    @Column(name = "customerId")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(length = 50)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Temporal(value = TemporalType.DATE)
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Transient
    public String getInfo() {
        return "" + id + ", " + lastName + ", " + email;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + ", age=" + age
                + ", createdTime=" + createdTime + ", birth=" + birth + '}';
    }
}
