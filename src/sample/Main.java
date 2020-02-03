package sample;

import com.sun.istack.internal.NotNull;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    public static void main( final String[] args ) throws IOException {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("TP MongoDB!");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }
}
