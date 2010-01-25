/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class GenericOrmPrimaryKeyJoinColumnJoiningStrategy
	extends AbstractOrmXmlContextNode
	implements OrmPrimaryKeyJoinColumnJoiningStrategy
{
	protected XmlOneToOne resource;
	
	protected final Vector<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns = new Vector<OrmPrimaryKeyJoinColumn>();
	
	protected final OrmJoinColumn.Owner joinColumnOwner;
	
	
	public GenericOrmPrimaryKeyJoinColumnJoiningStrategy(
			OrmPrimaryKeyJoinColumnEnabledRelationshipReference parent,
			XmlOneToOne resource) {
		super(parent);
		this.resource = resource;
		this.joinColumnOwner = this.buildJoinColumnOwner();
		this.initializePrimaryKeyJoinColumns();
	}
	
	protected OrmJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	protected void initializePrimaryKeyJoinColumns() {
		if (this.resource != null) {
			for (XmlPrimaryKeyJoinColumn resourceJoinColumn : this.resource.getPrimaryKeyJoinColumns()) {
				this.primaryKeyJoinColumns.add(buildPrimaryKeyJoinColumn(resourceJoinColumn));
			}
		}
	}
	
	protected OrmPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(
			XmlPrimaryKeyJoinColumn resourceJoinColumn) {
		return this.getXmlContextNodeFactory().buildOrmPrimaryKeyJoinColumn(this, this.joinColumnOwner, resourceJoinColumn);
	}
	
	@Override
	public OrmJoinColumnEnabledRelationshipReference getParent() {
		return (OrmJoinColumnEnabledRelationshipReference) super.getParent();
	}
	
	public OrmJoinColumnEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}
	
	public OrmRelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getRelationshipMapping();
	}

	public String getTableName() {
		return getTypeMapping().getPrimaryTableName();
	}

	public Table getDbTable(String tableName) {
		return getTypeMapping().getDbTable(tableName);
	}

	protected TypeMapping getTypeMapping() {
		return getRelationshipMapping().getTypeMapping();
	}

	public void addStrategy() {
		if (primaryKeyJoinColumnsSize() == 0) {
			addPrimaryKeyJoinColumn(0);
		}
	}
	
	public void removeStrategy() {
		for (PrimaryKeyJoinColumn each : CollectionTools.iterable(primaryKeyJoinColumns())) {
			removePrimaryKeyJoinColumn(each);
		}
	}
	
	public boolean isOverridableAssociation() {
		return false;
	}
	
	
	// **************** primary key join columns *******************************
	
	public ListIterator<OrmPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return new CloneListIterator<OrmPrimaryKeyJoinColumn>(this.primaryKeyJoinColumns);
	}
	
	public int primaryKeyJoinColumnsSize() {
		return this.primaryKeyJoinColumns.size();
	}
	
	public boolean hasPrimaryKeyJoinColumns() {
		return ! this.primaryKeyJoinColumns.isEmpty();
	}
	
	public OrmPrimaryKeyJoinColumn addPrimaryKeyJoinColumn(int index) {
		XmlPrimaryKeyJoinColumn resourcePkJoinColumn = 
			OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn();
		OrmPrimaryKeyJoinColumn contextPkJoinColumn = 
			this.buildPrimaryKeyJoinColumn(resourcePkJoinColumn);
		this.primaryKeyJoinColumns.add(index, contextPkJoinColumn);
		this.resource.getPrimaryKeyJoinColumns().add(index, resourcePkJoinColumn);
		this.fireItemAdded(PRIMARY_KEY_JOIN_COLUMNS_LIST, index, contextPkJoinColumn);
		return contextPkJoinColumn;
	}
	
	protected void addPrimaryKeyJoinColumn(int index, OrmPrimaryKeyJoinColumn joinColumn) {
		this.addItemToList(index, joinColumn, this.primaryKeyJoinColumns, PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	protected void addPrimaryKeyJoinColumn(OrmPrimaryKeyJoinColumn joinColumn) {
		this.addPrimaryKeyJoinColumn(this.primaryKeyJoinColumns.size(), joinColumn);
	}
	
	public void removePrimaryKeyJoinColumn(PrimaryKeyJoinColumn pkJoinColumn) {
		this.removePrimaryKeyJoinColumn(this.primaryKeyJoinColumns.indexOf(pkJoinColumn));
	}
	
	public void removePrimaryKeyJoinColumn(int index) {
		OrmPrimaryKeyJoinColumn removedPkJoinColumn = this.primaryKeyJoinColumns.remove(index);
		this.resource.getPrimaryKeyJoinColumns().remove(index);
		this.fireItemRemoved(PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPkJoinColumn);
	}
	
	protected void removePrimaryKeyJoinColumn_(OrmPrimaryKeyJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.primaryKeyJoinColumns, PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void movePrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.primaryKeyJoinColumns, targetIndex, sourceIndex);
		this.resource.getPrimaryKeyJoinColumns().move(targetIndex, sourceIndex);
		fireItemMoved(PRIMARY_KEY_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	
	// **************** resource -> context ************************************
	
	public void update() {
		updatePrimaryKeyJoinColumns();
	}
	
	protected void updatePrimaryKeyJoinColumns() {
		// make a copy of the XML PK join columns (to prevent ConcurrentModificationException)
		Iterator<XmlPrimaryKeyJoinColumn> xmlPkJoinColumns = 
			new CloneIterator<XmlPrimaryKeyJoinColumn>(this.resource.getPrimaryKeyJoinColumns());
		
		for (Iterator<OrmPrimaryKeyJoinColumn> contextPkJoinColumns = primaryKeyJoinColumns(); 
				contextPkJoinColumns.hasNext(); ) {
			OrmPrimaryKeyJoinColumn contextPkJoinColumn = contextPkJoinColumns.next();
			if (xmlPkJoinColumns.hasNext()) {
				contextPkJoinColumn.update(xmlPkJoinColumns.next());
			}
			else {
				removePrimaryKeyJoinColumn_(contextPkJoinColumn);
			}
		}
		
		while (xmlPkJoinColumns.hasNext()) {
			addPrimaryKeyJoinColumn(buildPrimaryKeyJoinColumn(xmlPkJoinColumns.next()));
		}
	}
	
	public TextRange getValidationTextRange() {
		return this.getRelationshipReference().getValidationTextRange();
	}
	

	// ********** join column owner adapter **********

	protected class JoinColumnOwner
		implements OrmJoinColumn.Owner 
	{
		protected JoinColumnOwner() {
			super();
		}
		
		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String getDefaultTableName() {
			return GenericOrmPrimaryKeyJoinColumnJoiningStrategy.this.getTableName();
		}
		
		public Entity getTargetEntity() {
			return getRelationshipMapping().getResolvedTargetEntity();
		}
		
		public String getAttributeName() {
			return getRelationshipMapping().getName();
		}
		
		public PersistentAttribute getPersistentAttribute() {
			return getRelationshipMapping().getPersistentAttribute();
		}
		
		public boolean tableNameIsInvalid(String tableName) {
			return getTypeMapping().tableNameIsInvalid(tableName);
		}
		
		/**
		 * the join column can be on a secondary table
		 */
		public boolean tableIsAllowed() {
			return true;
		}

		public Iterator<String> candidateTableNames() {
			return getTypeMapping().associatedTableNamesIncludingInherited();
		}

		public TypeMapping getTypeMapping() {
			return GenericOrmPrimaryKeyJoinColumnJoiningStrategy.this.getTypeMapping();
		}
		
		public Table getDbTable(String tableName) {
			return getTypeMapping().getDbTable(tableName);
		}
		
		public Table getReferencedColumnDbTable() {
			Entity targetEntity = getTargetEntity();
			return (targetEntity == null) ? null : targetEntity.getPrimaryDbTable();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return false;
		}
		
		public String getDefaultColumnName() {
			return null;
		}

		public int joinColumnsSize() {
			return GenericOrmPrimaryKeyJoinColumnJoiningStrategy.this.primaryKeyJoinColumnsSize();
		}
		
		public TextRange getValidationTextRange() {
			return GenericOrmPrimaryKeyJoinColumnJoiningStrategy.this.getValidationTextRange();
		}

	}

}
