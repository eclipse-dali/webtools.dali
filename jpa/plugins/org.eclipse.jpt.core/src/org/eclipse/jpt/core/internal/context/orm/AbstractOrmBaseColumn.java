/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmBaseColumn;
import org.eclipse.jpt.core.resource.orm.XmlAbstractColumn;
import org.eclipse.jpt.core.utility.TextRange;


public abstract class AbstractOrmBaseColumn<T extends XmlAbstractColumn> extends AbstractOrmNamedColumn<T>
	implements OrmBaseColumn
{
	protected String specifiedTable;
	
	protected String defaultTable;

	protected Boolean specifiedUnique;
	
	protected Boolean specifiedNullable;
	
	protected Boolean specifiedInsertable;
	
	protected Boolean specifiedUpdatable;

	protected AbstractOrmBaseColumn(XmlContextNode parent, OrmBaseColumn.Owner owner) {
		super(parent, owner);
	}
	
	public void initializeFrom(BaseColumn oldColumn) {
		super.initializeFrom(oldColumn);
		setSpecifiedTable(oldColumn.getSpecifiedTable());
		setSpecifiedUnique(oldColumn.getSpecifiedUnique());
		setSpecifiedNullable(oldColumn.getSpecifiedNullable());
		setSpecifiedInsertable(oldColumn.getSpecifiedInsertable());
		setSpecifiedUpdatable(oldColumn.getSpecifiedUpdatable());
	}
	
	@Override
	public OrmBaseColumn.Owner getOwner() {
		return (OrmBaseColumn.Owner) super.getOwner();
	}
	
//	@Override
//	protected void addInsignificantXmlFeatureIdsTo(Set<Integer> insignificantXmlFeatureIds) {
//		super.addInsignificantXmlFeatureIdsTo(insignificantXmlFeatureIds);
//		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.IABSTRACT_COLUMN__DEFAULT_TABLE);
//		insignificantXmlFeatureIds.add(JpaCoreMappingsPackage.IABSTRACT_COLUMN__TABLE);
//	}
	
	public String getTable() {
		return (this.getSpecifiedTable() == null) ? getDefaultTable() : this.getSpecifiedTable();
	}

	public String getSpecifiedTable() {
		return this.specifiedTable;
	}

	public void setSpecifiedTable(String newSpecifiedTable) {
		String oldSpecifiedTable = this.specifiedTable;
		this.specifiedTable = newSpecifiedTable;
		if (oldSpecifiedTable != newSpecifiedTable) {
			if (this.getResourceColumn() != null) {
				this.getResourceColumn().setTable(newSpecifiedTable);						
				if (this.getResourceColumn().isAllFeaturesUnset()) {
					removeResourceColumn();
				}
			}
			else if (newSpecifiedTable != null) {
				addResourceColumn();
				getResourceColumn().setTable(newSpecifiedTable);
			}
		}
		firePropertyChanged(BaseColumn.SPECIFIED_TABLE_PROPERTY, oldSpecifiedTable, newSpecifiedTable);
	}
	
	protected void setSpecifiedTable_(String newSpecifiedTable) {
		String oldSpecifiedTable = this.specifiedTable;
		this.specifiedTable = newSpecifiedTable;
		firePropertyChanged(BaseColumn.SPECIFIED_TABLE_PROPERTY, oldSpecifiedTable, newSpecifiedTable);
	}

	public String getDefaultTable() {
		return this.defaultTable;
	}

	protected void setDefaultTable(String newDefaultTable) {
		String oldDefaultTable = this.defaultTable;
		this.defaultTable = newDefaultTable;
		firePropertyChanged(BaseColumn.DEFAULT_TABLE_PROPERTY, oldDefaultTable, newDefaultTable);
	}

	public boolean isUnique() {
		return (this.getSpecifiedUnique() == null) ? this.isDefaultUnique() : this.getSpecifiedUnique().booleanValue();
	}
	
	public boolean isDefaultUnique() {
		return BaseColumn.DEFAULT_UNIQUE;
	}
	
	public Boolean getSpecifiedUnique() {
		return this.specifiedUnique;
	}
	
	public void setSpecifiedUnique(Boolean newSpecifiedUnique) {
		Boolean oldSpecifiedUnique = this.specifiedUnique;
		this.specifiedUnique = newSpecifiedUnique;
		if (oldSpecifiedUnique != newSpecifiedUnique) {
			if (this.getResourceColumn() != null) {
				this.getResourceColumn().setUnique(newSpecifiedUnique);						
				if (this.getResourceColumn().isAllFeaturesUnset()) {
					removeResourceColumn();
				}
			}
			else if (newSpecifiedUnique != null) {
				addResourceColumn();
				getResourceColumn().setUnique(newSpecifiedUnique);
			}
		}
		firePropertyChanged(BaseColumn.SPECIFIED_UNIQUE_PROPERTY, oldSpecifiedUnique, newSpecifiedUnique);
	}
	
	protected void setSpecifiedUnique_(Boolean newSpecifiedUnique) {
		Boolean oldSpecifiedUnique = this.specifiedUnique;
		this.specifiedUnique = newSpecifiedUnique;
		firePropertyChanged(BaseColumn.SPECIFIED_UNIQUE_PROPERTY, oldSpecifiedUnique, newSpecifiedUnique);
	}
	
	public boolean isNullable() {
		return (this.getSpecifiedNullable() == null) ? this.isDefaultNullable() : this.getSpecifiedNullable().booleanValue();
	}
	
	public boolean isDefaultNullable() {
		return BaseColumn.DEFAULT_NULLABLE;
	}
	
	public Boolean getSpecifiedNullable() {
		return this.specifiedNullable;
	}
	
	public void setSpecifiedNullable(Boolean newSpecifiedNullable) {
		Boolean oldSpecifiedNullable = this.specifiedNullable;
		this.specifiedNullable = newSpecifiedNullable;
		if (oldSpecifiedNullable != newSpecifiedNullable) {
			if (this.getResourceColumn() != null) {
				this.getResourceColumn().setNullable(newSpecifiedNullable);						
				if (this.getResourceColumn().isAllFeaturesUnset()) {
					removeResourceColumn();
				}
			}
			else if (newSpecifiedNullable != null) {
				addResourceColumn();
				getResourceColumn().setNullable(newSpecifiedNullable);
			}
		}
		firePropertyChanged(BaseColumn.SPECIFIED_NULLABLE_PROPERTY, oldSpecifiedNullable, newSpecifiedNullable);
	}

	protected void setSpecifiedNullable_(Boolean newSpecifiedNullable) {
		Boolean oldSpecifiedNullable = this.specifiedNullable;
		this.specifiedNullable = newSpecifiedNullable;
		firePropertyChanged(BaseColumn.SPECIFIED_NULLABLE_PROPERTY, oldSpecifiedNullable, newSpecifiedNullable);
	}

	public boolean isInsertable() {
		return (this.getSpecifiedInsertable() == null) ? this.isDefaultInsertable() : this.getSpecifiedInsertable().booleanValue();
	}
	
	public boolean isDefaultInsertable() {
		return BaseColumn.DEFAULT_INSERTABLE;
	}
	
	public Boolean getSpecifiedInsertable() {
		return this.specifiedInsertable;
	}
	
	public void setSpecifiedInsertable(Boolean newSpecifiedInsertable) {
		Boolean oldSpecifiedInsertable = this.specifiedInsertable;
		this.specifiedInsertable = newSpecifiedInsertable;
		if (oldSpecifiedInsertable != newSpecifiedInsertable) {
			if (this.getResourceColumn() != null) {
				this.getResourceColumn().setInsertable(newSpecifiedInsertable);						
				if (this.getResourceColumn().isAllFeaturesUnset()) {
					removeResourceColumn();
				}
			}
			else if (newSpecifiedInsertable != null) {
				addResourceColumn();
				getResourceColumn().setInsertable(newSpecifiedInsertable);
			}
		}
		firePropertyChanged(BaseColumn.SPECIFIED_INSERTABLE_PROPERTY, oldSpecifiedInsertable, newSpecifiedInsertable);
	}
	
	protected void setSpecifiedInsertable_(Boolean newSpecifiedInsertable) {
		Boolean oldSpecifiedInsertable = this.specifiedInsertable;
		this.specifiedInsertable = newSpecifiedInsertable;
		firePropertyChanged(BaseColumn.SPECIFIED_INSERTABLE_PROPERTY, oldSpecifiedInsertable, newSpecifiedInsertable);
	}

	public boolean isUpdatable() {
		return (this.getSpecifiedUpdatable() == null) ? this.isDefaultUpdatable() : this.getSpecifiedUpdatable().booleanValue();
	}
	
	public boolean isDefaultUpdatable() {
		return BaseColumn.DEFAULT_UPDATABLE;
	}
	
	public Boolean getSpecifiedUpdatable() {
		return this.specifiedUpdatable;
	}
	
	public void setSpecifiedUpdatable(Boolean newSpecifiedUpdatable) {
		Boolean oldSpecifiedUpdatable = this.specifiedUpdatable;
		this.specifiedUpdatable = newSpecifiedUpdatable;
		if (oldSpecifiedUpdatable != newSpecifiedUpdatable) {
			if (this.getResourceColumn() != null) {
				this.getResourceColumn().setUpdatable(newSpecifiedUpdatable);						
				if (this.getResourceColumn().isAllFeaturesUnset()) {
					removeResourceColumn();
				}
			}
			else if (newSpecifiedUpdatable != null) {
				addResourceColumn();
				getResourceColumn().setUpdatable(newSpecifiedUpdatable);
			}
		}
		firePropertyChanged(BaseColumn.SPECIFIED_UPDATABLE_PROPERTY, oldSpecifiedUpdatable, newSpecifiedUpdatable);
	}
	
	protected void setSpecifiedUpdatable_(Boolean newSpecifiedUpdatable) {
		Boolean oldSpecifiedUpdatable = this.specifiedUpdatable;
		this.specifiedUpdatable = newSpecifiedUpdatable;
		firePropertyChanged(BaseColumn.SPECIFIED_UPDATABLE_PROPERTY, oldSpecifiedUpdatable, newSpecifiedUpdatable);
	}

	@Override
	protected String getOwningTableName() {
		return this.getTable();
	}


	public TextRange getTableTextRange() {
		if (getResourceColumn() != null) {
			TextRange textRange = getResourceColumn().getTableTextRange();
			if (textRange != null) {
				return textRange;
			}
		}
		return this.getParent().getValidationTextRange(); 
	}
	
	@Override
	protected void initialize(T column) {
		super.initialize(column);
		this.specifiedTable = this.getResourceTable(column);
		this.defaultTable = this.getOwnerDefaultTableName();
		//TODO default from java for all of these settings
		this.specifiedNullable = this.getResourceNullable(column);
		this.specifiedUpdatable = this.getResourceUpdatable(column);
		this.specifiedUnique = this.getResourceUnique(column);
		this.specifiedInsertable = this.getResourceInsertable(column);
	}
	
	@Override
	protected void update(T column) {
		super.update(column);
		setSpecifiedTable_(this.getResourceTable(column));
		setDefaultTable(this.getOwnerDefaultTableName());
		setSpecifiedNullable_(this.getResourceNullable(column));
		setSpecifiedUpdatable_(this.getResourceUpdatable(column));
		setSpecifiedUnique_(this.getResourceUnique(column));
		setSpecifiedInsertable_(this.getResourceInsertable(column));
	}

	protected String getResourceTable(T column) {
		return column == null ? null : column.getTable();
	}

	protected Boolean getResourceNullable(T column) {
		return column == null ? null : column.getNullable();
	}
	
	protected Boolean getResourceUpdatable(T column) {
		return column == null ? null : column.getUpdatable();
	}
	
	protected Boolean getResourceUnique(T column) {
		return column == null ? null : column.getUnique();
	}
	
	protected Boolean getResourceInsertable(T column) {
		return column == null ? null : column.getInsertable();
	}
	
	protected String getOwnerDefaultTableName() {
		return getOwner().getDefaultTableName();
	}

}
