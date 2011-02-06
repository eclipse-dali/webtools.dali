/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.jpa.core.context.ReadOnlyColumn;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.orm.OrmColumn;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmBaseColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlColumn;

/**
 * <code>orm.xml</code> column
 */
public class GenericOrmColumn
	extends AbstractOrmBaseColumn<XmlColumn, OrmColumn.Owner>
	implements OrmColumn
{
	// TODO defaults from java for all of these settings
	protected Integer specifiedLength;

	protected Integer specifiedPrecision;

	protected Integer specifiedScale;


	public GenericOrmColumn(XmlContextNode parent, OrmColumn.Owner owner) {
		super(parent, owner);
		this.specifiedLength = this.buildSpecifiedLength();
		this.specifiedPrecision = this.buildSpecifiedPrecision();
		this.specifiedScale = this.buildSpecifiedScale();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedLength_(this.buildSpecifiedLength());
		this.setSpecifiedPrecision_(this.buildSpecifiedPrecision());
		this.setSpecifiedScale_(this.buildSpecifiedScale());
	}


	// ********** XML column **********

	@Override
	public XmlColumn getXmlColumn() {
		return this.owner.getXmlColumn();
	}

	@Override
	protected XmlColumn buildXmlColumn() {
		return this.owner.buildXmlColumn();
	}

	@Override
	protected void removeXmlColumn() {
		this.owner.removeXmlColumn();
	}


	// ********** length **********

	public int getLength() {
		return (this.specifiedLength != null) ? this.specifiedLength.intValue() : this.getDefaultLength();
	}

	public Integer getSpecifiedLength() {
		return this.specifiedLength;
	}

	public void setSpecifiedLength(Integer length) {
		if (this.valuesAreDifferent(this.specifiedLength, length)) {
			XmlColumn xmlColumn = this.getXmlColumnForUpdate();
			this.setSpecifiedLength_(length);
			xmlColumn.setLength(length);
			this.removeXmlColumnIfUnset();
		}
	}

	protected void setSpecifiedLength_(Integer length) {
		Integer old = this.specifiedLength;
		this.specifiedLength = length;
		this.firePropertyChanged(SPECIFIED_LENGTH_PROPERTY, old, length);
	}

	protected Integer buildSpecifiedLength() {
		XmlColumn xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getLength();
	}

	public int getDefaultLength() {
		return DEFAULT_LENGTH;
	}


	// ********** precision **********

	public int getPrecision() {
		return (this.specifiedPrecision != null) ? this.specifiedPrecision.intValue() : this.getDefaultPrecision();
	}

	public Integer getSpecifiedPrecision() {
		return this.specifiedPrecision;
	}

	public void setSpecifiedPrecision(Integer precision) {
		if (this.valuesAreDifferent(this.specifiedPrecision, precision)) {
			XmlColumn xmlColumn = this.getXmlColumnForUpdate();
			this.setSpecifiedPrecision_(precision);
			xmlColumn.setPrecision(precision);
			this.removeXmlColumnIfUnset();
		}
	}

	protected void setSpecifiedPrecision_(Integer precision) {
		Integer old = this.specifiedPrecision;
		this.specifiedPrecision = precision;
		this.firePropertyChanged(SPECIFIED_PRECISION_PROPERTY, old, precision);
	}

	protected Integer buildSpecifiedPrecision() {
		XmlColumn xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getPrecision();
	}

	public int getDefaultPrecision() {
		return DEFAULT_PRECISION;
	}


	// ********** scale **********

	public int getScale() {
		return (this.specifiedScale != null) ? this.specifiedScale.intValue() : this.getDefaultScale();
	}

	public Integer getSpecifiedScale() {
		return this.specifiedScale;
	}

	public void setSpecifiedScale(Integer scale) {
		if (this.valuesAreDifferent(this.specifiedScale, scale)) {
			XmlColumn xmlColumn = this.getXmlColumnForUpdate();
			this.setSpecifiedScale_(scale);
			xmlColumn.setScale(scale);
			this.removeXmlColumnIfUnset();
		}
	}

	protected void setSpecifiedScale_(Integer scale) {
		Integer old = this.specifiedScale;
		this.specifiedScale = scale;
		this.firePropertyChanged(SPECIFIED_SCALE_PROPERTY, old, scale);
	}

	protected Integer buildSpecifiedScale() {
		XmlColumn xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getScale();
	}

	public int getDefaultScale() {
		return DEFAULT_SCALE;
	}


	// ********** misc **********

	public void initializeFrom(ReadOnlyColumn oldColumn) {
		super.initializeFrom(oldColumn);
		this.setSpecifiedLength(oldColumn.getSpecifiedLength());
		this.setSpecifiedPrecision(oldColumn.getSpecifiedPrecision());
		this.setSpecifiedScale(oldColumn.getSpecifiedScale());
	}

	public void initializeFromVirtual(ReadOnlyColumn virtualColumn) {
		super.initializeFromVirtual(virtualColumn);
		// ignore other settings?
	}
}
