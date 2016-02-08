package edu.bsu.storygame.views;

import edu.bsu.storygame.GameContext;
import edu.bsu.storygame.Phase;
import edu.bsu.storygame.Player;
import edu.bsu.storygame.Regions;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import react.Slot;

import java.util.ArrayList;
import java.util.List;

public class MapView extends StackPane {

    private GameContext gameContext;
    private final Button africaRegion = createRegionButton(Regions.Africa, 0,50);
    private Rectangle africaSpace;
    private final Button europeRegion = createRegionButton(Regions.Europe, 0,-25);
    private Rectangle europeSpace;
    private HBox hBox = new HBox();

    public MapView(GameContext gameContext){
        this.gameContext = gameContext;
        africaSpace = createPlayerSpace(0, 29, Regions.Africa);
        europeSpace = createPlayerSpace(0, -46, Regions.Europe);
        gameContext.phase.connect(new Slot<Phase>() {
            @Override
            public void onEmit(Phase phase) {
                updateButtonStatus(phase);
            }
        });
        updateButtonStatus(gameContext.phase.get());
        this.initMap();
    }

    private void setRegionTravelButtons(){
        africaRegion.setOnAction(event -> {
            if(!africaSpace.isVisible()){
                setPlayerPosition(europeSpace,africaSpace);

            }
            if(!gameContext.players.isEmpty()){
                gameContext.players.get(0).setRegion(Regions.Africa);
            }
            gameContext.phase.update(Phase.ENCOUNTER);

        });
        europeRegion.setOnAction(event -> {
            if(!europeSpace.isVisible()){
                setPlayerPosition(africaSpace,europeSpace);
            }
            if(!gameContext.players.isEmpty()){
                gameContext.players.get(0).setRegion(Regions.Europe);
            }

            gameContext.phase.update(Phase.ENCOUNTER);
        });
    }

    private void initMap(){
        this.setRegionTravelButtons();
        this.getChildren().add(createMapImage());
        this.getChildren().add(africaRegion);
        this.getChildren().add(africaSpace);
        this.getChildren().add(europeRegion);
        this.getChildren().add(europeSpace);
        setPlayerPosition(europeSpace,europeSpace);
        hBox.setTranslateY(425);
        this.getChildren().add(hBox);
        for (Player player: gameContext.players) {
            hBox.getChildren().add(new PlayerView(player));
        }

    }


    private ImageView createMapImage(){
        ImageView mapImageView = new ImageView(new Image(getClass().getResourceAsStream("/WorldMap.png")));
        mapImageView.setFitHeight(500);
        mapImageView.setFitWidth(500);
        return mapImageView;
    }

    private Button createRegionButton(Regions regionName, double xPosition, double yPosition){
        Button region = new Button(regionName.toString());
        region.setTranslateX(xPosition);
        region.setTranslateY(yPosition);
        return region;
    }

    private Rectangle createPlayerSpace(double xPosition, double yPosition, Regions region){
        Rectangle space = new Rectangle(20,20);
        space.setArcHeight(15);
        space.setArcWidth(15);
        space.setTranslateX(xPosition);
        space.setTranslateY(yPosition);
        for (Player player: gameContext.players) {
            if(player.getRegion().equals(region)){
                space.setFill(player.getPlayerColor());
            }
        }
        return space;
    }

    private void setPlayerPosition(Rectangle playerCurrentSpace, Rectangle playerNewSpace){
        playerCurrentSpace.setVisible(false);
        playerNewSpace.setVisible(true);
    }

    private void updateButtonStatus(Phase phase){
        if(!phase.equals(Phase.MOVEMENT)){
            africaRegion.setDisable(true);
            europeRegion.setDisable(true);
        }
        else{
            africaRegion.setDisable(false);
            europeRegion.setDisable(false);
        }
    }
}