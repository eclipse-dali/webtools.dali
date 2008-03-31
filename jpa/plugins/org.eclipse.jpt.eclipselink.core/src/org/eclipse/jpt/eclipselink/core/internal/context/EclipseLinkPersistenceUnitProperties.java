/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * EclipseLinkPersistenceUnitProperties
 * 
 * Listens to the propertyListAdapter
 */
public abstract class EclipseLinkPersistenceUnitProperties extends AbstractModel 
				implements PersistenceUnitProperties
{
	private PersistenceUnit persistenceUnit;

	private PersistenceUnitPropertyListListener propertyListListener;

	// key = EclipseLink property key; value = property id
	private Map<String, String> propertyNames;

	// ********** constructors / initialization **********
	protected EclipseLinkPersistenceUnitProperties(
			PersistenceUnit parent, 
			ListValueModel<Property> propertyListAdapter) {
		super();
		this.initialize(parent, propertyListAdapter);
	}

	protected void initialize(
			PersistenceUnit parent, 
			ListValueModel<Property> propertyListAdapter) {
		this.persistenceUnit = parent;
		
		this.propertyListListener = new PersistenceUnitPropertyListListener(this);
		propertyListAdapter.addListChangeListener(ListValueModel.LIST_VALUES, this.propertyListListener);
		
		this.initializePropertyNames();
		this.initializeProperties();
	}

	protected void initializePropertyNames() {
		this.propertyNames = new HashMap<String, String>();
		this.addPropertyNames(this.propertyNames);
	}

	/**
	 * Initializes properties with values from the persistence unit.
	 */
	protected abstract void initializeProperties();

	// ********** behavior **********
	protected PersistenceUnit persistenceUnit() {
		PersistenceUnit pu = this.persistenceUnit;
		return pu;
	}

	public PersistenceUnitPropertyListListener propertyListListener() {
		return this.propertyListListener;
	}

	private Map<String, String> propertyNames() {
		return this.propertyNames;
	}

	/**
	 * Adds property names key/value pairs, used by the methods: itemIsProperty
	 * and propertyIdFor.
	 * 
	 * key = EclipseLink property key; value = property id
	 */
	protected abstract void addPropertyNames(Map<String, String> propertyNames);

	/**
	 * Method used for identifying the given property.
	 */
	public boolean itemIsProperty(Property item) {
		if (item == null) {
			throw new IllegalArgumentException("Property is null");
		}
		return this.propertyNames().keySet().contains(item.getName());
	}

	/**
	 * Returns the property name used for change notification of the given
	 * property.
	 */
	public String propertyIdFor(Property property) {
		String propertyId = this.propertyNames().get(property.getName());
		if (propertyId == null) {
			throw new IllegalArgumentException("Illegal property: " + property.toString());
		}
		return propertyId;
	}

	protected String eclipseLinkKeyFor(String propertyId) {
		for (String eclipseLinkKey : this.propertyNames().keySet()) {
			if (this.propertyNames().get(eclipseLinkKey).equals(propertyId)) {
				return eclipseLinkKey;
			}
		}
		throw new IllegalArgumentException("Illegal property: " + propertyId);
	}

	// ****** get/set String convenience methods *******
	/**
	 * Returns the String value of the given Property from the PersistenceXml.
	 */
	protected String getStringValue(String elKey) {
		return this.getStringValue(elKey, null);
	}

	protected String getStringValue(String key, String keySuffix) {
		String elKey = (keySuffix == null) ? key : key + keySuffix;
		if (this.persistenceUnit().containsProperty(elKey)) {
			// TOREVIEW - handle incorrect String in persistence.xml
			return this.persistenceUnit().getProperty(elKey).getValue();
		}
		return null;
	}

	/**
	 * Put the given String value into the PersistenceXml.
	 * @param key -
	 *            EclipseLink Key
	 * @param newValue
	 *            value to be associated with the key
	 */
	protected void putStringValue(String key, String newValue) {
		this.putStringValue(key, null, newValue, false);
	}

	/**
	 * Put the given String value into the PersistenceXml.
	 * @param key
	 *            EclipseLink Key
	 * @param newValue
	 *            value to be associated with the key
	 * @param allowDuplicate
	 */
	protected void putStringValue(String key, String newValue, boolean allowDuplicate) {
		this.putStringValue(key, null, newValue, allowDuplicate);
	}

	/**
	 * Put the given String value into the PersistenceXml.
	 * @param key
	 *            EclipseLink Key
	 * @param keySuffix
	 *            e.g. entity name
	 * @param newValue
	 *            value to be associated with the key
	 * @param allowDuplicate
	 */
	protected void putStringValue(String key, String keySuffix, String newValue, boolean allowDuplicate) {
		String elKey = (keySuffix == null) ? key : key + keySuffix;
		if (newValue == null) {
			this.persistenceUnit().removeProperty(elKey);
		}
		else {
			this.persistenceUnit().putProperty(elKey, newValue, allowDuplicate);
		}
	}

	// ****** Integer convenience methods *******
	/**
	 * Returns the Integer value of the given Property from the PersistenceXml.
	 */
	protected Integer getIntegerValue(String elKey) {
		return this.getIntegerValue(elKey, null);
	}

	protected Integer getIntegerValue(String key, String keySuffix) {
		String elKey = (keySuffix == null) ? key : key + keySuffix;
		if (this.persistenceUnit().containsProperty(elKey)) {
			String eclipseLinkValue = this.persistenceUnit().getProperty(elKey).getValue();
			// TOREVIEW - handle incorrect eclipseLinkValue String in
			// persistence.xml
			return getIntegerValueOf(eclipseLinkValue);
		}
		return null;
	}

	/**
	 * Put the given Integer value into the PersistenceXml.
	 * @param key -
	 *            EclipseLink Key
	 * @param newValue
	 *            value to be associated with the key
	 */
	protected void putIntegerValue(String key, Integer newValue) {
		this.putIntegerValue(key, null, newValue, false);
	}

	/**
	 * Put the given Integer value into the PersistenceXml.
	 * @param key
	 *            EclipseLink Key
	 * @param newValue
	 *            value to be associated with the key
	 * @param allowDuplicate
	 */
	protected void putIntegerValue(String key, Integer newValue, boolean allowDuplicate) {
		this.putIntegerValue(key, null, newValue, allowDuplicate);
	}

	/**
	 * Put the given Integer value into the PersistenceXml.
	 * @param key
	 *            EclipseLink Key
	 * @param keySuffix
	 *            e.g. entity name
	 * @param newValue
	 *            value to be associated with the key
	 * @param allowDuplicate
	 */
	protected void putIntegerValue(String key, String keySuffix, Integer newValue, boolean allowDuplicate) {
		String elKey = (keySuffix == null) ? key : key + keySuffix;
		if (newValue == null) {
			this.persistenceUnit().removeProperty(elKey);
		}
		else {
			this.persistenceUnit().putProperty(elKey, newValue.toString(), allowDuplicate);
		}
	}

	// ****** Boolean convenience methods ******* 
	/**
	 * Returns the Boolean value of the given Property from the PersistenceXml.
	 */
	protected Boolean getBooleanValue(String elKey) {
		return this.getBooleanValue(elKey, null);
	}

	/**
	 * Returns the Boolean value of the given Property from the PersistenceXml.
	 */
	protected Boolean getBooleanValue(String key, String keySuffix) {
		String elKey = (keySuffix == null) ? key : key + keySuffix;
		if (this.persistenceUnit().containsProperty(elKey)) {
			String eclipseLinkValue = this.persistenceUnit().getProperty(elKey).getValue();
			// TOREVIEW - handle incorrect eclipseLinkValue String in
			// persistence.xml
			return getBooleanValueOf(eclipseLinkValue);
		}
		return null;
	}

	/**
	 * Put the given Boolean value into the PersistenceXml.
	 * @param key
	 *            EclipseLink Key
	 * @param newValue
	 *            value to be associated with the key
	 */
	protected void putBooleanValue(String key, Boolean newValue) {
		this.putBooleanValue(key, null, newValue, false);
	}

	/**
	 * Put the given Boolean value into the PersistenceXml.
	 * @param key
	 *            EclipseLink Key
	 * @param newValue
	 *            value to be associated with the key
	 * @param allowDuplicate
	 */
	protected void putBooleanValue(String key, Boolean newValue, boolean allowDuplicate) {
		this.putBooleanValue(key, null, newValue, allowDuplicate);
	}

	/**
	 * Put the given Boolean value into the PersistenceXml.
	 * @param key
	 *            EclipseLink Key
	 * @param keySuffix
	 *            e.g. entity name
	 * @param newValue
	 *            value to be associated with the key
	 * @param allowDuplicate
	 */
	protected void putBooleanValue(String key, String keySuffix, Boolean newValue, boolean allowDuplicate) {
		String elKey = (keySuffix == null) ? key : key + keySuffix;
		if (newValue == null) {
			this.persistenceUnit().removeProperty(elKey);
		}
		else {
			this.persistenceUnit().putProperty(elKey, newValue.toString(), allowDuplicate);
		}
	}

	// ****** Enum convenience methods ******* 
	/**
	 * Returns the Enum value of the given Property from the PersistenceXml.
	 */
	protected <T extends Enum<T>> T getEnumValue(String elKey, T[] enumValues) {
		return getEnumValue(elKey, null, enumValues);
	}

	protected <T extends Enum<T>> T getEnumValue(String key, String keySuffix, T[] enumValues) {
		String elKey = (keySuffix == null) ? key : key + keySuffix;
		if (this.persistenceUnit().containsProperty(elKey)) {
			String eclipseLinkValue = this.persistenceUnit().getProperty(elKey).getValue();
			// TOREVIEW - handle incorrect eclipseLinkValue String in
			// persistence.xml
			return getEnumValueOf(eclipseLinkValue, enumValues);
		}
		return null;
	}

	/**
	 * Put the given Enum value into the PersistenceXml.
	 * 
	 * @param key -
	 *            EclipseLink Key
	 */
	protected <T extends Enum<T>> void putEnumValue(String key, T newValue) {
		this.putEnumValue(key, null, newValue, false);
	}

	protected <T extends Enum<T>> void putEnumValue(String key, T newValue, boolean allowDuplicate) {
		this.putEnumValue(key, null, newValue, allowDuplicate);
	}

	protected <T extends Enum<T>> void putEnumValue(String key, String keySuffix, T newValue, boolean allowDuplicate) {
		String elKey = (keySuffix == null) ? key : key + keySuffix;
		if (newValue == null) {
			this.persistenceUnit().removeProperty(elKey);
		}
		else {
			this.persistenceUnit().putProperty(elKey, getEclipseLinkStringValueOf(newValue), allowDuplicate);
		}
	}

	// ******** Convenience methods ********
	/**
	 * Put into persistenceUnit properties.
	 * 
	 * @param key -
	 *            property name
	 * @param value -
	 *            property value
	 */
	@SuppressWarnings("unchecked")
	protected void putProperty(String key, Object value) {
		String elKey = this.eclipseLinkKeyFor(key);
		if (value == null)
			this.removeProperty(elKey);
		else if (value.getClass().isEnum())
			this.putEnumValue(elKey, (Enum) value);
		else
			 this.putProperty_(elKey, value);
	}

	private void putProperty_(String key, Object value) {
		this.persistenceUnit().putProperty(key, value.toString(), false);
	}

	/**
	 * Removes a property with the given key.
	 */
	protected void removeProperty(String elKey) {
		this.persistenceUnit().removeProperty(elKey);
	}

	protected Set<Property> getPropertiesSetWithPrefix(String keyPrefix) {
		return CollectionTools.set(this.persistenceUnit().propertiesWithPrefix(keyPrefix));
	}

	/**
	 * Extracts the entityName of the specified property name. If the property name
	 * has no suffix, return an empty string.
	 */
	protected String getEntityName(Property property) {
		return getEntityName(property.getName());
	}

	/**
	 * Extracts the entityName of the specified string. If the string
	 * has no suffix, return an empty string.
	 */
	protected String getEntityName(String propertyName) {
		int index = propertyName.lastIndexOf('.');
		if (index == -1) {
			return "";
		}
		return propertyName.substring(index + 1);
	}

	// ****** Static methods ******* 
	/**
	 * Returns the EclipseLink string value for the given property value.
	 */
	public static String getEclipseLinkStringValueOf(Object value) {
		
		if (value.getClass().isEnum()) {
			return (String) ClassTools.staticFieldValue(value.getClass(), value.toString().toUpperCase());
		}
		return value.toString();
	}

	/**
	 * Returns the enum constant of the specified enum type with the specified
	 * EclipseLink value string.
	 */
	public static <T extends Enum<T>> T getEnumValueOf(String eclipseLinkValueString, T[] enumValues) {
		for (T mode : enumValues) {
			if (getEclipseLinkStringValueOf(mode).equals(eclipseLinkValueString)) {
				return mode;
			}
		}
		return null;
	}

	public static Boolean getBooleanValueOf(String eclipseLinkValueString) {
		if (eclipseLinkValueString == null) {
			return null;
		}
		return Boolean.valueOf(eclipseLinkValueString);
	}

	public static Integer getIntegerValueOf(String eclipseLinkValueString) {
		if (eclipseLinkValueString == null) {
			return null;
		}
		return Integer.valueOf(eclipseLinkValueString);
	}
}
