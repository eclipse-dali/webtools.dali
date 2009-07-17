/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlId;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.EnumType;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class Generic2_0VirtualXmlId extends XmlId
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaIdMapping javaAttributeMapping;

	protected final VirtualXmlId virtualXmlId;

	protected final Generic2_0VirtualXmlSequenceGenerator virtualSequenceGenerator;
		
	public Generic2_0VirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaIdMapping;
		this.virtualXmlId = new VirtualXmlId(ormTypeMapping, javaIdMapping);
		this.virtualSequenceGenerator = new Generic2_0VirtualXmlSequenceGenerator(javaIdMapping.getGeneratorContainer(), this.isOrmMetadataComplete());
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
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
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.getGeneratorContainer().getSequenceGenerator() != null) {
			return this.virtualSequenceGenerator;
		}
		return null;
	}

	@Override
	public void setSequenceGenerator(XmlSequenceGenerator value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
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
	public AccessType getAccess() {
		return org.eclipse.jpt.core.context.AccessType.toOrmResourceModel(this.javaAttributeMapping.getPersistentAttribute().getAccess());
	}
	
	@Override
	public void setAccess(AccessType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
}
