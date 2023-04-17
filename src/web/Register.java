package web;

import input.Action;
import input.Credentials;
import input.User;

import java.util.ArrayList;

public class Register extends PageState {

    public Register(final WebPage updatedPage) {
        super(updatedPage);
    }

    /**
     *
     * @param action change page action to be executed. Nothing to be done on this page
     */
    @Override
    public void changePage(final Action action) {
        message("error");
    }

    /**
     * Switches to HomepageAutentificat if "register", to outputs error otherwise.
     * @param action change page action to be executed
     */
    @Override
    public void onPage(final Action action) {
        if (action.getFeature().equals("register")) {
            register(getWebPage().getDataBase().getUsers(), action.getCredentials());
            getWebPage().setState(new HomepageAutentificat(getWebPage()));
            message("success");
        } else {
            message("error");
        }
    }

    /**
     * Creates new user and adds it to users array
     * @param users all users
     * @param newUserCredentials credentials of user to be created
     */
    public void register(final ArrayList<User> users, final Credentials newUserCredentials) {
        User user = new User();
        user.setCredentials(newUserCredentials);
        getWebPage().setCurrentUser(user);
        users.add(user);
    }
}
