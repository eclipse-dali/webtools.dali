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

import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlVersion;

/**
 * VirtualVersion is an implementation of Version used when there is 
 * no tag in the orm.xml and an underlying javaVersionMapping exists.
 */
public class VirtualVersion extends AbstractJpaEObject implements XmlVersion
{
	JavaVersionMapping javaVersionMapping;

	protected final VirtualColumn column;

	protected boolean metadataComplete;
	
	public VirtualVersion(JavaVersionMapping javaVersionMapping, boolean metadataComplete) {
		super();
		this.javaVersionMapping = javaVersionMapping;
		this.metadataComplete = metadataComplete;
		this.column = new VirtualColumn(javaVersionMapping.getColumn(), metadataComplete);
	}

	public String getName() {
		return this.javaVersionMapping.persistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public XmlColumn getColumn() {
		return this.column;
	}

	public void setColumn(XmlColumn value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public TemporalType getTemporal() {
		if (this.metadataComplete) {
			return null;
		}
		return org.eclipse.jpt.core.context.TemporalType.toOrmResourceModel(this.javaVersionMapping.getTemporal());
	}

	public void setTemporal(TemporalType newTemporal){
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public void update(JavaVersionMapping javaVersionMapping) {
		this.javaVersionMapping = javaVersionMapping;
		this.column.update(javaVersionMapping.getColumn());
	}
}
