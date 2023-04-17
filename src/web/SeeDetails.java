package web;

import input.Action;
import input.Movie;
import utils.Constants;

import java.util.ArrayList;

public class SeeDetails extends PageState {
    public SeeDetails(final WebPage updatedPage) {
        super(updatedPage);
    }

    /**
     * Case movies - resets movie arraylist, switches to movies
     * Case homepage autentificat - switches to  HomepageNeautentificat
     * Case upgrades - switches to upgrades
     * Case logout - resets user and current movie list, switches to HomepageNeautentificat
     * @param action change page action to be executed
     */
    @Override
    public void changePage(final Action action) {
        switch (action.getPage()) {
            case "movies" -> {
                getWebPage().setState(new Movies(getWebPage()));
                getWebPage().getCurrentUser().setCurrentMoviesList(
                        new ArrayList<>(getWebPage().getCurrentUser().getCurrentUserMovies()));
                message("success");
            }
            case "homepage autentificat" -> getWebPage().setState(
                    new HomepageAutentificat(getWebPage()));
            case "upgrades" -> getWebPage().setState(new Upgrades(getWebPage()));
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
     *Executes purchase, watch, like and rate actions
     * @param action on page action to be executed
     */
    @Override
    public void onPage(final Action action) {
        Movie currentMovie = findMovie(action.getMovie());
        switch (action.getFeature()) {
            case "purchase" -> {
                if (currentMovie!= null) {
                    //checks if user is premium
                    if (getWebPage().getCurrentUser().getCredentials()
                            .getAccountType().equals("premium")) {
                        purchasePremium(currentMovie);
                    } else if (getWebPage().getCurrentUser().getTokensCount() > 2) {
                        purchaseStandard(currentMovie);
                    }
                } else {
                    message("error");
                }
            }
            case "watch" -> {
                watchMovie(currentMovie);
            }

            case "like" -> {
                likeMovie(currentMovie);
            }

            case "rate" -> {
                if (currentMovie != null && action.getRate() <= Constants.MAX_RATE) {
                    rateMovie(currentMovie, action.getRate());
                } else {
                    message("error");
                }
            }
        }
    }

    /**
     * Executes purchase action if user is premium
     * @param currentMovie movie to be purchased
     */
    public void purchasePremium(Movie currentMovie){
        //checks for free movies
        if (getWebPage().getCurrentUser().getNumFreePremiumMovies() > 0) {
            getWebPage().getCurrentUser().setNumFreePremiumMovies(
                    getWebPage().getCurrentUser().getNumFreePremiumMovies() - 1);
            //adds movie to purchase movies
            getWebPage().getCurrentUser().getPurchasedMovies()
                    .add(currentMovie);
            message("success");
            //case no free movies left
        } else if (getWebPage().getCurrentUser().getTokensCount() > 2) {
            //user uses tokens to purchase
            getWebPage().getCurrentUser().setTokensCount(getWebPage()
                    .getCurrentUser().getTokensCount() - 2);
            getWebPage().getCurrentUser().getPurchasedMovies()
                    .add(currentMovie);
            message("success");
        } else {
            message("error");
        }
    }

    /**
     * Executes purchase movie for standard user
     * @param currentMovie movie to be purchased
     */
    public void purchaseStandard(Movie currentMovie){
        getWebPage().getCurrentUser().setTokensCount(getWebPage()
                .getCurrentUser().getTokensCount() - 2);
        getWebPage().getCurrentUser().getPurchasedMovies()
                .add(currentMovie);
        message("success");
    }

    /**
     * Executes watch movie action
     * @param currentMovie movie to be watched
     */
    public void watchMovie(Movie currentMovie){
        if (currentMovie != null) {
            //checks if movie was purchased
            if (getWebPage().getCurrentUser().getPurchasedMovies()
                    .contains(currentMovie)) {
                //adds movie to watched movies
                getWebPage().getCurrentUser().getWatchedMovies().add(currentMovie);
                message("success");
            } else {
                message("error");
            }
        } else {
            message("error");
        }
    }

    /**
     * Executes like movie action
     * @param currentMovie movie to like
     */
    public void likeMovie(Movie currentMovie){
        if (currentMovie!= null) {
            //checks if the movie was watched
            if (getWebPage().getCurrentUser().getWatchedMovies()
                    .contains(currentMovie)) {
                //adds movie to liked movies
                getWebPage().getCurrentUser().getLikedMovies().add(currentMovie);
                currentMovie.setNumLikes(currentMovie.getNumLikes() + 1);
                message("success");
            } else {
                message("error");
            }
        } else {
            message("error");
        }
    }

    /**
     * Executes rate movie action
     * @param currentMovie movie to be rated
     * @param rating rating to be given
     */
    public void rateMovie(Movie currentMovie, int rating){
        //checks if the movie was watched
        if (getWebPage().getCurrentUser().getWatchedMovies()
                .contains(currentMovie)) {
            double ratingSum = 0;
            currentMovie.getRatings().add(rating);
            currentMovie.setNumRatings(currentMovie.getNumRatings() + 1);
            //calculates previous ratings sum from ratings arraylist
            for (Integer currentRating : currentMovie.getRatings()){
                ratingSum+=currentRating;
            }
            //sets new movie rating
            currentMovie.setRating(ratingSum/ currentMovie.getNumRatings());
            //adds movie to rated movies
            getWebPage().getCurrentUser().getRatedMovies().add(currentMovie);
            message("success");
        } else {
            message("error");
        }
    }

    /**
     * If no parameter to action takes current movie
     * @param movieToFind movie to be looked for in database
     * @return movie from original database
     */
    public Movie findMovie(final String movieToFind) {
        //case no parameter take movie from current movie list as movieToFind
        if (movieToFind == null) {
            for (Movie movie : getWebPage().getDataBase().getMovies()) {
                if (movie.getName().equals(getWebPage().getCurrentUser()
                        .getCurrentMoviesList().get(0).getName())) {
                    return movie;
                }
            }
            return null;
        }
        //searches for movie by given name
        if (movieToFind.equals(getWebPage().getCurrentUser()
                .getCurrentMoviesList().get(0).getName())) {
            for (Movie movie : getWebPage().getDataBase().getMovies()) {
                if (movie.getName().equals(movieToFind)) {
                    return movie;
                }
            }
            return null;
        }
        return null;
    }
}
