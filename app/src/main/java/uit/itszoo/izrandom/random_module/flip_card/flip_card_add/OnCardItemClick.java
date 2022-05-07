package uit.itszoo.izrandom.random_module.flip_card.flip_card_add;

public interface OnCardItemClick {
    void onClickConfirmButton(int position, String newCardContent);
    void onClickDeleteButton(int position);
}
