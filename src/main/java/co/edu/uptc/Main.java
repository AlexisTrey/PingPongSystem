package co.edu.uptc;

import co.edu.uptc.presenter.Runner;
import co.edu.uptc.util.ThemeManager;

public class Main {

    public static void main(String[] args) {
        ThemeManager.applyByKey(ThemeManager.LIGHT);
        new Runner().start();
    }

}
