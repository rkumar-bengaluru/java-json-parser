package org.rk.json.pojo;

import java.io.IOException;

public class JsonInteger extends JsonObject {
    
    private Double value;

    public JsonInteger(JsonObjType type, Double value) {
        super(type);
        if (value == null || type == null) {
			throw new IllegalArgumentException("type || value is null.");
		}
        
        this.value = value;
    }
    @Override
    public void toString(Appendable destination,int currentLevel) throws IOException {
        destination.append(value.toString());
    }
    @Override
    public void toHtml(Appendable destination,int currentLevel) throws IOException {
        destination.append("<span class=\"type-number\">");
        destination.append(value.toString());
        destination.append("</span>");
    }

}