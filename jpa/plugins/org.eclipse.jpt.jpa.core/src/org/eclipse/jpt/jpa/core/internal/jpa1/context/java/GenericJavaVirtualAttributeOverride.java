/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.VirtualColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaVirtualOverride;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Virtual Java attribute override
 */
public class GenericJavaVirtualAttributeOverride
	extends AbstractJavaVirtualOverride<JavaAttributeOverrideContainer>
	implements JavaVirtualAttributeOverride, VirtualColumn.Owner
{
	protected final VirtualColumn column;


	public GenericJavaVirtualAttributeOverride(JavaAttributeOverrideContainer parent, String name) {
		super(parent, name);
		this.column = this.buildColumn();
	}

	@Override
	public void update() {
		super.update();
		this.column.update();
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
		return this.getJpaFactory().buildJavaVirtualColumn(this, this);
	}


	// ********** column owner implementation **********

	@Override
	public String getDefaultTableName() {
		String overriddenColumnTable = this.getOverriddenColumnTableName();
		return (overriddenColumnTable != null) ? overriddenColumnTable : super.getDefaultTableName();
	}

	protected String getOverriddenColumnTableName() {
		ReadOnlyColumn overriddenColumn = this.resolveOverriddenColumn();
		// pretty sure this is the *specified* table...
		return (overriddenColumn == null) ? null : overriddenColumn.getSpecifiedTableName();
	}

	public String getDefaultColumnName(ReadOnlyNamedColumn col) {
		String overriddenColumnName = this.getOverriddenColumnName();
		return (overriddenColumnName != null) ? overriddenColumnName : this.name;
	}

	protected String getOverriddenColumnName() {
		ReadOnlyColumn overriddenColumn = this.resolveOverriddenColumn();
		return (overriddenColumn == null) ? null : overriddenColumn.getName();
	}

	public ReadOnlyColumn resolveOverriddenColumn() {
		return this.getContainer().resolveOverriddenColumn(this.name);
	}

	public JptValidator buildColumnValidator(ReadOnlyNamedColumn col) {
		return this.getContainer().buildColumnValidator(this, (ReadOnlyBaseColumn) col, this);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.column.validate(messages, reporter);
	}
}
