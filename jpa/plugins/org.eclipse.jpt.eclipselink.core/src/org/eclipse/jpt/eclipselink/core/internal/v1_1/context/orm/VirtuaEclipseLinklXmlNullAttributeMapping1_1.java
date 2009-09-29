/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlNullAttributeMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlAttributeMapping;

/**
 * VirtualVersion is an implementation of Version used when there is 
 * no tag in the orm.xml and an underlying javaVersionMapping exists.
 */
public class VirtuaEclipseLinklXmlNullAttributeMapping1_1
	extends XmlNullAttributeMapping 
	implements XmlAttributeMapping
{
	
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaAttributeMapping javaAttributeMapping;
	
	protected final VirtualEclipseLinkXmlNullAttributeMapping virtualXmlNullAttributeMapping;
	
	public VirtuaEclipseLinklXmlNullAttributeMapping1_1(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaAttributeMapping;
		this.virtualXmlNullAttributeMapping = new VirtualEclipseLinkXmlNullAttributeMapping(ormTypeMapping, javaAttributeMapping);
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
	
	public EList<XmlProperty> getProperties() {
		return this.virtualXmlNullAttributeMapping.getProperties();
	}
}
