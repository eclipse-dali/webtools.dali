/*******************************************************************************
 *  Copyright (c) 2009, 2013  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

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
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlCollectionTable_2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlCollectionTable_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Collection Table</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlCollectionTable()
 * @model kind="class"
 * @generated
 */
public class XmlCollectionTable extends AbstractXmlReferenceTable implements XmlCollectionTable_2_0, XmlCollectionTable_2_1
{
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
	 * The cached value of the '{@link #getIndexes() <em>Indexes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndexes()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlIndex_2_1> indexes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlCollectionTable()
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
		return OrmPackage.Literals.XML_COLLECTION_TABLE;
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlCollectionTable_2_1_ForeignKey()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_COLLECTION_TABLE__FOREIGN_KEY, oldForeignKey, newForeignKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlCollectionTable#getForeignKey <em>Foreign Key</em>}' containment reference.
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
				msgs = ((InternalEObject)foreignKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_COLLECTION_TABLE__FOREIGN_KEY, null, msgs);
			if (newForeignKey != null)
				msgs = ((InternalEObject)newForeignKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_COLLECTION_TABLE__FOREIGN_KEY, null, msgs);
			msgs = basicSetForeignKey(newForeignKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_COLLECTION_TABLE__FOREIGN_KEY, newForeignKey, newForeignKey));
	}

	/**
	 * Returns the value of the '<em><b>Indexes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Indexes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Indexes</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlCollectionTable_2_1_Indexes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlIndex_2_1> getIndexes()
	{
		if (indexes == null)
		{
			indexes = new EObjectContainmentEList<XmlIndex_2_1>(XmlIndex_2_1.class, this, OrmPackage.XML_COLLECTION_TABLE__INDEXES);
		}
		return indexes;
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
			case OrmPackage.XML_COLLECTION_TABLE__FOREIGN_KEY:
				return basicSetForeignKey(null, msgs);
			case OrmPackage.XML_COLLECTION_TABLE__INDEXES:
				return ((InternalEList<?>)getIndexes()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.XML_COLLECTION_TABLE__FOREIGN_KEY:
				return getForeignKey();
			case OrmPackage.XML_COLLECTION_TABLE__INDEXES:
				return getIndexes();
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
			case OrmPackage.XML_COLLECTION_TABLE__FOREIGN_KEY:
				setForeignKey((XmlForeignKey_2_1)newValue);
				return;
			case OrmPackage.XML_COLLECTION_TABLE__INDEXES:
				getIndexes().clear();
				getIndexes().addAll((Collection<? extends XmlIndex_2_1>)newValue);
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
			case OrmPackage.XML_COLLECTION_TABLE__FOREIGN_KEY:
				setForeignKey((XmlForeignKey_2_1)null);
				return;
			case OrmPackage.XML_COLLECTION_TABLE__INDEXES:
				getIndexes().clear();
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
			case OrmPackage.XML_COLLECTION_TABLE__FOREIGN_KEY:
				return foreignKey != null;
			case OrmPackage.XML_COLLECTION_TABLE__INDEXES:
				return indexes != null && !indexes.isEmpty();
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
		if (baseClass == XmlCollectionTable_2_0.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlCollectionTable_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_COLLECTION_TABLE__FOREIGN_KEY: return OrmV2_1Package.XML_COLLECTION_TABLE_21__FOREIGN_KEY;
				case OrmPackage.XML_COLLECTION_TABLE__INDEXES: return OrmV2_1Package.XML_COLLECTION_TABLE_21__INDEXES;
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
		if (baseClass == XmlCollectionTable_2_0.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlCollectionTable_2_1.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_1Package.XML_COLLECTION_TABLE_21__FOREIGN_KEY: return OrmPackage.XML_COLLECTION_TABLE__FOREIGN_KEY;
				case OrmV2_1Package.XML_COLLECTION_TABLE_21__INDEXES: return OrmPackage.XML_COLLECTION_TABLE__INDEXES;
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
			buildJoinColumnTranslator(),
			buildForeignKeyTranslator(),
			buildUniqueConstraintTranslator(),
			buildIndexesTranslator(),
		};
	}

	protected static Translator buildForeignKeyTranslator() {
		return XmlForeignKey.buildTranslator(JPA2_1.FOREIGN_KEY, OrmV2_1Package.eINSTANCE.getXmlCollectionTable_2_1_ForeignKey());
	}
	
	protected static Translator buildIndexesTranslator() {
		return XmlIndex.buildTranslator(JPA2_1.INDEX, OrmV2_1Package.eINSTANCE.getXmlCollectionTable_2_1_Indexes());
	}

}
