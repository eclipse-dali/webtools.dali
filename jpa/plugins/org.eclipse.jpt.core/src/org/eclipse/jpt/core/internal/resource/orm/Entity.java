/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getTable <em>Table</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getSecondaryTables <em>Secondary Tables</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPrimaryKeyJoinColumns <em>Primary Key Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getIdClass <em>Id Class</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getInheritance <em>Inheritance</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getDiscriminatorValue <em>Discriminator Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getDiscriminatorColumn <em>Discriminator Column</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getSequenceGenerator <em>Sequence Generator</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getTableGenerator <em>Table Generator</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getNamedQueries <em>Named Queries</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getNamedNativeQueries <em>Named Native Queries</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getSqlResultSetMappings <em>Sql Result Set Mappings</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#isExcludeDefaultListeners <em>Exclude Default Listeners</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#isExcludeSuperclassListeners <em>Exclude Superclass Listeners</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getEntityListeners <em>Entity Listeners</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPrePersist <em>Pre Persist</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPostPersist <em>Post Persist</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPreRemove <em>Pre Remove</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPostRemove <em>Post Remove</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPreUpdate <em>Pre Update</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPostUpdate <em>Post Update</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPostLoad <em>Post Load</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getAttributeOverrides <em>Attribute Overrides</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getAssociationOverrides <em>Association Overrides</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity()
 * @model kind="class"
 * @generated
 */
public class Entity extends TypeMapping
{
	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final AccessType ACCESS_EDEFAULT = null;

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
	 * The cached value of the '{@link #getTable() <em>Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTable()
	 * @generated
	 * @ordered
	 */
	protected Table table;

	/**
	 * The cached value of the '{@link #getSecondaryTables() <em>Secondary Tables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryTables()
	 * @generated
	 * @ordered
	 */
	protected EList<SecondaryTable> secondaryTables;

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
	 * The cached value of the '{@link #getIdClass() <em>Id Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdClass()
	 * @generated
	 * @ordered
	 */
	protected IdClass idClass;

	/**
	 * The cached value of the '{@link #getInheritance() <em>Inheritance</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInheritance()
	 * @generated
	 * @ordered
	 */
	protected Inheritance inheritance;

	/**
	 * The default value of the '{@link #getDiscriminatorValue() <em>Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DISCRIMINATOR_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDiscriminatorValue() <em>Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorValue()
	 * @generated
	 * @ordered
	 */
	protected String discriminatorValue = DISCRIMINATOR_VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDiscriminatorColumn() <em>Discriminator Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorColumn()
	 * @generated
	 * @ordered
	 */
	protected DiscriminatorColumn discriminatorColumn;

	/**
	 * The cached value of the '{@link #getSequenceGenerator() <em>Sequence Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSequenceGenerator()
	 * @generated
	 * @ordered
	 */
	protected SequenceGenerator sequenceGenerator;

	/**
	 * The cached value of the '{@link #getTableGenerator() <em>Table Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTableGenerator()
	 * @generated
	 * @ordered
	 */
	protected TableGenerator tableGenerator;

	/**
	 * The cached value of the '{@link #getNamedQueries() <em>Named Queries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamedQueries()
	 * @generated
	 * @ordered
	 */
	protected EList<NamedQuery> namedQueries;

	/**
	 * The cached value of the '{@link #getNamedNativeQueries() <em>Named Native Queries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamedNativeQueries()
	 * @generated
	 * @ordered
	 */
	protected EList<NamedNativeQuery> namedNativeQueries;

	/**
	 * The cached value of the '{@link #getSqlResultSetMappings() <em>Sql Result Set Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSqlResultSetMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<SqlResultSetMapping> sqlResultSetMappings;

	/**
	 * The default value of the '{@link #isExcludeDefaultListeners() <em>Exclude Default Listeners</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExcludeDefaultListeners()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXCLUDE_DEFAULT_LISTENERS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExcludeDefaultListeners() <em>Exclude Default Listeners</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExcludeDefaultListeners()
	 * @generated
	 * @ordered
	 */
	protected boolean excludeDefaultListeners = EXCLUDE_DEFAULT_LISTENERS_EDEFAULT;

	/**
	 * The default value of the '{@link #isExcludeSuperclassListeners() <em>Exclude Superclass Listeners</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExcludeSuperclassListeners()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXCLUDE_SUPERCLASS_LISTENERS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExcludeSuperclassListeners() <em>Exclude Superclass Listeners</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExcludeSuperclassListeners()
	 * @generated
	 * @ordered
	 */
	protected boolean excludeSuperclassListeners = EXCLUDE_SUPERCLASS_LISTENERS_EDEFAULT;

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
	 * The cached value of the '{@link #getPrePersist() <em>Pre Persist</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrePersist()
	 * @generated
	 * @ordered
	 */
	protected PrePersist prePersist;

	/**
	 * The cached value of the '{@link #getPostPersist() <em>Post Persist</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostPersist()
	 * @generated
	 * @ordered
	 */
	protected PostPersist postPersist;

	/**
	 * The cached value of the '{@link #getPreRemove() <em>Pre Remove</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreRemove()
	 * @generated
	 * @ordered
	 */
	protected PreRemove preRemove;

	/**
	 * The cached value of the '{@link #getPostRemove() <em>Post Remove</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostRemove()
	 * @generated
	 * @ordered
	 */
	protected PostRemove postRemove;

	/**
	 * The cached value of the '{@link #getPreUpdate() <em>Pre Update</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreUpdate()
	 * @generated
	 * @ordered
	 */
	protected PreUpdate preUpdate;

	/**
	 * The cached value of the '{@link #getPostUpdate() <em>Post Update</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostUpdate()
	 * @generated
	 * @ordered
	 */
	protected PostUpdate postUpdate;

	/**
	 * The cached value of the '{@link #getPostLoad() <em>Post Load</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostLoad()
	 * @generated
	 * @ordered
	 */
	protected PostLoad postLoad;

	/**
	 * The cached value of the '{@link #getAttributeOverrides() <em>Attribute Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<AttributeOverride> attributeOverrides;

	/**
	 * The cached value of the '{@link #getAssociationOverrides() <em>Association Overrides</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssociationOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<AssociationOverride> associationOverrides;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Entity()
	{
		super();
	}

	public EntityMappings entityMappings() {
		return (EntityMappings) eContainer();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return OrmPackage.Literals.ENTITY;
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table</em>' containment reference.
	 * @see #setTable(Table)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_Table()
	 * @model containment="true"
	 * @generated
	 */
	public Table getTable()
	{
		return table;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTable(Table newTable, NotificationChain msgs)
	{
		Table oldTable = table;
		table = newTable;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__TABLE, oldTable, newTable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getTable <em>Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table</em>' containment reference.
	 * @see #getTable()
	 * @generated
	 */
	public void setTable(Table newTable)
	{
		if (newTable != table)
		{
			NotificationChain msgs = null;
			if (table != null)
				msgs = ((InternalEObject)table).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__TABLE, null, msgs);
			if (newTable != null)
				msgs = ((InternalEObject)newTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__TABLE, null, msgs);
			msgs = basicSetTable(newTable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__TABLE, newTable, newTable));
	}

	/**
	 * Returns the value of the '<em><b>Secondary Tables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.SecondaryTable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Secondary Tables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Secondary Tables</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_SecondaryTables()
	 * @model containment="true"
	 * @generated
	 */
	public EList<SecondaryTable> getSecondaryTables()
	{
		if (secondaryTables == null)
		{
			secondaryTables = new EObjectContainmentEList<SecondaryTable>(SecondaryTable.class, this, OrmPackage.ENTITY__SECONDARY_TABLES);
		}
		return secondaryTables;
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_PrimaryKeyJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<PrimaryKeyJoinColumn> getPrimaryKeyJoinColumns()
	{
		if (primaryKeyJoinColumns == null)
		{
			primaryKeyJoinColumns = new EObjectContainmentEList<PrimaryKeyJoinColumn>(PrimaryKeyJoinColumn.class, this, OrmPackage.ENTITY__PRIMARY_KEY_JOIN_COLUMNS);
		}
		return primaryKeyJoinColumns;
	}

	/**
	 * Returns the value of the '<em><b>Id Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id Class</em>' containment reference.
	 * @see #setIdClass(IdClass)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_IdClass()
	 * @model containment="true"
	 * @generated
	 */
	public IdClass getIdClass()
	{
		return idClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIdClass(IdClass newIdClass, NotificationChain msgs)
	{
		IdClass oldIdClass = idClass;
		idClass = newIdClass;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__ID_CLASS, oldIdClass, newIdClass);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getIdClass <em>Id Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Class</em>' containment reference.
	 * @see #getIdClass()
	 * @generated
	 */
	public void setIdClass(IdClass newIdClass)
	{
		if (newIdClass != idClass)
		{
			NotificationChain msgs = null;
			if (idClass != null)
				msgs = ((InternalEObject)idClass).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__ID_CLASS, null, msgs);
			if (newIdClass != null)
				msgs = ((InternalEObject)newIdClass).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__ID_CLASS, null, msgs);
			msgs = basicSetIdClass(newIdClass, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__ID_CLASS, newIdClass, newIdClass));
	}

	/**
	 * Returns the value of the '<em><b>Inheritance</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inheritance</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inheritance</em>' containment reference.
	 * @see #setInheritance(Inheritance)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_Inheritance()
	 * @model containment="true"
	 * @generated
	 */
	public Inheritance getInheritance()
	{
		return inheritance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInheritance(Inheritance newInheritance, NotificationChain msgs)
	{
		Inheritance oldInheritance = inheritance;
		inheritance = newInheritance;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__INHERITANCE, oldInheritance, newInheritance);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getInheritance <em>Inheritance</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inheritance</em>' containment reference.
	 * @see #getInheritance()
	 * @generated
	 */
	public void setInheritance(Inheritance newInheritance)
	{
		if (newInheritance != inheritance)
		{
			NotificationChain msgs = null;
			if (inheritance != null)
				msgs = ((InternalEObject)inheritance).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__INHERITANCE, null, msgs);
			if (newInheritance != null)
				msgs = ((InternalEObject)newInheritance).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__INHERITANCE, null, msgs);
			msgs = basicSetInheritance(newInheritance, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__INHERITANCE, newInheritance, newInheritance));
	}

	/**
	 * Returns the value of the '<em><b>Discriminator Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discriminator Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discriminator Value</em>' attribute.
	 * @see #setDiscriminatorValue(String)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_DiscriminatorValue()
	 * @model dataType="org.eclipse.jpt.core.internal.resource.orm.DiscriminatorValue"
	 * @generated
	 */
	public String getDiscriminatorValue()
	{
		return discriminatorValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getDiscriminatorValue <em>Discriminator Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discriminator Value</em>' attribute.
	 * @see #getDiscriminatorValue()
	 * @generated
	 */
	public void setDiscriminatorValue(String newDiscriminatorValue)
	{
		String oldDiscriminatorValue = discriminatorValue;
		discriminatorValue = newDiscriminatorValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__DISCRIMINATOR_VALUE, oldDiscriminatorValue, discriminatorValue));
	}

	/**
	 * Returns the value of the '<em><b>Discriminator Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discriminator Column</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discriminator Column</em>' containment reference.
	 * @see #setDiscriminatorColumn(DiscriminatorColumn)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_DiscriminatorColumn()
	 * @model containment="true"
	 * @generated
	 */
	public DiscriminatorColumn getDiscriminatorColumn()
	{
		return discriminatorColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDiscriminatorColumn(DiscriminatorColumn newDiscriminatorColumn, NotificationChain msgs)
	{
		DiscriminatorColumn oldDiscriminatorColumn = discriminatorColumn;
		discriminatorColumn = newDiscriminatorColumn;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__DISCRIMINATOR_COLUMN, oldDiscriminatorColumn, newDiscriminatorColumn);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getDiscriminatorColumn <em>Discriminator Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discriminator Column</em>' containment reference.
	 * @see #getDiscriminatorColumn()
	 * @generated
	 */
	public void setDiscriminatorColumn(DiscriminatorColumn newDiscriminatorColumn)
	{
		if (newDiscriminatorColumn != discriminatorColumn)
		{
			NotificationChain msgs = null;
			if (discriminatorColumn != null)
				msgs = ((InternalEObject)discriminatorColumn).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__DISCRIMINATOR_COLUMN, null, msgs);
			if (newDiscriminatorColumn != null)
				msgs = ((InternalEObject)newDiscriminatorColumn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__DISCRIMINATOR_COLUMN, null, msgs);
			msgs = basicSetDiscriminatorColumn(newDiscriminatorColumn, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__DISCRIMINATOR_COLUMN, newDiscriminatorColumn, newDiscriminatorColumn));
	}

	/**
	 * Returns the value of the '<em><b>Sequence Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sequence Generator</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sequence Generator</em>' containment reference.
	 * @see #setSequenceGenerator(SequenceGenerator)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_SequenceGenerator()
	 * @model containment="true"
	 * @generated
	 */
	public SequenceGenerator getSequenceGenerator()
	{
		return sequenceGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSequenceGenerator(SequenceGenerator newSequenceGenerator, NotificationChain msgs)
	{
		SequenceGenerator oldSequenceGenerator = sequenceGenerator;
		sequenceGenerator = newSequenceGenerator;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__SEQUENCE_GENERATOR, oldSequenceGenerator, newSequenceGenerator);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getSequenceGenerator <em>Sequence Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sequence Generator</em>' containment reference.
	 * @see #getSequenceGenerator()
	 * @generated
	 */
	public void setSequenceGenerator(SequenceGenerator newSequenceGenerator)
	{
		if (newSequenceGenerator != sequenceGenerator)
		{
			NotificationChain msgs = null;
			if (sequenceGenerator != null)
				msgs = ((InternalEObject)sequenceGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__SEQUENCE_GENERATOR, null, msgs);
			if (newSequenceGenerator != null)
				msgs = ((InternalEObject)newSequenceGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__SEQUENCE_GENERATOR, null, msgs);
			msgs = basicSetSequenceGenerator(newSequenceGenerator, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__SEQUENCE_GENERATOR, newSequenceGenerator, newSequenceGenerator));
	}

	/**
	 * Returns the value of the '<em><b>Table Generator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table Generator</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table Generator</em>' containment reference.
	 * @see #setTableGenerator(TableGenerator)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_TableGenerator()
	 * @model containment="true"
	 * @generated
	 */
	public TableGenerator getTableGenerator()
	{
		return tableGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTableGenerator(TableGenerator newTableGenerator, NotificationChain msgs)
	{
		TableGenerator oldTableGenerator = tableGenerator;
		tableGenerator = newTableGenerator;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__TABLE_GENERATOR, oldTableGenerator, newTableGenerator);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getTableGenerator <em>Table Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table Generator</em>' containment reference.
	 * @see #getTableGenerator()
	 * @generated
	 */
	public void setTableGenerator(TableGenerator newTableGenerator)
	{
		if (newTableGenerator != tableGenerator)
		{
			NotificationChain msgs = null;
			if (tableGenerator != null)
				msgs = ((InternalEObject)tableGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__TABLE_GENERATOR, null, msgs);
			if (newTableGenerator != null)
				msgs = ((InternalEObject)newTableGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__TABLE_GENERATOR, null, msgs);
			msgs = basicSetTableGenerator(newTableGenerator, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__TABLE_GENERATOR, newTableGenerator, newTableGenerator));
	}

	/**
	 * Returns the value of the '<em><b>Named Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.NamedQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_NamedQueries()
	 * @model containment="true"
	 * @generated
	 */
	public EList<NamedQuery> getNamedQueries()
	{
		if (namedQueries == null)
		{
			namedQueries = new EObjectContainmentEList<NamedQuery>(NamedQuery.class, this, OrmPackage.ENTITY__NAMED_QUERIES);
		}
		return namedQueries;
	}

	/**
	 * Returns the value of the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.NamedNativeQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Native Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Native Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_NamedNativeQueries()
	 * @model containment="true"
	 * @generated
	 */
	public EList<NamedNativeQuery> getNamedNativeQueries()
	{
		if (namedNativeQueries == null)
		{
			namedNativeQueries = new EObjectContainmentEList<NamedNativeQuery>(NamedNativeQuery.class, this, OrmPackage.ENTITY__NAMED_NATIVE_QUERIES);
		}
		return namedNativeQueries;
	}

	/**
	 * Returns the value of the '<em><b>Sql Result Set Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.SqlResultSetMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sql Result Set Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sql Result Set Mappings</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_SqlResultSetMappings()
	 * @model containment="true"
	 * @generated
	 */
	public EList<SqlResultSetMapping> getSqlResultSetMappings()
	{
		if (sqlResultSetMappings == null)
		{
			sqlResultSetMappings = new EObjectContainmentEList<SqlResultSetMapping>(SqlResultSetMapping.class, this, OrmPackage.ENTITY__SQL_RESULT_SET_MAPPINGS);
		}
		return sqlResultSetMappings;
	}

	/**
	 * Returns the value of the '<em><b>Exclude Default Listeners</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exclude Default Listeners</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exclude Default Listeners</em>' attribute.
	 * @see #setExcludeDefaultListeners(boolean)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_ExcludeDefaultListeners()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isExcludeDefaultListeners()
	{
		return excludeDefaultListeners;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#isExcludeDefaultListeners <em>Exclude Default Listeners</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exclude Default Listeners</em>' attribute.
	 * @see #isExcludeDefaultListeners()
	 * @generated
	 */
	public void setExcludeDefaultListeners(boolean newExcludeDefaultListeners)
	{
		boolean oldExcludeDefaultListeners = excludeDefaultListeners;
		excludeDefaultListeners = newExcludeDefaultListeners;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__EXCLUDE_DEFAULT_LISTENERS, oldExcludeDefaultListeners, excludeDefaultListeners));
	}

	/**
	 * Returns the value of the '<em><b>Exclude Superclass Listeners</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exclude Superclass Listeners</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exclude Superclass Listeners</em>' attribute.
	 * @see #setExcludeSuperclassListeners(boolean)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_ExcludeSuperclassListeners()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isExcludeSuperclassListeners()
	{
		return excludeSuperclassListeners;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#isExcludeSuperclassListeners <em>Exclude Superclass Listeners</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exclude Superclass Listeners</em>' attribute.
	 * @see #isExcludeSuperclassListeners()
	 * @generated
	 */
	public void setExcludeSuperclassListeners(boolean newExcludeSuperclassListeners)
	{
		boolean oldExcludeSuperclassListeners = excludeSuperclassListeners;
		excludeSuperclassListeners = newExcludeSuperclassListeners;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__EXCLUDE_SUPERCLASS_LISTENERS, oldExcludeSuperclassListeners, excludeSuperclassListeners));
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_EntityListeners()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__ENTITY_LISTENERS, oldEntityListeners, newEntityListeners);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getEntityListeners <em>Entity Listeners</em>}' containment reference.
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
				msgs = ((InternalEObject)entityListeners).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__ENTITY_LISTENERS, null, msgs);
			if (newEntityListeners != null)
				msgs = ((InternalEObject)newEntityListeners).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__ENTITY_LISTENERS, null, msgs);
			msgs = basicSetEntityListeners(newEntityListeners, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__ENTITY_LISTENERS, newEntityListeners, newEntityListeners));
	}

	/**
	 * Returns the value of the '<em><b>Pre Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Persist</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Persist</em>' containment reference.
	 * @see #setPrePersist(PrePersist)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_PrePersist()
	 * @model containment="true"
	 * @generated
	 */
	public PrePersist getPrePersist()
	{
		return prePersist;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrePersist(PrePersist newPrePersist, NotificationChain msgs)
	{
		PrePersist oldPrePersist = prePersist;
		prePersist = newPrePersist;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__PRE_PERSIST, oldPrePersist, newPrePersist);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPrePersist <em>Pre Persist</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Persist</em>' containment reference.
	 * @see #getPrePersist()
	 * @generated
	 */
	public void setPrePersist(PrePersist newPrePersist)
	{
		if (newPrePersist != prePersist)
		{
			NotificationChain msgs = null;
			if (prePersist != null)
				msgs = ((InternalEObject)prePersist).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__PRE_PERSIST, null, msgs);
			if (newPrePersist != null)
				msgs = ((InternalEObject)newPrePersist).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__PRE_PERSIST, null, msgs);
			msgs = basicSetPrePersist(newPrePersist, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__PRE_PERSIST, newPrePersist, newPrePersist));
	}

	/**
	 * Returns the value of the '<em><b>Post Persist</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Post Persist</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Post Persist</em>' containment reference.
	 * @see #setPostPersist(PostPersist)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_PostPersist()
	 * @model containment="true"
	 * @generated
	 */
	public PostPersist getPostPersist()
	{
		return postPersist;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPostPersist(PostPersist newPostPersist, NotificationChain msgs)
	{
		PostPersist oldPostPersist = postPersist;
		postPersist = newPostPersist;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__POST_PERSIST, oldPostPersist, newPostPersist);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPostPersist <em>Post Persist</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post Persist</em>' containment reference.
	 * @see #getPostPersist()
	 * @generated
	 */
	public void setPostPersist(PostPersist newPostPersist)
	{
		if (newPostPersist != postPersist)
		{
			NotificationChain msgs = null;
			if (postPersist != null)
				msgs = ((InternalEObject)postPersist).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__POST_PERSIST, null, msgs);
			if (newPostPersist != null)
				msgs = ((InternalEObject)newPostPersist).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__POST_PERSIST, null, msgs);
			msgs = basicSetPostPersist(newPostPersist, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__POST_PERSIST, newPostPersist, newPostPersist));
	}

	/**
	 * Returns the value of the '<em><b>Pre Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Remove</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Remove</em>' containment reference.
	 * @see #setPreRemove(PreRemove)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_PreRemove()
	 * @model containment="true"
	 * @generated
	 */
	public PreRemove getPreRemove()
	{
		return preRemove;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreRemove(PreRemove newPreRemove, NotificationChain msgs)
	{
		PreRemove oldPreRemove = preRemove;
		preRemove = newPreRemove;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__PRE_REMOVE, oldPreRemove, newPreRemove);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPreRemove <em>Pre Remove</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Remove</em>' containment reference.
	 * @see #getPreRemove()
	 * @generated
	 */
	public void setPreRemove(PreRemove newPreRemove)
	{
		if (newPreRemove != preRemove)
		{
			NotificationChain msgs = null;
			if (preRemove != null)
				msgs = ((InternalEObject)preRemove).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__PRE_REMOVE, null, msgs);
			if (newPreRemove != null)
				msgs = ((InternalEObject)newPreRemove).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__PRE_REMOVE, null, msgs);
			msgs = basicSetPreRemove(newPreRemove, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__PRE_REMOVE, newPreRemove, newPreRemove));
	}

	/**
	 * Returns the value of the '<em><b>Post Remove</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Post Remove</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Post Remove</em>' containment reference.
	 * @see #setPostRemove(PostRemove)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_PostRemove()
	 * @model containment="true"
	 * @generated
	 */
	public PostRemove getPostRemove()
	{
		return postRemove;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPostRemove(PostRemove newPostRemove, NotificationChain msgs)
	{
		PostRemove oldPostRemove = postRemove;
		postRemove = newPostRemove;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__POST_REMOVE, oldPostRemove, newPostRemove);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPostRemove <em>Post Remove</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post Remove</em>' containment reference.
	 * @see #getPostRemove()
	 * @generated
	 */
	public void setPostRemove(PostRemove newPostRemove)
	{
		if (newPostRemove != postRemove)
		{
			NotificationChain msgs = null;
			if (postRemove != null)
				msgs = ((InternalEObject)postRemove).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__POST_REMOVE, null, msgs);
			if (newPostRemove != null)
				msgs = ((InternalEObject)newPostRemove).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__POST_REMOVE, null, msgs);
			msgs = basicSetPostRemove(newPostRemove, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__POST_REMOVE, newPostRemove, newPostRemove));
	}

	/**
	 * Returns the value of the '<em><b>Pre Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Update</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Update</em>' containment reference.
	 * @see #setPreUpdate(PreUpdate)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_PreUpdate()
	 * @model containment="true"
	 * @generated
	 */
	public PreUpdate getPreUpdate()
	{
		return preUpdate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreUpdate(PreUpdate newPreUpdate, NotificationChain msgs)
	{
		PreUpdate oldPreUpdate = preUpdate;
		preUpdate = newPreUpdate;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__PRE_UPDATE, oldPreUpdate, newPreUpdate);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPreUpdate <em>Pre Update</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Update</em>' containment reference.
	 * @see #getPreUpdate()
	 * @generated
	 */
	public void setPreUpdate(PreUpdate newPreUpdate)
	{
		if (newPreUpdate != preUpdate)
		{
			NotificationChain msgs = null;
			if (preUpdate != null)
				msgs = ((InternalEObject)preUpdate).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__PRE_UPDATE, null, msgs);
			if (newPreUpdate != null)
				msgs = ((InternalEObject)newPreUpdate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__PRE_UPDATE, null, msgs);
			msgs = basicSetPreUpdate(newPreUpdate, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__PRE_UPDATE, newPreUpdate, newPreUpdate));
	}

	/**
	 * Returns the value of the '<em><b>Post Update</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Post Update</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Post Update</em>' containment reference.
	 * @see #setPostUpdate(PostUpdate)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_PostUpdate()
	 * @model containment="true"
	 * @generated
	 */
	public PostUpdate getPostUpdate()
	{
		return postUpdate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPostUpdate(PostUpdate newPostUpdate, NotificationChain msgs)
	{
		PostUpdate oldPostUpdate = postUpdate;
		postUpdate = newPostUpdate;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__POST_UPDATE, oldPostUpdate, newPostUpdate);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPostUpdate <em>Post Update</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post Update</em>' containment reference.
	 * @see #getPostUpdate()
	 * @generated
	 */
	public void setPostUpdate(PostUpdate newPostUpdate)
	{
		if (newPostUpdate != postUpdate)
		{
			NotificationChain msgs = null;
			if (postUpdate != null)
				msgs = ((InternalEObject)postUpdate).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__POST_UPDATE, null, msgs);
			if (newPostUpdate != null)
				msgs = ((InternalEObject)newPostUpdate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__POST_UPDATE, null, msgs);
			msgs = basicSetPostUpdate(newPostUpdate, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__POST_UPDATE, newPostUpdate, newPostUpdate));
	}

	/**
	 * Returns the value of the '<em><b>Post Load</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Post Load</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Post Load</em>' containment reference.
	 * @see #setPostLoad(PostLoad)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_PostLoad()
	 * @model containment="true"
	 * @generated
	 */
	public PostLoad getPostLoad()
	{
		return postLoad;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPostLoad(PostLoad newPostLoad, NotificationChain msgs)
	{
		PostLoad oldPostLoad = postLoad;
		postLoad = newPostLoad;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__POST_LOAD, oldPostLoad, newPostLoad);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.Entity#getPostLoad <em>Post Load</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Post Load</em>' containment reference.
	 * @see #getPostLoad()
	 * @generated
	 */
	public void setPostLoad(PostLoad newPostLoad)
	{
		if (newPostLoad != postLoad)
		{
			NotificationChain msgs = null;
			if (postLoad != null)
				msgs = ((InternalEObject)postLoad).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__POST_LOAD, null, msgs);
			if (newPostLoad != null)
				msgs = ((InternalEObject)newPostLoad).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY__POST_LOAD, null, msgs);
			msgs = basicSetPostLoad(newPostLoad, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY__POST_LOAD, newPostLoad, newPostLoad));
	}

	/**
	 * Returns the value of the '<em><b>Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.AttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_AttributeOverrides()
	 * @model containment="true"
	 * @generated
	 */
	public EList<AttributeOverride> getAttributeOverrides()
	{
		if (attributeOverrides == null)
		{
			attributeOverrides = new EObjectContainmentEList<AttributeOverride>(AttributeOverride.class, this, OrmPackage.ENTITY__ATTRIBUTE_OVERRIDES);
		}
		return attributeOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Association Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.AssociationOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Association Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Association Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntity_AssociationOverrides()
	 * @model containment="true"
	 * @generated
	 */
	public EList<AssociationOverride> getAssociationOverrides()
	{
		if (associationOverrides == null)
		{
			associationOverrides = new EObjectContainmentEList<AssociationOverride>(AssociationOverride.class, this, OrmPackage.ENTITY__ASSOCIATION_OVERRIDES);
		}
		return associationOverrides;
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
			case OrmPackage.ENTITY__TABLE:
				return basicSetTable(null, msgs);
			case OrmPackage.ENTITY__SECONDARY_TABLES:
				return ((InternalEList<?>)getSecondaryTables()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY__PRIMARY_KEY_JOIN_COLUMNS:
				return ((InternalEList<?>)getPrimaryKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY__ID_CLASS:
				return basicSetIdClass(null, msgs);
			case OrmPackage.ENTITY__INHERITANCE:
				return basicSetInheritance(null, msgs);
			case OrmPackage.ENTITY__DISCRIMINATOR_COLUMN:
				return basicSetDiscriminatorColumn(null, msgs);
			case OrmPackage.ENTITY__SEQUENCE_GENERATOR:
				return basicSetSequenceGenerator(null, msgs);
			case OrmPackage.ENTITY__TABLE_GENERATOR:
				return basicSetTableGenerator(null, msgs);
			case OrmPackage.ENTITY__NAMED_QUERIES:
				return ((InternalEList<?>)getNamedQueries()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY__NAMED_NATIVE_QUERIES:
				return ((InternalEList<?>)getNamedNativeQueries()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY__SQL_RESULT_SET_MAPPINGS:
				return ((InternalEList<?>)getSqlResultSetMappings()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY__ENTITY_LISTENERS:
				return basicSetEntityListeners(null, msgs);
			case OrmPackage.ENTITY__PRE_PERSIST:
				return basicSetPrePersist(null, msgs);
			case OrmPackage.ENTITY__POST_PERSIST:
				return basicSetPostPersist(null, msgs);
			case OrmPackage.ENTITY__PRE_REMOVE:
				return basicSetPreRemove(null, msgs);
			case OrmPackage.ENTITY__POST_REMOVE:
				return basicSetPostRemove(null, msgs);
			case OrmPackage.ENTITY__PRE_UPDATE:
				return basicSetPreUpdate(null, msgs);
			case OrmPackage.ENTITY__POST_UPDATE:
				return basicSetPostUpdate(null, msgs);
			case OrmPackage.ENTITY__POST_LOAD:
				return basicSetPostLoad(null, msgs);
			case OrmPackage.ENTITY__ATTRIBUTE_OVERRIDES:
				return ((InternalEList<?>)getAttributeOverrides()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY__ASSOCIATION_OVERRIDES:
				return ((InternalEList<?>)getAssociationOverrides()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.ENTITY__NAME:
				return getName();
			case OrmPackage.ENTITY__TABLE:
				return getTable();
			case OrmPackage.ENTITY__SECONDARY_TABLES:
				return getSecondaryTables();
			case OrmPackage.ENTITY__PRIMARY_KEY_JOIN_COLUMNS:
				return getPrimaryKeyJoinColumns();
			case OrmPackage.ENTITY__ID_CLASS:
				return getIdClass();
			case OrmPackage.ENTITY__INHERITANCE:
				return getInheritance();
			case OrmPackage.ENTITY__DISCRIMINATOR_VALUE:
				return getDiscriminatorValue();
			case OrmPackage.ENTITY__DISCRIMINATOR_COLUMN:
				return getDiscriminatorColumn();
			case OrmPackage.ENTITY__SEQUENCE_GENERATOR:
				return getSequenceGenerator();
			case OrmPackage.ENTITY__TABLE_GENERATOR:
				return getTableGenerator();
			case OrmPackage.ENTITY__NAMED_QUERIES:
				return getNamedQueries();
			case OrmPackage.ENTITY__NAMED_NATIVE_QUERIES:
				return getNamedNativeQueries();
			case OrmPackage.ENTITY__SQL_RESULT_SET_MAPPINGS:
				return getSqlResultSetMappings();
			case OrmPackage.ENTITY__EXCLUDE_DEFAULT_LISTENERS:
				return isExcludeDefaultListeners() ? Boolean.TRUE : Boolean.FALSE;
			case OrmPackage.ENTITY__EXCLUDE_SUPERCLASS_LISTENERS:
				return isExcludeSuperclassListeners() ? Boolean.TRUE : Boolean.FALSE;
			case OrmPackage.ENTITY__ENTITY_LISTENERS:
				return getEntityListeners();
			case OrmPackage.ENTITY__PRE_PERSIST:
				return getPrePersist();
			case OrmPackage.ENTITY__POST_PERSIST:
				return getPostPersist();
			case OrmPackage.ENTITY__PRE_REMOVE:
				return getPreRemove();
			case OrmPackage.ENTITY__POST_REMOVE:
				return getPostRemove();
			case OrmPackage.ENTITY__PRE_UPDATE:
				return getPreUpdate();
			case OrmPackage.ENTITY__POST_UPDATE:
				return getPostUpdate();
			case OrmPackage.ENTITY__POST_LOAD:
				return getPostLoad();
			case OrmPackage.ENTITY__ATTRIBUTE_OVERRIDES:
				return getAttributeOverrides();
			case OrmPackage.ENTITY__ASSOCIATION_OVERRIDES:
				return getAssociationOverrides();
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
			case OrmPackage.ENTITY__NAME:
				setName((String)newValue);
				return;
			case OrmPackage.ENTITY__TABLE:
				setTable((Table)newValue);
				return;
			case OrmPackage.ENTITY__SECONDARY_TABLES:
				getSecondaryTables().clear();
				getSecondaryTables().addAll((Collection<? extends SecondaryTable>)newValue);
				return;
			case OrmPackage.ENTITY__PRIMARY_KEY_JOIN_COLUMNS:
				getPrimaryKeyJoinColumns().clear();
				getPrimaryKeyJoinColumns().addAll((Collection<? extends PrimaryKeyJoinColumn>)newValue);
				return;
			case OrmPackage.ENTITY__ID_CLASS:
				setIdClass((IdClass)newValue);
				return;
			case OrmPackage.ENTITY__INHERITANCE:
				setInheritance((Inheritance)newValue);
				return;
			case OrmPackage.ENTITY__DISCRIMINATOR_VALUE:
				setDiscriminatorValue((String)newValue);
				return;
			case OrmPackage.ENTITY__DISCRIMINATOR_COLUMN:
				setDiscriminatorColumn((DiscriminatorColumn)newValue);
				return;
			case OrmPackage.ENTITY__SEQUENCE_GENERATOR:
				setSequenceGenerator((SequenceGenerator)newValue);
				return;
			case OrmPackage.ENTITY__TABLE_GENERATOR:
				setTableGenerator((TableGenerator)newValue);
				return;
			case OrmPackage.ENTITY__NAMED_QUERIES:
				getNamedQueries().clear();
				getNamedQueries().addAll((Collection<? extends NamedQuery>)newValue);
				return;
			case OrmPackage.ENTITY__NAMED_NATIVE_QUERIES:
				getNamedNativeQueries().clear();
				getNamedNativeQueries().addAll((Collection<? extends NamedNativeQuery>)newValue);
				return;
			case OrmPackage.ENTITY__SQL_RESULT_SET_MAPPINGS:
				getSqlResultSetMappings().clear();
				getSqlResultSetMappings().addAll((Collection<? extends SqlResultSetMapping>)newValue);
				return;
			case OrmPackage.ENTITY__EXCLUDE_DEFAULT_LISTENERS:
				setExcludeDefaultListeners(((Boolean)newValue).booleanValue());
				return;
			case OrmPackage.ENTITY__EXCLUDE_SUPERCLASS_LISTENERS:
				setExcludeSuperclassListeners(((Boolean)newValue).booleanValue());
				return;
			case OrmPackage.ENTITY__ENTITY_LISTENERS:
				setEntityListeners((EntityListeners)newValue);
				return;
			case OrmPackage.ENTITY__PRE_PERSIST:
				setPrePersist((PrePersist)newValue);
				return;
			case OrmPackage.ENTITY__POST_PERSIST:
				setPostPersist((PostPersist)newValue);
				return;
			case OrmPackage.ENTITY__PRE_REMOVE:
				setPreRemove((PreRemove)newValue);
				return;
			case OrmPackage.ENTITY__POST_REMOVE:
				setPostRemove((PostRemove)newValue);
				return;
			case OrmPackage.ENTITY__PRE_UPDATE:
				setPreUpdate((PreUpdate)newValue);
				return;
			case OrmPackage.ENTITY__POST_UPDATE:
				setPostUpdate((PostUpdate)newValue);
				return;
			case OrmPackage.ENTITY__POST_LOAD:
				setPostLoad((PostLoad)newValue);
				return;
			case OrmPackage.ENTITY__ATTRIBUTE_OVERRIDES:
				getAttributeOverrides().clear();
				getAttributeOverrides().addAll((Collection<? extends AttributeOverride>)newValue);
				return;
			case OrmPackage.ENTITY__ASSOCIATION_OVERRIDES:
				getAssociationOverrides().clear();
				getAssociationOverrides().addAll((Collection<? extends AssociationOverride>)newValue);
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
			case OrmPackage.ENTITY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OrmPackage.ENTITY__TABLE:
				setTable((Table)null);
				return;
			case OrmPackage.ENTITY__SECONDARY_TABLES:
				getSecondaryTables().clear();
				return;
			case OrmPackage.ENTITY__PRIMARY_KEY_JOIN_COLUMNS:
				getPrimaryKeyJoinColumns().clear();
				return;
			case OrmPackage.ENTITY__ID_CLASS:
				setIdClass((IdClass)null);
				return;
			case OrmPackage.ENTITY__INHERITANCE:
				setInheritance((Inheritance)null);
				return;
			case OrmPackage.ENTITY__DISCRIMINATOR_VALUE:
				setDiscriminatorValue(DISCRIMINATOR_VALUE_EDEFAULT);
				return;
			case OrmPackage.ENTITY__DISCRIMINATOR_COLUMN:
				setDiscriminatorColumn((DiscriminatorColumn)null);
				return;
			case OrmPackage.ENTITY__SEQUENCE_GENERATOR:
				setSequenceGenerator((SequenceGenerator)null);
				return;
			case OrmPackage.ENTITY__TABLE_GENERATOR:
				setTableGenerator((TableGenerator)null);
				return;
			case OrmPackage.ENTITY__NAMED_QUERIES:
				getNamedQueries().clear();
				return;
			case OrmPackage.ENTITY__NAMED_NATIVE_QUERIES:
				getNamedNativeQueries().clear();
				return;
			case OrmPackage.ENTITY__SQL_RESULT_SET_MAPPINGS:
				getSqlResultSetMappings().clear();
				return;
			case OrmPackage.ENTITY__EXCLUDE_DEFAULT_LISTENERS:
				setExcludeDefaultListeners(EXCLUDE_DEFAULT_LISTENERS_EDEFAULT);
				return;
			case OrmPackage.ENTITY__EXCLUDE_SUPERCLASS_LISTENERS:
				setExcludeSuperclassListeners(EXCLUDE_SUPERCLASS_LISTENERS_EDEFAULT);
				return;
			case OrmPackage.ENTITY__ENTITY_LISTENERS:
				setEntityListeners((EntityListeners)null);
				return;
			case OrmPackage.ENTITY__PRE_PERSIST:
				setPrePersist((PrePersist)null);
				return;
			case OrmPackage.ENTITY__POST_PERSIST:
				setPostPersist((PostPersist)null);
				return;
			case OrmPackage.ENTITY__PRE_REMOVE:
				setPreRemove((PreRemove)null);
				return;
			case OrmPackage.ENTITY__POST_REMOVE:
				setPostRemove((PostRemove)null);
				return;
			case OrmPackage.ENTITY__PRE_UPDATE:
				setPreUpdate((PreUpdate)null);
				return;
			case OrmPackage.ENTITY__POST_UPDATE:
				setPostUpdate((PostUpdate)null);
				return;
			case OrmPackage.ENTITY__POST_LOAD:
				setPostLoad((PostLoad)null);
				return;
			case OrmPackage.ENTITY__ATTRIBUTE_OVERRIDES:
				getAttributeOverrides().clear();
				return;
			case OrmPackage.ENTITY__ASSOCIATION_OVERRIDES:
				getAssociationOverrides().clear();
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
			case OrmPackage.ENTITY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrmPackage.ENTITY__TABLE:
				return table != null;
			case OrmPackage.ENTITY__SECONDARY_TABLES:
				return secondaryTables != null && !secondaryTables.isEmpty();
			case OrmPackage.ENTITY__PRIMARY_KEY_JOIN_COLUMNS:
				return primaryKeyJoinColumns != null && !primaryKeyJoinColumns.isEmpty();
			case OrmPackage.ENTITY__ID_CLASS:
				return idClass != null;
			case OrmPackage.ENTITY__INHERITANCE:
				return inheritance != null;
			case OrmPackage.ENTITY__DISCRIMINATOR_VALUE:
				return DISCRIMINATOR_VALUE_EDEFAULT == null ? discriminatorValue != null : !DISCRIMINATOR_VALUE_EDEFAULT.equals(discriminatorValue);
			case OrmPackage.ENTITY__DISCRIMINATOR_COLUMN:
				return discriminatorColumn != null;
			case OrmPackage.ENTITY__SEQUENCE_GENERATOR:
				return sequenceGenerator != null;
			case OrmPackage.ENTITY__TABLE_GENERATOR:
				return tableGenerator != null;
			case OrmPackage.ENTITY__NAMED_QUERIES:
				return namedQueries != null && !namedQueries.isEmpty();
			case OrmPackage.ENTITY__NAMED_NATIVE_QUERIES:
				return namedNativeQueries != null && !namedNativeQueries.isEmpty();
			case OrmPackage.ENTITY__SQL_RESULT_SET_MAPPINGS:
				return sqlResultSetMappings != null && !sqlResultSetMappings.isEmpty();
			case OrmPackage.ENTITY__EXCLUDE_DEFAULT_LISTENERS:
				return excludeDefaultListeners != EXCLUDE_DEFAULT_LISTENERS_EDEFAULT;
			case OrmPackage.ENTITY__EXCLUDE_SUPERCLASS_LISTENERS:
				return excludeSuperclassListeners != EXCLUDE_SUPERCLASS_LISTENERS_EDEFAULT;
			case OrmPackage.ENTITY__ENTITY_LISTENERS:
				return entityListeners != null;
			case OrmPackage.ENTITY__PRE_PERSIST:
				return prePersist != null;
			case OrmPackage.ENTITY__POST_PERSIST:
				return postPersist != null;
			case OrmPackage.ENTITY__PRE_REMOVE:
				return preRemove != null;
			case OrmPackage.ENTITY__POST_REMOVE:
				return postRemove != null;
			case OrmPackage.ENTITY__PRE_UPDATE:
				return preUpdate != null;
			case OrmPackage.ENTITY__POST_UPDATE:
				return postUpdate != null;
			case OrmPackage.ENTITY__POST_LOAD:
				return postLoad != null;
			case OrmPackage.ENTITY__ATTRIBUTE_OVERRIDES:
				return attributeOverrides != null && !attributeOverrides.isEmpty();
			case OrmPackage.ENTITY__ASSOCIATION_OVERRIDES:
				return associationOverrides != null && !associationOverrides.isEmpty();
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
		result.append(", discriminatorValue: ");
		result.append(discriminatorValue);
		result.append(", excludeDefaultListeners: ");
		result.append(excludeDefaultListeners);
		result.append(", excludeSuperclassListeners: ");
		result.append(excludeSuperclassListeners);
		result.append(')');
		return result.toString();
	}

} // Entity
