/*******************************************************************************
 *  Copyright (c) 2009, 2013 Oracle. 
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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.translators.EmptyTagBooleanTranslator;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlElementCollection_2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMapKeyAttributeOverrideContainer_2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMapKeyConvertibleMapping_2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlOrderable_2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvert_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConvertibleMapping_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlElementCollection_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlForeignKey_2_1;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Element Collection</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlElementCollection()
 * @model kind="class"
 * @generated
 */
public class XmlElementCollection extends AbstractXmlAttributeMapping implements XmlElementCollection_2_0, XmlElementCollection_2_1
{
	/**
	 * The cached value of the '{@link #getConvert() <em>Convert</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConvert()
	 * @generated
	 * @ordered
	 */
	protected XmlConvert convert;

	/**
	 * The default value of the '{@link #isLob() <em>Lob</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLob()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LOB_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLob() <em>Lob</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLob()
	 * @generated
	 * @ordered
	 */
	protected boolean lob = LOB_EDEFAULT;

	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final TemporalType TEMPORAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTemporal() <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemporal()
	 * @generated
	 * @ordered
	 */
	protected TemporalType temporal = TEMPORAL_EDEFAULT;

	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final EnumType ENUMERATED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEnumerated() <em>Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnumerated()
	 * @generated
	 * @ordered
	 */
	protected EnumType enumerated = ENUMERATED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOrderColumn() <em>Order Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrderColumn()
	 * @generated
	 * @ordered
	 */
	protected XmlOrderColumn orderColumn;

	/**
	 * The cached value of the '{@link #getOrderBy() <em>Order By</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrderBy()
	 * @generated
	 * @ordered
	 */
	protected XmlOrderBy orderBy;

	/**
	 * The cached value of the '{@link #getAttributeOverrides() <em>Attribute Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlAttributeOverride> attributeOverrides;

	/**
	 * The cached value of the '{@link #getAssociationOverrides() <em>Association Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssociationOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlAssociationOverride> associationOverrides;

	/**
	 * The cached value of the '{@link #getMapKeyAttributeOverrides() <em>Map Key Attribute Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyAttributeOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlAttributeOverride> mapKeyAttributeOverrides;

	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final TemporalType MAP_KEY_TEMPORAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMapKeyTemporal() <em>Map Key Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyTemporal()
	 * @generated
	 * @ordered
	 */
	protected TemporalType mapKeyTemporal = MAP_KEY_TEMPORAL_EDEFAULT;

	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final EnumType MAP_KEY_ENUMERATED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMapKeyEnumerated() <em>Map Key Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyEnumerated()
	 * @generated
	 * @ordered
	 */
	protected EnumType mapKeyEnumerated = MAP_KEY_ENUMERATED_EDEFAULT;

	/**
	 * The default value of the '{@link #getTargetClass() <em>Target Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetClass()
	 * @generated
	 * @ordered
	 */
	protected static final String TARGET_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTargetClass() <em>Target Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetClass()
	 * @generated
	 * @ordered
	 */
	protected String targetClass = TARGET_CLASS_EDEFAULT;

	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final FetchType FETCH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFetch() <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected FetchType fetch = FETCH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMapKey() <em>Map Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKey()
	 * @generated
	 * @ordered
	 */
	protected MapKey mapKey;

	/**
	 * The cached value of the '{@link #getMapKeyClass() <em>Map Key Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyClass()
	 * @generated
	 * @ordered
	 */
	protected XmlClassReference mapKeyClass;

	/**
	 * The cached value of the '{@link #getMapKeyColumn() <em>Map Key Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyColumn()
	 * @generated
	 * @ordered
	 */
	protected XmlColumn mapKeyColumn;

	/**
	 * The cached value of the '{@link #getMapKeyJoinColumns() <em>Map Key Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlJoinColumn> mapKeyJoinColumns;

	/**
	 * The cached value of the '{@link #getColumn() <em>Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumn()
	 * @generated
	 * @ordered
	 */
	protected XmlColumn column;

	/**
	 * The cached value of the '{@link #getCollectionTable() <em>Collection Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCollectionTable()
	 * @generated
	 * @ordered
	 */
	protected XmlCollectionTable collectionTable;

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
	 * The cached value of the '{@link #getConverts() <em>Converts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConverts()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlConvert> converts;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlElementCollection()
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
		return OrmPackage.Literals.XML_ELEMENT_COLLECTION;
	}

	/**
	 * Returns the value of the '<em><b>Convert</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Convert</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Convert</em>' containment reference.
	 * @see #setConvert(XmlConvert)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlConvertibleMapping_2_1_Convert()
	 * @model containment="true"
	 * @generated
	 */
	public XmlConvert getConvert()
	{
		return convert;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConvert(XmlConvert newConvert, NotificationChain msgs)
	{
		XmlConvert oldConvert = convert;
		convert = newConvert;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__CONVERT, oldConvert, newConvert);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection#getConvert <em>Convert</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Convert</em>' containment reference.
	 * @see #getConvert()
	 * @generated
	 */
	public void setConvert(XmlConvert newConvert)
	{
		if (newConvert != convert)
		{
			NotificationChain msgs = null;
			if (convert != null)
				msgs = ((InternalEObject)convert).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__CONVERT, null, msgs);
			if (newConvert != null)
				msgs = ((InternalEObject)newConvert).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__CONVERT, null, msgs);
			msgs = basicSetConvert(newConvert, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__CONVERT, newConvert, newConvert));
	}

	/**
	 * Returns the value of the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lob</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lob</em>' attribute.
	 * @see #setLob(boolean)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlConvertibleMapping_Lob()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isLob()
	{
		return lob;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection#isLob <em>Lob</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lob</em>' attribute.
	 * @see #isLob()
	 * @generated
	 */
	public void setLob(boolean newLob)
	{
		boolean oldLob = lob;
		lob = newLob;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__LOB, oldLob, lob));
	}

	/**
	 * Returns the value of the '<em><b>Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.core.resource.orm.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temporal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.TemporalType
	 * @see #setTemporal(TemporalType)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlConvertibleMapping_Temporal()
	 * @model
	 * @generated
	 */
	public TemporalType getTemporal()
	{
		return temporal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection#getTemporal <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.TemporalType
	 * @see #getTemporal()
	 * @generated
	 */
	public void setTemporal(TemporalType newTemporal)
	{
		TemporalType oldTemporal = temporal;
		temporal = newTemporal == null ? TEMPORAL_EDEFAULT : newTemporal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__TEMPORAL, oldTemporal, temporal));
	}

	/**
	 * Returns the value of the '<em><b>Enumerated</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.core.resource.orm.EnumType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enumerated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.EnumType
	 * @see #setEnumerated(EnumType)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlConvertibleMapping_Enumerated()
	 * @model
	 * @generated
	 */
	public EnumType getEnumerated()
	{
		return enumerated;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection#getEnumerated <em>Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.EnumType
	 * @see #getEnumerated()
	 * @generated
	 */
	public void setEnumerated(EnumType newEnumerated)
	{
		EnumType oldEnumerated = enumerated;
		enumerated = newEnumerated == null ? ENUMERATED_EDEFAULT : newEnumerated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__ENUMERATED, oldEnumerated, enumerated));
	}

	/**
	 * Returns the value of the '<em><b>Target Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Class</em>' attribute.
	 * @see #setTargetClass(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlElementCollection_2_0_TargetClass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getTargetClass()
	{
		return targetClass;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection#getTargetClass <em>Target Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Class</em>' attribute.
	 * @see #getTargetClass()
	 * @generated
	 */
	public void setTargetClass(String newTargetClass)
	{
		String oldTargetClass = targetClass;
		targetClass = newTargetClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__TARGET_CLASS, oldTargetClass, targetClass));
	}

	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.core.resource.orm.FetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.FetchType
	 * @see #setFetch(FetchType)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlElementCollection_2_0_Fetch()
	 * @model
	 * @generated
	 */
	public FetchType getFetch()
	{
		return fetch;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection#getFetch <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.FetchType
	 * @see #getFetch()
	 * @generated
	 */
	public void setFetch(FetchType newFetch)
	{
		FetchType oldFetch = fetch;
		fetch = newFetch == null ? FETCH_EDEFAULT : newFetch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__FETCH, oldFetch, fetch));
	}

	/**
	 * Returns the value of the '<em><b>Order By</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Order By</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Order By</em>' containment reference.
	 * @see #setOrderBy(XmlOrderBy)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlOrderable_OrderBy()
	 * @model containment="true"
	 * @generated
	 */
	public XmlOrderBy getOrderBy()
	{
		return orderBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOrderBy(XmlOrderBy newOrderBy, NotificationChain msgs)
	{
		XmlOrderBy oldOrderBy = orderBy;
		orderBy = newOrderBy;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__ORDER_BY, oldOrderBy, newOrderBy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection#getOrderBy <em>Order By</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Order By</em>' containment reference.
	 * @see #getOrderBy()
	 * @generated
	 */
	public void setOrderBy(XmlOrderBy newOrderBy)
	{
		if (newOrderBy != orderBy)
		{
			NotificationChain msgs = null;
			if (orderBy != null)
				msgs = ((InternalEObject)orderBy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__ORDER_BY, null, msgs);
			if (newOrderBy != null)
				msgs = ((InternalEObject)newOrderBy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__ORDER_BY, null, msgs);
			msgs = basicSetOrderBy(newOrderBy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__ORDER_BY, newOrderBy, newOrderBy));
	}

	/**
	 * Returns the value of the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlAttributeOverrideContainer_AttributeOverrides()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlAttributeOverride> getAttributeOverrides()
	{
		if (attributeOverrides == null)
		{
			attributeOverrides = new EObjectContainmentEList<XmlAttributeOverride>(XmlAttributeOverride.class, this, OrmPackage.XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES);
		}
		return attributeOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Association Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Association Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Association Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlAssociationOverrideContainer_AssociationOverrides()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlAssociationOverride> getAssociationOverrides()
	{
		if (associationOverrides == null)
		{
			associationOverrides = new EObjectContainmentEList<XmlAssociationOverride>(XmlAssociationOverride.class, this, OrmPackage.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES);
		}
		return associationOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Attribute Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlMapKeyAttributeOverrideContainer_2_0_MapKeyAttributeOverrides()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlAttributeOverride> getMapKeyAttributeOverrides()
	{
		if (mapKeyAttributeOverrides == null)
		{
			mapKeyAttributeOverrides = new EObjectContainmentEList<XmlAttributeOverride>(XmlAttributeOverride.class, this, OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES);
		}
		return mapKeyAttributeOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Order Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Order Column</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Order Column</em>' containment reference.
	 * @see #setOrderColumn(XmlOrderColumn)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlOrderable_2_0_OrderColumn()
	 * @model containment="true"
	 * @generated
	 */
	public XmlOrderColumn getOrderColumn()
	{
		return orderColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOrderColumn(XmlOrderColumn newOrderColumn, NotificationChain msgs)
	{
		XmlOrderColumn oldOrderColumn = orderColumn;
		orderColumn = newOrderColumn;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__ORDER_COLUMN, oldOrderColumn, newOrderColumn);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection#getOrderColumn <em>Order Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Order Column</em>' containment reference.
	 * @see #getOrderColumn()
	 * @generated
	 */
	public void setOrderColumn(XmlOrderColumn newOrderColumn)
	{
		if (newOrderColumn != orderColumn)
		{
			NotificationChain msgs = null;
			if (orderColumn != null)
				msgs = ((InternalEObject)orderColumn).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__ORDER_COLUMN, null, msgs);
			if (newOrderColumn != null)
				msgs = ((InternalEObject)newOrderColumn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__ORDER_COLUMN, null, msgs);
			msgs = basicSetOrderColumn(newOrderColumn, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__ORDER_COLUMN, newOrderColumn, newOrderColumn));
	}

	/**
	 * Returns the value of the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key</em>' containment reference.
	 * @see #setMapKey(MapKey)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlElementCollection_2_0_MapKey()
	 * @model containment="true"
	 * @generated
	 */
	public MapKey getMapKey()
	{
		return mapKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapKey(MapKey newMapKey, NotificationChain msgs)
	{
		MapKey oldMapKey = mapKey;
		mapKey = newMapKey;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY, oldMapKey, newMapKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection#getMapKey <em>Map Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key</em>' containment reference.
	 * @see #getMapKey()
	 * @generated
	 */
	public void setMapKey(MapKey newMapKey)
	{
		if (newMapKey != mapKey)
		{
			NotificationChain msgs = null;
			if (mapKey != null)
				msgs = ((InternalEObject)mapKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY, null, msgs);
			if (newMapKey != null)
				msgs = ((InternalEObject)newMapKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY, null, msgs);
			msgs = basicSetMapKey(newMapKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY, newMapKey, newMapKey));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Class</em>' containment reference.
	 * @see #setMapKeyClass(XmlClassReference)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlElementCollection_2_0_MapKeyClass()
	 * @model containment="true"
	 * @generated
	 */
	public XmlClassReference getMapKeyClass()
	{
		return mapKeyClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapKeyClass(XmlClassReference newMapKeyClass, NotificationChain msgs)
	{
		XmlClassReference oldMapKeyClass = mapKeyClass;
		mapKeyClass = newMapKeyClass;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS, oldMapKeyClass, newMapKeyClass);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection#getMapKeyClass <em>Map Key Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Class</em>' containment reference.
	 * @see #getMapKeyClass()
	 * @generated
	 */
	public void setMapKeyClass(XmlClassReference newMapKeyClass)
	{
		if (newMapKeyClass != mapKeyClass)
		{
			NotificationChain msgs = null;
			if (mapKeyClass != null)
				msgs = ((InternalEObject)mapKeyClass).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS, null, msgs);
			if (newMapKeyClass != null)
				msgs = ((InternalEObject)newMapKeyClass).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS, null, msgs);
			msgs = basicSetMapKeyClass(newMapKeyClass, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS, newMapKeyClass, newMapKeyClass));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.core.resource.orm.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Temporal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Temporal</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.TemporalType
	 * @see #setMapKeyTemporal(TemporalType)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlMapKeyConvertibleMapping_2_0_MapKeyTemporal()
	 * @model
	 * @generated
	 */
	public TemporalType getMapKeyTemporal()
	{
		return mapKeyTemporal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection#getMapKeyTemporal <em>Map Key Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Temporal</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.TemporalType
	 * @see #getMapKeyTemporal()
	 * @generated
	 */
	public void setMapKeyTemporal(TemporalType newMapKeyTemporal)
	{
		TemporalType oldMapKeyTemporal = mapKeyTemporal;
		mapKeyTemporal = newMapKeyTemporal == null ? MAP_KEY_TEMPORAL_EDEFAULT : newMapKeyTemporal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL, oldMapKeyTemporal, mapKeyTemporal));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.core.resource.orm.EnumType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Enumerated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.EnumType
	 * @see #setMapKeyEnumerated(EnumType)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlMapKeyConvertibleMapping_2_0_MapKeyEnumerated()
	 * @model
	 * @generated
	 */
	public EnumType getMapKeyEnumerated()
	{
		return mapKeyEnumerated;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection#getMapKeyEnumerated <em>Map Key Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.EnumType
	 * @see #getMapKeyEnumerated()
	 * @generated
	 */
	public void setMapKeyEnumerated(EnumType newMapKeyEnumerated)
	{
		EnumType oldMapKeyEnumerated = mapKeyEnumerated;
		mapKeyEnumerated = newMapKeyEnumerated == null ? MAP_KEY_ENUMERATED_EDEFAULT : newMapKeyEnumerated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED, oldMapKeyEnumerated, mapKeyEnumerated));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Column</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Column</em>' containment reference.
	 * @see #setMapKeyColumn(XmlColumn)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlElementCollection_2_0_MapKeyColumn()
	 * @model containment="true"
	 * @generated
	 */
	public XmlColumn getMapKeyColumn()
	{
		return mapKeyColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapKeyColumn(XmlColumn newMapKeyColumn, NotificationChain msgs)
	{
		XmlColumn oldMapKeyColumn = mapKeyColumn;
		mapKeyColumn = newMapKeyColumn;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN, oldMapKeyColumn, newMapKeyColumn);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection#getMapKeyColumn <em>Map Key Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Column</em>' containment reference.
	 * @see #getMapKeyColumn()
	 * @generated
	 */
	public void setMapKeyColumn(XmlColumn newMapKeyColumn)
	{
		if (newMapKeyColumn != mapKeyColumn)
		{
			NotificationChain msgs = null;
			if (mapKeyColumn != null)
				msgs = ((InternalEObject)mapKeyColumn).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN, null, msgs);
			if (newMapKeyColumn != null)
				msgs = ((InternalEObject)newMapKeyColumn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN, null, msgs);
			msgs = basicSetMapKeyColumn(newMapKeyColumn, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN, newMapKeyColumn, newMapKeyColumn));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlElementCollection_2_0_MapKeyJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlJoinColumn> getMapKeyJoinColumns()
	{
		if (mapKeyJoinColumns == null)
		{
			mapKeyJoinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS);
		}
		return mapKeyJoinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column</em>' containment reference.
	 * @see #setColumn(XmlColumn)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlElementCollection_2_0_Column()
	 * @model containment="true"
	 * @generated
	 */
	public XmlColumn getColumn()
	{
		return column;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetColumn(XmlColumn newColumn, NotificationChain msgs)
	{
		XmlColumn oldColumn = column;
		column = newColumn;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__COLUMN, oldColumn, newColumn);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection#getColumn <em>Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Column</em>' containment reference.
	 * @see #getColumn()
	 * @generated
	 */
	public void setColumn(XmlColumn newColumn)
	{
		if (newColumn != column)
		{
			NotificationChain msgs = null;
			if (column != null)
				msgs = ((InternalEObject)column).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__COLUMN, null, msgs);
			if (newColumn != null)
				msgs = ((InternalEObject)newColumn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__COLUMN, null, msgs);
			msgs = basicSetColumn(newColumn, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__COLUMN, newColumn, newColumn));
	}

	/**
	 * Returns the value of the '<em><b>Collection Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Collection Table</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Collection Table</em>' containment reference.
	 * @see #setCollectionTable(XmlCollectionTable)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlElementCollection_2_0_CollectionTable()
	 * @model containment="true"
	 * @generated
	 */
	public XmlCollectionTable getCollectionTable()
	{
		return collectionTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCollectionTable(XmlCollectionTable newCollectionTable, NotificationChain msgs)
	{
		XmlCollectionTable oldCollectionTable = collectionTable;
		collectionTable = newCollectionTable;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__COLLECTION_TABLE, oldCollectionTable, newCollectionTable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection#getCollectionTable <em>Collection Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Collection Table</em>' containment reference.
	 * @see #getCollectionTable()
	 * @generated
	 */
	public void setCollectionTable(XmlCollectionTable newCollectionTable)
	{
		if (newCollectionTable != collectionTable)
		{
			NotificationChain msgs = null;
			if (collectionTable != null)
				msgs = ((InternalEObject)collectionTable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__COLLECTION_TABLE, null, msgs);
			if (newCollectionTable != null)
				msgs = ((InternalEObject)newCollectionTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__COLLECTION_TABLE, null, msgs);
			msgs = basicSetCollectionTable(newCollectionTable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__COLLECTION_TABLE, newCollectionTable, newCollectionTable));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlElementCollection_2_1_MapKeyConverts()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlConvert> getMapKeyConverts()
	{
		if (mapKeyConverts == null)
		{
			mapKeyConverts = new EObjectContainmentEList<XmlConvert>(XmlConvert.class, this, OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CONVERTS);
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlElementCollection_2_1_MapKeyForeignKey()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_FOREIGN_KEY, oldMapKeyForeignKey, newMapKeyForeignKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlElementCollection#getMapKeyForeignKey <em>Map Key Foreign Key</em>}' containment reference.
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
				msgs = ((InternalEObject)mapKeyForeignKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_FOREIGN_KEY, null, msgs);
			if (newMapKeyForeignKey != null)
				msgs = ((InternalEObject)newMapKeyForeignKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_FOREIGN_KEY, null, msgs);
			msgs = basicSetMapKeyForeignKey(newMapKeyForeignKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_FOREIGN_KEY, newMapKeyForeignKey, newMapKeyForeignKey));
	}

	/**
	 * Returns the value of the '<em><b>Converts</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlConvert}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Converts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Converts</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlElementCollection_2_1_Converts()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlConvert> getConverts()
	{
		if (converts == null)
		{
			converts = new EObjectContainmentEList<XmlConvert>(XmlConvert.class, this, OrmPackage.XML_ELEMENT_COLLECTION__CONVERTS);
		}
		return converts;
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
			case OrmPackage.XML_ELEMENT_COLLECTION__CONVERT:
				return basicSetConvert(null, msgs);
			case OrmPackage.XML_ELEMENT_COLLECTION__ORDER_COLUMN:
				return basicSetOrderColumn(null, msgs);
			case OrmPackage.XML_ELEMENT_COLLECTION__ORDER_BY:
				return basicSetOrderBy(null, msgs);
			case OrmPackage.XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES:
				return ((InternalEList<?>)getAttributeOverrides()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES:
				return ((InternalEList<?>)getAssociationOverrides()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES:
				return ((InternalEList<?>)getMapKeyAttributeOverrides()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY:
				return basicSetMapKey(null, msgs);
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS:
				return basicSetMapKeyClass(null, msgs);
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN:
				return basicSetMapKeyColumn(null, msgs);
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS:
				return ((InternalEList<?>)getMapKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ELEMENT_COLLECTION__COLUMN:
				return basicSetColumn(null, msgs);
			case OrmPackage.XML_ELEMENT_COLLECTION__COLLECTION_TABLE:
				return basicSetCollectionTable(null, msgs);
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CONVERTS:
				return ((InternalEList<?>)getMapKeyConverts()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_FOREIGN_KEY:
				return basicSetMapKeyForeignKey(null, msgs);
			case OrmPackage.XML_ELEMENT_COLLECTION__CONVERTS:
				return ((InternalEList<?>)getConverts()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.XML_ELEMENT_COLLECTION__CONVERT:
				return getConvert();
			case OrmPackage.XML_ELEMENT_COLLECTION__LOB:
				return isLob();
			case OrmPackage.XML_ELEMENT_COLLECTION__TEMPORAL:
				return getTemporal();
			case OrmPackage.XML_ELEMENT_COLLECTION__ENUMERATED:
				return getEnumerated();
			case OrmPackage.XML_ELEMENT_COLLECTION__ORDER_COLUMN:
				return getOrderColumn();
			case OrmPackage.XML_ELEMENT_COLLECTION__ORDER_BY:
				return getOrderBy();
			case OrmPackage.XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES:
				return getAttributeOverrides();
			case OrmPackage.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES:
				return getAssociationOverrides();
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES:
				return getMapKeyAttributeOverrides();
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL:
				return getMapKeyTemporal();
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED:
				return getMapKeyEnumerated();
			case OrmPackage.XML_ELEMENT_COLLECTION__TARGET_CLASS:
				return getTargetClass();
			case OrmPackage.XML_ELEMENT_COLLECTION__FETCH:
				return getFetch();
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY:
				return getMapKey();
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS:
				return getMapKeyClass();
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN:
				return getMapKeyColumn();
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS:
				return getMapKeyJoinColumns();
			case OrmPackage.XML_ELEMENT_COLLECTION__COLUMN:
				return getColumn();
			case OrmPackage.XML_ELEMENT_COLLECTION__COLLECTION_TABLE:
				return getCollectionTable();
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CONVERTS:
				return getMapKeyConverts();
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_FOREIGN_KEY:
				return getMapKeyForeignKey();
			case OrmPackage.XML_ELEMENT_COLLECTION__CONVERTS:
				return getConverts();
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
			case OrmPackage.XML_ELEMENT_COLLECTION__CONVERT:
				setConvert((XmlConvert)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__LOB:
				setLob((Boolean)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__TEMPORAL:
				setTemporal((TemporalType)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__ENUMERATED:
				setEnumerated((EnumType)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__ORDER_COLUMN:
				setOrderColumn((XmlOrderColumn)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__ORDER_BY:
				setOrderBy((XmlOrderBy)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES:
				getAttributeOverrides().clear();
				getAttributeOverrides().addAll((Collection<? extends XmlAttributeOverride>)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES:
				getAssociationOverrides().clear();
				getAssociationOverrides().addAll((Collection<? extends XmlAssociationOverride>)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES:
				getMapKeyAttributeOverrides().clear();
				getMapKeyAttributeOverrides().addAll((Collection<? extends XmlAttributeOverride>)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL:
				setMapKeyTemporal((TemporalType)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED:
				setMapKeyEnumerated((EnumType)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__TARGET_CLASS:
				setTargetClass((String)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__FETCH:
				setFetch((FetchType)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY:
				setMapKey((MapKey)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS:
				setMapKeyClass((XmlClassReference)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN:
				setMapKeyColumn((XmlColumn)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS:
				getMapKeyJoinColumns().clear();
				getMapKeyJoinColumns().addAll((Collection<? extends XmlJoinColumn>)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__COLUMN:
				setColumn((XmlColumn)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__COLLECTION_TABLE:
				setCollectionTable((XmlCollectionTable)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CONVERTS:
				getMapKeyConverts().clear();
				getMapKeyConverts().addAll((Collection<? extends XmlConvert>)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_FOREIGN_KEY:
				setMapKeyForeignKey((XmlForeignKey)newValue);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__CONVERTS:
				getConverts().clear();
				getConverts().addAll((Collection<? extends XmlConvert>)newValue);
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
			case OrmPackage.XML_ELEMENT_COLLECTION__CONVERT:
				setConvert((XmlConvert)null);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__LOB:
				setLob(LOB_EDEFAULT);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__TEMPORAL:
				setTemporal(TEMPORAL_EDEFAULT);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__ENUMERATED:
				setEnumerated(ENUMERATED_EDEFAULT);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__ORDER_COLUMN:
				setOrderColumn((XmlOrderColumn)null);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__ORDER_BY:
				setOrderBy((XmlOrderBy)null);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES:
				getAttributeOverrides().clear();
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES:
				getAssociationOverrides().clear();
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES:
				getMapKeyAttributeOverrides().clear();
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL:
				setMapKeyTemporal(MAP_KEY_TEMPORAL_EDEFAULT);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED:
				setMapKeyEnumerated(MAP_KEY_ENUMERATED_EDEFAULT);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__TARGET_CLASS:
				setTargetClass(TARGET_CLASS_EDEFAULT);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__FETCH:
				setFetch(FETCH_EDEFAULT);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY:
				setMapKey((MapKey)null);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS:
				setMapKeyClass((XmlClassReference)null);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN:
				setMapKeyColumn((XmlColumn)null);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS:
				getMapKeyJoinColumns().clear();
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__COLUMN:
				setColumn((XmlColumn)null);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__COLLECTION_TABLE:
				setCollectionTable((XmlCollectionTable)null);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CONVERTS:
				getMapKeyConverts().clear();
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_FOREIGN_KEY:
				setMapKeyForeignKey((XmlForeignKey)null);
				return;
			case OrmPackage.XML_ELEMENT_COLLECTION__CONVERTS:
				getConverts().clear();
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
			case OrmPackage.XML_ELEMENT_COLLECTION__CONVERT:
				return convert != null;
			case OrmPackage.XML_ELEMENT_COLLECTION__LOB:
				return lob != LOB_EDEFAULT;
			case OrmPackage.XML_ELEMENT_COLLECTION__TEMPORAL:
				return temporal != TEMPORAL_EDEFAULT;
			case OrmPackage.XML_ELEMENT_COLLECTION__ENUMERATED:
				return enumerated != ENUMERATED_EDEFAULT;
			case OrmPackage.XML_ELEMENT_COLLECTION__ORDER_COLUMN:
				return orderColumn != null;
			case OrmPackage.XML_ELEMENT_COLLECTION__ORDER_BY:
				return orderBy != null;
			case OrmPackage.XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES:
				return attributeOverrides != null && !attributeOverrides.isEmpty();
			case OrmPackage.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES:
				return associationOverrides != null && !associationOverrides.isEmpty();
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES:
				return mapKeyAttributeOverrides != null && !mapKeyAttributeOverrides.isEmpty();
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL:
				return mapKeyTemporal != MAP_KEY_TEMPORAL_EDEFAULT;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED:
				return mapKeyEnumerated != MAP_KEY_ENUMERATED_EDEFAULT;
			case OrmPackage.XML_ELEMENT_COLLECTION__TARGET_CLASS:
				return TARGET_CLASS_EDEFAULT == null ? targetClass != null : !TARGET_CLASS_EDEFAULT.equals(targetClass);
			case OrmPackage.XML_ELEMENT_COLLECTION__FETCH:
				return fetch != FETCH_EDEFAULT;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY:
				return mapKey != null;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS:
				return mapKeyClass != null;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN:
				return mapKeyColumn != null;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS:
				return mapKeyJoinColumns != null && !mapKeyJoinColumns.isEmpty();
			case OrmPackage.XML_ELEMENT_COLLECTION__COLUMN:
				return column != null;
			case OrmPackage.XML_ELEMENT_COLLECTION__COLLECTION_TABLE:
				return collectionTable != null;
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CONVERTS:
				return mapKeyConverts != null && !mapKeyConverts.isEmpty();
			case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_FOREIGN_KEY:
				return mapKeyForeignKey != null;
			case OrmPackage.XML_ELEMENT_COLLECTION__CONVERTS:
				return converts != null && !converts.isEmpty();
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
		if (baseClass == XmlConvertibleMapping_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ELEMENT_COLLECTION__CONVERT: return OrmV2_1Package.XML_CONVERTIBLE_MAPPING_21__CONVERT;
				default: return -1;
			}
		}
		if (baseClass == XmlConvertibleMapping.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ELEMENT_COLLECTION__LOB: return OrmPackage.XML_CONVERTIBLE_MAPPING__LOB;
				case OrmPackage.XML_ELEMENT_COLLECTION__TEMPORAL: return OrmPackage.XML_CONVERTIBLE_MAPPING__TEMPORAL;
				case OrmPackage.XML_ELEMENT_COLLECTION__ENUMERATED: return OrmPackage.XML_CONVERTIBLE_MAPPING__ENUMERATED;
				default: return -1;
			}
		}
		if (baseClass == XmlOrderable_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ELEMENT_COLLECTION__ORDER_COLUMN: return OrmV2_0Package.XML_ORDERABLE_20__ORDER_COLUMN;
				default: return -1;
			}
		}
		if (baseClass == XmlOrderable.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ELEMENT_COLLECTION__ORDER_BY: return OrmPackage.XML_ORDERABLE__ORDER_BY;
				default: return -1;
			}
		}
		if (baseClass == XmlAttributeOverrideContainer.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES: return OrmPackage.XML_ATTRIBUTE_OVERRIDE_CONTAINER__ATTRIBUTE_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlAssociationOverrideContainer.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES: return OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER__ASSOCIATION_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlMapKeyAttributeOverrideContainer_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES: return OrmV2_0Package.XML_MAP_KEY_ATTRIBUTE_OVERRIDE_CONTAINER_20__MAP_KEY_ATTRIBUTE_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlMapKeyConvertibleMapping_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL: return OrmV2_0Package.XML_MAP_KEY_CONVERTIBLE_MAPPING_20__MAP_KEY_TEMPORAL;
				case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED: return OrmV2_0Package.XML_MAP_KEY_CONVERTIBLE_MAPPING_20__MAP_KEY_ENUMERATED;
				default: return -1;
			}
		}
		if (baseClass == XmlElementCollection_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ELEMENT_COLLECTION__TARGET_CLASS: return OrmV2_0Package.XML_ELEMENT_COLLECTION_20__TARGET_CLASS;
				case OrmPackage.XML_ELEMENT_COLLECTION__FETCH: return OrmV2_0Package.XML_ELEMENT_COLLECTION_20__FETCH;
				case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY: return OrmV2_0Package.XML_ELEMENT_COLLECTION_20__MAP_KEY;
				case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS: return OrmV2_0Package.XML_ELEMENT_COLLECTION_20__MAP_KEY_CLASS;
				case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN: return OrmV2_0Package.XML_ELEMENT_COLLECTION_20__MAP_KEY_COLUMN;
				case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS: return OrmV2_0Package.XML_ELEMENT_COLLECTION_20__MAP_KEY_JOIN_COLUMNS;
				case OrmPackage.XML_ELEMENT_COLLECTION__COLUMN: return OrmV2_0Package.XML_ELEMENT_COLLECTION_20__COLUMN;
				case OrmPackage.XML_ELEMENT_COLLECTION__COLLECTION_TABLE: return OrmV2_0Package.XML_ELEMENT_COLLECTION_20__COLLECTION_TABLE;
				default: return -1;
			}
		}
		if (baseClass == XmlElementCollection_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CONVERTS: return OrmV2_1Package.XML_ELEMENT_COLLECTION_21__MAP_KEY_CONVERTS;
				case OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_FOREIGN_KEY: return OrmV2_1Package.XML_ELEMENT_COLLECTION_21__MAP_KEY_FOREIGN_KEY;
				case OrmPackage.XML_ELEMENT_COLLECTION__CONVERTS: return OrmV2_1Package.XML_ELEMENT_COLLECTION_21__CONVERTS;
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
		if (baseClass == XmlConvertibleMapping_2_1.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_1Package.XML_CONVERTIBLE_MAPPING_21__CONVERT: return OrmPackage.XML_ELEMENT_COLLECTION__CONVERT;
				default: return -1;
			}
		}
		if (baseClass == XmlConvertibleMapping.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_CONVERTIBLE_MAPPING__LOB: return OrmPackage.XML_ELEMENT_COLLECTION__LOB;
				case OrmPackage.XML_CONVERTIBLE_MAPPING__TEMPORAL: return OrmPackage.XML_ELEMENT_COLLECTION__TEMPORAL;
				case OrmPackage.XML_CONVERTIBLE_MAPPING__ENUMERATED: return OrmPackage.XML_ELEMENT_COLLECTION__ENUMERATED;
				default: return -1;
			}
		}
		if (baseClass == XmlOrderable_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_ORDERABLE_20__ORDER_COLUMN: return OrmPackage.XML_ELEMENT_COLLECTION__ORDER_COLUMN;
				default: return -1;
			}
		}
		if (baseClass == XmlOrderable.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_ORDERABLE__ORDER_BY: return OrmPackage.XML_ELEMENT_COLLECTION__ORDER_BY;
				default: return -1;
			}
		}
		if (baseClass == XmlAttributeOverrideContainer.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_ATTRIBUTE_OVERRIDE_CONTAINER__ATTRIBUTE_OVERRIDES: return OrmPackage.XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlAssociationOverrideContainer.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER__ASSOCIATION_OVERRIDES: return OrmPackage.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlMapKeyAttributeOverrideContainer_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_MAP_KEY_ATTRIBUTE_OVERRIDE_CONTAINER_20__MAP_KEY_ATTRIBUTE_OVERRIDES: return OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ATTRIBUTE_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlMapKeyConvertibleMapping_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_MAP_KEY_CONVERTIBLE_MAPPING_20__MAP_KEY_TEMPORAL: return OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_TEMPORAL;
				case OrmV2_0Package.XML_MAP_KEY_CONVERTIBLE_MAPPING_20__MAP_KEY_ENUMERATED: return OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_ENUMERATED;
				default: return -1;
			}
		}
		if (baseClass == XmlElementCollection_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_ELEMENT_COLLECTION_20__TARGET_CLASS: return OrmPackage.XML_ELEMENT_COLLECTION__TARGET_CLASS;
				case OrmV2_0Package.XML_ELEMENT_COLLECTION_20__FETCH: return OrmPackage.XML_ELEMENT_COLLECTION__FETCH;
				case OrmV2_0Package.XML_ELEMENT_COLLECTION_20__MAP_KEY: return OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY;
				case OrmV2_0Package.XML_ELEMENT_COLLECTION_20__MAP_KEY_CLASS: return OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CLASS;
				case OrmV2_0Package.XML_ELEMENT_COLLECTION_20__MAP_KEY_COLUMN: return OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_COLUMN;
				case OrmV2_0Package.XML_ELEMENT_COLLECTION_20__MAP_KEY_JOIN_COLUMNS: return OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_JOIN_COLUMNS;
				case OrmV2_0Package.XML_ELEMENT_COLLECTION_20__COLUMN: return OrmPackage.XML_ELEMENT_COLLECTION__COLUMN;
				case OrmV2_0Package.XML_ELEMENT_COLLECTION_20__COLLECTION_TABLE: return OrmPackage.XML_ELEMENT_COLLECTION__COLLECTION_TABLE;
				default: return -1;
			}
		}
		if (baseClass == XmlElementCollection_2_1.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_1Package.XML_ELEMENT_COLLECTION_21__MAP_KEY_CONVERTS: return OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_CONVERTS;
				case OrmV2_1Package.XML_ELEMENT_COLLECTION_21__MAP_KEY_FOREIGN_KEY: return OrmPackage.XML_ELEMENT_COLLECTION__MAP_KEY_FOREIGN_KEY;
				case OrmV2_1Package.XML_ELEMENT_COLLECTION_21__CONVERTS: return OrmPackage.XML_ELEMENT_COLLECTION__CONVERTS;
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
		result.append(" (lob: ");
		result.append(lob);
		result.append(", temporal: ");
		result.append(temporal);
		result.append(", enumerated: ");
		result.append(enumerated);
		result.append(", mapKeyTemporal: ");
		result.append(mapKeyTemporal);
		result.append(", mapKeyEnumerated: ");
		result.append(mapKeyEnumerated);
		result.append(", targetClass: ");
		result.append(targetClass);
		result.append(", fetch: ");
		result.append(fetch);
		result.append(')');
		return result.toString();
	}
	
	
	// **************** XmlAttributeMapping impl ******************************
	
	public String getMappingKey() {
		return MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}
	
	
	// **************** validation support ************************************
	
	public TextRange getEnumeratedTextRange() {
		return getAttributeTextRange(JPA2_0.ENUMERATED);
	}
	
	public TextRange getLobTextRange() {
		return getAttributeTextRange(JPA2_0.LOB);
	}
	
	public TextRange getTemporalTextRange() {
		return getAttributeTextRange(JPA2_0.TEMPORAL);
	}
	
	public TextRange getTargetClassTextRange() {
		return getAttributeTextRange(JPA2_0.TARGET_CLASS);
	}

	public TextRange getMapKeyEnumeratedTextRange() {
		return getAttributeTextRange(JPA2_0.MAP_KEY_ENUMERATED);
	}

	public TextRange getMapKeyTemporalTextRange() {
		return getAttributeTextRange(JPA2_0.MAP_KEY_TEMPORAL);
	}

	public TextRange getMapKeyClassTextRange() {
		return getElementTextRange(JPA2_0.MAP_KEY_CLASS);
	}

	// **************** translators *******************************************
	
	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}
	
	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildTargetClassTranslator(),
			buildFetchTranslator(),
			buildAccessTranslator(),
			buildOrderByTranslator(),
			buildOrderColumnTranslator(),
			buildMapKeyTranslator(),
			buildMapKeyClassTranslator(),
			buildMapKeyTemporalTranslator(),
			buildMapKeyEnumeratedTranslator(),
			buildMapKeyAttributeOverrideTranslator(),
			buildMapKeyConvertTranslator(),
			XmlColumn.buildTranslator(JPA2_0.MAP_KEY_COLUMN, OrmV2_0Package.eINSTANCE.getXmlElementCollection_2_0_MapKeyColumn()),
			XmlJoinColumn.buildTranslator(JPA2_0.MAP_KEY_JOIN_COLUMN, OrmV2_0Package.eINSTANCE.getXmlElementCollection_2_0_MapKeyJoinColumns()),
			buildMapKeyForeignKeyTranslator(),
			XmlColumn.buildTranslator(JPA.COLUMN, OrmV2_0Package.eINSTANCE.getXmlElementCollection_2_0_Column()),
			buildTemporalTranslator(),
			buildEnumeratedTranslator(),
			buildLobTranslator(),
			buildAttributeOverrideTranslator(),
			buildAssociationOverrideTranslator(),
			buildConvertTranslator(),
			XmlCollectionTable.buildTranslator(JPA2_0.COLLECTION_TABLE, OrmV2_0Package.eINSTANCE.getXmlElementCollection_2_0_CollectionTable())
		};
	}
	
	protected static Translator buildTargetClassTranslator() {
		return new Translator(JPA2_0.TARGET_CLASS, OrmV2_0Package.eINSTANCE.getXmlElementCollection_2_0_TargetClass(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildFetchTranslator() {
		return new Translator(JPA.FETCH, OrmV2_0Package.eINSTANCE.getXmlElementCollection_2_0_Fetch(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildOrderByTranslator() {
		return new Translator(JPA.ORDER_BY, OrmPackage.eINSTANCE.getXmlOrderable_OrderBy());
	}
	
	protected static Translator buildOrderColumnTranslator() {
		return XmlOrderColumn.buildTranslator(JPA2_0.ORDER_COLUMN, OrmV2_0Package.eINSTANCE.getXmlOrderable_2_0_OrderColumn());
	}
	
	protected static Translator buildColumnTranslator() {
		return XmlColumn.buildTranslator(JPA.COLUMN, OrmV2_0Package.eINSTANCE.getXmlElementCollection_2_0_Column());
	}
	
	protected static Translator buildLobTranslator() {
		return new EmptyTagBooleanTranslator(JPA.LOB, OrmPackage.eINSTANCE.getXmlConvertibleMapping_Lob());
	}
	
	protected static Translator buildTemporalTranslator() {
		return new Translator(JPA.TEMPORAL, OrmPackage.eINSTANCE.getXmlConvertibleMapping_Temporal());
	}
	
	protected static Translator buildEnumeratedTranslator() {
		return new Translator(JPA.ENUMERATED, OrmPackage.eINSTANCE.getXmlConvertibleMapping_Enumerated());
	}
	
	protected static Translator buildMapKeyTranslator() {
		return MapKey.buildTranslator(JPA.MAP_KEY, OrmV2_0Package.eINSTANCE.getXmlElementCollection_2_0_MapKey());
	}
	
	protected static Translator buildMapKeyClassTranslator() {
		return XmlClassReference.buildTranslator(JPA2_0.MAP_KEY_CLASS, OrmV2_0Package.eINSTANCE.getXmlElementCollection_2_0_MapKeyClass());
	}
	
	protected static Translator buildMapKeyTemporalTranslator() {
		return new Translator(JPA2_0.MAP_KEY_TEMPORAL, OrmV2_0Package.eINSTANCE.getXmlMapKeyConvertibleMapping_2_0_MapKeyTemporal());
	}
	
	protected static Translator buildMapKeyEnumeratedTranslator() {
		return new Translator(JPA2_0.MAP_KEY_ENUMERATED, OrmV2_0Package.eINSTANCE.getXmlMapKeyConvertibleMapping_2_0_MapKeyEnumerated());
	}	
	
	protected static Translator buildAttributeOverrideTranslator() {
		return XmlAttributeOverride.buildTranslator(JPA.ATTRIBUTE_OVERRIDE, OrmPackage.eINSTANCE.getXmlAttributeOverrideContainer_AttributeOverrides());
	}
	
	protected static Translator buildAssociationOverrideTranslator() {
		return XmlAssociationOverride.buildTranslator(JPA.ASSOCIATION_OVERRIDE, OrmPackage.eINSTANCE.getXmlAssociationOverrideContainer_AssociationOverrides());
	}
	
	protected static Translator buildMapKeyAttributeOverrideTranslator() {
		return XmlAttributeOverride.buildTranslator(JPA2_0.MAP_KEY_ATTRIBUTE_OVERRIDE, OrmV2_0Package.eINSTANCE.getXmlMapKeyAttributeOverrideContainer_2_0_MapKeyAttributeOverrides());
	}

	protected static Translator buildMapKeyForeignKeyTranslator() {
		return XmlForeignKey.buildTranslator(JPA2_1.MAP_KEY_FOREIGN_KEY, OrmV2_1Package.eINSTANCE.getXmlElementCollection_2_1_MapKeyForeignKey());
	}

	protected static Translator buildMapKeyConvertTranslator() {
		return XmlConvert.buildTranslator(JPA2_1.MAP_KEY_CONVERT, OrmV2_1Package.eINSTANCE.getXmlElementCollection_2_1_MapKeyConverts());
	}
	
	protected static Translator buildConvertTranslator() {
		return XmlConvert.buildTranslator(JPA2_1.CONVERT, OrmV2_1Package.eINSTANCE.getXmlElementCollection_2_1_Converts());
	}


	// ********** refactoring **********

	public ReplaceEdit createRenameTargetClassEdit(IType originalType, String newName) {
		String originalName = originalType.getElementName();
		int nameIndex = this.targetClass.lastIndexOf(originalName);
		int offset = getAttributeNode(JPA2_0.TARGET_CLASS).getValueRegionStartOffset() + 1;
		return new ReplaceEdit(offset + nameIndex, originalName.length(), newName);
	}

	public ReplaceEdit createRenameMapKeyClassEdit(IType originalType, String newName) {
		return getMapKeyClass().createRenameEdit(originalType, newName);
	}

	public ReplaceEdit createRenameTargetClassPackageEdit(String newPackageName) {
		int packageLength = this.targetClass.lastIndexOf('.');
		if (newPackageName == "") {//$NON-NLS-1$
			//moving to the default package, remove the '.'
			packageLength++;
		}
		if (packageLength == -1) {
			//moving from the default package or unspecified package
			packageLength = 0;
			newPackageName = newPackageName + '.';
		}
		int offset = getAttributeNode(JPA2_0.TARGET_CLASS).getValueRegionStartOffset() + 1; // +1 = opening double quote
		return new ReplaceEdit(offset, packageLength, newPackageName);
	}

	public ReplaceEdit createRenameMapKeyClassPackageEdit(String newName) {
		return getMapKeyClass().createRenamePackageEdit(newName);		
	}

	// ********** content assist ***************
	
	public TextRange getTargetClassCodeAssistTextRange() {
		return getAttributeCodeAssistTextRange(JPA2_0.TARGET_CLASS);
	}
	
	public boolean targetClassTouches(int pos) {
		TextRange textRange = this.getTargetClassCodeAssistTextRange();
		return (textRange != null) && (textRange.touches(pos));
	}
}
