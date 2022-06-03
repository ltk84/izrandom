package uit.itszoo.izrandom.play_module.truth_dare.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "truthDareCard")
public class TruthDareCard {
    @PrimaryKey
    @NonNull
    private String id;
    private String content;
    private int count;

    public TruthDareCard(String id, String content, int count) {
        this.id = id;
        this.content = content;
        this.count = count;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
