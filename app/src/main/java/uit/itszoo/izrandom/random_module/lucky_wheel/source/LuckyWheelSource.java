package uit.itszoo.izrandom.random_module.lucky_wheel.source;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.Arrays;

import uit.itszoo.izrandom.R;

public class LuckyWheelSource {

    public static ArrayList<String> contentItem1 = new ArrayList<>(
            Arrays.asList(
                    "Ăn", "Ngủ", "Nhậu", "Học", "Chơi", "Gofl"
            )
    );

    public static ArrayList<String> contentItem2 = new ArrayList<>(
            Arrays.asList(
                    "Pizza", "Sushi", "Burger", "Lẩu Thái", "Mì", "Hủ tiếu"
            )
    );

    public static ArrayList<String> contentItem3 = new ArrayList<>(
            Arrays.asList(
                    "Bóng đá", "Bơi", "Chạy bộ", "Nhảy xa", "Bóng chuyền", "Gofl" ,"Lướt sóng", "Game",
                    "Bóng rổ", "Nhảy dây" , "Bún thun", "Cờ vua"
            )
    );

    public static  ArrayList<ArrayList<String>> listContent = new ArrayList<>(
      Arrays.asList(
              contentItem1,
              contentItem2,
              contentItem3
      )
    );
    public static ArrayList<String> listTitle = new ArrayList<>(
      Arrays.asList(
              "Làm gì bây giờ?",
              "Ăn gì bây giờ?",
              "Chơi gì bây giờ?"
      )
    );
    public static ArrayList<Integer> repeat = new ArrayList<>(
            Arrays.asList(
                    1,
                    1,
                    1
            )
    );
    public static ArrayList<Integer> textSize = new ArrayList<>(
            Arrays.asList(
                    16,
                    16,
                    16
            )
    );
    public static ArrayList<Integer> spinTime = new ArrayList<>(
            Arrays.asList(
                    5,
                    5,
                    5
            )
    );
    public static ArrayList<Boolean> fairMode = new ArrayList<>(
            Arrays.asList(
                    false,
                    false,
                    false
            )
    );
    public static  ArrayList<String> listColor1 = new ArrayList<>(
            Arrays.asList(
                    "#0b4581",
                    "#549cb9",
                    "#02a890",
                    "#8dfd03",
                    "#fff203",
                    "#ffc30d",
                    "#f78f1c",
                    "#e4722e",
                    "#ec1a23",
                    "#e44097",
                    "#a8449a",
                    "#6a419d"
            )
    );
    public static  ArrayList<String> listColor2 = new ArrayList<>(
            Arrays.asList(
                    "#6a419d",
                    "#a8449a",
                    "#e44097",
                    "#ec1a23",
                    "#e4722e",
                    "#f78f1c",
                    "#ffc30d",
                    "#fff203",
                    "#8dfd03",
                    "#02a890",
                    "#549cb9",
                    "#0b4581"
            )
    );
    public static  ArrayList<String> listColor3 = new ArrayList<>(
            Arrays.asList(
                    "#6a419d",
                    "#a8449a",
                    "#e44097",
                    "#ec1a23",
                    "#e4722e",
                    "#f78f1c",
                    "#ffc30d",
                    "#fff203",
                    "#8dfd03",
                    "#02a890",
                    "#549cb9",
                    "#0b4581"
            )
    );
    public static  ArrayList<ArrayList<String>> listColor = new ArrayList<>(
      Arrays.asList(
              listColor1,
              listColor2,
              listColor3
      )
    );
}
