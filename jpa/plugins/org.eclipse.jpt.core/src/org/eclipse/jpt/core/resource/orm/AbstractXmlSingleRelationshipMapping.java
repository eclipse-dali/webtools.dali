/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.resource.orm.v2_0.JPA2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlDerivedId_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlMapsId_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.common.internal.emf.resource.Translator;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Single Relationship Mapping</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.core.resource.orm.AbstractXmlSingleRelationshipMapping#getOptional <em>Optional</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlSingleRelationshipMapping()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class AbstractXmlSingleRelationshipMapping extends AbstractXmlRelationshipMapping implements XmlJoinTableMapping, XmlJoinColumnsMapping, XmlSingleRelationshipMapping_2_0
{
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
	 * The cached value of the '{@link #getJoinColumns() <em>Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlJoinColumn> joinColumns;
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean ID_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected Boolean id = ID_EDEFAULT;
	/**
	 * The default value of the '{@link #getMapsId() <em>Maps Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapsId()
	 * @generated
	 * @ordered
	 */
	protected static final String MAPS_ID_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getMapsId() <em>Maps Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapsId()
	 * @generated
	 * @ordered
	 */
	protected String mapsId = MAPS_ID_EDEFAULT;
	/**
	 * The default value of the '{@link #getOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOptional()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean OPTIONAL_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOptional()
	 * @generated
	 * @ordered
	 */
	protected Boolean optional = OPTIONAL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractXmlSingleRelationshipMapping()
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
		return OrmPackage.Literals.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING;
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinTableMapping_JoinTable()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE, oldJoinTable, newJoinTable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlSingleRelationshipMapping#getJoinTable <em>Join Table</em>}' containment reference.
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
				msgs = ((InternalEObject)joinTable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE, null, msgs);
			if (newJoinTable != null)
				msgs = ((InternalEObject)newJoinTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE, null, msgs);
			msgs = basicSetJoinTable(newJoinTable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE, newJoinTable, newJoinTable));
	}

	/**
	 * Returns the value of the '<em><b>Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlJoinColumnsMapping_JoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlJoinColumn> getJoinColumns()
	{
		if (joinColumns == null)
		{
			joinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS);
		}
		return joinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(Boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlDerivedId_2_0_Id()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getId()
	{
		return id;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlSingleRelationshipMapping#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	public void setId(Boolean newId)
	{
		Boolean oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__ID, oldId, id));
	}

	/**
	 * Returns the value of the '<em><b>Maps Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Maps Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Maps Id</em>' attribute.
	 * @see #setMapsId(String)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMapsId_2_0_MapsId()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getMapsId()
	{
		return mapsId;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlSingleRelationshipMapping#getMapsId <em>Maps Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Maps Id</em>' attribute.
	 * @see #getMapsId()
	 * @generated
	 */
	public void setMapsId(String newMapsId)
	{
		String oldMapsId = mapsId;
		mapsId = newMapsId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__MAPS_ID, oldMapsId, mapsId));
	}

	/**
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see #setOptional(Boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getAbstractXmlSingleRelationshipMapping_Optional()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getOptional() {
		return optional;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlSingleRelationshipMapping#getOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional</em>' attribute.
	 * @see #getOptional()
	 * @generated
	 */
	public void setOptional(Boolean newOptional) {
		Boolean oldOptional = optional;
		optional = newOptional;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL, oldOptional, optional));
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
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE:
				return basicSetJoinTable(null, msgs);
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS:
				return ((InternalEList<?>)getJoinColumns()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE:
				return getJoinTable();
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS:
				return getJoinColumns();
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__ID:
				return getId();
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__MAPS_ID:
				return getMapsId();
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL:
				return getOptional();
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
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE:
				setJoinTable((XmlJoinTable)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS:
				getJoinColumns().clear();
				getJoinColumns().addAll((Collection<? extends XmlJoinColumn>)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__ID:
				setId((Boolean)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__MAPS_ID:
				setMapsId((String)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL:
				setOptional((Boolean)newValue);
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
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE:
				setJoinTable((XmlJoinTable)null);
				return;
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS:
				getJoinColumns().clear();
				return;
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__ID:
				setId(ID_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__MAPS_ID:
				setMapsId(MAPS_ID_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL:
				setOptional(OPTIONAL_EDEFAULT);
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
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE:
				return joinTable != null;
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS:
				return joinColumns != null && !joinColumns.isEmpty();
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__MAPS_ID:
				return MAPS_ID_EDEFAULT == null ? mapsId != null : !MAPS_ID_EDEFAULT.equals(mapsId);
			case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__OPTIONAL:
				return OPTIONAL_EDEFAULT == null ? optional != null : !OPTIONAL_EDEFAULT.equals(optional);
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
		if (baseClass == XmlJoinTableMapping.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE: return OrmPackage.XML_JOIN_TABLE_MAPPING__JOIN_TABLE;
				default: return -1;
			}
		}
		if (baseClass == XmlJoinColumnsMapping.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS: return OrmPackage.XML_JOIN_COLUMNS_MAPPING__JOIN_COLUMNS;
				default: return -1;
			}
		}
		if (baseClass == XmlDerivedId_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__ID: return OrmV2_0Package.XML_DERIVED_ID_20__ID;
				default: return -1;
			}
		}
		if (baseClass == XmlMapsId_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__MAPS_ID: return OrmV2_0Package.XML_MAPS_ID_20__MAPS_ID;
				default: return -1;
			}
		}
		if (baseClass == XmlSingleRelationshipMapping_2_0.class)
		{
			switch (derivedFeatureID)
			{
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
		if (baseClass == XmlJoinTableMapping.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_JOIN_TABLE_MAPPING__JOIN_TABLE: return OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_TABLE;
				default: return -1;
			}
		}
		if (baseClass == XmlJoinColumnsMapping.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_JOIN_COLUMNS_MAPPING__JOIN_COLUMNS: return OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__JOIN_COLUMNS;
				default: return -1;
			}
		}
		if (baseClass == XmlDerivedId_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_DERIVED_ID_20__ID: return OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__ID;
				default: return -1;
			}
		}
		if (baseClass == XmlMapsId_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_MAPS_ID_20__MAPS_ID: return OrmPackage.ABSTRACT_XML_SINGLE_RELATIONSHIP_MAPPING__MAPS_ID;
				default: return -1;
			}
		}
		if (baseClass == XmlSingleRelationshipMapping_2_0.class)
		{
			switch (baseFeatureID)
			{
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
		result.append(" (id: ");
		result.append(id);
		result.append(", mapsId: ");
		result.append(mapsId);
		result.append(", optional: ");
		result.append(optional);
		result.append(')');
		return result.toString();
	}
	
	
	// ********** validation ***********
	
	public TextRange getMapsIdTextRange() {
		return getAttributeTextRange(JPA2_0.MAPS_ID);
	}
	
	public TextRange getIdTextRange() {
		return getAttributeTextRange(JPA2_0.ID);
	}
	
	
	// ********** translators **********
	
	protected static Translator buildOptionalTranslator() {
		return new Translator(JPA.OPTIONAL, OrmPackage.eINSTANCE.getAbstractXmlSingleRelationshipMapping_Optional(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildMapsIdTranslator() {
		return new Translator(JPA2_0.MAPS_ID, OrmV2_0Package.eINSTANCE.getXmlMapsId_2_0_MapsId(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildIdTranslator() {
		return new Translator(JPA2_0.ID, OrmV2_0Package.eINSTANCE.getXmlDerivedId_2_0_Id(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildJoinColumnTranslator() {
		return XmlJoinColumn.buildTranslator(JPA.JOIN_COLUMN, OrmPackage.eINSTANCE.getXmlJoinColumnsMapping_JoinColumns());
	}
	
	protected static Translator buildJoinTableTranslator() {
		return XmlJoinTable.buildTranslator(JPA.JOIN_TABLE, OrmPackage.eINSTANCE.getXmlJoinTableMapping_JoinTable());
	}
}
