package com.sun.darkstar.example.snowman.common.protocol.processor;

/**
 * <code>IProtocolProcessor</code> defines a common parent interface for both
 * <code>IClientProcessor</code> and <code>IServerProcessor</code>.
 * 
 * @author Yi Wang (Neakor)
 * @author Jeffrey Kesselman
 * @version Creation date: 05-29-08 12:05 EST
 * @version Modified date: 06-03-08 10:59 EST
 */
public interface IProtocolProcessor {

    /**
     * Chat message text.
     * @param text The text of the message.
     */
    public void chatText(String text);
}
