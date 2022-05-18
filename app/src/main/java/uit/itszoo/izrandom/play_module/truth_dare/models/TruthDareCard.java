package uit.itszoo.izrandom.play_module.truth_dare.models;

public class TruthDareCard {
    private String content;
    private int count;

    public TruthDareCard(String content, int count) {
        this.content = content;
        this.count = count;
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
