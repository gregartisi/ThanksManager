package thanks.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import thanks.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@Query("select c from Customer c where c.name like:x")
	Page<Customer> searchCustomer(@Param(value="x")String keyword,Pageable pages);

	
}
