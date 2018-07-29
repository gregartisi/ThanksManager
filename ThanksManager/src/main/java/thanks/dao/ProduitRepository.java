package thanks.dao;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import thanks.entities.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
	
	public List<Produit> findByName(String name);
	public Page<Produit> findByName(String name,Pageable pages);
	
	//public List<Produit> findByTypeProduit(int key);
	public Page<Produit> findByTypeProduit(int key,Pageable pages);
	
	@Query("select p from Produit p where p.name like:x")
	public Page<Produit> searchProduit(@Param(value="x")String keyword,Pageable pages);
	
	@Query("select p from Produit p where p.typeProduit=:tp and p.name like:x")
	public Page<Produit> searchProduitByType(@Param(value="x")String keyword,@Param(value="tp")long tp, Pageable pages);
	

}
