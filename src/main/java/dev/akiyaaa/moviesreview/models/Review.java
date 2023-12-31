package dev.akiyaaa.moviesreview.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private String id;
    private String body;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Boolean isUpdated;
    @DocumentReference
    private User user;
    public Review(String body, LocalDateTime created, LocalDateTime updated, Boolean isUpdated) {
        this.body = body;
        this.created = created;
        this.updated = updated;
        this.isUpdated = isUpdated;
    }
}
