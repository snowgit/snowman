package com.sun.darkstar.example.snowman.common.protocol.enumn;

/**
 * <code>EOPCODE</code> defines all the opcodes for packets.
 * 
 * @author Yi Wang (Neakor)
 * @author Jeffrey Kesselman
 * @version Creation date: 05-29-08 12:15 EST
 * @version Modified date: 05-29-08 12:16 EST
 */
public enum EOPCODE {
    /**
     * Client to server opcodes.
     */
    MOVEME, ATTACK, GETFLAG, REQINFO, MATCHME, READY,
    /**
     * Server to client opcodes.
     */
    ADDMOB, REMOVEMOB, MOVEMOB, NEWGAME, STARTGAME, ATTACHOBJ, SETHP, ENTERLOUNGE, ENDGAME, ATTACKED, INFO,
    /**
     * Common opcodes.
     */
    CHATTEXT 
}
