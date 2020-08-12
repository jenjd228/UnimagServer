package com.example.demo.Repository;

import com.example.demo.Model.Catalog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CatalogRepository extends CrudRepository<Catalog, Integer> {
    List<Catalog> findBy(Pageable pageable);
    Optional<Catalog> findById(Integer id);
    @Query("select o from Catalog o where id in :ids")
    List<Catalog> findByUserIds(@Param("ids") Iterable<Integer> ids);
}
