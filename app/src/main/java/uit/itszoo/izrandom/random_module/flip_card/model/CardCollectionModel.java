package uit.itszoo.izrandom.random_module.flip_card.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cardCollection")
public class CardCollectionModel implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String cardName;
    private long createdAtInMs;

    public CardCollectionModel(String id, String cardName, long createdAtInMs) {
        this.id = id;
        this.cardName = cardName;
        this.createdAtInMs = createdAtInMs;
    }

    public String getId() { return this.id; }
    public String getCardName() { return this.cardName; }
    public void setCardName(String cardName) { this.cardName = cardName; }
    public long getCreatedAtInMs() { return this.createdAtInMs; }
    public void setCreatedAtInMs(long createdAtInMs) { this.createdAtInMs = createdAtInMs; }



}
