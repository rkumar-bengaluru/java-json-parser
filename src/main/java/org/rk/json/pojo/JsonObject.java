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
    protected String input = "";
    protected boolean isKeyValue = false;

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

    public void setKey(boolean newKey) {
        this.isKey = newKey;
    }

    public void setInput(String in) {
        this.input = in;
    }

    public void setKeyValue(boolean newKeyValue) {
        this.isKeyValue = newKeyValue;
        System.out.println("setting key value " + newKeyValue + ",for String -" + input);
    }

    protected void replaceAndWrite(String fileName,String replaceContent,String formattedString) throws IOException{
        Path path = Paths.get("conf/temp.html");
        Stream <String> lines = Files.lines(path);
        List <String> replaced = lines.map(line -> line.replaceAll("%JSONCONTENT%", replaceContent)).collect(Collectors.toList());
        Files.write(Paths.get(fileName), replaced);
        path = Paths.get(fileName);
        lines = Files.lines(path);
        replaced = lines.map(line -> line.replaceAll("%INPUT%", formattedString)).collect(Collectors.toList());
        Files.write(Paths.get(fileName), replaced);
        // path = Paths.get(fileName);
        // lines = Files.lines(path);
        // replaced = lines.map(line -> line.replaceAll("%INPUT%", input)).collect(Collectors.toList());
        // Files.write(Paths.get(fileName), replaced);
    }

    /**
     */
    abstract public void toString(Appendable destination,int currentLevel) throws IOException ;
    /**
     * Generates an html file with the file name result.html in the current working dir.
     * 
     * @destination where the string buffer is stored.
     * @currentLeve default is -1
     */
    abstract public void toHtml(Appendable destination,int currentLevel) throws IOException ;
   
}