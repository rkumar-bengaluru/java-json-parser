package org.rk.json.parser;

public class NumberLexer extends AbstractLexer {
    int curLexState = 0;
    int defaultLexState = 0;
    int jjnewStateCnt;
    int jjround;

    private final int[] jjrounds = new int[1];
    private final int[] jjstateSet = new int[2];

    public NumberLexer(RCharStream input) {
        super(input);
    }

    public int findInteger(int startState, int curPos) {
        //int[] nextStates; // not used
        int startsAt = 0;
        jjnewStateCnt = 1;
        int i = 1;
        jjstateSet[0] = startState;
        //int j; // not used
        int kind = 0x7fffffff;
        for (;;) {
            if (curChar < 64) {
                RLogger.debug(NumberLexer.class, "findInteger()", "i=" + i + ",startsAt=" + startsAt + ",curChar=" + curChar);
                long l = 1L << curChar;
                do {
                    switch (jjstateSet[--i]) {
                        case 0:
                            kind = RJsonConstants.NUMBER_INTEGER;
                            jjstateSet[jjnewStateCnt++] = 0;
                            RLogger.debug(NumberLexer.class, "findInteger()", "i=" + i + ",startsAt=" + startsAt + ",curChar=" + curChar + ",kind=" + kind);
                            break;
                        default:
                            break;
                    }
                } while (i != startsAt);
            }
            if (kind != 0x7fffffff) {
                jjmatchedKind = kind;
                jjmatchedPos = curPos;
                kind = 0x7fffffff;
            }
            ++curPos;
            if ((i = jjnewStateCnt) == (startsAt = 1 - (jjnewStateCnt = startsAt)))
                return curPos;
            try {
                curChar = input_stream.readChar();
            } catch (java.io.IOException e) {
                return curPos;
            }
        }
    }

}