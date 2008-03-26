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

import org.eclipse.jpt.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.XmlTransient;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * VirtualTransient is an implementation of Transient used when there is 
 * no tag in the orm.xml and an underlying javaTransientMapping exists.
 */
public class VirtualXmlTransient extends AbstractJpaEObject implements XmlTransient
{
	JavaTransientMapping javaTransientMapping;

	protected boolean metadataComplete;
	
	public VirtualXmlTransient(JavaTransientMapping javaTransientMapping, boolean metadataComplete) {
		super();
		this.javaTransientMapping = javaTransientMapping;
		this.metadataComplete = metadataComplete;
	}

	public String getName() {
		return this.javaTransientMapping.getPersistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public void update(JavaTransientMapping javaTransientMapping) {
		this.javaTransientMapping = javaTransientMapping;
	}
	
	
	public TextRange nameTextRange() {
		return null;
	}

}
