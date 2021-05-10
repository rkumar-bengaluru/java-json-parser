
package org.rk.json.parser;
public class RJsonLexer implements RJsonConstants{
    int BRACE_OPEN = 7;
    int BRACE_CLOSE = 8;
    static final long[] jjtoToken = { 0x1ccf8fc1L, };
    public static final String[] jjstrLiteralImages = {
        "", null, null, null, null, null, "\54", "\173", "\175", "\72", "\133", 
        "\135", null, null, null, null, null, null, null, null, null, null, "\47\47", 
        "\42\42", null, null, null, null, null, };

    private RCharStream input_stream;
    protected char curChar;
    int jjmatchedKind;
    int jjmatchedPos;

    int curLexState = 0;

    public RJsonLexer(RCharStream input) {
        this.input_stream = input;
    }

    public RToken getNextToken() {
        RToken matchedToken = null;
        int curPos = 0;

        EOFLoop :
        for(;;) {
            try {     
                curChar = input_stream.beginToken();
                RLogger.debug(RJsonLexer.class, "getNextToken() ->", "\"" + curChar + "\"");
            } catch(java.io.IOException e) {        
                e.printStackTrace();
            }

            jjmatchedKind = 0x7fffffff;
            jjmatchedPos = 0;
            curPos = moveStringLiteralDfa();
            if (jjmatchedKind != 0x7fffffff) {
                 if (jjmatchedPos + 1 < curPos) {
                     input_stream.backup(curPos - jjmatchedPos - 1); // possible backtracking.
                 }
                 if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L) {
                     matchedToken = jjFillToken();
                     return matchedToken;
                 } else {
                     continue EOFLoop;
                 }
            }
            // error begins.....
            int error_line = input_stream.getEndLine();
            int error_column = input_stream.getEndColumn();
            String error_after = null;
            boolean EOFSeen = false;
            try { 
                input_stream.readChar(); 
                input_stream.backup(1); 
            } catch (java.io.IOException e1) { 
                EOFSeen = true;
                error_after = curPos <= 1 ? "" : input_stream.GetImage();
                if (curChar == '\n' || curChar == '\r') {
                    error_line++;
                    error_column = 0;
                } else {
                    error_column++;
                }
            }

            if (!EOFSeen) {
                input_stream.backup(1);
                error_after = curPos <= 1 ? "" : input_stream.GetImage();
            }

            throw new RTokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, RTokenMgrError.LEXICAL_ERROR);
        }
    }

    private int moveStringLiteralDfa() {
        switch(curChar) {
            case 123: // '{'
                return jjStopAtPos(0, RJsonConstants.BRACE_OPEN);
            case 125: // '}'
                return jjStopAtPos(0, RJsonConstants.BRACE_CLOSE);
            case 34: // '"'
                return jjMoveStringLiteralDfa1_0(0x800000L);
            case 39: // '\''
                return jjMoveStringLiteralDfa1_0(0x400000L);
            case 44: // ','
                return jjStopAtPos(0, RJsonConstants.COMMA);
            case 58: // ':'
                return jjStopAtPos(0, RJsonConstants.COLON);
            case 91: // '['
                return jjStopAtPos(0, RJsonConstants.BRACE_OPEN);
            case 93: // ']'
                return jjStopAtPos(0, RJsonConstants.BRACE_CLOSE);
            case 70: // 'F'
            case 102: // 'f'
                return jjMoveStringLiteralDfa1_0(0x40000L);
            case 78: // 'N'
            case 110: // 'n'
                 return jjMoveStringLiteralDfa1_0(0x80000L);
            case 84: // 'T'
            case 116: // 't'
                return jjMoveStringLiteralDfa1_0(0x20000L);
            default:
                return -1;
        }
    }

    private int jjStopAtPos(int pos, int kind) {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        return pos + 1;
    }

    protected RToken jjFillToken() {
        final RToken t;
        final String tokenImage;
        final int beginLine;
        final int endLine;
        final int beginColumn;
        final int endColumn;
        String im = jjstrLiteralImages[jjmatchedKind];
        tokenImage = (im == null) ? input_stream.GetImage() : im;
        beginLine = input_stream.getBeginLine();
        beginColumn = input_stream.getBeginColumn();
        endLine = input_stream.getEndLine();
        endColumn = input_stream.getEndColumn();
        t = RToken.newToken(jjmatchedKind, tokenImage);
        t.beginLine = beginLine;
        t.endLine = endLine;
        t.beginColumn = beginColumn;
        t.endColumn = endColumn;
        return t;
    }

    private int jjMoveStringLiteralDfa1_0(long active0) {
        try { 
             curChar = input_stream.readChar(); 
        } catch(java.io.IOException e) {
            //jjStopStringLiteralDfa_0(0, active0);
            return 1;
        }
        return -1;
    }
}