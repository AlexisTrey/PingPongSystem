package co.edu.uptc;

import co.edu.uptc.presenter.Runner;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        setupLookAndFeel();
        new Runner().start();
    }

    private static void setupLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            //UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Error al inicializar FlatLaf");
        }
    }
}
