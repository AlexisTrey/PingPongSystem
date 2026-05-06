package co.edu.uptc.util;

import co.edu.uptc.i18nlib.PropertiesLoader;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class ThemeManager {
    private static final String FILE_NAME = "theme.properties";
    private static final String KEY       = "theme";
    public static final String LIGHT = "light";
    public static final String DARK  = "dark";
    public static final Map<String, String> TEMAS = new LinkedHashMap<>();
    static {
        TEMAS.put("Claro",  LIGHT);
        TEMAS.put("Oscuro", DARK);
    }
    private ThemeManager() {}
    public static void applyFromProperties() {
        PropertiesLoader loader = new PropertiesLoader();
        loader.load(FILE_NAME);
        String theme = loader.get(KEY);
        if (theme == null) theme = LIGHT;
        applyByKey(theme);
    }
    public static void applyByKey(String key) {
        try {
            if (DARK.equals(key)) UIManager.setLookAndFeel(new FlatDarkLaf());
            else                  UIManager.setLookAndFeel(new FlatLightLaf());
            com.formdev.flatlaf.FlatLaf.updateUI();
            save(key);
        } catch (Exception e) {
            System.err.println("Error al aplicar tema: " + e.getMessage());
        }
    }
    private static void save(String key) {
        Properties props = new Properties();
        props.setProperty(KEY, key);
        try (FileOutputStream out = new FileOutputStream(FILE_NAME)) {
            props.store(out, "Configuracion de tema");
        } catch (IOException e) {
            System.err.println("Error guardando tema: " + e.getMessage());
        }
    }
}
