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

import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.Diagram;

public interface IJPAEditorImageCreator {
	
	static public enum RelEndDir {
		UP,
		LEFT,
		RIGHT,
		DOWN
	}

	public Polyline createConnectionLine(Diagram d, Connection connection);

	public ConnectionDecorator createTextConnectionDecorator(Connection connection, String text, double location);

	public ConnectionDecorator createArrowConnectionDecorator(Connection connection, double location);

	public ConnectionDecorator createManyEndWithArrowDecorator(Connection connection, double location);

	public ConnectionDecorator createManyStartDecorator(Connection connection, double location);
	
	public ConnectionDecorator createManyEndDecorator(Connection connection, double location);

	public ConnectionDecorator createIconConnectionDecorator(Connection connection, String iconId, double location);
	
	public ConnectionDecorator createCardinalityConnectionDecorator(Connection c, String text, double location);
	

}
