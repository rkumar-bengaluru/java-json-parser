package org.rk.json.pojo;

import java.util.*;
import java.io.IOException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class JsonMap extends JsonObject {
    static Logger logger = LogManager.getLogger(JsonMap.class);
    private final Map<JsonObject, JsonObject> data = new LinkedHashMap<>();

    public JsonMap(JsonObjType type) {
        super(type);
        if ( type == null) {
			throw new IllegalArgumentException("type  is null.");
		}
        
    }

    public JsonMap put(JsonObject k, JsonObject v) {
        if (k == null) {
			throw new IllegalArgumentException("Key is null.");
		}

        data.put(k, v);
        return this;
    }

    @Override
    public void toString(Appendable destination) throws IOException {
		destination.append("{");
		
		String sep = "";
		for (Map.Entry<JsonObject, JsonObject> i : data.entrySet()) {
			destination.append(sep);
            i.getKey().toString(destination);
            destination.append(":");
			i.getValue().toString(destination);
			
			sep = ",";
		}
		
		destination.append("}");
	}

    @Override
    public void toHtml(Appendable destination,int currentLevel) throws IOException {
        
        if(root) {
            destination.append("<div class=\"json-viewer\"><code class=\"js\" id=\"js\">");
		}
        int size = data.size();

		destination.append("<a class=\"list-link\" href=\"javascript:void(0)\">{");
        destination.append("<span style=\"color: #1d57d4;\"><i onClick=\"spanClicked(event);\" class=\"far fa-minus-square\"></i></span>");
        destination.append("<span style=\"color: #1d57d4;\" class=\"hide\"><i onClick=\"spanClicked(event);\" class=\"fas fa-plus-square\"></i></span>");
        destination.append("<span class=\"hide\"><span onClick=\"spanClicked(event);\" class=\"items-ph\">" + size + " items</span></span>");
        destination.append("</a>");
		String sep = "";
        destination.append("<ul data-level=\"" + ++currentLevel + "\" class=\"type-object\">");
        int j = 0;
		for (Map.Entry<JsonObject, JsonObject> i : data.entrySet()) {
            destination.append("<li>");
            i.getKey().toHtml(destination,currentLevel);
            destination.append("<span class=\"type-colon\">:</span>");
			i.getValue().toHtml(destination,currentLevel);
            logger.debug("j value " + j);
            if(j != (size-1))
                destination.append("<span class=\"type-comma\">" + "," + "</span></li>");
            destination.append("</li>");
            j++;
			sep = ",";
		}
		destination.append("</ul>");
		destination.append("<span class=\"type-symbol\">}</span>");

        if(root) {
            destination.append("</div>");
            replaceAndWrite("result.html",destination.toString());
        }
    }

}