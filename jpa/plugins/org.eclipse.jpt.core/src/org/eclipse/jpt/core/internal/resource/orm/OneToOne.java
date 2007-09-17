/**
 * <copyright>
 * </copyright>
 *
 * $Id: OneToOne.java,v 1.1.2.1 2007/09/17 20:49:51 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.jpt.core.internal.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>One To One</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getTargetEntity <em>Target Entity</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getFetch <em>Fetch</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#isOptional <em>Optional</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getMappedBy <em>Mapped By</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getPrimaryKeyJoinColumns <em>Primary Key Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getJoinColumns <em>Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getJoinTable <em>Join Table</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getCascade <em>Cascade</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToOne()
 * @model kind="class"
 * @generated
 */
public class OneToOne extends JpaEObject implements EObject
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
	 * The default value of the '{@link #isOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOptional()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OPTIONAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOptional() <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOptional()
	 * @generated
	 * @ordered
	 */
	protected boolean optional = OPTIONAL_EDEFAULT;

	/**
	 * This is true if the Optional attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean optionalESet;

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
	 * The cached value of the '{@link #getPrimaryKeyJoinColumns() <em>Primary Key Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryKeyJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<PrimaryKeyJoinColumn> primaryKeyJoinColumns;

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
	 * The cached value of the '{@link #getJoinTable() <em>Join Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinTable()
	 * @generated
	 * @ordered
	 */
	protected JoinTable joinTable;

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
	protected OneToOne()
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
		return OrmPackage.Literals.ONE_TO_ONE;
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToOne_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_ONE__NAME, oldName, name));
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToOne_TargetEntity()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getTargetEntity()
	{
		return targetEntity;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getTargetEntity <em>Target Entity</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_ONE__TARGET_ENTITY, oldTargetEntity, targetEntity));
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToOne_Fetch()
	 * @model default="LAZY" unsettable="true"
	 * @generated
	 */
	public FetchType getFetch()
	{
		return fetch;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getFetch <em>Fetch</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_ONE__FETCH, oldFetch, fetch, !oldFetchESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getFetch <em>Fetch</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, OrmPackage.ONE_TO_ONE__FETCH, oldFetch, FETCH_EDEFAULT, oldFetchESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getFetch <em>Fetch</em>}' attribute is set.
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
	 * Returns the value of the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional</em>' attribute.
	 * @see #isSetOptional()
	 * @see #unsetOptional()
	 * @see #setOptional(boolean)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToOne_Optional()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isOptional()
	{
		return optional;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#isOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional</em>' attribute.
	 * @see #isSetOptional()
	 * @see #unsetOptional()
	 * @see #isOptional()
	 * @generated
	 */
	public void setOptional(boolean newOptional)
	{
		boolean oldOptional = optional;
		optional = newOptional;
		boolean oldOptionalESet = optionalESet;
		optionalESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_ONE__OPTIONAL, oldOptional, optional, !oldOptionalESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#isOptional <em>Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOptional()
	 * @see #isOptional()
	 * @see #setOptional(boolean)
	 * @generated
	 */
	public void unsetOptional()
	{
		boolean oldOptional = optional;
		boolean oldOptionalESet = optionalESet;
		optional = OPTIONAL_EDEFAULT;
		optionalESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OrmPackage.ONE_TO_ONE__OPTIONAL, oldOptional, OPTIONAL_EDEFAULT, oldOptionalESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#isOptional <em>Optional</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Optional</em>' attribute is set.
	 * @see #unsetOptional()
	 * @see #isOptional()
	 * @see #setOptional(boolean)
	 * @generated
	 */
	public boolean isSetOptional()
	{
		return optionalESet;
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToOne_MappedBy()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getMappedBy()
	{
		return mappedBy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getMappedBy <em>Mapped By</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_ONE__MAPPED_BY, oldMappedBy, mappedBy));
	}

	/**
	 * Returns the value of the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.PrimaryKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToOne_PrimaryKeyJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<PrimaryKeyJoinColumn> getPrimaryKeyJoinColumns()
	{
		if (primaryKeyJoinColumns == null)
		{
			primaryKeyJoinColumns = new EObjectContainmentEList<PrimaryKeyJoinColumn>(PrimaryKeyJoinColumn.class, this, OrmPackage.ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS);
		}
		return primaryKeyJoinColumns;
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToOne_JoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<JoinColumn> getJoinColumns()
	{
		if (joinColumns == null)
		{
			joinColumns = new EObjectContainmentEList<JoinColumn>(JoinColumn.class, this, OrmPackage.ONE_TO_ONE__JOIN_COLUMNS);
		}
		return joinColumns;
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToOne_JoinTable()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_ONE__JOIN_TABLE, oldJoinTable, newJoinTable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getJoinTable <em>Join Table</em>}' containment reference.
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
				msgs = ((InternalEObject)joinTable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ONE_TO_ONE__JOIN_TABLE, null, msgs);
			if (newJoinTable != null)
				msgs = ((InternalEObject)newJoinTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ONE_TO_ONE__JOIN_TABLE, null, msgs);
			msgs = basicSetJoinTable(newJoinTable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_ONE__JOIN_TABLE, newJoinTable, newJoinTable));
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getOneToOne_Cascade()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_ONE__CASCADE, oldCascade, newCascade);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.OneToOne#getCascade <em>Cascade</em>}' containment reference.
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
				msgs = ((InternalEObject)cascade).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ONE_TO_ONE__CASCADE, null, msgs);
			if (newCascade != null)
				msgs = ((InternalEObject)newCascade).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ONE_TO_ONE__CASCADE, null, msgs);
			msgs = basicSetCascade(newCascade, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ONE_TO_ONE__CASCADE, newCascade, newCascade));
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
			case OrmPackage.ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS:
				return ((InternalEList<?>)getPrimaryKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.ONE_TO_ONE__JOIN_COLUMNS:
				return ((InternalEList<?>)getJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.ONE_TO_ONE__JOIN_TABLE:
				return basicSetJoinTable(null, msgs);
			case OrmPackage.ONE_TO_ONE__CASCADE:
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
			case OrmPackage.ONE_TO_ONE__NAME:
				return getName();
			case OrmPackage.ONE_TO_ONE__TARGET_ENTITY:
				return getTargetEntity();
			case OrmPackage.ONE_TO_ONE__FETCH:
				return getFetch();
			case OrmPackage.ONE_TO_ONE__OPTIONAL:
				return isOptional() ? Boolean.TRUE : Boolean.FALSE;
			case OrmPackage.ONE_TO_ONE__MAPPED_BY:
				return getMappedBy();
			case OrmPackage.ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS:
				return getPrimaryKeyJoinColumns();
			case OrmPackage.ONE_TO_ONE__JOIN_COLUMNS:
				return getJoinColumns();
			case OrmPackage.ONE_TO_ONE__JOIN_TABLE:
				return getJoinTable();
			case OrmPackage.ONE_TO_ONE__CASCADE:
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
			case OrmPackage.ONE_TO_ONE__NAME:
				setName((String)newValue);
				return;
			case OrmPackage.ONE_TO_ONE__TARGET_ENTITY:
				setTargetEntity((String)newValue);
				return;
			case OrmPackage.ONE_TO_ONE__FETCH:
				setFetch((FetchType)newValue);
				return;
			case OrmPackage.ONE_TO_ONE__OPTIONAL:
				setOptional(((Boolean)newValue).booleanValue());
				return;
			case OrmPackage.ONE_TO_ONE__MAPPED_BY:
				setMappedBy((String)newValue);
				return;
			case OrmPackage.ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS:
				getPrimaryKeyJoinColumns().clear();
				getPrimaryKeyJoinColumns().addAll((Collection<? extends PrimaryKeyJoinColumn>)newValue);
				return;
			case OrmPackage.ONE_TO_ONE__JOIN_COLUMNS:
				getJoinColumns().clear();
				getJoinColumns().addAll((Collection<? extends JoinColumn>)newValue);
				return;
			case OrmPackage.ONE_TO_ONE__JOIN_TABLE:
				setJoinTable((JoinTable)newValue);
				return;
			case OrmPackage.ONE_TO_ONE__CASCADE:
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
			case OrmPackage.ONE_TO_ONE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OrmPackage.ONE_TO_ONE__TARGET_ENTITY:
				setTargetEntity(TARGET_ENTITY_EDEFAULT);
				return;
			case OrmPackage.ONE_TO_ONE__FETCH:
				unsetFetch();
				return;
			case OrmPackage.ONE_TO_ONE__OPTIONAL:
				unsetOptional();
				return;
			case OrmPackage.ONE_TO_ONE__MAPPED_BY:
				setMappedBy(MAPPED_BY_EDEFAULT);
				return;
			case OrmPackage.ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS:
				getPrimaryKeyJoinColumns().clear();
				return;
			case OrmPackage.ONE_TO_ONE__JOIN_COLUMNS:
				getJoinColumns().clear();
				return;
			case OrmPackage.ONE_TO_ONE__JOIN_TABLE:
				setJoinTable((JoinTable)null);
				return;
			case OrmPackage.ONE_TO_ONE__CASCADE:
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
			case OrmPackage.ONE_TO_ONE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrmPackage.ONE_TO_ONE__TARGET_ENTITY:
				return TARGET_ENTITY_EDEFAULT == null ? targetEntity != null : !TARGET_ENTITY_EDEFAULT.equals(targetEntity);
			case OrmPackage.ONE_TO_ONE__FETCH:
				return isSetFetch();
			case OrmPackage.ONE_TO_ONE__OPTIONAL:
				return isSetOptional();
			case OrmPackage.ONE_TO_ONE__MAPPED_BY:
				return MAPPED_BY_EDEFAULT == null ? mappedBy != null : !MAPPED_BY_EDEFAULT.equals(mappedBy);
			case OrmPackage.ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS:
				return primaryKeyJoinColumns != null && !primaryKeyJoinColumns.isEmpty();
			case OrmPackage.ONE_TO_ONE__JOIN_COLUMNS:
				return joinColumns != null && !joinColumns.isEmpty();
			case OrmPackage.ONE_TO_ONE__JOIN_TABLE:
				return joinTable != null;
			case OrmPackage.ONE_TO_ONE__CASCADE:
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
		result.append(", optional: ");
		if (optionalESet) result.append(optional); else result.append("<unset>");
		result.append(", mappedBy: ");
		result.append(mappedBy);
		result.append(')');
		return result.toString();
	}

} // OneToOne
