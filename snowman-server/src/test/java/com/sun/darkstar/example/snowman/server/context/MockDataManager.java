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

import com.sun.sgs.app.DataManager;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedObjectRemoval;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.NameNotBoundException;
import com.sun.sgs.app.ObjectNotFoundException;
import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.math.BigInteger;

/**
 * Mock's darkstar's DataManager interface so that it behaves as 
 * expected without the backing datastore
 * 
 * @author Owen Kellett
 */
public class MockDataManager implements DataManager
{
    private static BigInteger masterId = BigInteger.ZERO;
    private Map<String, ManagedObject> bindings = new HashMap<String, ManagedObject>();
    private Map<ManagedObject, BigInteger> idMap = new IdentityHashMap<ManagedObject, BigInteger>();

    public <T> ManagedReference<T> createReference(T object) {
        checkArgument(object);
        ManagedObject o = (ManagedObject)object;
        BigInteger id = addToDataStore(o);
        return new MockManagedReference<T>(object, id);
    }

    public ManagedObject getBinding(String name) {
        ManagedObject object = bindings.get(name);
        if(object == null)
            throw new NameNotBoundException("No binding for "+name+" in the data store");
        
        return object;
    }

    public void markForUpdate(Object object) {
        checkArgument(object);
    }

    public String nextBoundName(String name) {
        List<String> names = new ArrayList<String>(bindings.keySet());
        Collections.sort(names);
        
        for(Iterator<String> in = names.iterator(); in.hasNext(); ) {
            if(in.next().equals(name) && in.hasNext()) {
                return in.next();
            }
        }
        return null;
    }

    public void removeBinding(String name) {
        if(!bindings.containsKey(name)) {
            throw new NameNotBoundException("No binding for "+name+" in the data store");
        }
        bindings.remove(name);
    }

    public void removeObject(Object object) {
        checkArgument(object);
        
        if(!idMap.containsKey(object)) {
            throw new ObjectNotFoundException("Object "+object+" not in the data store");
        }
        
        if (object instanceof ManagedObjectRemoval) {
            ((ManagedObjectRemoval)object).removingObject();
        }
        idMap.remove(object);
    }

    public void setBinding(String name, Object object) {
        checkArgument(object);
        addToDataStore((ManagedObject)object);
        bindings.put(name, (ManagedObject)object);
    }
    
    /**
     * Verify that the object implements both ManagedObject
     * and Serializable
     * @param object
     */
    private void checkArgument(Object object) {
        if(!(object instanceof ManagedObject)) {
            throw new IllegalArgumentException("Object "+object+" does not implement ManagedObject");
        }
        if(!(object instanceof Serializable)) {
            throw new IllegalArgumentException("Object "+object+" does not implement Serializable");
        }
    }
    
    private BigInteger addToDataStore(ManagedObject object) {
        BigInteger id = idMap.get(object);
        if(id == null) {
            id = masterId;
            masterId = masterId.add(BigInteger.ONE);
            idMap.put(object, id);
        }
        return id;
    }

}
