/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.ui.internal.wizards.gen;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jpt.ui.CommonImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

/**
 * A draw2D figure representing a database table
 *
 */
public class TableFigure extends Figure {
	  public static Color tableColor = new Color(null,220,232,241);
	  public static Font tableFont = new Font(null, "Arial", 8, SWT.NONE);
	  public static Color disabledColor = ColorConstants.white; 
	  public static Color borderColor = new Color( null, 14,66,115);
	  
	  public static final int OUTLINE_CORNER_RADIUS = 6; 
	  
	  public TableFigure(String name) {
		GridLayout gl = new GridLayout();
		gl.marginHeight = 2;
		gl.marginWidth = 10;
		setLayoutManager(gl);

		setBorder(new LineBorder(tableColor,0));
	    setBackgroundColor(tableColor);
	    setOpaque(true);
	    setSize(150, 20);
		
		Label nameLabel = new Label("", CommonImages.createImage( CommonImages.TABLE_OBJ_IMAGE));
		nameLabel.setFont(tableFont);
		nameLabel.setText(name);
		nameLabel.setForegroundColor(borderColor);
		nameLabel.setLabelAlignment(PositionConstants.CENTER);
	    add(nameLabel);	
	  }
	  
	protected void paintClientArea(Graphics graphics)
	{
		super.paintClientArea(graphics);
		graphics.pushState();
		Rectangle r = getBounds().getCopy();
		graphics.drawRoundRectangle(r.expand(new Insets(-1, -1, -2, -2)),
				OUTLINE_CORNER_RADIUS, OUTLINE_CORNER_RADIUS );					
		graphics.popState();
	}	  
	
	public void setEnabled(boolean enabled ) {
		super.setEnabled(enabled);
		if( enabled ){
			setBackgroundColor(tableColor);
		}else{
			setBackgroundColor(disabledColor);
		}
	}
}