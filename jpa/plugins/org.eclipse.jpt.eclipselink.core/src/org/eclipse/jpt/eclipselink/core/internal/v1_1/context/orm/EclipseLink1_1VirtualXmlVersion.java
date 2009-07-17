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
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.EnumType;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkVirtualXmlVersion;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlVersion;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class EclipseLink1_1VirtualXmlVersion extends XmlVersion
{
		
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaVersionMapping javaAttributeMapping;

	protected final EclipseLinkVirtualXmlVersion virtualXmlVersion;
	
	public EclipseLink1_1VirtualXmlVersion(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaVersionMapping;
		this.virtualXmlVersion = new EclipseLinkVirtualXmlVersion(ormTypeMapping, javaVersionMapping);
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlVersion.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlVersion.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlVersion.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlVersion.getNameTextRange();
	}

	@Override
	public XmlColumn getColumn() {
		return this.virtualXmlVersion.getColumn();
	}

	@Override
	public void setColumn(XmlColumn value) {
		this.virtualXmlVersion.setColumn(value);
	}

	@Override
	public TemporalType getTemporal() {
		return this.virtualXmlVersion.getTemporal();
	}

	@Override
	public void setTemporal(TemporalType newTemporal){
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public EnumType getEnumerated() {
		return this.virtualXmlVersion.getEnumerated();
	}
	
	@Override
	public void setEnumerated(EnumType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public boolean isLob() {
		return this.virtualXmlVersion.isLob();
	}
	
	@Override
	public void setLob(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}	
	
	@Override
	public TextRange getEnumeratedTextRange() {
		return this.virtualXmlVersion.getEnumeratedTextRange();
	}
	
	@Override
	public TextRange getLobTextRange() {
		return this.virtualXmlVersion.getLobTextRange();
	}
	
	@Override
	public TextRange getTemporalTextRange() {
		return this.virtualXmlVersion.getTemporalTextRange();
	}

	@Override
	public Boolean getMutable() {
		return this.virtualXmlVersion.getMutable();
	}
	
	@Override
	public void setMutable(Boolean value) {
		this.virtualXmlVersion.setMutable(value);
	}

	@Override
	public String getConvert() {
		return this.virtualXmlVersion.getConvert();
	}
	
	@Override
	public void setConvert(String value) {
		this.virtualXmlVersion.setConvert(value);
	}

	@Override
	public TextRange getMutableTextRange() {
		return this.virtualXmlVersion.getMutableTextRange();
	}
	
	@Override
	public TextRange getConvertTextRange() {
		return this.virtualXmlVersion.getConvertTextRange();
	}

	@Override
	public XmlConverter getConverter() {
		return this.virtualXmlVersion.getConverter();
	}

	@Override
	public void setConverter(XmlConverter value) {
		this.virtualXmlVersion.setConverter(value);
	}

	@Override
	public XmlObjectTypeConverter getObjectTypeConverter() {
		return this.virtualXmlVersion.getObjectTypeConverter();
	}

	@Override
	public void setObjectTypeConverter(XmlObjectTypeConverter value) {
		this.virtualXmlVersion.setObjectTypeConverter(value);
	}

	@Override
	public XmlStructConverter getStructConverter() {
		return this.virtualXmlVersion.getStructConverter();
	}

	@Override
	public void setStructConverter(XmlStructConverter value) {
		this.virtualXmlVersion.setStructConverter(value);
	}

	@Override
	public XmlTypeConverter getTypeConverter() {
		return this.virtualXmlVersion.getTypeConverter();
	}

	@Override
	public void setTypeConverter(XmlTypeConverter value) {
		this.virtualXmlVersion.setTypeConverter(value);
	}
	
	@Override
	public XmlAccessMethods getAccessMethods() {
		return this.virtualXmlVersion.getAccessMethods();
	}
	
	@Override
	public void setAccessMethods(XmlAccessMethods value) {
		this.virtualXmlVersion.setAccessMethods(value);
	}
	
	@Override
	public EList<XmlProperty> getProperties() {
		return this.virtualXmlVersion.getProperties();
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
