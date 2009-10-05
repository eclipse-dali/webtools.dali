/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.java.JavaMultiRelationshipMapping;
import org.eclipse.jpt.core.resource.orm.MapKey;

public class VirtualMapKey extends MapKey
{
	JavaMultiRelationshipMapping javaMultiRelationshipMapping;

	protected boolean metadataComplete;
	
	public VirtualMapKey(JavaMultiRelationshipMapping javaMultiRelationshipMapping, boolean metadataComplete) {
		super();
		this.javaMultiRelationshipMapping = javaMultiRelationshipMapping;
		this.metadataComplete = metadataComplete;
	}

	@Override
	public String getName() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaMultiRelationshipMapping.getSpecifiedMapKey();
	}

	@Override
	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
}
