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

package com.sun.darkstar.example.tool.test;

import com.sun.darkstar.example.tool.JKMenuBar;
import com.sun.darkstar.example.tool.WorldEditorMenuListener;
import java.io.InputStreamReader;
import javax.swing.JFrame;

/**
 *
 * @author Jeffrey Kesselman
 */
public class MenuTest extends JFrame{
    
    public MenuTest(){
        JKMenuBar menuBar = null;
        menuBar = new JKMenuBar(new InputStreamReader(
                getClass().getResourceAsStream("res/menubar.sdl")));
        setJMenuBar(menuBar);
        menuBar.addListener(new WorldEditorMenuListener(){

            public void doNew() {
                System.out.print("doNew");
            }

            public void doOpen() {
                System.out.print("doOpen");            }

            public void doImportModel() {
                  System.out.print("doImportModel");
            }

            public void doImportTexture() {
                 System.out.print("doExportTexture");
            }

            public void doExportScene() {
                  System.out.print("doExportScene");
            }

            public void doExportSelected() {
                  System.out.print("doExportSelected");
            }
            
            public void doExit(){
               System.out.print("doExit");
            }

            public void doAttachTo() {
                  System.out.print("doAttachTo");
            }

            public void doDettachFromParent() {
                  System.out.print("doDettachFromParent");
            }

            public void doMove() {
                  System.out.print("doMove");;
            }

            public void doRotateX() {
                  System.out.print("doRoateX");
            }

            public void doRotateY() {
                  System.out.print("doRotateY");
            }

            public void doRotateZ() {
                  System.out.print("doRotateZ");
            }

            public void doCreatePointLight() {
                  System.out.print("doCreatePointLight");
            }

            public void doCreateSpotLight() {
                  System.out.print("doCreateSpotLight");
            }

            public void doCreateDirectionalLight() {
                  System.out.print("doCreateDirectionalLight");
            }

            public void doCreateQuadParticle() {
                  System.out.print("doCreateQuadParticle");
            }

            public void doCreateLineParticle() {
                  System.out.print("doCreateLineParticle");
            }

            public void doCreateProjectedWater() {
                  System.out.print("doCreateProjectedWater");
            }

            public void doCreateQuadWater() {
                  System.out.print("doCreateQuadWater");
            }

            public void doCreateTextureLayer() {
                  System.out.print("doCreateTextureLayer");
            }

            public void doWorldPerspective() {
                 System.out.print("doWorldPerspective");
            }

            public void doModelPerspective() {
                  System.out.print("doModelPerspective");
            }

            public void doHelp() {
                 System.out.print("doHelp");
            }

			@Override
			public void doCreateWorld() {
				// TODO Auto-generated method stub
				
			}
            
        });
        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }
    
    static public void main(String[] args){
        new MenuTest();
    }
    
}
