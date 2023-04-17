package web;

import input.Action;

public class HomepageNeautentificat extends PageState {
    public HomepageNeautentificat(final WebPage updatedPage) {
        super(updatedPage);
    }

    /**
     * Switches to log in and register pages.
     * @param action change page action to be executed.
     *
     */
    @Override
    public void changePage(final Action action) {
        switch (action.getPage()) {
            case "login" -> getWebPage().setState(new Login(getWebPage()));
            case "register" -> getWebPage().setState(new Register((getWebPage())));
            default -> message("error");
        }
    }

    /**
     *
     * @param action on page action to be executed. Nothing to be done on this page
     */
    @Override
    public void onPage(final Action action) {
        message("error");
    }
}
