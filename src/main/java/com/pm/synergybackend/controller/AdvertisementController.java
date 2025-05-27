package com.pm.synergybackend.controller;

import com.pm.synergybackend.dto.AdvertisementDto;
import com.pm.synergybackend.dto.AdvertisementsCreateDto;
import com.pm.synergybackend.model.Advertisements;
import com.pm.synergybackend.service.AdvertisementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advertisement")
@Tag(name = "Advertisements", description = "API end points for advertisements")
public class AdvertisementController {
    private AdvertisementService advertisementService;
    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }
    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(description = "get all advertisements")
    public ResponseEntity<List<AdvertisementDto>> getAllAdvertisements(){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(advertisementService.getAdvertisements());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "create an advertisements")
    public ResponseEntity<AdvertisementDto> createAdvertisement(@RequestBody AdvertisementsCreateDto advertisementDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(advertisementService.createAdvertisement(advertisementDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Delete an advertisement by ID")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable("id") String advertisementId) {
        if (advertisementService.deleteAdvertisement(advertisementId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
