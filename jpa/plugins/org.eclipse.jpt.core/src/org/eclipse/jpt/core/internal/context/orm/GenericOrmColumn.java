/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.resource.orm.XmlColumn;

public class GenericOrmColumn extends AbstractOrmBaseColumn<XmlColumn> implements OrmColumn
{
	protected Integer specifiedLength;

	protected Integer specifiedPrecision;

	protected Integer specifiedScale;

	public GenericOrmColumn(XmlContextNode parent, OrmColumn.Owner owner) {
		super(parent, owner);
	}
	
	@Override
	public OrmColumn.Owner getOwner() {
		return (OrmColumn.Owner) super.getOwner();
	}
	
	public void initializeFrom(Column oldColumn) {
		super.initializeFrom(oldColumn);
		setSpecifiedLength(oldColumn.getSpecifiedLength());
		setSpecifiedPrecision(oldColumn.getSpecifiedPrecision());
		setSpecifiedScale(oldColumn.getSpecifiedScale());
	}

	public int getLength() {
		return (this.getSpecifiedLength() == null) ? getDefaultLength() : this.getSpecifiedLength().intValue();
	}

	public int getDefaultLength() {
		return Column.DEFAULT_LENGTH;
	}

	public Integer getSpecifiedLength() {
		return this.specifiedLength;
	}

	public void setSpecifiedLength(Integer newSpecifiedLength) {
		Integer oldSpecifiedLength = this.specifiedLength;
		this.specifiedLength = newSpecifiedLength;
		if (oldSpecifiedLength != newSpecifiedLength) {
			if (this.getResourceColumn() != null) {
				this.getResourceColumn().setLength(newSpecifiedLength);
				if (this.getResourceColumn().isUnset()) {
					removeResourceColumn();
				}
			}
			else if (newSpecifiedLength != null) {
				addResourceColumn();
				getResourceColumn().setLength(newSpecifiedLength);
			}
		}
		firePropertyChanged(SPECIFIED_LENGTH_PROPERTY, oldSpecifiedLength, newSpecifiedLength);
	}
	
	protected void setSpecifiedLength_(Integer newSpecifiedLength) {
		Integer oldSpecifiedLength = this.specifiedLength;
		this.specifiedLength = newSpecifiedLength;
		firePropertyChanged(SPECIFIED_LENGTH_PROPERTY, oldSpecifiedLength, newSpecifiedLength);
	}

	public int getPrecision() {
		return (this.getSpecifiedPrecision() == null) ? getDefaultPrecision() : this.getSpecifiedPrecision().intValue();
	}

	public int getDefaultPrecision() {
		return Column.DEFAULT_PRECISION;
	}
	
	public Integer getSpecifiedPrecision() {
		return this.specifiedPrecision;
	}

	public void setSpecifiedPrecision(Integer newSpecifiedPrecision) {
		Integer oldSpecifiedPrecision = this.specifiedPrecision;
		this.specifiedPrecision = newSpecifiedPrecision;
		if (oldSpecifiedPrecision != newSpecifiedPrecision) {
			if (this.getResourceColumn() != null) {
				this.getResourceColumn().setPrecision(newSpecifiedPrecision);
				if (this.getResourceColumn().isUnset()) {
					removeResourceColumn();
				}
			}
			else if (newSpecifiedPrecision != null) {
				addResourceColumn();
				getResourceColumn().setPrecision(newSpecifiedPrecision);
			}
		}
		firePropertyChanged(SPECIFIED_PRECISION_PROPERTY, oldSpecifiedPrecision, newSpecifiedPrecision);
	}
	
	protected void setSpecifiedPrecision_(Integer newSpecifiedPrecision) {
		Integer oldSpecifiedPrecision = this.specifiedPrecision;
		this.specifiedPrecision = newSpecifiedPrecision;
		firePropertyChanged(SPECIFIED_PRECISION_PROPERTY, oldSpecifiedPrecision, newSpecifiedPrecision);
	}

	public int getScale() {
		return (this.getSpecifiedScale() == null) ? getDefaultScale() : this.getSpecifiedScale().intValue();
	}

	public int getDefaultScale() {
		return Column.DEFAULT_SCALE;
	}
	
	public Integer getSpecifiedScale() {
		return this.specifiedScale;
	}

	public void setSpecifiedScale(Integer newSpecifiedScale) {
		Integer oldSpecifiedScale = this.specifiedScale;
		this.specifiedScale = newSpecifiedScale;
		if (oldSpecifiedScale != newSpecifiedScale) {
			if (this.getResourceColumn() != null) {
				this.getResourceColumn().setScale(newSpecifiedScale);
				if (this.getResourceColumn().isUnset()) {
					removeResourceColumn();
				}
			}
			else if (newSpecifiedScale != null) {
				addResourceColumn();
				getResourceColumn().setScale(newSpecifiedScale);
			}
		}
		firePropertyChanged(SPECIFIED_SCALE_PROPERTY, oldSpecifiedScale, newSpecifiedScale);
	}

	protected void setSpecifiedScale_(Integer newSpecifiedScale) {
		Integer oldSpecifiedScale = this.specifiedScale;
		this.specifiedScale = newSpecifiedScale;
		firePropertyChanged(SPECIFIED_SCALE_PROPERTY, oldSpecifiedScale, newSpecifiedScale);
	}

	@Override
	protected XmlColumn getResourceColumn() {
		return getOwner().getResourceColumn();
	}
	
	@Override
	protected void addResourceColumn() {
		getOwner().addResourceColumn();
	}
	
	@Override
	protected void removeResourceColumn() {
		getOwner().removeResourceColumn();
	}
	
	@Override
	public void initialize(XmlColumn column) {
		super.initialize(column);
		this.specifiedLength = this.getResourceLength(column);
		this.specifiedPrecision = this.getResourcePrecision(column);
		this.specifiedScale = this.getResourceScale(column);
	}
	
	@Override
	public void update(XmlColumn column) {
		super.update(column);
		this.setSpecifiedLength_(this.getResourceLength(column));
		this.setSpecifiedPrecision_(this.getResourcePrecision(column));
		this.setSpecifiedScale_(this.getResourceScale(column));
	}

	protected Integer getResourceLength(XmlColumn column) {
		return column == null ? null : column.getLength();
	}

	protected Integer getResourcePrecision(XmlColumn column) {
		return column == null ? null : column.getPrecision();
	}
	
	protected Integer getResourceScale(XmlColumn column) {
		return column == null ? null : column.getScale();
	}
}
