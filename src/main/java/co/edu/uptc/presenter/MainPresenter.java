package co.edu.uptc.presenter;

import co.edu.uptc.intefaces.ModelInterface;
import co.edu.uptc.intefaces.PresenterInterface;
import co.edu.uptc.intefaces.ViewInterface;

public class MainPresenter implements PresenterInterface{

    private ViewInterface view;
    private ModelInterface model;


    @Override
    public void setView(ViewInterface view) {
        this.view = view;
    }

    @Override
    public void setModel(ModelInterface model) {
        this.model = model;
    }
    
}
