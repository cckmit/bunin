package my.bunin.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Crypt {

    private String merchantNo;
    private String message;
    private String signature;
}
