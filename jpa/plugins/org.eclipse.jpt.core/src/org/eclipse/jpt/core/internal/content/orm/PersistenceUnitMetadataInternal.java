/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.jpt.core.internal.XmlEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Persistence Unit Metadata Internal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal#isXmlMappingMetadataCompleteInternal <em>Xml Mapping Metadata Complete Internal</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal#getPersistenceUnitDefaultsInternal <em>Persistence Unit Defaults Internal</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadataInternal()
 * @model kind="class"
 * @generated
 */
public class PersistenceUnitMetadataInternal extends XmlEObject
	implements PersistenceUnitMetadataForXml, PersistenceUnitMetadata
{
	/**
	 * The default value of the '{@link #isXmlMappingMetadataCompleteForXml() <em>Xml Mapping Metadata Complete For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlMappingMetadataCompleteForXml()
	 * @generated
	 * @ordered
	 */
	protected static final boolean XML_MAPPING_METADATA_COMPLETE_FOR_XML_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isXmlMappingMetadataComplete() <em>Xml Mapping Metadata Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlMappingMetadataComplete()
	 * @generated
	 * @ordered
	 */
	protected static final boolean XML_MAPPING_METADATA_COMPLETE_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isXmlMappingMetadataCompleteInternal() <em>Xml Mapping Metadata Complete Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlMappingMetadataCompleteInternal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean XML_MAPPING_METADATA_COMPLETE_INTERNAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isXmlMappingMetadataCompleteInternal() <em>Xml Mapping Metadata Complete Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isXmlMappingMetadataCompleteInternal()
	 * @generated
	 * @ordered
	 */
	protected boolean xmlMappingMetadataCompleteInternal = XML_MAPPING_METADATA_COMPLETE_INTERNAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPersistenceUnitDefaultsInternal() <em>Persistence Unit Defaults Internal</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPersistenceUnitDefaultsInternal()
	 * @generated
	 * @ordered
	 */
	protected PersistenceUnitDefaultsInternal persistenceUnitDefaultsInternal;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected PersistenceUnitMetadataInternal() {
		super();
		//we don't want a setter for this object since it should never be null, but
		//it must be initialized and is necessary for emf to call the eInverseAdd method
		this.persistenceUnitDefaultsInternal = OrmFactory.eINSTANCE.createPersistenceUnitDefaultsInternal();
		((InternalEObject) this.persistenceUnitDefaultsInternal).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS_INTERNAL, null, null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.PERSISTENCE_UNIT_METADATA_INTERNAL;
	}

	/**
	 * Returns the value of the '<em><b>Xml Mapping Metadata Complete For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * API used by the xml translator.  Defers to the internal attribute, no actual
	 * xml attribute is stored in the model.
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Mapping Metadata Complete For Xml</em>' attribute.
	 * @see #setXmlMappingMetadataCompleteForXml(boolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadataForXml_XmlMappingMetadataCompleteForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public boolean isXmlMappingMetadataCompleteForXml() {
		return isXmlMappingMetadataCompleteInternal();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal#isXmlMappingMetadataCompleteForXml <em>Xml Mapping Metadata Complete For Xml</em>}' attribute.
	 * API used by the XML translator, sets the internal attribute and
	 * fires notification about the XML attribute changing.  
	 * @param value the new value of the '<em>Xml Mapping Metadata Complete For Xml</em>' attribute.
	 * @see #isXmlMappingMetadataCompleteForXml()
	 * @generated NOT
	 */
	public void setXmlMappingMetadataCompleteForXml(boolean newXmlMappingMetadataCompleteForXml) {
		setXmlMappingMetadataCompleteInternal(newXmlMappingMetadataCompleteForXml);
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_FOR_XML, null, newXmlMappingMetadataCompleteForXml));
	}

	/**
	 * Returns the value of the '<em><b>Persistence Unit Defaults For Xml</b></em>' reference.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * API just for the xml translators. Null in the model for a containment 
	 * object corresponds to no persistence-unit-defaults xml tag in the xml file.
	 * We check for whether any features are set in the model and return null for
	 * persistenceUnitDefaultsForXml if there aren't any.  Otherwise we return
	 * the persistenceUnitDefaultsInternal that has already been created.
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Unit Defaults For Xml</em>' reference.
	 * @see #setPersistenceUnitDefaultsForXml(PersistenceUnitDefaultsForXml)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadataForXml_PersistenceUnitDefaultsForXml()
	 * @model resolveProxies="false" volatile="true"
	 * @generated NOT
	 */
	public PersistenceUnitDefaultsForXml getPersistenceUnitDefaultsForXml() {
		if (getPersistenceUnitDefaultsInternal().isAllFeaturesUnset()) {
			return null;
		}
		return getPersistenceUnitDefaultsInternal();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal#getPersistenceUnitDefaultsForXml <em>Persistence Unit Defaults For Xml</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persistence Unit Defaults For Xml</em>' reference.
	 * @see #getPersistenceUnitDefaultsForXml()
	 * @generated NOT
	 */
	public void setPersistenceUnitDefaultsForXmlGen(PersistenceUnitDefaultsForXml newPersistenceUnitDefaultsForXml) {
		PersistenceUnitDefaultsForXml oldValue = newPersistenceUnitDefaultsForXml == null ? (PersistenceUnitDefaultsForXml) getPersistenceUnitDefaults() : null;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS_FOR_XML, oldValue, newPersistenceUnitDefaultsForXml));
	}

	public void setPersistenceUnitDefaultsForXml(PersistenceUnitDefaultsForXml newPersistenceUnitDefaultsForXml) {
		setPersistenceUnitDefaultsForXmlGen(newPersistenceUnitDefaultsForXml);
		if (newPersistenceUnitDefaultsForXml == null) {
			getPersistenceUnitDefaultsInternal().unsetAllAttributes();
		}
	}

	public void makePersistenceUnitDefaultsForXmlNull() {
		setPersistenceUnitDefaultsForXmlGen(null);
		if (isAllFeaturesUnset()) {
			getEntityMappings().makePersistenceUnitMetadataForXmlNull();
		}
	}

	public void makePersistenceUnitDefaultsForXmlNonNull() {
		setPersistenceUnitDefaultsForXmlGen(getPersistenceUnitDefaultsForXml());
		getEntityMappings().makePersistenceUnitMetadataForXmlNonNull();
	}

	private EntityMappingsInternal getEntityMappings() {
		return (EntityMappingsInternal) eContainer();
	}

	/**
	 * Returns the value of the '<em><b>Xml Mapping Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Mapping Metadata Complete</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Mapping Metadata Complete</em>' attribute.
	 * @see #setXmlMappingMetadataComplete(boolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadata_XmlMappingMetadataComplete()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public boolean isXmlMappingMetadataComplete() {
		return isXmlMappingMetadataCompleteInternal();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal#isXmlMappingMetadataComplete <em>Xml Mapping Metadata Complete</em>}' attribute.
	 * This api should be used by the UI.  It calls the appropriate
	 * internal api for updating the xml.  It also handles setting container
	 * objects to null for the xml.  If access is set to the default, empty xml containment
	 * tags will be removed when they no longer contain any other xml tags. 
	 * This is done in the UI method because we do not want the same behavior
	 * when setting the access from the xml, we never want to change the xml
	 * as the user is directly edting the xml.
	 * @param value the new value of the '<em>Xml Mapping Metadata Complete</em>' attribute.
	 * @see #isXmlMappingMetadataComplete()
	 * @generated NOT
	 */
	public void setXmlMappingMetadataComplete(boolean newXmlMappingMetadataComplete) {
		setXmlMappingMetadataCompleteInternal(newXmlMappingMetadataComplete);
		if (newXmlMappingMetadataComplete != XML_MAPPING_METADATA_COMPLETE_EDEFAULT) {
			getEntityMappings().makePersistenceUnitMetadataForXmlNonNull();
		}
		setXmlMappingMetadataCompleteForXml(newXmlMappingMetadataComplete);
		if (isAllFeaturesUnset()) {
			getEntityMappings().makePersistenceUnitMetadataForXmlNull();
		}
	}

	/**
	 * Returns the value of the '<em><b>Persistence Unit Defaults</b></em>' reference.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persistence Unit Defaults</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Unit Defaults</em>' reference.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadata_PersistenceUnitDefaults()
	 * @model resolveProxies="false" changeable="false" volatile="true"
	 * @generated NOT
	 */
	public PersistenceUnitDefaults getPersistenceUnitDefaults() {
		return getPersistenceUnitDefaultsInternal();
	}

	/**
	 * Returns the value of the '<em><b>Xml Mapping Metadata Complete Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Xml Mapping Metadata Complete Internal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Xml Mapping Metadata Complete Internal</em>' attribute.
	 * @see #setXmlMappingMetadataCompleteInternal(boolean)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadataInternal_XmlMappingMetadataCompleteInternal()
	 * @model
	 * @generated
	 */
	public boolean isXmlMappingMetadataCompleteInternal() {
		return xmlMappingMetadataCompleteInternal;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadataInternal#isXmlMappingMetadataCompleteInternal <em>Xml Mapping Metadata Complete Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Xml Mapping Metadata Complete Internal</em>' attribute.
	 * @see #isXmlMappingMetadataCompleteInternal()
	 * @generated NOT
	 */
	public void setXmlMappingMetadataCompleteInternal(boolean newXmlMappingMetadataCompleteInternal) {
		boolean oldXmlMappingMetadataCompleteInternal = xmlMappingMetadataCompleteInternal;
		xmlMappingMetadataCompleteInternal = newXmlMappingMetadataCompleteInternal;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_INTERNAL, oldXmlMappingMetadataCompleteInternal, xmlMappingMetadataCompleteInternal));
			//notification so the UI is updated when the xml changes, can't call the UI api 
			//because it has other side effects
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE, oldXmlMappingMetadataCompleteInternal, xmlMappingMetadataCompleteInternal));
		}
	}

	/**
	 * Returns the value of the '<em><b>Persistence Unit Defaults Internal</b></em>' containment reference.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persistence Unit Defaults Internal</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistence Unit Defaults Internal</em>' containment reference.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getPersistenceUnitMetadataInternal_PersistenceUnitDefaultsInternal()
	 * @model containment="true" required="true" changeable="false"
	 * @generated
	 */
	public PersistenceUnitDefaultsInternal getPersistenceUnitDefaultsInternal() {
		return persistenceUnitDefaultsInternal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPersistenceUnitDefaultsInternal(PersistenceUnitDefaultsInternal newPersistenceUnitDefaultsInternal, NotificationChain msgs) {
		PersistenceUnitDefaultsInternal oldPersistenceUnitDefaultsInternal = persistenceUnitDefaultsInternal;
		persistenceUnitDefaultsInternal = newPersistenceUnitDefaultsInternal;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS_INTERNAL, oldPersistenceUnitDefaultsInternal, newPersistenceUnitDefaultsInternal);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS_INTERNAL :
				return basicSetPersistenceUnitDefaultsInternal(null, msgs);
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
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_FOR_XML :
				return isXmlMappingMetadataCompleteForXml() ? Boolean.TRUE : Boolean.FALSE;
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS_FOR_XML :
				return getPersistenceUnitDefaultsForXml();
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE :
				return isXmlMappingMetadataComplete() ? Boolean.TRUE : Boolean.FALSE;
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS :
				return getPersistenceUnitDefaults();
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_INTERNAL :
				return isXmlMappingMetadataCompleteInternal() ? Boolean.TRUE : Boolean.FALSE;
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS_INTERNAL :
				return getPersistenceUnitDefaultsInternal();
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
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_FOR_XML :
				setXmlMappingMetadataCompleteForXml(((Boolean) newValue).booleanValue());
				return;
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS_FOR_XML :
				setPersistenceUnitDefaultsForXml((PersistenceUnitDefaultsForXml) newValue);
				return;
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE :
				setXmlMappingMetadataComplete(((Boolean) newValue).booleanValue());
				return;
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_INTERNAL :
				setXmlMappingMetadataCompleteInternal(((Boolean) newValue).booleanValue());
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
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_FOR_XML :
				setXmlMappingMetadataCompleteForXml(XML_MAPPING_METADATA_COMPLETE_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS_FOR_XML :
				setPersistenceUnitDefaultsForXml((PersistenceUnitDefaultsForXml) null);
				return;
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE :
				setXmlMappingMetadataComplete(XML_MAPPING_METADATA_COMPLETE_EDEFAULT);
				return;
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_INTERNAL :
				setXmlMappingMetadataCompleteInternal(XML_MAPPING_METADATA_COMPLETE_INTERNAL_EDEFAULT);
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
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_FOR_XML :
				return isXmlMappingMetadataCompleteForXml() != XML_MAPPING_METADATA_COMPLETE_FOR_XML_EDEFAULT;
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS_FOR_XML :
				return getPersistenceUnitDefaultsForXml() != null;
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE :
				return isXmlMappingMetadataComplete() != XML_MAPPING_METADATA_COMPLETE_EDEFAULT;
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS :
				return getPersistenceUnitDefaults() != null;
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_INTERNAL :
				return xmlMappingMetadataCompleteInternal != XML_MAPPING_METADATA_COMPLETE_INTERNAL_EDEFAULT;
			case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS_INTERNAL :
				return persistenceUnitDefaultsInternal != null;
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
		if (baseClass == PersistenceUnitMetadataForXml.class) {
			switch (derivedFeatureID) {
				case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_FOR_XML :
					return OrmPackage.PERSISTENCE_UNIT_METADATA_FOR_XML__XML_MAPPING_METADATA_COMPLETE_FOR_XML;
				case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS_FOR_XML :
					return OrmPackage.PERSISTENCE_UNIT_METADATA_FOR_XML__PERSISTENCE_UNIT_DEFAULTS_FOR_XML;
				default :
					return -1;
			}
		}
		if (baseClass == PersistenceUnitMetadata.class) {
			switch (derivedFeatureID) {
				case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE :
					return OrmPackage.PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE;
				case OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS :
					return OrmPackage.PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS;
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
		if (baseClass == PersistenceUnitMetadataForXml.class) {
			switch (baseFeatureID) {
				case OrmPackage.PERSISTENCE_UNIT_METADATA_FOR_XML__XML_MAPPING_METADATA_COMPLETE_FOR_XML :
					return OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_FOR_XML;
				case OrmPackage.PERSISTENCE_UNIT_METADATA_FOR_XML__PERSISTENCE_UNIT_DEFAULTS_FOR_XML :
					return OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS_FOR_XML;
				default :
					return -1;
			}
		}
		if (baseClass == PersistenceUnitMetadata.class) {
			switch (baseFeatureID) {
				case OrmPackage.PERSISTENCE_UNIT_METADATA__XML_MAPPING_METADATA_COMPLETE :
					return OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE;
				case OrmPackage.PERSISTENCE_UNIT_METADATA__PERSISTENCE_UNIT_DEFAULTS :
					return OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__PERSISTENCE_UNIT_DEFAULTS;
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
		result.append(" (xmlMappingMetadataCompleteInternal: ");
		result.append(xmlMappingMetadataCompleteInternal);
		result.append(')');
		return result.toString();
	}

	/**
	 * Call this when the persistence-unit-metadata tag is removed
	 * from the xml, need to make sure all the model attributes are set to the default
	 */
	protected void unsetAllAttributes() {
		eUnset(OrmPackage.PERSISTENCE_UNIT_METADATA_INTERNAL__XML_MAPPING_METADATA_COMPLETE_INTERNAL);
		getPersistenceUnitDefaultsInternal().unsetAllAttributes();
	}
}