/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.context.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.utility.internal.ReflectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.text.edits.ReplaceEdit;

/**
 *  AbstractPersistenceUnitProperties
 */
public abstract class AbstractPersistenceUnitProperties extends AbstractModel
				implements PersistenceUnitProperties
{
	private PersistenceUnit persistenceUnit;

	// key = PersistenceUnit property key; value = property id
	private Map<String, String> propertyNames;

	public static final String PROPERTY_VALUE_DELIMITER = ",";	//$NON-NLS-1$
	private static final long serialVersionUID = 1L;
	
	// ********** constructors / initialization **********
	protected AbstractPersistenceUnitProperties(PersistenceUnit parent) {
		super();
		this.initialize();
		this.initialize(parent);
		this.postInitialize();
	}

	/**
	 * Base initialization.
	 */
	protected void initialize() {
		this.propertyNames = new HashMap<String, String>();
	}

	/**
	 * Initialization based on the persistence unit.
	 */
	protected void initialize(PersistenceUnit parent) {
		this.persistenceUnit = parent;
		
		this.initializePropertyNames();
		this.initializeProperties();
	}

	protected void initializePropertyNames() {
		this.addPropertyNames(this.propertyNames);
	}

	/**
	 * Initializes properties with values from the persistence unit.
	 */
	protected abstract void initializeProperties();

	/**
     * Does all post treatment in this method after the properties are initialized
	 */
	protected void postInitialize() {
		// do nothing by default
	}

	// ********** behavior **********
	public PersistenceUnit getPersistenceUnit() {
		return this.persistenceUnit;
	}
	
	public JpaProject getJpaProject() {
		return this.persistenceUnit.getJpaProject();
	}
	
	/**
	 * Adds property names key/value pairs, used by the methods: itemIsProperty
	 * and propertyIdFor.
	 * 
	 * key = property key; value = property id
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
	public String propertyIdOf(PersistenceUnit.Property property) {
		String propertyId = this.propertyNames().get(property.getName());
		if (propertyId == null) {
			throw new IllegalArgumentException("Illegal property: " + property); //$NON-NLS-1$
		}
		return propertyId;
	}
	
	public String propertyIdOf(String eclipseLinkkey) {
		String propertyId = this.propertyNames().get(eclipseLinkkey);
		if (propertyId == null) {
			throw new IllegalArgumentException("Illegal property: " + eclipseLinkkey); //$NON-NLS-1$
		}
		return propertyId;
	}

	/*
	 * Get the PersistenceUnit property key of the given property 
	 */
	protected String persistenceUnitKeyOf(String propertyId) {
		for (String persistenceUnitKey : this.propertyNames().keySet()) {
			if (this.propertyNames().get(persistenceUnitKey).equals(propertyId)) {
				return persistenceUnitKey;
			}
		}
		throw new IllegalArgumentException("Illegal property ID: " + propertyId); //$NON-NLS-1$
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
		this.putProperty(key, value, false);
	}

	@SuppressWarnings("unchecked")
	protected void putProperty(String key, Object value, boolean allowDuplicates) {
		String persistenceUnitKey = this.persistenceUnitKeyOf(key);
		if ((value != null) && value.getClass().isEnum()) {
			this.putEnumValue(persistenceUnitKey, (Enum) value, allowDuplicates);
		} 
		else {
			this.putPersistenceUnitProperty(persistenceUnitKey, null, value, allowDuplicates);
		}
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
		String persistenceUnitKey = this.persistenceUnitKeyOf(key);
		
		this.getPersistenceUnit().removeProperty(persistenceUnitKey, value);
	}

	/**
	 * Returns true when the given key exits in the PersistenceXml.
	 */
	protected boolean persistenceUnitKeyExists(String persistenceUnitKey) {
		return (this.getStringValue(persistenceUnitKey) != null);
	}
	
	// ****** get/set String convenience methods *******
	/**
	 * Returns the String value of the given Property from the PersistenceXml.
	 */
	protected String getStringValue(String persistenceUnitKey) {
		PersistenceUnit.Property p = this.getPersistenceUnit().getProperty(persistenceUnitKey);
		return (p == null) ? null : p.getValue();
	}

	protected String getStringValue(String key, String keySuffix) {
		return this.getStringValue((keySuffix == null) ? key : key + keySuffix);
	}

	/**
	 * Put the given String value into the PersistenceXml.
	 * @param key -
	 *            property key
	 * @param newValue
	 *            value to be associated with the key
	 */
	protected void putStringValue(String key, String newValue) {
		this.putStringValue(key, null, newValue, false);
	}

	/**
	 * Put the given String value into the PersistenceXml.
	 * @param key
	 *            property key
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
	 *            property key
	 * @param keySuffix
	 *            e.g. entity name
	 * @param newValue
	 *            value to be associated with the key
	 * @param allowDuplicate
	 */
	protected void putStringValue(String key, String keySuffix, String newValue, boolean allowDuplicate) {
		this.putPersistenceUnitProperty(key, keySuffix, newValue, allowDuplicate);
	}

	// ****** Integer convenience methods *******
	/**
	 * Returns the Integer value of the given Property from the PersistenceXml.
	 */
	protected Integer getIntegerValue(String persistenceUnitKey) {
		PersistenceUnit.Property p = this.getPersistenceUnit().getProperty(persistenceUnitKey);
		return (p == null) ? null : getIntegerValueOf(p.getValue());
	}

	protected Integer getIntegerValue(String key, String keySuffix) {
		return this.getIntegerValue((keySuffix == null) ? key : key + keySuffix);
	}

	/**
	 * Put the given Integer value into the PersistenceXml.
	 * @param key -
	 *            property key
	 * @param newValue
	 *            value to be associated with the key
	 */
	protected void putIntegerValue(String key, Integer newValue) {
		this.putIntegerValue(key, null, newValue, false);
	}

	/**
	 * Put the given Integer value into the PersistenceXml.
	 * @param key
	 *            property key
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
	 *            property key
	 * @param keySuffix
	 *            e.g. entity name
	 * @param newValue
	 *            value to be associated with the key
	 * @param allowDuplicate
	 */
	protected void putIntegerValue(String key, String keySuffix, Integer newValue, boolean allowDuplicate) {
		this.putPersistenceUnitProperty(key, keySuffix, newValue, allowDuplicate);
	}

	// ****** Boolean convenience methods ******* 
	/**
	 * Returns the Boolean value of the given Property from the PersistenceXml.
	 */
	protected Boolean getBooleanValue(String persistenceUnitKey) {
		PersistenceUnit.Property p = this.getPersistenceUnit().getProperty(persistenceUnitKey);
		return (p == null) ? null : getBooleanValueOf(p.getValue());
	}

	protected Boolean getBooleanValue(String key, String keySuffix) {
		return this.getBooleanValue((keySuffix == null) ? key : key + keySuffix);
	}

	/**
	 * Put the given Boolean value into the PersistenceXml.
	 * @param key
	 *            property key
	 * @param newValue
	 *            value to be associated with the key
	 */
	protected void putBooleanValue(String key, Boolean newValue) {
		this.putBooleanValue(key, null, newValue, false);
	}

	/**
	 * Put the given Boolean value into the PersistenceXml.
	 * @param key
	 *            property key
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
	 *            property key
	 * @param keySuffix
	 *            e.g. entity name
	 * @param newValue
	 *            value to be associated with the key
	 * @param allowDuplicate
	 */
	protected void putBooleanValue(String key, String keySuffix, Boolean newValue, boolean allowDuplicate) {
		this.putPersistenceUnitProperty(key, keySuffix, newValue, allowDuplicate);
	}

	// ****** Enum convenience methods ******* 
	/**
	 * Returns the Enum value of the given Property from the PersistenceXml.
	 */
	protected <T extends Enum<T>> T getEnumValue(String persistenceUnitKey, T[] enumValues) {
		PersistenceUnit.Property p = this.getPersistenceUnit().getProperty(persistenceUnitKey);
		return (p == null) ? null : getEnumValueOf(p.getValue(), enumValues);
	}

	protected <T extends Enum<T>> T getEnumValue(String key, String keySuffix, T[] enumValues) {
		return this.getEnumValue((keySuffix == null) ? key : key + keySuffix, enumValues);
	}

	/**
	 * Put the given Enum value into the PersistenceXml.
	 * 
	 * @param key -
	 *            property key
	 */
	protected <T extends Enum<T>> void putEnumValue(String key, T newValue) {
		this.putEnumValue(key, null, newValue, false);
	}

	protected <T extends Enum<T>> void putEnumValue(String key, T newValue, boolean allowDuplicate) {
		this.putEnumValue(key, null, newValue, allowDuplicate);
	}

	protected <T extends Enum<T>> void putEnumValue(String key, String keySuffix, T newValue, boolean allowDuplicate) {
		this.putPersistenceUnitProperty(key, keySuffix, getPropertyStringValueOf(newValue), allowDuplicate);
	}

	// ****** get/set CompositeValue convenience methods *******
	/**
	 * Returns the String values of the given Property from the PersistenceXml.
	 */
	protected List<String> getCompositeValue(String persistenceUnitKey) {
		String values = this.getStringValue(persistenceUnitKey);
		return this.extractCompositeValue(values);
	}
	
	/**
	 * Put into persistenceUnit properties. If the property already exists, 
	 * it appends the given value at the end of the property value.
	 * 
	 * @param key
	 * @param valueToAppend
	 */
	protected void putPropertyCompositeValue(String key, String valueToAppend) {
		String persistenceUnitKey = this.persistenceUnitKeyOf(key);

		 String persistenceUnitValue = this.buildCompositeValue(
			 											this.getStringValue(persistenceUnitKey), valueToAppend);

		this.putPersistenceUnitProperty(persistenceUnitKey, null, persistenceUnitValue, false);
	}

	/**
	 * Removes a value from a property with composite values.
	 * If the resulting value is empty, the property is removed from the persistenceUnit.
	 * 
	 * @param key -
	 *            property name
	 * @param valueToRemove -
	 *            value to remove from the property
	 */
	protected void removePropertyCompositeValue(String key, String valueToRemove) {
		String persistenceUnitKey = this.persistenceUnitKeyOf(key);
		
		String persistenceUnitValue = this.removeValueFrom(
			 											this.getStringValue(persistenceUnitKey), valueToRemove.trim());

		this.putPersistenceUnitProperty(persistenceUnitKey, null, persistenceUnitValue, false);
	}

	// ****** Static methods ******* 
	
	public static Boolean getBooleanValueOf(String puStringValue) {
		if (StringTools.stringIsEmpty(puStringValue)) {
			return null;
		}
		return Boolean.valueOf(puStringValue);
	}

	public static Integer getIntegerValueOf(String puStringValue) {
		if  (StringTools.stringIsEmpty(puStringValue)) {
			return null;
		}
		try {
			return Integer.valueOf(puStringValue);
		}
		catch (NumberFormatException nfe) {
			return null;
		}
	}

	/**
	 * Returns the enum constant of the specified enum type with the specified
	 * Property string value.
	 */
	public static <T extends Enum<T>> T getEnumValueOf(String puStringValue, T[] enumValues) {
		for (T enumValue : enumValues) {
			if (getPropertyStringValueOf(enumValue).equals(puStringValue)) {
				return enumValue;
			}
		}
		return null;
	}

	/**
	 * Returns the Property string value of the given property value.
	 */
	public static String getPropertyStringValueOf(Object value) {
		if (value == null) {
			return null;
		}
		if (value.getClass().isEnum()) {
			return (String) ReflectionTools.getStaticFieldValue(value.getClass(), value.toString().toUpperCase(Locale.ENGLISH));
		}
		return value.toString();
	}

	// ********** internal methods **********

	private Map<String, String> propertyNames() {
		return this.propertyNames;
	}

	private void putPersistenceUnitProperty(String key, String keySuffix, Object value, boolean allowDuplicates) {
		String persistenceUnitKey = (keySuffix == null) ? key : key + keySuffix;
		String stringValue = (value == null) ? null : value.toString();
		this.getPersistenceUnit().setProperty(persistenceUnitKey, stringValue, allowDuplicates);
	}

	private String buildCompositeValue(String value, String valueToAppend) {
		if((StringTools.stringIsEmpty(valueToAppend)) ) {
			return value;
		}
		return (StringTools.stringIsEmpty(value)) ? 
						valueToAppend : 
						(value + PROPERTY_VALUE_DELIMITER + valueToAppend);
	}

	protected List<String> extractCompositeValue(String compositeValue) {
		if(StringTools.stringIsEmpty(compositeValue)) {
			return new ArrayList<String>(0);
		}
		String[] values = compositeValue.split(PROPERTY_VALUE_DELIMITER);
		List<String> results = new ArrayList<String>(values.length);
		for(String value : values) {
			results.add(value.trim());
		}
		return results;
	}

	private String removeValueFrom(String compositeValue, String valueToRemove) {
		if((StringTools.stringIsEmpty(valueToRemove))) {
			return compositeValue;
		}
		String[] values = compositeValue.split(PROPERTY_VALUE_DELIMITER);
		ArrayList<String> results = new ArrayList<String>(values.length);
		
		for(String value : values) {
			if(value.trim().equals(valueToRemove)) {
				continue;
			}
			results.add(value);
		}
		if(results.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder(values.length);
		for(String value : results) {
			sb.append(value).append(PROPERTY_VALUE_DELIMITER);
		}
		sb.deleteCharAt(sb.length() - 1); 	// remove the last delimiter
		return sb.toString();
	}


	// ********** refactoring ************

	public Iterable<ReplaceEdit> createReplaceTypeEdits(IType originalType, String newName) {
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createMoveTypeReplaceEdits(IType originalType, IPackageFragment newPackage) {
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createReplacePackageEdits(IPackageFragment originalPackage, String newName) {
		return EmptyIterable.instance();
	}
}
