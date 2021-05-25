package org.rk.json.pojo;

import java.io.IOException;

public class JsonNull extends JsonObject {
    
    public JsonNull(JsonObjType type) {
        super(type);
        if ( type == null) {
			throw new IllegalArgumentException("type  is null.");
		}
        
    }

    public void toString(Appendable destination,int currentLevel) throws IOException {
        destination.append("null");
    }

    public void toHtml(Appendable destination,int currentLevel) throws IOException {
        destination.append("<span class=\"null\">");
        destination.append("null");
        destination.append("</span>");
    }

}