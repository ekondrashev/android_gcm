package org.notifyme.currency.model;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;

/**
 * Created by ekondrashev on 2/11/15.
 */
public class Pair extends Base implements JSONAware {

    private final String first;
    private final String second;

    public Pair(Long id, String first, String second) {
        super(id);
        this.first = first;
        this.second = second;
    }

    public String first(){
        return first;
    }

    public String second(){
        return second;
    }

    @Override
    public String toJSONString() {
        StringBuffer sb = new StringBuffer();

        sb.append("{");

        sb.append("\"" + JSONObject.escape("first") + "\"");
        sb.append(":");
        sb.append("\"" + JSONObject.escape(first) + "\"");
        sb.append(",");
        sb.append("\"" + JSONObject.escape("second") + "\"");
        sb.append(":");
        sb.append("\"" + JSONObject.escape(second) + "\"");

        sb.append("}");

        return sb.toString();

    }
    public static class Parser extends Base.Parser

    {
        @Override
        public Pair fromJson(String json) throws ParseException {
            JSONParser p = new JSONParser();
            JSONObject o = (JSONObject) p.parse(json);
            Long id = (Long) o.get("id");

            Map<String, Object> map = (Map<String, Object>) o;
            String first = (String) map.get("first");
            String second = (String) map.get("second");
            return new Pair(id, first, second);
        }
    }
}
