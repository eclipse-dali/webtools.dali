/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
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

	private static final long serialVersionUID = 1L;
	
	// ********** constructors / initialization **********
	protected EclipseLinkPersistenceUnitProperties(
			PersistenceUnit parent, 
			ListValueModel<PersistenceUnit.Property> propertyListAdapter) {
		super();
		this.initialize(parent, propertyListAdapter);
	}

	protected void initialize(
			PersistenceUnit parent, 
			ListValueModel<PersistenceUnit.Property> propertyListAdapter) {
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
	public PersistenceUnit getPersistenceUnit() {
		return this.persistenceUnit;
	}
	
	public JpaProject getJpaProject() {
		return this.persistenceUnit.getJpaProject();
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
	protected abstract void addPropertyNames(Map<String, String> pNames);

	/**
	 * Method used for identifying the given property.
	 */
	public boolean itemIsProperty(PersistenceUnit.Property item) {
		if (item == null) {
			throw new NullPointerException();
		}
		return this.propertyNames().keySet().contains(item.getName());
	}

	/**
	 * Returns the property name used for change notification of the given
	 * property.
	 */
	public String propertyIdFor(PersistenceUnit.Property property) {
		String propertyId = this.propertyNames().get(property.getName());
		if (propertyId == null) {
			throw new IllegalArgumentException("Illegal property: " + property); //$NON-NLS-1$
		}
		return propertyId;
	}

	protected String eclipseLinkKeyFor(String propertyId) {
		for (String eclipseLinkKey : this.propertyNames().keySet()) {
			if (this.propertyNames().get(eclipseLinkKey).equals(propertyId)) {
				return eclipseLinkKey;
			}
		}
		throw new IllegalArgumentException("Illegal property ID: " + propertyId); //$NON-NLS-1$
	}

	// ****** get/set String convenience methods *******
	/**
	 * Returns the String value of the given Property from the PersistenceXml.
	 */
	// TOREVIEW - handle incorrect String in persistence.xml
	protected String getStringValue(String elKey) {
		PersistenceUnit.Property p = this.getPersistenceUnit().getProperty(elKey);
		return (p == null) ? null : p.getValue();
	}

	protected String getStringValue(String key, String keySuffix) {
		return this.getStringValue((keySuffix == null) ? key : key + keySuffix);
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
		this.getPersistenceUnit().setProperty(elKey, newValue, allowDuplicate);
	}

	// ****** Integer convenience methods *******
	/**
	 * Returns the Integer value of the given Property from the PersistenceXml.
	 */
	// TOREVIEW - handle incorrect eclipseLinkValue String in
	// persistence.xml
	protected Integer getIntegerValue(String elKey) {
		PersistenceUnit.Property p = this.getPersistenceUnit().getProperty(elKey);
		return (p == null) ? null : getIntegerValueOf(p.getValue());
	}

	protected Integer getIntegerValue(String key, String keySuffix) {
		return this.getIntegerValue((keySuffix == null) ? key : key + keySuffix);
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
		String elValue = (newValue == null) ? null : newValue.toString();
		this.getPersistenceUnit().setProperty(elKey, elValue, allowDuplicate);
	}

	// ****** Boolean convenience methods ******* 
	/**
	 * Returns the Boolean value of the given Property from the PersistenceXml.
	 */
	// TOREVIEW - handle incorrect eclipseLinkValue String in
	// persistence.xml
	protected Boolean getBooleanValue(String elKey) {
		PersistenceUnit.Property p = this.getPersistenceUnit().getProperty(elKey);
		return (p == null) ? null : getBooleanValueOf(p.getValue());
	}

	protected Boolean getBooleanValue(String key, String keySuffix) {
		return this.getBooleanValue((keySuffix == null) ? key : key + keySuffix);
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
		String elValue = (newValue == null) ? null : newValue.toString();
		this.getPersistenceUnit().setProperty(elKey, elValue, allowDuplicate);
	}

	// ****** Enum convenience methods ******* 
	/**
	 * Returns the Enum value of the given Property from the PersistenceXml.
	 */
	// TOREVIEW - handle incorrect eclipseLinkValue String in persistence.xml
	protected <T extends Enum<T>> T getEnumValue(String elKey, T[] enumValues) {
		PersistenceUnit.Property p = this.getPersistenceUnit().getProperty(elKey);
		return (p == null) ? null : getEnumValueOf(p.getValue(), enumValues);
	}

	protected <T extends Enum<T>> T getEnumValue(String key, String keySuffix, T[] enumValues) {
		return this.getEnumValue((keySuffix == null) ? key : key + keySuffix, enumValues);
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
		this.getPersistenceUnit().setProperty(elKey, getEclipseLinkStringValueOf(newValue), allowDuplicate);
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
	protected void putProperty(String key, Object value) {
		putProperty(key, value, false);
	}
	
	@SuppressWarnings("unchecked")
	protected void putProperty(String key, Object value, boolean allowDuplicates) {
		String elKey = this.eclipseLinkKeyFor(key);
		if ((value != null) && value.getClass().isEnum()) {
			this.putEnumValue(elKey, (Enum) value, allowDuplicates);
		} else {
			this.putEclipseLinkProperty(elKey, value, allowDuplicates);
		}
	}

	private void putEclipseLinkProperty(String key, Object value, boolean allowDuplicates) {
		String string = (value == null) ? null : value.toString();
		this.getPersistenceUnit().setProperty(key, string, allowDuplicates);
	}

	/**
	 * Removes a persistenceUnit property.
	 * 
	 * @param key -
	 *            property name
	 * @param value -
	 *            property value
	 */
	protected void removeProperty(String key, String value) {
		String elKey = this.eclipseLinkKeyFor(key);
		
		this.getPersistenceUnit().removeProperty(elKey, value);
	}

	protected Set<PersistenceUnit.Property> getPropertiesSetWithPrefix(String keyPrefix) {
		return CollectionTools.set(this.getPersistenceUnit().propertiesWithNamePrefix(keyPrefix));
	}

	/**
	 * Extracts the entityName of the specified property name. If the property name
	 * has no suffix, return an empty string.
	 */
	protected String getEntityName(PersistenceUnit.Property property) {
		return getEntityName(property.getName());
	}

	/**
	 * Extracts the entityName of the specified string. If the string
	 * has no suffix, return an empty string.
	 */
	protected String getEntityName(String propertyName) {
		int index = propertyName.lastIndexOf('.');
		if (index == -1) {
			return ""; //$NON-NLS-1$
		}
		return propertyName.substring(index + 1);
	}

	// ****** Static methods ******* 
	/**
	 * Returns the EclipseLink string value for the given property value.
	 */
	public static String getEclipseLinkStringValueOf(Object value) {
		if (value == null) {
			return null;
		}
		if (value.getClass().isEnum()) {
			return (String) ClassTools.staticFieldValue(value.getClass(), value.toString().toUpperCase());
		}
		return value.toString();
	}

	/**
	 * Returns the enum constant of the specified enum type with the specified
	 * EclipseLink value string.
	 */
	public static <T extends Enum<T>> T getEnumValueOf(String elStringValue, T[] enumValues) {
		for (T enumValue : enumValues) {
			if (getEclipseLinkStringValueOf(enumValue).equals(elStringValue)) {
				return enumValue;
			}
		}
		return null;
	}

	public static Boolean getBooleanValueOf(String elStringValue) {
		if (elStringValue == null) {
			return null;
		}
		return Boolean.valueOf(elStringValue);
	}

	public static Integer getIntegerValueOf(String elStringValue) {
		if (elStringValue == null) {
			return null;
		}
		return Integer.valueOf(elStringValue);
	}
}
