package input.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Filters {
    private Sort sort;
    private Contains contains;

    public Filters() {

    }
}
