package org.rk.json.parser;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class AbstractJsonLexer {
    static Logger logger = LogManager.getLogger(AbstractJsonLexer.class);
    private static final String[] strLiteralImages = {
        "", null, null, null, null, null, "\54", "\173", "\175", "\72", "\133", 
        "\135", null, null, null, null, null, null, null, null, null, null, "\47\47", 
        "\42\42", null, null, null, null, null, };
    static final long[] toToken = { 0x1ccf8fc1L, };
    protected RJsonCharStream input_stream;
    protected char curChar;
    protected int matchedKind;
    protected int matchedPos;
    
    public AbstractJsonLexer(RJsonCharStream input) {
        this.input_stream = input;
    }

    protected void checkForSpace() {
        //RLogger.getLogger(AbstractJsonLexer.class).info("checkForSpace" + curChar);
        if(curChar == 32) {
            try { 
             curChar = input_stream.readChar(); 
            } catch(java.io.IOException e) {
            }
            if(curChar != 32) {
                input_stream.backup(1);
                return;
            } else {
                checkForSpace();
            }
        }
        //RLogger.getLogger(AbstractJsonLexer.class).info("checkForSpace" + curChar);
    }

    protected RJsonToken fillToken() {
        final RJsonToken t;
        final String tokenImage;
        final int beginLine;
        final int endLine;
        final int beginColumn;
        final int endColumn;
        String im = strLiteralImages[matchedKind];
        tokenImage = (im == null) ? input_stream.getImage() : im;
        beginLine = input_stream.getBeginLine();
        beginColumn = input_stream.getBeginColumn();
        endLine = input_stream.getEndLine();
        endColumn = input_stream.getEndColumn();
        t = RJsonToken.newToken(matchedKind, tokenImage);
        t.beginLine = beginLine;
        t.endLine = endLine;
        t.beginColumn = beginColumn;
        t.endColumn = endColumn;
        return t;
    }

    protected int findStringLiteral(int curPos, long active0) {
        logger.debug("finding string literal");
        int kind = 0x7fffffff;
        try { 
             curChar = input_stream.readChar(); 
        } catch(java.io.IOException e) {
            return 1;
        }
        
        for (;;) {
            logger.debug("findStringLiteral() = " + (int)curChar);
            switch(curChar) {
                case 34: // '"'
                    logger.debug("findStringLiteral() = " + (int)curChar);
                    kind = RJsonConstants.STRING_DOUBLE_NONEMPTY;
                    break;
                default:
                    break;
            }
            ++curPos;
            
            if (kind != 0x7fffffff) {
                matchedKind = kind;
                matchedPos = curPos;
                logger.debug("returning string literal () = " + curPos);
                return curPos;
            }
            
            try {
                curChar = input_stream.readChar();
            } catch (java.io.IOException e) {
                return curPos;
            }
            
        }
        
    }

    protected int moveChar(int curPos,char target) {
        int kind = 0x7fffffff;
        try { 
             curChar = input_stream.readChar(); 
        } catch(java.io.IOException e) {
            return 1;
        }

        for(;;) {
            
            if (curChar == '\n' || curChar == '\r' || curChar == '\f') {
                kind = RJsonConstants.C_SINGLE_COMMENT;
            }

            if(curChar == 42) {
                // lookahead for char '/'
                try { 
                    curChar = input_stream.readChar(); 
                } catch(java.io.IOException e) {
                    return 1;
                }
                        
                if(curChar == 47) {
                    ++curPos;
                    kind = RJsonConstants.C_MULTILINE_COMMENT;
                } else {
                    input_stream.backup(1);
                }
            }
            
            ++curPos;
            if (kind != 0x7fffffff) {
                matchedKind = kind;
                matchedPos = curPos;
                return curPos;
            }

            try {
                curChar = input_stream.readChar();
            } catch (java.io.IOException e) {
                return curPos;
            }
        }
    }

    protected int moveChar01(long active0) {
        try { 
             curChar = input_stream.readChar(); 
        } catch(java.io.IOException e) {
            return 1;
        }
        logger.debug("moveChar01::curChar=" + curChar);
        switch(curChar) {
            case 34: // '"'
                if ((active0 & 0x800000L) != 0L)
                    return stopAtPos(1, RJsonConstants.STRING_DOUBLE_EMPTY);
                break;
            case 39: // '\''
                if ((active0 & 0x400000L) != 0L)
                    return stopAtPos(1, RJsonConstants.STRING_SINGLE_EMPTY);
                break;
            case 65: // 'A'
            case 97: // 'a'
                if ((active0 & 0x40000L) != 0L)
                    return moveChar02(active0, 0x40000L);
                break;
            case 82: // 'R'
            case 114: // 'r'
                if ((active0 & 0x20000L) != 0L)
                    return moveChar02(active0, 0x20000L);
                break;
            case 85: // 'U'
            case 117: // 'u'
                if ((active0 & 0x80000L) != 0L)
                    return moveChar02(active0, 0x80000L);
                break;
            case 47: // '/' - C_SINGLE_COMMENT
                if ((active0 & 0x60000L) != 0L)
                    return moveChar(0,'\n');
                break;
            case 42: // '*' - C_MULTILINE_COMMENT
                if ((active0 & 0x60000L) != 0L)
                    return moveChar(0,'*');
                break;
            default:
                break;
        }
        //RLogger.getLogger(AbstractJsonLexer.class).info( "searching for \"");
        return findStringLiteral(0, active0);
    }

    protected int moveChar02(long old0, long active0) {
        
        try { 
             curChar = input_stream.readChar(); 
        } catch(java.io.IOException e) {
            return 2;
        }
        switch(curChar) {
            case 76: // L
            case 108:// l
                return moveChar03(active0, 0xc0000L);
            case 85:// U
            case 117:// u
                return moveChar03(active0, 0x20000L);
            default :
                break;
        }
        return -1;
    }

    protected int moveChar03(long old0, long active0) {
        
        try { 
             curChar = input_stream.readChar(); 
        } catch(java.io.IOException e) {
            return 3;
        }

        switch(curChar) {
            case 69: // E
            case 101: // e
                if ((active0 & 0x20000L) != 0L)
                    return stopAtPos(3, RJsonConstants.TRUE);
                break;
            case 76: // L
            case 108:// l
                if ((active0 & 0x80000L) != 0L)
                    return stopAtPos(3, RJsonConstants.NULL);
                break;
            case 83: // S
            case 115:// s
                return moveChar04(active0, 0x40000L);
            default :
                break;
        }
        return -1;
    }

    protected int moveChar04(long old0, long active0) {
        
        try { 
             curChar = input_stream.readChar(); 
        } catch(java.io.IOException e) {
            return 4;
        }

        switch(curChar) {
            case 69: // E
            case 101:// e
                if ((active0 & 0x40000L) != 0L)
                    return stopAtPos(4, RJsonConstants.FALSE);
                break;
            default :
                break;
        }
        return -1;
    }

    protected int stopAtPos(int pos, int kind) {
        matchedKind = kind;
        matchedPos = pos;
        return pos + 1;
    }
}