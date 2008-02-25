/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlColumn;

public class VirtualAttributeOverride extends AbstractJpaEObject implements XmlAttributeOverride
{
	
	protected JavaAttributeOverride javaAttributeOverride;

	protected final VirtualColumn column;

	protected boolean metadataComplete;

	protected VirtualAttributeOverride(JavaAttributeOverride javaAttributeOverride, boolean metadataComplete) {
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


	public XmlColumn getColumn() {
		return this.column;
	}

	public void setColumn(XmlColumn value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public void update(JavaAttributeOverride javaAttributeOverride) {
		this.javaAttributeOverride = javaAttributeOverride;
		this.column.update(javaAttributeOverride.getColumn());
	}

}
