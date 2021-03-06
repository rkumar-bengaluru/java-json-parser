package org.rk.json.parser;

import org.rk.json.pojo.*;

@SuppressWarnings("all") // Ignore warnings in generated code
public class RJsonParser implements RJsonConstants {

    private boolean nativeNumbers = false;
    /** Generated Token Manager. */
    public RJsonLexer token_source;
    RJsonCharStream jj_input_stream;
    /** Current token. */
    public RJsonToken token;
    /** Next token. */
    public RJsonToken jj_nt;
    private int jj_gen;
    final private int[] jj_la1 = new int[13];
    static private int[] jj_la1_0;
    static {
        jj_la1_init_0();
    }

    private static void jj_la1_init_0() {
        jj_la1_0 = new int[] {0xccf8480,0x78000,0x1ccf8000,0x40,0x1ccf8000,0x40,0xccf8480,0xccf8000,0x60000,0x18000,0xcc00000,0x8800000,0x4400000,};
    }

    private String input = null;
    /** Constructor. */
    public RJsonParser(String input) {
        this(new java.io.StringReader(input));
        this.input = input;
    }

    /** Constructor. */
    public RJsonParser(java.io.Reader stream) {
        jj_input_stream = new RJsonCharStream(stream, 1, 1);
        token_source = new RJsonLexer(jj_input_stream);
        
        token = new RJsonToken();
        token.next = jj_nt = token_source.getNextToken();
        jj_gen = 0;
        for (int i = 0; i < 13; i++) jj_la1[i] = -1;
    }

    /** Constructor with generated Token Manager. */
    public RJsonParser(RJsonLexer tm) {
        token_source = tm;
        token = new RJsonToken();
        token.next = jj_nt = token_source.getNextToken();
        jj_gen = 0;
        for (int i = 0; i < 13; i++) jj_la1[i] = -1;
    }
    /** Constructor with InputStream. */
      public RJsonParser(java.io.InputStream stream) {
        this(stream, null);
      }
      /** Constructor with InputStream and supplied encoding */
      public RJsonParser(java.io.InputStream stream, String encoding) {
        try { jj_input_stream = new RJsonCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
        token_source = new RJsonLexer(jj_input_stream);
        token = new RJsonToken();
        token.next = jj_nt = token_source.getNextToken();
        jj_gen = 0;
        for (int i = 0; i < 13; i++) jj_la1[i] = -1;
      }

    /**
     * Parses a JSON object into a Java {@code Map}.
     */
    public JsonMap parseObject() throws RJsonException {
        JsonMap toReturn = object();
        if (!ensureEOF()) {
            throw new IllegalStateException("parser.expectedEOF");
        }
        return toReturn;
    }

    /**
     * Parses a JSON array into a Java {@code List}.
     */
    public JsonList parseArray() throws RJsonException {
        JsonList toReturn = list();
        if (!ensureEOF()) {
            throw new IllegalStateException("parser.expectedEOF");
        }
        return toReturn;
    }

    /**
     * Parses any JSON-parsable object, returning the value.
     */
    public JsonObject parse() throws RJsonException {
      //RLogger.getLogger(RJsonParser.class).info("parse()" + "");
        JsonObject toReturn = anything();
        toReturn.setRoot();
        toReturn.setInput(input);
        if (!ensureEOF()) {
            throw new IllegalStateException("parser.expectedEOF");
        }
        return toReturn;
    }

    private static String substringBefore(String str, char delim) {
        int pos = str.indexOf(delim);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    public void setNativeNumbers(boolean value) {
        this.nativeNumbers = value;
    }

    public boolean getNativeNumbers() {
        return this.nativeNumbers;
    }

  final public boolean ensureEOF() throws RJsonException {
    jj_consume_token(0);
      {if (true) return true;}
    throw new Error("Missing return statement in function");
  }

  final public JsonObject anything() throws RJsonException {
    //RLogger.getLogger(RJsonParser.class).info("anything()" + jj_nt.toString());
    
    JsonObject x;
    switch (jj_nt.kind) {
    case BRACE_OPEN:
      x = object();
      break;
    case BRACKET_OPEN:
      x = list();
      break;
    case NUMBER_INTEGER:
    case NUMBER_DECIMAL:
    case TRUE:
    case FALSE:
    case NULL:
    case STRING_SINGLE_EMPTY:
    case STRING_DOUBLE_EMPTY:
    case STRING_SINGLE_NONEMPTY:
    case STRING_DOUBLE_NONEMPTY:
      x = value();
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new RJsonException();
    }
      {if (true) return x;}
    throw new Error("Missing return statement in function");
  }

  final public JsonObject objectKey() throws RJsonException {
    JsonObject o;
    JsonObject key;
    switch (jj_nt.kind) {
    case STRING_SINGLE_EMPTY:
    case STRING_DOUBLE_EMPTY:
    case STRING_SINGLE_NONEMPTY:
    case STRING_DOUBLE_NONEMPTY:
      key = string();
      break;
    case SYMBOL:
      key = symbol();
      break;
    case NULL:
      nullValue();
          key = null;
      break;
    case NUMBER_INTEGER:
    case NUMBER_DECIMAL:
    case TRUE:
    case FALSE:
      switch (jj_nt.kind) {
      case TRUE:
      case FALSE:
        o = booleanValue();
        break;
      case NUMBER_INTEGER:
      case NUMBER_DECIMAL:
        o = number();
        break;
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new RJsonException();
      }
              key = o;
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new RJsonException();
    }
      {if (true) {key.setKey();return key;}}
    throw new Error("Missing return statement in function");
  }

  final public JsonMap object() throws RJsonException {
    final JsonMap map = new JsonMap(JsonObjType.MAP);
    JsonObject key;
    JsonObject value;
    jj_consume_token(BRACE_OPEN);
    //RLogger.getLogger(RJsonParser.class).info("object()" + jj_nt.kind);
    switch (jj_nt.kind) {
    case NUMBER_INTEGER:
    case NUMBER_DECIMAL:
    case TRUE:
    case FALSE:
    case NULL:
    case STRING_SINGLE_EMPTY:
    case STRING_DOUBLE_EMPTY:
    case STRING_SINGLE_NONEMPTY:
    case STRING_DOUBLE_NONEMPTY:
    case SYMBOL:
      key = objectKey();
      jj_consume_token(COLON);
      value = anything();
      value.setKeyValue(true);
          map.put(key, value);
          key = null; value = null;
      label_1:
      while (true) {
        switch (jj_nt.kind) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[3] = jj_gen;
          break label_1;
        }
        jj_consume_token(COMMA);
        key = objectKey();
        jj_consume_token(COLON);
        value = anything();
        value.setKeyValue(true);
              map.put(key, value);
              key = null; value = null;
      }
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
    jj_consume_token(BRACE_CLOSE);
      {if (true) return map;}
    throw new Error("Missing return statement in function");
  }

  final public JsonList list() throws RJsonException {
    final JsonList list = new JsonList(JsonObjType.LIST);
    //RLogger.getLogger(RJsonParser.class).info("list()" + jj_nt.toString());
    JsonObject value;
    jj_consume_token(BRACKET_OPEN);
    switch (jj_nt.kind) {
    case BRACE_OPEN:
    case BRACKET_OPEN:
    case NUMBER_INTEGER:
    case NUMBER_DECIMAL:
    case TRUE:
    case FALSE:
    case NULL:
    case STRING_SINGLE_EMPTY:
    case STRING_DOUBLE_EMPTY:
    case STRING_SINGLE_NONEMPTY:
    case STRING_DOUBLE_NONEMPTY:
      value = anything();
          list.add(value);
          value = null;
      label_2:
      while (true) {
        switch (jj_nt.kind) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[5] = jj_gen;
          break label_2;
        }
        jj_consume_token(COMMA);
        value = anything();
              list.add(value);
              value = null;
      }
      break;
    default:
      jj_la1[6] = jj_gen;
      ;
    }
    jj_consume_token(BRACKET_CLOSE);
        list.trimToSize();
        {if (true) return list;}
    throw new Error("Missing return statement in function");
  }

  final public JsonObject value() throws RJsonException {
    JsonObject x;
    switch (jj_nt.kind) {
    case STRING_SINGLE_EMPTY:
    case STRING_DOUBLE_EMPTY:
    case STRING_SINGLE_NONEMPTY:
    case STRING_DOUBLE_NONEMPTY:
      x = string();
      break;
    case NUMBER_INTEGER:
    case NUMBER_DECIMAL:
      x = number();
      break;
    case TRUE:
    case FALSE:
      x = booleanValue();
      break;
    case NULL:
      x = nullValue();
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new RJsonException();
    }
      {if (true) return x;}
    throw new Error("Missing return statement in function");
  }

  final public JsonNull nullValue() throws RJsonException {
    jj_consume_token(NULL);
      {if (true) return new JsonNull(JsonObjType.NULL);}
    throw new Error("Missing return statement in function");
  }

  final public JsonBoolean booleanValue() throws RJsonException {
    Boolean b;
    switch (jj_nt.kind) {
    case TRUE:
      jj_consume_token(TRUE);
              b = Boolean.TRUE;
      break;
    case FALSE:
      jj_consume_token(FALSE);
              b = Boolean.FALSE;
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new RJsonException();
    }
      {if (true) return new JsonBoolean(JsonObjType.BOOLEAN,b);}
    throw new Error("Missing return statement in function");
  }

  final public JsonNumber number() throws RJsonException {
    RJsonToken t;
    switch (jj_nt.kind) {
    case NUMBER_DECIMAL:
      t = jj_consume_token(NUMBER_DECIMAL);
            if (nativeNumbers) {
                {if (true) return new JsonNumber(JsonObjType.NUMBER, t.image);}
            } else {
                {if (true) return new JsonNumber(JsonObjType.NUMBER, t.image);}
            }
      break;
    case NUMBER_INTEGER:
      t = jj_consume_token(NUMBER_INTEGER);
            if (nativeNumbers) {
                {if (true) return new JsonNumber(JsonObjType.NUMBER, t.image);}
            } else {
                {if (true) return new JsonNumber(JsonObjType.NUMBER, t.image);}
            }
      break;
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new RJsonException();
    }
    throw new Error("Missing return statement in function");
  }

  final public JsonObject string() throws RJsonException {
    JsonObject s;
    switch (jj_nt.kind) {
    case STRING_DOUBLE_EMPTY:
    case STRING_DOUBLE_NONEMPTY:
      s = doubleQuoteString();
      break;
    case STRING_SINGLE_EMPTY:
    case STRING_SINGLE_NONEMPTY:
      s = singleQuoteString();
      break;
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new RJsonException();
    }
      {if (true) return s;}
    throw new Error("Missing return statement in function");
  }

  final public JsonString doubleQuoteString() throws RJsonException {
    switch (jj_nt.kind) {
    case STRING_DOUBLE_EMPTY:
      jj_consume_token(STRING_DOUBLE_EMPTY);
          {if (true) return new JsonString(JsonObjType.STRING, "");}
      break;
    case STRING_DOUBLE_NONEMPTY:
      jj_consume_token(STRING_DOUBLE_NONEMPTY);
            String image = token.image;
            {if (true) return new  JsonString(JsonObjType.STRING, image.substring(1, image.length() - 1));}
      break;
    default:
      jj_la1[11] = jj_gen;
      jj_consume_token(-1);
      throw new RJsonException();
    }
    throw new Error("Missing return statement in function");
  }

  final public JsonString singleQuoteString() throws RJsonException {
    switch (jj_nt.kind) {
    case STRING_SINGLE_EMPTY:
      jj_consume_token(STRING_SINGLE_EMPTY);
          {if (true) return new JsonString(JsonObjType.STRING, "");}
      break;
    case STRING_SINGLE_NONEMPTY:
      jj_consume_token(STRING_SINGLE_NONEMPTY);
            String image = token.image;
            {if (true) return new JsonString(JsonObjType.STRING, image.substring(1, image.length() - 1)) ;}
      break;
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new RJsonException();
    }
    throw new Error("Missing return statement in function");
  }

  final public JsonString symbol() throws RJsonException {
    jj_consume_token(SYMBOL);
      {if (true) return new JsonString(JsonObjType.STRING, token.image); }
    throw new Error("Missing return statement in function");
  }

  
  /** Reinitialise. */
  public void ReInit(RJsonLexer tm) {
    token_source = tm;
    token = new RJsonToken();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  private RJsonToken jj_consume_token(int kind) throws RJsonException {
    RJsonToken oldToken = token;
    if ((token = jj_nt).next != null) jj_nt = jj_nt.next;
    else jj_nt = jj_nt.next = token_source.getNextToken();
    //RLogger.debug(RJsonParser.class,"jj_consume_token",token.kind + "," + kind);
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    jj_nt = token;
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public RJsonToken getNextToken() {
    if ((token = jj_nt).next != null) jj_nt = jj_nt.next;
    else jj_nt = jj_nt.next = token_source.getNextToken();
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public RJsonToken getToken(int index) {
    RJsonToken t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private java.util.List jj_expentries = new java.util.ArrayList();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public RJsonException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[29];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 13; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 29; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.get(i);
    }
    return new RJsonException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
