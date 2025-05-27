package com.pm.synergybackend.service;

import com.pm.synergybackend.dto.AdvertisementDto;
import com.pm.synergybackend.dto.AdvertisementsCreateDto;
import com.pm.synergybackend.mapper.AdvertisementMapper;
import com.pm.synergybackend.model.Advertisements;
import com.pm.synergybackend.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdvertisementService {
    private AdvertisementRepository advertisementRepository;
    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public List<AdvertisementDto> getAdvertisements(){
        List<Advertisements> advertisements = advertisementRepository.findAll();
        var array = AdvertisementMapper.convertToDtoList(advertisements);
        return array;
    }
    public AdvertisementDto createAdvertisement(AdvertisementsCreateDto advertisementDto){
        var advertisements = AdvertisementMapper.convertToEntity(advertisementDto);
        // Save Entity to DB (this assigns values like generated ID, timestamps, etc.)
        Advertisements savedAd = advertisementRepository.save(advertisements);

        // Convert saved Entity back to DTO
        AdvertisementDto savedDto = AdvertisementMapper.convertToDto(savedAd);
        return savedDto;
    }
    public Boolean deleteAdvertisement(String adId) {
        try {
            // Check if the advertisement DTO or its ID is null.
            if (adId == null) {
                return false;
            }

            // Check if advertisement exists in the repository.
            if (advertisementRepository.existsById(adId)) {
                advertisementRepository.deleteById(adId);
                return true;
            } else {
                // Advertisement not found in the repository.
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace(); // Replace with proper logging in production.
            return false;
        }
    }

}
