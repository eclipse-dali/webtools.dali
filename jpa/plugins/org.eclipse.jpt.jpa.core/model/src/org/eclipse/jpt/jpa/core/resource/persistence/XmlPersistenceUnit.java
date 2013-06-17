/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.core.resource.persistence;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.common.core.internal.utility.translators.BooleanTranslator;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.resource.persistence.v2_0.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.persistence.v2_0.PersistenceV2_0Package;
import org.eclipse.jpt.jpa.core.resource.persistence.v2_0.XmlPersistenceUnitCachingType_2_0;
import org.eclipse.jpt.jpa.core.resource.persistence.v2_0.XmlPersistenceUnitValidationModeType_2_0;
import org.eclipse.jpt.jpa.core.resource.persistence.v2_0.XmlPersistenceUnit_2_0;
import org.eclipse.wst.common.internal.emf.resource.Translator;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unit</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getProvider <em>Provider</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getJtaDataSource <em>Jta Data Source</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getNonJtaDataSource <em>Non Jta Data Source</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getMappingFiles <em>Mapping Files</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getJarFiles <em>Jar Files</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getClasses <em>Classes</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getExcludeUnlistedClasses <em>Exclude Unlisted Classes</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getTransactionType <em>Transaction Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage#getXmlPersistenceUnit()
 * @model kind="class"
 * @generated
 */
public class XmlPersistenceUnit extends EBaseObjectImpl implements XmlPersistenceUnit_2_0
{
	/**
	 * Changed this to <code>null</code> and removed the 'generated' flag so
	 * EMF won't overwrite it. Enums do not need a default - they are simply
	 * <code>null</code> if there is no tag.
	 */
	protected static final XmlPersistenceUnitCachingType_2_0 SHARED_CACHE_MODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSharedCacheMode() <em>Shared Cache Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSharedCacheMode()
	 * @generated
	 * @ordered
	 */
	protected XmlPersistenceUnitCachingType_2_0 sharedCacheMode = SHARED_CACHE_MODE_EDEFAULT;

	/**
	 * Changed this to <code>null</code> and removed the 'generated' flag so
	 * EMF won't overwrite it. Enums do not need a default - they are simply
	 * <code>null</code> if there is no tag.
	 */
	protected static final XmlPersistenceUnitValidationModeType_2_0 VALIDATION_MODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValidationMode() <em>Validation Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidationMode()
	 * @generated
	 * @ordered
	 */
	protected XmlPersistenceUnitValidationModeType_2_0 validationMode = VALIDATION_MODE_EDEFAULT;

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
	 * The default value of the '{@link #getProvider() <em>Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProvider()
	 * @generated
	 * @ordered
	 */
	protected static final String PROVIDER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProvider() <em>Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProvider()
	 * @generated
	 * @ordered
	 */
	protected String provider = PROVIDER_EDEFAULT;

	/**
	 * The default value of the '{@link #getJtaDataSource() <em>Jta Data Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJtaDataSource()
	 * @generated
	 * @ordered
	 */
	protected static final String JTA_DATA_SOURCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJtaDataSource() <em>Jta Data Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJtaDataSource()
	 * @generated
	 * @ordered
	 */
	protected String jtaDataSource = JTA_DATA_SOURCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getNonJtaDataSource() <em>Non Jta Data Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNonJtaDataSource()
	 * @generated
	 * @ordered
	 */
	protected static final String NON_JTA_DATA_SOURCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNonJtaDataSource() <em>Non Jta Data Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNonJtaDataSource()
	 * @generated
	 * @ordered
	 */
	protected String nonJtaDataSource = NON_JTA_DATA_SOURCE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMappingFiles() <em>Mapping Files</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappingFiles()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlMappingFileRef> mappingFiles;

	/**
	 * The cached value of the '{@link #getJarFiles() <em>Jar Files</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJarFiles()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlJarFileRef> jarFiles;

	/**
	 * The cached value of the '{@link #getClasses() <em>Classes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClasses()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlJavaClassRef> classes;

	/**
	 * The default value of the '{@link #getExcludeUnlistedClasses() <em>Exclude Unlisted Classes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExcludeUnlistedClasses()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean EXCLUDE_UNLISTED_CLASSES_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExcludeUnlistedClasses() <em>Exclude Unlisted Classes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExcludeUnlistedClasses()
	 * @generated
	 * @ordered
	 */
	protected Boolean excludeUnlistedClasses = EXCLUDE_UNLISTED_CLASSES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected XmlProperties properties;

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
	 * Changed this to <code>null</code> and removed the 'generated' flag so
	 * EMF won't overwrite it. Enums do not need a default - they are simply
	 * <code>null</code> if there is no tag.
	 */
	protected static final XmlPersistenceUnitTransactionType TRANSACTION_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTransactionType() <em>Transaction Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransactionType()
	 * @generated
	 * @ordered
	 */
	protected XmlPersistenceUnitTransactionType transactionType = TRANSACTION_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlPersistenceUnit()
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
		return PersistencePackage.Literals.XML_PERSISTENCE_UNIT;
	}

	/**
	 * Returns the value of the '<em><b>Shared Cache Mode</b></em>' attribute.
	 * The default value is <code>"UNSPECIFIED"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.core.resource.persistence.v2_0.XmlPersistenceUnitCachingType_2_0}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shared Cache Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shared Cache Mode</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.v2_0.XmlPersistenceUnitCachingType_2_0
	 * @see #setSharedCacheMode(XmlPersistenceUnitCachingType_2_0)
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage#getXmlPersistenceUnit_2_0_SharedCacheMode()
	 * @model default="UNSPECIFIED" unique="false"
	 * @generated
	 */
	public XmlPersistenceUnitCachingType_2_0 getSharedCacheMode()
	{
		return sharedCacheMode;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getSharedCacheMode <em>Shared Cache Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shared Cache Mode</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.v2_0.XmlPersistenceUnitCachingType_2_0
	 * @see #getSharedCacheMode()
	 * @generated
	 */
	public void setSharedCacheMode(XmlPersistenceUnitCachingType_2_0 newSharedCacheMode)
	{
		XmlPersistenceUnitCachingType_2_0 oldSharedCacheMode = sharedCacheMode;
		sharedCacheMode = newSharedCacheMode == null ? SHARED_CACHE_MODE_EDEFAULT : newSharedCacheMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.XML_PERSISTENCE_UNIT__SHARED_CACHE_MODE, oldSharedCacheMode, sharedCacheMode));
	}

	/**
	 * Returns the value of the '<em><b>Validation Mode</b></em>' attribute.
	 * The default value is <code>"AUTO"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.core.resource.persistence.v2_0.XmlPersistenceUnitValidationModeType_2_0}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validation Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validation Mode</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.v2_0.XmlPersistenceUnitValidationModeType_2_0
	 * @see #setValidationMode(XmlPersistenceUnitValidationModeType_2_0)
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage#getXmlPersistenceUnit_2_0_ValidationMode()
	 * @model default="AUTO" unique="false"
	 * @generated
	 */
	public XmlPersistenceUnitValidationModeType_2_0 getValidationMode()
	{
		return validationMode;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getValidationMode <em>Validation Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validation Mode</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.v2_0.XmlPersistenceUnitValidationModeType_2_0
	 * @see #getValidationMode()
	 * @generated
	 */
	public void setValidationMode(XmlPersistenceUnitValidationModeType_2_0 newValidationMode)
	{
		XmlPersistenceUnitValidationModeType_2_0 oldValidationMode = validationMode;
		validationMode = newValidationMode == null ? VALIDATION_MODE_EDEFAULT : newValidationMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.XML_PERSISTENCE_UNIT__VALIDATION_MODE, oldValidationMode, validationMode));
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
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage#getXmlPersistenceUnit_Name()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.XML_PERSISTENCE_UNIT__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Transaction Type</b></em>' attribute.
	 * The default value is <code>"JTA"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnitTransactionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transaction Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transaction Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnitTransactionType
	 * @see #setTransactionType(XmlPersistenceUnitTransactionType)
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage#getXmlPersistenceUnit_TransactionType()
	 * @model default="JTA" unique="false"
	 * @generated
	 */
	public XmlPersistenceUnitTransactionType getTransactionType()
	{
		return transactionType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getTransactionType <em>Transaction Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transaction Type</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnitTransactionType
	 * @see #getTransactionType()
	 * @generated
	 */
	public void setTransactionType(XmlPersistenceUnitTransactionType newTransactionType)
	{
		XmlPersistenceUnitTransactionType oldTransactionType = transactionType;
		transactionType = newTransactionType == null ? TRANSACTION_TYPE_EDEFAULT : newTransactionType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.XML_PERSISTENCE_UNIT__TRANSACTION_TYPE, oldTransactionType, transactionType));
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
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage#getXmlPersistenceUnit_Description()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getDescription <em>Description</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.XML_PERSISTENCE_UNIT__DESCRIPTION, oldDescription, description));
	}

	/**
	 * Returns the value of the '<em><b>Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider</em>' attribute.
	 * @see #setProvider(String)
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage#getXmlPersistenceUnit_Provider()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getProvider()
	{
		return provider;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getProvider <em>Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider</em>' attribute.
	 * @see #getProvider()
	 * @generated
	 */
	public void setProvider(String newProvider)
	{
		String oldProvider = provider;
		provider = newProvider;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.XML_PERSISTENCE_UNIT__PROVIDER, oldProvider, provider));
	}

	/**
	 * Returns the value of the '<em><b>Jta Data Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jta Data Source</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jta Data Source</em>' attribute.
	 * @see #setJtaDataSource(String)
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage#getXmlPersistenceUnit_JtaDataSource()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getJtaDataSource()
	{
		return jtaDataSource;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getJtaDataSource <em>Jta Data Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jta Data Source</em>' attribute.
	 * @see #getJtaDataSource()
	 * @generated
	 */
	public void setJtaDataSource(String newJtaDataSource)
	{
		String oldJtaDataSource = jtaDataSource;
		jtaDataSource = newJtaDataSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.XML_PERSISTENCE_UNIT__JTA_DATA_SOURCE, oldJtaDataSource, jtaDataSource));
	}

	/**
	 * Returns the value of the '<em><b>Non Jta Data Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Non Jta Data Source</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Jta Data Source</em>' attribute.
	 * @see #setNonJtaDataSource(String)
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage#getXmlPersistenceUnit_NonJtaDataSource()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getNonJtaDataSource()
	{
		return nonJtaDataSource;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getNonJtaDataSource <em>Non Jta Data Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Non Jta Data Source</em>' attribute.
	 * @see #getNonJtaDataSource()
	 * @generated
	 */
	public void setNonJtaDataSource(String newNonJtaDataSource)
	{
		String oldNonJtaDataSource = nonJtaDataSource;
		nonJtaDataSource = newNonJtaDataSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.XML_PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE, oldNonJtaDataSource, nonJtaDataSource));
	}

	/**
	 * Returns the value of the '<em><b>Mapping Files</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapping Files</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping Files</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage#getXmlPersistenceUnit_MappingFiles()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlMappingFileRef> getMappingFiles()
	{
		if (mappingFiles == null)
		{
			mappingFiles = new EObjectContainmentEList<XmlMappingFileRef>(XmlMappingFileRef.class, this, PersistencePackage.XML_PERSISTENCE_UNIT__MAPPING_FILES);
		}
		return mappingFiles;
	}

	/**
	 * Returns the value of the '<em><b>Jar Files</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.persistence.XmlJarFileRef}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jar Files</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jar Files</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage#getXmlPersistenceUnit_JarFiles()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlJarFileRef> getJarFiles()
	{
		if (jarFiles == null)
		{
			jarFiles = new EObjectContainmentEList<XmlJarFileRef>(XmlJarFileRef.class, this, PersistencePackage.XML_PERSISTENCE_UNIT__JAR_FILES);
		}
		return jarFiles;
	}

	/**
	 * Returns the value of the '<em><b>Classes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.persistence.XmlJavaClassRef}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Classes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classes</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage#getXmlPersistenceUnit_Classes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlJavaClassRef> getClasses()
	{
		if (classes == null)
		{
			classes = new EObjectContainmentEList<XmlJavaClassRef>(XmlJavaClassRef.class, this, PersistencePackage.XML_PERSISTENCE_UNIT__CLASSES);
		}
		return classes;
	}

	/**
	 * Returns the value of the '<em><b>Exclude Unlisted Classes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exclude Unlisted Classes</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exclude Unlisted Classes</em>' attribute.
	 * @see #setExcludeUnlistedClasses(Boolean)
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage#getXmlPersistenceUnit_ExcludeUnlistedClasses()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getExcludeUnlistedClasses()
	{
		return excludeUnlistedClasses;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getExcludeUnlistedClasses <em>Exclude Unlisted Classes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exclude Unlisted Classes</em>' attribute.
	 * @see #getExcludeUnlistedClasses()
	 * @generated
	 */
	public void setExcludeUnlistedClasses(Boolean newExcludeUnlistedClasses)
	{
		Boolean oldExcludeUnlistedClasses = excludeUnlistedClasses;
		excludeUnlistedClasses = newExcludeUnlistedClasses;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.XML_PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES, oldExcludeUnlistedClasses, excludeUnlistedClasses));
	}

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XmlProperties</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference.
	 * @see #setProperties(XmlProperties)
	 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage#getXmlPersistenceUnit_Properties()
	 * @model containment="true"
	 * @generated
	 */
	public XmlProperties getProperties()
	{
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperties(XmlProperties newProperties, NotificationChain msgs)
	{
		XmlProperties oldProperties = properties;
		properties = newProperties;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PersistencePackage.XML_PERSISTENCE_UNIT__PROPERTIES, oldProperties, newProperties);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit#getProperties <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties</em>' containment reference.
	 * @see #getProperties()
	 * @generated
	 */
	public void setProperties(XmlProperties newProperties)
	{
		if (newProperties != properties)
		{
			NotificationChain msgs = null;
			if (properties != null)
				msgs = ((InternalEObject)properties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PersistencePackage.XML_PERSISTENCE_UNIT__PROPERTIES, null, msgs);
			if (newProperties != null)
				msgs = ((InternalEObject)newProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PersistencePackage.XML_PERSISTENCE_UNIT__PROPERTIES, null, msgs);
			msgs = basicSetProperties(newProperties, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.XML_PERSISTENCE_UNIT__PROPERTIES, newProperties, newProperties));
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
			case PersistencePackage.XML_PERSISTENCE_UNIT__MAPPING_FILES:
				return ((InternalEList<?>)getMappingFiles()).basicRemove(otherEnd, msgs);
			case PersistencePackage.XML_PERSISTENCE_UNIT__JAR_FILES:
				return ((InternalEList<?>)getJarFiles()).basicRemove(otherEnd, msgs);
			case PersistencePackage.XML_PERSISTENCE_UNIT__CLASSES:
				return ((InternalEList<?>)getClasses()).basicRemove(otherEnd, msgs);
			case PersistencePackage.XML_PERSISTENCE_UNIT__PROPERTIES:
				return basicSetProperties(null, msgs);
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
			case PersistencePackage.XML_PERSISTENCE_UNIT__SHARED_CACHE_MODE:
				return getSharedCacheMode();
			case PersistencePackage.XML_PERSISTENCE_UNIT__VALIDATION_MODE:
				return getValidationMode();
			case PersistencePackage.XML_PERSISTENCE_UNIT__DESCRIPTION:
				return getDescription();
			case PersistencePackage.XML_PERSISTENCE_UNIT__PROVIDER:
				return getProvider();
			case PersistencePackage.XML_PERSISTENCE_UNIT__JTA_DATA_SOURCE:
				return getJtaDataSource();
			case PersistencePackage.XML_PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE:
				return getNonJtaDataSource();
			case PersistencePackage.XML_PERSISTENCE_UNIT__MAPPING_FILES:
				return getMappingFiles();
			case PersistencePackage.XML_PERSISTENCE_UNIT__JAR_FILES:
				return getJarFiles();
			case PersistencePackage.XML_PERSISTENCE_UNIT__CLASSES:
				return getClasses();
			case PersistencePackage.XML_PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES:
				return getExcludeUnlistedClasses();
			case PersistencePackage.XML_PERSISTENCE_UNIT__PROPERTIES:
				return getProperties();
			case PersistencePackage.XML_PERSISTENCE_UNIT__NAME:
				return getName();
			case PersistencePackage.XML_PERSISTENCE_UNIT__TRANSACTION_TYPE:
				return getTransactionType();
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
			case PersistencePackage.XML_PERSISTENCE_UNIT__SHARED_CACHE_MODE:
				setSharedCacheMode((XmlPersistenceUnitCachingType_2_0)newValue);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__VALIDATION_MODE:
				setValidationMode((XmlPersistenceUnitValidationModeType_2_0)newValue);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__PROVIDER:
				setProvider((String)newValue);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__JTA_DATA_SOURCE:
				setJtaDataSource((String)newValue);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE:
				setNonJtaDataSource((String)newValue);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__MAPPING_FILES:
				getMappingFiles().clear();
				getMappingFiles().addAll((Collection<? extends XmlMappingFileRef>)newValue);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__JAR_FILES:
				getJarFiles().clear();
				getJarFiles().addAll((Collection<? extends XmlJarFileRef>)newValue);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__CLASSES:
				getClasses().clear();
				getClasses().addAll((Collection<? extends XmlJavaClassRef>)newValue);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES:
				setExcludeUnlistedClasses((Boolean)newValue);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__PROPERTIES:
				setProperties((XmlProperties)newValue);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__NAME:
				setName((String)newValue);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__TRANSACTION_TYPE:
				setTransactionType((XmlPersistenceUnitTransactionType)newValue);
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
			case PersistencePackage.XML_PERSISTENCE_UNIT__SHARED_CACHE_MODE:
				setSharedCacheMode(SHARED_CACHE_MODE_EDEFAULT);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__VALIDATION_MODE:
				setValidationMode(VALIDATION_MODE_EDEFAULT);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__PROVIDER:
				setProvider(PROVIDER_EDEFAULT);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__JTA_DATA_SOURCE:
				setJtaDataSource(JTA_DATA_SOURCE_EDEFAULT);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE:
				setNonJtaDataSource(NON_JTA_DATA_SOURCE_EDEFAULT);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__MAPPING_FILES:
				getMappingFiles().clear();
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__JAR_FILES:
				getJarFiles().clear();
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__CLASSES:
				getClasses().clear();
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES:
				setExcludeUnlistedClasses(EXCLUDE_UNLISTED_CLASSES_EDEFAULT);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__PROPERTIES:
				setProperties((XmlProperties)null);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PersistencePackage.XML_PERSISTENCE_UNIT__TRANSACTION_TYPE:
				setTransactionType(TRANSACTION_TYPE_EDEFAULT);
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
			case PersistencePackage.XML_PERSISTENCE_UNIT__SHARED_CACHE_MODE:
				return sharedCacheMode != SHARED_CACHE_MODE_EDEFAULT;
			case PersistencePackage.XML_PERSISTENCE_UNIT__VALIDATION_MODE:
				return validationMode != VALIDATION_MODE_EDEFAULT;
			case PersistencePackage.XML_PERSISTENCE_UNIT__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case PersistencePackage.XML_PERSISTENCE_UNIT__PROVIDER:
				return PROVIDER_EDEFAULT == null ? provider != null : !PROVIDER_EDEFAULT.equals(provider);
			case PersistencePackage.XML_PERSISTENCE_UNIT__JTA_DATA_SOURCE:
				return JTA_DATA_SOURCE_EDEFAULT == null ? jtaDataSource != null : !JTA_DATA_SOURCE_EDEFAULT.equals(jtaDataSource);
			case PersistencePackage.XML_PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE:
				return NON_JTA_DATA_SOURCE_EDEFAULT == null ? nonJtaDataSource != null : !NON_JTA_DATA_SOURCE_EDEFAULT.equals(nonJtaDataSource);
			case PersistencePackage.XML_PERSISTENCE_UNIT__MAPPING_FILES:
				return mappingFiles != null && !mappingFiles.isEmpty();
			case PersistencePackage.XML_PERSISTENCE_UNIT__JAR_FILES:
				return jarFiles != null && !jarFiles.isEmpty();
			case PersistencePackage.XML_PERSISTENCE_UNIT__CLASSES:
				return classes != null && !classes.isEmpty();
			case PersistencePackage.XML_PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES:
				return EXCLUDE_UNLISTED_CLASSES_EDEFAULT == null ? excludeUnlistedClasses != null : !EXCLUDE_UNLISTED_CLASSES_EDEFAULT.equals(excludeUnlistedClasses);
			case PersistencePackage.XML_PERSISTENCE_UNIT__PROPERTIES:
				return properties != null;
			case PersistencePackage.XML_PERSISTENCE_UNIT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PersistencePackage.XML_PERSISTENCE_UNIT__TRANSACTION_TYPE:
				return transactionType != TRANSACTION_TYPE_EDEFAULT;
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
		result.append(" (sharedCacheMode: ");
		result.append(sharedCacheMode);
		result.append(", validationMode: ");
		result.append(validationMode);
		result.append(", description: ");
		result.append(description);
		result.append(", provider: ");
		result.append(provider);
		result.append(", jtaDataSource: ");
		result.append(jtaDataSource);
		result.append(", nonJtaDataSource: ");
		result.append(nonJtaDataSource);
		result.append(", excludeUnlistedClasses: ");
		result.append(excludeUnlistedClasses);
		result.append(", name: ");
		result.append(name);
		result.append(", transactionType: ");
		result.append(transactionType);
		result.append(')');
		return result.toString();
	}
	
	
	// ********** misc **********
	
	public int getLocationToInsertMappingFileRef() {
		if (getMappingFiles().size() > 0) {
			XmlMappingFileRef mappingFileRef = IterableTools.last(this.getMappingFiles());
			return mappingFileRef.getNodeEndOffset();
		}

		IDOMNode elementNode = null;
		if (getNonJtaDataSource() != null) {
			elementNode = getElementNode(JPA.NON_JTA_DATA_SOURCE);
		}
		else if (getJtaDataSource() != null) {
			elementNode = getElementNode(JPA.JTA_DATA_SOURCE);
		}
		else if (getProvider() != null) {
			elementNode = getElementNode(JPA.PROVIDER);			
		}
		else if (getDescription() != null) {
			elementNode = getElementNode(JPA.DESCRIPTION);			
		}
		if (elementNode != null) {
			return elementNode.getLastStructuredDocumentRegion().getEnd();
		}
		return this.node.getFirstStructuredDocumentRegion().getEnd();
	}

	/**
	 * Sort the persistence unit's class list.
	 */
	public void sortClasses() {
		ECollections.sort(this.getClasses(), XmlJavaClassRef.COMPARATOR);
	}


	// ********** translators **********
	
	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}
	
	private static Translator[] buildTranslatorChildren() {
		PersistencePackage pkg = PersistencePackage.eINSTANCE;
		return new Translator[] {
				buildNameTranslator(),
				buildTransactionTypeTranslator(),
				buildDescriptionTranslator(),
				buildProviderTranslator(),
				buildJtaDataSourceTranslator(),
				buildNonJtaDataSourceTranslator(),
				XmlMappingFileRef.buildTranslator(JPA.MAPPING_FILE, pkg.getXmlPersistenceUnit_MappingFiles()),
				XmlJarFileRef.buildTranslator(JPA.JAR_FILE, pkg.getXmlPersistenceUnit_JarFiles()),
				XmlJavaClassRef.buildTranslator(JPA.CLASS, pkg.getXmlPersistenceUnit_Classes()),
				buildExcludeUnlistedClassesTranslator(),
				buildSharedCacheModeTranslator(),
				buildValidationModeTranslator(),
				XmlProperties.buildTranslator(JPA.PROPERTIES, pkg.getXmlPersistenceUnit_Properties())};
	}
	
	protected static Translator buildNameTranslator() {
		return new Translator(
				JPA.PERSISTENCE_UNIT__NAME,
				PersistencePackage.eINSTANCE.getXmlPersistenceUnit_Name(),
				Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildTransactionTypeTranslator() {
		return new Translator(
				JPA.PERSISTENCE_UNIT__TRANSACTION_TYPE,
				PersistencePackage.eINSTANCE.getXmlPersistenceUnit_TransactionType(),
				Translator.DOM_ATTRIBUTE | Translator.UNSET_IF_NULL);
	}
	
	protected static Translator buildDescriptionTranslator() {
		return new Translator(
				JPA.DESCRIPTION,
				PersistencePackage.eINSTANCE.getXmlPersistenceUnit_Description());
	}
	
	protected static Translator buildProviderTranslator() {
		return new Translator(
				JPA.PROVIDER,
				PersistencePackage.eINSTANCE.getXmlPersistenceUnit_Provider());
	}
	
	protected static Translator buildJtaDataSourceTranslator() {
		return new Translator(
				JPA.JTA_DATA_SOURCE,
				PersistencePackage.eINSTANCE.getXmlPersistenceUnit_JtaDataSource());
	}
	
	protected static Translator buildNonJtaDataSourceTranslator() {
		return new Translator(
				JPA.NON_JTA_DATA_SOURCE,
				PersistencePackage.eINSTANCE.getXmlPersistenceUnit_NonJtaDataSource());
	}
	
	protected static Translator buildExcludeUnlistedClassesTranslator() {
		return new BooleanTranslator(
				JPA.EXCLUDE_UNLISTED_CLASSES,
				PersistencePackage.eINSTANCE.getXmlPersistenceUnit_ExcludeUnlistedClasses());
	}
	
	protected static Translator buildSharedCacheModeTranslator() {
		return new Translator(
				JPA2_0.PERSISTENCE_UNIT__SHARED_CACHE_MODE,
				PersistenceV2_0Package.eINSTANCE.getXmlPersistenceUnit_2_0_SharedCacheMode());
	}
	
	protected static Translator buildValidationModeTranslator() {
		return new Translator(
				JPA2_0.PERSISTENCE_UNIT__VALIDATION_MODE,
				PersistenceV2_0Package.eINSTANCE.getXmlPersistenceUnit_2_0_ValidationMode());
	}
}
