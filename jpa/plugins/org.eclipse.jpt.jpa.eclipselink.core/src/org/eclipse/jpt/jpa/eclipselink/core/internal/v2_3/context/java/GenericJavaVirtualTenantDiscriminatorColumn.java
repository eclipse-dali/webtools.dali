/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaVirtualNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaTableColumnTextRangeResolver;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.ReadOnlyTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java.JavaReadOnlyTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java.JavaVirtualTenantDiscriminatorColumn;

/**
 * Java virtual tenant discriminator column
 */
public class GenericJavaVirtualTenantDiscriminatorColumn
	extends AbstractJavaVirtualNamedDiscriminatorColumn<JavaReadOnlyTenantDiscriminatorColumn.Owner, ReadOnlyTenantDiscriminatorColumn>
	implements JavaVirtualTenantDiscriminatorColumn
{
	protected final ReadOnlyTenantDiscriminatorColumn overriddenColumn;

	protected String specifiedTable;
	protected String defaultTable;

	protected String specifiedContextProperty;
	protected String defaultContextProperty;

	protected Boolean specifiedPrimaryKey;
	protected boolean defaultPrimaryKey;


	public GenericJavaVirtualTenantDiscriminatorColumn(JavaJpaContextNode parent, JavaReadOnlyTenantDiscriminatorColumn.Owner owner, ReadOnlyTenantDiscriminatorColumn overriddenColumn) {
		super(parent, owner);
		this.overriddenColumn = overriddenColumn;
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();

		this.setSpecifiedTable(this.buildSpecifiedTable());
		this.setDefaultTable(this.buildDefaultTable());

		this.setSpecifiedContextProperty(this.buildSpecifiedContextProperty());
		this.setDefaultContextProperty(this.buildDefaultContextProperty());

		this.setSpecifiedPrimaryKey(this.buildSpecifiedPrimaryKey());
		this.setDefaultPrimaryKey(this.buildDefaultPrimaryKey());
	}


	// ********** column **********

	@Override
	public ReadOnlyTenantDiscriminatorColumn getOverriddenColumn() {
		return this.overriddenColumn;
	}

	public boolean isVirtual() {
		return true;
	}


	// ********** table **********

	@Override
	public String getTable() {
		return (this.specifiedTable != null) ? this.specifiedTable : this.defaultTable;
	}

	public String getSpecifiedTable() {
		return this.specifiedTable;
	}

	protected void setSpecifiedTable(String table) {
		String old = this.specifiedTable;
		this.specifiedTable = table;
		this.firePropertyChanged(SPECIFIED_TABLE_PROPERTY, old, table);
	}

	protected String buildSpecifiedTable() {
		return this.getOverriddenColumn().getSpecifiedTable();
	}

	public String getDefaultTable() {
		return this.defaultTable;
	}

	protected void setDefaultTable(String table) {
		String old = this.defaultTable;
		this.defaultTable = table;
		this.firePropertyChanged(DEFAULT_TABLE_PROPERTY, old, table);
	}

	protected String buildDefaultTable() {
		return this.owner.getDefaultTableName();
	}


	// ********** context property **********

	public String getContextProperty() {
		return (this.specifiedContextProperty != null) ? this.specifiedContextProperty : this.defaultContextProperty;
	}

	public String getSpecifiedContextProperty() {
		return this.specifiedContextProperty;
	}

	protected void setSpecifiedContextProperty(String contextProperty) {
		String old = this.specifiedContextProperty;
		this.specifiedContextProperty = contextProperty;
		this.firePropertyChanged(SPECIFIED_CONTEXT_PROPERTY_PROPERTY, old, contextProperty);
	}

	protected String buildSpecifiedContextProperty() {
		return this.getOverriddenColumn().getSpecifiedContextProperty();
	}

	public String getDefaultContextProperty() {
		return this.defaultContextProperty;
	}

	protected void setDefaultContextProperty(String contextProperty) {
		String old = this.defaultContextProperty;
		this.defaultContextProperty = contextProperty;
		this.firePropertyChanged(DEFAULT_CONTEXT_PROPERTY_PROPERTY, old, contextProperty);
	}

	protected String buildDefaultContextProperty() {
		return this.owner.getDefaultContextPropertyName();
	}


	// ********** primary key **********

	public boolean isPrimaryKey() {
		return (this.specifiedPrimaryKey != null) ? this.specifiedPrimaryKey.booleanValue() : this.defaultPrimaryKey;
	}

	public Boolean getSpecifiedPrimaryKey() {
		return this.specifiedPrimaryKey;
	}

	protected void setSpecifiedPrimaryKey(Boolean primaryKey) {
		Boolean old = this.specifiedPrimaryKey;
		this.specifiedPrimaryKey = primaryKey;
		this.firePropertyChanged(SPECIFIED_PRIMARY_KEY_PROPERTY, old, primaryKey);
	}

	protected Boolean buildSpecifiedPrimaryKey() {
		return this.getOverriddenColumn().getSpecifiedPrimaryKey();
	}

	public boolean isDefaultPrimaryKey() {
		return this.defaultPrimaryKey;
	}

	protected void setDefaultPrimaryKey(boolean primaryKey) {
		boolean old = this.defaultPrimaryKey;
		this.defaultPrimaryKey = primaryKey;
		this.firePropertyChanged(DEFAULT_PRIMARY_KEY_PROPERTY, old, primaryKey);
	}

	protected boolean buildDefaultPrimaryKey() {
		return this.owner.getDefaultPrimaryKey();
	}

	// ********** misc **********

	public boolean tableNameIsInvalid() {
		return this.owner.tableNameIsInvalid(this.getTable());
	}

	public Iterable<String> getCandidateTableNames() {
		return this.owner.getCandidateTableNames();
	}


	// ********** validation **********

	public TextRange getTableTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(astRoot);
	}

	@Override
	protected NamedColumnTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaTableColumnTextRangeResolver(this, astRoot);
	}
}
