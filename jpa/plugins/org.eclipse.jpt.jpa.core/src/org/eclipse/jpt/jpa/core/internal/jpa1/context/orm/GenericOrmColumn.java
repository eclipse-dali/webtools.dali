/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License 2.0, which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.context.VirtualColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedColumn;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmBaseColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlColumn;

/**
 * <code>orm.xml</code> column
 */
public class GenericOrmColumn
	extends AbstractOrmBaseColumn<OrmSpecifiedColumn.ParentAdapter, XmlColumn>
	implements OrmSpecifiedColumn
{
	// TODO defaults from java for all of these settings
	protected Integer specifiedLength;

	protected Integer specifiedPrecision;

	protected Integer specifiedScale;


	public GenericOrmColumn(OrmSpecifiedColumn.ParentAdapter parentAdapter) {
		super(parentAdapter);
		this.specifiedLength = this.buildSpecifiedLength();
		this.specifiedPrecision = this.buildSpecifiedPrecision();
		this.specifiedScale = this.buildSpecifiedScale();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedLength_(this.buildSpecifiedLength());
		this.setSpecifiedPrecision_(this.buildSpecifiedPrecision());
		this.setSpecifiedScale_(this.buildSpecifiedScale());
	}


	// ********** XML column **********

	@Override
	public XmlColumn getXmlColumn() {
		return this.parentAdapter.getXmlColumn();
	}

	@Override
	protected XmlColumn buildXmlColumn() {
		return this.parentAdapter.buildXmlColumn();
	}

	@Override
	protected void removeXmlColumn() {
		this.parentAdapter.removeXmlColumn();
	}


	// ********** length **********

	public int getLength() {
		return (this.specifiedLength != null) ? this.specifiedLength.intValue() : this.getDefaultLength();
	}

	public Integer getSpecifiedLength() {
		return this.specifiedLength;
	}

	public void setSpecifiedLength(Integer length) {
		if (ObjectTools.notEquals(this.specifiedLength, length)) {
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
		if (ObjectTools.notEquals(this.specifiedPrecision, precision)) {
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
		if (ObjectTools.notEquals(this.specifiedScale, scale)) {
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

	public void initializeFrom(OrmSpecifiedColumn oldColumn) {
		super.initializeFrom(oldColumn);
		this.setSpecifiedLength(oldColumn.getSpecifiedLength());
		this.setSpecifiedPrecision(oldColumn.getSpecifiedPrecision());
		this.setSpecifiedScale(oldColumn.getSpecifiedScale());
	}

	public void initializeFrom(VirtualColumn virtualColumn) {
		super.initializeFrom(virtualColumn);
		// ignore other settings?
	}
}
