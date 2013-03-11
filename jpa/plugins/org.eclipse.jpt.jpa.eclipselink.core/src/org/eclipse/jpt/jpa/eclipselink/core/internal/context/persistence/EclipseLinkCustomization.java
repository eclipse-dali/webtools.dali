/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Customization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.CustomizationEntity;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkProfiler;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkWeaving;
import org.eclipse.text.edits.ReplaceEdit;

/**
 *  EclipseLinkCustomization
 */
public class EclipseLinkCustomization extends EclipseLinkPersistenceUnitProperties
	implements Customization
{
	// ********** EclipseLink properties **********
	private Boolean throwExceptions;
	private EclipseLinkWeaving weaving;
	private Boolean weavingLazy;
	private Boolean weavingChangeTracking;
	private Boolean weavingFetchGroups;
	private Boolean weavingInternal;
	private Boolean weavingEager;
	private Boolean validationOnly;
	private Boolean validateSchema;
	private List<String> sessionCustomizers;
	private String profiler; // storing EclipseLinkStringValue since value can be Profiler or custom class
	private String exceptionHandler;

	private List<CustomizationEntity> entities;
	
	// ********** constructors **********
	public EclipseLinkCustomization(PersistenceUnit parent) {
		super(parent);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		this.entities = new ArrayList<CustomizationEntity>();

		this.throwExceptions = 
			this.getBooleanValue(ECLIPSELINK_THROW_EXCEPTIONS);
		this.weaving = 
			this.getEnumValue(ECLIPSELINK_WEAVING, EclipseLinkWeaving.values());
		this.weavingLazy = 
			this.getBooleanValue(ECLIPSELINK_WEAVING_LAZY);
		this.weavingChangeTracking = 
			this.getBooleanValue(ECLIPSELINK_WEAVING_CHANGE_TRACKING);
		this.weavingFetchGroups = 
			this.getBooleanValue(ECLIPSELINK_WEAVING_FETCH_GROUPS);
		this.weavingInternal = 
			this.getBooleanValue(ECLIPSELINK_WEAVING_INTERNAL);
		this.weavingEager = 
			this.getBooleanValue(ECLIPSELINK_WEAVING_EAGER);
		this.validationOnly = 
			this.getBooleanValue(ECLIPSELINK_VALIDATION_ONLY);
		this.validateSchema = 
			this.getBooleanValue(ECLIPSELINK_VALIDATE_SCHEMA);
		this.initializeSessionCustomizersFromPersistenceUnit();

		Set<PersistenceUnit.Property> properties = 
			this.getPropertiesSetWithPrefix(ECLIPSELINK_DESCRIPTOR_CUSTOMIZER);
		this.initializeEntitiesCustomizerClass(properties);
		
		this.profiler = this.getProfilerPropertyValue();
		this.exceptionHandler = 
			this.getStringValue(ECLIPSELINK_EXCEPTION_HANDLER);
	}

	private void initializeSessionCustomizersFromPersistenceUnit() {
		this.sessionCustomizers = this.buildSessionCustomizers();
	}

	private List<String> buildSessionCustomizers() {
		return ListTools.list(this.convertToValues(this.getPropertiesSetWithPrefix(ECLIPSELINK_SESSION_CUSTOMIZER)));
	}

	private Iterable<String> convertToValues(Iterable<PersistenceUnit.Property> properties) {
		return IterableTools.transform(properties, PersistenceUnit.Property.VALUE_TRANSFORMER);
	}

	private void initializeEntitiesCustomizerClass(Set<PersistenceUnit.Property> descriptorCustomizerProperties) {
		for (PersistenceUnit.Property descriptorCustomizerProperty : descriptorCustomizerProperties) {
			this.setEntityDescriptorCustomizerOf(descriptorCustomizerProperty);
		}
	}

	/**
	 * Gets the Profiler property from the persistence unit.
	 */
	private String getProfilerPropertyValue() {

		String value = this.getStringValue(ECLIPSELINK_PROFILER);
		if (value == null) {
			return null;	// no property found
		}
		EclipseLinkProfiler standardProfiler = this.getEnumValue(ECLIPSELINK_PROFILER, EclipseLinkProfiler.values());
		return (standardProfiler == null) ? value : getPropertyStringValueOf(standardProfiler);
	}

	// ********** behavior **********
	
	public void propertyValueChanged(String propertyName, String newValue) {
		if (propertyName.equals(ECLIPSELINK_THROW_EXCEPTIONS)) {
			this.throwExceptionsChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_WEAVING)) {
			this.weavingChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_WEAVING_LAZY)) {
			this.weavingLazyChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_WEAVING_CHANGE_TRACKING)) {
			this.weavingChangeTrackingChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_WEAVING_FETCH_GROUPS)) {
			this.weavingFetchGroupsChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_WEAVING_INTERNAL)) {
			this.weavingInternalChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_WEAVING_EAGER)) {
			this.weavingEagerChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_VALIDATION_ONLY)) {
			this.validationOnlyChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_VALIDATE_SCHEMA)) {
			this.validateSchemaChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_SESSION_CUSTOMIZER)) {
			this.sessionCustomizersChanged();
		}
		else if (propertyName.startsWith(ECLIPSELINK_DESCRIPTOR_CUSTOMIZER)) {
			this.descriptorCustomizerChanged(propertyName, newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_PROFILER)) {
			this.profilerChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_EXCEPTION_HANDLER)) {
			this.exceptionHandlerChanged(newValue);
		}
	}
		
	public void propertyRemoved(String propertyName) {
		if (propertyName.equals(ECLIPSELINK_THROW_EXCEPTIONS)) {
			this.throwExceptionsChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_WEAVING)) {
			this.weavingChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_WEAVING_LAZY)) {
			this.weavingLazyChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_WEAVING_CHANGE_TRACKING)) {
			this.weavingChangeTrackingChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_WEAVING_FETCH_GROUPS)) {
			this.weavingFetchGroupsChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_WEAVING_INTERNAL)) {
			this.weavingInternalChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_WEAVING_EAGER)) {
			this.weavingEagerChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_VALIDATION_ONLY)) {
			this.validationOnlyChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_VALIDATE_SCHEMA)) {
			this.validateSchemaChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_SESSION_CUSTOMIZER)) {
			this.sessionCustomizersChanged();
		}
		else if (propertyName.startsWith(ECLIPSELINK_DESCRIPTOR_CUSTOMIZER)) {
			this.descriptorCustomizerChanged(propertyName, null);
		}
		else if (propertyName.equals(ECLIPSELINK_PROFILER)) {
			this.profilerChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_EXCEPTION_HANDLER)) {
			this.exceptionHandlerChanged(null);
		}
		
	}

	/**
	 * Adds property names key/value pairs, where: 
	 * 		key = EclipseLink property key
	 * 		value = property id
	 */
	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
		propertyNames.put(
			ECLIPSELINK_THROW_EXCEPTIONS,
			THROW_EXCEPTIONS_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_WEAVING,
			WEAVING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_WEAVING_LAZY,
			WEAVING_LAZY_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_WEAVING_CHANGE_TRACKING,
			WEAVING_CHANGE_TRACKING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_WEAVING_FETCH_GROUPS,
			WEAVING_FETCH_GROUPS_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_WEAVING_INTERNAL,
			WEAVING_INTERNAL_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_WEAVING_EAGER,
			WEAVING_EAGER_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_VALIDATION_ONLY,
			VALIDATION_ONLY_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_VALIDATE_SCHEMA,
			VALIDATE_SCHEMA_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_SESSION_CUSTOMIZER,
			SESSION_CUSTOMIZER_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_PROFILER,
			PROFILER_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_EXCEPTION_HANDLER,
			EXCEPTION_HANDLER_PROPERTY);

		// Don't need to initialize propertyNames for: 
		// descriptorCustomizerProperty
	}

	/**
	 * Method used for identifying the given property.
	 */
	@Override
	public boolean itemIsProperty(PersistenceUnit.Property item) {
		boolean isProperty = super.itemIsProperty(item);
		
		if ( ! isProperty && item.getName() != null) {
				if (item.getName().startsWith(ECLIPSELINK_DESCRIPTOR_CUSTOMIZER)) {
					return true;
				}
		}
		return isProperty;
	}

	/**
	 * Returns the property name used for change notification of the given
	 * property.
	 */
	@Override
	public String propertyIdOf(PersistenceUnit.Property property) {
		try {
			return super.propertyIdOf(property);
		}
		catch (IllegalArgumentException e) {
			if (property.getName().startsWith(ECLIPSELINK_DESCRIPTOR_CUSTOMIZER)) {
				return DESCRIPTOR_CUSTOMIZER_PROPERTY;
			}
		}
		throw new IllegalArgumentException("Illegal property: " + property); //$NON-NLS-1$
	}
	
	public CustomizationEntity addEntity(String entityName) {
		if (this.entityExists(entityName)) {
			throw new IllegalStateException("Duplicate entity: " + entityName); //$NON-NLS-1$
		}
		CustomizationEntity newEntity = this.buildEntity(entityName);
		this.addItemToList(newEntity, this.entities, ENTITIES_LIST);
		return newEntity;
	}

	public void removeEntity(String entityName) {
		if ( ! this.entityExists(entityName)) {
			return;
		}
		CustomizationEntity entity = this.getEntityNamed(entityName);
		this.clearEntity(entity);
		this.removeEntity(entity);
	}

	
	// ********** ThrowExceptions **********
	public Boolean getThrowExceptions() {
		return this.throwExceptions;
	}
	
	public void setThrowExceptions(Boolean newThrowExceptions) {
		Boolean old = this.throwExceptions;
		this.throwExceptions = newThrowExceptions;
		this.putProperty(THROW_EXCEPTIONS_PROPERTY, newThrowExceptions);
		this.firePropertyChanged(THROW_EXCEPTIONS_PROPERTY, old, newThrowExceptions);
	}

	private void throwExceptionsChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.throwExceptions;
		this.throwExceptions = newValue;
		this.firePropertyChanged(THROW_EXCEPTIONS_PROPERTY, old, newValue);
	}
	
	public Boolean getDefaultThrowExceptions() {
		return DEFAULT_THROW_EXCEPTIONS;
	}

	// ********** WeavingLazy **********
	public Boolean getWeavingLazy() {
		return this.weavingLazy;
	}

	public void setWeavingLazy(Boolean newWeavingLazy) {
		Boolean old = this.weavingLazy;
		this.weavingLazy = newWeavingLazy;
		this.putProperty(WEAVING_LAZY_PROPERTY, newWeavingLazy);
		this.firePropertyChanged(WEAVING_LAZY_PROPERTY, old, newWeavingLazy);
	}

	private void weavingLazyChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.weavingLazy;
		this.weavingLazy = newValue;
		this.firePropertyChanged(WEAVING_LAZY_PROPERTY, old, newValue);
	}

	public Boolean getDefaultWeavingLazy() {
		return DEFAULT_WEAVING_LAZY;
	}

	// ********** WeavingChangeTracking **********
	public Boolean getWeavingChangeTracking() {
		return this.weavingChangeTracking;
	}

	public void setWeavingChangeTracking(Boolean newWeavingChangeTracking) {
		Boolean old = this.weavingChangeTracking;
		this.weavingChangeTracking = newWeavingChangeTracking;
		this.putProperty(WEAVING_CHANGE_TRACKING_PROPERTY, newWeavingChangeTracking);
		this.firePropertyChanged(WEAVING_CHANGE_TRACKING_PROPERTY, old, newWeavingChangeTracking);
	}

	private void weavingChangeTrackingChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.weavingChangeTracking;
		this.weavingChangeTracking = newValue;
		this.firePropertyChanged(WEAVING_CHANGE_TRACKING_PROPERTY, old, newValue);
	}

	public Boolean getDefaultWeavingChangeTracking() {
		return DEFAULT_WEAVING_CHANGE_TRACKING;
	}

	// ********** WeavingFetchGroups **********
	public Boolean getWeavingFetchGroups() {
		return this.weavingFetchGroups;
	}

	public void setWeavingFetchGroups(Boolean newWeavingFetchGroups) {
		Boolean old = this.weavingFetchGroups;
		this.weavingFetchGroups = newWeavingFetchGroups;
		this.putProperty(WEAVING_FETCH_GROUPS_PROPERTY, newWeavingFetchGroups);
		this.firePropertyChanged(WEAVING_FETCH_GROUPS_PROPERTY, old, newWeavingFetchGroups);
	}

	private void weavingFetchGroupsChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.weavingFetchGroups;
		this.weavingFetchGroups = newValue;
		this.firePropertyChanged(WEAVING_FETCH_GROUPS_PROPERTY, old, newValue);
	}

	public Boolean getDefaultWeavingFetchGroups() {
		return DEFAULT_WEAVING_FETCH_GROUPS;
	}

	// ********** WeavingInternal **********
	public Boolean getWeavingInternal() {
		return this.weavingInternal;
	}

	public void setWeavingInternal(Boolean newWeavingInternal) {
		Boolean old = this.weavingInternal;
		this.weavingInternal = newWeavingInternal;
		this.putProperty(WEAVING_INTERNAL_PROPERTY, newWeavingInternal);
		this.firePropertyChanged(WEAVING_INTERNAL_PROPERTY, old, newWeavingInternal);
	}

	private void weavingInternalChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.weavingInternal;
		this.weavingInternal = newValue;
		this.firePropertyChanged(WEAVING_INTERNAL_PROPERTY, old, newValue);
	}

	public Boolean getDefaultWeavingInternal() {
		return DEFAULT_WEAVING_INTERNAL;
	}

	// ********** WeavingEager **********
	public Boolean getWeavingEager() {
		return this.weavingEager;
	}

	public void setWeavingEager(Boolean newWeavingEager) {
		Boolean old = this.weavingEager;
		this.weavingEager = newWeavingEager;
		this.putProperty(WEAVING_EAGER_PROPERTY, newWeavingEager);
		this.firePropertyChanged(WEAVING_EAGER_PROPERTY, old, newWeavingEager);
	}

	private void weavingEagerChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.weavingEager;
		this.weavingEager = newValue;
		this.firePropertyChanged(WEAVING_EAGER_PROPERTY, old, newValue);
	}

	public Boolean getDefaultWeavingEager() {
		return DEFAULT_WEAVING_EAGER;
	}

	// ********** ValidationOnly **********
	public Boolean getValidationOnly() {
		return this.validationOnly;
	}

	public void setValidationOnly(Boolean newValidationOnly) {
		Boolean old = this.validationOnly;
		this.validationOnly = newValidationOnly;
		this.putProperty(VALIDATION_ONLY_PROPERTY, newValidationOnly);
		this.firePropertyChanged(VALIDATION_ONLY_PROPERTY, old, newValidationOnly);
	}

	private void validationOnlyChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.validationOnly;
		this.validationOnly = newValue;
		this.firePropertyChanged(VALIDATION_ONLY_PROPERTY, old, newValue);
	}

	public Boolean getDefaultValidationOnly() {
		return DEFAULT_VALIDATION_ONLY;
	}

	// ********** ValidateSchema **********
	public Boolean getValidateSchema() {
		return this.validateSchema;
	}

	public void setValidateSchema(Boolean newValidateSchema) {
		Boolean old = this.validateSchema;
		this.validateSchema = newValidateSchema;
		this.putProperty(VALIDATE_SCHEMA_PROPERTY, newValidateSchema);
		this.firePropertyChanged(VALIDATE_SCHEMA_PROPERTY, old, newValidateSchema);
	}

	private void validateSchemaChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.validateSchema;
		this.validateSchema = newValue;
		this.firePropertyChanged(VALIDATE_SCHEMA_PROPERTY, old, newValue);
	}

	public Boolean getDefaultValidateSchema() {
		return DEFAULT_VALIDATE_SCHEMA;
	}

	// ********** SessionCustomizers **********
	public ListIterable<String> getSessionCustomizers(){
		return IterableTools.cloneLive(this.sessionCustomizers);
	}
	
	public int getSessionCustomizersSize(){
		return this.sessionCustomizers.size();
	}

	public boolean sessionCustomizerExists(String sessionCustomizerClassName) {

		for (String sessionCustomizer : this.sessionCustomizers) {
			if(sessionCustomizer.equals(sessionCustomizerClassName)) {
				return true;
			}
		}
		return false;
	}

	public String addSessionCustomizer(String newSessionCustomizerClassName){

		if( ! this.sessionCustomizerExists(newSessionCustomizerClassName)) {
			this.addItemToList(newSessionCustomizerClassName, this.sessionCustomizers, SESSION_CUSTOMIZER_LIST);
			this.putProperty(SESSION_CUSTOMIZER_PROPERTY, newSessionCustomizerClassName, true);
			return newSessionCustomizerClassName;
		}
		return null;
	}
	
	public void removeSessionCustomizer(String className){
		if (this.removeItemFromList(className, this.sessionCustomizers, SESSION_CUSTOMIZER_LIST)) {
			this.removeProperty(SESSION_CUSTOMIZER_PROPERTY, className);
		}
	}
	
	private void sessionCustomizersChanged() {
		this.synchronizeList(this.buildSessionCustomizers(), this.sessionCustomizers, SESSION_CUSTOMIZER_LIST);
	}

	// ********** Weaving **********
	
	public EclipseLinkWeaving getWeaving() {
		return this.weaving;
	}
	
	public void setWeaving(EclipseLinkWeaving newWeaving) {
		EclipseLinkWeaving old = this.weaving;
		this.weaving = newWeaving;
		this.putProperty(WEAVING_PROPERTY, newWeaving);
		this.firePropertyChanged(WEAVING_PROPERTY, old, newWeaving);
	}

	private void weavingChanged(String stringValue) {
		EclipseLinkWeaving newValue = getEnumValueOf(stringValue, EclipseLinkWeaving.values());
		EclipseLinkWeaving old = this.weaving;
		this.weaving = newValue;
		this.firePropertyChanged(WEAVING_PROPERTY, old, newValue);
	}
	
	public EclipseLinkWeaving getDefaultWeaving() {
		return DEFAULT_WEAVING;
	}

	// ********** ExceptionHandler **********
	public String getExceptionHandler() {
		return this.exceptionHandler;
	}

	public void setExceptionHandler(String newExceptionHandler) {
		String old = this.exceptionHandler;
		this.exceptionHandler = newExceptionHandler;
		this.putProperty(EXCEPTION_HANDLER_PROPERTY, newExceptionHandler);
		this.firePropertyChanged(EXCEPTION_HANDLER_PROPERTY, old, newExceptionHandler);
	}

	private void exceptionHandlerChanged(String newValue) {
		String old = this.exceptionHandler;
		this.exceptionHandler = newValue;
		this.firePropertyChanged(EXCEPTION_HANDLER_PROPERTY, old, newValue);
	}

	public String getDefaultExceptionHandler() {
		return DEFAULT_EXCEPTION_HANDLER;
	}

	// ********** DescriptorCustomizer **********
	
	public String getDescriptorCustomizerOf(String entityName) {
		CustomizationEntity entity = this.getEntityNamed(entityName);
		return (entity == null) ? null : entity.getDescriptorCustomizer();
	}
	
	public void setDescriptorCustomizerOf(String entityName, String newDescriptorCustomizer) {
		CustomizationEntity old = this.setEntityDescriptorCustomizerOf(entityName, newDescriptorCustomizer);
		this.putStringValue(ECLIPSELINK_DESCRIPTOR_CUSTOMIZER, entityName, newDescriptorCustomizer, false);
		this.firePropertyChanged(DESCRIPTOR_CUSTOMIZER_PROPERTY, old, this.getEntityNamed(entityName));
	}
	
	private void descriptorCustomizerChanged(String propertyName, String stringValue) {
		String entityName = this.extractEntityNameOf(propertyName);
		if( ! StringTools.isBlank(entityName)) {
			CustomizationEntity old = this.setEntityDescriptorCustomizerOf(entityName, stringValue);
			this.firePropertyChanged(DESCRIPTOR_CUSTOMIZER_PROPERTY, old, this.getEntityNamed(entityName));
		}
	}
	
	public String getDefaultDescriptorCustomizer() {
		return DEFAULT_DESCRIPTOR_CUSTOMIZER;
	}

	/**
	 * Returns the old Entity
	 */
	private CustomizationEntity setEntityDescriptorCustomizerOf(String entityName, String descriptorCustomizerClassName) {
		 if(( ! this.entityExists(entityName)) && StringTools.isBlank(descriptorCustomizerClassName)) {
				//this is a property that is currently being added, we don't need to deal with it until the value is set
				 return null;
			 }
		CustomizationEntity entity = (this.entityExists(entityName)) ?
						this.getEntityNamed(entityName) :
						this.addEntity(entityName);
		return this.setEntityDescriptorCustomizerOf(entity, descriptorCustomizerClassName);
	}
	
	/**
	 * Returns the old Entity
	 */
	private CustomizationEntity setEntityDescriptorCustomizerOf(CustomizationEntity entity, String descriptorCustomizerClassName) {
		if(entity == null) {
			throw new IllegalArgumentException();
		}
		CustomizationEntity old = entity.clone();
		entity.setDescriptorCustomizer(descriptorCustomizerClassName);
		return old;
	}
	
	/**
	 * Convenience method to update the descriptorCustomizerClassName in entities.
	 * Returns the old Entity
	 */
	private CustomizationEntity setEntityDescriptorCustomizerOf(PersistenceUnit.Property descriptorCustomizerProperty) {
		String entityName = this.extractEntityNameOf(descriptorCustomizerProperty);
		if(StringTools.isBlank(entityName)) {
			return null;
		}
		return this.setEntityDescriptorCustomizerOf(entityName, descriptorCustomizerProperty.getValue());
	}

	// ********** Profiler **********
	/**
	 * Returns Profiler or custom profiler class.
	 * 
	 * @return EclipseLink string value for Profiler enum or custom profiler class
	 */
	public String getProfiler() {
		return this.profiler;
	}

	/**
	 * Sets EclipseLink profiler.
	 * 
	 * @param newProfiler - Profiler
	 */
	public void setProfiler(EclipseLinkProfiler newProfiler) {
		if( newProfiler == null) {
			this.setProfiler_((String) null);
			return;
		}
		this.setProfiler_(getPropertyStringValueOf(newProfiler));
	}

	/**
	 * Sets EclipseLink profiler or custom profiler.
	 * 
	 * @param newProfiler -
	 *            Can be a EclipseLink profiler literal or
	 *            a fully qualified class name of a custom profiler.
	 */
	public void setProfiler(String newProfiler) {
		if( newProfiler == null) {
			this.setProfiler_((String) null);
			return;
		}
		EclipseLinkProfiler p = EclipseLinkProfiler.getProfilerFor(newProfiler);
		if(p == null) {	// custom profiler class
			this.setProfiler_(newProfiler);
		}
		else {
			this.setProfiler(p);
		}
	}
	
	private void setProfiler_(String newProfiler) {
		String old = this.profiler;
		this.profiler = newProfiler;
		this.putProperty(PROFILER_PROPERTY, newProfiler);
		this.firePropertyChanged(PROFILER_PROPERTY, old, newProfiler);
	}

	private void profilerChanged(String newValue) {
		String old = this.profiler;
		this.profiler = newValue;
		this.firePropertyChanged(PROFILER_PROPERTY, old, newValue);
	}

	public String getDefaultProfiler() {
		return DEFAULT_PROFILER;
	}


	// ****** convenience methods *******

	/**
	 * Set all Entity properties to default.
	 */
	private void clearEntity(CustomizationEntity entity) {
		if(entity.isEmpty()) {
			return;
		}
		String entityName = entity.getName();
		this.setDescriptorCustomizerOf(entityName, null);
	}

	/**
	 * Returns the Entity with the given name.
	 */
	private CustomizationEntity getEntityNamed(String name) {
		for(CustomizationEntity entity: this.entities) {
			if(entity.getName().equals(name)) {
				return entity;
			}
		}
		return null;
	}

	private CustomizationEntity buildEntity(String name) {
		return new CustomizationEntity(this, name);
	}
	
	private void removeEntity(CustomizationEntity entity) {
		if(entity == null) {
			throw new NullPointerException();
		}
		this.removeItemFromList(entity, this.entities, ENTITIES_LIST);
	}

	/**
	 * Return whether the Entity exist.
	 */
	public boolean entityExists(String name) {
		for(CustomizationEntity entity: this.entities) {
			if(entity.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	// ****** entities list *******

	public ListIterable<CustomizationEntity> getEntities() {
		return IterableTools.cloneLive(this.entities);
	}

	public Iterable<String> getEntityNames() {
		return IterableTools.transform(this.getEntities(), CustomizationEntity.NAME_TRANSFORMER);
	}

	public int getEntitiesSize() {
		return this.entities.size();
	}


	// ********** refactoring ************

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.concatenate(
				this.createSessionCustomizerRenameTypeEdits(originalType, newName),
				this.createExceptionHandlerRenameTypeEdits(originalType, newName)
			);
	}

	protected Iterable<ReplaceEdit> createSessionCustomizerRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.children(this.getPersistenceUnit().getPropertiesNamed(ECLIPSELINK_SESSION_CUSTOMIZER), new TypeRefactoringParticipant.RenameTypeEditsTransformer(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createExceptionHandlerRenameTypeEdits(IType originalType, String newName) {
		PersistenceUnit.Property property = getPersistenceUnit().getProperty(ECLIPSELINK_EXCEPTION_HANDLER);
		return (property != null) ?
				property.createRenameTypeEdits(originalType, newName) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.concatenate(
				this.createSessionCustomizerMoveTypeEdits(originalType, newPackage),
				this.createExceptionHandlerMoveTypeEdits(originalType, newPackage)
			);
	}

	protected Iterable<ReplaceEdit> createSessionCustomizerMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.children(this.getPersistenceUnit().getPropertiesNamed(ECLIPSELINK_SESSION_CUSTOMIZER), new TypeRefactoringParticipant.MoveTypeEditsTransformer(originalType, newPackage));
	}

	protected Iterable<ReplaceEdit> createExceptionHandlerMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		PersistenceUnit.Property property = getPersistenceUnit().getProperty(ECLIPSELINK_EXCEPTION_HANDLER);
		return (property != null) ?
				property.createMoveTypeEdits(originalType, newPackage) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
				this.createSessionCustomizerRenamePackageEdits(originalPackage, newName),
				this.createExceptionHandlerRenamePackageEdits(originalPackage, newName)
			);
	}

	protected Iterable<ReplaceEdit> createSessionCustomizerRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.children(this.getPersistenceUnit().getPropertiesNamed(ECLIPSELINK_SESSION_CUSTOMIZER), new TypeRefactoringParticipant.RenamePackageEditsTransformer(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createExceptionHandlerRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		PersistenceUnit.Property property = getPersistenceUnit().getProperty(ECLIPSELINK_EXCEPTION_HANDLER);
		return (property != null) ?
				property.createRenamePackageEdits(originalPackage, newName) :
				IterableTools.<ReplaceEdit>emptyIterable();
	}
}
