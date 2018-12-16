/* 
 * Copyright 2018 Malmoset LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.malmoset.controls;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author sean
 */
public class TestApplication extends Application {

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        StackPane root = new StackPane();
        final StatDice dice = new StatDice();
        final StatBlock stats = new StatBlock();

        stats.setTranslateY(60);
        stats.setStatName("Cha");
        root.getChildren().add(dice);
        root.getChildren().add(stats);
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setOnHiding(new EventHandler<WindowEvent>() {

            public void handle(WindowEvent event) {
                //System.out.println("date " + dateChooser.getDate());
            }
        });
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
