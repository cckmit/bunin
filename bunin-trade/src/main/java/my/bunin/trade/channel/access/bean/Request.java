package my.bunin.trade.channel.access.bean;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public abstract class Request {

    private final RequestType type;

    @Setter
    private AccessConfiguration configuration;
    private final LocalDateTime createTime = LocalDateTime.now();

    public Request(RequestType type) {
        this.type = type;
    }
}
