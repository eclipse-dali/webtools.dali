/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.TemporalType;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseTemporalConverter;

public class GenericOrmBaseTemporalConverter
	extends AbstractOrmConverter<OrmBaseTemporalConverter.ParentAdapter>
	implements OrmBaseTemporalConverter
{
	protected TemporalType temporalType;

	public GenericOrmBaseTemporalConverter(OrmBaseTemporalConverter.ParentAdapter parentAdapter) {
		super(parentAdapter);
		this.temporalType = this.buildTemporalType();
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
		this.parentAdapter.setXmlTemporalType(TemporalType.toOrmResourceModel(temporalType));
	}

	protected TemporalType buildTemporalType() {
		return TemporalType.fromOrmResourceModel(this.parentAdapter.getXmlTemporalType());
	}


	// ********** misc **********

	public Class<? extends Converter> getType() {
		return BaseTemporalConverter.class;
	}

	public void initialize() {
		// start with DATE(?)
		this.temporalType = TemporalType.DATE;
		this.setXmlTemporal(this.temporalType);
	}


	// ********** validation **********
	
	@Override
	protected TextRange getXmlValidationTextRange() {
		return this.parentAdapter.getTemporalTextRange();
	}
}
