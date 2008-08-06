/*
* Copyright (c) 2008, Sun Microsystems, Inc.
*
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in
*       the documentation and/or other materials provided with the
*       distribution.
*     * Neither the name of Sun Microsystems, Inc. nor the names of its
*       contributors may be used to endorse or promote products derived
*       from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
* "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
* LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
* A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
* OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
* LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
* THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
* OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/ 

package com.sun.darkstar.example.snowman.server.context;

import com.sun.darkstar.example.snowman.server.service.GameWorldManager;
import com.sun.sgs.app.ChannelManager;
import com.sun.sgs.app.DataManager;
import com.sun.sgs.app.TaskManager;
import org.easymock.EasyMock;

/**
 * This class mocks Darkstar's AppContext so that we can properly unit test
 * 
 * @author Owen Kellett
 */
public class MockAppContext implements SnowmanAppContext
{
    private ChannelManager channelManager;
    private DataManager dataManager;
    private TaskManager taskManager;
    private GameWorldManager gameWorldManager;
    
    private MockAppContext()
    {
        channelManager = EasyMock.createMock(ChannelManager.class);
        dataManager = new MockDataManager();
        taskManager = EasyMock.createMock(TaskManager.class);
        gameWorldManager = EasyMock.createMock(GameWorldManager.class);
    }
    
    public ChannelManager getChannelManager() {
        return channelManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public <T> T getManager(Class<T> type) {
        if(type.isAssignableFrom(gameWorldManager.getClass()))
            return type.cast(gameWorldManager);
        
        throw new RuntimeException("Unavailable manager for type: "+type);
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    
    /**
     * Create a MockAppContext and initialize it as the SnowmanAppContext
     * returned by the SnowmanAppContextFactory
     */
    public static void create() {
        MockAppContext context = new MockAppContext();
        SnowmanAppContextFactory.setAppContext(context);
    }
}
