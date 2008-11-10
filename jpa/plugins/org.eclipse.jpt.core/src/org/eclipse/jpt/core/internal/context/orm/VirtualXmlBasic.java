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
public class VirtualXmlBasic extends VirtualXmlAttributeMapping<JavaBasicMapping> implements XmlBasic
{

	protected final VirtualXmlColumn column;
		
	public VirtualXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		super(ormTypeMapping, javaBasicMapping);
		this.column = new VirtualXmlColumn(ormTypeMapping, javaBasicMapping.getColumn());
	}

	public XmlColumn getColumn() {
		return this.column;
	}

	public void setColumn(@SuppressWarnings("unused")XmlColumn value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public FetchType getFetch() {
		if (this.isOrmMetadataComplete()) {
			return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaAttributeMapping.getDefaultFetch());
		}
		return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaAttributeMapping.getFetch());
	}

	public void setFetch(@SuppressWarnings("unused")FetchType newFetch) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public Boolean getOptional() {
		if (this.isOrmMetadataComplete()) {
			return this.javaAttributeMapping.getDefaultOptional();
		}
		return this.javaAttributeMapping.getOptional();
	}

	public void setOptional(@SuppressWarnings("unused")Boolean newOptional) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public boolean isLob() {
		if (this.isOrmMetadataComplete()) {
			return false;
		}
		return this.javaAttributeMapping.getConverter().getType() == Converter.LOB_CONVERTER;
	}

	public void setLob(@SuppressWarnings("unused")boolean newLob) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

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

	public void setTemporal(@SuppressWarnings("unused")TemporalType newTemporal){
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

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

	public void setEnumerated(@SuppressWarnings("unused")EnumType newEnumerated) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
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
