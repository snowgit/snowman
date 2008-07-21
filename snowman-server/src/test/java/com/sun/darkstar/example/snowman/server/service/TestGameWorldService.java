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

package com.sun.darkstar.example.snowman.server.service;

import com.sun.darkstar.example.snowman.common.util.SingletonRegistry;
import com.sun.darkstar.example.snowman.common.util.CollisionManager;
import com.sun.darkstar.example.snowman.common.util.DataImporter;
import com.sun.darkstar.example.snowman.common.util.enumn.EWorld;
import com.sun.sgs.kernel.ComponentRegistry;
import com.sun.sgs.service.TransactionProxy;
import com.sun.sgs.kernel.TaskScheduler;
import com.sun.sgs.kernel.TransactionScheduler;
import com.jme.scene.Spatial;
import com.jme.scene.Node;
import com.jme.math.Ray;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.easymock.EasyMock;

/**
 * Verify behavior of the <code>GameWorldService</code>
 * 
 * @author Owen Kellett
 */
public class TestGameWorldService 
{
    /** mock darkstar kernel interfaces */
    private ComponentRegistry mockRegistry;
    private TransactionProxy mockTxnProxy;
    private TaskScheduler mockTaskScheduler;
    private TransactionScheduler mockTransactionScheduler;
    
    /** mock singletons */
    private DataImporter mockDataImporter;
    private CollisionManager mockCollisionManager;
    
    /** dummy spatial object representing dummy game world */
    private static Spatial dummyWorld = new Node();
    
    @Before
    public void mockKernelInterfaces() {
        //create the mock objects to be sent to the constructor
        this.mockRegistry = EasyMock.createMock(ComponentRegistry.class);
        this.mockTxnProxy = EasyMock.createMock(TransactionProxy.class);
        
        //create scheduler mock objects
        this.mockTaskScheduler = EasyMock.createMock(TaskScheduler.class);
        this.mockTransactionScheduler = EasyMock.createMock(TransactionScheduler.class);
        
        //configure the behavior of the registry
        EasyMock.expect(mockRegistry.getComponent(TaskScheduler.class)).andReturn(mockTaskScheduler);
        EasyMock.expect(mockRegistry.getComponent(TransactionScheduler.class)).andReturn(mockTransactionScheduler);
    }
    
    @Before
    public void mockSingletons() {
        //create the mock singletons
        this.mockDataImporter = EasyMock.createMock(DataImporter.class);
        this.mockCollisionManager = EasyMock.createMock(CollisionManager.class);
        
        //configure behavior of the data importer to return a dummy world
        EasyMock.expect(mockDataImporter.getWorld(EWorld.Battle)).andReturn(dummyWorld);
        
        //load the singletons into the registry
        SingletonRegistry.setDataImporter(mockDataImporter);
        SingletonRegistry.setCollisionManager(mockCollisionManager);
    }
    
    
    
    
    
    
    @After 
    public void cleanupMocks() {
        this.mockRegistry = null;
        this.mockTxnProxy = null;
        this.mockTaskScheduler = null;
        this.mockTransactionScheduler = null;
    }
    
    @After
    public void cleanupSingletons() {
        SingletonRegistry.setDataImporter(null);
        SingletonRegistry.setCollisionManager(null);
    }

}
