package my.bunin.payment.endpoint.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
public class Crypt {

    public static final String MERCHANT_NO = "merchantNo";
    public static final String MESSAGE = "message";
    public static final String CRYPT_KEY = "cryptKey";
    public static final String SIGNATURE = "signature";

    @NotNull
    private String merchantNo;

    @NotNull
    private String message;

    @NotNull
    private String cryptKey;

    @NotNull
    private String signature;

}
