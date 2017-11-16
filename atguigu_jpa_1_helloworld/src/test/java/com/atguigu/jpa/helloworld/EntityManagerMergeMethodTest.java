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
     * �ܵ���˵: ������ hibernate Session �� saveOrUpdate ����.
     * ���������һ����ʱ�����������£�
     * 1. ����һ���µĶ���
     * 2. ����ʱ��������Ը��Ƶ��µĶ�����
     * 3. ���µĶ���ִ�г־û�����.
     * �����µĶ������� id, ����ǰ����ʱ������û�� id.
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
     * ���������һ���������, ������Ķ����� OID���������£�
     * 1. ���� EntityManager ������û�иö���
     * 2. �������ݿ���Ҳû�ж�Ӧ�ļ�¼
     * 3. JPA �ᴴ��һ���µĶ���, Ȼ��ѵ�ǰ�����������Ը��Ƶ��´����Ķ�����
     * 4. ���´����Ķ���ִ�� insert ����.
     */
    @Test
    public void testMerge2() {
        Customer customer = new Customer();
        customer.setAge(18);
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());
        customer.setEmail("dd@163.com");
        customer.setLastName("DD");
        //����ID�����ID���ᱻ���Ƶ��µĶ����С��µĶ�������²���һ���µ�ID��
        customer.setId(100);

        Customer customer2 = entityManager.merge(customer);

        System.out.println("customer: " + customer);
        System.out.println("customer2: " + customer2);
    }

    /**
     * ���������һ���������, ������Ķ����� OID. �������£�
     * 1. ���� EntityManager ������û�иö���
     * 2. �������ݿ����ж�Ӧ�ļ�¼
     * 3. JPA ���ѯ��Ӧ�ļ�¼, Ȼ�󷵻ظü�¼��һ���Ķ���, ��Ȼ���������������Ը��Ƶ���ѯ���Ķ�����.
     * 4. �Բ�ѯ���Ķ���ִ�� update ����.
     */
    @Test
    public void testMerge3() {
        Customer customer = new Customer();
        customer.setAge(18);
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());
        customer.setEmail("ee@163.com");
        customer.setLastName("EE");

        customer.setId(4);

        Customer customer2 = entityManager.merge(customer);

        System.out.println("customer: " + customer);
        System.out.println("customer2: " + customer2);
    }

    /**
     * ���������һ���������, ������Ķ����� OID.
     * 1. ���� EntityManager �������ж�Ӧ�Ķ���
     * 2. JPA ��������������Ը��Ƶ���ѯ�� EntityManager ����Ķ�����.
     * 3. EntityManager �����еĶ���ִ�� UPDATE.
     */
    @Test
    public void testMerge4() {
        Customer customer = new Customer();
        customer.setAge(28);
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());
        customer.setEmail("ff@163.com");
        customer.setLastName("FF");

        customer.setId(4);
        System.out.println("customer: " + customer);

        Customer customer2 = entityManager.find(Customer.class, 4);
        //notice: birth=2017-11-16
        System.out.println("customer2: " + customer2);

        Customer customer3 = entityManager.merge(customer);
        //notice: birth=Thu Nov 16 10:50:36 CST 2017 Ӧ�����ڴ�����ƣ������������ӵ�
        System.out.println("customer3: " + customer3);
    }
}
