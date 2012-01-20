/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.NonEmptyStringFilter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextNode;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.MappingFilePersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.MappingFileRoot;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence.ImpliedMappingFileRef;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.ReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkCustomization;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkGeneralProperties;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkSchemaGeneration;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * EclipseLink persistence unit
 */
public class EclipseLinkPersistenceUnit
	extends AbstractPersistenceUnit
{
	protected MappingFileRef impliedEclipseLinkMappingFileRef;
	/**
	 * String constant associated with changes to the implied eclipselink mapping file ref
	 */
	public static final String IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY = "impliedEclipseLinkMappingFileRef"; //$NON-NLS-1$


	private/*final*/ GeneralProperties generalProperties;
	private Customization customization;
	private Caching caching;
	private Logging logging;
	private SchemaGeneration schemaGeneration;

	/* global converter definitions, defined elsewhere in model */
	protected final Vector<EclipseLinkConverter> converters = new Vector<EclipseLinkConverter>();

	protected final Vector<ReadOnlyTenantDiscriminatorColumn2_3> defaultTenantDiscriminatorColumns = new Vector<ReadOnlyTenantDiscriminatorColumn2_3>();

	protected String defaultGetMethod;
	protected String defaultSetMethod;

	public EclipseLinkPersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		super(parent, xmlPersistenceUnit);
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.setConverters(this.buildConverters());

		OrmEclipseLinkPersistenceUnitMetadata metadata = this.getEclipseLinkMetadata();

		EclipseLinkPersistenceUnitDefaults defaults = (metadata == null) ? null : metadata.getPersistenceUnitDefaults();
		this.setDefaultTenantDiscriminatorColumns(this.buildDefaultTenantDiscriminatorColumns(defaults));
	}

	protected OrmEclipseLinkPersistenceUnitMetadata getEclipseLinkMetadata() {
		MappingFilePersistenceUnitMetadata metadata = super.getMetadata();
		if (metadata instanceof OrmEclipseLinkPersistenceUnitMetadata) {
			return (OrmEclipseLinkPersistenceUnitMetadata) metadata;
		}
		return null;
	}

	@Override
	protected void updatePersistenceUnitMetadata() {
		super.updatePersistenceUnitMetadata();
		OrmEclipseLinkPersistenceUnitMetadata metadata = this.getEclipseLinkMetadata();

		EclipseLinkPersistenceUnitDefaults defaults = (metadata == null) ? null : metadata.getPersistenceUnitDefaults();
		this.setDefaultGetMethod(this.buildDefaultGetMethod(defaults));
		this.setDefaultSetMethod(this.buildDefaultSetMethod(defaults));
	}

	// ********** properties **********

	public GeneralProperties getGeneralProperties() {
		return this.generalProperties;
	}

	@Override
	public Connection getConnection() {
		return (Connection) super.getConnection();
	}

	public Customization getCustomization() {
		return this.customization;
	}

	public Caching getCaching() {
		return this.caching;
	}

	public String getDefaultCacheTypePropertyValue() {
		Property cacheTypeDefaultProperty = getCacheTypeDefaultProperty();
		return cacheTypeDefaultProperty != null ? cacheTypeDefaultProperty.getValue() : null;
	}
	
	public String getDefaultCacheSizePropertyValue() {
		Property cacheSizeDefaultProperty = getCacheSizeDefaultProperty();
		return cacheSizeDefaultProperty != null ? cacheSizeDefaultProperty.getValue() : null;
	}
	
	public String getDefaultCacheSharedPropertyValue() {
		Property cacheSharedDefaultProperty = getCacheSharedDefaultProperty();
		return cacheSharedDefaultProperty != null ? cacheSharedDefaultProperty.getValue() : null;
	}
	
	public Logging getLogging() {
		return this.logging;
	}

	@Override
	public Options getOptions() {
		return (Options) super.getOptions();
	}

	public SchemaGeneration getSchemaGeneration() {
		return this.schemaGeneration;
	}
	
	protected GeneralProperties buildEclipseLinkGeneralProperties() {
		return new EclipseLinkGeneralProperties(this);
	}

	protected Connection buildEclipseLinkConnection() {
		return (Connection) this.getContextNodeFactory().buildConnection(this);
	}

	protected Customization buildEclipseLinkCustomization() {
		return new EclipseLinkCustomization(this);
	}

	protected Caching buildEclipseLinkCaching() {
		return new EclipseLinkCaching(this);
	}

	protected Logging buildEclipseLinkLogging() {
		return (Logging) this.getContextNodeFactory().buildLogging(this);
	}

	protected Options buildEclipseLinkOptions() {
		return (Options) this.getContextNodeFactory().buildOptions(this);
	}

	protected SchemaGeneration buildEclipseLinkSchemaGeneration() {
		return new EclipseLinkSchemaGeneration(this);
	}

	@Override
	protected void initializeProperties() {
		super.initializeProperties();
		this.generalProperties = this.buildEclipseLinkGeneralProperties();
		this.customization = this.buildEclipseLinkCustomization();
		this.caching = this.buildEclipseLinkCaching();
		this.logging = this.buildEclipseLinkLogging();
		this.schemaGeneration = this.buildEclipseLinkSchemaGeneration();
	}

	@Override
	public void propertyValueChanged(String propertyName, String newValue) {
		super.propertyValueChanged(propertyName, newValue);
		this.generalProperties.propertyValueChanged(propertyName, newValue);
		this.customization.propertyValueChanged(propertyName, newValue);
		this.caching.propertyValueChanged(propertyName, newValue);
		this.logging.propertyValueChanged(propertyName, newValue);
		this.schemaGeneration.propertyValueChanged(propertyName, newValue);
	}

	@Override
	public void propertyRemoved(String propertyName) {
		super.propertyRemoved(propertyName);
		this.generalProperties.propertyRemoved(propertyName);
		this.customization.propertyRemoved(propertyName);
		this.caching.propertyRemoved(propertyName);
		this.logging.propertyRemoved(propertyName);
		this.schemaGeneration.propertyRemoved(propertyName);
	}


	// ********** mapping file refs **********

	@Override
	public ListIterable<MappingFileRef> getMappingFileRefs() {
		return (this.impliedEclipseLinkMappingFileRef == null) ?
				super.getMappingFileRefs() :
				new CompositeListIterable<MappingFileRef>(super.getMappingFileRefs(), this.impliedEclipseLinkMappingFileRef);
	}

	@Override
	public int getMappingFileRefsSize() {
		return (this.impliedEclipseLinkMappingFileRef == null) ?
				super.getMappingFileRefsSize() :
				super.getMappingFileRefsSize() + 1;
	}


	// ********** implied eclipselink mapping file ref **********

	public MappingFileRef getImpliedEclipseLinkMappingFileRef() {
		return this.impliedEclipseLinkMappingFileRef;
	}

	protected MappingFileRef addImpliedEclipseLinkMappingFileRef() {
		if (this.impliedEclipseLinkMappingFileRef != null) {
			throw new IllegalStateException("The implied EclipseLink mapping file ref is already present: " + this.impliedEclipseLinkMappingFileRef); //$NON-NLS-1$
		}
		MappingFileRef mappingFileRef = this.buildEclipseLinkImpliedMappingFileRef();
		this.impliedEclipseLinkMappingFileRef = mappingFileRef;
		this.firePropertyChanged(IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY, null, mappingFileRef);
		return mappingFileRef;
	}

	private ImpliedMappingFileRef buildEclipseLinkImpliedMappingFileRef() {
		return new ImpliedMappingFileRef(this, JptJpaEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_RUNTIME_PATH.toString());
	}

	protected void removeImpliedEclipseLinkMappingFileRef() {
		if (this.impliedEclipseLinkMappingFileRef == null) {
			throw new IllegalStateException("The implied EclipseLink mapping file ref is null."); //$NON-NLS-1$
		}
		MappingFileRef mappingFileRef = this.impliedEclipseLinkMappingFileRef;
		this.impliedEclipseLinkMappingFileRef.dispose();
		this.impliedEclipseLinkMappingFileRef = null;
		this.firePropertyChanged(IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY, mappingFileRef, null);
	}

	@Override
	protected void syncImpliedMappingFileRef() {
		super.syncImpliedMappingFileRef();
		if (this.impliedEclipseLinkMappingFileRef != null) {
			this.impliedEclipseLinkMappingFileRef.synchronizeWithResourceModel();
		}
	}

	@Override
	protected void updateImpliedMappingFileRef() {
		super.updateImpliedMappingFileRef();

		if (this.buildsImpliedEclipseLinkMappingFile()) {
			if (this.impliedEclipseLinkMappingFileRef == null) {
				this.addImpliedEclipseLinkMappingFileRef();
			} else {
				this.impliedEclipseLinkMappingFileRef.update();
			}
		} else {
			if (this.impliedEclipseLinkMappingFileRef != null) {
				this.removeImpliedEclipseLinkMappingFileRef();
			}
		}
	}

	/**
	 * Build a virtual EclipseLink mapping file if all the following are true:<ul>
	 * <li>the properties do not explicitly exclude it
	 * <li>it is not specified explicitly in the persistence unit
	 * <li>the file actually exists
	 * </ul>
	 */
	private boolean buildsImpliedEclipseLinkMappingFile() {
		return this.impliedEclipseLinkMappingFileIsNotExcluded() &&
				this.impliedEclipseLinkMappingFileIsNotSpecified() &&
				this.impliedEclipseLinkMappingFileExists();
	}

	protected boolean impliedEclipseLinkMappingFileIsNotExcluded() {
		return ! this.impliedEclipseLinkMappingFileIsExcluded();
	}

	protected boolean impliedEclipseLinkMappingFileIsExcluded() {
		return this.getGeneralProperties().getExcludeEclipselinkOrm() == Boolean.TRUE;
	}

	protected boolean impliedEclipseLinkMappingFileIsNotSpecified() {
		return ! this.impliedEclipseLinkMappingFileIsSpecified();
	}

	protected boolean impliedEclipseLinkMappingFileIsSpecified() {
		return this.mappingFileIsSpecified(JptJpaEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_RUNTIME_PATH.toString());
	}

	protected boolean impliedEclipseLinkMappingFileExists() {
		return this.getJpaProject().getDefaultEclipseLinkOrmXmlResource() != null;
	}


	// ********** default tenant discriminator columns **********

	/**
	 * String constant associated with changes to the persistence unit's
	 * list of default tenant discriminator Columns
	 */
	public static final String DEFAULT_TENANT_DISCRIMINATOR_COLUMNS_LIST = "defaultTenantDiscriminatorColumns"; //$NON-NLS-1$

	public ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns() {
		return new LiveCloneListIterable<ReadOnlyTenantDiscriminatorColumn2_3>(this.defaultTenantDiscriminatorColumns);
	}

	protected void setDefaultTenantDiscriminatorColumns(Iterable<ReadOnlyTenantDiscriminatorColumn2_3> tenantDiscriminatorColumns) {
		this.synchronizeList(tenantDiscriminatorColumns, this.defaultTenantDiscriminatorColumns, DEFAULT_TENANT_DISCRIMINATOR_COLUMNS_LIST);
	}

	protected ListIterable<ReadOnlyTenantDiscriminatorColumn2_3> buildDefaultTenantDiscriminatorColumns(EclipseLinkPersistenceUnitDefaults defaults) {
		return (defaults == null) ? EmptyListIterable.<ReadOnlyTenantDiscriminatorColumn2_3> instance() : new SuperListIterableWrapper<ReadOnlyTenantDiscriminatorColumn2_3>(defaults.getTenantDiscriminatorColumns());
	}


	// ********** converters **********

	/**
	 * String constant associated with changes to the persistence unit's
	 * collection of "global" converters.
	 */
	public static final String CONVERTERS_COLLECTION = "converters"; //$NON-NLS-1$

	/**
	 * Return the "active" converters defined within the persistence unit's scope,
	 * including converters with duplicate names. "Active" converters are:<ul>
	 * <li>any converter defined in mapping files
	 * <li>any converter defined via Java annotations that is not "overridden"
	 *     by a mapping file converter with the same name
	 * </ul>
	 * <strong>NB:</strong> A Java converter defined on a class or attribute
	 * that is overridden in a mapping file is <em>not</em>, as a result,
	 * itself overridden. A Java converter can only be overridden by a mapping
	 * file converter with the same name.
	 * <p>
	 * <strong>NB:</strong> A Java converter defined on a class or attribute
	 * whose corresponding mapping file mapping (or mapping file) is marked
	 * "metadata complete" is ignored.
	 */
	// TODO bjv change to getConverterNames() etc.
	public Iterable<EclipseLinkConverter> getAllConverters() {
		return this.getConverters();
	}

	public Iterable<EclipseLinkConverter> getConverters() {
		return new LiveCloneIterable<EclipseLinkConverter>(this.converters);
	}

	public int getConvertersSize() {
		return this.converters.size();
	}

	/**
	 * Return the names of the "active" converters defined in the persistence
	 * unit's scope, with duplicates removed.
	 */
	public Iterable<String> getUniqueConverterNames() {
		return CollectionTools.set(this.getNonEmptyConverterNames(), this.getConvertersSize());
	}

	protected Iterable<String> getNonEmptyConverterNames() {
		return new FilteringIterable<String>(this.getConverterNames(), NonEmptyStringFilter.instance());
	}

	protected Iterable<String> getConverterNames() {
		return new TransformationIterable<EclipseLinkConverter, String>(this.getConverters(), JpaNamedContextNode.NameTransformer.<EclipseLinkConverter>instance());
	}

	protected void setConverters(Iterable<EclipseLinkConverter> converters) {
		this.synchronizeCollection(converters, this.converters, CONVERTERS_COLLECTION);
	}

	/**
	 * Converters are much like queries.
	 * @see #buildQueries()
	 */
	protected Iterable<EclipseLinkConverter> buildConverters() {
		ArrayList<EclipseLinkConverter> result = CollectionTools.list(this.getMappingFileConverters());

		HashSet<String> mappingFileConverterNames = this.convertToNames(result);
		HashMap<String, ArrayList<EclipseLinkConverter>> javaConverters = this.mapByName(this.getAllJavaConverters());
		for (Map.Entry<String, ArrayList<EclipseLinkConverter>> entry : javaConverters.entrySet()) {
			if ( ! mappingFileConverterNames.contains(entry.getKey())) {
				result.addAll(entry.getValue());
			}
		}

		return result;
	}

	protected Iterable<EclipseLinkConverter> getMappingFileConverters() {
		return new CompositeIterable<EclipseLinkConverter>(this.getMappingFileConverterLists());
	}

	protected Iterable<Iterable<EclipseLinkConverter>> getMappingFileConverterLists() {
		return new TransformationIterable<MappingFile, Iterable<EclipseLinkConverter>>(this.getMappingFiles()) {
					@Override
					protected Iterable<EclipseLinkConverter> transform(MappingFile mappingFile) {
						MappingFileRoot root = mappingFile.getRoot();
						return (root instanceof EclipseLinkEntityMappings) ?
								((EclipseLinkEntityMappings) root).getMappingFileConverters() :
								EmptyIterable.<EclipseLinkConverter>instance();
					}
				};
	}

	/**
	 * Include "overridden" Java converters.
	 */
	public Iterable<EclipseLinkConverter> getAllJavaConverters() {
		return new CompositeIterable<EclipseLinkConverter>(this.getAllJavaTypeMappingConverterLists());
	}

	protected Iterable<Iterable<EclipseLinkConverter>> getAllJavaTypeMappingConverterLists() {
		return new TransformationIterable<TypeMapping, Iterable<EclipseLinkConverter>>(this.getAllJavaTypeMappingsUnique()) {
					@Override
					protected Iterable<EclipseLinkConverter> transform(TypeMapping typeMapping) {
						// Java "null" type mappings are not EclipseLink mappings
						return (typeMapping instanceof EclipseLinkTypeMapping) ?
								((EclipseLinkTypeMapping) typeMapping).getConverters() :
								EmptyIterable.<EclipseLinkConverter>instance();
					}
				};
	}


	// ********** misc **********

	@Override
	public EclipseLinkJpaProject getJpaProject() {
		return (EclipseLinkJpaProject) super.getJpaProject();
	}

	@Override
	public EclipseLinkPersistenceXmlContextNodeFactory getContextNodeFactory() {
		return (EclipseLinkPersistenceXmlContextNodeFactory) super.getContextNodeFactory();
	}

	@Override
	public void setSpecifiedSharedCacheMode(SharedCacheMode specifiedSharedCacheMode) {
		super.setSpecifiedSharedCacheMode(specifiedSharedCacheMode);
		
		if(specifiedSharedCacheMode == SharedCacheMode.NONE) {
			this.caching.removeDefaultCachingProperties();
		}
	}
	
	@Override
	protected SharedCacheMode buildDefaultSharedCacheMode() {
		return SharedCacheMode.DISABLE_SELECTIVE;
	}

	@Override
	public boolean calculateDefaultCacheable() {
		SharedCacheMode sharedCacheMode = this.getSharedCacheMode();
		if (sharedCacheMode == null) {
			return true;
		}
		switch (sharedCacheMode) {
			case NONE:
			case ENABLE_SELECTIVE:
				return false;
			case ALL:
			case DISABLE_SELECTIVE:
			case UNSPECIFIED:
				return true;
		}
		return true;
	}


	public String getDefaultGetMethod() {
		return this.defaultGetMethod;
	}

	protected void setDefaultGetMethod(String getMethod) {
		String old = this.defaultGetMethod;
		this.defaultGetMethod = getMethod;
		this.firePropertyChanged(DEFAULT_GET_METHOD_PROPERTY, old, getMethod);
	}

	/**
	 * String constant associated with changes to the persistence unit's
	 * default get method.
	 */
	public static final String DEFAULT_GET_METHOD_PROPERTY = "defaultGetMethod"; //$NON-NLS-1$

	protected String buildDefaultGetMethod(EclipseLinkPersistenceUnitDefaults defaults) {
		String getMethod = (defaults == null) ? null : defaults.getGetMethod();
		return (getMethod != null) ? getMethod : null;
	}

	public String getDefaultSetMethod() {
		return this.defaultSetMethod;
	}

	protected void setDefaultSetMethod(String setMethod) {
		String old = this.defaultSetMethod;
		this.defaultSetMethod = setMethod;
		this.firePropertyChanged(DEFAULT_SET_METHOD_PROPERTY, old, setMethod);
	}

	/**
	 * String constant associated with changes to the persistence unit's
	 * default set method.
	 */
	public static final String DEFAULT_SET_METHOD_PROPERTY = "defaultSetMethod"; //$NON-NLS-1$

	protected String buildDefaultSetMethod(EclipseLinkPersistenceUnitDefaults defaults) {
		String setMethod = (defaults == null) ? null : defaults.getSetMethod();
		return (setMethod != null) ? setMethod : null;
	}

	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateConverters(messages, reporter);
	}

	@Override
	protected void validateProperties(List<IMessage> messages, IReporter reporter) {

		if(this.isJpa2_0Compatible()) {
			for(Property property: this.getLegacyEntityCachingProperties()) {
				messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						EclipseLinkJpaValidationMessages.PERSISTENCE_UNIT_LEGACY_ENTITY_CACHING,
						new String[] {property.getName()},
						this.getPersistenceUnit(),
						property.getValidationTextRange()
					)
				);
			}
			for(Property property: this.getLegacyDescriptorCustomizerProperties()) {
				messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						EclipseLinkJpaValidationMessages.PERSISTENCE_UNIT_LEGACY_DESCRIPTOR_CUSTOMIZER,
						new String[] {property.getName()},
						this.getPersistenceUnit(),
						property.getValidationTextRange()
					)
				);
			}
			this.validateDefaultCachingProperty(this.getCacheTypeDefaultProperty(), messages);
			this.validateDefaultCachingProperty(this.getCacheSizeDefaultProperty(), messages);
			this.validateDefaultCachingProperty(this.getFlushClearCacheProperty(), messages);
			this.validateLoggerProperty(this.getLoggerProperty(), messages);
			this.validateExceptionHandlerProperty(this.getExceptionHandlerProperty(), messages);
			this.validatePerformanceProfilerProperty(this.getPerformanceProfilerProperty(), messages);
			this.validateSessionCustomizerProperty(this.getSessionCustomizerProperties(), messages);
		}
	}

	protected void validateDefaultCachingProperty(Property cachingProperty, List<IMessage> messages) {
		
		if(this.getSharedCacheMode() == SharedCacheMode.NONE) {
			if(cachingProperty != null) {
				messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						EclipseLinkJpaValidationMessages.PERSISTENCE_UNIT_CACHING_PROPERTY_IGNORED,
						new String[] {cachingProperty.getName()},
						this.getPersistenceUnit(),
						cachingProperty.getValidationTextRange()
					)
				);
			}
		}
	}
	
	protected void validateLoggerProperty(Property loggerProperty, List<IMessage> messages) {
		if ((loggerProperty == null) || (loggerProperty.getValue() == null) ) {
			return;
		}

		if (ArrayTools.contains(Logging.RESERVED_LOGGER_NAMES, loggerProperty.getValue())) {
			return;
		}

		IJavaProject javaProject = getJpaProject().getJavaProject();
		if (StringTools.stringIsEmpty(loggerProperty.getValue())) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							EclipseLinkJpaValidationMessages.SESSION_LOGGER_CLASS_NOT_SPECIFIED,
							EMPTY_STRING_ARRAY,
							this.getPersistenceUnit(),
							loggerProperty.getValidationTextRange()
					)
			);
		} else if (JDTTools.findType(javaProject, loggerProperty.getValue()) == null) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							EclipseLinkJpaValidationMessages.SESSION_LOGGER_CLASS_NOT_EXIST,
							new String[] {loggerProperty.getValue()},
							this.getPersistenceUnit(),
							loggerProperty.getValidationTextRange()
					)
			);
		} else if (!JDTTools.typeIsSubType(
				javaProject, loggerProperty.getValue(), Logging.ECLIPSELINK_LOGGER_CLASS_NAME)
		) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							EclipseLinkJpaValidationMessages.SESSION_LOGGER_CLASS_IMPLEMENTS_SESSION_LOG,
							new String[] {loggerProperty.getValue()},
							this.getPersistenceUnit(),
							loggerProperty.getValidationTextRange()
					)
			);
		}
	}

	private void validateExceptionHandlerProperty(Property handlerProperty, List<IMessage> messages) {
		if ((handlerProperty == null) || (handlerProperty.getValue() == null) ) {
			return;
		}

		IJavaProject javaProject = getJpaProject().getJavaProject();
		if (StringTools.stringIsEmpty(handlerProperty.getValue())) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							EclipseLinkJpaValidationMessages.EXCEPTION_HANDLER_CLASS_NOT_SPECIFIED,
							EMPTY_STRING_ARRAY,
							this.getPersistenceUnit(),
							handlerProperty.getValidationTextRange()
					)
			);
		} else if (JDTTools.findType(javaProject, handlerProperty.getValue()) == null) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							EclipseLinkJpaValidationMessages.EXCEPTION_HANDLER_CLASS_NOT_EXIST,
							new String[] {handlerProperty.getValue()},
							this.getPersistenceUnit(),
							handlerProperty.getValidationTextRange()
					)
			);
		} else if (!JDTTools.classHasPublicZeroArgConstructor(javaProject, handlerProperty.getValue())) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							EclipseLinkJpaValidationMessages.EXCEPTION_HANDLER_CLASS_NOT_VALID,
							new String[] {handlerProperty.getValue()},
							this.getPersistenceUnit(),
							handlerProperty.getValidationTextRange()
					)
			);
		} else if (!JDTTools.typeIsSubType(
				javaProject, handlerProperty.getValue(), Customization.ECLIPSELINK_EXCEPTION_HANDLER_CLASS_NAME)
		) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							EclipseLinkJpaValidationMessages.EXCEPTION_HANDLER_CLASS_IMPLEMENTS_EXCEPTION_HANDLER,
							new String[] {handlerProperty.getValue()},
							this.getPersistenceUnit(),
							handlerProperty.getValidationTextRange()
					)
			);
		}
	}

	private void validatePerformanceProfilerProperty(Property profilerProperty, List<IMessage> messages) {

		if ((profilerProperty == null) || (profilerProperty.getValue() == null) ) {
			return;
		}

		if (ArrayTools.contains(Customization.RESERVED_PROFILER_NAMES, profilerProperty.getValue())) {
			return;
		}

		IJavaProject javaProject = getJpaProject().getJavaProject();
		if (StringTools.stringIsEmpty(profilerProperty.getValue())) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							EclipseLinkJpaValidationMessages.SESSION_PROFILER_CLASS_NOT_SPECIFIED,
							EMPTY_STRING_ARRAY,
							this.getPersistenceUnit(),
							profilerProperty.getValidationTextRange()
					)
			);
		} else if (JDTTools.findType(javaProject, profilerProperty.getValue()) == null) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							EclipseLinkJpaValidationMessages.SESSION_PROFILER_CLASS_NOT_EXIST,
							new String[] {profilerProperty.getValue()},
							this.getPersistenceUnit(),
							profilerProperty.getValidationTextRange()
					)
			);
		} else if (!JDTTools.classHasPublicZeroArgConstructor(javaProject, profilerProperty.getValue())){
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							EclipseLinkJpaValidationMessages.SESSION_PROFILER_CLASS_NOT_VALID,
							new String[] {profilerProperty.getValue()},
							this.getPersistenceUnit(),
							profilerProperty.getValidationTextRange()
					)
			);
		} else if (!JDTTools.typeIsSubType(
				javaProject, profilerProperty.getValue(), Customization.ECLIPSELINK_SESSION_PROFILER_CLASS_NAME)
		) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							EclipseLinkJpaValidationMessages.SESSION_PROFILER_CLASS_IMPLEMENTS_SESSIONP_ROFILER,
							new String[] {profilerProperty.getValue()},
							this.getPersistenceUnit(),
							profilerProperty.getValidationTextRange()
					)
			);
		}
	}

	private void validateSessionCustomizerProperty(	Iterable<Property> properties, List<IMessage> messages) {
		for (Property property : properties) {
			if (property.getValue() == null) {
				return;
			}

			IJavaProject javaProject = getJpaProject().getJavaProject();
				if (StringTools.stringIsEmpty(property.getValue())) {
					messages.add(
							DefaultEclipseLinkJpaValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									EclipseLinkJpaValidationMessages.SESSION_CUSTOMIZER_CLASS_NOT_SPECIFIED,
									EMPTY_STRING_ARRAY,
									this.getPersistenceUnit(),
									property.getValidationTextRange()
							)
					);
				} else if (JDTTools.findType(javaProject, property.getValue()) == null) {
					messages.add(
							DefaultEclipseLinkJpaValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									EclipseLinkJpaValidationMessages.SESSION_CUSTOMIZER_CLASS_NOT_EXIST,
									new String[] {property.getValue()},
									this.getPersistenceUnit(),
									property.getValidationTextRange()
							)
					);
				} else if (!JDTTools.classHasPublicZeroArgConstructor(javaProject, property.getValue())){
					messages.add(
							DefaultEclipseLinkJpaValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									EclipseLinkJpaValidationMessages.SESSION_CUSTOMIZER_CLASS_NOT_VALID,
									new String[] {property.getValue()},
									this.getPersistenceUnit(),
									property.getValidationTextRange()
							)
					);
				} else if (!JDTTools.typeIsSubType(
						javaProject, property.getValue(), Customization.ECLIPSELINK_SESSION_CUSTOMIZER_CLASS_NAME)
				) {
					messages.add(
							DefaultEclipseLinkJpaValidationMessages.buildMessage(
									IMessage.HIGH_SEVERITY,
									EclipseLinkJpaValidationMessages.SESSION_CUSTOMIZER_CLASS_IMPLEMENTS_SESSION_CUSTOMIZER,
									new String[] {property.getValue()},
									this.getPersistenceUnit(),
									property.getValidationTextRange()
							)
					);
				}
		}
	}
	

	protected ArrayList<Property> getLegacyDescriptorCustomizerProperties() {
		ArrayList<Property> result = new ArrayList<Property>();
		CollectionTools.addAll(result, this.getDescriptorCustomizerProperties());
		return result;
	}

	protected ArrayList<Property> getLegacyEntityCachingProperties() {
		ArrayList<Property> result = new ArrayList<Property>();
		CollectionTools.addAll(result, this.getSharedCacheProperties());
		CollectionTools.addAll(result, this.getEntityCacheTypeProperties());
		CollectionTools.addAll(result, this.getEntityCacheSizeProperties());
		return result;
	}

	private Property getCacheTypeDefaultProperty() {
		return this.getProperty(Caching.ECLIPSELINK_CACHE_TYPE_DEFAULT);
	}

	private Property getCacheSizeDefaultProperty() {
		return this.getProperty(Caching.ECLIPSELINK_CACHE_SIZE_DEFAULT);
	}
	
	private Property getCacheSharedDefaultProperty() {
		return this.getProperty(Caching.ECLIPSELINK_CACHE_SHARED_DEFAULT);
	}

	private Property getFlushClearCacheProperty() {
		return this.getProperty(Caching.ECLIPSELINK_FLUSH_CLEAR_CACHE);
	}

	/**
	 * Returns all Shared Cache Properties, including Entity and default.
	 */
	private Iterable<Property> getSharedCacheProperties() {
		return this.getPropertiesWithNamePrefix(Caching.ECLIPSELINK_SHARED_CACHE);
	}

	/**
	 * Returns Entity Cache Size Properties, excluding default.
	 */
	private Iterable<Property> getEntityCacheSizeProperties() {
		return this.getEntityPropertiesWithPrefix(Caching.ECLIPSELINK_CACHE_SIZE);
	}
	
	/**
	 * Returns Entity Cache Type Properties, excluding default.
	 */
	private Iterable<Property> getEntityCacheTypeProperties() {
		return this.getEntityPropertiesWithPrefix(Caching.ECLIPSELINK_CACHE_TYPE);
	}
	
	/**
	 * Returns Descriptor Customizer Properties.
	 */
	private Iterable<Property> getDescriptorCustomizerProperties() {
		return this.getEntityPropertiesWithPrefix(Customization.ECLIPSELINK_DESCRIPTOR_CUSTOMIZER);
	}
	
	/**
	 * Returns Entity Properties with the given prefix,
	 * excluding Entity which name equals "default".
	 */
	private Iterable<Property> getEntityPropertiesWithPrefix(String prefix) {
	   return new FilteringIterable<Property>(this.getPropertiesWithNamePrefix(prefix)) {
	      @Override
	      protected boolean accept(Property next) {
				return ! next.getName().endsWith("default"); //$NON-NLS-1$
	      }
	   };
	}

	private Property getLoggerProperty() {
		return this.getProperty(Logging.ECLIPSELINK_LOGGER);
	}
	
	private Property getExceptionHandlerProperty() {
		return this.getProperty(Customization.ECLIPSELINK_EXCEPTION_HANDLER);
	}

	private Property getPerformanceProfilerProperty() {
		return this.getProperty(Customization.ECLIPSELINK_PROFILER);
	}

	/**
	 * Returns all Session Customizer Properties.
	 */
	private Iterable<Property> getSessionCustomizerProperties() {
		return this.getPropertiesWithNamePrefix(Customization.ECLIPSELINK_SESSION_CUSTOMIZER);
	}

	/**
	 * <strong>NB:</strong> We validate converters here.
	 * @see #validateGenerators(List, IReporter)
	 */
	protected void validateConverters(List<IMessage> messages, IReporter reporter) {
		this.checkForDuplicateConverters(messages);
		for (EclipseLinkConverter converter : this.getConverters()) {
			this.validate(converter, messages, reporter);
		}
	}

	protected void checkForDuplicateConverters(List<IMessage> messages) {
		HashMap<String, ArrayList<EclipseLinkConverter>> convertersByName = this.mapByName(this.getConverters());
		for (Map.Entry<String, ArrayList<EclipseLinkConverter>> entry : convertersByName.entrySet()) {
			String converterName = entry.getKey();
			if (StringTools.stringIsNotEmpty(converterName)) {  // ignore empty names
				ArrayList<EclipseLinkConverter> dups = entry.getValue();
				if (dups.size() > 1) {
					// if duplicate name exists, check the types of the converters with the duplicate name
					HashMap<Class<? extends JpaNamedContextNode>, ArrayList<EclipseLinkConverter>> convertersByType = this.mapByType(dups);
					// if more than one types of converters have the same name, 
					// report duplicate error on every converter in the list;
					if (convertersByType.size() > 1) {
						String[] parms = new String[] {converterName};
						for (EclipseLinkConverter dup : dups) {
							messages.add(
									DefaultEclipseLinkJpaValidationMessages.buildMessage(
											IMessage.HIGH_SEVERITY,
											EclipseLinkJpaValidationMessages.CONVERTER_DUPLICATE_NAME,
											parms,
											dup,
											this.extractNameTextRange(dup)
											)
									);
						}
					} else {
						// otherwise if all the converters are with the same type, check every converter
						// to see if its definition is not identical with any one of the converters in the list;
						// if yes, report duplicate error on it; if not, doing nothing
						for (EclipseLinkConverter dup : dups) {
							String[] parms = new String[] {dup.getName()};
							if (hasDuplicateConverter(dup, dups)) {
								messages.add(
										DefaultEclipseLinkJpaValidationMessages.buildMessage(
												IMessage.HIGH_SEVERITY,
												EclipseLinkJpaValidationMessages.CONVERTER_DUPLICATE_NAME,
												parms,
												dup,
												this.extractNameTextRange(dup)
												)
										);
							}
						}
					}
				}
			}
		}
	}

	private boolean hasDuplicateConverter(EclipseLinkConverter converter, ArrayList<EclipseLinkConverter> converters) {
		boolean isDuplicate = false;
		for (int i=0; i<converters.size(); i++) {
			if (converter != converters.get(i) && !converter.isIdentical(converters.get(i))) {
				isDuplicate = true;
			}
		}
		return isDuplicate;
	}

	// TODO bjv isn't it obvious?
	protected TextRange extractNameTextRange(EclipseLinkConverter converter) {
		return (converter instanceof OrmEclipseLinkConverter<?>) ?
				((OrmEclipseLinkConverter<?>) converter).getNameTextRange() :
				((JavaEclipseLinkConverter<?>) converter).getNameTextRange(null);
	}

	// TODO bjv isn't it obvious?
	protected void validate(EclipseLinkConverter converter, List<IMessage> messages, IReporter reporter) {
		if (converter instanceof OrmEclipseLinkConverter<?>) {
			((OrmEclipseLinkConverter<?>) converter).validate(messages, reporter);
		} else {
			((JavaEclipseLinkConverter<?>) converter).validate(messages, reporter, null);
		}
	}

	@Override
	protected void checkForDuplicateGenerators(List<IMessage> messages) {		
		HashMap<String, ArrayList<Generator>> generatorsByName = this.mapByName(this.getGenerators());
		for (Map.Entry<String, ArrayList<Generator>> entry : generatorsByName.entrySet()) {
			String generatorName = entry.getKey();
			if (StringTools.stringIsNotEmpty(generatorName)) {  // ignore empty names
				ArrayList<Generator> dups = entry.getValue();
				if (dups.size() > 1) {
					// if duplicate name exists, check the types of the generators with the duplicate name
					HashMap<Class<? extends JpaNamedContextNode>, ArrayList<Generator>> generatorsByType = this.mapByType(dups);
					// if more than one types of generators have the same name, 
					// report duplicate error on every generator in the list;
					if (generatorsByType.size() > 1) {
						String[] parms = new String[] {generatorName};
						for (Generator dup : dups) {
							messages.add(
									DefaultEclipseLinkJpaValidationMessages.buildMessage(
											IMessage.HIGH_SEVERITY,
											EclipseLinkJpaValidationMessages.GENERATOR_DUPLICATE_NAME,
											parms,
											dup,
											this.extractNameTextRange(dup)
											)
									);
						}
					} else {
						// otherwise if all the generators are with the same type, check every generator
						// to see if its definition is not identical with any one of the generators in the list;
						// if yes, report duplicate error on it;
						for (Generator dup : dups) {
							String[] parms = new String[] {dup.getName()};
							if (hasDuplicateGenerator(dup, dups)) {
								messages.add(
										DefaultEclipseLinkJpaValidationMessages.buildMessage(
												IMessage.HIGH_SEVERITY,
												EclipseLinkJpaValidationMessages.GENERATOR_DUPLICATE_NAME,
												parms,
												dup,
												this.extractNameTextRange(dup)
												)
										);
							} else {
								// if not, report identical warning on it
								messages.add(
										DefaultEclipseLinkJpaValidationMessages.buildMessage(
												IMessage.LOW_SEVERITY,
												EclipseLinkJpaValidationMessages.GENERATOR_IDENTICAL,
												parms,
												dup,
												this.extractNameTextRange(dup)
												)
										);
							}
						}
					}
				}
			}
		}
	}

	private boolean hasDuplicateGenerator(Generator generator, ArrayList<Generator> generators) {
		boolean isDuplicate = false;
		for (int i=0; i<generators.size(); i++) {
			if (generator != generators.get(i) && !generator.isIdentical(generators.get(i))) {
				isDuplicate = true;
			}
		}
		return isDuplicate;
	}

	@Override
	protected void checkForDuplicateQueries(List<IMessage> messages) {
		HashMap<String, ArrayList<Query>> queriesByName = this.mapByName(this.getQueries());
		for (Map.Entry<String, ArrayList<Query>> entry : queriesByName.entrySet()) {
			String queryName = entry.getKey();
			if (StringTools.stringIsNotEmpty(queryName)) {  // ignore empty names
				ArrayList<Query> dups = entry.getValue();
				if (dups.size() > 1) {
					// if duplicate name exists, check the types of the queries with the duplicate name
					HashMap<Class<? extends JpaNamedContextNode>, ArrayList<Query>> querisByType = this.mapByType(dups);
					// if more than one types of queries have the same name, 
					// report duplicate error on every query in the list;
					if (querisByType.size() > 1) {
						String[] parms = new String[] {queryName};
						for (Query dup : dups) {
							messages.add(
									DefaultEclipseLinkJpaValidationMessages.buildMessage(
											IMessage.HIGH_SEVERITY,
											EclipseLinkJpaValidationMessages.QUERY_DUPLICATE_NAME,
											parms,
											dup,
											this.extractNameTextRange(dup)
											)
									);
						}
					} else {
						// otherwise if all the queries are with the same type, check every query
						// to see if its definition is not identical with any one of the queries in the list;
						// if yes, report duplicate error on it;
						for (Query dup : dups) {
							String[] parms = new String[] {dup.getName()};
							if (hasDuplicateQuery(dup, dups)) {
								messages.add(
										DefaultEclipseLinkJpaValidationMessages.buildMessage(
												IMessage.HIGH_SEVERITY,
												EclipseLinkJpaValidationMessages.QUERY_DUPLICATE_NAME,
												parms,
												dup,
												this.extractNameTextRange(dup)
												)
										);
							} else {
								// if not, report identical warning on it
								messages.add(
										DefaultEclipseLinkJpaValidationMessages.buildMessage(
												IMessage.LOW_SEVERITY,
												EclipseLinkJpaValidationMessages.QUERY_IDENTICAL,
												parms,
												dup,
												this.extractNameTextRange(dup)
												)
										);
							}
						}
					}
				}
			}
		}
	}
	
	private boolean hasDuplicateQuery(Query query, ArrayList<Query> queries) {
		boolean isDuplicate = false;
		for (int i=0; i<queries.size(); i++) {
			if (query != queries.get(i) && !query.isIdentical(queries.get(i))) {
				isDuplicate = true;
			}
		}
		return isDuplicate;
	}
	
	private <N extends JpaNamedContextNode> HashMap<Class<? extends JpaNamedContextNode>, ArrayList<N>> mapByType(Iterable<N> nodes) {
		HashMap<Class<? extends JpaNamedContextNode>, ArrayList<N>> map = 	
				new HashMap<Class<? extends JpaNamedContextNode>, ArrayList<N>>();
		for (N node : nodes) {
			Class<? extends JpaNamedContextNode> type = node.getType();
			ArrayList<N> list = map.get(type);
			if (list == null) {
				list = new ArrayList<N>();
				map.put(type, list);
			}
			list.add(node);
		}
		return map;
	}
	// ********** refactoring **********

	@Override
	@SuppressWarnings("unchecked")
	protected Iterable<ReplaceEdit> createPersistenceUnitPropertiesRenameTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
				super.createPersistenceUnitPropertiesRenameTypeEdits(originalType, newName),
				this.customization.createRenameTypeEdits(originalType, newName),
				this.logging.createRenameTypeEdits(originalType, newName)
			);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Iterable<ReplaceEdit> createPersistenceUnitPropertiesMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
				super.createPersistenceUnitPropertiesMoveTypeEdits(originalType, newPackage),
				this.customization.createMoveTypeEdits(originalType, newPackage),
				this.logging.createMoveTypeEdits(originalType, newPackage)
			);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Iterable<ReplaceEdit> createPersistenceUnitPropertiesRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
				super.createPersistenceUnitPropertiesRenamePackageEdits(originalPackage, newName),
				this.customization.createRenamePackageEdits(originalPackage, newName),
				this.logging.createRenamePackageEdits(originalPackage, newName)
			);
	}
}
