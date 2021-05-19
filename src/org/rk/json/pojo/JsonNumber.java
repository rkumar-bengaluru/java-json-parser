package org.rk.json.pojo;

import java.io.IOException;

public class JsonNumber extends JsonObject {
    
    private String value;

    public JsonNumber(JsonObjType type, String value) {
        super(type);
        if (value == null || type == null) {
			throw new IllegalArgumentException("type || value is null.");
		}
        
        this.value = value;
    }
    @Override
    public void toString(Appendable destination) throws IOException {
        destination.append(value);
    }
    @Override
    public void toHtml(Appendable destination) throws IOException {
        destination.append("<span class=\"integer\">");
        destination.append(value);
        destination.append("</span>");
    }

}