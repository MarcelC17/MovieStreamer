package input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Getter
@Setter
public class User {
    static final int FREE_PREMIUM = 15;
    private ArrayList<Movie> purchasedMovies = new ArrayList<>();
    private ArrayList<Movie> watchedMovies = new ArrayList<>();
    private ArrayList<Movie> likedMovies = new ArrayList<>();
    private ArrayList<Movie> ratedMovies = new ArrayList<>();
    @JsonIgnore
    private ArrayList<Movie> currentMoviesList = new ArrayList<>();
    @JsonIgnore
    private ArrayList<Movie> currentUserMovies = new ArrayList<>();
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies = FREE_PREMIUM;

    public User() {
    }
}
