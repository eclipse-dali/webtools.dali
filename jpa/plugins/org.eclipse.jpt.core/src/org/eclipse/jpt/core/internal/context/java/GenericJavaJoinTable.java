/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.core.internal.resource.java.NullJoinColumn;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 */
public class GenericJavaJoinTable
	extends AbstractJavaTable
	implements JavaJoinTable
{
	protected final List<JavaJoinColumn> specifiedJoinColumns;

	protected JavaJoinColumn defaultJoinColumn;

	protected final List<JavaJoinColumn> specifiedInverseJoinColumns;

	protected JavaJoinColumn defaultInverseJoinColumn;
	
	protected JavaResourcePersistentAttribute resourceAttribute;
	
	public GenericJavaJoinTable(JavaRelationshipMapping parent) {
		super(parent);
		this.specifiedJoinColumns = new ArrayList<JavaJoinColumn>();
		this.specifiedInverseJoinColumns = new ArrayList<JavaJoinColumn>();
	}
	
	@Override
	public JavaRelationshipMapping getParent() {
		return (JavaRelationshipMapping) super.getParent();
	}
	
	//******************* AbstractJavaTable implementation *****************

	@Override
	protected String getAnnotationName() {
		return JoinTableAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	protected String buildDefaultName() {
		return this.getRelationshipMapping().getJoinTableDefaultName();
	}

	/**
	 * if the join table is on the "mappedBy" side, it's bogus;
	 * so don't give it a default catalog
	 */
	@Override
	protected String buildDefaultCatalog() {
		return this.getRelationshipMapping().isRelationshipOwner() ? this.getContextDefaultCatalog() : null;
	}

	/**
	 * if the join table is on the "mappedBy" side, it's bogus;
	 * so don't give it a default schema
	 */
	@Override
	protected String buildDefaultSchema() {
		return this.getRelationshipMapping().isRelationshipOwner() ? this.getContextDefaultSchema() : null;
	}
	
	@Override
	protected JoinTableAnnotation getResourceTable() {
		return (JoinTableAnnotation) this.resourceAttribute.getNonNullAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	}
	
	/**
	 * Return the join table java resource, null if the annotation does not exist.
	 * Use getResourceTable() if you want a non null implementation
	 */
	protected JoinTableAnnotation getResourceJoinTable() {
		return (JoinTableAnnotation) this.resourceAttribute.getAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	}
	
	protected void addResourceJoinTable() {
		this.resourceAttribute.addAnnotation(JoinTableAnnotation.ANNOTATION_NAME);
	}
	
	
	//******************* IJoinTable implementation *****************

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
		firePropertyChanged(JoinTable.DEFAULT_JOIN_COLUMN, oldJoinColumn, newJoinColumn);
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
		if (getResourceJoinTable() == null) {
			//Add the JoinTable before creating the specifiedJoinColumn.
			//Otherwise we will remove it and create another during an update
			//from the java resource model
			addResourceJoinTable();
		}
		JavaJoinColumn joinColumn = getJpaFactory().buildJavaJoinColumn(this, createJoinColumnOwner());
		this.specifiedJoinColumns.add(index, joinColumn);
		JoinColumnAnnotation joinColumnResource = this.getResourceTable().addJoinColumn(index);
		joinColumn.initialize(joinColumnResource);
		this.fireItemAdded(JoinTable.SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);
		if (oldDefaultJoinColumn != null) {
			this.firePropertyChanged(JoinTable.DEFAULT_JOIN_COLUMN, oldDefaultJoinColumn, null);
		}
		return joinColumn;
	}

	protected void addSpecifiedJoinColumn(int index, JavaJoinColumn joinColumn) {
		addItemToList(index, joinColumn, this.specifiedJoinColumns, JoinTable.SPECIFIED_JOIN_COLUMNS_LIST);
	}
		
	protected void addSpecifiedJoinColumn(JavaJoinColumn joinColumn) {
		this.addSpecifiedJoinColumn(this.specifiedJoinColumns.size(), joinColumn);
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
			this.defaultJoinColumn = buildJoinColumn(new NullJoinColumn(getResourceTable()));
		}
		this.getResourceTable().removeJoinColumn(index);
		fireItemRemoved(JoinTable.SPECIFIED_JOIN_COLUMNS_LIST, index, removedJoinColumn);
		if (this.defaultJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(JoinTable.DEFAULT_JOIN_COLUMN, null, this.defaultJoinColumn);		
		}
	}

	protected void removeSpecifiedJoinColumn_(JavaJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedJoinColumns, JoinTable.SPECIFIED_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedJoinColumns, targetIndex, sourceIndex);
		this.getResourceTable().moveJoinColumn(targetIndex, sourceIndex);
		fireItemMoved(JoinTable.SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}

	public ListIterator<JavaJoinColumn> inverseJoinColumns() {
		return this.containsSpecifiedInverseJoinColumns() ? this.specifiedInverseJoinColumns() : this.defaultInverseJoinColumns();
	}

	public int inverseJoinColumnsSize() {
		return this.containsSpecifiedInverseJoinColumns() ? this.specifiedInverseJoinColumnsSize() : this.defaultInverseJoinColumnsSize();
	}
	
	public JavaJoinColumn getDefaultInverseJoinColumn() {
		return this.defaultInverseJoinColumn;
	}
	
	protected void setDefaultInverseJoinColumn(JavaJoinColumn newInverseJoinColumn) {
		JavaJoinColumn oldInverseJoinColumn = this.defaultInverseJoinColumn;
		this.defaultInverseJoinColumn = newInverseJoinColumn;
		firePropertyChanged(JoinTable.DEFAULT_INVERSE_JOIN_COLUMN, oldInverseJoinColumn, newInverseJoinColumn);
	}
	
	protected ListIterator<JavaJoinColumn> defaultInverseJoinColumns() {
		if (this.defaultInverseJoinColumn != null) {
			return new SingleElementListIterator<JavaJoinColumn>(this.defaultInverseJoinColumn);
		}
		return EmptyListIterator.instance();
	}

	protected int defaultInverseJoinColumnsSize() {
		return (this.defaultInverseJoinColumn == null) ? 0 : 1;
	}
	
	public ListIterator<JavaJoinColumn> specifiedInverseJoinColumns() {
		return new CloneListIterator<JavaJoinColumn>(this.specifiedInverseJoinColumns);
	}

	public int specifiedInverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumns.size();
	}

	public boolean containsSpecifiedInverseJoinColumns() {
		return !this.specifiedInverseJoinColumns.isEmpty();
	}

	public JavaJoinColumn addSpecifiedInverseJoinColumn(int index) {
		JoinColumn oldDefaultInverseJoinColumn = this.getDefaultInverseJoinColumn();
		if (oldDefaultInverseJoinColumn != null) {
			//null the default join column now if one already exists.
			//if one does not exist, there is already a specified join column.
			//Remove it now so that it doesn't get removed during an update and
			//cause change notifications to be sent to the UI in the wrong order
			this.defaultInverseJoinColumn = null;
		}
		if (getResourceJoinTable() == null) {
			//Add the JoinTable before creating the specifiedJoinColumn.
			//Otherwise we will remove it and create another during an update
			//from the java resource model
			addResourceJoinTable();
		}
		JavaJoinColumn inverseJoinColumn = getJpaFactory().buildJavaJoinColumn(this, createInverseJoinColumnOwner());
		this.specifiedInverseJoinColumns.add(index, inverseJoinColumn);
		JoinColumnAnnotation joinColumnResource = this.getResourceTable().addInverseJoinColumn(index);
		inverseJoinColumn.initialize(joinColumnResource);
		this.fireItemAdded(JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, index, inverseJoinColumn);
		if (oldDefaultInverseJoinColumn != null) {
			this.firePropertyChanged(JoinTable.DEFAULT_INVERSE_JOIN_COLUMN, oldDefaultInverseJoinColumn, null);
		}
		return inverseJoinColumn;
	}
	
	protected void addSpecifiedInverseJoinColumn(int index, JavaJoinColumn inverseJoinColumn) {
		addItemToList(index, inverseJoinColumn, this.specifiedInverseJoinColumns, JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}
	
	protected void addSpecifiedInverseJoinColumn(JavaJoinColumn inverseJoinColumn) {
		this.addSpecifiedInverseJoinColumn(this.specifiedInverseJoinColumns.size(), inverseJoinColumn);
	}
	
	public void removeSpecifiedInverseJoinColumn(JoinColumn inverseJoinColumn) {
		this.removeSpecifiedInverseJoinColumn(this.specifiedInverseJoinColumns.indexOf(inverseJoinColumn));
	}

	public void removeSpecifiedInverseJoinColumn(int index) {
		JavaJoinColumn removedJoinColumn = this.specifiedInverseJoinColumns.remove(index);
		if (!containsSpecifiedInverseJoinColumns()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultInverseJoinColumn = buildInverseJoinColumn(new NullJoinColumn(getResourceTable()));
		}
		this.getResourceTable().removeInverseJoinColumn(index);
		fireItemRemoved(JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, index, removedJoinColumn);
		if (this.defaultInverseJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(JoinTable.DEFAULT_INVERSE_JOIN_COLUMN, null, this.defaultInverseJoinColumn);		
		}
	}

	protected void removeSpecifiedInverseJoinColumn_(JavaJoinColumn joinColumn) {
		removeItemFromList(joinColumn, this.specifiedInverseJoinColumns, JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedInverseJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedInverseJoinColumns, targetIndex, sourceIndex);
		this.getResourceTable().moveInverseJoinColumn(targetIndex, sourceIndex);
		fireItemMoved(JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}


	public RelationshipMapping getRelationshipMapping() {
		return this.getParent();
	}

	public boolean isSpecified() {
		return getResourceJoinTable() != null;
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
		for (JavaJoinColumn column : CollectionTools.iterable(this.inverseJoinColumns())) {
			result = column.javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	protected JavaJoinColumn.Owner createJoinColumnOwner() {
		return new JoinColumnOwner();
	}
	
	protected JavaJoinColumn.Owner createInverseJoinColumnOwner() {
		return new InverseJoinColumnOwner();
	}

	public void initialize(JavaResourcePersistentAttribute jrpa) {
		this.resourceAttribute = jrpa;
		JoinTableAnnotation joinTable = getResourceTable();
		this.initialize(joinTable);
		this.initializeSpecifiedJoinColumns(joinTable);
		this.initializeDefaultJoinColumn(joinTable);
		this.initializeSpecifiedInverseJoinColumns(joinTable);
		this.initializeDefaultInverseJoinColumn(joinTable);
	}

	protected void initializeSpecifiedJoinColumns(JoinTableAnnotation joinTableResource) {
		ListIterator<JoinColumnAnnotation> annotations = joinTableResource.joinColumns();
		
		while(annotations.hasNext()) {
			this.specifiedJoinColumns.add(buildJoinColumn(annotations.next()));
		}
	}
	
	protected boolean shouldBuildDefaultJoinColumn() {
		return !containsSpecifiedJoinColumns() && getRelationshipMapping().isRelationshipOwner();
	}
	
	protected void initializeDefaultJoinColumn(JoinTableAnnotation joinTable) {
		if (!shouldBuildDefaultJoinColumn()) {
			return;
		}
		this.defaultJoinColumn = buildJoinColumn(new NullJoinColumn(joinTable));
	}	
	
	protected void initializeSpecifiedInverseJoinColumns(JoinTableAnnotation joinTableResource) {
		ListIterator<JoinColumnAnnotation> annotations = joinTableResource.inverseJoinColumns();
		
		while(annotations.hasNext()) {
			this.specifiedInverseJoinColumns.add(buildInverseJoinColumn(annotations.next()));
		}
	}
	
	protected boolean shouldBuildDefaultInverseJoinColumn() {
		return !containsSpecifiedInverseJoinColumns() && getRelationshipMapping().isRelationshipOwner();
	}
	
	protected void initializeDefaultInverseJoinColumn(JoinTableAnnotation joinTable) {
		if (!shouldBuildDefaultInverseJoinColumn()) {
			return;
		}
		this.defaultInverseJoinColumn = buildInverseJoinColumn(new NullJoinColumn(joinTable));		
	}
		
	public void update(JavaResourcePersistentAttribute jrpa) {
		this.resourceAttribute = jrpa;
		JoinTableAnnotation joinTable = getResourceTable();
		this.update(joinTable);
		this.updateSpecifiedJoinColumns(joinTable);
		this.updateDefaultJoinColumn(joinTable);
		this.updateSpecifiedInverseJoinColumns(joinTable);
		this.updateDefaultInverseJoinColumn(joinTable);
	}
	
	protected void updateSpecifiedJoinColumns(JoinTableAnnotation joinTableResource) {
		ListIterator<JavaJoinColumn> joinColumns = specifiedJoinColumns();
		ListIterator<JoinColumnAnnotation> resourceJoinColumns = joinTableResource.joinColumns();
		
		while (joinColumns.hasNext()) {
			JavaJoinColumn joinColumn = joinColumns.next();
			if (resourceJoinColumns.hasNext()) {
				joinColumn.update(resourceJoinColumns.next());
			}
			else {
				removeSpecifiedJoinColumn_(joinColumn);
			}
		}
		
		while (resourceJoinColumns.hasNext()) {
			addSpecifiedJoinColumn(buildJoinColumn(resourceJoinColumns.next()));
		}
	}
	
	protected void updateDefaultJoinColumn(JoinTableAnnotation joinTable) {
		if (!shouldBuildDefaultJoinColumn()) {
			setDefaultJoinColumn(null);
			return;
		}
		if (getDefaultJoinColumn() == null) {
			this.setDefaultJoinColumn(buildJoinColumn(new NullJoinColumn(joinTable)));
		}
		else {
			this.defaultJoinColumn.update(new NullJoinColumn(joinTable));
		}
	}	

	protected void updateSpecifiedInverseJoinColumns(JoinTableAnnotation joinTableResource) {
		ListIterator<JavaJoinColumn> joinColumns = specifiedInverseJoinColumns();
		ListIterator<JoinColumnAnnotation> resourceJoinColumns = joinTableResource.inverseJoinColumns();
		
		while (joinColumns.hasNext()) {
			JavaJoinColumn joinColumn = joinColumns.next();
			if (resourceJoinColumns.hasNext()) {
				joinColumn.update(resourceJoinColumns.next());
			}
			else {
				removeSpecifiedInverseJoinColumn_(joinColumn);
			}
		}
		
		while (resourceJoinColumns.hasNext()) {
			addSpecifiedInverseJoinColumn(buildInverseJoinColumn(resourceJoinColumns.next()));
		}
	}
	
	protected void updateDefaultInverseJoinColumn(JoinTableAnnotation joinTable) {
		if (!shouldBuildDefaultInverseJoinColumn()) {
			setDefaultInverseJoinColumn(null);
			return;
		}
		if (getDefaultInverseJoinColumn() == null) {
			this.setDefaultInverseJoinColumn(buildInverseJoinColumn(new NullJoinColumn(joinTable)));
		}
		else {
			this.defaultInverseJoinColumn.update(new NullJoinColumn(joinTable));
		}
	}	
	
	protected JavaJoinColumn buildJoinColumn(JoinColumnAnnotation joinColumnResource) {
		JavaJoinColumn joinColumn = getJpaFactory().buildJavaJoinColumn(this, createJoinColumnOwner());
		joinColumn.initialize(joinColumnResource);
		return joinColumn;
	}
	
	protected JavaJoinColumn buildInverseJoinColumn(JoinColumnAnnotation joinColumnResource) {
		JavaJoinColumn joinColumn = getJpaFactory().buildJavaJoinColumn(this, createInverseJoinColumnOwner());
		joinColumn.initialize(joinColumnResource);
		return joinColumn;
	}

	
	//********************* validation ********************
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		if (this.connectionProfileIsActive()) {
			this.checkDatabase(messages, astRoot);
		}
	}

	protected void checkDatabase(List<IMessage> messages, CompilationUnit astRoot) {
		if ( ! this.hasResolvedCatalog()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.JOIN_TABLE_UNRESOLVED_CATALOG,
						new String[] {this.getCatalog(), this.getName()}, 
						this, 
						this.getCatalogTextRange(astRoot))
				);
			return;
		}
		
		if ( ! this.hasResolvedSchema()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.JOIN_TABLE_UNRESOLVED_SCHEMA,
						new String[] {this.getSchema(), this.getName()}, 
						this, 
						this.getSchemaTextRange(astRoot))
				);
			return;
		}
		
		if ( ! this.isResolved()) {
			String attributeName = this.getRelationshipMapping().getPersistentAttribute().getName();
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.JOIN_TABLE_CANNOT_BE_DETERMINED,
						new String[] {attributeName}, 
						this, 
						this.getNameTextRange(astRoot))
				);
			return;
		}
		
		this.checkJoinColumns(this.joinColumns(), messages, astRoot);
		this.checkJoinColumns(this.inverseJoinColumns(), messages, astRoot);
	}		

	protected void checkJoinColumns(Iterator<JavaJoinColumn> joinColumns, List<IMessage> messages, CompilationUnit astRoot) {
		while (joinColumns.hasNext()) {
			joinColumns.next().addToMessages(messages, astRoot);
		}
	}

	/**
	 * just a little common behavior
	 */
	abstract class AbstractJoinColumnOwner implements JavaJoinColumn.Owner
	{
		AbstractJoinColumnOwner() {
			super();
		}

		public TypeMapping getTypeMapping() {
			return getRelationshipMapping().getTypeMapping();
		}
		
		public RelationshipMapping getRelationshipMapping() {
			return GenericJavaJoinTable.this.getRelationshipMapping();
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

		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			if (GenericJavaJoinTable.this.getName() == null) {
				return null;
			}
			return (GenericJavaJoinTable.this.getName().equals(tableName)) ? GenericJavaJoinTable.this.getDbTable() : null;
		}
		
		/**
		 * by default, the join column is, obviously, in the join table;
		 * not sure whether it can be anywhere else...
		 */
		public String getDefaultTableName() {
			return GenericJavaJoinTable.this.getName();
		}
		
		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return GenericJavaJoinTable.this.getValidationTextRange(astRoot);
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

		public Entity getTargetEntity() {
			return GenericJavaJoinTable.this.getRelationshipMapping().getResolvedTargetEntity();
		}

		public String getAttributeName() {
			return GenericJavaJoinTable.this.getRelationshipMapping().getPersistentAttribute().getName();
		}

		@Override
		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			org.eclipse.jpt.db.Table dbTable = super.getDbTable(tableName);
			if (dbTable != null) {
				return dbTable;
			}
			Entity targetEntity = getTargetEntity();
			return (targetEntity == null) ? null : targetEntity.getDbTable(tableName);
		}

		public org.eclipse.jpt.db.Table getReferencedColumnDbTable() {
			Entity targetEntity = getTargetEntity();
			return (targetEntity == null) ? null : targetEntity.getPrimaryDbTable();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return GenericJavaJoinTable.this.defaultInverseJoinColumn == joinColumn;
		}
		
		public String getDefaultColumnName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public int joinColumnsSize() {
			return GenericJavaJoinTable.this.inverseJoinColumnsSize();
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

		public Entity getTargetEntity() {
			return GenericJavaJoinTable.this.getRelationshipMapping().getEntity();
		}

		public String getAttributeName() {
			Entity targetEntity = GenericJavaJoinTable.this.getRelationshipMapping().getResolvedTargetEntity();
			if (targetEntity == null) {
				return null;
			}
			String attributeName = GenericJavaJoinTable.this.getRelationshipMapping().getPersistentAttribute().getName();
			for (Iterator<PersistentAttribute> stream = targetEntity.getPersistentType().allAttributes(); stream.hasNext();) {
				PersistentAttribute attribute = stream.next();
				AttributeMapping mapping = attribute.getMapping();
				if (mapping instanceof NonOwningMapping) {
					String mappedBy = ((NonOwningMapping) mapping).getMappedBy();
					if ((mappedBy != null) && mappedBy.equals(attributeName)) {
						return attribute.getName();
					}
				}
			}
			return null;
		}

		@Override
		public org.eclipse.jpt.db.Table getDbTable(String tableName) {
			org.eclipse.jpt.db.Table dbTable = super.getDbTable(tableName);
			return (dbTable != null) ? dbTable : this.getTypeMapping().getDbTable(tableName);
		}

		public org.eclipse.jpt.db.Table getReferencedColumnDbTable() {
			return getTypeMapping().getPrimaryDbTable();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return GenericJavaJoinTable.this.defaultJoinColumn == joinColumn;
		}
		
		public String getDefaultColumnName() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public int joinColumnsSize() {
			return GenericJavaJoinTable.this.joinColumnsSize();
		}
	}
	
}
