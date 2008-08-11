/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.elorm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.common.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Persistence Unit Defaults</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlPersistenceUnitDefaults#getSchema <em>Schema</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlPersistenceUnitDefaults#getCatalog <em>Catalog</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlPersistenceUnitDefaults#getAccess <em>Access</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlPersistenceUnitDefaults#isCascadePersist <em>Cascade Persist</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlPersistenceUnitDefaults#getEntityListeners <em>Entity Listeners</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlPersistenceUnitDefaults()
 * @model kind="class"
 * @extends JpaEObject
 * @generated
 */
public class XmlPersistenceUnitDefaults extends AbstractJpaEObject implements JpaEObject
{
	/**
	 * The default value of the '{@link #getSchema() <em>Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchema()
	 * @generated
	 * @ordered
	 */
	protected static final String SCHEMA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSchema() <em>Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchema()
	 * @generated
	 * @ordered
	 */
	protected String schema = SCHEMA_EDEFAULT;

	/**
	 * The default value of the '{@link #getCatalog() <em>Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCatalog()
	 * @generated
	 * @ordered
	 */
	protected static final String CATALOG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCatalog() <em>Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCatalog()
	 * @generated
	 * @ordered
	 */
	protected String catalog = CATALOG_EDEFAULT;

	/**
	 * The default value of the '{@link #getAccess() <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccess()
	 * @generated
	 * @ordered
	 */
	protected static final AccessType ACCESS_EDEFAULT = AccessType.PROPERTY;

	/**
	 * The cached value of the '{@link #getAccess() <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccess()
	 * @generated
	 * @ordered
	 */
	protected AccessType access = ACCESS_EDEFAULT;

	/**
	 * The default value of the '{@link #isCascadePersist() <em>Cascade Persist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadePersist()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASCADE_PERSIST_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCascadePersist() <em>Cascade Persist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadePersist()
	 * @generated
	 * @ordered
	 */
	protected boolean cascadePersist = CASCADE_PERSIST_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEntityListeners() <em>Entity Listeners</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityListeners()
	 * @generated
	 * @ordered
	 */
	protected EntityListeners entityListeners;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlPersistenceUnitDefaults()
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
		return EclipseLinkOrmPackage.Literals.XML_PERSISTENCE_UNIT_DEFAULTS;
	}

	/**
	 * Returns the value of the '<em><b>Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schema</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schema</em>' attribute.
	 * @see #setSchema(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlPersistenceUnitDefaults_Schema()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getSchema()
	{
		return schema;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlPersistenceUnitDefaults#getSchema <em>Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schema</em>' attribute.
	 * @see #getSchema()
	 * @generated
	 */
	public void setSchema(String newSchema)
	{
		String oldSchema = schema;
		schema = newSchema;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__SCHEMA, oldSchema, schema));
	}

	/**
	 * Returns the value of the '<em><b>Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Catalog</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Catalog</em>' attribute.
	 * @see #setCatalog(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlPersistenceUnitDefaults_Catalog()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getCatalog()
	{
		return catalog;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlPersistenceUnitDefaults#getCatalog <em>Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Catalog</em>' attribute.
	 * @see #getCatalog()
	 * @generated
	 */
	public void setCatalog(String newCatalog)
	{
		String oldCatalog = catalog;
		catalog = newCatalog;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CATALOG, oldCatalog, catalog));
	}

	/**
	 * Returns the value of the '<em><b>Access</b></em>' attribute.
	 * The default value is <code>"PROPERTY"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.eclipselink.core.resource.elorm.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.AccessType
	 * @see #setAccess(AccessType)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlPersistenceUnitDefaults_Access()
	 * @model default="PROPERTY"
	 * @generated
	 */
	public AccessType getAccess()
	{
		return access;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlPersistenceUnitDefaults#getAccess <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.AccessType
	 * @see #getAccess()
	 * @generated
	 */
	public void setAccess(AccessType newAccess)
	{
		AccessType oldAccess = access;
		access = newAccess == null ? ACCESS_EDEFAULT : newAccess;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS, oldAccess, access));
	}

	/**
	 * Returns the value of the '<em><b>Cascade Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Persist</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Persist</em>' attribute.
	 * @see #setCascadePersist(boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlPersistenceUnitDefaults_CascadePersist()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isCascadePersist()
	{
		return cascadePersist;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlPersistenceUnitDefaults#isCascadePersist <em>Cascade Persist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Persist</em>' attribute.
	 * @see #isCascadePersist()
	 * @generated
	 */
	public void setCascadePersist(boolean newCascadePersist)
	{
		boolean oldCascadePersist = cascadePersist;
		cascadePersist = newCascadePersist;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST, oldCascadePersist, cascadePersist));
	}

	/**
	 * Returns the value of the '<em><b>Entity Listeners</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Listeners</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Listeners</em>' containment reference.
	 * @see #setEntityListeners(EntityListeners)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getXmlPersistenceUnitDefaults_EntityListeners()
	 * @model containment="true"
	 * @generated
	 */
	public EntityListeners getEntityListeners()
	{
		return entityListeners;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEntityListeners(EntityListeners newEntityListeners, NotificationChain msgs)
	{
		EntityListeners oldEntityListeners = entityListeners;
		entityListeners = newEntityListeners;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS, oldEntityListeners, newEntityListeners);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.XmlPersistenceUnitDefaults#getEntityListeners <em>Entity Listeners</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity Listeners</em>' containment reference.
	 * @see #getEntityListeners()
	 * @generated
	 */
	public void setEntityListeners(EntityListeners newEntityListeners)
	{
		if (newEntityListeners != entityListeners)
		{
			NotificationChain msgs = null;
			if (entityListeners != null)
				msgs = ((InternalEObject)entityListeners).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS, null, msgs);
			if (newEntityListeners != null)
				msgs = ((InternalEObject)newEntityListeners).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS, null, msgs);
			msgs = basicSetEntityListeners(newEntityListeners, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS, newEntityListeners, newEntityListeners));
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
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS:
				return basicSetEntityListeners(null, msgs);
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
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__SCHEMA:
				return getSchema();
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CATALOG:
				return getCatalog();
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS:
				return getAccess();
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST:
				return isCascadePersist() ? Boolean.TRUE : Boolean.FALSE;
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS:
				return getEntityListeners();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__SCHEMA:
				setSchema((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CATALOG:
				setCatalog((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS:
				setAccess((AccessType)newValue);
				return;
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST:
				setCascadePersist(((Boolean)newValue).booleanValue());
				return;
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS:
				setEntityListeners((EntityListeners)newValue);
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
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__SCHEMA:
				setSchema(SCHEMA_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CATALOG:
				setCatalog(CATALOG_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS:
				setAccess(ACCESS_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST:
				setCascadePersist(CASCADE_PERSIST_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS:
				setEntityListeners((EntityListeners)null);
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
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__SCHEMA:
				return SCHEMA_EDEFAULT == null ? schema != null : !SCHEMA_EDEFAULT.equals(schema);
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CATALOG:
				return CATALOG_EDEFAULT == null ? catalog != null : !CATALOG_EDEFAULT.equals(catalog);
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS:
				return access != ACCESS_EDEFAULT;
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST:
				return cascadePersist != CASCADE_PERSIST_EDEFAULT;
			case EclipseLinkOrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS:
				return entityListeners != null;
		}
		return super.eIsSet(featureID);
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
		result.append(" (schema: ");
		result.append(schema);
		result.append(", catalog: ");
		result.append(catalog);
		result.append(", access: ");
		result.append(access);
		result.append(", cascadePersist: ");
		result.append(cascadePersist);
		result.append(')');
		return result.toString();
	}

} // XmlPersistenceUnitDefaults
