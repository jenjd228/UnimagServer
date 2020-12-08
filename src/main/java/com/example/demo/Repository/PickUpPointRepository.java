package com.example.demo.Repository;

import com.example.demo.Model.PickUpPoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickUpPointRepository extends CrudRepository<PickUpPoint,Long> {
}
