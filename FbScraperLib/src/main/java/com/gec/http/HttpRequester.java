package com.gec.http;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

/**
 * Created by Eric on 25/01/15.
 */
public class HttpRequester {
    private Callback callback;

    public HttpRequester(Callback callback) {
        this.callback = callback;
    }

    public void get(String url) {
        if (url != null && !url.isEmpty()) {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

            asyncHttpClient.prepareGet(url).execute(new AsyncCompletionHandler<Response>() {

                @Override
                public Response onCompleted(Response response) throws Exception {
                    // Do something with the Response
                    // ...
                    HttpRequester.this.onCompleted(response);
                    return response;
                }

                @Override
                public void onThrowable(Throwable t) {
                    // Something wrong happened.
                    onError(t);
                }
            });
        }
    }

    public void post(String url, String requestBody) {
        if (url != null && !url.isEmpty()) {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            AsyncHttpClient.BoundRequestBuilder builder = asyncHttpClient.preparePost(url);
            builder.setBody(requestBody);
            builder.execute(new AsyncCompletionHandler<Response>() {

                @Override
                public Response onCompleted(Response response) throws Exception {
                    // Do something with the Response
                    // ...
                    HttpRequester.this.onCompleted(response);
                    return response;
                }

                @Override
                public void onThrowable(Throwable t) {
                    // Something wrong happened.
                    onError(t);
                }
            });
        }
    }

    private void onError(Throwable t) {
        t.printStackTrace();
    }

    private void onCompleted(Response response) {
        if (this.callback != null) this.callback.onHttpCompleted(response);
        else printResponse(response);
    }

    private void printResponse(Response response) {
        System.out.println(response.toString());
    }

    /**
     *
     */
    public interface Callback {
        /**
         *
         * @param response
         */
        public void onHttpCompleted(Response response);

        public void onHtttpError(Throwable t);
    }
}
