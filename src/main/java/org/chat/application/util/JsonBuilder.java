package org.chat.application.util;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.chat.application.constants.AppConstants;
import org.json.JSONObject;

import java.util.Collection;
import java.util.List;

/**
 * @author Saransh Gupta
 */
public class JsonBuilder {

    private final String MESSAGE_TYPE = "MESSAGE_TYPE";

    private final String USERNAME = "USERNAME";
    private final String MESSAGE = "MESSAGE";

    private JSONObject jsonObj;

    public JsonBuilder() {
         jsonObj = new JSONObject();
    }

    public JsonBuilder withMessageType(AppConstants.MessageType messageType)
    {
        jsonObj.put(MESSAGE_TYPE, messageType.toString());
        return this;
    }

    public JsonBuilder withUserName(String userName)
    {
        jsonObj.put(USERNAME, userName);
        return this;
    }

    public JsonBuilder withMessage(String message)
    {
        jsonObj.put(MESSAGE, message);
        return this;
    }

    public JsonBuilder withAttribute(String key, String value)
    {
        jsonObj.put(key, value);
        return this;
    }

    public JsonBuilder withAttribute(String key, List<String> list)
    {
        if(list.size()>0)
        {
            jsonObj.put(key, list);
        }

        return this;
    }

    public String build()
    {
        return jsonObj.toString();
    }
}
