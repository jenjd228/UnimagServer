package com.example.demo.Repository;

import com.example.demo.Model.EmailKod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailKodRepository extends CrudRepository<EmailKod,Long> {
    EmailKod findByKod(String kod);
    EmailKod findByEmail(String email);
}
