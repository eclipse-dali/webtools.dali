/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJoinTable;
import org.eclipse.jpt.core.internal.context.base.INonOwningMapping;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JoinColumn;
import org.eclipse.jpt.core.internal.resource.java.JoinTable;
import org.eclipse.jpt.core.internal.resource.java.NullJoinColumn;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

public class JavaJoinTable extends AbstractJavaTable implements IJavaJoinTable
{
	protected final List<IJavaJoinColumn> specifiedJoinColumns;

	protected IJavaJoinColumn defaultJoinColumn;

	protected final List<IJavaJoinColumn> specifiedInverseJoinColumns;

	protected IJavaJoinColumn defaultInverseJoinColumn;
	
	protected JavaPersistentAttributeResource attributeResource;
	
	public JavaJoinTable(IJavaRelationshipMapping parent) {
		super(parent);
		this.specifiedJoinColumns = new ArrayList<IJavaJoinColumn>();
		this.specifiedInverseJoinColumns = new ArrayList<IJavaJoinColumn>();
	}
	
	
	//******************* AbstractJavaTable implementation *****************

	@Override
	protected String annotationName() {
		return JoinTable.ANNOTATION_NAME;
	}
	
	/**
	 * Default join table name from the JPA spec:
	 * 	The concatenated names of the two associated primary
	 * 	entity tables, separated by a underscore.
	 * 
	 * [owning table name]_[target table name]
	 */
	@Override
	protected String defaultName() {
		if (!relationshipMapping().isRelationshipOwner()) {
			return null;
		}
		String owningTableName = relationshipMapping().typeMapping().tableName();
		if (owningTableName == null) {
			return null;
		}
		IEntity targetEntity = relationshipMapping().getResolvedTargetEntity();
		if (targetEntity == null) {
			return null;
		}
		String targetTableName = targetEntity.tableName();
		if (targetTableName == null) {
			return null;
		}
		return owningTableName + "_" + targetTableName;
	}
	
	@Override
	protected String defaultCatalog() {
		if (!relationshipMapping().isRelationshipOwner()) {
			return null;
		}
		return super.defaultCatalog();
	}
	
	@Override
	protected String defaultSchema() {
		if (!relationshipMapping().isRelationshipOwner()) {
			return null;
		}
		return super.defaultSchema();
	}
	
	@Override
	protected JoinTable tableResource() {
		return (JoinTable) this.attributeResource.nonNullAnnotation(JoinTable.ANNOTATION_NAME);
	}
	
	/**
	 * Return the join table java resource, null if the annotation does not exist.
	 * Use tableResource() if you want a non null implementation
	 */
	protected JoinTable joinTableResource() {
		return (JoinTable) this.attributeResource.annotation(JoinTable.ANNOTATION_NAME);
	}
	
	protected void addJoinTableResource() {
		this.attributeResource.addAnnotation(JoinTable.ANNOTATION_NAME);
	}
	
	
	//******************* IJoinTable implementation *****************

	public ListIterator<IJavaJoinColumn> joinColumns() {
		return this.containsSpecifiedJoinColumns() ? this.specifiedJoinColumns() : this.defaultJoinColumns();
	}

	public int joinColumnsSize() {
		return this.containsSpecifiedJoinColumns() ? this.specifiedJoinColumnsSize() : this.defaultJoinColumnsSize();
	}
	
	public IJavaJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}
	
	protected void setDefaultJoinColumn(IJavaJoinColumn newJoinColumn) {
		IJavaJoinColumn oldJoinColumn = this.defaultJoinColumn;
		this.defaultJoinColumn = newJoinColumn;
		firePropertyChanged(IJoinTable.DEFAULT_JOIN_COLUMN, oldJoinColumn, newJoinColumn);
	}

	protected ListIterator<IJavaJoinColumn> defaultJoinColumns() {
		if (this.defaultJoinColumn != null) {
			return new SingleElementListIterator<IJavaJoinColumn>(this.defaultJoinColumn);
		}
		return EmptyListIterator.instance();
	}
	
	protected int defaultJoinColumnsSize() {
		return (this.defaultJoinColumn == null) ? 0 : 1;
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
		//TODO this is a bad idea, but it's working for now. Need to revist
		// the idea of having a way to add the JoinTable and the JoinColumn
		//and then firing property change events.  Otherwise we end up creating
		//the spcifiedJoinColumn then wiping it out and creating another one during
		//an update from the java resource model
		if (joinTableResource() == null) {
			addJoinTableResource();
		}
		IJavaJoinColumn joinColumn = jpaFactory().createJavaJoinColumn(this, createJoinColumnOwner());
		this.specifiedJoinColumns.add(index, joinColumn);
		JoinColumn joinColumnResource = this.tableResource().addJoinColumn(index);
		joinColumn.initializeFromResource(joinColumnResource);
		this.fireItemAdded(IJoinTable.SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		return joinColumn;
	}

	protected void addSpecifiedJoinColumn(int index, IJavaJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedJoinColumns, IJoinTable.SPECIFIED_JOIN_COLUMNS_LIST);
	}
		
	public void removeSpecifiedJoinColumn(IJoinColumn joinColumn) {
		this.removeSpecifiedJoinColumn(this.specifiedJoinColumns.indexOf(joinColumn));
	}
	
	public void removeSpecifiedJoinColumn(int index) {
		IJavaJoinColumn removedJoinColumn = this.specifiedJoinColumns.remove(index);
		this.tableResource().removeJoinColumn(index);
		fireItemRemoved(IJoinTable.SPECIFIED_JOIN_COLUMNS_LIST, index, removedJoinColumn);
	}

	protected void removeSpecifiedJoinColumn_(IJavaJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, IJoinTable.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedJoinColumns, targetIndex, sourceIndex);
		this.tableResource().moveJoinColumn(targetIndex, sourceIndex);
		fireItemMoved(IJoinTable.SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}

	public ListIterator<IJavaJoinColumn> inverseJoinColumns() {
		return this.containsSpecifiedInverseJoinColumns() ? this.specifiedInverseJoinColumns() : this.defaultInverseJoinColumns();
	}

	public int inverseJoinColumnsSize() {
		return this.containsSpecifiedInverseJoinColumns() ? this.specifiedInverseJoinColumnsSize() : this.defaultInverseJoinColumnsSize();
	}
	
	public IJavaJoinColumn getDefaultInverseJoinColumn() {
		return this.defaultInverseJoinColumn;
	}
	
	protected void setDefaultInverseJoinColumn(IJavaJoinColumn newInverseJoinColumn) {
		IJavaJoinColumn oldInverseJoinColumn = this.defaultInverseJoinColumn;
		this.defaultInverseJoinColumn = newInverseJoinColumn;
		firePropertyChanged(IJoinTable.DEFAULT_INVERSE_JOIN_COLUMN, oldInverseJoinColumn, newInverseJoinColumn);
	}
	
	protected ListIterator<IJavaJoinColumn> defaultInverseJoinColumns() {
		if (this.defaultInverseJoinColumn != null) {
			return new SingleElementListIterator<IJavaJoinColumn>(this.defaultInverseJoinColumn);
		}
		return EmptyListIterator.instance();
	}

	protected int defaultInverseJoinColumnsSize() {
		return (this.defaultInverseJoinColumn == null) ? 0 : 1;
	}
	
	public ListIterator<IJavaJoinColumn> specifiedInverseJoinColumns() {
		return new CloneListIterator<IJavaJoinColumn>(this.specifiedInverseJoinColumns);
	}

	public int specifiedInverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumns.size();
	}

	public boolean containsSpecifiedInverseJoinColumns() {
		return !this.specifiedInverseJoinColumns.isEmpty();
	}

	public IJavaJoinColumn addSpecifiedInverseJoinColumn(int index) {
		//TODO this is a bad idea, but it's working for now and tests are passing. Need to revist
		//the idea of having a way to add the JoinTable and the JoinColumn
		//and then firing property change events.  Otherwise we end up creating
		//the spcifiedJoinColumn then wiping it out and creating another one during
		//an update from the java resource model
		if (joinTableResource() == null) {
			addJoinTableResource();
		}
		IJavaJoinColumn inverseJoinColumn = jpaFactory().createJavaJoinColumn(this, createInverseJoinColumnOwner());
		this.specifiedInverseJoinColumns.add(index, inverseJoinColumn);
		JoinColumn joinColumnResource = this.tableResource().addInverseJoinColumn(index);
		inverseJoinColumn.initializeFromResource(joinColumnResource);
		this.fireItemAdded(IJoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, index, inverseJoinColumn);
		return inverseJoinColumn;
	}
	
	protected void addSpecifiedInverseJoinColumn(int index, IJavaJoinColumn inverseJoinColumn) {
		addItemToList(index, inverseJoinColumn, this.specifiedInverseJoinColumns, IJoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}
	
	public void removeSpecifiedInverseJoinColumn(IJoinColumn inverseJoinColumn) {
		this.removeSpecifiedInverseJoinColumn(this.specifiedInverseJoinColumns.indexOf(inverseJoinColumn));
	}

	public void removeSpecifiedInverseJoinColumn(int index) {
		IJavaJoinColumn removedJoinColumn = this.specifiedInverseJoinColumns.remove(index);
		this.tableResource().removeInverseJoinColumn(index);
		fireItemRemoved(IJoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, index, removedJoinColumn);
	}

	protected void removeSpecifiedInverseJoinColumn_(IJavaJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedInverseJoinColumns, IJoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedInverseJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedInverseJoinColumns, targetIndex, sourceIndex);
		this.tableResource().moveInverseJoinColumn(targetIndex, sourceIndex);
		fireItemMoved(IJoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}


	public IRelationshipMapping relationshipMapping() {
		return (IRelationshipMapping) this.parent();
	}

	public boolean isSpecified() {
		return joinTableResource() != null;
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
		for (IJavaJoinColumn column : CollectionTools.iterable(this.inverseJoinColumns())) {
			result = column.candidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	protected IJoinColumn.Owner createJoinColumnOwner() {
		return new JoinColumnOwner();
	}
	
	protected IJoinColumn.Owner createInverseJoinColumnOwner() {
		return new InverseJoinColumnOwner();
	}

	public void initializeFromResource(JavaPersistentAttributeResource attributeResource) {
		this.attributeResource = attributeResource;
		JoinTable joinTable = tableResource();
		this.initializeFromResource(joinTable);
		this.initializeSpecifiedJoinColumns(joinTable);
		this.initializeDefaultJoinColumn(joinTable);
		this.initializeSpecifiedInverseJoinColumns(joinTable);
		this.initializeDefaultInverseJoinColumn(joinTable);
	}

	protected void initializeSpecifiedJoinColumns(JoinTable joinTableResource) {
		ListIterator<JoinColumn> annotations = joinTableResource.joinColumns();
		
		while(annotations.hasNext()) {
			this.specifiedJoinColumns.add(createJoinColumn(annotations.next()));
		}
	}
	
	protected boolean shouldBuildDefaultJoinColumn() {
		return !containsSpecifiedJoinColumns() && relationshipMapping().isRelationshipOwner();
	}
	
	protected void initializeDefaultJoinColumn(JoinTable joinTable) {
		if (!shouldBuildDefaultJoinColumn()) {
			return;
		}
		this.defaultJoinColumn = this.jpaFactory().createJavaJoinColumn(this, createJoinColumnOwner());
		this.defaultJoinColumn.initializeFromResource(new NullJoinColumn(joinTable));
	}	
	
	protected void initializeSpecifiedInverseJoinColumns(JoinTable joinTableResource) {
		ListIterator<JoinColumn> annotations = joinTableResource.inverseJoinColumns();
		
		while(annotations.hasNext()) {
			this.specifiedInverseJoinColumns.add(createInverseJoinColumn(annotations.next()));
		}
	}
	
	protected boolean shouldBuildDefaultInverseJoinColumn() {
		return !containsSpecifiedInverseJoinColumns() && relationshipMapping().isRelationshipOwner();
	}
	
	protected void initializeDefaultInverseJoinColumn(JoinTable joinTable) {
		if (!shouldBuildDefaultInverseJoinColumn()) {
			return;
		}
		this.defaultInverseJoinColumn = this.jpaFactory().createJavaJoinColumn(this, createInverseJoinColumnOwner());
		this.defaultInverseJoinColumn.initializeFromResource(new NullJoinColumn(joinTable));		
	}
		
	public void update(JavaPersistentAttributeResource attributeResource) {
		this.attributeResource = attributeResource;
		JoinTable joinTable = tableResource();
		this.update(joinTable);
		this.updateSpecifiedJoinColumns(joinTable);
		this.updateDefaultJoinColumn(joinTable);
		this.updateSpecifiedInverseJoinColumns(joinTable);
		this.updateDefaultInverseJoinColumn(joinTable);
	}
	
	protected void updateSpecifiedJoinColumns(JoinTable joinTableResource) {
		ListIterator<IJavaJoinColumn> joinColumns = specifiedJoinColumns();
		ListIterator<JoinColumn> resourceJoinColumns = joinTableResource.joinColumns();
		
		while (joinColumns.hasNext()) {
			IJavaJoinColumn joinColumn = joinColumns.next();
			if (resourceJoinColumns.hasNext()) {
				joinColumn.update(resourceJoinColumns.next());
			}
			else {
				removeSpecifiedJoinColumn_(joinColumn);
			}
		}
		
		while (resourceJoinColumns.hasNext()) {
			addSpecifiedJoinColumn(specifiedJoinColumnsSize(), createJoinColumn(resourceJoinColumns.next()));
		}
	}
	
	protected void updateDefaultJoinColumn(JoinTable joinTable) {
		if (!shouldBuildDefaultJoinColumn()) {
			setDefaultJoinColumn(null);
			return;
		}
		if (getDefaultJoinColumn() == null) {
			IJavaJoinColumn joinColumn = this.jpaFactory().createJavaJoinColumn(this, createJoinColumnOwner());
			joinColumn.initializeFromResource(new NullJoinColumn(joinTable));
			this.setDefaultJoinColumn(joinColumn);
		}
		else {
			this.defaultJoinColumn.update(new NullJoinColumn(joinTable));
		}
	}	

	protected void updateSpecifiedInverseJoinColumns(JoinTable joinTableResource) {
		ListIterator<IJavaJoinColumn> joinColumns = specifiedInverseJoinColumns();
		ListIterator<JoinColumn> resourceJoinColumns = joinTableResource.inverseJoinColumns();
		
		while (joinColumns.hasNext()) {
			IJavaJoinColumn joinColumn = joinColumns.next();
			if (resourceJoinColumns.hasNext()) {
				joinColumn.update(resourceJoinColumns.next());
			}
			else {
				removeSpecifiedInverseJoinColumn_(joinColumn);
			}
		}
		
		while (resourceJoinColumns.hasNext()) {
			addSpecifiedInverseJoinColumn(specifiedInverseJoinColumnsSize(), createInverseJoinColumn(resourceJoinColumns.next()));
		}
	}
	
	protected void updateDefaultInverseJoinColumn(JoinTable joinTable) {
		if (!shouldBuildDefaultInverseJoinColumn()) {
			setDefaultInverseJoinColumn(null);
			return;
		}
		if (getDefaultInverseJoinColumn() == null) {
			IJavaJoinColumn joinColumn = this.jpaFactory().createJavaJoinColumn(this, createInverseJoinColumnOwner());
			joinColumn.initializeFromResource(new NullJoinColumn(joinTable));
			this.setDefaultInverseJoinColumn(joinColumn);
		}
		else {
			this.defaultInverseJoinColumn.update(new NullJoinColumn(joinTable));
		}
	}	
	
	protected IJavaJoinColumn createJoinColumn(JoinColumn joinColumnResource) {
		IJavaJoinColumn joinColumn = jpaFactory().createJavaJoinColumn(this, createJoinColumnOwner());
		joinColumn.initializeFromResource(joinColumnResource);
		return joinColumn;
	}
	
	protected IJavaJoinColumn createInverseJoinColumn(JoinColumn joinColumnResource) {
		IJavaJoinColumn joinColumn = jpaFactory().createJavaJoinColumn(this, createInverseJoinColumnOwner());
		joinColumn.initializeFromResource(joinColumnResource);
		return joinColumn;
	}

	
	
	/**
	 * just a little common behavior
	 */
	abstract class AbstractJoinColumnOwner implements IJoinColumn.Owner
	{
		AbstractJoinColumnOwner() {
			super();
		}

		public ITypeMapping typeMapping() {
			return relationshipMapping().typeMapping();
		}
		public IRelationshipMapping relationshipMapping() {
			return JavaJoinTable.this.relationshipMapping();
		}

		/**
		 * the default table name is always valid and a specified table name
		 * is prohibited (which will be handled elsewhere)
		 */
		public boolean tableNameIsInvalid(String tableName) {
			return false;
		}

		/**
		 * the join column can only be on the join table itself
		 */
		public boolean tableIsAllowed() {
			return false;
		}

		public org.eclipse.jpt.db.internal.Table dbTable(String tableName) {
			if (JavaJoinTable.this.getName() == null) {
				return null;
			}
			return (JavaJoinTable.this.getName().equals(tableName)) ? JavaJoinTable.this.dbTable() : null;
		}
	}


	/**
	 * owner for "forward-pointer" JoinColumns;
	 * these point at the target/inverse entity
	 */
	class InverseJoinColumnOwner extends AbstractJoinColumnOwner
	{
		public InverseJoinColumnOwner() {
			super();
		}

		public IEntity targetEntity() {
			return JavaJoinTable.this.relationshipMapping().getResolvedTargetEntity();
		}

		public String attributeName() {
			return JavaJoinTable.this.relationshipMapping().persistentAttribute().getName();
		}

		@Override
		public org.eclipse.jpt.db.internal.Table dbTable(String tableName) {
			org.eclipse.jpt.db.internal.Table dbTable = super.dbTable(tableName);
			if (dbTable != null) {
				return dbTable;
			}
			IEntity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity.dbTable(tableName);
		}

		public org.eclipse.jpt.db.internal.Table dbReferencedColumnTable() {
			IEntity targetEntity = targetEntity();
			return (targetEntity == null) ? null : targetEntity.primaryDbTable();
		}
		
		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
			return JavaJoinTable.this.defaultInverseJoinColumn == joinColumn;
		}
		
		public String defaultColumnName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public String defaultTableName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public ITextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public int joinColumnsSize() {
			return JavaJoinTable.this.inverseJoinColumnsSize();
		}
	}


	/**
	 * owner for "back-pointer" JoinColumns;
	 * these point at the source/owning entity
	 */
	class JoinColumnOwner extends AbstractJoinColumnOwner
	{
		public JoinColumnOwner() {
			super();
		}

		public IEntity targetEntity() {
			return JavaJoinTable.this.relationshipMapping().getEntity();
		}

		public String attributeName() {
			IEntity targetEntity = JavaJoinTable.this.relationshipMapping().getResolvedTargetEntity();
			if (targetEntity == null) {
				return null;
			}
			String attributeName = JavaJoinTable.this.relationshipMapping().persistentAttribute().getName();
			for (Iterator<IPersistentAttribute> stream = targetEntity.persistentType().allAttributes(); stream.hasNext();) {
				IPersistentAttribute attribute = stream.next();
				IAttributeMapping mapping = attribute.getMapping();
				if (mapping instanceof INonOwningMapping) {
					String mappedBy = ((INonOwningMapping) mapping).getMappedBy();
					if ((mappedBy != null) && mappedBy.equals(attributeName)) {
						return attribute.getName();
					}
				}
			}
			return null;
		}

		@Override
		public org.eclipse.jpt.db.internal.Table dbTable(String tableName) {
			org.eclipse.jpt.db.internal.Table dbTable = super.dbTable(tableName);
			if (dbTable != null) {
				return dbTable;
			}
			return typeMapping().dbTable(tableName);
		}

		public org.eclipse.jpt.db.internal.Table dbReferencedColumnTable() {
			return typeMapping().primaryDbTable();
		}
		
		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
			return JavaJoinTable.this.defaultJoinColumn == joinColumn;
		}
		
		public String defaultColumnName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public String defaultTableName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public ITextRange validationTextRange(CompilationUnit astRoot) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public int joinColumnsSize() {
			return JavaJoinTable.this.joinColumnsSize();
		}
	}
	
}
