package com.pm.synergybackend.repository;

import com.pm.synergybackend.model.Advertisements;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends MongoRepository<Advertisements, String> {
    List<Advertisements> findByUserId(String userId);

}
