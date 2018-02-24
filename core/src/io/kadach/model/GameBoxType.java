package io.kadach.model;


public enum GameBoxType {

    _2048(null, 2048),
    _1024(_2048, 1024),
    _512(_1024, 512),
    _256(_512, 256),
    _128(_256, 128),
    _64(_128, 64),
    _32(_64, 32),
    _16(_32, 16),
    _8(_16, 8),
    _4(_8, 4),
    _2(_4, 2);

    private final GameBoxType next;
    private final int points;

    public GameBoxType getNext() {
        return next;
    }

    public int getPoints() {
        return points;
    }

    GameBoxType(GameBoxType next, int points) {
        this.next = next;
        this.points = points;
    }

}
