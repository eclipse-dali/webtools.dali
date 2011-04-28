/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.VirtualNamedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;

/**
 * Java virtual<ul>
 * <li>column
 * <li>join column
 * </ul>
 * <strong>NB:</strong> all state is sync'ed/updated in {@link #update()}
 * because <em>all</em> of it is derived from the context model (i.e. none of it
 * is derived from the resource model).
 */
public abstract class AbstractJavaVirtualNamedColumn<O extends ReadOnlyNamedColumn.Owner, C extends NamedColumn>
	extends AbstractJavaJpaContextNode
	implements VirtualNamedColumn
{
	protected final O owner;

	protected String specifiedName;
	protected String defaultName;

	protected String columnDefinition;


	protected AbstractJavaVirtualNamedColumn(JavaJpaContextNode parent, O owner) {
		super(parent);
		this.owner = owner;
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();

		this.setSpecifiedName(this.buildSpecifiedName());
		this.setDefaultName(this.buildDefaultName());

		this.setColumnDefinition(this.buildColumnDefinition());
	}


	// ********** column **********

	/**
	 * This should never return <code>null</code>.
	 */
	public abstract C getOverriddenColumn();


	// ********** name **********

	public String getName() {
		return (this.specifiedName != null) ? this.specifiedName : this.defaultName;
	}

	public String getSpecifiedName() {
		return this.specifiedName;
	}

	protected void setSpecifiedName(String name) {
		String old = this.specifiedName;
		this.specifiedName = name;
		this.firePropertyChanged(SPECIFIED_NAME_PROPERTY, old, name);
	}

	protected String buildSpecifiedName() {
		return this.getOverriddenColumn().getSpecifiedName();
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

	protected void setColumnDefinition(String columnDefinition) {
		String old = this.columnDefinition;
		this.columnDefinition = columnDefinition;
		this.firePropertyChanged(COLUMN_DEFINITION_PROPERTY, old, columnDefinition);
	}

	protected String buildColumnDefinition() {
		return this.getOverriddenColumn().getColumnDefinition();
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getParent().getValidationTextRange(astRoot);
	}


	// ********** misc **********

	@Override
	public JavaJpaContextNode getParent() {
		return (JavaJpaContextNode) super.getParent();
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
