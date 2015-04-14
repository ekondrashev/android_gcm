package org.notifyme.currency.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ekondrashev on 2/13/15.
 */
public class Base {

    protected final Long id;

    protected Base(Long id){
        this.id = id;
    }

    public Long id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Base base = (Base) o;

        if (id != null ? !id.equals(base.id) : base.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public static abstract class Parser<T extends Base> {

        public abstract T fromJson(String json) throws ParseException;

        public List<T> collectionFromJson(String json) throws ParseException {
            List<T> result = new LinkedList<>();
            JSONParser p = new JSONParser();
            JSONArray o = (JSONArray) p.parse(json);
            for (Object el : o) {
                result.add(fromJson(((JSONObject)el).toJSONString()));
            }
            return result;
        }
    }
}
