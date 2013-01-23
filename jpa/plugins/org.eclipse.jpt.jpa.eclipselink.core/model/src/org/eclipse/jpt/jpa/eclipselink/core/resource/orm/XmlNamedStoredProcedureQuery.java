/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.internal.utility.translators.BooleanTranslator;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.jpa.core.resource.orm.OrmPackage;
import org.eclipse.jpt.jpa.core.resource.orm.XmlQueryHint;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.OrmV2_1Package;
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
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getReturnsResultSet <em>Returns Result Set</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getMultipleResultSets <em>Multiple Result Sets</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getCallByIndex <em>Call By Index</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedStoredProcedureQuery()
 * @model kind="class"
 * @generated
 */
public class XmlNamedStoredProcedureQuery extends org.eclipse.jpt.jpa.core.resource.orm.XmlNamedStoredProcedureQuery
{
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
	 * The default value of the '{@link #getMultipleResultSets() <em>Multiple Result Sets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultipleResultSets()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean MULTIPLE_RESULT_SETS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMultipleResultSets() <em>Multiple Result Sets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultipleResultSets()
	 * @generated
	 * @ordered
	 */
	protected Boolean multipleResultSets = MULTIPLE_RESULT_SETS_EDEFAULT;

	/**
	 * The default value of the '{@link #getCallByIndex() <em>Call By Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCallByIndex()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean CALL_BY_INDEX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCallByIndex() <em>Call By Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCallByIndex()
	 * @generated
	 * @ordered
	 */
	protected Boolean callByIndex = CALL_BY_INDEX_EDEFAULT;

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
	 * Returns the value of the '<em><b>Returns Result Set</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Returns Result Set</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Returns Result Set</em>' attribute.
	 * @see #setReturnsResultSet(Boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedStoredProcedureQuery_ReturnsResultSet()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getReturnsResultSet()
	{
		return returnsResultSet;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getReturnsResultSet <em>Returns Result Set</em>}' attribute.
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
	 * Returns the value of the '<em><b>Multiple Result Sets</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multiple Result Sets</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multiple Result Sets</em>' attribute.
	 * @see #setMultipleResultSets(Boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedStoredProcedureQuery_MultipleResultSets()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getMultipleResultSets()
	{
		return multipleResultSets;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getMultipleResultSets <em>Multiple Result Sets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multiple Result Sets</em>' attribute.
	 * @see #getMultipleResultSets()
	 * @generated
	 */
	public void setMultipleResultSets(Boolean newMultipleResultSets)
	{
		Boolean oldMultipleResultSets = multipleResultSets;
		multipleResultSets = newMultipleResultSets;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__MULTIPLE_RESULT_SETS, oldMultipleResultSets, multipleResultSets));
	}

	/**
	 * Returns the value of the '<em><b>Call By Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Call By Index</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Call By Index</em>' attribute.
	 * @see #setCallByIndex(Boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlNamedStoredProcedureQuery_CallByIndex()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getCallByIndex()
	{
		return callByIndex;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedStoredProcedureQuery#getCallByIndex <em>Call By Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Call By Index</em>' attribute.
	 * @see #getCallByIndex()
	 * @generated
	 */
	public void setCallByIndex(Boolean newCallByIndex)
	{
		Boolean oldCallByIndex = callByIndex;
		callByIndex = newCallByIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__CALL_BY_INDEX, oldCallByIndex, callByIndex));
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
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET:
				return getReturnsResultSet();
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__MULTIPLE_RESULT_SETS:
				return getMultipleResultSets();
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__CALL_BY_INDEX:
				return getCallByIndex();
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
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET:
				setReturnsResultSet((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__MULTIPLE_RESULT_SETS:
				setMultipleResultSets((Boolean)newValue);
				return;
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__CALL_BY_INDEX:
				setCallByIndex((Boolean)newValue);
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
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET:
				setReturnsResultSet(RETURNS_RESULT_SET_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__MULTIPLE_RESULT_SETS:
				setMultipleResultSets(MULTIPLE_RESULT_SETS_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__CALL_BY_INDEX:
				setCallByIndex(CALL_BY_INDEX_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET:
				return RETURNS_RESULT_SET_EDEFAULT == null ? returnsResultSet != null : !RETURNS_RESULT_SET_EDEFAULT.equals(returnsResultSet);
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__MULTIPLE_RESULT_SETS:
				return MULTIPLE_RESULT_SETS_EDEFAULT == null ? multipleResultSets != null : !MULTIPLE_RESULT_SETS_EDEFAULT.equals(multipleResultSets);
			case EclipseLinkOrmPackage.XML_NAMED_STORED_PROCEDURE_QUERY__CALL_BY_INDEX:
				return CALL_BY_INDEX_EDEFAULT == null ? callByIndex != null : !CALL_BY_INDEX_EDEFAULT.equals(callByIndex);
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
		result.append(" (returnsResultSet: ");
		result.append(returnsResultSet);
		result.append(", multipleResultSets: ");
		result.append(multipleResultSets);
		result.append(", callByIndex: ");
		result.append(callByIndex);
		result.append(')');
		return result.toString();
	}

	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName,
			structuralFeature,
			EclipseLinkOrmPackage.eINSTANCE.getXmlNamedStoredProcedureQuery(),
			buildTranslatorChildren()
		);
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildNameTranslator(),
			buildResultClassTranslator(),
			buildResultSetMappingTranslator(),
			buildProcedureNameTranslator(),
			buildReturnResultSetTranslator(),
			buildHintTranslator(),
//TODO			buildParameterTranslator(),
		};
	}

	protected static Translator buildNameTranslator() {
		return new Translator(EclipseLink.NAMED_STORED_PROCEDURE_QUERY__NAME, OrmPackage.eINSTANCE.getXmlQuery_Name(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildResultClassTranslator() {
		return new Translator(EclipseLink.NAMED_STORED_PROCEDURE_QUERY__RESULT_CLASS, OrmV2_1Package.eINSTANCE.getXmlNamedStoredProcedureQuery_2_1_ResultClasses(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildResultSetMappingTranslator() {
		return new Translator(EclipseLink.NAMED_STORED_PROCEDURE_QUERY__RESULT_SET_MAPPING, OrmV2_1Package.eINSTANCE.getXmlNamedStoredProcedureQuery_2_1_ResultSetMappings(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildProcedureNameTranslator() {
		return new Translator(EclipseLink.NAMED_STORED_PROCEDURE_QUERY__PROCEDURE_NAME, OrmV2_1Package.eINSTANCE.getXmlNamedStoredProcedureQuery_2_1_ProcedureName(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildReturnResultSetTranslator() {
		return new BooleanTranslator(EclipseLink.NAMED_STORED_PROCEDURE_QUERY__RETURNS_RESULT_SET, EclipseLinkOrmPackage.eINSTANCE.getXmlNamedStoredProcedureQuery_ReturnsResultSet(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildHintTranslator() {
		return XmlQueryHint.buildTranslator(EclipseLink.NAMED_STORED_PROCEDURE_QUERY__HINT, OrmPackage.eINSTANCE.getXmlQuery_Hints());
	}
	
	protected static Translator buildParameterTranslator() {
		return XmlStoredProcedureParameter.buildTranslator(EclipseLink.NAMED_STORED_PROCEDURE_QUERY__PARAMETER, OrmV2_1Package.eINSTANCE.getXmlNamedStoredProcedureQuery_2_1_Parameters());
	}
} // XmlNamedStoredProcedureQuery
