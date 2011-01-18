/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.orm.OrmVirtualColumn;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmVirtualBaseColumn;

/**
 * <code>orm.xml</code> virtual column
 */
public class GenericOrmVirtualColumn
	extends AbstractOrmVirtualBaseColumn<OrmVirtualColumn.Owner, Column>
	implements OrmVirtualColumn
{
	protected Integer specifiedLength;
	protected int defaultLength;

	protected Integer specifiedPrecision;
	protected int defaultPrecision;

	protected Integer specifiedScale;
	protected int defaultScale;


	public GenericOrmVirtualColumn(JpaContextNode parent, OrmVirtualColumn.Owner owner) {
		super(parent, owner);
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();

		this.setSpecifiedLength(this.buildSpecifiedLength());
		this.setDefaultLength(this.buildDefaultLength());

		this.setSpecifiedPrecision(this.buildSpecifiedPrecision());
		this.setDefaultPrecision(this.buildDefaultPrecision());

		this.setSpecifiedScale(this.buildSpecifiedScale());
		this.setDefaultScale(this.buildDefaultScale());
	}


	// ********** column **********

	@Override
	public Column getOverriddenColumn() {
		return this.owner.resolveOverriddenColumn();
	}


	// ********** length **********

	public int getLength() {
		return (this.specifiedLength != null) ? this.specifiedLength.intValue() : this.defaultLength;
	}

	public Integer getSpecifiedLength() {
		return this.specifiedLength;
	}

	protected void setSpecifiedLength(Integer length) {
		Integer old = this.specifiedLength;
		this.specifiedLength = length;
		this.firePropertyChanged(SPECIFIED_LENGTH_PROPERTY, old, length);
	}

	protected Integer buildSpecifiedLength() {
		return this.getOverriddenColumn().getSpecifiedLength();
	}

	public int getDefaultLength() {
		return this.defaultLength;
	}

	protected void setDefaultLength(int length) {
		int old = this.defaultLength;
		this.defaultLength = length;
		this.firePropertyChanged(DEFAULT_LENGTH_PROPERTY, old, length);
	}

	protected int buildDefaultLength() {
		return DEFAULT_LENGTH;
	}


	// ********** precision **********

	public int getPrecision() {
		return (this.specifiedPrecision != null) ? this.specifiedPrecision.intValue() : this.defaultPrecision;
	}

	public Integer getSpecifiedPrecision() {
		return this.specifiedPrecision;
	}

	protected void setSpecifiedPrecision(Integer precision) {
		Integer old = this.specifiedPrecision;
		this.specifiedPrecision = precision;
		this.firePropertyChanged(SPECIFIED_PRECISION_PROPERTY, old, precision);
	}

	protected Integer buildSpecifiedPrecision() {
		return this.getOverriddenColumn().getSpecifiedPrecision();
	}

	public int getDefaultPrecision() {
		return this.defaultPrecision;
	}

	protected void setDefaultPrecision(int precision) {
		int old = this.defaultPrecision;
		this.defaultPrecision = precision;
		this.firePropertyChanged(DEFAULT_PRECISION_PROPERTY, old, precision);
	}

	protected int buildDefaultPrecision() {
		return DEFAULT_PRECISION;
	}


	// ********** scale **********

	public int getScale() {
		return (this.specifiedScale != null) ? this.specifiedScale.intValue() : this.defaultScale;
	}

	public Integer getSpecifiedScale() {
		return this.specifiedScale;
	}

	protected void setSpecifiedScale(Integer scale) {
		Integer old = this.specifiedScale;
		this.specifiedScale = scale;
		this.firePropertyChanged(SPECIFIED_SCALE_PROPERTY, old, scale);
	}

	protected Integer buildSpecifiedScale() {
		return this.getOverriddenColumn().getSpecifiedScale();
	}

	public int getDefaultScale() {
		return this.defaultScale;
	}

	protected void setDefaultScale(int scale) {
		int old = this.defaultScale;
		this.defaultScale = scale;
		this.firePropertyChanged(DEFAULT_SCALE_PROPERTY, old, scale);
	}

	protected int buildDefaultScale() {
		return DEFAULT_SCALE;
	}
}
