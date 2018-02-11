package io.kadach.model;


public enum GameBoxType {

    _2048(null),
    _1024(_2048),
    _512(_1024),
    _256(_512),
    _128(_256),
    _64(_128),
    _32(_64),
    _16(_32),
    _8(_16),
    _4(_8),
    _2(_4);

    private final GameBoxType next;

    public GameBoxType getNext() {
        return next;
    }

    GameBoxType(GameBoxType next) {
        this.next = next;
    }

}
