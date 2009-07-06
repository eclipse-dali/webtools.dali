/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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

import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;

public class GenericOrmAssociationOverride extends AbstractXmlContextNode
	implements OrmAssociationOverride
{
	protected final AssociationOverride.Owner owner;

	protected XmlAssociationOverride resourceAssociationOverride;

	protected String name;

	protected final Vector<OrmJoinColumn> specifiedJoinColumns = new Vector<OrmJoinColumn>();
	protected final OrmJoinColumn.Owner joinColumnOwner;


	public GenericOrmAssociationOverride(XmlContextNode parent, AssociationOverride.Owner owner, XmlAssociationOverride xmlAssociationOverride) {
		super(parent);
		this.owner = owner;
		this.resourceAssociationOverride = xmlAssociationOverride;
		this.name = xmlAssociationOverride.getName();
		this.joinColumnOwner = this.buildJoinColumnOwner();
		this.initializeSpecifiedJoinColumns();
	}
	
	protected OrmJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	public OrmAssociationOverride setVirtual(boolean virtual) {
		return (OrmAssociationOverride) getOwner().setVirtual(virtual, this);
	}
	
	public Owner getOwner() {
		return this.owner;
	}

	// ********** IAssociationOverride implementation **********

	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.resourceAssociationOverride.setName(newName);
		firePropertyChanged(BaseOverride.NAME_PROPERTY, oldName, newName);
	}

	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(BaseOverride.NAME_PROPERTY, oldName, newName);
	}

	public ListIterator<OrmJoinColumn> joinColumns() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumns() : this.specifiedJoinColumns();
	}
	
	public int joinColumnsSize() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumnsSize() : this.specifiedJoinColumnsSize();
	}
	
	public ListIterator<OrmJoinColumn> defaultJoinColumns() {
		return EmptyListIterator.instance();
	}
	
	public int defaultJoinColumnsSize() {
		return 0;
	}
	
	public ListIterator<OrmJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<OrmJoinColumn>(this.specifiedJoinColumns);
	}
	
	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}
	
	public OrmJoinColumn addSpecifiedJoinColumn(int index) {
		XmlJoinColumn resourceJoinColumn = OrmFactory.eINSTANCE.createXmlJoinColumn();
		OrmJoinColumn contextJoinColumn = buildJoinColumn(resourceJoinColumn);
		this.specifiedJoinColumns.add(index, contextJoinColumn);
		this.resourceAssociationOverride.getJoinColumns().add(index, resourceJoinColumn);
		this.fireItemAdded(AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST, index, contextJoinColumn);
		return contextJoinColumn;
	}
	
	protected void addSpecifiedJoinColumn(int index, OrmJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedJoinColumns, AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	protected void addSpecifiedJoinColumn(OrmJoinColumn joinColumn) {
		this.addSpecifiedJoinColumn(this.specifiedJoinColumns.size(), joinColumn);
	}
	
	public void removeSpecifiedJoinColumn(int index) {
		OrmJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		this.resourceAssociationOverride.getJoinColumns().remove(index);
		fireItemRemoved(Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedJoinColumn);
	}

	protected void removeSpecifiedJoinColumn(OrmJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		this.resourceAssociationOverride.getJoinColumns().move(targetIndex, sourceIndex);
		moveItemInList(targetIndex, sourceIndex, this.specifiedJoinColumns, AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);		
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.specifiedJoinColumns.isEmpty();
	}	

	public boolean isVirtual() {
		return getOwner().isVirtual(this);
	}
	
	protected void initializeSpecifiedJoinColumns() {
		for (XmlJoinColumn joinColumn : this.resourceAssociationOverride.getJoinColumns()) {
			this.specifiedJoinColumns.add(buildJoinColumn(joinColumn));
		}
	}

	public void update(XmlAssociationOverride xao) {
		this.resourceAssociationOverride = xao;
		this.setName_(xao.getName());
		updateSpecifiedJoinColumns();
	}	
	
	protected void updateSpecifiedJoinColumns() {
		// make a copy of the XML join columns (to prevent ConcurrentModificationException)
		Iterator<XmlJoinColumn> xmlJoinColumns = new CloneIterator<XmlJoinColumn>(this.resourceAssociationOverride.getJoinColumns());
		
		for (Iterator<OrmJoinColumn> contextJoinColumns = this.specifiedJoinColumns(); contextJoinColumns.hasNext(); ) {
			OrmJoinColumn contextJoinColumn = contextJoinColumns.next();
			if (xmlJoinColumns.hasNext()) {
				contextJoinColumn.update(xmlJoinColumns.next());
			}
			else {
				removeSpecifiedJoinColumn(contextJoinColumn);
			}
		}
		
		while (xmlJoinColumns.hasNext()) {
			addSpecifiedJoinColumn(buildJoinColumn(xmlJoinColumns.next()));
		}
	}
	
	protected OrmJoinColumn buildJoinColumn(XmlJoinColumn resourceJoinColumn) {
		return this.getJpaFactory().buildOrmJoinColumn(this, this.joinColumnOwner, resourceJoinColumn);
	}

	public TextRange getValidationTextRange() {
		return null;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
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
			return GenericOrmAssociationOverride.this.owner.getTypeMapping().getPrimaryTableName();
		}
		
		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}
		
		public Entity getTargetEntity() {
			RelationshipMapping relationshipMapping = getRelationshipMapping();
			return relationshipMapping == null ? null : relationshipMapping.getResolvedTargetEntity();
		}

		public String getAttributeName() {
			return GenericOrmAssociationOverride.this.getName();
		}

		public RelationshipMapping getRelationshipMapping() {
			return GenericOrmAssociationOverride.this.owner.getRelationshipMapping(GenericOrmAssociationOverride.this.getName());
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

		public TypeMapping getTypeMapping() {
			return GenericOrmAssociationOverride.this.owner.getTypeMapping();
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

		public int joinColumnsSize() {
			return GenericOrmAssociationOverride.this.joinColumnsSize();
		}
		
		public TextRange getValidationTextRange() {
			return null;
		}

	}

}
