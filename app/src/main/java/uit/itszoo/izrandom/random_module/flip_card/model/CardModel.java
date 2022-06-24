package uit.itszoo.izrandom.random_module.flip_card.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity (tableName = "card")
public class CardModel implements Serializable, Cloneable {
    @PrimaryKey
    @NonNull
    private String id;
    private String cardCollectionId;
    private String cardContent;
    private long createdAtInMs;

    public CardModel(String id, String cardCollectionId, String cardContent, long createdAtInMs) {
        this.id = id;
        this.cardCollectionId = cardCollectionId;
        this.cardContent = cardContent;
        this.createdAtInMs = createdAtInMs;
    }

    public String getId() { return this.id; }
    public String getCardCollectionId() { return this.cardCollectionId; }
    public String getCardContent() { return this.cardContent; }
    public void setCardContent(String cardContent) { this.cardContent = cardContent; }
    public long getCreatedAtInMs() { return this.createdAtInMs; }
    public void setCreatedAtInMs(long createdAtInMs) { this.createdAtInMs = createdAtInMs; }

    public CardModel clone() throws CloneNotSupportedException {
        return (CardModel) super.clone();
    }

}
