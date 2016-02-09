package edu.bsu.storygame.views;


import edu.bsu.storygame.Encounter;
import edu.bsu.storygame.GameContext;
import edu.bsu.storygame.Phase;
import edu.bsu.storygame.Reaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class EncounterView extends VBox {
    @FXML
    private Label regionLabel;
    @FXML
    private ImageView monsterImageView;
    @FXML
    private Label monsterNarrative;
    @FXML
    private VBox choices;
    @FXML
    private Button confirmButton;
    @FXML
    private Label promptText;

    private final Encounter encounter;
    private final ToggleGroup choiceGroup = new ToggleGroup();
    private GameContext context;

    public EncounterView(Encounter encounter, GameContext context) {
        super();
        this.encounter = encounter;
        this.context = context;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EncounterView.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.setLocation(getClass().getResource("/EncounterView.fxml"));
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        populate();
    }

    private void populate() {
        setRegion();
        setMonsterNarrative();
        setImage();
        addChoices();
    }

    private void setRegion() {
        regionLabel.setText("Encounter in " + encounter.getRegion());
    }

    private void setMonsterNarrative() {
        monsterNarrative.setText(encounter.getNarrative());
    }

    private void setImage() {
        monsterImageView.setImage(encounter.getMonsterImage());
    }

    private void addChoices() {
        ToggleButton newChoiceButton;
        for (Reaction reaction : encounter.getReactions()) {
            newChoiceButton = new ChoiceToggleButton(reaction);
            choices.getChildren().add(newChoiceButton);
        }
    }

    @FXML
    public void onConfirm(ActionEvent actionEvent) {
        confirmButton.setOnAction(event -> {
            context.phase.update(Phase.MOVEMENT);
        });
    }

    private final class ChoiceToggleButton extends ToggleButton {
        public final Reaction reaction;

        public ChoiceToggleButton(Reaction reaction) {
            super(reaction.toString());
            this.reaction = reaction;
            setToggleGroup(choiceGroup);
            setOnAction(event -> choose());
        }

        private void choose() {
            boolean isChoiceSelected = choiceGroup.getSelectedToggle() != null;
            confirmButton.setDisable(!isChoiceSelected);
            promptText.setText("Hand off to the next player!");
            promptText.setStyle("-fx-font-weight: bold;");
        }
    }
}
