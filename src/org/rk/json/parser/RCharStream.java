package org.rk.json.parser;

public class RCharStream {

    protected java.io.Reader inputStream;
    protected int line = 1;
    protected int column = 0;
    int available;
    protected char[] buffer;
    int bufsize;
    protected int bufline[];
    protected int bufcolumn[];
    protected char[] nextCharBuf;

    protected int inBuf = 0;
    public int bufpos = -1;

    protected int nextCharInd = -1;
    protected int maxNextCharInd = 0;

    int tokenBegin;
    protected int tabSize = 8;

    protected boolean prevCharIsCR = false;
    protected boolean prevCharIsLF = false;

    

    public RCharStream(java.io.Reader dstream,int startline, int startcolumn) {
        this(dstream, startline, startcolumn, 4096);
    }

    public RCharStream(java.io.Reader dstream, int startline, int startcolumn, int buffersize) {
        inputStream = dstream;
        line = startline;
        column = startcolumn - 1;

        available = bufsize = buffersize;
        buffer = new char[buffersize];
        bufline = new int[buffersize];
        bufcolumn = new int[buffersize];
        nextCharBuf = new char[4096];
    }

    public char readChar() throws java.io.IOException {
        if (inBuf > 0) {
             return buffer();
        }

        char c;

        checkBufferSize();

        if ((buffer[bufpos] = c = ReadByte()) == '\\') {
            RLogger.debug(RCharStream.class, "readChar() - " + "inside \\    ");
            updateLineColumn(c);
            int backSlashCnt = 1;
            for (;;) // Read all the backslashes
            {
                checkBufferSize();
                try {
                    if ((buffer[bufpos] = c = ReadByte()) != '\\') {
                        updateLineColumn(c);
                        // found a non-backslash char.
                         if ((c == 'u') && ((backSlashCnt & 1) == 1)) {
                            if (--bufpos < 0) {
                                bufpos = bufsize - 1;
                            }
                            break;
                        }
                    }
                } catch(java.io.IOException e) {
                    if (backSlashCnt > 1)
                        backup(backSlashCnt-1);
                    return '\\';
                }
                updateLineColumn(c);
                backSlashCnt++;
            }
            // Here, we have seen an odd number of backslash's followed by a 'u'
            try {
                while ((c = ReadByte()) == 'u')
                    ++column;
                 buffer[bufpos] = c = (char)(hexval(c) << 12 | hexval(ReadByte()) << 8 |hexval(ReadByte()) << 4 |hexval(ReadByte()));
                 column += 4;
            } catch(java.io.IOException e) {
                throw new Error("Invalid escape character at line " + line +  " column " + column + ".");
            }
            if (backSlashCnt == 1)
                return c;
            else {
                backup(backSlashCnt - 1);
                return '\\';
            }
        } else {
            updateLineColumn(c);
            return c;
        }
    }

    public char beginToken() throws java.io.IOException {
        if (inBuf > 0) {
            return buffer();
        }
        tokenBegin = bufpos + 1;
        return readChar();
    }

    private char buffer() {
        --inBuf;
        if (++bufpos == bufsize)
            bufpos = 0;
        tokenBegin = bufpos;
        return buffer[bufpos];
    }

    protected void AdjustBuffSize() {
        if (available == bufsize) {
            RLogger.debug(RCharStream.class,"AdjustBuffSize::(available == bufsize) ");
        } else if (available > tokenBegin) {
            RLogger.debug(RCharStream.class,"AdjustBuffSize::(available > tokenBegin)  ");
        } else if ((tokenBegin - available) < 2048) {
            RLogger.debug(RCharStream.class,"AdjustBuffSize::((tokenBegin - available) < 2048)   ");
        } else {
            available = tokenBegin;
        }
    }

    protected char ReadByte() throws java.io.IOException {
        //RLogger.debug(RCharStream.class, "ReadByte()", "nextCharInd ->" + nextCharInd + ", maxNextCharInd->" + maxNextCharInd);
        if (++nextCharInd >= maxNextCharInd)
            FillBuff();
        return nextCharBuf[nextCharInd];
    }

    protected void FillBuff() throws java.io.IOException {
        //RLogger.debug(RCharStream.class, "FillBuff()", "nextCharInd ->" + nextCharInd + ", maxNextCharInd->" + maxNextCharInd);
        int i;
        if (maxNextCharInd == 4096)
            maxNextCharInd = nextCharInd = 0;
         try {
             if ((i = inputStream.read(nextCharBuf, maxNextCharInd,4096 - maxNextCharInd)) == -1) {
                 inputStream.close();
                 throw new java.io.IOException();   
             } else {
                 maxNextCharInd += i;
             }
         }  catch(java.io.IOException e) {
             e.printStackTrace();
         }
    }

    protected void updateLineColumn(char c) {
        column++;
        if (prevCharIsLF) {
            prevCharIsLF = false;
            line += (column = 1);
        } else if (prevCharIsCR) {
            prevCharIsCR = false;
            if (c == '\n') {
                prevCharIsLF = true;
            } else {
                line += (column = 1);
            }
        }
        switch (c) {
            case '\r' :
                prevCharIsCR = true;
                break;
            case '\n' :
                prevCharIsLF = true;
                break;
            case '\t' :
                column--;
                column += (tabSize - (column % tabSize));
                break;
            default:
                break;
        }
        bufline[bufpos] = line;
        bufcolumn[bufpos] = column;
    }

    private void checkBufferSize() {
        if (++bufpos == available)
            AdjustBuffSize();
    }

    static final int hexval(char c) throws java.io.IOException {
        switch(c) {
            case '0' :
                return 0;
            case '1' :
                return 1;
            case '2' :
                return 2;
            case '3' :
                return 3;
            case '4' :
                return 4;
            case '5' :
                return 5;
            case '6' :
                return 6;
            case '7' :
                return 7;
            case '8' :
                return 8;
            case '9' :
                return 9;

            case 'a' :
            case 'A' :
                return 10;
            case 'b' :
            case 'B' :
                return 11;
            case 'c' :
            case 'C' :
                return 12;
            case 'd' :
            case 'D' :
                return 13;
            case 'e' :
            case 'E' :
                return 14;
            case 'f' :
            case 'F' :
                return 15;
        }
        throw new java.io.IOException(); // Should never come here
    }    

    public void backup(int amount) {
        inBuf += amount;
        if ((bufpos -= amount) < 0)
            bufpos += bufsize;
    }

    public String GetImage() {
        RLogger.debug(RCharStream.class,"GetImage()" , "tokenBegin - " + tokenBegin + ", bufpos - " + bufpos);
        String response = null;
        if (bufpos >= tokenBegin)
            response =  new String(buffer, tokenBegin, bufpos - tokenBegin + 1);
        else 
            response = new String(buffer, tokenBegin, bufsize - tokenBegin) + new String(buffer, 0, bufpos + 1);
        RLogger.debug(RCharStream.class,"GetImage()" , response);
        return response;
    }

    public int getEndColumn() {
        return bufcolumn[bufpos];
    }

    public int getEndLine() {
        return bufline[bufpos];
    }

    public int getBeginColumn() {
        return bufcolumn[tokenBegin];
    }

    public int getBeginLine() {
        return bufline[tokenBegin];
    }

   

}
