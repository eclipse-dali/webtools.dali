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

import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * VirtualVersion is an implementation of Version used when there is 
 * no tag in the orm.xml and an underlying javaVersionMapping exists.
 */
public class VirtualXmlNullAttributeMapping extends AbstractJpaEObject implements XmlNullAttributeMapping
{
	JavaAttributeMapping javaAttributeMapping;

	protected boolean metadataComplete;
	
	public VirtualXmlNullAttributeMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping, boolean metadataComplete) {
		super();
		this.javaAttributeMapping = javaAttributeMapping;
		this.metadataComplete = metadataComplete;
	}

	public String getName() {
		return this.javaAttributeMapping.getPersistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public void update(JavaAttributeMapping javaAttributeMapping) {
		this.javaAttributeMapping = javaAttributeMapping;
	}
	
	public TextRange getNameTextRange() {
		return null;
	}

}
