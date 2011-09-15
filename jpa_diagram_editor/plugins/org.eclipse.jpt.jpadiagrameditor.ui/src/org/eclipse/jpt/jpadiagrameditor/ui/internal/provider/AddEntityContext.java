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
 *    Petya Sabeva - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.provider;

import org.eclipse.graphiti.features.context.impl.AddContext;

public class AddEntityContext extends AddContext implements IAddEntityContext{
	private boolean primaryCollapsed;
	private boolean relationCollapsed;
	private boolean basicCollapsed;
	
	public boolean isPrimaryCollapsed() {
		return primaryCollapsed;
	}
	public void setPrimaryCollapsed(boolean primaryCollapsed) {
		this.primaryCollapsed = primaryCollapsed;
	}

	public boolean isRelationCollapsed() {
		return relationCollapsed;
	}
	public void setRelationCollapsed(boolean relationCollapsed) {
		this.relationCollapsed = relationCollapsed;
	}

	public AddEntityContext(boolean primaryCollapsed,
			boolean relationCollapsed, boolean basicCollapsed) {
		super();
		this.primaryCollapsed = primaryCollapsed;
		this.relationCollapsed = relationCollapsed;
		this.basicCollapsed = basicCollapsed;
	}
	
	public boolean isBasicCollapsed() {
		return basicCollapsed;
	}
	public void setBasicCollapsed(boolean basicCollapsed) {
		this.basicCollapsed = basicCollapsed;
	}
	
}
