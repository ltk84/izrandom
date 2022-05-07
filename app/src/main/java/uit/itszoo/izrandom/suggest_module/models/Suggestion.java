package uit.itszoo.izrandom.suggest_module.models;

public class Suggestion {
    public String title;
    public int image;
    public String category;
    public Suggestion(String title, int image, String category)
    {
        this.title = title;
        this.category = category;
        this.image = image;
    }
}
