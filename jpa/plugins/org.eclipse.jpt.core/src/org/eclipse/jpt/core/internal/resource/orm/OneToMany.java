/**
 * <copyright>
 * </copyright>
 *
 * $Id: OneToMany.java,v 1.1.2.2 2007/10/16 17:01:40 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.internal.resource.common.IJptEObject;
import org.eclipse.jpt.core.internal.resource.common.JptEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>One To Many</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getTargetEntity <em>Target Entity</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getFetch <em>Fetch</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getMappedBy <em>Mapped By</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getOrderBy <em>Order By</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getMapKey <em>Map Key</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getJoinTable <em>Join Table</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getJoinColumns <em>Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getCascade <em>Cascade</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToMany()
 * @model kind="class"
 * @extends IJptEObject
 * @generated
 */
public class OneToMany extends JptEObject implements IJptEObject
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
	 * The default value of the '{@link #getTargetEntity() <em>Target Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetEntity()
	 * @generated
	 * @ordered
	 */
	protected static final String TARGET_ENTITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTargetEntity() <em>Target Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetEntity()
	 * @generated
	 * @ordered
	 */
	protected String targetEntity = TARGET_ENTITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getFetch() <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFetch()
	 * @generated
	 * @ordered
	 */
	protected static final FetchType FETCH_EDEFAULT = FetchType.LAZY;

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
	 * This is true if the Fetch attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean fetchESet;

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
	 * The cached value of the '{@link #getMapKey() <em>Map Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapKey()
	 * @generated
	 * @ordered
	 */
	protected MapKey mapKey;

	/**
	 * The cached value of the '{@link #getJoinTable() <em>Join Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinTable()
	 * @generated
	 * @ordered
	 */
	protected JoinTable joinTable;

	/**
	 * The cached value of the '{@link #getJoinColumns() <em>Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<JoinColumn> joinColumns;

	/**
	 * The cached value of the '{@link #getCascade() <em>Cascade</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCascade()
	 * @generated
	 * @ordered
	 */
	protected CascadeType cascade;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OneToMany()
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
		return OrmPackage.Literals.ONE_TO_MANY;
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToMany_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_MANY__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Entity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Entity</em>' attribute.
	 * @see #setTargetEntity(String)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToMany_TargetEntity()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getTargetEntity()
	{
		return targetEntity;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getTargetEntity <em>Target Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Entity</em>' attribute.
	 * @see #getTargetEntity()
	 * @generated
	 */
	public void setTargetEntity(String newTargetEntity)
	{
		String oldTargetEntity = targetEntity;
		targetEntity = newTargetEntity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_MANY__TARGET_ENTITY, oldTargetEntity, targetEntity));
	}

	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' attribute.
	 * The default value is <code>"LAZY"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.resource.orm.FetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.resource.orm.FetchType
	 * @see #isSetFetch()
	 * @see #unsetFetch()
	 * @see #setFetch(FetchType)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToMany_Fetch()
	 * @model default="LAZY" unsettable="true"
	 * @generated
	 */
	public FetchType getFetch()
	{
		return fetch;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getFetch <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.resource.orm.FetchType
	 * @see #isSetFetch()
	 * @see #unsetFetch()
	 * @see #getFetch()
	 * @generated
	 */
	public void setFetch(FetchType newFetch)
	{
		FetchType oldFetch = fetch;
		fetch = newFetch == null ? FETCH_EDEFAULT : newFetch;
		boolean oldFetchESet = fetchESet;
		fetchESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_MANY__FETCH, oldFetch, fetch, !oldFetchESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getFetch <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetFetch()
	 * @see #getFetch()
	 * @see #setFetch(FetchType)
	 * @generated
	 */
	public void unsetFetch()
	{
		FetchType oldFetch = fetch;
		boolean oldFetchESet = fetchESet;
		fetch = FETCH_EDEFAULT;
		fetchESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OrmPackage.ONE_TO_MANY__FETCH, oldFetch, FETCH_EDEFAULT, oldFetchESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getFetch <em>Fetch</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Fetch</em>' attribute is set.
	 * @see #unsetFetch()
	 * @see #getFetch()
	 * @see #setFetch(FetchType)
	 * @generated
	 */
	public boolean isSetFetch()
	{
		return fetchESet;
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToMany_MappedBy()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getMappedBy()
	{
		return mappedBy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getMappedBy <em>Mapped By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapped By</em>' attribute.
	 * @see #getMappedBy()
	 * @generated
	 */
	public void setMappedBy(String newMappedBy)
	{
		String oldMappedBy = mappedBy;
		mappedBy = newMappedBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_MANY__MAPPED_BY, oldMappedBy, mappedBy));
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToMany_OrderBy()
	 * @model dataType="org.eclipse.jpt.core.internal.resource.orm.OrderBy"
	 * @generated
	 */
	public String getOrderBy()
	{
		return orderBy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getOrderBy <em>Order By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Order By</em>' attribute.
	 * @see #getOrderBy()
	 * @generated
	 */
	public void setOrderBy(String newOrderBy)
	{
		String oldOrderBy = orderBy;
		orderBy = newOrderBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_MANY__ORDER_BY, oldOrderBy, orderBy));
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToMany_MapKey()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_MANY__MAP_KEY, oldMapKey, newMapKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getMapKey <em>Map Key</em>}' containment reference.
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
				msgs = ((InternalEObject)mapKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ONE_TO_MANY__MAP_KEY, null, msgs);
			if (newMapKey != null)
				msgs = ((InternalEObject)newMapKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ONE_TO_MANY__MAP_KEY, null, msgs);
			msgs = basicSetMapKey(newMapKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_MANY__MAP_KEY, newMapKey, newMapKey));
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
	 * @see #setJoinTable(JoinTable)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToMany_JoinTable()
	 * @model containment="true"
	 * @generated
	 */
	public JoinTable getJoinTable()
	{
		return joinTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJoinTable(JoinTable newJoinTable, NotificationChain msgs)
	{
		JoinTable oldJoinTable = joinTable;
		joinTable = newJoinTable;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_MANY__JOIN_TABLE, oldJoinTable, newJoinTable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getJoinTable <em>Join Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Join Table</em>' containment reference.
	 * @see #getJoinTable()
	 * @generated
	 */
	public void setJoinTable(JoinTable newJoinTable)
	{
		if (newJoinTable != joinTable)
		{
			NotificationChain msgs = null;
			if (joinTable != null)
				msgs = ((InternalEObject)joinTable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ONE_TO_MANY__JOIN_TABLE, null, msgs);
			if (newJoinTable != null)
				msgs = ((InternalEObject)newJoinTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ONE_TO_MANY__JOIN_TABLE, null, msgs);
			msgs = basicSetJoinTable(newJoinTable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_MANY__JOIN_TABLE, newJoinTable, newJoinTable));
	}

	/**
	 * Returns the value of the '<em><b>Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.JoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToMany_JoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<JoinColumn> getJoinColumns()
	{
		if (joinColumns == null)
		{
			joinColumns = new EObjectContainmentEList<JoinColumn>(JoinColumn.class, this, OrmPackage.ONE_TO_MANY__JOIN_COLUMNS);
		}
		return joinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Cascade</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade</em>' containment reference.
	 * @see #setCascade(CascadeType)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToMany_Cascade()
	 * @model containment="true"
	 * @generated
	 */
	public CascadeType getCascade()
	{
		return cascade;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCascade(CascadeType newCascade, NotificationChain msgs)
	{
		CascadeType oldCascade = cascade;
		cascade = newCascade;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_MANY__CASCADE, oldCascade, newCascade);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToMany#getCascade <em>Cascade</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade</em>' containment reference.
	 * @see #getCascade()
	 * @generated
	 */
	public void setCascade(CascadeType newCascade)
	{
		if (newCascade != cascade)
		{
			NotificationChain msgs = null;
			if (cascade != null)
				msgs = ((InternalEObject)cascade).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ONE_TO_MANY__CASCADE, null, msgs);
			if (newCascade != null)
				msgs = ((InternalEObject)newCascade).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ONE_TO_MANY__CASCADE, null, msgs);
			msgs = basicSetCascade(newCascade, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_MANY__CASCADE, newCascade, newCascade));
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
			case OrmPackage.ONE_TO_MANY__MAP_KEY:
				return basicSetMapKey(null, msgs);
			case OrmPackage.ONE_TO_MANY__JOIN_TABLE:
				return basicSetJoinTable(null, msgs);
			case OrmPackage.ONE_TO_MANY__JOIN_COLUMNS:
				return ((InternalEList<?>)getJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.ONE_TO_MANY__CASCADE:
				return basicSetCascade(null, msgs);
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
			case OrmPackage.ONE_TO_MANY__NAME:
				return getName();
			case OrmPackage.ONE_TO_MANY__TARGET_ENTITY:
				return getTargetEntity();
			case OrmPackage.ONE_TO_MANY__FETCH:
				return getFetch();
			case OrmPackage.ONE_TO_MANY__MAPPED_BY:
				return getMappedBy();
			case OrmPackage.ONE_TO_MANY__ORDER_BY:
				return getOrderBy();
			case OrmPackage.ONE_TO_MANY__MAP_KEY:
				return getMapKey();
			case OrmPackage.ONE_TO_MANY__JOIN_TABLE:
				return getJoinTable();
			case OrmPackage.ONE_TO_MANY__JOIN_COLUMNS:
				return getJoinColumns();
			case OrmPackage.ONE_TO_MANY__CASCADE:
				return getCascade();
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
			case OrmPackage.ONE_TO_MANY__NAME:
				setName((String)newValue);
				return;
			case OrmPackage.ONE_TO_MANY__TARGET_ENTITY:
				setTargetEntity((String)newValue);
				return;
			case OrmPackage.ONE_TO_MANY__FETCH:
				setFetch((FetchType)newValue);
				return;
			case OrmPackage.ONE_TO_MANY__MAPPED_BY:
				setMappedBy((String)newValue);
				return;
			case OrmPackage.ONE_TO_MANY__ORDER_BY:
				setOrderBy((String)newValue);
				return;
			case OrmPackage.ONE_TO_MANY__MAP_KEY:
				setMapKey((MapKey)newValue);
				return;
			case OrmPackage.ONE_TO_MANY__JOIN_TABLE:
				setJoinTable((JoinTable)newValue);
				return;
			case OrmPackage.ONE_TO_MANY__JOIN_COLUMNS:
				getJoinColumns().clear();
				getJoinColumns().addAll((Collection<? extends JoinColumn>)newValue);
				return;
			case OrmPackage.ONE_TO_MANY__CASCADE:
				setCascade((CascadeType)newValue);
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
			case OrmPackage.ONE_TO_MANY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OrmPackage.ONE_TO_MANY__TARGET_ENTITY:
				setTargetEntity(TARGET_ENTITY_EDEFAULT);
				return;
			case OrmPackage.ONE_TO_MANY__FETCH:
				unsetFetch();
				return;
			case OrmPackage.ONE_TO_MANY__MAPPED_BY:
				setMappedBy(MAPPED_BY_EDEFAULT);
				return;
			case OrmPackage.ONE_TO_MANY__ORDER_BY:
				setOrderBy(ORDER_BY_EDEFAULT);
				return;
			case OrmPackage.ONE_TO_MANY__MAP_KEY:
				setMapKey((MapKey)null);
				return;
			case OrmPackage.ONE_TO_MANY__JOIN_TABLE:
				setJoinTable((JoinTable)null);
				return;
			case OrmPackage.ONE_TO_MANY__JOIN_COLUMNS:
				getJoinColumns().clear();
				return;
			case OrmPackage.ONE_TO_MANY__CASCADE:
				setCascade((CascadeType)null);
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
			case OrmPackage.ONE_TO_MANY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrmPackage.ONE_TO_MANY__TARGET_ENTITY:
				return TARGET_ENTITY_EDEFAULT == null ? targetEntity != null : !TARGET_ENTITY_EDEFAULT.equals(targetEntity);
			case OrmPackage.ONE_TO_MANY__FETCH:
				return isSetFetch();
			case OrmPackage.ONE_TO_MANY__MAPPED_BY:
				return MAPPED_BY_EDEFAULT == null ? mappedBy != null : !MAPPED_BY_EDEFAULT.equals(mappedBy);
			case OrmPackage.ONE_TO_MANY__ORDER_BY:
				return ORDER_BY_EDEFAULT == null ? orderBy != null : !ORDER_BY_EDEFAULT.equals(orderBy);
			case OrmPackage.ONE_TO_MANY__MAP_KEY:
				return mapKey != null;
			case OrmPackage.ONE_TO_MANY__JOIN_TABLE:
				return joinTable != null;
			case OrmPackage.ONE_TO_MANY__JOIN_COLUMNS:
				return joinColumns != null && !joinColumns.isEmpty();
			case OrmPackage.ONE_TO_MANY__CASCADE:
				return cascade != null;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", targetEntity: ");
		result.append(targetEntity);
		result.append(", fetch: ");
		if (fetchESet) result.append(fetch); else result.append("<unset>");
		result.append(", mappedBy: ");
		result.append(mappedBy);
		result.append(", orderBy: ");
		result.append(orderBy);
		result.append(')');
		return result.toString();
	}

} // OneToMany
