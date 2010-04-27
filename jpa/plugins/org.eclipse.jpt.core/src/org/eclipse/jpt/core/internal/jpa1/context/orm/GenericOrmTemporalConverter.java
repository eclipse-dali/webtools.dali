/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.TemporalConverter;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericOrmTemporalConverter extends AbstractOrmXmlContextNode
	implements TemporalConverter, OrmConverter
{
	protected TemporalType temporalType;
	
	protected XmlConvertibleMapping resourceConvertibleMapping;
	
	public GenericOrmTemporalConverter(OrmAttributeMapping parent, XmlConvertibleMapping resourceConvertableMapping) {
		super(parent);
		this.initialize(resourceConvertableMapping);
	}

	@Override
	public OrmAttributeMapping getParent() {
		return (OrmAttributeMapping) super.getParent();
	}

	public String getType() {
		return Converter.TEMPORAL_CONVERTER;
	}
	
	public TemporalType getTemporalType() {
		return this.temporalType;
	}

	public void setTemporalType(TemporalType newTemporalType) {
		TemporalType oldTemporalType = this.temporalType;
		this.temporalType = newTemporalType;
		this.resourceConvertibleMapping.setTemporal(TemporalType.toOrmResourceModel(newTemporalType));
		firePropertyChanged(TEMPORAL_TYPE_PROPERTY, oldTemporalType, newTemporalType);
	}
	
	protected void setTemporalType_(TemporalType newTemporalType) {
		TemporalType oldTemporalType = this.temporalType;
		this.temporalType = newTemporalType;
		firePropertyChanged(TEMPORAL_TYPE_PROPERTY, oldTemporalType, newTemporalType);
	}

	
	protected void initialize(XmlConvertibleMapping resourceConvertibleMapping) {
		this.resourceConvertibleMapping = resourceConvertibleMapping;
		this.temporalType = this.temporalType();
	}
	
	public void update() {		
		this.setTemporalType_(this.temporalType());
	}
	
	protected TemporalType temporalType() {
		return TemporalType.fromOrmResourceModel(this.resourceConvertibleMapping.getTemporal());
	}

	
	public TextRange getValidationTextRange() {
		return this.resourceConvertibleMapping.getTemporalTextRange();
	}
	
	public void addToResourceModel() {
		this.resourceConvertibleMapping.setTemporal(org.eclipse.jpt.core.resource.orm.TemporalType.DATE);
	}
	
	public void removeFromResourceModel() {
		this.resourceConvertibleMapping.setTemporal(null);
	}

}
