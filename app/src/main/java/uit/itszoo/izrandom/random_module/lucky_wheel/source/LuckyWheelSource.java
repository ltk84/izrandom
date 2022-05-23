package uit.itszoo.izrandom.random_module.lucky_wheel.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;

public class LuckyWheelSource {

    public static ArrayList<LuckyWheelData> luckyWheelList = new ArrayList<>(
            Arrays.asList(
                    new LuckyWheelData(UUID.randomUUID().toString(), "Ca sĩ yêu thích?", 16, 1, 3,
                            false),
                    new LuckyWheelData(UUID.randomUUID().toString(), "Hôm nay làm gì?", 16, 1, 3,
                            false),
                    new LuckyWheelData(UUID.randomUUID().toString(), "Hôm nay ăn gì?", 16, 1, 3,
                            false),
                    new LuckyWheelData(UUID.randomUUID().toString(), "Hôm nay chơi gì?", 16, 1, 3,
                            false)
            )
    );

    public static List<LuckyWheelSlice> slices = new ArrayList<>(
            Arrays.asList(
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Charlie Puth", "#0b4581", "#FFFFFF", 12, luckyWheelList.get(0).getId(), 0),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Selena Gomez", "#549cb9", "#FFFFFF", 12, luckyWheelList.get(0).getId(), 1),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Adam Levine", "#fff203", "#FFFFFF", 12, luckyWheelList.get(0).getId(), 2),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Ăn", "#0b4581", "#FFFFFF", 12, luckyWheelList.get(1).getId(), 0),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Ngủ", "#549cb9", "#FFFFFF", 12, luckyWheelList.get(1).getId(), 1),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Nhậu", "#02a890", "#FFFFFF", 12, luckyWheelList.get(1).getId(), 2),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Học", "#8dfd03", "#FFFFFF", 12, luckyWheelList.get(1).getId(), 3),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Chơi", "#fff203", "#FFFFFF", 12, luckyWheelList.get(1).getId(), 4),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Golf", "#ffc30d", "#FFFFFF", 12, luckyWheelList.get(1).getId(), 5),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Pizza", "#0b4581", "#FFFFFF", 12, luckyWheelList.get(2).getId(), 6),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Sushi", "#549cb9", "#FFFFFF", 12, luckyWheelList.get(2).getId(), 7),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Burger", "#02a890", "#FFFFFF", 12, luckyWheelList.get(2).getId(), 8),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Lẩu Thái", "#8dfd03", "#FFFFFF", 12, luckyWheelList.get(2).getId(), 9),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Mì", "#fff203", "#FFFFFF", 12, luckyWheelList.get(2).getId(), 10),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Hủ tiếu", "#ffc30d", "#FFFFFF", 12, luckyWheelList.get(2).getId(), 11),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Bóng đá", "#0b4581", "#FFFFFF", 12, luckyWheelList.get(2).getId(), 12),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Bơi", "#549cb9", "#FFFFFF", 12, luckyWheelList.get(3).getId(), 0),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Chạy bộ", "#02a890", "#FFFFFF", 12, luckyWheelList.get(3).getId(), 1),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Nhảy xa", "#8dfd03", "#FFFFFF", 12, luckyWheelList.get(3).getId(), 2),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Bóng chuyền", "#fff203", "#FFFFFF", 12, luckyWheelList.get(3).getId(), 3),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Gofl", "#ffc30d", "#FFFFFF", 12, luckyWheelList.get(3).getId(), 4),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Lướt sóng", "#6a419d", "#FFFFFF", 12, luckyWheelList.get(3).getId(), 5),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Game", "#ffc30d", "#FFFFFF", 12, luckyWheelList.get(3).getId(), 6),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Bóng rổ", "#a8449a", "#FFFFFF", 12, luckyWheelList.get(3).getId(), 7),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Nhảy dây", "#e44097", "#FFFFFF", 12, luckyWheelList.get(3).getId(), 8)
            ));


    public static ArrayList<String> listColor1 = new ArrayList<>(
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
    public static ArrayList<String> listColor2 = new ArrayList<>(
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
    public static ArrayList<String> listColor3 = new ArrayList<>(
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

}
