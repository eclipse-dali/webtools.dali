/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.context.orm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.context.TenantDiscriminatorColumnValidator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMultitenant;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlMultitenantHolder;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlMultitenant_2_4;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.EclipseLinkMultitenantType;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.ReadOnlyTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.TenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java.JavaEclipseLinkMultitenancy;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.java.JavaTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm.OrmEclipseLinkMultitenancy;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm.OrmReadOnlyTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm.OrmTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm.OrmVirtualTenantDiscriminatorColumn;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkMultitenancyImpl
	extends AbstractOrmXmlContextNode
	implements OrmEclipseLinkMultitenancy
{
	protected boolean specifiedMultitenant;
	protected boolean defaultMultitenant;

	protected EclipseLinkMultitenantType specifiedType;
	protected EclipseLinkMultitenantType defaultType;

	protected Boolean specifiedIncludeCriteria;
	protected boolean defaultIncludeCriteria;

	protected final ContextListContainer<OrmTenantDiscriminatorColumn, XmlTenantDiscriminatorColumn_2_3> specifiedTenantDiscriminatorColumnContainer;
	protected final OrmReadOnlyTenantDiscriminatorColumn.Owner tenantDiscriminatorColumnOwner;

	protected final ContextListContainer<OrmVirtualTenantDiscriminatorColumn, ReadOnlyTenantDiscriminatorColumn> defaultTenantDiscriminatorColumnContainer;

	protected final OrmReadOnlyTenantDiscriminatorColumn defaultTenantDiscriminatorColumn;

	public OrmEclipseLinkMultitenancyImpl(OrmEclipseLinkNonEmbeddableTypeMapping parent) {
		super(parent);

		this.specifiedMultitenant = isMultitenantElementSpecified();
		this.specifiedType = this.buildSpecifiedType();
		this.specifiedIncludeCriteria = this.buildSpecifiedIncludeCriteria();
		this.tenantDiscriminatorColumnOwner = this.buildTenantDiscriminatorColumnOwner();
		this.specifiedTenantDiscriminatorColumnContainer = new SpecifiedTenantDiscriminatorColumnContainer();
		this.defaultTenantDiscriminatorColumn = this.buildTenantDiscriminatorColumn(null);
		this.defaultTenantDiscriminatorColumnContainer = new DefaultTenantDiscriminatorColumnContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedMultitenant_(isMultitenantElementSpecified());
		this.setSpecifiedType_(this.buildSpecifiedType());
		this.setSpecifiedIncludeCriteria_(this.buildSpecifiedIncludeCriteria());
		this.syncSpecifiedTenantDiscriminatorColumns();
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getSpecifiedTenantDiscriminatorColumns());

		boolean xmlMultitenantNotSpecified = (this.getXmlMultitenant() == null);
		JavaEclipseLinkMultitenancy javaMultitenantPolicy = this.getJavaMultitenancyPolicyForDefaults();
		boolean javaMultitenantPolicySpecified = javaMultitenantPolicy != null;
		boolean useJavaValue = (xmlMultitenantNotSpecified && javaMultitenantPolicySpecified);

		this.setDefaultMultitenant(useJavaValue ? javaMultitenantPolicy.isMultitenant() : this.buildDefaultMultitenant());
		this.setDefaultType(useJavaValue ? javaMultitenantPolicy.getType() : this.buildDefaultType());
		this.setDefaultIncludeCriteria(useJavaValue ? javaMultitenantPolicy.isIncludeCriteria() : DEFAULT_INCLUDE_CRITERIA);
		this.updateDefaultTenantDiscriminatorColumns();
	}


	// ********** multitenant **********

	public boolean isMultitenant() {
		return isSpecifiedMultitenant() || isDefaultMultitenant();
	}

	public boolean isDefaultMultitenant() {
		return this.defaultMultitenant;
	}

	public void setDefaultMultitenant(boolean defaultMultitenant) {
		boolean old = this.defaultMultitenant;
		this.defaultMultitenant = defaultMultitenant;
		this.firePropertyChanged(DEFAULT_MULTITENANT_PROPERTY, old, defaultMultitenant);
	}

	protected boolean buildDefaultMultitenant() {
		return this.isMultitenantInheritanceHierarchy() || this.isSuperMappedSuperclassMultitenant();
	}

	public boolean isSpecifiedMultitenant() {
		return this.specifiedMultitenant;
	}

	public void setSpecifiedMultitenant(boolean isMultitenant) {
		if (isMultitenant() && isMultitenant) {
			return;
		}
		if (!isMultitenant() && !isMultitenant) {
			return;
		}
		if (isMultitenant) {
			this.addXmlMultitenant();
		}
		else {
			this.removeXmlMultitenant();
		}
		this.setSpecifiedMultitenant_(isMultitenant);
	}

	protected void setSpecifiedMultitenant_(boolean isMultitenant) {
		boolean old = this.specifiedMultitenant;
		this.specifiedMultitenant = isMultitenant;
		this.firePropertyChanged(SPECIFIED_MULTITENANT_PROPERTY, old, isMultitenant);
	}


	// ********** type **********

	public EclipseLinkMultitenantType getType() {
		return (this.specifiedType != null) ? this.specifiedType : this.getDefaultType();
	}

	public EclipseLinkMultitenantType getSpecifiedType() {
		return this.specifiedType;
	}

	public void setSpecifiedType(EclipseLinkMultitenantType type) {
		if (this.valuesAreDifferent(this.specifiedType, type)) {
			XmlMultitenant xmlMultitenant = this.getXmlMultitenantForUpdate();
			this.setSpecifiedType_(type);
			xmlMultitenant.setType(EclipseLinkMultitenantType.toOrmResourceModel(type));
			if (getType() != EclipseLinkMultitenantType.SINGLE_TABLE) {
				this.specifiedTenantDiscriminatorColumnContainer.clearContextList();
			}
		}
	}

	protected void setSpecifiedType_(EclipseLinkMultitenantType type) {
		EclipseLinkMultitenantType old = this.specifiedType;
		this.specifiedType = type;
		this.firePropertyChanged(SPECIFIED_TYPE_PROPERTY, old, type);
	}

	protected EclipseLinkMultitenantType buildSpecifiedType() {
		XmlMultitenant xmlMultitenant = this.getXmlMultitenant();
		return (xmlMultitenant == null) ? null : EclipseLinkMultitenantType.fromOrmResourceModel(xmlMultitenant.getType());
	}

	public EclipseLinkMultitenantType getDefaultType() {
		return this.defaultType;
	}

	protected void setDefaultType(EclipseLinkMultitenantType type) {
		EclipseLinkMultitenantType old = this.defaultType;
		this.defaultType = type;
		this.firePropertyChanged(DEFAULT_TYPE_PROPERTY, old, type);
	}

	protected EclipseLinkMultitenantType buildDefaultType() {
		if (!isMultitenant()) {
			return null;
		}
		if (isSpecifiedMultitenant()) {
			return DEFAULT_TYPE;
		}
		if (this.isMultitenantInheritanceHierarchy()) {
			return getRootEntity().getMultitenancy().getType();
		}
		EclipseLinkMappedSuperclass superMappedSuperclass = getSuperMappedSuperclass();
		return superMappedSuperclass != null ? superMappedSuperclass.getMultitenancy().getType() : DEFAULT_TYPE;
	}


	// ********** include criteria **********

	public boolean isIncludeCriteria() {
		return (this.specifiedIncludeCriteria != null) ? this.specifiedIncludeCriteria.booleanValue() : this.defaultIncludeCriteria;
	}

	public Boolean getSpecifiedIncludeCriteria() {
		return this.specifiedIncludeCriteria;
	}

	public void setSpecifiedIncludeCriteria(Boolean includeCriteria) {
		if (this.valuesAreDifferent(this.specifiedIncludeCriteria, includeCriteria)) {
			XmlMultitenant xmlMultitenant = this.getXmlMultitenantForUpdate();
			this.setSpecifiedIncludeCriteria_(includeCriteria);
			xmlMultitenant.setIncludeCriteria(includeCriteria);
		}
	}

	protected void setSpecifiedIncludeCriteria_(Boolean includeCriteria) {
		Boolean old = this.specifiedIncludeCriteria;
		this.specifiedIncludeCriteria = includeCriteria;
		this.firePropertyChanged(SPECIFIED_INCLUDE_CRITERIA_PROPERTY, old, includeCriteria);
	}

	protected Boolean buildSpecifiedIncludeCriteria() {
		XmlMultitenant xmlMultitenant = this.getXmlMultitenant();
		return (xmlMultitenant == null) ? null : xmlMultitenant.getIncludeCriteria();
	}


	public boolean isDefaultIncludeCriteria() {
		return this.defaultIncludeCriteria;
	}

	protected void setDefaultIncludeCriteria(boolean includeCriteria) {
		boolean old = this.defaultIncludeCriteria;
		this.defaultIncludeCriteria = includeCriteria;
		this.firePropertyChanged(DEFAULT_INCLUDE_CRITERIA_PROPERTY, old, includeCriteria);
	}


	// ********** tenant discriminator columns **********

	public ListIterable<OrmReadOnlyTenantDiscriminatorColumn> getTenantDiscriminatorColumns() {
		return this.hasSpecifiedTenantDiscriminatorColumns() ?
			this.getReadOnlySpecifiedTenantDiscriminatorColumns() : 
			this.getReadOnlyDefaultTenantDiscriminatorColumns();
	}

	public int getTenantDiscriminatorColumnsSize() {
		return this.hasSpecifiedTenantDiscriminatorColumns() ?
			this.getSpecifiedTenantDiscriminatorColumnsSize() : 
			this.getDefaultTenantDiscriminatorColumnsSize();
	}

	// ********** specified tenant discriminator columns **********

	public ListIterable<OrmTenantDiscriminatorColumn> getSpecifiedTenantDiscriminatorColumns() {
		return this.specifiedTenantDiscriminatorColumnContainer.getContextElements();
	}

	protected ListIterable<OrmReadOnlyTenantDiscriminatorColumn> getReadOnlySpecifiedTenantDiscriminatorColumns() {
		return new SuperListIterableWrapper<OrmReadOnlyTenantDiscriminatorColumn>(this.getSpecifiedTenantDiscriminatorColumns());
	}

	public int getSpecifiedTenantDiscriminatorColumnsSize() {
		return this.specifiedTenantDiscriminatorColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedTenantDiscriminatorColumns() {
		return this.getSpecifiedTenantDiscriminatorColumnsSize() != 0;
	}

	public OrmTenantDiscriminatorColumn addSpecifiedTenantDiscriminatorColumn() {
		return this.addSpecifiedTenantDiscriminatorColumn(this.getSpecifiedTenantDiscriminatorColumnsSize());
	}

	public OrmTenantDiscriminatorColumn addSpecifiedTenantDiscriminatorColumn(int index) {
		XmlTenantDiscriminatorColumn xmlJoinColumn = this.buildXmlTenantDiscriminatorColumn();
		OrmTenantDiscriminatorColumn joinColumn = this.specifiedTenantDiscriminatorColumnContainer.addContextElement(index, xmlJoinColumn);
		this.getXmlMultitenantForUpdate().getTenantDiscriminatorColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	protected XmlTenantDiscriminatorColumn buildXmlTenantDiscriminatorColumn() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlTenantDiscriminatorColumn();
	}

	public void removeSpecifiedTenantDiscriminatorColumn(TenantDiscriminatorColumn tenantDiscriminatorColumn) {
		this.removeSpecifiedTenantDiscriminatorColumn(this.specifiedTenantDiscriminatorColumnContainer.indexOfContextElement((OrmTenantDiscriminatorColumn) tenantDiscriminatorColumn));
	}

	public void removeSpecifiedTenantDiscriminatorColumn(int index) {
		this.specifiedTenantDiscriminatorColumnContainer.removeContextElement(index);
		this.getXmlMultitenant().getTenantDiscriminatorColumns().remove(index);
	}

	public void moveSpecifiedTenantDiscriminatorColumn(int targetIndex, int sourceIndex) {
		this.specifiedTenantDiscriminatorColumnContainer.moveContextElement(targetIndex, sourceIndex);
		this.getXmlMultitenant().getTenantDiscriminatorColumns().move(targetIndex, sourceIndex);
	}


	protected void syncSpecifiedTenantDiscriminatorColumns() {
		this.specifiedTenantDiscriminatorColumnContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlTenantDiscriminatorColumn_2_3> getXmlTenantDiscriminatorColumns() {
		if (getXmlMultitenant() == null) {
			return EmptyListIterable.instance();
		}
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlTenantDiscriminatorColumn_2_3>(this.getXmlMultitenant().getTenantDiscriminatorColumns());
	}

	/**
	 *  specified tenant discriminator column container
	 */
	protected class SpecifiedTenantDiscriminatorColumnContainer
		extends ContextListContainer<OrmTenantDiscriminatorColumn, XmlTenantDiscriminatorColumn_2_3>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}
		@Override
		protected OrmTenantDiscriminatorColumn buildContextElement(XmlTenantDiscriminatorColumn_2_3 resourceElement) {
			return OrmEclipseLinkMultitenancyImpl.this.buildTenantDiscriminatorColumn(resourceElement);
		}
		@Override
		protected ListIterable<XmlTenantDiscriminatorColumn_2_3> getResourceElements() {
			return OrmEclipseLinkMultitenancyImpl.this.getXmlTenantDiscriminatorColumns();
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


	// ********** default tenant discriminator columns **********

	public ListIterable<OrmVirtualTenantDiscriminatorColumn> getDefaultTenantDiscriminatorColumns() {
		return this.defaultTenantDiscriminatorColumnContainer.getContextElements();
	}

	protected ListIterable<OrmReadOnlyTenantDiscriminatorColumn> getReadOnlyDefaultTenantDiscriminatorColumns() {
		return new SuperListIterableWrapper<OrmReadOnlyTenantDiscriminatorColumn>(this.getDefaultTenantDiscriminatorColumns());
	}

	public int getDefaultTenantDiscriminatorColumnsSize() {
		return this.defaultTenantDiscriminatorColumnContainer.getContextElementsSize();
	}

	protected void clearDefaultTenantDiscriminatorColumns() {
		this.defaultTenantDiscriminatorColumnContainer.clearContextList();
	}

	/**
	 * If there are any specified tenant discriminator columns, then there are no default
	 * tenant discriminator columns.
	 * If there are Java specified tenant discriminator columns, then those are the default
	 * tenant discriminator columns.
	 * @see #getTenantDiscriminatorColumnsForDefaults()
	 */
	protected void updateDefaultTenantDiscriminatorColumns() {
		this.defaultTenantDiscriminatorColumnContainer.update();
	}

	protected ListIterable<ReadOnlyTenantDiscriminatorColumn> getTenantDiscriminatorColumnsForDefaults() {
		if (this.getType() != EclipseLinkMultitenantType.SINGLE_TABLE) {
			return EmptyListIterable.instance();
		}
		JavaEclipseLinkMultitenancy javaMultitenancy = this.getJavaMultitenancyPolicyForDefaults();
		if (javaMultitenancy != null && !this.isSpecifiedMultitenant()) {
			if (javaMultitenancy.hasSpecifiedTenantDiscriminatorColumns()) {
				return new SuperListIterableWrapper<ReadOnlyTenantDiscriminatorColumn>(javaMultitenancy.getSpecifiedTenantDiscriminatorColumns());
			}
		}
		if (this.isMultitenantInheritanceHierarchy()) {
			return new SuperListIterableWrapper<ReadOnlyTenantDiscriminatorColumn>(this.getRootEntity().getMultitenancy().getTenantDiscriminatorColumns());
		}
		if (!isSpecifiedMultitenant()) {
			EclipseLinkMappedSuperclass superMappedSuperclass = this.getSuperMappedSuperclass();
			if (superMappedSuperclass != null && superMappedSuperclass.getMultitenancy().isMultitenant()) {
				return new SuperListIterableWrapper<ReadOnlyTenantDiscriminatorColumn>(superMappedSuperclass.getMultitenancy().getTenantDiscriminatorColumns());
			}
		}
		if (this.getSpecifiedTenantDiscriminatorColumnsSize() == 0) {
			ListIterable<ReadOnlyTenantDiscriminatorColumn> contextColumns = this.getContextDefaultTenantDiscriminatorColumns();
			if (CollectionTools.isEmpty(contextColumns)) {
				return new SingleElementListIterable<ReadOnlyTenantDiscriminatorColumn>(this.defaultTenantDiscriminatorColumn);
			}
			return contextColumns;
		}
		return EmptyListIterable.instance();
	}

	protected ListIterable<ReadOnlyTenantDiscriminatorColumn> getContextDefaultTenantDiscriminatorColumns() {
		EclipseLinkEntityMappings entityMappings = this.getEclipseLinkEntityMappings();
		return (entityMappings != null) ? entityMappings.getTenantDiscriminatorColumns() : this.getPersistenceUnit().getDefaultTenantDiscriminatorColumns();
	}


	protected void moveDefaultTenantDiscriminatorColumn(int index, OrmVirtualTenantDiscriminatorColumn tenantDiscriminatorColumn) {
		this.defaultTenantDiscriminatorColumnContainer.moveContextElement(index, tenantDiscriminatorColumn);
	}

	protected OrmVirtualTenantDiscriminatorColumn addDefaultTenantDiscriminatorColumn(int index, JavaTenantDiscriminatorColumn javaTenantDiscriminatorColumn) {
		return this.defaultTenantDiscriminatorColumnContainer.addContextElement(index, javaTenantDiscriminatorColumn);
	}

	protected OrmVirtualTenantDiscriminatorColumn buildVirtualTenantDiscriminatorColumn(ReadOnlyTenantDiscriminatorColumn tenantDiscriminatorColumn) {
		return new GenericOrmVirtualTenantDiscriminatorColumn(this, this.tenantDiscriminatorColumnOwner, tenantDiscriminatorColumn);
	}

	protected void removeDefaultTenantDiscriminatorColumn(OrmVirtualTenantDiscriminatorColumn tenantDiscriminatorColumn) {
		this.defaultTenantDiscriminatorColumnContainer.removeContextElement(tenantDiscriminatorColumn);
	}

	protected ContextListContainer<OrmVirtualTenantDiscriminatorColumn, ReadOnlyTenantDiscriminatorColumn> buildDefaultTenantDiscriminatorColumnContainer() {
		return new DefaultTenantDiscriminatorColumnContainer();
	}


	/**
	 * default tenant discriminator column container
	 */
	protected class DefaultTenantDiscriminatorColumnContainer
		extends ContextListContainer<OrmVirtualTenantDiscriminatorColumn, ReadOnlyTenantDiscriminatorColumn>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return DEFAULT_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}
		@Override
		protected OrmVirtualTenantDiscriminatorColumn buildContextElement(ReadOnlyTenantDiscriminatorColumn resourceElement) {
			return OrmEclipseLinkMultitenancyImpl.this.buildVirtualTenantDiscriminatorColumn(resourceElement);
		}
		@Override
		protected ListIterable<ReadOnlyTenantDiscriminatorColumn> getResourceElements() {
			return OrmEclipseLinkMultitenancyImpl.this.getTenantDiscriminatorColumnsForDefaults();
		}
		@Override
		protected ReadOnlyTenantDiscriminatorColumn getResourceElement(OrmVirtualTenantDiscriminatorColumn contextElement) {
			return contextElement.getOverriddenColumn();
		}
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

		public String getDefaultTableName() {
			return getTypeMapping().getPrimaryTableName();
		}

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return ReadOnlyTenantDiscriminatorColumn.DEFAULT_NAME;
		}

		public Table resolveDbTable(String tableName) {
			return getTypeMapping().resolveDbTable(tableName);
		}

		public Iterable<String> getCandidateTableNames() {
			return getTypeMapping().getAllAssociatedTableNames();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return getTypeMapping().tableNameIsInvalid(tableName);
		}

		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new TenantDiscriminatorColumnValidator((ReadOnlyTenantDiscriminatorColumn) column, textRangeResolver);
		}

		public TextRange getValidationTextRange() {
			return OrmEclipseLinkMultitenancyImpl.this.getValidationTextRange();
		}
	}

	// ********** XML multitenant **********

	/**
	 * Return null if the XML multitenant does not exists.
	 */
	protected XmlMultitenant getXmlMultitenant() {
		return getXmlMultitenantHolder().getMultitenant();
	}

	protected XmlMultitenantHolder getXmlMultitenantHolder() {
		return (XmlMultitenantHolder) this.getXmlTypeMapping();
	}

	/**
	 * Build the XML multitenant if it does not exist.
	 */
	protected XmlMultitenant getXmlMultitenantForUpdate() {
		XmlMultitenant xmlMultitenant = this.getXmlMultitenant();
		return (xmlMultitenant != null) ? xmlMultitenant : this.buildXmlMultitenant();
	}

	protected XmlMultitenant buildXmlMultitenant() {
		XmlMultitenant xmlMultitenant = EclipseLinkOrmFactory.eINSTANCE.createXmlMultitenant();
		this.getXmlMultitenantHolder().setMultitenant(xmlMultitenant);
		return xmlMultitenant;
	}

	public boolean isMultitenantElementSpecified() {
		return this.getXmlMultitenant() != null;
	}

	protected XmlMultitenant addXmlMultitenant() {
		XmlMultitenant xmlMultitenant = this.buildXmlMultitenant();
		this.getXmlMultitenantHolder().setMultitenant(xmlMultitenant);
		return xmlMultitenant;
	}

	protected void removeXmlMultitenant() {
		this.getXmlMultitenantHolder().setMultitenant(null);
	}

	// ********** misc **********

	@Override
	public OrmEclipseLinkNonEmbeddableTypeMapping getParent() {
		return (OrmEclipseLinkNonEmbeddableTypeMapping) super.getParent();
	}

	protected OrmEclipseLinkNonEmbeddableTypeMapping getTypeMapping() {
		return this.getParent();
	}

	protected XmlTypeMapping getXmlTypeMapping() {
		return this.getTypeMapping().getXmlTypeMapping();
	}

	public EclipseLinkEntityMappings getEclipseLinkEntityMappings() {
		return (EclipseLinkEntityMappings) super.getMappingFileRoot();
	}

	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}

	protected JavaEclipseLinkNonEmbeddableTypeMapping getJavaTypeMappingForDefaults() {
		return this.getTypeMapping().getJavaTypeMappingForDefaults();
	}

	protected JavaEclipseLinkMultitenancy getJavaMultitenancyPolicyForDefaults() {
		JavaEclipseLinkNonEmbeddableTypeMapping javaTypeMapping = this.getJavaTypeMappingForDefaults();
		return (javaTypeMapping == null) ? null : javaTypeMapping.getMultitenancy();
	}

	protected boolean isMultitenantMetadataAllowed() {
		return this.isRootEntity() || this.isInheritanceStrategyTablePerClass();
	}

	protected boolean isRootEntity() {
		return this.getParent().isRootEntity();
	}

	protected boolean isInheritanceStrategyTablePerClass() {
		return this.getParent().getInheritanceStrategy() == InheritanceType.TABLE_PER_CLASS;
	}

	protected boolean isMultitenantInheritanceHierarchy() {
		if (this.isInheritanceStrategyTablePerClass()) {
			return false;
		}
		return this.isRootEntityMultitenant();
	}

	protected boolean isRootEntityMultitenant() {
		EclipseLinkEntity rootEntity = this.getRootEntity();
		return rootEntity != null && rootEntity != getParent() && rootEntity.getMultitenancy().isMultitenant();
	}

	protected EclipseLinkEntity getRootEntity() {
		//instanceof check in case the rootEntity is in an orm.xml instead of an eclipselinkorm.xml file.
		Entity entity = getParent().getRootEntity();
		return entity instanceof EclipseLinkEntity ? (EclipseLinkEntity) entity : null;
	}

	protected boolean isSuperMappedSuperclassMultitenant() {
		EclipseLinkMappedSuperclass mappedSuperclass = this.getSuperMappedSuperclass(getParent());
		return mappedSuperclass != null && mappedSuperclass.getMultitenancy().isMultitenant();
	}

	protected EclipseLinkMappedSuperclass getSuperMappedSuperclass() {
		return this.getSuperMappedSuperclass(this.getParent());
	}

	protected EclipseLinkMappedSuperclass getSuperMappedSuperclass(TypeMapping typeMapping) {
		TypeMapping superTypeMapping = typeMapping.getSuperTypeMapping();
		if (superTypeMapping == null) {
			return null;
		}
		//instanceof check in case the mapped superclass is in an orm.xml instead of an eclipselinkorm.xml file.
		if (superTypeMapping instanceof EclipseLinkMappedSuperclass) {
			return (EclipseLinkMappedSuperclass) superTypeMapping;
		}
		return this.getSuperMappedSuperclass(superTypeMapping);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (getSpecifiedTenantDiscriminatorColumnsSize() > 0) {
			if (!this.isMultitenantMetadataAllowed()) {
				messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						EclipseLinkJpaValidationMessages.MULTIENANT_METADATA_CANNOT_BE_SPECIFIED_ON_NON_ROOT_ENTITY,
						EMPTY_STRING_ARRAY,
						this,
						this.getXmlMultitenant().getValidationTextRange()
					)
				);
			}
			else {
				for (OrmTenantDiscriminatorColumn column : this.getSpecifiedTenantDiscriminatorColumns()) {
					column.validate(messages, reporter);
				}
			}
		}
		for (OrmVirtualTenantDiscriminatorColumn column : this.getDefaultTenantDiscriminatorColumns()) {
			column.validate(messages, reporter);
		}
	}

	public TextRange getValidationTextRange() {
		TextRange textRange = this.getXmlValidationTextRange();
		return (textRange != null) ? textRange : this.getTypeMapping().getValidationTextRange();
	}

	protected TextRange getXmlValidationTextRange() {
		XmlMultitenant_2_4 xmlMultitenant = this.getXmlMultitenant();
		return (xmlMultitenant == null) ? null : xmlMultitenant.getValidationTextRange();
	}
}
