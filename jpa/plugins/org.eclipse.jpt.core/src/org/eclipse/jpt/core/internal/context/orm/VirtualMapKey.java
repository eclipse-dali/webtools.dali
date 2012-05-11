/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.CollectionMapping;
import org.eclipse.jpt.core.resource.orm.MapKey;

public class VirtualMapKey extends MapKey
{
	CollectionMapping javaCollectionMapping;
	
	public VirtualMapKey(CollectionMapping collectionMapping) {
		super();
		this.javaCollectionMapping = collectionMapping;
	}

	@Override
	public String getName() {
		return this.javaCollectionMapping.getSpecifiedMapKey();
	}

	@Override
	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
}
