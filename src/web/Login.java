package web;

import input.Action;
import input.Credentials;
import input.User;

import java.util.ArrayList;

public class Login extends PageState {
    public Login(final WebPage updatedPage) {
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
     * Switches to HomepageAutentificat if log in is successful, to HomepageNeautentificat otherwise.
     * @param action change page action to be executed.
     */
    @Override
    public void onPage(final Action action) {
        if (action.getFeature().equals("login")) {
            User userToLogin = checkLogin(
                    getWebPage().getDataBase().getUsers(), action.getCredentials());

            if (userToLogin != null && getWebPage().getCurrentUser() == null) {
                getWebPage().setCurrentUser(userToLogin);
                getWebPage().setState(new HomepageAutentificat(getWebPage()));
                message("success");
                return;
            }

            getWebPage().setState(new HomepageNeautentificat(getWebPage()));
            message("error");
            return;
        }
        message("error");
    }

    /**
     *
     * @param users contains information about all users
     * @param userCredentials credential of user to log in
     * @return Logged in user if credentials matched
     */
    public User checkLogin(final ArrayList<User> users, final Credentials userCredentials) {
        for (User user : users) {
            if (user.getCredentials().getName().equals(
                    userCredentials.getName()) && user.getCredentials().getPassword().equals(
                            userCredentials.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
