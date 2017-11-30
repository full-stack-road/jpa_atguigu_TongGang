package com.atguigu.jpa.helloworld;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by CYH on 2017/11/16.
 */
public class EntityManagerMergeMethodTest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    @Before
    public void before() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa-1");
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
    }

    @After
    public void after() {
        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    /**
     * 总的来说: 类似于 hibernate Session 的 saveOrUpdate 方法.
     * 若传入的是一个临时对象，则步骤如下：
     * 1. 创建一个新的对象
     * 2. 把临时对象的属性复制到新的对象中
     * 3. 对新的对象执行 Insert 操作.
     * 所以新的对象中有 id, 但以前的临时对象中没有 id. 同时，整个流程中不会有 Select 语句产生
     */
    @Test
    public void testMerge1() {
        Customer customer = new Customer();
        customer.setAge(18);
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());
        customer.setEmail("cc@163.com");
        customer.setLastName("CC");

        Customer customer2 = entityManager.merge(customer);

        System.out.println("customer: " + customer);
        System.out.println("customer2: " + customer2);
    }

    /**
     * 若传入的是一个游离对象, 即传入的对象有 OID，则步骤如下：
     * 1. 若在 EntityManager 缓存中没有该对象
     * 2. 若在数据库中也没有对应的记录（会有 Select 语句产生）
     * 3. JPA 会创建一个新的对象, 然后把当前游离对象的属性复制到新创建的对象中
     * 4. 对新创建的对象执行 insert 操作.
     */
    @Test
    public void testMerge2() {
        Customer customer = new Customer();
        customer.setAge(18);
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());
        customer.setEmail("dd@163.com");
        customer.setLastName("DD");
        //设置ID，这个ID不会被复制到新的对象中。新的对象会重新产生一个新的ID。
        customer.setId(100);

        Customer customer2 = entityManager.merge(customer);

        System.out.println("customer: " + customer);
        System.out.println("customer2: " + customer2);
    }

    /**
     * 若传入的是一个游离对象, 即传入的对象有 OID. 步骤如下：
     * 1. 若在 EntityManager 缓存中没有该对象
     * 2. 若在数据库中有对应的记录（会有 Select 语句产生）
     * 3. JPA 会查询对应的记录, 然后返回该记录对一个的对象, 再然后会把游离对象的属性复制到查询到的对象中.
     * 4. 对查询到的对象执行 update 操作.
     */
    @Test
    public void testMerge3() {
        Customer customer = new Customer();
        customer.setAge(18);
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());
        customer.setEmail("ee@163.com");
        customer.setLastName("EE");

        customer.setId(1);

        Customer customer2 = entityManager.merge(customer);

        System.out.println("customer: " + customer);
        System.out.println("customer2: " + customer2);
    }

    /**
     * 若传入的是一个游离对象, 即传入的对象有 OID.
     * 1. 若在 EntityManager 缓存中有对应的对象
     * 2. JPA 会把游离对象的属性复制到查询到 EntityManager 缓存的对象中.
     * 3. EntityManager 缓存中的对象执行 Update 操作.
     */
    @Test
    public void testMerge4() {
        Customer customer = new Customer();
        customer.setAge(28);
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());
        customer.setEmail("ff@163.com");
        customer.setLastName("FF");

        customer.setId(1);
        System.out.println("customer: " + customer);

        Customer customer2 = entityManager.find(Customer.class, 1);
        //notice: birth=2017-11-16
        System.out.println("customer2: " + customer2);

        Customer customer3 = entityManager.merge(customer);
        //notice: birth=Thu Nov 16 10:50:36 CST 2017 应该是内存对象复制，所以是这样子的
        System.out.println("customer3: " + customer3);
    }
}
