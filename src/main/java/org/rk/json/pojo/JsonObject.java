package org.rk.json.pojo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class JsonObject {
    
    private JsonObjType type;
    protected boolean root = false;
    protected boolean isKey = false;

    public JsonObject(JsonObjType type) {
        this.type = type;
    }

    JsonObjType type() {
        return type;
    }

    public void setRoot() {
        this.root = true;
    }

    public void setKey() {
        this.isKey = true;
    }

    protected void replaceAndWrite(String fileName,String replaceContent) throws IOException{
        Path path = Paths.get("conf/temp.html");
        Stream <String> lines = Files.lines(path);
        List <String> replaced = lines.map(line -> line.replaceAll("%JSONCONTENT%", replaceContent)).collect(Collectors.toList());
        Files.write(Paths.get(fileName), replaced);
    }

    /**
     */
    abstract public void toString(Appendable destination) throws IOException ;
    /**
     */
    abstract public void toHtml(Appendable destination,int level) throws IOException ;
}