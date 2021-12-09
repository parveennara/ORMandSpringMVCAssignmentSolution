package com.greatlearning.crm.serviceimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.greatlearning.crm.entity.Customer;
import com.greatlearning.crm.service.CustomerService;

@Repository
public class CustomerServiceImpl implements CustomerService {

	private SessionFactory sessionFactory;

	private Session session;

	@Autowired
	CustomerServiceImpl(SessionFactory sessionFactory) {

		this.sessionFactory = sessionFactory;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}
	}

	@Override
	@Transactional
	public List<Customer> findAll() {

		Transaction tx = session.beginTransaction();

		List<Customer> customers = session.createQuery("from Customer").list();

		tx.commit();

		return customers;
	}

	@Override
	@Transactional
	public Customer findById(int id) {

		Customer customer = new Customer();

		Transaction tx = session.beginTransaction();

		customer = session.get(Customer.class, id);

		tx.commit();

		return customer;
	}

	@Override
	@Transactional
	public void save(Customer customer) {

		Transaction tx = session.beginTransaction();

		session.saveOrUpdate(customer);

		tx.commit();
	}

	@Override
	@Transactional
	public void deleteById(int id) {

		Transaction tx = session.beginTransaction();

		Customer customer = session.get(Customer.class, id);

		session.delete(customer);

		tx.commit();

	}

}
