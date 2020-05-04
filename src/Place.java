public class Place {
    Rooms room;
    int numberOfBookcase;//номер шкафа
    int numberBookshelf;//номер полки
    String comment;

    public Place(Rooms room, int numberOfBookcase) {
        this.room = room;
        this.numberOfBookcase = numberOfBookcase;
    }

    public Place(Rooms room, int numberOfBookcase, int numberBookshelf, String comment) {
        this.room = room;
        this.numberOfBookcase = numberOfBookcase;
        this.numberBookshelf = numberBookshelf;
        this.comment = comment;
    }

    public Place(Rooms room) {
        this.room = room;
    }

    public Rooms getRoom() {
        return room;
    }

    public void setRoom(Rooms room) {
        this.room = room;
    }

    public int getNumberOfBookcase() {
        return numberOfBookcase;
    }

    public void setNumberOfBookcase(int numberOfBookcase) {
        this.numberOfBookcase = numberOfBookcase;
    }

    public int getNumberBookshelf() {
        return numberBookshelf;
    }

    public void setNumberBookshelf(int numberBookshelf) {
        this.numberBookshelf = numberBookshelf;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
        return ret+", комментарий - "+ comment;
    }
}
