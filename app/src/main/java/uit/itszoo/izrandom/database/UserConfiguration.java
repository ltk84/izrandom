package uit.itszoo.izrandom.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity(tableName = "userConfig")
@TypeConverters(Converters.class)
public class UserConfiguration {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "arrow")
    public int arrow;


    @ColumnInfo(name = "dice")
    public List<Integer> dice;

}
