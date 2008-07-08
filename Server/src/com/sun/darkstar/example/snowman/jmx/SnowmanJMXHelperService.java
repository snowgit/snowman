/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
