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
import org.eclipse.jpt.core.context.EnumeratedConverter;
import org.eclipse.jpt.core.context.TemporalConverter;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.EnumType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualXmlBasic extends XmlBasic
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaBasicMapping javaAttributeMapping;

	protected final VirtualXmlAttributeMapping virtualXmlAttributeMapping;

	protected final VirtualXmlColumn column;
	
	public VirtualXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaBasicMapping;
		this.virtualXmlAttributeMapping = new VirtualXmlAttributeMapping(ormTypeMapping, javaBasicMapping);
		this.column = new VirtualXmlColumn(ormTypeMapping, javaBasicMapping.getColumn());
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
	public FetchType getFetch() {
		if (this.isOrmMetadataComplete()) {
			return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaAttributeMapping.getDefaultFetch());
		}
		return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaAttributeMapping.getFetch());
	}

	@Override
	public void setFetch(FetchType newFetch) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public Boolean getOptional() {
		if (this.isOrmMetadataComplete()) {
			return Boolean.valueOf(this.javaAttributeMapping.isDefaultOptional());
		}
		return Boolean.valueOf(this.javaAttributeMapping.isOptional());
	}

	@Override
	public void setOptional(Boolean newOptional) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public boolean isLob() {
		if (this.isOrmMetadataComplete()) {
			return false;
		}
		return this.javaAttributeMapping.getConverter().getType() == Converter.LOB_CONVERTER;
	}

	@Override
	public void setLob(boolean newLob) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public TemporalType getTemporal() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.getConverter().getType() == Converter.TEMPORAL_CONVERTER) {
			org.eclipse.jpt.core.context.TemporalType javaTemporalType = ((TemporalConverter) this.javaAttributeMapping.getConverter()).getTemporalType();
			return org.eclipse.jpt.core.context.TemporalType.toOrmResourceModel(javaTemporalType);
		}
		return null;
	}

	@Override
	public void setTemporal(TemporalType newTemporal){
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public EnumType getEnumerated() {
		if (this.javaAttributeMapping.getConverter().getType() != Converter.ENUMERATED_CONVERTER) {
			return null;
		}
		org.eclipse.jpt.core.context.EnumType javaEnumeratedType;
		if (this.isOrmMetadataComplete()) {
			if (this.javaAttributeMapping.getDefaultConverter().getType() != Converter.ENUMERATED_CONVERTER) {
				return null;
			}
			javaEnumeratedType = ((EnumeratedConverter) this.javaAttributeMapping.getDefaultConverter()).getSpecifiedEnumType();
		}
		else {
			javaEnumeratedType = ((EnumeratedConverter) this.javaAttributeMapping.getConverter()).getEnumType();
		}
		return org.eclipse.jpt.core.context.EnumType.toOrmResourceModel(javaEnumeratedType);
	}

	@Override
	public void setEnumerated(EnumType newEnumerated) {
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
