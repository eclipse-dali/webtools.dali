/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.DiscriminatorType;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.NamedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.NullJpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkVirtualTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmUuidGenerator;
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
	implements EclipseLinkEntityMappings, EclipseLinkOrmConverterContainer.Parent
{
	protected final EclipseLinkOrmConverterContainer converterContainer;

	protected final ContextListContainer<EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3, XmlTenantDiscriminatorColumn> specifiedTenantDiscriminatorColumnContainer;
	protected final EclipseLinkTenantDiscriminatorColumn2_3.ParentAdapter tenantDiscriminatorColumnParentAdapter;

	protected final ContextListContainer<EclipseLinkVirtualTenantDiscriminatorColumn2_3, EclipseLinkTenantDiscriminatorColumn2_3> defaultTenantDiscriminatorColumnContainer;


	protected String specifiedGetMethod;
	protected String defaultGetMethod;

	protected String specifiedSetMethod;
	protected String defaultSetMethod;

	protected final ContextListContainer<EclipseLinkOrmUuidGenerator, XmlUuidGenerator_2_4> uuidGeneratorContainer;

	public EclipseLinkEntityMappingsImpl(OrmXml parent, XmlEntityMappings resource) {
		super(parent, resource);
		this.converterContainer = this.buildConverterContainer();
		this.tenantDiscriminatorColumnParentAdapter = this.buildTenantDiscriminatorColumnParentAdapter();
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
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.converterContainer.update(monitor);
		this.updateModels(this.getSpecifiedTenantDiscriminatorColumns(), monitor);
		this.updateDefaultTenantDiscriminatorColumns(monitor);
		this.setDefaultGetMethod(this.buildDefaultGetMethod());
		this.setDefaultSetMethod(this.buildDefaultSetMethod());
		this.updateModels(this.getUuidGenerators(), monitor);
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

	public EclipseLinkOrmConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected EclipseLinkOrmConverterContainer buildConverterContainer() {
		return new EclipseLinkEntityMappingsConverterContainer(this, (XmlEntityMappings) this.xmlEntityMappings);
	}

	public int getMaximumAllowedConverters() {
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

	public ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getTenantDiscriminatorColumns() {
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

	public ListIterable<EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3> getSpecifiedTenantDiscriminatorColumns() {
		return this.specifiedTenantDiscriminatorColumnContainer;
	}

	protected ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getReadOnlySpecifiedTenantDiscriminatorColumns() {
		return new SuperListIterableWrapper<EclipseLinkTenantDiscriminatorColumn2_3>(this.getSpecifiedTenantDiscriminatorColumns());
	}

	public int getSpecifiedTenantDiscriminatorColumnsSize() {
		return this.specifiedTenantDiscriminatorColumnContainer.size();
	}

	public boolean hasSpecifiedTenantDiscriminatorColumns() {
		return this.getSpecifiedTenantDiscriminatorColumnsSize() != 0;
	}

	public EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 getSpecifiedTenantDiscriminatorColumn(int index) {
		return this.specifiedTenantDiscriminatorColumnContainer.get(index);
	}

	public EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn() {
		return this.addSpecifiedTenantDiscriminatorColumn(this.getSpecifiedTenantDiscriminatorColumnsSize());
	}

	public EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 addSpecifiedTenantDiscriminatorColumn(int index) {
		XmlTenantDiscriminatorColumn xmlJoinColumn = this.buildXmlTenantDiscriminatorColumn();
		EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 joinColumn = this.specifiedTenantDiscriminatorColumnContainer.addContextElement(index, xmlJoinColumn);
		this.getXmlEntityMappings().getTenantDiscriminatorColumns().add(index, xmlJoinColumn);
		return joinColumn;
	}

	protected XmlTenantDiscriminatorColumn buildXmlTenantDiscriminatorColumn() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlTenantDiscriminatorColumn();
	}

	public void removeSpecifiedTenantDiscriminatorColumn(EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		this.removeSpecifiedTenantDiscriminatorColumn(this.specifiedTenantDiscriminatorColumnContainer.indexOf(tenantDiscriminatorColumn));
	}

	public void removeSpecifiedTenantDiscriminatorColumn(int index) {
		this.specifiedTenantDiscriminatorColumnContainer.remove(index);
		this.getXmlEntityMappings().getTenantDiscriminatorColumns().remove(index);
	}

	public void moveSpecifiedTenantDiscriminatorColumn(int targetIndex, int sourceIndex) {
		this.specifiedTenantDiscriminatorColumnContainer.move(targetIndex, sourceIndex);
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

	protected ContextListContainer<EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3, XmlTenantDiscriminatorColumn> buildSpecifiedTenantDiscriminatorColumnContainer() {
		return this.buildSpecifiedContextListContainer(SPECIFIED_TENANT_DISCRIMINATOR_COLUMNS_LIST, new SpecifiedTenantDiscriminatorColumnContainerAdapter());
	}

	/**
	 * specified tenant discriminator column container adapter
	 */
	public class SpecifiedTenantDiscriminatorColumnContainerAdapter
		extends AbstractContainerAdapter<EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3, XmlTenantDiscriminatorColumn>
	{
		public EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 buildContextElement(XmlTenantDiscriminatorColumn resourceElement) {
			return EclipseLinkEntityMappingsImpl.this.buildTenantDiscriminatorColumn(resourceElement);
		}
		public ListIterable<XmlTenantDiscriminatorColumn> getResourceElements() {
			return EclipseLinkEntityMappingsImpl.this.getXmlTenantDiscriminatorColumns();
		}
		public XmlTenantDiscriminatorColumn extractResourceElement(EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 contextElement) {
			return contextElement.getXmlColumn();
		}
	}

	protected EclipseLinkTenantDiscriminatorColumn2_3.ParentAdapter buildTenantDiscriminatorColumnParentAdapter() {
		return new TenantDiscriminatorColumnParentAdapter();
	}

	protected EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 buildTenantDiscriminatorColumn(XmlTenantDiscriminatorColumn xmlTenantDiscriminatorColumn) {
		return new EclipseLinkOrmTenantDiscriminatorColumn2_3(this.tenantDiscriminatorColumnParentAdapter, xmlTenantDiscriminatorColumn);
	}


	// ********** default tenant discriminator columns **********

	public ListIterable<EclipseLinkVirtualTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns() {
		return this.defaultTenantDiscriminatorColumnContainer;
	}

	protected ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getReadOnlyDefaultTenantDiscriminatorColumns() {
		return new SuperListIterableWrapper<EclipseLinkTenantDiscriminatorColumn2_3>(this.getDefaultTenantDiscriminatorColumns());
	}

	public int getDefaultTenantDiscriminatorColumnsSize() {
		return this.defaultTenantDiscriminatorColumnContainer.size();
	}

	protected void clearDefaultTenantDiscriminatorColumns() {
		this.defaultTenantDiscriminatorColumnContainer.clear();
	}

	/**
	 * If there are any specified tenant discriminator columns, then there are no default
	 * tenant discriminator columns.
	 * @see #getTenantDiscriminatorColumnsForDefaults()
	 */
	protected void updateDefaultTenantDiscriminatorColumns(IProgressMonitor monitor) {
		this.defaultTenantDiscriminatorColumnContainer.update(monitor);
	}

	protected ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getTenantDiscriminatorColumnsForDefaults() {
		if (this.getSpecifiedTenantDiscriminatorColumnsSize() > 0) {
			return EmptyListIterable.instance();
		}
		return getContextDefaultTenantDiscriminatorColumns();
	}

	protected ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getContextDefaultTenantDiscriminatorColumns() {
		return this.getPersistenceUnit().getDefaultTenantDiscriminatorColumns();
	}
	
	protected void moveDefaultTenantDiscriminatorColumn(int index, EclipseLinkVirtualTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		this.defaultTenantDiscriminatorColumnContainer.move(index, tenantDiscriminatorColumn);
	}

	protected EclipseLinkVirtualTenantDiscriminatorColumn2_3 addDefaultTenantDiscriminatorColumn(int index, EclipseLinkOrmSpecifiedTenantDiscriminatorColumn2_3 ormTenantDiscriminatorColumn) {
		return this.defaultTenantDiscriminatorColumnContainer.addContextElement(index, ormTenantDiscriminatorColumn);
	}

	protected EclipseLinkVirtualTenantDiscriminatorColumn2_3 buildVirtualTenantDiscriminatorColumn(EclipseLinkTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		return new EclipseLinkOrmVirtualTenantDiscriminatorColumn2_3(this.tenantDiscriminatorColumnParentAdapter, tenantDiscriminatorColumn);
	}

	protected void removeDefaultTenantDiscriminatorColumn(EclipseLinkVirtualTenantDiscriminatorColumn2_3 tenantDiscriminatorColumn) {
		this.defaultTenantDiscriminatorColumnContainer.remove(tenantDiscriminatorColumn);
	}

	protected ContextListContainer<EclipseLinkVirtualTenantDiscriminatorColumn2_3, EclipseLinkTenantDiscriminatorColumn2_3> buildDefaultTenantDiscriminatorColumnContainer() {
		return this.buildVirtualContextListContainer(DEFAULT_TENANT_DISCRIMINATOR_COLUMNS_LIST, new DefaultTenantDiscriminatorColumnContainerAdapter());
	}

	/**
	 * default tenant discriminator column container adapter
	 */
	public class DefaultTenantDiscriminatorColumnContainerAdapter
		extends AbstractContainerAdapter<EclipseLinkVirtualTenantDiscriminatorColumn2_3, EclipseLinkTenantDiscriminatorColumn2_3>
	{
		public EclipseLinkVirtualTenantDiscriminatorColumn2_3 buildContextElement(EclipseLinkTenantDiscriminatorColumn2_3 resourceElement) {
			return EclipseLinkEntityMappingsImpl.this.buildVirtualTenantDiscriminatorColumn(resourceElement);
		}
		public ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getResourceElements() {
			return EclipseLinkEntityMappingsImpl.this.getTenantDiscriminatorColumnsForDefaults();
		}
		public EclipseLinkTenantDiscriminatorColumn2_3 extractResourceElement(EclipseLinkVirtualTenantDiscriminatorColumn2_3 contextElement) {
			return contextElement.getOverriddenColumn();
		}
	}


	// ********** tenant discriminator column parent adapter **********

	public class TenantDiscriminatorColumnParentAdapter
		implements EclipseLinkTenantDiscriminatorColumn2_3.ParentAdapter
	{
		public JpaContextModel getColumnParent() {
			return EclipseLinkEntityMappingsImpl.this;
		}

		public String getDefaultContextPropertyName() {
			return EclipseLinkTenantDiscriminatorColumn2_3.DEFAULT_CONTEXT_PROPERTY;
		}

		public boolean getDefaultPrimaryKey() {
			return EclipseLinkTenantDiscriminatorColumn2_3.DEFAULT_PRIMARY_KEY;
		}

		public int getDefaultLength() {
			return NamedDiscriminatorColumn.DEFAULT_LENGTH;
		}

		public DiscriminatorType getDefaultDiscriminatorType() {
			return NamedDiscriminatorColumn.DEFAULT_DISCRIMINATOR_TYPE;
		}

		/**
		 * No default table name in the context of entity mappings
		 */
		public String getDefaultTableName() {
			return null;
		}

		public String getDefaultColumnName(NamedColumn column) {
			return EclipseLinkTenantDiscriminatorColumn2_3.DEFAULT_NAME;
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
		public JpaValidator buildColumnValidator(NamedColumn column) {
			return NullJpaValidator.instance();
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
		if (ObjectTools.notEquals(this.specifiedGetMethod, getMethod)) {
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
		if (ObjectTools.notEquals(this.specifiedSetMethod, setMethod)) {
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

	public ListIterable<EclipseLinkOrmUuidGenerator> getUuidGenerators() {
		return this.uuidGeneratorContainer;
	}

	public int getUuidGeneratorsSize() {
		return this.uuidGeneratorContainer.size();
	}

	public EclipseLinkOrmUuidGenerator addUuidGenerator() {
		return this.addUuidGenerator(this.getUuidGeneratorsSize());
	}

	public EclipseLinkOrmUuidGenerator addUuidGenerator(int index) {
		XmlUuidGenerator xmlGenerator = this.buildXmlUuidGenerator();
		EclipseLinkOrmUuidGenerator uuidGenerator = this.uuidGeneratorContainer.addContextElement(index, xmlGenerator);
		this.getXmlEntityMappings().getUuidGenerators().add(index, xmlGenerator);
		return uuidGenerator;
	}

	protected XmlUuidGenerator buildXmlUuidGenerator() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlUuidGenerator();
	}

	protected EclipseLinkOrmUuidGenerator buildUuidGenerator(XmlUuidGenerator_2_4 xmlUuidGenerator) {
		return new EclipseLinkOrmUuidGeneratorImpl(this, xmlUuidGenerator);
	}

	public void removeUuidGenerator(EclipseLinkOrmUuidGenerator uuidGenerator) {
		this.removeUuidGenerator(this.uuidGeneratorContainer.indexOf(uuidGenerator));
	}

	public void removeUuidGenerator(int index) {
		this.uuidGeneratorContainer.remove(index);
		this.getXmlEntityMappings().getUuidGenerators().remove(index);
	}

	public void moveUuidGenerator(int targetIndex, int sourceIndex) {
		this.sequenceGeneratorContainer.move(targetIndex, sourceIndex);
		this.xmlEntityMappings.getSequenceGenerators().move(targetIndex, sourceIndex);
	}

	protected void syncUuidGenerators() {
		this.uuidGeneratorContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlUuidGenerator_2_4> getXmlUuidGenerators() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.getXmlEntityMappings().getUuidGenerators());
	}

	protected ContextListContainer<EclipseLinkOrmUuidGenerator, XmlUuidGenerator_2_4> buildUuidGeneratorContainer() {
		return this.buildSpecifiedContextListContainer(UUID_GENERATORS_LIST, new UuidGeneratorContainerAdapter());
	}

	/**
	 * UUID generator container adapter
	 */
	public class UuidGeneratorContainerAdapter
		extends AbstractContainerAdapter<EclipseLinkOrmUuidGenerator, XmlUuidGenerator_2_4>
	{
		public EclipseLinkOrmUuidGenerator buildContextElement(XmlUuidGenerator_2_4 resourceElement) {
			return EclipseLinkEntityMappingsImpl.this.buildUuidGenerator(resourceElement);
		}
		public ListIterable<XmlUuidGenerator_2_4> getResourceElements() {
			return EclipseLinkEntityMappingsImpl.this.getXmlUuidGenerators();
		}
		public XmlUuidGenerator_2_4 extractResourceElement(EclipseLinkOrmUuidGenerator contextElement) {
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
