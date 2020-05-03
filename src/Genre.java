public enum Genre {
    FANTASTIC("Фантастика"),
    DETECTIVE("Детектив"),
    PROSE("Проза"),
    POEMS("Стихи"),
    ENCYCLOPAEDIA("Энциклопедия");

    String inRussian;

    Genre(String inRussian) {
        this.inRussian = inRussian;
    }

    @Override
    public String toString() {
        return inRussian;
    }



}
