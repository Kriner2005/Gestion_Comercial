package co.edu.uptc.presenter;

import co.edu.uptc.intefaces.ModelInterface;
import co.edu.uptc.intefaces.PresenterInterface;
import co.edu.uptc.intefaces.ViewInterface;

public class Runner {
    PresenterInterface presenter;
    ModelInterface model;
    ViewInterface view;

    private void makwMVP() {
        presenter = null;
        model = null;
        view = null;

        presenter.setModel(model);
        presenter.setView(view);

        view.setPresenter(presenter);
    }

    public void run() {
        makwMVP();
        view.start();
    }
}
