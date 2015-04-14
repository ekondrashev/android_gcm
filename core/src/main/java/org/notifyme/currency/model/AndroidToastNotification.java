package org.notifyme.currency.model;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by ekondrashev on 2/12/15.
 */
public class AndroidToastNotification extends Notification implements JSONAware {

    private final Subscription subscription;

    AndroidToastNotification(Long id, Subscription subscription) {
        super(id);
        this.subscription = subscription;
    }


    @Override
    public String toJSONString() {
        StringBuffer sb = new StringBuffer();

        sb.append("{");

        sb.append("\"" + JSONObject.escape("type") + "\"");
        sb.append(":");
        sb.append("\"" + JSONObject.escape("android_push_notification") + "\"");
        sb.append(",");

        sb.append("\"" + JSONObject.escape("subscription") + "\"");
        sb.append(":");
        sb.append(subscription.toJSONString());

        sb.append("}");

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AndroidToastNotification that = (AndroidToastNotification) o;

        if (subscription != null ? !subscription.equals(that.subscription) : that.subscription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (subscription != null ? subscription.hashCode() : 0);
        return result;
    }

    @Override
    public void apply() {

    }

    @Override
    public NotificationType type() {
        return NotificationType.PUSH;
    }

    public static class Parser extends Notification.Parser {

        @Override
        public Notification fromJson(String json) throws ParseException {
            JSONParser p = new JSONParser();
            JSONObject o = (JSONObject) p.parse(json);
            Long type = (Long) o.get("polymorphic_ctype");
            Long id = (Long) o.get("id");
            return new AndroidToastNotification(id, new Subscription((Long) o.get("subscription")));

        }
    }
}
