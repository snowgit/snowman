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

package com.sun.darkstar.example.snowman.jmx;

import com.sun.darkstar.example.snowman.server.SnowmanGameValues;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.auth.Identity;
import com.sun.sgs.impl.util.AbstractKernelRunnable;
import com.sun.sgs.kernel.ComponentRegistry;
import com.sun.sgs.kernel.KernelRunnable;
import com.sun.sgs.kernel.TransactionScheduler;
import com.sun.sgs.service.Service;
import com.sun.sgs.service.TransactionProxy;
import java.lang.management.ManagementFactory;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 *
 * @author Jeffrey Kesselman
 */
public class SnowmanJMXHelperService implements Service{
    Properties properties;
    ComponentRegistry registry;
    TransactionProxy transactionProxy;
    Identity id;
    
    public SnowmanJMXHelperService(Properties props, 
            ComponentRegistry reg, TransactionProxy tproxy){
        properties = props;
        registry = reg;
        transactionProxy = tproxy;  
        id = tproxy.getCurrentOwner();
    //    System.out.println("service constructed");
    }
    
    public String getName() {
        return "SnowmanJMXHelperService";
    }

    public void ready() throws Exception {
      //  System.out.println("service readying");
        SnowmanJMX jmxBean = new SnowmanJMX(this);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer(); 
        ObjectName name = 
                new ObjectName(
                    "com.sun.darkstar.example.snowman.jmx:type=SnowmanJMX"); 
         mbs.registerMBean(jmxBean, name); 
       //   System.out.println("service readied");
    }

    public boolean shutdown() {
       return true;
    }
    
    public int getSessionCount(){
       // System.out.println("Getting session count");
        TransactionScheduler sched = 
                registry.getComponent(TransactionScheduler.class);
        GetGameValues task = new GetGameValues();
        try {
            sched.runTask(task, id);
            return task.sessionCount;
        } catch (Exception ex) {
            Logger.getLogger(SnowmanJMXHelperService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public int getAICount(){
        // System.out.println("Getting AI count");
        TransactionScheduler sched = 
                registry.getComponent(TransactionScheduler.class);
        GetGameValues task = new GetGameValues();
        try {
            sched.runTask(task, id);
            return task.aiCount;
        } catch (Exception ex) {
            Logger.getLogger(SnowmanJMXHelperService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    // Task classes
    private static class GetGameValues extends AbstractKernelRunnable{
        int sessionCount;
        int aiCount;
        
        public void run() throws Exception {           
            SnowmanGameValues gameValues = SnowmanGameValues.get();
            sessionCount = gameValues.getNumberOfGameSessions();
            aiCount = gameValues.getNumberOfAIperSession();
        }
        
    }

}
