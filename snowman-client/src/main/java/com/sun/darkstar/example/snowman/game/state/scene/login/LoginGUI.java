package com.sun.darkstar.example.snowman.game.state.scene.login;

import org.fenggui.Label;
import org.fenggui.util.Alphabet;
import org.fenggui.util.Color;
import org.fenggui.util.fonttoolkit.FontFactory;

import com.sun.darkstar.example.snowman.game.gui.GUIPass;

/**
 * <code>LoginGUI</code> extends <code>GUIPass</code> to define the user
 * interface for the login scene. It initializes and maintains all the
 * FengGUI widgets.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-09-2008 15:43 EST
 * @version Modified date: 07-10-2008 18:24 EST
 */
public class LoginGUI extends GUIPass {
    /**
     * Serial version.
     */
    private static final long serialVersionUID = -1020458021673923988L;
    /**
     * The default status text.
     */
    private final String defaultStatus;
    private final String waitingStatus;
    private final String failedStatus;
    /**
     * The status note.
     */
    private Label labelStatus;

    /**
     * Constructor of <code>LoginGUI</code>.
     */
    public LoginGUI() {
        super();
        this.defaultStatus = "Connecting to server, please wait ...";
        this.waitingStatus = "Waiting for server to match you into a game...";
        this.failedStatus = "Login failed";
    }

    @Override
    public void buildWidgets() {
        this.buildStatus();
    }

    /**
     * Build the status note.
     */
    private void buildStatus() {
        this.labelStatus = new Label(this.defaultStatus);
        java.awt.Font awtFont = new java.awt.Font("Sans", java.awt.Font.BOLD, 16);
        this.labelStatus.getAppearance().setFont(FontFactory.renderStandardFont(awtFont, true, Alphabet.getDefaultAlphabet()));
        this.labelStatus.setSizeToMinSize();
        this.labelStatus.setHeight(60);
        this.labelStatus.setXY(this.display.getWidth() / 2 - this.labelStatus.getWidth() / 2, this.display.getHeight() / 6);
        this.labelStatus.getAppearance().setTextColor(Color.WHITE);
        this.display.addWidget(this.labelStatus);
    }

    private void positionLabel(Label label) {
        label.setSizeToMinSize();
        label.setHeight(60);
        label.setXY(this.display.getWidth() / 2 - label.getWidth() / 2, this.display.getHeight() / 6);
    }

    /**
     * Set the status text.
     * @param text The <code>String</code> status text to be set.
     */
    public void setStatus(String text) {
        this.labelStatus.setText(text);
        positionLabel(this.labelStatus);
    }



    /**
     * Retrieve the default status text.
     * @return The default <code>String</code> status text.
     */
    public String getDefaultStatus() {
        return this.defaultStatus;
    }

    public String getWaitingStatus() {
        return this.waitingStatus;
    }

    public String getFailedStatus() {
        return this.failedStatus;
    }
}
