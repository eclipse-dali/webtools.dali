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
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaBaseJoinColumn;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.internal.resource.java.NullPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTableAnnotation;
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
public class GenericJavaSecondaryTable
	extends AbstractJavaTable
	implements JavaSecondaryTable
{
	protected final List<JavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;

	protected JavaPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;

	protected SecondaryTableAnnotation resourceSecondaryTable;
	
	public GenericJavaSecondaryTable(JavaEntity parent) {
		super(parent);
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<JavaPrimaryKeyJoinColumn>();
	}
	
	@Override
	public JavaEntity getParent() {
		return (JavaEntity) super.getParent();
	}


	//***************** AbstractJavaTable implementation ********************
	
	@Override
	protected String getAnnotationName() {
		return SecondaryTableAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	protected SecondaryTableAnnotation getResourceTable() {
		return this.resourceSecondaryTable;
	}

	// a secondary table doesn't have a default name
	@Override
	protected String buildDefaultName() {
		return null;
	}

	@Override
	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}
	
	@Override
	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}
	

	//***************** ISecondaryTable implementation ********************

	public ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.containsSpecifiedPrimaryKeyJoinColumns() ? this.specifiedPrimaryKeyJoinColumns() : this.defaultPrimaryKeyJoinColumns();
	}
	
	public int primaryKeyJoinColumnsSize() {
		return this.containsSpecifiedPrimaryKeyJoinColumns() ? this.specifiedPrimaryKeyJoinColumnsSize() : this.defaultPrimaryKeyJoinColumnsSize();
	}
	
	public ListIterator<JavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return new CloneListIterator<JavaPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}
	
	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}
	
	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
		return !this.specifiedPrimaryKeyJoinColumns.isEmpty();
	}	
	
	public JavaPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn() {
		return this.defaultPrimaryKeyJoinColumn;
	}
	
	protected void setDefaultPrimaryKeyJoinColumn(JavaPrimaryKeyJoinColumn newPkJoinColumn) {
		JavaPrimaryKeyJoinColumn oldPkJoinColumn = this.defaultPrimaryKeyJoinColumn;
		this.defaultPrimaryKeyJoinColumn = newPkJoinColumn;
		firePropertyChanged(SecondaryTable.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, oldPkJoinColumn, newPkJoinColumn);
	}

	protected ListIterator<JavaPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns() {
		if (this.defaultPrimaryKeyJoinColumn != null) {
			return new SingleElementListIterator<JavaPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumn);
		}
		return EmptyListIterator.instance();
	}
	
	protected int defaultPrimaryKeyJoinColumnsSize() {
		return (this.defaultPrimaryKeyJoinColumn == null) ? 0 : 1;
	}

	public JavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		JavaPrimaryKeyJoinColumn oldDefaultPkJoinColumn = this.getDefaultPrimaryKeyJoinColumn();
		if (oldDefaultPkJoinColumn != null) {
			//null the default join column now if one already exists.
			//if one does not exist, there is already a specified join column.
			//Remove it now so that it doesn't get removed during an update and
			//cause change notifications to be sent to the UI in the wrong order
			this.defaultPrimaryKeyJoinColumn = null;
		}
		JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.specifiedPrimaryKeyJoinColumns.add(index, primaryKeyJoinColumn);
		PrimaryKeyJoinColumnAnnotation pkJoinColumnResource = this.resourceSecondaryTable.addPkJoinColumn(index);
		primaryKeyJoinColumn.initialize(pkJoinColumnResource);
		this.fireItemAdded(SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, primaryKeyJoinColumn);
		if (oldDefaultPkJoinColumn != null) {
			this.firePropertyChanged(SecondaryTable.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, oldDefaultPkJoinColumn, null);
		}
		return primaryKeyJoinColumn;
	}

	protected void addSpecifiedPrimaryKeyJoinColumn(int index, JavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	protected void addSpecifiedPrimaryKeyJoinColumn(JavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		this.addSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.size(), primaryKeyJoinColumn);
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(PrimaryKeyJoinColumn joinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.indexOf(joinColumn));
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		JavaPrimaryKeyJoinColumn removedPrimaryKeyJoinColumn = this.specifiedPrimaryKeyJoinColumns.remove(index);
		if (!containsSpecifiedPrimaryKeyJoinColumns()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultPrimaryKeyJoinColumn = buildPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumn(this.resourceSecondaryTable));
		}
		this.resourceSecondaryTable.removePkJoinColumn(index);
		fireItemRemoved(SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPrimaryKeyJoinColumn);
		if (this.defaultPrimaryKeyJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(Entity.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, null, this.defaultPrimaryKeyJoinColumn);
		}
	}	

	protected void removeSpecifiedPrimaryKeyJoinColumn_(JavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		removeItemFromList(primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedPrimaryKeyJoinColumns, targetIndex, sourceIndex);
		this.resourceSecondaryTable.movePkJoinColumn(targetIndex, sourceIndex);
		fireItemMoved(SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	public JavaEntity getJavaEntity() {
		return getParent();
	}
	
	
	//********************* updating ************************
	
	public void initialize(SecondaryTableAnnotation secondaryTable) {
		super.initialize(secondaryTable);
		this.resourceSecondaryTable = secondaryTable;
		this.initializeSpecifiedPrimaryKeyJoinColumns(secondaryTable);
		this.initializeDefaultPrimaryKeyJoinColumn(secondaryTable);
	}
	
	protected void initializeSpecifiedPrimaryKeyJoinColumns(SecondaryTableAnnotation secondaryTable) {
		ListIterator<PrimaryKeyJoinColumnAnnotation> annotations = secondaryTable.pkJoinColumns();
		
		while(annotations.hasNext()) {
			this.specifiedPrimaryKeyJoinColumns.add(buildPrimaryKeyJoinColumn(annotations.next()));
		}
	}
	
	protected boolean shouldBuildDefaultPrimaryKeyJoinColumn() {
		return !containsSpecifiedPrimaryKeyJoinColumns();
	}
	
	protected void initializeDefaultPrimaryKeyJoinColumn(SecondaryTableAnnotation secondaryTable) {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			return;
		}
		this.defaultPrimaryKeyJoinColumn = buildPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumn(secondaryTable));
	}	

	
	public void update(SecondaryTableAnnotation sta) {
		this.resourceSecondaryTable = sta;
		super.update(sta);
		this.updateSpecifiedPrimaryKeyJoinColumns(sta);
		this.updateDefaultPrimaryKeyJoinColumn(sta);
	}
	
	protected void updateSpecifiedPrimaryKeyJoinColumns(SecondaryTableAnnotation sta) {
		ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns = specifiedPrimaryKeyJoinColumns();
		ListIterator<PrimaryKeyJoinColumnAnnotation> resourcePrimaryKeyJoinColumns = sta.pkJoinColumns();
		
		while (primaryKeyJoinColumns.hasNext()) {
			JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = primaryKeyJoinColumns.next();
			if (resourcePrimaryKeyJoinColumns.hasNext()) {
				primaryKeyJoinColumn.update(resourcePrimaryKeyJoinColumns.next());
			}
			else {
				removeSpecifiedPrimaryKeyJoinColumn_(primaryKeyJoinColumn);
			}
		}
		
		while (resourcePrimaryKeyJoinColumns.hasNext()) {
			addSpecifiedPrimaryKeyJoinColumn(buildPrimaryKeyJoinColumn(resourcePrimaryKeyJoinColumns.next()));
		}
	}
	
	protected void updateDefaultPrimaryKeyJoinColumn(SecondaryTableAnnotation sta) {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			setDefaultPrimaryKeyJoinColumn(null);
			return;
		}
		if (getDefaultPrimaryKeyJoinColumn() == null) {
			this.setDefaultPrimaryKeyJoinColumn(buildPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumn(sta)));
		}
		else {
			this.defaultPrimaryKeyJoinColumn.update(new NullPrimaryKeyJoinColumn(sta));
		}
	}	
	
	protected JavaPrimaryKeyJoinColumn buildPrimaryKeyJoinColumn(PrimaryKeyJoinColumnAnnotation resourcePrimaryKeyJoinColumn) {
		JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = getJpaFactory().buildJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		primaryKeyJoinColumn.initialize(resourcePrimaryKeyJoinColumn);
		return primaryKeyJoinColumn;
	}
	
	protected JavaBaseJoinColumn.Owner createPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}
	
	@Override
	public void addToMessages(List<IMessage> messages, CompilationUnit astRoot) {
		super.addToMessages(messages, astRoot);
		if (this.connectionProfileIsActive()) {
			this.checkDatabase(messages, astRoot);
		}
		for (Iterator<JavaPrimaryKeyJoinColumn> stream = this.primaryKeyJoinColumns(); stream.hasNext(); ) {
			stream.next().addToMessages(messages, astRoot);
		}
	}

	protected void checkDatabase(List<IMessage> messages, CompilationUnit astRoot) {
		if ( ! this.hasResolvedCatalog()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.SECONDARY_TABLE_UNRESOLVED_CATALOG,
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
						JpaValidationMessages.SECONDARY_TABLE_UNRESOLVED_SCHEMA,
						new String[] {this.getSchema(), this.getName()}, 
						this, 
						this.getSchemaTextRange(astRoot))
				);
			return;
		}
		
		if ( ! this.isResolved()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.SECONDARY_TABLE_UNRESOLVED_NAME,
						new String[] {this.getName()}, 
						this, 
						this.getNameTextRange(astRoot))
				);
			return;
		}
	}


	//********************* code completion ************************

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

	public boolean isVirtual() {
		return false;
	}
	
	class PrimaryKeyJoinColumnOwner implements JavaBaseJoinColumn.Owner
	{
		public TextRange getValidationTextRange(CompilationUnit astRoot) {
			return GenericJavaSecondaryTable.this.getValidationTextRange(astRoot);
		}

		public TypeMapping getTypeMapping() {
			return GenericJavaSecondaryTable.this.getJavaEntity();
		}

		public Table getDbTable(String tableName) {
			return GenericJavaSecondaryTable.this.getDbTable();
		}

		public Table getReferencedColumnDbTable() {
			return getTypeMapping().getPrimaryDbTable();
		}

		public int joinColumnsSize() {
			return GenericJavaSecondaryTable.this.primaryKeyJoinColumnsSize();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return GenericJavaSecondaryTable.this.defaultPrimaryKeyJoinColumn == joinColumn;
		}
		
		public String getDefaultColumnName() {
			if (joinColumnsSize() != 1) {
				return null;
			}
			return getJavaEntity().getPrimaryKeyColumnName();

		}
	}

}
