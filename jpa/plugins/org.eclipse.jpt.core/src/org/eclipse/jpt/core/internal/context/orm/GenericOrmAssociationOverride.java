/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class GenericOrmAssociationOverride extends AbstractOrmJpaContextNode
	implements OrmAssociationOverride
{

	protected String name;

	protected final List<OrmJoinColumn> specifiedJoinColumns;

	protected final List<OrmJoinColumn> defaultJoinColumns;

	private final AssociationOverride.Owner owner;

	protected XmlAssociationOverride associationOverride;


	public GenericOrmAssociationOverride(JpaContextNode parent, AssociationOverride.Owner owner, XmlAssociationOverride associationOverride) {
		super(parent);
		this.owner = owner;
		this.specifiedJoinColumns = new ArrayList<OrmJoinColumn>();
		this.defaultJoinColumns = new ArrayList<OrmJoinColumn>();
		this.initialize(associationOverride);
	}
	
	public Owner owner() {
		return this.owner;
	}

	// ********** IAssociationOverride implementation **********

	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.associationOverride.setName(newName);
		firePropertyChanged(BaseOverride.NAME_PROPERTY, oldName, newName);
	}


	public ListIterator<OrmJoinColumn> joinColumns() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumns() : this.specifiedJoinColumns();
	}
	
	public int joinColumnsSize() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumnsSize() : this.specifiedJoinColumnsSize();
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
	
	public OrmJoinColumn addSpecifiedJoinColumn(int index) {
		OrmJoinColumn joinColumn = jpaFactory().buildOrmJoinColumn(this, createJoinColumnOwner());
		this.specifiedJoinColumns.add(index, joinColumn);
		this.associationOverride.getJoinColumns().add(index, OrmFactory.eINSTANCE.createJoinColumnImpl());
		this.fireItemAdded(AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		return joinColumn;
	}
	
	protected JoinColumn.Owner createJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	protected void addSpecifiedJoinColumn(int index, OrmJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedJoinColumns, AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void removeSpecifiedJoinColumn(int index) {
		OrmJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		this.associationOverride.getJoinColumns().remove(index);
		fireItemRemoved(Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedJoinColumn);
	}

	protected void removeSpecifiedJoinColumn(OrmJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		this.associationOverride.getJoinColumns().move(targetIndex, sourceIndex);
		moveItemInList(targetIndex, sourceIndex, this.specifiedJoinColumns, AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);		
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.specifiedJoinColumns.isEmpty();
	}	

	public boolean isVirtual() {
		return owner().isVirtual(this);
	}
	
	protected void initialize(XmlAssociationOverride associationOverride) {
		this.associationOverride = associationOverride;
		this.name = associationOverride.getName();
		initializeSpecifiedJoinColumns(associationOverride);
	}
	
	protected void initializeSpecifiedJoinColumns(XmlAssociationOverride associationOverride) {
		for (XmlJoinColumn joinColumn : associationOverride.getJoinColumns()) {
			this.specifiedJoinColumns.add(createJoinColumn(joinColumn));
		}
	}

	public void update(XmlAssociationOverride associationOverride) {
		this.associationOverride = associationOverride;
		this.setName(associationOverride.getName());
		updateSpecifiedJoinColumns(associationOverride);
	}	
	
	protected void updateSpecifiedJoinColumns(XmlAssociationOverride associationOverride) {
		ListIterator<OrmJoinColumn> joinColumns = specifiedJoinColumns();
		ListIterator<XmlJoinColumn> resourceJoinColumns = associationOverride.getJoinColumns().listIterator();
		
		while (joinColumns.hasNext()) {
			OrmJoinColumn joinColumn = joinColumns.next();
			if (resourceJoinColumns.hasNext()) {
				joinColumn.update(resourceJoinColumns.next());
			}
			else {
				removeSpecifiedJoinColumn(joinColumn);
			}
		}
		
		while (resourceJoinColumns.hasNext()) {
			addSpecifiedJoinColumn(specifiedJoinColumnsSize(), createJoinColumn(resourceJoinColumns.next()));
		}
	}
	
	protected OrmJoinColumn createJoinColumn(XmlJoinColumn joinColumn) {
		OrmJoinColumn ormJoinColumn = jpaFactory().buildOrmJoinColumn(this, new JoinColumnOwner());
		ormJoinColumn.initialize(joinColumn);
		return ormJoinColumn;
	}

	public TextRange validationTextRange() {
		// TODO Auto-generated method stub
		return null;
	}

	class JoinColumnOwner implements JoinColumn.Owner
	{

		public JoinColumnOwner() {
			super();
		}

		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String defaultTableName() {
			return GenericOrmAssociationOverride.this.owner.typeMapping().tableName();
		}
		
		public String defaultColumnName() {
			return null;
		}
		
		public Entity targetEntity() {
			RelationshipMapping relationshipMapping = relationshipMapping();
			return relationshipMapping == null ? null : relationshipMapping.getResolvedTargetEntity();
		}

		public String attributeName() {
			return GenericOrmAssociationOverride.this.getName();
		}

		public RelationshipMapping relationshipMapping() {
			return GenericOrmAssociationOverride.this.owner.relationshipMapping(GenericOrmAssociationOverride.this.getName());
		}

		public boolean tableNameIsInvalid(String tableName) {
			return typeMapping().tableNameIsInvalid(tableName);
		}

		/**
		 * the join column can be on a secondary table
		 */
		public boolean tableIsAllowed() {
			return true;
		}

		public TypeMapping typeMapping() {
			return GenericOrmAssociationOverride.this.owner.typeMapping();
		}

		public Table dbTable(String tableName) {
			return typeMapping().dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			Entity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity.primaryDbTable();
		}
		
		public boolean isVirtual(AbstractJoinColumn joinColumn) {
			return GenericOrmAssociationOverride.this.defaultJoinColumns.contains(joinColumn);
		}

		public int joinColumnsSize() {
			return GenericOrmAssociationOverride.this.joinColumnsSize();
		}

	}

}
