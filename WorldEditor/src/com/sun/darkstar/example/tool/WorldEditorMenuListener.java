/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sun.darkstar.example.tool;

/**
 * This interface mirrors the action merthods in the current menu bar
 * SADDL file.  Listeners to JKMenuBar can implement this interface and know
 * they have all possible menu actiosn covered
 * @author Jeffrey Kesselman
 */
public interface WorldEditorMenuListener {
    public void doNew();
    public void doOpen();
    public void doImportModel();
    public void doImportTexture();
    public void doExportScene(); 
    public void doExportSelected();
    public void doExit();
    public void doAttachTo();
    public void doDettachFromParent();
    public void doMove();
    public void doRotateX();
    public void doRotateY();
    public void doRotateZ();
    public void doCreatePointLight();
    public void doCreateSpotLight();
    public void doCreateDirectionalLight();
    public void doCreateQuadParticle();
    public void doCreateLineParticle();
    public void doCreateProjectedWater();
    public void doCreateQuadWater();
    public void doCreateTextureLayer();   
    public void doWorldPerspective();
    public void doModelPerspective();
    public void doHelp();
    public void doCreateWorld();
}
