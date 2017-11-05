package com.example.lalit.movieguide;

import android.content.Context;

/**
 * Created by jainl on 04-11-2017.
 */

public class FavouriteButton extends android.support.v7.widget.AppCompatImageButton {

    Boolean isClicked;
    String cardType;

    public FavouriteButton(Context context) {
        super(context);
    }

    public FavouriteButton(Context context,String cardType) {
        super(context);
        isClicked = false;
        this.cardType = cardType;
    }

    public Boolean getClicked() {
        return isClicked;
    }

    public void setClicked(Boolean clicked) {
        isClicked = clicked;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
