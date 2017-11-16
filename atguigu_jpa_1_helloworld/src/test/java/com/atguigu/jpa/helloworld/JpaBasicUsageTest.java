package com.atguigu.jpa.helloworld;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author CYH
 */
public class JpaBasicUsageTest {

    public static void main(String[] args) {
        //1. 创建 EntityManagerFactory
        String persistenceUnitName = "jpa-1";
        Map<String, Object> properties = new HashMap<>(1);
        properties.put("hibernate.show_sql", true);

        /**
         * 还有一个不需要Map只需要persistenceUnitName的方法
         */
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory(persistenceUnitName, properties);

        //2. 创建 EntityManager. 类似于 Hibernate 的 SessionFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //3. 开启事务
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        //4. 进行持久化操作
        Customer customer = new Customer();
        customer.setAge(12);
        customer.setEmail("tom@atguigu.com");
        customer.setLastName("Tom");
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());

        entityManager.persist(customer);

        //5. 提交事务
        entityTransaction.commit();

        //6. 关闭 EntityManager
        entityManager.close();

        //7. 关闭 EntityManagerFactory
        entityManagerFactory.close();
    }

}
