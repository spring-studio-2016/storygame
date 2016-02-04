package edu.bsu.storygame;

import javafx.scene.paint.Color;
import react.RList;
import react.Value;

import java.util.ArrayList;
import java.util.List;

public class Player {


    private final String name;
    private final Color playerColor;
    public final RList<String> skills = new RList<String>(new ArrayList<String>(){});
    private String position;
    public final Value<Integer> totalPoints = Value.create(0);
    private Regions region = Regions.Europe;


    public Player(String name, Color playerColor, String position) {
        this.name = name;
        this.playerColor = playerColor;
        this.region = Regions.Europe;
        this.position = position;

    }

    public void setRegion(Regions region) {
        this.region = region;
    }

    public Regions getRegion() {
        return this.region;
    }

    public String getName() {
        return name;
    }

    public Color getPlayerColor() {
        return playerColor;
    }


}
