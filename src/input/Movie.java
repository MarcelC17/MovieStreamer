package input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Movie implements Comparable<Movie> {
    @JsonIgnore
    private String filterByRating;
    @JsonIgnore
    private ArrayList<Integer> ratings = new ArrayList<>();
    @JsonIgnore
    private String filterByDuration;
    private int numLikes = 0;
    private int numRatings = 0;
    private double rating = 0;
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;

    public Movie() {

    }

    /**
     *
     * @param movieToCompare the object to be compared.
     * @return negative or positive value depending on difference between objects
     */
    @Override
    public int compareTo(final Movie movieToCompare) {
        //compares both duration and rating
        if (filterByDuration != null && filterByRating != null) {
            switch (this.filterByDuration) {
                case "increasing" -> {
                    if (this.duration - movieToCompare.duration != 0) {
                        return this.duration - movieToCompare.duration;
                    }
                    if (filterByRating.equals("increasing")) {
                        return (int) (this.rating - movieToCompare.rating);
                    }
                    return (int) (movieToCompare.rating - this.rating);

                }
                case "decreasing" -> {
                    if (this.duration - movieToCompare.duration != 0) {
                        return movieToCompare.duration - this.duration;
                    }
                    if (filterByRating.equals("increasing")) {
                        return (int) (this.rating - movieToCompare.rating);
                    }
                    return (int) (movieToCompare.rating - this.rating);
                }
                default -> {
                    return -1;
                }
            }
            //compares rating
        } else if (filterByDuration == null) {
            if (filterByRating.equals("increasing")) {
                return (int) (this.rating - movieToCompare.rating);
            }
            return (int) (movieToCompare.rating - this.rating);
            //compares both duration
        } else {
            if (filterByDuration.equals("increasing")) {
                return this.duration - movieToCompare.duration;
            }
            return movieToCompare.duration - this.duration;
        }
    }
}

