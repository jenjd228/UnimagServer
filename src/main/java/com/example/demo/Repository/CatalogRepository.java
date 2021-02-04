package com.example.demo.Repository;

import com.example.demo.Model.Catalog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CatalogRepository extends CrudRepository<Catalog, Integer> {

    @Query("select o from Catalog o ORDER BY o.price DESC,o.date")
    List<Catalog> findByPriceDESC(Pageable pageable);

    @Query("select o from Catalog o ORDER BY o.price ASC,o.date")
    List<Catalog> findByPriceASC(Pageable pageable);

    @Query("select o from Catalog o WHERE o.category = ?1 ORDER BY o.date")
    List<Catalog> findByCategory(Pageable pageable,String category);

    @Query("select o from Catalog o WHERE o.category = ?1 ORDER BY o.price ASC,o.date")
    List<Catalog> findByCategoryPriceASC(Pageable pageable,String category);

    @Query("select o from Catalog o WHERE o.category = ?1 ORDER BY o.price DESC,o.date")
    List<Catalog> findByCategoryPriceDESC(Pageable pageable,String category);

    @Query("select o from Catalog o ORDER BY o.date")
    List<Catalog> findBy(Pageable pageable);

    @Query("select o from Catalog o where o.id in :ids")
    List<Catalog> findByUserIds(@Param("ids") Iterable<Integer> ids);

    @Override
    @Transactional
    @Modifying
    void deleteById(Integer integer);

    @Transactional
    @Modifying
    void deleteByHash(String hash);

    List<Catalog> findCatalogByTitleContaining (String regex);

    Catalog findCatalogByHashContaining(String hash);
}
