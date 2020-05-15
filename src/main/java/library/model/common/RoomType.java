package library.model.common;

public enum RoomType {
    KATESLEEPING("Катина спальня"),
    SASHASLEEPING("Сашина спальня"),
    PARENTSSLEEPING("Родительская спальня"),
    LIBRARY("Библиотека"),
    KITCHEN("Кухня"),
    OUTOFHOME("Вне дома"),
    UNKNOWN("Местоположение неизвестно");

    String inRussian;

    RoomType(String inRussian) {
        this.inRussian = inRussian;
    }

    @Override
    public String toString() {
        return inRussian;
    }
}
