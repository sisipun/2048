package io.kadach.model;

public enum GameFieldSize {

    _3x3(3),
    _4x4(4),
    _5x5(5),
    _6x6(6);

    private final int size;

    public int getValue() {
        return size;
    }

    GameFieldSize(int size) {
        this.size = size;
    }

}
