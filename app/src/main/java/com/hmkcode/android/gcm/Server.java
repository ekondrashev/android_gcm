package com.hmkcode.android.gcm;

import com.hmkcode.android.gcm.exception.OperationFailureException;

import org.json.simple.parser.ParseException;
import org.notifyme.currency.model.Device;
import org.notifyme.currency.model.Subscription;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by ekondrashev on 2/12/15.
 */
public class Server {

    private final HttpClient client;


    public Server() throws MalformedURLException {
        this(new HttpClient());
    }
    public Server(HttpClient client){
        this.client = client;
    }

    public List<Subscription> getSubscriptions(Device subscriber) throws  OperationFailureException {
        HttpClient.Result result = null;
        try {
            result = client.get(String.format("/android/%s/subscriptions", subscriber.installationId()));
        } catch (IOException e) {
            throw new OperationFailureException("Failed to perform get request", e);
        }
        if (result.resoponseCode() == 200){

            Subscription.Parser parser = new Subscription.Parser();
            try {
                return parser.collectionFromJson(result.result());
            } catch (ParseException e) {
                throw new OperationFailureException("Failed to parse json");
            }
        }

        throw new OperationFailureException("Got non 200 status code");
    }


}
