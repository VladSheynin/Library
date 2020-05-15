package library.model;

import library.model.common.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String name;
    private String author;
    private Genre genre;

    private String series;
    private int numberInSeries;
    private int yearOfCreate;

    private Place place;
}