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
                    new LuckyWheelData(UUID.randomUUID().toString(), "Hôm nay ăn gì?", 36, 1, 3,
                            false),
                    new LuckyWheelData(UUID.randomUUID().toString(), "Hôm nay làm gì?", 36, 1, 3,
                            false),
                    new LuckyWheelData(UUID.randomUUID().toString(), "Ca sĩ yêu thích?", 36, 1, 3,
                            false),
                    new LuckyWheelData(UUID.randomUUID().toString(), "Hôm nay chơi gì?", 36, 1, 3,
                            false)
            )
    );

    public static List<LuckyWheelSlice> slices = new ArrayList<>(
            Arrays.asList(
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Charlie Puth", "#1E3163", "#F8F8F8", 12, luckyWheelList.get(2).getId(), 0),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Selena Gomez", "#2D46B9", "#F8F8F8", 12, luckyWheelList.get(2).getId(), 1),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Adam Levine", "#F037A5", "#F8F8F8", 12, luckyWheelList.get(2).getId(), 2),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Ăn", "#001A72", "#FFFFFF", 12, luckyWheelList.get(1).getId(), 0),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Ngủ", "#423F9F", "#FFFFFF", 12, luckyWheelList.get(1).getId(), 1),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Nhậu", "#7267CE", "#FFFFFF", 12, luckyWheelList.get(1).getId(), 2),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Học", "#A292FE", "#FFFFFF", 12, luckyWheelList.get(1).getId(), 3),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Chơi", "#D2C0FF", "#FFFFFF", 12, luckyWheelList.get(1).getId(), 4),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Pizza", "#c31850", "#FFFFFF", 12, luckyWheelList.get(0).getId(), 6),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Sushi", "#2c6b5d", "#FFFFFF", 12, luckyWheelList.get(0).getId(), 7),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Burger", "#333da0", "#FFFFFF", 12, luckyWheelList.get(0).getId(), 8),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Lẩu Thái", "#c01500", "#FFFFFF", 12, luckyWheelList.get(0).getId(), 9),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Mì", "#f7b600", "#FFFFFF", 12, luckyWheelList.get(0).getId(), 10),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Hủ tiếu", "#3380e8", "#FFFFFF", 12, luckyWheelList.get(0).getId(), 11),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Cơm gà", "#0b4581", "#FFFFFF", 12, luckyWheelList.get(0).getId(), 12),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Bơi", "#98f5e1", "#4c7a70", 12, luckyWheelList.get(3).getId(), 0),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Chạy bộ", "#8eecf5", "#47767a", 12, luckyWheelList.get(3).getId(), 1),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Đá bóng", "#90dbf4", "#486d7a", 12, luckyWheelList.get(3).getId(), 2),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Bóng chuyền", "#a3c4f3", "#516279", 12, luckyWheelList.get(3).getId(), 3),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Golf", "#cfbaf0", "#7c6f90", 12, luckyWheelList.get(3).getId(), 4),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Lướt sóng", "#f1c0e8", "#786074", 12, luckyWheelList.get(3).getId(), 5),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Video Game", "#ffcfd2", "#7f6769", 12, luckyWheelList.get(3).getId(), 6),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Bóng rổ", "#fde4cf", "#7e7267", 12, luckyWheelList.get(3).getId(), 7),
                    new LuckyWheelSlice(UUID.randomUUID().toString(), "Nhảy dây", "#fbf8cc", "#7d7c66", 12, luckyWheelList.get(3).getId(), 8)
            ));

}
