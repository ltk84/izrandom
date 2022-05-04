package uit.itszoo.izrandom.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;

@Dao
public interface WheelDAO {
    @Insert
    public void insertWheel(LuckyWheelData... wheel);

    @Update
    public void updateWheel(LuckyWheelData wheel);

    @Delete
    public void deleteWheel(LuckyWheelData... wheel);

    @Query("SELECT * FROM luckyWheel WHERE id IN (:ids)")
    public LiveData<List<LuckyWheelData>> getWheelByIDs(List<String> ids);

    @Query("SELECT * FROM luckyWheel")
    public LiveData<List<LuckyWheelData>> getAllWheels();
}
