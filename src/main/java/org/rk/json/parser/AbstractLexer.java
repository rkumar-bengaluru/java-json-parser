package org.rk.json.parser;

public class AbstractLexer {
    protected RCharStream input_stream;
    protected char curChar;
    public int jjmatchedKind;
    protected int jjmatchedPos;
    public static final String[] jjstrLiteralImages = {
        "", null, null, null, null, null, "\54", "\173", "\175", "\72", "\133", 
        "\135", null, null, null, null, null, null, null, null, null, null, "\47\47", 
        "\42\42", null, null, null, null, null, };
    static final long[] jjtoToken = { 0x1ccf8fc1L, };

    public AbstractLexer(RCharStream input) {
        this.input_stream = input;
    }

    protected void checkForSpace() {
        System.err.println("checkForSpace" + curChar);
        if(curChar == 32) {
            try { 
             curChar = input_stream.readChar(); 
            } catch(java.io.IOException e) {
                e.printStackTrace();
                stopStringLiteralAt(0, 0);
            }
            if(curChar != 32) {
                System.err.println("checkForSpace" + curChar);
                input_stream.backup(1);
                return;
            } else {
                checkForSpace();
            }
        }
        System.err.println("checkForSpace" + curChar);
    }

    protected RToken fillToken() {
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

    protected int findStringLiteral(int curPos, long active0) {
        int startsAt = 0;
        
        int i = 1;
        
        int kind = 0x7fffffff;
        
        try { 
             curChar = input_stream.readChar(); 
        } catch(java.io.IOException e) {
            stopStringLiteralAt(0, active0);
            return 1;
        }
        
        for (;;) {
            RLogger.debug(AbstractLexer.class,"findStringLiteral()",curChar);
            switch(curChar) {
                case 34: // '"'
                    RLogger.debug(AbstractLexer.class,"-----",curChar);
                    kind = RJsonConstants.STRING_DOUBLE_NONEMPTY;
                    break;
                default:
                    break;
            }
            ++curPos;
            if (kind != 0x7fffffff) {
                jjmatchedKind = kind;
                jjmatchedPos = curPos;
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
            stopStringLiteralAt(0, active0);
            return 1;
        }
        System.err.println("moveChar01::curChar=" + curChar);
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
            default:
                break;
        }
        RLogger.debug(RJsonLexer.class, "moveChar01()" , "searching for \"");
        return findStringLiteral(0, active0);
    }

    protected int moveChar02(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(0, old0);
        try { 
             curChar = input_stream.readChar(); 
        } catch(java.io.IOException e) {
            stopStringLiteralAt(1, active0);
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
        return jjStartNfa_0(1, active0);
    }

    protected int moveChar03(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(1, old0); 
        try { 
             curChar = input_stream.readChar(); 
        } catch(java.io.IOException e) {
            stopStringLiteralAt(2, active0);
            return 3;
        }

        switch(curChar) {
            case 69: // E
            case 101: // e
                if ((active0 & 0x20000L) != 0L)
                    return jjStartNfaWithStates_0(3, RJsonConstants.TRUE, 15);
                break;
            case 76: // L
            case 108:// l
                if ((active0 & 0x80000L) != 0L)
                    return jjStartNfaWithStates_0(3, RJsonConstants.NULL, 15);
                break;
            case 83: // S
            case 115:// s
                return moveChar04(active0, 0x40000L);
            default :
                break;
        }
        return jjStartNfa_0(2, active0);
    }

    protected int moveChar04(long old0, long active0) {
        if (((active0 &= old0)) == 0L)
            return jjStartNfa_0(2, old0); 
        try { 
             curChar = input_stream.readChar(); 
        } catch(java.io.IOException e) {
            stopStringLiteralAt(3, active0);
            return 4;
        }

        switch(curChar) {
            case 69: // E
            case 101:// e
                if ((active0 & 0x40000L) != 0L)
                    return jjStartNfaWithStates_0(4, RJsonConstants.FALSE, 15);
                break;
            default :
                break;
        }
        return jjStartNfa_0(3, active0);
    }

    protected int jjStartNfaWithStates_0(int pos, int kind, int state) {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        try { curChar = input_stream.readChar(); }
        catch(java.io.IOException e) { return pos + 1; }
        return jjMoveNfa_0(state, pos + 1);
    }

    protected int stopAtPos(int pos, int kind) {
        
        jjmatchedKind = kind;
        //RLogger.debug(AbstractLexer.class, "stopAtPos()" , jjmatchedKind);
        jjmatchedPos = pos;
        return pos + 1;
    }

    protected int jjMoveNfa_0(int startState, int curPos) {
        int startsAt = 0;
        int i = 1;
        int kind = 0x7fffffff;
        boolean tokenFound = false;
        int noOfCharacters = 1;
        
        for (;;) 
        {
            //RLogger.debug(RJsonLexer.class,"jjMoveNfa_0()",curChar);
            if (curChar < 64) {
                long l = 1L << curChar;
                switch(startState) {
                    case 39:
                        kind = RJsonConstants.STRING_DOUBLE_NONEMPTY;
                        tokenFound = true;
                        break;
                    case 34: // '"'
                        kind = RJsonConstants.STRING_DOUBLE_NONEMPTY;
                        tokenFound = true;
                        break;
                    case 48:
                    case 49:
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                        kind = RJsonConstants.NUMBER_INTEGER;
                        tokenFound = true;
                        break;
                    case 46:
                        break;
                    default: 
                        kind = RJsonConstants.NUMBER_DECIMAL;
                        input_stream.backup(1);
                        tokenFound = true;
                        break;
                }
             }
            
            if (kind != 0x7fffffff) {
                jjmatchedKind = kind;
                jjmatchedPos = curPos;
                kind = 0x7fffffff;
            }
            ++curPos;
            if(tokenFound) {
                return curPos;
            }
            try { curChar = input_stream.readChar(); }
            catch(java.io.IOException e) { return curPos; }
        }
    }

    private final int jjStartNfa_0(int pos, long active0) {
        return jjMoveNfa_0(stopStringLiteralAt(pos, active0), pos + 1);
    }


    protected final int stopStringLiteralAt(int pos, long active0) {
        switch (pos) {
            case 0:
                if ((active0 & 0xe0000L) != 0L) {
                    jjmatchedKind = 28;
                    return 15;
                }
                if ((active0 & 0x400000L) != 0L) {
                    return 38;
                }
                if ((active0 & 0x800000L) != 0L) { 
                    return 39;
                }
                return -1;
            case 1:
                if ((active0 & 0xe0000L) != 0L) {
                    jjmatchedKind = 28;
                    jjmatchedPos = 1;
                    return 15;
                }
                return -1;
            case 2:
                if ((active0 & 0xe0000L) != 0L) {
                    jjmatchedKind = 28;
                    jjmatchedPos = 2;
                    return 15;
                }
                return -1;
            case 3:
                if ((active0 & 0xa0000L) != 0L)
                    return 15;
                if ((active0 & 0x40000L) != 0L) {
                    jjmatchedKind = 28;
                    jjmatchedPos = 3;
                    return 15;
                }
                return -1;
            default:
                return -1;
        }
    }
}