package org.notifyme.currency.model;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by ekondrashev on 2/19/15.
 */
public class TestAndroidToastNotification {

    @Test
    public void testFromJson() throws ParseException {

        String json = "{\"subscription\":1,\"polymorphic_ctype\":15,\"id\":1}";
        AndroidToastNotification.Parser p = new AndroidToastNotification.Parser();
        Notification actual = p.fromJson(json);

        AndroidToastNotification expected = new AndroidToastNotification(1l, new Subscription(1l));

        assertEquals(expected, actual);
    }
}
