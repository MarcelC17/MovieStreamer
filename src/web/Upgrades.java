package web;

import input.Action;
import input.User;
import utils.Constants;

import java.util.ArrayList;

public class Upgrades extends PageState {
    public Upgrades(final WebPage updatedPage) {
        super(updatedPage);
    }

    /**
     * Executes buy tokens, buy premium account actions
     * @param action on page action to be executed
     */
    @Override
    public void onPage(final Action action) {
        switch (action.getFeature()) {
            case "buy tokens" -> {
                // checks user balance
                if (Integer.parseInt(action.getCount())
                        <= Integer.parseInt(
                                getWebPage().getCurrentUser().getCredentials().getBalance())) {
                    buyTokens(getWebPage().getCurrentUser(), action.getCount());
                } else {
                    message("error");
                }
            }
            case "buy premium account" -> {
                //checks if user has enough tokens
                if (Constants.PREMIUM_ACC_PRICE
                        <= getWebPage().getCurrentUser().getTokensCount()) {
                    buyPremiumAcc(getWebPage().getCurrentUser());
                } else {
                    message("error");
                }
            }
            default -> {
                message("error");
            }
        }
    }

    /**
     * @param action change page action to be executed
     */
    @Override
    public void changePage(final Action action) {
        switch (action.getPage()) {
            case "homepage autentificat" -> {
                getWebPage().setState(new HomepageAutentificat(getWebPage()));
            }
            case "movies" -> {
                getWebPage().setState(new Movies(getWebPage()));
                message("success");
            }
            case "logout" -> {
                getWebPage().getCurrentUser().setCurrentMoviesList(new ArrayList<>());
                getWebPage().setCurrentUser(null);
                getWebPage().setState(new HomepageNeautentificat(getWebPage()));
            }
            default -> {
                message("error");
            }
        }
    }

    /**
     * Adds tokens to user wallet and subtracts from balance
     * @param user user to buy tokens
     * @param tokenCount users tokens
     */
    public void buyTokens(final User user, final String tokenCount) {
        user.setTokensCount(Integer.parseInt(
                user.getTokensCount() + tokenCount));
        user.getCredentials().setBalance(
                Integer.toString(Integer.parseInt(
                        user.getCredentials().getBalance()) - Integer.parseInt(tokenCount)));
    }

    /**
     * Sets user account to premium, subtracts tokens
     * @param user user to buy premium account
     */
    public void buyPremiumAcc(final User user) {
        user.getCredentials().setAccountType("premium");
        user.setTokensCount(
                user.getTokensCount() - Constants.PREMIUM_ACC_PRICE);
    }
}
