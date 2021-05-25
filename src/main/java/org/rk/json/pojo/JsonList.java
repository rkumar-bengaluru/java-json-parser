package org.rk.json.pojo;

import java.io.IOException;
import java.util.*;

public class JsonList extends JsonObject {
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
    public void toHtml(Appendable destination,int currentLevel) throws IOException {

		if(root) {
            destination.append("<div class=\"json-viewer\"><code class=\"js\" id=\"js\">");
		}
		int size = data.size();

		destination.append("<a class=\"list-link\" href=\"javascript:void(0)\">[");
        destination.append("<span style=\"color: #1d57d4;\"><i onClick=\"spanClicked(event);\" class=\"far fa-minus-square\"></i></span>");
        destination.append("<span style=\"color: #1d57d4;\" class=\"hide\"><i onClick=\"spanClicked(event);\" class=\"fas fa-plus-square\"></i></span>");
        destination.append("<span class=\"hide\"><span onClick=\"spanClicked(event);\" class=\"items-ph\">" + size + " items</span></span>");
        destination.append("</a>");

		String sep = "";
		int j = 0;
		destination.append("<ul data-level=\"" + ++currentLevel + "\" class=\"type-array\">");
		for (JsonObject i : data) {
			destination.append("<li>");
			i.toHtml(destination,currentLevel);
			if(j != (size-1))
                destination.append("<span class=\"type-comma\">" + "," + "</span></li>");
			destination.append("</li>");
			sep = ",";
			j++;
		}
		destination.append("</ul>");
		destination.append("<span class=\"type-symbol\">]</span>");
		if(root) {
            destination.append("</div>");
            replaceAndWrite("result.html",destination.toString());
        }

    }

}