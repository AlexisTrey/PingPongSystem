package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.GameModel;
import co.edu.uptc.view.GameFrame;

public class Runner {
    private ModelInterface model;
    private ViewInterface view;
    private PresenterInterface presenter;

    public void makeMVP() {
        model = new GameModel();
        presenter = new GamePresenter();
        view = GameFrame.getInstance();

        presenter.setModel(model);
        presenter.setView(view);
        view.setPresenter(presenter);
    }

    public void start() {
        makeMVP();
        view.start();
    }
}
