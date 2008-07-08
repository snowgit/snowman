/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
