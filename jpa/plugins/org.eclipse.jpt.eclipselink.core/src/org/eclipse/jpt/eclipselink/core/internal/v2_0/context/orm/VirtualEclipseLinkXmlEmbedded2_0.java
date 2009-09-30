/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlEmbedded1_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualEclipseLinkXmlEmbedded2_0 extends XmlEmbedded
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaEmbeddedMapping javaAttributeMapping;

	protected final VirtualEclipseLinkXmlEmbedded1_1 virtualXmlEmbedded;
		
	public VirtualEclipseLinkXmlEmbedded2_0(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaEmbeddedMapping;
		this.virtualXmlEmbedded = new VirtualEclipseLinkXmlEmbedded1_1(ormTypeMapping, javaEmbeddedMapping);
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
		return this.virtualXmlEmbedded.getAccess();
	}
	
	@Override
	public void setAccess(AccessType value) {
		this.virtualXmlEmbedded.setAccess(null);
	}
	
//	@Override
//	public EList<XmlAttributeOverride> getAssociationOverrides() {
//		EList<XmlAttributeOverride> attributeOverrides = new EObjectContainmentEList<XmlAttributeOverride>(XmlAttributeOverride.class, this, OrmPackage.XML_EMBEDDED__ATTRIBUTE_OVERRIDES);
//		ListIterator<JavaAttributeOverride> javaAttributeOverrides;
//		if (!this.isOrmMetadataComplete()) {
//			javaAttributeOverrides = this.javaAttributeMapping.getAttributeOverrideContainer().attributeOverrides();
//		}
//		else {
//			javaAttributeOverrides = this.javaAttributeMapping.getAttributeOverrideContainer().virtualAttributeOverrides();
//		}
//		for (JavaAttributeOverride javaAttributeOverride : CollectionTools.iterable(javaAttributeOverrides)) {
//			XmlColumn xmlColumn = new VirtualXmlColumn(this.ormTypeMapping, javaAttributeOverride.getColumn());
//			XmlAttributeOverride xmlAttributeOverride = new VirtualXmlAttributeOverride(javaAttributeOverride.getName(), xmlColumn);
//			attributeOverrides.add(xmlAttributeOverride);
//		}
//		return attributeOverrides;
//	}
}
