package com.example.demo.Repository;

import com.example.demo.Model.Catalog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends CrudRepository<Catalog, Long> {

    @Query("select o from Catalog o ORDER BY price DESC,date")
    List<Catalog> findByPriceDESC(Pageable pageable);

    @Query("select o from Catalog o ORDER BY price ASC,date")
    List<Catalog> findByPriceASC(Pageable pageable);

    @Query("select o from Catalog o WHERE o.category = ?1 ORDER BY date")
    List<Catalog> findByCategory(Pageable pageable,String category);

    @Query("select o from Catalog o WHERE o.category = ?1 ORDER BY price ASC,date")
    List<Catalog> findByCategoryPriceASC(Pageable pageable,String category);

    @Query("select o from Catalog o WHERE o.category = ?1 ORDER BY price DESC,date")
    List<Catalog> findByCategoryPriceDESC(Pageable pageable,String category);

    @Query("select o from Catalog o ORDER BY date")
    List<Catalog> findBy(Pageable pageable);

    Catalog findById(Integer id);

    @Query("select o from Catalog o where id in :ids")
    List<Catalog> findByUserIds(@Param("ids") Iterable<Integer> ids);
}
