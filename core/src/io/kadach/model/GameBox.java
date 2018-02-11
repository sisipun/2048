package io.kadach.model;


import static io.kadach.model.GameBoxType._2;

public class GameBox {

    private GameBoxType type;

    public GameBoxType getType() {
        return type;
    }

    public GameBox(GameBoxType type) {
        this.type = type;
    }

    public GameBox() {
        this.type = _2;
    }

}
