package com.example.demo.Repository;

import com.example.demo.Model.UserPartner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPartnerRepository extends CrudRepository<UserPartner,Long> {
    List<UserPartner> findAllByUserId(Integer userId);
}
