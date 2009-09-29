/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.persistence;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.internal.resource.xml.translators.EnumeratedValueTranslator;
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleRootTranslator;
import org.eclipse.jpt.core.resource.persistence.v2_0.JPA2_0;
import org.eclipse.jpt.core.resource.xml.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.xml.CommonPackage;
import org.eclipse.jpt.core.resource.xml.JpaRootEObject;
import org.eclipse.jpt.core.resource.xml.XML;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.wst.common.internal.emf.resource.ConstantAttributeTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>XmlPersistence</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.resource.persistence.XmlPersistence#getPersistenceUnits <em>Persistence Units</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlPersistence()
 * @model kind="class"
 * @generated
 */
public class XmlPersistence extends AbstractJpaEObject implements JpaRootEObject
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
	 * This is true if the Version attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean versionESet;

	/**
	 * The cached value of the '{@link #getPersistenceUnits() <em>Persistence Units</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPersistenceUnits()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlPersistenceUnit> persistenceUnits;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlPersistence()
	{
		super();
	}
	
	@Override
	public EObject eContainer() {
		return super.eContainer();
	}
	
	@Override
	protected void eBasicSetContainer(InternalEObject newContainer, int newContainerFeatureID) {
		super.eBasicSetContainer(newContainer, newContainerFeatureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return PersistencePackage.Literals.XML_PERSISTENCE;
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
	 * @see #isSetVersion()
	 * @see #unsetVersion()
	 * @see #setVersion(String)
	 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getJpaRootEObject_Version()
	 * @model unsettable="true" required="true"
	 * @generated
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistence#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #isSetVersion()
	 * @see #unsetVersion()
	 * @see #getVersion()
	 * @generated
	 */
	public void setVersion(String newVersion)
	{
		String oldVersion = version;
		version = newVersion;
		boolean oldVersionESet = versionESet;
		versionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.XML_PERSISTENCE__VERSION, oldVersion, version, !oldVersionESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistence#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetVersion()
	 * @see #getVersion()
	 * @see #setVersion(String)
	 * @generated
	 */
	public void unsetVersion()
	{
		String oldVersion = version;
		boolean oldVersionESet = versionESet;
		version = VERSION_EDEFAULT;
		versionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PersistencePackage.XML_PERSISTENCE__VERSION, oldVersion, VERSION_EDEFAULT, oldVersionESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.jpt.core.resource.persistence.XmlPersistence#getVersion <em>Version</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Version</em>' attribute is set.
	 * @see #unsetVersion()
	 * @see #getVersion()
	 * @see #setVersion(String)
	 * @generated
	 */
	public boolean isSetVersion()
	{
		return versionESet;
	}

	/**
	 * Returns the value of the '<em><b>Persistence Units</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XmlPersistence Units</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Units</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage#getXmlPersistence_PersistenceUnits()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlPersistenceUnit> getPersistenceUnits()
	{
		if (persistenceUnits == null)
		{
			persistenceUnits = new EObjectContainmentEList<XmlPersistenceUnit>(XmlPersistenceUnit.class, this, PersistencePackage.XML_PERSISTENCE__PERSISTENCE_UNITS);
		}
		return persistenceUnits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case PersistencePackage.XML_PERSISTENCE__PERSISTENCE_UNITS:
				return ((InternalEList<?>)getPersistenceUnits()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
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
			case PersistencePackage.XML_PERSISTENCE__VERSION:
				return getVersion();
			case PersistencePackage.XML_PERSISTENCE__PERSISTENCE_UNITS:
				return getPersistenceUnits();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case PersistencePackage.XML_PERSISTENCE__VERSION:
				setVersion((String)newValue);
				return;
			case PersistencePackage.XML_PERSISTENCE__PERSISTENCE_UNITS:
				getPersistenceUnits().clear();
				getPersistenceUnits().addAll((Collection<? extends XmlPersistenceUnit>)newValue);
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
			case PersistencePackage.XML_PERSISTENCE__VERSION:
				unsetVersion();
				return;
			case PersistencePackage.XML_PERSISTENCE__PERSISTENCE_UNITS:
				getPersistenceUnits().clear();
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
			case PersistencePackage.XML_PERSISTENCE__VERSION:
				return isSetVersion();
			case PersistencePackage.XML_PERSISTENCE__PERSISTENCE_UNITS:
				return persistenceUnits != null && !persistenceUnits.isEmpty();
		}
		return super.eIsSet(featureID);
	}
	
	
	// **************** overrides **********************************************
	
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
		if (versionESet) result.append(version); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}


	// ********** translators **********

	public static Translator getRootTranslator() {
		return ROOT_TRANSLATOR;
	}
	private static final Translator ROOT_TRANSLATOR = buildRootTranslator();

	private static Translator buildRootTranslator() {
		return new SimpleRootTranslator(
				JPA.PERSISTENCE,
				PersistencePackage.eINSTANCE.getXmlPersistence(),
				buildTranslatorChildren()
			);
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
				buildVersionTranslator(),
				buildNamespaceTranslator(),
				buildSchemaNamespaceTranslator(),
				buildSchemaLocationTranslator(),
				XmlPersistenceUnit.buildTranslator(JPA.PERSISTENCE_UNIT, PersistencePackage.eINSTANCE.getXmlPersistence_PersistenceUnits())
			};
	}
	
	protected static Translator buildVersionTranslator() {
		return new EnumeratedValueTranslator(
				JPA.PERSISTENCE__VERSION, 
				CommonPackage.eINSTANCE.getJpaRootEObject_Version(),
				Translator.DOM_ATTRIBUTE) {
			
			@Override
			protected Iterator enumeratedObjectValues() {
				return new ArrayIterator(new Object[] { JPA.SCHEMA_VERSION, JPA2_0.SCHEMA_VERSION });
			}
		};
	}
	
	protected static Translator buildNamespaceTranslator() {
		return new ConstantAttributeTranslator(XML.NAMESPACE, JPA.SCHEMA_NAMESPACE);
	}

	protected static Translator buildSchemaNamespaceTranslator() {
		return new ConstantAttributeTranslator(XML.NAMESPACE_XSI, XML.XSI_NAMESPACE_URL);
	}

	private static Translator buildSchemaLocationTranslator() {
		return new ConstantAttributeTranslator(XML.XSI_SCHEMA_LOCATION, JPA.SCHEMA_NAMESPACE + ' ' + JPA.SCHEMA_LOCATION);
	}
}
