/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
public class VirtualXmlId extends VirtualXmlAttributeMapping<JavaIdMapping> implements XmlId
{
	protected final VirtualXmlColumn column;

	protected final VirtualXmlGeneratedValue virtualGeneratedValue;
	
	protected final VirtualXmlTableGenerator virtualTableGenerator;
	
	protected final VirtualXmlSequenceGenerator virtualSequenceGenerator;
	
		
	public VirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		super(ormTypeMapping, javaIdMapping);
		this.column = new VirtualXmlColumn(ormTypeMapping, javaIdMapping.getColumn());
		this.virtualGeneratedValue = new VirtualXmlGeneratedValue(javaIdMapping, this.isOrmMetadataComplete());
		this.virtualTableGenerator = new VirtualXmlTableGenerator(javaIdMapping, this.isOrmMetadataComplete());
		this.virtualSequenceGenerator = new VirtualXmlSequenceGenerator(javaIdMapping, this.isOrmMetadataComplete());
	}

	public String getName() {
		return this.javaAttributeMapping.getPersistentAttribute().getName();
	}

	public void setName(@SuppressWarnings("unused")String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public XmlColumn getColumn() {
		return this.column;
	}

	public void setColumn(@SuppressWarnings("unused")XmlColumn value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

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

	public void setTemporal(@SuppressWarnings("unused")TemporalType newTemporal){
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}


	public XmlGeneratedValue getGeneratedValue() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.getGeneratedValue() != null) {
			return this.virtualGeneratedValue;
		}
		return null;
	}
	
	public void setGeneratedValue(@SuppressWarnings("unused")XmlGeneratedValue value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public XmlSequenceGenerator getSequenceGenerator() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.getSequenceGenerator() != null) {
			return this.virtualSequenceGenerator;
		}
		return null;
	}

	public void setSequenceGenerator(@SuppressWarnings("unused")XmlSequenceGenerator value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public XmlTableGenerator getTableGenerator() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.getTableGenerator() != null) {
			return this.virtualTableGenerator;
		}
		return null;
	}

	public void setTableGenerator(@SuppressWarnings("unused")XmlTableGenerator value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public EnumType getEnumerated() {
		throw new UnsupportedOperationException("enumerated not supported on id mappings"); //$NON-NLS-1$
	}
	
	public void setEnumerated(@SuppressWarnings("unused")EnumType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public boolean isLob() {
		throw new UnsupportedOperationException("lob not supported on id mappings"); //$NON-NLS-1$
	}
	
	public void setLob(@SuppressWarnings("unused")boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public TextRange getNameTextRange() {
		return null;
	}
	
	public TextRange getEnumeratedTextRange() {
		return null;
	}
	
	public TextRange getLobTextRange() {
		return null;
	}
	
	public TextRange getTemporalTextRange() {
		return null;
	}

}
