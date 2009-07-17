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
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.EnumType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkVirtualXmlBasic;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlBasic;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class EclipseLink1_1VirtualXmlBasic extends XmlBasic
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaBasicMapping javaAttributeMapping;
	
	protected final EclipseLinkVirtualXmlBasic virtualXmlBasic;
		
	public EclipseLink1_1VirtualXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaBasicMapping;
		this.virtualXmlBasic = new EclipseLinkVirtualXmlBasic(ormTypeMapping, javaBasicMapping);
	}
	protected boolean isOrmMetadataComplete() {
		return this.virtualXmlBasic.isOrmMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlBasic.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlBasic.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlBasic.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlBasic.getNameTextRange();
	}

	@Override
	public XmlColumn getColumn() {
		return this.virtualXmlBasic.getColumn();
	}

	@Override
	public void setColumn(XmlColumn value) {
		this.virtualXmlBasic.setColumn(value);
	}
	
	@Override
	public FetchType getFetch() {
		return this.virtualXmlBasic.getFetch();
	}

	@Override
	public void setFetch(FetchType newFetch) {
		this.virtualXmlBasic.setFetch(newFetch);
	}

	@Override
	public Boolean getOptional() {
		return this.virtualXmlBasic.getOptional();
	}

	@Override
	public void setOptional(Boolean newOptional) {
		this.virtualXmlBasic.setOptional(newOptional);
	}

	@Override
	public boolean isLob() {
		return this.virtualXmlBasic.isLob();
	}

	@Override
	public void setLob(boolean newLob) {
		this.virtualXmlBasic.setLob(newLob);
	}
	
	@Override
	public TextRange getLobTextRange() {
		return this.virtualXmlBasic.getLobTextRange();
	}

	@Override
	public TemporalType getTemporal() {
		return this.virtualXmlBasic.getTemporal();
	}

	@Override
	public void setTemporal(TemporalType setTemporal){
		this.virtualXmlBasic.setTemporal(setTemporal);
	}
	
	@Override
	public TextRange getTemporalTextRange() {
		return this.virtualXmlBasic.getTemporalTextRange();
	}

	@Override
	public EnumType getEnumerated() {
		return this.virtualXmlBasic.getEnumerated();
	}

	@Override
	public void setEnumerated(EnumType setEnumerated) {
		this.virtualXmlBasic.setEnumerated(setEnumerated);
	}
	
	@Override
	public TextRange getEnumeratedTextRange() {
		return null;
	}

	@Override
	public Boolean getMutable() {
		return this.virtualXmlBasic.getMutable();
	}
	
	@Override
	public void setMutable(Boolean value) {
		this.virtualXmlBasic.setMutable(value);
	}
	
	@Override
	public TextRange getMutableTextRange() {
		return this.virtualXmlBasic.getMutableTextRange();
	}

	@Override
	public String getConvert() {
		return this.virtualXmlBasic.getConvert();
	}
	
	@Override
	public void setConvert(String value) {
		this.virtualXmlBasic.setConvert(value);
	}
	
	@Override
	public TextRange getConvertTextRange() {
		return this.virtualXmlBasic.getConvertTextRange();
	}

	@Override
	public XmlConverter getConverter() {
		return this.virtualXmlBasic.getConverter();
	}

	@Override
	public void setConverter(XmlConverter value) {
		this.virtualXmlBasic.setConverter(value);
	}

	@Override
	public XmlObjectTypeConverter getObjectTypeConverter() {
		return this.virtualXmlBasic.getObjectTypeConverter();
	}

	@Override
	public void setObjectTypeConverter(XmlObjectTypeConverter value) {
		this.virtualXmlBasic.setObjectTypeConverter(value);
	}

	@Override
	public XmlStructConverter getStructConverter() {
		return this.virtualXmlBasic.getStructConverter();
	}

	@Override
	public void setStructConverter(XmlStructConverter value) {
		this.virtualXmlBasic.setStructConverter(value);
	}

	@Override
	public XmlTypeConverter getTypeConverter() {
		return this.virtualXmlBasic.getTypeConverter();
	}

	@Override
	public void setTypeConverter(XmlTypeConverter value) {
		this.virtualXmlBasic.setTypeConverter(value);
	}
		
	@Override
	public XmlAccessMethods getAccessMethods() {
		return this.virtualXmlBasic.getAccessMethods();
	}
	
	@Override
	public void setAccessMethods(XmlAccessMethods value) {
		this.virtualXmlBasic.setAccessMethods(value);
	}
	
	@Override
	public EList<XmlProperty> getProperties() {
		return this.virtualXmlBasic.getProperties();
	}
	
	@Override
	public AccessType getAccess() {
		return org.eclipse.jpt.core.context.AccessType.toOrmResourceModel(this.javaAttributeMapping.getPersistentAttribute().getAccess());
	}
	
	@Override
	public void setAccess(AccessType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public XmlTableGenerator getTableGenerator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setTableGenerator(XmlTableGenerator value) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public XmlSequenceGenerator getSequenceGenerator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setSequenceGenerator(XmlSequenceGenerator value) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public XmlGeneratedValue getGeneratedValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setGeneratedValue(XmlGeneratedValue value) {
		// TODO Auto-generated method stub
		
	}
	
}
