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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class GenericJavaAssociationOverride extends JavaOverride<AssociationOverrideAnnotation>
	implements JavaAssociationOverride
{

	protected final List<JavaJoinColumn> specifiedJoinColumns;

	protected final List<JavaJoinColumn> defaultJoinColumns;


	public GenericJavaAssociationOverride(JavaJpaContextNode parent, AssociationOverride.Owner owner) {
		super(parent, owner);
		this.specifiedJoinColumns = new ArrayList<JavaJoinColumn>();
		this.defaultJoinColumns = new ArrayList<JavaJoinColumn>();
	}

	@Override
	public AssociationOverride.Owner owner() {
		return (AssociationOverride.Owner) super.owner();
	}
	
	public ListIterator<JavaJoinColumn> joinColumns() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumns() : this.specifiedJoinColumns();
	}
	
	public int joinColumnsSize() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumnsSize() : this.specifiedJoinColumnsSize();
	}
	
	public ListIterator<JavaJoinColumn> defaultJoinColumns() {
		return new CloneListIterator<JavaJoinColumn>(this.defaultJoinColumns);
	}
	
	public int defaultJoinColumnsSize() {
		return this.defaultJoinColumns.size();
	}
	
	public ListIterator<JavaJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<JavaJoinColumn>(this.specifiedJoinColumns);
	}
	
	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}
	
	public JavaJoinColumn addSpecifiedJoinColumn(int index) {
		JavaJoinColumn joinColumn = jpaFactory().buildJavaJoinColumn(this, createJoinColumnOwner());
		this.specifiedJoinColumns.add(index, joinColumn);
		JoinColumnAnnotation joinColumnResource = this.overrideResource.addJoinColumn(index);
		joinColumn.initializeFromResource(joinColumnResource);
		this.fireItemAdded(AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		return joinColumn;
	}
	
	protected JoinColumn.Owner createJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	protected void addSpecifiedJoinColumn(int index, JavaJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedJoinColumns, AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void removeSpecifiedJoinColumn(int index) {
		JavaJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		this.overrideResource.removeJoinColumn(index);
		fireItemRemoved(Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedJoinColumn);
	}

	protected void removeSpecifiedJoinColumn(JavaJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		this.overrideResource.moveJoinColumn(targetIndex, sourceIndex);
		moveItemInList(targetIndex, sourceIndex, this.specifiedJoinColumns, AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST);		
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.specifiedJoinColumns.isEmpty();
	}


	@Override
	protected Iterator<String> candidateNames() {
		return this.owner().typeMapping().allOverridableAssociationNames();
	}

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaJoinColumn column : CollectionTools.iterable(this.joinColumns())) {
			result = column.javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public void initializeFromResource(AssociationOverrideAnnotation associationOverride) {
		super.initializeFromResource(associationOverride);
		this.name = associationOverride.getName();
		initializeSpecifiedJoinColumns(associationOverride);
	}
	
	protected void initializeSpecifiedJoinColumns(AssociationOverrideAnnotation associationOverride) {
		ListIterator<JoinColumnAnnotation> annotations = associationOverride.joinColumns();
		
		while(annotations.hasNext()) {
			this.specifiedJoinColumns.add(createJoinColumn(annotations.next()));
		}
	}

	@Override
	public void update(AssociationOverrideAnnotation associationOverride) {
		super.update(associationOverride);
		updateSpecifiedJoinColumns(associationOverride);
	}

	protected void updateSpecifiedJoinColumns(AssociationOverrideAnnotation associationOverride) {
		ListIterator<JavaJoinColumn> joinColumns = specifiedJoinColumns();
		ListIterator<JoinColumnAnnotation> resourceJoinColumns = associationOverride.joinColumns();
		
		while (joinColumns.hasNext()) {
			JavaJoinColumn joinColumn = joinColumns.next();
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
	
	
	protected JavaJoinColumn createJoinColumn(JoinColumnAnnotation joinColumnResource) {
		JavaJoinColumn joinColumn = jpaFactory().buildJavaJoinColumn(this, createJoinColumnOwner());
		joinColumn.initializeFromResource(joinColumnResource);
		return joinColumn;
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
			return GenericJavaAssociationOverride.this.owner.typeMapping().tableName();
		}
		
		public String defaultColumnName() {
			return null;
		}
		
		public Entity targetEntity() {
			RelationshipMapping relationshipMapping = relationshipMapping();
			return relationshipMapping == null ? null : relationshipMapping.getResolvedTargetEntity();
		}

		public String attributeName() {
			return GenericJavaAssociationOverride.this.getName();
		}

		public RelationshipMapping relationshipMapping() {
			return GenericJavaAssociationOverride.this.owner().relationshipMapping(GenericJavaAssociationOverride.this.getName());
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

		public TextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}

		public TypeMapping typeMapping() {
			return GenericJavaAssociationOverride.this.owner.typeMapping();
		}

		public Table dbTable(String tableName) {
			return typeMapping().dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			Entity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity.primaryDbTable();
		}
		
		public boolean isVirtual(AbstractJoinColumn joinColumn) {
			return GenericJavaAssociationOverride.this.defaultJoinColumns.contains(joinColumn);
		}

		public int joinColumnsSize() {
			return GenericJavaAssociationOverride.this.joinColumnsSize();
		}
	}
}
