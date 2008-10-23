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
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.XmlTransient;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * VirtualTransient is an implementation of Transient used when there is 
 * no tag in the orm.xml and an underlying javaTransientMapping exists.
 */
public class VirtualXmlTransient extends VirtualXmlAttributeMapping<JavaTransientMapping> implements XmlTransient
{
	
	public VirtualXmlTransient(OrmTypeMapping ormTypeMapping, JavaTransientMapping javaTransientMapping) {
		super(ormTypeMapping, javaTransientMapping);
	}

	public String getName() {
		return this.javaAttributeMapping.getPersistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}	
	
	public TextRange getNameTextRange() {
		return null;
	}

}
