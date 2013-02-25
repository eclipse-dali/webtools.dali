/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.AbstractOrmNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.context.ReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3;

/**
 * <code>orm.xml</code> tenant discriminator column
 */
public class EclipseLinkOrmTenantDiscriminatorColumn2_3
	extends AbstractOrmNamedDiscriminatorColumn<XmlTenantDiscriminatorColumn, ReadOnlyTenantDiscriminatorColumn2_3.Owner>
	implements OrmTenantDiscriminatorColumn2_3
{
	protected XmlTenantDiscriminatorColumn xmlTenantDiscriminatorColumn;

	protected String specifiedTableName;
	protected String defaultTableName;

	protected String specifiedContextProperty;
	protected String defaultContextProperty;

	protected Boolean specifiedPrimaryKey;
	protected boolean defaultPrimaryKey = DEFAULT_PRIMARY_KEY;

	public EclipseLinkOrmTenantDiscriminatorColumn2_3(JpaContextModel parent, ReadOnlyTenantDiscriminatorColumn2_3.Owner owner, XmlTenantDiscriminatorColumn column) {
		super(parent, owner, column);
		this.specifiedTableName = this.buildSpecifiedTableName();
		this.specifiedContextProperty = this.buildSpecifiedContextProperty();
		this.specifiedPrimaryKey = this.buildSpecifiedPrimaryKey();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedTableName_(this.buildSpecifiedTableName());
		this.setSpecifiedContextProperty_(this.buildSpecifiedContextProperty());
		this.setSpecifiedPrimaryKey_(this.buildSpecifiedPrimaryKey());
	}

	@Override
	public void update() {
		super.update();
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
	 * the owner's collection
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
		if (this.valuesAreDifferent(this.specifiedTableName, tableName)) {
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
		return this.owner.getDefaultTableName();
	}

	// ********** contextProperty **********

	public String getContextProperty() {
		return (this.specifiedContextProperty != null) ? this.specifiedContextProperty : this.defaultContextProperty;
	}

	public String getSpecifiedContextProperty() {
		return this.specifiedContextProperty;
	}

	public void setSpecifiedContextProperty(String contextProperty) {
		if (this.valuesAreDifferent(this.specifiedContextProperty, contextProperty)) {
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
		return this.owner.getDefaultContextPropertyName();
	}


	// ********** primaryKey **********

	public boolean isPrimaryKey() {
		return (this.specifiedPrimaryKey != null) ? this.specifiedPrimaryKey.booleanValue() : this.isDefaultPrimaryKey();
	}

	public Boolean getSpecifiedPrimaryKey() {
		return this.specifiedPrimaryKey;
	}

	public void setSpecifiedPrimaryKey(Boolean primaryKey) {
		if (this.valuesAreDifferent(this.specifiedPrimaryKey, primaryKey)) {
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
		return this.owner.getDefaultPrimaryKey();
	}


	// ********** misc **********

	public Iterable<String> getCandidateTableNames() {
		return this.owner.getCandidateTableNames();
	}


	// ********** validation **********

	public boolean tableNameIsInvalid() {
		return this.owner.tableNameIsInvalid(this.getTableName());
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
