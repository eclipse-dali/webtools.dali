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

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumn;
import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;


public class JavaPrimaryKeyJoinColumn extends JavaNamedColumn<PrimaryKeyJoinColumn>
	implements IJavaPrimaryKeyJoinColumn
{
	protected String specifiedReferencedColumnName;

	protected String defaultReferencedColumnName;

	protected PrimaryKeyJoinColumn primaryKeyJoinColumnResource;
	
	public JavaPrimaryKeyJoinColumn(IJavaJpaContextNode parent, IAbstractJoinColumn.Owner owner) {
		super(parent, owner);
	}

	@Override
	public void initializeFromResource(PrimaryKeyJoinColumn column) {
		this.primaryKeyJoinColumnResource = column;
		super.initializeFromResource(column);
		this.specifiedReferencedColumnName = this.specifiedReferencedColumnName(column);
		this.defaultReferencedColumnName = this.defaultReferencedColumnName();
	}

	//************** JavaNamedColumn implementation ***************
	@Override
	public IAbstractJoinColumn.Owner owner() {
		return (IAbstractJoinColumn.Owner) super.owner();
	}
	
	@Override
	protected PrimaryKeyJoinColumn columnResource() {
		return this.primaryKeyJoinColumnResource;
	}
	
	//************** IAbstractJoinColumn implementation ***************
	
	public String getReferencedColumnName() {
		return (this.specifiedReferencedColumnName == null) ? this.defaultReferencedColumnName : this.specifiedReferencedColumnName;
	}

	public String getSpecifiedReferencedColumnName() {
		return this.specifiedReferencedColumnName;
	}

	public void setSpecifiedReferencedColumnName(String newSpecifiedReferencedColumnName) {
		String oldSpecifiedReferencedColumnName = this.specifiedReferencedColumnName;
		this.specifiedReferencedColumnName = newSpecifiedReferencedColumnName;
		columnResource().setReferencedColumnName(newSpecifiedReferencedColumnName);
		firePropertyChanged(SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY, oldSpecifiedReferencedColumnName, newSpecifiedReferencedColumnName);
	}

	public String getDefaultReferencedColumnName() {
		return this.defaultReferencedColumnName;
	}

	protected void setDefaultReferencedColumnName(String newDefaultReferencedColumnName) {
		String oldDefaultReferencedColumnName = this.defaultReferencedColumnName;
		this.defaultReferencedColumnName = newDefaultReferencedColumnName;
		firePropertyChanged(DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY, oldDefaultReferencedColumnName, newDefaultReferencedColumnName);
	}
	
	public boolean isVirtual() {
		return owner().isVirtual(this);
	}

	@Override
	protected String tableName() {
		return this.owner().typeMapping().getTableName();
	}

	public Column dbReferencedColumn() {
		Table table = this.dbReferencedColumnTable();
		return (table == null) ? null : table.columnNamed(this.getReferencedColumnName());
	}

	public Table dbReferencedColumnTable() {
		return owner().dbReferencedColumnTable();
	}

	public boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot) {
		return this.columnResource().referencedColumnNameTouches(pos, astRoot);
	}

	private Iterator<String> candidateReferencedColumnNames() {
		Table table = this.owner().dbReferencedColumnTable();
		return (table != null) ? table.columnNames() : EmptyIterator.<String> instance();
	}

	private Iterator<String> candidateReferencedColumnNames(Filter<String> filter) {
		return new FilteringIterator<String>(this.candidateReferencedColumnNames(), filter);
	}

	private Iterator<String> quotedCandidateReferencedColumnNames(Filter<String> filter) {
		return StringTools.quote(this.candidateReferencedColumnNames(filter));
	}

	@Override
	public Iterator<String> connectedCandidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedCandidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.referencedColumnNameTouches(pos, astRoot)) {
			return this.quotedCandidateReferencedColumnNames(filter);
		}
		return null;
	}

	public boolean isReferencedColumnResolved() {
		return dbReferencedColumn() != null;
	}

	public ITextRange referencedColumnNameTextRange(CompilationUnit astRoot) {
		return this.columnResource().referencedColumnNameTextRange(astRoot);
	}
	
	public ITextRange validationTextRange(CompilationUnit astRoot) {
		ITextRange textRange = columnResource().textRange(astRoot);
		return (textRange != null) ? textRange : this.owner().validationTextRange(astRoot);	
	}

	@Override
	public void update(PrimaryKeyJoinColumn column) {
		this.primaryKeyJoinColumnResource = column;
		super.update(column);
		this.setSpecifiedReferencedColumnName(this.specifiedReferencedColumnName(column));
		this.setDefaultReferencedColumnName(this.defaultReferencedColumnName());
	}
	
	protected String specifiedReferencedColumnName(PrimaryKeyJoinColumn column) {
		return column.getReferencedColumnName();
	}
	
	//TODO This default is different for oneToOne mappings, we don't yet support pkJoinColumns there
	@Override
	protected String defaultName() {
		if (owner().joinColumnsSize() != 1) {
			return null;
		}
		//ClassCastException as soon as we support primaryKeyJoinColumns anywhere other than Entity
		IEntity entity = (IEntity) owner().typeMapping();
		return entity.parentEntity().primaryKeyColumnName();
	}
	
	protected String defaultReferencedColumnName() {
		return defaultName();
	}

}
