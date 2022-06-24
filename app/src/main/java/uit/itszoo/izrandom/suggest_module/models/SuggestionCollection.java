package uit.itszoo.izrandom.suggest_module.models;

import java.util.ArrayList;
import java.util.List;

public class SuggestionCollection {
    public String title;
    public int icon;
    public List<String> categories;
    public List<Suggestion> suggestions;
    public SuggestionCollection(String title, int icon, List<String> categories, List<Suggestion> suggestions)
    {
        this.categories = new ArrayList<>();
        this.suggestions = new ArrayList<>();
        this.title = title;
        this.icon = icon;
        this.categories = categories;
        this.suggestions = suggestions;
    }
}
