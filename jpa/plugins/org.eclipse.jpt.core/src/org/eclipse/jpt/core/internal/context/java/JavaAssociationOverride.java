/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IAssociationOverride;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.resource.java.AssociationOverride;
import org.eclipse.jpt.core.internal.resource.java.JoinColumn;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class JavaAssociationOverride extends JavaOverride<AssociationOverride>
	implements IJavaAssociationOverride
{

	protected final List<IJavaJoinColumn> specifiedJoinColumns;

	protected final List<IJavaJoinColumn> defaultJoinColumns;


	public JavaAssociationOverride(IJavaJpaContextNode parent, Owner owner) {
		super(parent, owner);
		this.specifiedJoinColumns = new ArrayList<IJavaJoinColumn>();
		this.defaultJoinColumns = new ArrayList<IJavaJoinColumn>();
	}

	public ListIterator<IJavaJoinColumn> joinColumns() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumns() : this.specifiedJoinColumns();
	}
	
	public int joinColumnsSize() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumnsSize() : this.specifiedJoinColumnsSize();
	}
	
	public ListIterator<IJavaJoinColumn> defaultJoinColumns() {
		return new CloneListIterator<IJavaJoinColumn>(this.defaultJoinColumns);
	}
	
	public int defaultJoinColumnsSize() {
		return this.defaultJoinColumns.size();
	}
	
	public ListIterator<IJavaJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<IJavaJoinColumn>(this.specifiedJoinColumns);
	}
	
	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}
	
	public IJavaJoinColumn addSpecifiedJoinColumn(int index) {
		IJavaJoinColumn joinColumn = jpaFactory().createJavaJoinColumn(this, createJoinColumnOwner());
		this.specifiedJoinColumns.add(index, joinColumn);
		this.overrideResource.addJoinColumn(index);
		this.fireItemAdded(IAssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		return joinColumn;
	}
	
	protected IJoinColumn.Owner createJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	protected void addSpecifiedJoinColumn(int index, IJavaJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedJoinColumns, IAssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void removeSpecifiedJoinColumn(int index) {
		IJavaJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		this.overrideResource.removeJoinColumn(index);
		fireItemRemoved(IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedJoinColumn);
	}

	protected void removeSpecifiedJoinColumn(IJavaJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, IAssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		this.overrideResource.moveJoinColumn(targetIndex, sourceIndex);
		moveItemInList(targetIndex, sourceIndex, this.specifiedJoinColumns, IAssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);		
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.specifiedJoinColumns.isEmpty();
	}


	@Override
	protected Iterator<String> candidateNames() {
		return this.owner().typeMapping().allOverridableAssociationNames();
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (IJavaJoinColumn column : CollectionTools.iterable(this.joinColumns())) {
			result = column.candidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public void initializeFromResource(AssociationOverride associationOverride) {
		super.initializeFromResource(associationOverride);
		this.name = associationOverride.getName();
		initializeSpecifiedJoinColumns(associationOverride);
	}
	
	protected void initializeSpecifiedJoinColumns(AssociationOverride associationOverride) {
		ListIterator<JoinColumn> annotations = associationOverride.joinColumns();
		
		while(annotations.hasNext()) {
			this.specifiedJoinColumns.add(createJoinColumn(annotations.next()));
		}
	}

	@Override
	public void update(AssociationOverride associationOverride) {
		super.update(associationOverride);
		this.setName(associationOverride.getName());
		updateSpecifiedJoinColumns(associationOverride);
	}

	protected void updateSpecifiedJoinColumns(AssociationOverride associationOverride) {
		ListIterator<IJavaJoinColumn> joinColumns = specifiedJoinColumns();
		ListIterator<JoinColumn> resourceJoinColumns = associationOverride.joinColumns();
		
		while (joinColumns.hasNext()) {
			IJavaJoinColumn joinColumn = joinColumns.next();
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
	
	
	protected IJavaJoinColumn createJoinColumn(JoinColumn joinColumnResource) {
		IJavaJoinColumn joinColumn = jpaFactory().createJavaJoinColumn(this, createJoinColumnOwner());
		joinColumn.initializeFromResource(joinColumnResource);
		return joinColumn;
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
			return JavaAssociationOverride.this.owner.typeMapping().tableName();
		}
		
		public String defaultColumnName() {
			return null;
		}
		
		public IEntity targetEntity() {
			return relationshipMapping().getResolvedTargetEntity();
		}

		public String attributeName() {
			return JavaAssociationOverride.this.getName();
		}

		public IRelationshipMapping relationshipMapping() {
			//TODO this isn't going to work, classCastException
			return (IRelationshipMapping) JavaAssociationOverride.this.owner.columnMapping(JavaAssociationOverride.this.getName());
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
			return JavaAssociationOverride.this.owner.typeMapping();
		}

		public Table dbTable(String tableName) {
			return typeMapping().dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			IEntity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity().primaryDbTable();
		}
		
		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
			return JavaAssociationOverride.this.defaultJoinColumns.contains(joinColumn);
		}

		public int joinColumnsSize() {
			return JavaAssociationOverride.this.joinColumnsSize();
		}
	}
}
