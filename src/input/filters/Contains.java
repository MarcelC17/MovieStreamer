package input.filters;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Contains {
    private ArrayList<String> actors;
    private ArrayList<String> genre;

    public Contains() {

    }
}
