package uit.itszoo.izrandom.random_module.lucky_wheel.source;

import java.util.ArrayList;
import java.util.Arrays;

import uit.itszoo.izrandom.random_module.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.model.LuckyWheelSlice;

public class LuckyWheelSource {

    public static ArrayList<ArrayList<LuckyWheelSlice>> slices = new ArrayList<>(
            Arrays.asList(
                    new ArrayList<>(
                            Arrays.asList(
                                    new LuckyWheelSlice("1", "Charlie Puth", "#0b4581", 12),
                                    new LuckyWheelSlice("2", "Selena Gomez", "#549cb9", 12))
                    ),
                    new ArrayList<>(
                            Arrays.asList(
                                    new LuckyWheelSlice("1", "Ăn", "#0b4581", 12),
                                    new LuckyWheelSlice("2", "Ngủ", "#549cb9", 12),
                                    new LuckyWheelSlice("3", "Nhậu", "#02a890", 12),
                                    new LuckyWheelSlice("4", "Học", "#8dfd03", 12),
                                    new LuckyWheelSlice("5", "Chơi", "#fff203", 12),
                                    new LuckyWheelSlice("6", "Golf", "#ffc30d", 12)
                            )
                    ),
                    new ArrayList<>(
                            Arrays.asList(
                                    new LuckyWheelSlice("1", "Pizza", "#0b4581", 12),
                                    new LuckyWheelSlice("2", "Sushi", "#549cb9", 12),
                                    new LuckyWheelSlice("3", "Burger", "#02a890", 12),
                                    new LuckyWheelSlice("4", "Lẩu Thái", "#8dfd03", 12),
                                    new LuckyWheelSlice("5", "Mì", "#fff203", 12),
                                    new LuckyWheelSlice("6", "Hủ tiếu", "#ffc30d", 12)
                            )
                    ),
                    new ArrayList<>(
                            Arrays.asList(
                                    new LuckyWheelSlice("1", "Bóng đá", "#0b4581", 12),
                                    new LuckyWheelSlice("2", "Bơi", "#549cb9", 12),
                                    new LuckyWheelSlice("3", "Chạy bộ", "#02a890", 12),
                                    new LuckyWheelSlice("4", "Nhảy xa", "#8dfd03", 12),
                                    new LuckyWheelSlice("5", "Bóng chuyền", "#fff203", 12),
                                    new LuckyWheelSlice("6", "Gofl", "#ffc30d", 12),
                                    new LuckyWheelSlice("7", "Lướt sóng", "#6a419d", 12),
                                    new LuckyWheelSlice("8", "Game", "#ffc30d", 12),
                                    new LuckyWheelSlice("9", "Bóng rổ", "#a8449a", 12),
                                    new LuckyWheelSlice("10", "Nhảy dây", "#e44097", 12),
                                    new LuckyWheelSlice("11", "Bún thun", "#ec1a23", 12),
                                    new LuckyWheelSlice("12", "Cờ vua", "#e4722e", 12)
                            )
                    )

            ));

    public static ArrayList<LuckyWheelData> luckyWheelList = new ArrayList<>(
            Arrays.asList(
                    new LuckyWheelData("1", "Ca sĩ yêu thích?", 16, 1, 3,
                            false, slices.get(0)
                    ),
                    new LuckyWheelData("2", "Hôm nay làm gì?", 16, 1, 3,
                            false, slices.get(1)),
                    new LuckyWheelData("3", "Hôm nay ăn gì?", 16, 1, 3,
                            false, slices.get(2)),
                    new LuckyWheelData("4", "Hôm nay chơi gì?", 16, 1, 3,
                            false, slices.get(3))
            )
    );

    public static ArrayList<String> contentItem1 = new ArrayList<>(
            Arrays.asList(
                    "Ăn", "Ngủ", "Nhậu", "Học", "Chơi", "Golf"
            )
    );

    public static ArrayList<String> contentItem2 = new ArrayList<>(
            Arrays.asList(
                    "Pizza", "Sushi", "Burger", "Lẩu Thái", "Mì", "Hủ tiếu"
            )
    );

    public static ArrayList<String> contentItem3 = new ArrayList<>(
            Arrays.asList(
                    "Bóng đá", "Bơi", "Chạy bộ", "Nhảy xa", "Bóng chuyền", "Gofl", "Lướt sóng", "Game",
                    "Bóng rổ", "Nhảy dây", "Bún thun", "Cờ vua"
            )
    );

    public static ArrayList<String> mixedContentItem1 = new ArrayList<>(
            Arrays.asList(
                    "Ăn", "Ngủ", "Nhậu", "Học", "Chơi", "Gofl"
            )
    );

    public static ArrayList<String> mixedContentItem2 = new ArrayList<>(
            Arrays.asList(
                    "Pizza", "Sushi", "Burger", "Lẩu Thái", "Mì", "Hủ tiếu"
            )
    );

    public static ArrayList<String> mixedContentItem3 = new ArrayList<>(
            Arrays.asList(
                    "Bóng đá", "Bơi", "Chạy bộ", "Nhảy xa", "Bóng chuyền", "Gofl", "Lướt sóng", "Game",
                    "Bóng rổ", "Nhảy dây", "Bún thun", "Cờ vua"
            )
    );
    public static ArrayList<ArrayList<String>> listContent = new ArrayList<>(
            Arrays.asList(
                    contentItem1,
                    contentItem2,
                    contentItem3
            )
    );
    public static ArrayList<ArrayList<String>> mixedContentItem = new ArrayList<>(
            Arrays.asList(
                    mixedContentItem1,
                    mixedContentItem2,
                    mixedContentItem3
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
    public static ArrayList<ArrayList<String>> listColor = new ArrayList<>(
            Arrays.asList(
                    listColor1,
                    listColor2,
                    listColor3
            )
    );

}
