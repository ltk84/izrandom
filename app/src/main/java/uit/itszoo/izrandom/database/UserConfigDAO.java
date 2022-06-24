package uit.itszoo.izrandom.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserConfigDAO {
    @Insert
    public void insertUserConfig(UserConfiguration... userConfigurations);

    @Query("UPDATE userConfig SET arrowId=:arrowId")
    public void updateArrow(String arrowId);

    @Query("UPDATE userConfig SET coinId=:coinId")
    public void updateCoin(String coinId);

    @Query("UPDATE userConfig SET diceId=:diceId")
    public void updateDice(String diceId);

    @Query("UPDATE userConfig SET chooserThemeId=:themeId")
    public void updateChooserTheme(String themeId);

    @Query("UPDATE userConfig SET wheelID=:wheelID")
    public void updateWheel(String wheelID);

    @Query("SELECT * FROM userConfig LIMIT 1")
    public LiveData<UserConfiguration> getUserConfig();

    @Query("DELETE FROM userConfig")
    public void deleteAll();
}
