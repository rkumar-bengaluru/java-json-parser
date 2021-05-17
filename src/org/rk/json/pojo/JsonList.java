package org.rk.json.pojo;

import java.io.IOException;
import java.util.*;

public class JsonList extends JsonObject {
    //final java.util.ArrayList<Object> data = new java.util.ArrayList<Object>();
    private final ArrayList<JsonObject> data = new ArrayList<>();

    public JsonList(JsonObjType type) {
        super(type);
        if ( type == null) {
			throw new IllegalArgumentException("type  is null.");
		}
        
    }

    public void trimToSize() {
        data.trimToSize();
    }

    public JsonList add(JsonObject e) {
		data.add(e);
		
		return this;
	}

    @Override
    public void toString(Appendable destination) throws IOException {
		destination.append("[");
		
		String sep = "";
		for (JsonObject i : data) {
			destination.append(sep);
			i.toString(destination);
			
			sep = ",";
		}
		
		destination.append("]");
	}

    @Override
    public void toHtml(Appendable destination) throws IOException {
        destination.append("[");
		
		String sep = "";
		for (JsonObject i : data) {
			destination.append(sep);
			i.toString(destination);
			
			sep = ",";
		}
		
		destination.append("]");

    }

}