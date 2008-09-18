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
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
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
public class VirtualXmlBasic extends AbstractJpaEObject implements XmlBasic
{
	JavaBasicMapping javaBasicMapping;

	protected final VirtualXmlColumn column;

	protected boolean metadataComplete;
		
	public VirtualXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping, boolean metadataComplete) {
		super();
		this.javaBasicMapping = javaBasicMapping;
		this.metadataComplete = metadataComplete;
		this.column = new VirtualXmlColumn(ormTypeMapping, javaBasicMapping.getColumn(), metadataComplete);
	}

	public String getName() {
		return this.javaBasicMapping.getPersistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public XmlColumn getColumn() {
		return this.column;
	}

	public void setColumn(XmlColumn value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public FetchType getFetch() {
		if (this.metadataComplete) {
			return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaBasicMapping.getDefaultFetch());
		}
		return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaBasicMapping.getFetch());
	}

	public void setFetch(FetchType newFetch) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Boolean getOptional() {
		if (this.metadataComplete) {
			return this.javaBasicMapping.getDefaultOptional();
		}
		return this.javaBasicMapping.getOptional();
	}

	public void setOptional(Boolean newOptional) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public boolean isLob() {
		if (this.metadataComplete) {
			return false;
		}
		return this.javaBasicMapping.getConverter().getType() == Converter.LOB_CONVERTER;
	}

	public void setLob(boolean newLob) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public TemporalType getTemporal() {
		if (this.metadataComplete) {
			return null;
		}
		if (this.javaBasicMapping.getConverter().getType() == Converter.TEMPORAL_CONVERTER) {
			org.eclipse.jpt.core.context.TemporalType javaTemporalType = ((TemporalConverter) this.javaBasicMapping.getConverter()).getTemporalType();
			return org.eclipse.jpt.core.context.TemporalType.toOrmResourceModel(javaTemporalType);
		}
		return null;
	}

	public void setTemporal(TemporalType newTemporal){
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public EnumType getEnumerated() {
		if (this.javaBasicMapping.getConverter().getType() != Converter.ENUMERATED_CONVERTER) {
			return null;
		}
		org.eclipse.jpt.core.context.EnumType javaEnumeratedType;
		if (this.metadataComplete) {
			if (this.javaBasicMapping.getDefaultConverter().getType() != Converter.ENUMERATED_CONVERTER) {
				return null;
			}
			javaEnumeratedType = ((EnumeratedConverter) this.javaBasicMapping.getDefaultConverter()).getSpecifiedEnumType();
		}
		else {
			javaEnumeratedType = ((EnumeratedConverter) this.javaBasicMapping.getConverter()).getEnumType();
		}
		return org.eclipse.jpt.core.context.EnumType.toOrmResourceModel(javaEnumeratedType);
	}

	public void setEnumerated(EnumType newEnumerated) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}


	public void update(JavaBasicMapping javaBasicMapping) {
		this.javaBasicMapping = javaBasicMapping;
		this.column.update(javaBasicMapping.getColumn());
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
