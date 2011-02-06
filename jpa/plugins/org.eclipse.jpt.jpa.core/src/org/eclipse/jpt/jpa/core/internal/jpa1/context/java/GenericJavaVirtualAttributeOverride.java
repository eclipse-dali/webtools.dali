/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.ReadOnlyColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualColumn;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaVirtualOverride;

/**
 * Virtual Java attribute override
 */
public class GenericJavaVirtualAttributeOverride
	extends AbstractJavaVirtualOverride<JavaAttributeOverrideContainer>
	implements JavaVirtualAttributeOverride, JavaVirtualColumn.Owner
{
	protected final JavaVirtualColumn column;


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
	public JavaAttributeOverride convertToSpecified() {
		return (JavaAttributeOverride) super.convertToSpecified();
	}


	// ********** column **********

	public JavaVirtualColumn getColumn() {
		return this.column;
	}

	/**
	 * The original column should be available (since the presence of its
	 * attribute is what precipitated the creation of the virtual override).
	 */
	protected JavaVirtualColumn buildColumn() {
		return this.getJpaFactory().buildJavaVirtualColumn(this, this);
	}


	// ********** column owner implementation **********

	public TypeMapping getTypeMapping() {
		return this.getContainer().getTypeMapping();
	}

	public String getDefaultTableName() {
		String overriddenColumnTable = this.getOverriddenColumnTable();
		return (overriddenColumnTable != null) ? overriddenColumnTable : this.getContainer().getDefaultTableName();
	}

	protected String getOverriddenColumnTable() {
		ReadOnlyColumn overriddenColumn = this.resolveOverriddenColumn();
		// pretty sure this is the *specified* table...
		return (overriddenColumn == null) ? null : overriddenColumn.getSpecifiedTable();
	}

	public String getDefaultColumnName() {
		String overriddenColumnName = this.getOverriddenColumnName();
		return (overriddenColumnName != null) ? overriddenColumnName : this.name;
	}

	protected String getOverriddenColumnName() {
		ReadOnlyColumn overriddenColumn = this.resolveOverriddenColumn();
		return (overriddenColumn == null) ? null : overriddenColumn.getName();
	}

	public Column resolveOverriddenColumn() {
		return this.getContainer().resolveOverriddenColumn(this.name);
	}
}
