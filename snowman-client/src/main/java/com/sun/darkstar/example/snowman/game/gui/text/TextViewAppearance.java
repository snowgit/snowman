package com.sun.darkstar.example.snowman.game.gui.text;

import java.io.IOException;

import org.fenggui.DecoratorAppearance;
import org.fenggui.render.DirectTextRenderer;
import org.fenggui.render.Font;
import org.fenggui.render.Graphics;
import org.fenggui.render.IOpenGL;
import org.fenggui.theme.xml.IXMLStreamableException;
import org.fenggui.theme.xml.InputOutputStream;
import org.fenggui.util.Color;
import org.fenggui.util.Dimension;
import org.fenggui.util.Rectangle;

/**
 * <code>TextViewAppearance</code> defines the appearance of <code>TextView</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 11-07-2008 17:20 EST
 * @version Modified date: 11-10-2008 18:15 EST
 */
public class TextViewAppearance extends DecoratorAppearance {
	/**
	 * The text view object.
	 */
	private TextView view;
	/**
	 * The default text color.
	 */
	private Color textColor;
	/**
	 * The text renderer object.
	 */
	private DirectTextRenderer textRenderer;

	/**
	 * Constructor of TextViewAppearance.
	 * @param view The text view object.
	 */
	public TextViewAppearance(TextView view) {
		super(view);
		this.view = view;
		this.textColor = Color.BLACK;
		this.textRenderer = new DirectTextRenderer();
	}

	/**
	 * Either serializes or deserializes the data contained by the object
	 * by calling the processing methods of the passed InputOutputStream.
	 * 
	 * @param stream the stream used by the serialization or deserialization
	 * @throws IOException thrown if an I/O exception occurs during the operation
	 * @throws IXMLStreamableException if the input/output operations fail
	 */
	@Override
	public void process(InputOutputStream stream) throws IOException, IXMLStreamableException{
		super.process(stream);
		if(stream.isInputStream()) {
			this.setFont(stream.processChild("Font", this.getFont(), Font.getDefaultFont(), Font.class));
		}
		this.textColor = stream.processChild("Color", this.textColor, Color.BLACK, Color.class);		
	}

	/**
	 * Set the font of the text renderer.
	 * @param font The font to be set.
	 */
	public void setFont(Font font) {
		this.textRenderer.setFont(font);
		this.view.updateMinSize();
	}

	/**
	 * Set the default text color.
	 * @param textColor The color to be set.
	 */
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	/**
	 * Retrieve the font of the text renderer.
	 * @return The font of the text renderer.
	 */
	public Font getFont() {
		return this.textRenderer.getFont();
	}

	/**
	 * Retrieve the default text color.
	 * @return The defualt text color.
	 */
	public Color getTextColor() {
		return this.textColor;
	}

	@Override
	public Dimension getContentMinSizeHint() {
		return new Dimension(this.view.getMinWidth(), this.view.fullHeight);
	}

	@Override
	public void paintContent(Graphics g, IOpenGL gl) {
		int y = this.getContentHeight();
		int x = 0;
		x += g.getTranslation().getX();
		y += g.getTranslation().getY();
		Rectangle clipRect = new Rectangle(g.getClipSpace());
		clipRect.setX(0);
		clipRect.setY(clipRect.getY() - this.view.getDisplayY() - this.getContentHeight());
		for (TextRun run : this.view.getRuns()) {
			if (run.getBoundingRect().intersect(clipRect)) {
				run.paint(g, x, y);
			}
		}
	}
}