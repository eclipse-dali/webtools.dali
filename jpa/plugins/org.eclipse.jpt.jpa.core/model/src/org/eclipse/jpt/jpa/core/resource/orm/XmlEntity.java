/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
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
import org.eclipse.jpt.common.core.internal.utility.translators.EmptyTagBooleanTranslator;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlCacheable_2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlEntity_2_0;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlEntity_2_1;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlQueryContainer_2_1;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getTable <em>Table</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getSecondaryTables <em>Secondary Tables</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getPrimaryKeyJoinColumns <em>Primary Key Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getInheritance <em>Inheritance</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getDiscriminatorValue <em>Discriminator Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getDiscriminatorColumn <em>Discriminator Column</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getSqlResultSetMappings <em>Sql Result Set Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntity()
 * @model kind="class"
 * @generated
 */
public class XmlEntity extends AbstractXmlTypeMapping implements XmlIdTypeMapping, XmlQueryContainer, XmlGeneratorContainer, XmlEventMethodContainer, XmlAttributeOverrideContainer, XmlAssociationOverrideContainer, XmlEntity_2_0, XmlEntity_2_1
{

	/**
	 * The cached value of the '{@link #getIdClass() <em>Id Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdClass()
	 * @generated
	 * @ordered
	 */
	protected XmlClassReference idClass;

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
	 * The cached value of the '{@link #getNamedStoredProcedureQueries() <em>Named Stored Procedure Queries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamedStoredProcedureQueries()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlNamedStoredProcedureQuery> namedStoredProcedureQueries;

	/**
	 * The cached value of the '{@link #getNamedQueries() <em>Named Queries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamedQueries()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlNamedQuery> namedQueries;

	/**
	 * The cached value of the '{@link #getNamedNativeQueries() <em>Named Native Queries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamedNativeQueries()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlNamedNativeQuery> namedNativeQueries;

	/**
	 * The cached value of the '{@link #getSequenceGenerator() <em>Sequence Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSequenceGenerator()
	 * @generated
	 * @ordered
	 */
	protected XmlSequenceGenerator sequenceGenerator;

	/**
	 * The cached value of the '{@link #getTableGenerator() <em>Table Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTableGenerator()
	 * @generated
	 * @ordered
	 */
	protected XmlTableGenerator tableGenerator;

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
	 * The default value of the '{@link #getCacheable() <em>Cacheable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheable()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean CACHEABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCacheable() <em>Cacheable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheable()
	 * @generated
	 * @ordered
	 */
	protected Boolean cacheable = CACHEABLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPrimaryKeyForeignKey() <em>Primary Key Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryKeyForeignKey()
	 * @generated
	 * @ordered
	 */
	protected XmlForeignKey primaryKeyForeignKey;

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
	 * The cached value of the '{@link #getNamedEntityGraphs() <em>Named Entity Graphs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamedEntityGraphs()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlNamedEntityGraph> namedEntityGraphs;

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
	protected XmlTable table;

	/**
	 * The cached value of the '{@link #getSecondaryTables() <em>Secondary Tables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryTables()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlSecondaryTable> secondaryTables;

	/**
	 * The cached value of the '{@link #getPrimaryKeyJoinColumns() <em>Primary Key Join Columns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryKeyJoinColumns()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlPrimaryKeyJoinColumn> primaryKeyJoinColumns;

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
	protected XmlDiscriminatorColumn discriminatorColumn;

	/**
	 * The cached value of the '{@link #getSqlResultSetMappings() <em>Sql Result Set Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSqlResultSetMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlSqlResultSetMapping> sqlResultSetMappings;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlEntity()
	{
		super();
	}
	
	public String getMappingKey() {
		return MappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

	public XmlEntityMappings entityMappings() {
		return (XmlEntityMappings) eContainer();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return OrmPackage.Literals.XML_ENTITY;
	}

	/**
	 * Returns the value of the '<em><b>Named Stored Procedure Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlNamedStoredProcedureQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Stored Procedure Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Stored Procedure Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlQueryContainer_2_1_NamedStoredProcedureQueries()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlNamedStoredProcedureQuery> getNamedStoredProcedureQueries()
	{
		if (namedStoredProcedureQueries == null)
		{
			namedStoredProcedureQueries = new EObjectContainmentEList<XmlNamedStoredProcedureQuery>(XmlNamedStoredProcedureQuery.class, this, OrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES);
		}
		return namedStoredProcedureQueries;
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntity_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__NAME, oldName, name));
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
	 * @see #setTable(XmlTable)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntity_Table()
	 * @model containment="true"
	 * @generated
	 */
	public XmlTable getTable()
	{
		return table;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTable(XmlTable newTable, NotificationChain msgs)
	{
		XmlTable oldTable = table;
		table = newTable;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__TABLE, oldTable, newTable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getTable <em>Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table</em>' containment reference.
	 * @see #getTable()
	 * @generated
	 */
	public void setTable(XmlTable newTable)
	{
		if (newTable != table)
		{
			NotificationChain msgs = null;
			if (table != null)
				msgs = ((InternalEObject)table).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__TABLE, null, msgs);
			if (newTable != null)
				msgs = ((InternalEObject)newTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__TABLE, null, msgs);
			msgs = basicSetTable(newTable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__TABLE, newTable, newTable));
	}

	/**
	 * Returns the value of the '<em><b>Secondary Tables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlSecondaryTable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Secondary Tables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Secondary Tables</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntity_SecondaryTables()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlSecondaryTable> getSecondaryTables()
	{
		if (secondaryTables == null)
		{
			secondaryTables = new EObjectContainmentEList<XmlSecondaryTable>(XmlSecondaryTable.class, this, OrmPackage.XML_ENTITY__SECONDARY_TABLES);
		}
		return secondaryTables;
	}

	/**
	 * Returns the value of the '<em><b>Primary Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlPrimaryKeyJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntity_PrimaryKeyJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns()
	{
		if (primaryKeyJoinColumns == null)
		{
			primaryKeyJoinColumns = new EObjectContainmentEList<XmlPrimaryKeyJoinColumn>(XmlPrimaryKeyJoinColumn.class, this, OrmPackage.XML_ENTITY__PRIMARY_KEY_JOIN_COLUMNS);
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
	 * @see #setIdClass(XmlClassReference)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlIdClassContainer_IdClass()
	 * @model containment="true"
	 * @generated
	 */
	public XmlClassReference getIdClass()
	{
		return idClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIdClass(XmlClassReference newIdClass, NotificationChain msgs)
	{
		XmlClassReference oldIdClass = idClass;
		idClass = newIdClass;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__ID_CLASS, oldIdClass, newIdClass);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getIdClass <em>Id Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Class</em>' containment reference.
	 * @see #getIdClass()
	 * @generated
	 */
	public void setIdClass(XmlClassReference newIdClass)
	{
		if (newIdClass != idClass)
		{
			NotificationChain msgs = null;
			if (idClass != null)
				msgs = ((InternalEObject)idClass).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__ID_CLASS, null, msgs);
			if (newIdClass != null)
				msgs = ((InternalEObject)newIdClass).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__ID_CLASS, null, msgs);
			msgs = basicSetIdClass(newIdClass, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__ID_CLASS, newIdClass, newIdClass));
	}

	/**
	 * Returns the value of the '<em><b>Primary Key Foreign Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Key Foreign Key</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Key Foreign Key</em>' containment reference.
	 * @see #setPrimaryKeyForeignKey(XmlForeignKey)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntity_2_1_PrimaryKeyForeignKey()
	 * @model containment="true"
	 * @generated
	 */
	public XmlForeignKey getPrimaryKeyForeignKey()
	{
		return primaryKeyForeignKey;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrimaryKeyForeignKey(XmlForeignKey newPrimaryKeyForeignKey, NotificationChain msgs)
	{
		XmlForeignKey oldPrimaryKeyForeignKey = primaryKeyForeignKey;
		primaryKeyForeignKey = newPrimaryKeyForeignKey;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__PRIMARY_KEY_FOREIGN_KEY, oldPrimaryKeyForeignKey, newPrimaryKeyForeignKey);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getPrimaryKeyForeignKey <em>Primary Key Foreign Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Key Foreign Key</em>' containment reference.
	 * @see #getPrimaryKeyForeignKey()
	 * @generated
	 */
	public void setPrimaryKeyForeignKey(XmlForeignKey newPrimaryKeyForeignKey)
	{
		if (newPrimaryKeyForeignKey != primaryKeyForeignKey)
		{
			NotificationChain msgs = null;
			if (primaryKeyForeignKey != null)
				msgs = ((InternalEObject)primaryKeyForeignKey).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__PRIMARY_KEY_FOREIGN_KEY, null, msgs);
			if (newPrimaryKeyForeignKey != null)
				msgs = ((InternalEObject)newPrimaryKeyForeignKey).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__PRIMARY_KEY_FOREIGN_KEY, null, msgs);
			msgs = basicSetPrimaryKeyForeignKey(newPrimaryKeyForeignKey, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__PRIMARY_KEY_FOREIGN_KEY, newPrimaryKeyForeignKey, newPrimaryKeyForeignKey));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntity_2_1_Converts()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlConvert> getConverts()
	{
		if (converts == null)
		{
			converts = new EObjectContainmentEList<XmlConvert>(XmlConvert.class, this, OrmPackage.XML_ENTITY__CONVERTS);
		}
		return converts;
	}

	/**
	 * Returns the value of the '<em><b>Named Entity Graphs</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlNamedEntityGraph}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Entity Graphs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Entity Graphs</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntity_2_1_NamedEntityGraphs()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlNamedEntityGraph> getNamedEntityGraphs()
	{
		if (namedEntityGraphs == null)
		{
			namedEntityGraphs = new EObjectContainmentEList<XmlNamedEntityGraph>(XmlNamedEntityGraph.class, this, OrmPackage.XML_ENTITY__NAMED_ENTITY_GRAPHS);
		}
		return namedEntityGraphs;
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntity_Inheritance()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__INHERITANCE, oldInheritance, newInheritance);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getInheritance <em>Inheritance</em>}' containment reference.
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
				msgs = ((InternalEObject)inheritance).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__INHERITANCE, null, msgs);
			if (newInheritance != null)
				msgs = ((InternalEObject)newInheritance).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__INHERITANCE, null, msgs);
			msgs = basicSetInheritance(newInheritance, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__INHERITANCE, newInheritance, newInheritance));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntity_DiscriminatorValue()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDiscriminatorValue()
	{
		return discriminatorValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getDiscriminatorValue <em>Discriminator Value</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__DISCRIMINATOR_VALUE, oldDiscriminatorValue, discriminatorValue));
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
	 * @see #setDiscriminatorColumn(XmlDiscriminatorColumn)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntity_DiscriminatorColumn()
	 * @model containment="true"
	 * @generated
	 */
	public XmlDiscriminatorColumn getDiscriminatorColumn()
	{
		return discriminatorColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDiscriminatorColumn(XmlDiscriminatorColumn newDiscriminatorColumn, NotificationChain msgs)
	{
		XmlDiscriminatorColumn oldDiscriminatorColumn = discriminatorColumn;
		discriminatorColumn = newDiscriminatorColumn;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__DISCRIMINATOR_COLUMN, oldDiscriminatorColumn, newDiscriminatorColumn);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getDiscriminatorColumn <em>Discriminator Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discriminator Column</em>' containment reference.
	 * @see #getDiscriminatorColumn()
	 * @generated
	 */
	public void setDiscriminatorColumn(XmlDiscriminatorColumn newDiscriminatorColumn)
	{
		if (newDiscriminatorColumn != discriminatorColumn)
		{
			NotificationChain msgs = null;
			if (discriminatorColumn != null)
				msgs = ((InternalEObject)discriminatorColumn).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__DISCRIMINATOR_COLUMN, null, msgs);
			if (newDiscriminatorColumn != null)
				msgs = ((InternalEObject)newDiscriminatorColumn).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__DISCRIMINATOR_COLUMN, null, msgs);
			msgs = basicSetDiscriminatorColumn(newDiscriminatorColumn, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__DISCRIMINATOR_COLUMN, newDiscriminatorColumn, newDiscriminatorColumn));
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
	 * @see #setSequenceGenerator(XmlSequenceGenerator)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlGeneratorContainer_SequenceGenerator()
	 * @model containment="true"
	 * @generated
	 */
	public XmlSequenceGenerator getSequenceGenerator()
	{
		return sequenceGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSequenceGenerator(XmlSequenceGenerator newSequenceGenerator, NotificationChain msgs)
	{
		XmlSequenceGenerator oldSequenceGenerator = sequenceGenerator;
		sequenceGenerator = newSequenceGenerator;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__SEQUENCE_GENERATOR, oldSequenceGenerator, newSequenceGenerator);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getSequenceGenerator <em>Sequence Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sequence Generator</em>' containment reference.
	 * @see #getSequenceGenerator()
	 * @generated
	 */
	public void setSequenceGenerator(XmlSequenceGenerator newSequenceGenerator)
	{
		if (newSequenceGenerator != sequenceGenerator)
		{
			NotificationChain msgs = null;
			if (sequenceGenerator != null)
				msgs = ((InternalEObject)sequenceGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__SEQUENCE_GENERATOR, null, msgs);
			if (newSequenceGenerator != null)
				msgs = ((InternalEObject)newSequenceGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__SEQUENCE_GENERATOR, null, msgs);
			msgs = basicSetSequenceGenerator(newSequenceGenerator, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__SEQUENCE_GENERATOR, newSequenceGenerator, newSequenceGenerator));
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
	 * @see #setTableGenerator(XmlTableGenerator)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlGeneratorContainer_TableGenerator()
	 * @model containment="true"
	 * @generated
	 */
	public XmlTableGenerator getTableGenerator()
	{
		return tableGenerator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTableGenerator(XmlTableGenerator newTableGenerator, NotificationChain msgs)
	{
		XmlTableGenerator oldTableGenerator = tableGenerator;
		tableGenerator = newTableGenerator;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__TABLE_GENERATOR, oldTableGenerator, newTableGenerator);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getTableGenerator <em>Table Generator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table Generator</em>' containment reference.
	 * @see #getTableGenerator()
	 * @generated
	 */
	public void setTableGenerator(XmlTableGenerator newTableGenerator)
	{
		if (newTableGenerator != tableGenerator)
		{
			NotificationChain msgs = null;
			if (tableGenerator != null)
				msgs = ((InternalEObject)tableGenerator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__TABLE_GENERATOR, null, msgs);
			if (newTableGenerator != null)
				msgs = ((InternalEObject)newTableGenerator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__TABLE_GENERATOR, null, msgs);
			msgs = basicSetTableGenerator(newTableGenerator, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__TABLE_GENERATOR, newTableGenerator, newTableGenerator));
	}

	/**
	 * Returns the value of the '<em><b>Named Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlNamedQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlQueryContainer_NamedQueries()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlNamedQuery> getNamedQueries()
	{
		if (namedQueries == null)
		{
			namedQueries = new EObjectContainmentEList<XmlNamedQuery>(XmlNamedQuery.class, this, OrmPackage.XML_ENTITY__NAMED_QUERIES);
		}
		return namedQueries;
	}

	/**
	 * Returns the value of the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlNamedNativeQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Native Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Native Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlQueryContainer_NamedNativeQueries()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlNamedNativeQuery> getNamedNativeQueries()
	{
		if (namedNativeQueries == null)
		{
			namedNativeQueries = new EObjectContainmentEList<XmlNamedNativeQuery>(XmlNamedNativeQuery.class, this, OrmPackage.XML_ENTITY__NAMED_NATIVE_QUERIES);
		}
		return namedNativeQueries;
	}

	/**
	 * Returns the value of the '<em><b>Sql Result Set Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlSqlResultSetMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sql Result Set Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sql Result Set Mappings</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntity_SqlResultSetMappings()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlSqlResultSetMapping> getSqlResultSetMappings()
	{
		if (sqlResultSetMappings == null)
		{
			sqlResultSetMappings = new EObjectContainmentEList<XmlSqlResultSetMapping>(XmlSqlResultSetMapping.class, this, OrmPackage.XML_ENTITY__SQL_RESULT_SET_MAPPINGS);
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlIdTypeMapping_ExcludeDefaultListeners()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isExcludeDefaultListeners()
	{
		return excludeDefaultListeners;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#isExcludeDefaultListeners <em>Exclude Default Listeners</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__EXCLUDE_DEFAULT_LISTENERS, oldExcludeDefaultListeners, excludeDefaultListeners));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlIdTypeMapping_ExcludeSuperclassListeners()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isExcludeSuperclassListeners()
	{
		return excludeSuperclassListeners;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#isExcludeSuperclassListeners <em>Exclude Superclass Listeners</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__EXCLUDE_SUPERCLASS_LISTENERS, oldExcludeSuperclassListeners, excludeSuperclassListeners));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlIdTypeMapping_EntityListeners()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__ENTITY_LISTENERS, oldEntityListeners, newEntityListeners);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getEntityListeners <em>Entity Listeners</em>}' containment reference.
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
				msgs = ((InternalEObject)entityListeners).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__ENTITY_LISTENERS, null, msgs);
			if (newEntityListeners != null)
				msgs = ((InternalEObject)newEntityListeners).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__ENTITY_LISTENERS, null, msgs);
			msgs = basicSetEntityListeners(newEntityListeners, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__ENTITY_LISTENERS, newEntityListeners, newEntityListeners));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEventMethodContainer_PrePersist()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__PRE_PERSIST, oldPrePersist, newPrePersist);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getPrePersist <em>Pre Persist</em>}' containment reference.
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
				msgs = ((InternalEObject)prePersist).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__PRE_PERSIST, null, msgs);
			if (newPrePersist != null)
				msgs = ((InternalEObject)newPrePersist).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__PRE_PERSIST, null, msgs);
			msgs = basicSetPrePersist(newPrePersist, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__PRE_PERSIST, newPrePersist, newPrePersist));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEventMethodContainer_PostPersist()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__POST_PERSIST, oldPostPersist, newPostPersist);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getPostPersist <em>Post Persist</em>}' containment reference.
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
				msgs = ((InternalEObject)postPersist).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__POST_PERSIST, null, msgs);
			if (newPostPersist != null)
				msgs = ((InternalEObject)newPostPersist).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__POST_PERSIST, null, msgs);
			msgs = basicSetPostPersist(newPostPersist, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__POST_PERSIST, newPostPersist, newPostPersist));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEventMethodContainer_PreRemove()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__PRE_REMOVE, oldPreRemove, newPreRemove);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getPreRemove <em>Pre Remove</em>}' containment reference.
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
				msgs = ((InternalEObject)preRemove).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__PRE_REMOVE, null, msgs);
			if (newPreRemove != null)
				msgs = ((InternalEObject)newPreRemove).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__PRE_REMOVE, null, msgs);
			msgs = basicSetPreRemove(newPreRemove, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__PRE_REMOVE, newPreRemove, newPreRemove));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEventMethodContainer_PostRemove()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__POST_REMOVE, oldPostRemove, newPostRemove);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getPostRemove <em>Post Remove</em>}' containment reference.
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
				msgs = ((InternalEObject)postRemove).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__POST_REMOVE, null, msgs);
			if (newPostRemove != null)
				msgs = ((InternalEObject)newPostRemove).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__POST_REMOVE, null, msgs);
			msgs = basicSetPostRemove(newPostRemove, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__POST_REMOVE, newPostRemove, newPostRemove));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEventMethodContainer_PreUpdate()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__PRE_UPDATE, oldPreUpdate, newPreUpdate);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getPreUpdate <em>Pre Update</em>}' containment reference.
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
				msgs = ((InternalEObject)preUpdate).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__PRE_UPDATE, null, msgs);
			if (newPreUpdate != null)
				msgs = ((InternalEObject)newPreUpdate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__PRE_UPDATE, null, msgs);
			msgs = basicSetPreUpdate(newPreUpdate, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__PRE_UPDATE, newPreUpdate, newPreUpdate));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEventMethodContainer_PostUpdate()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__POST_UPDATE, oldPostUpdate, newPostUpdate);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getPostUpdate <em>Post Update</em>}' containment reference.
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
				msgs = ((InternalEObject)postUpdate).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__POST_UPDATE, null, msgs);
			if (newPostUpdate != null)
				msgs = ((InternalEObject)newPostUpdate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__POST_UPDATE, null, msgs);
			msgs = basicSetPostUpdate(newPostUpdate, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__POST_UPDATE, newPostUpdate, newPostUpdate));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEventMethodContainer_PostLoad()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__POST_LOAD, oldPostLoad, newPostLoad);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getPostLoad <em>Post Load</em>}' containment reference.
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
				msgs = ((InternalEObject)postLoad).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__POST_LOAD, null, msgs);
			if (newPostLoad != null)
				msgs = ((InternalEObject)newPostLoad).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY__POST_LOAD, null, msgs);
			msgs = basicSetPostLoad(newPostLoad, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__POST_LOAD, newPostLoad, newPostLoad));
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
			attributeOverrides = new EObjectContainmentEList<XmlAttributeOverride>(XmlAttributeOverride.class, this, OrmPackage.XML_ENTITY__ATTRIBUTE_OVERRIDES);
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
			associationOverrides = new EObjectContainmentEList<XmlAssociationOverride>(XmlAssociationOverride.class, this, OrmPackage.XML_ENTITY__ASSOCIATION_OVERRIDES);
		}
		return associationOverrides;
	}

	/**
	 * Returns the value of the '<em><b>Cacheable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cacheable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cacheable</em>' attribute.
	 * @see #setCacheable(Boolean)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlCacheable_2_0_Cacheable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getCacheable()
	{
		return cacheable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity#getCacheable <em>Cacheable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cacheable</em>' attribute.
	 * @see #getCacheable()
	 * @generated
	 */
	public void setCacheable(Boolean newCacheable)
	{
		Boolean oldCacheable = cacheable;
		cacheable = newCacheable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY__CACHEABLE, oldCacheable, cacheable));
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
			case OrmPackage.XML_ENTITY__ID_CLASS:
				return basicSetIdClass(null, msgs);
			case OrmPackage.XML_ENTITY__ENTITY_LISTENERS:
				return basicSetEntityListeners(null, msgs);
			case OrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES:
				return ((InternalEList<?>)getNamedStoredProcedureQueries()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY__NAMED_QUERIES:
				return ((InternalEList<?>)getNamedQueries()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY__NAMED_NATIVE_QUERIES:
				return ((InternalEList<?>)getNamedNativeQueries()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY__SEQUENCE_GENERATOR:
				return basicSetSequenceGenerator(null, msgs);
			case OrmPackage.XML_ENTITY__TABLE_GENERATOR:
				return basicSetTableGenerator(null, msgs);
			case OrmPackage.XML_ENTITY__PRE_PERSIST:
				return basicSetPrePersist(null, msgs);
			case OrmPackage.XML_ENTITY__POST_PERSIST:
				return basicSetPostPersist(null, msgs);
			case OrmPackage.XML_ENTITY__PRE_REMOVE:
				return basicSetPreRemove(null, msgs);
			case OrmPackage.XML_ENTITY__POST_REMOVE:
				return basicSetPostRemove(null, msgs);
			case OrmPackage.XML_ENTITY__PRE_UPDATE:
				return basicSetPreUpdate(null, msgs);
			case OrmPackage.XML_ENTITY__POST_UPDATE:
				return basicSetPostUpdate(null, msgs);
			case OrmPackage.XML_ENTITY__POST_LOAD:
				return basicSetPostLoad(null, msgs);
			case OrmPackage.XML_ENTITY__ATTRIBUTE_OVERRIDES:
				return ((InternalEList<?>)getAttributeOverrides()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY__ASSOCIATION_OVERRIDES:
				return ((InternalEList<?>)getAssociationOverrides()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY__PRIMARY_KEY_FOREIGN_KEY:
				return basicSetPrimaryKeyForeignKey(null, msgs);
			case OrmPackage.XML_ENTITY__CONVERTS:
				return ((InternalEList<?>)getConverts()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY__NAMED_ENTITY_GRAPHS:
				return ((InternalEList<?>)getNamedEntityGraphs()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY__TABLE:
				return basicSetTable(null, msgs);
			case OrmPackage.XML_ENTITY__SECONDARY_TABLES:
				return ((InternalEList<?>)getSecondaryTables()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY__PRIMARY_KEY_JOIN_COLUMNS:
				return ((InternalEList<?>)getPrimaryKeyJoinColumns()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY__INHERITANCE:
				return basicSetInheritance(null, msgs);
			case OrmPackage.XML_ENTITY__DISCRIMINATOR_COLUMN:
				return basicSetDiscriminatorColumn(null, msgs);
			case OrmPackage.XML_ENTITY__SQL_RESULT_SET_MAPPINGS:
				return ((InternalEList<?>)getSqlResultSetMappings()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.XML_ENTITY__ID_CLASS:
				return getIdClass();
			case OrmPackage.XML_ENTITY__EXCLUDE_DEFAULT_LISTENERS:
				return isExcludeDefaultListeners();
			case OrmPackage.XML_ENTITY__EXCLUDE_SUPERCLASS_LISTENERS:
				return isExcludeSuperclassListeners();
			case OrmPackage.XML_ENTITY__ENTITY_LISTENERS:
				return getEntityListeners();
			case OrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES:
				return getNamedStoredProcedureQueries();
			case OrmPackage.XML_ENTITY__NAMED_QUERIES:
				return getNamedQueries();
			case OrmPackage.XML_ENTITY__NAMED_NATIVE_QUERIES:
				return getNamedNativeQueries();
			case OrmPackage.XML_ENTITY__SEQUENCE_GENERATOR:
				return getSequenceGenerator();
			case OrmPackage.XML_ENTITY__TABLE_GENERATOR:
				return getTableGenerator();
			case OrmPackage.XML_ENTITY__PRE_PERSIST:
				return getPrePersist();
			case OrmPackage.XML_ENTITY__POST_PERSIST:
				return getPostPersist();
			case OrmPackage.XML_ENTITY__PRE_REMOVE:
				return getPreRemove();
			case OrmPackage.XML_ENTITY__POST_REMOVE:
				return getPostRemove();
			case OrmPackage.XML_ENTITY__PRE_UPDATE:
				return getPreUpdate();
			case OrmPackage.XML_ENTITY__POST_UPDATE:
				return getPostUpdate();
			case OrmPackage.XML_ENTITY__POST_LOAD:
				return getPostLoad();
			case OrmPackage.XML_ENTITY__ATTRIBUTE_OVERRIDES:
				return getAttributeOverrides();
			case OrmPackage.XML_ENTITY__ASSOCIATION_OVERRIDES:
				return getAssociationOverrides();
			case OrmPackage.XML_ENTITY__CACHEABLE:
				return getCacheable();
			case OrmPackage.XML_ENTITY__PRIMARY_KEY_FOREIGN_KEY:
				return getPrimaryKeyForeignKey();
			case OrmPackage.XML_ENTITY__CONVERTS:
				return getConverts();
			case OrmPackage.XML_ENTITY__NAMED_ENTITY_GRAPHS:
				return getNamedEntityGraphs();
			case OrmPackage.XML_ENTITY__NAME:
				return getName();
			case OrmPackage.XML_ENTITY__TABLE:
				return getTable();
			case OrmPackage.XML_ENTITY__SECONDARY_TABLES:
				return getSecondaryTables();
			case OrmPackage.XML_ENTITY__PRIMARY_KEY_JOIN_COLUMNS:
				return getPrimaryKeyJoinColumns();
			case OrmPackage.XML_ENTITY__INHERITANCE:
				return getInheritance();
			case OrmPackage.XML_ENTITY__DISCRIMINATOR_VALUE:
				return getDiscriminatorValue();
			case OrmPackage.XML_ENTITY__DISCRIMINATOR_COLUMN:
				return getDiscriminatorColumn();
			case OrmPackage.XML_ENTITY__SQL_RESULT_SET_MAPPINGS:
				return getSqlResultSetMappings();
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
			case OrmPackage.XML_ENTITY__ID_CLASS:
				setIdClass((XmlClassReference)newValue);
				return;
			case OrmPackage.XML_ENTITY__EXCLUDE_DEFAULT_LISTENERS:
				setExcludeDefaultListeners((Boolean)newValue);
				return;
			case OrmPackage.XML_ENTITY__EXCLUDE_SUPERCLASS_LISTENERS:
				setExcludeSuperclassListeners((Boolean)newValue);
				return;
			case OrmPackage.XML_ENTITY__ENTITY_LISTENERS:
				setEntityListeners((EntityListeners)newValue);
				return;
			case OrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES:
				getNamedStoredProcedureQueries().clear();
				getNamedStoredProcedureQueries().addAll((Collection<? extends XmlNamedStoredProcedureQuery>)newValue);
				return;
			case OrmPackage.XML_ENTITY__NAMED_QUERIES:
				getNamedQueries().clear();
				getNamedQueries().addAll((Collection<? extends XmlNamedQuery>)newValue);
				return;
			case OrmPackage.XML_ENTITY__NAMED_NATIVE_QUERIES:
				getNamedNativeQueries().clear();
				getNamedNativeQueries().addAll((Collection<? extends XmlNamedNativeQuery>)newValue);
				return;
			case OrmPackage.XML_ENTITY__SEQUENCE_GENERATOR:
				setSequenceGenerator((XmlSequenceGenerator)newValue);
				return;
			case OrmPackage.XML_ENTITY__TABLE_GENERATOR:
				setTableGenerator((XmlTableGenerator)newValue);
				return;
			case OrmPackage.XML_ENTITY__PRE_PERSIST:
				setPrePersist((PrePersist)newValue);
				return;
			case OrmPackage.XML_ENTITY__POST_PERSIST:
				setPostPersist((PostPersist)newValue);
				return;
			case OrmPackage.XML_ENTITY__PRE_REMOVE:
				setPreRemove((PreRemove)newValue);
				return;
			case OrmPackage.XML_ENTITY__POST_REMOVE:
				setPostRemove((PostRemove)newValue);
				return;
			case OrmPackage.XML_ENTITY__PRE_UPDATE:
				setPreUpdate((PreUpdate)newValue);
				return;
			case OrmPackage.XML_ENTITY__POST_UPDATE:
				setPostUpdate((PostUpdate)newValue);
				return;
			case OrmPackage.XML_ENTITY__POST_LOAD:
				setPostLoad((PostLoad)newValue);
				return;
			case OrmPackage.XML_ENTITY__ATTRIBUTE_OVERRIDES:
				getAttributeOverrides().clear();
				getAttributeOverrides().addAll((Collection<? extends XmlAttributeOverride>)newValue);
				return;
			case OrmPackage.XML_ENTITY__ASSOCIATION_OVERRIDES:
				getAssociationOverrides().clear();
				getAssociationOverrides().addAll((Collection<? extends XmlAssociationOverride>)newValue);
				return;
			case OrmPackage.XML_ENTITY__CACHEABLE:
				setCacheable((Boolean)newValue);
				return;
			case OrmPackage.XML_ENTITY__PRIMARY_KEY_FOREIGN_KEY:
				setPrimaryKeyForeignKey((XmlForeignKey)newValue);
				return;
			case OrmPackage.XML_ENTITY__CONVERTS:
				getConverts().clear();
				getConverts().addAll((Collection<? extends XmlConvert>)newValue);
				return;
			case OrmPackage.XML_ENTITY__NAMED_ENTITY_GRAPHS:
				getNamedEntityGraphs().clear();
				getNamedEntityGraphs().addAll((Collection<? extends XmlNamedEntityGraph>)newValue);
				return;
			case OrmPackage.XML_ENTITY__NAME:
				setName((String)newValue);
				return;
			case OrmPackage.XML_ENTITY__TABLE:
				setTable((XmlTable)newValue);
				return;
			case OrmPackage.XML_ENTITY__SECONDARY_TABLES:
				getSecondaryTables().clear();
				getSecondaryTables().addAll((Collection<? extends XmlSecondaryTable>)newValue);
				return;
			case OrmPackage.XML_ENTITY__PRIMARY_KEY_JOIN_COLUMNS:
				getPrimaryKeyJoinColumns().clear();
				getPrimaryKeyJoinColumns().addAll((Collection<? extends XmlPrimaryKeyJoinColumn>)newValue);
				return;
			case OrmPackage.XML_ENTITY__INHERITANCE:
				setInheritance((Inheritance)newValue);
				return;
			case OrmPackage.XML_ENTITY__DISCRIMINATOR_VALUE:
				setDiscriminatorValue((String)newValue);
				return;
			case OrmPackage.XML_ENTITY__DISCRIMINATOR_COLUMN:
				setDiscriminatorColumn((XmlDiscriminatorColumn)newValue);
				return;
			case OrmPackage.XML_ENTITY__SQL_RESULT_SET_MAPPINGS:
				getSqlResultSetMappings().clear();
				getSqlResultSetMappings().addAll((Collection<? extends XmlSqlResultSetMapping>)newValue);
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
			case OrmPackage.XML_ENTITY__ID_CLASS:
				setIdClass((XmlClassReference)null);
				return;
			case OrmPackage.XML_ENTITY__EXCLUDE_DEFAULT_LISTENERS:
				setExcludeDefaultListeners(EXCLUDE_DEFAULT_LISTENERS_EDEFAULT);
				return;
			case OrmPackage.XML_ENTITY__EXCLUDE_SUPERCLASS_LISTENERS:
				setExcludeSuperclassListeners(EXCLUDE_SUPERCLASS_LISTENERS_EDEFAULT);
				return;
			case OrmPackage.XML_ENTITY__ENTITY_LISTENERS:
				setEntityListeners((EntityListeners)null);
				return;
			case OrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES:
				getNamedStoredProcedureQueries().clear();
				return;
			case OrmPackage.XML_ENTITY__NAMED_QUERIES:
				getNamedQueries().clear();
				return;
			case OrmPackage.XML_ENTITY__NAMED_NATIVE_QUERIES:
				getNamedNativeQueries().clear();
				return;
			case OrmPackage.XML_ENTITY__SEQUENCE_GENERATOR:
				setSequenceGenerator((XmlSequenceGenerator)null);
				return;
			case OrmPackage.XML_ENTITY__TABLE_GENERATOR:
				setTableGenerator((XmlTableGenerator)null);
				return;
			case OrmPackage.XML_ENTITY__PRE_PERSIST:
				setPrePersist((PrePersist)null);
				return;
			case OrmPackage.XML_ENTITY__POST_PERSIST:
				setPostPersist((PostPersist)null);
				return;
			case OrmPackage.XML_ENTITY__PRE_REMOVE:
				setPreRemove((PreRemove)null);
				return;
			case OrmPackage.XML_ENTITY__POST_REMOVE:
				setPostRemove((PostRemove)null);
				return;
			case OrmPackage.XML_ENTITY__PRE_UPDATE:
				setPreUpdate((PreUpdate)null);
				return;
			case OrmPackage.XML_ENTITY__POST_UPDATE:
				setPostUpdate((PostUpdate)null);
				return;
			case OrmPackage.XML_ENTITY__POST_LOAD:
				setPostLoad((PostLoad)null);
				return;
			case OrmPackage.XML_ENTITY__ATTRIBUTE_OVERRIDES:
				getAttributeOverrides().clear();
				return;
			case OrmPackage.XML_ENTITY__ASSOCIATION_OVERRIDES:
				getAssociationOverrides().clear();
				return;
			case OrmPackage.XML_ENTITY__CACHEABLE:
				setCacheable(CACHEABLE_EDEFAULT);
				return;
			case OrmPackage.XML_ENTITY__PRIMARY_KEY_FOREIGN_KEY:
				setPrimaryKeyForeignKey((XmlForeignKey)null);
				return;
			case OrmPackage.XML_ENTITY__CONVERTS:
				getConverts().clear();
				return;
			case OrmPackage.XML_ENTITY__NAMED_ENTITY_GRAPHS:
				getNamedEntityGraphs().clear();
				return;
			case OrmPackage.XML_ENTITY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OrmPackage.XML_ENTITY__TABLE:
				setTable((XmlTable)null);
				return;
			case OrmPackage.XML_ENTITY__SECONDARY_TABLES:
				getSecondaryTables().clear();
				return;
			case OrmPackage.XML_ENTITY__PRIMARY_KEY_JOIN_COLUMNS:
				getPrimaryKeyJoinColumns().clear();
				return;
			case OrmPackage.XML_ENTITY__INHERITANCE:
				setInheritance((Inheritance)null);
				return;
			case OrmPackage.XML_ENTITY__DISCRIMINATOR_VALUE:
				setDiscriminatorValue(DISCRIMINATOR_VALUE_EDEFAULT);
				return;
			case OrmPackage.XML_ENTITY__DISCRIMINATOR_COLUMN:
				setDiscriminatorColumn((XmlDiscriminatorColumn)null);
				return;
			case OrmPackage.XML_ENTITY__SQL_RESULT_SET_MAPPINGS:
				getSqlResultSetMappings().clear();
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
			case OrmPackage.XML_ENTITY__ID_CLASS:
				return idClass != null;
			case OrmPackage.XML_ENTITY__EXCLUDE_DEFAULT_LISTENERS:
				return excludeDefaultListeners != EXCLUDE_DEFAULT_LISTENERS_EDEFAULT;
			case OrmPackage.XML_ENTITY__EXCLUDE_SUPERCLASS_LISTENERS:
				return excludeSuperclassListeners != EXCLUDE_SUPERCLASS_LISTENERS_EDEFAULT;
			case OrmPackage.XML_ENTITY__ENTITY_LISTENERS:
				return entityListeners != null;
			case OrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES:
				return namedStoredProcedureQueries != null && !namedStoredProcedureQueries.isEmpty();
			case OrmPackage.XML_ENTITY__NAMED_QUERIES:
				return namedQueries != null && !namedQueries.isEmpty();
			case OrmPackage.XML_ENTITY__NAMED_NATIVE_QUERIES:
				return namedNativeQueries != null && !namedNativeQueries.isEmpty();
			case OrmPackage.XML_ENTITY__SEQUENCE_GENERATOR:
				return sequenceGenerator != null;
			case OrmPackage.XML_ENTITY__TABLE_GENERATOR:
				return tableGenerator != null;
			case OrmPackage.XML_ENTITY__PRE_PERSIST:
				return prePersist != null;
			case OrmPackage.XML_ENTITY__POST_PERSIST:
				return postPersist != null;
			case OrmPackage.XML_ENTITY__PRE_REMOVE:
				return preRemove != null;
			case OrmPackage.XML_ENTITY__POST_REMOVE:
				return postRemove != null;
			case OrmPackage.XML_ENTITY__PRE_UPDATE:
				return preUpdate != null;
			case OrmPackage.XML_ENTITY__POST_UPDATE:
				return postUpdate != null;
			case OrmPackage.XML_ENTITY__POST_LOAD:
				return postLoad != null;
			case OrmPackage.XML_ENTITY__ATTRIBUTE_OVERRIDES:
				return attributeOverrides != null && !attributeOverrides.isEmpty();
			case OrmPackage.XML_ENTITY__ASSOCIATION_OVERRIDES:
				return associationOverrides != null && !associationOverrides.isEmpty();
			case OrmPackage.XML_ENTITY__CACHEABLE:
				return CACHEABLE_EDEFAULT == null ? cacheable != null : !CACHEABLE_EDEFAULT.equals(cacheable);
			case OrmPackage.XML_ENTITY__PRIMARY_KEY_FOREIGN_KEY:
				return primaryKeyForeignKey != null;
			case OrmPackage.XML_ENTITY__CONVERTS:
				return converts != null && !converts.isEmpty();
			case OrmPackage.XML_ENTITY__NAMED_ENTITY_GRAPHS:
				return namedEntityGraphs != null && !namedEntityGraphs.isEmpty();
			case OrmPackage.XML_ENTITY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrmPackage.XML_ENTITY__TABLE:
				return table != null;
			case OrmPackage.XML_ENTITY__SECONDARY_TABLES:
				return secondaryTables != null && !secondaryTables.isEmpty();
			case OrmPackage.XML_ENTITY__PRIMARY_KEY_JOIN_COLUMNS:
				return primaryKeyJoinColumns != null && !primaryKeyJoinColumns.isEmpty();
			case OrmPackage.XML_ENTITY__INHERITANCE:
				return inheritance != null;
			case OrmPackage.XML_ENTITY__DISCRIMINATOR_VALUE:
				return DISCRIMINATOR_VALUE_EDEFAULT == null ? discriminatorValue != null : !DISCRIMINATOR_VALUE_EDEFAULT.equals(discriminatorValue);
			case OrmPackage.XML_ENTITY__DISCRIMINATOR_COLUMN:
				return discriminatorColumn != null;
			case OrmPackage.XML_ENTITY__SQL_RESULT_SET_MAPPINGS:
				return sqlResultSetMappings != null && !sqlResultSetMappings.isEmpty();
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
		if (baseClass == XmlIdClassContainer.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ENTITY__ID_CLASS: return OrmPackage.XML_ID_CLASS_CONTAINER__ID_CLASS;
				default: return -1;
			}
		}
		if (baseClass == XmlIdTypeMapping.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ENTITY__EXCLUDE_DEFAULT_LISTENERS: return OrmPackage.XML_ID_TYPE_MAPPING__EXCLUDE_DEFAULT_LISTENERS;
				case OrmPackage.XML_ENTITY__EXCLUDE_SUPERCLASS_LISTENERS: return OrmPackage.XML_ID_TYPE_MAPPING__EXCLUDE_SUPERCLASS_LISTENERS;
				case OrmPackage.XML_ENTITY__ENTITY_LISTENERS: return OrmPackage.XML_ID_TYPE_MAPPING__ENTITY_LISTENERS;
				default: return -1;
			}
		}
		if (baseClass == XmlQueryContainer_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES: return OrmV2_1Package.XML_QUERY_CONTAINER_21__NAMED_STORED_PROCEDURE_QUERIES;
				default: return -1;
			}
		}
		if (baseClass == XmlQueryContainer.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ENTITY__NAMED_QUERIES: return OrmPackage.XML_QUERY_CONTAINER__NAMED_QUERIES;
				case OrmPackage.XML_ENTITY__NAMED_NATIVE_QUERIES: return OrmPackage.XML_QUERY_CONTAINER__NAMED_NATIVE_QUERIES;
				default: return -1;
			}
		}
		if (baseClass == XmlGeneratorContainer.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ENTITY__SEQUENCE_GENERATOR: return OrmPackage.XML_GENERATOR_CONTAINER__SEQUENCE_GENERATOR;
				case OrmPackage.XML_ENTITY__TABLE_GENERATOR: return OrmPackage.XML_GENERATOR_CONTAINER__TABLE_GENERATOR;
				default: return -1;
			}
		}
		if (baseClass == XmlEventMethodContainer.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ENTITY__PRE_PERSIST: return OrmPackage.XML_EVENT_METHOD_CONTAINER__PRE_PERSIST;
				case OrmPackage.XML_ENTITY__POST_PERSIST: return OrmPackage.XML_EVENT_METHOD_CONTAINER__POST_PERSIST;
				case OrmPackage.XML_ENTITY__PRE_REMOVE: return OrmPackage.XML_EVENT_METHOD_CONTAINER__PRE_REMOVE;
				case OrmPackage.XML_ENTITY__POST_REMOVE: return OrmPackage.XML_EVENT_METHOD_CONTAINER__POST_REMOVE;
				case OrmPackage.XML_ENTITY__PRE_UPDATE: return OrmPackage.XML_EVENT_METHOD_CONTAINER__PRE_UPDATE;
				case OrmPackage.XML_ENTITY__POST_UPDATE: return OrmPackage.XML_EVENT_METHOD_CONTAINER__POST_UPDATE;
				case OrmPackage.XML_ENTITY__POST_LOAD: return OrmPackage.XML_EVENT_METHOD_CONTAINER__POST_LOAD;
				default: return -1;
			}
		}
		if (baseClass == XmlAttributeOverrideContainer.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ENTITY__ATTRIBUTE_OVERRIDES: return OrmPackage.XML_ATTRIBUTE_OVERRIDE_CONTAINER__ATTRIBUTE_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlAssociationOverrideContainer.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ENTITY__ASSOCIATION_OVERRIDES: return OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER__ASSOCIATION_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlCacheable_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ENTITY__CACHEABLE: return OrmV2_0Package.XML_CACHEABLE_20__CACHEABLE;
				default: return -1;
			}
		}
		if (baseClass == XmlEntity_2_0.class)
		{
			switch (derivedFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlEntity_2_1.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ENTITY__PRIMARY_KEY_FOREIGN_KEY: return OrmV2_1Package.XML_ENTITY_21__PRIMARY_KEY_FOREIGN_KEY;
				case OrmPackage.XML_ENTITY__CONVERTS: return OrmV2_1Package.XML_ENTITY_21__CONVERTS;
				case OrmPackage.XML_ENTITY__NAMED_ENTITY_GRAPHS: return OrmV2_1Package.XML_ENTITY_21__NAMED_ENTITY_GRAPHS;
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
		if (baseClass == XmlIdClassContainer.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_ID_CLASS_CONTAINER__ID_CLASS: return OrmPackage.XML_ENTITY__ID_CLASS;
				default: return -1;
			}
		}
		if (baseClass == XmlIdTypeMapping.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_ID_TYPE_MAPPING__EXCLUDE_DEFAULT_LISTENERS: return OrmPackage.XML_ENTITY__EXCLUDE_DEFAULT_LISTENERS;
				case OrmPackage.XML_ID_TYPE_MAPPING__EXCLUDE_SUPERCLASS_LISTENERS: return OrmPackage.XML_ENTITY__EXCLUDE_SUPERCLASS_LISTENERS;
				case OrmPackage.XML_ID_TYPE_MAPPING__ENTITY_LISTENERS: return OrmPackage.XML_ENTITY__ENTITY_LISTENERS;
				default: return -1;
			}
		}
		if (baseClass == XmlQueryContainer_2_1.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_1Package.XML_QUERY_CONTAINER_21__NAMED_STORED_PROCEDURE_QUERIES: return OrmPackage.XML_ENTITY__NAMED_STORED_PROCEDURE_QUERIES;
				default: return -1;
			}
		}
		if (baseClass == XmlQueryContainer.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_QUERY_CONTAINER__NAMED_QUERIES: return OrmPackage.XML_ENTITY__NAMED_QUERIES;
				case OrmPackage.XML_QUERY_CONTAINER__NAMED_NATIVE_QUERIES: return OrmPackage.XML_ENTITY__NAMED_NATIVE_QUERIES;
				default: return -1;
			}
		}
		if (baseClass == XmlGeneratorContainer.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_GENERATOR_CONTAINER__SEQUENCE_GENERATOR: return OrmPackage.XML_ENTITY__SEQUENCE_GENERATOR;
				case OrmPackage.XML_GENERATOR_CONTAINER__TABLE_GENERATOR: return OrmPackage.XML_ENTITY__TABLE_GENERATOR;
				default: return -1;
			}
		}
		if (baseClass == XmlEventMethodContainer.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_EVENT_METHOD_CONTAINER__PRE_PERSIST: return OrmPackage.XML_ENTITY__PRE_PERSIST;
				case OrmPackage.XML_EVENT_METHOD_CONTAINER__POST_PERSIST: return OrmPackage.XML_ENTITY__POST_PERSIST;
				case OrmPackage.XML_EVENT_METHOD_CONTAINER__PRE_REMOVE: return OrmPackage.XML_ENTITY__PRE_REMOVE;
				case OrmPackage.XML_EVENT_METHOD_CONTAINER__POST_REMOVE: return OrmPackage.XML_ENTITY__POST_REMOVE;
				case OrmPackage.XML_EVENT_METHOD_CONTAINER__PRE_UPDATE: return OrmPackage.XML_ENTITY__PRE_UPDATE;
				case OrmPackage.XML_EVENT_METHOD_CONTAINER__POST_UPDATE: return OrmPackage.XML_ENTITY__POST_UPDATE;
				case OrmPackage.XML_EVENT_METHOD_CONTAINER__POST_LOAD: return OrmPackage.XML_ENTITY__POST_LOAD;
				default: return -1;
			}
		}
		if (baseClass == XmlAttributeOverrideContainer.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_ATTRIBUTE_OVERRIDE_CONTAINER__ATTRIBUTE_OVERRIDES: return OrmPackage.XML_ENTITY__ATTRIBUTE_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlAssociationOverrideContainer.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_ASSOCIATION_OVERRIDE_CONTAINER__ASSOCIATION_OVERRIDES: return OrmPackage.XML_ENTITY__ASSOCIATION_OVERRIDES;
				default: return -1;
			}
		}
		if (baseClass == XmlCacheable_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_CACHEABLE_20__CACHEABLE: return OrmPackage.XML_ENTITY__CACHEABLE;
				default: return -1;
			}
		}
		if (baseClass == XmlEntity_2_0.class)
		{
			switch (baseFeatureID)
			{
				default: return -1;
			}
		}
		if (baseClass == XmlEntity_2_1.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_1Package.XML_ENTITY_21__PRIMARY_KEY_FOREIGN_KEY: return OrmPackage.XML_ENTITY__PRIMARY_KEY_FOREIGN_KEY;
				case OrmV2_1Package.XML_ENTITY_21__CONVERTS: return OrmPackage.XML_ENTITY__CONVERTS;
				case OrmV2_1Package.XML_ENTITY_21__NAMED_ENTITY_GRAPHS: return OrmPackage.XML_ENTITY__NAMED_ENTITY_GRAPHS;
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
		result.append(" (excludeDefaultListeners: ");
		result.append(excludeDefaultListeners);
		result.append(", excludeSuperclassListeners: ");
		result.append(excludeSuperclassListeners);
		result.append(", cacheable: ");
		result.append(cacheable);
		result.append(", name: ");
		result.append(name);
		result.append(", discriminatorValue: ");
		result.append(discriminatorValue);
		result.append(')');
		return result.toString();
	}
	
	public TextRange getDiscriminatorValueTextRange() {
		return getElementTextRange(JPA.DISCRIMINATOR_VALUE);
	}
	
	public TextRange getInheritanceStrategyTextRange() {
		return getInheritance() != null ? getInheritance().getStrategyTextRange() : getValidationTextRange();
	}
	
	public TextRange getCacheableTextRange() {
		return getAttributeTextRange(JPA2_0.CACHEABLE);
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildClassTranslator(),
			buildAccessTranslator(),
			buildCacheableTranslator(),
			buildMetadataCompleteTranslator(),
			buildDescriptionTranslator(),
			buildTableTranslator(),
			buildSecondaryTableTranslator(),
			buildPrimaryKeyJoinColumnTranslator(),
			buildPrimaryKeyForeignKeyTranslator(),
			buildIdClassTranslator(),
			buildInheritanceTranslator(),
			buildDiscriminatorValueTranslator(),
			buildDiscriminatorColumnTranslator(),
			buildSequenceGeneratorTranslator(),
			buildTableGeneratorTranslator(),
			buildNamedQueryTranslator(),
			buildNamedNativeQueryTranslator(),
			buildNamedStoredProcedureQueryTranslator(),
			buildSqlResultSetMappingTranslator(),
			buildExcludeDefaultListenersTranslator(),
			buildExcludeSuperclassListenersTranslator(),
			buildEntityListenersTranslator(),
			PrePersist.buildTranslator(),
			PostPersist.buildTranslator(),
			PreRemove.buildTranslator(),
			PostRemove.buildTranslator(),
			PreUpdate.buildTranslator(),
			PostUpdate.buildTranslator(),
			PostLoad.buildTranslator(),
			buildAttributeOverrideTranslator(),
			buildAssociationOverrideTranslator(),
			buildConvertTranslator(),
			buildNamedEntityGraphTranslator(),
			Attributes.buildTranslator()
		};
	}

	protected static Translator buildNameTranslator() {
		return new Translator(JPA.NAME, OrmPackage.eINSTANCE.getXmlEntity_Name(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildCacheableTranslator() {
		return new Translator(JPA2_0.CACHEABLE, OrmV2_0Package.eINSTANCE.getXmlCacheable_2_0_Cacheable(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildTableTranslator() {
		return XmlTable.buildTranslator(JPA.TABLE, OrmPackage.eINSTANCE.getXmlEntity_Table());
	}
	
	protected static Translator buildSecondaryTableTranslator() {
		return XmlSecondaryTable.buildTranslator(JPA.SECONDARY_TABLE, OrmPackage.eINSTANCE.getXmlEntity_SecondaryTables());
	}	
	
	protected static Translator buildPrimaryKeyJoinColumnTranslator() {
		return XmlPrimaryKeyJoinColumn.buildTranslator(JPA.PRIMARY_KEY_JOIN_COLUMN, OrmPackage.eINSTANCE.getXmlEntity_PrimaryKeyJoinColumns());
	}
	
	protected static Translator buildIdClassTranslator() {
		return XmlClassReference.buildTranslator(JPA.ID_CLASS, OrmPackage.eINSTANCE.getXmlIdClassContainer_IdClass());
	}
	
	protected static Translator buildInheritanceTranslator() {
		return Inheritance.buildTranslator(JPA.INHERITANCE, OrmPackage.eINSTANCE.getXmlEntity_Inheritance());
	}
	
	protected static Translator buildDiscriminatorValueTranslator() {
		return new Translator(JPA.DISCRIMINATOR_VALUE, OrmPackage.eINSTANCE.getXmlEntity_DiscriminatorValue());
	}
	
	protected static Translator buildDiscriminatorColumnTranslator() {
		return XmlDiscriminatorColumn.buildTranslator(JPA.DISCRIMINATOR_COLUMN, OrmPackage.eINSTANCE.getXmlEntity_DiscriminatorColumn());
	}
	
	protected static Translator buildSequenceGeneratorTranslator() {
		return XmlSequenceGenerator.buildTranslator(JPA.SEQUENCE_GENERATOR, OrmPackage.eINSTANCE.getXmlGeneratorContainer_SequenceGenerator());
	}
	
	protected static Translator buildTableGeneratorTranslator() {
		return XmlTableGenerator.buildTranslator(JPA.TABLE_GENERATOR, OrmPackage.eINSTANCE.getXmlGeneratorContainer_TableGenerator());
	}
	
	protected static Translator buildNamedQueryTranslator() {
		return XmlNamedQuery.buildTranslator(JPA.NAMED_QUERY, OrmPackage.eINSTANCE.getXmlQueryContainer_NamedQueries());
	}
	
	protected static Translator buildNamedNativeQueryTranslator() {
		return XmlNamedNativeQuery.buildTranslator(JPA.NAMED_NATIVE_QUERY, OrmPackage.eINSTANCE.getXmlQueryContainer_NamedNativeQueries());
	}
	
	protected static Translator buildNamedStoredProcedureQueryTranslator() {
		return XmlNamedStoredProcedureQuery.buildTranslator(JPA2_1.NAMED_STORED_PROCEDURE_QUERY, OrmV2_1Package.eINSTANCE.getXmlQueryContainer_2_1_NamedStoredProcedureQueries());
	}
	
	protected static Translator buildSqlResultSetMappingTranslator() {
		return XmlSqlResultSetMapping.buildTranslator(JPA.SQL_RESULT_SET_MAPPING, OrmPackage.eINSTANCE.getXmlEntity_SqlResultSetMappings());
	}
	
	protected static Translator buildExcludeDefaultListenersTranslator() {
		return new EmptyTagBooleanTranslator(JPA.EXCLUDE_DEFAULT_LISTENERS, OrmPackage.eINSTANCE.getXmlIdTypeMapping_ExcludeDefaultListeners());
	}
	
	protected static Translator buildExcludeSuperclassListenersTranslator() {
		return new EmptyTagBooleanTranslator(JPA.EXCLUDE_SUPERCLASS_LISTENERS, OrmPackage.eINSTANCE.getXmlIdTypeMapping_ExcludeSuperclassListeners());
	}
	
	protected static Translator buildEntityListenersTranslator() {
		return EntityListeners.buildTranslator(JPA.ENTITY_LISTENERS, OrmPackage.eINSTANCE.getXmlIdTypeMapping_EntityListeners());
	}

	protected static Translator buildPrePersistTranslator() {
		return PrePersist.buildTranslator();
	}

	protected static Translator buildPostPersistTranslator() {
		return PostPersist.buildTranslator();
	}

	protected static Translator buildPreRemoveTranslator() {
		return PreRemove.buildTranslator();
	}

	protected static Translator buildPostRemoveTranslator() {
		return PostRemove.buildTranslator();
	}
	
	protected static Translator buildPreUpdateTranslator() {
		return PreUpdate.buildTranslator();
	}
	
	protected static Translator buildPostUpdateTranslator() {
		return PostUpdate.buildTranslator();
	}

	protected static Translator buildPostLoadTranslator() {
		return PostLoad.buildTranslator();
	}
		
	protected static Translator buildAttributeOverrideTranslator() {
		return XmlAttributeOverride.buildTranslator(JPA.ATTRIBUTE_OVERRIDE, OrmPackage.eINSTANCE.getXmlAttributeOverrideContainer_AttributeOverrides());
	}
	
	protected static Translator buildAssociationOverrideTranslator() {
		return XmlAssociationOverride.buildTranslator(JPA.ASSOCIATION_OVERRIDE, OrmPackage.eINSTANCE.getXmlAssociationOverrideContainer_AssociationOverrides());
	}
	
	protected static Translator buildConvertTranslator() {
		return XmlConvert.buildTranslator(JPA2_1.CONVERT, OrmV2_1Package.eINSTANCE.getXmlEntity_2_1_Converts());
	}
	
	protected static Translator buildPrimaryKeyForeignKeyTranslator() {
		return XmlForeignKey.buildTranslator(JPA2_1.PRIMARY_KEY_FOREIGN_KEY, OrmV2_1Package.eINSTANCE.getXmlEntity_2_1_PrimaryKeyForeignKey());
	}
	
	protected static Translator buildNamedEntityGraphTranslator() {
		return XmlNamedEntityGraph.buildTranslator(JPA2_1.NAMED_ENTITY_GRAPH, OrmV2_1Package.eINSTANCE.getXmlEntity_2_1_NamedEntityGraphs());
	}
}
