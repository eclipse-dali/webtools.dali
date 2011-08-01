/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.ConvertibleMapping;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence.ImpliedMappingFileRef;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.JavaEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmTypeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.general.GeneralProperties;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.logging.Logging;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.options.Options;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.schema.generation.SchemaGeneration;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.java.JavaEclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.caching.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.customization.EclipseLinkCustomization;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.general.EclipseLinkGeneralProperties;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.schema.generation.EclipseLinkSchemaGeneration;
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


	public EclipseLinkPersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		super(parent, xmlPersistenceUnit);
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
		this.setConverters(this.buildConverters());
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
	public Iterator<EclipseLinkConverter> allConverters() {
		return this.getConverters().iterator();
	}

	public Iterable<EclipseLinkConverter> getConverters() {
		return new LiveCloneIterable<EclipseLinkConverter>(this.converters);
	}

	public int convertersSize() {
		return this.converters.size();
	}

	// TODO bjv remove
	public void addConverter(EclipseLinkConverter converter) {
		this.converters.add(converter);
	}

	/**
	 * Return the names of the "active" converters defined in the persistence
	 * unit's scope, with duplicates removed.
	 */
	public Iterable<String> getUniqueConverterNames() {
		HashSet<String> names = new HashSet<String>(this.converters.size());
		this.addNonEmptyConverterNamesTo(names);
		return names;
	}

	protected void addNonEmptyConverterNamesTo(Set<String> names) {
		for (EclipseLinkConverter converter : this.getConverters()) {
			String converterName = converter.getName();
			if (StringTools.stringIsNotEmpty(converterName)) {
				names.add(converterName);
			}
		}
	}

	protected void setConverters(Iterable<EclipseLinkConverter> converters) {
		this.synchronizeCollection(converters, this.converters, CONVERTERS_COLLECTION);
	}

	/**
	 * We only hold "active" converters; i.e. the mapping file converters and
	 * the Java converters that are not "overridden" by mapping file
	 * converters (by converter name).
	 */
	protected Iterable<EclipseLinkConverter> buildConverters() {
		ArrayList<EclipseLinkConverter> converterList = new ArrayList<EclipseLinkConverter>();

		this.addMappingFileConvertersTo(converterList);

		HashMap<String, ArrayList<EclipseLinkConverter>> mappingFileConverters = this.mapConvertersByName(this.getMappingFileConverters());
		HashMap<String, ArrayList<EclipseLinkConverter>> javaConverters = this.mapConvertersByName(this.getJavaConverters());
		for (Map.Entry<String, ArrayList<EclipseLinkConverter>> javaConverterEntry : javaConverters.entrySet()) {
			if (mappingFileConverters.get(javaConverterEntry.getKey()) == null) {
				converterList.addAll(javaConverterEntry.getValue());
			}
		}

		return converterList;
	}

	protected Iterable<EclipseLinkConverter> getMappingFileConverters() {
		ArrayList<EclipseLinkConverter> converterList = new ArrayList<EclipseLinkConverter>();
		this.addMappingFileConvertersTo(converterList);
		return converterList;
	}

	protected void addMappingFileConvertersTo(ArrayList<EclipseLinkConverter> converterList) {
		for (MappingFileRef mappingFileRef : this.getMappingFileRefs()) {
			MappingFile mappingFile = mappingFileRef.getMappingFile();
			// TODO bjv - bogus cast - need to add API to MappingFileRef?
			if (mappingFile instanceof OrmXml) {
				EntityMappings entityMappings = ((OrmXml) mappingFile).getRoot();
				if (entityMappings instanceof EclipseLinkEntityMappings) {
					this.addConvertersTo(((EclipseLinkEntityMappings) entityMappings).getConverterContainer(), converterList);
				}
			}
		}
		this.addConvertersTo(this.getMappingFilePersistentTypes(), converterList);
	}

	/**
	 * Include "overridden" Java converters.
	 */
	protected Iterable<EclipseLinkConverter> getJavaConverters() {
		ArrayList<EclipseLinkConverter> converterList = new ArrayList<EclipseLinkConverter>();
		this.addJavaConvertersTo(converterList);
		return converterList;
	}

	/**
	 * Include "overridden" Java converters.
	 */
	protected void addJavaConvertersTo(ArrayList<EclipseLinkConverter> converterList) {
		this.addConvertersTo(this.getAllJavaPersistentTypesUnique(), converterList);
	}

	// TODO bjv - bogus casts - need to delegate...
	protected void addConvertersTo(Iterable<PersistentType> persistentTypes, ArrayList<EclipseLinkConverter> converterList) {
		for (PersistentType persistentType : persistentTypes) {
			TypeMapping typeMapping = persistentType.getMapping();
			if (typeMapping instanceof EclipseLinkOrmTypeMapping) {
				this.addConvertersTo(((EclipseLinkOrmTypeMapping) typeMapping).getConverterContainer(), converterList);
			}
			else if (typeMapping instanceof EclipseLinkJavaTypeMapping) {
				this.addConvertersTo(((EclipseLinkJavaTypeMapping) typeMapping).getConverterContainer(), converterList);
			}
			for (ReadOnlyPersistentAttribute persistentAttribute : persistentType.getAttributes()) {
				AttributeMapping attributeMapping = persistentAttribute.getMapping();
				if (attributeMapping instanceof ConvertibleMapping) {
					Converter converter = ((ConvertibleMapping) attributeMapping).getConverter();
					if ((converter != null) && (converter.getType() == EclipseLinkConvert.class)) {
						EclipseLinkConverter elConverter = ((EclipseLinkConvert) converter).getConverter();
						if (elConverter != null) {
							converterList.add(elConverter);
						}
					}
				}
			}
		}
	}

	protected void addConvertersTo(OrmEclipseLinkConverterContainer converterContainer, ArrayList<EclipseLinkConverter> converterList) {
		CollectionTools.addAll(converterList, converterContainer.getCustomConverters());
		CollectionTools.addAll(converterList, converterContainer.getObjectTypeConverters());
		CollectionTools.addAll(converterList, converterContainer.getStructConverters());
		CollectionTools.addAll(converterList, converterContainer.getTypeConverters());
	}

	protected void addConvertersTo(JavaEclipseLinkConverterContainer converterContainer, ArrayList<EclipseLinkConverter> converterList) {
		EclipseLinkConverter converter = converterContainer.getCustomConverter();
		if (converter != null) {
			converterList.add(converter);
		}
		converter = converterContainer.getObjectTypeConverter();
		if (converter != null) {
			converterList.add(converter);
		}
		converter = converterContainer.getStructConverter();
		if (converter != null) {
			converterList.add(converter);
		}
		converter = converterContainer.getTypeConverter();
		if (converter != null) {
			converterList.add(converter);
		}
	}

	protected HashMap<String, ArrayList<EclipseLinkConverter>> mapConvertersByName(Iterable<EclipseLinkConverter> converterList) {
		HashMap<String, ArrayList<EclipseLinkConverter>> map = new HashMap<String, ArrayList<EclipseLinkConverter>>();
		for (EclipseLinkConverter converter : converterList) {
			String converterName = converter.getName();
			ArrayList<EclipseLinkConverter> list = map.get(converterName);
			if (list == null) {
				list = new ArrayList<EclipseLinkConverter>();
				map.put(converterName, list);
			}
			list.add(converter);
		}
		return map;
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
		} else if (!JDTTools.typeNamedImplementsInterfaceNamed(
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
		} else if (!JDTTools.typeNamedImplementsInterfaceNamed(
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
		} else if (!JDTTools.typeNamedImplementsInterfaceNamed(
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
				} else if (!JDTTools.typeNamedImplementsInterfaceNamed(
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
		HashMap<String, ArrayList<EclipseLinkConverter>> convertersByName = this.mapConvertersByName(this.getConverters());
		for (ArrayList<EclipseLinkConverter> dups : convertersByName.values()) {
			if (dups.size() > 1) {
				for (EclipseLinkConverter dup : dups) {
					messages.add(
						DefaultEclipseLinkJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							EclipseLinkJpaValidationMessages.CONVERTER_DUPLICATE_NAME,
							new String[] {dup.getName()},
							dup,
							this.extractNameTextRange(dup)
						)
					);
				}
			}
		}
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
