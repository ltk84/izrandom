package uit.itszoo.izrandom.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userConfig")
public class UserConfiguration {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "arrow")
    public String arrow;

//    @ColumnInfo(name = "dice")
//    public String dice;

}
