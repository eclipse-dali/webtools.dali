/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlSecondaryTable_2_1;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Secondary Table Impl</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlSecondaryTable#getPrimaryKeyJoinColumns <em>Primary Key Join Columns</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlSecondaryTable()
 * @model kind="class"
 * @generated
 */
public class XmlSecondaryTable extends AbstractXmlTable implements XmlSecondaryTable_2_1
{
	/**
	 * The cached value of the '{@link #getPrimaryKeyForeignKey() <em>Primary Key Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryKeyForeignKey()
	 * @generated
	 * @ordered
	 */
	protected XmlForeignKey primaryKeyForeignKey;
	/**
	 * The cached value of the '{@link #getIndexes() <em>Indexes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndexes()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlIndex> indexes;
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlSecondaryTable()
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
		return OrmPackage.Literals.XML_SECONDARY_TABLE;
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
	 * @see #setPrimaryKeyForeignKey(XmlForeignKey)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlSecondaryTable_2_1_PrimaryKeyForeignKey()
	 * @model containment="true"
	 * @generated
	 */
	public XmlForeignKey getPrimaryKeyForeignKey()
	{
		return primaryKeyForeignKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrimaryKeyForeignKey(XmlForeignKey newPrimaryKeyForeignKey, NotificationChain msgs)
	{
		XmlForeignKey oldPrimaryKeyForeignKey = primaryKeyForeignKey;
		primaryKeyForeignKey = newPrimaryKeyForeignKey;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_FOREIGN_KEY, oldPrimaryKeyForeignKey, newPrimaryKeyForeignKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlSecondaryTable#getPrimaryKeyForeignKey <em>Primary Key Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Key Foreign Key</em>' containment reference.
	 * @see #getPrimaryKeyForeignKey()
	 * @generated
	 */
	public void setPrimaryKeyForeignKey(XmlForeignKey newPrimaryKeyForeignKey)
	{
		if (newPrimaryKeyForeignKey != primaryKeyForeignKey)
		{
			NotificationChain msgs = null;
			if (primaryKeyForeignKey != null)
				msgs = ((InternalEObject)primaryKeyForeignKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_FOREIGN_KEY, null, msgs);
			if (newPrimaryKeyForeignKey != null)
				msgs = ((InternalEObject)newPrimaryKeyForeignKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_FOREIGN_KEY, null, msgs);
			msgs = basicSetPrimaryKeyForeignKey(newPrimaryKeyForeignKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_FOREIGN_KEY, newPrimaryKeyForeignKey, newPrimaryKeyForeignKey));
	}

	/**
	 * Returns the value of the '<em><b>Indexes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlIndex}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Indexes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Indexes</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlSecondaryTable_2_1_Indexes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlIndex> getIndexes()
	{
		if (indexes == null)
		{
			indexes = new EObjectContainmentEList<XmlIndex>(XmlIndex.class, this, OrmPackage.XML_SECONDARY_TABLE__INDEXES);
		}
		return indexes;
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlSecondaryTable_PrimaryKeyJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns()
	{
		if (primaryKeyJoinColumns == null)
		{
			primaryKeyJoinColumns = new EObjectContainmentEList<XmlPrimaryKeyJoinColumn>(XmlPrimaryKeyJoinColumn.class, this, OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS);
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
			case OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_FOREIGN_KEY:
				return basicSetPrimaryKeyForeignKey(null, msgs);
			case OrmPackage.XML_SECONDARY_TABLE__INDEXES:
				return ((InternalEList<?>)getIndexes()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS:
				return ((InternalEList<?>)getPrimaryKeyJoinColumns()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_FOREIGN_KEY:
				return getPrimaryKeyForeignKey();
			case OrmPackage.XML_SECONDARY_TABLE__INDEXES:
				return getIndexes();
			case OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS:
				return getPrimaryKeyJoinColumns();
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
			case OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_FOREIGN_KEY:
				setPrimaryKeyForeignKey((XmlForeignKey)newValue);
				return;
			case OrmPackage.XML_SECONDARY_TABLE__INDEXES:
				getIndexes().clear();
				getIndexes().addAll((Collection<? extends XmlIndex>)newValue);
				return;
			case OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS:
				getPrimaryKeyJoinColumns().clear();
				getPrimaryKeyJoinColumns().addAll((Collection<? extends XmlPrimaryKeyJoinColumn>)newValue);
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
			case OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_FOREIGN_KEY:
				setPrimaryKeyForeignKey((XmlForeignKey)null);
				return;
			case OrmPackage.XML_SECONDARY_TABLE__INDEXES:
				getIndexes().clear();
				return;
			case OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS:
				getPrimaryKeyJoinColumns().clear();
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
			case OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_FOREIGN_KEY:
				return primaryKeyForeignKey != null;
			case OrmPackage.XML_SECONDARY_TABLE__INDEXES:
				return indexes != null && !indexes.isEmpty();
			case OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMNS:
				return primaryKeyJoinColumns != null && !primaryKeyJoinColumns.isEmpty();
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
		if (baseClass == XmlSecondaryTable_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_FOREIGN_KEY: return OrmV2_1Package.XML_SECONDARY_TABLE_21__PRIMARY_KEY_FOREIGN_KEY;
				case OrmPackage.XML_SECONDARY_TABLE__INDEXES: return OrmV2_1Package.XML_SECONDARY_TABLE_21__INDEXES;
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
		if (baseClass == XmlSecondaryTable_2_1.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_1Package.XML_SECONDARY_TABLE_21__PRIMARY_KEY_FOREIGN_KEY: return OrmPackage.XML_SECONDARY_TABLE__PRIMARY_KEY_FOREIGN_KEY;
				case OrmV2_1Package.XML_SECONDARY_TABLE_21__INDEXES: return OrmPackage.XML_SECONDARY_TABLE__INDEXES;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildCatalogTranslator(),
			buildSchemaTranslator(),
			buildPrimaryKeyJoinColumnsTranslator(),
			buildPrimaryKeyForeignKeyTranslator(),
			buildUniqueConstraintTranslator(),
			buildIndexesTranslator()
		};
	}
	
	protected static Translator buildPrimaryKeyJoinColumnsTranslator() {
		return XmlPrimaryKeyJoinColumn.buildTranslator(JPA.PRIMARY_KEY_JOIN_COLUMN, OrmPackage.eINSTANCE.getXmlSecondaryTable_PrimaryKeyJoinColumns());
	}

	protected static Translator buildPrimaryKeyForeignKeyTranslator() {
		return XmlForeignKey.buildTranslator(JPA2_1.PRIMARY_KEY_FOREIGN_KEY, OrmV2_1Package.eINSTANCE.getXmlSecondaryTable_2_1_PrimaryKeyForeignKey());
	}
	
	protected static Translator buildIndexesTranslator() {
		return XmlIndex.buildTranslator(JPA2_1.INDEX, OrmV2_1Package.eINSTANCE.getXmlSecondaryTable_2_1_Indexes());
	}

} // XmlSecondaryTableImpl
