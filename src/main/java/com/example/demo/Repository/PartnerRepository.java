package com.example.demo.Repository;

import com.example.demo.Model.Partner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends CrudRepository<Partner,Long> {

}
