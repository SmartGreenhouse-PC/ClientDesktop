package it.unibo.smartgh;

import it.unibo.smartgh.controller.ApplicationController;
import it.unibo.smartgh.controller.ApplicationControllerImpl;
import it.unibo.smartgh.view.ApplicationView;
import it.unibo.smartgh.view.ApplicationViewImpl;

public final class Launcher {

    public static void main(final String[] args) {
        ApplicationView view = new ApplicationViewImpl();
        ApplicationController controller = new ApplicationControllerImpl(view);
        controller.start();
    }

}
