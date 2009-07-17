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
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.EnumType;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkVirtualXmlId;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlId;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class EclipseLink1_1VirtualXmlId extends XmlId
{
		
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaIdMapping javaAttributeMapping;

	protected final EclipseLinkVirtualXmlId virtualXmlId;
		
	public EclipseLink1_1VirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaIdMapping;
		this.virtualXmlId = new EclipseLinkVirtualXmlId(ormTypeMapping, javaIdMapping);
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlId.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlId.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlId.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlId.getNameTextRange();
	}

	@Override
	public XmlColumn getColumn() {
		return this.virtualXmlId.getColumn();
	}

	@Override
	public void setColumn(XmlColumn value) {
		this.virtualXmlId.setColumn(value);
	}

	@Override
	public TemporalType getTemporal() {
		return this.virtualXmlId.getTemporal();
	}

	@Override
	public void setTemporal(TemporalType newTemporal){
		this.virtualXmlId.setTemporal(newTemporal);
	}
	
	@Override
	public TextRange getTemporalTextRange() {
		return this.virtualXmlId.getTemporalTextRange();
	}

	@Override
	public XmlGeneratedValue getGeneratedValue() {
		return this.virtualXmlId.getGeneratedValue();
	}
	
	@Override
	public void setGeneratedValue(XmlGeneratedValue value) {
		this.virtualXmlId.setGeneratedValue(value);
	}

	@Override
	public XmlSequenceGenerator getSequenceGenerator() {
		return this.virtualXmlId.getSequenceGenerator();
	}

	@Override
	public void setSequenceGenerator(XmlSequenceGenerator value) {
		this.virtualXmlId.setSequenceGenerator(value);
	}

	@Override
	public XmlTableGenerator getTableGenerator() {
		return this.virtualXmlId.getTableGenerator();
	}

	@Override
	public void setTableGenerator(XmlTableGenerator value) {
		this.virtualXmlId.setTableGenerator(value);
	}
	
	@Override
	public EnumType getEnumerated() {
		return this.virtualXmlId.getEnumerated();
	}
	
	@Override
	public void setEnumerated(EnumType value) {
		this.virtualXmlId.setEnumerated(value);
	}
	
	@Override
	public TextRange getEnumeratedTextRange() {
		return this.virtualXmlId.getEnumeratedTextRange();
	}
	
	@Override
	public boolean isLob() {
		return this.virtualXmlId.isLob();
	}
	
	@Override
	public void setLob(boolean value) {
		this.virtualXmlId.setLob(value);
	}
	
	@Override
	public TextRange getLobTextRange() {
		return this.virtualXmlId.getLobTextRange();
	}
		
	@Override
	public Boolean getMutable() {
		return this.virtualXmlId.getMutable();
	}
	
	@Override
	public void setMutable(Boolean value) {
		this.virtualXmlId.setMutable(value);
	}

	@Override
	public String getConvert() {
		return this.virtualXmlId.getConvert();
	}
	
	@Override
	public void setConvert(String value) {
		this.virtualXmlId.setConvert(value);
	}
	
	@Override
	public XmlConverter getConverter() {
		return this.virtualXmlId.getConverter();
	}

	@Override
	public void setConverter(XmlConverter value) {
		this.virtualXmlId.setConverter(value);
	}

	@Override
	public XmlObjectTypeConverter getObjectTypeConverter() {
		return this.virtualXmlId.getObjectTypeConverter();
	}

	@Override
	public void setObjectTypeConverter(XmlObjectTypeConverter value) {
		this.virtualXmlId.setObjectTypeConverter(value);
	}

	@Override
	public XmlStructConverter getStructConverter() {
		return this.virtualXmlId.getStructConverter();
	}

	@Override
	public void setStructConverter(XmlStructConverter value) {
		this.virtualXmlId.setStructConverter(value);
	}

	@Override
	public XmlTypeConverter getTypeConverter() {
		return this.virtualXmlId.getTypeConverter();
	}

	@Override
	public void setTypeConverter(XmlTypeConverter value) {
		this.virtualXmlId.setTypeConverter(value);
	}
	
	@Override
	public XmlAccessMethods getAccessMethods() {
		return this.virtualXmlId.getAccessMethods();
	}
	
	@Override
	public void setAccessMethods(XmlAccessMethods value) {
		this.virtualXmlId.setAccessMethods(value);
	}
	
	@Override
	public EList<XmlProperty> getProperties() {
		return this.virtualXmlId.getProperties();
	}
	
	@Override
	public TextRange getMutableTextRange() {
		return this.virtualXmlId.getMutableTextRange();
	}

	@Override
	public TextRange getConvertTextRange() {
		return this.virtualXmlId.getConvertTextRange();
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
