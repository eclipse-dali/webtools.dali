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

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AbstractColumn;
import org.eclipse.jpt.core.context.java.JavaAbstractColumn;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.AbstractColumnAnnotation;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

public abstract class AbstractJavaColumn<T extends AbstractColumnAnnotation> extends AbstractJavaNamedColumn<T>
	implements JavaAbstractColumn
{

	protected String specifiedTable;
	
	protected String defaultTable;

	protected Boolean specifiedUnique;
	
	protected Boolean specifiedNullable;
	
	protected Boolean specifiedInsertable;
	
	protected Boolean specifiedUpdatable;

	protected AbstractJavaColumn(JavaJpaContextNode parent, JavaAbstractColumn.Owner owner) {
		super(parent, owner);
	}
	
	@Override
	protected void initializeFromResource(T column) {
		super.initializeFromResource(column);
		this.defaultTable = this.defaultTable();
		this.specifiedTable = this.specifiedTable(column);
		this.specifiedUnique = this.specifiedUnique(column);
		this.specifiedNullable = this.specifiedNullable(column);
		this.specifiedInsertable = this.specifiedInsertable(column);
		this.specifiedUpdatable = this.specifiedUpdatable(column);
	}
	
	@Override
	public JavaAbstractColumn.Owner owner() {
		return (JavaAbstractColumn.Owner) super.owner();
	}

	//************** IAbstractColumn implementation *******************
	

	public String getTable() {
		return (this.getSpecifiedTable() == null) ? getDefaultTable() : this.getSpecifiedTable();
	}

	public String getSpecifiedTable() {
		return this.specifiedTable;
	}

	public void setSpecifiedTable(String newSpecifiedTable) {
		String oldSpecifiedTable = this.specifiedTable;
		this.specifiedTable = newSpecifiedTable;
		columnResource().setTable(newSpecifiedTable);
		firePropertyChanged(AbstractColumn.SPECIFIED_TABLE_PROPERTY, oldSpecifiedTable, newSpecifiedTable);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedTable_(String newSpecifiedTable) {
		String oldSpecifiedTable = this.specifiedTable;
		this.specifiedTable = newSpecifiedTable;
		firePropertyChanged(AbstractColumn.SPECIFIED_TABLE_PROPERTY, oldSpecifiedTable, newSpecifiedTable);
	}

	public String getDefaultTable() {
		return this.defaultTable;
	}

	protected void setDefaultTable(String newDefaultTable) {
		String oldDefaultTable = this.defaultTable;
		this.defaultTable = newDefaultTable;
		firePropertyChanged(AbstractColumn.DEFAULT_TABLE_PROPERTY, oldDefaultTable, newDefaultTable);
	}

	public Boolean getUnique() {
		return (this.getSpecifiedUnique() == null) ? this.getDefaultUnique() : this.getSpecifiedUnique();
	}
	
	public Boolean getDefaultUnique() {
		return AbstractColumn.DEFAULT_UNIQUE;
	}
	
	public Boolean getSpecifiedUnique() {
		return this.specifiedUnique;
	}
	
	public void setSpecifiedUnique(Boolean newSpecifiedUnique) {
		Boolean oldSpecifiedUnique = this.specifiedUnique;
		this.specifiedUnique = newSpecifiedUnique;
		this.columnResource().setUnique(newSpecifiedUnique);
		firePropertyChanged(AbstractColumn.SPECIFIED_UNIQUE_PROPERTY, oldSpecifiedUnique, newSpecifiedUnique);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedUnique_(Boolean newSpecifiedUnique) {
		Boolean oldSpecifiedUnique = this.specifiedUnique;
		this.specifiedUnique = newSpecifiedUnique;
		firePropertyChanged(AbstractColumn.SPECIFIED_UNIQUE_PROPERTY, oldSpecifiedUnique, newSpecifiedUnique);
	}
	
	public Boolean getNullable() {
		return (this.getSpecifiedNullable() == null) ? this.getDefaultNullable() : this.getSpecifiedNullable();
	}
	
	public Boolean getDefaultNullable() {
		return AbstractColumn.DEFAULT_NULLABLE;
	}
	
	public Boolean getSpecifiedNullable() {
		return this.specifiedNullable;
	}
	
	public void setSpecifiedNullable(Boolean newSpecifiedNullable) {
		Boolean oldSpecifiedNullable = this.specifiedNullable;
		this.specifiedNullable = newSpecifiedNullable;
		this.columnResource().setNullable(newSpecifiedNullable);
		firePropertyChanged(AbstractColumn.SPECIFIED_NULLABLE_PROPERTY, oldSpecifiedNullable, newSpecifiedNullable);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedNullable_(Boolean newSpecifiedNullable) {
		Boolean oldSpecifiedNullable = this.specifiedNullable;
		this.specifiedNullable = newSpecifiedNullable;
		firePropertyChanged(AbstractColumn.SPECIFIED_NULLABLE_PROPERTY, oldSpecifiedNullable, newSpecifiedNullable);
	}
	
	public Boolean getInsertable() {
		return (this.getSpecifiedInsertable() == null) ? this.getDefaultInsertable() : this.getSpecifiedInsertable();
	}
	
	public Boolean getDefaultInsertable() {
		return AbstractColumn.DEFAULT_INSERTABLE;
	}
	
	public Boolean getSpecifiedInsertable() {
		return this.specifiedInsertable;
	}
	
	public void setSpecifiedInsertable(Boolean newSpecifiedInsertable) {
		Boolean oldSpecifiedInsertable = this.specifiedInsertable;
		this.specifiedInsertable = newSpecifiedInsertable;
		this.columnResource().setInsertable(newSpecifiedInsertable);
		firePropertyChanged(AbstractColumn.SPECIFIED_INSERTABLE_PROPERTY, oldSpecifiedInsertable, newSpecifiedInsertable);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedInsertable_(Boolean newSpecifiedInsertable) {
		Boolean oldSpecifiedInsertable = this.specifiedInsertable;
		this.specifiedInsertable = newSpecifiedInsertable;
		firePropertyChanged(AbstractColumn.SPECIFIED_INSERTABLE_PROPERTY, oldSpecifiedInsertable, newSpecifiedInsertable);
	}
	
	public Boolean getUpdatable() {
		return (this.getSpecifiedUpdatable() == null) ? this.getDefaultUpdatable() : this.getSpecifiedUpdatable();
	}
	
	public Boolean getDefaultUpdatable() {
		return AbstractColumn.DEFAULT_UPDATABLE;
	}
	
	public Boolean getSpecifiedUpdatable() {
		return this.specifiedUpdatable;
	}
	
	public void setSpecifiedUpdatable(Boolean newSpecifiedUpdatable) {
		Boolean oldSpecifiedUpdatable = this.specifiedUpdatable;
		this.specifiedUpdatable = newSpecifiedUpdatable;
		this.columnResource().setUpdatable(newSpecifiedUpdatable);
		firePropertyChanged(AbstractColumn.SPECIFIED_UPDATABLE_PROPERTY, oldSpecifiedUpdatable, newSpecifiedUpdatable);
	}

	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedUpdatable_(Boolean newSpecifiedUpdatable) {
		Boolean oldSpecifiedUpdatable = this.specifiedUpdatable;
		this.specifiedUpdatable = newSpecifiedUpdatable;
		firePropertyChanged(AbstractColumn.SPECIFIED_UPDATABLE_PROPERTY, oldSpecifiedUpdatable, newSpecifiedUpdatable);
	}

	@Override
	protected String tableName() {
		return this.getTable();
	}

	public TextRange tableTextRange(CompilationUnit astRoot) {
		return columnResource().tableTextRange(astRoot);
	}

	public boolean tableTouches(int pos, CompilationUnit astRoot) {
		return columnResource().tableTouches(pos, astRoot);
	}

	private Iterator<String> candidateTableNames() {
		return this.tableIsAllowed() ? this.owner().typeMapping().associatedTableNamesIncludingInherited() : EmptyIterator.<String> instance();
	}

	private Iterator<String> candidateTableNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateTableNames(), filter);
	}

	private Iterator<String> quotedCandidateTableNames(Filter<String> filter) {
		return StringTools.quote(this.candidateTableNames(filter));
	}

	/**
	 * Return whether the 'table' element is allowed. It is not allowed for
	 * join columns inside of join tables.
	 */
	public abstract boolean tableIsAllowed();

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.tableTouches(pos, astRoot)) {
			return this.quotedCandidateTableNames(filter);
		}
		return null;
	}
	
	@Override
	protected void update(T column) {
		super.update(column);
		this.setDefaultTable(this.defaultTable());
		this.setSpecifiedTable_(this.specifiedTable(column));
		this.setSpecifiedUnique_(this.specifiedUnique(column));
		this.setSpecifiedNullable_(this.specifiedNullable(column));
		this.setSpecifiedInsertable_(this.specifiedInsertable(column));
		this.setSpecifiedUpdatable_(this.specifiedUpdatable(column));
	}

	protected String defaultTable() {
		return this.owner().defaultTableName();
	}
	
	protected String specifiedTable(AbstractColumnAnnotation column) {
		return column.getTable();
	}
	
	protected Boolean specifiedUnique(AbstractColumnAnnotation column) {
		return column.getUnique();
	}
	
	protected Boolean specifiedNullable(AbstractColumnAnnotation column) {
		return column.getNullable();
	}
	
	protected Boolean specifiedInsertable(AbstractColumnAnnotation column) {
		return column.getInsertable();
	}
	
	protected Boolean specifiedUpdatable(AbstractColumnAnnotation column) {
		return column.getUpdatable();
	}
}
