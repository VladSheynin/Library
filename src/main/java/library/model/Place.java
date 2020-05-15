package library.model;

import library.model.common.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    private RoomType roomType;
    private int numberOfBookcase;//номер шкафа
    private int numberBookshelf;//номер полки
    private String comment;
}