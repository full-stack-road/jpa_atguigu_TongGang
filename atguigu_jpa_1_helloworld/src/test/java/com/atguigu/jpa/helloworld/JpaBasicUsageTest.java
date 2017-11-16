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
        //1. ���� EntityManagerFactory
        String persistenceUnitName = "jpa-1";
        Map<String, Object> properties = new HashMap<>(1);
        properties.put("hibernate.show_sql", true);

        /**
         * ����һ������ҪMapֻ��ҪpersistenceUnitName�ķ���
         */
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory(persistenceUnitName, properties);

        //2. ���� EntityManager. ������ Hibernate �� SessionFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //3. ��������
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        //4. ���г־û�����
        Customer customer = new Customer();
        customer.setAge(12);
        customer.setEmail("tom@atguigu.com");
        customer.setLastName("Tom");
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());

        entityManager.persist(customer);

        //5. �ύ����
        entityTransaction.commit();

        //6. �ر� EntityManager
        entityManager.close();

        //7. �ر� EntityManagerFactory
        entityManagerFactory.close();
    }

}