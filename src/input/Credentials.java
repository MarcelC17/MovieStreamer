package input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Credentials {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private String balance;

    public Credentials() {
    }
}
