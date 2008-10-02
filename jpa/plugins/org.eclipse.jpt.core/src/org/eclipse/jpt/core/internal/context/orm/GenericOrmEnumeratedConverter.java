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
import org.eclipse.jpt.core.context.EnumType;
import org.eclipse.jpt.core.context.EnumeratedConverter;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.internal.context.persistence.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericOrmEnumeratedConverter extends AbstractXmlContextNode
	implements EnumeratedConverter, OrmConverter
{
	private EnumType specifiedEnumType;
	
	private XmlConvertibleMapping resourceConvertableMapping;
	
	public GenericOrmEnumeratedConverter(OrmAttributeMapping parent, XmlBasic resourceBasic) {
		super(parent);
		this.initialize(resourceBasic);
	}
	
	@Override
	public OrmAttributeMapping getParent() {
		return (OrmAttributeMapping) super.getParent();
	}

	public String getType() {
		return Converter.ENUMERATED_CONVERTER;
	}
	
	public EnumType getEnumType() {
		return getSpecifiedEnumType() == null ? getDefaultEnumType() : getSpecifiedEnumType();
	}
	
	public EnumType getDefaultEnumType() {
		//there is no default enumType in xml, if you specify the enumerated element, you must
		//specify either ORDINAL or STRING
		return null;
	}
	
	public EnumType getSpecifiedEnumType() {
		return this.specifiedEnumType;
	}
	
	public void setSpecifiedEnumType(EnumType newSpecifiedEnumType) {
		EnumType oldSpecifiedEnumType = this.specifiedEnumType;
		this.specifiedEnumType = newSpecifiedEnumType;
		this.resourceConvertableMapping.setEnumerated(EnumType.toOrmResourceModel(newSpecifiedEnumType));
		firePropertyChanged(EnumeratedConverter.SPECIFIED_ENUM_TYPE_PROPERTY, oldSpecifiedEnumType, newSpecifiedEnumType);
	}

	protected void setSpecifiedEnumType_(EnumType newSpecifiedEnumType) {
		EnumType oldSpecifiedEnumType = this.specifiedEnumType;
		this.specifiedEnumType = newSpecifiedEnumType;
		firePropertyChanged(EnumeratedConverter.SPECIFIED_ENUM_TYPE_PROPERTY, oldSpecifiedEnumType, newSpecifiedEnumType);
	}
	
	protected void initialize(XmlConvertibleMapping resourceConvertableMapping) {
		this.resourceConvertableMapping = resourceConvertableMapping;
		this.specifiedEnumType = this.specifiedEnumType(this.resourceConvertableMapping);
	}
	
	public void update(XmlConvertibleMapping resourceConvertableMapping) {
		this.resourceConvertableMapping = resourceConvertableMapping;		
		this.setSpecifiedEnumType_(this.specifiedEnumType(this.resourceConvertableMapping));
	}
	
	protected EnumType specifiedEnumType(XmlConvertibleMapping resourceConvertableMapping) {
		return EnumType.fromOrmResourceModel(resourceConvertableMapping.getEnumerated());
	}
	
	public TextRange getValidationTextRange() {
		return this.resourceConvertableMapping.getEnumeratedTextRange();
	}

	public void addToResourceModel() {
		this.resourceConvertableMapping.setEnumerated(org.eclipse.jpt.core.resource.orm.EnumType.ORDINAL);
	}
	
	public void removeFromResourceModel() {
		this.resourceConvertableMapping.setEnumerated(null);
	}
	
}
