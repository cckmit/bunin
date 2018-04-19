package my.bunin.trade.channel.access.custody.lanmao;

import lombok.extern.slf4j.Slf4j;
import my.bunin.trade.channel.access.AbstractExecutor;
import my.bunin.trade.channel.access.bean.CustodyRegisterRequest;
import my.bunin.trade.channel.access.bean.Request;
import my.bunin.trade.channel.access.bean.Response;
import my.bunin.util.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

@Slf4j
public class LanmaoExecutor extends AbstractExecutor {

    private HttpClient client;

    public LanmaoExecutor(HttpClient client) {
        this.client = client;
    }

    @Override
    public <T extends Response> T execute(Request request) throws IOException {
        String requestContent = format(request);

        HttpResponse httpResponse = doExecute(request, requestContent);

        String responseContent = EntityUtils.toString(httpResponse.getEntity(), StringUtils.UTF8);

        //noinspection unchecked
        return (T)parse(request, responseContent);
    }

    @Override
    protected String format(CustodyRegisterRequest request) {
        return null;

    }

    private HttpResponse doExecute(Request request, String requestContent) throws IOException {
        HttpPost httpPost = new HttpPost(request.getConfiguration().getBaseUrl());
        httpPost.setEntity(new StringEntity(requestContent, StringUtils.UTF8));
        return client.execute(httpPost);
    }
}
