package net.engineeringdigest.journalApp.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
// import lombok.Data;
import lombok.EqualsAndHashCode;

// import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
// import lombok.Getter;
// import lombok.Setter;
// import java.util.Date;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Data
public class JournalEntry {

    @Id
    private ObjectId id;
    @NonNull
    private String title;

    private String content;
    private LocalDateTime date;

}
