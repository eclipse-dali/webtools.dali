/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import java.util.Iterator;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.content.orm.resource.OrmXmlMapper;
import org.eclipse.jpt.core.internal.emfutility.DOMUtilities;
import org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IJoinTable;
import org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.INonOwningMapping;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Multi Relationship Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlMultiRelationshipMappingInternal()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class XmlMultiRelationshipMappingInternal
	extends XmlRelationshipMapping
	implements IMultiRelationshipMapping, XmlMultiRelationshipMappingForXml,
	XmlMultiRelationshipMapping
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
	 * The default value of the '{@link #getOrderBy() <em>Order By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrderBy()
	 * @generated
	 * @ordered
	 */
	protected static final String ORDER_BY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOrderBy() <em>Order By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrderBy()
	 * @generated
	 * @ordered
	 */
	protected String orderBy = ORDER_BY_EDEFAULT;

	/**
	 * The default value of the '{@link #getFetch() <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultLazyFetchType FETCH_EDEFAULT = DefaultLazyFetchType.DEFAULT;

	/**
	 * The cached value of the '{@link #getFetch() <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected DefaultLazyFetchType fetch = FETCH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getJoinTable() <em>Join Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinTable()
	 * @generated
	 * @ordered
	 */
	protected IJoinTable joinTable;

	/**
	 * The default value of the '{@link #getMapKey() <em>Map Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKey()
	 * @generated
	 * @ordered
	 */
	protected static final String MAP_KEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMapKey() <em>Map Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKey()
	 * @generated
	 * @ordered
	 */
	protected String mapKey = MAP_KEY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMapKeyForXml() <em>Map Key For Xml</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKeyForXml()
	 * @generated
	 * @ordered
	 */
	protected XmlMapKey mapKeyForXml;

	protected XmlMultiRelationshipMappingInternal() {
		super();
		this.joinTable = OrmFactory.eINSTANCE.createXmlJoinTable(buildJoinTableOwner());
		((InternalEObject) this.joinTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE, null, null);
		this.eAdapters().add(this.buildListener());
	}

	protected Adapter buildListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				XmlMultiRelationshipMappingInternal.this.notifyChanged(notification);
			}
		};
	}

	protected void notifyChanged(Notification notification) {
		switch (notification.getFeatureID(IMultiRelationshipMapping.class)) {
			case JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__MAP_KEY :
				mapKeyChanged();
				break;
			default :
				break;
		}
		switch (notification.getFeatureID(XmlMultiRelationshipMappingForXml.class)) {
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY_FOR_XML :
				xmlMapKeyChanged();
				break;
			default :
				break;
		}
	}

	protected void mapKeyChanged() {
		if (getMapKey() == null) {
			setMapKeyForXml(null);
		}
		else {
			if (getMapKeyForXml() == null) {
				setMapKeyForXml(OrmFactory.eINSTANCE.createXmlMapKey());
			}
			getMapKeyForXml().setName(getMapKey());
		}
	}

	protected void xmlMapKeyChanged() {
		if (getMapKeyForXml() == null) {
			setMapKey(null);
		}
	}

	private IJoinTable.Owner buildJoinTableOwner() {
		return new IJoinTable.Owner() {
			public ITextRange validationTextRange() {
				return XmlMultiRelationshipMappingInternal.this.validationTextRange();
			}

			public ITypeMapping getTypeMapping() {
				return XmlMultiRelationshipMappingInternal.this.typeMapping();
			}
		};
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL;
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getINonOwningMapping_MappedBy()
	 * @model
	 * @generated
	 */
	public String getMappedBy() {
		return mappedBy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal#getMappedBy <em>Mapped By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapped By</em>' attribute.
	 * @see #getMappedBy()
	 * @generated
	 */
	public void setMappedBy(String newMappedBy) {
		String oldMappedBy = mappedBy;
		mappedBy = newMappedBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAPPED_BY, oldMappedBy, mappedBy));
	}

	/**
	 * Returns the value of the '<em><b>Order By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Order By</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Order By</em>' attribute.
	 * @see #setOrderBy(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIMultiRelationshipMapping_OrderBy()
	 * @model unique="false" ordered="false"
	 * @generated
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal#getOrderBy <em>Order By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Order By</em>' attribute.
	 * @see #getOrderBy()
	 * @generated
	 */
	public void setOrderBy(String newOrderBy) {
		String oldOrderBy = orderBy;
		orderBy = newOrderBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__ORDER_BY, oldOrderBy, orderBy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false" required="true" ordered="false"
	 * @generated NOT
	 */
	public boolean isNoOrdering() {
		return getOrderBy() == null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public void setNoOrdering() {
		setOrderBy(null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false" required="true" ordered="false"
	 * @generated NOT
	 */
	public boolean isOrderByPk() {
		return "".equals(getOrderBy());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public void setOrderByPk() {
		setOrderBy("");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false" required="true" ordered="false"
	 * @generated NOT
	 */
	public boolean isCustomOrdering() {
		return ! StringTools.stringIsEmpty(getOrderBy());
	}

	public ITextRange mappedByTextRange() {
		if (node == null) {
			return typeMapping().validationTextRange();
		}
		IDOMNode mappedByNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.MAPPED_BY);
		return (mappedByNode == null) ? validationTextRange() : buildTextRange(mappedByNode);
	}

	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType
	 * @see #setFetch(DefaultLazyFetchType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIMultiRelationshipMapping_Fetch()
	 * @model
	 * @generated
	 */
	public DefaultLazyFetchType getFetch() {
		return fetch;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal#getFetch <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType
	 * @see #getFetch()
	 * @generated
	 */
	public void setFetch(DefaultLazyFetchType newFetch) {
		DefaultLazyFetchType oldFetch = fetch;
		fetch = newFetch == null ? FETCH_EDEFAULT : newFetch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__FETCH, oldFetch, fetch));
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIMultiRelationshipMapping_JoinTable()
	 * @model containment="true" required="true" changeable="false"
	 * @generated
	 */
	public IJoinTable getJoinTable() {
		return joinTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJoinTable(IJoinTable newJoinTable, NotificationChain msgs) {
		IJoinTable oldJoinTable = joinTable;
		joinTable = newJoinTable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE, oldJoinTable, newJoinTable);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	public boolean isJoinTableSpecified() {
		XmlJoinTable table = getJoinTableForXml();
		return table != null && table.isSpecified();
	}

	/**
	 * Returns the value of the '<em><b>Map Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key</em>' attribute.
	 * @see #setMapKey(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIMultiRelationshipMapping_MapKey()
	 * @model
	 * @generated
	 */
	public String getMapKey() {
		return mapKey;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal#getMapKey <em>Map Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key</em>' attribute.
	 * @see #getMapKey()
	 * @generated
	 */
	public void setMapKey(String newMapKey) {
		String oldMapKey = mapKey;
		mapKey = newMapKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY, oldMapKey, mapKey));
	}

	private XmlJoinTable getJoinTableInternal() {
		return (XmlJoinTable) getJoinTable();
	}

	public XmlJoinTable getJoinTableForXml() {
		if (getJoinTableInternal().isAllFeaturesUnset()) {
			return null;
		}
		return getJoinTableInternal();
	}

	public void setJoinTableForXmlGen(XmlJoinTable newJoinTableForXml) {
		XmlJoinTable oldValue = newJoinTableForXml == null ? (XmlJoinTable) getJoinTable() : null;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE_FOR_XML, oldValue, newJoinTableForXml));
	}

	public void setJoinTableForXml(XmlJoinTable newJoinTableForXml) {
		setJoinTableForXmlGen(newJoinTableForXml);
		if (newJoinTableForXml == null) {
			getJoinTableInternal().unsetAllAttributes();
		}
	}

	/**
	 * Returns the value of the '<em><b>Map Key For Xml</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key For Xml</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key For Xml</em>' containment reference.
	 * @see #setMapKeyForXml(XmlMapKey)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlMultiRelationshipMappingForXml_MapKeyForXml()
	 * @model containment="true"
	 * @generated
	 */
	public XmlMapKey getMapKeyForXml() {
		return mapKeyForXml;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMapKeyForXml(XmlMapKey newMapKeyForXml, NotificationChain msgs) {
		XmlMapKey oldMapKeyForXml = mapKeyForXml;
		mapKeyForXml = newMapKeyForXml;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY_FOR_XML, oldMapKeyForXml, newMapKeyForXml);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlMultiRelationshipMappingInternal#getMapKeyForXml <em>Map Key For Xml</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key For Xml</em>' containment reference.
	 * @see #getMapKeyForXml()
	 * @generated
	 */
	public void setMapKeyForXml(XmlMapKey newMapKeyForXml) {
		if (newMapKeyForXml != mapKeyForXml) {
			NotificationChain msgs = null;
			if (mapKeyForXml != null)
				msgs = ((InternalEObject) mapKeyForXml).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY_FOR_XML, null, msgs);
			if (newMapKeyForXml != null)
				msgs = ((InternalEObject) newMapKeyForXml).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY_FOR_XML, null, msgs);
			msgs = basicSetMapKeyForXml(newMapKeyForXml, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY_FOR_XML, newMapKeyForXml, newMapKeyForXml));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE :
				return basicSetJoinTable(null, msgs);
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY_FOR_XML :
				return basicSetMapKeyForXml(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAPPED_BY :
				return getMappedBy();
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__ORDER_BY :
				return getOrderBy();
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__FETCH :
				return getFetch();
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE :
				return getJoinTable();
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY :
				return getMapKey();
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE_FOR_XML :
				return getJoinTableForXml();
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY_FOR_XML :
				return getMapKeyForXml();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAPPED_BY :
				setMappedBy((String) newValue);
				return;
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__ORDER_BY :
				setOrderBy((String) newValue);
				return;
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__FETCH :
				setFetch((DefaultLazyFetchType) newValue);
				return;
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY :
				setMapKey((String) newValue);
				return;
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE_FOR_XML :
				setJoinTableForXml((XmlJoinTable) newValue);
				return;
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY_FOR_XML :
				setMapKeyForXml((XmlMapKey) newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAPPED_BY :
				setMappedBy(MAPPED_BY_EDEFAULT);
				return;
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__ORDER_BY :
				setOrderBy(ORDER_BY_EDEFAULT);
				return;
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__FETCH :
				setFetch(FETCH_EDEFAULT);
				return;
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY :
				setMapKey(MAP_KEY_EDEFAULT);
				return;
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE_FOR_XML :
				setJoinTableForXml((XmlJoinTable) null);
				return;
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY_FOR_XML :
				setMapKeyForXml((XmlMapKey) null);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAPPED_BY :
				return MAPPED_BY_EDEFAULT == null ? mappedBy != null : !MAPPED_BY_EDEFAULT.equals(mappedBy);
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__ORDER_BY :
				return ORDER_BY_EDEFAULT == null ? orderBy != null : !ORDER_BY_EDEFAULT.equals(orderBy);
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__FETCH :
				return fetch != FETCH_EDEFAULT;
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE :
				return joinTable != null;
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY :
				return MAP_KEY_EDEFAULT == null ? mapKey != null : !MAP_KEY_EDEFAULT.equals(mapKey);
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE_FOR_XML :
				return getJoinTableForXml() != null;
			case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY_FOR_XML :
				return mapKeyForXml != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == INonOwningMapping.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAPPED_BY :
					return JpaCoreMappingsPackage.INON_OWNING_MAPPING__MAPPED_BY;
				default :
					return -1;
			}
		}
		if (baseClass == IMultiRelationshipMapping.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__ORDER_BY :
					return JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__ORDER_BY;
				case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__FETCH :
					return JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__FETCH;
				case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE :
					return JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__JOIN_TABLE;
				case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY :
					return JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__MAP_KEY;
				default :
					return -1;
			}
		}
		if (baseClass == XmlMultiRelationshipMappingForXml.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE_FOR_XML :
					return OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_FOR_XML__JOIN_TABLE_FOR_XML;
				case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY_FOR_XML :
					return OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_FOR_XML__MAP_KEY_FOR_XML;
				default :
					return -1;
			}
		}
		if (baseClass == XmlMultiRelationshipMapping.class) {
			switch (derivedFeatureID) {
				default :
					return -1;
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
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == INonOwningMapping.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.INON_OWNING_MAPPING__MAPPED_BY :
					return OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAPPED_BY;
				default :
					return -1;
			}
		}
		if (baseClass == IMultiRelationshipMapping.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__ORDER_BY :
					return OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__ORDER_BY;
				case JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__FETCH :
					return OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__FETCH;
				case JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__JOIN_TABLE :
					return OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE;
				case JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING__MAP_KEY :
					return OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY;
				default :
					return -1;
			}
		}
		if (baseClass == XmlMultiRelationshipMappingForXml.class) {
			switch (baseFeatureID) {
				case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_FOR_XML__JOIN_TABLE_FOR_XML :
					return OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__JOIN_TABLE_FOR_XML;
				case OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_FOR_XML__MAP_KEY_FOR_XML :
					return OrmPackage.XML_MULTI_RELATIONSHIP_MAPPING_INTERNAL__MAP_KEY_FOR_XML;
				default :
					return -1;
			}
		}
		if (baseClass == XmlMultiRelationshipMapping.class) {
			switch (baseFeatureID) {
				default :
					return -1;
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
	public String toString() {
		if (eIsProxy())
			return super.toString();
		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mappedBy: ");
		result.append(mappedBy);
		result.append(", orderBy: ");
		result.append(orderBy);
		result.append(", fetch: ");
		result.append(fetch);
		result.append(", mapKey: ");
		result.append(mapKey);
		result.append(')');
		return result.toString();
	}

	public void makeJoinTableForXmlNull() {
		setJoinTableForXmlGen(null);
	}

	public void makeJoinTableForXmlNonNull() {
		setJoinTableForXmlGen(getJoinTableForXml());
	}

	@Override
	public void initializeFromXmlMulitRelationshipMapping(XmlMultiRelationshipMappingInternal oldMapping) {
		super.initializeFromXmlMulitRelationshipMapping(oldMapping);
		setFetch(oldMapping.getFetch());
	}

	public Iterator<String> candidateMapKeyNames() {
		return this.allTargetEntityAttributeNames();
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {
		super.refreshDefaults(defaultsContext);
		// TODO
		//		if (isOrderByPk()) {
		//			refreshDefaultOrderBy(defaultsContext);
		//		}
	}

	//primary key ordering when just the @OrderBy annotation is present
	protected void refreshDefaultOrderBy(DefaultsContext defaultsContext) {
		IEntity targetEntity = getResolvedTargetEntity();
		if (targetEntity != null) {
			setOrderBy(targetEntity.primaryKeyAttributeName() + " ASC");
		}
	}
}
