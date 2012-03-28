/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.orm.OrmTableColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.AbstractOrmNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3;

/**
 * <code>orm.xml</code> tenant discriminator column
 */
public class EclipseLinkOrmTenantDiscriminatorColumn2_3
	extends AbstractOrmNamedDiscriminatorColumn<XmlTenantDiscriminatorColumn_2_3, OrmReadOnlyTenantDiscriminatorColumn2_3.Owner>
	implements OrmTenantDiscriminatorColumn2_3
{
	protected XmlTenantDiscriminatorColumn_2_3 xmlTenantDiscriminatorColumn;

	protected String specifiedTable;
	protected String defaultTable;

	protected String specifiedContextProperty;
	protected String defaultContextProperty;

	protected Boolean specifiedPrimaryKey;
	protected boolean defaultPrimaryKey = DEFAULT_PRIMARY_KEY;

	public EclipseLinkOrmTenantDiscriminatorColumn2_3(XmlContextNode parent, OrmReadOnlyTenantDiscriminatorColumn2_3.Owner owner, XmlTenantDiscriminatorColumn_2_3 column) {
		super(parent, owner, column);
		this.specifiedTable = this.buildSpecifiedTable();
		this.specifiedContextProperty = this.buildSpecifiedContextProperty();
		this.specifiedPrimaryKey = this.buildSpecifiedPrimaryKey();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedTable_(this.buildSpecifiedTable());
		this.setSpecifiedContextProperty_(this.buildSpecifiedContextProperty());
		this.setSpecifiedPrimaryKey_(this.buildSpecifiedPrimaryKey());
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultTable(this.buildDefaultTable());
		this.setDefaultContextProperty(this.buildDefaultContextProperty());
		this.setDefaultPrimaryKey(this.buildDefaultPrimaryKey());
	}



	// ********** XML column **********

	@Override
	public XmlTenantDiscriminatorColumn_2_3 getXmlColumn() {
		return this.xmlTenantDiscriminatorColumn;
	}

	@Override
	protected void setXmlColumn(XmlTenantDiscriminatorColumn_2_3 xmlColumn) {
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


	// ********** table **********

	@Override
	public String getTable() {
		return (this.specifiedTable != null) ? this.specifiedTable : this.defaultTable;
	}

	public String getSpecifiedTable() {
		return this.specifiedTable;
	}

	public void setSpecifiedTable(String table) {
		if (this.valuesAreDifferent(this.specifiedTable, table)) {
			this.setSpecifiedTable_(table);
			this.xmlTenantDiscriminatorColumn.setTable(table);
		}
	}

	protected void setSpecifiedTable_(String table) {
		String old = this.specifiedTable;
		this.specifiedTable = table;
		this.firePropertyChanged(SPECIFIED_TABLE_PROPERTY, old, table);
	}

	protected String buildSpecifiedTable() {
		return (this.xmlTenantDiscriminatorColumn == null) ? null : this.xmlTenantDiscriminatorColumn.getTable();
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
		return this.owner.tableNameIsInvalid(this.getTable());
	}

	public TextRange getTableTextRange() {
		return this.getValidationTextRange(this.getXmlColumnTableTextRange());
	}

	protected TextRange getXmlColumnTableTextRange() {
		XmlTenantDiscriminatorColumn_2_3 xmlColumn = this.getXmlColumn();
		return (xmlColumn == null) ? null : xmlColumn.getTableTextRange();
	}

	@Override
	protected NamedColumnTextRangeResolver buildTextRangeResolver() {
		return new OrmTableColumnTextRangeResolver(this);
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getXmlCompletionProposals(int pos) {
		Iterable<String> result = super.getXmlCompletionProposals(pos);
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
