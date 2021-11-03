package nifi.testing.application;
import javafx.application.Application;
import javafx.stage.Stage;
public class NifiTestingApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        GUI GUI = new GUI(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}