package uit.itszoo.izrandom.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userConfig")
public class UserConfiguration {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int arrow;
}
