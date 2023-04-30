package it.polimi.softeng.JSONParser;

import org.json.simple.JSONObject;

public class RequestParser {
    static class Request{
        String request;
        String requester;

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }

        public String getRequester() {
            return requester;
        }

        public void setRequester(String requester) {
            this.requester = requester;
        }
    }

    /**
     *
     * @param message contains request in JSONObject form
     * @return a request containing requester and request in String form
     */
    public Request requestParser (JSONObject message)
    {
        Request req = new Request();
        req.setRequest(message.get("request").toString());
        req.setRequester(message.get("requester").toString());

        return req;
    }
}
