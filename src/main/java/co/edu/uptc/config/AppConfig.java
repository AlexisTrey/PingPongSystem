package co.edu.uptc.config;

public class AppConfig {
    public static final int FRAME_WIDTH = 920;
    public static final int FRAME_HEIGHT = 640;
    public static final int PANEL_WIDTH = 690;
    public static final int PANEL_HEIGHT = 590;
    public static final int INFO_PANEL_WIDTH = 210;

    public static final int BALL_DIAMETER = 20;
    public static final int INITIAL_SPEED_X = 3;
    public static final int INITIAL_SPEED_Y = 3;

    public static final int RACKET_WIDTH = 15;
    public static final int RACKET_HEIGHT = 80;
    public static final int RACKET_X = PANEL_WIDTH - 30;

    public static final int SPEED_INCREMENT = 1;
    public static final int MAX_SPEED = 15;
    public static final int MIN_SPEED = 1;

    public static final int GAME_LOOP_DELAY_MS = 16;

    private AppConfig() {
    }
}
