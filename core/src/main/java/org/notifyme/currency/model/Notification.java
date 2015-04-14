package org.notifyme.currency.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by ekondrashev on 2/18/15.
 */
public abstract class Notification extends Base {
    protected Notification(Long id) {
        super(id);
    }

    public abstract NotificationType type();
    public abstract void apply();

    public static class Parser extends Base.Parser<Notification> {

        public Notification fromJson(String json) throws ParseException {
            JSONParser p = new JSONParser();
            JSONObject o = (JSONObject) p.parse(json);
            Long type = (Long) o.get("polymorphic_ctype");
            return NotificationType.byType(type).parser().fromJson(json);
        }
    }
}
