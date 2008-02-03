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

import org.eclipse.jpt.core.internal.context.java.IJavaMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.resource.common.JpaEObject;

public class VirtualMapKey extends JpaEObject implements MapKey
{
	IJavaMultiRelationshipMapping javaMultiRelationshipMapping;

	protected boolean metadataComplete;
	
	public VirtualMapKey(IJavaMultiRelationshipMapping javaMultiRelationshipMapping, boolean metadataComplete) {
		super();
		this.javaMultiRelationshipMapping = javaMultiRelationshipMapping;
		this.metadataComplete = metadataComplete;
	}

	public String getName() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaMultiRelationshipMapping.getMapKey();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public void update(IJavaMultiRelationshipMapping javaMultiRelationshipMapping) {
		this.javaMultiRelationshipMapping = javaMultiRelationshipMapping;
	}
}
