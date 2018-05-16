package Presentation.Views;

import Presentation.Controllers.CtrlPresentation;

import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Abstract class to represent a FX View
 */
public abstract class ViewFX
{
    /**
     * The presentation controller instance
     */
    CtrlPresentation ctrlPresentation;

    /**
     * The view stage
     */
    Stage stage;


    /**
     * Default constructor needed by JavaFX
     */
    public ViewFX() {}

    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public ViewFX(CtrlPresentation ctrlPresentation)
    {
        this.ctrlPresentation = ctrlPresentation;
    }

    /**
     * Initializes the view
     */
    public abstract void initialize();

    /**
     * Displays the view
     */
    public abstract void display();

    /**
     * Hides the view
     */
    public abstract void hide();

    /**
     * Sets the view stage
     * @param stage The stage to be set
     */
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    /**
     * Sets the presentation controller
     * @param ctrlPresentation The CtrlPresentation instance to be set
     */
    public void setCtrlPresentation(CtrlPresentation ctrlPresentation) {
        this.ctrlPresentation = ctrlPresentation;
    }

    /**
     * Shows an Alert to the user
     * @param type Type of alert (Information, Warning, Error...)
     * @param title Title of the alert
     * @param header Header of the alert
     * @param content Content of the alert
     * @return Object containing the reference to the Alert
     */
    public Optional<ButtonType> showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert newAlert = new Alert(type);
        newAlert.setTitle(title);
        newAlert.setHeaderText(header);
        newAlert.setContentText(content);
        newAlert.getDialogPane().getStylesheets()
                .add(getClass().getResource("/Presentation/pasterStyle.css").toExternalForm());
        newAlert.getDialogPane().getStyleClass().add("pane");
        return newAlert.showAndWait();
    }

    /**
     * Generates the breaker mode panel
     * @param colorListHBox A reference to the HBox containing the colors
     * @param mouseHandler A mouse handler to detect clicks
     * @param colors Chosen colors list
     */
    public void generateBreakerPanel(HBox colorListHBox, MouseHandler mouseHandler, List<String> colors)
    {
        for (String color : colors)
        {
            Circle circle = new Circle(15, Paint.valueOf(color.toLowerCase()));
            circle.setUserData(this);
            mouseHandler.setCircleMousePress(circle);
            colorListHBox.getChildren().add(circle);
        }
    }


    /**
     * Handler class to detect mouse clicks
     */
    static class MouseHandler
    {
        /**
         * Enables a handler on the indicated circle
         * @param circle The circle representing the peg
         */
        public void setCircleMousePress(Circle circle)
        {
            circle.setOnMouseClicked(onMousePressedEventHandler);
        }

        EventHandler<MouseEvent> onMousePressedEventHandler = event ->
        {
            Circle circle = (Circle) event.getSource();
            IUserPanel view = (IUserPanel)circle.getUserData();
            view.addCircleToChosenPlayList(new Circle(circle.getRadius(), circle.getFill()));
        };
    }
}
