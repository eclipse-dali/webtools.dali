/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaJoinColumnJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.core.internal.resource.java.NullJoinColumn;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinColumnsAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaJoinColumnJoiningStrategy 
	extends AbstractJavaJpaContextNode
	implements JavaJoinColumnJoiningStrategy
{
	protected JavaResourcePersistentAttribute resourcePersistentAttribute;
	
	protected JavaJoinColumn defaultJoinColumn;
	
	protected final List<JavaJoinColumn> specifiedJoinColumns;
	
	
	public GenericJavaJoinColumnJoiningStrategy(JavaJoinColumnEnabledRelationshipReference parent) {
		super(parent);
		this.specifiedJoinColumns = new ArrayList<JavaJoinColumn>();
	}
	
	
	@Override
	public JavaJoinColumnEnabledRelationshipReference getParent() {
		return (JavaJoinColumnEnabledRelationshipReference) super.getParent();
	}
	
	public JavaJoinColumnEnabledRelationshipReference getRelationshipReference() {
		return this.getParent();
	}
	
	public JavaRelationshipMapping getRelationshipMapping() {
		return this.getRelationshipReference().getRelationshipMapping();
	}
	
	public void addStrategy() {
		if (specifiedJoinColumnsSize() == 0) {
			addSpecifiedJoinColumn(0);
		}
	}
	
	public void removeStrategy() {
		for (JoinColumn each : CollectionTools.iterable(specifiedJoinColumns())) {
			removeSpecifiedJoinColumn(each);
		}
	}
	
	
	// **************** join columns *******************************************
	
	public ListIterator<JavaJoinColumn> joinColumns() {
		return this.hasSpecifiedJoinColumns() ? 
			this.specifiedJoinColumns() : this.defaultJoinColumns();
	}
	
	public int joinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? 
			this.specifiedJoinColumnsSize() : this.defaultJoinColumnsSize();
	}
	
	
	// **************** default join column ************************************
	
	public JavaJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}
	
	protected void setDefaultJoinColumn(JavaJoinColumn column) {
		JavaJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = column;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, old, column);
	}

	protected ListIterator<JavaJoinColumn> defaultJoinColumns() {
		if (this.defaultJoinColumn != null) {
			return new SingleElementListIterator<JavaJoinColumn>(this.defaultJoinColumn);
		}
		return EmptyListIterator.instance();
	}
	
	protected int defaultJoinColumnsSize() {
		return (this.defaultJoinColumn == null) ? 0 : 1;
	}
	
	
	// **************** specified join columns *********************************
	
	public ListIterator<JavaJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<JavaJoinColumn>(this.specifiedJoinColumns);
	}
	
	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}
	
	public boolean hasSpecifiedJoinColumns() {
		return ! this.specifiedJoinColumns.isEmpty();
	}
	
	public JavaJoinColumn addSpecifiedJoinColumn(int index) {
		JavaJoinColumn oldDefaultJoinColumn = this.defaultJoinColumn;
		if (oldDefaultJoinColumn != null) {
			//null the default join column now if one already exists.
			//if one does not exist, there is already a specified join column.
			//Remove it now so that it doesn't get removed during an update and
			//cause change notifications to be sent to the UI in the wrong order
			this.defaultJoinColumn = null;
		}
		JavaJoinColumn joinColumn = 
			getJpaFactory().buildJavaJoinColumn(this, createJoinColumnOwner());
		this.specifiedJoinColumns.add(index, joinColumn);
		JoinColumnAnnotation joinColumnAnnotation = addAnnotation(index);
		joinColumn.initialize(joinColumnAnnotation);
		fireItemAdded(SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		if (oldDefaultJoinColumn != null) {
			firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, oldDefaultJoinColumn, null);
		}
		return joinColumn;
	}

	protected void addSpecifiedJoinColumn(int index, JavaJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	protected void addSpecifiedJoinColumn(JavaJoinColumn joinColumn) {
		addSpecifiedJoinColumn(this.specifiedJoinColumns.size(), joinColumn);
	}

	public void removeSpecifiedJoinColumn(JoinColumn joinColumn) {
		removeSpecifiedJoinColumn(this.specifiedJoinColumns.indexOf(joinColumn));
	}
	
	public void removeSpecifiedJoinColumn(int index) {
		JavaJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		if (this.specifiedJoinColumns.isEmpty()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultJoinColumn = buildJoinColumn(
				new NullJoinColumn(this.resourcePersistentAttribute));
		}
		removeAnnotation(index);
		fireItemRemoved(SPECIFIED_JOIN_COLUMNS_LIST, index, removedJoinColumn);
		if (this.defaultJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			firePropertyChanged(DEFAULT_JOIN_COLUMN_PROPERTY, null, this.defaultJoinColumn);		
		}
	}

	protected void removeSpecifiedJoinColumn_(JavaJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedJoinColumns, targetIndex, sourceIndex);
		moveAnnotation(targetIndex, sourceIndex);
		fireItemMoved(SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	protected JoinColumnAnnotation addAnnotation(int index) {
		return (JoinColumnAnnotation) this.resourcePersistentAttribute.
			addSupportingAnnotation(
				index, 
				JoinColumnAnnotation.ANNOTATION_NAME, 
				JoinColumnsAnnotation.ANNOTATION_NAME);
	}
	
	protected void removeAnnotation(int index) {
		this.resourcePersistentAttribute.
			removeSupportingAnnotation(
				index, 
				JoinColumnAnnotation.ANNOTATION_NAME, 
				JoinColumnsAnnotation.ANNOTATION_NAME);
	}
	
	protected void moveAnnotation(int targetIndex, int sourceIndex) {
		this.resourcePersistentAttribute.
			moveSupportingAnnotation(
				targetIndex, 
				sourceIndex, 
				JoinColumnsAnnotation.ANNOTATION_NAME);
	}
	
	
	// **************** resource => context ************************************

	public void initialize() {
		this.resourcePersistentAttribute = 
			getRelationshipReference().getRelationshipMapping().
				getPersistentAttribute().getResourcePersistentAttribute();
		initializeSpecifiedJoinColumns();
		initializeDefaultJoinColumn();
	}
	
	protected void initializeSpecifiedJoinColumns() {
		ListIterator<NestableAnnotation> annotations = 
			this.resourcePersistentAttribute.supportingAnnotations(
				JoinColumnAnnotation.ANNOTATION_NAME, 
				JoinColumnsAnnotation.ANNOTATION_NAME);
		while (annotations.hasNext()) {
			this.specifiedJoinColumns.add(buildJoinColumn((JoinColumnAnnotation) annotations.next()));
		}
	}
	
	public void initializeDefaultJoinColumn() {
		if (mayHaveDefaultJoinColumn()) {
			this.defaultJoinColumn = 
				buildJoinColumn(new NullJoinColumn(this.resourcePersistentAttribute));
		}
	}
	
	public void update() {
		this.resourcePersistentAttribute = 
			getRelationshipReference().getRelationshipMapping().
				getPersistentAttribute().getResourcePersistentAttribute();
		updateSpecifiedJoinColumns();
		updateDefaultJoinColumn();
	}
	
	protected void updateSpecifiedJoinColumns() {
		ListIterator<JavaJoinColumn> joinColumns = specifiedJoinColumns();
		ListIterator<NestableAnnotation> resourceJoinColumns = this.resourcePersistentAttribute.supportingAnnotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
		
		while (joinColumns.hasNext()) {
			JavaJoinColumn joinColumn = joinColumns.next();
			if (resourceJoinColumns.hasNext()) {
				joinColumn.update((JoinColumnAnnotation) resourceJoinColumns.next());
			}
			else {
				removeSpecifiedJoinColumn_(joinColumn);
			}
		}
		
		while (resourceJoinColumns.hasNext()) {
			addSpecifiedJoinColumn(buildJoinColumn((JoinColumnAnnotation) resourceJoinColumns.next()));
		}
	}
	
	protected void updateDefaultJoinColumn() {
		if (mayHaveDefaultJoinColumn()) {
			JoinColumnAnnotation nullAnnotation = 
				new NullJoinColumn(this.resourcePersistentAttribute);
			if (this.defaultJoinColumn == null) {
				setDefaultJoinColumn(this.buildJoinColumn(nullAnnotation));
			}
			this.defaultJoinColumn.update(nullAnnotation);
		}
		else {
			if (this.defaultJoinColumn != null) {
				setDefaultJoinColumn(null);
			}
		}
	}
	
	protected boolean mayHaveDefaultJoinColumn() {
		return getRelationshipReference().mayHaveDefaultJoinColumn()
			&& ! hasSpecifiedJoinColumns();
	}

	protected JavaJoinColumn buildJoinColumn(JoinColumnAnnotation joinColumnResource) {
		JavaJoinColumn joinColumn = getJpaFactory().buildJavaJoinColumn(this, createJoinColumnOwner());
		joinColumn.initialize(joinColumnResource);
		return joinColumn;
	}
	
	protected JavaJoinColumn.Owner createJoinColumnOwner() {
		return new JoinColumnOwner();
	}
	
	
	// **************** Java completion proposals ******************************
	
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
	
	
	// **************** validation *********************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (getRelationshipMapping().shouldValidateAgainstDatabase()) {
			for (Iterator<JavaJoinColumn> stream = this.joinColumns(); stream.hasNext(); ) {
				validateJoinColumn(stream.next(), messages, astRoot);
			}
		}
	}

	protected void validateJoinColumn(JavaJoinColumn joinColumn, List<IMessage> messages, CompilationUnit astRoot) {
		if (getRelationshipMapping().getTypeMapping().tableNameIsInvalid(joinColumn.getTable())) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_TABLE,
					new String[] {joinColumn.getTable(), joinColumn.getName()}, 
					joinColumn,
					joinColumn.getTableTextRange(astRoot)
				)
			);
			return;
		}

		if ( ! joinColumn.isResolved()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
					new String[] {joinColumn.getName()}, 
					joinColumn,
					joinColumn.getNameTextRange(astRoot)
				)
			);
		}

		if ( ! joinColumn.isReferencedColumnResolved()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
					new String[] {joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
					joinColumn,
					joinColumn.getReferencedColumnNameTextRange(astRoot)
				)
			);
		}
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getRelationshipReference().getValidationTextRange(astRoot);
	}


	// **************** join column owner adapter ******************************

	public class JoinColumnOwner 
		implements JavaJoinColumn.Owner 
	{
		public JoinColumnOwner() {
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
			return getRelationshipMapping().getPersistentAttribute().getName();
		}
		
		public RelationshipMapping getRelationshipMapping() {
			return GenericJavaJoinColumnJoiningStrategy.this.getRelationshipMapping();
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
			return GenericJavaJoinColumnJoiningStrategy.this.defaultJoinColumn == joinColumn;
		}
		
		public String getDefaultColumnName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return GenericJavaJoinColumnJoiningStrategy.this.getValidationTextRange(astRoot);
		}
		
		public int joinColumnsSize() {
			return GenericJavaJoinColumnJoiningStrategy.this.joinColumnsSize();
		}
	}
}
