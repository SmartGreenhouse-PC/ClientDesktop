package it.unibo.smartgh.controller.homepage;

import it.unibo.smartgh.view.homepage.HomepageView;
import it.unibo.smartgh.view.homepage.HomepageViewImpl;

public class HomepageControllerImpl implements HomepageController {

    private final HomepageView view;

    public HomepageControllerImpl(HomepageViewImpl homepageView) {
        this.view = homepageView;
    }
}
