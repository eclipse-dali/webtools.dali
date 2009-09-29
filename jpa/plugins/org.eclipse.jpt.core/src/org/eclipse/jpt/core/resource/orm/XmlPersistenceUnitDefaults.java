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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.resource.xml.translators.EmptyTagBooleanTranslator;
import org.eclipse.jpt.core.internal.resource.xml.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.v2_0.JPA2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlPersistenceUnitDefaults_2_0;
import org.eclipse.jpt.core.resource.xml.AbstractJpaEObject;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>XmlPersistence Unit Defaults</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#getSchema <em>Schema</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#getCatalog <em>Catalog</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#isCascadePersist <em>Cascade Persist</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#getEntityListeners <em>Entity Listeners</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlPersistenceUnitDefaults()
 * @model kind="class"
 * @generated
 */
public class XmlPersistenceUnitDefaults extends AbstractJpaEObject implements XmlAccessHolder, XmlPersistenceUnitDefaults_2_0
{
	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final AccessType ACCESS_EDEFAULT = null;

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
	 * The default value of the '{@link #isDelimitedIdentifiers() <em>Delimited Identifiers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDelimitedIdentifiers()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DELIMITED_IDENTIFIERS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDelimitedIdentifiers() <em>Delimited Identifiers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDelimitedIdentifiers()
	 * @generated
	 * @ordered
	 */
	protected boolean delimitedIdentifiers = DELIMITED_IDENTIFIERS_EDEFAULT;

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
		return OrmPackage.Literals.XML_PERSISTENCE_UNIT_DEFAULTS;
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlPersistenceUnitDefaults_Schema()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getSchema()
	{
		return schema;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#getSchema <em>Schema</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__SCHEMA, oldSchema, schema));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlPersistenceUnitDefaults_Catalog()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getCatalog()
	{
		return catalog;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#getCatalog <em>Catalog</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CATALOG, oldCatalog, catalog));
	}

	/**
	 * Returns the value of the '<em><b>Access</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.AccessType
	 * @see #setAccess(AccessType)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlAccessHolder_Access()
	 * @model
	 * @generated
	 */
	public AccessType getAccess()
	{
		return access;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#getAccess <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.AccessType
	 * @see #getAccess()
	 * @generated
	 */
	public void setAccess(AccessType newAccess)
	{
		AccessType oldAccess = access;
		access = newAccess == null ? ACCESS_EDEFAULT : newAccess;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS, oldAccess, access));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlPersistenceUnitDefaults_2_0_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#getDescription <em>Description</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__DESCRIPTION, oldDescription, description));
	}

	/**
	 * Returns the value of the '<em><b>Delimited Identifiers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delimited Identifiers</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delimited Identifiers</em>' attribute.
	 * @see #setDelimitedIdentifiers(boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlPersistenceUnitDefaults_2_0_DelimitedIdentifiers()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isDelimitedIdentifiers()
	{
		return delimitedIdentifiers;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#isDelimitedIdentifiers <em>Delimited Identifiers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delimited Identifiers</em>' attribute.
	 * @see #isDelimitedIdentifiers()
	 * @generated
	 */
	public void setDelimitedIdentifiers(boolean newDelimitedIdentifiers)
	{
		boolean oldDelimitedIdentifiers = delimitedIdentifiers;
		delimitedIdentifiers = newDelimitedIdentifiers;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__DELIMITED_IDENTIFIERS, oldDelimitedIdentifiers, delimitedIdentifiers));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlPersistenceUnitDefaults_CascadePersist()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isCascadePersist()
	{
		return cascadePersist;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#isCascadePersist <em>Cascade Persist</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST, oldCascadePersist, cascadePersist));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlPersistenceUnitDefaults_EntityListeners()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS, oldEntityListeners, newEntityListeners);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitDefaults#getEntityListeners <em>Entity Listeners</em>}' containment reference.
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
				msgs = ((InternalEObject)entityListeners).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS, null, msgs);
			if (newEntityListeners != null)
				msgs = ((InternalEObject)newEntityListeners).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS, null, msgs);
			msgs = basicSetEntityListeners(newEntityListeners, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS, newEntityListeners, newEntityListeners));
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
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS:
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
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS:
				return getAccess();
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__DESCRIPTION:
				return getDescription();
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__DELIMITED_IDENTIFIERS:
				return isDelimitedIdentifiers();
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__SCHEMA:
				return getSchema();
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CATALOG:
				return getCatalog();
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST:
				return isCascadePersist();
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS:
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
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS:
				setAccess((AccessType)newValue);
				return;
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__DELIMITED_IDENTIFIERS:
				setDelimitedIdentifiers((Boolean)newValue);
				return;
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__SCHEMA:
				setSchema((String)newValue);
				return;
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CATALOG:
				setCatalog((String)newValue);
				return;
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST:
				setCascadePersist((Boolean)newValue);
				return;
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS:
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
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS:
				setAccess(ACCESS_EDEFAULT);
				return;
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__DELIMITED_IDENTIFIERS:
				setDelimitedIdentifiers(DELIMITED_IDENTIFIERS_EDEFAULT);
				return;
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__SCHEMA:
				setSchema(SCHEMA_EDEFAULT);
				return;
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CATALOG:
				setCatalog(CATALOG_EDEFAULT);
				return;
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST:
				setCascadePersist(CASCADE_PERSIST_EDEFAULT);
				return;
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS:
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
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ACCESS:
				return access != ACCESS_EDEFAULT;
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__DELIMITED_IDENTIFIERS:
				return delimitedIdentifiers != DELIMITED_IDENTIFIERS_EDEFAULT;
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__SCHEMA:
				return SCHEMA_EDEFAULT == null ? schema != null : !SCHEMA_EDEFAULT.equals(schema);
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CATALOG:
				return CATALOG_EDEFAULT == null ? catalog != null : !CATALOG_EDEFAULT.equals(catalog);
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST:
				return cascadePersist != CASCADE_PERSIST_EDEFAULT;
			case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS:
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
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass)
	{
		if (baseClass == XmlPersistenceUnitDefaults_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__DESCRIPTION: return OrmV2_0Package.XML_PERSISTENCE_UNIT_DEFAULTS_20__DESCRIPTION;
				case OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__DELIMITED_IDENTIFIERS: return OrmV2_0Package.XML_PERSISTENCE_UNIT_DEFAULTS_20__DELIMITED_IDENTIFIERS;
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
		if (baseClass == XmlPersistenceUnitDefaults_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_PERSISTENCE_UNIT_DEFAULTS_20__DESCRIPTION: return OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__DESCRIPTION;
				case OrmV2_0Package.XML_PERSISTENCE_UNIT_DEFAULTS_20__DELIMITED_IDENTIFIERS: return OrmPackage.XML_PERSISTENCE_UNIT_DEFAULTS__DELIMITED_IDENTIFIERS;
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
		result.append(" (access: ");
		result.append(access);
		result.append(", description: ");
		result.append(description);
		result.append(", delimitedIdentifiers: ");
		result.append(delimitedIdentifiers);
		result.append(", schema: ");
		result.append(schema);
		result.append(", catalog: ");
		result.append(catalog);
		result.append(", cascadePersist: ");
		result.append(cascadePersist);
		result.append(')');
		return result.toString();
	}

	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildDescriptionTranslator(),
			buildSchemaTranslator(),
			buildCatalogTranslator(),
			buildDelimitedIdentifiersTranslator(),
			buildAccessTranslator(),
			buildCascadePersistTranslator(),
			EntityListeners.buildTranslator(JPA2_0.ENTITY_LISTENERS, OrmPackage.eINSTANCE.getXmlPersistenceUnitDefaults_EntityListeners())
		};
	}
	
	protected static Translator buildDescriptionTranslator() {
		return new Translator(JPA2_0.DESCRIPTION, OrmV2_0Package.eINSTANCE.getXmlPersistenceUnitDefaults_2_0_Description());
	}
	
	protected static Translator buildSchemaTranslator() {
		return new Translator(JPA.SCHEMA, OrmPackage.eINSTANCE.getXmlPersistenceUnitDefaults_Schema());
	}
	
	protected static Translator buildCatalogTranslator() {
		return new Translator(JPA.CATALOG, OrmPackage.eINSTANCE.getXmlPersistenceUnitDefaults_Catalog());
	}
	
	protected static Translator buildDelimitedIdentifiersTranslator() {
		return new EmptyTagBooleanTranslator(JPA2_0.DELIMITIED_IDENTIFIERS, OrmV2_0Package.eINSTANCE.getXmlPersistenceUnitDefaults_2_0_DelimitedIdentifiers());
	}
	
	protected static Translator buildAccessTranslator() {
		return new Translator(JPA.ACCESS, OrmPackage.eINSTANCE.getXmlAccessHolder_Access());
	}
	
	protected static Translator buildCascadePersistTranslator() {
		return new EmptyTagBooleanTranslator(JPA.CASCADE_PERSIST, OrmPackage.eINSTANCE.getXmlPersistenceUnitDefaults_CascadePersist());
	}
}
