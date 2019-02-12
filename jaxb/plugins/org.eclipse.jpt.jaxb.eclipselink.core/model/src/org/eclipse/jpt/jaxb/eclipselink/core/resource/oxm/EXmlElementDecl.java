/*******************************************************************************
 * Copyright (c) 2012, 2019 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/**
 */
package org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.common.core.resource.xml.EBaseObjectImpl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EXml Element Decl</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getJavaMethod <em>Java Method</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getScope <em>Scope</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getSubstitutionHeadName <em>Substitution Head Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getSubstitutionHeadNamespace <em>Substitution Head Namespace</em>}</li>
 *   <li>{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementDecl()
 * @model kind="class"
 * @extends EBaseObject
 * @generated
 */
public class EXmlElementDecl extends EBaseObjectImpl implements EBaseObject
{
	/**
	 * The default value of the '{@link #getJavaMethod() <em>Java Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaMethod()
	 * @generated
	 * @ordered
	 */
	protected static final String JAVA_METHOD_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getJavaMethod() <em>Java Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJavaMethod()
	 * @generated
	 * @ordered
	 */
	protected String javaMethod = JAVA_METHOD_EDEFAULT;
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
	 * The default value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected static final String NAMESPACE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected String namespace = NAMESPACE_EDEFAULT;
	/**
	 * The default value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_VALUE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected String defaultValue = DEFAULT_VALUE_EDEFAULT;
	/**
	 * The default value of the '{@link #getScope() <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScope()
	 * @generated
	 * @ordered
	 */
	protected static final String SCOPE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getScope() <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScope()
	 * @generated
	 * @ordered
	 */
	protected String scope = SCOPE_EDEFAULT;
	/**
	 * The default value of the '{@link #getSubstitutionHeadName() <em>Substitution Head Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubstitutionHeadName()
	 * @generated
	 * @ordered
	 */
	protected static final String SUBSTITUTION_HEAD_NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getSubstitutionHeadName() <em>Substitution Head Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubstitutionHeadName()
	 * @generated
	 * @ordered
	 */
	protected String substitutionHeadName = SUBSTITUTION_HEAD_NAME_EDEFAULT;
	/**
	 * The default value of the '{@link #getSubstitutionHeadNamespace() <em>Substitution Head Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubstitutionHeadNamespace()
	 * @generated
	 * @ordered
	 */
	protected static final String SUBSTITUTION_HEAD_NAMESPACE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getSubstitutionHeadNamespace() <em>Substitution Head Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubstitutionHeadNamespace()
	 * @generated
	 * @ordered
	 */
	protected String substitutionHeadNamespace = SUBSTITUTION_HEAD_NAMESPACE_EDEFAULT;
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EXmlElementDecl()
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
		return OxmPackage.Literals.EXML_ELEMENT_DECL;
	}

	/**
	 * Returns the value of the '<em><b>Java Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Java Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Java Method</em>' attribute.
	 * @see #setJavaMethod(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementDecl_JavaMethod()
	 * @model
	 * @generated
	 */
	public String getJavaMethod()
	{
		return javaMethod;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getJavaMethod <em>Java Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Java Method</em>' attribute.
	 * @see #getJavaMethod()
	 * @generated
	 */
	public void setJavaMethod(String newJavaMethod)
	{
		String oldJavaMethod = javaMethod;
		javaMethod = newJavaMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_DECL__JAVA_METHOD, oldJavaMethod, javaMethod));
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
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementDecl_Name()
	 * @model
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_DECL__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Namespace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Namespace</em>' attribute.
	 * @see #setNamespace(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementDecl_Namespace()
	 * @model
	 * @generated
	 */
	public String getNamespace()
	{
		return namespace;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getNamespace <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' attribute.
	 * @see #getNamespace()
	 * @generated
	 */
	public void setNamespace(String newNamespace)
	{
		String oldNamespace = namespace;
		namespace = newNamespace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_DECL__NAMESPACE, oldNamespace, namespace));
	}

	/**
	 * Returns the value of the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Value</em>' attribute.
	 * @see #setDefaultValue(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementDecl_DefaultValue()
	 * @model
	 * @generated
	 */
	public String getDefaultValue()
	{
		return defaultValue;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getDefaultValue <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Value</em>' attribute.
	 * @see #getDefaultValue()
	 * @generated
	 */
	public void setDefaultValue(String newDefaultValue)
	{
		String oldDefaultValue = defaultValue;
		defaultValue = newDefaultValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_DECL__DEFAULT_VALUE, oldDefaultValue, defaultValue));
	}

	/**
	 * Returns the value of the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scope</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scope</em>' attribute.
	 * @see #setScope(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementDecl_Scope()
	 * @model
	 * @generated
	 */
	public String getScope()
	{
		return scope;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getScope <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scope</em>' attribute.
	 * @see #getScope()
	 * @generated
	 */
	public void setScope(String newScope)
	{
		String oldScope = scope;
		scope = newScope;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_DECL__SCOPE, oldScope, scope));
	}

	/**
	 * Returns the value of the '<em><b>Substitution Head Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Substitution Head Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Substitution Head Name</em>' attribute.
	 * @see #setSubstitutionHeadName(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementDecl_SubstitutionHeadName()
	 * @model
	 * @generated
	 */
	public String getSubstitutionHeadName()
	{
		return substitutionHeadName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getSubstitutionHeadName <em>Substitution Head Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Substitution Head Name</em>' attribute.
	 * @see #getSubstitutionHeadName()
	 * @generated
	 */
	public void setSubstitutionHeadName(String newSubstitutionHeadName)
	{
		String oldSubstitutionHeadName = substitutionHeadName;
		substitutionHeadName = newSubstitutionHeadName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAME, oldSubstitutionHeadName, substitutionHeadName));
	}

	/**
	 * Returns the value of the '<em><b>Substitution Head Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Substitution Head Namespace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Substitution Head Namespace</em>' attribute.
	 * @see #setSubstitutionHeadNamespace(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementDecl_SubstitutionHeadNamespace()
	 * @model
	 * @generated
	 */
	public String getSubstitutionHeadNamespace()
	{
		return substitutionHeadNamespace;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getSubstitutionHeadNamespace <em>Substitution Head Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Substitution Head Namespace</em>' attribute.
	 * @see #getSubstitutionHeadNamespace()
	 * @generated
	 */
	public void setSubstitutionHeadNamespace(String newSubstitutionHeadNamespace)
	{
		String oldSubstitutionHeadNamespace = substitutionHeadNamespace;
		substitutionHeadNamespace = newSubstitutionHeadNamespace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAMESPACE, oldSubstitutionHeadNamespace, substitutionHeadNamespace));
	}

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmPackage#getEXmlElementDecl_Type()
	 * @model
	 * @generated
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementDecl#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	public void setType(String newType)
	{
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OxmPackage.EXML_ELEMENT_DECL__TYPE, oldType, type));
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
			case OxmPackage.EXML_ELEMENT_DECL__JAVA_METHOD:
				return getJavaMethod();
			case OxmPackage.EXML_ELEMENT_DECL__NAME:
				return getName();
			case OxmPackage.EXML_ELEMENT_DECL__NAMESPACE:
				return getNamespace();
			case OxmPackage.EXML_ELEMENT_DECL__DEFAULT_VALUE:
				return getDefaultValue();
			case OxmPackage.EXML_ELEMENT_DECL__SCOPE:
				return getScope();
			case OxmPackage.EXML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAME:
				return getSubstitutionHeadName();
			case OxmPackage.EXML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAMESPACE:
				return getSubstitutionHeadNamespace();
			case OxmPackage.EXML_ELEMENT_DECL__TYPE:
				return getType();
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
			case OxmPackage.EXML_ELEMENT_DECL__JAVA_METHOD:
				setJavaMethod((String)newValue);
				return;
			case OxmPackage.EXML_ELEMENT_DECL__NAME:
				setName((String)newValue);
				return;
			case OxmPackage.EXML_ELEMENT_DECL__NAMESPACE:
				setNamespace((String)newValue);
				return;
			case OxmPackage.EXML_ELEMENT_DECL__DEFAULT_VALUE:
				setDefaultValue((String)newValue);
				return;
			case OxmPackage.EXML_ELEMENT_DECL__SCOPE:
				setScope((String)newValue);
				return;
			case OxmPackage.EXML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAME:
				setSubstitutionHeadName((String)newValue);
				return;
			case OxmPackage.EXML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAMESPACE:
				setSubstitutionHeadNamespace((String)newValue);
				return;
			case OxmPackage.EXML_ELEMENT_DECL__TYPE:
				setType((String)newValue);
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
			case OxmPackage.EXML_ELEMENT_DECL__JAVA_METHOD:
				setJavaMethod(JAVA_METHOD_EDEFAULT);
				return;
			case OxmPackage.EXML_ELEMENT_DECL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OxmPackage.EXML_ELEMENT_DECL__NAMESPACE:
				setNamespace(NAMESPACE_EDEFAULT);
				return;
			case OxmPackage.EXML_ELEMENT_DECL__DEFAULT_VALUE:
				setDefaultValue(DEFAULT_VALUE_EDEFAULT);
				return;
			case OxmPackage.EXML_ELEMENT_DECL__SCOPE:
				setScope(SCOPE_EDEFAULT);
				return;
			case OxmPackage.EXML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAME:
				setSubstitutionHeadName(SUBSTITUTION_HEAD_NAME_EDEFAULT);
				return;
			case OxmPackage.EXML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAMESPACE:
				setSubstitutionHeadNamespace(SUBSTITUTION_HEAD_NAMESPACE_EDEFAULT);
				return;
			case OxmPackage.EXML_ELEMENT_DECL__TYPE:
				setType(TYPE_EDEFAULT);
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
			case OxmPackage.EXML_ELEMENT_DECL__JAVA_METHOD:
				return JAVA_METHOD_EDEFAULT == null ? javaMethod != null : !JAVA_METHOD_EDEFAULT.equals(javaMethod);
			case OxmPackage.EXML_ELEMENT_DECL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OxmPackage.EXML_ELEMENT_DECL__NAMESPACE:
				return NAMESPACE_EDEFAULT == null ? namespace != null : !NAMESPACE_EDEFAULT.equals(namespace);
			case OxmPackage.EXML_ELEMENT_DECL__DEFAULT_VALUE:
				return DEFAULT_VALUE_EDEFAULT == null ? defaultValue != null : !DEFAULT_VALUE_EDEFAULT.equals(defaultValue);
			case OxmPackage.EXML_ELEMENT_DECL__SCOPE:
				return SCOPE_EDEFAULT == null ? scope != null : !SCOPE_EDEFAULT.equals(scope);
			case OxmPackage.EXML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAME:
				return SUBSTITUTION_HEAD_NAME_EDEFAULT == null ? substitutionHeadName != null : !SUBSTITUTION_HEAD_NAME_EDEFAULT.equals(substitutionHeadName);
			case OxmPackage.EXML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAMESPACE:
				return SUBSTITUTION_HEAD_NAMESPACE_EDEFAULT == null ? substitutionHeadNamespace != null : !SUBSTITUTION_HEAD_NAMESPACE_EDEFAULT.equals(substitutionHeadNamespace);
			case OxmPackage.EXML_ELEMENT_DECL__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
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
		result.append(" (javaMethod: ");
		result.append(javaMethod);
		result.append(", name: ");
		result.append(name);
		result.append(", namespace: ");
		result.append(namespace);
		result.append(", defaultValue: ");
		result.append(defaultValue);
		result.append(", scope: ");
		result.append(scope);
		result.append(", substitutionHeadName: ");
		result.append(substitutionHeadName);
		result.append(", substitutionHeadNamespace: ");
		result.append(substitutionHeadNamespace);
		result.append(", type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

} // EXmlElementDecl
