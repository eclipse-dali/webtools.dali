/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Kiril Mitov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.provider;

import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.swt.graphics.Point;

public class JPAEditorImageCreator implements IJPAEditorImageCreator {

	public Polyline createConnectionLine(Diagram d, Connection connection) {
		Polyline polyline = Graphiti.getGaService().createPolyline(connection);
		polyline.setForeground(Graphiti.getGaService().manageColor(d, JPAEditorConstants.CONNECTION_LINE_COLOR));
		polyline.setLineWidth(JPAEditorConstants.CONNECTION_LINE_WIDTH);
		return polyline;
	}

	public ConnectionDecorator createTextConnectionDecorator(Connection connection, String text, double location) {
		ConnectionDecorator textDecorator = Graphiti.getPeService().createConnectionDecorator(connection, true, 0.5, true);
		Text txt = Graphiti.getGaService().createDefaultText(textDecorator);
		txt.setLineWidth(JPAEditorConstants.CONNECTION_LINE_WIDTH);
		txt.setValue(text);
		Graphiti.getGaService().manageColor(connection.getParent(), IColorConstant.BLACK);
		Graphiti.getGaService().setLocation(txt, 5, -20);
		return textDecorator;
	}
	
	public ConnectionDecorator createCardinalityConnectionDecorator(Connection c, String text, double location) {
		ConnectionDecorator textDecorator = Graphiti.getPeService().createConnectionDecorator(c, true, location, true);
		Text txt = Graphiti.getGaService().createDefaultText(textDecorator);
		txt.setLineWidth(JPAEditorConstants.CONNECTION_LINE_WIDTH);
		txt.setValue(text);
		Graphiti.getGaService().manageColor(c.getParent(), IColorConstant.BLACK);
		Point pt = JPAEditorUtil.recalcTextDecoratorPosition((FreeFormConnection)c, textDecorator);
		Graphiti.getGaService().setLocation(txt, pt.x, pt.y, false);
		return textDecorator;
	}

	public ConnectionDecorator createArrowConnectionDecorator(Connection connection, double location) {
		ConnectionDecorator cd = Graphiti.getPeService().createConnectionDecorator(connection, false, location, true);
		Polyline arrow = null; 
			arrow = Graphiti.getGaService().createPolyline(cd, new int[] { 11, 3, 0, 0, 11, -3, 11, 3});
		arrow.setForeground(Graphiti.getGaService().manageColor(connection.getParent(), JPAEditorConstants.CONNECTION_LINE_COLOR));
		arrow.setLineWidth(JPAEditorConstants.CONNECTION_LINE_WIDTH);
		return cd;
	}

	public ConnectionDecorator createManyEndWithArrowDecorator(Connection connection, double location) {
		ConnectionDecorator cd = Graphiti.getPeService().createConnectionDecorator(connection, false, location, true);
		Polyline arrow = null;
		arrow = Graphiti.getGaService().createPolyline(cd, new int[] { -2, 7, 6, 0, -2, -7, 6, 0, 20, 3, 20, -3, 7, 0});
		arrow.setForeground(Graphiti.getGaService().manageColor(connection.getParent(), JPAEditorConstants.CONNECTION_LINE_COLOR));
		arrow.setLineWidth(JPAEditorConstants.CONNECTION_LINE_WIDTH);
		return cd;
	}

	public ConnectionDecorator createManyEndDecorator(Connection connection, double location) {
		ConnectionDecorator cd = Graphiti.getPeService().createConnectionDecorator(connection, false, location, true);
		Polyline arrow = null;
		arrow = Graphiti.getGaService().createPolyline(cd, new int[] { 0, 7, 
													 8, 0, 
													0, -7 });
		arrow.setForeground(Graphiti.getGaService().manageColor(connection.getParent(), JPAEditorConstants.CONNECTION_LINE_COLOR));
		arrow.setLineWidth(JPAEditorConstants.CONNECTION_LINE_WIDTH);
		return cd;
	}
	
	public ConnectionDecorator createManyStartDecorator(Connection connection, double location) {
		ConnectionDecorator cd = Graphiti.getPeService().createConnectionDecorator(connection, false, location, true);
		Polyline arrow = null;
		arrow = Graphiti.getGaService().createPolyline(cd, new int[] {0, 7, 
													 -8, 0, 
													0, -7 });		
		arrow.setForeground(Graphiti.getGaService().manageColor(connection.getParent(), JPAEditorConstants.CONNECTION_LINE_COLOR));
		arrow.setLineWidth(JPAEditorConstants.CONNECTION_LINE_WIDTH);
		return cd;
	}	

	public ConnectionDecorator createIconConnectionDecorator(Connection connection, String iconId, double location) {
		ConnectionDecorator iconDecorator = Graphiti.getPeService().createConnectionDecorator(connection, true, location, true);
		Image icon = Graphiti.getGaService().createImage(connection, iconId);
		Graphiti.getGaService().setLocation(icon, 5, -20);
		return iconDecorator;
	}

}
