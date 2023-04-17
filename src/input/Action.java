package input;

import input.filters.Filters;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Action {
    private String type;
    private String feature;
    private String page;

    private String startsWith;
    private Credentials credentials;

    private Filters filters;

    private String movie;

    private String count;

    private int rate;

    public Action() {

    }
}
