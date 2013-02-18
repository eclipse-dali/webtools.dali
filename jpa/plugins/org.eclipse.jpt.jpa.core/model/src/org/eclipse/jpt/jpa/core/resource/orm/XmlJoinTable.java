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
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlIndex_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlJoinTable_2_1;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Join Table</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlJoinTable#getInverseJoinColumns <em>Inverse Join Columns</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlJoinTable()
 * @model kind="class"
 * @generated
 */
public class XmlJoinTable extends AbstractXmlReferenceTable implements XmlJoinTable_2_1
{
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
	 * The cached value of the '{@link #getInverseForeignKey() <em>Inverse Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInverseForeignKey()
	 * @generated
	 * @ordered
	 */
	protected XmlForeignKey inverseForeignKey;
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
	 * The cached value of the '{@link #getInverseJoinColumns() <em>Inverse Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInverseJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlJoinColumn> inverseJoinColumns;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlJoinTable()
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
		return OrmPackage.Literals.XML_JOIN_TABLE;
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlJoinTable_2_1_ForeignKey()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_JOIN_TABLE__FOREIGN_KEY, oldForeignKey, newForeignKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlJoinTable#getForeignKey <em>Foreign Key</em>}' containment reference.
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
				msgs = ((InternalEObject)foreignKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_JOIN_TABLE__FOREIGN_KEY, null, msgs);
			if (newForeignKey != null)
				msgs = ((InternalEObject)newForeignKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_JOIN_TABLE__FOREIGN_KEY, null, msgs);
			msgs = basicSetForeignKey(newForeignKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_JOIN_TABLE__FOREIGN_KEY, newForeignKey, newForeignKey));
	}

	/**
	 * Returns the value of the '<em><b>Inverse Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inverse Foreign Key</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inverse Foreign Key</em>' containment reference.
	 * @see #setInverseForeignKey(XmlForeignKey)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlJoinTable_2_1_InverseForeignKey()
	 * @model containment="true"
	 * @generated
	 */
	public XmlForeignKey getInverseForeignKey()
	{
		return inverseForeignKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInverseForeignKey(XmlForeignKey newInverseForeignKey, NotificationChain msgs)
	{
		XmlForeignKey oldInverseForeignKey = inverseForeignKey;
		inverseForeignKey = newInverseForeignKey;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_JOIN_TABLE__INVERSE_FOREIGN_KEY, oldInverseForeignKey, newInverseForeignKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlJoinTable#getInverseForeignKey <em>Inverse Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inverse Foreign Key</em>' containment reference.
	 * @see #getInverseForeignKey()
	 * @generated
	 */
	public void setInverseForeignKey(XmlForeignKey newInverseForeignKey)
	{
		if (newInverseForeignKey != inverseForeignKey)
		{
			NotificationChain msgs = null;
			if (inverseForeignKey != null)
				msgs = ((InternalEObject)inverseForeignKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_JOIN_TABLE__INVERSE_FOREIGN_KEY, null, msgs);
			if (newInverseForeignKey != null)
				msgs = ((InternalEObject)newInverseForeignKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_JOIN_TABLE__INVERSE_FOREIGN_KEY, null, msgs);
			msgs = basicSetInverseForeignKey(newInverseForeignKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_JOIN_TABLE__INVERSE_FOREIGN_KEY, newInverseForeignKey, newInverseForeignKey));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlJoinTable_2_1_Indexes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlIndex_2_1> getIndexes()
	{
		if (indexes == null)
		{
			indexes = new EObjectContainmentEList<XmlIndex_2_1>(XmlIndex_2_1.class, this, OrmPackage.XML_JOIN_TABLE__INDEXES);
		}
		return indexes;
	}

	/**
	 * Returns the value of the '<em><b>Inverse Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inverse Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inverse Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlJoinTable_InverseJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlJoinColumn> getInverseJoinColumns()
	{
		if (inverseJoinColumns == null)
		{
			inverseJoinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS);
		}
		return inverseJoinColumns;
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
			case OrmPackage.XML_JOIN_TABLE__FOREIGN_KEY:
				return basicSetForeignKey(null, msgs);
			case OrmPackage.XML_JOIN_TABLE__INVERSE_FOREIGN_KEY:
				return basicSetInverseForeignKey(null, msgs);
			case OrmPackage.XML_JOIN_TABLE__INDEXES:
				return ((InternalEList<?>)getIndexes()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS:
				return ((InternalEList<?>)getInverseJoinColumns()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.XML_JOIN_TABLE__FOREIGN_KEY:
				return getForeignKey();
			case OrmPackage.XML_JOIN_TABLE__INVERSE_FOREIGN_KEY:
				return getInverseForeignKey();
			case OrmPackage.XML_JOIN_TABLE__INDEXES:
				return getIndexes();
			case OrmPackage.XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS:
				return getInverseJoinColumns();
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
			case OrmPackage.XML_JOIN_TABLE__FOREIGN_KEY:
				setForeignKey((XmlForeignKey)newValue);
				return;
			case OrmPackage.XML_JOIN_TABLE__INVERSE_FOREIGN_KEY:
				setInverseForeignKey((XmlForeignKey)newValue);
				return;
			case OrmPackage.XML_JOIN_TABLE__INDEXES:
				getIndexes().clear();
				getIndexes().addAll((Collection<? extends XmlIndex_2_1>)newValue);
				return;
			case OrmPackage.XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS:
				getInverseJoinColumns().clear();
				getInverseJoinColumns().addAll((Collection<? extends XmlJoinColumn>)newValue);
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
			case OrmPackage.XML_JOIN_TABLE__FOREIGN_KEY:
				setForeignKey((XmlForeignKey)null);
				return;
			case OrmPackage.XML_JOIN_TABLE__INVERSE_FOREIGN_KEY:
				setInverseForeignKey((XmlForeignKey)null);
				return;
			case OrmPackage.XML_JOIN_TABLE__INDEXES:
				getIndexes().clear();
				return;
			case OrmPackage.XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS:
				getInverseJoinColumns().clear();
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
			case OrmPackage.XML_JOIN_TABLE__FOREIGN_KEY:
				return foreignKey != null;
			case OrmPackage.XML_JOIN_TABLE__INVERSE_FOREIGN_KEY:
				return inverseForeignKey != null;
			case OrmPackage.XML_JOIN_TABLE__INDEXES:
				return indexes != null && !indexes.isEmpty();
			case OrmPackage.XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS:
				return inverseJoinColumns != null && !inverseJoinColumns.isEmpty();
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
		if (baseClass == XmlJoinTable_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_JOIN_TABLE__FOREIGN_KEY: return OrmV2_1Package.XML_JOIN_TABLE_21__FOREIGN_KEY;
				case OrmPackage.XML_JOIN_TABLE__INVERSE_FOREIGN_KEY: return OrmV2_1Package.XML_JOIN_TABLE_21__INVERSE_FOREIGN_KEY;
				case OrmPackage.XML_JOIN_TABLE__INDEXES: return OrmV2_1Package.XML_JOIN_TABLE_21__INDEXES;
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
		if (baseClass == XmlJoinTable_2_1.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_1Package.XML_JOIN_TABLE_21__FOREIGN_KEY: return OrmPackage.XML_JOIN_TABLE__FOREIGN_KEY;
				case OrmV2_1Package.XML_JOIN_TABLE_21__INVERSE_FOREIGN_KEY: return OrmPackage.XML_JOIN_TABLE__INVERSE_FOREIGN_KEY;
				case OrmV2_1Package.XML_JOIN_TABLE_21__INDEXES: return OrmPackage.XML_JOIN_TABLE__INDEXES;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	public boolean isSpecified() {
		return true;
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
			buildInverseJoinColumnTranslator(),
			buildInverseForeignKeyTranslator(),
			buildUniqueConstraintTranslator(),
			buildIndexTranslator()
		};
	}
	
	protected static Translator buildForeignKeyTranslator() {
		return XmlForeignKey.buildTranslator(JPA2_1.FOREIGN_KEY, OrmV2_1Package.eINSTANCE.getXmlJoinTable_2_1_ForeignKey());
	}
	
	protected static Translator buildInverseJoinColumnTranslator() {
		return XmlJoinColumn.buildTranslator(JPA.INVERSE_JOIN_COLUMN, OrmPackage.eINSTANCE.getXmlJoinTable_InverseJoinColumns());
	}

	protected static Translator buildInverseForeignKeyTranslator() {
		return XmlForeignKey.buildTranslator(JPA2_1.INVERSE_FOREIGN_KEY, OrmV2_1Package.eINSTANCE.getXmlJoinTable_2_1_InverseForeignKey());
	}
	
	protected static Translator buildIndexTranslator() {
		return XmlIndex.buildTranslator(JPA2_1.INDEX, OrmV2_1Package.eINSTANCE.getXmlJoinTable_2_1_Indexes());
	}
	

} // JoinTable
