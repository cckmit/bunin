package my.bunin.trade.channel.access;

import my.bunin.trade.channel.access.bean.Request;
import my.bunin.trade.channel.access.bean.Response;

import java.io.IOException;

public interface Executor {

  <T extends Response> T execute(Request request) throws IOException;

}
