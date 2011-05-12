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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Vector;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.common.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.common.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence.ImpliedMappingFileRef;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.caching.Caching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.general.GeneralProperties;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.logging.Logging;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.options.Options;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.schema.generation.SchemaGeneration;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
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

	// TODO bjv calculate converters directly...
	@Override
	public void update() {
		this.converters.clear();
		super.update();
		this.fireCollectionChanged(CONVERTERS_COLLECTION, this.converters);
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
	protected ListIterable<MappingFileRef> getMappingFileRefs() {
		return (this.impliedEclipseLinkMappingFileRef == null) ?
				super.getMappingFileRefs() :
				new CompositeListIterable<MappingFileRef>(super.getMappingFileRefs(), this.impliedEclipseLinkMappingFileRef);
	}

	@Override
	public int mappingFileRefsSize() {
		return (this.impliedEclipseLinkMappingFileRef == null) ?
				super.mappingFileRefsSize() :
				super.mappingFileRefsSize() + 1;
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
	 * Identifier for changes to the list of global converter definitions.
	 * Note that there are no granular changes to this list.  There is only
	 * notification that the entire list has changed.
	 */
	public static final String CONVERTERS_COLLECTION = "converters"; //$NON-NLS-1$

	/**
	 * Add the converter definition (defined elsewhere) to the list of converters
	 * defined within this persistence unit.
	 * Note that this should only be called during the process of updating the
	 * local converter definition.
	 * No change notification accompanies this action specifically.
	 */
	public void addConverter(EclipseLinkConverter converter) {
		this.converters.add(converter);
	}

	/**
	 * Return an iterator on all converters defined within this persistence unit,
	 * included duplicately named converters definitions.
	 */
	public ListIterator<EclipseLinkConverter> allConverters() {
		return new CloneListIterator<EclipseLinkConverter>(this.converters);
	}

	public int convertersSize() {
		return this.converters.size();
	}

	/**
	 * Return the names of the converters defined in the persistence
	 * unit, with duplicates removed.
	 */
	public Iterable<String> getUniqueConverterNames() {
		return CollectionTools.set(this.allNonNullConverterNames(), this.converters.size());
	}

	protected Iterator<String> allNonNullConverterNames() {
		return new FilteringIterator<String>(this.allConverterNames(), NotNullFilter.<String>instance());
	}

	protected Iterator<String> allConverterNames() {
		return new TransformationIterator<EclipseLinkConverter, String>(this.allConverters()) {
			@Override
			protected String transform(EclipseLinkConverter converter) {
				return converter.getName();
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
	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
		super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
		nonUpdateAspectNames.add(CONVERTERS_COLLECTION);
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

	private Property getFlushClearCacheProperty() {
		return this.getProperty(Caching.ECLIPSELINK_FLUSH_CLEAR_CACHE);
	}

	/**
	 * Returns all Shared Cache Properties, including Entity and default.
	 */
	private Iterable<Property> getSharedCacheProperties() {
		return CollectionTools.iterable(this.propertiesWithNamePrefix(Caching.ECLIPSELINK_SHARED_CACHE));
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
	   return new FilteringIterable<Property>(
		   				CollectionTools.iterable(this.propertiesWithNamePrefix(prefix))) {
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
		return CollectionTools.iterable(this.propertiesWithNamePrefix(Customization.ECLIPSELINK_SESSION_CUSTOMIZER));
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
