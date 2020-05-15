package library.model;

import library.model.common.Genre;
import library.model.common.RoomType;
import org.junit.Assert;
import org.junit.Test;

public class BookTest {

    @Test
    public void createBookWithDefaultConstructorTest() {
        Book book = new Book();
        Assert.assertNotNull(book);
    }

    @Test
    public void createBookWithAlArgsConstructorTest() {
        Place place = new Place(
                RoomType.LIBRARY,
                2,
                2,
                "Trololo"
        );
        Book book = new Book(
                "Best in the world",
                "Best author in the world",
                Genre.FANTASTIC,
                "Best series in the world",
                2,
                2019,
                place
                );
        Assert.assertNotNull(book);
        Assert.assertEquals(book.getPlace(), place);
    }
}