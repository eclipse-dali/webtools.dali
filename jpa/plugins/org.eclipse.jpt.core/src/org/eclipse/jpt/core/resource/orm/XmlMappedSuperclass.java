/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.internal.utility.translators.EmptyTagBooleanTranslator;
import org.eclipse.jpt.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.v2_0.JPA2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlMappedSuperclass_2_0;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mapped Superclass</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#getIdClass <em>Id Class</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#isExcludeDefaultListeners <em>Exclude Default Listeners</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#isExcludeSuperclassListeners <em>Exclude Superclass Listeners</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#getEntityListeners <em>Entity Listeners</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMappedSuperclass()
 * @model kind="class"
 * @generated
 */
public class XmlMappedSuperclass extends AbstractXmlTypeMapping implements XmlMappedSuperclass_2_0
{

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
	 * The cached value of the '{@link #getIdClass() <em>Id Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdClass()
	 * @generated
	 * @ordered
	 */
	protected XmlIdClass idClass;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlMappedSuperclass()
	{
		super();
	}
	
	public String getMappingKey() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
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
		return OrmPackage.Literals.XML_MAPPED_SUPERCLASS;
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMappedSuperclass_2_0_Cacheable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getCacheable()
	{
		return cacheable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#getCacheable <em>Cacheable</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MAPPED_SUPERCLASS__CACHEABLE, oldCacheable, cacheable));
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
	 * @see #setIdClass(XmlIdClass)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMappedSuperclass_IdClass()
	 * @model containment="true"
	 * @generated
	 */
	public XmlIdClass getIdClass()
	{
		return idClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIdClass(XmlIdClass newIdClass, NotificationChain msgs)
	{
		XmlIdClass oldIdClass = idClass;
		idClass = newIdClass;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MAPPED_SUPERCLASS__ID_CLASS, oldIdClass, newIdClass);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#getIdClass <em>Id Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Class</em>' containment reference.
	 * @see #getIdClass()
	 * @generated
	 */
	public void setIdClass(XmlIdClass newIdClass)
	{
		if (newIdClass != idClass)
		{
			NotificationChain msgs = null;
			if (idClass != null)
				msgs = ((InternalEObject)idClass).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_MAPPED_SUPERCLASS__ID_CLASS, null, msgs);
			if (newIdClass != null)
				msgs = ((InternalEObject)newIdClass).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_MAPPED_SUPERCLASS__ID_CLASS, null, msgs);
			msgs = basicSetIdClass(newIdClass, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MAPPED_SUPERCLASS__ID_CLASS, newIdClass, newIdClass));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMappedSuperclass_ExcludeDefaultListeners()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isExcludeDefaultListeners()
	{
		return excludeDefaultListeners;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#isExcludeDefaultListeners <em>Exclude Default Listeners</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_LISTENERS, oldExcludeDefaultListeners, excludeDefaultListeners));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMappedSuperclass_ExcludeSuperclassListeners()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	public boolean isExcludeSuperclassListeners()
	{
		return excludeSuperclassListeners;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#isExcludeSuperclassListeners <em>Exclude Superclass Listeners</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MAPPED_SUPERCLASS__EXCLUDE_SUPERCLASS_LISTENERS, oldExcludeSuperclassListeners, excludeSuperclassListeners));
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
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlMappedSuperclass_EntityListeners()
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MAPPED_SUPERCLASS__ENTITY_LISTENERS, oldEntityListeners, newEntityListeners);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass#getEntityListeners <em>Entity Listeners</em>}' containment reference.
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
				msgs = ((InternalEObject)entityListeners).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_MAPPED_SUPERCLASS__ENTITY_LISTENERS, null, msgs);
			if (newEntityListeners != null)
				msgs = ((InternalEObject)newEntityListeners).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_MAPPED_SUPERCLASS__ENTITY_LISTENERS, null, msgs);
			msgs = basicSetEntityListeners(newEntityListeners, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_MAPPED_SUPERCLASS__ENTITY_LISTENERS, newEntityListeners, newEntityListeners));
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
			case OrmPackage.XML_MAPPED_SUPERCLASS__ID_CLASS:
				return basicSetIdClass(null, msgs);
			case OrmPackage.XML_MAPPED_SUPERCLASS__ENTITY_LISTENERS:
				return basicSetEntityListeners(null, msgs);
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
			case OrmPackage.XML_MAPPED_SUPERCLASS__CACHEABLE:
				return getCacheable();
			case OrmPackage.XML_MAPPED_SUPERCLASS__ID_CLASS:
				return getIdClass();
			case OrmPackage.XML_MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_LISTENERS:
				return isExcludeDefaultListeners();
			case OrmPackage.XML_MAPPED_SUPERCLASS__EXCLUDE_SUPERCLASS_LISTENERS:
				return isExcludeSuperclassListeners();
			case OrmPackage.XML_MAPPED_SUPERCLASS__ENTITY_LISTENERS:
				return getEntityListeners();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case OrmPackage.XML_MAPPED_SUPERCLASS__CACHEABLE:
				setCacheable((Boolean)newValue);
				return;
			case OrmPackage.XML_MAPPED_SUPERCLASS__ID_CLASS:
				setIdClass((XmlIdClass)newValue);
				return;
			case OrmPackage.XML_MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_LISTENERS:
				setExcludeDefaultListeners((Boolean)newValue);
				return;
			case OrmPackage.XML_MAPPED_SUPERCLASS__EXCLUDE_SUPERCLASS_LISTENERS:
				setExcludeSuperclassListeners((Boolean)newValue);
				return;
			case OrmPackage.XML_MAPPED_SUPERCLASS__ENTITY_LISTENERS:
				setEntityListeners((EntityListeners)newValue);
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
			case OrmPackage.XML_MAPPED_SUPERCLASS__CACHEABLE:
				setCacheable(CACHEABLE_EDEFAULT);
				return;
			case OrmPackage.XML_MAPPED_SUPERCLASS__ID_CLASS:
				setIdClass((XmlIdClass)null);
				return;
			case OrmPackage.XML_MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_LISTENERS:
				setExcludeDefaultListeners(EXCLUDE_DEFAULT_LISTENERS_EDEFAULT);
				return;
			case OrmPackage.XML_MAPPED_SUPERCLASS__EXCLUDE_SUPERCLASS_LISTENERS:
				setExcludeSuperclassListeners(EXCLUDE_SUPERCLASS_LISTENERS_EDEFAULT);
				return;
			case OrmPackage.XML_MAPPED_SUPERCLASS__ENTITY_LISTENERS:
				setEntityListeners((EntityListeners)null);
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
			case OrmPackage.XML_MAPPED_SUPERCLASS__CACHEABLE:
				return CACHEABLE_EDEFAULT == null ? cacheable != null : !CACHEABLE_EDEFAULT.equals(cacheable);
			case OrmPackage.XML_MAPPED_SUPERCLASS__ID_CLASS:
				return idClass != null;
			case OrmPackage.XML_MAPPED_SUPERCLASS__EXCLUDE_DEFAULT_LISTENERS:
				return excludeDefaultListeners != EXCLUDE_DEFAULT_LISTENERS_EDEFAULT;
			case OrmPackage.XML_MAPPED_SUPERCLASS__EXCLUDE_SUPERCLASS_LISTENERS:
				return excludeSuperclassListeners != EXCLUDE_SUPERCLASS_LISTENERS_EDEFAULT;
			case OrmPackage.XML_MAPPED_SUPERCLASS__ENTITY_LISTENERS:
				return entityListeners != null;
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
		if (baseClass == XmlMappedSuperclass_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_MAPPED_SUPERCLASS__CACHEABLE: return OrmV2_0Package.XML_MAPPED_SUPERCLASS_20__CACHEABLE;
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
		if (baseClass == XmlMappedSuperclass_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_MAPPED_SUPERCLASS_20__CACHEABLE: return OrmPackage.XML_MAPPED_SUPERCLASS__CACHEABLE;
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
		result.append(" (cacheable: ");
		result.append(cacheable);
		result.append(", excludeDefaultListeners: ");
		result.append(excludeDefaultListeners);
		result.append(", excludeSuperclassListeners: ");
		result.append(excludeSuperclassListeners);
		result.append(')');
		return result.toString();
	}

	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildClassTranslator(),
			buildAccessTranslator(),
			buildCacheableTranslator(),
			buildMetadataCompleteTranslator(),
			buildDescriptionTranslator(),
			buildIdClassTranslator(),
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
			Attributes.buildTranslator()
		};
	}
	
	protected static Translator buildAccessTranslator() {
		return new Translator(JPA.ACCESS, OrmPackage.eINSTANCE.getXmlAccessHolder_Access(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildCacheableTranslator() {
		return new Translator(JPA2_0.CACHEABLE, OrmV2_0Package.eINSTANCE.getXmlMappedSuperclass_2_0_Cacheable(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildIdClassTranslator() {
		return XmlIdClass.buildTranslator(JPA.ID_CLASS, OrmPackage.eINSTANCE.getXmlMappedSuperclass_IdClass());
	}
	
	protected static Translator buildExcludeDefaultListenersTranslator() {
		return new EmptyTagBooleanTranslator(JPA.EXCLUDE_DEFAULT_LISTENERS, OrmPackage.eINSTANCE.getXmlMappedSuperclass_ExcludeDefaultListeners());
	}
	
	protected static Translator buildExcludeSuperclassListenersTranslator() {
		return new EmptyTagBooleanTranslator(JPA.EXCLUDE_SUPERCLASS_LISTENERS, OrmPackage.eINSTANCE.getXmlMappedSuperclass_ExcludeSuperclassListeners());
	}
	
	protected static Translator buildEntityListenersTranslator() {
		return EntityListeners.buildTranslator(JPA.ENTITY_LISTENERS, OrmPackage.eINSTANCE.getXmlMappedSuperclass_EntityListeners());
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
}
