/**
 * <copyright>
 * </copyright>
 *
 * $Id: PersistenceUnitDefaults.java,v 1.1.2.2 2007/10/16 17:01:40 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.resource.common.IJptEObject;
import org.eclipse.jpt.core.internal.resource.common.JptEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Persistence Unit Defaults</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getSchema <em>Schema</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getCatalog <em>Catalog</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getAccess <em>Access</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getCascadePersist <em>Cascade Persist</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getEntityListeners <em>Entity Listeners</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPersistenceUnitDefaults()
 * @model kind="class"
 * @extends IJptEObject
 * @generated
 */
public class PersistenceUnitDefaults extends JptEObject implements IJptEObject
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
	 * This is true if the Access attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean accessESet;

	/**
	 * The cached value of the '{@link #getCascadePersist() <em>Cascade Persist</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCascadePersist()
	 * @generated
	 * @ordered
	 */
	protected EmptyType cascadePersist;

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
	protected PersistenceUnitDefaults()
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
		return OrmPackage.Literals.PERSISTENCE_UNIT_DEFAULTS;
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPersistenceUnitDefaults_Schema()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getSchema()
	{
		return schema;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getSchema <em>Schema</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS__SCHEMA, oldSchema, schema));
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPersistenceUnitDefaults_Catalog()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getCatalog()
	{
		return catalog;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getCatalog <em>Catalog</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CATALOG, oldCatalog, catalog));
	}

	/**
	 * Returns the value of the '<em><b>Access</b></em>' attribute.
	 * The default value is <code>"PROPERTY"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.resource.orm.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AccessType
	 * @see #isSetAccess()
	 * @see #unsetAccess()
	 * @see #setAccess(AccessType)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPersistenceUnitDefaults_Access()
	 * @model default="PROPERTY" unsettable="true"
	 * @generated
	 */
	public AccessType getAccess()
	{
		return access;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getAccess <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.resource.orm.AccessType
	 * @see #isSetAccess()
	 * @see #unsetAccess()
	 * @see #getAccess()
	 * @generated
	 */
	public void setAccess(AccessType newAccess)
	{
		AccessType oldAccess = access;
		access = newAccess == null ? ACCESS_EDEFAULT : newAccess;
		boolean oldAccessESet = accessESet;
		accessESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ACCESS, oldAccess, access, !oldAccessESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getAccess <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAccess()
	 * @see #getAccess()
	 * @see #setAccess(AccessType)
	 * @generated
	 */
	public void unsetAccess()
	{
		AccessType oldAccess = access;
		boolean oldAccessESet = accessESet;
		access = ACCESS_EDEFAULT;
		accessESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ACCESS, oldAccess, ACCESS_EDEFAULT, oldAccessESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getAccess <em>Access</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Access</em>' attribute is set.
	 * @see #unsetAccess()
	 * @see #getAccess()
	 * @see #setAccess(AccessType)
	 * @generated
	 */
	public boolean isSetAccess()
	{
		return accessESet;
	}

	/**
	 * Returns the value of the '<em><b>Cascade Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Persist</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Persist</em>' containment reference.
	 * @see #setCascadePersist(EmptyType)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPersistenceUnitDefaults_CascadePersist()
	 * @model containment="true"
	 * @generated
	 */
	public EmptyType getCascadePersist()
	{
		return cascadePersist;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCascadePersist(EmptyType newCascadePersist, NotificationChain msgs)
	{
		EmptyType oldCascadePersist = cascadePersist;
		cascadePersist = newCascadePersist;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST, oldCascadePersist, newCascadePersist);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getCascadePersist <em>Cascade Persist</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Persist</em>' containment reference.
	 * @see #getCascadePersist()
	 * @generated
	 */
	public void setCascadePersist(EmptyType newCascadePersist)
	{
		if (newCascadePersist != cascadePersist)
		{
			NotificationChain msgs = null;
			if (cascadePersist != null)
				msgs = ((InternalEObject)cascadePersist).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST, null, msgs);
			if (newCascadePersist != null)
				msgs = ((InternalEObject)newCascadePersist).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST, null, msgs);
			msgs = basicSetCascadePersist(newCascadePersist, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST, newCascadePersist, newCascadePersist));
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getPersistenceUnitDefaults_EntityListeners()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS, oldEntityListeners, newEntityListeners);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults#getEntityListeners <em>Entity Listeners</em>}' containment reference.
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
				msgs = ((InternalEObject)entityListeners).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS, null, msgs);
			if (newEntityListeners != null)
				msgs = ((InternalEObject)newEntityListeners).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS, null, msgs);
			msgs = basicSetEntityListeners(newEntityListeners, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS, newEntityListeners, newEntityListeners));
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
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST:
				return basicSetCascadePersist(null, msgs);
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS:
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
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__SCHEMA:
				return getSchema();
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CATALOG:
				return getCatalog();
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ACCESS:
				return getAccess();
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST:
				return getCascadePersist();
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS:
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
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__SCHEMA:
				setSchema((String)newValue);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CATALOG:
				setCatalog((String)newValue);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ACCESS:
				setAccess((AccessType)newValue);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST:
				setCascadePersist((EmptyType)newValue);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS:
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
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__SCHEMA:
				setSchema(SCHEMA_EDEFAULT);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CATALOG:
				setCatalog(CATALOG_EDEFAULT);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ACCESS:
				unsetAccess();
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST:
				setCascadePersist((EmptyType)null);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS:
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
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__SCHEMA:
				return SCHEMA_EDEFAULT == null ? schema != null : !SCHEMA_EDEFAULT.equals(schema);
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CATALOG:
				return CATALOG_EDEFAULT == null ? catalog != null : !CATALOG_EDEFAULT.equals(catalog);
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ACCESS:
				return isSetAccess();
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST:
				return cascadePersist != null;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS:
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
		if (accessESet) result.append(access); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} // PersistenceUnitDefaults
