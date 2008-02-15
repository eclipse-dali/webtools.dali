/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.resource.orm.Column;

public class XmlColumn extends AbstractXmlColumn<Column> implements IColumn
{
	protected Integer specifiedLength;

	protected Integer specifiedPrecision;

	protected Integer specifiedScale;

	protected XmlColumn(IJpaContextNode parent, IXmlColumn.Owner owner) {
		super(parent, owner);
	}
	
	@Override
	public IXmlColumn.Owner owner() {
		return (IXmlColumn.Owner) super.owner();
	}
	
	public void initializeFrom(IColumn oldColumn) {
		super.initializeFrom(oldColumn);
		setSpecifiedLength(oldColumn.getSpecifiedLength());
		setSpecifiedPrecision(oldColumn.getSpecifiedPrecision());
		setSpecifiedScale(oldColumn.getSpecifiedScale());
	}
//
//	@Override
//	protected void addInsignificantXmlFeatureIdsTo(Set<Integer> insignificantXmlFeatureIds) {
//		super.addInsignificantXmlFeatureIdsTo(insignificantXmlFeatureIds);
//		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.ICOLUMN__LENGTH);
//		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.ICOLUMN__SCALE);
//		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.ICOLUMN__PRECISION);
//	}


	public Integer getLength() {
		return (this.getSpecifiedLength() == null) ? getDefaultLength() : this.getSpecifiedLength();
	}

	public Integer getDefaultLength() {
		return IColumn.DEFAULT_LENGTH;
	}

	public Integer getSpecifiedLength() {
		return this.specifiedLength;
	}

	public void setSpecifiedLength(Integer newSpecifiedLength) {
		Integer oldSpecifiedLength = this.specifiedLength;
		this.specifiedLength = newSpecifiedLength;
		if (oldSpecifiedLength != newSpecifiedLength) {
			if (this.columnResource() != null) {
				this.columnResource().setLength(newSpecifiedLength);
				if (this.columnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newSpecifiedLength != null) {
				addColumnResource();
				columnResource().setLength(newSpecifiedLength);
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
		return IColumn.DEFAULT_PRECISION;
	}
	
	public Integer getSpecifiedPrecision() {
		return this.specifiedPrecision;
	}

	public void setSpecifiedPrecision(Integer newSpecifiedPrecision) {
		Integer oldSpecifiedPrecision = this.specifiedPrecision;
		this.specifiedPrecision = newSpecifiedPrecision;
		if (oldSpecifiedPrecision != newSpecifiedPrecision) {
			if (this.columnResource() != null) {
				this.columnResource().setPrecision(newSpecifiedPrecision);
				if (this.columnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newSpecifiedPrecision != null) {
				addColumnResource();
				columnResource().setPrecision(newSpecifiedPrecision);
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
		return IColumn.DEFAULT_SCALE;
	}
	
	public Integer getSpecifiedScale() {
		return this.specifiedScale;
	}

	public void setSpecifiedScale(Integer newSpecifiedScale) {
		Integer oldSpecifiedScale = this.specifiedScale;
		this.specifiedScale = newSpecifiedScale;
		if (oldSpecifiedScale != newSpecifiedScale) {
			if (this.columnResource() != null) {
				this.columnResource().setScale(newSpecifiedScale);
				if (this.columnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newSpecifiedScale != null) {
				addColumnResource();
				columnResource().setScale(newSpecifiedScale);
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
	protected Column columnResource() {
		return owner().columnResource();
	}
	
	@Override
	protected void addColumnResource() {
		owner().addColumnResource();
		//this.columnMapping.setColumn(OrmFactory.eINSTANCE.createColumnImpl());
	}
	
	@Override
	protected void removeColumnResource() {
		owner().removeColumnResource();
		//this.columnMapping.setColumn(null);
	}
	
	@Override
	protected void initialize(Column column) {
		super.initialize(column);
		this.specifiedLength = this.specifiedLength(column);
		this.specifiedPrecision = this.specifiedPrecision(column);
		this.specifiedScale = this.specifiedScale(column);
	}
	
	@Override
	protected void update(Column column) {
		super.update(column);
		this.setSpecifiedLength_(this.specifiedLength(column));
		this.setSpecifiedPrecision_(this.specifiedPrecision(column));
		this.setSpecifiedScale_(this.specifiedScale(column));
	}

	protected Integer specifiedLength(Column column) {
		return column == null ? null : column.getLength();
	}

	protected Integer specifiedPrecision(Column column) {
		return column == null ? null : column.getPrecision();
	}
	
	protected Integer specifiedScale(Column column) {
		return column == null ? null : column.getScale();
	}
}
