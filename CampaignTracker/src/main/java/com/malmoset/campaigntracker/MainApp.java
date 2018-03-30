package com.malmoset.campaigntracker;

import com.malmoset.controls.BaseForm;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    //Property that holds out application data, universally accessible
    private static AppData appData;

    public static boolean isAppDataUP() {
        return appData != null;
    }

    public static AppData getAppData() {
        if (appData == null) {
            appData = new AppData();
        }
        return appData;
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Show the splash screen on the main stage, then launch the main menu
        stage.initStyle(StageStyle.TRANSPARENT);
        BaseForm controller = BaseForm.LoadForm(MenuActions.class.getResource("/fxml/Splash.fxml"), "Player Editor", false, stage);
        controller.getScene().setFill(Color.TRANSPARENT);
        PlatformSpecific.SetupIcon(stage);
        controller.Show();

        //Launch the main menu on a timer (500ms delay)
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                Platform.runLater(() -> {
                    try {
                        Stage Menustage = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));

                        Scene scene = new Scene(root);
                        scene.getStylesheets().add("/styles/Styles.css");

                        PlatformSpecific.SetupIcon(Menustage);

                        Menustage.setTitle("Campaign Tracker");
                        Menustage.setScene(scene);
                        Menustage.setResizable(false);
                        Menustage.show();

                        //Destroy the splash
                        stage.close();
                    } catch (Exception E) {
                        System.out.println(E.toString());
                    }
                });
            }
        }, 500l);

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
