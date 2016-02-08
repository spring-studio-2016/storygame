package edu.bsu.storygame;

import edu.bsu.storygame.views.EncounterView;
import edu.bsu.storygame.views.GameWinView;
import edu.bsu.storygame.views.MapView;
import edu.bsu.storygame.views.PlayerCreationView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import react.Slot;
import react.UnitSlot;


public class StoryGame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    final GameContext context = new GameContext();
    private EncounterTable encounterTable;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        PlayerCreationView view = new PlayerCreationView(context);
        primaryStage.setScene(view.getPlayerCreationScene());
        view.onFinish.connect(new UnitSlot() {
            @Override
            public void onEmit() {
                context.phase.connect(new Slot<Phase>() {
                    @Override
                    public void onEmit(Phase phase) {
                        if(context.phase.get().equals(Phase.MOVEMENT)){
                            if(checkWinningCondition(context.players.get(0))){
                                primaryStage.setScene(setWinningScene());
                            } else {
                                MapView mapView = new MapView(context);
                                primaryStage.setScene(new Scene(mapView));
                            }
                        }
                        if (context.phase.get() == Phase.ENCOUNTER) {
                            Encounter encounter = encounterTable.createEncounter();
                            EncounterView view = new EncounterView(encounter, context);
                            primaryStage.setScene(new Scene(view));
                        }
                    }

                });
                context.phase.update(Phase.MOVEMENT);
            }
        });
        encounterTable = new EncounterTable(context);
        primaryStage.show();
    }

    private Scene setMapViewScene() {
        return new Scene(new MapView(context));

    }

    private Scene setWinningScene(){
        GameWinView view = new GameWinView();
        return view.getWinningScene();
    }

    private boolean checkWinningCondition(Player player){
        return player.totalPoints.get().equals(context.winningPointTotal.get());
    }
}
