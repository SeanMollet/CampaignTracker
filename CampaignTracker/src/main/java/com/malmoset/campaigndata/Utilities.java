/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.google.common.io.Files;
import com.google.common.io.Resources;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
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

            dialog.show();
        }
        return files;
    }
}
