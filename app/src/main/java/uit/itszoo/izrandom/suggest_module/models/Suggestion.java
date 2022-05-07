package uit.itszoo.izrandom.suggest_module.models;

public class Suggestion {
    String title;
    int image;
    String category;
    public Suggestion(String title, int image, String category)
    {
        this.title = title;
        this.category = category;
        this.image = image;
    }
}
