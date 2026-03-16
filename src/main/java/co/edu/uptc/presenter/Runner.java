package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.MainModel;
import co.edu.uptc.view.ConsoleView;

public class Runner {

    public void run() {
        ModelInterface model = new MainModel();
        PresenterInterface presenter = new MainPresenter();
        ViewInterface view = new ConsoleView();

        presenter.setModel(model);
        presenter.setView(view);
        view.setPresenter(presenter);

        view.start();
    }
}