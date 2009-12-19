/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaNamedColumn;
import org.eclipse.jpt.core.resource.java.NamedColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;


public abstract class AbstractJavaNamedColumn<T extends NamedColumnAnnotation> extends AbstractJavaJpaContextNode
	implements JavaNamedColumn
{

	protected Owner owner;
	
	protected String specifiedName;

	protected String defaultName;

	protected String columnDefinition;

	protected T resourceColumn;

	protected AbstractJavaNamedColumn(JavaJpaContextNode parent, Owner owner) {
		super(parent);
		this.owner = owner;
	}

	// ******************* initialization from java resource model ********************
	
	protected void initialize(T column) {
		this.resourceColumn = column;
		this.specifiedName = column.getName();
		this.defaultName = this.buildDefaultName();
		this.columnDefinition = column.getColumnDefinition();	
	}

	protected void update(T column) {
		this.resourceColumn = column;
		this.setSpecifiedName_(column.getName());
		this.setDefaultName(this.buildDefaultName());
		this.setColumnDefinition_(column.getColumnDefinition());
	}	

	protected T getResourceColumn() {
		return this.resourceColumn;
	}

	
	//************** NamedColumn implementation *****************
	public String getName() {
		return (this.specifiedName != null) ? this.specifiedName : this.defaultName;
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	public void setSpecifiedName(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		getResourceColumn().setName(newSpecifiedName);
		firePropertyChanged(NamedColumn.SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setSpecifiedName_(String newSpecifiedName) {
		String oldSpecifiedName = this.specifiedName;
		this.specifiedName = newSpecifiedName;
		firePropertyChanged(NamedColumn.SPECIFIED_NAME_PROPERTY, oldSpecifiedName, newSpecifiedName);
	}

	public String getDefaultName() {
		return this.defaultName;
	}

	protected void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		firePropertyChanged(NamedColumn.DEFAULT_NAME_PROPERTY, oldDefaultName, newDefaultName);
	}
	
	/**
	 * Return the default column name.
	 */
	protected String buildDefaultName() {
		return this.getOwner().getDefaultColumnName();
	}

	public String getColumnDefinition() {
		return this.columnDefinition;
	}
	
	public void setColumnDefinition(String newColumnDefinition) {
		String oldColumnDefinition = this.columnDefinition;
		this.columnDefinition = newColumnDefinition;
		getResourceColumn().setColumnDefinition(newColumnDefinition);
		firePropertyChanged(NamedColumn.COLUMN_DEFINITION_PROPERTY, oldColumnDefinition, newColumnDefinition);
	}
	
	/**
	 * internal setter used only for updating from the resource model.
	 * There were problems with InvalidThreadAccess exceptions in the UI
	 * when you set a value from the UI and the annotation doesn't exist yet.
	 * Adding the annotation causes an update to occur and then the exception.
	 */
	protected void setColumnDefinition_(String newColumnDefinition) {
		String oldColumnDefinition = this.columnDefinition;
		this.columnDefinition = newColumnDefinition;
		firePropertyChanged(NamedColumn.COLUMN_DEFINITION_PROPERTY, oldColumnDefinition, newColumnDefinition);
	}

	public Owner getOwner() {
		return this.owner;
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getResourceColumn().getNameTextRange(astRoot);
		return (textRange != null) ? textRange : this.getOwner().getValidationTextRange(astRoot);
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.getResourceColumn().nameTouches(pos, astRoot);
	}
	
	public Column getDbColumn() {
		Table table = this.getDbTable();
		return (table == null) ? null : table.getColumnForIdentifier(this.getName());
	}

	public Table getDbTable() {
		return getOwner().getDbTable(this.getTableName());
	}

	/**
	 * Return the name of the column's table.
	 */
	protected String getTableName() {
		return this.getOwner().getTypeMapping().getPrimaryTableName();
	}

	public boolean isResolved() {
		return this.getDbColumn() != null;
	}

	@Override
	public Iterator<String> connectedJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.nameTouches(pos, astRoot)) {
			return this.getJavaCandidateNames(filter).iterator();
		}
		return null;
	}

	private Iterable<String> getJavaCandidateNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.getCandidateNames(filter));
	}

	private Iterable<String> getCandidateNames(Filter<String> filter) {
		return new FilteringIterable<String, String>(this.getCandidateNames(), filter);
	}

	private Iterable<String> getCandidateNames() {
		Table dbTable = this.getDbTable();
		return (dbTable != null) ? dbTable.getSortedColumnIdentifiers() : EmptyIterable.<String> instance();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}
}
