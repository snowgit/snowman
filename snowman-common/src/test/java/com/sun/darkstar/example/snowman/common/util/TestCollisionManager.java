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

package com.sun.darkstar.example.snowman.common.util;

import com.jme.scene.Spatial;
import com.jme.scene.Node;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.scene.shape.Pyramid;
import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.math.Vector3f;
import com.jme.math.Ray;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.easymock.EasyMock;

/**
 *
 * @author Owen Kellett
 */
public class TestCollisionManager {
    
    /** Test world of objects */
    private Node testWorld;
    private Box box;
    private BoundingBox boxBound;
    private Sphere sphere;
    private Pyramid pyramid;
    
    /**
     * The test world is created with three objects lined up in a row
     * A Box with size 10,10,10 with its center at position 0, 0, 20
     * A Sphere with radius 5 with its center at position 0, 0, 40
     * A Pyramid with base size 10x10 and height 10 with center at position 0, 0, 60
     */
    @Before
    public void createTestWorld() {
        //create and position the box
        box = new Box("TestBox", new Vector3f(0,0,0), new Vector3f(10,10,10));
        box.setLocalTranslation(new Vector3f(-5, -5, 15));
        box.setModelBound(new BoundingBox());
        box.updateModelBound();
        
        //create and position the sphere
        sphere = new Sphere("TestSphere", new Vector3f(0,0,40), 10, 10, 5);
        sphere.setModelBound(new BoundingSphere());
        sphere.updateModelBound();
        
        //create and position the pyramid
        pyramid = new Pyramid("TestPyramid", 10, 10);
        pyramid.setLocalTranslation(new Vector3f(0, -5, 55));
        pyramid.setModelBound(new BoundingBox());
        pyramid.updateModelBound();
        
        //create the world and add the objects to it
        testWorld = new Node("TestWorld");
        testWorld.attachChild(box);
        testWorld.attachChild(sphere);
        testWorld.attachChild(pyramid);
        
        //update the bounding geometry for the world
        testWorld.setModelBound(new BoundingBox());
        testWorld.updateModelBound();
        testWorld.updateWorldBound();
    }
    
    
    /**
     * This method will check the getIntersectObject method of the
     * CollisionManager by sending a Ray which goes through all objects
     * of the testWorld.  It will look for the given Class type of object,
     * and should return the given result Node
     * 
     * @param reference Class type to check for an intersection
     * @param result result node to expect
     * @param iterate whether or not to iterate through all collided objects
     */
    private void testGetIntersectObject(Class reference, Spatial result, boolean iterate) {
        //create the ray that goes through all objects
        Vector3f origin = new Vector3f(0,0,0);
        Vector3f destination = new Vector3f(0,0,100);
        
        Ray ray = new Ray(origin, destination);
        
        //call the collision manager
        Spatial actualResult = SingletonRegistry.getCollisionManager().getIntersectObject(ray, testWorld, reference, iterate);
        
        //verify that the resulting node is the same as the expected result
        Assert.assertSame(actualResult, result);
    }

    /**
     * Verify that a Ray which goes through all three objects checking
     * for a box intersection will find the TestBox
     */
    @Test
    public void testGetIntersectObjectIterateBox() {
        testGetIntersectObject(Box.class, box, true);
    }
    
    /**
     * Verify that a Ray which goes through all three objects checking
     * for a sphere intersection will find the TestSphere
     */
    @Test
    public void testGetIntersectObjectIterateSphere() {
        testGetIntersectObject(Sphere.class, sphere, true);
    }
    
    /**
     * Verify that a Ray which goes through all three objects checking
     * for a pyramid intersection will find the TestPyramid
     */
    @Test
    public void testGetIntersectObjectIteratePyramid() {
        testGetIntersectObject(Pyramid.class, pyramid, true);
    }
    
    /**
     * Verify that a Ray which goes through all three objects checking
     * for a box intersection will find the TestBox when iterating (since
     * the box is the first collision)
     */
    @Test
    public void testGetIntersectObjectNoIterateBox() {
        testGetIntersectObject(Box.class, box, false);
    }
    
    /**
     * Verify that a Ray which goes through all three objects checking
     * for a sphere intersection will not find the TestSphere (since the
     * sphere is not the first collision)
     */
    @Test
    public void testGetIntersectObjectNoIterateSphere() {
        testGetIntersectObject(Sphere.class, null, false);
    }
    
    /**
     * Verify that a Ray which goes through all three objects checking
     * for a pyramid intersection will not find the TestPyramid (since
     * the pyramid is not the first collision)
     */
    @Test
    public void testGetIntersectObjectNoIteratePyramid() {
        testGetIntersectObject(Pyramid.class, null, false);
    }
    
    /**
     * Verify that if a Ray is sent that does not intersect with
     * any objects in the world, null will be returned from
     * getIntersectObject
     */
    @Test
    public void testGetIntersectObjectNoCollision() {
        //create the ray that doesn't go through any objects
        Vector3f origin = new Vector3f(0,0,0);
        Vector3f destination = new Vector3f(0,0,-100);
        
        Ray ray = new Ray(origin, destination);
        
        //call the collision manager
        Spatial actualResult = SingletonRegistry.getCollisionManager().getIntersectObject(ray, testWorld, Box.class, true);
        
        //verify that the resulting node is null
        Assert.assertSame(actualResult, null);
    }
    
    
    
    @After
    public void cleanupTestWorld() {
        box = null;
        sphere = null;
        pyramid = null;
        testWorld = null;
    }

}
