package co.edu.uptc.interfaces;

public interface ViewInterface {
    void setPresenter(PresenterInterface presenter);
    void start();
    void refresh();
    void showGameOver();
}
