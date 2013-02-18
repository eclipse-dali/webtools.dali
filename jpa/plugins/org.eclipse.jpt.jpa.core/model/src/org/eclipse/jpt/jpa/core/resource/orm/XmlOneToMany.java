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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlOneToMany_2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlOrphanRemovable_2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToMany_2_1;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>One To Many</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlOneToMany()
 * @model kind="class"
 * @generated
 */
public class XmlOneToMany extends AbstractXmlMultiRelationshipMapping implements XmlJoinColumnContainer, XmlOneToMany_2_0, XmlOneToMany_2_1
{

	/**
	 * The cached value of the '{@link #getJoinColumns() <em>Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlJoinColumn> joinColumns;

	/**
	 * The default value of the '{@link #getOrphanRemoval() <em>Orphan Removal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrphanRemoval()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean ORPHAN_REMOVAL_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getOrphanRemoval() <em>Orphan Removal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrphanRemoval()
	 * @generated
	 * @ordered
	 */
	protected Boolean orphanRemoval = ORPHAN_REMOVAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMapKeyConverts() <em>Map Key Converts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyConverts()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlConvert> mapKeyConverts;

	/**
	 * The cached value of the '{@link #getMapKeyForeignKey() <em>Map Key Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyForeignKey()
	 * @generated
	 * @ordered
	 */
	protected XmlForeignKey mapKeyForeignKey;

	/**
	 * The cached value of the '{@link #getForeignKey() <em>Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getForeignKey()
	 * @generated
	 * @ordered
	 */
	protected XmlForeignKey foreignKey;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlOneToMany()
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
		return OrmPackage.Literals.XML_ONE_TO_MANY;
	}

	/**
	 * Returns the value of the '<em><b>Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlJoinColumnContainer_JoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlJoinColumn> getJoinColumns()
	{
		if (joinColumns == null)
		{
			joinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS);
		}
		return joinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Orphan Removal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Orphan Removal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Orphan Removal</em>' attribute.
	 * @see #setOrphanRemoval(Boolean)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlOrphanRemovable_2_0_OrphanRemoval()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getOrphanRemoval()
	{
		return orphanRemoval;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlOneToMany#getOrphanRemoval <em>Orphan Removal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Orphan Removal</em>' attribute.
	 * @see #getOrphanRemoval()
	 * @generated
	 */
	public void setOrphanRemoval(Boolean newOrphanRemoval)
	{
		Boolean oldOrphanRemoval = orphanRemoval;
		orphanRemoval = newOrphanRemoval;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ONE_TO_MANY__ORPHAN_REMOVAL, oldOrphanRemoval, orphanRemoval));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Converts</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlConvert}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Converts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Converts</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlOneToMany_2_1_MapKeyConverts()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlConvert> getMapKeyConverts()
	{
		if (mapKeyConverts == null)
		{
			mapKeyConverts = new EObjectContainmentEList<XmlConvert>(XmlConvert.class, this, OrmPackage.XML_ONE_TO_MANY__MAP_KEY_CONVERTS);
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
	 * @see #setMapKeyForeignKey(XmlForeignKey)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlOneToMany_2_1_MapKeyForeignKey()
	 * @model containment="true"
	 * @generated
	 */
	public XmlForeignKey getMapKeyForeignKey()
	{
		return mapKeyForeignKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapKeyForeignKey(XmlForeignKey newMapKeyForeignKey, NotificationChain msgs)
	{
		XmlForeignKey oldMapKeyForeignKey = mapKeyForeignKey;
		mapKeyForeignKey = newMapKeyForeignKey;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ONE_TO_MANY__MAP_KEY_FOREIGN_KEY, oldMapKeyForeignKey, newMapKeyForeignKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlOneToMany#getMapKeyForeignKey <em>Map Key Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Foreign Key</em>' containment reference.
	 * @see #getMapKeyForeignKey()
	 * @generated
	 */
	public void setMapKeyForeignKey(XmlForeignKey newMapKeyForeignKey)
	{
		if (newMapKeyForeignKey != mapKeyForeignKey)
		{
			NotificationChain msgs = null;
			if (mapKeyForeignKey != null)
				msgs = ((InternalEObject)mapKeyForeignKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ONE_TO_MANY__MAP_KEY_FOREIGN_KEY, null, msgs);
			if (newMapKeyForeignKey != null)
				msgs = ((InternalEObject)newMapKeyForeignKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ONE_TO_MANY__MAP_KEY_FOREIGN_KEY, null, msgs);
			msgs = basicSetMapKeyForeignKey(newMapKeyForeignKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ONE_TO_MANY__MAP_KEY_FOREIGN_KEY, newMapKeyForeignKey, newMapKeyForeignKey));
	}

	/**
	 * Returns the value of the '<em><b>Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Foreign Key</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Foreign Key</em>' containment reference.
	 * @see #setForeignKey(XmlForeignKey)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlOneToMany_2_1_ForeignKey()
	 * @model containment="true"
	 * @generated
	 */
	public XmlForeignKey getForeignKey()
	{
		return foreignKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetForeignKey(XmlForeignKey newForeignKey, NotificationChain msgs)
	{
		XmlForeignKey oldForeignKey = foreignKey;
		foreignKey = newForeignKey;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ONE_TO_MANY__FOREIGN_KEY, oldForeignKey, newForeignKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlOneToMany#getForeignKey <em>Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Foreign Key</em>' containment reference.
	 * @see #getForeignKey()
	 * @generated
	 */
	public void setForeignKey(XmlForeignKey newForeignKey)
	{
		if (newForeignKey != foreignKey)
		{
			NotificationChain msgs = null;
			if (foreignKey != null)
				msgs = ((InternalEObject)foreignKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ONE_TO_MANY__FOREIGN_KEY, null, msgs);
			if (newForeignKey != null)
				msgs = ((InternalEObject)newForeignKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ONE_TO_MANY__FOREIGN_KEY, null, msgs);
			msgs = basicSetForeignKey(newForeignKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ONE_TO_MANY__FOREIGN_KEY, newForeignKey, newForeignKey));
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
			case OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS:
				return ((InternalEList<?>)getJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ONE_TO_MANY__MAP_KEY_CONVERTS:
				return ((InternalEList<?>)getMapKeyConverts()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ONE_TO_MANY__MAP_KEY_FOREIGN_KEY:
				return basicSetMapKeyForeignKey(null, msgs);
			case OrmPackage.XML_ONE_TO_MANY__FOREIGN_KEY:
				return basicSetForeignKey(null, msgs);
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
			case OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS:
				return getJoinColumns();
			case OrmPackage.XML_ONE_TO_MANY__ORPHAN_REMOVAL:
				return getOrphanRemoval();
			case OrmPackage.XML_ONE_TO_MANY__MAP_KEY_CONVERTS:
				return getMapKeyConverts();
			case OrmPackage.XML_ONE_TO_MANY__MAP_KEY_FOREIGN_KEY:
				return getMapKeyForeignKey();
			case OrmPackage.XML_ONE_TO_MANY__FOREIGN_KEY:
				return getForeignKey();
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
			case OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS:
				getJoinColumns().clear();
				getJoinColumns().addAll((Collection<? extends XmlJoinColumn>)newValue);
				return;
			case OrmPackage.XML_ONE_TO_MANY__ORPHAN_REMOVAL:
				setOrphanRemoval((Boolean)newValue);
				return;
			case OrmPackage.XML_ONE_TO_MANY__MAP_KEY_CONVERTS:
				getMapKeyConverts().clear();
				getMapKeyConverts().addAll((Collection<? extends XmlConvert>)newValue);
				return;
			case OrmPackage.XML_ONE_TO_MANY__MAP_KEY_FOREIGN_KEY:
				setMapKeyForeignKey((XmlForeignKey)newValue);
				return;
			case OrmPackage.XML_ONE_TO_MANY__FOREIGN_KEY:
				setForeignKey((XmlForeignKey)newValue);
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
			case OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS:
				getJoinColumns().clear();
				return;
			case OrmPackage.XML_ONE_TO_MANY__ORPHAN_REMOVAL:
				setOrphanRemoval(ORPHAN_REMOVAL_EDEFAULT);
				return;
			case OrmPackage.XML_ONE_TO_MANY__MAP_KEY_CONVERTS:
				getMapKeyConverts().clear();
				return;
			case OrmPackage.XML_ONE_TO_MANY__MAP_KEY_FOREIGN_KEY:
				setMapKeyForeignKey((XmlForeignKey)null);
				return;
			case OrmPackage.XML_ONE_TO_MANY__FOREIGN_KEY:
				setForeignKey((XmlForeignKey)null);
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
			case OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS:
				return joinColumns != null && !joinColumns.isEmpty();
			case OrmPackage.XML_ONE_TO_MANY__ORPHAN_REMOVAL:
				return ORPHAN_REMOVAL_EDEFAULT == null ? orphanRemoval != null : !ORPHAN_REMOVAL_EDEFAULT.equals(orphanRemoval);
			case OrmPackage.XML_ONE_TO_MANY__MAP_KEY_CONVERTS:
				return mapKeyConverts != null && !mapKeyConverts.isEmpty();
			case OrmPackage.XML_ONE_TO_MANY__MAP_KEY_FOREIGN_KEY:
				return mapKeyForeignKey != null;
			case OrmPackage.XML_ONE_TO_MANY__FOREIGN_KEY:
				return foreignKey != null;
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
		if (baseClass == XmlJoinColumnContainer.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS: return OrmPackage.XML_JOIN_COLUMN_CONTAINER__JOIN_COLUMNS;
				default: return -1;
			}
		}
		if (baseClass == XmlOrphanRemovable_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ONE_TO_MANY__ORPHAN_REMOVAL: return OrmV2_0Package.XML_ORPHAN_REMOVABLE_20__ORPHAN_REMOVAL;
				default: return -1;
			}
		}
		if (baseClass == XmlOneToMany_2_0.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlOneToMany_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ONE_TO_MANY__MAP_KEY_CONVERTS: return OrmV2_1Package.XML_ONE_TO_MANY_21__MAP_KEY_CONVERTS;
				case OrmPackage.XML_ONE_TO_MANY__MAP_KEY_FOREIGN_KEY: return OrmV2_1Package.XML_ONE_TO_MANY_21__MAP_KEY_FOREIGN_KEY;
				case OrmPackage.XML_ONE_TO_MANY__FOREIGN_KEY: return OrmV2_1Package.XML_ONE_TO_MANY_21__FOREIGN_KEY;
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
		if (baseClass == XmlJoinColumnContainer.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_JOIN_COLUMN_CONTAINER__JOIN_COLUMNS: return OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS;
				default: return -1;
			}
		}
		if (baseClass == XmlOrphanRemovable_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_ORPHAN_REMOVABLE_20__ORPHAN_REMOVAL: return OrmPackage.XML_ONE_TO_MANY__ORPHAN_REMOVAL;
				default: return -1;
			}
		}
		if (baseClass == XmlOneToMany_2_0.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlOneToMany_2_1.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_1Package.XML_ONE_TO_MANY_21__MAP_KEY_CONVERTS: return OrmPackage.XML_ONE_TO_MANY__MAP_KEY_CONVERTS;
				case OrmV2_1Package.XML_ONE_TO_MANY_21__MAP_KEY_FOREIGN_KEY: return OrmPackage.XML_ONE_TO_MANY__MAP_KEY_FOREIGN_KEY;
				case OrmV2_1Package.XML_ONE_TO_MANY_21__FOREIGN_KEY: return OrmPackage.XML_ONE_TO_MANY__FOREIGN_KEY;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (orphanRemoval: ");
		result.append(orphanRemoval);
		result.append(')');
		return result.toString();
	}

	public String getMappingKey() {
		return MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
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
			buildOrphanRemovalTranslator(),
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
			buildJoinColumnTranslator(),
			buildForeignKeyTranslator(),
			buildCascadeTranslator()
		};
	}
	
	protected static Translator buildOrphanRemovalTranslator() {
		return new Translator(JPA2_0.ORPHAN_REMOVAL, OrmV2_0Package.eINSTANCE.getXmlOrphanRemovable_2_0_OrphanRemoval(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildJoinColumnTranslator() {
		return XmlJoinColumn.buildTranslator(JPA.JOIN_COLUMN, OrmPackage.eINSTANCE.getXmlJoinColumnContainer_JoinColumns());
	}
	
	protected static Translator buildMapKeyConvertTranslator() {
		return XmlConvert.buildTranslator(JPA2_1.MAP_KEY_CONVERT, OrmV2_1Package.eINSTANCE.getXmlOneToMany_2_1_MapKeyConverts());
	}
	
	protected static Translator buildMapKeyForeignKeyTranslator() {
		return XmlForeignKey.buildTranslator(JPA2_1.MAP_KEY_FOREIGN_KEY, OrmV2_1Package.eINSTANCE.getXmlOneToMany_2_1_MapKeyForeignKey());
	}
	
	protected static Translator buildForeignKeyTranslator() {
		return XmlForeignKey.buildTranslator(JPA2_1.FOREIGN_KEY, OrmV2_1Package.eINSTANCE.getXmlOneToMany_2_1_ForeignKey());
	}

}
