package web;

import input.Action;

import java.util.ArrayList;

public class HomepageAutentificat extends PageState {

    public HomepageAutentificat(final WebPage updatedPage) {
        super(updatedPage);
    }

    /**
     * Finds user movies depending on banned country before switching to movies and upgrades pages.
     * Logout resets current user and goes to HomepageNeautentificat.
     * @param action change page action to be executed.
     */
    @Override
    public void changePage(final Action action) {
        switch (action.getPage()) {
            case "logout" -> {
                getWebPage().getCurrentUser().setCurrentMoviesList(new ArrayList<>());
                getWebPage().setCurrentUser(null);
                getWebPage().setState(new HomepageNeautentificat(getWebPage()));
            }
            case "movies" -> {
                getWebPage().findCurrentUserMovies(getWebPage().getDataBase().getMovies());
                getWebPage().setState(new Movies(getWebPage()));
                message("success");
            }
            case "upgrades" -> {
                getWebPage().findCurrentUserMovies(getWebPage().getDataBase().getMovies());
                getWebPage().setState(new Upgrades(getWebPage()));
            }
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
