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
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.base.ISecondaryTable;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.resource.java.NullPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTable;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

public class JavaSecondaryTable extends AbstractJavaTable
	implements IJavaSecondaryTable
{
	protected final List<IJavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;

	protected IJavaPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;

	protected SecondaryTable secondaryTableResource;
	
	public JavaSecondaryTable(IJavaEntity parent) {
		super(parent);
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<IJavaPrimaryKeyJoinColumn>();
	}
	
	@Override
	public IJavaEntity parent() {
		return (IJavaEntity) super.parent();
	}

	//***************** AbstractJavaTable implementation ********************
	
	@Override
	protected String annotationName() {
		return SecondaryTable.ANNOTATION_NAME;
	}
	
	@Override
	protected SecondaryTable tableResource() {
		return this.secondaryTableResource;
	}

	@Override
	protected String defaultName() {
		return null;
	}
	
	//***************** ISecondaryTable implementation ********************
	

	public ListIterator<IJavaPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.containsSpecifiedPrimaryKeyJoinColumns() ? this.specifiedPrimaryKeyJoinColumns() : this.defaultPrimaryKeyJoinColumns();
	}
	
	public int primaryKeyJoinColumnsSize() {
		return this.containsSpecifiedPrimaryKeyJoinColumns() ? this.specifiedPrimaryKeyJoinColumnsSize() : this.defaultPrimaryKeyJoinColumnsSize();
	}
	
	public ListIterator<IJavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return new CloneListIterator<IJavaPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}
	
	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}
	
	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
		return !this.specifiedPrimaryKeyJoinColumns.isEmpty();
	}	
	
	public IJavaPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn() {
		return this.defaultPrimaryKeyJoinColumn;
	}
	
	protected void setDefaultPrimaryKeyJoinColumn(IJavaPrimaryKeyJoinColumn newPkJoinColumn) {
		IJavaPrimaryKeyJoinColumn oldPkJoinColumn = this.defaultPrimaryKeyJoinColumn;
		this.defaultPrimaryKeyJoinColumn = newPkJoinColumn;
		firePropertyChanged(ISecondaryTable.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, oldPkJoinColumn, newPkJoinColumn);
	}

	protected ListIterator<IJavaPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns() {
		if (this.defaultPrimaryKeyJoinColumn != null) {
			return new SingleElementListIterator<IJavaPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumn);
		}
		return EmptyListIterator.instance();
	}
	
	protected int defaultPrimaryKeyJoinColumnsSize() {
		return (this.defaultPrimaryKeyJoinColumn == null) ? 0 : 1;
	}

	public IJavaPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		IJavaPrimaryKeyJoinColumn oldDefaultPkJoinColumn = this.getDefaultPrimaryKeyJoinColumn();
		if (oldDefaultPkJoinColumn != null) {
			//null the default join column now if one already exists.
			//if one does not exist, there is already a specified join column.
			//Remove it now so that it doesn't get removed during an update and
			//cause change notifications to be sent to the UI in the wrong order
			this.defaultPrimaryKeyJoinColumn = null;
		}
		IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().createJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.specifiedPrimaryKeyJoinColumns.add(index, primaryKeyJoinColumn);
		PrimaryKeyJoinColumn pkJoinColumnResource = this.secondaryTableResource.addPkJoinColumn(index);
		primaryKeyJoinColumn.initializeFromResource(pkJoinColumnResource);
		this.fireItemAdded(ISecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, primaryKeyJoinColumn);
		if (oldDefaultPkJoinColumn != null) {
			this.firePropertyChanged(ISecondaryTable.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, oldDefaultPkJoinColumn, null);
		}
		return primaryKeyJoinColumn;
	}

	protected void addSpecifiedPrimaryKeyJoinColumn(int index, IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, ISecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(IPrimaryKeyJoinColumn joinColumn) {
		this.removeSpecifiedPrimaryKeyJoinColumn(this.specifiedPrimaryKeyJoinColumns.indexOf(joinColumn));
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		IJavaPrimaryKeyJoinColumn removedPrimaryKeyJoinColumn = this.specifiedPrimaryKeyJoinColumns.remove(index);
		if (!containsSpecifiedPrimaryKeyJoinColumns()) {
			//create the defaultJoinColumn now or this will happen during project update 
			//after removing the join column from the resource model. That causes problems 
			//in the UI because the change notifications end up in the wrong order.
			this.defaultPrimaryKeyJoinColumn = createPrimaryKeyJoinColumn(new NullPrimaryKeyJoinColumn(this.secondaryTableResource));
		}
		this.secondaryTableResource.removePkJoinColumn(index);
		fireItemRemoved(ISecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPrimaryKeyJoinColumn);
		if (this.defaultPrimaryKeyJoinColumn != null) {
			//fire change notification if a defaultJoinColumn was created above
			this.firePropertyChanged(IEntity.DEFAULT_PRIMARY_KEY_JOIN_COLUMN, null, this.defaultPrimaryKeyJoinColumn);
		}
	}	

	protected void removeSpecifiedPrimaryKeyJoinColumn_(IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		removeItemFromList(primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, ISecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedPrimaryKeyJoinColumns, targetIndex, sourceIndex);
		this.secondaryTableResource.movePkJoinColumn(targetIndex, sourceIndex);
		fireItemMoved(ISecondaryTable.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	public IJavaEntity javaEntity() {
		return parent();
	}
	
	
	//********************* updating ************************
	
	public void initializeFromResource(SecondaryTable secondaryTable) {
		super.initializeFromResource(secondaryTable);
		this.secondaryTableResource = secondaryTable;
		this.initializeSpecifiedPrimaryKeyJoinColumns(secondaryTable);
		this.initializeDefaultPrimaryKeyJoinColumn(secondaryTable);
	}
	
	protected void initializeSpecifiedPrimaryKeyJoinColumns(SecondaryTable secondaryTable) {
		ListIterator<PrimaryKeyJoinColumn> annotations = secondaryTable.pkJoinColumns();
		
		while(annotations.hasNext()) {
			IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().createJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
			primaryKeyJoinColumn.initializeFromResource(annotations.next());
			this.specifiedPrimaryKeyJoinColumns.add(primaryKeyJoinColumn);
		}
	}
	
	protected boolean shouldBuildDefaultPrimaryKeyJoinColumn() {
		return !containsSpecifiedPrimaryKeyJoinColumns();
	}
	
	protected void initializeDefaultPrimaryKeyJoinColumn(SecondaryTable secondaryTable) {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			return;
		}
		this.defaultPrimaryKeyJoinColumn = this.jpaFactory().createJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.defaultPrimaryKeyJoinColumn.initializeFromResource(new NullPrimaryKeyJoinColumn(secondaryTable));
	}	

	
	public void update(SecondaryTable secondaryTableResource) {
		this.secondaryTableResource = secondaryTableResource;
		super.update(secondaryTableResource);
		this.updateSpecifiedPrimaryKeyJoinColumns(secondaryTableResource);
		this.updateDefaultPrimaryKeyJoinColumn(secondaryTableResource);
	}
	
	protected void updateSpecifiedPrimaryKeyJoinColumns(SecondaryTable secondaryTableResource) {
		ListIterator<IJavaPrimaryKeyJoinColumn> primaryKeyJoinColumns = specifiedPrimaryKeyJoinColumns();
		ListIterator<PrimaryKeyJoinColumn> resourcePrimaryKeyJoinColumns = secondaryTableResource.pkJoinColumns();
		
		while (primaryKeyJoinColumns.hasNext()) {
			IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn = primaryKeyJoinColumns.next();
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
	
	protected void updateDefaultPrimaryKeyJoinColumn(SecondaryTable secondaryTableResource) {
		if (!shouldBuildDefaultPrimaryKeyJoinColumn()) {
			setDefaultPrimaryKeyJoinColumn(null);
			return;
		}
		if (getDefaultPrimaryKeyJoinColumn() == null) {
			IJavaPrimaryKeyJoinColumn joinColumn = this.jpaFactory().createJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
			joinColumn.initializeFromResource(new NullPrimaryKeyJoinColumn(secondaryTableResource));
			this.setDefaultPrimaryKeyJoinColumn(joinColumn);
		}
		else {
			this.defaultPrimaryKeyJoinColumn.update(new NullPrimaryKeyJoinColumn(secondaryTableResource));
		}
	}	
	
	protected IJavaPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(PrimaryKeyJoinColumn primaryKeyJoinColumnResource) {
		IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().createJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		primaryKeyJoinColumn.initializeFromResource(primaryKeyJoinColumnResource);
		return primaryKeyJoinColumn;
	}
	
	protected IAbstractJoinColumn.Owner createPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}
	

	//********************* code completion ************************

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (IJavaPrimaryKeyJoinColumn column : CollectionTools.iterable(this.primaryKeyJoinColumns())) {
			result = column.candidateValuesFor(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public boolean isVirtual() {
		return false;
	}
	
	class PrimaryKeyJoinColumnOwner implements IAbstractJoinColumn.Owner
	{
		public ITextRange validationTextRange(CompilationUnit astRoot) {
			return JavaSecondaryTable.this.validationTextRange(astRoot);
		}

		public ITypeMapping typeMapping() {
			return JavaSecondaryTable.this.javaEntity();
		}

		public Table dbTable(String tableName) {
			return JavaSecondaryTable.this.dbTable();
		}

		public Table dbReferencedColumnTable() {
			return typeMapping().primaryDbTable();
		}

		public int joinColumnsSize() {
			return JavaSecondaryTable.this.primaryKeyJoinColumnsSize();
		}
		
		public boolean isVirtual(IAbstractJoinColumn joinColumn) {
			return JavaSecondaryTable.this.defaultPrimaryKeyJoinColumn == joinColumn;
		}
		
		public String defaultColumnName() {
			if (joinColumnsSize() != 1) {
				return null;
			}
			return javaEntity().parentEntity().primaryKeyColumnName();

		}
	}

}
