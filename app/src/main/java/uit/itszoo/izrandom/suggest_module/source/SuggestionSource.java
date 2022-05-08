package uit.itszoo.izrandom.suggest_module.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.suggest_module.models.Suggestion;
import uit.itszoo.izrandom.suggest_module.models.SuggestionCollection;

public class SuggestionSource {
    public static  SuggestionCollection suggestionCollection1 = new SuggestionCollection(
            "Ăn gì bây giờ?", R.drawable.ic_food,
            Arrays.asList(
                    "Tất cả","Món nước", "Món khô"
            ),
            Arrays.asList(
                    new Suggestion("Phở" , R.drawable.pho, "Món nước"),
                    new Suggestion("Hủ tiếu" , R.drawable.pho, "Món nước"),
                    new Suggestion("Bún bò" , R.drawable.pho, "Món nước"),
                    new Suggestion("Bò khô" , R.drawable.pho, "Món nước"),
                    new Suggestion("Bánh canh" , R.drawable.pho, "Món nước"),
                    new Suggestion("Cháo" , R.drawable.pho, "Món nước"),
                    new Suggestion("Bánh mì" , R.drawable.pho, "Món khô"),
                    new Suggestion("Cơm" , R.drawable.pho, "Món khô"),
                    new Suggestion("Hamburger" , R.drawable.pho, "Món khô"),
                    new Suggestion("Pizza" , R.drawable.pho, "Món khô"),
                    new Suggestion("Mì Ý" , R.drawable.pho, "Món khô"),
                    new Suggestion("Sushi" , R.drawable.pho, "Món khô")
            )
    );
    public static  SuggestionCollection suggestionCollection2 = new SuggestionCollection(
            "Uống gì bây giờ?", R.drawable.ic_drink,
            Arrays.asList(
                    "Tất cả","Có cồn", "Có ga", "Không ga, không cồn"
            ),
            Arrays.asList(
                    new Suggestion("Rượu" , R.drawable.pho, "Có cồn"),
                    new Suggestion("Bia" , R.drawable.pho, "Có cồn"),
                    new Suggestion("Soda" , R.drawable.pho, "Có ga"),
                    new Suggestion("Nước ngọt có ga" , R.drawable.pho, "Có ga"),
                    new Suggestion("Nước tonic" , R.drawable.pho, "Có ga"),
                    new Suggestion("Nước lọc" , R.drawable.pho, "Không ga, không cồn"),
                    new Suggestion("Nước trái cây" , R.drawable.pho, "Không ga, không cồn"),
                    new Suggestion("Trà" , R.drawable.pho, "Không ga, không cồn"),
                    new Suggestion("Cafe" , R.drawable.pho, "Không ga, không cồn")
            )
    );
    static SuggestionCollection suggestionCollection3 = new SuggestionCollection(
            "Chơi gì bây giờ?", R.drawable.ic_bowling,
            Arrays.asList(
                    "Tất cả","Đồng đội", "Cá nhân"
            ),
            Arrays.asList(
                    new Suggestion("Bóng đá" , R.drawable.pho, "Đồng đội"),
                    new Suggestion("Bơi" , R.drawable.pho, "Cá nhân"),
                    new Suggestion("Bóng chuyền" , R.drawable.pho, "Đồng dội"),
                    new Suggestion("Bóng rổ" , R.drawable.pho, "Đồng đội"),
                    new Suggestion("LOL" , R.drawable.pho, "Đồng đội"),
                    new Suggestion("Liên quân" , R.drawable.pho, "Đồng đội"),
                    new Suggestion("Bowling" , R.drawable.pho, "Cá nhân"),
                    new Suggestion("Cầu lông" , R.drawable.pho, "Đống đội"),
                    new Suggestion("Cầu lông" , R.drawable.pho, "Cá nhân"),
                    new Suggestion("Quần vợt" , R.drawable.pho, "Đồng đội"),
                    new Suggestion("Quần vợt" , R.drawable.pho, "Cá nhân"),
                    new Suggestion("Golf" , R.drawable.pho, "Cá nhân")
            )
    );
    static SuggestionCollection suggestionCollection4 = new SuggestionCollection(
            "Làm gì bây giờ?", R.drawable.ic_do_some_thing,
            Arrays.asList(
                     "Tất cả","Món nước", "Món khô"
            ),
            Arrays.asList(
                    new Suggestion("Phở" , R.drawable.pho, "Món nước"),
                    new Suggestion("Hủ tiếu" , R.drawable.pho, "Món nước"),
                    new Suggestion("Bún bò" , R.drawable.pho, "Món nước"),
                    new Suggestion("Bò khô" , R.drawable.pho, "Món nước"),
                    new Suggestion("Bánh canh" , R.drawable.pho, "Món nước"),
                    new Suggestion("Cháo" , R.drawable.pho, "Món nước"),
                    new Suggestion("Bánh mì" , R.drawable.pho, "Món khô"),
                    new Suggestion("Cơm" , R.drawable.pho, "Món khô"),
                    new Suggestion("Hamburger" , R.drawable.pho, "Món khô"),
                    new Suggestion("Pizza" , R.drawable.pho, "Món khô"),
                    new Suggestion("Mì Ý" , R.drawable.pho, "Món khô"),
                    new Suggestion("Sushi" , R.drawable.pho, "Món khô")
            )
    );
    static SuggestionCollection suggestionCollection5 = new SuggestionCollection(
            "Xem gì bây giờ?", R.drawable.ic_film,
            Arrays.asList(
                    "Tất cả","Món nước", "Món khô"
            ),
            Arrays.asList(
                    new Suggestion("Phở" , R.drawable.pho, "Món nước"),
                    new Suggestion("Hủ tiếu" , R.drawable.pho, "Món nước"),
                    new Suggestion("Bún bò" , R.drawable.pho, "Món nước"),
                    new Suggestion("Bò khô" , R.drawable.pho, "Món nước"),
                    new Suggestion("Bánh canh" , R.drawable.pho, "Món nước"),
                    new Suggestion("Cháo" , R.drawable.pho, "Món nước"),
                    new Suggestion("Bánh mì" , R.drawable.pho, "Món khô"),
                    new Suggestion("Cơm" , R.drawable.pho, "Món khô"),
                    new Suggestion("Hamburger" , R.drawable.pho, "Món khô"),
                    new Suggestion("Pizza" , R.drawable.pho, "Món khô"),
                    new Suggestion("Mì Ý" , R.drawable.pho, "Món khô"),
                    new Suggestion("Sushi" , R.drawable.pho, "Món khô")
            )
    );
    public static  SuggestionCollection suggestionCollection6 = new SuggestionCollection(
            "Đọc sách gì nhỉ?", R.drawable.ic_book,
            Arrays.asList(
                    "Tất cả","Món nước", "Món khô"
            ),
            Arrays.asList(
                    new Suggestion("Phở" , R.drawable.pho, "Món nước"),
                    new Suggestion("Hủ tiếu" , R.drawable.pho, "Món nước"),
                    new Suggestion("Bún bò" , R.drawable.pho, "Món nước"),
                    new Suggestion("Bò khô" , R.drawable.pho, "Món nước"),
                    new Suggestion("Bánh canh" , R.drawable.pho, "Món nước"),
                    new Suggestion("Cháo" , R.drawable.pho, "Món nước"),
                    new Suggestion("Bánh mì" , R.drawable.pho, "Món khô"),
                    new Suggestion("Cơm" , R.drawable.pho, "Món khô"),
                    new Suggestion("Hamburger" , R.drawable.pho, "Món khô"),
                    new Suggestion("Pizza" , R.drawable.pho, "Món khô"),
                    new Suggestion("Mì Ý" , R.drawable.pho, "Món khô"),
                    new Suggestion("Sushi" , R.drawable.pho, "Món khô")
            )
    );
     public static ArrayList<SuggestionCollection> listCollection = new ArrayList<>(
            Arrays.asList(
                    suggestionCollection1,
                    suggestionCollection2,
                    suggestionCollection3,
                    suggestionCollection4,
                    suggestionCollection5,
                    suggestionCollection6
            )
    );
}
