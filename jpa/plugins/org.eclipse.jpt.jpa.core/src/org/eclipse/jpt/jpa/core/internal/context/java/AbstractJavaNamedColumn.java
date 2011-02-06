/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedColumn;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.resource.java.NamedColumnAnnotation;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java<ul>
 * <li>column
 * <li>join column
 * <li>discriminator column
 * <li>order column
 * <li>primary key join column
 * </ul>
 * <strong>NB:</strong> any subclass that directly holds its column annotation
 * must:<ul>
 * <li>call the "super" constructor that takes a column annotation
 *     {@link #AbstractJavaNamedColumn(JavaJpaContextNode, JavaNamedColumn.Owner, NamedColumnAnnotation)}
 * <li>override {@link #setColumnAnnotation(NamedColumnAnnotation)} to set the column annotation
 *     so it is in place before the column's state (e.g. {@link #specifiedName})
 *     is initialized
 * </ul>
 */
public abstract class AbstractJavaNamedColumn<A extends NamedColumnAnnotation, O extends JavaNamedColumn.Owner>
	extends AbstractJavaJpaContextNode
	implements JavaNamedColumn
{
	protected final O owner;

	protected String specifiedName;
	protected String defaultName;

	protected String columnDefinition;


	protected AbstractJavaNamedColumn(JavaJpaContextNode parent, O owner) {
		this(parent, owner, null);
	}

	protected AbstractJavaNamedColumn(JavaJpaContextNode parent, O owner, A columnAnnotation) {
		super(parent);
		this.owner = owner;
		this.setColumnAnnotation(columnAnnotation);
		this.specifiedName = this.buildSpecifiedName();
		this.columnDefinition = this.buildColumnDefinition();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedName_(this.buildSpecifiedName());
		this.setColumnDefinition_(this.buildColumnDefinition());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultName(this.buildDefaultName());
	}


	// ********** column annotation **********

	/**
	 * Return the Java column annotation. Do not return <code>null</code> if the
	 * Java annotation does not exist; return a <em>null</em> column annotation
	 * instead.
	 */
	public abstract A getColumnAnnotation();

	/**
	 * see class comment... ({@link AbstractJavaNamedColumn})
	 */
	protected void setColumnAnnotation(A columnAnnotation) {
		if (columnAnnotation != null) {
			throw new IllegalArgumentException("this method must be overridden if the column annotation is not null: " + columnAnnotation); //$NON-NLS-1$
		}
	}

	protected void removeColumnAnnotationIfUnset() {
		if (this.getColumnAnnotation().isUnset()) {
			this.removeColumnAnnotation();
		}
	}

	protected abstract void removeColumnAnnotation();


	// ********** name **********

	public String getName() {
		return (this.specifiedName != null) ? this.specifiedName : this.defaultName;
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	public void setSpecifiedName(String name) {
		if (this.valuesAreDifferent(this.specifiedName, name)) {
			this.getColumnAnnotation().setName(name);
			this.removeColumnAnnotationIfUnset();
			this.setSpecifiedName_(name);
		}
	}

	protected void setSpecifiedName_(String name) {
		String old = this.specifiedName;
		this.specifiedName = name;
		this.firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, name);
	}

	protected String buildSpecifiedName() {
		return this.getColumnAnnotation().getName();
	}

	public String getDefaultName() {
		return this.defaultName;
	}

	protected void setDefaultName(String name) {
		String old = this.defaultName;
		this.defaultName = name;
		this.firePropertyChanged(DEFAULT_NAME_PROPERTY, old, name);
	}

	protected String buildDefaultName() {
		return this.owner.getDefaultColumnName();
	}


	// ********** column definition **********

	public String getColumnDefinition() {
		return this.columnDefinition;
	}

	public void setColumnDefinition(String columnDefinition) {
		if (this.valuesAreDifferent(this.columnDefinition, columnDefinition)) {
			this.getColumnAnnotation().setColumnDefinition(columnDefinition);
			this.removeColumnAnnotationIfUnset();
			this.setColumnDefinition_(columnDefinition);
		}
	}

	protected void setColumnDefinition_(String columnDefinition) {
		String old = this.columnDefinition;
		this.columnDefinition = columnDefinition;
		this.firePropertyChanged(COLUMN_DEFINITION_PROPERTY, old, columnDefinition);
	}

	public String buildColumnDefinition() {
		return this.getColumnAnnotation().getColumnDefinition();
	}


	// ********** database stuff **********

	protected Column getDbColumn() {
		Table table = this.getDbTable();
		return (table == null) ? null : table.getColumnForIdentifier(this.getName());
	}

	public Table getDbTable() {
		return this.owner.resolveDbTable(this.getTable());
	}

	/**
	 * Return the name of the column's table. This is overridden
	 * in {@link AbstractJavaBaseColumn} where a table can be defined.
	 */
	public String getTable() {
		return this.owner.getTypeMapping().getPrimaryTableName();
	}

	public boolean isResolved() {
		return this.getDbColumn() != null;
	}


	// ********** Java completion proposals **********

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

	protected boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.getColumnAnnotation().nameTouches(pos, astRoot);
	}

	protected Iterable<String> getJavaCandidateNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.getCandidateNames(filter));
	}

	protected Iterable<String> getCandidateNames(Filter<String> filter) {
		return new FilteringIterable<String>(this.getCandidateNames(), filter);
	}

	protected Iterable<String> getCandidateNames() {
		Table dbTable = this.getDbTable();
		return (dbTable != null) ? dbTable.getSortedColumnIdentifiers() : EmptyIterable.<String> instance();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.buildColumnValidator(astRoot).validate(messages, reporter);
	}

	protected JptValidator buildColumnValidator(CompilationUnit astRoot) {
		return this.owner.buildColumnValidator(this, buildTextRangeResolver(astRoot));
	}

	protected NamedColumnTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaNamedColumnTextRangeResolver(this, astRoot);
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getColumnAnnotation().getTextRange(astRoot);
		return (textRange != null) ? textRange : this.owner.getValidationTextRange(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getColumnAnnotation().getNameTextRange(astRoot);
		return (textRange != null) ? textRange : this.owner.getValidationTextRange(astRoot);
	}


	// ********** misc **********

	public boolean isVirtual() {
		return false;
	}

	protected void initializeFrom(ReadOnlyNamedColumn oldColumn) {
		this.setSpecifiedName(oldColumn.getSpecifiedName());
		this.setColumnDefinition(oldColumn.getColumnDefinition());
	}

	protected void initializeFromVirtual(ReadOnlyNamedColumn virtualColumn) {
		this.setSpecifiedName(virtualColumn.getName());
		this.setColumnDefinition(virtualColumn.getColumnDefinition());
	}

	@Override
	public void toString(StringBuilder sb) {
		String table = this.getTable();
		if (table != null) {
			sb.append(table);
			sb.append('.');
		}
		sb.append(this.getName());
	}
}
