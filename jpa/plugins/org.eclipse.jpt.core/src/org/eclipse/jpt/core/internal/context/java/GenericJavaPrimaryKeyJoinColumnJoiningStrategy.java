/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnsAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public class GenericJavaPrimaryKeyJoinColumnJoiningStrategy
	extends AbstractJavaJpaContextNode
	implements JavaPrimaryKeyJoinColumnJoiningStrategy
{
	protected JavaResourcePersistentAttribute resourcePersistentAttribute;
	
	protected final Vector<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns = new Vector<JavaPrimaryKeyJoinColumn>();
	protected final JavaJoinColumn.Owner joinColumnOwner;
	
	
	public GenericJavaPrimaryKeyJoinColumnJoiningStrategy(
			JavaPrimaryKeyJoinColumnEnabledRelationshipReference parent) {
		super(parent);
		this.joinColumnOwner = this.buildJoinColumnOwner();
	}
	
	protected JavaJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	@Override
	public JavaPrimaryKeyJoinColumnEnabledRelationshipReference getParent() {
		return (JavaPrimaryKeyJoinColumnEnabledRelationshipReference) super.getParent();
	}
	
	public JavaPrimaryKeyJoinColumnEnabledRelationshipReference getRelationshipReference() {
		return getParent();
	}
	
	public JavaRelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getRelationshipMapping();
	}
	
	public boolean isOverridableAssociation() {
		return false;
	}
	
	// **************** primary key join columns *******************************
	
	public ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return new CloneListIterator<JavaPrimaryKeyJoinColumn>(this.primaryKeyJoinColumns);
	}
	
	public int primaryKeyJoinColumnsSize() {
		return this.primaryKeyJoinColumns.size();
	}
	
	public boolean hasPrimaryKeyJoinColumns() {
		return ! this.primaryKeyJoinColumns.isEmpty();
	}
	
	public JavaPrimaryKeyJoinColumn addPrimaryKeyJoinColumn(int index) {
		JavaPrimaryKeyJoinColumn pkJoinColumn = 
			getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, this.joinColumnOwner);
		this.primaryKeyJoinColumns.add(index, pkJoinColumn);
		PrimaryKeyJoinColumnAnnotation pkJoinColumnAnnotation = addAnnotation(index);
		pkJoinColumn.initialize(pkJoinColumnAnnotation);
		fireItemAdded(PRIMARY_KEY_JOIN_COLUMNS_LIST, index, pkJoinColumn);
		return pkJoinColumn;
	}
	
	protected void addPrimaryKeyJoinColumn(int index, JavaPrimaryKeyJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.primaryKeyJoinColumns, PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	protected void addPrimaryKeyJoinColumn(JavaPrimaryKeyJoinColumn joinColumn) {
		addPrimaryKeyJoinColumn(this.primaryKeyJoinColumns.size(), joinColumn);
	}
	
	public void removePrimaryKeyJoinColumn(PrimaryKeyJoinColumn pkJoinColumn) {
		removePrimaryKeyJoinColumn(this.primaryKeyJoinColumns.indexOf(pkJoinColumn));
	}
	
	public void removePrimaryKeyJoinColumn(int index) {
		JavaPrimaryKeyJoinColumn pkJoinColumn = this.primaryKeyJoinColumns.remove(index);
		removeAnnotation(index);
		fireItemRemoved(PRIMARY_KEY_JOIN_COLUMNS_LIST, index, pkJoinColumn);
	}

	protected void removePrimaryKeyJoinColumn_(JavaPrimaryKeyJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.primaryKeyJoinColumns, PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void movePrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.primaryKeyJoinColumns, targetIndex, sourceIndex);
		moveAnnotation(targetIndex, sourceIndex);
		fireItemMoved(PRIMARY_KEY_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	protected PrimaryKeyJoinColumnAnnotation addAnnotation(int index) {
		return (PrimaryKeyJoinColumnAnnotation) this.resourcePersistentAttribute.
			addAnnotation(
				index, 
				PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, 
				PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
	}
	
	protected void removeAnnotation(int index) {
		this.resourcePersistentAttribute.
			removeAnnotation(
				index, 
				PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, 
				PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
	}
	
	protected void moveAnnotation(int targetIndex, int sourceIndex) {
		this.resourcePersistentAttribute.
			moveAnnotation(
				targetIndex, 
				sourceIndex, 
				PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
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
	
	
	// **************** resource -> context ************************************
	
	public void initialize() {
		this.resourcePersistentAttribute = 
			getRelationshipReference().getRelationshipMapping().
				getPersistentAttribute().getResourcePersistentAttribute();
		initializePrimaryKeyJoinColumns();
	}
	
	protected void initializePrimaryKeyJoinColumns() {
		Iterator<NestableAnnotation> annotations = 
			this.resourcePersistentAttribute.annotations(
				PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, 
				PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		
		while (annotations.hasNext()) {
			this.primaryKeyJoinColumns.add(
				buildPrimaryKeyJoinColumn(
					(PrimaryKeyJoinColumnAnnotation) annotations.next()));
		}
	}
	
	public void update() {
		this.resourcePersistentAttribute = 
			getRelationshipReference().getRelationshipMapping().
				getPersistentAttribute().getResourcePersistentAttribute();
		updatePrimaryKeyJoinColumns();
	}
	
	protected void updatePrimaryKeyJoinColumns() {
		ListIterator<JavaPrimaryKeyJoinColumn> joinColumns = primaryKeyJoinColumns();
		Iterator<NestableAnnotation> annotations = 
			this.resourcePersistentAttribute.annotations(
				PrimaryKeyJoinColumnAnnotation.ANNOTATION_NAME, 
				PrimaryKeyJoinColumnsAnnotation.ANNOTATION_NAME);
		
		while (joinColumns.hasNext()) {
			JavaPrimaryKeyJoinColumn joinColumn = joinColumns.next();
			if (annotations.hasNext()) {
				joinColumn.update(
					(PrimaryKeyJoinColumnAnnotation) annotations.next());
			}
			else {
				removePrimaryKeyJoinColumn_(joinColumn);
			}
		}
		
		while (annotations.hasNext()) {
			addPrimaryKeyJoinColumn(
				buildPrimaryKeyJoinColumn(
					(PrimaryKeyJoinColumnAnnotation) annotations.next()));
		}
	}
	
	protected JavaPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(
			PrimaryKeyJoinColumnAnnotation annotation) {
		JavaPrimaryKeyJoinColumn joinColumn = 
			getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, this.joinColumnOwner);
		joinColumn.initialize(annotation);
		return joinColumn;
	}
	
	
	// **************** Java completion proposals ******************************
	
	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaPrimaryKeyJoinColumn column : CollectionTools.iterable(this.primaryKeyJoinColumns())) {
			result = column.javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	
	// **************** validation *********************************************
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getRelationshipReference().getValidationTextRange(astRoot);
	}
	
	
	// ********** join column owner adapter **********

	protected class JoinColumnOwner 
		implements JavaJoinColumn.Owner 
	{
		protected JoinColumnOwner() {
			super();
		}
		
		
		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String getDefaultTableName() {
			return getTypeMapping().getPrimaryTableName();
		}
		
		public Entity getTargetEntity() {
			return getRelationshipMapping().getResolvedTargetEntity();
		}
		
		public String getAttributeName() {
			return getRelationshipMapping().getName();
		}
		
		public RelationshipMapping getRelationshipMapping() {
			return GenericJavaPrimaryKeyJoinColumnJoiningStrategy.this.getRelationshipMapping();
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
			return getRelationshipMapping().getTypeMapping();
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
			// TODO Auto-generated method stub
			return null;
		}
		
		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return GenericJavaPrimaryKeyJoinColumnJoiningStrategy.this.getValidationTextRange(astRoot);
		}
		
		public int joinColumnsSize() {
			return GenericJavaPrimaryKeyJoinColumnJoiningStrategy.this.primaryKeyJoinColumnsSize();
		}
	}
}
