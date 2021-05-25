package org.rk.json.pojo;

import java.io.IOException;

public class JsonString extends JsonObject {
    
    private String value;

    public JsonString(JsonObjType type, String value) {
        super(type);
        if (value == null || type == null) {
			throw new IllegalArgumentException("type || value is null.");
		}
        
        this.value = value;
    }
    @Override
    public void toString(Appendable destination) throws IOException {
        destination.append("\"");
        destination.append(value);
        destination.append("\"");
    }
    @Override
    public void toHtml(Appendable destination,int currentLevel) throws IOException {
        if(isKey) {
            destination.append("<span class=\"type-key\">\"");
        }else  {
            destination.append("<span class=\"type-string\">\"");
        }
        
        destination.append(value);
        destination.append("\"</span>");
    }

}