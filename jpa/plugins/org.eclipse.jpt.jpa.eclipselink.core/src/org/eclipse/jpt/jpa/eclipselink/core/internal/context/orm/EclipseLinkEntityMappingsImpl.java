/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractEntityMappings;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.ReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmVirtualTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlTenantDiscriminatorColumn_2_3;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkEntityMappingsImpl
	extends AbstractEntityMappings
	implements 
		EclipseLinkEntityMappings, 
		OrmEclipseLinkConverterContainer.Owner
{
	protected final OrmEclipseLinkConverterContainer converterContainer;

	protected final ContextListContainer<OrmTenantDiscriminatorColumn2_3, XmlTenantDiscriminatorColumn_2_3> specifiedTenantDiscriminatorColumnContainer;
	protected final OrmReadOnlyTenantDiscriminatorColumn2_3.Owner tenantDiscriminatorColumnOwner;

	protected final ContextListContainer<OrmVirtualTenantDiscriminatorColumn2_3, ReadOnlyTenantDiscriminatorColumn2_3> defaultTenantDiscriminatorColumnContainer;


	protected String specifiedGetMethod;
	protected String defaultGetMethod;

	protected String specifiedSetMethod;
	protected String defaultSetMethod;


	public EclipseLinkEntityMappingsImpl(OrmXml parent, XmlEntityMappings resource) {
		super(parent, resource);
		this.converterContainer = this.buildConverterContainer();
		this.tenantDiscriminatorColumnOwner = this.buildTenantDiscriminatorColumnOwner();
		this.specifiedTenantDiscriminatorColumnContainer = this.buildSpecifiedTenantDiscriminatorColumnContainer();
		this.defaultTenantDiscriminatorColumnContainer = this.buildDefaultTenantDiscriminatorColumnContainer();
		this.specifiedGetMethod = this.buildSpecifiedGetMethod();
		this.specifiedSetMethod = this.buildSpecifiedSetMethod();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.converterContainer.synchronizeWithResourceModel();
		this.syncSpecifiedTenantDiscriminatorColumns();
		this.setSpecifiedGetMethod_(this.buildSpecifiedGetMethod());
		this.setSpecifiedSetMethod_(this.buildSpecifiedSetMethod());
	}

	@Override
	public void update() {
		super.update();
		this.converterContainer.update();
		this.updateNodes(this.getSpecifiedTenantDiscriminatorColumns());
		this.updateDefaultTenantDiscriminatorColumns();
		this.setDefaultGetMethod(this.buildDefaultGetMethod());
		this.setDefaultSetMethod(this.buildDefaultSetMethod());
	}

	@Override
	public XmlEntityMappings getXmlEntityMappings() {
		return (XmlEntityMappings) super.getXmlEntityMappings();
	}

	// ********** converter container **********

	public OrmEclipseLinkConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected OrmEclipseLinkConverterContainer buildConverterContainer() {
		return new OrmEclipseLinkConverterContainerImpl(this, this, (XmlEntityMappings) this.xmlEntityMappings);
	}

	public int getNumberSupportedConverters() {
		return Integer.MAX_VALUE;
	}

	@SuppressWarnings("unchecked")
	public Iterable<EclipseLinkConverter> getMappingFileConverters() {
		return new CompositeIterable<EclipseLinkConverter>(
					this.converterContainer.getConverters(),
					this.getTypeMappingConverters()
				);
	}

	protected Iterable<EclipseLinkConverter> getTypeMappingConverters() {
		return new CompositeIterable<EclipseLinkConverter>(this.getTypeMappingConverterLists());
	}

	protected Iterable<Iterable<EclipseLinkConverter>> getTypeMappingConverterLists() {
		return new TransformationIterable<OrmTypeMapping, Iterable<EclipseLinkConverter>>(this.getTypeMappings()) {
					@Override
					protected Iterable<EclipseLinkConverter> transform(OrmTypeMapping typeMapping) {
						return ((EclipseLinkTypeMapping) typeMapping).getConverters();
					}
				};
	}


	// ********** tenant discriminator columns **********

	public ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getTenantDiscriminatorColumns() {
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

	public ListIterable<OrmTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns() {
		return this.specifiedTenantDiscriminatorColumnContainer.getContextElements();
	}

	protected ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getReadOnlySpecifiedTenantDiscriminatorColumns() {
		return new SuperListIterableWrapper<ReadOnlyTenantDiscriminatorColumn2_3>(this.getSpecifiedTenantDiscriminatorColumns());
	}

	public int getSpecifiedTenantDiscriminatorColumnsSize() {
		return this.specifiedTenantDiscriminatorColumnContainer.getContextElementsSize();
	}

	public boolean hasSpecifiedTenantDiscriminatorColumns() {
		return this.getSpecifiedTenantDiscriminatorColumnsSize() != 0;
	}

	public OrmTenantDiscriminatorColumn2_3 getSpecifiedTenantDiscriminatorColumn(int index) {
		return this.specifiedTenantDiscriminatorColumnContainer.getContextElement(index);
	}

	public OrmTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn() {
		return this.addSpecifiedTenantDiscriminatorColumn(this.getSpecifiedTenantDiscriminatorColumnsSize());
	}

	public OrmTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn(int index) {
		XmlTenantDiscriminatorColumn xmlJoinColumn = this.buildXmlTenantDiscriminatorColumn();
		OrmTenantDiscriminatorColumn2_3 joinColumn = this.specifiedTenantDiscriminatorColumnContainer.addContextElement(index, xmlJoinColumn);
		this.getXmlEntityMappings().getTenantDiscriminatorColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	protected XmlTenantDiscriminatorColumn buildXmlTenantDiscriminatorColumn() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlTenantDiscriminatorColumn();
	}

	public void removeSpecifiedTenantDiscriminatorColumn(OrmTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		this.removeSpecifiedTenantDiscriminatorColumn(this.specifiedTenantDiscriminatorColumnContainer.indexOfContextElement(tenantDiscriminatorColumn));
	}

	public void removeSpecifiedTenantDiscriminatorColumn(int index) {
		this.specifiedTenantDiscriminatorColumnContainer.removeContextElement(index);
		this.getXmlEntityMappings().getTenantDiscriminatorColumns().remove(index);
	}

	public void moveSpecifiedTenantDiscriminatorColumn(int targetIndex, int sourceIndex) {
		this.specifiedTenantDiscriminatorColumnContainer.moveContextElement(targetIndex, sourceIndex);
		this.getXmlEntityMappings().getTenantDiscriminatorColumns().move(targetIndex, sourceIndex);
	}

	protected void syncSpecifiedTenantDiscriminatorColumns() {
		this.specifiedTenantDiscriminatorColumnContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlTenantDiscriminatorColumn_2_3> getXmlTenantDiscriminatorColumns() {
		if (getXmlEntityMappings() == null) {
			return EmptyListIterable.instance();
		}
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlTenantDiscriminatorColumn_2_3>(this.getXmlEntityMappings().getTenantDiscriminatorColumns());
	}

	/**
	 *  specified tenant discriminator column container
	 */
	protected class SpecifiedTenantDiscriminatorColumnContainer
		extends ContextListContainer<OrmTenantDiscriminatorColumn2_3, XmlTenantDiscriminatorColumn_2_3>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}
		@Override
		protected OrmTenantDiscriminatorColumn2_3 buildContextElement(XmlTenantDiscriminatorColumn_2_3 resourceElement) {
			return EclipseLinkEntityMappingsImpl.this.buildTenantDiscriminatorColumn(resourceElement);
		}
		@Override
		protected ListIterable<XmlTenantDiscriminatorColumn_2_3> getResourceElements() {
			return EclipseLinkEntityMappingsImpl.this.getXmlTenantDiscriminatorColumns();
		}
		@Override
		protected XmlTenantDiscriminatorColumn_2_3 getResourceElement(OrmTenantDiscriminatorColumn2_3 contextElement) {
			return contextElement.getXmlColumn();
		}
	}

	protected OrmReadOnlyTenantDiscriminatorColumn2_3.Owner buildTenantDiscriminatorColumnOwner() {
		return new TenantDiscriminatorColumnOwner();
	}

	protected SpecifiedTenantDiscriminatorColumnContainer buildSpecifiedTenantDiscriminatorColumnContainer() {
		SpecifiedTenantDiscriminatorColumnContainer container = new SpecifiedTenantDiscriminatorColumnContainer();
		container.initialize();
		return container;
	}

	protected OrmTenantDiscriminatorColumn2_3 buildTenantDiscriminatorColumn(XmlTenantDiscriminatorColumn_2_3 xmlTenantDiscriminatorColumn) {
		return new EclipseLinkOrmTenantDiscriminatorColumn2_3(this, this.tenantDiscriminatorColumnOwner, xmlTenantDiscriminatorColumn);
	}


	// ********** default tenant discriminator columns **********

	public ListIterable<OrmVirtualTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns() {
		return this.defaultTenantDiscriminatorColumnContainer.getContextElements();
	}

	protected ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getReadOnlyDefaultTenantDiscriminatorColumns() {
		return new SuperListIterableWrapper<ReadOnlyTenantDiscriminatorColumn2_3>(this.getDefaultTenantDiscriminatorColumns());
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
	 * @see #getTenantDiscriminatorColumnsForDefaults()
	 */
	protected void updateDefaultTenantDiscriminatorColumns() {
		this.defaultTenantDiscriminatorColumnContainer.update();
	}

	protected ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getTenantDiscriminatorColumnsForDefaults() {
		if (this.getSpecifiedTenantDiscriminatorColumnsSize() > 0) {
			return EmptyListIterable.instance();
		}
		return getContextDefaultTenantDiscriminatorColumns();
	}

	protected ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getContextDefaultTenantDiscriminatorColumns() {
		return this.getPersistenceUnit().getDefaultTenantDiscriminatorColumns();
	}
	
	protected void moveDefaultTenantDiscriminatorColumn(int index, OrmVirtualTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		this.defaultTenantDiscriminatorColumnContainer.moveContextElement(index, tenantDiscriminatorColumn);
	}

	protected OrmVirtualTenantDiscriminatorColumn2_3 addDefaultTenantDiscriminatorColumn(int index, OrmTenantDiscriminatorColumn2_3 ormTenantDiscriminatorColumn) {
		return this.defaultTenantDiscriminatorColumnContainer.addContextElement(index, ormTenantDiscriminatorColumn);
	}

	protected OrmVirtualTenantDiscriminatorColumn2_3 buildVirtualTenantDiscriminatorColumn(ReadOnlyTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		return new GenericOrmVirtualTenantDiscriminatorColumn2_3(this, this.tenantDiscriminatorColumnOwner, tenantDiscriminatorColumn);
	}

	protected void removeDefaultTenantDiscriminatorColumn(OrmVirtualTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		this.defaultTenantDiscriminatorColumnContainer.removeContextElement(tenantDiscriminatorColumn);
	}

	protected DefaultTenantDiscriminatorColumnContainer buildDefaultTenantDiscriminatorColumnContainer() {
		return new DefaultTenantDiscriminatorColumnContainer();
	}


	/**
	 * default tenant discriminator column container
	 */
	protected class DefaultTenantDiscriminatorColumnContainer
		extends ContextListContainer<OrmVirtualTenantDiscriminatorColumn2_3, ReadOnlyTenantDiscriminatorColumn2_3>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return DEFAULT_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}
		@Override
		protected OrmVirtualTenantDiscriminatorColumn2_3 buildContextElement(ReadOnlyTenantDiscriminatorColumn2_3 resourceElement) {
			return EclipseLinkEntityMappingsImpl.this.buildVirtualTenantDiscriminatorColumn(resourceElement);
		}
		@Override
		protected ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getResourceElements() {
			return EclipseLinkEntityMappingsImpl.this.getTenantDiscriminatorColumnsForDefaults();
		}
		@Override
		protected ReadOnlyTenantDiscriminatorColumn2_3 getResourceElement(OrmVirtualTenantDiscriminatorColumn2_3 contextElement) {
			return contextElement.getOverriddenColumn();
		}
	}


	// ********** OrmReadOnlyTenantDiscriminatorColumn.Owner implementation **********

	protected class TenantDiscriminatorColumnOwner 
		implements OrmReadOnlyTenantDiscriminatorColumn2_3.Owner
	{

		public String getDefaultContextPropertyName() {
			return ReadOnlyTenantDiscriminatorColumn2_3.DEFAULT_CONTEXT_PROPERTY;
		}

		public boolean getDefaultPrimaryKey() {
			return ReadOnlyTenantDiscriminatorColumn2_3.DEFAULT_PRIMARY_KEY;
		}

		public int getDefaultLength() {
			return ReadOnlyNamedDiscriminatorColumn.DEFAULT_LENGTH;
		}

		public DiscriminatorType getDefaultDiscriminatorType() {
			return ReadOnlyNamedDiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE;
		}

		/**
		 * No default table name in the context of entity mappings
		 */
		public String getDefaultTableName() {
			return null;
		}

		public String getDefaultColumnName(ReadOnlyNamedColumn column) {
			return ReadOnlyTenantDiscriminatorColumn2_3.DEFAULT_NAME;
		}

		/**
		 * No table in the context of entity mappings
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
			return EclipseLinkEntityMappingsImpl.this.getValidationTextRange();
		}
	}


	//*************** get method *****************

	public String getGetMethod() {
		String specifiedGetMethod = this.getSpecifiedGetMethod();
		return (specifiedGetMethod != null) ? specifiedGetMethod : this.defaultGetMethod;
	}

	public String getDefaultGetMethod() {
		return this.defaultGetMethod;
	}

	protected String buildDefaultGetMethod() {
		return this.getPersistenceUnit().getDefaultGetMethod();
	}

	protected void setDefaultGetMethod(String getMethod) {
		String old = this.defaultGetMethod;
		this.defaultGetMethod = getMethod;
		this.firePropertyChanged(DEFAULT_GET_METHOD_PROPERTY, old, getMethod);
	}

	public String getSpecifiedGetMethod() {
		return this.specifiedGetMethod;
	}

	public void setSpecifiedGetMethod(String getMethod) {
		if (this.valuesAreDifferent(this.specifiedGetMethod, getMethod)) {
			XmlAccessMethods xmlAccessMethods = this.getXmlAccessMethodsForUpdate();
			this.setSpecifiedGetMethod_(getMethod);
			xmlAccessMethods.setGetMethod(getMethod);
			this.removeXmlAccessMethodsIfUnset();
		}
	}

	protected void setSpecifiedGetMethod_(String getMethod) {
		String old = this.specifiedGetMethod;
		this.specifiedGetMethod = getMethod;
		this.firePropertyChanged(SPECIFIED_GET_METHOD_PROPERTY, old, getMethod);
	}

	protected String buildSpecifiedGetMethod() {
		XmlAccessMethods xmlAccessMethods = getXmlAccessMethods();
		return xmlAccessMethods != null ? xmlAccessMethods.getGetMethod() : null;
	}


	//*************** set method *****************

	public String getSetMethod() {
		String specifiedSetMethod = this.getSpecifiedSetMethod();
		return (specifiedSetMethod != null) ? specifiedSetMethod : this.defaultSetMethod;
	}

	public String getDefaultSetMethod() {
		return this.defaultSetMethod;
	}

	protected void setDefaultSetMethod(String setMethod) {
		String old = this.defaultSetMethod;
		this.defaultSetMethod = setMethod;
		this.firePropertyChanged(DEFAULT_SET_METHOD_PROPERTY, old, setMethod);
	}

	protected String buildDefaultSetMethod() {
		return this.getPersistenceUnit().getDefaultSetMethod();
	}

	public String getSpecifiedSetMethod() {
		return this.specifiedSetMethod;
	}

	public void setSpecifiedSetMethod(String setMethod) {
		if (this.valuesAreDifferent(this.specifiedSetMethod, setMethod)) {
			XmlAccessMethods xmlAccessMethods = this.getXmlAccessMethodsForUpdate();
			this.setSpecifiedSetMethod_(setMethod);
			xmlAccessMethods.setSetMethod(setMethod);
			this.removeXmlAccessMethodsIfUnset();
		}
	}

	protected void setSpecifiedSetMethod_(String setMethod) {
		String old = this.specifiedSetMethod;
		this.specifiedSetMethod = setMethod;
		this.firePropertyChanged(SPECIFIED_SET_METHOD_PROPERTY, old, setMethod);
	}

	protected String buildSpecifiedSetMethod() {
		XmlAccessMethods xmlAccessMethods = getXmlAccessMethods();
		return xmlAccessMethods != null ? xmlAccessMethods.getSetMethod() : null;
	}

	//*************** XML access methods *****************
	protected XmlAccessMethods getXmlAccessMethods() {
		return getXmlEntityMappings().getAccessMethods();
	}

	/**
	 * Build the XML access methods (and XML defaults and XML metadata if necessary) if it does not exist.
	 */
	protected XmlAccessMethods getXmlAccessMethodsForUpdate() {
		XmlAccessMethods xmlAccessMethods = getXmlAccessMethods();
		return (xmlAccessMethods != null) ? xmlAccessMethods : this.buildXmlAccessMethods();
	}

	protected XmlAccessMethods buildXmlAccessMethods() {
		XmlAccessMethods xmlAccessMethods = this.buildXmlAccessMethods_();
		this.getXmlEntityMappings().setAccessMethods(xmlAccessMethods);
		return xmlAccessMethods;
	}

	protected XmlAccessMethods buildXmlAccessMethods_() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlAccessMethods();
	}

	/**
	 * clear the XML access methods if appropriate
	 */
	protected void removeXmlAccessMethodsIfUnset() {
		if (this.getXmlAccessMethods().isUnset()) {
			this.getXmlEntityMappings().setAccessMethods(null);
		}
	}


	// ********** misc **********

	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}

	@Override
	protected IContentType getContentType() {
		return JptJpaEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE;
	}


	// ********** refactoring **********

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			super.createRenameTypeEdits(originalType, newName),
			this.createConverterRenameTypeEdits(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createConverterRenameTypeEdits(IType originalType, String newName) {
		return this.converterContainer.createRenameTypeEdits(originalType, newName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			super.createMoveTypeEdits(originalType, newPackage),
			this.createConverterMoveTypeEdits(originalType, newPackage));
	}

	protected Iterable<ReplaceEdit> createConverterMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.converterContainer.createMoveTypeEdits(originalType, newPackage);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			super.createRenamePackageEdits(originalPackage, newName),
			this.createConverterRenamePackageEdits(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createConverterRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.converterContainer.createRenamePackageEdits(originalPackage, newName);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.converterContainer.validate(messages, reporter);
	}
}
