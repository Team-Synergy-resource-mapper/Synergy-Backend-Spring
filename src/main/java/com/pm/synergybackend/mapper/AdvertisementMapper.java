package com.pm.synergybackend.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pm.synergybackend.dto.AdvertisementDto;
import com.pm.synergybackend.dto.AdvertisementsCreateDto;
import com.pm.synergybackend.model.Advertisements;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AdvertisementMapper {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

    public static List<AdvertisementDto> convertToDtoList(List<Advertisements> ads) {
        List<AdvertisementDto> dtoList = new ArrayList<>();

        for (Advertisements ad : ads) {
            try {
                JsonNode jsonNode = mapper.readTree(ad.getText());

                String url = jsonNode.has("url") ? jsonNode.get("url").asText() : null;
                String description = jsonNode.has("description") ? jsonNode.get("description").asText() : null;
                List<String> imageUrls = new ArrayList<>();
                if (jsonNode.has("image_urls") && jsonNode.get("image_urls").isArray()) {
                    for (JsonNode img : jsonNode.get("image_urls")) {
                        imageUrls.add(img.asText());
                    }
                }

                AdvertisementDto dto = AdvertisementDto.builder()
                        .id(ad.getId())
                        .mainCategory(ad.getMain_category())
                        .subCategory(ad.getSub_category())
                        .transactionType(ad.getTransaction_type())
                        .wantedOffering(ad.getWanted_offering())
                        .createdAt(ad.getCreated_at().toString()) // Optional: use formatter.format() if desired
                        .description(description)
                        .url(url)
                        .imageUrls(imageUrls)
                        .build();

                dtoList.add(dto);

            } catch (Exception e) {
                e.printStackTrace(); // Log properly in production
            }
        }

        return dtoList;
    }

    public static Advertisements convertToAdvertisement(AdvertisementDto advertisementDto){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.createObjectNode();

            // We'll create a simulated version of the original ad `text` JSON structure.
            ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).put("title", ""); // optional or inferred
            ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).put("meta_data", "");
            ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).put("price", "");
            ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).putArray("attributes"); // optional

            ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).put("description", advertisementDto.getDescription() != null ? advertisementDto.getDescription() : "");
            ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).put("url", advertisementDto.getUrl() != null ? advertisementDto.getUrl() : "");

//            // Add image URLs
//            if (advertisementDto.getImageUrls() != null && !advertisementDto.getImageUrls().isEmpty()) {
//                ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).putArray("image_urls")
//                        .addAll(advertisementDto.getImageUrls().stream()
//                                .map(objectMapper::convertValue)
//                                .toList());
//            } else {
//                ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).putArray("image_urls");
//            }

            // Dummy placeholders for other fields to maintain structure
            ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).putArray("breadcrumbs");
            ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).set("additional_data", objectMapper.createObjectNode());

            // Convert to string for Mongo document
            String textJson = objectMapper.writeValueAsString(rootNode);

            return Advertisements.builder()
                    .id(advertisementDto.getId())
                    .main_category(advertisementDto.getMainCategory())
                    .sub_category(advertisementDto.getSubCategory())
                    .transaction_type(advertisementDto.getTransactionType())
                    .wanted_offering(advertisementDto.getWantedOffering())
                    .created_at(Instant.parse(advertisementDto.getCreatedAt()))
                    .text(textJson)
                    .build();

        } catch (Exception e) {
            e.printStackTrace(); // Replace with proper logging
            return null;
        }
    }

    public static Advertisements convertToEntity(AdvertisementsCreateDto dto) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            ObjectNode jsonNode = mapper.createObjectNode();

            if (dto.getUrl() != null) {
                jsonNode.put("url", dto.getUrl());
            }

            if (dto.getDescription() != null) {
                jsonNode.put("description", dto.getDescription());
            }

            if (dto.getImageUrls() != null) {
                ArrayNode imageUrlsNode = mapper.createArrayNode();
                for (String imgUrl : dto.getImageUrls()) {
                    imageUrlsNode.add(imgUrl);
                }
                jsonNode.set("image_urls", imageUrlsNode);
            }

            String text = mapper.writeValueAsString(jsonNode);

            // Convert createdAt back to Instant
            Instant createdAt = dto.getCreatedAt() != null ? Instant.parse(dto.getCreatedAt()) : Instant.now();

            return Advertisements.builder()
                    .id(dto.getId())
                    .userId(dto.getUserId())
                    .text(text)
                    .main_category(dto.getMainCategory())
                    .sub_category(dto.getSubCategory())
                    .transaction_type(dto.getTransactionType())
                    .wanted_offering(dto.getWantedOffering())
                    .created_at(createdAt)
                    .userId(null) // Set if known
                    .ad_embedding(null) // Populate separately if needed
                    .build();

        } catch (Exception e) {
            e.printStackTrace(); // Log properly in production
            return null;
        }
    }

    public static AdvertisementDto convertToDto(Advertisements ad) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(ad.getText());

            String url = jsonNode.has("url") ? jsonNode.get("url").asText() : null;
            String description = jsonNode.has("description") ? jsonNode.get("description").asText() : null;

            List<String> imageUrls = new ArrayList<>();
            if (jsonNode.has("image_urls") && jsonNode.get("image_urls").isArray()) {
                for (JsonNode img : jsonNode.get("image_urls")) {
                    imageUrls.add(img.asText());
                }
            }

            return AdvertisementDto.builder()
                    .id(ad.getId())
                    .mainCategory(ad.getMain_category())
                    .subCategory(ad.getSub_category())
                    .transactionType(ad.getTransaction_type())
                    .wantedOffering(ad.getWanted_offering())
                    .createdAt(ad.getCreated_at() != null ? ad.getCreated_at().toString() : null)
                    .description(description)
                    .url(url)
                    .imageUrls(imageUrls)
                    .build();

        } catch (Exception e) {
            e.printStackTrace(); // You should use proper logging in production
            return null;
        }
    }


}

