
package org.rk.json.parser;

class RJsonParser implements RJsonConstants {
    
    RCharStream jj_input_stream;
    public RToken jj_nt;
    private int jj_gen;
    private int jj_kind = -1;

    public RToken token;
    public RJsonLexer token_source;

    final private int[] jj_la1 = new int[13];
    
    public RJsonParser(String input) {
        this(new java.io.StringReader(input));
    }

    public RJsonParser(java.io.Reader stream) {
        RLogger.debug(RJsonParser.class, "RJsonParser(java.io.Reader stream) - ");
        jj_input_stream = new RCharStream(stream, 1, 1);
        token_source = new RJsonLexer(jj_input_stream);
        token = new RToken();
        token.next = jj_nt = token_source.getNextToken();
        jj_gen = 0;
        for (int i = 0; i < 13; i++) jj_la1[i] = -1;
    }

     public Object parse() throws RJsonException {
        Object toReturn = anything();
        if (!ensureEOF()) {
            throw new IllegalStateException("parser.expectedEOF");
        }
        return toReturn;
    }

    final public Object anything() throws RJsonException {
        Object x = null;
        switch (jj_nt.kind) {
            case BRACE_OPEN:
                RLogger.debug(RJsonParser.class,"found  BRACE_OPEN " + jj_nt);
                x = object();
                break;
        }

        return x;
    }


    final public java.util.LinkedHashMap<String, Object> object() throws RJsonException {
        final java.util.LinkedHashMap<String, Object> map = new java.util.LinkedHashMap<String, Object>();
        String key;
        Object value;
        jj_consume_token(BRACE_OPEN);
        RLogger.debug(RJsonParser.class,"object()", "jj_nt " + jj_nt + ", jj_nt.kind " + jj_nt.kind);
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
                key = findKey();
                jj_consume_token(COLON);
                value = anything();
                map.put(key, value);
                key = null; value = null;
        }
        jj_consume_token(BRACE_CLOSE);
        {if (true) return map;}
        throw new Error("Missing return statement in function");
    }

    final public String findKey() throws RJsonException {
        Object o;
        String key = null;
        switch (jj_nt.kind) {
            case STRING_SINGLE_EMPTY:
            case STRING_DOUBLE_EMPTY:
            case STRING_SINGLE_NONEMPTY:
            case STRING_DOUBLE_NONEMPTY:
                RLogger.debug(RJsonParser.class,"ffffffffffffffffffffff");
                //key = string();
                break;
        }
        return key;
    }

    final public boolean ensureEOF() throws RJsonException {
        jj_consume_token(0);
        {if (true) return true;}
        throw new Error("Missing return statement in function");
    }

    private void getNextTokenPre(String methodName) {
        //debug(methodName + " - token - " + token + ", token.next - " + token.next + ", jj_nt - " + jj_nt + ", jj_nt.next - " + jj_nt.next);
        if ((token = jj_nt).next != null) jj_nt = jj_nt.next;
        else jj_nt = jj_nt.next = token_source.getNextToken();
        //debug(methodName + " - token - " + token + ", token.next - " + token.next + ", jj_nt - " + jj_nt + ", jj_nt.next - " + jj_nt.next);
    }

    final public RToken getNextToken() {
        getNextTokenPre("getNextToken()");
        jj_gen++;
        return token;
    }

    private RToken jj_consume_token(int kind) throws RJsonException {
        RToken oldToken = token;
        getNextTokenPre("jj_consume_token(int kind");

        if (token.kind == kind) {
            jj_gen++;
            return token;
        }

        jj_nt = token;
        token = oldToken;
        jj_kind = kind;
        throw generateParseException();
  }

    private java.util.List jj_expentries = new java.util.ArrayList();
    private int[] jj_expentry;
    static private int[] jj_la1_0 = new int[] {0xccf8480,0x78000,0x1ccf8000,0x40,0x1ccf8000,0x40,0xccf8480,0xccf8000,0x60000,0x18000,0xcc00000,0x8800000,0x4400000,};
    
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
}