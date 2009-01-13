/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence.customization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnitProperties;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 *  EclipseLinkCustomization
 */
public class EclipseLinkCustomization extends EclipseLinkPersistenceUnitProperties
	implements Customization
{
	// ********** EclipseLink properties **********
	private Boolean throwExceptions;
	private Weaving weaving;
	private Boolean weavingLazy;
	private Boolean weavingChangeTracking;
	private Boolean weavingFetchGroups;
	private Boolean weavingInternal;
	private Boolean weavingEager;
	private Boolean validationOnly;
	private ArrayList<String> sessionCustomizers;
	private String profiler; // storing EclipseLinkStringValue since value can be Profiler or custom class
	private String exceptionHandler;

	// key = Entity name ; value = Customizer properties
	private Map<String, CustomizerProperties> entitiesCustomizerProperties;
	
	// ********** constructors **********
	public EclipseLinkCustomization(PersistenceUnit parent, ListValueModel<Property> propertyListAdapter) {
		super(parent, propertyListAdapter);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		// TOREVIEW - handle incorrect String in persistence.xml
		this.entitiesCustomizerProperties = 
			new HashMap<String, CustomizerProperties>();

		this.throwExceptions = 
			this.getBooleanValue(ECLIPSELINK_THROW_EXCEPTIONS);
		this.weaving = 
			this.getEnumValue(ECLIPSELINK_WEAVING, Weaving.values());
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
		this.initializeSessionCustomizersFromPersistenceUnit();

		Set<Property> properties = 
			this.getPropertiesSetWithPrefix(ECLIPSELINK_DESCRIPTOR_CUSTOMIZER);
		this.initializeEntitiesCustomizerClass(properties);
		
		this.profiler = this.getProfilerProtertyValue();
		this.exceptionHandler = 
			this.getStringValue(ECLIPSELINK_EXCEPTION_HANDLER);
	}

	private void initializeSessionCustomizersFromPersistenceUnit() {
		Set<Property> properties = 
			this.getPropertiesSetWithPrefix(ECLIPSELINK_SESSION_CUSTOMIZER);
		
		this.sessionCustomizers = new ArrayList<String>(properties.size());
		this.initializeSessionCustomizersWith(properties);
	}

	private void initializeSessionCustomizersWith(Set<Property> properties) {
		for (Property property : properties) {
			this.sessionCustomizers.add(property.getValue());
		}
	}

	private void initializeEntitiesCustomizerClass(Set<Property> properties) {
		for (Property property : properties) {
			String entityName = this.getEntityName(property);
			this.setCustomizerClass_(property, entityName);
		}
	}

	/**
	 * Gets the Profiler property from the persistence unit.
	 */
	private String getProfilerProtertyValue() {

		Profiler standardProfiler = this.getEnumValue(ECLIPSELINK_PROFILER, Profiler.values());
		if( ! this.getPersistenceUnit().containsProperty(ECLIPSELINK_PROFILER)) {
			return null;	// no property found
		}
		else if(standardProfiler == null) {
			return this.getStringValue(ECLIPSELINK_PROFILER); // custom profiler
		}
		else {
			return getEclipseLinkStringValueOf(standardProfiler); // a Profiler
		}
	}

	// ********** behavior **********
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
	public boolean itemIsProperty(Property item) {
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
	public String propertyIdFor(Property property) {
		try {
			return super.propertyIdFor(property);
		}
		catch (IllegalArgumentException e) {
			if (property.getName().startsWith(ECLIPSELINK_DESCRIPTOR_CUSTOMIZER)) {
				return DESCRIPTOR_CUSTOMIZER_PROPERTY;
			}
		}
		throw new IllegalArgumentException("Illegal property: " + property.toString());
	}

	public void propertyChanged(PropertyChangeEvent event) {
		String aspectName = event.getAspectName();
		if (aspectName.equals(THROW_EXCEPTIONS_PROPERTY)) {
			this.throwExceptionsChanged(event);
		}
		else if (aspectName.equals(WEAVING_PROPERTY)) {
			this.weavingChanged(event);
		}
		else if (aspectName.equals(WEAVING_LAZY_PROPERTY)) {
			this.weavingLazyChanged(event);
		}
		else if (aspectName.equals(WEAVING_CHANGE_TRACKING_PROPERTY)) {
			this.weavingChangeTrackingChanged(event);
		}
		else if (aspectName.equals(WEAVING_FETCH_GROUPS_PROPERTY)) {
			this.weavingFetchGroupsChanged(event);
		}
		else if (aspectName.equals(WEAVING_INTERNAL_PROPERTY)) {
			this.weavingInternalChanged(event);
		}
		else if (aspectName.equals(WEAVING_EAGER_PROPERTY)) {
			this.weavingEagerChanged(event);
		}
		else if (aspectName.equals(VALIDATION_ONLY_PROPERTY)) {
			this.validationOnlyChanged(event);
		}
		else if (aspectName.equals(SESSION_CUSTOMIZER_PROPERTY)) {
			this.sessionCustomizersChanged(event);
		}
		else if (aspectName.equals(DESCRIPTOR_CUSTOMIZER_PROPERTY)) {
			this.descriptorCustomizerChanged(event);
		}
		else if (aspectName.equals(PROFILER_PROPERTY)) {
			this.profilerChanged(event);
		}
		else if (aspectName.equals(EXCEPTION_HANDLER_PROPERTY)) {
			this.exceptionHandlerChanged(event);
		}
		else {
			throw new IllegalArgumentException("Illegal event received - property not applicable: " + aspectName);
		}
		return;
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

	private void throwExceptionsChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.throwExceptions;
		this.throwExceptions = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
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

	private void weavingLazyChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.weavingLazy;
		this.weavingLazy = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
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

	private void weavingChangeTrackingChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.weavingChangeTracking;
		this.weavingChangeTracking = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
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

	private void weavingFetchGroupsChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.weavingFetchGroups;
		this.weavingFetchGroups = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
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

	private void weavingInternalChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.weavingInternal;
		this.weavingInternal = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
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

	private void weavingEagerChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.weavingEager;
		this.weavingEager = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
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

	private void validationOnlyChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.validationOnly;
		this.validationOnly = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Boolean getDefaultValidationOnly() {
		return DEFAULT_VALIDATION_ONLY;
	}

	// ********** SessionCustomizers **********
	public ListIterator<String> sessionCustomizers(){
		return new CloneListIterator<String>(this.sessionCustomizers);
	}
	
	public int sessionCustomizersSize(){
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
			this.sessionCustomizers.add(newSessionCustomizerClassName);
			this.putProperty(SESSION_CUSTOMIZER_PROPERTY, newSessionCustomizerClassName, true);
			this.fireListChanged(SESSION_CUSTOMIZER_LIST_PROPERTY);
			return newSessionCustomizerClassName;
		}
		return null;
	}
	
	public void removeSessionCustomizer(String className){

		if(this.removeSessionCustomizer_(className) != null) {
			this.removeProperty(SESSION_CUSTOMIZER_PROPERTY, className);
			this.fireListChanged(SESSION_CUSTOMIZER_LIST_PROPERTY);
		}
	}
	
	private String removeSessionCustomizer_(String className){

		for ( ListIterator<String> i = this.sessionCustomizers(); i.hasNext();) {
			String sessionCustomizer = i.next();
			if(sessionCustomizer.equals(className)) {
				this.sessionCustomizers.remove(sessionCustomizer);
				return sessionCustomizer;
			}
		}
		return null;
	}

	private void sessionCustomizersChanged(@SuppressWarnings("unused") PropertyChangeEvent event) {

		this.initializeSessionCustomizersFromPersistenceUnit();
		this.fireListChanged(SESSION_CUSTOMIZER_LIST_PROPERTY);
	}

	// ********** Weaving **********
	
	public Weaving getWeaving() {
		return this.weaving;
	}
	
	public void setWeaving(Weaving newWeaving) {
		Weaving old = this.weaving;
		this.weaving = newWeaving;
		this.putProperty(WEAVING_PROPERTY, newWeaving);
		this.firePropertyChanged(WEAVING_PROPERTY, old, newWeaving);
	}

	private void weavingChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		Weaving newValue = getEnumValueOf(stringValue, Weaving.values());
		Weaving old = this.weaving;
		this.weaving = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}
	
	public Weaving getDefaultWeaving() {
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

	private void exceptionHandlerChanged(PropertyChangeEvent event) {
		String newValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		String old = this.exceptionHandler;
		this.exceptionHandler = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public String getDefaultExceptionHandler() {
		return DEFAULT_EXCEPTION_HANDLER;
	}

	// ********** DescriptorCustomizer **********
	
	public String getDescriptorCustomizer(String entityName) {
		CustomizerProperties customizer = this.customizerPropertiesOf(entityName);
		return (customizer == null) ? null : customizer.getClassName();
	}
	
	public void setDescriptorCustomizer(String newDescriptorCustomizer, String entityName) {
		CustomizerProperties old = this.setCustomizerClass_(newDescriptorCustomizer, entityName);
		this.putStringValue(ECLIPSELINK_DESCRIPTOR_CUSTOMIZER, entityName, newDescriptorCustomizer, false);
		this.firePropertyChanged(DESCRIPTOR_CUSTOMIZER_PROPERTY, old, this.customizerPropertiesOf(entityName));
	}

	private void descriptorCustomizerChanged(PropertyChangeEvent event) {
		String entityName;
		Property newProperty = (Property) event.getNewValue();
		// property == null when removed
		entityName = (newProperty == null) ? 
			this.getEntityName((Property) event.getOldValue()) : this.getEntityName(newProperty);
		CustomizerProperties old = this.setCustomizerClass_(newProperty, entityName);
		this.firePropertyChanged(event.getAspectName(), old, this.customizerPropertiesOf(entityName));
	}
	
	public String getDefaultDescriptorCustomizer() {
		return DEFAULT_DESCRIPTOR_CUSTOMIZER;
	}


	// ****** CustomizerProperties *******

	/**
	 * Convenience method to update the CustomizerClass in entitiesCustomizerProperties map.
	 * Returns the old value of CustomizerProperties
	 */
	private CustomizerProperties setCustomizerClass_(Property newProperty, String entityName) {
		String newValue = (newProperty == null) ? null : newProperty.getValue();
		return this.setCustomizerClass_(newValue, entityName);
	}

	private CustomizerProperties setCustomizerClass_(String newValue, String entityName) {
		CustomizerProperties properties = this.customizerPropertiesOf(entityName);
		CustomizerProperties old = properties.clone();
		properties.setClassName(newValue);
		this.putEntityCustomizerProperties(entityName, properties);
		return old;
	}

	/**
	 * Returns the CustomizerProperties of the Entity with the given name.
	 */
	private CustomizerProperties customizerPropertiesOf(String entityName) {
		CustomizerProperties properties = this.entitiesCustomizerProperties.get(entityName);
		if (properties == null) {
			properties = new CustomizerProperties(entityName);
		}
		return properties;
	}

	/**
	 * Set all CustomizerProperties to default.
	 */
	private void clearCustomizerProperties(String entityName) {
		this.setDescriptorCustomizer(null, entityName);
	}

	// ****** convenience methods *******
	/**
	 * Put the given Entity CustomizerProperties in this entitiesCustomizerProperties map.
	 * @param entityName - Entity name. The entity may be a new or an existing entity.
	 * @param properties - Entity CustomizerProperties
	 */
	private void putEntityCustomizerProperties(String entityName, CustomizerProperties properties) {
		this.addOrReplacePropertiesForEntity(entityName, properties);
	}
	


	// ****** entities list *******
	
	public ListIterator<String> entities() {
		return CollectionTools.list(this.entitiesCustomizerProperties.keySet()).listIterator();
	}

	public int entitiesSize() {
		return this.entitiesCustomizerProperties.size();
	}

	/* 
	 * Verifies if this entitiesCacheProperties map contains the given Entity. 
	 */
	public boolean entityExists(String entity) {
		return this.entitiesCustomizerProperties.containsKey(entity);
	}

	public String addEntity(String entity) {
		if (entityExists(entity)) {
			throw new IllegalStateException("Entity " + entity + " already exist.");
		}
		return this.addOrReplacePropertiesForEntity(entity, new CustomizerProperties(entity));
	}

	/**
	 * Adds or Replaces the given Entity CustomizerProperties in 
	 * this entitiesCustomizerProperties map.
	 * If the specified Entity exists and the given CustomizerProperties is empty 
	 * (i.e. all properties are null) the mapping will be removed from the map.
	 * @param entity - Entity name
	 * @param properties - Entity CustomizerProperties
	 * @return
	 */
	private String addOrReplacePropertiesForEntity(String entity, CustomizerProperties properties) {
		if (entityExists(entity)) {
			this.replaceEntity_(entity, properties);
			return null;
		}
		this.entitiesCustomizerProperties.put(entity, properties);
		this.fireListChanged(ENTITIES_LIST_PROPERTY);
		return entity;
	}

	/**
	 * Replaces the given Entity CustomizerProperties in this
	 * entitiesCustomizerProperties map.
	 * If the Entity CustomizerProperties is empty (i.e. all properties is null) the 
	 * mapping will be removed from the map.
	 * @param entity - Entity name
	 * @param properties - Entity CustomizerProperties
	 * @return
	 */
	private CustomizerProperties replaceEntity_(String entity, CustomizerProperties properties) {
		CustomizerProperties old = this.entitiesCustomizerProperties.get(entity);
		if (properties.isEmpty()) {
			this.entitiesCustomizerProperties.remove(entity);
			this.fireListChanged(ENTITIES_LIST_PROPERTY);
		}
		else {
			this.entitiesCustomizerProperties.put(entity, properties);
		}
		return old;
	}

	public void removeEntity(String entity) {
		if ( ! entityExists(entity)) {
			return;
		}
		this.clearCustomizerProperties(entity);
		this.entitiesCustomizerProperties.remove(entity);
		this.fireListChanged(ENTITIES_LIST_PROPERTY);
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
	public void setProfiler(Profiler newProfiler) {
		if( newProfiler == null) {
			this.setProfiler_((String) null);
			return;
		}
		this.setProfiler_(getEclipseLinkStringValueOf(newProfiler));
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
		Profiler profiler = Profiler.getProfilerFor(newProfiler);
		if(profiler == null) {	// custom profiler class
			this.setProfiler_(newProfiler);
		}
		else {
			this.setProfiler(profiler);
		}
	}
	
	private void setProfiler_(String newProfiler) {
		String old = this.profiler;
		this.profiler = newProfiler;
		this.putProperty(PROFILER_PROPERTY, newProfiler);
		this.firePropertyChanged(PROFILER_PROPERTY, old, newProfiler);
	}

	private void profilerChanged(PropertyChangeEvent event) {
		String newValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		String old = this.profiler;
		this.profiler = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public String getDefaultProfiler() {
		return DEFAULT_PROFILER;
	}

}