/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.customization;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkPersistenceUnitProperties;
import org.eclipse.jpt.utility.internal.CollectionTools;
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
	private String sessionCustomizer;

	// key = Entity name ; value = Customizer properties
	private Map<String, CustomizerProperties> entitiesCustomizerProperties;
	
	private static final long serialVersionUID = 1L;
	
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
		this.sessionCustomizer = 
			this.getStringValue(ECLIPSELINK_SESSION_CUSTOMIZER);

		Set<Property> properties = 
			this.getPropertiesSetWithPrefix(ECLIPSELINK_DESCRIPTOR_CUSTOMIZER);
		
		this.initializeEntitiesCustomizerClass(properties);
	}

	private void initializeEntitiesCustomizerClass(Set<Property> properties) {
		for (Property property : properties) {
			String entityName = this.getEntityName(property);
			this.setCustomizerClass_(property, entityName);
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
			ECLIPSELINK_SESSION_CUSTOMIZER,
			SESSION_CUSTOMIZER_PROPERTY);

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
		else if (aspectName.equals(SESSION_CUSTOMIZER_PROPERTY)) {
			this.sessionCustomizerChanged(event);
		}
		else if (aspectName.equals(DESCRIPTOR_CUSTOMIZER_PROPERTY)) {
			this.descriptorCustomizerChanged(event);
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

	// ********** SessionCustomizer **********
	public String getSessionCustomizer() {
		return this.sessionCustomizer;
	}

	public void setSessionCustomizer(String newSessionCustomizer) {
		String old = this.sessionCustomizer;
		this.sessionCustomizer = newSessionCustomizer;
		this.putProperty(SESSION_CUSTOMIZER_PROPERTY, newSessionCustomizer);
		this.firePropertyChanged(SESSION_CUSTOMIZER_PROPERTY, old, newSessionCustomizer);
	}

	private void sessionCustomizerChanged(PropertyChangeEvent event) {
		String newValue = (event.getNewValue() == null) ? null : ((Property) event.getNewValue()).getValue();
		String old = this.sessionCustomizer;
		this.sessionCustomizer = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public String getDefaultSessionCustomizer() {
		return DEFAULT_SESSION_CUSTOMIZER;
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

	public String addEntity(String entity) {
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
		if (this.entitiesCustomizerProperties.containsKey(entity)) {
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
		if ( ! this.entitiesCustomizerProperties.containsKey(entity)) {
			return;
		}
		this.clearCustomizerProperties(entity);
		this.entitiesCustomizerProperties.remove(entity);
		this.fireListChanged(ENTITIES_LIST_PROPERTY);
	}
	
}