import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.sql.*;
import java.util.*;

public class BooksTest {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/test_library";
    static final String USER = "postgres";
    static final String PASS = "";
    static Connection connection = null;

    static final String DATABASE_CONNECTION_ERROR = "Не могу подключиться к базе данных";

    public static void main(String[] args) throws IOException, SQLException {

        //File file = new File("file1");
        //writeToDisk(file, books);
        //List<Book> booksRead = readFromDisk(file);
        //viewAllBooks(books);
        //readFromPostgres();
        //Book book1 = getBookInPostgresByName("Сборник сочинений пушкина");
        //System.out.println(book1);


        //int id = getIDByNameAndAuthor("Сборник сочинений Пушкина", "Пушкин А.С.");
        //System.out.println(id);
        //deleteBookByID(id);

        //deleteAllAndCreateDatabase();

    }


    public static boolean connectToPostgres() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            return true;
        } catch (SQLException e) {
            System.out.println(DATABASE_CONNECTION_ERROR);
            e.printStackTrace();
            return false;
        }
    }

    public static void addBook(Book book) throws SQLException {
        if (!connectToPostgres()) {
            System.out.println(DATABASE_CONNECTION_ERROR);return;}

        String sqlRequestText = "INSERT INTO books (name, author, genre, series, \"numberInSeries\", \"yearOfCreate\") VALUES ("
                + "'" + book.getName() + "','" + book.getAuthor() + "','"
                + book.getGenre().name() + "','" + book.getSeries() + "',"
                + book.getNumberInSeries() + "," + book.getYearOfCreate() + ");";
        Statement stmt = connection.createStatement();
        stmt.execute(sqlRequestText);
        stmt.close();
        connection.close();

    }

    public static int getIDByNameAndAuthor(String name, String author) throws SQLException {
        if (!connectToPostgres()) {
            System.out.println(DATABASE_CONNECTION_ERROR);return -1;}

        int id = 0;
        String sqlRequestText = "SELECT id FROM books WHERE name ='" + name + "' AND author='" + author + "';";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sqlRequestText);
        while (resultSet.next()) {
            id = Integer.valueOf(resultSet.getString(1));
            break;
        }
        resultSet.close();
        stmt.close();
        connection.close();
        return id;
    }

    public static Book getBookByName(String bookName) throws SQLException {
        if (!connectToPostgres()) {
            System.out.println(DATABASE_CONNECTION_ERROR);return null;}

        String sqlRequestText = "SELECT * FROM books";
        Book book = null;
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sqlRequestText);
        while (resultSet.next()) {
            if (resultSet.getString(2).toUpperCase().trim().equals(bookName.toUpperCase().trim())) {
                book = new Book(resultSet.getString(2), resultSet.getString(3), Genre.valueOf(resultSet.getString(4)));
            }
        }
        resultSet.close();
        stmt.close();
        connection.close();
        return book;
    }

    public static void deleteBookByID(int id) throws SQLException {
        if (!connectToPostgres()) {
            System.out.println(DATABASE_CONNECTION_ERROR);return;}

        String sqlRequestText = "DELETE FROM books WHERE id = " + id + ";";
        Statement stmt = connection.createStatement();
        stmt.execute(sqlRequestText);
        stmt.close();
        connection.close();

    }

    public static void deleteAllBooks() throws SQLException {
        if (!connectToPostgres()) {
            System.out.println(DATABASE_CONNECTION_ERROR);return;}
        
        String sqlRequestText = "DELETE FROM books *;";
        Statement stmt = connection.createStatement();
        stmt.execute(sqlRequestText);
        stmt.close();
        connection.close();

    }

    //очистить и наполнить базу с нуля
    public static void deleteAllAndCreateDatabase() throws SQLException {
        List<Book> books = createBooks();
        deleteAllBooks();

        for (Book book : books) {
            addBook(book);
        }
    }


    //не для работы с базой, локальный лист объектов

    public static void viewAllBooks(List<Book> books) {
        for (Book book : books) {
            printString(book);
        }
    }

    public static void getBooksByGenre(List<Book> books, Genre genre) {
        boolean isEmpty = true;
        for (Book book : books) {
            if (book.getGenre() == genre) {
                printString(book);
                isEmpty = false;
            }
        }
        if (isEmpty) System.out.println("Таких книг нет");
    }

    public static void getBooksByAuthor(List<Book> books, String author) {
        boolean isEmpty = true;
        for (Book book : books) {
            if (book.getAuthor() == author) {
                printString(book);
                isEmpty = false;
            }
        }
        if (isEmpty) System.out.println("Таких книг нет");
    }

    public static void getBooksBySeries(List<Book> books, String series) {
        boolean isEmpty = true;
        List<Book> booksFinal = new ArrayList<Book>();
        for (Book book : books) {
            if (book.getSeries() == series) {
                booksFinal.add(book);
            }
        }
        if (booksFinal.size() != 0)
            booksFinal.sort(new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    if (o1.getNumberInSeries() > o2.getNumberInSeries()) return 1;
                    else if (o1.getNumberInSeries() < o2.getNumberInSeries()) return -1;
                    else return 0;
                }
            });
        for (Book book : booksFinal) {
            printString(book);
            isEmpty = false;
        }
        if (isEmpty) System.out.println("Таких книг нет");
    }

    public static void getAllSeries(List<Book> books) {
        Map<String, String> seriesAndAuthor = new HashMap<>();

        for (Book book : books) {
            if (book.getSeries() == null) continue;
            seriesAndAuthor.put(book.getSeries(), book.getAuthor());
        }

        if (seriesAndAuthor.size() == 0)
            System.out.println("Ни одной серии в библиотеке нет");
        else
            System.out.println(seriesAndAuthor);
    }

    public static List<Book> createBooks() {
        List<Book> books = new ArrayList<Book>();

        Place place1 = new Place(3, Rooms.LIBRARY);
        Place place2 = new Place(3, Rooms.SASHASLEEPING);
        Book book;

        book = new Book("Мы были. Путь", "Эльтеррус", Genre.FANTASTIC, place1);
        book.setSeries("Отзвуки серебрянного ветра");
        book.setNumberInSeries(2);
        books.add(book);

        book = new Book("Мы были. Призыв", "Эльтеррус", Genre.FANTASTIC, place1);
        book.setSeries("Отзвуки серебрянного ветра");
        book.setNumberInSeries(1);
        books.add(book);

        book = new Book("Еще один великолепный миф", "Роберт Асприн", Genre.FANTASTIC, place2);
        book.setSeries("Великолепный миф");
        book.setNumberInSeries(1);
        books.add(book);

        book = new Book("Сборник сочинений Пушкина", "Пушкин А.С.", Genre.POEMS, place2);
        books.add(book);
        return books;
    }

    public static void printString(Book book) {
        String fullInfo;
        if (book.getPlace() == null)
            fullInfo = book.toString();
        else
            fullInfo = book.toString() + ", " + book.getPlace();
        System.out.println(fullInfo);
    }
}
