package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;

public class GamePresenter implements PresenterInterface {

    private ModelInterface model;
    private ViewInterface view;

    public GamePresenter() {}

    @Override public void setModel(ModelInterface model)  { this.model = model; }
    @Override public void setView(ViewInterface view)     { this.view = view;   }
    @Override public void onStartGame()                   {}
    @Override public void onPauseGame()                   {}
    @Override public void onResumeGame()                  {}
    @Override public void onResetGame()                   {}
    @Override public void onAddBall()                     {}
    @Override public void onMoveRacket(int y)             {}
    @Override public void onIncreaseSpeed()               {}
    @Override public void onDecreaseSpeed()               {}
    @Override public void refreshView()                   {}
    @Override public void onGameOver()                    {}
}
