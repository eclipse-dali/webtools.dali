/*******************************************************************************
 * Copyright (c) 2011, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmVirtualNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkVirtualTenantDiscriminatorColumn2_3;

/**
 * <code>orm.xml</code> virtual tenant discriminator column
 */
public class EclipseLinkOrmVirtualTenantDiscriminatorColumn2_3
	extends AbstractOrmVirtualNamedDiscriminatorColumn<EclipseLinkTenantDiscriminatorColumn2_3.ParentAdapter, EclipseLinkTenantDiscriminatorColumn2_3>
	implements EclipseLinkVirtualTenantDiscriminatorColumn2_3
{
	protected final EclipseLinkTenantDiscriminatorColumn2_3 overriddenColumn;

	protected String specifiedTableName;
	protected String defaultTableName;

	protected String specifiedContextProperty;
	protected String defaultContextProperty;

	protected Boolean specifiedPrimaryKey;
	protected boolean defaultPrimaryKey;


	public EclipseLinkOrmVirtualTenantDiscriminatorColumn2_3(EclipseLinkTenantDiscriminatorColumn2_3.ParentAdapter parentAdapter, EclipseLinkTenantDiscriminatorColumn2_3 overriddenColumn) {
		super(parentAdapter);
		this.overriddenColumn = overriddenColumn;
	}


	// ********** synchronize/update **********

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);

		this.setSpecifiedTableName(this.buildSpecifiedTableName());
		this.setDefaultTableName(this.buildDefaultTableName());

		this.setSpecifiedContextProperty(this.buildSpecifiedContextProperty());
		this.setDefaultContextProperty(this.buildDefaultContextProperty());

		this.setSpecifiedPrimaryKey(this.buildSpecifiedPrimaryKey());
		this.setDefaultPrimaryKey(this.buildDefaultPrimaryKey());
	}


	// ********** column **********

	@Override
	public EclipseLinkTenantDiscriminatorColumn2_3 getOverriddenColumn() {
		return this.overriddenColumn;
	}


	// ********** table name **********

	public String getTableName() {
		return (this.specifiedTableName != null) ? this.specifiedTableName : this.defaultTableName;
	}

	public String getSpecifiedTableName() {
		return this.specifiedTableName;
	}

	protected void setSpecifiedTableName(String tableName) {
		String old = this.specifiedTableName;
		this.specifiedTableName = tableName;
		this.firePropertyChanged(SPECIFIED_TABLE_NAME_PROPERTY, old, tableName);
	}

	protected String buildSpecifiedTableName() {
		return this.getOverriddenColumn().getSpecifiedTableName();
	}

	public String getDefaultTableName() {
		return this.defaultTableName;
	}

	protected void setDefaultTableName(String tableName) {
		String old = this.defaultTableName;
		this.defaultTableName = tableName;
		this.firePropertyChanged(DEFAULT_TABLE_NAME_PROPERTY, old, tableName);
	}

	protected String buildDefaultTableName() {
		return this.parentAdapter.getDefaultTableName();
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
		return this.parentAdapter.getDefaultContextPropertyName();
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
		return this.parentAdapter.getDefaultPrimaryKey();
	}


	// ********** misc **********

	public boolean tableNameIsInvalid() {
		return this.parentAdapter.tableNameIsInvalid(this.getTableName());
	}

	public Iterable<String> getCandidateTableNames() {
		return this.parentAdapter.getCandidateTableNames();
	}


	// ********** validation **********

	public TextRange getTableNameValidationTextRange() {
		return this.getValidationTextRange();
	}
}
