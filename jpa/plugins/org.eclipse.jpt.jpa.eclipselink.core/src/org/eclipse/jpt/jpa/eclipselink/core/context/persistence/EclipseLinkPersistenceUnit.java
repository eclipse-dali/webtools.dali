/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.internal.utility.TypeTools;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JptJpaCoreMessages;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextModel;
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
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.SharedCacheMode2_0;
import org.eclipse.jpt.jpa.core.jpql.JpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCoreMessages;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTenantDiscriminatorColumn2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaJpqlQueryHelper;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.EclipseLinkJavaConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkCustomization;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkGeneralProperties;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkSchemaGenerationImpl;
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


	private/*final*/ EclipseLinkGeneralProperties generalProperties;
	private EclipseLinkCustomization customization;
	private EclipseLinkCaching caching;
	private EclipseLinkLogging logging;
	private EclipseLinkSchemaGeneration eclipseLinkSchemaGeneration;
	private EclipseLinkConnection eclipseLinkConnection1_0;
	private EclipseLinkOptions eclipseLinkOptions1_0;

	/* global converter definitions, defined elsewhere in model */
	protected final Vector<EclipseLinkConverter> converters = new Vector<EclipseLinkConverter>();

	protected final Vector<EclipseLinkTenantDiscriminatorColumn2_3> defaultTenantDiscriminatorColumns = new Vector<EclipseLinkTenantDiscriminatorColumn2_3>();

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

		EclipseLinkOrmPersistenceUnitMetadata metadata = this.getEclipseLinkMetadata();

		EclipseLinkPersistenceUnitDefaults defaults = (metadata == null) ? null : metadata.getPersistenceUnitDefaults();
		this.setDefaultTenantDiscriminatorColumns(this.buildDefaultTenantDiscriminatorColumns(defaults));
	}

	protected EclipseLinkOrmPersistenceUnitMetadata getEclipseLinkMetadata() {
		MappingFilePersistenceUnitMetadata metadata = super.getMetadata();
		if (metadata instanceof EclipseLinkOrmPersistenceUnitMetadata) {
			return (EclipseLinkOrmPersistenceUnitMetadata) metadata;
		}
		return null;
	}

	@Override
	protected void updatePersistenceUnitMetadata() {
		super.updatePersistenceUnitMetadata();
		EclipseLinkOrmPersistenceUnitMetadata metadata = this.getEclipseLinkMetadata();

		EclipseLinkPersistenceUnitDefaults defaults = (metadata == null) ? null : metadata.getPersistenceUnitDefaults();
		this.setDefaultGetMethod(this.buildDefaultGetMethod(defaults));
		this.setDefaultSetMethod(this.buildDefaultSetMethod(defaults));
	}

	// ********** properties **********

	public EclipseLinkGeneralProperties getGeneralProperties() {
		return this.generalProperties;
	}

	@Override
	public EclipseLinkConnection2_0 getConnection() {
		return (EclipseLinkConnection2_0) super.getConnection();
	}

	@Override
	public EclipseLinkOptions2_0 getOptions() {
		return (EclipseLinkOptions2_0) super.getOptions();
	}

	public EclipseLinkCustomization getCustomization() {
		return this.customization;
	}

	public EclipseLinkCaching getCaching() {
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

	public EclipseLinkLogging getLogging() {
		return this.logging;
	}

	public EclipseLinkSchemaGeneration getEclipseLinkSchemaGeneration() {
		return this.eclipseLinkSchemaGeneration;
	}

	public EclipseLinkConnection getEclipseLinkConnection() {
		return this.isPersistenceXml2_0Compatible() ?
				this.getConnection() :
				this.eclipseLinkConnection1_0;
	}

	public EclipseLinkOptions getEclipseLinkOptions() {
		return this.isPersistenceXml2_0Compatible() ?
				this.getOptions() :
				this.eclipseLinkOptions1_0;
	}

	protected EclipseLinkGeneralProperties buildEclipseLinkGeneralProperties() {
		return new EclipseLinkGeneralProperties(this);
	}

	protected EclipseLinkCustomization buildEclipseLinkCustomization() {
		return new EclipseLinkCustomization(this);
	}

	protected EclipseLinkCaching buildEclipseLinkCaching() {
		return new EclipseLinkCaching(this);
	}

	protected EclipseLinkLogging buildEclipseLinkLogging() {
		return this.getContextModelFactory().buildLogging(this);
	}

	protected EclipseLinkConnection buildEclipseLinkConnection1_0() {
		return this.getContextModelFactory().buildConnection(this);
	}

	protected EclipseLinkOptions buildEclipseLinkOptions1_0() {
		return this.getContextModelFactory().buildOptions(this);
	}

	protected EclipseLinkSchemaGeneration buildEclipseLinkSchemaGeneration() {
		return new EclipseLinkSchemaGenerationImpl(this);
	}

	@Override
	protected void initializeProperties() {
		super.initializeProperties();
		this.generalProperties = this.buildEclipseLinkGeneralProperties();
		this.customization = this.buildEclipseLinkCustomization();
		this.caching = this.buildEclipseLinkCaching();
		this.logging = this.buildEclipseLinkLogging();
		this.eclipseLinkSchemaGeneration = this.buildEclipseLinkSchemaGeneration();
		this.eclipseLinkConnection1_0 = this.buildEclipseLinkConnection1_0();
		this.eclipseLinkOptions1_0 = this.buildEclipseLinkOptions1_0();
	}

	@Override
	public void propertyValueChanged(String propertyName, String newValue) {
		super.propertyValueChanged(propertyName, newValue);
		this.generalProperties.propertyValueChanged(propertyName, newValue);
		this.customization.propertyValueChanged(propertyName, newValue);
		this.caching.propertyValueChanged(propertyName, newValue);
		this.logging.propertyValueChanged(propertyName, newValue);
		this.eclipseLinkSchemaGeneration.propertyValueChanged(propertyName, newValue);
		this.eclipseLinkConnection1_0.propertyValueChanged(propertyName, newValue);
		this.eclipseLinkOptions1_0.propertyValueChanged(propertyName, newValue);
	}

	@Override
	public void propertyRemoved(String propertyName) {
		super.propertyRemoved(propertyName);
		this.generalProperties.propertyRemoved(propertyName);
		this.customization.propertyRemoved(propertyName);
		this.caching.propertyRemoved(propertyName);
		this.logging.propertyRemoved(propertyName);
		this.eclipseLinkSchemaGeneration.propertyRemoved(propertyName);
		this.eclipseLinkConnection1_0.propertyRemoved(propertyName);
		this.eclipseLinkOptions1_0.propertyRemoved(propertyName);
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

	public ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> getDefaultTenantDiscriminatorColumns() {
		return IterableTools.cloneLive(this.defaultTenantDiscriminatorColumns);
	}

	protected void setDefaultTenantDiscriminatorColumns(Iterable<EclipseLinkTenantDiscriminatorColumn2_3> tenantDiscriminatorColumns) {
		this.synchronizeList(tenantDiscriminatorColumns, this.defaultTenantDiscriminatorColumns, DEFAULT_TENANT_DISCRIMINATOR_COLUMNS_LIST);
	}

	protected ListIterable<EclipseLinkTenantDiscriminatorColumn2_3> buildDefaultTenantDiscriminatorColumns(EclipseLinkPersistenceUnitDefaults defaults) {
		return (defaults == null) ? EmptyListIterable.<EclipseLinkTenantDiscriminatorColumn2_3> instance() : new SuperListIterableWrapper<EclipseLinkTenantDiscriminatorColumn2_3>(defaults.getTenantDiscriminatorColumns());
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
		return CollectionTools.hashSet(this.getNonEmptyConverterNames(), this.getConvertersSize());
	}

	protected Iterable<String> getNonEmptyConverterNames() {
		return IterableTools.filter(this.getConverterNames(), StringTools.IS_NOT_BLANK);
	}

	protected Iterable<String> getConverterNames() {
		return new TransformationIterable<EclipseLinkConverter, String>(this.getConverters(), JpaNamedContextModel.NAME_TRANSFORMER);
	}

	protected void setConverters(Iterable<EclipseLinkConverter> converters) {
		this.synchronizeCollection(converters, this.converters, CONVERTERS_COLLECTION);
	}

	/**
	 * Converters are much like queries.
	 * @see #buildQueries()
	 */
	protected Iterable<EclipseLinkConverter> buildConverters() {
		ArrayList<EclipseLinkConverter> result = ListTools.arrayList(this.getMappingFileConverters());

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
	public Iterable<EclipseLinkConverter> getAllJavaConverters() {
		return IterableTools.children(this.getAllJavaTypeMappingsUnique(), TYPE_MAPPING_CONVERTERS_TRANSFORMER);
	}

	public static final Transformer<TypeMapping, Iterable<EclipseLinkConverter>> TYPE_MAPPING_CONVERTERS_TRANSFORMER = new TypeMappingConvertersTransformer();

	public static class TypeMappingConvertersTransformer
		extends TransformerAdapter<TypeMapping, Iterable<EclipseLinkConverter>>
	{
		@Override
		public Iterable<EclipseLinkConverter> transform(TypeMapping typeMapping) {
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
	public EclipseLinkPersistenceXmlContextModelFactory getContextModelFactory() {
		return (EclipseLinkPersistenceXmlContextModelFactory) super.getContextModelFactory();
	}

	@Override
	public void setSpecifiedSharedCacheMode(SharedCacheMode2_0 specifiedSharedCacheMode) {
		super.setSpecifiedSharedCacheMode(specifiedSharedCacheMode);

		if(specifiedSharedCacheMode == SharedCacheMode2_0.NONE) {
			this.caching.removeDefaultCachingProperties();
		}
	}

	@Override
	protected SharedCacheMode2_0 buildDefaultSharedCacheMode() {
		return SharedCacheMode2_0.DISABLE_SELECTIVE;
	}

	@Override
	public boolean calculateDefaultCacheable() {
		SharedCacheMode2_0 sharedCacheMode = this.getSharedCacheMode();
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
					PredicateTools.<PersistentType>instanceOf(EclipseLinkOrmPersistentType.class)
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

		if(this.getSharedCacheMode() == SharedCacheMode2_0.NONE) {
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

		if (ArrayTools.contains(EclipseLinkLogging.RESERVED_LOGGER_NAMES, loggerProperty.getValue())) {
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
		} else if (JavaProjectTools.findType(javaProject, loggerProperty.getValue()) == null) {
			messages.add(
					this.buildValidationMessage(
							this.getPersistenceUnit(),
							loggerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.SESSION_LOGGER_CLASS_NOT_EXIST,
							loggerProperty.getValue()
					)
			);
		} else if (!TypeTools.isSubType(
				loggerProperty.getValue(), EclipseLinkLogging.ECLIPSELINK_LOGGER_CLASS_NAME, javaProject)
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
		} else if (JavaProjectTools.findType(javaProject, handlerProperty.getValue()) == null) {
			messages.add(
					this.buildValidationMessage(
							this.getPersistenceUnit(),
							handlerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.EXCEPTION_HANDLER_CLASS_NOT_EXIST,
							handlerProperty.getValue()
					)
			);
		} else if (!TypeTools.hasPublicZeroArgConstructor(handlerProperty.getValue(), javaProject)) {
			messages.add(
					this.buildValidationMessage(
							this.getPersistenceUnit(),
							handlerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.EXCEPTION_HANDLER_CLASS_NOT_VALID,
							handlerProperty.getValue()
					)
			);
		} else if ( ! TypeTools.isSubType(
				handlerProperty.getValue(),
				org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomization.ECLIPSELINK_EXCEPTION_HANDLER_CLASS_NAME,
				javaProject)
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

		if (ArrayTools.contains(org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomization.RESERVED_PROFILER_NAMES, profilerProperty.getValue())) {
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
		} else if (JavaProjectTools.findType(javaProject, profilerProperty.getValue()) == null) {
			messages.add(
					this.buildValidationMessage(
							this.getPersistenceUnit(),
							profilerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.SESSION_PROFILER_CLASS_NOT_EXIST,
							profilerProperty.getValue()
					)
			);
		} else if (!TypeTools.hasPublicZeroArgConstructor(profilerProperty.getValue(), javaProject)){
			messages.add(
					this.buildValidationMessage(
							this.getPersistenceUnit(),
							profilerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.SESSION_PROFILER_CLASS_NOT_VALID,
							profilerProperty.getValue()
					)
			);
		} else if (!TypeTools.isSubType(
				profilerProperty.getValue(), org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomization.ECLIPSELINK_SESSION_PROFILER_CLASS_NAME, javaProject)
		) {
			messages.add(
					this.buildValidationMessage(
							this.getPersistenceUnit(),
							profilerProperty.getValidationTextRange(),
							JptJpaEclipseLinkCoreValidationMessages.SESSION_PROFILER_CLASS_IMPLEMENTS_SESSION_PROFILER,
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
				} else if (JavaProjectTools.findType(javaProject, property.getValue()) == null) {
					messages.add(
							this.buildValidationMessage(
									this.getPersistenceUnit(),
									property.getValidationTextRange(),
									JptJpaEclipseLinkCoreValidationMessages.SESSION_CUSTOMIZER_CLASS_NOT_EXIST,
									property.getValue()
							)
					);
				} else if (!TypeTools.hasPublicZeroArgConstructor(property.getValue(), javaProject)){
					messages.add(
							this.buildValidationMessage(
									this.getPersistenceUnit(),
									property.getValidationTextRange(),
									JptJpaEclipseLinkCoreValidationMessages.SESSION_CUSTOMIZER_CLASS_NOT_VALID,
									property.getValue()
							)
					);
				} else if (!TypeTools.isSubType(
						property.getValue(), org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomization.ECLIPSELINK_SESSION_CUSTOMIZER_CLASS_NAME, javaProject)
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
		return this.getProperty(org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCaching.ECLIPSELINK_CACHE_TYPE_DEFAULT);
	}

	private Property getCacheSizeDefaultProperty() {
		return this.getProperty(org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCaching.ECLIPSELINK_CACHE_SIZE_DEFAULT);
	}

	private Property getCacheSharedDefaultProperty() {
		return this.getProperty(org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCaching.ECLIPSELINK_CACHE_SHARED_DEFAULT);
	}

	private Property getFlushClearCacheProperty() {
		return this.getProperty(org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCaching.ECLIPSELINK_FLUSH_CLEAR_CACHE);
	}

	/**
	 * Returns all Shared Cache Properties, including Entity and default.
	 */
	private Iterable<Property> getSharedCacheProperties() {
		return this.getPropertiesWithNamePrefix(org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCaching.ECLIPSELINK_SHARED_CACHE);
	}

	/**
	 * Returns Entity Cache Size Properties, excluding default.
	 */
	private Iterable<Property> getEntityCacheSizeProperties() {
		return this.getEntityPropertiesWithPrefix(org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCaching.ECLIPSELINK_CACHE_SIZE);
	}

	/**
	 * Returns Entity Cache Type Properties, excluding default.
	 */
	private Iterable<Property> getEntityCacheTypeProperties() {
		return this.getEntityPropertiesWithPrefix(org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCaching.ECLIPSELINK_CACHE_TYPE);
	}

	/**
	 * Returns Descriptor Customizer Properties.
	 */
	private Iterable<Property> getDescriptorCustomizerProperties() {
		return this.getEntityPropertiesWithPrefix(org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomization.ECLIPSELINK_DESCRIPTOR_CUSTOMIZER);
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
		extends CriterionPredicate<Property, String>
	{
		public PropertyNameDoesNotEndWith(String suffix) {
			super(suffix);
		}
		public boolean evaluate(Property property) {
			String propertyName = property.getName();
			return (propertyName == null) || ! propertyName.endsWith(this.criterion);
		}
	}

	private Property getLoggerProperty() {
		return this.getProperty(EclipseLinkLogging.ECLIPSELINK_LOGGER);
	}

	private Property getExceptionHandlerProperty() {
		return this.getProperty(org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomization.ECLIPSELINK_EXCEPTION_HANDLER);
	}

	private Property getPerformanceProfilerProperty() {
		return this.getProperty(org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomization.ECLIPSELINK_PROFILER);
	}

	/**
	 * Returns all Session Customizer Properties.
	 */
	private Iterable<Property> getSessionCustomizerProperties() {
		return this.getPropertiesWithNamePrefix(org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomization.ECLIPSELINK_SESSION_CUSTOMIZER);
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
		if (this.allAreEquivalent(dups, CONVERTER_EQUIVALENCY_ADAPTER)) {
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

	protected static final EquivalencyAdapter<EclipseLinkConverter> CONVERTER_EQUIVALENCY_ADAPTER = new ConverterEquivalencyAdapter();

	public static class ConverterEquivalencyAdapter
		implements EquivalencyAdapter<EclipseLinkConverter>
	{
		public boolean valuesAreEquivalent(EclipseLinkConverter converter1, EclipseLinkConverter converter2) {
			return converter1.isEquivalentTo(converter2);
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
	}

	/**
	 * Using the specified adapter, return whether all the specified objects
	 * are "equivalent" (i.e. they all have the same state).
	 */
	protected <T> boolean allAreEquivalent(ArrayList<T> collection, EquivalencyAdapter<T> adapter) {
		return ! this.anyAreInequivalent(collection, adapter);
	}

	protected <T> boolean anyAreInequivalent(ArrayList<T> collection, EquivalencyAdapter<T> adapter) {
		if (collection.size() < 2) {
			throw new IllegalArgumentException();
		}
		Iterator<T> stream = collection.iterator();
		T first = stream.next();
		while (stream.hasNext()) {
			if ( ! adapter.valuesAreEquivalent(stream.next(), first)) {
				return true;
			}
		}
		return false;
	}

	public interface EquivalencyAdapter<T> {
		/**
		 * Return whether the two objects are "equivalent".
		 */
		boolean valuesAreEquivalent(T o1, T o2);
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
		if (this.allAreEquivalent(dups, GENERATOR_EQUIVALENCY_ADAPTER)) {
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

	protected static final EquivalencyAdapter<Generator> GENERATOR_EQUIVALENCY_ADAPTER = new GeneratorEquivalencyAdapter();

	public static class GeneratorEquivalencyAdapter
		implements EquivalencyAdapter<Generator>
	{
		public boolean valuesAreEquivalent(Generator generator1, Generator generator2) {
			return generator1.isEquivalentTo(generator2);
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
	}

	/**
	 * @see #validateGeneratorsWithSameName(String, ArrayList, List)
	 */
	@Override
	protected void validateQueriesWithSameName(String queryName, ArrayList<Query> dups, List<IMessage> messages) {
		if (this.allAreEquivalent(dups, QUERY_EQUIVALENCY_ADAPTER)) {
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

	protected static final EquivalencyAdapter<Query> QUERY_EQUIVALENCY_ADAPTER = new QueryEquivalencyAdapter();

	public static class QueryEquivalencyAdapter
		implements EquivalencyAdapter<Query>
	{
		public boolean valuesAreEquivalent(Query query1, Query query2) {
			return query1.isEquivalentTo(query2);
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
	}


	// ********** refactoring **********

	@Override
	@SuppressWarnings("unchecked")
	protected Iterable<ReplaceEdit> createPersistenceUnitPropertiesRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.concatenate(
				super.createPersistenceUnitPropertiesRenameTypeEdits(originalType, newName),
				this.customization.createRenameTypeEdits(originalType, newName),
				this.logging.createRenameTypeEdits(originalType, newName),
				this.eclipseLinkOptions1_0.createRenameTypeEdits(originalType, newName)
			);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Iterable<ReplaceEdit> createPersistenceUnitPropertiesMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.concatenate(
				super.createPersistenceUnitPropertiesMoveTypeEdits(originalType, newPackage),
				this.customization.createMoveTypeEdits(originalType, newPackage),
				this.logging.createMoveTypeEdits(originalType, newPackage),
				this.eclipseLinkOptions1_0.createMoveTypeEdits(originalType, newPackage)
			);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Iterable<ReplaceEdit> createPersistenceUnitPropertiesRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
				super.createPersistenceUnitPropertiesRenamePackageEdits(originalPackage, newName),
				this.customization.createRenamePackageEdits(originalPackage, newName),
				this.logging.createRenamePackageEdits(originalPackage, newName),
				this.eclipseLinkOptions1_0.createRenamePackageEdits(originalPackage, newName)
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
		return this.hasAnyEquivalentJavaModels(this.getAllJavaQueries(), this.getMappingFileQueries(), QUERY_EQUIVALENCY_ADAPTER);
	}

	/**
	 * Override the default implementation because EclipseLink allows
	 * "equivalent" queries.
	 */
	@Override
	public void convertJavaQueries(EntityMappings entityMappings, IProgressMonitor monitor) {
		OrmQueryContainer queryContainer = entityMappings.getQueryContainer();
		HashMap<String, ArrayList<Query>> convertibleJavaQueries = this.getEclipseLinkConvertibleJavaQueries();
		int work = this.calculateCumulativeSize(convertibleJavaQueries.values());
		SubMonitor sm = SubMonitor.convert(monitor, JptJpaCoreMessages.JAVA_METADATA_CONVERSION_IN_PROGRESS, work);
		for (Map.Entry<String, ArrayList<Query>> entry : convertibleJavaQueries.entrySet()) {
			this.convertJavaQueriesWithSameName(queryContainer, entry, sm.newChild(entry.getValue().size()));
		}
		sm.setTaskName(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_COMPLETE);
	}

	protected void convertJavaQueriesWithSameName(OrmQueryContainer queryContainer, Map.Entry<String, ArrayList<Query>> entry, SubMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_CANCELED);
		}
		monitor.setTaskName(NLS.bind(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_CONVERT_QUERY, entry.getKey()));

		ArrayList<Query> javaQueriesWithSameName = entry.getValue();
		JavaQuery first = (JavaQuery) javaQueriesWithSameName.get(0);
		first.convertTo(queryContainer);
		first.delete();  // delete any converted queries
		monitor.worked(1);

		for (int i = 1; i < javaQueriesWithSameName.size(); i++) {  // NB: start with 1!
			((JavaQuery) javaQueriesWithSameName.get(i)).delete();
			monitor.worked(1);
		}
	}
	
	/**
	 * @see #extractEclipseLinkConvertibleJavaModels(Iterable, Iterable, EquivalencyAdapter)
	 */
	protected HashMap<String, ArrayList<Query>> getEclipseLinkConvertibleJavaQueries() {
		return this.extractEclipseLinkConvertibleJavaModels(this.getAllJavaQueries(), this.getMappingFileQueries(), QUERY_EQUIVALENCY_ADAPTER);
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
		return this.hasAnyEquivalentJavaModels(this.getAllJavaGenerators(), this.getMappingFileGenerators(), GENERATOR_EQUIVALENCY_ADAPTER);
	}

	/**
	 * Override the default implementation because EclipseLink allows
	 * "equivalent" generators.
	 */
	@Override
	public void convertJavaGenerators(EntityMappings entityMappings, IProgressMonitor monitor) {
		HashMap<String, ArrayList<Generator>> convertibleJavaGenerators = this.getEclipseLinkConvertibleJavaGenerators();
		int work = this.calculateCumulativeSize(convertibleJavaGenerators.values());
		SubMonitor sm = SubMonitor.convert(monitor, JptJpaCoreMessages.JAVA_METADATA_CONVERSION_IN_PROGRESS, work);
		for (Map.Entry<String, ArrayList<Generator>> entry : convertibleJavaGenerators.entrySet()) {
			this.convertJavaGeneratorsWithSameName(entityMappings, entry, sm.newChild(entry.getValue().size()));
		}
		sm.setTaskName(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_COMPLETE);
	}

	protected void convertJavaGeneratorsWithSameName(EntityMappings entityMappings, Map.Entry<String, ArrayList<Generator>> entry, SubMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_CANCELED);
		}
		monitor.setTaskName(NLS.bind(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_CONVERT_GENERATOR, entry.getKey()));

		ArrayList<Generator> javaGeneratorsWithSameName = entry.getValue();
		JavaGenerator first = (JavaGenerator) javaGeneratorsWithSameName.get(0);
		first.convertTo(entityMappings);
		first.delete();  // delete any converted generators
		monitor.worked(1);

		for (int i = 1; i < javaGeneratorsWithSameName.size(); i++) {  // NB: start with 1!
			((JavaGenerator) javaGeneratorsWithSameName.get(i)).delete();
			monitor.worked(1);
		}
	}
	
	/**
	 * @see #extractEclipseLinkConvertibleJavaModels(Iterable, Iterable, EquivalencyAdapter)
	 */
	protected HashMap<String, ArrayList<Generator>> getEclipseLinkConvertibleJavaGenerators() {
		return this.extractEclipseLinkConvertibleJavaModels(this.getAllJavaGenerators(), this.getMappingFileGenerators(), GENERATOR_EQUIVALENCY_ADAPTER);
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
		return this.hasAnyEquivalentJavaModels(this.getAllJavaConverters(), this.getMappingFileConverters(), CONVERTER_EQUIVALENCY_ADAPTER);
	}

	/**
	 * Override the default implementation because EclipseLink allows
	 * "equivalent" converters.
	 */
	public void convertJavaConverters(EclipseLinkEntityMappings entityMappings, IProgressMonitor monitor) {
		EclipseLinkOrmConverterContainer ormConverterContainer = entityMappings.getConverterContainer();
		HashMap<String, ArrayList<EclipseLinkConverter>> convertibleJavaConverters = this.getEclipseLinkConvertibleJavaConverters();
		int work = this.calculateCumulativeSize(convertibleJavaConverters.values());
		SubMonitor sm = SubMonitor.convert(monitor, JptJpaCoreMessages.JAVA_METADATA_CONVERSION_IN_PROGRESS, work);
		for (Map.Entry<String, ArrayList<EclipseLinkConverter>> entry : convertibleJavaConverters.entrySet()) {
			this.convertJavaConvertersWithSameName(ormConverterContainer, entry, sm.newChild(entry.getValue().size()));
		}
		sm.setTaskName(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_COMPLETE);
	}

	protected void convertJavaConvertersWithSameName(EclipseLinkOrmConverterContainer ormConverterContainer, Map.Entry<String, ArrayList<EclipseLinkConverter>> entry, SubMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException(JptJpaCoreMessages.JAVA_METADATA_CONVERSION_CANCELED);
		}
		monitor.setTaskName(NLS.bind(JptJpaEclipseLinkCoreMessages.JAVA_METADATA_CONVERSION_CONVERT_CONVERTER, entry.getKey()));

		ArrayList<EclipseLinkConverter> javaConvertersWithSameName = entry.getValue();
		EclipseLinkJavaConverter<?> first = (EclipseLinkJavaConverter<?>) javaConvertersWithSameName.get(0);
		first.convertTo(ormConverterContainer);
		first.delete();  // delete any converted generators
		monitor.worked(1);

		for (int i = 1; i < javaConvertersWithSameName.size(); i++) {  // NB: start with 1!
			((EclipseLinkJavaConverter<?>) javaConvertersWithSameName.get(i)).delete();
			monitor.worked(1);
		}
	}
	
	/**
	 * @see #extractEclipseLinkConvertibleJavaModels(Iterable, Iterable, EquivalencyAdapter)
	 */
	protected HashMap<String, ArrayList<EclipseLinkConverter>> getEclipseLinkConvertibleJavaConverters() {
		return this.extractEclipseLinkConvertibleJavaModels(this.getAllJavaConverters(), this.getMappingFileConverters(), CONVERTER_EQUIVALENCY_ADAPTER);
	}

	protected int calculateCumulativeSize(Collection<? extends Collection<?>> collections) {
		int cumulativeSize = 0;
		for (Collection<?> collection : collections) {
			cumulativeSize += collection.size();
		}
		return cumulativeSize;
	}

	protected <M extends JpaNamedContextModel> boolean hasAnyEquivalentJavaModels(Iterable<M> allJavaModels, Iterable<M> mappingFileModels, EquivalencyAdapter<M> adapter) {
		HashMap<String, ArrayList<M>> convertibleJavaModels = this.extractEclipseLinkConvertibleJavaModels(allJavaModels, mappingFileModels, adapter);
		for (Map.Entry<String, ArrayList<M>> entry : convertibleJavaModels.entrySet()) {
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
	protected <M extends JpaNamedContextModel> HashMap<String, ArrayList<M>> extractEclipseLinkConvertibleJavaModels(Iterable<M> allJavaModels, Iterable<M> mappingFileModels, EquivalencyAdapter<M> adapter) {
		HashMap<String, ArrayList<M>> convertibleModels = new HashMap<String, ArrayList<M>>();

		HashSet<String> mappingFileModelNames = this.convertToNames(ListTools.arrayList(mappingFileModels));
		HashMap<String, ArrayList<M>> allJavaModelsByName = this.mapByName(allJavaModels);
		for (Map.Entry<String, ArrayList<M>> entry : allJavaModelsByName.entrySet()) {
			String javaModelName = entry.getKey();
			if (StringTools.isBlank(javaModelName)) {
				continue;  // ignore any nodes with an empty name(?)
			}
			if (mappingFileModelNames.contains(javaModelName)) {
				continue;  // ignore any Java nodes overridden in the mapping file
			}
			ArrayList<M> javaModelsWithSameName = entry.getValue();
			if ((javaModelsWithSameName.size() == 1) || this.allAreEquivalent(javaModelsWithSameName, adapter)) {
				convertibleModels.put(javaModelName, javaModelsWithSameName);
			} else {
				// ignore multiple Java nodes with the same name but that are not all "equivalent"
			}
		}

		return convertibleModels;
	}
}
