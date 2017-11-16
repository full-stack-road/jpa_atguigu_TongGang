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
     * ������ hibernate �� Session �� get ����.
     * ֱ����Sql��ѯ���ݿ⣬�õ������û�д���
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
     * ������ hibernate �� Session �� load ����
     * ����ֱ�Ӳ�ѯ���ݿ⣬����ʹ��һ��������󡣵��������ʶ���ʱ��ȥ���ݿ��ѯ
     * ���˴������������в������״̬����ô���׳��쳣
     */
    @Test
    public void testGetReference() {
        Customer customer = entityManager.getReference(Customer.class, 1);
        System.out.println("-------------------------------------");
        System.out.println(customer.getClass().getName());
        System.out.println("-------------------------------------");
        //�ر���entityManager��ʹ�ö��󣬻ᱨ�쳣������Ϊʹ���˴����ӳټ���
        //entityTransaction.commit();
        //entityManager.close();
        System.out.println(customer);
    }

    /**
     * ������ hibernate �� save ����. ʹ��������ʱ״̬��Ϊ�־û�״̬.
     * �� hibernate �� save �����Ĳ�֮ͬ��: �������� id, ����ִ�� insert ����, �����׳��쳣.
     */
    @Test
    public void testPersistence() {
        Customer customer = new Customer();
        customer.setAge(15);
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());
        customer.setEmail("bb@163.com");
        customer.setLastName("BB");
        //ȡ����������ע�ͣ��ͻᱨ��
        //customer.setId(100);

        entityManager.persist(customer);
        System.out.println("-------------------------------------");
        System.out.println(customer);
    }

    /**
     * ������ hibernate �� Session �� delete ����. �Ѷ����Ӧ�ļ�¼�����ݿ����Ƴ�
     * ��ע��: �÷���ֻ���Ƴ� �־û� ����. �� hibernate �� delete ����ʵ���ϻ������Ƴ� �������.
     */
    @Test
    public void testRemove(){
        //������������󣬲��ܱ�ɾ�����ᱨ��
		//Customer customer = new Customer();
		//customer.setId(3);

        Customer customer = entityManager.find(Customer.class, 2);

        entityManager.remove(customer);
        System.out.println("-------------------------------------");
        System.out.println(customer);
    }


}
