/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.VirtualColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaVirtualOverride;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Virtual Java attribute override
 */
public class GenericJavaVirtualAttributeOverride
	extends AbstractJavaVirtualOverride<JavaAttributeOverrideContainer>
	implements JavaVirtualAttributeOverride, VirtualColumn.ParentAdapter
{
	protected final VirtualColumn column;


	public GenericJavaVirtualAttributeOverride(JavaAttributeOverrideContainer parent, String name) {
		super(parent, name);
		this.column = this.buildColumn();
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.column.update(monitor);
	}

	@Override
	public JavaSpecifiedAttributeOverride convertToSpecified() {
		return (JavaSpecifiedAttributeOverride) super.convertToSpecified();
	}


	// ********** column **********

	public VirtualColumn getColumn() {
		return this.column;
	}

	/**
	 * The original column should be available (since the presence of its
	 * attribute is what precipitated the creation of the virtual override).
	 */
	protected VirtualColumn buildColumn() {
		return this.getJpaFactory().buildJavaVirtualColumn(this);
	}


	// ********** column parent adapter **********

	public JpaContextModel getColumnParent() {
		return this;  // no adapter
	}

	@Override
	public String getDefaultTableName() {
		String overriddenColumnTable = this.getOverriddenColumnTableName();
		return (overriddenColumnTable != null) ? overriddenColumnTable : super.getDefaultTableName();
	}

	protected String getOverriddenColumnTableName() {
		Column overriddenColumn = this.resolveOverriddenColumn();
		// pretty sure this is the *specified* table...
		return (overriddenColumn == null) ? null : overriddenColumn.getSpecifiedTableName();
	}

	public String getDefaultColumnName(NamedColumn col) {
		String overriddenColumnName = this.getOverriddenColumnName();
		return (overriddenColumnName != null) ? overriddenColumnName : this.name;
	}

	protected String getOverriddenColumnName() {
		Column overriddenColumn = this.resolveOverriddenColumn();
		return (overriddenColumn == null) ? null : overriddenColumn.getName();
	}

	public Column resolveOverriddenColumn() {
		return this.getContainer().resolveOverriddenColumn(this.name);
	}

	public JpaValidator buildColumnValidator(NamedColumn col) {
		return this.getContainer().buildColumnValidator(this, (BaseColumn) col, this);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.column.validate(messages, reporter);
	}
}
