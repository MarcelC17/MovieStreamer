package web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Action;
import input.DataBase;
import input.Movie;
import input.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class WebPage {
    int count = 1;
    private PageState state;
    private DataBase dataBase;
    private ObjectMapper objectMapper = new ObjectMapper();
    private ArrayNode output = objectMapper.createArrayNode();
    private User currentUser;


    public WebPage() {
        setState(new HomepageNeautentificat(this));
    }

    /**
     * executes instructions
     */
    public void run() {
        setCurrentUser(null);
        for (Action action : dataBase.getActions()) {
            switch (action.getType()) {
                case "on page" -> state.onPage(action);
                case "change page" -> state.changePage(action);
                default -> System.out.println("No such action type");
            }
        }
    }

    /**
     * Constructs output
     * @param type output type
     */
    public void consructOutput(final String type) {

        ObjectNode outNode = getObjectMapper().createObjectNode();
        ArrayNode movieList = getObjectMapper().createArrayNode();
        //case error
        if (type.equals("error")) {
            outNode.put("error", "Error");
            outNode.set("currentMoviesList", movieList);
            outNode.set("currentUser", null);
            //case error null
        } else {
            outNode.set("error", null);
            movieList = getObjectMapper().valueToTree(
                    currentUser.getCurrentMoviesList());
            outNode.set("currentMoviesList", movieList);
            ObjectNode currentUser = getObjectMapper()
                    .valueToTree(getCurrentUser());
            outNode.set("currentUser", currentUser);
            count++;
        }

        output.add(outNode);
    }

    /**
     * finds movies for current user
     * @param movies all movies
     */
    public void findCurrentUserMovies(final ArrayList<Movie> movies) {
        getCurrentUser().setCurrentMoviesList(new ArrayList<>(movies));
        getCurrentUser().getCurrentMoviesList().removeIf(movie -> {
            for (String bannedCountry : movie.getCountriesBanned()) {
                if (bannedCountry.equals(getCurrentUser()
                        .getCredentials().getCountry())) {
                    return true;
                }
            }
            return false;
        });
        getCurrentUser().setCurrentUserMovies(
                new ArrayList<>(getCurrentUser().getCurrentMoviesList()));
    }
}

