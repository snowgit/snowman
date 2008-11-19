package com.sun.darkstar.example.snowman.game.gui.text;

import org.fenggui.util.Color;

/**
 * <code>TextViewFader</code> simulates the fading effect on texts
 * displayed by <code>TextView</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 11-07-2008 18:10 EST
 * @version Modified date: 11-10-2008 14:39 EST
 */
public class TextViewFader extends Thread{
	/**
	 * The text view object.
	 */
	private TextView view;
	/**
	 * The boolean flag indicates if the fading is processing.
	 */
	private boolean fading;
	
	/**
	 * Constructor of TextViewFader.
	 * @param view The text view object.
	 */
	public TextViewFader(TextView view) {
		super();
		this.setDaemon(true);
		// Store the text view.
		this.view = view;
		// Start the fading thread.
		this.start();
	}
	
	/**
	 * Start fading out texts of the text view.
	 */
	public void startFading() {
		this.stopFading();
		this.fading = true;
		synchronized(this) {
			this.notifyAll();
		}
	}
	
	/**
	 * Stop fading out texts of the text view and reset them back to normal transparency.
	 */
	public void stopFading() {
		this.fading = false;
		this.setTextAlpha(1);
	}

	/**
	 * Called by start method.
	 */
	public void run() {
		while(true) {
			int size = this.view.getRuns().size();
			while(size <= 0) {
				try {
					synchronized(this) {
						this.wait(100);
						size = this.view.getRuns().size();
					}
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			int cycled = 0;
			// Wait for 10 seconds before fading out texts.
			while(cycled < 1000 && this.fading) {
				try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
				// If there is new text added to the view, start back from 0.
				if(size != this.view.getRuns().size()) {
					cycled = 0;
					size = this.view.getRuns().size();
					this.setTextAlpha(1);
				// Otherwise advance the cycled number.	
				} else {
					cycled++;
				}
			}
			// Fading out texts.
			float alpha = this.view.getRuns().peek().getColor().getAlpha();
			final float change = 20.0f/3000.0f;
			while(alpha > 0 && cycled == 1000 && this.fading) {
				try {Thread.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
				// If there is new text added to the view, start back from 0.
				if(size != this.view.getRuns().size()) {
					cycled = 0;
					this.setTextAlpha(1);
				// Otherwise decrease the alpha of text color.
				} else {
					alpha = alpha - change;
					if(alpha < 0) alpha = 0;
					this.setTextAlpha(alpha);
				}
			}
			// If not fading, let the thread wait.
			if(!this.fading) {
				synchronized(this){
					try {this.wait();}
					catch (InterruptedException e) {e.printStackTrace();}
				}
			}
		}
	}
	
	/**
	 * Set the alpha of all the texts in the text view.
	 * @param alpha The new alpha value to be set.
	 */
	private void setTextAlpha(float alpha) {
		Color color = null;
		for(TextRun run : this.view.getRuns()) {
			color = run.getColor();
			run.setTextColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
		}
	}
}
