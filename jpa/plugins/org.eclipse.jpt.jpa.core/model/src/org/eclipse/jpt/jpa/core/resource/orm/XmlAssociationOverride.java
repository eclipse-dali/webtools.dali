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
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlAssociationOverride_2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlAssociationOverride_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Association Override Impl</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlAssociationOverride()
 * @model kind="class"
 * @generated
 */
public class XmlAssociationOverride extends EBaseObjectImpl implements XmlOverride, XmlJoinColumnContainer, XmlAssociationOverride_2_0, XmlAssociationOverride_2_1
{
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

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
	 * The cached value of the '{@link #getJoinTable() <em>Join Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinTable()
	 * @generated
	 * @ordered
	 */
	protected XmlJoinTable joinTable;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

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
	protected XmlAssociationOverride()
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
		return OrmPackage.Literals.XML_ASSOCIATION_OVERRIDE;
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
			joinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS);
		}
		return joinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlAssociationOverride_2_0_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	public void setDescription(String newDescription)
	{
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ASSOCIATION_OVERRIDE__DESCRIPTION, oldDescription, description));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlAssociationOverride_2_1_ForeignKey()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ASSOCIATION_OVERRIDE__FOREIGN_KEY, oldForeignKey, newForeignKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride#getForeignKey <em>Foreign Key</em>}' containment reference.
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
				msgs = ((InternalEObject)foreignKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ASSOCIATION_OVERRIDE__FOREIGN_KEY, null, msgs);
			if (newForeignKey != null)
				msgs = ((InternalEObject)newForeignKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ASSOCIATION_OVERRIDE__FOREIGN_KEY, null, msgs);
			msgs = basicSetForeignKey(newForeignKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ASSOCIATION_OVERRIDE__FOREIGN_KEY, newForeignKey, newForeignKey));
	}

	/**
	 * Returns the value of the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Table</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Table</em>' containment reference.
	 * @see #setJoinTable(XmlJoinTable)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlJoinTableContainer_JoinTable()
	 * @model containment="true"
	 * @generated
	 */
	public XmlJoinTable getJoinTable()
	{
		return joinTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJoinTable(XmlJoinTable newJoinTable, NotificationChain msgs)
	{
		XmlJoinTable oldJoinTable = joinTable;
		joinTable = newJoinTable;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_TABLE, oldJoinTable, newJoinTable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride#getJoinTable <em>Join Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Join Table</em>' containment reference.
	 * @see #getJoinTable()
	 * @generated
	 */
	public void setJoinTable(XmlJoinTable newJoinTable)
	{
		if (newJoinTable != joinTable)
		{
			NotificationChain msgs = null;
			if (joinTable != null)
				msgs = ((InternalEObject)joinTable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_TABLE, null, msgs);
			if (newJoinTable != null)
				msgs = ((InternalEObject)newJoinTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_TABLE, null, msgs);
			msgs = basicSetJoinTable(newJoinTable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_TABLE, newJoinTable, newJoinTable));
	}

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlOverride_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	public void setName(String newName)
	{
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ASSOCIATION_OVERRIDE__NAME, oldName, name));
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
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS:
				return ((InternalEList<?>)getJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_TABLE:
				return basicSetJoinTable(null, msgs);
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__FOREIGN_KEY:
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
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__NAME:
				return getName();
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS:
				return getJoinColumns();
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_TABLE:
				return getJoinTable();
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__DESCRIPTION:
				return getDescription();
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__FOREIGN_KEY:
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
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__NAME:
				setName((String)newValue);
				return;
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS:
				getJoinColumns().clear();
				getJoinColumns().addAll((Collection<? extends XmlJoinColumn>)newValue);
				return;
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_TABLE:
				setJoinTable((XmlJoinTable)newValue);
				return;
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__FOREIGN_KEY:
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
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS:
				getJoinColumns().clear();
				return;
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_TABLE:
				setJoinTable((XmlJoinTable)null);
				return;
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__FOREIGN_KEY:
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
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS:
				return joinColumns != null && !joinColumns.isEmpty();
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_TABLE:
				return joinTable != null;
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case OrmPackage.XML_ASSOCIATION_OVERRIDE__FOREIGN_KEY:
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
				case OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS: return OrmPackage.XML_JOIN_COLUMN_CONTAINER__JOIN_COLUMNS;
				default: return -1;
			}
		}
		if (baseClass == XmlJoinTableContainer.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_TABLE: return OrmPackage.XML_JOIN_TABLE_CONTAINER__JOIN_TABLE;
				default: return -1;
			}
		}
		if (baseClass == XmlAssociationOverride_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ASSOCIATION_OVERRIDE__DESCRIPTION: return OrmV2_0Package.XML_ASSOCIATION_OVERRIDE_20__DESCRIPTION;
				default: return -1;
			}
		}
		if (baseClass == XmlAssociationOverride_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ASSOCIATION_OVERRIDE__FOREIGN_KEY: return OrmV2_1Package.XML_ASSOCIATION_OVERRIDE_21__FOREIGN_KEY;
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
				case OrmPackage.XML_JOIN_COLUMN_CONTAINER__JOIN_COLUMNS: return OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_COLUMNS;
				default: return -1;
			}
		}
		if (baseClass == XmlJoinTableContainer.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_JOIN_TABLE_CONTAINER__JOIN_TABLE: return OrmPackage.XML_ASSOCIATION_OVERRIDE__JOIN_TABLE;
				default: return -1;
			}
		}
		if (baseClass == XmlAssociationOverride_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_ASSOCIATION_OVERRIDE_20__DESCRIPTION: return OrmPackage.XML_ASSOCIATION_OVERRIDE__DESCRIPTION;
				default: return -1;
			}
		}
		if (baseClass == XmlAssociationOverride_2_1.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_1Package.XML_ASSOCIATION_OVERRIDE_21__FOREIGN_KEY: return OrmPackage.XML_ASSOCIATION_OVERRIDE__FOREIGN_KEY;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", description: ");
		result.append(description);
		result.append(')');
		return result.toString();
	}
	
	public TextRange getNameTextRange() {
		return getAttributeTextRange(JPA.NAME);
	}


	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildDescriptionTranslator(),
			buildJoinColumnTranslator(),
			buildForeignKeyTranslator(),
			buildJoinTableTranslator()
		};
	}
	
	protected static Translator buildNameTranslator() {
		return new Translator(JPA.NAME, OrmPackage.eINSTANCE.getXmlOverride_Name(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildDescriptionTranslator() {
		return new Translator(JPA.DESCRIPTION, OrmV2_0Package.eINSTANCE.getXmlAssociationOverride_2_0_Description());
	}
	
	protected static Translator buildJoinColumnTranslator() {
		return XmlJoinColumn.buildTranslator(JPA.JOIN_COLUMN, OrmPackage.eINSTANCE.getXmlJoinColumnContainer_JoinColumns());
	}	
	
	protected static Translator buildJoinTableTranslator() {
		return XmlJoinTable.buildTranslator(JPA.JOIN_TABLE, OrmPackage.eINSTANCE.getXmlJoinTableContainer_JoinTable());
	}
	
	protected static Translator buildForeignKeyTranslator() {
		return XmlForeignKey.buildTranslator(JPA2_1.FOREIGN_KEY, OrmV2_1Package.eINSTANCE.getXmlAssociationOverride_2_1_ForeignKey());
	}	

	// ************ content assist ***********
	public TextRange getNameTextCodeAssistRange() {
		return getAttributeCodeAssistTextRange(JPA.NAME);
	}
	
	public boolean nameTouches(int pos) {
		TextRange textRange = this.getNameTextCodeAssistRange();
		return (textRange != null) && (textRange.touches(pos));
	}
}
