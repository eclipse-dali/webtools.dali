/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SubIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JptJpaCoreMessages;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextNode;
import org.eclipse.jpt.jpa.core.context.ManagedType;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.MappingFilePersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaQuery;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence.VirtualOrmXmlRef;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCoreMessages;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.ReadOnlyTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkCustomization;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkGeneralProperties;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkSchemaGeneration;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * EclipseLink persistence unit
 */
public class EclipseLinkPersistenceUnit
	extends AbstractPersistenceUnit
{
	/**
	 * Will be null if the implied EL mapping file should not be part of the context model.
	 * Otherwise will be equal to potentialImpliedEclipseLinkMappingFileRef.
	 * 
	 * @see #potentialImpliedEclipseLinkMappingFileRef
	 */
	protected MappingFileRef impliedEclipseLinkMappingFileRef;
	/**
	 * String constant associated with changes to the implied eclipselink mapping file ref
	 */
	public static final String IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY = "impliedEclipseLinkMappingFileRef"; //$NON-NLS-1$

	/**
	 * Store the implied EL mapping file ref even if it is not part of the context model.
	 * This allows us to sync it in the syncWithResourceModel. In the update, determine if
	 * it should be part of the context model and set the impliedEclipseLinkMappingFileRef appropriately.
	 * 
	 * @see #impliedEclipseLinkMappingFileRef
	 * @see #usesImpliedEclipseLinkMappingFile()
	 */
	protected final MappingFileRef potentialImpliedEclipseLinkMappingFileRef;


	private/*final*/ GeneralProperties generalProperties;
	private Customization customization;
	private Caching caching;
	private Logging logging;
	private SchemaGeneration eclipseLinkSchemaGeneration;

	/* global converter definitions, defined elsewhere in model */
	protected final Vector<EclipseLinkConverter> converters = new Vector<EclipseLinkConverter>();

	protected final Vector<ReadOnlyTenantDiscriminatorColumn2_3> defaultTenantDiscriminatorColumns = new Vector<ReadOnlyTenantDiscriminatorColumn2_3>();

	protected String defaultGetMethod;
	protected String defaultSetMethod;

	public EclipseLinkPersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		super(parent, xmlPersistenceUnit);
		this.potentialImpliedEclipseLinkMappingFileRef = this.buildEclipseLinkVirtualMappingFileRef();
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

	public SchemaGeneration getEclipseLinkSchemaGeneration() {
		return this.eclipseLinkSchemaGeneration;
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
		this.eclipseLinkSchemaGeneration = this.buildEclipseLinkSchemaGeneration();
	}

	@Override
	public void propertyValueChanged(String propertyName, String newValue) {
		super.propertyValueChanged(propertyName, newValue);
		this.generalProperties.propertyValueChanged(propertyName, newValue);
		this.customization.propertyValueChanged(propertyName, newValue);
		this.caching.propertyValueChanged(propertyName, newValue);
		this.logging.propertyValueChanged(propertyName, newValue);
		this.eclipseLinkSchemaGeneration.propertyValueChanged(propertyName, newValue);
	}

	@Override
	public void propertyRemoved(String propertyName) {
		super.propertyRemoved(propertyName);
		this.generalProperties.propertyRemoved(propertyName);
		this.customization.propertyRemoved(propertyName);
		this.caching.propertyRemoved(propertyName);
		this.logging.propertyRemoved(propertyName);
		this.eclipseLinkSchemaGeneration.propertyRemoved(propertyName);
	}


	// ********** mapping file refs **********

	@Override
	public ListIterable<MappingFileRef> getMappingFileRefs() {
		return (this.impliedEclipseLinkMappingFileRef == null) ?
				super.getMappingFileRefs() :
				IterableTools.insert(this.impliedEclipseLinkMappingFileRef, super.getMappingFileRefs());
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

	protected void setImpliedEclipseLinkMappingFileRef(MappingFileRef mappingFileRef) {
		MappingFileRef old = this.impliedEclipseLinkMappingFileRef;
		this.impliedEclipseLinkMappingFileRef = mappingFileRef;
		this.firePropertyChanged(IMPLIED_ECLIPSELINK_MAPPING_FILE_REF_PROPERTY, old, mappingFileRef);
	}

	private VirtualOrmXmlRef buildEclipseLinkVirtualMappingFileRef() {
		return new VirtualOrmXmlRef(this, XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
	}

	@Override
	protected void syncImpliedMappingFileRef() {
		super.syncImpliedMappingFileRef();
		this.potentialImpliedEclipseLinkMappingFileRef.synchronizeWithResourceModel();
	}

	@Override
	protected void updateImpliedMappingFileRef() {
		super.updateImpliedMappingFileRef();

		if (this.usesImpliedEclipseLinkMappingFile()) {
			this.setImpliedEclipseLinkMappingFileRef(this.potentialImpliedEclipseLinkMappingFileRef);
			this.impliedEclipseLinkMappingFileRef.update();
		}
		else if (this.impliedEclipseLinkMappingFileRef != null) {
			this.impliedEclipseLinkMappingFileRef.dispose();
			this.setImpliedEclipseLinkMappingFileRef(null);
		}
	}

	/**
	 * Use the implied EclipseLink mapping file if all the following are true:<ul>
	 * <li>the properties do not explicitly exclude it
	 * <li>it is not specified explicitly in the persistence unit
	 * <li>the file actually exists
	 * </ul>
	 */
	private boolean usesImpliedEclipseLinkMappingFile() {
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
		return this.mappingFileIsSpecified(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
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
		return IterableTools.cloneLive(this.defaultTenantDiscriminatorColumns);
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
		return IterableTools.cloneLive(this.converters);
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
		return IterableTools.filter(this.getConverterNames(), StringTools.NON_BLANK_FILTER);
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
		ArrayList<EclipseLinkConverter> result = ListTools.list(this.getMappingFileConverters());

		HashSet<String> mappingFileConverterNames = this.convertToNames(result);
		HashMap<String, ArrayList<JavaEclipseLinkConverter<?>>> javaConverters = this.mapByName(this.getAllJavaConverters());
		for (Map.Entry<String, ArrayList<JavaEclipseLinkConverter<?>>> entry : javaConverters.entrySet()) {
			if ( ! mappingFileConverterNames.contains(entry.getKey())) {
				result.addAll(entry.getValue());
			}
		}

		return result;
	}

	protected Iterable<EclipseLinkConverter> getMappingFileConverters() {
		return IterableTools.children(this.getMappingFiles(), MAPPING_FILE_CONVERTERS_TRANSFORMER);
	}

	public static final Transformer<MappingFile, Iterable<EclipseLinkConverter>> MAPPING_FILE_CONVERTERS_TRANSFORMER = new MappingFileConvertersTransformer();

	public static class MappingFileConvertersTransformer
		extends TransformerAdapter<MappingFile, Iterable<EclipseLinkConverter>>
	{
		@Override
		public Iterable<EclipseLinkConverter> transform(MappingFile mappingFile) {
				MappingFile.Root root = mappingFile.getRoot();
				return (root instanceof EclipseLinkEntityMappings) ?
						((EclipseLinkEntityMappings) root).getMappingFileConverters() :
						EmptyIterable.<EclipseLinkConverter>instance();
		}
	}

	/**
	 * Include "overridden" Java converters.
	 */
	public Iterable<JavaEclipseLinkConverter<?>> getAllJavaConverters() {
		return IterableTools.children(this.getAllJavaTypeMappingsUnique(), TYPE_MAPPING_CONVERTER_TRANSFORMER);
	}

	public static final Transformer<TypeMapping, Iterable<JavaEclipseLinkConverter<?>>> TYPE_MAPPING_CONVERTER_TRANSFORMER = new TypeMappingConverterTransformer();

	public static class TypeMappingConverterTransformer
		extends TransformerAdapter<TypeMapping, Iterable<JavaEclipseLinkConverter<?>>>
	{
		@Override
		public Iterable<JavaEclipseLinkConverter<?>> transform(TypeMapping typeMapping) {
			return new SubIterableWrapper<EclipseLinkConverter, JavaEclipseLinkConverter<?>>(this.transform_(typeMapping));
		}
		protected Iterable<EclipseLinkConverter> transform_(TypeMapping typeMapping) {
			// Java "null" type mappings are not EclipseLink mappings
			return (typeMapping instanceof EclipseLinkTypeMapping) ?
					((EclipseLinkTypeMapping) typeMapping).getConverters() :
					EmptyIterable.<EclipseLinkConverter>instance();
		}
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
			default:
				throw new IllegalArgumentException("invalid shared cache mode: " + sharedCacheMode); //$NON-NLS-1$
		}
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

	public Iterable<String> getEclipseLinkDynamicPersistentTypeNames() {
		return IterableTools.transform(this.getEclipseLinkDynamicPersistentTypes(), ManagedType.NAME_TRANSFORMER);
	}

	public Iterable<EclipseLinkOrmPersistentType> getEclipseLinkDynamicPersistentTypes() {
		return IterableTools.filter(this.getEclipseLinkOrmPersistentTypes(), EclipseLinkOrmPersistentType.IS_DYNAMIC);
	}

	public Iterable<EclipseLinkOrmPersistentType> getEclipseLinkOrmPersistentTypes() {
		return IterableTools.downCast(
				IterableTools.filter(
					this.getMappingFilePersistentTypes(),
					PredicateTools.<PersistentType>instanceOfPredicate(EclipseLinkOrmPersistentType.class)
				)
			);
	}

	// ********** validation **********

	public JpaJpqlQueryHelper createJpqlQueryHelper() {
		return new EclipseLinkJpaJpqlQueryHelper(this.getJpaPlatform().getJpqlGrammar());
	}

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
					this.buildValidationMessage(
						this.getPersistenceUnit(),
						property.getValidationTextRange(),
						JptJpaEclipseLinkCoreValidationMessages.PERSISTENCE_UNIT_LEGACY_ENTITY_CACHING,
						property.getName()
					)
				);
			}
			for(Property property: this.getLegacyDescriptorCustomizerProperties()) {
				messages.add(
					this.buildValidationMessage(
						this.getPersistenceUnit(),
						property.getValidationTextRange(),
						JptJpaEclipseLinkCoreValidationMessages.PERSISTENCE_UNIT_LEGACY_DESCRIPTOR_CUSTOMIZER,
						property.getName()
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
					this.buildValidationMessage(
						this.getPersistenceUnit(),
						cachingProperty.getValidationTextRange(),
						JptJpaEclipseLinkCoreValidationMessages.PERSISTENCE_UNIT_CACHING_PROPERTY_IGNORED,
						cachingProperty.getName()
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
		if (StringTools.isBlank(loggerProperty.getValue())) {
			messages.add(
					this.buildValidationMessage(
							loggerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.SESSION_LOGGER_CLASS_NOT_SPECIFIED
					)
			);
		} else if (JDTTools.findType(javaProject, loggerProperty.getValue()) == null) {
			messages.add(
					this.buildValidationMessage(
							this.getPersistenceUnit(),
							loggerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.SESSION_LOGGER_CLASS_NOT_EXIST,
							loggerProperty.getValue()
					)
			);
		} else if (!JDTTools.typeIsSubType(
				javaProject, loggerProperty.getValue(), Logging.ECLIPSELINK_LOGGER_CLASS_NAME)
		) {
			messages.add(
					this.buildValidationMessage(
							this.getPersistenceUnit(),
							loggerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.SESSION_LOGGER_CLASS_IMPLEMENTS_SESSION_LOG,
							loggerProperty.getValue()
					)
			);
		}
	}

	private void validateExceptionHandlerProperty(Property handlerProperty, List<IMessage> messages) {
		if ((handlerProperty == null) || (handlerProperty.getValue() == null) ) {
			return;
		}

		IJavaProject javaProject = getJpaProject().getJavaProject();
		if (StringTools.isBlank(handlerProperty.getValue())) {
			messages.add(
					this.buildValidationMessage(
							handlerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.EXCEPTION_HANDLER_CLASS_NOT_SPECIFIED
					)
			);
		} else if (JDTTools.findType(javaProject, handlerProperty.getValue()) == null) {
			messages.add(
					this.buildValidationMessage(
							this.getPersistenceUnit(),
							handlerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.EXCEPTION_HANDLER_CLASS_NOT_EXIST,
							handlerProperty.getValue()
					)
			);
		} else if (!JDTTools.classHasPublicZeroArgConstructor(javaProject, handlerProperty.getValue())) {
			messages.add(
					this.buildValidationMessage(
							this.getPersistenceUnit(),
							handlerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.EXCEPTION_HANDLER_CLASS_NOT_VALID,
							handlerProperty.getValue()
					)
			);
		} else if (!JDTTools.typeIsSubType(
				javaProject, handlerProperty.getValue(), Customization.ECLIPSELINK_EXCEPTION_HANDLER_CLASS_NAME)
		) {
			messages.add(
					this.buildValidationMessage(
							this.getPersistenceUnit(),
							handlerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.EXCEPTION_HANDLER_CLASS_IMPLEMENTS_EXCEPTION_HANDLER,
							handlerProperty.getValue()
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
		if (StringTools.isBlank(profilerProperty.getValue())) {
			messages.add(
					this.buildValidationMessage(
							profilerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.SESSION_PROFILER_CLASS_NOT_SPECIFIED
					)
			);
		} else if (JDTTools.findType(javaProject, profilerProperty.getValue()) == null) {
			messages.add(
					this.buildValidationMessage(
							this.getPersistenceUnit(),
							profilerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.SESSION_PROFILER_CLASS_NOT_EXIST,
							profilerProperty.getValue()
					)
			);
		} else if (!JDTTools.classHasPublicZeroArgConstructor(javaProject, profilerProperty.getValue())){
			messages.add(
					this.buildValidationMessage(
							this.getPersistenceUnit(),
							profilerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.SESSION_PROFILER_CLASS_NOT_VALID,
							profilerProperty.getValue()
					)
			);
		} else if (!JDTTools.typeIsSubType(
				javaProject, profilerProperty.getValue(), Customization.ECLIPSELINK_SESSION_PROFILER_CLASS_NAME)
		) {
			messages.add(
					this.buildValidationMessage(
							this.getPersistenceUnit(),
							profilerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.SESSION_PROFILER_CLASS_IMPLEMENTS_SESSIONP_ROFILER,
							profilerProperty.getValue()
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
				if (StringTools.isBlank(property.getValue())) {
					messages.add(
							this.buildValidationMessage(
									property.getValidationTextRange(),
									JptJpaEclipseLinkCoreValidationMessages.SESSION_CUSTOMIZER_CLASS_NOT_SPECIFIED
							)
					);
				} else if (JDTTools.findType(javaProject, property.getValue()) == null) {
					messages.add(
							this.buildValidationMessage(
									this.getPersistenceUnit(),
									property.getValidationTextRange(),
									JptJpaEclipseLinkCoreValidationMessages.SESSION_CUSTOMIZER_CLASS_NOT_EXIST,
									property.getValue()
							)
					);
				} else if (!JDTTools.classHasPublicZeroArgConstructor(javaProject, property.getValue())){
					messages.add(
							this.buildValidationMessage(
									this.getPersistenceUnit(),
									property.getValidationTextRange(),
									JptJpaEclipseLinkCoreValidationMessages.SESSION_CUSTOMIZER_CLASS_NOT_VALID,
									property.getValue()
							)
					);
				} else if (!JDTTools.typeIsSubType(
						javaProject, property.getValue(), Customization.ECLIPSELINK_SESSION_CUSTOMIZER_CLASS_NAME)
				) {
					messages.add(
							this.buildValidationMessage(
									this.getPersistenceUnit(),
									property.getValidationTextRange(),
									JptJpaEclipseLinkCoreValidationMessages.SESSION_CUSTOMIZER_CLASS_IMPLEMENTS_SESSION_CUSTOMIZER,
									property.getValue()
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
		return IterableTools.filter(
				this.getPropertiesWithNamePrefix(prefix),
				PROPERTY_NAME_DOES_NOT_END_WITH_DEFAULT
			);
	}

	private static final Predicate<Property> PROPERTY_NAME_DOES_NOT_END_WITH_DEFAULT = new PropertyNameDoesNotEndWith("default"); //$NON-NLS-1$
	public static class PropertyNameDoesNotEndWith
		extends Predicate.Adapter<Property>
	{
		private final String suffix;
		public PropertyNameDoesNotEndWith(String suffix) {
			super();
			this.suffix = suffix;
		}
		@Override
		public boolean evaluate(Property property) {
			String propertyName = property.getName();
			return (propertyName == null) || ! propertyName.endsWith(this.suffix);
		}
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
		this.checkForConvertersWithSameName(messages);
		for (EclipseLinkConverter converter : this.getConverters()) {
			this.validate(converter, messages, reporter);
		}
	}

	protected void checkForConvertersWithSameName(List<IMessage> messages) {
		HashMap<String, ArrayList<EclipseLinkConverter>> convertersByName = this.mapByName(this.getConverters());
		for (Map.Entry<String, ArrayList<EclipseLinkConverter>> entry : convertersByName.entrySet()) {
			String converterName = entry.getKey();
			if (StringTools.isNotBlank(converterName)) {  // ignore empty names
				ArrayList<EclipseLinkConverter> dups = entry.getValue();
				if (dups.size() > 1) {
					this.validateConvertersWithSameName(converterName, dups, messages);
				}
			}
		}
	}

	/**
	 * <strong>NB:</strong> Unlike generators and queries, we do not mark
	 * "equivalent" converters with info messages - we just ignore them
	 * because they cannot be "portable" (since only EclipseLink has converters).
	 */
	protected void validateConvertersWithSameName(String converterName, ArrayList<EclipseLinkConverter> dups, List<IMessage> messages) {
		if (this.anyNodesAreInequivalent(dups)) {
			for (EclipseLinkConverter dup : dups) {
				if (dup.supportsValidationMessages()) {
					messages.add(
						this.buildValidationMessage(
							dup,
							dup.getNameTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.CONVERTER_DUPLICATE_NAME,
							converterName
						)
					);
				}
			}
		}
	}

	/**
	 * Return whether all the specified nodes are "equivalent"
	 * (i.e. they all have the same state).
	 */
	protected boolean allNodesAreEquivalent(ArrayList<? extends JpaNamedContextNode> nodes) {
		return ! this.anyNodesAreInequivalent(nodes);
	}

	protected boolean anyNodesAreInequivalent(ArrayList<? extends JpaNamedContextNode> nodes) {
		if (nodes.size() < 2) {
			throw new IllegalArgumentException();
		}
		Iterator<? extends JpaNamedContextNode> stream = nodes.iterator();
		JpaNamedContextNode first = stream.next();
		while (stream.hasNext()) {
			if ( ! stream.next().isEquivalentTo(first)) {
				return true;
			}
		}
		return false;
	}

	protected void validate(EclipseLinkConverter converter, List<IMessage> messages, IReporter reporter) {
		if (converter.supportsValidationMessages()) {
			converter.validate(messages, reporter);
		}
	}

	/**
	 * If all the generators are "equivalent" add info messages;
	 * otherwise mark them all as duplicates.
	 */
	@Override
	protected void validateGeneratorsWithSameName(String generatorName, ArrayList<Generator> dups, List<IMessage> messages) {
		if (this.allNodesAreEquivalent(dups)) {
			for (Generator dup : dups) {
				if (dup.supportsValidationMessages()) {
					messages.add(
						this.buildValidationMessage(
							dup,
							dup.getNameTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.GENERATOR_EQUIVALENT,
							generatorName
						)
					);
				}
			}
		} else {
			super.validateGeneratorsWithSameName(generatorName, dups, messages);
		}
	}

	/**
	 * @see #validateGeneratorsWithSameName(String, ArrayList, List)
	 */
	@Override
	protected void validateQueriesWithSameName(String queryName, ArrayList<Query> dups, List<IMessage> messages) {
		if (this.allNodesAreEquivalent(dups)) {
			for (Query dup : dups) {
				if (dup.supportsValidationMessages()) {
					messages.add(
						this.buildValidationMessage(
							dup,
							dup.getNameTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.QUERY_EQUIVALENT,
							queryName
						)
					);
				}
			}
		} else {
			super.validateQueriesWithSameName(queryName, dups, messages);
		}
	}


	// ********** refactoring **********

	@Override
	@SuppressWarnings("unchecked")
	protected Iterable<ReplaceEdit> createPersistenceUnitPropertiesRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.concatenate(
				super.createPersistenceUnitPropertiesRenameTypeEdits(originalType, newName),
				this.customization.createRenameTypeEdits(originalType, newName),
				this.logging.createRenameTypeEdits(originalType, newName)
			);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Iterable<ReplaceEdit> createPersistenceUnitPropertiesMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.concatenate(
				super.createPersistenceUnitPropertiesMoveTypeEdits(originalType, newPackage),
				this.customization.createMoveTypeEdits(originalType, newPackage),
				this.logging.createMoveTypeEdits(originalType, newPackage)
			);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Iterable<ReplaceEdit> createPersistenceUnitPropertiesRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
				super.createPersistenceUnitPropertiesRenamePackageEdits(originalPackage, newName),
				this.customization.createRenamePackageEdits(originalPackage, newName),
				this.logging.createRenamePackageEdits(originalPackage, newName)
			);
	}

	// ********** metadata conversion **********
	// ***** queries
	
	/**
	 * @see #convertJavaQueries(EntityMappings, IProgressMonitor)
	 */
	@Override
	public boolean hasConvertibleJavaQueries() {
		return ! this.getEclipseLinkConvertibleJavaQueries().isEmpty();
	}

	/**
	 * Return whether the persistence unit has any equivalent Java generators.
	 */
	public boolean hasAnyEquivalentJavaQueries() {
		return this.hasAnyEquivalentJavaNodes(this.getAllJavaQueries(), this.getMappingFileQueries());
	}

	/**
	 * Override the default implementation because EclipseLink allows
	 * "equivalent" queries.
	 */
	@Override
	public void convertJavaQueries(EntityMappings entityMappings, IProgressMonitor monitor) {
		OrmQueryContainer queryContainer = entityMappings.getQueryContainer();
		HashMap<String, ArrayList<JavaQuery>> convertibleJavaQueries = this.getEclipseLinkConvertibleJavaQueries();
		int work = this.calculateCumulativeSize(convertibleJavaQueries.values());
		SubMonitor sm = SubMonitor.convert(monitor, JptJpaCoreMessages.JAVA_METADATA_CONVERSION_IN_PROGRESS, work);
		for (Map.Entry<String, ArrayList<JavaQuery>> entry : convertibleJavaQueries.entrySet()) {
			this.convertJavaQueriesWithSameName(queryContainer, entry, sm.newChild(entry.getValue().size()));
		}
		sm.setTaskName(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_COMPLETE);
	}

	protected void convertJavaQueriesWithSameName(OrmQueryContainer queryContainer, Map.Entry<String, ArrayList<JavaQuery>> entry, SubMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_CANCELED);
		}
		monitor.setTaskName(NLS.bind(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_CONVERT_QUERY, entry.getKey()));

		ArrayList<JavaQuery> javaQueriesWithSameName = entry.getValue();
		JavaQuery first = javaQueriesWithSameName.get(0);
		first.convertTo(queryContainer);
		first.delete();  // delete any converted queries
		monitor.worked(1);

		for (int i = 1; i < javaQueriesWithSameName.size(); i++) {  // NB: start with 1!
			javaQueriesWithSameName.get(i).delete();
			monitor.worked(1);
		}
	}
	
	/**
	 * @see #extractEclipseLinkConvertibleJavaNodes(Iterable, Iterable)
	 */
	protected HashMap<String, ArrayList<JavaQuery>> getEclipseLinkConvertibleJavaQueries() {
		return this.extractEclipseLinkConvertibleJavaNodes(this.getAllJavaQueries(), this.getMappingFileQueries());
	}

	// ***** generators
	
	/**
	 * @see #convertJavaGenerators(EntityMappings, IProgressMonitor)
	 */
	@Override
	public boolean hasConvertibleJavaGenerators() {
		return ! this.getEclipseLinkConvertibleJavaGenerators().isEmpty();
	}

	/**
	 * Return whether the persistence unit has any equivalent Java generators.
	 */
	public boolean hasAnyEquivalentJavaGenerators() {
		return this.hasAnyEquivalentJavaNodes(this.getAllJavaGenerators(), this.getMappingFileGenerators());
	}

	/**
	 * Override the default implementation because EclipseLink allows
	 * "equivalent" generators.
	 */
	@Override
	public void convertJavaGenerators(EntityMappings entityMappings, IProgressMonitor monitor) {
		HashMap<String, ArrayList<JavaGenerator>> convertibleJavaGenerators = this.getEclipseLinkConvertibleJavaGenerators();
		int work = this.calculateCumulativeSize(convertibleJavaGenerators.values());
		SubMonitor sm = SubMonitor.convert(monitor, JptJpaCoreMessages.JAVA_METADATA_CONVERSION_IN_PROGRESS, work);
		for (Map.Entry<String, ArrayList<JavaGenerator>> entry : convertibleJavaGenerators.entrySet()) {
			this.convertJavaGeneratorsWithSameName(entityMappings, entry, sm.newChild(entry.getValue().size()));
		}
		sm.setTaskName(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_COMPLETE);
	}

	protected void convertJavaGeneratorsWithSameName(EntityMappings entityMappings, Map.Entry<String, ArrayList<JavaGenerator>> entry, SubMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_CANCELED);
		}
		monitor.setTaskName(NLS.bind(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_CONVERT_GENERATOR, entry.getKey()));

		ArrayList<JavaGenerator> javaGeneratorsWithSameName = entry.getValue();
		JavaGenerator first = javaGeneratorsWithSameName.get(0);
		first.convertTo(entityMappings);
		first.delete();  // delete any converted generators
		monitor.worked(1);

		for (int i = 1; i < javaGeneratorsWithSameName.size(); i++) {  // NB: start with 1!
			javaGeneratorsWithSameName.get(i).delete();
			monitor.worked(1);
		}
	}
	
	/**
	 * @see #extractEclipseLinkConvertibleJavaNodes(Iterable, Iterable)
	 */
	protected HashMap<String, ArrayList<JavaGenerator>> getEclipseLinkConvertibleJavaGenerators() {
		return this.extractEclipseLinkConvertibleJavaNodes(this.getAllJavaGenerators(), this.getMappingFileGenerators());
	}

	// ***** converters
	
	/**
	 * @see #convertJavaConverters(EclipseLinkEntityMappings, IProgressMonitor)
	 */
	public boolean hasConvertibleJavaConverters() {
		return ! this.getEclipseLinkConvertibleJavaConverters().isEmpty();
	}

	/**
	 * Return whether the persistence unit has any equivalent Java generators.
	 */
	public boolean hasAnyEquivalentJavaConverters() {
		return this.hasAnyEquivalentJavaNodes(this.getAllJavaConverters(), this.getMappingFileConverters());
	}

	/**
	 * Override the default implementation because EclipseLink allows
	 * "equivalent" converters.
	 */
	public void convertJavaConverters(EclipseLinkEntityMappings entityMappings, IProgressMonitor monitor) {
		OrmEclipseLinkConverterContainer ormConverterContainer = entityMappings.getConverterContainer();
		HashMap<String, ArrayList<JavaEclipseLinkConverter<?>>> convertibleJavaConverters = this.getEclipseLinkConvertibleJavaConverters();
		int work = this.calculateCumulativeSize(convertibleJavaConverters.values());
		SubMonitor sm = SubMonitor.convert(monitor, JptJpaCoreMessages.JAVA_METADATA_CONVERSION_IN_PROGRESS, work);
		for (Map.Entry<String, ArrayList<JavaEclipseLinkConverter<?>>> entry : convertibleJavaConverters.entrySet()) {
			this.convertJavaConvertersWithSameName(ormConverterContainer, entry, sm.newChild(entry.getValue().size()));
		}
		sm.setTaskName(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_COMPLETE);
	}

	protected void convertJavaConvertersWithSameName(OrmEclipseLinkConverterContainer ormConverterContainer, Map.Entry<String, ArrayList<JavaEclipseLinkConverter<?>>> entry, SubMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_CANCELED);
		}
		monitor.setTaskName(NLS.bind(JptJpaEclipseLinkCoreMessages.JAVA_METADATA_CONVERSION_CONVERT_CONVERTER, entry.getKey()));

		ArrayList<JavaEclipseLinkConverter<?>> javaConvertersWithSameName = entry.getValue();
		JavaEclipseLinkConverter<?> first = javaConvertersWithSameName.get(0);
		first.convertTo(ormConverterContainer);
		first.delete();  // delete any converted generators
		monitor.worked(1);

		for (int i = 1; i < javaConvertersWithSameName.size(); i++) {  // NB: start with 1!
			javaConvertersWithSameName.get(i).delete();
			monitor.worked(1);
		}
	}
	
	/**
	 * @see #extractEclipseLinkConvertibleJavaNodes(Iterable, Iterable)
	 */
	protected HashMap<String, ArrayList<JavaEclipseLinkConverter<?>>> getEclipseLinkConvertibleJavaConverters() {
		return this.extractEclipseLinkConvertibleJavaNodes(this.getAllJavaConverters(), this.getMappingFileConverters());
	}

	protected int calculateCumulativeSize(Collection<? extends Collection<?>> collections) {
		int cumulativeSize = 0;
		for (Collection<?> collection : collections) {
			cumulativeSize += collection.size();
		}
		return cumulativeSize;
	}

	protected <N extends JpaNamedContextNode> boolean hasAnyEquivalentJavaNodes(Iterable<N> allJavaNodes, Iterable<? extends JpaNamedContextNode> mappingFileNodes) {
		HashMap<String, ArrayList<N>> convertibleJavaNodes = this.extractEclipseLinkConvertibleJavaNodes(allJavaNodes, mappingFileNodes);
		for (Map.Entry<String, ArrayList<N>> entry : convertibleJavaNodes.entrySet()) {
			if (entry.getValue().size() > 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return the Java nodes that are neither overridden nor duplicated
	 * (by default any Java nodes with the same name are "duplicates");
	 * but, in EclipseLink we return any "equivalent" nodes also.
	 */
	protected <N extends JpaNamedContextNode> HashMap<String, ArrayList<N>> extractEclipseLinkConvertibleJavaNodes(Iterable<N> allJavaNodes, Iterable<? extends JpaNamedContextNode> mappingFileNodes) {
		HashMap<String, ArrayList<N>> convertibleNodes = new HashMap<String, ArrayList<N>>();

		HashSet<String> mappingFileNodeNames = this.convertToNames(ListTools.list(mappingFileNodes));
		HashMap<String, ArrayList<N>> allJavaNodesByName = this.mapByName(allJavaNodes);
		for (Map.Entry<String, ArrayList<N>> entry : allJavaNodesByName.entrySet()) {
			String javaNodeName = entry.getKey();
			if (StringTools.isBlank(javaNodeName)) {
				continue;  // ignore any nodes with an empty name(?)
			}
			if (mappingFileNodeNames.contains(javaNodeName)) {
				continue;  // ignore any Java nodes overridden in the mapping file
			}
			ArrayList<N> javaNodesWithSameName = entry.getValue();
			if ((javaNodesWithSameName.size() == 1) || this.allNodesAreEquivalent(javaNodesWithSameName)) {
				convertibleNodes.put(javaNodeName, javaNodesWithSameName);
			} else {
				// ignore multiple Java nodes with the same name but that are not all "equivalent"
			}
		}

		return convertibleNodes;
	}

	// ********** metamodel **********

	@Override
	protected HashMap<String, PersistentType> getPersistentTypesToSynchronizeMetamodel() {
		HashMap<String, PersistentType> allPersistentTypes = super.getPersistentTypesToSynchronizeMetamodel();
		
		this.removeDynamicTypes(allPersistentTypes);
		return allPersistentTypes;
	}

	private void removeDynamicTypes(HashMap<String, PersistentType> persistentTypes) {
		Iterator<String> dynamicTypeNames = this.getEclipseLinkDynamicPersistentTypeNames().iterator();
		while(dynamicTypeNames.hasNext()) {
			persistentTypes.remove(dynamicTypeNames.next());
		}
	}
}
