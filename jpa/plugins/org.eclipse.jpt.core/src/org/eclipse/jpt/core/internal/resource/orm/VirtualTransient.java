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

import org.eclipse.jpt.core.internal.context.java.IJavaTransientMapping;
import org.eclipse.jpt.core.internal.resource.common.JpaEObject;

/**
 * VirtualTransient is an implementation of Transient used when there is 
 * no tag in the orm.xml and an underlying javaTransientMapping exists.
 */
public class VirtualTransient extends JpaEObject implements Transient
{
	IJavaTransientMapping javaTransientMapping;

	protected boolean metadataComplete;
	
	public VirtualTransient(IJavaTransientMapping javaTransientMapping, boolean metadataComplete) {
		super();
		this.javaTransientMapping = javaTransientMapping;
		this.metadataComplete = metadataComplete;
	}

	public String getName() {
		return this.javaTransientMapping.persistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public void update(IJavaTransientMapping javaTransientMapping) {
		this.javaTransientMapping = javaTransientMapping;
	}
}
