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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.SecondaryTable;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAbstractJoinColumn;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.internal.resource.java.NullPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

public class GenericJavaSecondaryTable extends AbstractJavaTable
	implements JavaSecondaryTable
{
	protected final List<JavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;

	protected JavaPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;

	protected SecondaryTableAnnotation secondaryTableResource;
	
	public GenericJavaSecondaryTable(JavaEntity parent) {
		super(parent);
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<JavaPrimaryKeyJoinColumn>();
	}
	
	@Override
	public JavaEntity parent() {
		return (JavaEntity) super.parent();
	}

	//***************** AbstractJavaTable implementation ********************
	
	@Override
	protected String annotationName() {
		return SecondaryTableAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	protected SecondaryTableAnnotation tableResource() {
		return this.secondaryTableResource;
	}

	@Override
	protected String defaultName() {
		return null;
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
		JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().buildJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.specifiedPrimaryKeyJoinColumns.add(index, primaryKeyJoinColumn);
		PrimaryKeyJoinColumnAnnotation pkJoinColumnResource = this.secondaryTableResource.addPkJoinColumn(index);
		primaryKeyJoinColumn.initializeFromResource(pkJoinColumnResource);
		this.fireItemAdded(SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, primaryKeyJoinColumn);
		if (oldDefaultPkJoinColumn != null) {
			this.firePropertyChanged(SecondaryTable.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, oldDefaultPkJoinColumn, null);
		}
		return primaryKeyJoinColumn;
	}

	protected void addSpecifiedPrimaryKeyJoinColumn(int index, JavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
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
			this.defaultPrimaryKeyJoinColumn = createPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumn(this.secondaryTableResource));
		}
		this.secondaryTableResource.removePkJoinColumn(index);
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
		this.secondaryTableResource.movePkJoinColumn(targetIndex, sourceIndex);
		fireItemMoved(SecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	public JavaEntity javaEntity() {
		return parent();
	}
	
	
	//********************* updating ************************
	
	public void initializeFromResource(SecondaryTableAnnotation secondaryTable) {
		super.initializeFromResource(secondaryTable);
		this.secondaryTableResource = secondaryTable;
		this.initializeSpecifiedPrimaryKeyJoinColumns(secondaryTable);
		this.initializeDefaultPrimaryKeyJoinColumn(secondaryTable);
	}
	
	protected void initializeSpecifiedPrimaryKeyJoinColumns(SecondaryTableAnnotation secondaryTable) {
		ListIterator<PrimaryKeyJoinColumnAnnotation> annotations = secondaryTable.pkJoinColumns();
		
		while(annotations.hasNext()) {
			JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().buildJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
			primaryKeyJoinColumn.initializeFromResource(annotations.next());
			this.specifiedPrimaryKeyJoinColumns.add(primaryKeyJoinColumn);
		}
	}
	
	protected boolean shouldBuildDefaultPrimaryKeyJoinColumn() {
		return !containsSpecifiedPrimaryKeyJoinColumns();
	}
	
	protected void initializeDefaultPrimaryKeyJoinColumn(SecondaryTableAnnotation secondaryTable) {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			return;
		}
		this.defaultPrimaryKeyJoinColumn = this.jpaFactory().buildJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.defaultPrimaryKeyJoinColumn.initializeFromResource(new NullPrimaryKeyJoinColumn(secondaryTable));
	}	

	
	public void update(SecondaryTableAnnotation secondaryTableResource) {
		this.secondaryTableResource = secondaryTableResource;
		super.update(secondaryTableResource);
		this.updateSpecifiedPrimaryKeyJoinColumns(secondaryTableResource);
		this.updateDefaultPrimaryKeyJoinColumn(secondaryTableResource);
	}
	
	protected void updateSpecifiedPrimaryKeyJoinColumns(SecondaryTableAnnotation secondaryTableResource) {
		ListIterator<JavaPrimaryKeyJoinColumn> primaryKeyJoinColumns = specifiedPrimaryKeyJoinColumns();
		ListIterator<PrimaryKeyJoinColumnAnnotation> resourcePrimaryKeyJoinColumns = secondaryTableResource.pkJoinColumns();
		
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
			addSpecifiedPrimaryKeyJoinColumn(specifiedPrimaryKeyJoinColumnsSize(), createPrimaryKeyJoinColumn(resourcePrimaryKeyJoinColumns.next()));
		}
	}
	
	protected void updateDefaultPrimaryKeyJoinColumn(SecondaryTableAnnotation secondaryTableResource) {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			setDefaultPrimaryKeyJoinColumn(null);
			return;
		}
		if (getDefaultPrimaryKeyJoinColumn() == null) {
			JavaPrimaryKeyJoinColumn joinColumn = this.jpaFactory().buildJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
			joinColumn.initializeFromResource(new NullPrimaryKeyJoinColumn(secondaryTableResource));
			this.setDefaultPrimaryKeyJoinColumn(joinColumn);
		}
		else {
			this.defaultPrimaryKeyJoinColumn.update(new NullPrimaryKeyJoinColumn(secondaryTableResource));
		}
	}	
	
	protected JavaPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(PrimaryKeyJoinColumnAnnotation primaryKeyJoinColumnResource) {
		JavaPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().buildJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		primaryKeyJoinColumn.initializeFromResource(primaryKeyJoinColumnResource);
		return primaryKeyJoinColumn;
	}
	
	protected JavaAbstractJoinColumn.Owner createPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
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
	
	class PrimaryKeyJoinColumnOwner implements JavaAbstractJoinColumn.Owner
	{
		public TextRange validationTextRange(CompilationUnit astRoot) {
			return GenericJavaSecondaryTable.this.validationTextRange(astRoot);
		}

		public TypeMapping typeMapping() {
			return GenericJavaSecondaryTable.this.javaEntity();
		}

		public Table dbTable(String tableName) {
			return GenericJavaSecondaryTable.this.dbTable();
		}

		public Table dbReferencedColumnTable() {
			return typeMapping().primaryDbTable();
		}

		public int joinColumnsSize() {
			return GenericJavaSecondaryTable.this.primaryKeyJoinColumnsSize();
		}
		
		public boolean isVirtual(AbstractJoinColumn joinColumn) {
			return GenericJavaSecondaryTable.this.defaultPrimaryKeyJoinColumn == joinColumn;
		}
		
		public String defaultColumnName() {
			if (joinColumnsSize() != 1) {
				return null;
			}
			return javaEntity().primaryKeyColumnName();

		}
	}

}
