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

import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;

/**
 * <code>CollisionManager</code> is a <code>Manager</code> that is responsible
 * for processing all collision detection tasks.
 * 
 * @author Yi Wang (Neakor)
 * @author Owen Kellett
 * @version Creation date: 07-02-2008 24:26 EST
 * @version Modified date: 07-16-2008 11:40 EST
 */
public interface CollisionManager {
	

    /**
     * Retrieve the intersecting object under the given node.
     * @param ray The <code>Ray</code> to check with.
     * @param root The root <code>Node</code> to check against.
     * @param reference The <code>Class</code> reference of the expected object.
     * @param iterate True if all intersected objects should be checked. Otherwise only the first is checked.
     * @return The <code>Spatial</code> that is of the given reference <code>Class</code>.
     */
    public Spatial getIntersectObject(Ray ray, Node root, Class reference, boolean iterate);

    /**
     * Retrieve the intersection point with the given ray and spatial in either
     * world coordinate system or local coordinate system of the given spatial
     * based on the given flag value. The intersection result is stored in the
     * given vector and returned. If the given store is null, a new vector instance
     * is created and returned with the intersection result.
     * @param ray The <code>Ray</code> to check with.
     * @param parent The parent <code>Spatial</code> to check against.
     * @param store The <code>Vector3f</code> to store the intersection result in.
     * @param local True if the intersection should be converted to local coordinate system of the parent.
     * @return If hit, the <code>Vector3f</code> intersection is returned. Otherwise <code>null</code> is returned.
     */
    public Vector3f getIntersection(Ray ray, Spatial parent, Vector3f store, boolean local);

    /**
     * Validate the movement from the given position to the destination
     * defined by the given displacement.
     * @param position The <code>Vector3f</code> starting movement position in local
     * coordinate system relative to the given <code>Spatial</code>.
     * @param displacement The <code>Vector2f</code> planar displacement of movement
     * in local coordinate system relative to the given <code>Spatial</code>.
     * @param spatial The <code>Spatial</code> to check the movement on.
     * @param angle The maximum allowed movement angle in degrees.
     * @return True if this movement is valid. False otherwise.
     */
    public boolean validate(Vector3f position, Vector2f displacement, Spatial spatial, float angle);
}
