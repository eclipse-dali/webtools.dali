/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AbstractColumn;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.resource.orm.XmlAbstractColumn;


public abstract class AbstractOrmColumn<T extends XmlAbstractColumn> extends AbstractOrmNamedColumn<T>
	implements AbstractColumn
{
	protected String specifiedTable;
	
	protected String defaultTable;

	protected Boolean specifiedUnique;
	
	protected Boolean specifiedNullable;
	
	protected Boolean specifiedInsertable;
	
	protected Boolean specifiedUpdatable;

	protected AbstractOrmColumn(JpaContextNode parent, AbstractColumn.Owner owner) {
		super(parent, owner);
	}
	
	public void initializeFrom(AbstractColumn oldColumn) {
		super.initializeFrom(oldColumn);
		setSpecifiedTable(oldColumn.getSpecifiedTable());
		setSpecifiedUnique(oldColumn.getSpecifiedUnique());
		setSpecifiedNullable(oldColumn.getSpecifiedNullable());
		setSpecifiedInsertable(oldColumn.getSpecifiedInsertable());
		setSpecifiedUpdatable(oldColumn.getSpecifiedUpdatable());
	}
	
	@Override
	public AbstractColumn.Owner owner() {
		return (AbstractColumn.Owner) super.owner();
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
			if (this.columnResource() != null) {
				this.columnResource().setTable(newSpecifiedTable);						
				if (this.columnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newSpecifiedTable != null) {
				addColumnResource();
				columnResource().setTable(newSpecifiedTable);
			}
		}
		firePropertyChanged(AbstractColumn.SPECIFIED_TABLE_PROPERTY, oldSpecifiedTable, newSpecifiedTable);
	}
	
	protected void setSpecifiedTable_(String newSpecifiedTable) {
		String oldSpecifiedTable = this.specifiedTable;
		this.specifiedTable = newSpecifiedTable;
		firePropertyChanged(AbstractColumn.SPECIFIED_TABLE_PROPERTY, oldSpecifiedTable, newSpecifiedTable);
	}

	public String getDefaultTable() {
		return this.defaultTable;
	}

	protected void setDefaultTable(String newDefaultTable) {
		String oldDefaultTable = this.defaultTable;
		this.defaultTable = newDefaultTable;
		firePropertyChanged(AbstractColumn.DEFAULT_TABLE_PROPERTY, oldDefaultTable, newDefaultTable);
	}

	public Boolean getUnique() {
		return (this.getSpecifiedUnique() == null) ? this.getDefaultUnique() : this.getSpecifiedUnique();
	}
	
	public Boolean getDefaultUnique() {
		return AbstractColumn.DEFAULT_UNIQUE;
	}
	
	public Boolean getSpecifiedUnique() {
		return this.specifiedUnique;
	}
	
	public void setSpecifiedUnique(Boolean newSpecifiedUnique) {
		Boolean oldSpecifiedUnique = this.specifiedUnique;
		this.specifiedUnique = newSpecifiedUnique;
		if (oldSpecifiedUnique != newSpecifiedUnique) {
			if (this.columnResource() != null) {
				this.columnResource().setUnique(newSpecifiedUnique);						
				if (this.columnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newSpecifiedUnique != null) {
				addColumnResource();
				columnResource().setUnique(newSpecifiedUnique);
			}
		}
		firePropertyChanged(AbstractColumn.SPECIFIED_UNIQUE_PROPERTY, oldSpecifiedUnique, newSpecifiedUnique);
	}
	
	protected void setSpecifiedUnique_(Boolean newSpecifiedUnique) {
		Boolean oldSpecifiedUnique = this.specifiedUnique;
		this.specifiedUnique = newSpecifiedUnique;
		firePropertyChanged(AbstractColumn.SPECIFIED_UNIQUE_PROPERTY, oldSpecifiedUnique, newSpecifiedUnique);
	}
	
	public Boolean getNullable() {
		return (this.getSpecifiedNullable() == null) ? this.getDefaultNullable() : this.getSpecifiedNullable();
	}
	
	public Boolean getDefaultNullable() {
		return AbstractColumn.DEFAULT_NULLABLE;
	}
	
	public Boolean getSpecifiedNullable() {
		return this.specifiedNullable;
	}
	
	public void setSpecifiedNullable(Boolean newSpecifiedNullable) {
		Boolean oldSpecifiedNullable = this.specifiedNullable;
		this.specifiedNullable = newSpecifiedNullable;
		if (oldSpecifiedNullable != newSpecifiedNullable) {
			if (this.columnResource() != null) {
				this.columnResource().setNullable(newSpecifiedNullable);						
				if (this.columnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newSpecifiedNullable != null) {
				addColumnResource();
				columnResource().setNullable(newSpecifiedNullable);
			}
		}
		firePropertyChanged(AbstractColumn.SPECIFIED_NULLABLE_PROPERTY, oldSpecifiedNullable, newSpecifiedNullable);
	}

	protected void setSpecifiedNullable_(Boolean newSpecifiedNullable) {
		Boolean oldSpecifiedNullable = this.specifiedNullable;
		this.specifiedNullable = newSpecifiedNullable;
		firePropertyChanged(AbstractColumn.SPECIFIED_NULLABLE_PROPERTY, oldSpecifiedNullable, newSpecifiedNullable);
	}

	public Boolean getInsertable() {
		return (this.getSpecifiedInsertable() == null) ? this.getDefaultInsertable() : this.getSpecifiedInsertable();
	}
	
	public Boolean getDefaultInsertable() {
		return AbstractColumn.DEFAULT_INSERTABLE;
	}
	
	public Boolean getSpecifiedInsertable() {
		return this.specifiedInsertable;
	}
	
	public void setSpecifiedInsertable(Boolean newSpecifiedInsertable) {
		Boolean oldSpecifiedInsertable = this.specifiedInsertable;
		this.specifiedInsertable = newSpecifiedInsertable;
		if (oldSpecifiedInsertable != newSpecifiedInsertable) {
			if (this.columnResource() != null) {
				this.columnResource().setInsertable(newSpecifiedInsertable);						
				if (this.columnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newSpecifiedInsertable != null) {
				addColumnResource();
				columnResource().setInsertable(newSpecifiedInsertable);
			}
		}
		firePropertyChanged(AbstractColumn.SPECIFIED_INSERTABLE_PROPERTY, oldSpecifiedInsertable, newSpecifiedInsertable);
	}
	
	protected void setSpecifiedInsertable_(Boolean newSpecifiedInsertable) {
		Boolean oldSpecifiedInsertable = this.specifiedInsertable;
		this.specifiedInsertable = newSpecifiedInsertable;
		firePropertyChanged(AbstractColumn.SPECIFIED_INSERTABLE_PROPERTY, oldSpecifiedInsertable, newSpecifiedInsertable);
	}

	public Boolean getUpdatable() {
		return (this.getSpecifiedUpdatable() == null) ? this.getDefaultUpdatable() : this.getSpecifiedUpdatable();
	}
	
	public Boolean getDefaultUpdatable() {
		return AbstractColumn.DEFAULT_UPDATABLE;
	}
	
	public Boolean getSpecifiedUpdatable() {
		return this.specifiedUpdatable;
	}
	
	public void setSpecifiedUpdatable(Boolean newSpecifiedUpdatable) {
		Boolean oldSpecifiedUpdatable = this.specifiedUpdatable;
		this.specifiedUpdatable = newSpecifiedUpdatable;
		if (oldSpecifiedUpdatable != newSpecifiedUpdatable) {
			if (this.columnResource() != null) {
				this.columnResource().setUpdatable(newSpecifiedUpdatable);						
				if (this.columnResource().isAllFeaturesUnset()) {
					removeColumnResource();
				}
			}
			else if (newSpecifiedUpdatable != null) {
				addColumnResource();
				columnResource().setUpdatable(newSpecifiedUpdatable);
			}
		}
		firePropertyChanged(AbstractColumn.SPECIFIED_UPDATABLE_PROPERTY, oldSpecifiedUpdatable, newSpecifiedUpdatable);
	}
	
	protected void setSpecifiedUpdatable_(Boolean newSpecifiedUpdatable) {
		Boolean oldSpecifiedUpdatable = this.specifiedUpdatable;
		this.specifiedUpdatable = newSpecifiedUpdatable;
		firePropertyChanged(AbstractColumn.SPECIFIED_UPDATABLE_PROPERTY, oldSpecifiedUpdatable, newSpecifiedUpdatable);
	}

	@Override
	protected String tableName() {
		return this.getTable();
	}
	
	public TextRange tableTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
//
//	public ITextRange tableTextRange() {
//		if (node == null) {
//			return owner.validationTextRange();
//		}
//		IDOMNode tableNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.ENTITY__TABLE);
//		return (tableNode == null) ? validationTextRange() : buildTextRange(tableNode);
//	}
	
	@Override
	protected void initialize(T column) {
		super.initialize(column);
		this.specifiedTable = this.specifiedTable(column);
		this.defaultTable = this.defaultTable();
		//TODO default from java for all of these settings
		this.specifiedNullable = this.specifiedNullable(column);
		this.specifiedUpdatable = this.specifiedUpdatable(column);
		this.specifiedUnique = this.specifiedUnique(column);
		this.specifiedInsertable = this.specifiedInsertable(column);
	}
	
	@Override
	protected void update(T column) {
		super.update(column);
		setSpecifiedTable_(this.specifiedTable(column));
		setDefaultTable(this.defaultTable());
		setSpecifiedNullable_(this.specifiedNullable(column));
		setSpecifiedUpdatable_(this.specifiedUpdatable(column));
		setSpecifiedUnique_(this.specifiedUnique(column));
		setSpecifiedInsertable_(this.specifiedInsertable(column));
	}

	protected String specifiedTable(T column) {
		return column == null ? null : column.getTable();
	}

	protected Boolean specifiedNullable(T column) {
		return column == null ? null : column.getNullable();
	}
	
	protected Boolean specifiedUpdatable(T column) {
		return column == null ? null : column.getUpdatable();
	}
	
	protected Boolean specifiedUnique(T column) {
		return column == null ? null : column.getUnique();
	}
	
	protected Boolean specifiedInsertable(T column) {
		return column == null ? null : column.getInsertable();
	}
	
	protected String defaultTable() {
		return owner().defaultTableName();
	}

}
