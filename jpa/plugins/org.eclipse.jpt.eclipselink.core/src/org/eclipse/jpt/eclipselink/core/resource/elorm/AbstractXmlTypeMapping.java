/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.elorm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.common.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Xml Type Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.elorm.AbstractXmlTypeMapping#getClassName <em>Class Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.elorm.AbstractXmlTypeMapping#getAccess <em>Access</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.elorm.AbstractXmlTypeMapping#getMetadataComplete <em>Metadata Complete</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.elorm.AbstractXmlTypeMapping#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.elorm.AbstractXmlTypeMapping#getAttributes <em>Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getAbstractXmlTypeMapping()
 * @model kind="class" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public abstract class AbstractXmlTypeMapping extends AbstractJpaEObject implements JpaEObject
{
	/**
	 * The default value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected String className = CLASS_NAME_EDEFAULT;

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
	 * The default value of the '{@link #getMetadataComplete() <em>Metadata Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetadataComplete()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean METADATA_COMPLETE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMetadataComplete() <em>Metadata Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetadataComplete()
	 * @generated
	 * @ordered
	 */
	protected Boolean metadataComplete = METADATA_COMPLETE_EDEFAULT;

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
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected Attributes attributes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractXmlTypeMapping()
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
		return EclipseLinkOrmPackage.Literals.ABSTRACT_XML_TYPE_MAPPING;
	}

	/**
	 * Returns the value of the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Name</em>' attribute.
	 * @see #setClassName(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getAbstractXmlTypeMapping_ClassName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated
	 */
	public String getClassName()
	{
		return className;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.AbstractXmlTypeMapping#getClassName <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class Name</em>' attribute.
	 * @see #getClassName()
	 * @generated
	 */
	public void setClassName(String newClassName)
	{
		String oldClassName = className;
		className = newClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__CLASS_NAME, oldClassName, className));
	}

	/**
	 * Returns the value of the '<em><b>Access</b></em>' attribute.
	 * The default value is <code>"PROPERTY"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.eclipselink.core.resource.elorm.AccessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.AccessType
	 * @see #setAccess(AccessType)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getAbstractXmlTypeMapping_Access()
	 * @model default="PROPERTY"
	 * @generated
	 */
	public AccessType getAccess()
	{
		return access;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.AbstractXmlTypeMapping#getAccess <em>Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.AccessType
	 * @see #getAccess()
	 * @generated
	 */
	public void setAccess(AccessType newAccess)
	{
		AccessType oldAccess = access;
		access = newAccess == null ? ACCESS_EDEFAULT : newAccess;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__ACCESS, oldAccess, access));
	}

	/**
	 * Returns the value of the '<em><b>Metadata Complete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metadata Complete</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metadata Complete</em>' attribute.
	 * @see #setMetadataComplete(Boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getAbstractXmlTypeMapping_MetadataComplete()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getMetadataComplete()
	{
		return metadataComplete;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.AbstractXmlTypeMapping#getMetadataComplete <em>Metadata Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metadata Complete</em>' attribute.
	 * @see #getMetadataComplete()
	 * @generated
	 */
	public void setMetadataComplete(Boolean newMetadataComplete)
	{
		Boolean oldMetadataComplete = metadataComplete;
		metadataComplete = newMetadataComplete;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__METADATA_COMPLETE, oldMetadataComplete, metadataComplete));
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
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getAbstractXmlTypeMapping_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.AbstractXmlTypeMapping#getDescription <em>Description</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__DESCRIPTION, oldDescription, description));
	}

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference.
	 * @see #setAttributes(Attributes)
	 * @see org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmPackage#getAbstractXmlTypeMapping_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	public Attributes getAttributes()
	{
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributes(Attributes newAttributes, NotificationChain msgs)
	{
		Attributes oldAttributes = attributes;
		attributes = newAttributes;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__ATTRIBUTES, oldAttributes, newAttributes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.elorm.AbstractXmlTypeMapping#getAttributes <em>Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attributes</em>' containment reference.
	 * @see #getAttributes()
	 * @generated
	 */
	public void setAttributes(Attributes newAttributes)
	{
		if (newAttributes != attributes)
		{
			NotificationChain msgs = null;
			if (attributes != null)
				msgs = ((InternalEObject)attributes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__ATTRIBUTES, null, msgs);
			if (newAttributes != null)
				msgs = ((InternalEObject)newAttributes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__ATTRIBUTES, null, msgs);
			msgs = basicSetAttributes(newAttributes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__ATTRIBUTES, newAttributes, newAttributes));
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
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__ATTRIBUTES:
				return basicSetAttributes(null, msgs);
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
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__CLASS_NAME:
				return getClassName();
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__ACCESS:
				return getAccess();
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__METADATA_COMPLETE:
				return getMetadataComplete();
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__DESCRIPTION:
				return getDescription();
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__ATTRIBUTES:
				return getAttributes();
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
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__CLASS_NAME:
				setClassName((String)newValue);
				return;
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__ACCESS:
				setAccess((AccessType)newValue);
				return;
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__METADATA_COMPLETE:
				setMetadataComplete((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__ATTRIBUTES:
				setAttributes((Attributes)newValue);
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
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__CLASS_NAME:
				setClassName(CLASS_NAME_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__ACCESS:
				setAccess(ACCESS_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__METADATA_COMPLETE:
				setMetadataComplete(METADATA_COMPLETE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__ATTRIBUTES:
				setAttributes((Attributes)null);
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
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__CLASS_NAME:
				return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__ACCESS:
				return access != ACCESS_EDEFAULT;
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__METADATA_COMPLETE:
				return METADATA_COMPLETE_EDEFAULT == null ? metadataComplete != null : !METADATA_COMPLETE_EDEFAULT.equals(metadataComplete);
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case EclipseLinkOrmPackage.ABSTRACT_XML_TYPE_MAPPING__ATTRIBUTES:
				return attributes != null;
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
		result.append(" (className: ");
		result.append(className);
		result.append(", access: ");
		result.append(access);
		result.append(", metadataComplete: ");
		result.append(metadataComplete);
		result.append(", description: ");
		result.append(description);
		result.append(')');
		return result.toString();
	}

} // AbstractXmlTypeMapping
