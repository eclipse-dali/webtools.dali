/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.jpt.core.internal.context.java.IJavaAttributeOverride;
import org.eclipse.jpt.core.internal.resource.common.JpaEObject;

public class VirtualAttributeOverride extends JpaEObject implements AttributeOverride
{
	
	protected IJavaAttributeOverride javaAttributeOverride;

	protected final VirtualColumn column;

	protected boolean metadataComplete;

	protected VirtualAttributeOverride(IJavaAttributeOverride javaAttributeOverride, boolean metadataComplete) {
		super();
		this.javaAttributeOverride = javaAttributeOverride;
		this.metadataComplete = metadataComplete;
		this.column = new VirtualColumn(javaAttributeOverride.getColumn(), metadataComplete);
	}

	
	public String getName() {
		if (this.metadataComplete) {
			return null;//TODO is this right??
		}
		return this.javaAttributeOverride.getName();
	}

	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}


	public Column getColumn() {
		return this.column;
	}

	public void setColumn(Column value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public void update(IJavaAttributeOverride javaAttributeOverride) {
		this.javaAttributeOverride = javaAttributeOverride;
		this.column.update(javaAttributeOverride.getColumn());
	}

}
