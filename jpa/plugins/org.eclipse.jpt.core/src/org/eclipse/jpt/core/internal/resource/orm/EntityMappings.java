/**
 * <copyright>
 * </copyright>
 *
 * $Id: EntityMappings.java,v 1.1.2.4 2007/11/01 16:45:10 kmoore Exp $
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
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.resource.common.IJptEObject;
import org.eclipse.jpt.core.internal.resource.common.JptEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity Mappings</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getVersion <em>Version</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getPersistenceUnitMetadata <em>XmlPersistence Unit Metadata</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getPackage <em>Package</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getSchema <em>Schema</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getCatalog <em>Catalog</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getAccess <em>Access</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getSequenceGenerators <em>Sequence Generators</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getTableGenerators <em>Table Generators</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getNamedQueries <em>Named Queries</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getNamedNativeQueries <em>Named Native Queries</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getSqlResultSetMappings <em>Sql Result Set Mappings</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getMappedSuperclasses <em>Mapped Superclasses</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getEntities <em>Entities</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getEmbeddables <em>Embeddables</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings()
 * @model kind="class"
 * @extends IJptEObject
 * @generated
 */
public class EntityMappings extends JptEObject implements IJptEObject
{
	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = "1.0";

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * This is true if the Version attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean versionESet;

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
	 * The cached value of the '{@link #getPersistenceUnitMetadata() <em>XmlPersistence Unit Metadata</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPersistenceUnitMetadata()
	 * @generated
	 * @ordered
	 */
	protected PersistenceUnitMetadata persistenceUnitMetadata;

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
	 * The cached value of the '{@link #getSequenceGenerators() <em>Sequence Generators</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSequenceGenerators()
	 * @generated
	 * @ordered
	 */
	protected EList<SequenceGenerator> sequenceGenerators;

	/**
	 * The cached value of the '{@link #getTableGenerators() <em>Table Generators</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTableGenerators()
	 * @generated
	 * @ordered
	 */
	protected EList<TableGenerator> tableGenerators;

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
	 * The cached value of the '{@link #getMappedSuperclasses() <em>Mapped Superclasses</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappedSuperclasses()
	 * @generated
	 * @ordered
	 */
	protected EList<MappedSuperclass> mappedSuperclasses;

	/**
	 * The cached value of the '{@link #getEntities() <em>Entities</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntities()
	 * @generated
	 * @ordered
	 */
	protected EList<Entity> entities;

	/**
	 * The cached value of the '{@link #getEmbeddables() <em>Embeddables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmbeddables()
	 * @generated
	 * @ordered
	 */
	protected EList<Embeddable> embeddables;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EntityMappings()
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
		return OrmPackage.Literals.ENTITY_MAPPINGS;
	}

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * The default value is <code>"1.0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #isSetVersion()
	 * @see #unsetVersion()
	 * @see #setVersion(String)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings_Version()
	 * @model default="1.0" unsettable="true" dataType="org.eclipse.jpt.core.internal.resource.orm.VersionType" required="true"
	 * @generated
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #isSetVersion()
	 * @see #unsetVersion()
	 * @see #getVersion()
	 * @generated
	 */
	public void setVersion(String newVersion)
	{
		String oldVersion = version;
		version = newVersion;
		boolean oldVersionESet = versionESet;
		versionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS__VERSION, oldVersion, version, !oldVersionESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetVersion()
	 * @see #getVersion()
	 * @see #setVersion(String)
	 * @generated
	 */
	public void unsetVersion()
	{
		String oldVersion = version;
		boolean oldVersionESet = versionESet;
		version = VERSION_EDEFAULT;
		versionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, OrmPackage.ENTITY_MAPPINGS__VERSION, oldVersion, VERSION_EDEFAULT, oldVersionESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getVersion <em>Version</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Version</em>' attribute is set.
	 * @see #unsetVersion()
	 * @see #getVersion()
	 * @see #setVersion(String)
	 * @generated
	 */
	public boolean isSetVersion()
	{
		return versionESet;
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getDescription <em>Description</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS__DESCRIPTION, oldDescription, description));
	}

	/**
	 * Returns the value of the '<em><b>XmlPersistence Unit Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XmlPersistence Unit Metadata</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XmlPersistence Unit Metadata</em>' containment reference.
	 * @see #setPersistenceUnitMetadata(PersistenceUnitMetadata)
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings_PersistenceUnitMetadata()
	 * @model containment="true"
	 * @generated
	 */
	public PersistenceUnitMetadata getPersistenceUnitMetadata()
	{
		return persistenceUnitMetadata;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPersistenceUnitMetadata(PersistenceUnitMetadata newPersistenceUnitMetadata, NotificationChain msgs)
	{
		PersistenceUnitMetadata oldPersistenceUnitMetadata = persistenceUnitMetadata;
		persistenceUnitMetadata = newPersistenceUnitMetadata;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA, oldPersistenceUnitMetadata, newPersistenceUnitMetadata);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getPersistenceUnitMetadata <em>XmlPersistence Unit Metadata</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>XmlPersistence Unit Metadata</em>' containment reference.
	 * @see #getPersistenceUnitMetadata()
	 * @generated
	 */
	public void setPersistenceUnitMetadata(PersistenceUnitMetadata newPersistenceUnitMetadata)
	{
		if (newPersistenceUnitMetadata != persistenceUnitMetadata)
		{
			NotificationChain msgs = null;
			if (persistenceUnitMetadata != null)
				msgs = ((InternalEObject)persistenceUnitMetadata).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA, null, msgs);
			if (newPersistenceUnitMetadata != null)
				msgs = ((InternalEObject)newPersistenceUnitMetadata).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA, null, msgs);
			msgs = basicSetPersistenceUnitMetadata(newPersistenceUnitMetadata, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA, newPersistenceUnitMetadata, newPersistenceUnitMetadata));
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings_Package()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getPackage()
	{
		return package_;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getPackage <em>Package</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS__PACKAGE, oldPackage, package_));
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings_Schema()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getSchema()
	{
		return schema;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getSchema <em>Schema</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS__SCHEMA, oldSchema, schema));
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings_Catalog()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getCatalog()
	{
		return catalog;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getCatalog <em>Catalog</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS__CATALOG, oldCatalog, catalog));
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings_Access()
	 * @model default="PROPERTY" unsettable="true"
	 * @generated
	 */
	public AccessType getAccess()
	{
		return access;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getAccess <em>Access</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS__ACCESS, oldAccess, access, !oldAccessESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getAccess <em>Access</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, OrmPackage.ENTITY_MAPPINGS__ACCESS, oldAccess, ACCESS_EDEFAULT, oldAccessESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.jpt.core.internal.resource.orm.EntityMappings#getAccess <em>Access</em>}' attribute is set.
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
	 * Returns the value of the '<em><b>Sequence Generators</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.SequenceGenerator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sequence Generators</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sequence Generators</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings_SequenceGenerators()
	 * @model containment="true"
	 * @generated
	 */
	public EList<SequenceGenerator> getSequenceGenerators()
	{
		if (sequenceGenerators == null)
		{
			sequenceGenerators = new EObjectContainmentEList<SequenceGenerator>(SequenceGenerator.class, this, OrmPackage.ENTITY_MAPPINGS__SEQUENCE_GENERATORS);
		}
		return sequenceGenerators;
	}

	/**
	 * Returns the value of the '<em><b>Table Generators</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.TableGenerator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table Generators</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table Generators</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings_TableGenerators()
	 * @model containment="true"
	 * @generated
	 */
	public EList<TableGenerator> getTableGenerators()
	{
		if (tableGenerators == null)
		{
			tableGenerators = new EObjectContainmentEList<TableGenerator>(TableGenerator.class, this, OrmPackage.ENTITY_MAPPINGS__TABLE_GENERATORS);
		}
		return tableGenerators;
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings_NamedQueries()
	 * @model containment="true"
	 * @generated
	 */
	public EList<NamedQuery> getNamedQueries()
	{
		if (namedQueries == null)
		{
			namedQueries = new EObjectContainmentEList<NamedQuery>(NamedQuery.class, this, OrmPackage.ENTITY_MAPPINGS__NAMED_QUERIES);
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings_NamedNativeQueries()
	 * @model containment="true"
	 * @generated
	 */
	public EList<NamedNativeQuery> getNamedNativeQueries()
	{
		if (namedNativeQueries == null)
		{
			namedNativeQueries = new EObjectContainmentEList<NamedNativeQuery>(NamedNativeQuery.class, this, OrmPackage.ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES);
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
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings_SqlResultSetMappings()
	 * @model containment="true"
	 * @generated
	 */
	public EList<SqlResultSetMapping> getSqlResultSetMappings()
	{
		if (sqlResultSetMappings == null)
		{
			sqlResultSetMappings = new EObjectContainmentEList<SqlResultSetMapping>(SqlResultSetMapping.class, this, OrmPackage.ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS);
		}
		return sqlResultSetMappings;
	}

	/**
	 * Returns the value of the '<em><b>Mapped Superclasses</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.MappedSuperclass}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapped Superclasses</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapped Superclasses</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings_MappedSuperclasses()
	 * @model containment="true"
	 * @generated
	 */
	public EList<MappedSuperclass> getMappedSuperclasses()
	{
		if (mappedSuperclasses == null)
		{
			mappedSuperclasses = new EObjectContainmentEList<MappedSuperclass>(MappedSuperclass.class, this, OrmPackage.ENTITY_MAPPINGS__MAPPED_SUPERCLASSES);
		}
		return mappedSuperclasses;
	}

	/**
	 * Returns the value of the '<em><b>Entities</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.Entity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entities</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entities</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings_Entities()
	 * @model containment="true"
	 * @generated
	 */
	public EList<Entity> getEntities()
	{
		if (entities == null)
		{
			entities = new EObjectContainmentEList<Entity>(Entity.class, this, OrmPackage.ENTITY_MAPPINGS__ENTITIES);
		}
		return entities;
	}

	/**
	 * Returns the value of the '<em><b>Embeddables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.resource.orm.Embeddable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Embeddables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Embeddables</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEntityMappings_Embeddables()
	 * @model containment="true"
	 * @generated
	 */
	public EList<Embeddable> getEmbeddables()
	{
		if (embeddables == null)
		{
			embeddables = new EObjectContainmentEList<Embeddable>(Embeddable.class, this, OrmPackage.ENTITY_MAPPINGS__EMBEDDABLES);
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
			case OrmPackage.ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA:
				return basicSetPersistenceUnitMetadata(null, msgs);
			case OrmPackage.ENTITY_MAPPINGS__SEQUENCE_GENERATORS:
				return ((InternalEList<?>)getSequenceGenerators()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY_MAPPINGS__TABLE_GENERATORS:
				return ((InternalEList<?>)getTableGenerators()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY_MAPPINGS__NAMED_QUERIES:
				return ((InternalEList<?>)getNamedQueries()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES:
				return ((InternalEList<?>)getNamedNativeQueries()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS:
				return ((InternalEList<?>)getSqlResultSetMappings()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY_MAPPINGS__MAPPED_SUPERCLASSES:
				return ((InternalEList<?>)getMappedSuperclasses()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY_MAPPINGS__ENTITIES:
				return ((InternalEList<?>)getEntities()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY_MAPPINGS__EMBEDDABLES:
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
			case OrmPackage.ENTITY_MAPPINGS__VERSION:
				return getVersion();
			case OrmPackage.ENTITY_MAPPINGS__DESCRIPTION:
				return getDescription();
			case OrmPackage.ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA:
				return getPersistenceUnitMetadata();
			case OrmPackage.ENTITY_MAPPINGS__PACKAGE:
				return getPackage();
			case OrmPackage.ENTITY_MAPPINGS__SCHEMA:
				return getSchema();
			case OrmPackage.ENTITY_MAPPINGS__CATALOG:
				return getCatalog();
			case OrmPackage.ENTITY_MAPPINGS__ACCESS:
				return getAccess();
			case OrmPackage.ENTITY_MAPPINGS__SEQUENCE_GENERATORS:
				return getSequenceGenerators();
			case OrmPackage.ENTITY_MAPPINGS__TABLE_GENERATORS:
				return getTableGenerators();
			case OrmPackage.ENTITY_MAPPINGS__NAMED_QUERIES:
				return getNamedQueries();
			case OrmPackage.ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES:
				return getNamedNativeQueries();
			case OrmPackage.ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS:
				return getSqlResultSetMappings();
			case OrmPackage.ENTITY_MAPPINGS__MAPPED_SUPERCLASSES:
				return getMappedSuperclasses();
			case OrmPackage.ENTITY_MAPPINGS__ENTITIES:
				return getEntities();
			case OrmPackage.ENTITY_MAPPINGS__EMBEDDABLES:
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
			case OrmPackage.ENTITY_MAPPINGS__VERSION:
				setVersion((String)newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA:
				setPersistenceUnitMetadata((PersistenceUnitMetadata)newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS__PACKAGE:
				setPackage((String)newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS__SCHEMA:
				setSchema((String)newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS__CATALOG:
				setCatalog((String)newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS__ACCESS:
				setAccess((AccessType)newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS__SEQUENCE_GENERATORS:
				getSequenceGenerators().clear();
				getSequenceGenerators().addAll((Collection<? extends SequenceGenerator>)newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS__TABLE_GENERATORS:
				getTableGenerators().clear();
				getTableGenerators().addAll((Collection<? extends TableGenerator>)newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS__NAMED_QUERIES:
				getNamedQueries().clear();
				getNamedQueries().addAll((Collection<? extends NamedQuery>)newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES:
				getNamedNativeQueries().clear();
				getNamedNativeQueries().addAll((Collection<? extends NamedNativeQuery>)newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS:
				getSqlResultSetMappings().clear();
				getSqlResultSetMappings().addAll((Collection<? extends SqlResultSetMapping>)newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS__MAPPED_SUPERCLASSES:
				getMappedSuperclasses().clear();
				getMappedSuperclasses().addAll((Collection<? extends MappedSuperclass>)newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS__ENTITIES:
				getEntities().clear();
				getEntities().addAll((Collection<? extends Entity>)newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS__EMBEDDABLES:
				getEmbeddables().clear();
				getEmbeddables().addAll((Collection<? extends Embeddable>)newValue);
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
			case OrmPackage.ENTITY_MAPPINGS__VERSION:
				unsetVersion();
				return;
			case OrmPackage.ENTITY_MAPPINGS__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case OrmPackage.ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA:
				setPersistenceUnitMetadata((PersistenceUnitMetadata)null);
				return;
			case OrmPackage.ENTITY_MAPPINGS__PACKAGE:
				setPackage(PACKAGE_EDEFAULT);
				return;
			case OrmPackage.ENTITY_MAPPINGS__SCHEMA:
				setSchema(SCHEMA_EDEFAULT);
				return;
			case OrmPackage.ENTITY_MAPPINGS__CATALOG:
				setCatalog(CATALOG_EDEFAULT);
				return;
			case OrmPackage.ENTITY_MAPPINGS__ACCESS:
				unsetAccess();
				return;
			case OrmPackage.ENTITY_MAPPINGS__SEQUENCE_GENERATORS:
				getSequenceGenerators().clear();
				return;
			case OrmPackage.ENTITY_MAPPINGS__TABLE_GENERATORS:
				getTableGenerators().clear();
				return;
			case OrmPackage.ENTITY_MAPPINGS__NAMED_QUERIES:
				getNamedQueries().clear();
				return;
			case OrmPackage.ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES:
				getNamedNativeQueries().clear();
				return;
			case OrmPackage.ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS:
				getSqlResultSetMappings().clear();
				return;
			case OrmPackage.ENTITY_MAPPINGS__MAPPED_SUPERCLASSES:
				getMappedSuperclasses().clear();
				return;
			case OrmPackage.ENTITY_MAPPINGS__ENTITIES:
				getEntities().clear();
				return;
			case OrmPackage.ENTITY_MAPPINGS__EMBEDDABLES:
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
			case OrmPackage.ENTITY_MAPPINGS__VERSION:
				return isSetVersion();
			case OrmPackage.ENTITY_MAPPINGS__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case OrmPackage.ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA:
				return persistenceUnitMetadata != null;
			case OrmPackage.ENTITY_MAPPINGS__PACKAGE:
				return PACKAGE_EDEFAULT == null ? package_ != null : !PACKAGE_EDEFAULT.equals(package_);
			case OrmPackage.ENTITY_MAPPINGS__SCHEMA:
				return SCHEMA_EDEFAULT == null ? schema != null : !SCHEMA_EDEFAULT.equals(schema);
			case OrmPackage.ENTITY_MAPPINGS__CATALOG:
				return CATALOG_EDEFAULT == null ? catalog != null : !CATALOG_EDEFAULT.equals(catalog);
			case OrmPackage.ENTITY_MAPPINGS__ACCESS:
				return isSetAccess();
			case OrmPackage.ENTITY_MAPPINGS__SEQUENCE_GENERATORS:
				return sequenceGenerators != null && !sequenceGenerators.isEmpty();
			case OrmPackage.ENTITY_MAPPINGS__TABLE_GENERATORS:
				return tableGenerators != null && !tableGenerators.isEmpty();
			case OrmPackage.ENTITY_MAPPINGS__NAMED_QUERIES:
				return namedQueries != null && !namedQueries.isEmpty();
			case OrmPackage.ENTITY_MAPPINGS__NAMED_NATIVE_QUERIES:
				return namedNativeQueries != null && !namedNativeQueries.isEmpty();
			case OrmPackage.ENTITY_MAPPINGS__SQL_RESULT_SET_MAPPINGS:
				return sqlResultSetMappings != null && !sqlResultSetMappings.isEmpty();
			case OrmPackage.ENTITY_MAPPINGS__MAPPED_SUPERCLASSES:
				return mappedSuperclasses != null && !mappedSuperclasses.isEmpty();
			case OrmPackage.ENTITY_MAPPINGS__ENTITIES:
				return entities != null && !entities.isEmpty();
			case OrmPackage.ENTITY_MAPPINGS__EMBEDDABLES:
				return embeddables != null && !embeddables.isEmpty();
		}
		return super.eIsSet(featureID);
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
		result.append(" (version: ");
		if (versionESet) result.append(version); else result.append("<unset>");
		result.append(", description: ");
		result.append(description);
		result.append(", package: ");
		result.append(package_);
		result.append(", schema: ");
		result.append(schema);
		result.append(", catalog: ");
		result.append(catalog);
		result.append(", access: ");
		if (accessESet) result.append(access); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}
	
	@Override
	public IJptEObject root() {
		return this;
	}
	
	@Override
	public IJpaFile jpaFile() {
		return resourceModel().jpaFile();
	}
	
	@Override
	public OrmResourceModel resourceModel() {
		return (OrmResourceModel) eResource();
	}
}