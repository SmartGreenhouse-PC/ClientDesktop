package it.unibo.smartgh.view;

/**
 * The interface that represents a sub view of the application.
 */
public interface SubView {

    /**
     * Initialize the view.
     * @param mainView the application main view
     * @param id       the greenhouse id
     */
    void initView(ApplicationView mainView, String id);
}
