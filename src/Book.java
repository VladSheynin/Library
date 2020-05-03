import java.io.Serializable;

public class Book implements Serializable {
    private String name;
    private String author;
    private Genre genre;

    private String series;
    private int numberInSeries;
    private int yearOfCreate;

    private Place place;

    public Book(String name, String author, Genre genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Book(String name, String author, Genre genre, Place place) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public int getNumberInSeries() {
        return numberInSeries;
    }

    public void setNumberInSeries(int numberInSeries) {
        this.numberInSeries = numberInSeries;
    }

    public int getYearOfCreate() {
        return yearOfCreate;
    }

    public void setYearOfCreate(int yearOfCreate) {
        this.yearOfCreate = yearOfCreate;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public String toString() {

        String ret;
        ret = "Автор='" + author + '\'' + ", Название='" + name + '\'' + ", жанр=" + genre;

        if (!"".equals(series))
            ret = ret + ", серия='" + series + '\'' + "(" + numberInSeries + ")";
        return ret;
    }
}
