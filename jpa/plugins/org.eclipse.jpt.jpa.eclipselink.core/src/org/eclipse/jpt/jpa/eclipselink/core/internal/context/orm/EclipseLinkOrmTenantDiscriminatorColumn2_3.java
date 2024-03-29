/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License 2.0, which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.AbstractOrmNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3;

/**
 * <code>orm.xml</code> tenant discriminator column
 */
public class EclipseLinkOrmTenantDiscriminatorColumn2_3
	extends AbstractOrmNamedDiscriminatorColumn<EclipseLinkTenantDiscriminatorColumn2_3.ParentAdapter, XmlTenantDiscriminatorColumn>
	implements EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3
{
	/** @see org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmNamedColumn#AbstractOrmNamedColumn(org.eclipse.jpt.jpa.core.context.NamedColumn.ParentAdapter, org.eclipse.jpt.jpa.core.resource.orm.XmlNamedColumn) */
	protected /* final */ XmlTenantDiscriminatorColumn xmlTenantDiscriminatorColumn;  // never null

	protected String specifiedTableName;
	protected String defaultTableName;

	protected String specifiedContextProperty;
	protected String defaultContextProperty;

	protected Boolean specifiedPrimaryKey;
	protected boolean defaultPrimaryKey = DEFAULT_PRIMARY_KEY;


	public EclipseLinkOrmTenantDiscriminatorColumn2_3(EclipseLinkTenantDiscriminatorColumn2_3.ParentAdapter parentAdapter, XmlTenantDiscriminatorColumn xmlColumn) {
		super(parentAdapter, xmlColumn);
		this.specifiedTableName = this.buildSpecifiedTableName();
		this.specifiedContextProperty = this.buildSpecifiedContextProperty();
		this.specifiedPrimaryKey = this.buildSpecifiedPrimaryKey();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedTableName_(this.buildSpecifiedTableName());
		this.setSpecifiedContextProperty_(this.buildSpecifiedContextProperty());
		this.setSpecifiedPrimaryKey_(this.buildSpecifiedPrimaryKey());
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultTableName(this.buildDefaultTableName());
		this.setDefaultContextProperty(this.buildDefaultContextProperty());
		this.setDefaultPrimaryKey(this.buildDefaultPrimaryKey());
	}



	// ********** XML column **********

	@Override
	public XmlTenantDiscriminatorColumn getXmlColumn() {
		return this.xmlTenantDiscriminatorColumn;
	}

	@Override
	protected void setXmlColumn(XmlTenantDiscriminatorColumn xmlColumn) {
		this.xmlTenantDiscriminatorColumn = xmlColumn;
	}

	/**
	 * tenant discriminator columns are part of a collection;
	 * the 'tenant-discriminator-column' element will be removed/added
	 * when the XML tenant discriminator column is removed from/added to
	 * the parent's collection
	 */
	@Override
	protected XmlTenantDiscriminatorColumn buildXmlColumn() {
		throw new IllegalStateException("XML tenant discriminator column is missing"); //$NON-NLS-1$
	}

	/**
	 * @see #buildXmlColumn()
	 */
	@Override
	protected void removeXmlColumn() {
		// do nothing
	}


	// ********** table name **********

	@Override
	public String getTableName() {
		return (this.specifiedTableName != null) ? this.specifiedTableName : this.defaultTableName;
	}

	public String getSpecifiedTableName() {
		return this.specifiedTableName;
	}

	public void setSpecifiedTableName(String tableName) {
		if (ObjectTools.notEquals(this.specifiedTableName, tableName)) {
			this.setSpecifiedTableName_(tableName);
			this.xmlTenantDiscriminatorColumn.setTable(tableName);
		}
	}

	protected void setSpecifiedTableName_(String tableName) {
		String old = this.specifiedTableName;
		this.specifiedTableName = tableName;
		this.firePropertyChanged(SPECIFIED_TABLE_NAME_PROPERTY, old, tableName);
	}

	protected String buildSpecifiedTableName() {
		return (this.xmlTenantDiscriminatorColumn == null) ? null : this.xmlTenantDiscriminatorColumn.getTable();
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

	// ********** contextProperty **********

	public String getContextProperty() {
		return (this.specifiedContextProperty != null) ? this.specifiedContextProperty : this.defaultContextProperty;
	}

	public String getSpecifiedContextProperty() {
		return this.specifiedContextProperty;
	}

	public void setSpecifiedContextProperty(String contextProperty) {
		if (ObjectTools.notEquals(this.specifiedContextProperty, contextProperty)) {
			this.setSpecifiedContextProperty_(contextProperty);
			this.xmlTenantDiscriminatorColumn.setContextProperty(contextProperty);
		}
	}

	protected void setSpecifiedContextProperty_(String contextProperty) {
		String old = this.specifiedContextProperty;
		this.specifiedContextProperty = contextProperty;
		this.firePropertyChanged(SPECIFIED_CONTEXT_PROPERTY_PROPERTY, old, contextProperty);
	}

	protected String buildSpecifiedContextProperty() {
		return (this.xmlTenantDiscriminatorColumn == null) ? null : this.xmlTenantDiscriminatorColumn.getContextProperty();
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


	// ********** primaryKey **********

	public boolean isPrimaryKey() {
		return (this.specifiedPrimaryKey != null) ? this.specifiedPrimaryKey.booleanValue() : this.isDefaultPrimaryKey();
	}

	public Boolean getSpecifiedPrimaryKey() {
		return this.specifiedPrimaryKey;
	}

	public void setSpecifiedPrimaryKey(Boolean primaryKey) {
		if (ObjectTools.notEquals(this.specifiedPrimaryKey, primaryKey)) {
			this.setSpecifiedPrimaryKey_(primaryKey);
			this.xmlTenantDiscriminatorColumn.setPrimaryKey(primaryKey);
		}
	}

	protected void setSpecifiedPrimaryKey_(Boolean primaryKey) {
		Boolean old = this.specifiedPrimaryKey;
		this.specifiedPrimaryKey = primaryKey;
		this.firePropertyChanged(SPECIFIED_PRIMARY_KEY_PROPERTY, old, primaryKey);
	}

	protected Boolean buildSpecifiedPrimaryKey() {
		return (this.xmlTenantDiscriminatorColumn == null) ? null : this.xmlTenantDiscriminatorColumn.getPrimaryKey();
	}

	public boolean isDefaultPrimaryKey() {
		return this.defaultPrimaryKey;
	}

	protected void setDefaultPrimaryKey(boolean defaultPrimaryKey) {
		boolean old = this.defaultPrimaryKey;
		this.defaultPrimaryKey = defaultPrimaryKey;
		this.firePropertyChanged(DEFAULT_PRIMARY_KEY_PROPERTY, old, defaultPrimaryKey);
	}

	protected boolean buildDefaultPrimaryKey() {
		return this.parentAdapter.getDefaultPrimaryKey();
	}


	// ********** misc **********

	public Iterable<String> getCandidateTableNames() {
		return this.parentAdapter.getCandidateTableNames();
	}


	// ********** validation **********

	public boolean tableNameIsInvalid() {
		return this.parentAdapter.tableNameIsInvalid(this.getTableName());
	}

	public TextRange getTableNameValidationTextRange() {
		return this.getValidationTextRange(this.getXmlColumnTableTextRange());
	}

	protected TextRange getXmlColumnTableTextRange() {
		XmlTenantDiscriminatorColumn_2_3 xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getTableTextRange();
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.tableTouches(pos)) {
			return this.getCandidateTableNames();
		}
		return null;
	}

	protected boolean tableTouches(int pos) {
		return this.xmlTenantDiscriminatorColumn.tableTouches(pos);
	}
}
