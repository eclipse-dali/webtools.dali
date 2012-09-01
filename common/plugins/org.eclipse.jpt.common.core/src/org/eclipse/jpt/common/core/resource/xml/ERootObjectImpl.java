/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.common.core.resource.xml;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.internal.utility.translators.EnumeratedValueTranslator;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.wst.common.internal.emf.resource.ConstantAttributeTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Jpa Root EObject</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.common.core.resource.xml.CommonPackage#getERootObjectImpl()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class ERootObjectImpl extends EBaseObjectImpl implements ERootObject
{
	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getSchemaLocation() <em>Schema Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected static final String SCHEMA_LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSchemaLocation() <em>Schema Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected String schemaLocation = SCHEMA_LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getImpliedVersion() <em>Implied Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImpliedVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLIED_VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImpliedVersion() <em>Implied Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImpliedVersion()
	 * @generated
	 * @ordered
	 */
	protected String impliedVersion = IMPLIED_VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ERootObjectImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return CommonPackage.Literals.EROOT_OBJECT_IMPL;
	}

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.eclipse.jpt.common.core.resource.xml.CommonPackage#getERootObject_Version()
	 * @model
	 * @generated
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.common.core.resource.xml.ERootObjectImpl#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	public void setVersion(String newVersion)
	{
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CommonPackage.EROOT_OBJECT_IMPL__VERSION, oldVersion, version));
	}
	
	public String getDocumentVersion() {
		String version = getVersion();
		return (version == null) ? getImpliedVersion() : version;
	}
	
	public void setDocumentVersion(String newVersion) {
		setVersion(newVersion);
		setSchemaLocation(getSchemaLocationForVersion(newVersion));
	}
	
	/**
	 * Returns the value of the '<em><b>Schema Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schema Location</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schema Location</em>' attribute.
	 * @see #setSchemaLocation(String)
	 * @see org.eclipse.jpt.common.core.resource.xml.CommonPackage#getERootObject_SchemaLocation()
	 * @model required="true"
	 * @generated
	 */
	public String getSchemaLocation()
	{
		return schemaLocation;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.common.core.resource.xml.ERootObjectImpl#getSchemaLocation <em>Schema Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schema Location</em>' attribute.
	 * @see #getSchemaLocation()
	 * @generated
	 */
	public void setSchemaLocationGen(String newSchemaLocation)
	{
		String oldSchemaLocation = schemaLocation;
		schemaLocation = newSchemaLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CommonPackage.EROOT_OBJECT_IMPL__SCHEMA_LOCATION, oldSchemaLocation, schemaLocation));
	}
	
	public void setSchemaLocation(String newSchemaLocation) {
		setSchemaLocationGen(newSchemaLocation);
		for (Entry<String, String> entry : schemaLocations().entrySet()) {
			if (entry.getValue().equals(newSchemaLocation)) {
				setImpliedVersion(entry.getKey());
				return;
			}
		}
		setImpliedVersion(null);
	}
	
	/**
	 * Returns the value of the '<em><b>Implied Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implied Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implied Version</em>' attribute.
	 * @see #setImpliedVersion(String)
	 * @see org.eclipse.jpt.common.core.resource.xml.CommonPackage#getERootObject_ImpliedVersion()
	 * @model
	 * @generated
	 */
	public String getImpliedVersion()
	{
		return impliedVersion;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.common.core.resource.xml.ERootObjectImpl#getImpliedVersion <em>Implied Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implied Version</em>' attribute.
	 * @see #getImpliedVersion()
	 * @generated
	 */
	public void setImpliedVersion(String newImpliedVersion)
	{
		String oldImpliedVersion = impliedVersion;
		impliedVersion = newImpliedVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CommonPackage.EROOT_OBJECT_IMPL__IMPLIED_VERSION, oldImpliedVersion, impliedVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case CommonPackage.EROOT_OBJECT_IMPL__VERSION:
				return getVersion();
			case CommonPackage.EROOT_OBJECT_IMPL__SCHEMA_LOCATION:
				return getSchemaLocation();
			case CommonPackage.EROOT_OBJECT_IMPL__IMPLIED_VERSION:
				return getImpliedVersion();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case CommonPackage.EROOT_OBJECT_IMPL__VERSION:
				setVersion((String)newValue);
				return;
			case CommonPackage.EROOT_OBJECT_IMPL__SCHEMA_LOCATION:
				setSchemaLocation((String)newValue);
				return;
			case CommonPackage.EROOT_OBJECT_IMPL__IMPLIED_VERSION:
				setImpliedVersion((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case CommonPackage.EROOT_OBJECT_IMPL__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case CommonPackage.EROOT_OBJECT_IMPL__SCHEMA_LOCATION:
				setSchemaLocation(SCHEMA_LOCATION_EDEFAULT);
				return;
			case CommonPackage.EROOT_OBJECT_IMPL__IMPLIED_VERSION:
				setImpliedVersion(IMPLIED_VERSION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case CommonPackage.EROOT_OBJECT_IMPL__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case CommonPackage.EROOT_OBJECT_IMPL__SCHEMA_LOCATION:
				return SCHEMA_LOCATION_EDEFAULT == null ? schemaLocation != null : !SCHEMA_LOCATION_EDEFAULT.equals(schemaLocation);
			case CommonPackage.EROOT_OBJECT_IMPL__IMPLIED_VERSION:
				return IMPLIED_VERSION_EDEFAULT == null ? impliedVersion != null : !IMPLIED_VERSION_EDEFAULT.equals(impliedVersion);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (version: ");
		result.append(version);
		result.append(", schemaLocation: ");
		result.append(schemaLocation);
		result.append(", impliedVersion: ");
		result.append(impliedVersion);
		result.append(')');
		return result.toString();
	}
	
	
	// **************** validation ********************************************
	
	public TextRange getVersionTextRange() {
		return getAttributeTextRange(XML.VERSION);
	}
	
	
	// **************** version -> schema location mapping ********************
	
	protected abstract String getNamespace();
	
	protected abstract HashMap<String, String> schemaLocations();
	
	protected String getSchemaLocationForVersion(String version) {
		return schemaLocations().get(version);
	}
	
	private static String buildSchemaLocationString(String namespace, String schemaLocation) {
		return namespace + ' ' + schemaLocation;
	}
	
	
	// **************** translators *******************************************
	
	protected static Translator buildVersionTranslator(final Map<String, String> versionsToSchemaLocations) {
		return new EnumeratedValueTranslator(
				XML.VERSION, 
				CommonPackage.eINSTANCE.getERootObject_Version(),
				Translator.DOM_ATTRIBUTE) {
			
			@Override
			protected Iterable<String> getEnumeratedObjectValues() {
				return versionsToSchemaLocations.keySet();
			}
		};
	}
	
	protected static Translator buildNamespaceTranslator(String namespace) {
		return new ConstantAttributeTranslator(XML.NAMESPACE, namespace);
	}
	
	protected static Translator buildSchemaNamespaceTranslator() {
		return new ConstantAttributeTranslator(XML.NAMESPACE_XSI, XML.XSI_NAMESPACE_URL);
	}
	
	protected static Translator buildSchemaLocationTranslator(
			final String namespace,
			final Map<String, String> versionsToSchemaLocations) {
		
		return new EnumeratedValueTranslator(
				XML.XSI_SCHEMA_LOCATION, 
				CommonPackage.eINSTANCE.getERootObject_SchemaLocation(),
				Translator.DOM_ATTRIBUTE) {
			
			@Override
			protected Iterable<String> getEnumeratedObjectValues() {
				return versionsToSchemaLocations.values();
			}
			
			@Override
			public Object convertStringToValue(String string, EObject owner) {
				String[] split = string.split("\\s+");  // splits on any whitespace
				int length = split.length;
				return (length == 0) ? null : split[length - 1];
			}
			
			@Override
			public String convertValueToString(Object value, EObject owner) {
				return buildSchemaLocationString(namespace, (String) value);
			}
		};	
	}
}
