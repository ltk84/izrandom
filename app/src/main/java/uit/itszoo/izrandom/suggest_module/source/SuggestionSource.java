package uit.itszoo.izrandom.suggest_module.source;

import java.util.ArrayList;
import java.util.Arrays;

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
                    new Suggestion("Hủ tiếu" , R.drawable.hu_tieu, "Món nước"),
                    new Suggestion("Bún bò" , R.drawable.bun_bo, "Món nước"),
                    new Suggestion("Bún măng gà" , R.drawable.bun_mang_ga, "Món nước"),
                    new Suggestion("Bánh canh" , R.drawable.banh_canh, "Món nước"),
                    new Suggestion("Cháo" , R.drawable.chao, "Món nước"),
                    new Suggestion("Bánh mì" , R.drawable.banh_mi, "Món khô"),
                    new Suggestion("Cơm" , R.drawable.com, "Món khô"),
                    new Suggestion("Hamburger" , R.drawable.hamburger, "Món khô"),
                    new Suggestion("Pizza" , R.drawable.pizza, "Món khô"),
                    new Suggestion("Mì Ý" , R.drawable.mi_y, "Món khô"),
                    new Suggestion("Sushi" , R.drawable.sushi, "Món khô")
            )
    );
    public static  SuggestionCollection suggestionCollection2 = new SuggestionCollection(
            "Uống gì bây giờ?", R.drawable.ic_drink,
            Arrays.asList(
                    "Tất cả","Có cồn", "Có ga", "Không ga, không cồn"
            ),
            Arrays.asList(
                    new Suggestion("Rượu" , R.drawable.ruou, "Có cồn"),
                    new Suggestion("Bia" , R.drawable.bia, "Có cồn"),
                    new Suggestion("Soda" , R.drawable.soda, "Có ga"),
                    new Suggestion("Nước ngọt có ga" , R.drawable.nuoc_co_ga, "Có ga"),
                    new Suggestion("Nước tonic" , R.drawable.nuoc_tonic, "Có ga"),
                    new Suggestion("Nước lọc" , R.drawable.nuoc_loc, "Không ga, không cồn"),
                    new Suggestion("Nước trái cây" , R.drawable.nuoc_trai_cay, "Không ga, không cồn"),
                    new Suggestion("Trà" , R.drawable.tra, "Không ga, không cồn"),
                    new Suggestion("Cafe" , R.drawable.cafe, "Không ga, không cồn")
            )
    );
    static SuggestionCollection suggestionCollection3 = new SuggestionCollection(
            "Chơi gì bây giờ?", R.drawable.ic_bowling,
            Arrays.asList(
                    "Tất cả","Đồng đội", "Cá nhân"
            ),
            Arrays.asList(
                    new Suggestion("Bóng đá" , R.drawable.bong_da, "Đồng đội"),
                    new Suggestion("Bơi" , R.drawable.boi, "Cá nhân"),
                    new Suggestion("Bóng chuyền" , R.drawable.bong_chuyen, "Đồng dội"),
                    new Suggestion("Bóng rổ" , R.drawable.bong_ro, "Đồng đội"),
                    new Suggestion("LOL" , R.drawable.lol, "Đồng đội"),
                    new Suggestion("Liên quân" , R.drawable.lien_quan, "Đồng đội"),
                    new Suggestion("Bowling" , R.drawable.bowling, "Cá nhân"),
                    new Suggestion("Cầu lông" , R.drawable.cau_long, "Đống đội"),
                    new Suggestion("Cầu lông" , R.drawable.cau_long, "Cá nhân"),
                    new Suggestion("Quần vợt" , R.drawable.quan_vot, "Đồng đội"),
                    new Suggestion("Quần vợt" , R.drawable.quan_vot, "Cá nhân"),
                    new Suggestion("Golf" , R.drawable.golf, "Cá nhân")
            )
    );
    static SuggestionCollection suggestionCollection4 = new SuggestionCollection(
            "Làm gì bây giờ?", R.drawable.ic_do_some_thing,
            Arrays.asList(
                     "Tất cả","Ngoài trời", "Trong nhà"
            ),
            Arrays.asList(
                    new Suggestion("Cắm trại" , R.drawable.cam_trai, "Ngoài trời"),
                    new Suggestion("Đọc sách" , R.drawable.doc_sach, "Trong nhà"),
                    new Suggestion("Chơi game" , R.drawable.choi_game, "Trong nhà"),
                    new Suggestion("Đi du lịch" , R.drawable.di_du_lich, "Ngoài trời"),
                    new Suggestion("Ngủ thôi" , R.drawable.di_ngu, "Trong nhà"),
                    new Suggestion("Đi dạo" , R.drawable.di_dao, "Ngoài trời"),
                    new Suggestion("Thả diều" , R.drawable.tha_dieu, "Trong nhà"),
                    new Suggestion("Đi phượt" , R.drawable.di_phuot, "Ngoài trời"),
                    new Suggestion("Tập yoga" , R.drawable.tap_yoga, "Trong nhà"),
                    new Suggestion("Nấu ăn" , R.drawable.nau_an, "Trong nhà"),
                    new Suggestion("Câu cá" , R.drawable.cau_ca, "Ngoài trời"),
                    new Suggestion("Chạy bộ" , R.drawable.chay_bo, "Ngoài trời")
            )
    );
    static SuggestionCollection suggestionCollection5 = new SuggestionCollection(
            "Xem gì bây giờ?", R.drawable.ic_film,
            Arrays.asList(
                    "Tất cả","Âm nhạc", "Tin tức", "Thể thao", "Phim" , "Chia sẻ trải nghiệm"
            ),
            Arrays.asList(
                    new Suggestion("MV nhạc Âu Mĩ" , R.drawable.mv_au_mi, "Âm nhạc"),
                    new Suggestion("MV nhạc Kpop" , R.drawable.mc_kpop, "Âm nhạc"),
                    new Suggestion("MV nhạc Vpop" , R.drawable.mv_vpop, "Âm nhạc"),
                    new Suggestion("Tin tổng hợp" , R.drawable.tin_tong_hop, "Tin tức"),
                    new Suggestion("Tin chú ý hôm nay" , R.drawable.tin_nong, "Tin tức"),
                    new Suggestion("Tin tức thể thao" , R.drawable.tin_the_thao, "Tin tức"),
                    new Suggestion("Bóng đá" , R.drawable.xem_bong_da, "Thể thao"),
                    new Suggestion("Bóng chuyền" , R.drawable.bong_chuyen, "Thể thao"),
                    new Suggestion("Esport" , R.drawable.lol, "Thể thao"),
                    new Suggestion("Đấu võ đài" , R.drawable.dau_vo_dai, "Thể thao"),
                    new Suggestion("Cầu lông" , R.drawable.cau_long, "Thể thao"),
                    new Suggestion("Bơi lội" , R.drawable.boi, "Thể thao"),
                    new Suggestion("Phim hành động" , R.drawable.phim_hoat_dong, "Phim"),
                    new Suggestion("Anime" , R.drawable.anime, "Phim"),
                    new Suggestion("Phim hài" , R.drawable.phim_hai, "Phim"),
                    new Suggestion("Phim viễn tưởng" , R.drawable.phim_vien_tuong, "Phim"),
                    new Suggestion("Phim kinh dị" , R.drawable.phim_kinh_di, "Phim"),
                    new Suggestion("Phim tình cảm" , R.drawable.phim_ngon_tinh, "Phim"),
                    new Suggestion("Phim chiến tranh" , R.drawable.phim_chien_tranh, "Phim"),
                    new Suggestion("Phim cổ trang" , R.drawable.phim_co_trang, "Phim"),
                    new Suggestion("Vlog" , R.drawable.vlog, "Chia sẻ trải nghiệm"),
                    new Suggestion("Review phim" , R.drawable.review_phim, "Chia sẻ trải nghiệm"),
                    new Suggestion("Video unboxing" , R.drawable.unboxing, "Chia sẻ trải nghiệm"),
                    new Suggestion("Stream" , R.drawable.stream, "Chia sẻ trải nghiệm")
            )
    );
    /*public static  SuggestionCollection suggestionCollection6 = new SuggestionCollection(
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
    );*/
     public static ArrayList<SuggestionCollection> listCollection = new ArrayList<>(
            Arrays.asList(
                    suggestionCollection1,
                    suggestionCollection2,
                    suggestionCollection3,
                    suggestionCollection4,
                    suggestionCollection5
                    //suggestionCollection6
            )
    );
}
