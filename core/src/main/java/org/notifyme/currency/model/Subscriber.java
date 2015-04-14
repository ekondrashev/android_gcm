package org.notifyme.currency.model;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

/**
 * Created by ekondrashev on 2/11/15.
 */
public abstract class Subscriber extends Base implements JSONAware {

    protected Subscriber(Long id){
        super(id);
    }

    @Override
    public abstract String toJSONString();
}
