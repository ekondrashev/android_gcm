package org.notifyme.currency.model;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;

/**
 * Created by ekondrashev on 2/11/15.
 */
public class Subscription extends Base implements JSONAware {

    private final Pair pair;
    private final Subscriber subscriber;

    public Subscription(Long id){
        this(id, null, null);
    }

    public Subscription(Long id, Pair pair, Subscriber subscriber) {
        super(id);
        this.pair = pair;
        this.subscriber = subscriber;
    }

    @Override
    public String toJSONString() {
        StringBuffer sb = new StringBuffer();

        sb.append("{");

        sb.append("\"" + JSONObject.escape("pair") + "\"");
        sb.append(":");
        sb.append(pair.toJSONString());

        sb.append("}");

        return sb.toString();

    }

    public static class Parser extends Base.Parser

    {
        @Override
        public Subscription fromJson(String json) throws ParseException {
            JSONParser p = new JSONParser();
            JSONObject o = (JSONObject) p.parse(json);
            Long id = (Long) o.get("id");

            Map<String, Object> map = (Map<String, Object>) o;
            Pair pair = (new Pair.Parser()).fromJson(((JSONObject) map.get("pair")).toJSONString());
            Device device = (new Device.Parser()).fromJson(((JSONObject) map.get("subscriber")).toJSONString());
            return new Subscription(id, pair, device);
        }
    }
}
