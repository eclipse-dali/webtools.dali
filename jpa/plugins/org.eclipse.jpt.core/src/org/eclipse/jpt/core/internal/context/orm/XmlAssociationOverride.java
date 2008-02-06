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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IAssociationOverride;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.IOverride;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.context.base.JpaContextNode;
import org.eclipse.jpt.core.internal.resource.orm.AssociationOverride;
import org.eclipse.jpt.core.internal.resource.orm.JoinColumn;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class XmlAssociationOverride extends JpaContextNode
	implements IAssociationOverride
{

	protected String name;

	protected final List<XmlJoinColumn> specifiedJoinColumns;

	protected final List<XmlJoinColumn> defaultJoinColumns;

	private final Owner owner;

	protected AssociationOverride associationOverride;


	protected XmlAssociationOverride(IJpaContextNode parent, IOverride.Owner owner) {
		super(parent);
		this.owner = owner;
		this.specifiedJoinColumns = new ArrayList<XmlJoinColumn>();
		this.defaultJoinColumns = new ArrayList<XmlJoinColumn>();
	}
	
	// ********** IAssociationOverride implementation **********

	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		this.associationOverride.setName(newName);
		firePropertyChanged(IOverride.NAME_PROPERTY, oldName, newName);
	}

	@SuppressWarnings("unchecked")
	public ListIterator<XmlJoinColumn> joinColumns() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumns() : this.specifiedJoinColumns();
	}
	
	public int joinColumnsSize() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumnsSize() : this.specifiedJoinColumnsSize();
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<XmlJoinColumn> defaultJoinColumns() {
		return new CloneListIterator<XmlJoinColumn>(this.defaultJoinColumns);
	}
	
	public int defaultJoinColumnsSize() {
		return this.defaultJoinColumns.size();
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<XmlJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<XmlJoinColumn>(this.specifiedJoinColumns);
	}
	
	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}
	
	public XmlJoinColumn addSpecifiedJoinColumn(int index) {
		XmlJoinColumn joinColumn = new XmlJoinColumn(this, createJoinColumnOwner());
		this.specifiedJoinColumns.add(index, joinColumn);
		this.associationOverride.getJoinColumns().add(index, OrmFactory.eINSTANCE.createJoinColumnImpl());
		this.fireItemAdded(IAssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		return joinColumn;
	}
	
	protected IJoinColumn.Owner createJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	protected void addSpecifiedJoinColumn(int index, XmlJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedJoinColumns, IAssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void removeSpecifiedJoinColumn(int index) {
		XmlJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		this.associationOverride.getJoinColumns().remove(index);
		fireItemRemoved(IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedJoinColumn);
	}

	protected void removeSpecifiedJoinColumn(XmlJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, IAssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		this.associationOverride.getJoinColumns().move(targetIndex, sourceIndex);
		moveItemInList(targetIndex, sourceIndex, this.specifiedJoinColumns, IAssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);		
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.specifiedJoinColumns.isEmpty();
	}	

	public boolean isVirtual() {
		return this.owner.isVirtual(this);
	}
	
	
	public void initialize(AssociationOverride associationOverride) {
		this.associationOverride = associationOverride;
		this.name = associationOverride.getName();
		initializeSpecifiedJoinColumns(associationOverride);
	}
	
	protected void initializeSpecifiedJoinColumns(AssociationOverride associationOverride) {
		for (JoinColumn joinColumn : associationOverride.getJoinColumns()) {
			this.specifiedJoinColumns.add(createJoinColumn(joinColumn));
		}
	}

	public void update(AssociationOverride associationOverride) {
		this.associationOverride = associationOverride;
		this.setName(associationOverride.getName());
		updateSpecifiedJoinColumns(associationOverride);
	}	
	
	protected void updateSpecifiedJoinColumns(AssociationOverride associationOverride) {
		ListIterator<XmlJoinColumn> joinColumns = specifiedJoinColumns();
		ListIterator<JoinColumn> resourceJoinColumns = associationOverride.getJoinColumns().listIterator();
		
		while (joinColumns.hasNext()) {
			XmlJoinColumn joinColumn = joinColumns.next();
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
	
	protected XmlJoinColumn createJoinColumn(JoinColumn joinColumn) {
		XmlJoinColumn xmlJoinColumn = new XmlJoinColumn(this, new JoinColumnOwner());
		xmlJoinColumn.initialize(joinColumn);
		return xmlJoinColumn;
	}

	public class JoinColumnOwner implements IJoinColumn.Owner
	{

		public JoinColumnOwner() {
			super();
		}

		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String defaultTableName() {
			return XmlAssociationOverride.this.owner.typeMapping().tableName();
		}
		
		public String defaultColumnName() {
			return null;
		}
		
		public IEntity targetEntity() {
			return relationshipMapping().getResolvedTargetEntity();
		}

		public String attributeName() {
			return XmlAssociationOverride.this.getName();
		}

		public IRelationshipMapping relationshipMapping() {
			//TODO this isn't going to work, classCastException
			return (IRelationshipMapping) XmlAssociationOverride.this.owner.columnMapping(XmlAssociationOverride.this.getName());
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

		public ITextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}

		public ITypeMapping typeMapping() {
			return XmlAssociationOverride.this.owner.typeMapping();
		}

		public Table dbTable(String tableName) {
			return typeMapping().dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			IEntity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity().primaryDbTable();
		}
		
		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
			return XmlAssociationOverride.this.defaultJoinColumns.contains(joinColumn);
		}

		public int joinColumnsSize() {
			return XmlAssociationOverride.this.joinColumnsSize();
		}

	}

}
