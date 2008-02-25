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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.Nullable;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.SingleRelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaSingleRelationshipMapping;
import org.eclipse.jpt.core.internal.resource.java.NullJoinColumn;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinColumns;
import org.eclipse.jpt.core.resource.java.RelationshipMappingAnnotation;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public abstract class AbstractJavaSingleRelationshipMapping<T extends RelationshipMappingAnnotation>
	extends AbstractJavaRelationshipMapping<T> implements JavaSingleRelationshipMapping
{
	
	protected final List<JavaJoinColumn> specifiedJoinColumns;

	protected JavaJoinColumn defaultJoinColumn;

	protected Boolean specifiedOptional;

	protected AbstractJavaSingleRelationshipMapping(JavaPersistentAttribute parent) {
		super(parent);
		this.specifiedJoinColumns = new ArrayList<JavaJoinColumn>();
	}
	
	public FetchType getDefaultFetch() {
		return SingleRelationshipMapping.DEFAULT_FETCH_TYPE;
	}
	
	//***************** ISingleRelationshipMapping implementation *****************
	public ListIterator<JavaJoinColumn> joinColumns() {
		return this.containsSpecifiedJoinColumns() ? this.specifiedJoinColumns() : this.defaultJoinColumns();
	}

	public int joinColumnsSize() {
		return this.containsSpecifiedJoinColumns() ? this.specifiedJoinColumnsSize() : this.defaultJoinColumnsSize();
	}
	
	public JavaJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}
	
	protected void setDefaultJoinColumn(JavaJoinColumn newJoinColumn) {
		JavaJoinColumn oldJoinColumn = this.defaultJoinColumn;
		this.defaultJoinColumn = newJoinColumn;
		firePropertyChanged(SingleRelationshipMapping.DEFAULT_JOIN_COLUMN, oldJoinColumn, newJoinColumn);
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
	
	public ListIterator<JavaJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<JavaJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean containsSpecifiedJoinColumns() {
		return !this.specifiedJoinColumns.isEmpty();
	}

	public JavaJoinColumn addSpecifiedJoinColumn(int index) {
		JoinColumn oldDefaultJoinColumn = this.getDefaultJoinColumn();
		if (oldDefaultJoinColumn != null) {
			//null the default join column now if one already exists.
			//if one does not exist, there is already a specified join column.
			//Remove it now so that it doesn't get removed during an update and
			//cause change notifications to be sent to the UI in the wrong order
			this.defaultJoinColumn = null;
		}
		JavaJoinColumn joinColumn = jpaFactory().buildJavaJoinColumn(this, createJoinColumnOwner());
		this.specifiedJoinColumns.add(index, joinColumn);
		JoinColumnAnnotation joinColumnResource = (JoinColumnAnnotation) this.persistentAttributeResource.addAnnotation(index, JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		joinColumn.initializeFromResource(joinColumnResource);
		this.fireItemAdded(SingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		if (oldDefaultJoinColumn != null) {
			this.firePropertyChanged(SingleRelationshipMapping.DEFAULT_JOIN_COLUMN, oldDefaultJoinColumn, null);
		}
		return joinColumn;
	}

	protected void addSpecifiedJoinColumn(int index, JavaJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedJoinColumns, SingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST);
	}

	public void removeSpecifiedJoinColumn(JoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumns.indexOf(joinColumn));
	}
	
	public void removeSpecifiedJoinColumn(int index) {
		JavaJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		if (!containsSpecifiedJoinColumns()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultJoinColumn = createJoinColumn(new NullJoinColumn(this.persistentAttributeResource));
		}
		this.persistentAttributeResource.removeAnnotation(index, JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		fireItemRemoved(SingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST, index, removedJoinColumn);
		if (this.defaultJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(SingleRelationshipMapping.DEFAULT_JOIN_COLUMN, null, this.defaultJoinColumn);		
		}
	}

	protected void removeSpecifiedJoinColumn_(JavaJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, SingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedJoinColumns, targetIndex, sourceIndex);
		this.persistentAttributeResource.move(targetIndex, sourceIndex, JoinColumns.ANNOTATION_NAME);
		fireItemMoved(SingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}

	public Boolean getOptional() {
		return getSpecifiedOptional() == null ? getDefaultOptional() : getSpecifiedOptional();
	}
	
	public Boolean getDefaultOptional() {
		return Nullable.DEFAULT_OPTIONAL;
	}
	
	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}
	
	public void setSpecifiedOptional(Boolean newSpecifiedOptional) {
		Boolean oldSpecifiedOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		setOptionalOnResourceModel(newSpecifiedOptional);
		firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, oldSpecifiedOptional, newSpecifiedOptional);
	}

	protected abstract void setOptionalOnResourceModel(Boolean newOptional);


	@Override
	public void initializeFromResource(JavaResourcePersistentAttribute persistentAttributeResource) {
		super.initializeFromResource(persistentAttributeResource);
		this.initializeSpecifiedJoinColumns(persistentAttributeResource);
		this.initializeDefaultJoinColumn(persistentAttributeResource);
	}
	
	@Override
	protected void initialize(T relationshipMapping) {
		super.initialize(relationshipMapping);
		this.specifiedOptional = this.specifiedOptional(relationshipMapping);
	}
	
	protected void initializeSpecifiedJoinColumns(JavaResourcePersistentAttribute persistentAttributeResource) {
		ListIterator<JavaResourceNode> annotations = persistentAttributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		
		while(annotations.hasNext()) {
			this.specifiedJoinColumns.add(createJoinColumn((JoinColumnAnnotation) annotations.next()));
		}
	}
	
	protected boolean shouldBuildDefaultJoinColumn() {
		return !containsSpecifiedJoinColumns() && isRelationshipOwner();
	}
	
	protected void initializeDefaultJoinColumn(JavaResourcePersistentAttribute persistentAttributeResource) {
		if (!shouldBuildDefaultJoinColumn()) {
			return;
		}
		this.defaultJoinColumn = this.jpaFactory().buildJavaJoinColumn(this, createJoinColumnOwner());
		this.defaultJoinColumn.initializeFromResource(new NullJoinColumn(persistentAttributeResource));
	}	
	
	
	@Override
	public void update(JavaResourcePersistentAttribute persistentAttributeResource) {
		super.update(persistentAttributeResource);
		this.updateSpecifiedJoinColumns(persistentAttributeResource);
		this.updateDefaultJoinColumn(persistentAttributeResource);
	}
	
	@Override
	protected void update(T relationshipMapping) {
		super.update(relationshipMapping);
		this.setSpecifiedOptional(this.specifiedOptional(relationshipMapping));
	}
	
	protected abstract Boolean specifiedOptional(T relationshipMapping);
	
	
	protected void updateSpecifiedJoinColumns(JavaResourcePersistentAttribute persistentAttributeResource) {
		ListIterator<JavaJoinColumn> joinColumns = specifiedJoinColumns();
		ListIterator<JavaResourceNode> resourceJoinColumns = persistentAttributeResource.annotations(JoinColumnAnnotation.ANNOTATION_NAME, JoinColumns.ANNOTATION_NAME);
		
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
			addSpecifiedJoinColumn(specifiedJoinColumnsSize(), createJoinColumn((JoinColumnAnnotation) resourceJoinColumns.next()));
		}
	}
	
	protected void updateDefaultJoinColumn(JavaResourcePersistentAttribute persistentAttributeResource) {
		if (!shouldBuildDefaultJoinColumn()) {
			setDefaultJoinColumn(null);
			return;
		}
		if (getDefaultJoinColumn() == null) {
			JavaJoinColumn joinColumn = this.jpaFactory().buildJavaJoinColumn(this, createJoinColumnOwner());
			joinColumn.initializeFromResource(new NullJoinColumn(persistentAttributeResource));
			this.setDefaultJoinColumn(joinColumn);
		}
		else {
			this.defaultJoinColumn.update(new NullJoinColumn(persistentAttributeResource));
		}
	}	

	protected JavaJoinColumn createJoinColumn(JoinColumnAnnotation joinColumnResource) {
		JavaJoinColumn joinColumn = jpaFactory().buildJavaJoinColumn(this, createJoinColumnOwner());
		joinColumn.initializeFromResource(joinColumnResource);
		return joinColumn;
	}
	
	protected JoinColumn.Owner createJoinColumnOwner() {
		return new JoinColumnOwner();
	}
	
	/**
	 * eliminate any "container" types
	 */
	@Override
	protected String defaultTargetEntity(JavaResourcePersistentAttribute persistentAttributeResource) {
		if (persistentAttributeResource.typeIsContainer()) {
			return null;
		}
		return persistentAttributeResource.getQualifiedReferenceEntityTypeName();
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
	
	//************* Validation  **********************************
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		
		//bug 192287 - do not want joinColumn validation errors on the non-owning side
		//of a bidirectional relationship.  This is a low risk fix for RC3, but a better
		//solution would be to not have the default joinColumns on the non-owning side.
		//This would fix another bug that we show default joinColumns in this situation.
		if (entityOwned() && isRelationshipOwner()) {
			addJoinColumnMessages(messages, astRoot);
		}
	}
	
	protected void addJoinColumnMessages(List<IMessage> messages, CompilationUnit astRoot) {
		
		for (Iterator<JavaJoinColumn> stream = this.joinColumns(); stream.hasNext();) {
			JavaJoinColumn joinColumn = stream.next();
			String table = joinColumn.getTable();
			boolean doContinue = joinColumn.isConnected();
			
			if (doContinue && this.typeMapping().tableNameIsInvalid(table)) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_TABLE,
						new String[] {table, joinColumn.getName()}, 
						joinColumn, joinColumn.tableTextRange(astRoot))
				);
				doContinue = false;
			}
			
			if (doContinue && ! joinColumn.isResolved()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.JOIN_COLUMN_UNRESOLVED_NAME,
						new String[] {joinColumn.getName()}, 
						joinColumn, joinColumn.nameTextRange(astRoot))
				);
			}
			
			if (doContinue && ! joinColumn.isReferencedColumnResolved()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.JOIN_COLUMN_REFERENCED_COLUMN_UNRESOLVED_NAME,
						new String[] {joinColumn.getReferencedColumnName(), joinColumn.getName()}, 
						joinColumn, joinColumn.referencedColumnNameTextRange(astRoot))
				);
			}
		}
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
			return AbstractJavaSingleRelationshipMapping.this.typeMapping().tableName();
		}

		public Entity targetEntity() {
			return AbstractJavaSingleRelationshipMapping.this.getResolvedTargetEntity();
		}

		public String attributeName() {
			return AbstractJavaSingleRelationshipMapping.this.persistentAttribute().getName();
		}

		public RelationshipMapping relationshipMapping() {
			return AbstractJavaSingleRelationshipMapping.this;
		}

		public boolean tableNameIsInvalid(String tableName) {
			return AbstractJavaSingleRelationshipMapping.this.typeMapping().tableNameIsInvalid(tableName);
		}

		/**
		 * the join column can be on a secondary table
		 */
		public boolean tableIsAllowed() {
			return true;
		}

		public TypeMapping typeMapping() {
			return AbstractJavaSingleRelationshipMapping.this.typeMapping();
		}

		public Table dbTable(String tableName) {
			return typeMapping().dbTable(tableName);
		}

		public Table dbReferencedColumnTable() {
			Entity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity.primaryDbTable();
		}
		
		public boolean isVirtual(AbstractJoinColumn joinColumn) {
			return AbstractJavaSingleRelationshipMapping.this.defaultJoinColumn == joinColumn;
		}
		
		public String defaultColumnName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public TextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return AbstractJavaSingleRelationshipMapping.this.validationTextRange(astRoot);
		}
		
		public int joinColumnsSize() {
			return AbstractJavaSingleRelationshipMapping.this.joinColumnsSize();
		}
	}
}
