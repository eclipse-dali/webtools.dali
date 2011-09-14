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
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.InheritanceType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkNonEmbeddableTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkNonEmbeddableTypeMapping;
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
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm.OrmEclipseLinkMultitenancy;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm.OrmReadOnlyTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.v2_3.context.orm.OrmTenantDiscriminatorColumn;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkMultitenancyImpl
	extends AbstractOrmXmlContextNode
	implements OrmEclipseLinkMultitenancy
{
	protected EclipseLinkMultitenantType specifiedType;
	protected EclipseLinkMultitenantType defaultType;

	protected Boolean specifiedIncludeCriteria;
	protected boolean defaultIncludeCriteria;

	protected final ContextListContainer<OrmTenantDiscriminatorColumn, XmlTenantDiscriminatorColumn_2_3> specifiedTenantDiscriminatorColumnContainer;
	protected final OrmReadOnlyTenantDiscriminatorColumn.Owner tenantDiscriminatorColumnOwner;

	public OrmEclipseLinkMultitenancyImpl(OrmEclipseLinkNonEmbeddableTypeMapping parent) {
		super(parent);

		this.tenantDiscriminatorColumnOwner = this.buildTenantDiscriminatorColumnOwner();
		this.specifiedTenantDiscriminatorColumnContainer = new SpecifiedTenantDiscriminatorColumnContainer();
		this.specifiedType = this.buildSpecifiedType();
		this.specifiedIncludeCriteria = this.buildSpecifiedIncludeCriteria();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncTenantDiscriminatorColumns();
		this.setSpecifiedType_(this.buildSpecifiedType());
		this.setSpecifiedIncludeCriteria_(this.buildSpecifiedIncludeCriteria());
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getSpecifiedTenantDiscriminatorColumns());

		boolean xmlMultitenantNotSpecified = (this.getXmlMultitenant() == null);
		JavaEclipseLinkMultitenancy javaMultitenantPolicy = this.getJavaMultitenancyPolicyForDefaults();
		boolean javaMultitenantPolicySpecified = (javaMultitenantPolicy != null);
		boolean useJavaValue = (xmlMultitenantNotSpecified && javaMultitenantPolicySpecified);

		this.setDefaultType(useJavaValue ? javaMultitenantPolicy.getType() : (isMultitenant() ? DEFAULT_TYPE : null));
		this.setDefaultIncludeCriteria(useJavaValue ? javaMultitenantPolicy.isIncludeCriteria() : DEFAULT_INCLUDE_CRITERIA);

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

	public ListIterable<OrmTenantDiscriminatorColumn> getSpecifiedTenantDiscriminatorColumns() {
		return this.specifiedTenantDiscriminatorColumnContainer.getContextElements();
	}

	public int getSpecifiedTenantDiscriminatorColumnsSize() {
		return this.specifiedTenantDiscriminatorColumnContainer.getContextElementsSize();
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


	protected void syncTenantDiscriminatorColumns() {
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
			return new TenantDiscriminatorColumnValidator(column, textRangeResolver);
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

	public boolean isMultitenant() {
		return isMultitenantElementSpecified();
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

	protected JavaEclipseLinkNonEmbeddableTypeMapping getJavaTypeMappingForDefaults() {
		return this.getTypeMapping().getJavaTypeMappingForDefaults();
	}

	protected JavaEclipseLinkMultitenancy getJavaMultitenancyPolicyForDefaults() {
		JavaEclipseLinkNonEmbeddableTypeMapping javaTypeMapping = this.getJavaTypeMappingForDefaults();
		return (javaTypeMapping == null) ? null : javaTypeMapping.getMultitenancy();
	}

	protected boolean isParentInheritanceStrategySingleTableOrJoined() {
		InheritanceType inheritanceStrategy = getParent().getInheritanceStrategy();
		return inheritanceStrategy == InheritanceType.SINGLE_TABLE || inheritanceStrategy == InheritanceType.JOINED;
	}

	protected boolean isParentRootEntity() {
		return getParent().isRootEntity();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (getSpecifiedTenantDiscriminatorColumnsSize() > 0) {
			if (this.isParentInheritanceStrategySingleTableOrJoined() && !isParentRootEntity()) {
				//TODO need to validate this if the multitenant annotation exists, 
				//but not the multitenant xml element , I think it is defaulted in
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
