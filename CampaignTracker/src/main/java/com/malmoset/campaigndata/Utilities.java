/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

/**
 *
 * @author sean
 */
public class Utilities {

    public static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean tryParseLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Thanks to https://stackoverflow.com/questions/1429172/how-do-i-list-the-files-inside-a-jar-file
    public static Map<String, String> FindFiles(String filter) {

        Map<String, String> files = new LinkedHashMap<>();

        try {
            Reflections reflections = new Reflections(new ResourcesScanner());
            Set<String> paths = reflections.getResources(Pattern.compile(filter));

            for (String path : paths) {
                //log.info("Found " + path);
                String templateName = Files.getNameWithoutExtension(path);
                URL resource = Utilities.class.getClassLoader().getResource(path);
                String text = Resources.toString(resource, StandardCharsets.UTF_8);
                files.put(templateName, text);
            }
        } catch (Exception E) {
            //If this blows up, they won't get any entries. That's acceptable
            Dialog dialog = new Dialog();
            dialog.setTitle("Error");
            dialog.setContentText(E.toString());
            dialog.getDialogPane().getButtonTypes().add(new ButtonType("Got it!", ButtonData.CANCEL_CLOSE));
            dialog.showAndWait();
        }
        return files;
    }

    public static void TestJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String text = Resources.toString(Utilities.class.getResource("/LootLibraries/ac.json"), StandardCharsets.UTF_8);

            AC test2 = new AC();
            test2.setAcValue(15);
            test2.setNotes("notes and stuff");
//            test2.getShit().add("shit1");
//            test2.getShit().add("shit2");

            String json = mapper.writeValueAsString(test2);

            AC test = mapper.readValue(text, AC.class);

            Dialog dialog = new Dialog();
            dialog.setTitle(test.getNotes());
            dialog.setContentText(json);

            dialog.show();

        } catch (Exception E) {
            //If this blows up, they won't get any entries. That's acceptable
            Dialog dialog = new Dialog();
            dialog.setTitle("Error");
            dialog.setContentText(E.toString());
            dialog.getDialogPane().getButtonTypes().add(new ButtonType("Got it!", ButtonData.CANCEL_CLOSE));
            dialog.showAndWait();
        }
    }
//Various auto resize methods, don't appear to work very well

//                SavesList.setColumnResizePolicy((param) -> true);
//                Platform.runLater(() -> Utilities.customResize(SkillsList));
//    public static void customResize(TableView<?> view) {
//
//        AtomicDouble width = new AtomicDouble();
//        view.getColumns().forEach(col -> {
//            width.addAndGet(col.getWidth());
//        });
//        double tableWidth = view.getWidth();
//
//        if (tableWidth > width.get()) {
//            TableColumn<?, ?> col = view.getColumns().get(view.getColumns().size() - 1);
//            col.setPrefWidth(col.getWidth() + (tableWidth - width.get()));
//        }
//
//    }
//
//Utilities.autoFitTable(SavesList);
//    private static Method columnToFitMethod;
//
//    static {
//        try {
//            columnToFitMethod = TableViewSkin.class.getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
//            columnToFitMethod.setAccessible(true);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void autoFitTable(TableView tableView) {
//        tableView.getItems().addListener(new ListChangeListener<Object>() {
//            @Override
//            public void onChanged(Change<?> c) {
//                for (Object column : tableView.getColumns()) {
//                    try {
//                        columnToFitMethod.invoke(tableView.getSkin(), column, -1);
//                    } catch (IllegalAccessException | InvocationTargetException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
}
