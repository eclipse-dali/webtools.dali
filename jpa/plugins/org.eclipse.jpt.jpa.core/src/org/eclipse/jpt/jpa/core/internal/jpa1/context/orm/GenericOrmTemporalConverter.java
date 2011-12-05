/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.TemporalConverter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmTemporalConverter;

public class GenericOrmTemporalConverter
	extends AbstractOrmConverter
	implements OrmTemporalConverter
{
	protected TemporalType temporalType;

	public GenericOrmTemporalConverter(OrmAttributeMapping parent, OrmTemporalConverter.Owner owner) {
		super(parent, owner);
		this.temporalType = this.buildTemporalType();
	}

	@Override
	protected OrmTemporalConverter.Owner getOwner() {
		return (OrmTemporalConverter.Owner) super.getOwner();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setTemporalType_(this.buildTemporalType());
	}


	// ********** temporal type **********

	public TemporalType getTemporalType() {
		return this.temporalType;
	}

	public void setTemporalType(TemporalType temporalType) {
		this.setTemporalType_(temporalType);
		this.setXmlTemporal(temporalType);
	}

	protected void setTemporalType_(TemporalType temporalType) {
		TemporalType old = this.temporalType;
		this.temporalType = temporalType;
		this.firePropertyChanged(TEMPORAL_TYPE_PROPERTY, old, temporalType);
	}

	protected void setXmlTemporal(TemporalType temporalType) {
		this.getOwner().setXmlTemporalType(TemporalType.toOrmResourceModel(temporalType));
	}

	protected TemporalType buildTemporalType() {
		return TemporalType.fromOrmResourceModel(this.getOwner().getXmlTemporalType());
	}


	// ********** misc **********

	public Class<? extends Converter> getType() {
		return TemporalConverter.class;
	}

	public void initialize() {
		// start with DATE(?)
		this.temporalType = TemporalType.DATE;
		this.setXmlTemporal(this.temporalType);
	}


	// ********** validation **********
	
	@Override
	protected TextRange getXmlValidationTextRange() {
		return this.getOwner().getTemporalTextRange();
	}
}
