/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;
import org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedId;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class EclipseLink1_1VirtualXmlEmbeddedId extends XmlEmbeddedId
{
		
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaEmbeddedIdMapping javaAttributeMapping;

	protected final EclipseLinkVirtualXmlEmbeddedId virtualXmlEmbeddedId;
		
	public EclipseLink1_1VirtualXmlEmbeddedId(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaEmbeddedIdMapping;
		this.virtualXmlEmbeddedId = new EclipseLinkVirtualXmlEmbeddedId(ormTypeMapping, javaEmbeddedIdMapping);
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlEmbeddedId.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlEmbeddedId.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlEmbeddedId.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlEmbeddedId.getNameTextRange();
	}

	@Override
	public EList<XmlAttributeOverride> getAttributeOverrides() {
		return this.virtualXmlEmbeddedId.getAttributeOverrides();
	}
	
	@Override
	public XmlAccessMethods getAccessMethods() {
		return this.virtualXmlEmbeddedId.getAccessMethods();
	}
	
	@Override
	public void setAccessMethods(XmlAccessMethods value) {
		this.virtualXmlEmbeddedId.setAccessMethods(value);
	}
	
	@Override
	public EList<XmlProperty> getProperties() {
		return this.virtualXmlEmbeddedId.getProperties();
	}
	
	@Override
	public AccessType getAccess() {
		return org.eclipse.jpt.core.context.AccessType.toOrmResourceModel(this.javaAttributeMapping.getPersistentAttribute().getAccess());
	}
	
	@Override
	public void setAccess(AccessType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
}
