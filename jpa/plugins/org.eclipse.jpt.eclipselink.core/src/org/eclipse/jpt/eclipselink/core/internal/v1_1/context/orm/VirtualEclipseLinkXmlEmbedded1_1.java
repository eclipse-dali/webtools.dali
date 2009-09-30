/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlEmbedded;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualEclipseLinkXmlEmbedded1_1 extends XmlEmbedded
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaEmbeddedMapping javaAttributeMapping;

	protected final VirtualEclipseLinkXmlEmbedded virtualXmlEmbedded;
		
	public VirtualEclipseLinkXmlEmbedded1_1(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaEmbeddedMapping;
		this.virtualXmlEmbedded = new VirtualEclipseLinkXmlEmbedded(ormTypeMapping, javaEmbeddedMapping);
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlEmbedded.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlEmbedded.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlEmbedded.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlEmbedded.getNameTextRange();
	}

	@Override
	public EList<XmlAttributeOverride> getAttributeOverrides() {
		return this.virtualXmlEmbedded.getAttributeOverrides();
	}
	
	@Override
	public XmlAccessMethods getAccessMethods() {
		return this.virtualXmlEmbedded.getAccessMethods();
	}
	
	@Override
	public void setAccessMethods(XmlAccessMethods value) {
		this.virtualXmlEmbedded.setAccessMethods(value);
	}
	
	@Override
	public EList<XmlProperty> getProperties() {
		return this.virtualXmlEmbedded.getProperties();
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
