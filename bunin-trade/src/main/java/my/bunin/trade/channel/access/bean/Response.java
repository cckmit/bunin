package my.bunin.trade.channel.access.bean;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class Response {

  private String code;
  private String message;
  private String content;

  private final LocalDateTime createTime = LocalDateTime.now();

}
