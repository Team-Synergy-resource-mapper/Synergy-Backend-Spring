package com.pm.synergybackend.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class AdvertisementDto {

        private String id;

        @JsonProperty("main_category")
        private String mainCategory;

        @JsonProperty("sub_category")
        private String subCategory;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("transaction_type")
        private String transactionType;

        @JsonProperty("wanted_offering")
        private String wantedOffering;

        private String url;

        @JsonProperty("image_urls")
        private List<String> imageUrls;

        private String description;


}
