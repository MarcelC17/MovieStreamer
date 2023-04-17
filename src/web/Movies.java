package web;

import input.Action;
import input.Movie;
import input.filters.Contains;

import java.util.ArrayList;
import java.util.Collections;

public class Movies extends PageState {
    public Movies(final WebPage updatedPage) {
        super(updatedPage);
    }

    /**
     * Case logout resets user and current movie list.
     * Case see details checks if the movie is in current movie list and goes to see Details
     * Case movies resets user movies, remains on the same page
     * @param action on page action to be executed
     */
    @Override
    public void changePage(final Action action) {
        switch (action.getPage()) {
            case "see details" -> {
                checkMovie(action.getMovie());
            }
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
            default -> message("error");
        }

    }

    /**
     * Executes search and filter actions
     * @param action on page action to be executed
     */
    @Override
    public void onPage(final Action action) {
        switch (action.getFeature()) {
            case "search" -> {
                getWebPage().getCurrentUser().getCurrentMoviesList().removeIf(movie ->
                        !movie.getName().startsWith(action.getStartsWith()));;
                message("success");
                getWebPage().getCurrentUser().setCurrentMoviesList(
                        new ArrayList<>(getWebPage().getCurrentUser().getCurrentUserMovies()));
            }
            case "filter" -> {
                //filters by actors and genres
                if (action.getFilters().getSort() != null
                        && action.getFilters().getContains() == null) {
                    setRate(getWebPage().getCurrentUser().getCurrentMoviesList(), action);
                    Collections.sort(getWebPage().getCurrentUser().getCurrentMoviesList());
                    message("success");
                    //filters by rating and duration
                } else if (action.getFilters().getContains() != null
                        && action.getFilters().getSort() == null) {
                    getWebPage().getCurrentUser().getCurrentMoviesList().removeIf(movie ->
                            setFilter(movie, action.getFilters().getContains()));
                    message("success");
                    // filters by rating,duration, actors and genres
                } else {
                    setRate(getWebPage().getCurrentUser().getCurrentMoviesList(), action);
                    getWebPage().getCurrentUser().getCurrentMoviesList().removeIf(movie ->
                            setFilter(movie, action.getFilters().getContains()));
                    Collections.sort(getWebPage().getCurrentUser().getCurrentMoviesList());
                    message("success");
                }
            }
            default -> message("error");
        }
    }

    /**
     * assigns sort type to filterByRating and filterByDuration movie params
     * @param currentMovieList list to be sorted
     * @param action sort action to be executed
     */
    public void setRate(final ArrayList<Movie> currentMovieList, Action action) {
        currentMovieList.forEach(movie -> {
            movie.setFilterByRating(action.getFilters().getSort().getRating());
            movie.setFilterByDuration(action.getFilters().getSort().getDuration());
        });
    }


    /**
     * finds movies not containing actors and genres to be removed
     * @param movie movie to be compared by actors and genres
     * @param contentToFilter lists of actors and genres given as filter
     * @return true if movie is to be removed
     */
    public boolean setFilter(final Movie movie, final Contains contentToFilter) {
        if (contentToFilter.getActors() != null && contentToFilter.getGenre() != null) {
            return !(checkGenres(movie, contentToFilter) && checkActors(movie, contentToFilter));

        } else if ((contentToFilter.getActors() == null)) {
            return !checkGenres(movie, contentToFilter);
        } else {
            return !checkActors(movie, contentToFilter);
        }
    }


    /**
     * Check if movie contains actors
     * @param movie movie to be compared by actors
     * @param contentToFilter lists of actors and genres given as filter
     * @return true if actors were found, false otherwise
     */
    public boolean checkActors(final Movie movie, final Contains contentToFilter) {
        int actorCount = 0;
        for (String actor : contentToFilter.getActors()) {
            if (movie.getActors().contains(actor)) {
                actorCount++;
            }
        }
        return actorCount == contentToFilter.getActors().size();
    }

    /**
     * Check if movie contains genres
     * @param movie movie to be compared by genres
     * @param contentToFilter lists of actors and genres given as filter
     * @return true if actors were found, false otherwise
     */
    public boolean checkGenres(final Movie movie, final Contains contentToFilter) {
        int genresCount = 0;
        for (String genre : contentToFilter.getGenre()) {
            if (movie.getGenres().contains(genre)) {
                genresCount++;
            }
        }
        return genresCount == contentToFilter.getGenre().size();
    }

    /**
     * Checks if movie belongs to current movie list. Switches to SeeDetails page if successful,
     * outputs error otherwise
     * @param movieToCheck movie to see details
     */
    public void checkMovie(final String movieToCheck) {
        getWebPage().getCurrentUser().getCurrentMoviesList().removeIf(movie -> {
            return !movie.getName().equals(movieToCheck);
        });

        if (getWebPage().getCurrentUser().getCurrentMoviesList().isEmpty()) {
            getWebPage().getCurrentUser().setCurrentMoviesList(
                    new ArrayList<>(getWebPage().getCurrentUser().getCurrentUserMovies()));
            message("error");
        } else {
            getWebPage().setState(new SeeDetails(getWebPage()));
            message("success");
        }
    }
}
