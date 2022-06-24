package uit.itszoo.izrandom.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;

@Dao
public interface SliceDAO {
    @Insert
    public void insertSlice(LuckyWheelSlice... slices);

    @Update
    public void updateSlice(LuckyWheelSlice slice);

    @Query("DELETE FROM luckyWheelSlice WHERE id in (:ids)")
    public void deleteSlicesByIDs(List<String> ids);

    @Query("SELECT * FROM luckyWheelSlice WHERE wheelID =:id")
    public List<LuckyWheelSlice> getSliceByWheelID(String id);

    @Query("SELECT * FROM luckyWheelSlice")
    public LiveData<List<LuckyWheelSlice>> getAllSlices();

    @Query("SELECT * FROM luckyWheelSlice WHERE id =:id")
    public LuckyWheelSlice getSliceByID(String id);
}
