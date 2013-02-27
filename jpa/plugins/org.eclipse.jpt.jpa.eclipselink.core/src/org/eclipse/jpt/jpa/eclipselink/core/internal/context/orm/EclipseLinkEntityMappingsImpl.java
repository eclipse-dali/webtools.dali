/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.ReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.VirtualTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmSpecifiedTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmUuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTenantDiscriminatorColumn;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlUuidGenerator;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlUuidGenerator_2_4;
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

	protected final ContextListContainer<OrmSpecifiedTenantDiscriminatorColumn2_3, XmlTenantDiscriminatorColumn> specifiedTenantDiscriminatorColumnContainer;
	protected final ReadOnlyTenantDiscriminatorColumn2_3.Owner tenantDiscriminatorColumnOwner;

	protected final ContextListContainer<VirtualTenantDiscriminatorColumn2_3, ReadOnlyTenantDiscriminatorColumn2_3> defaultTenantDiscriminatorColumnContainer;


	protected String specifiedGetMethod;
	protected String defaultGetMethod;

	protected String specifiedSetMethod;
	protected String defaultSetMethod;

	protected final ContextListContainer<OrmUuidGenerator, XmlUuidGenerator_2_4> uuidGeneratorContainer;

	public EclipseLinkEntityMappingsImpl(OrmXml parent, XmlEntityMappings resource) {
		super(parent, resource);
		this.converterContainer = this.buildConverterContainer();
		this.tenantDiscriminatorColumnOwner = this.buildTenantDiscriminatorColumnOwner();
		this.specifiedTenantDiscriminatorColumnContainer = this.buildSpecifiedTenantDiscriminatorColumnContainer();
		this.defaultTenantDiscriminatorColumnContainer = this.buildDefaultTenantDiscriminatorColumnContainer();
		this.specifiedGetMethod = this.buildSpecifiedGetMethod();
		this.specifiedSetMethod = this.buildSpecifiedSetMethod();
		this.uuidGeneratorContainer = this.buildUuidGeneratorContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.converterContainer.synchronizeWithResourceModel();
		this.syncSpecifiedTenantDiscriminatorColumns();
		this.setSpecifiedGetMethod_(this.buildSpecifiedGetMethod());
		this.setSpecifiedSetMethod_(this.buildSpecifiedSetMethod());
		this.syncUuidGenerators();
	}

	@Override
	public void update() {
		super.update();
		this.converterContainer.update();
		this.updateModels(this.getSpecifiedTenantDiscriminatorColumns());
		this.updateDefaultTenantDiscriminatorColumns();
		this.setDefaultGetMethod(this.buildDefaultGetMethod());
		this.setDefaultSetMethod(this.buildDefaultSetMethod());
		this.updateModels(this.getUuidGenerators());
	}

	@Override
	public XmlEntityMappings getXmlEntityMappings() {
		return (XmlEntityMappings) super.getXmlEntityMappings();
	}


	// ********** types **********

	/**
	 * preconditions: 
	 * 		getPackage() is not null
	 * 		unqualifiedClassName is not qualified (contains no '.')
	 */
	@Override
	protected String prependGlobalPackage(String unqualifiedClassName) {
		// Format of global package is "foo.bar."
		if (this.getPackage().endsWith(".")) { //$NON-NLS-1$
			return this.getPackage() + unqualifiedClassName;
		}
		// Format of global package is "foo.bar"
		return super.prependGlobalPackage(unqualifiedClassName);
	}


	// ********** managed types **********

	@Override
	protected List<XmlConverter> getXml2_1Converters() {
		ArrayList<XmlConverter> xmlConverters = new ArrayList<XmlConverter>();
		for (XmlConverter xmlConverter : this.xmlEntityMappings.getConverters()) {
			if (((XmlNamedConverter) xmlConverter).getName() == null){
				xmlConverters.add(xmlConverter);
			}
		}

		return xmlConverters;
	}


	// ********** converter container **********

	public OrmEclipseLinkConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected OrmEclipseLinkConverterContainer buildConverterContainer() {
		return new OrmEclipseLinkEntityMappingsConverterContainer(this, this, (XmlEntityMappings) this.xmlEntityMappings);
	}

	public int getNumberSupportedConverters() {
		return Integer.MAX_VALUE;
	}

	@SuppressWarnings("unchecked")
	public Iterable<EclipseLinkConverter> getMappingFileConverters() {
		return IterableTools.concatenate(
					this.converterContainer.getConverters(),
					this.getTypeMappingConverters()
				);
	}

	protected Iterable<EclipseLinkConverter> getTypeMappingConverters() {
		return IterableTools.children(this.getEclipseLinkTypeMappings(), EclipseLinkTypeMapping.CONVERTERS_TRANSFORMER);
	}

	protected Iterable<EclipseLinkOrmTypeMapping> getEclipseLinkTypeMappings() {
		return IterableTools.downCast(this.getTypeMappings());
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

	public ListIterable<OrmSpecifiedTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns() {
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

	public OrmSpecifiedTenantDiscriminatorColumn2_3 getSpecifiedTenantDiscriminatorColumn(int index) {
		return this.specifiedTenantDiscriminatorColumnContainer.getContextElement(index);
	}

	public OrmSpecifiedTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn() {
		return this.addSpecifiedTenantDiscriminatorColumn(this.getSpecifiedTenantDiscriminatorColumnsSize());
	}

	public OrmSpecifiedTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn(int index) {
		XmlTenantDiscriminatorColumn xmlJoinColumn = this.buildXmlTenantDiscriminatorColumn();
		OrmSpecifiedTenantDiscriminatorColumn2_3 joinColumn = this.specifiedTenantDiscriminatorColumnContainer.addContextElement(index, xmlJoinColumn);
		this.getXmlEntityMappings().getTenantDiscriminatorColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	protected XmlTenantDiscriminatorColumn buildXmlTenantDiscriminatorColumn() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlTenantDiscriminatorColumn();
	}

	public void removeSpecifiedTenantDiscriminatorColumn(OrmSpecifiedTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
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

	protected ListIterable<XmlTenantDiscriminatorColumn> getXmlTenantDiscriminatorColumns() {
		if (getXmlEntityMappings() == null) {
			return EmptyListIterable.instance();
		}
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.getXmlEntityMappings().getTenantDiscriminatorColumns());
	}

	/**
	 *  specified tenant discriminator column container
	 */
	protected class SpecifiedTenantDiscriminatorColumnContainer
		extends ContextListContainer<OrmSpecifiedTenantDiscriminatorColumn2_3, XmlTenantDiscriminatorColumn>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}
		@Override
		protected OrmSpecifiedTenantDiscriminatorColumn2_3 buildContextElement(XmlTenantDiscriminatorColumn resourceElement) {
			return EclipseLinkEntityMappingsImpl.this.buildTenantDiscriminatorColumn(resourceElement);
		}
		@Override
		protected ListIterable<XmlTenantDiscriminatorColumn> getResourceElements() {
			return EclipseLinkEntityMappingsImpl.this.getXmlTenantDiscriminatorColumns();
		}
		@Override
		protected XmlTenantDiscriminatorColumn getResourceElement(OrmSpecifiedTenantDiscriminatorColumn2_3 contextElement) {
			return contextElement.getXmlColumn();
		}
	}

	protected ReadOnlyTenantDiscriminatorColumn2_3.Owner buildTenantDiscriminatorColumnOwner() {
		return new TenantDiscriminatorColumnOwner();
	}

	protected SpecifiedTenantDiscriminatorColumnContainer buildSpecifiedTenantDiscriminatorColumnContainer() {
		SpecifiedTenantDiscriminatorColumnContainer container = new SpecifiedTenantDiscriminatorColumnContainer();
		container.initialize();
		return container;
	}

	protected OrmSpecifiedTenantDiscriminatorColumn2_3 buildTenantDiscriminatorColumn(XmlTenantDiscriminatorColumn xmlTenantDiscriminatorColumn) {
		return new EclipseLinkOrmTenantDiscriminatorColumn2_3(this, this.tenantDiscriminatorColumnOwner, xmlTenantDiscriminatorColumn);
	}


	// ********** default tenant discriminator columns **********

	public ListIterable<VirtualTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns() {
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
	
	protected void moveDefaultTenantDiscriminatorColumn(int index, VirtualTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		this.defaultTenantDiscriminatorColumnContainer.moveContextElement(index, tenantDiscriminatorColumn);
	}

	protected VirtualTenantDiscriminatorColumn2_3 addDefaultTenantDiscriminatorColumn(int index, OrmSpecifiedTenantDiscriminatorColumn2_3 ormTenantDiscriminatorColumn) {
		return this.defaultTenantDiscriminatorColumnContainer.addContextElement(index, ormTenantDiscriminatorColumn);
	}

	protected VirtualTenantDiscriminatorColumn2_3 buildVirtualTenantDiscriminatorColumn(ReadOnlyTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		return new GenericOrmVirtualTenantDiscriminatorColumn2_3(this, this.tenantDiscriminatorColumnOwner, tenantDiscriminatorColumn);
	}

	protected void removeDefaultTenantDiscriminatorColumn(VirtualTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		this.defaultTenantDiscriminatorColumnContainer.removeContextElement(tenantDiscriminatorColumn);
	}

	protected DefaultTenantDiscriminatorColumnContainer buildDefaultTenantDiscriminatorColumnContainer() {
		return new DefaultTenantDiscriminatorColumnContainer();
	}


	/**
	 * default tenant discriminator column container
	 */
	protected class DefaultTenantDiscriminatorColumnContainer
		extends ContextListContainer<VirtualTenantDiscriminatorColumn2_3, ReadOnlyTenantDiscriminatorColumn2_3>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return DEFAULT_TENANT_DISCRIMINATOR_COLUMNS_LIST;
		}
		@Override
		protected VirtualTenantDiscriminatorColumn2_3 buildContextElement(ReadOnlyTenantDiscriminatorColumn2_3 resourceElement) {
			return EclipseLinkEntityMappingsImpl.this.buildVirtualTenantDiscriminatorColumn(resourceElement);
		}
		@Override
		protected ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getResourceElements() {
			return EclipseLinkEntityMappingsImpl.this.getTenantDiscriminatorColumnsForDefaults();
		}
		@Override
		protected ReadOnlyTenantDiscriminatorColumn2_3 getResourceElement(VirtualTenantDiscriminatorColumn2_3 contextElement) {
			return contextElement.getOverriddenColumn();
		}
	}


	// ********** OrmReadOnlyTenantDiscriminatorColumn.Owner implementation **********

	protected class TenantDiscriminatorColumnOwner 
		implements ReadOnlyTenantDiscriminatorColumn2_3.Owner
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
		public JptValidator buildColumnValidator(ReadOnlyNamedColumn column) {
			return JptValidator.Null.instance();
		}

		public TextRange getValidationTextRange() {
			return EclipseLinkEntityMappingsImpl.this.getValidationTextRange();
		}
	}


	//*************** get method *****************

	public String getGetMethod() {
		return (this.specifiedGetMethod != null) ? this.specifiedGetMethod : this.defaultGetMethod;
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
		return (this.specifiedSetMethod != null) ? this.specifiedSetMethod : this.defaultSetMethod;
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


	// ********** uuid generators **********

	public ListIterable<OrmUuidGenerator> getUuidGenerators() {
		return this.uuidGeneratorContainer.getContextElements();
	}

	public int getUuidGeneratorsSize() {
		return this.uuidGeneratorContainer.getContextElementsSize();
	}

	public OrmUuidGenerator addUuidGenerator() {
		return this.addUuidGenerator(this.getUuidGeneratorsSize());
	}

	public OrmUuidGenerator addUuidGenerator(int index) {
		XmlUuidGenerator xmlGenerator = this.buildXmlUuidGenerator();
		OrmUuidGenerator uuidGenerator = this.uuidGeneratorContainer.addContextElement(index, xmlGenerator);
		this.getXmlEntityMappings().getUuidGenerators().add(index, xmlGenerator);
		return uuidGenerator;
	}

	protected XmlUuidGenerator buildXmlUuidGenerator() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlUuidGenerator();
	}

	protected OrmUuidGenerator buildUuidGenerator(XmlUuidGenerator_2_4 xmlUuidGenerator) {
		return new OrmEclipseLinkUuidGenerator(this, xmlUuidGenerator);
	}

	public void removeUuidGenerator(OrmUuidGenerator uuidGenerator) {
		this.removeUuidGenerator(this.uuidGeneratorContainer.indexOfContextElement(uuidGenerator));
	}

	public void removeUuidGenerator(int index) {
		this.uuidGeneratorContainer.removeContextElement(index);
		this.getXmlEntityMappings().getUuidGenerators().remove(index);
	}

	public void moveUuidGenerator(int targetIndex, int sourceIndex) {
		this.sequenceGeneratorContainer.moveContextElement(targetIndex, sourceIndex);
		this.xmlEntityMappings.getSequenceGenerators().move(targetIndex, sourceIndex);
	}

	protected void syncUuidGenerators() {
		this.uuidGeneratorContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlUuidGenerator_2_4> getXmlUuidGenerators() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.getXmlEntityMappings().getUuidGenerators());
	}

	protected ContextListContainer<OrmUuidGenerator, XmlUuidGenerator_2_4> buildUuidGeneratorContainer() {
		UuidGeneratorContainer container = new UuidGeneratorContainer();
		container.initialize();
		return container;
	}

	/**
	 * sequence generator container
	 */
	protected class UuidGeneratorContainer
		extends ContextListContainer<OrmUuidGenerator, XmlUuidGenerator_2_4>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return UUID_GENERATORS_LIST;
		}
		@Override
		protected OrmUuidGenerator buildContextElement(XmlUuidGenerator_2_4 resourceElement) {
			return EclipseLinkEntityMappingsImpl.this.buildUuidGenerator(resourceElement);
		}
		@Override
		protected ListIterable<XmlUuidGenerator_2_4> getResourceElements() {
			return EclipseLinkEntityMappingsImpl.this.getXmlUuidGenerators();
		}
		@Override
		protected XmlUuidGenerator_2_4 getResourceElement(OrmUuidGenerator contextElement) {
			return contextElement.getXmlGenerator();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<Generator> getMappingFileGenerators() {
		return IterableTools.concatenate(
					super.getMappingFileGenerators(),
					this.getUuidGenerators()
				);
	}

	// ********** misc **********

	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}

	@Override
	protected IContentType getContentType() {
		return XmlEntityMappings.CONTENT_TYPE;
	}


	// ********** refactoring **********

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.concatenate(
			super.createRenameTypeEdits(originalType, newName),
			this.createConverterRenameTypeEdits(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createConverterRenameTypeEdits(IType originalType, String newName) {
		return this.converterContainer.createRenameTypeEdits(originalType, newName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.concatenate(
			super.createMoveTypeEdits(originalType, newPackage),
			this.createConverterMoveTypeEdits(originalType, newPackage));
	}

	protected Iterable<ReplaceEdit> createConverterMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.converterContainer.createMoveTypeEdits(originalType, newPackage);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
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

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.converterContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}
}
