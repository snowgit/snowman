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

package com.sun.darkstar.example.snowman.server;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.NameNotBoundException;
import java.io.Serializable;

/**
 * This is an experimental class thatw as used to expose some values to the 
 * experimental JMX interface.
 * 
 * @author Jeffrey Kesselman
 */
public class SnowmanGameValues implements Serializable, ManagedObject{
    public static final long serialVersionUID = 1L;
    private static final String BOUNDNAME = "__SNOWMAN_VALUES";
    int numberOfGameSessions;
    int numberOfAIperSession;
    
    private SnowmanGameValues(){
        // prevents direct instantiation;
    }
    
    static public SnowmanGameValues get(){
        SnowmanGameValues obj = null;
        try {
            obj = (SnowmanGameValues) 
                    AppContext.getDataManager().getBinding(BOUNDNAME);
        } catch (NameNotBoundException e){
            obj = new SnowmanGameValues();
            AppContext.getDataManager().setBinding(BOUNDNAME, obj);
        }
        return obj;
    }
    
    public int getNumberOfGameSessions(){
        return numberOfGameSessions;
    }
    
    public int getNumberOfAIperSession(){
        return numberOfAIperSession;
    }
}
