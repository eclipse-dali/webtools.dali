/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.INullable;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.core.internal.context.base.ISingleRelationshipMapping;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResource;
import org.eclipse.jpt.core.internal.resource.java.JoinColumn;
import org.eclipse.jpt.core.internal.resource.java.JoinColumns;
import org.eclipse.jpt.core.internal.resource.java.NullJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.RelationshipMapping;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;


public abstract class JavaSingleRelationshipMapping<T extends RelationshipMapping>
	extends JavaRelationshipMapping<T> implements IJavaSingleRelationshipMapping
{
	
	protected final List<IJavaJoinColumn> specifiedJoinColumns;

	protected final IJavaJoinColumn defaultJoinColumn;

	protected Boolean specifiedOptional;

	protected JavaSingleRelationshipMapping(IJavaPersistentAttribute parent) {
		super(parent);
		this.specifiedJoinColumns = new ArrayList<IJavaJoinColumn>();
		this.defaultJoinColumn = jpaFactory().createJavaJoinColumn(this, createJoinColumnOwner());
	}
	
	public FetchType getDefaultFetch() {
		return ISingleRelationshipMapping.DEFAULT_FETCH_TYPE;
	}
	
	//***************** ISingleRelationshipMapping implementation *****************
	public ListIterator<IJavaJoinColumn> joinColumns() {
		return this.specifiedJoinColumns.isEmpty() ? this.defaultJoinColumns() : this.specifiedJoinColumns();
	}

	public ListIterator<IJavaJoinColumn> defaultJoinColumns() {
		return new SingleElementListIterator<IJavaJoinColumn>(this.defaultJoinColumn);
	}

	public ListIterator<IJavaJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<IJavaJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.specifiedJoinColumns.isEmpty();
	}

	public IJavaJoinColumn addSpecifiedJoinColumn(int index) {
		IJavaJoinColumn joinColumn = jpaFactory().createJavaJoinColumn(this, createJoinColumnOwner());
		this.specifiedJoinColumns.add(index, joinColumn);
		this.persistentAttributeResource.addAnnotation(index, JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		this.fireItemAdded(ISingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		return joinColumn;
	}

	protected void addSpecifiedJoinColumn(int index, IJavaJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedJoinColumns, ISingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST);
	}

	public void removeSpecifiedJoinColumn(int index) {
		IJavaJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		this.persistentAttributeResource.removeAnnotation(index, JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		fireItemRemoved(ISingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST, index, removedJoinColumn);
	}

	protected void removeSpecifiedJoinColumn(IJavaJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, ISingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedJoinColumns, targetIndex, sourceIndex);
		this.persistentAttributeResource.move(targetIndex, sourceIndex, JoinColumns.ANNOTATION_NAME);
		fireItemMoved(ISingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}

	public Boolean getOptional() {
		return getSpecifiedOptional() == null ? getDefaultOptional() : getSpecifiedOptional();
	}
	
	public Boolean getDefaultOptional() {
		return INullable.DEFAULT_OPTIONAL;
	}
	
	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}
	
	public void setSpecifiedOptional(Boolean newSpecifiedOptional) {
		Boolean oldSpecifiedOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		setOptionalOnResourceModel(newSpecifiedOptional);
		firePropertyChanged(INullable.SPECIFIED_OPTIONAL_PROPERTY, oldSpecifiedOptional, newSpecifiedOptional);
	}

	protected abstract void setOptionalOnResourceModel(Boolean newOptional);


	@Override
	public void initializeFromResource(JavaPersistentAttributeResource persistentAttributeResource) {
		super.initializeFromResource(persistentAttributeResource);
		this.initializeSpecifiedJoinColumns(persistentAttributeResource);
		this.defaultJoinColumn.initializeFromResource(new NullJoinColumn(persistentAttributeResource));
	}
	
	@Override
	protected void initialize(T relationshipMapping) {
		super.initialize(relationshipMapping);
		this.specifiedOptional = this.specifiedOptional(relationshipMapping);
	}
	
	protected void initializeSpecifiedJoinColumns(JavaPersistentAttributeResource persistentAttributeResource) {
		ListIterator<JavaResource> annotations = persistentAttributeResource.annotations(JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.specifiedJoinColumns.add(createJoinColumn((JoinColumn) annotations.next()));
		}
	}
	
	@Override
	public void update(JavaPersistentAttributeResource persistentAttributeResource) {
		super.update(persistentAttributeResource);
		this.updateSpecifiedJoinColumns(persistentAttributeResource);
		this.defaultJoinColumn.update(new NullJoinColumn(persistentAttributeResource));
	}
	
	@Override
	protected void update(T relationshipMapping) {
		super.update(relationshipMapping);
		this.setSpecifiedOptional(this.specifiedOptional(relationshipMapping));
	}
	
	protected abstract Boolean specifiedOptional(T relationshipMapping);
	
	
	protected void updateSpecifiedJoinColumns(JavaPersistentAttributeResource persistentAttributeResource) {
		ListIterator<IJavaJoinColumn> joinColumns = specifiedJoinColumns();
		ListIterator<JavaResource> resourceJoinColumns = persistentAttributeResource.annotations(JoinColumn.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		
		while (joinColumns.hasNext()) {
			IJavaJoinColumn primaryKeyJoinColumn = joinColumns.next();
			if (resourceJoinColumns.hasNext()) {
				primaryKeyJoinColumn.update((JoinColumn) resourceJoinColumns.next());
			}
			else {
				removeSpecifiedJoinColumn(primaryKeyJoinColumn);
			}
		}
		
		while (resourceJoinColumns.hasNext()) {
			addSpecifiedJoinColumn(specifiedJoinColumnsSize(), createJoinColumn((JoinColumn) resourceJoinColumns.next()));
		}
	}
	
	protected IJavaJoinColumn createJoinColumn(JoinColumn joinColumnResource) {
		IJavaJoinColumn joinColumn = jpaFactory().createJavaJoinColumn(this, createJoinColumnOwner());
		joinColumn.initializeFromResource(joinColumnResource);
		return joinColumn;
	}
	
	protected IJoinColumn.Owner createJoinColumnOwner() {
		return new JoinColumnOwner();
	}
	
	/**
	 * eliminate any "container" types
	 */
	@Override
	protected String defaultTargetEntity(JavaPersistentAttributeResource persistentAttributeResource) {
		if (persistentAttributeResource.typeIsContainer()) {
			return null;
		}
		return persistentAttributeResource.getQualifiedReferenceEntityTypeName();
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

	public class JoinColumnOwner implements IJoinColumn.Owner
	{

		public JoinColumnOwner() {
			super();
		}

		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String defaultTableName() {
			return JavaSingleRelationshipMapping.this.typeMapping().getTableName();
		}

		public IEntity targetEntity() {
			return JavaSingleRelationshipMapping.this.getResolvedTargetEntity();
		}

		public String attributeName() {
			return JavaSingleRelationshipMapping.this.persistentAttribute().getName();
		}

		public IRelationshipMapping relationshipMapping() {
			return JavaSingleRelationshipMapping.this;
		}

		public boolean tableNameIsInvalid(String tableName) {
			return JavaSingleRelationshipMapping.this.typeMapping().tableNameIsInvalid(tableName);
		}

		/**
		 * the join column can be on a secondary table
		 */
		public boolean tableIsAllowed() {
			return true;
		}

		public ITypeMapping typeMapping() {
			return JavaSingleRelationshipMapping.this.typeMapping();
		}

		public Table dbTable(String tableName) {
			return typeMapping().dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			IEntity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity().primaryDbTable();
		}
		
		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
			return JavaSingleRelationshipMapping.this.defaultJoinColumn == joinColumn;
		}
		
		public String defaultColumnName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public ITextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
