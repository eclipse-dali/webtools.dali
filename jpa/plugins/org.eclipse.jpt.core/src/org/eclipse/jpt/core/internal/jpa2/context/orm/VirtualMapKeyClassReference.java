/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.jpa2.context.CollectionMapping2_0;
import org.eclipse.jpt.core.resource.orm.XmlClassReference;

public class VirtualMapKeyClassReference extends XmlClassReference
{
	CollectionMapping2_0 javaCollectionMapping;

	public VirtualMapKeyClassReference(CollectionMapping2_0 collectionMapping) {
		super();
		this.javaCollectionMapping = collectionMapping;
	}

	@Override
	public String getClassName() {
		return this.javaCollectionMapping.getSpecifiedMapKeyClass();
	}

	@Override
	public void setClassName(String newClassName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
}
