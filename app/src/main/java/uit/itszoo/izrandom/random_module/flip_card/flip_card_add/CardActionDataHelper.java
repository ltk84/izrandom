package uit.itszoo.izrandom.random_module.flip_card.flip_card_add;

import uit.itszoo.izrandom.random_module.flip_card.model.CardModel;

public class CardActionDataHelper {
    public String action;
    public CardModel cardModel;

    public CardActionDataHelper(String action, CardModel cardModel) {
        this.action = action;
        this.cardModel = cardModel;
    }

    public void setCardModel(CardModel cardModel) {
        this.cardModel = cardModel;
    }
}
