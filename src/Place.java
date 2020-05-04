public class Place {
    int numberOfBookcase;//номер шкафа
    int numberBookshelf;//номер полки
    Rooms room;

    public Place(int numberOfBookcase, Rooms room) {
        this.numberOfBookcase = numberOfBookcase;
        this.room = room;
    }

    public Place(Rooms room) {
        this.room = room;
    }

    public int getNumberBookshelf() {
        return numberBookshelf;
    }

    public void setNumberBookshelf(int numberBookshelf) {
        this.numberBookshelf = numberBookshelf;
    }

    @Override
    public String toString() {
        String ret;
        if (room == Rooms.UNKNOWN)
            ret = Rooms.UNKNOWN.inRussian;
        else
            ret = "Местоположение: " + room + ", номер шкафа=" + numberOfBookcase;
        if (numberBookshelf != 0)
            ret = ret + ", номер полки=" + numberBookshelf;
        return ret;
    }
}
