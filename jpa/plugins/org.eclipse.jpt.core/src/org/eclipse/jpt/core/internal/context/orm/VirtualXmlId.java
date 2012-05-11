/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.TemporalConverter;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.EnumType;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * VirtualId is an implementation of Id used when there is 
 * no tag in the orm.xml and an underlying javaIdMapping exists.
 */
public class VirtualXmlId extends XmlId
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaIdMapping javaAttributeMapping;

	protected final VirtualXmlAttributeMapping virtualXmlAttributeMapping;

	protected final VirtualXmlColumn column;

	protected final VirtualXmlGeneratedValue virtualGeneratedValue;
	
	protected final VirtualXmlTableGenerator virtualTableGenerator;
	
	protected final VirtualXmlSequenceGenerator virtualSequenceGenerator;
	
		
	public VirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaIdMapping;
		this.virtualXmlAttributeMapping = new VirtualXmlAttributeMapping(ormTypeMapping, javaIdMapping);
		this.column = new VirtualXmlColumn(ormTypeMapping, javaIdMapping.getColumn());
		this.virtualGeneratedValue = new VirtualXmlGeneratedValue(javaIdMapping, this.isOrmMetadataComplete());
		this.virtualTableGenerator = new VirtualXmlTableGenerator(javaIdMapping.getGeneratorContainer(), this.isOrmMetadataComplete());
		this.virtualSequenceGenerator = new VirtualXmlSequenceGenerator(javaIdMapping.getGeneratorContainer(), this.isOrmMetadataComplete());
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlAttributeMapping.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlAttributeMapping.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlAttributeMapping.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlAttributeMapping.getNameTextRange();
	}

	@Override
	public XmlColumn getColumn() {
		return this.column;
	}

	@Override
	public void setColumn(XmlColumn value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public TemporalType getTemporal() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.getConverter().getType() == Converter.TEMPORAL_CONVERTER) {
			org.eclipse.jpt.core.context.TemporalType javaTemporalType = ((TemporalConverter) this.javaAttributeMapping.getConverter()).getTemporalType();
			return  org.eclipse.jpt.core.context.TemporalType.toOrmResourceModel(javaTemporalType);
		}
		return null;
	}

	@Override
	public void setTemporal(TemporalType newTemporal){
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}


	@Override
	public XmlGeneratedValue getGeneratedValue() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.getGeneratedValue() != null) {
			return this.virtualGeneratedValue;
		}
		return null;
	}
	
	@Override
	public void setGeneratedValue(XmlGeneratedValue value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
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
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.getGeneratorContainer().getTableGenerator() != null) {
			return this.virtualTableGenerator;
		}
		return null;
	}

	@Override
	public void setTableGenerator(XmlTableGenerator value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	//see eclipselink bug 247078 for info one why I made the interface XmlConvertibleMapping
	@Override
	public EnumType getEnumerated() {
		throw new UnsupportedOperationException("enumerated not supported on id mappings"); //$NON-NLS-1$
	}
	
	@Override
	public void setEnumerated(EnumType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	//see eclipselink bug 247078 for info one why I made the interface XmlConvertibleMapping
	@Override
	public boolean isLob() {
		throw new UnsupportedOperationException("lob not supported on id mappings"); //$NON-NLS-1$
	}
	
	@Override
	public void setLob(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public TextRange getEnumeratedTextRange() {
		return null;
	}
	
	@Override
	public TextRange getLobTextRange() {
		return null;
	}
	
	@Override
	public TextRange getTemporalTextRange() {
		return null;
	}

}
