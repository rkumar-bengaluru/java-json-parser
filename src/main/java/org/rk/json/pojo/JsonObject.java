package org.rk.json.pojo;

import java.io.IOException;

public abstract class JsonObject {
    private JsonObjType type;

    public JsonObject(JsonObjType type) {
        this.type = type;
    }

    JsonObjType type() {
        return type;
    }

    /**
     */
    abstract public void toString(Appendable destination) throws IOException ;
    /**
     */
    abstract public void toHtml(Appendable destination) throws IOException ;
}