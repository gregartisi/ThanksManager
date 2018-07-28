package thanks.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import thanks.dao.CustomerRepository;
import thanks.entities.Customer;
import thanks.entities.Produit;
import thanks.entities.Typeproduit;

@Controller
@RequestMapping("customers")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;
	
	@RequestMapping("index")
	public String index(Model  model , @RequestParam(name="page",defaultValue="0") int page, @RequestParam(name="keyword",defaultValue="")String keyword) {
		
		//method de repository pour retourner une liste de clients organisée en page
		Page<Customer> etds =  customerRepository.searchCustomer("%"+keyword+"%", new PageRequest(page, 5));
		
		int pageCount = etds.getTotalPages();//nombre total de pages
		model.addAttribute("customers", etds);//
		
		int[] pages= new int[pageCount];
		for(int i=0;i<pageCount;i++) {
			pages[i]=i;
		}
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", page);
		model.addAttribute("keyword",keyword);
		
		
		return "customers";}
	
	@RequestMapping(value="formCustomer")
	public String formCustomer(Model model) {
		
		model.addAttribute("customer", new Customer());
		
		return "formCustomer";
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String saveCustomer(Model model,@Valid Customer c,BindingResult bindingResult) throws IllegalStateException, IOException  {
		
		if(bindingResult.hasErrors()) {
			
			return "formCustomer";
		}
		customerRepository.save(c);
		
		return "redirect:index";
	}
	
	@RequestMapping(value="edit")
	public String editCustomer(Long id,Model model) {
		
		
		Customer customer = customerRepository.getOne(id);
		
		
		model.addAttribute("customer",customer);
		
		return "editCustomer";
		
	}
	
	@RequestMapping(value="updateCustomer", method=RequestMethod.POST)
	public String updateCustomer(Model model,@Valid Customer tp,BindingResult bindingResult) throws IllegalStateException, IOException {
		
		
		if(bindingResult.hasErrors()) {
			
			return "redirect:edit";
		}
		
		customerRepository.save(tp);
		
		return "redirect:index";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String supprimerCustomer(Long id) {
		
		customerRepository.deleteById(id);
		return "redirect:index";
		
	}
}
