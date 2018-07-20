package thanks.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import thanks.entities.Typeproduit;

public interface TypeproduitRepository extends JpaRepository<Typeproduit, Integer> {

	@Query("select name from Typeproduit tp where tp.id = :x")
	public String getNameById(@Param(value="x")long key);
}
