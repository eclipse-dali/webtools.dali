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
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * VirtualVersion is an implementation of Version used when there is 
 * no tag in the orm.xml and an underlying javaVersionMapping exists.
 */
public class VirtualXmlNullAttributeMapping extends XmlNullAttributeMapping
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaAttributeMapping javaAttributeMapping;
	
	protected final VirtualXmlAttributeMapping virtualXmlAttributeMapping;

	public VirtualXmlNullAttributeMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaAttributeMapping;
		this.virtualXmlAttributeMapping = new VirtualXmlAttributeMapping(ormTypeMapping, javaAttributeMapping);
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	public String getMappingKey() {
		if (this.isOrmMetadataComplete()) {
			return this.javaAttributeMapping.getPersistentAttribute().getDefaultMappingKey();
		}
		return this.javaAttributeMapping.getKey();
	}
	
	@Override
	public String getName() {
		return this.javaAttributeMapping.getPersistentAttribute().getName();
	}

	@Override
	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public TextRange getNameTextRange() {
		return null;
	}
}
