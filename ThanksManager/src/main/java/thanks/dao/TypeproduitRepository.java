package thanks.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import thanks.entities.Produit;
import thanks.entities.Typeproduit;

public interface TypeproduitRepository extends JpaRepository<Typeproduit, Long> {

	@Query("select name from Typeproduit tp where tp.id = :x")
	public String getNameById(@Param(value="x")long key);
	
	@Query("select tp from Typeproduit tp where tp.name like:x")
	public Page<Typeproduit> searchTypeproduit(@Param(value="x")String keyword,Pageable pages);
}
