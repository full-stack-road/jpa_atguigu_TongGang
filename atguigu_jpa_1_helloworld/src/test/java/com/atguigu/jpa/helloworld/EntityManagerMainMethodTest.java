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
public class EntityManagerMainMethodTest {

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
     * 类似于 hibernate 中 Session 的 get 方法.
     * 直接用Sql查询数据库，得到结果，没有代理
     */
    @Test
    public void testFind() {
        Customer customer = entityManager.find(Customer.class, 1);
        System.out.println("-------------------------------------");
        System.out.println(customer.getClass().getName());
        System.out.println("-------------------------------------");
        System.out.println(customer);
    }

    /**
     * 类似于 hibernate 中 Session 的 load 方法
     * 不会直接查询数据库，而是使用一个代理对象。当真正访问对象时才去数据库查询
     * 用了代理对象，则如果有不合理的状态，那么会抛出异常
     */
    @Test
    public void testGetReference() {
        Customer customer = entityManager.getReference(Customer.class, 1);
        System.out.println("-------------------------------------");
        System.out.println(customer.getClass().getName());
        System.out.println("-------------------------------------");
        //关闭了entityManager再使用对象，会报异常，，因为使用了代理，延迟加载
        //entityTransaction.commit();
        //entityManager.close();
        System.out.println(customer);
    }

    /**
     * 类似于 hibernate 的 save 方法. 使对象由临时状态变为持久化状态.
     * 和 hibernate 的 save 方法的不同之处: 若对象有 id, 则不能执行 insert 操作, 而会抛出异常.
     */
    @Test
    public void testPersistence() {
        Customer customer = new Customer();
        customer.setAge(15);
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());
        customer.setEmail("bb@163.com");
        customer.setLastName("BB");
        //取消下面语句的注释，就会报错
        //customer.setId(100);

        entityManager.persist(customer);
        System.out.println("-------------------------------------");
        System.out.println(customer);
    }

    /**
     * 类似于 hibernate 中 Session 的 delete 方法. 把对象对应的记录从数据库中移除
     * 但注意: 该方法只能移除 持久化 对象. 而 hibernate 的 delete 方法实际上还可以移除 游离对象.
     */
    @Test
    public void testRemove(){
        //这样的游离对象，不能被删除，会报错
        //Customer customer = new Customer();
        //customer.setId(3);

        Customer customer = entityManager.find(Customer.class, 2);

        entityManager.remove(customer);
        System.out.println("-------------------------------------");
        System.out.println(customer);
    }


}
