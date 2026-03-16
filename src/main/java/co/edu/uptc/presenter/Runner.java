package co.edu.uptc.presenter;

import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.model.MainModel;
import co.edu.uptc.view.ConsoleView;

/**
 * Runner — Ensambla el patrón MVP y arranca la aplicación.
 *
 * Única responsabilidad: crear las tres piezas y conectarlas.
 * No tiene lógica de negocio ni de presentación.
 *
 * Orden del wiring:
 *   1. Instanciar Model, Presenter y View (concretos).
 *   2. Presenter conoce a Model y a View (a través de sus interfaces).
 *   3. View conoce a Presenter (a través de su interfaz).
 *   4. Arrancar con view.start().
 *
 * Por qué Runner no es Main directamente?
 *   En los tests puedes hacer new Runner() y cambiar qué
 *   implementaciones concretas inyecta, sin correr el main real.
 */
public class Runner {

    public void run() {
        ModelInterface     model     = new MainModel();
        PresenterInterface presenter = new MainPresenter();
        ViewInterface      view      = new ConsoleView();

        presenter.setModel(model);
        presenter.setView(view);
        view.setPresenter(presenter);

        view.start();
    }
}