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

package org.eclipse.jpt.jpa.core.resource.xml;

import java.util.Map;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.internal.utility.translators.EnumeratedValueTranslator;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
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
 * @see org.eclipse.jpt.jpa.core.resource.xml.CommonPackage#getAbstractJpaRootEObject()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class AbstractJpaRootEObject extends AbstractJpaEObject implements JpaRootEObject
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractJpaRootEObject()
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
		return CommonPackage.Literals.ABSTRACT_JPA_ROOT_EOBJECT;
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
	 * @see org.eclipse.jpt.jpa.core.resource.xml.CommonPackage#getJpaRootEObject_Version()
	 * @model required="true"
	 * @generated
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.xml.AbstractJpaRootEObject#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	protected void setVersionGen(String newVersion)
	{
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT__VERSION, oldVersion, version));
	}
	
	public void setVersion(String newVersion) {
		setVersionGen(newVersion);
		setSchemaLocation(buildSchemaLocationString(getNamespace(), getSchemaLocationForVersion(newVersion)));
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
	 * @see org.eclipse.jpt.jpa.core.resource.xml.CommonPackage#getJpaRootEObject_SchemaLocation()
	 * @model required="true"
	 * @generated
	 */
	public String getSchemaLocation()
	{
		return schemaLocation;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.xml.AbstractJpaRootEObject#getSchemaLocation <em>Schema Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schema Location</em>' attribute.
	 * @see #getSchemaLocation()
	 * @generated
	 */
	public void setSchemaLocation(String newSchemaLocation)
	{
		String oldSchemaLocation = schemaLocation;
		schemaLocation = newSchemaLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT__SCHEMA_LOCATION, oldSchemaLocation, schemaLocation));
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
			case CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT__VERSION:
				return getVersion();
			case CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT__SCHEMA_LOCATION:
				return getSchemaLocation();
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
			case CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT__VERSION:
				setVersion((String)newValue);
				return;
			case CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT__SCHEMA_LOCATION:
				setSchemaLocation((String)newValue);
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
			case CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT__SCHEMA_LOCATION:
				setSchemaLocation(SCHEMA_LOCATION_EDEFAULT);
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
			case CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case CommonPackage.ABSTRACT_JPA_ROOT_EOBJECT__SCHEMA_LOCATION:
				return SCHEMA_LOCATION_EDEFAULT == null ? schemaLocation != null : !SCHEMA_LOCATION_EDEFAULT.equals(schemaLocation);
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
		result.append(')');
		return result.toString();
	}
	
	
	// **************** validation ********************************************
	
	public TextRange getVersionTextRange() {
		return getAttributeTextRange(XML.VERSION);
	}
	
	
	// **************** version -> schema location mapping ********************
	
	protected abstract String getNamespace();
	
	protected abstract String getSchemaLocationForVersion(String version);
	
	private static String buildSchemaLocationString(String namespace, String schemaLocation) {
		return namespace + ' ' + schemaLocation;
	}
	
	
	// **************** translators *******************************************
	
	protected static Translator buildVersionTranslator(final Map<String, String> versionsToSchemaLocations) {
		return new EnumeratedValueTranslator(
				XML.VERSION, 
				CommonPackage.eINSTANCE.getJpaRootEObject_Version(),
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
				CommonPackage.eINSTANCE.getJpaRootEObject_SchemaLocation(),
				Translator.DOM_ATTRIBUTE) {
			
			@Override
			protected Iterable<String> getEnumeratedObjectValues() {
				return new TransformationIterable<String, String>(versionsToSchemaLocations.values()) {
					@Override
					protected String transform(String next) {
						return buildSchemaLocationString(namespace, next);
					}
				};
			}
		};	
	}
}
