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
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlBasicMap;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMap;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualEclipseLinkXmlBasicMap1_1 extends XmlBasicMap
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaEclipseLinkBasicMapMapping javaAttributeMapping;

	protected final VirtualEclipseLinkXmlBasicMap virtualXmlBasicMap;
		
	public VirtualEclipseLinkXmlBasicMap1_1(OrmTypeMapping ormTypeMapping, JavaEclipseLinkBasicMapMapping javaBasicMapMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaBasicMapMapping;
		this.virtualXmlBasicMap = new VirtualEclipseLinkXmlBasicMap(ormTypeMapping, javaBasicMapMapping);
	}
	
	public boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlBasicMap.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlBasicMap.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlBasicMap.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlBasicMap.getNameTextRange();
	}

	
	@Override
	public XmlAccessMethods getAccessMethods() {
		return this.virtualXmlBasicMap.getAccessMethods();
	}
	
	@Override
	public void setAccessMethods(XmlAccessMethods value) {
		this.virtualXmlBasicMap.setAccessMethods(value);
	}
	
	
	@Override
	public EList<XmlProperty> getProperties() {
		return this.virtualXmlBasicMap.getProperties();
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
