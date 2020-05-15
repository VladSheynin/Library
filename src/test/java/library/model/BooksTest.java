package library.model;

import library.exception.DBException;
import library.model.common.Genre;
import library.model.common.RoomType;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooksTest {
//    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/test_library";
//    static final String USER = "postgres";
//    static final String PASS = "";
//    static Connection connection = null;
//
//    static final String DATABASE_CONNECTION_ERROR = "Не могу подключиться к базе данных";
//
//    static List<Book> books1 = new ArrayList<>(); // рабочий список книг в памяти - инициализируется к конструкторе
//    static List<Book> books2 = new ArrayList<>(); // второй рабочий список. Нужен что-бы можно было переключаться на время загрузки из базы в память
//    static List<Book> alternateBookList = books2; //ссылка на альтернативный список (для загрузки данных из базы)
//    static List<Book> activeBookList = books1; // ссылка на активный список
//    static final int FIRSTLISTACTIVE = 1;
//    static final int SECONDLISTACTIVE = 2;
//    static int triggerAlternateList = SECONDLISTACTIVE; //триггер указывающий на актуальный список книг (пока один в работе другой изменяется) (book1 или book2)
//
//    static List<Place> places = new ArrayList<Place>();
//
//    //Парадигма: все книги вначале грузится в память - getAllBooks, а потом все операции поиска и фильтрации для клиента происходят уже из памяти
//    // (нет необходимости каждый раз мучать базу)
//    //исключение следующие операции:
//    // добавления книги - addBook
//    // удаления книги по ID - deleteBookByID
//    // удаления всех книг - deleteAllBooks
//    // и посика по ID в базе - getIDByNameAndAuthor
//
//    public static void main(String[] args) throws SQLException {
//        try {
//            // !!! всегда первая операция - не забыть добавить в конструктор для BooksFactory
//            getAllBooks();
//            //System.out.println("Альтернативный список = "+triggerAlternateList);
//            //for (main.java.library.model.Book book :activeBookList) {
//            //    System.out.println(book);
//            //}
//
//            deleteAllPlaces();
//            createPlaces();
//
//            getAllPlaces();
//            for (Place place : places) {
//                System.out.println(place);
//            }
//
//            /*Integer id = getIdByPlace(new library.model.Place(main.java.library.model.common.Rooms.LIBRARY, 5, 4,""));
//            if (id != null) System.out.println("ID места " + id);
//            else
//                System.out.println("Место не найдено");
//
//            deletePlaceByID(id);
//            for (library.model.Place place : places) {
//                System.out.println(place);
//            }
//            */
//
//            /*
//            List<main.java.library.model.Book> booksByName = getBooksByName("Сборник сочинений пушкина");
//            if (booksByName != null) System.out.println(booksByName.toString());
//            else
//                System.out.println("Такой книги нет. ");
//
//            List<main.java.library.model.Book> booksByGenre = getBooksByGenre(main.java.library.model.common.Genre.POEMS);
//            if (booksByGenre != null) System.out.println(booksByGenre.toString());
//            else
//                System.out.println("Книг такого жанра нет");
//
//            List<main.java.library.model.Book> booksByAuthor = getBooksByAuthor("Эльтеррус");
//            if (booksByAuthor != null) System.out.println(booksByAuthor.toString());
//            else
//                System.out.println("Книг такого автра нет");
//
//            List<main.java.library.model.Book> booksBySeries = getBooksBySeries("Отзвуки серебрянного ветра");
//            if (booksBySeries != null) System.out.println(booksBySeries.toString());
//            else
//                System.out.println("Такой серии нет");
//
//            Map<String, String> seriesAndAuthor = getAllSeries();
//            if (seriesAndAuthor != null) System.out.println(seriesAndAuthor.toString());
//            else
//                System.out.println("В нашей библиотеке ни одной серии нет");
//
//            Integer id = getIDByNameAndAuthor("Мы были. Путь  2", "Эльтеррус ");
//            if (id != null) System.out.println("ID книги " + id);
//            else
//                System.out.println("В нашей библиотеке такой книги нет"); */
//            //deleteBookByID(id);
//
//            //deleteAllAndCreateDatabase();
//        } catch (DBException e) {
//            System.out.println(DATABASE_CONNECTION_ERROR);
//        }
//    }
//
//    //меняет ссылки на активный и альтернативный списки местами
//    public static void changeAlternateList() {
//        if (triggerAlternateList == FIRSTLISTACTIVE) {
//            triggerAlternateList = SECONDLISTACTIVE;
//            alternateBookList = books2;
//            activeBookList = books1;
//        } else {
//            triggerAlternateList = FIRSTLISTACTIVE;
//            alternateBookList = books1;
//            activeBookList = books2;
//        }
//    }
//
//    //методы реализующие работу из базы
//    //после каждого изменения в базе обязательно обновить список книг в паняти
//    public static boolean connectToPostgres() {
//        try {
//            connection = DriverManager.getConnection(DB_URL, USER, PASS);
//            return true;
//        } catch (SQLException e) {
//            //System.out.println(DATABASE_CONNECTION_ERROR + "in connection method");
//            //e.printStackTrace();
//            return false;
//        }
//    }
//
//    public static void getAllBooks() throws DBException, SQLException {
//        Place place;
//        if (!connectToPostgres()) throw new DBException();
//
//        String sqlRequestText = "SELECT * FROM books ;";
//        Statement stmt = connection.createStatement();
//        ResultSet resultSet = stmt.executeQuery(sqlRequestText);
//        while (resultSet.next()) {
//            if ("".equals(resultSet.getString(8))) place = new Place(RoomType.UNKNOWN);
//            else {
//                //реализовать получение нужного местоположения из базы!!!!!!
//                place = new Place(RoomType.UNKNOWN);
//            }
//            alternateBookList.add(new Book(resultSet.getString(2),
//                    resultSet.getString(3),
//                    Genre.valueOf(resultSet.getString(4)),
//                    resultSet.getString(5),
//                    resultSet.getInt(6),
//                    resultSet.getInt(7),
//                    place));
//        }
//        resultSet.close();
//        stmt.close();
//        connection.close();
//
//        changeAlternateList();
//    }
//
//    public static void addBook(Book book) throws SQLException, DBException {
//        if (!connectToPostgres()) throw new DBException();
//
//        String sqlRequestText = "INSERT INTO books (name, author, genre, series, \"numberInSeries\", \"yearOfCreate\") VALUES ("
//                + "'" + book.getName() + "','" + book.getAuthor() + "','"
//                + book.getGenre().name() + "','" + book.getSeries() + "',"
//                + book.getNumberInSeries() + "," + book.getYearOfCreate() + ");";
//        Statement stmt = connection.createStatement();
//        stmt.execute(sqlRequestText);
//        stmt.close();
//        connection.close();
//
//        //после добавления книги в базу - добавлем ее в active спиосок
//        activeBookList.add(book);
//    }
//
//    //возвращает 0 если такой книги нет
//    public static Integer getIDByNameAndAuthor(String name, String author) throws SQLException, DBException {
//        if (!connectToPostgres()) throw new DBException();
//
//        Integer id = -1;
//        String sqlRequestText = "SELECT id FROM books WHERE name ='" + name.trim() + "' AND author='" + author.trim() + "';";
//        Statement stmt = connection.createStatement();
//        ResultSet resultSet = stmt.executeQuery(sqlRequestText);
//        while (resultSet.next()) {
//            id = Integer.valueOf(resultSet.getString(1));
//            break;
//        }
//        resultSet.close();
//        stmt.close();
//        connection.close();
//        if (id == -1) return null;
//        else return id;
//    }
//
//    public static void deleteBookByID(int id) throws SQLException, DBException {
//        if (!connectToPostgres()) throw new DBException();
//
//        String sqlRequestText = "DELETE FROM books WHERE id = " + id + ";";
//        Statement stmt = connection.createStatement();
//        stmt.execute(sqlRequestText);
//        stmt.close();
//        connection.close();
//
//        //обновляем список после любых изменений вносимых в базу
//        getAllBooks();
//    }
//
//    public static void deleteAllBooks() throws SQLException, DBException {
//        if (!connectToPostgres()) throw new DBException();
//        String sqlRequestText = "DELETE FROM books *;";
//        Statement stmt = connection.createStatement();
//        stmt.execute(sqlRequestText);
//        stmt.close();
//        connection.close();
//
//        activeBookList.clear();
//        alternateBookList.clear();
//    }
//
//    // методы реализующие работу с данными в памяти (фактически операции ReadOnly): поиск и фильтрация
//    // если совпадений в поиске нет возвращает null
//
//    public static List<Book> getBooksByName(String bookName) {
//        List<Book> resultBooks = new ArrayList<>();
//        for (Book book : activeBookList) {
//            if (book.getName().toUpperCase().trim().equals(bookName.toUpperCase().trim()))
//                resultBooks.add(book);
//        }
//        if (resultBooks.size() == 0) return null;
//        else
//            return resultBooks;
//    }
//
//    public static List<Book> getBooksByGenre(Genre genre) {
//        List<Book> books = new ArrayList<>();
//        for (Book book : activeBookList) {
//            if (book.getGenre() == genre) {
//                books.add(book);
//            }
//        }
//        if (books.size() == 0) return null;
//        else return books;
//    }
//
//    public static List<Book> getBooksByAuthor(String author) {
//        List<Book> books = new ArrayList<>();
//        for (Book book : activeBookList) {
//            if (book.getAuthor().toUpperCase().trim().equals(author.toUpperCase().trim())) {
//                books.add(book);
//            }
//        }
//        if (books.size() == 0) return null;
//        else return books;
//    }
//
//    public static List<Book> getBooksBySeries(String series) {
//        List<Book> books = new ArrayList<>();
//        for (Book book : activeBookList) {
//            if (book.getSeries().toUpperCase().trim().equals(series.toUpperCase().trim())) {
//                books.add(book);
//            }
//        }
//        if (books.size() != 0)
//            books.sort((o1, o2) -> {
//                if (o1.getNumberInSeries() > o2.getNumberInSeries()) return 1;
//                else if (o1.getNumberInSeries() < o2.getNumberInSeries()) return -1;
//                else return 0;
//            });
//        if (books.size() == 0) return null;
//        else return books;
//    }
//
//    //возвращает Map содержащий серию и автора
//    public static Map<String, String> getAllSeries() {
//        Map<String, String> seriesAndAuthor = new HashMap<>();
//        for (Book book : activeBookList) {
//            if (book.getSeries().isEmpty()) continue;
//            seriesAndAuthor.put(book.getAuthor(), book.getSeries());
//        }
//        if (seriesAndAuthor.size() == 0)
//            return null;
//        else
//            return seriesAndAuthor;
//    }
//
//
//    //Технический метод - очистить и наполнить базу с нуля из списка книг в методе createBooks
//    private static void deleteAllAndCreateDatabase() throws SQLException, DBException {
//        List<Book> books = createBooks();
//        deleteAllBooks();
//
//        for (Book book : books) {
//            addBook(book);
//        }
//    }
//
//    private static List<Book> createBooks() {
//        List<Book> books = new ArrayList<>();
//
//        Place place1 = new Place(RoomType.LIBRARY, 3);
//        Place place2 = new Place(RoomType.SASHASLEEPING, 3);
//        Book book;
//
//        book = new Book("Мы были. Путь", "Эльтеррус", Genre.FANTASTIC, "Отзвуки серебрянного ветра", 2, 0, place1);
//        books.add(book);
//
//        book = new Book("Мы были. Призыв", "Эльтеррус", Genre.FANTASTIC, "Отзвуки серебрянного ветра", 1, 0, place1);
//        books.add(book);
//
//        book = new Book("Еще один великолепный миф", "Роберт Асприн", Genre.FANTASTIC, "Великолепный миф", 1, 0, place2);
//        books.add(book);
//
//        book = new Book("Сборник сочинений Пушкина", "Пушкин А.С.", Genre.POEMS, "", 0, 0, place2);
//        books.add(book);
//        return books;
//    }
//
//
//    //методы работы с library.model.Place
//    public static void getAllPlaces() throws DBException, SQLException {
//
//        if (!connectToPostgres()) throw new DBException();
//
//        //если база была уже наполнена - очищаем перед новым наполнением (считаем что на диске - эталон)
//        if (places.size() != 0) places.clear();
//
//        String sqlRequestText = "SELECT * FROM places ;";
//        Statement stmt = connection.createStatement();
//        ResultSet resultSet = stmt.executeQuery(sqlRequestText);
//        while (resultSet.next()) {
//            places.add(new Place(RoomType.valueOf(resultSet.getString(2)),
//                    resultSet.getInt(3),
//                    resultSet.getInt(4),
//                    resultSet.getString(5)));
//        }
//        stmt.execute(sqlRequestText);
//        stmt.close();
//        connection.close();
//    }
//
//    private static void addPlace(Place place) throws DBException, SQLException {
//        if (!connectToPostgres()) throw new DBException();
//        String sqlRequestText = "INSERT INTO places (room, numberbookcase, numberbookshelf, comment) VALUES ("
//                + "'" + place.getRoom().name() + "','" + place.getNumberOfBookcase() + "','"
//                + place.getNumberBookshelf() + "','" + place.getComment() + "');";
//        Statement stmt = connection.createStatement();
//        stmt.execute(sqlRequestText);
//        stmt.close();
//        connection.close();
//
//    }
//
//    public static Integer getIdByPlace(Place place) throws DBException, SQLException {
//        if (!connectToPostgres()) throw new DBException();
//
//        Integer id = -1;
//        String sqlRequestText = "SELECT id FROM places WHERE room ='" +
//                place.getRoom().name().trim() +
//                "' AND numberbookcase=" + place.getNumberOfBookcase() +
//                " AND numberbookshelf=" + place.getNumberBookshelf() +
//                ";";
//
//        Statement stmt = connection.createStatement();
//        ResultSet resultSet = stmt.executeQuery(sqlRequestText);
//        while (resultSet.next()) {
//            id = Integer.valueOf(resultSet.getString(1));
//            break;
//        }
//        resultSet.close();
//        stmt.close();
//        connection.close();
//        if (id == -1) return null;
//        else return id;
//    }
//
//    public static void deletePlaceByID(int id) throws SQLException, DBException {
//        if (!connectToPostgres()) throw new DBException();
//
//        String sqlRequestText = "DELETE FROM places WHERE id = " + id + ";";
//        Statement stmt = connection.createStatement();
//        stmt.execute(sqlRequestText);
//        stmt.close();
//        connection.close();
//
//        //обновляем список после любых изменений вносимых в базу
//        getAllPlaces();
//    }
//
//    public static void deleteAllPlaces() throws DBException, SQLException {
//        if (!connectToPostgres()) throw new DBException();
//        String sqlRequestText = "DELETE FROM places *;";
//        Statement stmt = connection.createStatement();
//        stmt.execute(sqlRequestText);
//        stmt.close();
//        connection.close();
//    }
//
//    // технический метод для первоначального создания Places
//    private static void createPlaces() throws DBException, SQLException {
//        Place[] newPlaces = new Place[10];
//        newPlaces[0] = new Place(RoomType.LIBRARY, 5, 1, "Нижняя полка крайнего правого шкафа");
//        newPlaces[1] = new Place(RoomType.LIBRARY, 5, 2, "Вторая снизу полка крайнего правого шкафа");
//        newPlaces[2] = new Place(RoomType.LIBRARY, 5, 3, "Третья снизу полка крайнего правого шкафа");
//        newPlaces[3] = new Place(RoomType.LIBRARY, 5, 4, "Четвертая снизу полка крайнего правого шкафа");
//        newPlaces[4] = new Place(RoomType.LIBRARY, 5, 5, "Пятая снизу полка крайнего правого шкафа");
//        newPlaces[5] = new Place(RoomType.LIBRARY, 5, 6, "Шестая снизу полка крайнего правого шкафа");
//        newPlaces[6] = new Place(RoomType.LIBRARY, 5, 7, "Седьмая снизу полка крайнего правого шкафа");
//        newPlaces[7] = new Place(RoomType.LIBRARY, 4, 1, "Полка над дверью");
//        newPlaces[8] = new Place(RoomType.UNKNOWN, 0, 0, "Местоположение неизвестно");
//        for (Place place : newPlaces) {
//            if (place != null) addPlace(place);
//        }
//    }


}