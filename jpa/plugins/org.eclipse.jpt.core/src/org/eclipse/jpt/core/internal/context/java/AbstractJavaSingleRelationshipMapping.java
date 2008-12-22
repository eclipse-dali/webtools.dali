/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.Nullable;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaSingleRelationshipMapping;
import org.eclipse.jpt.core.internal.resource.java.NullJoinColumn;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinColumnsAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 */
public abstract class AbstractJavaSingleRelationshipMapping<T extends RelationshipMappingAnnotation>
	extends AbstractJavaRelationshipMapping<T>
	implements JavaSingleRelationshipMapping
{
	
	protected final List<JavaJoinColumn> specifiedJoinColumns;
	protected JavaJoinColumn defaultJoinColumn;

	protected Boolean specifiedOptional;


	protected AbstractJavaSingleRelationshipMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.specifiedJoinColumns = new ArrayList<JavaJoinColumn>();
	}


	// ********** join columns **********

	public ListIterator<JavaJoinColumn> joinColumns() {
		return this.containsSpecifiedJoinColumns() ? this.specifiedJoinColumns() : this.defaultJoinColumns();
	}

	public int joinColumnsSize() {
		return this.containsSpecifiedJoinColumns() ? this.specifiedJoinColumnsSize() : this.defaultJoinColumnsSize();
	}
	
	public ListIterator<JavaJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<JavaJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean containsSpecifiedJoinColumns() {
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
		JavaJoinColumn joinColumn = this.getJpaFactory().buildJavaJoinColumn(this, this.createJoinColumnOwner());
		this.specifiedJoinColumns.add(index, joinColumn);
		JoinColumnAnnotation joinColumnAnnotation = (JoinColumnAnnotation) this.getResourcePersistentAttribute().addSupportingAnnotation(index, JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
		joinColumn.initialize(joinColumnAnnotation);
		this.fireItemAdded(SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		if (oldDefaultJoinColumn != null) {
			this.firePropertyChanged(DEFAULT_JOIN_COLUMN, oldDefaultJoinColumn, null);
		}
		return joinColumn;
	}

	protected void addSpecifiedJoinColumn(int index, JavaJoinColumn joinColumn) {
		this.addItemToList(index, joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}

	protected void addSpecifiedJoinColumn(JavaJoinColumn joinColumn) {
		this.addSpecifiedJoinColumn(this.specifiedJoinColumns.size(), joinColumn);
	}

	public void removeSpecifiedJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumns.indexOf(joinColumn));
	}
	
	public void removeSpecifiedJoinColumn(int index) {
		JavaJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		if (this.specifiedJoinColumns.isEmpty()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultJoinColumn = this.buildJoinColumn(new NullJoinColumn(this.getResourcePersistentAttribute()));
		}
		this.getResourcePersistentAttribute().removeSupportingAnnotation(index, JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
		this.fireItemRemoved(SPECIFIED_JOIN_COLUMNS_LIST, index, removedJoinColumn);
		if (this.defaultJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(DEFAULT_JOIN_COLUMN, null, this.defaultJoinColumn);		
		}
	}

	protected void removeSpecifiedJoinColumn_(JavaJoinColumn joinColumn) {
		this.removeItemFromList(joinColumn, this.specifiedJoinColumns, SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedJoinColumns, targetIndex, sourceIndex);
		this.getResourcePersistentAttribute().moveSupportingAnnotation(targetIndex, sourceIndex, JoinColumnsAnnotation.ANNOTATION_NAME);
		this.fireItemMoved(SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}

	public JavaJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}
	
	protected void setDefaultJoinColumn(JavaJoinColumn column) {
		JavaJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = column;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN, old, column);
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


	// ********** optional **********

	public boolean isOptional() {
		return (this.specifiedOptional != null) ? this.specifiedOptional.booleanValue() : this.isDefaultOptional();
	}

	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}

	public void setSpecifiedOptional(Boolean optional) {
		Boolean old = this.specifiedOptional;
		this.specifiedOptional = optional;
		this.setOptionalOnResourceModel(optional);
		this.firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, old, optional);
	}
	
	protected void setSpecifiedOptional_(Boolean optional) {
		Boolean old = this.specifiedOptional;
		this.specifiedOptional = optional;
		this.firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, old, optional);
	}
	
	public boolean isDefaultOptional() {
		return Nullable.DEFAULT_OPTIONAL;
	}
	
	protected abstract void setOptionalOnResourceModel(Boolean newOptional);


	// ********** resource => context **********

	@Override
	public void initialize(JavaResourcePersistentAttribute javaResourcePersistentAttribute) {
		super.initialize(javaResourcePersistentAttribute);
		this.initializeSpecifiedJoinColumns(javaResourcePersistentAttribute);
		this.initializeDefaultJoinColumn(javaResourcePersistentAttribute);
	}
	
	@Override
	protected void initialize(T relationshipMapping) {
		super.initialize(relationshipMapping);
		this.specifiedOptional = this.buildSpecifiedOptional(relationshipMapping);
	}
	
	protected void initializeSpecifiedJoinColumns(JavaResourcePersistentAttribute javaResourcePersistentAttribute) {
		ListIterator<NestableAnnotation> annotations = javaResourcePersistentAttribute.supportingAnnotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.specifiedJoinColumns.add(buildJoinColumn((JoinColumnAnnotation) annotations.next()));
		}
	}
	
	protected void initializeDefaultJoinColumn(JavaResourcePersistentAttribute javaResourcePersistentAttribute) {
		if (this.shouldBuildDefaultJoinColumn()) {
			this.defaultJoinColumn = this.buildJoinColumn(new NullJoinColumn(javaResourcePersistentAttribute));
		}
	}	
	
	protected boolean shouldBuildDefaultJoinColumn() {
		return ! this.containsSpecifiedJoinColumns() && this.isRelationshipOwner();
	}
	
	@Override
	public void update(JavaResourcePersistentAttribute javaResourcePersistentAttribute) {
		super.update(javaResourcePersistentAttribute);
		this.updateSpecifiedJoinColumns(javaResourcePersistentAttribute);
		this.updateDefaultJoinColumn(javaResourcePersistentAttribute);
	}
	
	@Override
	protected void update(T relationshipMapping) {
		super.update(relationshipMapping);
		this.setSpecifiedOptional_(this.buildSpecifiedOptional(relationshipMapping));
	}
	
	protected abstract Boolean buildSpecifiedOptional(T relationshipMapping);
	
	
	protected void updateSpecifiedJoinColumns(JavaResourcePersistentAttribute javaResourcePersistentAttribute) {
		ListIterator<JavaJoinColumn> joinColumns = specifiedJoinColumns();
		ListIterator<NestableAnnotation> resourceJoinColumns = javaResourcePersistentAttribute.supportingAnnotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumnsAnnotation.ANNOTATION_NAME);
		
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
	
	protected void updateDefaultJoinColumn(JavaResourcePersistentAttribute javaResourcePersistentAttribute) {
		if (this.shouldBuildDefaultJoinColumn()) {
			JoinColumnAnnotation jcAnnotation = new NullJoinColumn(javaResourcePersistentAttribute);
			if (this.defaultJoinColumn == null) {
				this.setDefaultJoinColumn(this.buildJoinColumn(jcAnnotation));
			} else {
				this.defaultJoinColumn.update(jcAnnotation);
			}
		} else {
			this.setDefaultJoinColumn(null);
		}
	}	

	protected JavaJoinColumn buildJoinColumn(JoinColumnAnnotation joinColumnResource) {
		JavaJoinColumn joinColumn = getJpaFactory().buildJavaJoinColumn(this, createJoinColumnOwner());
		joinColumn.initialize(joinColumnResource);
		return joinColumn;
	}
	
	protected JavaJoinColumn.Owner createJoinColumnOwner() {
		return new JoinColumnOwner();
	}
	
	/**
	 * eliminate any "container" types
	 */
	@Override
	protected String buildDefaultTargetEntity(JavaResourcePersistentAttribute jrpa) {
		if (jrpa.typeIsContainer()) {
			return null;
		}
		return jrpa.getQualifiedReferenceEntityTypeName();
	}


	// ********** Fetchable implementation **********

	public FetchType getDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}


	// ********** Java completion proposals **********

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
	

	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, CompilationUnit astRoot) {
		super.validate(messages, astRoot);
		
		if (this.connectionProfileIsActive()) {
			this.validateJoinColumns(messages, astRoot);
		}
	}
	
	// 192287 - We don't want join column validation errors on the non-owning side
	// of a bidirectional relationship. This is a low risk fix for RC3, but a better
	// solution would be to not have the default join columns on the non-owning side.
	// This would fix another bug that we show default join columns in this situation.
	protected void validateJoinColumns(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.ownerIsEntity() && this.isRelationshipOwner()) {
			for (Iterator<JavaJoinColumn> stream = this.joinColumns(); stream.hasNext(); ) {
				this.validateJoinColumn(stream.next(), messages, astRoot);
			}
		}
	}

	protected void validateJoinColumn(JavaJoinColumn joinColumn, List<IMessage> messages, CompilationUnit astRoot) {
		if (this.getTypeMapping().tableNameIsInvalid(joinColumn.getTable())) {
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


	// ********** join column owner adapter **********

	public class JoinColumnOwner implements JavaJoinColumn.Owner {

		public JoinColumnOwner() {
			super();
		}

		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String getDefaultTableName() {
			return AbstractJavaSingleRelationshipMapping.this.getTypeMapping().getPrimaryTableName();
		}

		public Entity getTargetEntity() {
			return AbstractJavaSingleRelationshipMapping.this.getResolvedTargetEntity();
		}

		public String getAttributeName() {
			return AbstractJavaSingleRelationshipMapping.this.getPersistentAttribute().getName();
		}

		public RelationshipMapping getRelationshipMapping() {
			return AbstractJavaSingleRelationshipMapping.this;
		}

		public boolean tableNameIsInvalid(String tableName) {
			return AbstractJavaSingleRelationshipMapping.this.getTypeMapping().tableNameIsInvalid(tableName);
		}

		/**
		 * the join column can be on a secondary table
		 */
		public boolean tableIsAllowed() {
			return true;
		}

		public TypeMapping getTypeMapping() {
			return AbstractJavaSingleRelationshipMapping.this.getTypeMapping();
		}

		public Table getDbTable(String tableName) {
			return getTypeMapping().getDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			Entity targetEntity = getTargetEntity();
			return (targetEntity == null) ? null : targetEntity.getPrimaryDbTable();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return AbstractJavaSingleRelationshipMapping.this.defaultJoinColumn == joinColumn;
		}
		
		public String getDefaultColumnName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return AbstractJavaSingleRelationshipMapping.this.getValidationTextRange(astRoot);
		}
		
		public int joinColumnsSize() {
			return AbstractJavaSingleRelationshipMapping.this.joinColumnsSize();
		}

	}

}
