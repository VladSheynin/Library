package library.model;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class LibraryTest {

    @Test
    public void showAllBooks() {
        Library library = Mockito.mock(Library.class);
        Assert.assertFalse(library.showAllAuthor().isEmpty());
    }
}