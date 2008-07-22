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


/**
 * The SingletonRegistry is a Singleton itself that should be a single
 * point of access for all Singletons.  It is introduced to increase 
 * testability of code that depends on Singleton objects.
 * 
 * @author Owen Kellett
 */
public class SingletonRegistry 
{
	/**
	 * The <code>SingletonRegistry</code> instance
	 */
	private static SingletonRegistry instance;

	private DataImporter dataImporter;
	private CollisionManager collisionManager;
	private IHPConverter hpConverter;

	protected SingletonRegistry() {}

	public static SingletonRegistry getInstance() {
		if(SingletonRegistry.instance == null) {
			SingletonRegistry.instance = new SingletonRegistry();
		}
		return SingletonRegistry.instance;
	}

	public static IHPConverter getHPConverter() {
		if(SingletonRegistry.getInstance().hpConverter == null) {
			SingletonRegistry.getInstance().hpConverter = HPConverter.getInstance();
		}
		return SingletonRegistry.getInstance().hpConverter;
	}

	public static DataImporter getDataImporter() {
		if(getInstance().dataImporter == null) {
			getInstance().dataImporter = DataImporterImpl.getInstance();
		}
		return getInstance().dataImporter;
	}
	public static CollisionManager getCollisionManager() {
		if(getInstance().collisionManager == null) {
			getInstance().collisionManager = CollisionManagerImpl.getInstance();
		}
		return getInstance().collisionManager;
	}

	public static void setDataImporter(DataImporter dataImporter) {
		getInstance().dataImporter = dataImporter;
	}
	public static void setCollisionManager(CollisionManager collisionManager) {
		getInstance().collisionManager = collisionManager;
	}

}
