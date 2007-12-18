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

import org.eclipse.jpt.core.internal.context.base.IAbstractColumn;
import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.resource.orm.Column;
import org.eclipse.jpt.core.internal.resource.orm.ColumnMapping;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;

public class XmlColumn extends AbstractXmlColumn<Column> implements IColumn
{
	protected Integer specifiedLength;
	protected Integer defaultLength;

	protected Integer specifiedPrecision;
	protected Integer defaultPrecision;

	protected Integer specifiedScale;
	protected Integer defaultScale;

	protected ColumnMapping columnMapping;
	
	protected XmlColumn(IJpaContextNode parent, IAbstractColumn.Owner owner) {
		super(parent, owner);
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
		return this.defaultLength;
	}
	
	protected void setDefaultLength(Integer newDefaultLength) {
		Integer oldDefaultLength = this.defaultLength;
		this.defaultLength = newDefaultLength;
		firePropertyChanged(DEFAULT_LENGTH_PROPERTY, oldDefaultLength, newDefaultLength);
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
		return this.defaultPrecision;
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
		return this.defaultScale;
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
		return this.columnMapping.getColumn();
	}
	
	@Override
	protected void addColumnResource() {
		this.columnMapping.setColumn(OrmFactory.eINSTANCE.createColumn());
		
	}
	
	@Override
	protected void removeColumnResource() {
		this.columnMapping.setColumn(null);
	}
	
	public void initialize(ColumnMapping columnMapping) {
		this.columnMapping = columnMapping;
		this.initialize(this.columnResource());
	}
	
	public void update(ColumnMapping columnMapping) {
		this.columnMapping = columnMapping;
		this.update(this.columnResource());
	}

	
	@Override
	protected void initialize(Column column) {
		super.initialize(column);
		this.specifiedLength = this.specifiedLength(column);
		this.specifiedPrecision = this.specifiedPrecision(column);
		this.specifiedScale = this.specifiedScale(column);
		//TODO defaultLength, scale, precision from java column
	}
	
	@Override
	protected void update(Column column) {
		super.update(column);
		this.setSpecifiedLength_(this.specifiedLength(column));
		this.setSpecifiedPrecision_(this.specifiedPrecision(column));
		this.setSpecifiedScale_(this.specifiedScale(column));
		//TODO defaultLength, scale, precision from java column
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
	
//	public void refreshDefaults(DefaultsContext defaultsContext) {
//		setDefaultTable((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_COLUMN_TABLE_KEY));
//		setDefaultName((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_COLUMN_NAME_KEY));
//	}
}
