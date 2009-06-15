/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.internal.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkVirtualXmlNullAttributeMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;
import org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlAttributeMapping;

/**
 * VirtualVersion is an implementation of Version used when there is 
 * no tag in the orm.xml and an underlying javaVersionMapping exists.
 */
public class EclipseLink1_1VirtualXmlNullAttributeMapping extends XmlNullAttributeMapping implements XmlAttributeMapping
{
	
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaAttributeMapping javaAttributeMapping;
	
	protected final EclipseLinkVirtualXmlNullAttributeMapping virtualXmlNullAttributeMapping;
	
	public EclipseLink1_1VirtualXmlNullAttributeMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaAttributeMapping;
		this.virtualXmlNullAttributeMapping = new EclipseLinkVirtualXmlNullAttributeMapping(ormTypeMapping, javaAttributeMapping);
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	public String getMappingKey() {
		return this.virtualXmlNullAttributeMapping.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlNullAttributeMapping.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlNullAttributeMapping.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlNullAttributeMapping.getNameTextRange();
	}	
	
	public XmlAccessMethods getAccessMethods() {
		return this.virtualXmlNullAttributeMapping.getAccessMethods();
	}
	
	public void setAccessMethods(XmlAccessMethods value) {
		this.virtualXmlNullAttributeMapping.setAccessMethods(value);
	}
	
	public AccessType getAccess() {
		return org.eclipse.jpt.core.context.AccessType.toOrmResourceModel(this.javaAttributeMapping.getPersistentAttribute().getAccess());
	}

	public void setAccess(AccessType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public EList<XmlProperty> getProperties() {
		return this.virtualXmlNullAttributeMapping.getProperties();
	}

}
