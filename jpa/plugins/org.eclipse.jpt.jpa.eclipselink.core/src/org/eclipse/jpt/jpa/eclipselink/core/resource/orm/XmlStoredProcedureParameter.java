/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.jpa.core.resource.xml.AbstractJpaEObject;
import org.eclipse.jpt.jpa.core.resource.xml.JpaEObject;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Stored Procedure Parameter</b></em>'.
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.2
 *
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getDirection <em>Direction</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getQueryParameter <em>Query Parameter</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getJdbcType <em>Jdbc Type</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getJdbcTypeName <em>Jdbc Type Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStoredProcedureParameter()
 * @model kind="class"
 * @extends JpaEObject
 * @generated
 */
public class XmlStoredProcedureParameter extends AbstractJpaEObject implements JpaEObject
{
	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final XmlDirection DIRECTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected XmlDirection direction = DIRECTION_EDEFAULT;

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
	 * The default value of the '{@link #getQueryParameter() <em>Query Parameter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueryParameter()
	 * @generated
	 * @ordered
	 */
	protected static final String QUERY_PARAMETER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQueryParameter() <em>Query Parameter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueryParameter()
	 * @generated
	 * @ordered
	 */
	protected String queryParameter = QUERY_PARAMETER_EDEFAULT;

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
	 * The default value of the '{@link #getJdbcType() <em>Jdbc Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJdbcType()
	 * @generated
	 * @ordered
	 */
	protected static final Integer JDBC_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJdbcType() <em>Jdbc Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJdbcType()
	 * @generated
	 * @ordered
	 */
	protected Integer jdbcType = JDBC_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getJdbcTypeName() <em>Jdbc Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJdbcTypeName()
	 * @generated
	 * @ordered
	 */
	protected static final String JDBC_TYPE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJdbcTypeName() <em>Jdbc Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJdbcTypeName()
	 * @generated
	 * @ordered
	 */
	protected String jdbcTypeName = JDBC_TYPE_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlStoredProcedureParameter()
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
		return EclipseLinkOrmPackage.Literals.XML_STORED_PROCEDURE_PARAMETER;
	}

	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlDirection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlDirection
	 * @see #setDirection(XmlDirection)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStoredProcedureParameter_Direction()
	 * @model
	 * @generated
	 */
	public XmlDirection getDirection()
	{
		return direction;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlDirection
	 * @see #getDirection()
	 * @generated
	 */
	public void setDirection(XmlDirection newDirection)
	{
		XmlDirection oldDirection = direction;
		direction = newDirection == null ? DIRECTION_EDEFAULT : newDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__DIRECTION, oldDirection, direction));
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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStoredProcedureParameter_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Query Parameter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Query Parameter</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Query Parameter</em>' attribute.
	 * @see #setQueryParameter(String)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStoredProcedureParameter_QueryParameter()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getQueryParameter()
	{
		return queryParameter;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getQueryParameter <em>Query Parameter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Query Parameter</em>' attribute.
	 * @see #getQueryParameter()
	 * @generated
	 */
	public void setQueryParameter(String newQueryParameter)
	{
		String oldQueryParameter = queryParameter;
		queryParameter = newQueryParameter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__QUERY_PARAMETER, oldQueryParameter, queryParameter));
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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStoredProcedureParameter_Type()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getType <em>Type</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__TYPE, oldType, type));
	}

	/**
	 * Returns the value of the '<em><b>Jdbc Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jdbc Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jdbc Type</em>' attribute.
	 * @see #setJdbcType(Integer)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStoredProcedureParameter_JdbcType()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.IntObject"
	 * @generated
	 */
	public Integer getJdbcType()
	{
		return jdbcType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getJdbcType <em>Jdbc Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jdbc Type</em>' attribute.
	 * @see #getJdbcType()
	 * @generated
	 */
	public void setJdbcType(Integer newJdbcType)
	{
		Integer oldJdbcType = jdbcType;
		jdbcType = newJdbcType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__JDBC_TYPE, oldJdbcType, jdbcType));
	}

	/**
	 * Returns the value of the '<em><b>Jdbc Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jdbc Type Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jdbc Type Name</em>' attribute.
	 * @see #setJdbcTypeName(String)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlStoredProcedureParameter_JdbcTypeName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getJdbcTypeName()
	{
		return jdbcTypeName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStoredProcedureParameter#getJdbcTypeName <em>Jdbc Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jdbc Type Name</em>' attribute.
	 * @see #getJdbcTypeName()
	 * @generated
	 */
	public void setJdbcTypeName(String newJdbcTypeName)
	{
		String oldJdbcTypeName = jdbcTypeName;
		jdbcTypeName = newJdbcTypeName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__JDBC_TYPE_NAME, oldJdbcTypeName, jdbcTypeName));
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
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__DIRECTION:
				return getDirection();
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__NAME:
				return getName();
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__QUERY_PARAMETER:
				return getQueryParameter();
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__TYPE:
				return getType();
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__JDBC_TYPE:
				return getJdbcType();
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__JDBC_TYPE_NAME:
				return getJdbcTypeName();
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
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__DIRECTION:
				setDirection((XmlDirection)newValue);
				return;
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__NAME:
				setName((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__QUERY_PARAMETER:
				setQueryParameter((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__TYPE:
				setType((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__JDBC_TYPE:
				setJdbcType((Integer)newValue);
				return;
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__JDBC_TYPE_NAME:
				setJdbcTypeName((String)newValue);
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
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__DIRECTION:
				setDirection(DIRECTION_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__QUERY_PARAMETER:
				setQueryParameter(QUERY_PARAMETER_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__JDBC_TYPE:
				setJdbcType(JDBC_TYPE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__JDBC_TYPE_NAME:
				setJdbcTypeName(JDBC_TYPE_NAME_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__DIRECTION:
				return direction != DIRECTION_EDEFAULT;
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__QUERY_PARAMETER:
				return QUERY_PARAMETER_EDEFAULT == null ? queryParameter != null : !QUERY_PARAMETER_EDEFAULT.equals(queryParameter);
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__JDBC_TYPE:
				return JDBC_TYPE_EDEFAULT == null ? jdbcType != null : !JDBC_TYPE_EDEFAULT.equals(jdbcType);
			case EclipseLinkOrmPackage.XML_STORED_PROCEDURE_PARAMETER__JDBC_TYPE_NAME:
				return JDBC_TYPE_NAME_EDEFAULT == null ? jdbcTypeName != null : !JDBC_TYPE_NAME_EDEFAULT.equals(jdbcTypeName);
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
		result.append(" (direction: ");
		result.append(direction);
		result.append(", name: ");
		result.append(name);
		result.append(", queryParameter: ");
		result.append(queryParameter);
		result.append(", type: ");
		result.append(type);
		result.append(", jdbcType: ");
		result.append(jdbcType);
		result.append(", jdbcTypeName: ");
		result.append(jdbcTypeName);
		result.append(')');
		return result.toString();
	}
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildDirectionTranslator(),
			buildNameTranslator(),
			buildQueryParameterTranslator(),
			buildTypeTranslator(),
			buildJdbcTypeTranslator(),
			buildJdbcTypeNameTranslator(),
		};
	}
	
	protected static Translator buildDirectionTranslator() {
		return new Translator(EclipseLink.PARAMETER__DIRECTION, EclipseLinkOrmPackage.eINSTANCE.getXmlStoredProcedureParameter_Direction(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildNameTranslator() {
		return new Translator(EclipseLink.PARAMETER__NAME, EclipseLinkOrmPackage.eINSTANCE.getXmlStoredProcedureParameter_Name(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildQueryParameterTranslator() {
		return new Translator(EclipseLink.PARAMETER__QUERY_PARAMETER, EclipseLinkOrmPackage.eINSTANCE.getXmlStoredProcedureParameter_QueryParameter(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildTypeTranslator() {
		return new Translator(EclipseLink.PARAMETER__TYPE, EclipseLinkOrmPackage.eINSTANCE.getXmlStoredProcedureParameter_Type(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildJdbcTypeTranslator() {
		return new Translator(EclipseLink.PARAMETER__JDBC_TYPE, EclipseLinkOrmPackage.eINSTANCE.getXmlStoredProcedureParameter_JdbcType(), Translator.DOM_ATTRIBUTE);
	}

	protected static Translator buildJdbcTypeNameTranslator() {
		return new Translator(EclipseLink.PARAMETER__JDBC_TYPE_NAME, EclipseLinkOrmPackage.eINSTANCE.getXmlStoredProcedureParameter_JdbcTypeName(), Translator.DOM_ATTRIBUTE);
	}
	
} // XmlStoredProcedureParameter
