import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class BooksTest {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/test_library";
    static final String USER = "postgres";
    static final String PASS = "";
    static Connection connection = null;

    static final String DATABASE_CONNECTION_ERROR = "Не могу подключиться к базе данных";

    static List<Book> books1 = new ArrayList<Book>(); // рабочий список книг в памяти - инициализируется к конструкторе
    static List<Book> books2 = new ArrayList<Book>(); // второй рабочий список. Нужен что-бы можно было переключаться на время загрузки из базы в память
    static List<Book> alternateBookList = books2; //ссылка на альтернативный список (для загрузки данных из базы)
    static List<Book> activeBookList = books1; // ссылка на активные список

    static final int FIRSTLISTACTIVE = 1;
    static final int SECONDLISTACTIVE = 2;
    static int triggerAlternateList = SECONDLISTACTIVE; //триггер указывающий на актуальный список книг (пока один в работе другой изменяется) (book1 или book2)

    //Парадигма: все книги вначале грузится в память - getAllBooks, а потом все операции поиска и фильтрации для клиента происходят уже из памяти
    // (нет необходимости каждый раз мучать базу)
    //исключение следующие операции:
    // добавления книги - addBook
    // удаления книги по ID - deleteBookByID
    // удаления всех книг - deleteAllBooks
    // и посика по ID в базе - getIDByNameAndAuthor

    public static void main(String[] args) throws IOException, SQLException {

        try {
            // !!! всегда первая операция - не забыть добавить в конструктор для BooksFactory
            getAllBooks();
            //System.out.println("Альтернативный список = "+triggerAlternateList);
            //for (Book book :activeBookList) {
            //    System.out.println(book);
            //}

            List<Book> booksByName = getBooksByName("Сборник сочинений пушкина");
            if (booksByName != null) System.out.println(booksByName.toString());
            else
                System.out.println("Такой книги нет. ");

            List<Book> booksByGenre = getBooksByGenre(Genre.POEMS);
            if (booksByGenre != null) System.out.println(booksByGenre.toString());
            else
                System.out.println("Книг такого жанра нет");

            List<Book> booksByAuthor = getBooksByAuthor("Эльтеррус");
            if (booksByAuthor != null) System.out.println(booksByAuthor.toString());
            else
                System.out.println("Книг такого автра нет");

            List<Book> booksBySeries = getBooksBySeries("Отзвуки серебрянного ветра");
            if (booksBySeries != null) System.out.println(booksBySeries.toString());
            else
                System.out.println("Такой серии нет");

            Map<String,String> seriesAndAuthor = getAllSeries();
            if (seriesAndAuthor != null) System.out.println(seriesAndAuthor.toString());
            else
                System.out.println("В нашей библиотеке ни одной серии нет");

            int id = getIDByNameAndAuthor("Мы были. Путь  ", "Эльтеррус ");
            System.out.println(id);
            //deleteBookByID(id);

            //deleteAllAndCreateDatabase();
        } catch (DBException e) {
            System.out.println(DATABASE_CONNECTION_ERROR);
        }
    }

    //меняет ссылки на активный и альтернативный списки местами
    public static void changeAlternateList() {
        if (triggerAlternateList == FIRSTLISTACTIVE) {
            triggerAlternateList = SECONDLISTACTIVE;
            alternateBookList = books2;
            activeBookList = books1;
        } else {
            triggerAlternateList = FIRSTLISTACTIVE;
            alternateBookList = books1;
            activeBookList = books2;
        }
    }

    //методы реализующие работу из базы
    //после каждого изменения в базе обязательно обновить список книг в паняти
    public static boolean connectToPostgres() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            return true;
        } catch (SQLException e) {
            //System.out.println(DATABASE_CONNECTION_ERROR + "in connection method");
            //e.printStackTrace();
            return false;
        }
    }

    public static void getAllBooks() throws DBException, SQLException {

        Place place;
        if (!connectToPostgres()) throw new DBException();

        String sqlRequestText = "SELECT * FROM books ;";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sqlRequestText);
        while (resultSet.next()) {
            if ("".equals(resultSet.getString(8))) place = new Place(Rooms.UNKNOWN);
            else {
                //реализовать получение нужного местоположения из базы!!!!!!
                place = new Place(Rooms.UNKNOWN);
            }
            alternateBookList.add(new Book(resultSet.getString(2),
                    resultSet.getString(3),
                    Genre.valueOf(resultSet.getString(4)),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getInt(7),
                    place));
        }
        resultSet.close();
        stmt.close();
        connection.close();

        changeAlternateList();
    }

    public static void addBook(Book book) throws SQLException, DBException {
        if (!connectToPostgres()) throw new DBException();

        if (!connectToPostgres()) {
            System.out.println(DATABASE_CONNECTION_ERROR);
            return;
        }

        String sqlRequestText = "INSERT INTO books (name, author, genre, series, \"numberInSeries\", \"yearOfCreate\") VALUES ("
                + "'" + book.getName() + "','" + book.getAuthor() + "','"
                + book.getGenre().name() + "','" + book.getSeries() + "',"
                + book.getNumberInSeries() + "," + book.getYearOfCreate() + ");";
        Statement stmt = connection.createStatement();
        stmt.execute(sqlRequestText);
        stmt.close();
        connection.close();

        //после добавления книги в базу - добавлем ее в active спиосок
        activeBookList.add(book);
    }

    public static int getIDByNameAndAuthor(String name, String author) throws SQLException, DBException {
        if (!connectToPostgres()) throw new DBException();

        int id = 0;
        String sqlRequestText = "SELECT id FROM books WHERE name ='" + name.trim() + "' AND author='" + author.trim() + "';";
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

    public static void deleteBookByID(int id) throws SQLException, DBException {
        if (!connectToPostgres()) throw new DBException();

        String sqlRequestText = "DELETE FROM books WHERE id = " + id + ";";
        Statement stmt = connection.createStatement();
        stmt.execute(sqlRequestText);
        stmt.close();
        connection.close();

        //обновляем список после любых изменений вносимых в базу
        getAllBooks();
    }

    public static void deleteAllBooks() throws SQLException, DBException {
        if (!connectToPostgres()) throw new DBException();
        String sqlRequestText = "DELETE FROM books *;";
        Statement stmt = connection.createStatement();
        stmt.execute(sqlRequestText);
        stmt.close();
        connection.close();

        activeBookList.clear();
        alternateBookList.clear();
    }

    // методы реализующие работу с данными в памяти (фактически операции ReadOnly): поиск и фильтрация
    // если совпадений в поиске нет возвращает null

    public static List<Book> getBooksByName(String bookName) {
        List<Book> resultBooks = new ArrayList<Book>();
        for (Book book : activeBookList) {
            if (book.getName().toUpperCase().trim().equals(bookName.toUpperCase().trim()))
                resultBooks.add(book);
        }
        if (resultBooks.size() == 0) return null;
        else
            return resultBooks;
    }

    public static List<Book> getBooksByGenre(Genre genre) {
        List<Book> books = new ArrayList<Book>();
        for (Book book : activeBookList) {
            if (book.getGenre() == genre) {
                books.add(book);
            }
        }
        if (books.size() == 0) return null;
        else return books;
    }

    public static List<Book> getBooksByAuthor(String author) {
        List<Book> books = new ArrayList<Book>();
        for (Book book : activeBookList) {
            if (book.getAuthor().toUpperCase().trim().equals(author.toUpperCase().trim())) {
                books.add(book);
            }
        }
        if (books.size() == 0) return null;
        else return books;
    }

    public static List<Book> getBooksBySeries(String series) {
        List<Book> books = new ArrayList<Book>();
        for (Book book : activeBookList) {
            if (book.getSeries().toUpperCase().trim().equals(series.toUpperCase().trim())) {
                books.add(book);
            }
        }
        if (books.size() != 0)
            books.sort(new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    if (o1.getNumberInSeries() > o2.getNumberInSeries()) return 1;
                    else if (o1.getNumberInSeries() < o2.getNumberInSeries()) return -1;
                    else return 0;
                }
            });
        if (books.size() == 0) return null;
        else return books;
    }

    //возвращает Map содержащий серию и автора
    public static Map<String, String> getAllSeries() {
        Map<String, String> seriesAndAuthor = new HashMap<>();
        for (Book book : activeBookList) {
            if (book.getSeries().isEmpty()) continue;
            seriesAndAuthor.put(book.getAuthor(), book.getSeries());
        }
        if (seriesAndAuthor.size() == 0)
            return null;
        else
            return seriesAndAuthor;
    }


    //Технический метод - очистить и наполнить базу с нуля из списка книг в методе createBooks
    private static void deleteAllAndCreateDatabase() throws SQLException, DBException {
        List<Book> books = createBooks();
        deleteAllBooks();

        for (Book book : books) {
            addBook(book);
        }
    }

    private static List<Book> createBooks() {
        List<Book> books = new ArrayList<Book>();

        Place place1 = new Place(3, Rooms.LIBRARY);
        Place place2 = new Place(3, Rooms.SASHASLEEPING);
        Book book;

        book = new Book("Мы были. Путь", "Эльтеррус", Genre.FANTASTIC, "Отзвуки серебрянного ветра", 2, 0, place1);
        books.add(book);

        book = new Book("Мы были. Призыв", "Эльтеррус", Genre.FANTASTIC, "Отзвуки серебрянного ветра", 1, 0, place1);
        books.add(book);

        book = new Book("Еще один великолепный миф", "Роберт Асприн", Genre.FANTASTIC, "Великолепный миф", 1, 0, place2);
        books.add(book);

        book = new Book("Сборник сочинений Пушкина", "Пушкин А.С.", Genre.POEMS, "", 0, 0, place2);
        books.add(book);
        return books;
    }
}