package my.bunin.trade.channel.access;

import my.bunin.trade.channel.access.bean.PaymentRequest;
import my.bunin.trade.channel.access.bean.Request;
import my.bunin.trade.channel.access.bean.RequestType;
import my.bunin.trade.channel.access.bean.Response;

import java.io.IOException;
import java.util.Map;

public interface Executor {

    <T extends Response> T execute(Request request) throws IOException;

}
