package org.rk.json.pojo;

import java.io.IOException;
import java.util.*;

public class JsonMap extends JsonObject {
    
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
    public void toHtml(Appendable destination) throws IOException {
        destination.append("{\\n");
		
		String sep = "";
		for (Map.Entry<JsonObject, JsonObject> i : data.entrySet()) {
			destination.append(sep);
            destination.append("\\n\\t");
            i.getKey().toString(destination);
            destination.append(":");
			i.getValue().toString(destination);
			
			sep = ",";
		}
		
		destination.append("}");

    }

}