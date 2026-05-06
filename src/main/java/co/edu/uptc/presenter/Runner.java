package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.MainModel;
import co.edu.uptc.view.MainFrame;

public class Runner {
    private ModelInterface model;
    private ViewInterface view;
    private PresenterInterface presenter;

    public void makeMVP() {
        model = new MainModel();
        presenter = new MainPresenter();
        view = MainFrame.getInstance();

        presenter.setModel(model);
        presenter.setView(view);
        view.setPresenter(presenter);
    }

    public void start() {
        makeMVP();
        view.start();
    }
}
