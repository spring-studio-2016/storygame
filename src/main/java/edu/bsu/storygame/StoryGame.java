package edu.bsu.storygame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import react.Slot;

public class StoryGame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        final GameContext context = new GameContext();
        PhaseLabel phaseLabel = new PhaseLabel(context);
        Button button = new Button("Change phase!");
        final MapView mapScreen = new MapView(context);
        final Stage mapStage = mapScreen.mapStage();
        mapStage.setTitle("Nightmare Defenders Map");
        button.setOnAction(event -> {
            if (context.phase.get().equals(Phase.MOVEMENT)) {
                context.phase.update(Phase.ENCOUNTER);
                mapScreen.africaRegion.setDisable(true);
                mapScreen.europeRegion.setDisable(true);
            } else {
                context.phase.update(Phase.MOVEMENT);
                mapScreen.africaRegion.setDisable(false);
                mapScreen.europeRegion.setDisable(false);
            }
        });


        Button loadMapViewButton = new Button("Go To Game Map!");
        loadMapViewButton.setOnAction(event -> {
            try {
                mapStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        primaryStage.setTitle("Spring Studio Project");
        VBox root = new VBox();
        root.getChildren().add(phaseLabel);
        root.getChildren().add(button);
        root.getChildren().add(loadMapViewButton);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    private class PhaseLabel extends Label {

        public PhaseLabel(GameContext context) {
            super(context.phase.get().toString());
            context.phase.connect(new Slot<Phase>() {
                @Override
                public void onEmit(Phase phase) {
                    PhaseLabel.this.setText(phase.toString());
                }
            });
        }
    }




}
