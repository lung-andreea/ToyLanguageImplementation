package View;

import Model.Commands.Command;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    MainWindowController mainWindowController;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("selectProgram.fxml"));
        Parent root = loader1.load();
        SelectProgramController selectProgramController = loader1.getController();
        primaryStage.setTitle("Toy Language Interpreter");
        primaryStage.setScene(new Scene(root, 600, 300));
        primaryStage.show();

        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        Parent root2 = loader2.load();
        MainWindowController mainWindowController = loader2.getController();
        Stage stg = new Stage();
        stg.setTitle("Toy Language Interpreter");
        stg.setScene(new Scene(root2, 800, 500));
        stg.show();

        Channel channel = new Channel(selectProgramController,mainWindowController);
        selectProgramController.channel=channel;
        mainWindowController.channel=channel;

    }



    public static void main(String[] args) {
        launch(args);
    }
}
