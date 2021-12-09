package com.greatlearning.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatlearning.crm.entity.Customer;
import com.greatlearning.crm.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping("/list")
	public String listStudents(Model model) {

		List<Customer> customers = customerService.findAll();

		model.addAttribute("Customers", customers);

		return "List-customers";

	}

	@RequestMapping("/showAddForm")
	public String showAddForm(Model model) {

		Customer customer = new Customer();

		model.addAttribute("Customer", customer);

		return "Customer-form";

	}

	@RequestMapping("/updateCustomerForm")
	public String updateCustomerForm(@RequestParam("customerId") int id, Model model) {

		Customer customer = customerService.findById(id);

		model.addAttribute("Customer", customer);

		return "Customer-form";
	}

	@PostMapping("/save")
	public String saveCustomer(@RequestParam("id") int id, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("email") String email) {

		Customer customer;

		if (id != 0) {

			customer = customerService.findById(id);
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			customer.setEmail(email);

		} else
			customer = new Customer(firstName, lastName, email);

		customerService.save(customer);

		return "redirect:/customer/list";

	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("customerId") int id) {

		customerService.deleteById(id);

		return "redirect:/customer/list";

	}

}
