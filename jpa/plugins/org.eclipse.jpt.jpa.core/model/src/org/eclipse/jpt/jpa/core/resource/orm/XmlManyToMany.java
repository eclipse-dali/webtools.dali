/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlManyToMany_2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlManyToMany_2_1;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Many To Many</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlManyToMany()
 * @model kind="class"
 * @generated
 */
public class XmlManyToMany extends AbstractXmlMultiRelationshipMapping implements XmlManyToMany_2_0, XmlManyToMany_2_1
{
	/**
	 * The cached value of the '{@link #getMapKeyConverts() <em>Map Key Converts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyConverts()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlConvert_2_1> mapKeyConverts;
	/**
	 * The cached value of the '{@link #getMapKeyForeignKey() <em>Map Key Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyForeignKey()
	 * @generated
	 * @ordered
	 */
	protected XmlForeignKey_2_1 mapKeyForeignKey;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlManyToMany()
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
		return OrmPackage.Literals.XML_MANY_TO_MANY;
	}
	
	/**
	 * Returns the value of the '<em><b>Map Key Converts</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Converts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Converts</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlManyToMany_2_1_MapKeyConverts()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlConvert_2_1> getMapKeyConverts()
	{
		if (mapKeyConverts == null)
		{
			mapKeyConverts = new EObjectContainmentEList<XmlConvert_2_1>(XmlConvert_2_1.class, this, OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CONVERTS);
		}
		return mapKeyConverts;
	}

	/**
	 * Returns the value of the '<em><b>Map Key Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Foreign Key</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Foreign Key</em>' containment reference.
	 * @see #setMapKeyForeignKey(XmlForeignKey_2_1)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlManyToMany_2_1_MapKeyForeignKey()
	 * @model containment="true"
	 * @generated
	 */
	public XmlForeignKey_2_1 getMapKeyForeignKey()
	{
		return mapKeyForeignKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapKeyForeignKey(XmlForeignKey_2_1 newMapKeyForeignKey, NotificationChain msgs)
	{
		XmlForeignKey_2_1 oldMapKeyForeignKey = mapKeyForeignKey;
		mapKeyForeignKey = newMapKeyForeignKey;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MANY_TO_MANY__MAP_KEY_FOREIGN_KEY, oldMapKeyForeignKey, newMapKeyForeignKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlManyToMany#getMapKeyForeignKey <em>Map Key Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Foreign Key</em>' containment reference.
	 * @see #getMapKeyForeignKey()
	 * @generated
	 */
	public void setMapKeyForeignKey(XmlForeignKey_2_1 newMapKeyForeignKey)
	{
		if (newMapKeyForeignKey != mapKeyForeignKey)
		{
			NotificationChain msgs = null;
			if (mapKeyForeignKey != null)
				msgs = ((InternalEObject)mapKeyForeignKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_MANY_TO_MANY__MAP_KEY_FOREIGN_KEY, null, msgs);
			if (newMapKeyForeignKey != null)
				msgs = ((InternalEObject)newMapKeyForeignKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_MANY_TO_MANY__MAP_KEY_FOREIGN_KEY, null, msgs);
			msgs = basicSetMapKeyForeignKey(newMapKeyForeignKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MANY_TO_MANY__MAP_KEY_FOREIGN_KEY, newMapKeyForeignKey, newMapKeyForeignKey));
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
			case OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CONVERTS:
				return ((InternalEList<?>)getMapKeyConverts()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_MANY_TO_MANY__MAP_KEY_FOREIGN_KEY:
				return basicSetMapKeyForeignKey(null, msgs);
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
			case OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CONVERTS:
				return getMapKeyConverts();
			case OrmPackage.XML_MANY_TO_MANY__MAP_KEY_FOREIGN_KEY:
				return getMapKeyForeignKey();
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
			case OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CONVERTS:
				getMapKeyConverts().clear();
				getMapKeyConverts().addAll((Collection<? extends XmlConvert_2_1>)newValue);
				return;
			case OrmPackage.XML_MANY_TO_MANY__MAP_KEY_FOREIGN_KEY:
				setMapKeyForeignKey((XmlForeignKey_2_1)newValue);
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
			case OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CONVERTS:
				getMapKeyConverts().clear();
				return;
			case OrmPackage.XML_MANY_TO_MANY__MAP_KEY_FOREIGN_KEY:
				setMapKeyForeignKey((XmlForeignKey_2_1)null);
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
			case OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CONVERTS:
				return mapKeyConverts != null && !mapKeyConverts.isEmpty();
			case OrmPackage.XML_MANY_TO_MANY__MAP_KEY_FOREIGN_KEY:
				return mapKeyForeignKey != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
	{
		if (baseClass == XmlManyToMany_2_0.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlManyToMany_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CONVERTS: return OrmV2_1Package.XML_MANY_TO_MANY_21__MAP_KEY_CONVERTS;
				case OrmPackage.XML_MANY_TO_MANY__MAP_KEY_FOREIGN_KEY: return OrmV2_1Package.XML_MANY_TO_MANY_21__MAP_KEY_FOREIGN_KEY;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass)
	{
		if (baseClass == XmlManyToMany_2_0.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlManyToMany_2_1.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_1Package.XML_MANY_TO_MANY_21__MAP_KEY_CONVERTS: return OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CONVERTS;
				case OrmV2_1Package.XML_MANY_TO_MANY_21__MAP_KEY_FOREIGN_KEY: return OrmPackage.XML_MANY_TO_MANY__MAP_KEY_FOREIGN_KEY;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	public String getMappingKey() {
		return MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildTargetEntityTranslator(),
			buildFetchTranslator(),
			buildAccessTranslator(),
			buildMappedByTranslator(),
			buildOrderByTranslator(),
			buildOrderColumnTranslator(),		
			buildMapKeyTranslator(),
			buildMapKeyClassTranslator(),
			buildMapKeyTemporalTranslator(),
			buildMapKeyEnumeratedTranslator(),
			buildMapKeyAttributeOverrideTranslator(),
			buildMapKeyConvertTranslator(),
			buildMapKeyColumnTranslator(),
			buildMapKeyJoinColumnTranslator(),
			buildMapKeyForeignKeyTranslator(),
			buildJoinTableTranslator(),
			buildCascadeTranslator()
		};
	}
	
	protected static Translator buildMapKeyConvertTranslator() {
		return XmlConvert.buildTranslator(JPA2_1.MAP_KEY_CONVERT, OrmV2_1Package.eINSTANCE.getXmlManyToMany_2_1_MapKeyConverts());
	}
	
	protected static Translator buildMapKeyForeignKeyTranslator() {
		return XmlForeignKey.buildTranslator(JPA2_1.MAP_KEY_FOREIGN_KEY, OrmV2_1Package.eINSTANCE.getXmlManyToMany_2_1_MapKeyForeignKey());
	}

}
