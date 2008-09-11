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
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericOrmTemporalConverter extends AbstractOrmJpaContextNode
	implements TemporalConverter, OrmConverter
{
	private TemporalType temporalType;
	
	private XmlConvertibleMapping resourceConvertableMapping;
	
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
		this.resourceConvertableMapping.setTemporal(TemporalType.toOrmResourceModel(newTemporalType));
		firePropertyChanged(TEMPORAL_TYPE_PROPERTY, oldTemporalType, newTemporalType);
	}
	
	protected void setTemporalType_(TemporalType newTemporalType) {
		TemporalType oldTemporalType = this.temporalType;
		this.temporalType = newTemporalType;
		firePropertyChanged(TEMPORAL_TYPE_PROPERTY, oldTemporalType, newTemporalType);
	}

	
	protected void initialize(XmlConvertibleMapping resourceConvertableMapping) {
		this.resourceConvertableMapping = resourceConvertableMapping;
		this.temporalType = this.temporalType(this.resourceConvertableMapping);
	}
	
	public void update(XmlConvertibleMapping resourceConvertableMapping) {
		this.resourceConvertableMapping = resourceConvertableMapping;		
		this.setTemporalType_(this.temporalType(this.resourceConvertableMapping));
	}
	
	protected TemporalType temporalType(XmlConvertibleMapping resourceConvertableMapping) {
		return TemporalType.fromOrmResourceModel(resourceConvertableMapping.getTemporal());
	}

	
	public TextRange getValidationTextRange() {
		return this.resourceConvertableMapping.getTemporalTextRange();
	}
	
	public void addToResourceModel() {
		this.resourceConvertableMapping.setTemporal(org.eclipse.jpt.core.resource.orm.TemporalType.DATE);
	}
	
	public void removeFromResourceModel() {
		this.resourceConvertableMapping.setTemporal(null);
	}

}
