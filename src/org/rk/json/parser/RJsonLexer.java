
package org.rk.json.parser;

public class RJsonLexer extends NumberLexer implements RJsonConstants {

    public RJsonLexer(RCharStream input) {
        super(input);
    }

    public RToken getNextToken() {
        RToken matchedToken = null;
        int curPos = 0;

        EOFLoop :
        for(;;) {
            try {     
                curChar = input_stream.BeginToken();
            } catch(java.io.IOException e) {        
                jjmatchedKind = 0;
                matchedToken = fillToken();
                return matchedToken;
            }

            jjmatchedKind = 0x7fffffff;
            jjmatchedPos = 0;
            curPos = analyzeCurrentCharacter();
            //RLogger.debug(RJsonLexer.class, "jjmatchedKind()", "jjmatchedKind" + jjmatchedKind );
            if (jjmatchedKind != 0x7fffffff) {
                 if (jjmatchedPos + 1 < curPos) {
                     input_stream.backup(curPos - jjmatchedPos - 1); // possible backtracking.
                 }
                 if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L) {
                     matchedToken = fillToken();
                     RLogger.debug(RJsonParser.class,"getNextToken()::Matched",matchedToken.toString());
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


    protected int analyzeCurrentCharacter() {
        switch(curChar) {
            case 123: // '{'
                return stopAtPos(0, RJsonConstants.BRACE_OPEN);
            case 125: // '}'
                return stopAtPos(0, RJsonConstants.BRACE_CLOSE);
            case 34: // '"'
                return moveChar01(0x800000L);
            case 39: // '\''
                return moveChar01(0x40000L);
            case 44: // ','
                return stopAtPos(0, RJsonConstants.COMMA);
            case 58: // ':'
                return stopAtPos(0, RJsonConstants.COLON);
            case 91: // '['
                return stopAtPos(0, RJsonConstants.BRACKET_OPEN);
            case 93: // ']'
                return stopAtPos(0, RJsonConstants.BRACKET_CLOSE);
            case 70: // 'F'
            case 102: // 'f'
                return moveChar01(0x40000L);
            case 78: // 'N'
            case 110: // 'n'
                 return moveChar01(0x80000L);
            case 84: // 'T'
            case 116: // 't'
                return moveChar01(0x20000L);
            default:
                return findInteger(0,0);
        }
    }




    public void ReInit(RCharStream stream)
{
//    jjmatchedPos = jjnewStateCnt = 0;
//    curLexState = defaultLexState;
//    input_stream = stream;
   //ReInitRounds();
}
}