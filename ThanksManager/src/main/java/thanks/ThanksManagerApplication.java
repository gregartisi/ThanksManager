package thanks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import thanks.dao.ProduitRepository;
import thanks.entities.Produit;

@SpringBootApplication
public class ThanksManagerApplication {

	public static void main(String[] args) {
		ApplicationContext actx = SpringApplication.run(ThanksManagerApplication.class, args);
		ProduitRepository produitRepository = actx.getBean(ProduitRepository.class);
		
		
		Page<Produit> produits = produitRepository.searchProduit("%%", new PageRequest(0,5));
		produits.forEach(p->System.out.println(p.getName()+"  "+p.getPrice()));
	}
}
