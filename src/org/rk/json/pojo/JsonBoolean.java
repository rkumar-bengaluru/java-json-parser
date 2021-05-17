package org.rk.json.pojo;

import java.io.IOException;

public class JsonBoolean extends JsonObject {
    
    private Boolean value;

    public JsonBoolean(JsonObjType type, Boolean value) {
        super(type);
        if (value == null || type == null) {
			throw new IllegalArgumentException("type || value is null.");
		}
        
        this.value = value;
    }
    @Override
    public void toString(Appendable destination) throws IOException {
        destination.append(value.toString());
    }
    @Override
    public void toHtml(Appendable destination) throws IOException {
        destination.append("<span class=\"boolean\">");
        destination.append(value.toString());
        destination.append("</span>");
    }

}