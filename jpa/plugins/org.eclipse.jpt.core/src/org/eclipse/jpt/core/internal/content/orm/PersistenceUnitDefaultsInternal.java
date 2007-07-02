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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.AccessType;
import org.eclipse.jpt.core.internal.XmlEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Persistence Unit Defaults Internal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getSchemaInternal <em>Schema Internal</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getCatalogInternal <em>Catalog Internal</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getAccessInternal <em>Access Internal</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#isCascadePersistInternal <em>Cascade Persist Internal</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsInternal()
 * @model kind="class"
 * @generated
 */
public class PersistenceUnitDefaultsInternal extends XmlEObject
	implements PersistenceUnitDefaults, PersistenceUnitDefaultsForXml
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
	 * The default value of the '{@link #getCatalog() <em>Catalog</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCatalog()
	 * @generated
	 * @ordered
	 */
	protected static final String CATALOG_EDEFAULT = null;

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
	 * The default value of the '{@link #isCascadePersist() <em>Cascade Persist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadePersist()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASCADE_PERSIST_EDEFAULT = false;

	/**
	 * The default value of the '{@link #getSchemaForXml() <em>Schema For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchemaForXml()
	 * @generated
	 * @ordered
	 */
	protected static final String SCHEMA_FOR_XML_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getCatalogForXml() <em>Catalog For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCatalogForXml()
	 * @generated
	 * @ordered
	 */
	protected static final String CATALOG_FOR_XML_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getAccessForXml() <em>Access For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccessForXml()
	 * @generated
	 * @ordered
	 */
	protected static final AccessType ACCESS_FOR_XML_EDEFAULT = AccessType.DEFAULT;

	/**
	 * The default value of the '{@link #isCascadePersistForXml() <em>Cascade Persist For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadePersistForXml()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASCADE_PERSIST_FOR_XML_EDEFAULT = false;

	/**
	 * The default value of the '{@link #getSchemaInternal() <em>Schema Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchemaInternal()
	 * @generated
	 * @ordered
	 */
	protected static final String SCHEMA_INTERNAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSchemaInternal() <em>Schema Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchemaInternal()
	 * @generated
	 * @ordered
	 */
	protected String schemaInternal = SCHEMA_INTERNAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getCatalogInternal() <em>Catalog Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCatalogInternal()
	 * @generated
	 * @ordered
	 */
	protected static final String CATALOG_INTERNAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCatalogInternal() <em>Catalog Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCatalogInternal()
	 * @generated
	 * @ordered
	 */
	protected String catalogInternal = CATALOG_INTERNAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getAccessInternal() <em>Access Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccessInternal()
	 * @generated
	 * @ordered
	 */
	protected static final AccessType ACCESS_INTERNAL_EDEFAULT = AccessType.DEFAULT;

	/**
	 * The cached value of the '{@link #getAccessInternal() <em>Access Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccessInternal()
	 * @generated
	 * @ordered
	 */
	protected AccessType accessInternal = ACCESS_INTERNAL_EDEFAULT;

	/**
	 * The default value of the '{@link #isCascadePersistInternal() <em>Cascade Persist Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadePersistInternal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CASCADE_PERSIST_INTERNAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCascadePersistInternal() <em>Cascade Persist Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCascadePersistInternal()
	 * @generated
	 * @ordered
	 */
	protected boolean cascadePersistInternal = CASCADE_PERSIST_INTERNAL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PersistenceUnitDefaultsInternal() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.PERSISTENCE_UNIT_DEFAULTS_INTERNAL;
	}

	/**
	 * Returns the value of the '<em><b>Access</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.content.orm.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.AccessType
	 * @see #setAccess(AccessType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAccessHolder_Access()
	 * @model default="" volatile="true"
	 * @generated NOT
	 */
	public AccessType getAccess() {
		return getAccessInternal();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getAccess <em>Access</em>}' attribute.
	 * This api should be used by the UI.  It calls the appropriate
	 * internal api for updating the xml.  It also handles setting container
	 * objects to null for the xml.  If access is set to the default, empty xml containment
	 * tags will be removed when they no longer contain any other xml tags. 
	 * This is done in the UI method because we do not want the same behavior
	 * when setting the access from the xml, we never want to change the xml
	 * as the user is directly edting the xml.
	 *
	 * @param value the new value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.AccessType
	 * @see #getAccess()
	 * @generated NOT
	 */
	public void setAccess(AccessType newAccess) {
		setAccessInternal(newAccess);
		if (newAccess != ACCESS_EDEFAULT) {
			getPersistenceUnitMetadata().makePersistenceUnitDefaultsForXmlNonNull();
		}
		setAccessForXml(newAccess);
		if (isAllFeaturesUnset()) {
			getPersistenceUnitMetadata().makePersistenceUnitDefaultsForXmlNull();
		}
	}

	private PersistenceUnitMetadataInternal getPersistenceUnitMetadata() {
		return (PersistenceUnitMetadataInternal) eContainer();
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getCatalogHolder_Catalog()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public String getCatalog() {
		return getCatalogInternal();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getCatalog <em>Catalog</em>}' attribute.
	 * This api should be used by the UI.  It calls the appropriate
	 * internal api for updating the xml.  It also handles setting container
	 * objects to null for the xml.  If access is set to the default, empty xml containment
	 * tags will be removed when they no longer contain any other xml tags. 
	 * This is done in the UI method because we do not want the same behavior
	 * when setting the access from the xml, we never want to change the xml
	 * as the user is directly edting the xml.
	 * @param value the new value of the '<em>Catalog</em>' attribute.
	 * @see #getCatalog()
	 * @generated NOT
	 */
	public void setCatalog(String newCatalog) {
		setCatalogInternal(newCatalog);
		if (newCatalog != CATALOG_EDEFAULT) {
			getPersistenceUnitMetadata().makePersistenceUnitDefaultsForXmlNonNull();
		}
		setCatalogForXml(newCatalog);
		if (isAllFeaturesUnset()) {
			getPersistenceUnitMetadata().makePersistenceUnitDefaultsForXmlNull();
		}
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getSchemaHolder_Schema()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public String getSchema() {
		return getSchemaInternal();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getSchema <em>Schema</em>}' attribute.
	 * This api should be used by the UI.  It calls the appropriate
	 * internal api for updating the xml.  It also handles setting container
	 * objects to null for the xml.  If access is set to the default, empty xml containment
	 * tags will be removed when they no longer contain any other xml tags. 
	 * This is done in the UI method because we do not want the same behavior
	 * when setting the access from the xml, we never want to change the xml
	 * as the user is directly edting the xml.
	 * @param value the new value of the '<em>Schema</em>' attribute.
	 * @see #getSchema()
	 * @generated NOT
	 */
	public void setSchema(String newSchema) {
		setSchemaInternal(newSchema);
		if (newSchema != SCHEMA_EDEFAULT) {
			getPersistenceUnitMetadata().makePersistenceUnitDefaultsForXmlNonNull();
		}
		setSchemaForXml(newSchema);
		if (isAllFeaturesUnset()) {
			getPersistenceUnitMetadata().makePersistenceUnitDefaultsForXmlNull();
		}
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaults_CascadePersist()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public boolean isCascadePersist() {
		return isCascadePersistInternal();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#isCascadePersist <em>Cascade Persist</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Persist</em>' attribute.
	 * @see #isCascadePersist()
	 * @generated NOT
	 */
	public void setCascadePersist(boolean newCascadePersist) {
		setCascadePersistInternal(newCascadePersist);
		if (newCascadePersist != CASCADE_PERSIST_EDEFAULT) {
			getPersistenceUnitMetadata().makePersistenceUnitDefaultsForXmlNonNull();
		}
		setCascadePersistForXml(newCascadePersist);
		if (isAllFeaturesUnset()) {
			getPersistenceUnitMetadata().makePersistenceUnitDefaultsForXmlNull();
		}
	}

	/**
	 * Returns the value of the '<em><b>Schema For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schema For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schema For Xml</em>' attribute.
	 * @see #setSchemaForXml(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsForXml_SchemaForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public String getSchemaForXml() {
		return getSchemaInternal();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getSchemaForXml <em>Schema For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schema For Xml</em>' attribute.
	 * @see #getSchemaForXml()
	 * @generated NOT
	 */
	public void setSchemaForXml(String newSchemaForXml) {
		setSchemaInternal(newSchemaForXml);
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_FOR_XML, newSchemaForXml + " ", newSchemaForXml));
	}

	/**
	 * Returns the value of the '<em><b>Catalog For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Catalog For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Catalog For Xml</em>' attribute.
	 * @see #setCatalogForXml(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsForXml_CatalogForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public String getCatalogForXml() {
		return getCatalogInternal();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getCatalogForXml <em>Catalog For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Catalog For Xml</em>' attribute.
	 * @see #getCatalogForXml()
	 * @generated NOT
	 */
	public void setCatalogForXml(String newCatalogForXml) {
		setCatalogInternal(newCatalogForXml);
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_FOR_XML, newCatalogForXml + " ", newCatalogForXml));
	}

	/**
	 * Returns the value of the '<em><b>Access For Xml</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.content.orm.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access For Xml</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.AccessType
	 * @see #setAccessForXml(AccessType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsForXml_AccessForXml()
	 * @model default="" volatile="true"
	 * @generated NOT
	 */
	public AccessType getAccessForXml() {
		return getAccessInternal();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getAccessForXml <em>Access For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access For Xml</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.AccessType
	 * @see #getAccessForXml()
	 * @generated NOT
	 */
	public void setAccessForXml(AccessType newAccessForXml) {
		setAccessInternal(newAccessForXml);
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_FOR_XML, null, newAccessForXml));
	}

	/**
	 * Returns the value of the '<em><b>Cascade Persist For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Persist For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Persist For Xml</em>' attribute.
	 * @see #setCascadePersistForXml(boolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsForXml_CascadePersistForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public boolean isCascadePersistForXml() {
		return isCascadePersistInternal();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#isCascadePersistForXml <em>Cascade Persist For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Persist For Xml</em>' attribute.
	 * @see #isCascadePersistForXml()
	 * @generated NOT
	 */
	public void setCascadePersistForXml(boolean newCascadePersistForXml) {
		setCascadePersistInternal(newCascadePersistForXml);
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_FOR_XML, null, newCascadePersistForXml));
	}

	/**
	 * Returns the value of the '<em><b>Schema Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schema Internal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schema Internal</em>' attribute.
	 * @see #setSchemaInternal(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsInternal_SchemaInternal()
	 * @model
	 * @generated
	 */
	public String getSchemaInternal() {
		return schemaInternal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getSchemaInternal <em>Schema Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schema Internal</em>' attribute.
	 * @see #getSchemaInternal()
	 * @generated NOT
	 */
	public void setSchemaInternal(String newSchemaInternal) {
		String oldSchemaInternal = schemaInternal;
		schemaInternal = newSchemaInternal;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_INTERNAL, oldSchemaInternal, schemaInternal));
			//notification so the UI is updated when the xml changes, can't call the UI api 
			//because it has other side effects
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA, oldSchemaInternal, schemaInternal));
		}
	}

	/**
	 * Returns the value of the '<em><b>Catalog Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Catalog Internal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Catalog Internal</em>' attribute.
	 * @see #setCatalogInternal(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsInternal_CatalogInternal()
	 * @model
	 * @generated
	 */
	public String getCatalogInternal() {
		return catalogInternal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getCatalogInternal <em>Catalog Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Catalog Internal</em>' attribute.
	 * @see #getCatalogInternal()
	 * @generated NOT
	 */
	public void setCatalogInternal(String newCatalogInternal) {
		String oldCatalogInternal = catalogInternal;
		catalogInternal = newCatalogInternal;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_INTERNAL, oldCatalogInternal, catalogInternal));
			//notification so the UI is updated when the xml changes, can't call the UI api 
			//because it has other side effects
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG, oldCatalogInternal, catalogInternal));
		}
	}

	/**
	 * Returns the value of the '<em><b>Access Internal</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access Internal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access Internal</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.AccessType
	 * @see #setAccessInternal(AccessType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsInternal_AccessInternal()
	 * @model default=""
	 * @generated
	 */
	public AccessType getAccessInternal() {
		return accessInternal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#getAccessInternal <em>Access Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access Internal</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.AccessType
	 * @see #getAccessInternal()
	 * @generated NOT
	 */
	public void setAccessInternal(AccessType newAccessInternal) {
		AccessType oldAccessInternal = accessInternal;
		accessInternal = newAccessInternal == null ? ACCESS_INTERNAL_EDEFAULT : newAccessInternal;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_INTERNAL, oldAccessInternal, accessInternal));
			//notification so the UI is updated when the xml changes, can't call the UI api 
			//because it has other side effects
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS, oldAccessInternal, accessInternal));
		}
	}

	/**
	 * Returns the value of the '<em><b>Cascade Persist Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Persist Internal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Persist Internal</em>' attribute.
	 * @see #setCascadePersistInternal(boolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitDefaultsInternal_CascadePersistInternal()
	 * @model
	 * @generated
	 */
	public boolean isCascadePersistInternal() {
		return cascadePersistInternal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitDefaultsInternal#isCascadePersistInternal <em>Cascade Persist Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Persist Internal</em>' attribute.
	 * @see #isCascadePersistInternal()
	 * @generated NOT
	 */
	public void setCascadePersistInternal(boolean newCascadePersistInternal) {
		boolean oldCascadePersistInternal = cascadePersistInternal;
		cascadePersistInternal = newCascadePersistInternal;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_INTERNAL, oldCascadePersistInternal, cascadePersistInternal));
			//notification so the UI is updated when the xml changes, can't call the UI api 
			//because it has other side effects
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST, oldCascadePersistInternal, newCascadePersistInternal));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA :
				return getSchema();
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG :
				return getCatalog();
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS :
				return getAccess();
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST :
				return isCascadePersist() ? Boolean.TRUE : Boolean.FALSE;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_FOR_XML :
				return getSchemaForXml();
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_FOR_XML :
				return getCatalogForXml();
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_FOR_XML :
				return getAccessForXml();
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_FOR_XML :
				return isCascadePersistForXml() ? Boolean.TRUE : Boolean.FALSE;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_INTERNAL :
				return getSchemaInternal();
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_INTERNAL :
				return getCatalogInternal();
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_INTERNAL :
				return getAccessInternal();
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_INTERNAL :
				return isCascadePersistInternal() ? Boolean.TRUE : Boolean.FALSE;
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA :
				setSchema((String) newValue);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG :
				setCatalog((String) newValue);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS :
				setAccess((AccessType) newValue);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST :
				setCascadePersist(((Boolean) newValue).booleanValue());
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_FOR_XML :
				setSchemaForXml((String) newValue);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_FOR_XML :
				setCatalogForXml((String) newValue);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_FOR_XML :
				setAccessForXml((AccessType) newValue);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_FOR_XML :
				setCascadePersistForXml(((Boolean) newValue).booleanValue());
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_INTERNAL :
				setSchemaInternal((String) newValue);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_INTERNAL :
				setCatalogInternal((String) newValue);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_INTERNAL :
				setAccessInternal((AccessType) newValue);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_INTERNAL :
				setCascadePersistInternal(((Boolean) newValue).booleanValue());
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
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA :
				setSchema(SCHEMA_EDEFAULT);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG :
				setCatalog(CATALOG_EDEFAULT);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS :
				setAccess(ACCESS_EDEFAULT);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST :
				setCascadePersist(CASCADE_PERSIST_EDEFAULT);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_FOR_XML :
				setSchemaForXml(SCHEMA_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_FOR_XML :
				setCatalogForXml(CATALOG_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_FOR_XML :
				setAccessForXml(ACCESS_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_FOR_XML :
				setCascadePersistForXml(CASCADE_PERSIST_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_INTERNAL :
				setSchemaInternal(SCHEMA_INTERNAL_EDEFAULT);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_INTERNAL :
				setCatalogInternal(CATALOG_INTERNAL_EDEFAULT);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_INTERNAL :
				setAccessInternal(ACCESS_INTERNAL_EDEFAULT);
				return;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_INTERNAL :
				setCascadePersistInternal(CASCADE_PERSIST_INTERNAL_EDEFAULT);
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
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA :
				return SCHEMA_EDEFAULT == null ? getSchema() != null : !SCHEMA_EDEFAULT.equals(getSchema());
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG :
				return CATALOG_EDEFAULT == null ? getCatalog() != null : !CATALOG_EDEFAULT.equals(getCatalog());
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS :
				return getAccess() != ACCESS_EDEFAULT;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST :
				return isCascadePersist() != CASCADE_PERSIST_EDEFAULT;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_FOR_XML :
				return SCHEMA_FOR_XML_EDEFAULT == null ? getSchemaForXml() != null : !SCHEMA_FOR_XML_EDEFAULT.equals(getSchemaForXml());
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_FOR_XML :
				return CATALOG_FOR_XML_EDEFAULT == null ? getCatalogForXml() != null : !CATALOG_FOR_XML_EDEFAULT.equals(getCatalogForXml());
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_FOR_XML :
				return getAccessForXml() != ACCESS_FOR_XML_EDEFAULT;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_FOR_XML :
				return isCascadePersistForXml() != CASCADE_PERSIST_FOR_XML_EDEFAULT;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_INTERNAL :
				return SCHEMA_INTERNAL_EDEFAULT == null ? schemaInternal != null : !SCHEMA_INTERNAL_EDEFAULT.equals(schemaInternal);
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_INTERNAL :
				return CATALOG_INTERNAL_EDEFAULT == null ? catalogInternal != null : !CATALOG_INTERNAL_EDEFAULT.equals(catalogInternal);
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_INTERNAL :
				return accessInternal != ACCESS_INTERNAL_EDEFAULT;
			case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_INTERNAL :
				return cascadePersistInternal != CASCADE_PERSIST_INTERNAL_EDEFAULT;
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
		if (baseClass == PersistenceUnitDefaults.class) {
			switch (derivedFeatureID) {
				case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA :
					return OrmPackage.PERSISTENCE_UNIT_DEFAULTS__SCHEMA;
				case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG :
					return OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CATALOG;
				case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS :
					return OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ACCESS;
				case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST :
					return OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST;
				default :
					return -1;
			}
		}
		if (baseClass == PersistenceUnitDefaultsForXml.class) {
			switch (derivedFeatureID) {
				case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_FOR_XML :
					return OrmPackage.PERSISTENCE_UNIT_DEFAULTS_FOR_XML__SCHEMA_FOR_XML;
				case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_FOR_XML :
					return OrmPackage.PERSISTENCE_UNIT_DEFAULTS_FOR_XML__CATALOG_FOR_XML;
				case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_FOR_XML :
					return OrmPackage.PERSISTENCE_UNIT_DEFAULTS_FOR_XML__ACCESS_FOR_XML;
				case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_FOR_XML :
					return OrmPackage.PERSISTENCE_UNIT_DEFAULTS_FOR_XML__CASCADE_PERSIST_FOR_XML;
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
		if (baseClass == PersistenceUnitDefaults.class) {
			switch (baseFeatureID) {
				case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__SCHEMA :
					return OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA;
				case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CATALOG :
					return OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG;
				case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ACCESS :
					return OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS;
				case OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CASCADE_PERSIST :
					return OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST;
				default :
					return -1;
			}
		}
		if (baseClass == PersistenceUnitDefaultsForXml.class) {
			switch (baseFeatureID) {
				case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_FOR_XML__SCHEMA_FOR_XML :
					return OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_FOR_XML;
				case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_FOR_XML__CATALOG_FOR_XML :
					return OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_FOR_XML;
				case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_FOR_XML__ACCESS_FOR_XML :
					return OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_FOR_XML;
				case OrmPackage.PERSISTENCE_UNIT_DEFAULTS_FOR_XML__CASCADE_PERSIST_FOR_XML :
					return OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_FOR_XML;
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
		result.append(" (schemaInternal: ");
		result.append(schemaInternal);
		result.append(", catalogInternal: ");
		result.append(catalogInternal);
		result.append(", accessInternal: ");
		result.append(accessInternal);
		result.append(", cascadePersistInternal: ");
		result.append(cascadePersistInternal);
		result.append(')');
		return result.toString();
	}

	/**
	 * Call this when the persistence-unit-defaults tag is removed
	 * from the xml, need to make sure all the model attributes are set to the default
	 */
	protected void unsetAllAttributes() {
		eUnset(OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__ACCESS_INTERNAL);
		eUnset(OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CATALOG_INTERNAL);
		eUnset(OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__SCHEMA_INTERNAL);
		eUnset(OrmPackage.PERSISTENCE_UNIT_DEFAULTS_INTERNAL__CASCADE_PERSIST_INTERNAL);
	}
} // PersistenceUnitDefaultsInternal
