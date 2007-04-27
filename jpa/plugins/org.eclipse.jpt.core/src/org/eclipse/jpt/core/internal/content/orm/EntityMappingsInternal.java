/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.AccessType;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity Mappings Internal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getRoot <em>Root</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getVersion <em>Version</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getPersistenceUnitMetadataInternal <em>Persistence Unit Metadata Internal</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getPackageInternal <em>Package Internal</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getDefaultSchema <em>Default Schema</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSpecifiedSchema <em>Specified Schema</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSchema <em>Schema</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getDefaultCatalog <em>Default Catalog</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSpecifiedCatalog <em>Specified Catalog</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getCatalog <em>Catalog</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getDefaultAccess <em>Default Access</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSpecifiedAccess <em>Specified Access</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getAccess <em>Access</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getTypeMappings <em>Type Mappings</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getPersistentTypes <em>Persistent Types</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSequenceGenerators <em>Sequence Generators</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getTableGenerators <em>Table Generators</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getNamedQueries <em>Named Queries</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getNamedNativeQueries <em>Named Native Queries</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal()
 * @model kind="class"
 * @generated
 */
public class EntityMappingsInternal extends XmlEObject
	implements IJpaContentNode, EntityMappingsForXml, EntityMappings
{
	/**
	 * The default value of the '{@link #getPackageForXml() <em>Package For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageForXml()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_FOR_XML_EDEFAULT = null;

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
	 * The cached value of the '{@link #getRoot() <em>Root</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRoot()
	 * @generated
	 * @ordered
	 */
	protected XmlRootContentNode root;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

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
	 * The cached value of the '{@link #getPersistenceUnitMetadataInternal() <em>Persistence Unit Metadata Internal</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPersistenceUnitMetadataInternal()
	 * @generated
	 * @ordered
	 */
	protected PersistenceUnitMetadataInternal persistenceUnitMetadataInternal;

	/**
	 * The default value of the '{@link #getPackageInternal() <em>Package Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageInternal()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_INTERNAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPackageInternal() <em>Package Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageInternal()
	 * @generated
	 * @ordered
	 */
	protected String packageInternal = PACKAGE_INTERNAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultSchema() <em>Default Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultSchema()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_SCHEMA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultSchema() <em>Default Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultSchema()
	 * @generated
	 * @ordered
	 */
	protected String defaultSchema = DEFAULT_SCHEMA_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpecifiedSchema() <em>Specified Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedSchema()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_SCHEMA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedSchema() <em>Specified Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedSchema()
	 * @generated
	 * @ordered
	 */
	protected String specifiedSchema = SPECIFIED_SCHEMA_EDEFAULT;

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
	 * The default value of the '{@link #getDefaultCatalog() <em>Default Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultCatalog()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_CATALOG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultCatalog() <em>Default Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultCatalog()
	 * @generated
	 * @ordered
	 */
	protected String defaultCatalog = DEFAULT_CATALOG_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpecifiedCatalog() <em>Specified Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedCatalog()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_CATALOG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedCatalog() <em>Specified Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedCatalog()
	 * @generated
	 * @ordered
	 */
	protected String specifiedCatalog = SPECIFIED_CATALOG_EDEFAULT;

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
	 * The default value of the '{@link #getDefaultAccess() <em>Default Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultAccess()
	 * @generated
	 * @ordered
	 */
	protected static final AccessType DEFAULT_ACCESS_EDEFAULT = AccessType.DEFAULT;

	/**
	 * The cached value of the '{@link #getDefaultAccess() <em>Default Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultAccess()
	 * @generated
	 * @ordered
	 */
	protected AccessType defaultAccess = DEFAULT_ACCESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpecifiedAccess() <em>Specified Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedAccess()
	 * @generated
	 * @ordered
	 */
	protected static final AccessType SPECIFIED_ACCESS_EDEFAULT = AccessType.DEFAULT;

	/**
	 * The cached value of the '{@link #getSpecifiedAccess() <em>Specified Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedAccess()
	 * @generated
	 * @ordered
	 */
	protected AccessType specifiedAccess = SPECIFIED_ACCESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getAccess() <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccess()
	 * @generated
	 * @ordered
	 */
	protected static final AccessType ACCESS_EDEFAULT = AccessType.DEFAULT;

	/**
	 * The cached value of the '{@link #getTypeMappings() <em>Type Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlTypeMapping> typeMappings;

	/**
	 * The cached value of the '{@link #getPersistentTypes() <em>Persistent Types</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPersistentTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlPersistentType> persistentTypes;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected EntityMappingsInternal() {
		super();
		//we don't want a setter for this object since it should never be null, but
		//it must be initialized and is necessary for emf to call the eInverseAdd method
		this.persistenceUnitMetadataInternal = OrmFactory.eINSTANCE.createPersistenceUnitMetadataInternal();
		((InternalEObject) this.persistenceUnitMetadataInternal).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_INTERNAL, null, null);
	}

	@Override
	protected void addInsignificantFeatureIdsTo(Set<Integer> insignificantFeatureIds) {
		super.addInsignificantFeatureIdsTo(insignificantFeatureIds);
		insignificantFeatureIds.add(OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENT_TYPES);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.ENTITY_MAPPINGS_INTERNAL;
	}

	/**
	 * Returns the value of the '<em><b>Access</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.content.orm.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.AccessType
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_Access()
	 * @model transient="true" changeable="false" volatile="true"
	 * @generated NOT
	 */
	public AccessType getAccess() {
		return (this.getSpecifiedAccess() == null) ? this.getDefaultAccess() : this.getSpecifiedAccess();
	}

	/**
	 * Returns the value of the '<em><b>Persistence Unit Metadata For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * API just for the xml translators. Null in the model for a containment 
	 * object corresponds to no persistence-unit-metadata xml tag in the xml file.
	 * We check for whether any features are set in the model and return null for
	 * persistenceUnitMetadataForXml if there aren't any.  Otherwise we return
	 * the persistenceUnitMetadataInternal that has already been created.
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Unit Metadata For Xml</em>' reference.
	 * @see #setPersistenceUnitMetadataForXml(PersistenceUnitMetadataForXml)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsForXml_PersistenceUnitMetadataForXml()
	 * @model resolveProxies="false" volatile="true"
	 * @generated NOT
	 */
	public PersistenceUnitMetadataForXml getPersistenceUnitMetadataForXml() {
		if (getPersistenceUnitMetadataInternal().isAllFeaturesUnset()) {
			return null;
		}
		return getPersistenceUnitMetadataInternal();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getPersistenceUnitMetadataForXml <em>Persistence Unit Metadata For Xml</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persistence Unit Metadata For Xml</em>' reference.
	 * @see #getPersistenceUnitMetadataForXml()
	 * @generated NOT
	 */
	public void setPersistenceUnitMetadataForXmlGen(PersistenceUnitMetadataForXml newPersistenceUnitMetadataForXml) {
		PersistenceUnitMetadataForXml oldValue = newPersistenceUnitMetadataForXml == null ? (PersistenceUnitMetadataForXml) getPersistenceUnitMetadata() : null;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_FOR_XML, oldValue, newPersistenceUnitMetadataForXml));
	}

	public void setPersistenceUnitMetadataForXml(PersistenceUnitMetadataForXml newPersistenceUnitMetadataForXml) {
		setPersistenceUnitMetadataForXmlGen(newPersistenceUnitMetadataForXml);
		if (newPersistenceUnitMetadataForXml == null) {
			getPersistenceUnitMetadataInternal().unsetAllAttributes();
		}
	}

	/**
	 * Returns the value of the '<em><b>Package For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package For Xml</em>' attribute.
	 * @see #setPackageForXml(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsForXml_PackageForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public String getPackageForXml() {
		return getPackageInternal();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getPackageForXml <em>Package For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package For Xml</em>' attribute.
	 * @see #getPackageForXml()
	 * @generated NOT
	 */
	public void setPackageForXml(String newPackageForXml) {
		String oldValue = getPackageForXml();
		setPackageInternal(newPackageForXml);
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE_FOR_XML, oldValue, newPackageForXml));
	}

	public void makePersistenceUnitMetadataForXmlNull() {
		setPersistenceUnitMetadataForXmlGen(null);
	}

	public void makePersistenceUnitMetadataForXmlNonNull() {
		setPersistenceUnitMetadataForXmlGen(getPersistenceUnitMetadataForXml());
	}

	/**
	 * Returns the value of the '<em><b>Persistence Unit Metadata</b></em>' containment reference.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persistence Unit Metadata</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Unit Metadata</em>' containment reference.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappings_PersistenceUnitMetadata()
	 * @model containment="true" required="true" changeable="false" volatile="true"
	 * @generated NOT
	 */
	public PersistenceUnitMetadata getPersistenceUnitMetadata() {
		return getPersistenceUnitMetadataInternal();
	}

	/**
	 * Returns the value of the '<em><b>Root</b></em>' reference.
	 * The default value is <code>""</code>.
	 * It is bidirectional and its opposite is '{@link org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode#getEntityMappings <em>Entity Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root</em>' reference.
	 * @see #setRoot(XmlRootContentNode)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_Root()
	 * @see org.eclipse.jpt.core.internal.content.orm.XmlRootContentNode#getEntityMappings
	 * @model opposite="entityMappings" resolveProxies="false" required="true" ordered="false"
	 * @generated
	 */
	public XmlRootContentNode getRoot() {
		return root;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRoot(XmlRootContentNode newRoot, NotificationChain msgs) {
		XmlRootContentNode oldRoot = root;
		root = newRoot;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__ROOT, oldRoot, newRoot);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getRoot <em>Root</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root</em>' reference.
	 * @see #getRoot()
	 * @generated
	 */
	public void setRoot(XmlRootContentNode newRoot) {
		if (newRoot != root) {
			NotificationChain msgs = null;
			if (root != null)
				msgs = ((InternalEObject) root).eInverseRemove(this, OrmPackage.XML_ROOT_CONTENT_NODE__ENTITY_MAPPINGS, XmlRootContentNode.class, msgs);
			if (newRoot != null)
				msgs = ((InternalEObject) newRoot).eInverseAdd(this, OrmPackage.XML_ROOT_CONTENT_NODE__ENTITY_MAPPINGS, XmlRootContentNode.class, msgs);
			msgs = basicSetRoot(newRoot, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__ROOT, newRoot, newRoot));
	}

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_Version()
	 * @model
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__VERSION, oldVersion, version));
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_Description()
	 * @model
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__DESCRIPTION, oldDescription, description));
	}

	/**
	 * Returns the value of the '<em><b>Persistence Unit Metadata Internal</b></em>' containment reference.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persistence Unit Metadata Internal</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Unit Metadata Internal</em>' containment reference.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_PersistenceUnitMetadataInternal()
	 * @model containment="true" required="true" changeable="false"
	 * @generated
	 */
	public PersistenceUnitMetadataInternal getPersistenceUnitMetadataInternal() {
		return persistenceUnitMetadataInternal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPersistenceUnitMetadataInternal(PersistenceUnitMetadataInternal newPersistenceUnitMetadataInternal, NotificationChain msgs) {
		PersistenceUnitMetadataInternal oldPersistenceUnitMetadataInternal = persistenceUnitMetadataInternal;
		persistenceUnitMetadataInternal = newPersistenceUnitMetadataInternal;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_INTERNAL, oldPersistenceUnitMetadataInternal, newPersistenceUnitMetadataInternal);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Returns the value of the '<em><b>Package Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package Internal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package Internal</em>' attribute.
	 * @see #setPackageInternal(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_PackageInternal()
	 * @model
	 * @generated
	 */
	public String getPackageInternal() {
		return packageInternal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getPackageInternal <em>Package Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Internal</em>' attribute.
	 * @see #getPackageInternal()
	 * @generated NOT
	 */
	public void setPackageInternal(String newPackageInternal) {
		String oldPackageInternal = packageInternal;
		packageInternal = newPackageInternal;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE_INTERNAL, oldPackageInternal, packageInternal));
			//notification so the UI is updated when the xml changes, can't call the UI api 
			//because it has other side effects
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE, oldPackageInternal, packageInternal));
		}
	}

	/**
	 * Returns the value of the '<em><b>Default Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Schema</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Schema</em>' attribute.
	 * @see #setDefaultSchema(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_DefaultSchema()
	 * @model
	 * @generated
	 */
	public String getDefaultSchema() {
		return defaultSchema;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getDefaultSchema <em>Default Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Schema</em>' attribute.
	 * @see #getDefaultSchema()
	 * @generated
	 */
	public void setDefaultSchema(String newDefaultSchema) {
		String oldDefaultSchema = defaultSchema;
		defaultSchema = newDefaultSchema;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_SCHEMA, oldDefaultSchema, defaultSchema));
	}

	/**
	 * Returns the value of the '<em><b>Specified Schema</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Schema</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Schema</em>' attribute.
	 * @see #setSpecifiedSchema(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_SpecifiedSchema()
	 * @model
	 * @generated
	 */
	public String getSpecifiedSchema() {
		return specifiedSchema;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSpecifiedSchema <em>Specified Schema</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Schema</em>' attribute.
	 * @see #getSpecifiedSchema()
	 * @generated
	 */
	public void setSpecifiedSchema(String newSpecifiedSchema) {
		String oldSpecifiedSchema = specifiedSchema;
		specifiedSchema = newSpecifiedSchema;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_SCHEMA, oldSpecifiedSchema, specifiedSchema));
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappings_Package()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public String getPackage() {
		return getPackageInternal();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getPackage <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package</em>' attribute.
	 * @see #getPackage()
	 * @generated NOT
	 */
	public void setPackage(String newPackage) {
		if (newPackage == "") {
			newPackage = null;
		}
		setPackageInternal(newPackage);
		setPackageForXml(newPackage);
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_Schema()
	 * @model transient="true" changeable="false" volatile="true"
	 * @generated NOT
	 */
	public String getSchema() {
		return (this.getSpecifiedSchema() == null) ? this.getDefaultSchema() : this.getSpecifiedSchema();
	}

	/**
	 * Returns the value of the '<em><b>Default Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Catalog</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Catalog</em>' attribute.
	 * @see #setDefaultCatalog(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_DefaultCatalog()
	 * @model
	 * @generated
	 */
	public String getDefaultCatalog() {
		return defaultCatalog;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getDefaultCatalog <em>Default Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Catalog</em>' attribute.
	 * @see #getDefaultCatalog()
	 * @generated
	 */
	public void setDefaultCatalog(String newDefaultCatalog) {
		String oldDefaultCatalog = defaultCatalog;
		defaultCatalog = newDefaultCatalog;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_CATALOG, oldDefaultCatalog, defaultCatalog));
	}

	/**
	 * Returns the value of the '<em><b>Specified Catalog</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Catalog</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Catalog</em>' attribute.
	 * @see #setSpecifiedCatalog(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_SpecifiedCatalog()
	 * @model
	 * @generated
	 */
	public String getSpecifiedCatalog() {
		return specifiedCatalog;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSpecifiedCatalog <em>Specified Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Catalog</em>' attribute.
	 * @see #getSpecifiedCatalog()
	 * @generated
	 */
	public void setSpecifiedCatalog(String newSpecifiedCatalog) {
		String oldSpecifiedCatalog = specifiedCatalog;
		specifiedCatalog = newSpecifiedCatalog;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_CATALOG, oldSpecifiedCatalog, specifiedCatalog));
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_Catalog()
	 * @model transient="true" changeable="false" volatile="true"
	 * @generated NOT
	 */
	public String getCatalog() {
		return (this.getSpecifiedCatalog() == null) ? this.getDefaultCatalog() : this.getSpecifiedCatalog();
	}

	/**
	 * Returns the value of the '<em><b>Default Access</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Access</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.AccessType
	 * @see #setDefaultAccess(AccessType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_DefaultAccess()
	 * @model
	 * @generated
	 */
	public AccessType getDefaultAccess() {
		return defaultAccess;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getDefaultAccess <em>Default Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Access</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.AccessType
	 * @see #getDefaultAccess()
	 * @generated
	 */
	public void setDefaultAccess(AccessType newDefaultAccess) {
		AccessType oldDefaultAccess = defaultAccess;
		defaultAccess = newDefaultAccess == null ? DEFAULT_ACCESS_EDEFAULT : newDefaultAccess;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_ACCESS, oldDefaultAccess, defaultAccess));
	}

	/**
	 * Returns the value of the '<em><b>Specified Access</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Access</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.AccessType
	 * @see #setSpecifiedAccess(AccessType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_SpecifiedAccess()
	 * @model
	 * @generated
	 */
	public AccessType getSpecifiedAccess() {
		return specifiedAccess;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal#getSpecifiedAccess <em>Specified Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Access</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.AccessType
	 * @see #getSpecifiedAccess()
	 * @generated
	 */
	public void setSpecifiedAccess(AccessType newSpecifiedAccess) {
		AccessType oldSpecifiedAccess = specifiedAccess;
		specifiedAccess = newSpecifiedAccess == null ? SPECIFIED_ACCESS_EDEFAULT : newSpecifiedAccess;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_ACCESS, oldSpecifiedAccess, specifiedAccess));
	}

	/**
	 * Returns the value of the '<em><b>Type Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Mappings</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_TypeMappings()
	 * @model type="org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping" containment="true"
	 * @generated
	 */
	public EList<XmlTypeMapping> getTypeMappingsGen() {
		if (typeMappings == null) {
			typeMappings = new EObjectContainmentEList<XmlTypeMapping>(XmlTypeMapping.class, this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__TYPE_MAPPINGS);
		}
		return typeMappings;
	}

	public EList<XmlTypeMapping> getTypeMappings() {
		if (typeMappings == null) {
			typeMappings = new TypeMappingsList<XmlTypeMapping>();
		}
		return getTypeMappingsGen();
	}

	/**
	 * Returns the value of the '<em><b>Persistent Types</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.orm.XmlPersistentType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persistent Types</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistent Types</em>' reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_PersistentTypes()
	 * @model type="org.eclipse.jpt.core.internal.content.orm.XmlPersistentType" resolveProxies="false"
	 * @generated
	 */
	public EList<XmlPersistentType> getPersistentTypes() {
		if (persistentTypes == null) {
			persistentTypes = new EObjectEList<XmlPersistentType>(XmlPersistentType.class, this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENT_TYPES);
		}
		return persistentTypes;
	}

	public boolean containsPersistentType(IType type) {
		if (type == null) {
			return false;
		}
		for (XmlPersistentType each : getPersistentTypes()) {
			if (type.equals(each.findJdtType())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the value of the '<em><b>Table Generators</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.orm.XmlTableGenerator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table Generators</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table Generators</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_TableGenerators()
	 * @model type="org.eclipse.jpt.core.internal.content.orm.XmlTableGenerator" containment="true"
	 * @generated
	 */
	public EList<XmlTableGenerator> getTableGenerators() {
		if (tableGenerators == null) {
			tableGenerators = new EObjectContainmentEList<XmlTableGenerator>(XmlTableGenerator.class, this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__TABLE_GENERATORS);
		}
		return tableGenerators;
	}

	/**
	 * Returns the value of the '<em><b>Named Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.orm.XmlNamedQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_NamedQueries()
	 * @model type="org.eclipse.jpt.core.internal.content.orm.XmlNamedQuery" containment="true"
	 * @generated
	 */
	public EList<XmlNamedQuery> getNamedQueries() {
		if (namedQueries == null) {
			namedQueries = new EObjectContainmentEList<XmlNamedQuery>(XmlNamedQuery.class, this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__NAMED_QUERIES);
		}
		return namedQueries;
	}

	/**
	 * Returns the value of the '<em><b>Named Native Queries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.orm.XmlNamedNativeQuery}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Named Native Queries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Named Native Queries</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_NamedNativeQueries()
	 * @model type="org.eclipse.jpt.core.internal.content.orm.XmlNamedNativeQuery" containment="true"
	 * @generated
	 */
	public EList<XmlNamedNativeQuery> getNamedNativeQueries() {
		if (namedNativeQueries == null) {
			namedNativeQueries = new EObjectContainmentEList<XmlNamedNativeQuery>(XmlNamedNativeQuery.class, this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__NAMED_NATIVE_QUERIES);
		}
		return namedNativeQueries;
	}

	/**
	 * Returns the value of the '<em><b>Sequence Generators</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.orm.XmlSequenceGenerator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sequence Generators</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sequence Generators</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getEntityMappingsInternal_SequenceGenerators()
	 * @model type="org.eclipse.jpt.core.internal.content.orm.XmlSequenceGenerator" containment="true"
	 * @generated
	 */
	public EList<XmlSequenceGenerator> getSequenceGenerators() {
		if (sequenceGenerators == null) {
			sequenceGenerators = new EObjectContainmentEList<XmlSequenceGenerator>(XmlSequenceGenerator.class, this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__SEQUENCE_GENERATORS);
		}
		return sequenceGenerators;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__ROOT :
				if (root != null)
					msgs = ((InternalEObject) root).eInverseRemove(this, OrmPackage.XML_ROOT_CONTENT_NODE__ENTITY_MAPPINGS, XmlRootContentNode.class, msgs);
				return basicSetRoot((XmlRootContentNode) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__ROOT :
				return basicSetRoot(null, msgs);
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_INTERNAL :
				return basicSetPersistenceUnitMetadataInternal(null, msgs);
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__TYPE_MAPPINGS :
				return ((InternalEList<?>) getTypeMappings()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SEQUENCE_GENERATORS :
				return ((InternalEList<?>) getSequenceGenerators()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__TABLE_GENERATORS :
				return ((InternalEList<?>) getTableGenerators()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__NAMED_QUERIES :
				return ((InternalEList<?>) getNamedQueries()).basicRemove(otherEnd, msgs);
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__NAMED_NATIVE_QUERIES :
				return ((InternalEList<?>) getNamedNativeQueries()).basicRemove(otherEnd, msgs);
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
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_FOR_XML :
				return getPersistenceUnitMetadataForXml();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE_FOR_XML :
				return getPackageForXml();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA :
				return getPersistenceUnitMetadata();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE :
				return getPackage();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__ROOT :
				return getRoot();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__VERSION :
				return getVersion();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__DESCRIPTION :
				return getDescription();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_INTERNAL :
				return getPersistenceUnitMetadataInternal();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE_INTERNAL :
				return getPackageInternal();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_SCHEMA :
				return getDefaultSchema();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_SCHEMA :
				return getSpecifiedSchema();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SCHEMA :
				return getSchema();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_CATALOG :
				return getDefaultCatalog();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_CATALOG :
				return getSpecifiedCatalog();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__CATALOG :
				return getCatalog();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_ACCESS :
				return getDefaultAccess();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_ACCESS :
				return getSpecifiedAccess();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__ACCESS :
				return getAccess();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__TYPE_MAPPINGS :
				return getTypeMappings();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENT_TYPES :
				return getPersistentTypes();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SEQUENCE_GENERATORS :
				return getSequenceGenerators();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__TABLE_GENERATORS :
				return getTableGenerators();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__NAMED_QUERIES :
				return getNamedQueries();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__NAMED_NATIVE_QUERIES :
				return getNamedNativeQueries();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_FOR_XML :
				setPersistenceUnitMetadataForXml((PersistenceUnitMetadataForXml) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE_FOR_XML :
				setPackageForXml((String) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE :
				setPackage((String) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__ROOT :
				setRoot((XmlRootContentNode) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__VERSION :
				setVersion((String) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__DESCRIPTION :
				setDescription((String) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE_INTERNAL :
				setPackageInternal((String) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_SCHEMA :
				setDefaultSchema((String) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_SCHEMA :
				setSpecifiedSchema((String) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_CATALOG :
				setDefaultCatalog((String) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_CATALOG :
				setSpecifiedCatalog((String) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_ACCESS :
				setDefaultAccess((AccessType) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_ACCESS :
				setSpecifiedAccess((AccessType) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__TYPE_MAPPINGS :
				getTypeMappings().clear();
				getTypeMappings().addAll((Collection<? extends XmlTypeMapping>) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENT_TYPES :
				getPersistentTypes().clear();
				getPersistentTypes().addAll((Collection<? extends XmlPersistentType>) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SEQUENCE_GENERATORS :
				getSequenceGenerators().clear();
				getSequenceGenerators().addAll((Collection<? extends XmlSequenceGenerator>) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__TABLE_GENERATORS :
				getTableGenerators().clear();
				getTableGenerators().addAll((Collection<? extends XmlTableGenerator>) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__NAMED_QUERIES :
				getNamedQueries().clear();
				getNamedQueries().addAll((Collection<? extends XmlNamedQuery>) newValue);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__NAMED_NATIVE_QUERIES :
				getNamedNativeQueries().clear();
				getNamedNativeQueries().addAll((Collection<? extends XmlNamedNativeQuery>) newValue);
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
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_FOR_XML :
				setPersistenceUnitMetadataForXml((PersistenceUnitMetadataForXml) null);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE_FOR_XML :
				setPackageForXml(PACKAGE_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE :
				setPackage(PACKAGE_EDEFAULT);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__ROOT :
				setRoot((XmlRootContentNode) null);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__VERSION :
				setVersion(VERSION_EDEFAULT);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__DESCRIPTION :
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE_INTERNAL :
				setPackageInternal(PACKAGE_INTERNAL_EDEFAULT);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_SCHEMA :
				setDefaultSchema(DEFAULT_SCHEMA_EDEFAULT);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_SCHEMA :
				setSpecifiedSchema(SPECIFIED_SCHEMA_EDEFAULT);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_CATALOG :
				setDefaultCatalog(DEFAULT_CATALOG_EDEFAULT);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_CATALOG :
				setSpecifiedCatalog(SPECIFIED_CATALOG_EDEFAULT);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_ACCESS :
				setDefaultAccess(DEFAULT_ACCESS_EDEFAULT);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_ACCESS :
				setSpecifiedAccess(SPECIFIED_ACCESS_EDEFAULT);
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__TYPE_MAPPINGS :
				getTypeMappings().clear();
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENT_TYPES :
				getPersistentTypes().clear();
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SEQUENCE_GENERATORS :
				getSequenceGenerators().clear();
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__TABLE_GENERATORS :
				getTableGenerators().clear();
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__NAMED_QUERIES :
				getNamedQueries().clear();
				return;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__NAMED_NATIVE_QUERIES :
				getNamedNativeQueries().clear();
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
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_FOR_XML :
				return getPersistenceUnitMetadataForXml() != null;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE_FOR_XML :
				return PACKAGE_FOR_XML_EDEFAULT == null ? getPackageForXml() != null : !PACKAGE_FOR_XML_EDEFAULT.equals(getPackageForXml());
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA :
				return getPersistenceUnitMetadata() != null;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE :
				return PACKAGE_EDEFAULT == null ? getPackage() != null : !PACKAGE_EDEFAULT.equals(getPackage());
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__ROOT :
				return root != null;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__VERSION :
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__DESCRIPTION :
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_INTERNAL :
				return persistenceUnitMetadataInternal != null;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE_INTERNAL :
				return PACKAGE_INTERNAL_EDEFAULT == null ? packageInternal != null : !PACKAGE_INTERNAL_EDEFAULT.equals(packageInternal);
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_SCHEMA :
				return DEFAULT_SCHEMA_EDEFAULT == null ? defaultSchema != null : !DEFAULT_SCHEMA_EDEFAULT.equals(defaultSchema);
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_SCHEMA :
				return SPECIFIED_SCHEMA_EDEFAULT == null ? specifiedSchema != null : !SPECIFIED_SCHEMA_EDEFAULT.equals(specifiedSchema);
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SCHEMA :
				return SCHEMA_EDEFAULT == null ? getSchema() != null : !SCHEMA_EDEFAULT.equals(getSchema());
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_CATALOG :
				return DEFAULT_CATALOG_EDEFAULT == null ? defaultCatalog != null : !DEFAULT_CATALOG_EDEFAULT.equals(defaultCatalog);
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_CATALOG :
				return SPECIFIED_CATALOG_EDEFAULT == null ? specifiedCatalog != null : !SPECIFIED_CATALOG_EDEFAULT.equals(specifiedCatalog);
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__CATALOG :
				return CATALOG_EDEFAULT == null ? getCatalog() != null : !CATALOG_EDEFAULT.equals(getCatalog());
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_ACCESS :
				return defaultAccess != DEFAULT_ACCESS_EDEFAULT;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_ACCESS :
				return specifiedAccess != SPECIFIED_ACCESS_EDEFAULT;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__ACCESS :
				return getAccess() != ACCESS_EDEFAULT;
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__TYPE_MAPPINGS :
				return typeMappings != null && !typeMappings.isEmpty();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENT_TYPES :
				return persistentTypes != null && !persistentTypes.isEmpty();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__SEQUENCE_GENERATORS :
				return sequenceGenerators != null && !sequenceGenerators.isEmpty();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__TABLE_GENERATORS :
				return tableGenerators != null && !tableGenerators.isEmpty();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__NAMED_QUERIES :
				return namedQueries != null && !namedQueries.isEmpty();
			case OrmPackage.ENTITY_MAPPINGS_INTERNAL__NAMED_NATIVE_QUERIES :
				return namedNativeQueries != null && !namedNativeQueries.isEmpty();
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
		if (baseClass == IJpaContentNode.class) {
			switch (derivedFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == EntityMappingsForXml.class) {
			switch (derivedFeatureID) {
				case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_FOR_XML :
					return OrmPackage.ENTITY_MAPPINGS_FOR_XML__PERSISTENCE_UNIT_METADATA_FOR_XML;
				case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE_FOR_XML :
					return OrmPackage.ENTITY_MAPPINGS_FOR_XML__PACKAGE_FOR_XML;
				default :
					return -1;
			}
		}
		if (baseClass == EntityMappings.class) {
			switch (derivedFeatureID) {
				case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA :
					return OrmPackage.ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA;
				case OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE :
					return OrmPackage.ENTITY_MAPPINGS__PACKAGE;
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
		if (baseClass == IJpaContentNode.class) {
			switch (baseFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == EntityMappingsForXml.class) {
			switch (baseFeatureID) {
				case OrmPackage.ENTITY_MAPPINGS_FOR_XML__PERSISTENCE_UNIT_METADATA_FOR_XML :
					return OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA_FOR_XML;
				case OrmPackage.ENTITY_MAPPINGS_FOR_XML__PACKAGE_FOR_XML :
					return OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE_FOR_XML;
				default :
					return -1;
			}
		}
		if (baseClass == EntityMappings.class) {
			switch (baseFeatureID) {
				case OrmPackage.ENTITY_MAPPINGS__PERSISTENCE_UNIT_METADATA :
					return OrmPackage.ENTITY_MAPPINGS_INTERNAL__PERSISTENCE_UNIT_METADATA;
				case OrmPackage.ENTITY_MAPPINGS__PACKAGE :
					return OrmPackage.ENTITY_MAPPINGS_INTERNAL__PACKAGE;
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
		result.append(" (version: ");
		result.append(version);
		result.append(", description: ");
		result.append(description);
		result.append(", packageInternal: ");
		result.append(packageInternal);
		result.append(", defaultSchema: ");
		result.append(defaultSchema);
		result.append(", specifiedSchema: ");
		result.append(specifiedSchema);
		result.append(", defaultCatalog: ");
		result.append(defaultCatalog);
		result.append(", specifiedCatalog: ");
		result.append(specifiedCatalog);
		result.append(", defaultAccess: ");
		result.append(defaultAccess);
		result.append(", specifiedAccess: ");
		result.append(specifiedAccess);
		result.append(')');
		return result.toString();
	}

	public void addMapping(String className, String mappingKey) {
		XmlPersistentType persistentType = OrmFactory.eINSTANCE.createXmlPersistentType();
		XmlTypeMapping typeMapping = buildXmlTypeMapping(persistentType.typeMappingProviders(), mappingKey);
		if (className.startsWith(getPackage() + ".")) {
			// adds short name if package name is specified
			className = className.substring(getPackage().length() + 1);
		}
		typeMapping.getPersistentType().setClass(className);
		insertTypeMapping(typeMapping);
	}

	public void changeMapping(XmlTypeMapping oldMapping, String newMappingKey) {
		XmlTypeMapping newTypeMapping = buildXmlTypeMapping(oldMapping.getPersistentType().typeMappingProviders(), newMappingKey);
		getTypeMappings().remove(oldMapping);
		newTypeMapping.initializeFrom(oldMapping);
		insertTypeMapping(newTypeMapping);
	}

	private XmlTypeMapping buildXmlTypeMapping(Collection<IXmlTypeMappingProvider> providers, String key) {
		for (IXmlTypeMappingProvider provider : providers) {
			if (provider.key().equals(key)) {
				return provider.buildTypeMapping();
			}
		}
		//TODO throw an exception? what about the NullJavaTypeMapping?
		return null;
	}

	private void insertTypeMapping(XmlTypeMapping newMapping) {
		int newIndex = CollectionTools.insertionIndexOf(getTypeMappings(), newMapping, buildMappingComparator());
		getTypeMappings().add(newIndex, newMapping);
	}

	private Comparator<XmlTypeMapping> buildMappingComparator() {
		return new Comparator<XmlTypeMapping>() {
			public int compare(XmlTypeMapping o1, XmlTypeMapping o2) {
				int o1Sequence = o1.xmlSequence();
				int o2Sequence = o2.xmlSequence();
				if (o1Sequence < o2Sequence) {
					return -1;
				}
				if (o1Sequence == o2Sequence) {
					return 0;
				}
				return 1;
			}
		};
	}

	/**
	 * Override this because EntityMappingInternal does not have an eContainer()
	 * This is because entityMappings is the "root" feature of the doc for xml Translators
	 * and thus cannot be "contained"
	 */
	@Override
	public IJpaProject getJpaProject() {
		IJpaFile file = getJpaFile();
		return (file == null) ? null : file.getJpaProject();
	}

	/* @see IJpaContentNode#getId() */
	public Object getId() {
		return IXmlContentNodes.ENTITY_MAPPINGS_ID;
	}

	public IJpaContentNode getContentNode(int offset) {
		for (Iterator i = getTypeMappings().iterator(); i.hasNext();) {
			XmlTypeMapping mapping = (XmlTypeMapping) i.next();
			if (mapping.getNode().contains(offset)) {
				return mapping.getContentNode(offset);
			}
		}
		return this;
	}

	public void handleJavaElementChangedEvent(ElementChangedEvent event) {
		for (Iterator i = getTypeMappings().iterator(); i.hasNext();) {
			XmlTypeMapping mapping = (XmlTypeMapping) i.next();
			//mapping.javaElementChanged(event);
		}
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {
		setDefaultCatalog((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_TABLE_CATALOG_KEY));
		setDefaultSchema((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_TABLE_SCHEMA_KEY));
		setDefaultAccess((AccessType) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_ACCESS_KEY));
	}
	private class TypeMappingsList<E>
		extends EObjectContainmentEList<XmlTypeMapping>
	{
		private TypeMappingsList() {
			super(XmlTypeMapping.class, EntityMappingsInternal.this, OrmPackage.ENTITY_MAPPINGS_INTERNAL__TYPE_MAPPINGS);
		}

		@Override
		protected void didAdd(int index, XmlTypeMapping newObject) {
			getPersistentTypes().add(index, newObject.getPersistentType());
		}

		@Override
		protected void didChange() {
			// TODO Auto-generated method stub
			super.didChange();
		}

		@Override
		protected void didClear(int size, Object[] oldObjects) {
			getPersistentTypes().clear();
		}

		@Override
		protected void didMove(int index, XmlTypeMapping movedObject, int oldIndex) {
			getPersistentTypes().move(index, movedObject.getPersistentType());
		}

		@Override
		protected void didRemove(int index, XmlTypeMapping oldObject) {
			getPersistentTypes().remove(oldObject.getPersistentType());
		}

		@Override
		protected void didSet(int index, XmlTypeMapping newObject, XmlTypeMapping oldObject) {
			getPersistentTypes().set(index, newObject.getPersistentType());
		}
	}
} // EntityMappingsInternal
