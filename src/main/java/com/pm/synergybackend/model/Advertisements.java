package com.pm.synergybackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("ads")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Advertisements {
    @Id
    private String id;
    private String text;
    private String userId;
    private String main_category;
    private String sub_category;
    private String transaction_type;
    private String wanted_offering;
    private Binary ad_embedding;
    private Instant created_at;
}
