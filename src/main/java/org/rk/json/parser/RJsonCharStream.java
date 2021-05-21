package org.rk.json.parser; 

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
/**
 * An implementation of interface CharStream, where the stream is assumed to
 * contain only ASCII characters (with java-like unicode escape processing).
 */

public class RJsonCharStream {
    static Logger logger = LogManager.getLogger(RJsonCharStream.class);

    protected int maxNextCharInd = 0;
    protected int nextCharInd = -1;

    protected int bufline[];
    protected int bufcolumn[];
    protected int column = 0;
    protected int line = 1;

    public int bufpos = -1;
    int bufsize;
    int available;
    protected int inBuf = 0;

    protected boolean prevCharIsCR = false;
    protected boolean prevCharIsLF = false;
    protected int tabSize = 8;

    int tokenBegin;
    protected char[] nextCharBuf;
    protected char[] buffer;
    protected Reader inputStream;

    private char readFromBuffer() {
        --inBuf;
        if (++bufpos == bufsize)
            bufpos = 0;
        return buffer[bufpos];
    }

    public char beginToken() throws IOException {

        if (inBuf > 0) {
            // lookahead and backup has happened.
            return readFromBuffer();
        }

        tokenBegin = 0;
        bufpos = -1;
        return readChar();
    }

    public char readChar() throws IOException {
        if (inBuf > 0) {
            // lookahead and backup has happened.
            return returnFirstCharInBuffer();
        }

        char c;
        if (++bufpos == available)
            adjustBuffSize();

        if ((buffer[bufpos] = c = readByte()) == '\\') {
            return checkSpecial(c);
        } else {
            updateLineColumn(c);
            return c;
        }
    }

    protected char readByte() throws IOException {
        if (++nextCharInd >= maxNextCharInd)
            fillBuff();

        return nextCharBuf[nextCharInd];
    }  

    public String getImage() {
        String response = null;

        if (bufpos >= tokenBegin)
            response = new String(buffer, tokenBegin, bufpos - tokenBegin + 1);
        else 
            response = new String(buffer, tokenBegin, bufsize - tokenBegin) + new String(buffer, 0, bufpos + 1);
        
        return response;
    }  

    protected void fillBuff() throws IOException {
        int i;
        if (maxNextCharInd == 4096)
            maxNextCharInd = nextCharInd = 0;
        try { 
            if ((i = inputStream.read(nextCharBuf, maxNextCharInd,
                                            4096 - maxNextCharInd)) == -1) {
                inputStream.close();
                throw new java.io.IOException();
            } else {
                maxNextCharInd += i;
            }
            return;
        } catch(IOException e) {
            if (bufpos != 0) {
                --bufpos;
                backup(0);
            } else {
                bufline[bufpos] = line;
                bufcolumn[bufpos] = column;
            }
            throw e;
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
            default :
                break;
        }
        bufline[bufpos] = line;
        bufcolumn[bufpos] = column;
    }

    public void backup(int amount) {
        inBuf += amount;
        if ((bufpos -= amount) < 0)
            bufpos += bufsize;
    }

    public int getColumn() {
        return bufcolumn[bufpos];
    }

    public int getLine() {
        return bufline[bufpos];
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

    protected void adjustBuffSize() {
        if (available == bufsize) {
            if (tokenBegin > 2048) {
                bufpos = 0;
            } else {
                expandBuff(true);
            }
        } else if (available > tokenBegin) {
            available = bufsize;
        }  else if ((tokenBegin - available) < 2048) {
            expandBuff(true);
        } else {
            available = tokenBegin;
        }
    }

    protected void expandBuff(boolean wrapAround) {
        char[] newbuffer = new char[bufsize + 2048];
        int newbufline[] = new int[bufsize + 2048];
        int newbufcolumn[] = new int[bufsize + 2048];
        try {
            if (wrapAround) {
                System.arraycopy(buffer, tokenBegin, newbuffer, 0, bufsize - tokenBegin);
                System.arraycopy(buffer, 0, newbuffer,bufsize - tokenBegin, bufpos);
                buffer = newbuffer;
                System.arraycopy(bufline, tokenBegin, newbufline, 0, bufsize - tokenBegin);
                System.arraycopy(bufline, 0, newbufline, bufsize - tokenBegin, bufpos);
                bufline = newbufline;
                System.arraycopy(bufcolumn, tokenBegin, newbufcolumn, 0, bufsize - tokenBegin);
                System.arraycopy(bufcolumn, 0, newbufcolumn, bufsize - tokenBegin, bufpos);
                bufcolumn = newbufcolumn;
                bufpos += (bufsize - tokenBegin);
            } else {
                System.arraycopy(buffer, tokenBegin, newbuffer, 0, bufsize - tokenBegin);
                System.arraycopy(bufline, tokenBegin, newbufline, 0, bufsize - tokenBegin);
                System.arraycopy(bufcolumn, tokenBegin, newbufcolumn, 0, bufsize - tokenBegin);
                buffer = newbuffer;
                bufline = newbufline;
                bufcolumn = newbufcolumn;
                bufpos -= tokenBegin;
            }

        } catch (Throwable t) {
            throw new Error(t.getMessage());
        }
        available = (bufsize += 2048);
        tokenBegin = 0;
    }

    private char checkSpecial(char c) {
        updateLineColumn(c);
        int backSlashCnt = 1;
        for (;;) // Read all the backslashes
        {
            if (++bufpos == available)
                adjustBuffSize();
            try {
                if ((buffer[bufpos] = c = readByte()) != '\\') {
                    updateLineColumn(c);
                     // found a non-backslash char.
                     if ((c == 'u') && ((backSlashCnt & 1) == 1)) {
                         if (--bufpos < 0)
                            bufpos = bufsize - 1;
                         break;
                     }
                     backup(backSlashCnt);
                     return '\\';
                }
            } catch(IOException e) {
                if (backSlashCnt > 1)
                    backup(backSlashCnt-1);
                return '\\';
            }
        }
        // Here, we have seen an odd number of backslash's followed by a 'u'
        try {
            while ((c = readByte()) == 'u')
                ++column;
            buffer[bufpos] = c = (char)(hexval(c) << 12 | hexval(readByte()) << 8 | hexval(readByte()) << 4 | hexval(readByte()));
            column += 4;
        } catch(IOException e) {
            throw new Error("Invalid escape character at line " + line + " column " + column + ".");
        }

        if (backSlashCnt == 1) {
            return c;
        } else {
            backup(backSlashCnt - 1);
            return '\\';
        }
    }

    static final int hexval(char c) throws IOException {
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
        throw new IOException(); // Should never come here
    }

    public RJsonCharStream(InputStream dstream, int startline,int startcolumn) {
        this(dstream, startline, startcolumn, 4096);
    }

    public RJsonCharStream(InputStream dstream, int startline,int startcolumn, int buffersize) {
        this(new InputStreamReader(dstream), startline, startcolumn, buffersize);
    }

    public RJsonCharStream(Reader dstream, int startline,int startcolumn) {
        this(dstream, startline, startcolumn, 4096);
    }

    public RJsonCharStream(Reader dstream, int startline,int startcolumn, int buffersize) {
        inputStream = dstream;
        line = startline;
        column = startcolumn - 1;
        available = bufsize = buffersize;
        buffer = new char[buffersize];
        bufline = new int[buffersize];
        bufcolumn = new int[buffersize];
        nextCharBuf = new char[buffersize];
    }
}