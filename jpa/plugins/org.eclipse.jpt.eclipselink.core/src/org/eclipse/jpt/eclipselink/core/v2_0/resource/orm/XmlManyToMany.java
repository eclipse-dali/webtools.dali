/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.v2_0.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Package;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyClass;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyColumn;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyJoinColumn;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlOrderColumn;
import org.eclipse.jpt.core.resource.orm.EnumType;
import org.eclipse.jpt.core.resource.orm.JPA;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Many To Many</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.XmlManyToMany#getMapKeyAssociationOverrides <em>Map Key Association Overrides</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.XmlManyToMany#getMapKeyConvert <em>Map Key Convert</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlManyToMany()
 * @model kind="class"
 * @generated
 */
public class XmlManyToMany extends org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlManyToMany implements XmlConverterHolder
{
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
	 * The cached value of the '{@link #getMapKeyClass() <em>Map Key Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyClass()
	 * @generated
	 * @ordered
	 */
	protected XmlMapKeyClass mapKeyClass;

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
	 * The cached value of the '{@link #getMapKeyAttributeOverrides() <em>Map Key Attribute Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyAttributeOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlAttributeOverride> mapKeyAttributeOverrides;

	/**
	 * The cached value of the '{@link #getMapKeyColumn() <em>Map Key Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyColumn()
	 * @generated
	 * @ordered
	 */
	protected XmlMapKeyColumn mapKeyColumn;

	/**
	 * The cached value of the '{@link #getMapKeyJoinColumns() <em>Map Key Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlMapKeyJoinColumn> mapKeyJoinColumns;

	/**
	 * The cached value of the '{@link #getConverter() <em>Converter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConverter()
	 * @generated
	 * @ordered
	 */
	protected XmlConverter converter;

	/**
	 * The cached value of the '{@link #getTypeConverter() <em>Type Converter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeConverter()
	 * @generated
	 * @ordered
	 */
	protected XmlTypeConverter typeConverter;

	/**
	 * The cached value of the '{@link #getObjectTypeConverter() <em>Object Type Converter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectTypeConverter()
	 * @generated
	 * @ordered
	 */
	protected XmlObjectTypeConverter objectTypeConverter;

	/**
	 * The cached value of the '{@link #getStructConverter() <em>Struct Converter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStructConverter()
	 * @generated
	 * @ordered
	 */
	protected XmlStructConverter structConverter;

	/**
	 * The cached value of the '{@link #getMapKeyAssociationOverrides() <em>Map Key Association Overrides</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyAssociationOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlAssociationOverride> mapKeyAssociationOverrides;

	/**
	 * The default value of the '{@link #getMapKeyConvert() <em>Map Key Convert</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyConvert()
	 * @generated
	 * @ordered
	 */
	protected static final String MAP_KEY_CONVERT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMapKeyConvert() <em>Map Key Convert</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyConvert()
	 * @generated
	 * @ordered
	 */
	protected String mapKeyConvert = MAP_KEY_CONVERT_EDEFAULT;

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
		return EclipseLink2_0OrmPackage.Literals.XML_MANY_TO_MANY;
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
	 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlManyToMany_OrderColumn()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__ORDER_COLUMN, oldOrderColumn, newOrderColumn);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.XmlManyToMany#getOrderColumn <em>Order Column</em>}' containment reference.
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
				msgs = ((InternalEObject)orderColumn).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__ORDER_COLUMN, null, msgs);
			if (newOrderColumn != null)
				msgs = ((InternalEObject)newOrderColumn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__ORDER_COLUMN, null, msgs);
			msgs = basicSetOrderColumn(newOrderColumn, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__ORDER_COLUMN, newOrderColumn, newOrderColumn));
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
	 * @see #setMapKeyClass(XmlMapKeyClass)
	 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlManyToMany_MapKeyClass()
	 * @model containment="true"
	 * @generated
	 */
	public XmlMapKeyClass getMapKeyClass()
	{
		return mapKeyClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapKeyClass(XmlMapKeyClass newMapKeyClass, NotificationChain msgs)
	{
		XmlMapKeyClass oldMapKeyClass = mapKeyClass;
		mapKeyClass = newMapKeyClass;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CLASS, oldMapKeyClass, newMapKeyClass);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.XmlManyToMany#getMapKeyClass <em>Map Key Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Class</em>' containment reference.
	 * @see #getMapKeyClass()
	 * @generated
	 */
	public void setMapKeyClass(XmlMapKeyClass newMapKeyClass)
	{
		if (newMapKeyClass != mapKeyClass)
		{
			NotificationChain msgs = null;
			if (mapKeyClass != null)
				msgs = ((InternalEObject)mapKeyClass).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CLASS, null, msgs);
			if (newMapKeyClass != null)
				msgs = ((InternalEObject)newMapKeyClass).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CLASS, null, msgs);
			msgs = basicSetMapKeyClass(newMapKeyClass, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CLASS, newMapKeyClass, newMapKeyClass));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Temporal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.TemporalType
	 * @see #setMapKeyTemporal(TemporalType)
	 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlManyToMany_MapKeyTemporal()
	 * @model
	 * @generated
	 */
	public TemporalType getMapKeyTemporal()
	{
		return mapKeyTemporal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.XmlManyToMany#getMapKeyTemporal <em>Map Key Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.TemporalType
	 * @see #getMapKeyTemporal()
	 * @generated
	 */
	public void setMapKeyTemporal(TemporalType newMapKeyTemporal)
	{
		TemporalType oldMapKeyTemporal = mapKeyTemporal;
		mapKeyTemporal = newMapKeyTemporal == null ? MAP_KEY_TEMPORAL_EDEFAULT : newMapKeyTemporal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_TEMPORAL, oldMapKeyTemporal, mapKeyTemporal));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.EnumType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Enumerated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.EnumType
	 * @see #setMapKeyEnumerated(EnumType)
	 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlManyToMany_MapKeyEnumerated()
	 * @model
	 * @generated
	 */
	public EnumType getMapKeyEnumerated()
	{
		return mapKeyEnumerated;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.XmlManyToMany#getMapKeyEnumerated <em>Map Key Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.EnumType
	 * @see #getMapKeyEnumerated()
	 * @generated
	 */
	public void setMapKeyEnumerated(EnumType newMapKeyEnumerated)
	{
		EnumType oldMapKeyEnumerated = mapKeyEnumerated;
		mapKeyEnumerated = newMapKeyEnumerated == null ? MAP_KEY_ENUMERATED_EDEFAULT : newMapKeyEnumerated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ENUMERATED, oldMapKeyEnumerated, mapKeyEnumerated));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Attribute Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlManyToMany_MapKeyAttributeOverrides()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlAttributeOverride> getMapKeyAttributeOverrides()
	{
		if (mapKeyAttributeOverrides == null)
		{
			mapKeyAttributeOverrides = new EObjectContainmentEList<XmlAttributeOverride>(XmlAttributeOverride.class, this, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES);
		}
		return mapKeyAttributeOverrides;
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
	 * @see #setMapKeyColumn(XmlMapKeyColumn)
	 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlManyToMany_MapKeyColumn()
	 * @model containment="true"
	 * @generated
	 */
	public XmlMapKeyColumn getMapKeyColumn()
	{
		return mapKeyColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapKeyColumn(XmlMapKeyColumn newMapKeyColumn, NotificationChain msgs)
	{
		XmlMapKeyColumn oldMapKeyColumn = mapKeyColumn;
		mapKeyColumn = newMapKeyColumn;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_COLUMN, oldMapKeyColumn, newMapKeyColumn);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.XmlManyToMany#getMapKeyColumn <em>Map Key Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Column</em>' containment reference.
	 * @see #getMapKeyColumn()
	 * @generated
	 */
	public void setMapKeyColumn(XmlMapKeyColumn newMapKeyColumn)
	{
		if (newMapKeyColumn != mapKeyColumn)
		{
			NotificationChain msgs = null;
			if (mapKeyColumn != null)
				msgs = ((InternalEObject)mapKeyColumn).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_COLUMN, null, msgs);
			if (newMapKeyColumn != null)
				msgs = ((InternalEObject)newMapKeyColumn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_COLUMN, null, msgs);
			msgs = basicSetMapKeyColumn(newMapKeyColumn, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_COLUMN, newMapKeyColumn, newMapKeyColumn));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.jpa2.resource.orm.XmlMapKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlManyToMany_MapKeyJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlMapKeyJoinColumn> getMapKeyJoinColumns()
	{
		if (mapKeyJoinColumns == null)
		{
			mapKeyJoinColumns = new EObjectContainmentEList<XmlMapKeyJoinColumn>(XmlMapKeyJoinColumn.class, this, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_JOIN_COLUMNS);
		}
		return mapKeyJoinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Converter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Converter</em>' containment reference.
	 * @see #setConverter(XmlConverter)
	 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlConverterHolder_Converter()
	 * @model containment="true"
	 * @generated
	 */
	public XmlConverter getConverter()
	{
		return converter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConverter(XmlConverter newConverter, NotificationChain msgs)
	{
		XmlConverter oldConverter = converter;
		converter = newConverter;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__CONVERTER, oldConverter, newConverter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.XmlManyToMany#getConverter <em>Converter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Converter</em>' containment reference.
	 * @see #getConverter()
	 * @generated
	 */
	public void setConverter(XmlConverter newConverter)
	{
		if (newConverter != converter)
		{
			NotificationChain msgs = null;
			if (converter != null)
				msgs = ((InternalEObject)converter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__CONVERTER, null, msgs);
			if (newConverter != null)
				msgs = ((InternalEObject)newConverter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__CONVERTER, null, msgs);
			msgs = basicSetConverter(newConverter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__CONVERTER, newConverter, newConverter));
	}

	/**
	 * Returns the value of the '<em><b>Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Converter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Converter</em>' containment reference.
	 * @see #setTypeConverter(XmlTypeConverter)
	 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlConverterHolder_TypeConverter()
	 * @model containment="true"
	 * @generated
	 */
	public XmlTypeConverter getTypeConverter()
	{
		return typeConverter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypeConverter(XmlTypeConverter newTypeConverter, NotificationChain msgs)
	{
		XmlTypeConverter oldTypeConverter = typeConverter;
		typeConverter = newTypeConverter;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__TYPE_CONVERTER, oldTypeConverter, newTypeConverter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.XmlManyToMany#getTypeConverter <em>Type Converter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Converter</em>' containment reference.
	 * @see #getTypeConverter()
	 * @generated
	 */
	public void setTypeConverter(XmlTypeConverter newTypeConverter)
	{
		if (newTypeConverter != typeConverter)
		{
			NotificationChain msgs = null;
			if (typeConverter != null)
				msgs = ((InternalEObject)typeConverter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__TYPE_CONVERTER, null, msgs);
			if (newTypeConverter != null)
				msgs = ((InternalEObject)newTypeConverter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__TYPE_CONVERTER, null, msgs);
			msgs = basicSetTypeConverter(newTypeConverter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__TYPE_CONVERTER, newTypeConverter, newTypeConverter));
	}

	/**
	 * Returns the value of the '<em><b>Object Type Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Type Converter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Type Converter</em>' containment reference.
	 * @see #setObjectTypeConverter(XmlObjectTypeConverter)
	 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlConverterHolder_ObjectTypeConverter()
	 * @model containment="true"
	 * @generated
	 */
	public XmlObjectTypeConverter getObjectTypeConverter()
	{
		return objectTypeConverter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetObjectTypeConverter(XmlObjectTypeConverter newObjectTypeConverter, NotificationChain msgs)
	{
		XmlObjectTypeConverter oldObjectTypeConverter = objectTypeConverter;
		objectTypeConverter = newObjectTypeConverter;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__OBJECT_TYPE_CONVERTER, oldObjectTypeConverter, newObjectTypeConverter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.XmlManyToMany#getObjectTypeConverter <em>Object Type Converter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Type Converter</em>' containment reference.
	 * @see #getObjectTypeConverter()
	 * @generated
	 */
	public void setObjectTypeConverter(XmlObjectTypeConverter newObjectTypeConverter)
	{
		if (newObjectTypeConverter != objectTypeConverter)
		{
			NotificationChain msgs = null;
			if (objectTypeConverter != null)
				msgs = ((InternalEObject)objectTypeConverter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__OBJECT_TYPE_CONVERTER, null, msgs);
			if (newObjectTypeConverter != null)
				msgs = ((InternalEObject)newObjectTypeConverter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__OBJECT_TYPE_CONVERTER, null, msgs);
			msgs = basicSetObjectTypeConverter(newObjectTypeConverter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__OBJECT_TYPE_CONVERTER, newObjectTypeConverter, newObjectTypeConverter));
	}

	/**
	 * Returns the value of the '<em><b>Struct Converter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Struct Converter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Struct Converter</em>' containment reference.
	 * @see #setStructConverter(XmlStructConverter)
	 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlConverterHolder_StructConverter()
	 * @model containment="true"
	 * @generated
	 */
	public XmlStructConverter getStructConverter()
	{
		return structConverter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStructConverter(XmlStructConverter newStructConverter, NotificationChain msgs)
	{
		XmlStructConverter oldStructConverter = structConverter;
		structConverter = newStructConverter;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__STRUCT_CONVERTER, oldStructConverter, newStructConverter);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.XmlManyToMany#getStructConverter <em>Struct Converter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Struct Converter</em>' containment reference.
	 * @see #getStructConverter()
	 * @generated
	 */
	public void setStructConverter(XmlStructConverter newStructConverter)
	{
		if (newStructConverter != structConverter)
		{
			NotificationChain msgs = null;
			if (structConverter != null)
				msgs = ((InternalEObject)structConverter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__STRUCT_CONVERTER, null, msgs);
			if (newStructConverter != null)
				msgs = ((InternalEObject)newStructConverter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__STRUCT_CONVERTER, null, msgs);
			msgs = basicSetStructConverter(newStructConverter, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__STRUCT_CONVERTER, newStructConverter, newStructConverter));
	}

	/**
	 * Returns the value of the '<em><b>Map Key Association Overrides</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.jpa2.resource.orm.XmlAssociationOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Association Overrides</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Association Overrides</em>' reference list.
	 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlManyToMany_MapKeyAssociationOverrides()
	 * @model
	 * @generated
	 */
	public EList<XmlAssociationOverride> getMapKeyAssociationOverrides()
	{
		if (mapKeyAssociationOverrides == null)
		{
			mapKeyAssociationOverrides = new EObjectResolvingEList<XmlAssociationOverride>(XmlAssociationOverride.class, this, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ASSOCIATION_OVERRIDES);
		}
		return mapKeyAssociationOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Map Key Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Convert</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Convert</em>' attribute.
	 * @see #setMapKeyConvert(String)
	 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage#getXmlManyToMany_MapKeyConvert()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getMapKeyConvert()
	{
		return mapKeyConvert;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.XmlManyToMany#getMapKeyConvert <em>Map Key Convert</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Convert</em>' attribute.
	 * @see #getMapKeyConvert()
	 * @generated
	 */
	public void setMapKeyConvert(String newMapKeyConvert)
	{
		String oldMapKeyConvert = mapKeyConvert;
		mapKeyConvert = newMapKeyConvert;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CONVERT, oldMapKeyConvert, mapKeyConvert));
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
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__ORDER_COLUMN:
				return basicSetOrderColumn(null, msgs);
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CLASS:
				return basicSetMapKeyClass(null, msgs);
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES:
				return ((InternalEList<?>)getMapKeyAttributeOverrides()).basicRemove(otherEnd, msgs);
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_COLUMN:
				return basicSetMapKeyColumn(null, msgs);
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_JOIN_COLUMNS:
				return ((InternalEList<?>)getMapKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__CONVERTER:
				return basicSetConverter(null, msgs);
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__TYPE_CONVERTER:
				return basicSetTypeConverter(null, msgs);
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__OBJECT_TYPE_CONVERTER:
				return basicSetObjectTypeConverter(null, msgs);
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__STRUCT_CONVERTER:
				return basicSetStructConverter(null, msgs);
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
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__ORDER_COLUMN:
				return getOrderColumn();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CLASS:
				return getMapKeyClass();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_TEMPORAL:
				return getMapKeyTemporal();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ENUMERATED:
				return getMapKeyEnumerated();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES:
				return getMapKeyAttributeOverrides();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_COLUMN:
				return getMapKeyColumn();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_JOIN_COLUMNS:
				return getMapKeyJoinColumns();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__CONVERTER:
				return getConverter();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__TYPE_CONVERTER:
				return getTypeConverter();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__OBJECT_TYPE_CONVERTER:
				return getObjectTypeConverter();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__STRUCT_CONVERTER:
				return getStructConverter();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ASSOCIATION_OVERRIDES:
				return getMapKeyAssociationOverrides();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CONVERT:
				return getMapKeyConvert();
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
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__ORDER_COLUMN:
				setOrderColumn((XmlOrderColumn)newValue);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CLASS:
				setMapKeyClass((XmlMapKeyClass)newValue);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_TEMPORAL:
				setMapKeyTemporal((TemporalType)newValue);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ENUMERATED:
				setMapKeyEnumerated((EnumType)newValue);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES:
				getMapKeyAttributeOverrides().clear();
				getMapKeyAttributeOverrides().addAll((Collection<? extends XmlAttributeOverride>)newValue);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_COLUMN:
				setMapKeyColumn((XmlMapKeyColumn)newValue);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_JOIN_COLUMNS:
				getMapKeyJoinColumns().clear();
				getMapKeyJoinColumns().addAll((Collection<? extends XmlMapKeyJoinColumn>)newValue);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__CONVERTER:
				setConverter((XmlConverter)newValue);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__TYPE_CONVERTER:
				setTypeConverter((XmlTypeConverter)newValue);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__OBJECT_TYPE_CONVERTER:
				setObjectTypeConverter((XmlObjectTypeConverter)newValue);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__STRUCT_CONVERTER:
				setStructConverter((XmlStructConverter)newValue);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ASSOCIATION_OVERRIDES:
				getMapKeyAssociationOverrides().clear();
				getMapKeyAssociationOverrides().addAll((Collection<? extends XmlAssociationOverride>)newValue);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CONVERT:
				setMapKeyConvert((String)newValue);
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
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__ORDER_COLUMN:
				setOrderColumn((XmlOrderColumn)null);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CLASS:
				setMapKeyClass((XmlMapKeyClass)null);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_TEMPORAL:
				setMapKeyTemporal(MAP_KEY_TEMPORAL_EDEFAULT);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ENUMERATED:
				setMapKeyEnumerated(MAP_KEY_ENUMERATED_EDEFAULT);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES:
				getMapKeyAttributeOverrides().clear();
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_COLUMN:
				setMapKeyColumn((XmlMapKeyColumn)null);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_JOIN_COLUMNS:
				getMapKeyJoinColumns().clear();
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__CONVERTER:
				setConverter((XmlConverter)null);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__TYPE_CONVERTER:
				setTypeConverter((XmlTypeConverter)null);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__OBJECT_TYPE_CONVERTER:
				setObjectTypeConverter((XmlObjectTypeConverter)null);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__STRUCT_CONVERTER:
				setStructConverter((XmlStructConverter)null);
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ASSOCIATION_OVERRIDES:
				getMapKeyAssociationOverrides().clear();
				return;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CONVERT:
				setMapKeyConvert(MAP_KEY_CONVERT_EDEFAULT);
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
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__ORDER_COLUMN:
				return orderColumn != null;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CLASS:
				return mapKeyClass != null;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_TEMPORAL:
				return mapKeyTemporal != MAP_KEY_TEMPORAL_EDEFAULT;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ENUMERATED:
				return mapKeyEnumerated != MAP_KEY_ENUMERATED_EDEFAULT;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES:
				return mapKeyAttributeOverrides != null && !mapKeyAttributeOverrides.isEmpty();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_COLUMN:
				return mapKeyColumn != null;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_JOIN_COLUMNS:
				return mapKeyJoinColumns != null && !mapKeyJoinColumns.isEmpty();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__CONVERTER:
				return converter != null;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__TYPE_CONVERTER:
				return typeConverter != null;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__OBJECT_TYPE_CONVERTER:
				return objectTypeConverter != null;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__STRUCT_CONVERTER:
				return structConverter != null;
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ASSOCIATION_OVERRIDES:
				return mapKeyAssociationOverrides != null && !mapKeyAssociationOverrides.isEmpty();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CONVERT:
				return MAP_KEY_CONVERT_EDEFAULT == null ? mapKeyConvert != null : !MAP_KEY_CONVERT_EDEFAULT.equals(mapKeyConvert);
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
		if (baseClass == XmlAttributeMapping.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__ORDER_COLUMN: return Orm2_0Package.XML_MANY_TO_MANY__ORDER_COLUMN;
				case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CLASS: return Orm2_0Package.XML_MANY_TO_MANY__MAP_KEY_CLASS;
				case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_TEMPORAL: return Orm2_0Package.XML_MANY_TO_MANY__MAP_KEY_TEMPORAL;
				case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ENUMERATED: return Orm2_0Package.XML_MANY_TO_MANY__MAP_KEY_ENUMERATED;
				case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES: return Orm2_0Package.XML_MANY_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES;
				case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_COLUMN: return Orm2_0Package.XML_MANY_TO_MANY__MAP_KEY_COLUMN;
				case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_JOIN_COLUMNS: return Orm2_0Package.XML_MANY_TO_MANY__MAP_KEY_JOIN_COLUMNS;
				default: return -1;
			}
		}
		if (baseClass == XmlConverterHolder.class)
		{
			switch (derivedFeatureID)
			{
				case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__CONVERTER: return EclipseLinkOrmPackage.XML_CONVERTER_HOLDER__CONVERTER;
				case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__TYPE_CONVERTER: return EclipseLinkOrmPackage.XML_CONVERTER_HOLDER__TYPE_CONVERTER;
				case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__OBJECT_TYPE_CONVERTER: return EclipseLinkOrmPackage.XML_CONVERTER_HOLDER__OBJECT_TYPE_CONVERTER;
				case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__STRUCT_CONVERTER: return EclipseLinkOrmPackage.XML_CONVERTER_HOLDER__STRUCT_CONVERTER;
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
		if (baseClass == XmlAttributeMapping.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany.class)
		{
			switch (baseFeatureID)
			{
				case Orm2_0Package.XML_MANY_TO_MANY__ORDER_COLUMN: return EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__ORDER_COLUMN;
				case Orm2_0Package.XML_MANY_TO_MANY__MAP_KEY_CLASS: return EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_CLASS;
				case Orm2_0Package.XML_MANY_TO_MANY__MAP_KEY_TEMPORAL: return EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_TEMPORAL;
				case Orm2_0Package.XML_MANY_TO_MANY__MAP_KEY_ENUMERATED: return EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ENUMERATED;
				case Orm2_0Package.XML_MANY_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES: return EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES;
				case Orm2_0Package.XML_MANY_TO_MANY__MAP_KEY_COLUMN: return EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_COLUMN;
				case Orm2_0Package.XML_MANY_TO_MANY__MAP_KEY_JOIN_COLUMNS: return EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__MAP_KEY_JOIN_COLUMNS;
				default: return -1;
			}
		}
		if (baseClass == XmlConverterHolder.class)
		{
			switch (baseFeatureID)
			{
				case EclipseLinkOrmPackage.XML_CONVERTER_HOLDER__CONVERTER: return EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__CONVERTER;
				case EclipseLinkOrmPackage.XML_CONVERTER_HOLDER__TYPE_CONVERTER: return EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__TYPE_CONVERTER;
				case EclipseLinkOrmPackage.XML_CONVERTER_HOLDER__OBJECT_TYPE_CONVERTER: return EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__OBJECT_TYPE_CONVERTER;
				case EclipseLinkOrmPackage.XML_CONVERTER_HOLDER__STRUCT_CONVERTER: return EclipseLink2_0OrmPackage.XML_MANY_TO_MANY__STRUCT_CONVERTER;
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
		result.append(" (mapKeyTemporal: ");
		result.append(mapKeyTemporal);
		result.append(", mapKeyEnumerated: ");
		result.append(mapKeyEnumerated);
		result.append(", mapKeyConvert: ");
		result.append(mapKeyConvert);
		result.append(')');
		return result.toString();
	}
	
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName, 
			structuralFeature, 
			EclipseLink2_0OrmPackage.eINSTANCE.getXmlManyToMany(), 
			buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildTargetEntityTranslator(),
			buildFetchTranslator(),
			buildAccessTranslator(),
			buildMappedByTranslator(),
			buildOrderByTranslator(),
			buildMapKeyTranslator(),
			XmlMapKeyClass.buildTranslator(EclipseLink2_0.MAP_KEY_CLASS, Orm2_0Package.eINSTANCE.getXmlManyToMany_MapKeyClass()),		
			buildMapKeyTemporalTranslator(),
			buildMapKeyEnumeratedTranslator(),
			buildMapKeyConvertTranslator(),
			XmlAttributeOverride.buildTranslator(EclipseLink2_0.MAP_KEY_ATTRIBUTE_OVERRIDE, Orm2_0Package.eINSTANCE.getXmlManyToMany_MapKeyAttributeOverrides()),		
			org.eclipse.jpt.core.jpa2.resource.orm.XmlAssociationOverride.buildTranslator(EclipseLink2_0.MAP_KEY_ASSOCIATION_OVERRIDE, EclipseLink2_0OrmPackage.eINSTANCE.getXmlManyToMany_MapKeyAssociationOverrides()),		
			XmlColumn.buildTranslator(EclipseLink2_0.MAP_KEY_COLUMN, Orm2_0Package.eINSTANCE.getXmlManyToMany_MapKeyColumn()),		
			XmlJoinColumn.buildTranslator(EclipseLink2_0.MAP_KEY_JOIN_COLUMN, Orm2_0Package.eINSTANCE.getXmlManyToMany_MapKeyJoinColumns()),		
			buildJoinTableTranslator(),
			buildCascadeTranslator(),
			buildJoinFetchTranslator(),
			buildPropertyTranslator(),
			buildAccessMethodsTranslator()
		};
	}

	protected static Translator buildAccessTranslator() {
		return new Translator(JPA.ACCESS, OrmPackage.eINSTANCE.getXmlAccessHolder_Access(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildMapKeyTemporalTranslator() {
		return new Translator(EclipseLink2_0.MAP_KEY_TEMPORAL, Orm2_0Package.eINSTANCE.getXmlManyToMany_MapKeyTemporal());
	}
	
	protected static Translator buildMapKeyEnumeratedTranslator() {
		return new Translator(EclipseLink2_0.MAP_KEY_ENUMERATED, Orm2_0Package.eINSTANCE.getXmlManyToMany_MapKeyEnumerated());
	}
	
	protected static Translator buildMapKeyConvertTranslator() {
		return new Translator(EclipseLink2_0.MAP_KEY_CONVERT, EclipseLink2_0OrmPackage.eINSTANCE.getXmlManyToMany_MapKeyConvert());
	}

} // XmlManyToMany
