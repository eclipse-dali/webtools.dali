/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.persistence;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.content.persistence.resource.IPersistenceXmlContentNodes;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Persistence Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getProvider <em>Provider</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getJtaDataSource <em>Jta Data Source</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getNonJtaDataSource <em>Non Jta Data Source</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getMappingFiles <em>Mapping Files</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getJarFiles <em>Jar Files</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getClasses <em>Classes</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#isExcludeUnlistedClasses <em>Exclude Unlisted Classes</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getTransactionType <em>Transaction Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistenceUnit()
 * @model kind="class"
 * @generated
 */
public class PersistenceUnit extends XmlEObject implements IJpaContentNode
{
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
	protected EList<MappingFileRef> mappingFiles;

	/**
	 * The cached value of the '{@link #getJarFiles() <em>Jar Files</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJarFiles()
	 * @generated
	 * @ordered
	 */
	protected EList<String> jarFiles;

	/**
	 * The cached value of the '{@link #getClasses() <em>Classes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClasses()
	 * @generated
	 * @ordered
	 */
	protected EList<JavaClassRef> classes;

	/**
	 * The default value of the '{@link #isExcludeUnlistedClasses() <em>Exclude Unlisted Classes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExcludeUnlistedClasses()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXCLUDE_UNLISTED_CLASSES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExcludeUnlistedClasses() <em>Exclude Unlisted Classes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExcludeUnlistedClasses()
	 * @generated
	 * @ordered
	 */
	protected boolean excludeUnlistedClasses = EXCLUDE_UNLISTED_CLASSES_EDEFAULT;

	/**
	 * This is true if the Exclude Unlisted Classes attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean excludeUnlistedClassesESet;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected Properties properties;

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
	 * The default value of the '{@link #getTransactionType() <em>Transaction Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransactionType()
	 * @generated
	 * @ordered
	 */
	protected static final PersistenceUnitTransactionType TRANSACTION_TYPE_EDEFAULT = PersistenceUnitTransactionType.JTA;

	/**
	 * The cached value of the '{@link #getTransactionType() <em>Transaction Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransactionType()
	 * @generated
	 * @ordered
	 */
	protected PersistenceUnitTransactionType transactionType = TRANSACTION_TYPE_EDEFAULT;

	/**
	 * This is true if the Transaction Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean transactionTypeESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PersistenceUnit() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PersistencePackage.Literals.PERSISTENCE_UNIT;
	}

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistenceUnit_Description()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getDescription <em>Description</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.PERSISTENCE_UNIT__DESCRIPTION, oldDescription, description));
	}

	/**
	 * Returns the value of the '<em><b>Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider</em>' attribute.
	 * @see #setProvider(String)
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistenceUnit_Provider()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getProvider <em>Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider</em>' attribute.
	 * @see #getProvider()
	 * @generated
	 */
	public void setProvider(String newProvider) {
		String oldProvider = provider;
		provider = newProvider;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.PERSISTENCE_UNIT__PROVIDER, oldProvider, provider));
	}

	/**
	 * Returns the value of the '<em><b>Jta Data Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jta Data Source</em>' attribute.
	 * @see #setJtaDataSource(String)
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistenceUnit_JtaDataSource()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getJtaDataSource() {
		return jtaDataSource;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getJtaDataSource <em>Jta Data Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jta Data Source</em>' attribute.
	 * @see #getJtaDataSource()
	 * @generated
	 */
	public void setJtaDataSource(String newJtaDataSource) {
		String oldJtaDataSource = jtaDataSource;
		jtaDataSource = newJtaDataSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.PERSISTENCE_UNIT__JTA_DATA_SOURCE, oldJtaDataSource, jtaDataSource));
	}

	/**
	 * Returns the value of the '<em><b>Non Jta Data Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Jta Data Source</em>' attribute.
	 * @see #setNonJtaDataSource(String)
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistenceUnit_NonJtaDataSource()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getNonJtaDataSource() {
		return nonJtaDataSource;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getNonJtaDataSource <em>Non Jta Data Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Non Jta Data Source</em>' attribute.
	 * @see #getNonJtaDataSource()
	 * @generated
	 */
	public void setNonJtaDataSource(String newNonJtaDataSource) {
		String oldNonJtaDataSource = nonJtaDataSource;
		nonJtaDataSource = newNonJtaDataSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE, oldNonJtaDataSource, nonJtaDataSource));
	}

	/**
	 * Returns the value of the '<em><b>Mapping Files</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.persistence.MappingFileRef}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping Files</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistenceUnit_MappingFiles()
	 * @model containment="true"
	 * @generated
	 */
	public EList<MappingFileRef> getMappingFiles() {
		if (mappingFiles == null) {
			mappingFiles = new EObjectContainmentEList<MappingFileRef>(MappingFileRef.class, this, PersistencePackage.PERSISTENCE_UNIT__MAPPING_FILES);
		}
		return mappingFiles;
	}

	/**
	 * Returns the value of the '<em><b>Jar Files</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jar Files</em>' attribute list.
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistenceUnit_JarFiles()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public EList<String> getJarFiles() {
		if (jarFiles == null) {
			jarFiles = new EDataTypeEList<String>(String.class, this, PersistencePackage.PERSISTENCE_UNIT__JAR_FILES);
		}
		return jarFiles;
	}

	/**
	 * Returns the value of the '<em><b>Classes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.content.persistence.JavaClassRef}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classes</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistenceUnit_Classes()
	 * @model containment="true"
	 * @generated
	 */
	public EList<JavaClassRef> getClasses() {
		if (classes == null) {
			classes = new EObjectContainmentEList<JavaClassRef>(JavaClassRef.class, this, PersistencePackage.PERSISTENCE_UNIT__CLASSES);
		}
		return classes;
	}

	/**
	 * Returns the value of the '<em><b>Exclude Unlisted Classes</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exclude Unlisted Classes</em>' attribute.
	 * @see #isSetExcludeUnlistedClasses()
	 * @see #unsetExcludeUnlistedClasses()
	 * @see #setExcludeUnlistedClasses(boolean)
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistenceUnit_ExcludeUnlistedClasses()
	 * @model default="false" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isExcludeUnlistedClasses() {
		return excludeUnlistedClasses;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#isExcludeUnlistedClasses <em>Exclude Unlisted Classes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exclude Unlisted Classes</em>' attribute.
	 * @see #isSetExcludeUnlistedClasses()
	 * @see #unsetExcludeUnlistedClasses()
	 * @see #isExcludeUnlistedClasses()
	 * @generated
	 */
	public void setExcludeUnlistedClasses(boolean newExcludeUnlistedClasses) {
		boolean oldExcludeUnlistedClasses = excludeUnlistedClasses;
		excludeUnlistedClasses = newExcludeUnlistedClasses;
		boolean oldExcludeUnlistedClassesESet = excludeUnlistedClassesESet;
		excludeUnlistedClassesESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES, oldExcludeUnlistedClasses, excludeUnlistedClasses, !oldExcludeUnlistedClassesESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#isExcludeUnlistedClasses <em>Exclude Unlisted Classes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetExcludeUnlistedClasses()
	 * @see #isExcludeUnlistedClasses()
	 * @see #setExcludeUnlistedClasses(boolean)
	 * @generated
	 */
	public void unsetExcludeUnlistedClasses() {
		boolean oldExcludeUnlistedClasses = excludeUnlistedClasses;
		boolean oldExcludeUnlistedClassesESet = excludeUnlistedClassesESet;
		excludeUnlistedClasses = EXCLUDE_UNLISTED_CLASSES_EDEFAULT;
		excludeUnlistedClassesESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PersistencePackage.PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES, oldExcludeUnlistedClasses, EXCLUDE_UNLISTED_CLASSES_EDEFAULT, oldExcludeUnlistedClassesESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#isExcludeUnlistedClasses <em>Exclude Unlisted Classes</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Exclude Unlisted Classes</em>' attribute is set.
	 * @see #unsetExcludeUnlistedClasses()
	 * @see #isExcludeUnlistedClasses()
	 * @see #setExcludeUnlistedClasses(boolean)
	 * @generated
	 */
	public boolean isSetExcludeUnlistedClasses() {
		return excludeUnlistedClassesESet;
	}

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference.
	 * @see #setProperties(Properties)
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistenceUnit_Properties()
	 * @model containment="true"
	 * @generated
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperties(Properties newProperties, NotificationChain msgs) {
		Properties oldProperties = properties;
		properties = newProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PersistencePackage.PERSISTENCE_UNIT__PROPERTIES, oldProperties, newProperties);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getProperties <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties</em>' containment reference.
	 * @see #getProperties()
	 * @generated
	 */
	public void setProperties(Properties newProperties) {
		if (newProperties != properties) {
			NotificationChain msgs = null;
			if (properties != null)
				msgs = ((InternalEObject) properties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PersistencePackage.PERSISTENCE_UNIT__PROPERTIES, null, msgs);
			if (newProperties != null)
				msgs = ((InternalEObject) newProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PersistencePackage.PERSISTENCE_UNIT__PROPERTIES, null, msgs);
			msgs = basicSetProperties(newProperties, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.PERSISTENCE_UNIT__PROPERTIES, newProperties, newProperties));
	}

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistenceUnit_Name()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.PERSISTENCE_UNIT__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Transaction Type</b></em>' attribute.
	 * The default value is <code>"JTA"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnitTransactionType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transaction Type</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistenceUnitTransactionType
	 * @see #isSetTransactionType()
	 * @see #unsetTransactionType()
	 * @see #setTransactionType(PersistenceUnitTransactionType)
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistencePackage#getPersistenceUnit_TransactionType()
	 * @model default="JTA" unique="false" unsettable="true"
	 * @generated
	 */
	public PersistenceUnitTransactionType getTransactionType() {
		return transactionType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getTransactionType <em>Transaction Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transaction Type</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.persistence.PersistenceUnitTransactionType
	 * @see #isSetTransactionType()
	 * @see #unsetTransactionType()
	 * @see #getTransactionType()
	 * @generated
	 */
	public void setTransactionType(PersistenceUnitTransactionType newTransactionType) {
		PersistenceUnitTransactionType oldTransactionType = transactionType;
		transactionType = newTransactionType == null ? TRANSACTION_TYPE_EDEFAULT : newTransactionType;
		boolean oldTransactionTypeESet = transactionTypeESet;
		transactionTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PersistencePackage.PERSISTENCE_UNIT__TRANSACTION_TYPE, oldTransactionType, transactionType, !oldTransactionTypeESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getTransactionType <em>Transaction Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTransactionType()
	 * @see #getTransactionType()
	 * @see #setTransactionType(PersistenceUnitTransactionType)
	 * @generated
	 */
	public void unsetTransactionType() {
		PersistenceUnitTransactionType oldTransactionType = transactionType;
		boolean oldTransactionTypeESet = transactionTypeESet;
		transactionType = TRANSACTION_TYPE_EDEFAULT;
		transactionTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PersistencePackage.PERSISTENCE_UNIT__TRANSACTION_TYPE, oldTransactionType, TRANSACTION_TYPE_EDEFAULT, oldTransactionTypeESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit#getTransactionType <em>Transaction Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Transaction Type</em>' attribute is set.
	 * @see #unsetTransactionType()
	 * @see #getTransactionType()
	 * @see #setTransactionType(PersistenceUnitTransactionType)
	 * @generated
	 */
	public boolean isSetTransactionType() {
		return transactionTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PersistencePackage.PERSISTENCE_UNIT__MAPPING_FILES :
				return ((InternalEList<?>) getMappingFiles()).basicRemove(otherEnd, msgs);
			case PersistencePackage.PERSISTENCE_UNIT__CLASSES :
				return ((InternalEList<?>) getClasses()).basicRemove(otherEnd, msgs);
			case PersistencePackage.PERSISTENCE_UNIT__PROPERTIES :
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PersistencePackage.PERSISTENCE_UNIT__DESCRIPTION :
				return getDescription();
			case PersistencePackage.PERSISTENCE_UNIT__PROVIDER :
				return getProvider();
			case PersistencePackage.PERSISTENCE_UNIT__JTA_DATA_SOURCE :
				return getJtaDataSource();
			case PersistencePackage.PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE :
				return getNonJtaDataSource();
			case PersistencePackage.PERSISTENCE_UNIT__MAPPING_FILES :
				return getMappingFiles();
			case PersistencePackage.PERSISTENCE_UNIT__JAR_FILES :
				return getJarFiles();
			case PersistencePackage.PERSISTENCE_UNIT__CLASSES :
				return getClasses();
			case PersistencePackage.PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES :
				return isExcludeUnlistedClasses() ? Boolean.TRUE : Boolean.FALSE;
			case PersistencePackage.PERSISTENCE_UNIT__PROPERTIES :
				return getProperties();
			case PersistencePackage.PERSISTENCE_UNIT__NAME :
				return getName();
			case PersistencePackage.PERSISTENCE_UNIT__TRANSACTION_TYPE :
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PersistencePackage.PERSISTENCE_UNIT__DESCRIPTION :
				setDescription((String) newValue);
				return;
			case PersistencePackage.PERSISTENCE_UNIT__PROVIDER :
				setProvider((String) newValue);
				return;
			case PersistencePackage.PERSISTENCE_UNIT__JTA_DATA_SOURCE :
				setJtaDataSource((String) newValue);
				return;
			case PersistencePackage.PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE :
				setNonJtaDataSource((String) newValue);
				return;
			case PersistencePackage.PERSISTENCE_UNIT__MAPPING_FILES :
				getMappingFiles().clear();
				getMappingFiles().addAll((Collection<? extends MappingFileRef>) newValue);
				return;
			case PersistencePackage.PERSISTENCE_UNIT__JAR_FILES :
				getJarFiles().clear();
				getJarFiles().addAll((Collection<? extends String>) newValue);
				return;
			case PersistencePackage.PERSISTENCE_UNIT__CLASSES :
				getClasses().clear();
				getClasses().addAll((Collection<? extends JavaClassRef>) newValue);
				return;
			case PersistencePackage.PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES :
				setExcludeUnlistedClasses(((Boolean) newValue).booleanValue());
				return;
			case PersistencePackage.PERSISTENCE_UNIT__PROPERTIES :
				setProperties((Properties) newValue);
				return;
			case PersistencePackage.PERSISTENCE_UNIT__NAME :
				setName((String) newValue);
				return;
			case PersistencePackage.PERSISTENCE_UNIT__TRANSACTION_TYPE :
				setTransactionType((PersistenceUnitTransactionType) newValue);
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
			case PersistencePackage.PERSISTENCE_UNIT__DESCRIPTION :
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case PersistencePackage.PERSISTENCE_UNIT__PROVIDER :
				setProvider(PROVIDER_EDEFAULT);
				return;
			case PersistencePackage.PERSISTENCE_UNIT__JTA_DATA_SOURCE :
				setJtaDataSource(JTA_DATA_SOURCE_EDEFAULT);
				return;
			case PersistencePackage.PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE :
				setNonJtaDataSource(NON_JTA_DATA_SOURCE_EDEFAULT);
				return;
			case PersistencePackage.PERSISTENCE_UNIT__MAPPING_FILES :
				getMappingFiles().clear();
				return;
			case PersistencePackage.PERSISTENCE_UNIT__JAR_FILES :
				getJarFiles().clear();
				return;
			case PersistencePackage.PERSISTENCE_UNIT__CLASSES :
				getClasses().clear();
				return;
			case PersistencePackage.PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES :
				unsetExcludeUnlistedClasses();
				return;
			case PersistencePackage.PERSISTENCE_UNIT__PROPERTIES :
				setProperties((Properties) null);
				return;
			case PersistencePackage.PERSISTENCE_UNIT__NAME :
				setName(NAME_EDEFAULT);
				return;
			case PersistencePackage.PERSISTENCE_UNIT__TRANSACTION_TYPE :
				unsetTransactionType();
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
			case PersistencePackage.PERSISTENCE_UNIT__DESCRIPTION :
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case PersistencePackage.PERSISTENCE_UNIT__PROVIDER :
				return PROVIDER_EDEFAULT == null ? provider != null : !PROVIDER_EDEFAULT.equals(provider);
			case PersistencePackage.PERSISTENCE_UNIT__JTA_DATA_SOURCE :
				return JTA_DATA_SOURCE_EDEFAULT == null ? jtaDataSource != null : !JTA_DATA_SOURCE_EDEFAULT.equals(jtaDataSource);
			case PersistencePackage.PERSISTENCE_UNIT__NON_JTA_DATA_SOURCE :
				return NON_JTA_DATA_SOURCE_EDEFAULT == null ? nonJtaDataSource != null : !NON_JTA_DATA_SOURCE_EDEFAULT.equals(nonJtaDataSource);
			case PersistencePackage.PERSISTENCE_UNIT__MAPPING_FILES :
				return mappingFiles != null && !mappingFiles.isEmpty();
			case PersistencePackage.PERSISTENCE_UNIT__JAR_FILES :
				return jarFiles != null && !jarFiles.isEmpty();
			case PersistencePackage.PERSISTENCE_UNIT__CLASSES :
				return classes != null && !classes.isEmpty();
			case PersistencePackage.PERSISTENCE_UNIT__EXCLUDE_UNLISTED_CLASSES :
				return isSetExcludeUnlistedClasses();
			case PersistencePackage.PERSISTENCE_UNIT__PROPERTIES :
				return properties != null;
			case PersistencePackage.PERSISTENCE_UNIT__NAME :
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PersistencePackage.PERSISTENCE_UNIT__TRANSACTION_TYPE :
				return isSetTransactionType();
		}
		return super.eIsSet(featureID);
	}

	public Persistence getPersistence() {
		return (Persistence) eContainer();
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
		result.append(" (description: ");
		result.append(description);
		result.append(", provider: ");
		result.append(provider);
		result.append(", jtaDataSource: ");
		result.append(jtaDataSource);
		result.append(", nonJtaDataSource: ");
		result.append(nonJtaDataSource);
		result.append(", jarFiles: ");
		result.append(jarFiles);
		result.append(", excludeUnlistedClasses: ");
		if (excludeUnlistedClassesESet)
			result.append(excludeUnlistedClasses);
		else
			result.append("<unset>");
		result.append(", name: ");
		result.append(name);
		result.append(", transactionType: ");
		if (transactionTypeESet)
			result.append(transactionType);
		else
			result.append("<unset>");
		result.append(')');
		return result.toString();
	}

	public Object getId() {
		return IPersistenceXmlContentNodes.PERSISTENCE_UNIT_ID;
	}
}
