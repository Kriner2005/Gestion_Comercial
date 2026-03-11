package co.edu.uptc.intefaces;

public interface ViewInterface {
    void setPresenter(PresenterInterface presenter);

    void start();

    void showMessage(String msg);

    void showError(String msg);

    void showAlert(String msg);
}
