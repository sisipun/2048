package io.kadach.model;


import com.badlogic.gdx.graphics.Texture;

public enum GameBoxType {

    _32768(null, 32768, "32768.png"),
    _16384(_32768, 16384, "16384.png"),
    _8192(_16384, 8192, "8192.png"),
    _4096(_8192, 4096, "4096.png"),
    _2048(_4096, 2048, "2048.png"),
    _1024(_2048, 1024, "1024.png"),
    _512(_1024, 512, "512.png"),
    _256(_512, 256, "256.png"),
    _128(_256, 128, "128.png"),
    _64(_128, 64, "64.png"),
    _32(_64, 32, "32.png"),
    _16(_32, 16, "16.png"),
    _8(_16, 8, "8.png"),
    _4(_8, 4, "4.png"),
    _2(_4, 0, "2.png");

    private final GameBoxType next;
    private final int points;
    private final Texture texture;

    public GameBoxType getNext() {
        return next;
    }

    public int getPoints() {
        return points;
    }

    public Texture getTexture() {
        return texture;
    }

    GameBoxType(GameBoxType next, int points, String texturePath) {
        this.next = next;
        this.points = points;
        this.texture = new Texture(texturePath);
    }

}
