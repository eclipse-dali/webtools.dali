/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.eclipselink.core.resource.orm;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jpt.core.internal.utility.translators.BooleanTranslator;
import org.eclipse.jpt.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.XmlQueryHint;
import org.eclipse.jpt.core.resource.xml.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.xml.JpaEObject;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Named Stored Procedure Query</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getResultClass <em>Result Class</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getResultSetMapping <em>Result Set Mapping</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getProcedureName <em>Procedure Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getReturnsResultSet <em>Returns Result Set</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getHints <em>Hints</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedStoredProcedureQuery()
 * @model kind="class"
 * @extends JpaEObject
 * @generated
 */
public class XmlNamedStoredProcedureQuery extends AbstractJpaEObject implements JpaEObject
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
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getResultClass() <em>Result Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultClass()
	 * @generated
	 * @ordered
	 */
	protected static final String RESULT_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResultClass() <em>Result Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultClass()
	 * @generated
	 * @ordered
	 */
	protected String resultClass = RESULT_CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getResultSetMapping() <em>Result Set Mapping</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultSetMapping()
	 * @generated
	 * @ordered
	 */
	protected static final String RESULT_SET_MAPPING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResultSetMapping() <em>Result Set Mapping</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultSetMapping()
	 * @generated
	 * @ordered
	 */
	protected String resultSetMapping = RESULT_SET_MAPPING_EDEFAULT;

	/**
	 * The default value of the '{@link #getProcedureName() <em>Procedure Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcedureName()
	 * @generated
	 * @ordered
	 */
	protected static final String PROCEDURE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProcedureName() <em>Procedure Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcedureName()
	 * @generated
	 * @ordered
	 */
	protected String procedureName = PROCEDURE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getReturnsResultSet() <em>Returns Result Set</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnsResultSet()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean RETURNS_RESULT_SET_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReturnsResultSet() <em>Returns Result Set</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnsResultSet()
	 * @generated
	 * @ordered
	 */
	protected Boolean returnsResultSet = RETURNS_RESULT_SET_EDEFAULT;

	/**
	 * The cached value of the '{@link #getHints() <em>Hints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHints()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlQueryHint> hints;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<XmlStoredProcedureParameter> parameters;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlNamedStoredProcedureQuery()
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
		return EclipseLinkOrmPackage.Literals.XML_NAMED_STORED_PROCEDURE_QUERY;
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
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedStoredProcedureQuery_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getName <em>Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__NAME, oldName, name));
	}

	/**
	 * Returns the value of the '<em><b>Result Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result Class</em>' attribute.
	 * @see #setResultClass(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedStoredProcedureQuery_ResultClass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getResultClass()
	{
		return resultClass;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getResultClass <em>Result Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result Class</em>' attribute.
	 * @see #getResultClass()
	 * @generated
	 */
	public void setResultClass(String newResultClass)
	{
		String oldResultClass = resultClass;
		resultClass = newResultClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASS, oldResultClass, resultClass));
	}

	/**
	 * Returns the value of the '<em><b>Result Set Mapping</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result Set Mapping</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result Set Mapping</em>' attribute.
	 * @see #setResultSetMapping(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedStoredProcedureQuery_ResultSetMapping()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getResultSetMapping()
	{
		return resultSetMapping;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getResultSetMapping <em>Result Set Mapping</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result Set Mapping</em>' attribute.
	 * @see #getResultSetMapping()
	 * @generated
	 */
	public void setResultSetMapping(String newResultSetMapping)
	{
		String oldResultSetMapping = resultSetMapping;
		resultSetMapping = newResultSetMapping;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPING, oldResultSetMapping, resultSetMapping));
	}

	/**
	 * Returns the value of the '<em><b>Procedure Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Procedure Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Procedure Name</em>' attribute.
	 * @see #setProcedureName(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedStoredProcedureQuery_ProcedureName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getProcedureName()
	{
		return procedureName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getProcedureName <em>Procedure Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Procedure Name</em>' attribute.
	 * @see #getProcedureName()
	 * @generated
	 */
	public void setProcedureName(String newProcedureName)
	{
		String oldProcedureName = procedureName;
		procedureName = newProcedureName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME, oldProcedureName, procedureName));
	}

	/**
	 * Returns the value of the '<em><b>Returns Result Set</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Returns Result Set</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Returns Result Set</em>' attribute.
	 * @see #setReturnsResultSet(Boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedStoredProcedureQuery_ReturnsResultSet()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getReturnsResultSet()
	{
		return returnsResultSet;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getReturnsResultSet <em>Returns Result Set</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Returns Result Set</em>' attribute.
	 * @see #getReturnsResultSet()
	 * @generated
	 */
	public void setReturnsResultSet(Boolean newReturnsResultSet)
	{
		Boolean oldReturnsResultSet = returnsResultSet;
		returnsResultSet = newReturnsResultSet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET, oldReturnsResultSet, returnsResultSet));
	}

	/**
	 * Returns the value of the '<em><b>Hints</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlQueryHint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hints</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedStoredProcedureQuery_Hints()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlQueryHint> getHints()
	{
		if (hints == null)
		{
			hints = new EObjectContainmentEList<XmlQueryHint>(XmlQueryHint.class, this, EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__HINTS);
		}
		return hints;
	}

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlStoredProcedureParameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedStoredProcedureQuery_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	public EList<XmlStoredProcedureParameter> getParameters()
	{
		if (parameters == null)
		{
			parameters = new EObjectContainmentEList<XmlStoredProcedureParameter>(XmlStoredProcedureParameter.class, this, EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PARAMETERS);
		}
		return parameters;
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
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__HINTS:
				return ((InternalEList<?>)getHints()).basicRemove(otherEnd, msgs);
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
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
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__NAME:
				return getName();
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASS:
				return getResultClass();
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPING:
				return getResultSetMapping();
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME:
				return getProcedureName();
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET:
				return getReturnsResultSet();
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__HINTS:
				return getHints();
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PARAMETERS:
				return getParameters();
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
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__NAME:
				setName((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASS:
				setResultClass((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPING:
				setResultSetMapping((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME:
				setProcedureName((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET:
				setReturnsResultSet((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__HINTS:
				getHints().clear();
				getHints().addAll((Collection<? extends XmlQueryHint>)newValue);
				return;
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends XmlStoredProcedureParameter>)newValue);
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
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASS:
				setResultClass(RESULT_CLASS_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPING:
				setResultSetMapping(RESULT_SET_MAPPING_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME:
				setProcedureName(PROCEDURE_NAME_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET:
				setReturnsResultSet(RETURNS_RESULT_SET_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__HINTS:
				getHints().clear();
				return;
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PARAMETERS:
				getParameters().clear();
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
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASS:
				return RESULT_CLASS_EDEFAULT == null ? resultClass != null : !RESULT_CLASS_EDEFAULT.equals(resultClass);
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPING:
				return RESULT_SET_MAPPING_EDEFAULT == null ? resultSetMapping != null : !RESULT_SET_MAPPING_EDEFAULT.equals(resultSetMapping);
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME:
				return PROCEDURE_NAME_EDEFAULT == null ? procedureName != null : !PROCEDURE_NAME_EDEFAULT.equals(procedureName);
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET:
				return RETURNS_RESULT_SET_EDEFAULT == null ? returnsResultSet != null : !RETURNS_RESULT_SET_EDEFAULT.equals(returnsResultSet);
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__HINTS:
				return hints != null && !hints.isEmpty();
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", resultClass: ");
		result.append(resultClass);
		result.append(", resultSetMapping: ");
		result.append(resultSetMapping);
		result.append(", procedureName: ");
		result.append(procedureName);
		result.append(", returnsResultSet: ");
		result.append(returnsResultSet);
		result.append(')');
		return result.toString();
	}

	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(elementName, structuralFeature, buildTranslatorChildren());
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildResultClassTranslator(),
			buildResultSetMappingTranslator(),
			buildProcedureNameTranslator(),
			buildReturnResultSetTranslator(),
			buildHintTranslator(),
			buildParameterTranslator(),
		};
	}

	protected static Translator buildNameTranslator() {
		return new Translator(EclipseLink.NAMED_STORED_PROCEDURE_QUERY__NAME, EclipseLinkOrmPackage.eINSTANCE.getXmlNamedStoredProcedureQuery_Name(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildResultClassTranslator() {
		return new Translator(EclipseLink.NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASS, EclipseLinkOrmPackage.eINSTANCE.getXmlNamedStoredProcedureQuery_ResultClass(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildResultSetMappingTranslator() {
		return new Translator(EclipseLink.NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPING, EclipseLinkOrmPackage.eINSTANCE.getXmlNamedStoredProcedureQuery_ResultSetMapping(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildProcedureNameTranslator() {
		return new Translator(EclipseLink.NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME, EclipseLinkOrmPackage.eINSTANCE.getXmlNamedStoredProcedureQuery_ProcedureName(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildReturnResultSetTranslator() {
		return new BooleanTranslator(EclipseLink.NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET, EclipseLinkOrmPackage.eINSTANCE.getXmlNamedStoredProcedureQuery_ReturnsResultSet(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildHintTranslator() {
		return XmlQueryHint.buildTranslator(EclipseLink.NAMED_STORED_PROCEDURE_QUERY__HINT, EclipseLinkOrmPackage.eINSTANCE.getXmlNamedStoredProcedureQuery_Hints());
	}
	
	protected static Translator buildParameterTranslator() {
		return XmlStoredProcedureParameter.buildTranslator(EclipseLink.NAMED_STORED_PROCEDURE_QUERY__PARAMETER, EclipseLinkOrmPackage.eINSTANCE.getXmlNamedStoredProcedureQuery_Parameters());
	}
} // XmlNamedStoredProcedureQuery
