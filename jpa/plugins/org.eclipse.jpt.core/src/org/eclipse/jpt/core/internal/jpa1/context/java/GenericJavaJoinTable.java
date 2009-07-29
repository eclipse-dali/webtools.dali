/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.core.internal.context.MappingTools;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaTable;
import org.eclipse.jpt.core.internal.resource.java.NullJoinColumnAnnotation;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java join table
 */
public class GenericJavaJoinTable
	extends AbstractJavaTable
	implements JavaJoinTable
{

	protected JavaJoinColumn defaultJoinColumn;

	protected final Vector<JavaJoinColumn> specifiedJoinColumns = new Vector<JavaJoinColumn>();
	protected final JavaJoinColumn.Owner joinColumnOwner;

	protected JavaJoinColumn defaultInverseJoinColumn;

	protected final Vector<JavaJoinColumn> specifiedInverseJoinColumns = new Vector<JavaJoinColumn>();
	protected final JavaJoinColumn.Owner inverseJoinColumnOwner;


	public GenericJavaJoinTable(JavaJoinTableJoiningStrategy parent) {
		super(parent);
		this.joinColumnOwner = this.buildJoinColumnOwner();
		this.inverseJoinColumnOwner = this.buildInverseJoinColumnOwner();
	}

	protected JavaJoinColumn.Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	protected JavaJoinColumn.Owner buildInverseJoinColumnOwner() {
		return new InverseJoinColumnOwner();
	}

	public JavaRelationshipMapping getRelationshipMapping() {
		return this.getParent().getRelationshipReference().getRelationshipMapping();
	}

	public void initialize(JoinTableAnnotation joinTable) {
		super.initialize(joinTable);
		this.initializeSpecifiedJoinColumns(joinTable);
		this.initializeDefaultJoinColumn(joinTable);
		this.initializeSpecifiedInverseJoinColumns(joinTable);
		this.initializeDefaultInverseJoinColumn(joinTable);
	}

	public void update(JoinTableAnnotation joinTable) {
		super.update(joinTable);
		this.updateSpecifiedJoinColumns(joinTable);
		this.updateDefaultJoinColumn(joinTable);
		this.updateSpecifiedInverseJoinColumns(joinTable);
		this.updateDefaultInverseJoinColumn(joinTable);
	}


	// ********** AbstractJavaTable implementation **********

	@Override
	public JavaJoinTableJoiningStrategy getParent() {
		return (JavaJoinTableJoiningStrategy) super.getParent();
	}

	@Override
	protected String getAnnotationName() {
		return JoinTableAnnotation.ANNOTATION_NAME;
	}

	@Override
	protected String buildDefaultName() {
		return this.getRelationshipMapping().getJoinTableDefaultName();
	}

	@Override
	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}

	@Override
	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}

	@Override
	protected JoinTableAnnotation getAnnotation() {
		return this.getParent().getAnnotation();
	}


	// ********** Table implementation **********

	public boolean isResourceSpecified() {
		return this.getAnnotation().isSpecified();
	}


	// ********** join columns **********

	public ListIterator<JavaJoinColumn> joinColumns() {
		return this.hasSpecifiedJoinColumns() ? this.specifiedJoinColumns() : this.defaultJoinColumns();
	}

	public int joinColumnsSize() {
		return this.hasSpecifiedJoinColumns() ? this.specifiedJoinColumnsSize() : this.defaultJoinColumnsSize();
	}

	public void convertDefaultToSpecifiedJoinColumn() {
		MappingTools.convertJoinTableDefaultToSpecifiedJoinColumn(this);
	}

	protected JavaJoinColumn buildJoinColumn(JoinColumnAnnotation joinColumnAnnotation) {
		return this.buildJoinColumn(joinColumnAnnotation, this.joinColumnOwner);
	}


	// ********** default join column **********

	public JavaJoinColumn getDefaultJoinColumn() {
		return this.defaultJoinColumn;
	}

	protected void setDefaultJoinColumn(JavaJoinColumn defaultJoinColumn) {
		JavaJoinColumn old = this.defaultJoinColumn;
		this.defaultJoinColumn = defaultJoinColumn;
		this.firePropertyChanged(DEFAULT_JOIN_COLUMN, old, defaultJoinColumn);
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

	protected void initializeDefaultJoinColumn(JoinTableAnnotation joinTableAnnotation) {
		if (this.shouldBuildDefaultJoinColumn()) {
			this.defaultJoinColumn = this.buildJoinColumn(new NullJoinColumnAnnotation(joinTableAnnotation));
		}
	}

	protected void updateDefaultJoinColumn(JoinTableAnnotation joinTableAnnotation) {
		if (this.shouldBuildDefaultJoinColumn()) {
			if (this.defaultJoinColumn == null) {
				this.setDefaultJoinColumn(this.buildJoinColumn(new NullJoinColumnAnnotation(joinTableAnnotation)));
			} else {
				this.defaultJoinColumn.update(new NullJoinColumnAnnotation(joinTableAnnotation));
			}
		} else {
			this.setDefaultJoinColumn(null);
		}
	}

	protected boolean shouldBuildDefaultJoinColumn() {
		return ! this.hasSpecifiedJoinColumns();
	}


	// ********** specified join columns **********

	public ListIterator<JavaJoinColumn> specifiedJoinColumns() {
		return new CloneListIterator<JavaJoinColumn>(this.specifiedJoinColumns);
	}

	public int specifiedJoinColumnsSize() {
		return this.specifiedJoinColumns.size();
	}

	public boolean hasSpecifiedJoinColumns() {
		return this.specifiedJoinColumns.size() != 0;
	}

	public JavaJoinColumn addSpecifiedJoinColumn(int index) {
		// Clear out the default now so it doesn't get removed during an update and
		// cause change notifications to be sent to the UI in the wrong order.
		// If the default is already null, nothing will happen.
		JoinColumn oldDefault = this.defaultJoinColumn;
		this.defaultJoinColumn = null;

		JavaJoinColumn joinColumn = this.getJpaFactory().buildJavaJoinColumn(this, this.joinColumnOwner);
		this.specifiedJoinColumns.add(index, joinColumn);
		JoinTableAnnotation joinTableAnnotation = this.getAnnotation();
		JoinColumnAnnotation joinColumnAnnotation = joinTableAnnotation.addJoinColumn(index);
		joinColumn.initialize(joinColumnAnnotation);
		this.fireItemAdded(SPECIFIED_JOIN_COLUMNS_LIST, index, joinColumn);

		this.firePropertyChanged(DEFAULT_JOIN_COLUMN, oldDefault, null);
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
		if ( ! this.hasSpecifiedJoinColumns()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultJoinColumn = this.buildJoinColumn(new NullJoinColumnAnnotation(this.getAnnotation()));
		}
		this.getAnnotation().removeJoinColumn(index);
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
		this.getAnnotation().moveJoinColumn(targetIndex, sourceIndex);
		this.fireItemMoved(SPECIFIED_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);
	}

	public void clearSpecifiedJoinColumns() {
		// for now, we have to remove annotations one at a time...
		for (int i = this.specifiedJoinColumns.size(); i-- > 0; ) {
			this.removeSpecifiedJoinColumn(i);
		}
	}

	protected void initializeSpecifiedJoinColumns(JoinTableAnnotation joinTableAnnotation) {
		for (ListIterator<JoinColumnAnnotation> stream = joinTableAnnotation.joinColumns(); stream.hasNext(); ) {
			this.specifiedJoinColumns.add(this.buildJoinColumn(stream.next()));
		}
	}

	protected void updateSpecifiedJoinColumns(JoinTableAnnotation joinTableAnnotation) {
		ListIterator<JavaJoinColumn> joinColumns = this.specifiedJoinColumns();
		ListIterator<JoinColumnAnnotation> joinColumnAnnotations = joinTableAnnotation.joinColumns();

		while (joinColumns.hasNext()) {
			JavaJoinColumn joinColumn = joinColumns.next();
			if (joinColumnAnnotations.hasNext()) {
				joinColumn.update(joinColumnAnnotations.next());
			} else {
				this.removeSpecifiedJoinColumn_(joinColumn);
			}
		}

		while (joinColumnAnnotations.hasNext()) {
			this.addSpecifiedJoinColumn(this.buildJoinColumn(joinColumnAnnotations.next()));
		}
	}


	// ********** inverse join columns **********

	public ListIterator<JavaJoinColumn> inverseJoinColumns() {
		return this.hasSpecifiedInverseJoinColumns() ? this.specifiedInverseJoinColumns() : this.defaultInverseJoinColumns();
	}

	public int inverseJoinColumnsSize() {
		return this.hasSpecifiedInverseJoinColumns() ? this.specifiedInverseJoinColumnsSize() : this.defaultInverseJoinColumnsSize();
	}

	public void convertDefaultToSpecifiedInverseJoinColumn() {
		MappingTools.convertJoinTableDefaultToSpecifiedInverseJoinColumn(this);
	}

	protected JavaJoinColumn buildInverseJoinColumn(JoinColumnAnnotation joinColumnAnnotation) {
		return this.buildJoinColumn(joinColumnAnnotation, this.inverseJoinColumnOwner);
	}


	// ********** default inverse join column **********

	public JavaJoinColumn getDefaultInverseJoinColumn() {
		return this.defaultInverseJoinColumn;
	}

	protected void setDefaultInverseJoinColumn(JavaJoinColumn defaultInverseJoinColumn) {
		JavaJoinColumn old = this.defaultInverseJoinColumn;
		this.defaultInverseJoinColumn = defaultInverseJoinColumn;
		this.firePropertyChanged(DEFAULT_INVERSE_JOIN_COLUMN, old, defaultInverseJoinColumn);
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

	protected void initializeDefaultInverseJoinColumn(JoinTableAnnotation joinTableAnnotation) {
		if (this.shouldBuildDefaultInverseJoinColumn()) {
			this.defaultInverseJoinColumn = this.buildInverseJoinColumn(new NullJoinColumnAnnotation(joinTableAnnotation));
		}
	}

	protected boolean shouldBuildDefaultInverseJoinColumn() {
		return ! this.hasSpecifiedInverseJoinColumns();
	}

	protected void updateDefaultInverseJoinColumn(JoinTableAnnotation joinTableAnnotation) {
		if (this.shouldBuildDefaultInverseJoinColumn()) {
			if (this.defaultInverseJoinColumn == null) {
				this.setDefaultInverseJoinColumn(this.buildInverseJoinColumn(new NullJoinColumnAnnotation(joinTableAnnotation)));
			} else {
				this.defaultInverseJoinColumn.update(new NullJoinColumnAnnotation(joinTableAnnotation));
			}
		} else {
			this.setDefaultInverseJoinColumn(null);
		}
	}


	// ********** specified inverse join columns **********

	public ListIterator<JavaJoinColumn> specifiedInverseJoinColumns() {
		return new CloneListIterator<JavaJoinColumn>(this.specifiedInverseJoinColumns);
	}

	public int specifiedInverseJoinColumnsSize() {
		return this.specifiedInverseJoinColumns.size();
	}

	public boolean hasSpecifiedInverseJoinColumns() {
		return this.specifiedInverseJoinColumns.size() != 0;
	}

	public JavaJoinColumn addSpecifiedInverseJoinColumn(int index) {
		// Clear out the default now so it doesn't get removed during an update and
		// cause change notifications to be sent to the UI in the wrong order.
		// If the default is already null, nothing will happen.
		JoinColumn oldDefault = this.defaultInverseJoinColumn;
		this.defaultInverseJoinColumn = null;

		JavaJoinColumn inverseJoinColumn = this.getJpaFactory().buildJavaJoinColumn(this, this.inverseJoinColumnOwner);
		this.specifiedInverseJoinColumns.add(index, inverseJoinColumn);
		JoinTableAnnotation joinTableAnnotation = this.getAnnotation();
		JoinColumnAnnotation joinColumnAnnotation = joinTableAnnotation.addInverseJoinColumn(index);
		inverseJoinColumn.initialize(joinColumnAnnotation);
		this.fireItemAdded(SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, index, inverseJoinColumn);

		this.firePropertyChanged(DEFAULT_INVERSE_JOIN_COLUMN, oldDefault, null);
		return inverseJoinColumn;
	}

	protected void addSpecifiedInverseJoinColumn(int index, JavaJoinColumn inverseJoinColumn) {
		this.addItemToList(index, inverseJoinColumn, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}

	protected void addSpecifiedInverseJoinColumn(JavaJoinColumn inverseJoinColumn) {
		this.addSpecifiedInverseJoinColumn(this.specifiedInverseJoinColumns.size(), inverseJoinColumn);
	}

	public void removeSpecifiedInverseJoinColumn(JoinColumn inverseJoinColumn) {
		this.removeSpecifiedInverseJoinColumn(this.specifiedInverseJoinColumns.indexOf(inverseJoinColumn));
	}

	public void removeSpecifiedInverseJoinColumn(int index) {
		JavaJoinColumn removedJoinColumn = this.specifiedInverseJoinColumns.remove(index);
		if ( ! this.hasSpecifiedInverseJoinColumns()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultInverseJoinColumn = this.buildInverseJoinColumn(new NullJoinColumnAnnotation(this.getAnnotation()));
		}
		this.getAnnotation().removeInverseJoinColumn(index);
		this.fireItemRemoved(SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, index, removedJoinColumn);
		if (this.defaultInverseJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(DEFAULT_INVERSE_JOIN_COLUMN, null, this.defaultInverseJoinColumn);
		}
	}

	protected void removeSpecifiedInverseJoinColumn_(JavaJoinColumn joinColumn) {
		this.removeItemFromList(joinColumn, this.specifiedInverseJoinColumns, SPECIFIED_INVERSE_JOIN_COLUMNS_LIST);
	}

	public void moveSpecifiedInverseJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedInverseJoinColumns, targetIndex, sourceIndex);
		this.getAnnotation().moveInverseJoinColumn(targetIndex, sourceIndex);
		this.fireItemMoved(SPECIFIED_INVERSE_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);
	}

	public void clearSpecifiedInverseJoinColumns() {
		// for now, we have to remove annotations one at a time...
		for (int i = this.specifiedInverseJoinColumns.size(); i-- > 0; ) {
			this.removeSpecifiedInverseJoinColumn(i);
		}
	}

	protected void initializeSpecifiedInverseJoinColumns(JoinTableAnnotation joinTableAnnotation) {
		for (ListIterator<JoinColumnAnnotation> stream = joinTableAnnotation.inverseJoinColumns(); stream.hasNext(); ) {
			this.specifiedInverseJoinColumns.add(this.buildInverseJoinColumn(stream.next()));
		}
	}

	protected void updateSpecifiedInverseJoinColumns(JoinTableAnnotation joinTableAnnotation) {
		ListIterator<JavaJoinColumn> joinColumns = this.specifiedInverseJoinColumns();
		ListIterator<JoinColumnAnnotation> joinColumnAnnotations = joinTableAnnotation.inverseJoinColumns();

		while (joinColumns.hasNext()) {
			JavaJoinColumn joinColumn = joinColumns.next();
			if (joinColumnAnnotations.hasNext()) {
				joinColumn.update(joinColumnAnnotations.next());
			} else {
				this.removeSpecifiedInverseJoinColumn_(joinColumn);
			}
		}

		while (joinColumnAnnotations.hasNext()) {
			this.addSpecifiedInverseJoinColumn(this.buildInverseJoinColumn(joinColumnAnnotations.next()));
		}
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
		for (JavaJoinColumn column : CollectionTools.iterable(this.inverseJoinColumns())) {
			result = column.javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}


	// ********** misc **********

	protected JavaJoinColumn buildJoinColumn(JoinColumnAnnotation joinColumnAnnotation, JavaJoinColumn.Owner owner) {
		JavaJoinColumn joinColumn = this.getJpaFactory().buildJavaJoinColumn(this, owner);
		joinColumn.initialize(joinColumnAnnotation);
		return joinColumn;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (this.getRelationshipMapping().shouldValidateAgainstDatabase()) {
			this.validateAgainstDatabase(messages, reporter, astRoot);
		}
	}

	protected void validateAgainstDatabase(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		if ( ! this.hasResolvedCatalog()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.JOIN_TABLE_UNRESOLVED_CATALOG,
					new String[] {this.getCatalog(), this.getName()}, 
					this, 
					this.getCatalogTextRange(astRoot)
				)
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
					this.getSchemaTextRange(astRoot)
				)
			);
			return;
		}

		if ( ! this.isResolved()) {
			if (getName() != null) { //if name is null, the validation will be handled elsewhere, such as the target entity is not defined
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.JOIN_TABLE_UNRESOLVED_NAME,
							new String[] {this.getName()}, 
							this, 
							this.getNameTextRange(astRoot))
					);
			}
			return;
		}

		this.validateJoinColumns(this.joinColumns(), messages, reporter, astRoot);
		this.validateJoinColumns(this.inverseJoinColumns(), messages, reporter, astRoot);
	}

	protected void validateJoinColumns(Iterator<JavaJoinColumn> joinColumns, List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		while (joinColumns.hasNext()) {
			joinColumns.next().validate(messages, reporter, astRoot);
		}
	}


	// ********** join column owner adapters **********

	/**
	 * just a little common behavior
	 */
	protected abstract class AbstractJoinColumnOwner
		implements JavaJoinColumn.Owner
	{
		protected AbstractJoinColumnOwner() {
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
	 * owner for "back-pointer" JoinColumns;
	 * these point at the source/owning entity
	 */
	protected class JoinColumnOwner
		extends AbstractJoinColumnOwner
	{
		protected JoinColumnOwner() {
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
			for (PersistentAttribute each : 
					CollectionTools.iterable(
						targetEntity.getPersistentType().allAttributes())) {
				if (each.getMapping().isOwnedBy(getRelationshipMapping())) {
					return each.getName();
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
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public int joinColumnsSize() {
			return GenericJavaJoinTable.this.joinColumnsSize();
		}
	}


	/**
	 * owner for "forward-pointer" JoinColumns;
	 * these point at the target/inverse entity
	 */
	protected class InverseJoinColumnOwner
		extends AbstractJoinColumnOwner
	{
		protected InverseJoinColumnOwner() {
			super();
		}

		public Entity getTargetEntity() {
			return GenericJavaJoinTable.this.getRelationshipMapping().getResolvedTargetEntity();
		}

		public String getAttributeName() {
			return GenericJavaJoinTable.this.getRelationshipMapping().getName();
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
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public int joinColumnsSize() {
			return GenericJavaJoinTable.this.inverseJoinColumnsSize();
		}
	}

}
