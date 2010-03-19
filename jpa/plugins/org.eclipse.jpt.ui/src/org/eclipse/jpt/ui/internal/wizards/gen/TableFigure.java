/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
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
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.ui.internal.ImageRepository;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

/**
 * A draw2D figure representing a database table
 * 
 */
public class TableFigure extends Figure
{
	private Color tableColor = new Color(null, 220, 232, 241);

	private Font tableFont = new Font(null, "Arial", 8, SWT.NONE); //$NON-NLS-1$

	private Color borderColor = new Color(null, 14, 66, 115);

	public static final int OUTLINE_CORNER_RADIUS = 6;

	protected final ResourceManager resourceManager;

	public TableFigure(String name, ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
		GridLayout gl = new GridLayout();
		gl.marginHeight = 2;
		gl.marginWidth = 10;
		setLayoutManager(gl);
		setBorder(new LineBorder(this.tableColor, 0));
		setBackgroundColor(this.tableColor);
		setOpaque(true);
		setSize(150, 20);
		Label nameLabel = new Label("", ImageRepository.getTableObjImage(this.resourceManager));
		nameLabel.setFont(this.tableFont);
		nameLabel.setText(name);
		nameLabel.setForegroundColor(this.borderColor);
		nameLabel.setLabelAlignment(PositionConstants.CENTER);
		add(nameLabel);
	}

	@Override
	protected void paintClientArea(Graphics graphics) {
		super.paintClientArea(graphics);
		graphics.pushState();
		Rectangle r = getBounds().getCopy();
		graphics.drawRoundRectangle(r.expand(new Insets(-1, -1, -2, -2)), OUTLINE_CORNER_RADIUS, OUTLINE_CORNER_RADIUS);
		graphics.popState();
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			setBackgroundColor(this.tableColor);
		}
		else {
			setBackgroundColor(ColorConstants.white);
		}
	}

	public void dispose() {
		this.borderColor.dispose();
		this.tableFont.dispose();
		this.tableColor.dispose();
	}
}