/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.AccessType;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.JpaCorePackage;
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.content.orm.resource.OrmXmlMapper;
import org.eclipse.jpt.core.internal.emfutility.DOMUtilities;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Xml Persistent Type Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getDefaultAccess <em>Default Access</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getSpecifiedAccess <em>Specified Access</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getAccess <em>Access</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getMetadataComplete <em>Metadata Complete</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getPersistentType <em>Persistent Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlTypeMapping()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class XmlTypeMapping extends XmlEObject implements ITypeMapping
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
	 * The default value of the '{@link #getTableName() <em>Table Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTableName()
	 * @generated
	 * @ordered
	 */
	protected static final String TABLE_NAME_EDEFAULT = null;

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
	 * The default value of the '{@link #getMetadataComplete() <em>Metadata Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetadataComplete()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultFalseBoolean METADATA_COMPLETE_EDEFAULT = DefaultFalseBoolean.DEFAULT;

	/**
	 * The cached value of the '{@link #getMetadataComplete() <em>Metadata Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetadataComplete()
	 * @generated
	 * @ordered
	 */
	protected DefaultFalseBoolean metadataComplete = METADATA_COMPLETE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPersistentType() <em>Persistent Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPersistentType()
	 * @generated
	 * @ordered
	 */
	protected XmlPersistentType persistentType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected XmlTypeMapping() {
		super();
		XmlPersistentType persistentType = createXmlPersistentType();
		setPersistentType(persistentType);
	}

	protected XmlPersistentType createXmlPersistentType() {
		return OrmFactory.eINSTANCE.createXmlPersistentType(getKey());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_TYPE_MAPPING;
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getITypeMapping_Name()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getName() {
		Type type = getPersistentType().findType();
		return (type == null) ? "" : type.getName();
	}

	/**
	 * Returns the value of the '<em><b>Table Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getITypeMapping_TableName()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getTableName() {
		return "";
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlTypeMapping_DefaultAccess()
	 * @model
	 * @generated
	 */
	public AccessType getDefaultAccess() {
		return defaultAccess;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getDefaultAccess <em>Default Access</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_TYPE_MAPPING__DEFAULT_ACCESS, oldDefaultAccess, defaultAccess));
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlTypeMapping_SpecifiedAccess()
	 * @model
	 * @generated
	 */
	public AccessType getSpecifiedAccess() {
		return specifiedAccess;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getSpecifiedAccess <em>Specified Access</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_TYPE_MAPPING__SPECIFIED_ACCESS, oldSpecifiedAccess, specifiedAccess));
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlTypeMapping_Access()
	 * @model transient="true" changeable="false" volatile="true"
	 * @generated NOT
	 */
	public AccessType getAccess() {
		return (this.getSpecifiedAccess() == null) ? this.getDefaultAccess() : this.getSpecifiedAccess();
	}

	public EntityMappings getEntityMappings() {
		return (EntityMappings) eContainer();
	}

	/**
	 * Returns the value of the '<em><b>Metadata Complete</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metadata Complete</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean
	 * @see #setMetadataComplete(DefaultFalseBoolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlTypeMapping_MetadataComplete()
	 * @model
	 * @generated
	 */
	public DefaultFalseBoolean getMetadataComplete() {
		return metadataComplete;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getMetadataComplete <em>Metadata Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metadata Complete</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean
	 * @see #getMetadataComplete()
	 * @generated
	 */
	public void setMetadataComplete(DefaultFalseBoolean newMetadataComplete) {
		DefaultFalseBoolean oldMetadataComplete = metadataComplete;
		metadataComplete = newMetadataComplete == null ? METADATA_COMPLETE_EDEFAULT : newMetadataComplete;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_TYPE_MAPPING__METADATA_COMPLETE, oldMetadataComplete, metadataComplete));
	}

	public boolean isXmlMetadataComplete() {
		return isPersistenceUnitXmlMetadataComplete() || (getMetadataComplete() == DefaultFalseBoolean.TRUE);
	}

	protected boolean isPersistenceUnitXmlMetadataComplete() {
		return ((XmlRootContentNode) getRoot()).entityMappings.getPersistenceUnitMetadata().isXmlMappingMetadataComplete();
	}

	/**
	 * Returns the value of the '<em><b>Persistent Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistent Type</em>' containment reference.
	 * @see #setPersistentType(XmlPersistentType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlTypeMapping_PersistentType()
	 * @model containment="true" required="true"
	 * @generated
	 */
	public XmlPersistentType getPersistentType() {
		return persistentType;
	}

	public IPersistentType javaPersistentType() {
		return getPersistentType().findJavaPersistentType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPersistentType(XmlPersistentType newPersistentType, NotificationChain msgs) {
		XmlPersistentType oldPersistentType = persistentType;
		persistentType = newPersistentType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_TYPE_MAPPING__PERSISTENT_TYPE, oldPersistentType, newPersistentType);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping#getPersistentType <em>Persistent Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persistent Type</em>' containment reference.
	 * @see #getPersistentType()
	 * @generated
	 */
	public void setPersistentType(XmlPersistentType newPersistentType) {
		if (newPersistentType != persistentType) {
			NotificationChain msgs = null;
			if (persistentType != null)
				msgs = ((InternalEObject) persistentType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_TYPE_MAPPING__PERSISTENT_TYPE, null, msgs);
			if (newPersistentType != null)
				msgs = ((InternalEObject) newPersistentType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_TYPE_MAPPING__PERSISTENT_TYPE, null, msgs);
			msgs = basicSetPersistentType(newPersistentType, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_TYPE_MAPPING__PERSISTENT_TYPE, newPersistentType, newPersistentType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.XML_TYPE_MAPPING__PERSISTENT_TYPE :
				return basicSetPersistentType(null, msgs);
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
			case OrmPackage.XML_TYPE_MAPPING__NAME :
				return getName();
			case OrmPackage.XML_TYPE_MAPPING__TABLE_NAME :
				return getTableName();
			case OrmPackage.XML_TYPE_MAPPING__DEFAULT_ACCESS :
				return getDefaultAccess();
			case OrmPackage.XML_TYPE_MAPPING__SPECIFIED_ACCESS :
				return getSpecifiedAccess();
			case OrmPackage.XML_TYPE_MAPPING__ACCESS :
				return getAccess();
			case OrmPackage.XML_TYPE_MAPPING__METADATA_COMPLETE :
				return getMetadataComplete();
			case OrmPackage.XML_TYPE_MAPPING__PERSISTENT_TYPE :
				return getPersistentType();
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
			case OrmPackage.XML_TYPE_MAPPING__DEFAULT_ACCESS :
				setDefaultAccess((AccessType) newValue);
				return;
			case OrmPackage.XML_TYPE_MAPPING__SPECIFIED_ACCESS :
				setSpecifiedAccess((AccessType) newValue);
				return;
			case OrmPackage.XML_TYPE_MAPPING__METADATA_COMPLETE :
				setMetadataComplete((DefaultFalseBoolean) newValue);
				return;
			case OrmPackage.XML_TYPE_MAPPING__PERSISTENT_TYPE :
				setPersistentType((XmlPersistentType) newValue);
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
			case OrmPackage.XML_TYPE_MAPPING__DEFAULT_ACCESS :
				setDefaultAccess(DEFAULT_ACCESS_EDEFAULT);
				return;
			case OrmPackage.XML_TYPE_MAPPING__SPECIFIED_ACCESS :
				setSpecifiedAccess(SPECIFIED_ACCESS_EDEFAULT);
				return;
			case OrmPackage.XML_TYPE_MAPPING__METADATA_COMPLETE :
				setMetadataComplete(METADATA_COMPLETE_EDEFAULT);
				return;
			case OrmPackage.XML_TYPE_MAPPING__PERSISTENT_TYPE :
				setPersistentType((XmlPersistentType) null);
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
			case OrmPackage.XML_TYPE_MAPPING__NAME :
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case OrmPackage.XML_TYPE_MAPPING__TABLE_NAME :
				return TABLE_NAME_EDEFAULT == null ? getTableName() != null : !TABLE_NAME_EDEFAULT.equals(getTableName());
			case OrmPackage.XML_TYPE_MAPPING__DEFAULT_ACCESS :
				return defaultAccess != DEFAULT_ACCESS_EDEFAULT;
			case OrmPackage.XML_TYPE_MAPPING__SPECIFIED_ACCESS :
				return specifiedAccess != SPECIFIED_ACCESS_EDEFAULT;
			case OrmPackage.XML_TYPE_MAPPING__ACCESS :
				return getAccess() != ACCESS_EDEFAULT;
			case OrmPackage.XML_TYPE_MAPPING__METADATA_COMPLETE :
				return metadataComplete != METADATA_COMPLETE_EDEFAULT;
			case OrmPackage.XML_TYPE_MAPPING__PERSISTENT_TYPE :
				return persistentType != null;
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
		if (baseClass == ITypeMapping.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_TYPE_MAPPING__NAME :
					return JpaCorePackage.ITYPE_MAPPING__NAME;
				case OrmPackage.XML_TYPE_MAPPING__TABLE_NAME :
					return JpaCorePackage.ITYPE_MAPPING__TABLE_NAME;
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
		if (baseClass == ITypeMapping.class) {
			switch (baseFeatureID) {
				case JpaCorePackage.ITYPE_MAPPING__NAME :
					return OrmPackage.XML_TYPE_MAPPING__NAME;
				case JpaCorePackage.ITYPE_MAPPING__TABLE_NAME :
					return OrmPackage.XML_TYPE_MAPPING__TABLE_NAME;
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
		result.append(" (defaultAccess: ");
		result.append(defaultAccess);
		result.append(", specifiedAccess: ");
		result.append(specifiedAccess);
		result.append(", metadataComplete: ");
		result.append(metadataComplete);
		result.append(')');
		return result.toString();
	}

	public void initialize() {
	//do nothing as this will be handle by the Xml Translators
	}

	/**
	 * ITypeMapping is changed and various ITypeMappings may have
	 * common settings.  In this method initialize the new ITypeMapping (this)
	 * fromthe old ITypeMapping (oldMapping)
	 * @param oldMapping
	 */
	public void initializeFrom(XmlTypeMapping oldMapping) {
		setPersistentType(oldMapping.getPersistentType());
	}

	public IJpaContentNode getContentNode(int offset) {
		return getPersistentType().getContentNode(offset);
	}

	public Table primaryDbTable() {
		return null;
	}

	public Table dbTable(String tableName) {
		return null;
	}
	
	public Schema dbSchema() {
		return null;
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {}

	public ITextRange getClassTextRange() {
		IDOMNode classNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.CLASS);
		if (classNode != null) {
			return buildTextRange(classNode);
		}
		else {
			return getTextRange();
		}
	}

	public ITextRange getAttributesTextRange() {
		IDOMNode attributesNode = (IDOMNode) DOMUtilities.getNodeChild(node, OrmXmlMapper.ATTRIBUTES);
		if (attributesNode != null) {
			return buildTextRange(attributesNode);
		}
		else {
			return getTextRange();
		}
	}

	/**
	 * type mappings are a sequence in the orm schema. We must keep
	 * the list of type mappings in the appropriate order so the wtp xml 
	 * translators will write them to the xml in that order and they
	 * will adhere to the schema.  
	 * 
	 * Each concrete subclass of XmlTypeMapping must implement this
	 * method and return an int that matches it's order in the schema
	 * @return
	 */
	public abstract int xmlSequence();

	/**
	 * @see ITypeMapping#attributeMappingKeyAllowed(String)
	 * 
	 * Default implementation:  override where needed
	 */
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return true;
	}
}
