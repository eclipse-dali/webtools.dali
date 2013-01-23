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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlOneToOne_2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlOrphanRemovable_2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlOneToOne_2_1;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>One To One</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlOneToOne()
 * @model kind="class"
 * @generated
 */
public class XmlOneToOne extends AbstractXmlSingleRelationshipMapping implements XmlMappedByMapping, XmlPrimaryKeyJoinColumnContainer, XmlOneToOne_2_0, XmlOneToOne_2_1
{

	/**
	 * The default value of the '{@link #getMappedBy() <em>Mapped By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappedBy()
	 * @generated
	 * @ordered
	 */
	protected static final String MAPPED_BY_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getMappedBy() <em>Mapped By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappedBy()
	 * @generated
	 * @ordered
	 */
	protected String mappedBy = MAPPED_BY_EDEFAULT;
	/**
	 * The cached value of the '{@link #getPrimaryKeyJoinColumns() <em>Primary Key Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryKeyJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlPrimaryKeyJoinColumn> primaryKeyJoinColumns;
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
	protected EList<XmlConvert_2_1> mapKeyConverts;
	/**
	 * The cached value of the '{@link #getPrimaryKeyForeignKey() <em>Primary Key Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryKeyForeignKey()
	 * @generated
	 * @ordered
	 */
	protected XmlForeignKey_2_1 primaryKeyForeignKey;
	/**
	 * The cached value of the '{@link #getForeignKey() <em>Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getForeignKey()
	 * @generated
	 * @ordered
	 */
	protected XmlForeignKey_2_1 foreignKey;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlOneToOne()
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
		return OrmPackage.Literals.XML_ONE_TO_ONE;
	}

	/**
	 * Returns the value of the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapped By</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapped By</em>' attribute.
	 * @see #setMappedBy(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlMappedByMapping_MappedBy()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getMappedBy()
	{
		return mappedBy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlOneToOne#getMappedBy <em>Mapped By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapped By</em>' attribute.
	 * @see #getMappedBy()
	 * @generated
	 */
	public void setMappedBy(String newMappedBy)
	{
		String oldMappedBy = mappedBy;
		mappedBy = newMappedBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ONE_TO_ONE__MAPPED_BY, oldMappedBy, mappedBy));
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
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlOneToOne#getOrphanRemoval <em>Orphan Removal</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ONE_TO_ONE__ORPHAN_REMOVAL, oldOrphanRemoval, orphanRemoval));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlOneToOne_2_1_MapKeyConverts()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlConvert_2_1> getMapKeyConverts()
	{
		if (mapKeyConverts == null)
		{
			mapKeyConverts = new EObjectContainmentEList<XmlConvert_2_1>(XmlConvert_2_1.class, this, OrmPackage.XML_ONE_TO_ONE__MAP_KEY_CONVERTS);
		}
		return mapKeyConverts;
	}

	/**
	 * Returns the value of the '<em><b>Primary Key Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Key Foreign Key</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Key Foreign Key</em>' containment reference.
	 * @see #setPrimaryKeyForeignKey(XmlForeignKey_2_1)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlOneToOne_2_1_PrimaryKeyForeignKey()
	 * @model containment="true"
	 * @generated
	 */
	public XmlForeignKey_2_1 getPrimaryKeyForeignKey()
	{
		return primaryKeyForeignKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrimaryKeyForeignKey(XmlForeignKey_2_1 newPrimaryKeyForeignKey, NotificationChain msgs)
	{
		XmlForeignKey_2_1 oldPrimaryKeyForeignKey = primaryKeyForeignKey;
		primaryKeyForeignKey = newPrimaryKeyForeignKey;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_FOREIGN_KEY, oldPrimaryKeyForeignKey, newPrimaryKeyForeignKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlOneToOne#getPrimaryKeyForeignKey <em>Primary Key Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Key Foreign Key</em>' containment reference.
	 * @see #getPrimaryKeyForeignKey()
	 * @generated
	 */
	public void setPrimaryKeyForeignKey(XmlForeignKey_2_1 newPrimaryKeyForeignKey)
	{
		if (newPrimaryKeyForeignKey != primaryKeyForeignKey)
		{
			NotificationChain msgs = null;
			if (primaryKeyForeignKey != null)
				msgs = ((InternalEObject)primaryKeyForeignKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_FOREIGN_KEY, null, msgs);
			if (newPrimaryKeyForeignKey != null)
				msgs = ((InternalEObject)newPrimaryKeyForeignKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_FOREIGN_KEY, null, msgs);
			msgs = basicSetPrimaryKeyForeignKey(newPrimaryKeyForeignKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_FOREIGN_KEY, newPrimaryKeyForeignKey, newPrimaryKeyForeignKey));
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
	 * @see #setForeignKey(XmlForeignKey_2_1)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlOneToOne_2_1_ForeignKey()
	 * @model containment="true"
	 * @generated
	 */
	public XmlForeignKey_2_1 getForeignKey()
	{
		return foreignKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetForeignKey(XmlForeignKey_2_1 newForeignKey, NotificationChain msgs)
	{
		XmlForeignKey_2_1 oldForeignKey = foreignKey;
		foreignKey = newForeignKey;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ONE_TO_ONE__FOREIGN_KEY, oldForeignKey, newForeignKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlOneToOne#getForeignKey <em>Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Foreign Key</em>' containment reference.
	 * @see #getForeignKey()
	 * @generated
	 */
	public void setForeignKey(XmlForeignKey_2_1 newForeignKey)
	{
		if (newForeignKey != foreignKey)
		{
			NotificationChain msgs = null;
			if (foreignKey != null)
				msgs = ((InternalEObject)foreignKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ONE_TO_ONE__FOREIGN_KEY, null, msgs);
			if (newForeignKey != null)
				msgs = ((InternalEObject)newForeignKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ONE_TO_ONE__FOREIGN_KEY, null, msgs);
			msgs = basicSetForeignKey(newForeignKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ONE_TO_ONE__FOREIGN_KEY, newForeignKey, newForeignKey));
	}

	/**
	 * Returns the value of the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlPrimaryKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlPrimaryKeyJoinColumnContainer_PrimaryKeyJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns()
	{
		if (primaryKeyJoinColumns == null)
		{
			primaryKeyJoinColumns = new EObjectContainmentEList<XmlPrimaryKeyJoinColumn>(XmlPrimaryKeyJoinColumn.class, this, OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS);
		}
		return primaryKeyJoinColumns;
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
			case OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS:
				return ((InternalEList<?>)getPrimaryKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ONE_TO_ONE__MAP_KEY_CONVERTS:
				return ((InternalEList<?>)getMapKeyConverts()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_FOREIGN_KEY:
				return basicSetPrimaryKeyForeignKey(null, msgs);
			case OrmPackage.XML_ONE_TO_ONE__FOREIGN_KEY:
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
			case OrmPackage.XML_ONE_TO_ONE__MAPPED_BY:
				return getMappedBy();
			case OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS:
				return getPrimaryKeyJoinColumns();
			case OrmPackage.XML_ONE_TO_ONE__ORPHAN_REMOVAL:
				return getOrphanRemoval();
			case OrmPackage.XML_ONE_TO_ONE__MAP_KEY_CONVERTS:
				return getMapKeyConverts();
			case OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_FOREIGN_KEY:
				return getPrimaryKeyForeignKey();
			case OrmPackage.XML_ONE_TO_ONE__FOREIGN_KEY:
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
			case OrmPackage.XML_ONE_TO_ONE__MAPPED_BY:
				setMappedBy((String)newValue);
				return;
			case OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS:
				getPrimaryKeyJoinColumns().clear();
				getPrimaryKeyJoinColumns().addAll((Collection<? extends XmlPrimaryKeyJoinColumn>)newValue);
				return;
			case OrmPackage.XML_ONE_TO_ONE__ORPHAN_REMOVAL:
				setOrphanRemoval((Boolean)newValue);
				return;
			case OrmPackage.XML_ONE_TO_ONE__MAP_KEY_CONVERTS:
				getMapKeyConverts().clear();
				getMapKeyConverts().addAll((Collection<? extends XmlConvert_2_1>)newValue);
				return;
			case OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_FOREIGN_KEY:
				setPrimaryKeyForeignKey((XmlForeignKey_2_1)newValue);
				return;
			case OrmPackage.XML_ONE_TO_ONE__FOREIGN_KEY:
				setForeignKey((XmlForeignKey_2_1)newValue);
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
			case OrmPackage.XML_ONE_TO_ONE__MAPPED_BY:
				setMappedBy(MAPPED_BY_EDEFAULT);
				return;
			case OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS:
				getPrimaryKeyJoinColumns().clear();
				return;
			case OrmPackage.XML_ONE_TO_ONE__ORPHAN_REMOVAL:
				setOrphanRemoval(ORPHAN_REMOVAL_EDEFAULT);
				return;
			case OrmPackage.XML_ONE_TO_ONE__MAP_KEY_CONVERTS:
				getMapKeyConverts().clear();
				return;
			case OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_FOREIGN_KEY:
				setPrimaryKeyForeignKey((XmlForeignKey_2_1)null);
				return;
			case OrmPackage.XML_ONE_TO_ONE__FOREIGN_KEY:
				setForeignKey((XmlForeignKey_2_1)null);
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
			case OrmPackage.XML_ONE_TO_ONE__MAPPED_BY:
				return MAPPED_BY_EDEFAULT == null ? mappedBy != null : !MAPPED_BY_EDEFAULT.equals(mappedBy);
			case OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS:
				return primaryKeyJoinColumns != null && !primaryKeyJoinColumns.isEmpty();
			case OrmPackage.XML_ONE_TO_ONE__ORPHAN_REMOVAL:
				return ORPHAN_REMOVAL_EDEFAULT == null ? orphanRemoval != null : !ORPHAN_REMOVAL_EDEFAULT.equals(orphanRemoval);
			case OrmPackage.XML_ONE_TO_ONE__MAP_KEY_CONVERTS:
				return mapKeyConverts != null && !mapKeyConverts.isEmpty();
			case OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_FOREIGN_KEY:
				return primaryKeyForeignKey != null;
			case OrmPackage.XML_ONE_TO_ONE__FOREIGN_KEY:
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
		if (baseClass == XmlMappedByMapping.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ONE_TO_ONE__MAPPED_BY: return OrmPackage.XML_MAPPED_BY_MAPPING__MAPPED_BY;
				default: return -1;
			}
		}
		if (baseClass == XmlPrimaryKeyJoinColumnContainer.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS: return OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN_CONTAINER__PRIMARY_KEY_JOIN_COLUMNS;
				default: return -1;
			}
		}
		if (baseClass == XmlOrphanRemovable_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ONE_TO_ONE__ORPHAN_REMOVAL: return OrmV2_0Package.XML_ORPHAN_REMOVABLE_20__ORPHAN_REMOVAL;
				default: return -1;
			}
		}
		if (baseClass == XmlOneToOne_2_0.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlOneToOne_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ONE_TO_ONE__MAP_KEY_CONVERTS: return OrmV2_1Package.XML_ONE_TO_ONE_21__MAP_KEY_CONVERTS;
				case OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_FOREIGN_KEY: return OrmV2_1Package.XML_ONE_TO_ONE_21__PRIMARY_KEY_FOREIGN_KEY;
				case OrmPackage.XML_ONE_TO_ONE__FOREIGN_KEY: return OrmV2_1Package.XML_ONE_TO_ONE_21__FOREIGN_KEY;
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
		if (baseClass == XmlMappedByMapping.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_MAPPED_BY_MAPPING__MAPPED_BY: return OrmPackage.XML_ONE_TO_ONE__MAPPED_BY;
				default: return -1;
			}
		}
		if (baseClass == XmlPrimaryKeyJoinColumnContainer.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN_CONTAINER__PRIMARY_KEY_JOIN_COLUMNS: return OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS;
				default: return -1;
			}
		}
		if (baseClass == XmlOrphanRemovable_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_ORPHAN_REMOVABLE_20__ORPHAN_REMOVAL: return OrmPackage.XML_ONE_TO_ONE__ORPHAN_REMOVAL;
				default: return -1;
			}
		}
		if (baseClass == XmlOneToOne_2_0.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlOneToOne_2_1.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_1Package.XML_ONE_TO_ONE_21__MAP_KEY_CONVERTS: return OrmPackage.XML_ONE_TO_ONE__MAP_KEY_CONVERTS;
				case OrmV2_1Package.XML_ONE_TO_ONE_21__PRIMARY_KEY_FOREIGN_KEY: return OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_FOREIGN_KEY;
				case OrmV2_1Package.XML_ONE_TO_ONE_21__FOREIGN_KEY: return OrmPackage.XML_ONE_TO_ONE__FOREIGN_KEY;
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
		result.append(" (mappedBy: ");
		result.append(mappedBy);
		result.append(", orphanRemoval: ");
		result.append(orphanRemoval);
		result.append(')');
		return result.toString();
	}
	
	
	// **************** XmlAttributeMapping impl ******************************

	public String getMappingKey() {
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	
	// **************** validation support ************************************
	
	public TextRange getMappedByTextRange() {
		return getAttributeTextRange(JPA.MAPPED_BY);
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
			buildOptionalTranslator(),
			buildAccessTranslator(),
			buildMappedByTranslator(),
			buildOrphanRemovalTranslator(),
			buildMapsIdTranslator(),
			buildIdTranslator(),
			buildPrimaryKeyJoinColumnTranslator(),
			buildPrimaryKeyForeignKeyTranslator(),
			buildJoinColumnTranslator(),
			buildForeignKeyTranslator(),
			buildJoinTableTranslator(),
			buildCascadeTranslator()
		};
	}
	
	protected static Translator buildMappedByTranslator() {
		return new Translator(JPA.MAPPED_BY, OrmPackage.eINSTANCE.getXmlMappedByMapping_MappedBy(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildOrphanRemovalTranslator() {
		return new Translator(JPA2_0.ORPHAN_REMOVAL, OrmV2_0Package.eINSTANCE.getXmlOrphanRemovable_2_0_OrphanRemoval(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildPrimaryKeyJoinColumnTranslator() {
		return XmlPrimaryKeyJoinColumn.buildTranslator(JPA.PRIMARY_KEY_JOIN_COLUMN, OrmPackage.eINSTANCE.getXmlPrimaryKeyJoinColumnContainer_PrimaryKeyJoinColumns());
	}
	
	protected static Translator buildForeignKeyTranslator() {
		return XmlForeignKey.buildTranslator(JPA2_1.FOREIGN_KEY, OrmV2_1Package.eINSTANCE.getXmlOneToOne_2_1_ForeignKey());
	}
	
	protected static Translator buildPrimaryKeyForeignKeyTranslator() {
		return XmlForeignKey.buildTranslator(JPA2_1.PRIMARY_KEY_FOREIGN_KEY, OrmV2_1Package.eINSTANCE.getXmlOneToOne_2_1_PrimaryKeyForeignKey());
	}
	

	// ********** content assist ***************
	
	public TextRange getMappedByCodeAssistTextRange() {
		return getAttributeCodeAssistTextRange(JPA.MAPPED_BY);
	}
	
	public boolean mappedByTouches(int pos) {
		TextRange textRange = this.getMappedByCodeAssistTextRange();
		return (textRange!= null) && textRange.touches(pos);
	}
}
