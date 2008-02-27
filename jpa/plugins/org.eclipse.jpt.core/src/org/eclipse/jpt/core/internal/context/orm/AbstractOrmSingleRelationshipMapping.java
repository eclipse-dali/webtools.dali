/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.Nullable;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.SingleRelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmSingleRelationshipMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlSingleRelationshipMapping;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;


public abstract class AbstractOrmSingleRelationshipMapping<T extends XmlSingleRelationshipMapping>
	extends AbstractOrmRelationshipMapping<T> implements OrmSingleRelationshipMapping
{
	
	protected final List<OrmJoinColumn> specifiedJoinColumns;

	protected final List<OrmJoinColumn> defaultJoinColumns;

	protected Boolean specifiedOptional;

	protected AbstractOrmSingleRelationshipMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.specifiedJoinColumns = new ArrayList<OrmJoinColumn>();
		this.defaultJoinColumns = new ArrayList<OrmJoinColumn>();

		//this.getDefaultJoinColumns().add(this.createJoinColumn(new JoinColumnOwner(this)));
	}
	
	@Override
	public void initializeFromXmlSingleRelationshipMapping(OrmSingleRelationshipMapping oldMapping) {
		super.initializeFromXmlSingleRelationshipMapping(oldMapping);
		int index = 0;
		for (JoinColumn joinColumn : CollectionTools.iterable(oldMapping.specifiedJoinColumns())) {
			OrmJoinColumn newJoinColumn = addSpecifiedJoinColumn(index++);
			newJoinColumn.initializeFrom(joinColumn);
		}
	}
	
	public FetchType getDefaultFetch() {
		return SingleRelationshipMapping.DEFAULT_FETCH_TYPE;
	}

	//***************** ISingleRelationshipMapping implementation *****************
	public ListIterator<OrmJoinColumn> joinColumns() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumns() : this.specifiedJoinColumns();
	}

	public int joinColumnsSize() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumnsSize() : this.specifiedJoinColumnsSize();
	}
	
	public OrmJoinColumn getDefaultJoinColumn() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public ListIterator<OrmJoinColumn> defaultJoinColumns() {
		return new CloneListIterator<OrmJoinColumn>(this.defaultJoinColumns);
	}

	public int defaultJoinColumnsSize() {
		return this.defaultJoinColumns.size();
	}
	
	public ListIterator<OrmJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<OrmJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.specifiedJoinColumns.isEmpty();
	}

	public OrmJoinColumn addSpecifiedJoinColumn(int index) {
		OrmJoinColumn joinColumn = jpaFactory().buildOrmJoinColumn(this, new JoinColumnOwner());
		this.specifiedJoinColumns.add(index, joinColumn);
		this.attributeMapping().getJoinColumns().add(index, OrmFactory.eINSTANCE.createJoinColumnImpl());
		this.fireItemAdded(SingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		return joinColumn;
	}

	protected void addSpecifiedJoinColumn(int index, OrmJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedJoinColumns, SingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST);
	}

	public void removeSpecifiedJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumns.indexOf(joinColumn));
	}
	
	public void removeSpecifiedJoinColumn(int index) {
		OrmJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		this.attributeMapping().getJoinColumns().remove(index);
		fireItemRemoved(SingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST, index, removedJoinColumn);
	}

	protected void removeSpecifiedJoinColumn_(OrmJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, SingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedJoinColumns, targetIndex, sourceIndex);
		this.attributeMapping().getJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(SingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}

	public Boolean getOptional() {
		return getSpecifiedOptional() == null ? getDefaultOptional() : getSpecifiedOptional();
	}
	
	public Boolean getDefaultOptional() {
		return Nullable.DEFAULT_OPTIONAL;
	}

	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}
	
	public void setSpecifiedOptional(Boolean newSpecifiedOptional) {
		Boolean oldSpecifiedOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		attributeMapping().setOptional(newSpecifiedOptional);
		firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, oldSpecifiedOptional, newSpecifiedOptional);
	}
	
	protected void setSpecifiedOptional_(Boolean newSpecifiedOptional) {
		Boolean oldSpecifiedOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, oldSpecifiedOptional, newSpecifiedOptional);
	}
//
//	public boolean containsSpecifiedJoinColumns() {
//		return !this.getSpecifiedJoinColumns().isEmpty();
//	}
	
	@Override
	public void initialize(T singleRelationshipMapping) {
		super.initialize(singleRelationshipMapping);
		this.specifiedOptional = singleRelationshipMapping.getOptional();
		//TODO defaultOptional
		this.initializeSpecifiedJoinColumns(singleRelationshipMapping);
	}
	
	protected void initializeSpecifiedJoinColumns(T singleRelationshipMapping) {
		if (singleRelationshipMapping == null) {
			return;
		}
		for (XmlJoinColumn joinColumn : singleRelationshipMapping.getJoinColumns()) {
			this.specifiedJoinColumns.add(createJoinColumn(joinColumn));
		}
	}
	
	protected OrmJoinColumn createJoinColumn(XmlJoinColumn joinColumn) {
		OrmJoinColumn ormJoinColumn = jpaFactory().buildOrmJoinColumn(this, new JoinColumnOwner());
		ormJoinColumn.initialize(joinColumn);
		return ormJoinColumn;
	}	

	@Override
	public void update(T singleRelationshipMapping) {
		super.update(singleRelationshipMapping);
		this.setSpecifiedOptional_(singleRelationshipMapping.getOptional());
		this.updateSpecifiedJoinColumns(singleRelationshipMapping);
	}
	
	protected void updateSpecifiedJoinColumns(T singleRelationshipMapping) {
		ListIterator<OrmJoinColumn> joinColumns = specifiedJoinColumns();
		ListIterator<XmlJoinColumn> resourceJoinColumns = EmptyListIterator.instance();
		if (singleRelationshipMapping != null) {
			resourceJoinColumns = singleRelationshipMapping.getJoinColumns().listIterator();
		}
		
		while (joinColumns.hasNext()) {
			OrmJoinColumn joinColumn = joinColumns.next();
			if (resourceJoinColumns.hasNext()) {
				joinColumn.update(resourceJoinColumns.next());
			}
			else {
				removeSpecifiedJoinColumn_(joinColumn);
			}
		}
		
		while (resourceJoinColumns.hasNext()) {
			addSpecifiedJoinColumn(specifiedJoinColumnsSize(), createJoinColumn(resourceJoinColumns.next()));
		}
	}

	
	public class JoinColumnOwner implements JoinColumn.Owner
	{

		public JoinColumnOwner() {
			super();
		}

		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String defaultTableName() {
			return AbstractOrmSingleRelationshipMapping.this.typeMapping().tableName();
		}

		public Entity targetEntity() {
			return AbstractOrmSingleRelationshipMapping.this.getResolvedTargetEntity();
		}

		public String attributeName() {
			return AbstractOrmSingleRelationshipMapping.this.getName();
		}

		public RelationshipMapping relationshipMapping() {
			return AbstractOrmSingleRelationshipMapping.this;
		}

		public boolean tableNameIsInvalid(String tableName) {
			return AbstractOrmSingleRelationshipMapping.this.typeMapping().tableNameIsInvalid(tableName);
		}

		/**
		 * the join column can be on a secondary table
		 */
		public boolean tableIsAllowed() {
			return true;
		}

		public TypeMapping typeMapping() {
			return AbstractOrmSingleRelationshipMapping.this.typeMapping();
		}

		public Table dbTable(String tableName) {
			return typeMapping().dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			Entity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity.primaryDbTable();
		}
		
		public boolean isVirtual(AbstractJoinColumn joinColumn) {
			return AbstractOrmSingleRelationshipMapping.this.defaultJoinColumns.contains(joinColumn);
		}
		
		public String defaultColumnName() {
			// TODO Auto-generated method stub
			return null;
		}

		public int joinColumnsSize() {
			return AbstractOrmSingleRelationshipMapping.this.joinColumnsSize();
		}
	}
}
