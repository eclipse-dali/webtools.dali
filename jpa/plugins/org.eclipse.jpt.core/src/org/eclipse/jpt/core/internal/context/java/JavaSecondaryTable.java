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
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.resource.java.NullPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTable;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

public class JavaSecondaryTable extends AbstractJavaTable
	implements IJavaSecondaryTable
{
	protected final List<IJavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns;

	protected final IJavaPrimaryKeyJoinColumn defaultPrimaryKeyJoinColumn;

	protected SecondaryTable secondaryTableResource;
	
	public JavaSecondaryTable(IJavaEntity parent) {
		super(parent);
		this.specifiedPrimaryKeyJoinColumns = new ArrayList<IJavaPrimaryKeyJoinColumn>();
		this.defaultPrimaryKeyJoinColumn = this.jpaFactory().createJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
	}
	
	protected IAbstractJoinColumn.Owner createPrimaryKeyJoinColumnOwner() {
		return new PrimaryKeyJoinColumnOwner();
	}
	
	public void initializeFromResource(SecondaryTable secondaryTable) {
		super.initializeFromResource(secondaryTable);
		this.secondaryTableResource = secondaryTable;
		this.initializePrimaryKeyJoinColumns(secondaryTable);
	}
	
	protected void initializePrimaryKeyJoinColumns(SecondaryTable secondaryTable) {
		ListIterator<PrimaryKeyJoinColumn> annotations = secondaryTable.pkJoinColumns();
		
		while(annotations.hasNext()) {
			IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().createJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
			primaryKeyJoinColumn.initializeFromResource(annotations.next());
			this.specifiedPrimaryKeyJoinColumns.add(primaryKeyJoinColumn);
		}
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
	

	@SuppressWarnings("unchecked")
	public ListIterator<IJavaPrimaryKeyJoinColumn> primaryKeyJoinColumns() {
		return this.specifiedPrimaryKeyJoinColumns.isEmpty() ? this.defaultPrimaryKeyJoinColumns() : this.specifiedPrimaryKeyJoinColumns();
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<IJavaPrimaryKeyJoinColumn> specifiedPrimaryKeyJoinColumns() {
		return new CloneListIterator<IJavaPrimaryKeyJoinColumn>(this.specifiedPrimaryKeyJoinColumns);
	}
	
	@SuppressWarnings("unchecked")
	public ListIterator<IJavaPrimaryKeyJoinColumn> defaultPrimaryKeyJoinColumns() {
		return new SingleElementListIterator<IJavaPrimaryKeyJoinColumn>(this.defaultPrimaryKeyJoinColumn);
	}

	public IPrimaryKeyJoinColumn addSpecifiedPrimaryKeyJoinColumn(int index) {
		IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().createJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		this.specifiedPrimaryKeyJoinColumns.add(index, primaryKeyJoinColumn);
		this.secondaryTableResource.addPkJoinColumn(index);
		this.fireItemAdded(IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, primaryKeyJoinColumn);
		return primaryKeyJoinColumn;
	}

	protected void addSpecifiedPrimaryKeyJoinColumn(int index, IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		addItemToList(index, primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void removeSpecifiedPrimaryKeyJoinColumn(int index) {
		IJavaPrimaryKeyJoinColumn removedPrimaryKeyJoinColumn = this.specifiedPrimaryKeyJoinColumns.remove(index);
		this.secondaryTableResource.removePkJoinColumn(index);
		fireItemRemoved(IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, index, removedPrimaryKeyJoinColumn);
	}

	protected void removeSpecifiedPrimaryKeyJoinColumn(IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn) {
		removeItemFromList(primaryKeyJoinColumn, this.specifiedPrimaryKeyJoinColumns, IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST);
	}
	
	public void moveSpecifiedPrimaryKeyJoinColumn(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.specifiedPrimaryKeyJoinColumns, targetIndex, sourceIndex);
		this.secondaryTableResource.movePkJoinColumn(targetIndex, sourceIndex);
		fireItemMoved(IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST, targetIndex, sourceIndex);		
	}
	
	public int specifiedPrimaryKeyJoinColumnsSize() {
		return this.specifiedPrimaryKeyJoinColumns.size();
	}
	
	public int primaryKeyJoinColumnsSize() {
		return CollectionTools.size(primaryKeyJoinColumns());
	}
	
	public IJavaEntity javaEntity() {
		return (IJavaEntity) parent();
	}
	
	
	//********************* updating ************************

	public void update(SecondaryTable secondaryTableResource) {
		this.secondaryTableResource = secondaryTableResource;
		super.update(secondaryTableResource);
		this.updateSpecifiedPrimaryKeyJoinColumns(secondaryTableResource);
		this.updateDefaultPrimaryKeyJoinColumns(secondaryTableResource);
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
				removeSpecifiedPrimaryKeyJoinColumn(primaryKeyJoinColumn);
			}
		}
		
		while (resourcePrimaryKeyJoinColumns.hasNext()) {
			addSpecifiedPrimaryKeyJoinColumn(specifiedPrimaryKeyJoinColumnsSize(), createPrimaryKeyJoinColumn(resourcePrimaryKeyJoinColumns.next()));
		}
	}
	
	protected IJavaPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(PrimaryKeyJoinColumn primaryKeyJoinColumnResource) {
		IJavaPrimaryKeyJoinColumn primaryKeyJoinColumn = jpaFactory().createJavaPrimaryKeyJoinColumn(this, createPrimaryKeyJoinColumnOwner());
		primaryKeyJoinColumn.initializeFromResource(primaryKeyJoinColumnResource);
		return primaryKeyJoinColumn;
	}

	protected void updateDefaultPrimaryKeyJoinColumns(SecondaryTable secondaryTableResource) {
		this.defaultPrimaryKeyJoinColumn.update(new NullPrimaryKeyJoinColumn(secondaryTableResource));
	}
	
	// ********** AbstractJavaTable implementation **********
//
//	public IPrimaryKeyJoinColumn createPrimaryKeyJoinColumn(int index) {
//		return this.createJavaPrimaryKeyJoinColumn(index);
//	}
//
//	private JavaPrimaryKeyJoinColumn createJavaPrimaryKeyJoinColumn(int index) {
//		return JavaPrimaryKeyJoinColumn.createSecondaryTableJoinColumn(this.getDeclarationAnnotationAdapter(), buildPkJoinColumnOwner(), this.getMember(), index);
//	}
//
//	protected IAbstractJoinColumn.Owner buildPkJoinColumnOwner() {
//		return new ISecondaryTable.PrimaryKeyJoinColumnOwner(this);
//	}
//
//	public boolean containsSpecifiedPrimaryKeyJoinColumns() {
//		return !this.getSpecifiedPrimaryKeyJoinColumns().isEmpty();
//	}
//
//	@Override
//	protected JavaUniqueConstraint createJavaUniqueConstraint(int index) {
//		return JavaUniqueConstraint.createSecondaryTableUniqueConstraint(new UniqueConstraintOwner(this), this.getDeclarationAnnotationAdapter(), getMember(), index);
//	}



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
