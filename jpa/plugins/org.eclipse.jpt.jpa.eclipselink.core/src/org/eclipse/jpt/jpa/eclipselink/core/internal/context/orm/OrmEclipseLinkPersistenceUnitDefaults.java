/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.AbstractOrmPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.context.orm.EclipseLinkOrmTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.ReadOnlyTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.TenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm.OrmReadOnlyTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm.OrmTenantDiscriminatorColumn;

/**
 * EclipseLink <code>orm.xml</code> file
 * <br>
 * <code>persistence-unit-defaults</code> element
 */
public class OrmEclipseLinkPersistenceUnitDefaults
	extends AbstractOrmPersistenceUnitDefaults
{

	protected final ContextListContainer<OrmTenantDiscriminatorColumn, XmlTenantDiscriminatorColumn_2_3> tenantDiscriminatorColumnContainer;
	protected final OrmReadOnlyTenantDiscriminatorColumn.Owner tenantDiscriminatorColumnOwner;
		public static final String TENANT_DISCRIMINATOR_COLUMNS_LIST = "tenantDiscriminatorColumns"; //$NON-NLS-1$

	// ********** constructor/initialization **********

	public OrmEclipseLinkPersistenceUnitDefaults(OrmPersistenceUnitMetadata parent) {
		super(parent);
		this.tenantDiscriminatorColumnOwner = this.buildTenantDiscriminatorColumnOwner();
		this.tenantDiscriminatorColumnContainer = new TenantDiscriminatorColumnContainer();
	}

	@Override
	protected XmlPersistenceUnitDefaults buildXmlPersistenceUnitDefaults() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults();
	}

	@Override
	protected XmlPersistenceUnitDefaults getXmlDefaults() {
		return (XmlPersistenceUnitDefaults) super.getXmlDefaults();
	}

	@Override
	protected XmlPersistenceUnitDefaults getXmlDefaultsForUpdate() {
		return (XmlPersistenceUnitDefaults) super.getXmlDefaultsForUpdate();
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncTenantDiscriminatorColumns();
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getTenantDiscriminatorColumns());
	}


	// ********** tenant discriminator columns **********

	public ListIterable<OrmTenantDiscriminatorColumn> getTenantDiscriminatorColumns() {
		return this.tenantDiscriminatorColumnContainer.getContextElements();
	}

	public int getTenantDiscriminatorColumnsSize() {
		return this.tenantDiscriminatorColumnContainer.getContextElementsSize();
	}

	public boolean hasTenantDiscriminatorColumns() {
		return this.getTenantDiscriminatorColumnsSize() != 0;
	}

	public OrmTenantDiscriminatorColumn getTenantDiscriminatorColumn(int index) {
		return this.tenantDiscriminatorColumnContainer.getContextElement(index);
	}

	public OrmTenantDiscriminatorColumn addTenantDiscriminatorColumn() {
		return this.addTenantDiscriminatorColumn(this.getTenantDiscriminatorColumnsSize());
	}

	public OrmTenantDiscriminatorColumn addTenantDiscriminatorColumn(int index) {
		XmlTenantDiscriminatorColumn xmlJoinColumn = this.buildXmlTenantDiscriminatorColumn();
		OrmTenantDiscriminatorColumn joinColumn = this.tenantDiscriminatorColumnContainer.addContextElement(index, xmlJoinColumn);
		this.getXmlDefaultsForUpdate().getTenantDiscriminatorColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	protected XmlTenantDiscriminatorColumn buildXmlTenantDiscriminatorColumn() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlTenantDiscriminatorColumn();
	}

	public void removeTenantDiscriminatorColumn(TenantDiscriminatorColumn tenantDiscriminatorColumn) {
		this.removeTenantDiscriminatorColumn(this.tenantDiscriminatorColumnContainer.indexOfContextElement((OrmTenantDiscriminatorColumn) tenantDiscriminatorColumn));
	}

	public void removeTenantDiscriminatorColumn(int index) {
		this.tenantDiscriminatorColumnContainer.removeContextElement(index);
		this.getXmlDefaults().getTenantDiscriminatorColumns().remove(index);
	}

	public void moveTenantDiscriminatorColumn(int targetIndex, int sourceIndex) {
		this.tenantDiscriminatorColumnContainer.moveContextElement(targetIndex, sourceIndex);
		this.getXmlDefaults().getTenantDiscriminatorColumns().move(targetIndex, sourceIndex);
	}


	protected void syncTenantDiscriminatorColumns() {
		this.tenantDiscriminatorColumnContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlTenantDiscriminatorColumn_2_3> getXmlTenantDiscriminatorColumns() {
		if (getXmlDefaults() == null) {
			return EmptyListIterable.instance();
		}
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlTenantDiscriminatorColumn_2_3>(this.getXmlDefaults().getTenantDiscriminatorColumns());
	}

	/**
	 *  tenant discriminator column container
	 */
	protected class TenantDiscriminatorColumnContainer
		extends ContextListContainer<OrmTenantDiscriminatorColumn, XmlTenantDiscriminatorColumn_2_3>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}
		@Override
		protected OrmTenantDiscriminatorColumn buildContextElement(XmlTenantDiscriminatorColumn_2_3 resourceElement) {
			return OrmEclipseLinkPersistenceUnitDefaults.this.buildTenantDiscriminatorColumn(resourceElement);
		}
		@Override
		protected ListIterable<XmlTenantDiscriminatorColumn_2_3> getResourceElements() {
			return OrmEclipseLinkPersistenceUnitDefaults.this.getXmlTenantDiscriminatorColumns();
		}
		@Override
		protected XmlTenantDiscriminatorColumn_2_3 getResourceElement(OrmTenantDiscriminatorColumn contextElement) {
			return contextElement.getXmlColumn();
		}
	}

	protected OrmReadOnlyTenantDiscriminatorColumn.Owner buildTenantDiscriminatorColumnOwner() {
		return new TenantDiscriminatorColumnOwner();
	}

	protected OrmTenantDiscriminatorColumn buildTenantDiscriminatorColumn(XmlTenantDiscriminatorColumn_2_3 xmlTenantDiscriminatorColumn) {
		return new EclipseLinkOrmTenantDiscriminatorColumn(this, this.tenantDiscriminatorColumnOwner, xmlTenantDiscriminatorColumn);
	}


	// ********** OrmReadOnlyTenantDiscriminatorColumn.Owner implementation **********

	protected class TenantDiscriminatorColumnOwner 
		implements OrmReadOnlyTenantDiscriminatorColumn.Owner
	{

		public String getDefaultContextPropertyName() {
			return ReadOnlyTenantDiscriminatorColumn.DEFAULT_CONTEXT_PROPERTY;
		}

		public boolean getDefaultPrimaryKey() {
			return ReadOnlyTenantDiscriminatorColumn.DEFAULT_PRIMARY_KEY;
		}

		public int getDefaultLength() {
			return ReadOnlyNamedDiscriminatorColumn.DEFAULT_LENGTH;
		}

		public DiscriminatorType getDefaultDiscriminatorType() {
			return ReadOnlyNamedDiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE;
		}

		/**
		 * No default table name in the context of the persistence unit defaults
		 */
		public String getDefaultTableName() {
			return null;
		}

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return ReadOnlyTenantDiscriminatorColumn.DEFAULT_NAME;
		}

		/**
		 * No table in the context of the persistence unit defaults
		 */
		public Table resolveDbTable(String tableName) {
			return null;
		}

		public Iterable<String> getCandidateTableNames() {
			return EmptyIterable.instance();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return false;
		}

		/**
		 * no column validation can be done in the context of the persistence unit defaults
		 */
		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return JptValidator.Null.instance();
		}

		public TextRange getValidationTextRange() {
			return OrmEclipseLinkPersistenceUnitDefaults.this.getValidationTextRange();
		}
	}
}
