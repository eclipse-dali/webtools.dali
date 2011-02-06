/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleRootTranslator;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jpa.core.resource.orm.v2_0.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.xml.AbstractJpaRootEObject;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity Mappings</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getPersistenceUnitMetadata <em>Persistence Unit Metadata</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getPackage <em>Package</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getSchema <em>Schema</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getCatalog <em>Catalog</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getSequenceGenerators <em>Sequence Generators</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getTableGenerators <em>Table Generators</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getSqlResultSetMappings <em>Sql Result Set Mappings</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getMappedSuperclasses <em>Mapped Superclasses</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getEntities <em>Entities</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getEmbeddables <em>Embeddables</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntityMappings()
 * @model kind="class"
 * @generated
 */
public class XmlEntityMappings extends AbstractJpaRootEObject implements XmlQueryContainer, XmlAccessHolder
{
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
	 * The cached value of the '{@link #getPersistenceUnitMetadata() <em>Persistence Unit Metadata</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPersistenceUnitMetadata()
	 * @generated
	 * @ordered
	 */
	protected XmlPersistenceUnitMetadata persistenceUnitMetadata;

	/**
	 * The default value of the '{@link #getPackage() <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPackage() <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected String package_ = PACKAGE_EDEFAULT;

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
	 * The cached value of the '{@link #getSequenceGenerators() <em>Sequence Generators</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSequenceGenerators()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlSequenceGenerator> sequenceGenerators;

	/**
	 * The cached value of the '{@link #getTableGenerators() <em>Table Generators</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTableGenerators()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlTableGenerator> tableGenerators;

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
	 * The cached value of the '{@link #getMappedSuperclasses() <em>Mapped Superclasses</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappedSuperclasses()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlMappedSuperclass> mappedSuperclasses;

	/**
	 * The cached value of the '{@link #getEntities() <em>Entities</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntities()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlEntity> entities;

	/**
	 * The cached value of the '{@link #getEmbeddables() <em>Embeddables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmbeddables()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlEmbeddable> embeddables;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlEntityMappings()
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
		return OrmPackage.Literals.XML_ENTITY_MAPPINGS;
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
			namedQueries = new EObjectContainmentEList<XmlNamedQuery>(XmlNamedQuery.class, this, OrmPackage.XML_ENTITY_MAPPINGS__NAMED_QUERIES);
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
			namedNativeQueries = new EObjectContainmentEList<XmlNamedNativeQuery>(XmlNamedNativeQuery.class, this, OrmPackage.XML_ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES);
		}
		return namedNativeQueries;
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntityMappings_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getDescription <em>Description</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_MAPPINGS__DESCRIPTION, oldDescription, description));
	}

	/**
	 * Returns the value of the '<em><b>Persistence Unit Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XmlPersistence Unit Metadata</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Unit Metadata</em>' containment reference.
	 * @see #setPersistenceUnitMetadata(XmlPersistenceUnitMetadata)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntityMappings_PersistenceUnitMetadata()
	 * @model containment="true"
	 * @generated
	 */
	public XmlPersistenceUnitMetadata getPersistenceUnitMetadata()
	{
		return persistenceUnitMetadata;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPersistenceUnitMetadata(XmlPersistenceUnitMetadata newPersistenceUnitMetadata, NotificationChain msgs)
	{
		XmlPersistenceUnitMetadata oldPersistenceUnitMetadata = persistenceUnitMetadata;
		persistenceUnitMetadata = newPersistenceUnitMetadata;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA, oldPersistenceUnitMetadata, newPersistenceUnitMetadata);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getPersistenceUnitMetadata <em>Persistence Unit Metadata</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persistence Unit Metadata</em>' containment reference.
	 * @see #getPersistenceUnitMetadata()
	 * @generated
	 */
	public void setPersistenceUnitMetadata(XmlPersistenceUnitMetadata newPersistenceUnitMetadata)
	{
		if (newPersistenceUnitMetadata != persistenceUnitMetadata)
		{
			NotificationChain msgs = null;
			if (persistenceUnitMetadata != null)
				msgs = ((InternalEObject)persistenceUnitMetadata).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA, null, msgs);
			if (newPersistenceUnitMetadata != null)
				msgs = ((InternalEObject)newPersistenceUnitMetadata).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA, null, msgs);
			msgs = basicSetPersistenceUnitMetadata(newPersistenceUnitMetadata, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA, newPersistenceUnitMetadata, newPersistenceUnitMetadata));
	}

	/**
	 * Returns the value of the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package</em>' attribute.
	 * @see #setPackage(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntityMappings_Package()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getPackage()
	{
		return package_;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getPackage <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package</em>' attribute.
	 * @see #getPackage()
	 * @generated
	 */
	public void setPackage(String newPackage)
	{
		String oldPackage = package_;
		package_ = newPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_MAPPINGS__PACKAGE, oldPackage, package_));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntityMappings_Schema()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getSchema()
	{
		return schema;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getSchema <em>Schema</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_MAPPINGS__SCHEMA, oldSchema, schema));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntityMappings_Catalog()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getCatalog()
	{
		return catalog;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getCatalog <em>Catalog</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_MAPPINGS__CATALOG, oldCatalog, catalog));
	}

	/**
	 * Returns the value of the '<em><b>Access</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.core.resource.orm.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.AccessType
	 * @see #setAccess(AccessType)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlAccessHolder_Access()
	 * @model
	 * @generated
	 */
	public AccessType getAccess()
	{
		return access;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings#getAccess <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.AccessType
	 * @see #getAccess()
	 * @generated
	 */
	public void setAccess(AccessType newAccess)
	{
		AccessType oldAccess = access;
		access = newAccess == null ? ACCESS_EDEFAULT : newAccess;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ENTITY_MAPPINGS__ACCESS, oldAccess, access));
	}

	/**
	 * Returns the value of the '<em><b>Sequence Generators</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlSequenceGenerator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sequence Generators</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sequence Generators</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntityMappings_SequenceGenerators()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlSequenceGenerator> getSequenceGenerators()
	{
		if (sequenceGenerators == null)
		{
			sequenceGenerators = new EObjectContainmentEList<XmlSequenceGenerator>(XmlSequenceGenerator.class, this, OrmPackage.XML_ENTITY_MAPPINGS__SEQUENCE_GENERATORS);
		}
		return sequenceGenerators;
	}

	/**
	 * Returns the value of the '<em><b>Table Generators</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlTableGenerator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table Generators</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table Generators</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntityMappings_TableGenerators()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlTableGenerator> getTableGenerators()
	{
		if (tableGenerators == null)
		{
			tableGenerators = new EObjectContainmentEList<XmlTableGenerator>(XmlTableGenerator.class, this, OrmPackage.XML_ENTITY_MAPPINGS__TABLE_GENERATORS);
		}
		return tableGenerators;
	}

	/**
	 * Returns the value of the '<em><b>Sql Result Set Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.SqlResultSetMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sql Result Set Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sql Result Set Mappings</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntityMappings_SqlResultSetMappings()
	 * @model containment="true"
	 * @generated
	 */
	public EList<SqlResultSetMapping> getSqlResultSetMappings()
	{
		if (sqlResultSetMappings == null)
		{
			sqlResultSetMappings = new EObjectContainmentEList<SqlResultSetMapping>(SqlResultSetMapping.class, this, OrmPackage.XML_ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS);
		}
		return sqlResultSetMappings;
	}

	/**
	 * Returns the value of the '<em><b>Mapped Superclasses</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlMappedSuperclass}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapped Superclasses</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapped Superclasses</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntityMappings_MappedSuperclasses()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlMappedSuperclass> getMappedSuperclasses()
	{
		if (mappedSuperclasses == null)
		{
			mappedSuperclasses = new EObjectContainmentEList<XmlMappedSuperclass>(XmlMappedSuperclass.class, this, OrmPackage.XML_ENTITY_MAPPINGS__MAPPED_SUPERCLASSES);
		}
		return mappedSuperclasses;
	}

	/**
	 * Returns the value of the '<em><b>Entities</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlEntity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entities</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entities</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntityMappings_Entities()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlEntity> getEntities()
	{
		if (entities == null)
		{
			entities = new EObjectContainmentEList<XmlEntity>(XmlEntity.class, this, OrmPackage.XML_ENTITY_MAPPINGS__ENTITIES);
		}
		return entities;
	}

	/**
	 * Returns the value of the '<em><b>Embeddables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlEmbeddable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Embeddables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Embeddables</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlEntityMappings_Embeddables()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlEmbeddable> getEmbeddables()
	{
		if (embeddables == null)
		{
			embeddables = new EObjectContainmentEList<XmlEmbeddable>(XmlEmbeddable.class, this, OrmPackage.XML_ENTITY_MAPPINGS__EMBEDDABLES);
		}
		return embeddables;
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
			case OrmPackage.XML_ENTITY_MAPPINGS__NAMED_QUERIES:
				return ((InternalEList<?>)getNamedQueries()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES:
				return ((InternalEList<?>)getNamedNativeQueries()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA:
				return basicSetPersistenceUnitMetadata(null, msgs);
			case OrmPackage.XML_ENTITY_MAPPINGS__SEQUENCE_GENERATORS:
				return ((InternalEList<?>)getSequenceGenerators()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_MAPPINGS__TABLE_GENERATORS:
				return ((InternalEList<?>)getTableGenerators()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS:
				return ((InternalEList<?>)getSqlResultSetMappings()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_MAPPINGS__MAPPED_SUPERCLASSES:
				return ((InternalEList<?>)getMappedSuperclasses()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_MAPPINGS__ENTITIES:
				return ((InternalEList<?>)getEntities()).basicRemove(otherEnd, msgs);
			case OrmPackage.XML_ENTITY_MAPPINGS__EMBEDDABLES:
				return ((InternalEList<?>)getEmbeddables()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.XML_ENTITY_MAPPINGS__NAMED_QUERIES:
				return getNamedQueries();
			case OrmPackage.XML_ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES:
				return getNamedNativeQueries();
			case OrmPackage.XML_ENTITY_MAPPINGS__ACCESS:
				return getAccess();
			case OrmPackage.XML_ENTITY_MAPPINGS__DESCRIPTION:
				return getDescription();
			case OrmPackage.XML_ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA:
				return getPersistenceUnitMetadata();
			case OrmPackage.XML_ENTITY_MAPPINGS__PACKAGE:
				return getPackage();
			case OrmPackage.XML_ENTITY_MAPPINGS__SCHEMA:
				return getSchema();
			case OrmPackage.XML_ENTITY_MAPPINGS__CATALOG:
				return getCatalog();
			case OrmPackage.XML_ENTITY_MAPPINGS__SEQUENCE_GENERATORS:
				return getSequenceGenerators();
			case OrmPackage.XML_ENTITY_MAPPINGS__TABLE_GENERATORS:
				return getTableGenerators();
			case OrmPackage.XML_ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS:
				return getSqlResultSetMappings();
			case OrmPackage.XML_ENTITY_MAPPINGS__MAPPED_SUPERCLASSES:
				return getMappedSuperclasses();
			case OrmPackage.XML_ENTITY_MAPPINGS__ENTITIES:
				return getEntities();
			case OrmPackage.XML_ENTITY_MAPPINGS__EMBEDDABLES:
				return getEmbeddables();
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
			case OrmPackage.XML_ENTITY_MAPPINGS__NAMED_QUERIES:
				getNamedQueries().clear();
				getNamedQueries().addAll((Collection<? extends XmlNamedQuery>)newValue);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES:
				getNamedNativeQueries().clear();
				getNamedNativeQueries().addAll((Collection<? extends XmlNamedNativeQuery>)newValue);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__ACCESS:
				setAccess((AccessType)newValue);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA:
				setPersistenceUnitMetadata((XmlPersistenceUnitMetadata)newValue);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__PACKAGE:
				setPackage((String)newValue);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__SCHEMA:
				setSchema((String)newValue);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__CATALOG:
				setCatalog((String)newValue);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__SEQUENCE_GENERATORS:
				getSequenceGenerators().clear();
				getSequenceGenerators().addAll((Collection<? extends XmlSequenceGenerator>)newValue);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__TABLE_GENERATORS:
				getTableGenerators().clear();
				getTableGenerators().addAll((Collection<? extends XmlTableGenerator>)newValue);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS:
				getSqlResultSetMappings().clear();
				getSqlResultSetMappings().addAll((Collection<? extends SqlResultSetMapping>)newValue);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__MAPPED_SUPERCLASSES:
				getMappedSuperclasses().clear();
				getMappedSuperclasses().addAll((Collection<? extends XmlMappedSuperclass>)newValue);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__ENTITIES:
				getEntities().clear();
				getEntities().addAll((Collection<? extends XmlEntity>)newValue);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__EMBEDDABLES:
				getEmbeddables().clear();
				getEmbeddables().addAll((Collection<? extends XmlEmbeddable>)newValue);
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
			case OrmPackage.XML_ENTITY_MAPPINGS__NAMED_QUERIES:
				getNamedQueries().clear();
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES:
				getNamedNativeQueries().clear();
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__ACCESS:
				setAccess(ACCESS_EDEFAULT);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA:
				setPersistenceUnitMetadata((XmlPersistenceUnitMetadata)null);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__PACKAGE:
				setPackage(PACKAGE_EDEFAULT);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__SCHEMA:
				setSchema(SCHEMA_EDEFAULT);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__CATALOG:
				setCatalog(CATALOG_EDEFAULT);
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__SEQUENCE_GENERATORS:
				getSequenceGenerators().clear();
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__TABLE_GENERATORS:
				getTableGenerators().clear();
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS:
				getSqlResultSetMappings().clear();
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__MAPPED_SUPERCLASSES:
				getMappedSuperclasses().clear();
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__ENTITIES:
				getEntities().clear();
				return;
			case OrmPackage.XML_ENTITY_MAPPINGS__EMBEDDABLES:
				getEmbeddables().clear();
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
			case OrmPackage.XML_ENTITY_MAPPINGS__NAMED_QUERIES:
				return namedQueries != null && !namedQueries.isEmpty();
			case OrmPackage.XML_ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES:
				return namedNativeQueries != null && !namedNativeQueries.isEmpty();
			case OrmPackage.XML_ENTITY_MAPPINGS__ACCESS:
				return access != ACCESS_EDEFAULT;
			case OrmPackage.XML_ENTITY_MAPPINGS__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case OrmPackage.XML_ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA:
				return persistenceUnitMetadata != null;
			case OrmPackage.XML_ENTITY_MAPPINGS__PACKAGE:
				return PACKAGE_EDEFAULT == null ? package_ != null : !PACKAGE_EDEFAULT.equals(package_);
			case OrmPackage.XML_ENTITY_MAPPINGS__SCHEMA:
				return SCHEMA_EDEFAULT == null ? schema != null : !SCHEMA_EDEFAULT.equals(schema);
			case OrmPackage.XML_ENTITY_MAPPINGS__CATALOG:
				return CATALOG_EDEFAULT == null ? catalog != null : !CATALOG_EDEFAULT.equals(catalog);
			case OrmPackage.XML_ENTITY_MAPPINGS__SEQUENCE_GENERATORS:
				return sequenceGenerators != null && !sequenceGenerators.isEmpty();
			case OrmPackage.XML_ENTITY_MAPPINGS__TABLE_GENERATORS:
				return tableGenerators != null && !tableGenerators.isEmpty();
			case OrmPackage.XML_ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS:
				return sqlResultSetMappings != null && !sqlResultSetMappings.isEmpty();
			case OrmPackage.XML_ENTITY_MAPPINGS__MAPPED_SUPERCLASSES:
				return mappedSuperclasses != null && !mappedSuperclasses.isEmpty();
			case OrmPackage.XML_ENTITY_MAPPINGS__ENTITIES:
				return entities != null && !entities.isEmpty();
			case OrmPackage.XML_ENTITY_MAPPINGS__EMBEDDABLES:
				return embeddables != null && !embeddables.isEmpty();
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
		if (baseClass == XmlQueryContainer.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ENTITY_MAPPINGS__NAMED_QUERIES: return OrmPackage.XML_QUERY_CONTAINER__NAMED_QUERIES;
				case OrmPackage.XML_ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES: return OrmPackage.XML_QUERY_CONTAINER__NAMED_NATIVE_QUERIES;
				default: return -1;
			}
		}
		if (baseClass == XmlAccessHolder.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ENTITY_MAPPINGS__ACCESS: return OrmPackage.XML_ACCESS_HOLDER__ACCESS;
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
		if (baseClass == XmlQueryContainer.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_QUERY_CONTAINER__NAMED_QUERIES: return OrmPackage.XML_ENTITY_MAPPINGS__NAMED_QUERIES;
				case OrmPackage.XML_QUERY_CONTAINER__NAMED_NATIVE_QUERIES: return OrmPackage.XML_ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES;
				default: return -1;
			}
		}
		if (baseClass == XmlAccessHolder.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_ACCESS_HOLDER__ACCESS: return OrmPackage.XML_ENTITY_MAPPINGS__ACCESS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	// **************** overrides **********************************************
	
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
		result.append(", package: ");
		result.append(package_);
		result.append(", schema: ");
		result.append(schema);
		result.append(", catalog: ");
		result.append(catalog);
		result.append(')');
		return result.toString();
	}
	
	public List<XmlTypeMapping> getTypeMappings() {
		// convert lists to arrays to avoid ConcurrentModificationException while adding to result list
		ArrayList<XmlTypeMapping> typeMappings = new ArrayList<XmlTypeMapping>();
		CollectionTools.addAll(typeMappings, this.getMappedSuperclasses().toArray(EMPTY_XML_TYPE_MAPPING_ARRAY));
		CollectionTools.addAll(typeMappings, this.getEntities().toArray(EMPTY_XML_TYPE_MAPPING_ARRAY));
		CollectionTools.addAll(typeMappings, this.getEmbeddables().toArray(EMPTY_XML_TYPE_MAPPING_ARRAY));
		return typeMappings;
	}
	
	private static final XmlTypeMapping[] EMPTY_XML_TYPE_MAPPING_ARRAY = new XmlTypeMapping[0];
	
	
	// **************** version -> schema location mapping ********************
	
	private static String namespace = JPA.SCHEMA_NAMESPACE;
	
	@Override
	protected String getNamespace() {
		return namespace;
	}
	
	private static Map<String, String> versionsToSchemaLocations = buildVersionsToSchemaLocations();
	
	private static Map<String, String> buildVersionsToSchemaLocations() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(JPA.SCHEMA_VERSION, JPA.SCHEMA_LOCATION);
		map.put(JPA2_0.SCHEMA_VERSION, JPA2_0.SCHEMA_LOCATION);
		return map;
	}
	
	@Override
	protected String getSchemaLocationForVersion(String version) {
		return versionsToSchemaLocations.get(version);
	}
	
	
	// **************** translators *******************************************
	
	public static Translator getRootTranslator() {
		return ROOT_TRANSLATOR;
	}
	
	private static final Translator ROOT_TRANSLATOR = buildRootTranslator();
	
	private static Translator buildRootTranslator() {
		return new SimpleRootTranslator(
				JPA.ENTITY_MAPPINGS,
				OrmPackage.eINSTANCE.getXmlEntityMappings(),
				buildTranslatorChildren()
			);
	}
	
	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildVersionTranslator(versionsToSchemaLocations),
			buildNamespaceTranslator(namespace),
			buildSchemaNamespaceTranslator(),
			buildSchemaLocationTranslator(namespace, versionsToSchemaLocations),
			buildDescriptionTranslator(),
			XmlPersistenceUnitMetadata.buildTranslator(JPA.PERSISTENCE_UNIT_METADATA, OrmPackage.eINSTANCE.getXmlEntityMappings_PersistenceUnitMetadata()),
			buildPackageTranslator(),
			buildSchemaTranslator(),
			buildCatalogTranslator(),
			buildAccessTranslator(),
			XmlSequenceGenerator.buildTranslator(JPA.SEQUENCE_GENERATOR, OrmPackage.eINSTANCE.getXmlEntityMappings_SequenceGenerators()),
			XmlTableGenerator.buildTranslator(JPA.TABLE_GENERATOR, OrmPackage.eINSTANCE.getXmlEntityMappings_TableGenerators()),
			XmlNamedQuery.buildTranslator(JPA.NAMED_QUERY, OrmPackage.eINSTANCE.getXmlQueryContainer_NamedQueries()),
			XmlNamedNativeQuery.buildTranslator(JPA.NAMED_NATIVE_QUERY, OrmPackage.eINSTANCE.getXmlQueryContainer_NamedNativeQueries()),
			SqlResultSetMapping.buildTranslator(JPA.SQL_RESULT_SET_MAPPING, OrmPackage.eINSTANCE.getXmlEntityMappings_SqlResultSetMappings()),
			XmlMappedSuperclass.buildTranslator(JPA.MAPPED_SUPERCLASS, OrmPackage.eINSTANCE.getXmlEntityMappings_MappedSuperclasses()),
			XmlEntity.buildTranslator(JPA.ENTITY, OrmPackage.eINSTANCE.getXmlEntityMappings_Entities()),
			XmlEmbeddable.buildTranslator(JPA.EMBEDDABLE, OrmPackage.eINSTANCE.getXmlEntityMappings_Embeddables()),
		};
	}
	protected static Translator buildDescriptionTranslator() {
		return new Translator(JPA.DESCRIPTION, OrmPackage.eINSTANCE.getXmlEntityMappings_Description());
	}
		
	protected static Translator buildPackageTranslator() {
		return new Translator(JPA.PACKAGE, OrmPackage.eINSTANCE.getXmlEntityMappings_Package());
	}
	
	protected static Translator buildSchemaTranslator() {
		return new Translator(JPA.SCHEMA, OrmPackage.eINSTANCE.getXmlEntityMappings_Schema());
	}
	
	protected static Translator buildCatalogTranslator() {
		return new Translator(JPA.CATALOG, OrmPackage.eINSTANCE.getXmlEntityMappings_Catalog());
	}
	
	protected static Translator buildAccessTranslator() {
		return new Translator(JPA.ACCESS, OrmPackage.eINSTANCE.getXmlAccessHolder_Access());
	}


	// ********** refactorings **********

	public ReplaceEdit createRenamePackageEdit(String newName) {
		int offset = getElementNode(JPA.PACKAGE).getStartStructuredDocumentRegion().getEndOffset();
		return new ReplaceEdit(offset, this.package_.length(), newName);		
	}

}
