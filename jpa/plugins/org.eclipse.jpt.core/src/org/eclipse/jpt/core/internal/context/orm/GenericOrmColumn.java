/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.resource.orm.XmlColumn;

public class GenericOrmColumn extends AbstractOrmBaseColumn<XmlColumn> implements OrmColumn
{
	protected Integer specifiedLength;

	protected Integer specifiedPrecision;

	protected Integer specifiedScale;

	public GenericOrmColumn(OrmJpaContextNode parent, OrmColumn.Owner owner) {
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

	public Integer getLength() {
		return (this.getSpecifiedLength() == null) ? getDefaultLength() : this.getSpecifiedLength();
	}

	public Integer getDefaultLength() {
		return Column.DEFAULT_LENGTH;
	}

	public Integer getSpecifiedLength() {
		return this.specifiedLength;
	}

	public void setSpecifiedLength(Integer newSpecifiedLength) {
		Integer oldSpecifiedLength = this.specifiedLength;
		this.specifiedLength = newSpecifiedLength;
		if (oldSpecifiedLength != newSpecifiedLength) {
			if (this.getColumnResource() != null) {
				this.getColumnResource().setLength(newSpecifiedLength);
				if (this.getColumnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newSpecifiedLength != null) {
				addColumnResource();
				getColumnResource().setLength(newSpecifiedLength);
			}
		}
		firePropertyChanged(SPECIFIED_LENGTH_PROPERTY, oldSpecifiedLength, newSpecifiedLength);
	}
	
	protected void setSpecifiedLength_(Integer newSpecifiedLength) {
		Integer oldSpecifiedLength = this.specifiedLength;
		this.specifiedLength = newSpecifiedLength;
		firePropertyChanged(SPECIFIED_LENGTH_PROPERTY, oldSpecifiedLength, newSpecifiedLength);
	}

	public Integer getPrecision() {
		return (this.getSpecifiedPrecision() == null) ? getDefaultPrecision() : this.getSpecifiedPrecision();
	}

	public Integer getDefaultPrecision() {
		return Column.DEFAULT_PRECISION;
	}
	
	public Integer getSpecifiedPrecision() {
		return this.specifiedPrecision;
	}

	public void setSpecifiedPrecision(Integer newSpecifiedPrecision) {
		Integer oldSpecifiedPrecision = this.specifiedPrecision;
		this.specifiedPrecision = newSpecifiedPrecision;
		if (oldSpecifiedPrecision != newSpecifiedPrecision) {
			if (this.getColumnResource() != null) {
				this.getColumnResource().setPrecision(newSpecifiedPrecision);
				if (this.getColumnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newSpecifiedPrecision != null) {
				addColumnResource();
				getColumnResource().setPrecision(newSpecifiedPrecision);
			}
		}
		firePropertyChanged(SPECIFIED_PRECISION_PROPERTY, oldSpecifiedPrecision, newSpecifiedPrecision);
	}
	
	protected void setSpecifiedPrecision_(Integer newSpecifiedPrecision) {
		Integer oldSpecifiedPrecision = this.specifiedPrecision;
		this.specifiedPrecision = newSpecifiedPrecision;
		firePropertyChanged(SPECIFIED_PRECISION_PROPERTY, oldSpecifiedPrecision, newSpecifiedPrecision);
	}

	public Integer getScale() {
		return (this.getSpecifiedScale() == null) ? getDefaultScale() : this.getSpecifiedScale();
	}

	public Integer getDefaultScale() {
		return Column.DEFAULT_SCALE;
	}
	
	public Integer getSpecifiedScale() {
		return this.specifiedScale;
	}

	public void setSpecifiedScale(Integer newSpecifiedScale) {
		Integer oldSpecifiedScale = this.specifiedScale;
		this.specifiedScale = newSpecifiedScale;
		if (oldSpecifiedScale != newSpecifiedScale) {
			if (this.getColumnResource() != null) {
				this.getColumnResource().setScale(newSpecifiedScale);
				if (this.getColumnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newSpecifiedScale != null) {
				addColumnResource();
				getColumnResource().setScale(newSpecifiedScale);
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
	protected XmlColumn getColumnResource() {
		return getOwner().getColumnResource();
	}
	
	@Override
	protected void addColumnResource() {
		getOwner().addColumnResource();
	}
	
	@Override
	protected void removeColumnResource() {
		getOwner().removeColumnResource();
	}
	
	@Override
	public void initialize(XmlColumn column) {
		super.initialize(column);
		this.specifiedLength = this.specifiedLength(column);
		this.specifiedPrecision = this.specifiedPrecision(column);
		this.specifiedScale = this.specifiedScale(column);
	}
	
	@Override
	public void update(XmlColumn column) {
		super.update(column);
		this.setSpecifiedLength_(this.specifiedLength(column));
		this.setSpecifiedPrecision_(this.specifiedPrecision(column));
		this.setSpecifiedScale_(this.specifiedScale(column));
	}

	protected Integer specifiedLength(XmlColumn column) {
		return column == null ? null : column.getLength();
	}

	protected Integer specifiedPrecision(XmlColumn column) {
		return column == null ? null : column.getPrecision();
	}
	
	protected Integer specifiedScale(XmlColumn column) {
		return column == null ? null : column.getScale();
	}
}
