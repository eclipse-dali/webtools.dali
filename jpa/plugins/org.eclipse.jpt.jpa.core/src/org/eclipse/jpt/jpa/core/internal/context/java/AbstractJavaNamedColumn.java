/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.VirtualNamedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedNamedColumn;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
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
 *     {@link #AbstractJavaNamedColumn(NamedColumn.ParentAdapter, NamedColumnAnnotation)}
 * <li>override {@link #setColumnAnnotation(NamedColumnAnnotation)} to set the column annotation
 *     so it is in place before the column's state (e.g. {@link #specifiedName})
 *     is initialized
 * </ul>
 */
public abstract class AbstractJavaNamedColumn<PA extends NamedColumn.ParentAdapter, A extends NamedColumnAnnotation>
	extends AbstractJavaContextModel<JpaContextModel>
	implements JavaSpecifiedNamedColumn
{
	protected final PA parentAdapter;

	protected String specifiedName;
	protected String defaultName;

	protected String columnDefinition;

	protected Table dbTable;


	protected AbstractJavaNamedColumn(PA parentAdapter) {
		this(parentAdapter, null);
	}

	protected AbstractJavaNamedColumn(PA parentAdapter, A columnAnnotation) {
		super(parentAdapter.getColumnParent());
		this.parentAdapter = parentAdapter;
		this.setColumnAnnotation(columnAnnotation);
		this.initialize(this.getColumnAnnotation());
	}

	protected void initialize(A columnAnnotation) {
		this.specifiedName = this.buildSpecifiedName(columnAnnotation);
		this.columnDefinition = this.buildColumnDefinition(columnAnnotation);
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.synchronizeWithResourceModel(this.getColumnAnnotation());
	}

	protected void synchronizeWithResourceModel(A columnAnnotation) {
		this.setSpecifiedName_(this.buildSpecifiedName(columnAnnotation));
		this.setColumnDefinition_(this.buildColumnDefinition(columnAnnotation));		
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultName(this.buildDefaultName());
		this.setDbTable(this.buildDbTable());
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
		if (ObjectTools.notEquals(this.specifiedName, name)) {
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

	protected String buildSpecifiedName(A columnAnnotation) {
		return columnAnnotation.getName();
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
		return this.parentAdapter.getDefaultColumnName(this);
	}


	// ********** column definition **********

	public String getColumnDefinition() {
		return this.columnDefinition;
	}

	public void setColumnDefinition(String columnDefinition) {
		if (ObjectTools.notEquals(this.columnDefinition, columnDefinition)) {
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

	public String buildColumnDefinition(A columnAnnotation) {
		return columnAnnotation.getColumnDefinition();
	}


	// ********** database stuff **********

	protected Column getDbColumn() {
		return (this.dbTable == null) ? null : this.dbTable.getColumnForIdentifier(this.getName());
	}

	public Table getDbTable() {
		return this.dbTable;
	}

	protected void setDbTable(Table dbTable) {
		Table old = this.dbTable;
		this.dbTable = dbTable;
		this.firePropertyChanged(DB_TABLE_PROPERTY, old, dbTable);
	}

	protected Table buildDbTable() {
		return this.parentAdapter.resolveDbTable(this.getTableName());
	}

	/**
	 * Return the name of the column's table. This is overridden
	 * in {@link AbstractJavaBaseColumn} where a table can be defined.
	 */
	public String getTableName() {
		return this.parentAdapter.getDefaultTableName();
	}

	public boolean isResolved() {
		return this.getDbColumn() != null;
	}


	// ********** Java completion proposals **********

	@Override
	protected Iterable<String> getConnectedCompletionProposals(int pos) {
		Iterable<String> result = super.getConnectedCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.nameTouches(pos)) {
			return this.getJavaCandidateNames();
		}
		return null;
	}

	protected boolean nameTouches(int pos) {
		return this.getColumnAnnotation().nameTouches(pos);
	}

	protected Iterable<String> getJavaCandidateNames() {
		return new TransformationIterable<String, String>(this.getCandidateNames(),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}

	protected Iterable<String> getCandidateNames() {
		return (this.dbTable != null) ? this.dbTable.getSortedColumnIdentifiers() : EmptyIterable.<String> instance();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.buildValidator().validate(messages, reporter);
	}

	protected JpaValidator buildValidator() {
		return this.parentAdapter.buildColumnValidator(this);
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getColumnAnnotation().getTextRange();
		return (textRange != null) ? textRange : this.parentAdapter.getValidationTextRange();
	}

	public TextRange getNameValidationTextRange() {
		return this.getValidationTextRange(this.getColumnAnnotation().getNameValidationTextRange());
	}


	// ********** misc **********


	public boolean isVirtual() {
		return !this.getColumnAnnotation().isSpecified();
	}

	protected void initializeFrom(VirtualNamedColumn virtualColumn) {
		this.setSpecifiedName(virtualColumn.getName());
		this.setColumnDefinition(virtualColumn.getColumnDefinition());
	}

	@Override
	public void toString(StringBuilder sb) {
		String table = this.getTableName();
		if (table != null) {
			sb.append(table);
			sb.append('.');
		}
		sb.append(this.getName());
	}
}
