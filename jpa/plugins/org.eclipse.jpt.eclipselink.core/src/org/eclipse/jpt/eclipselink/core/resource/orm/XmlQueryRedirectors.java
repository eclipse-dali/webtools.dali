/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.xml.AbstractJpaEObject;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.XmlQueryRedirectors_2_0;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Query Redirectors</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlQueryRedirectors()
 * @model kind="class"
 * @generated
 */
public class XmlQueryRedirectors extends AbstractJpaEObject implements XmlQueryRedirectors_2_0
{
	/**
	 * The default value of the '{@link #getAllQueries() <em>All Queries</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllQueries()
	 * @generated
	 * @ordered
	 */
	protected static final String ALL_QUERIES_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAllQueries() <em>All Queries</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllQueries()
	 * @generated
	 * @ordered
	 */
	protected String allQueries = ALL_QUERIES_EDEFAULT;

	/**
	 * The default value of the '{@link #getReadAll() <em>Read All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReadAll()
	 * @generated
	 * @ordered
	 */
	protected static final String READ_ALL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReadAll() <em>Read All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReadAll()
	 * @generated
	 * @ordered
	 */
	protected String readAll = READ_ALL_EDEFAULT;

	/**
	 * The default value of the '{@link #getReadObject() <em>Read Object</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReadObject()
	 * @generated
	 * @ordered
	 */
	protected static final String READ_OBJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReadObject() <em>Read Object</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReadObject()
	 * @generated
	 * @ordered
	 */
	protected String readObject = READ_OBJECT_EDEFAULT;

	/**
	 * The default value of the '{@link #getReport() <em>Report</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReport()
	 * @generated
	 * @ordered
	 */
	protected static final String REPORT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReport() <em>Report</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReport()
	 * @generated
	 * @ordered
	 */
	protected String report = REPORT_EDEFAULT;

	/**
	 * The default value of the '{@link #getUpdate() <em>Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdate()
	 * @generated
	 * @ordered
	 */
	protected static final String UPDATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUpdate() <em>Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdate()
	 * @generated
	 * @ordered
	 */
	protected String update = UPDATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getInsert() <em>Insert</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInsert()
	 * @generated
	 * @ordered
	 */
	protected static final String INSERT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInsert() <em>Insert</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInsert()
	 * @generated
	 * @ordered
	 */
	protected String insert = INSERT_EDEFAULT;

	/**
	 * The default value of the '{@link #getDelete() <em>Delete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelete()
	 * @generated
	 * @ordered
	 */
	protected static final String DELETE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDelete() <em>Delete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelete()
	 * @generated
	 * @ordered
	 */
	protected String delete = DELETE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlQueryRedirectors()
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
		return EclipseLinkOrmPackage.Literals.XML_QUERY_REDIRECTORS;
	}

	/**
	 * Returns the value of the '<em><b>All Queries</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Queries</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Queries</em>' attribute.
	 * @see #setAllQueries(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlQueryRedirectors_2_0_AllQueries()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getAllQueries()
	{
		return allQueries;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlQueryRedirectors#getAllQueries <em>All Queries</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>All Queries</em>' attribute.
	 * @see #getAllQueries()
	 * @generated
	 */
	public void setAllQueries(String newAllQueries)
	{
		String oldAllQueries = allQueries;
		allQueries = newAllQueries;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__ALL_QUERIES, oldAllQueries, allQueries));
	}

	/**
	 * Returns the value of the '<em><b>Read All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Read All</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Read All</em>' attribute.
	 * @see #setReadAll(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlQueryRedirectors_2_0_ReadAll()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getReadAll()
	{
		return readAll;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlQueryRedirectors#getReadAll <em>Read All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Read All</em>' attribute.
	 * @see #getReadAll()
	 * @generated
	 */
	public void setReadAll(String newReadAll)
	{
		String oldReadAll = readAll;
		readAll = newReadAll;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__READ_ALL, oldReadAll, readAll));
	}

	/**
	 * Returns the value of the '<em><b>Read Object</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Read Object</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Read Object</em>' attribute.
	 * @see #setReadObject(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlQueryRedirectors_2_0_ReadObject()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getReadObject()
	{
		return readObject;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlQueryRedirectors#getReadObject <em>Read Object</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Read Object</em>' attribute.
	 * @see #getReadObject()
	 * @generated
	 */
	public void setReadObject(String newReadObject)
	{
		String oldReadObject = readObject;
		readObject = newReadObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__READ_OBJECT, oldReadObject, readObject));
	}

	/**
	 * Returns the value of the '<em><b>Report</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Report</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Report</em>' attribute.
	 * @see #setReport(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlQueryRedirectors_2_0_Report()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getReport()
	{
		return report;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlQueryRedirectors#getReport <em>Report</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Report</em>' attribute.
	 * @see #getReport()
	 * @generated
	 */
	public void setReport(String newReport)
	{
		String oldReport = report;
		report = newReport;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__REPORT, oldReport, report));
	}

	/**
	 * Returns the value of the '<em><b>Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Update</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Update</em>' attribute.
	 * @see #setUpdate(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlQueryRedirectors_2_0_Update()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getUpdate()
	{
		return update;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlQueryRedirectors#getUpdate <em>Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Update</em>' attribute.
	 * @see #getUpdate()
	 * @generated
	 */
	public void setUpdate(String newUpdate)
	{
		String oldUpdate = update;
		update = newUpdate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__UPDATE, oldUpdate, update));
	}

	/**
	 * Returns the value of the '<em><b>Insert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Insert</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Insert</em>' attribute.
	 * @see #setInsert(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlQueryRedirectors_2_0_Insert()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getInsert()
	{
		return insert;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlQueryRedirectors#getInsert <em>Insert</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Insert</em>' attribute.
	 * @see #getInsert()
	 * @generated
	 */
	public void setInsert(String newInsert)
	{
		String oldInsert = insert;
		insert = newInsert;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__INSERT, oldInsert, insert));
	}

	/**
	 * Returns the value of the '<em><b>Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delete</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delete</em>' attribute.
	 * @see #setDelete(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlQueryRedirectors_2_0_Delete()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getDelete()
	{
		return delete;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlQueryRedirectors#getDelete <em>Delete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete</em>' attribute.
	 * @see #getDelete()
	 * @generated
	 */
	public void setDelete(String newDelete)
	{
		String oldDelete = delete;
		delete = newDelete;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__DELETE, oldDelete, delete));
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
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__ALL_QUERIES:
				return getAllQueries();
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__READ_ALL:
				return getReadAll();
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__READ_OBJECT:
				return getReadObject();
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__REPORT:
				return getReport();
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__UPDATE:
				return getUpdate();
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__INSERT:
				return getInsert();
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__DELETE:
				return getDelete();
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
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__ALL_QUERIES:
				setAllQueries((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__READ_ALL:
				setReadAll((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__READ_OBJECT:
				setReadObject((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__REPORT:
				setReport((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__UPDATE:
				setUpdate((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__INSERT:
				setInsert((String)newValue);
				return;
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__DELETE:
				setDelete((String)newValue);
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
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__ALL_QUERIES:
				setAllQueries(ALL_QUERIES_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__READ_ALL:
				setReadAll(READ_ALL_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__READ_OBJECT:
				setReadObject(READ_OBJECT_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__REPORT:
				setReport(REPORT_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__UPDATE:
				setUpdate(UPDATE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__INSERT:
				setInsert(INSERT_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__DELETE:
				setDelete(DELETE_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__ALL_QUERIES:
				return ALL_QUERIES_EDEFAULT == null ? allQueries != null : !ALL_QUERIES_EDEFAULT.equals(allQueries);
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__READ_ALL:
				return READ_ALL_EDEFAULT == null ? readAll != null : !READ_ALL_EDEFAULT.equals(readAll);
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__READ_OBJECT:
				return READ_OBJECT_EDEFAULT == null ? readObject != null : !READ_OBJECT_EDEFAULT.equals(readObject);
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__REPORT:
				return REPORT_EDEFAULT == null ? report != null : !REPORT_EDEFAULT.equals(report);
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__UPDATE:
				return UPDATE_EDEFAULT == null ? update != null : !UPDATE_EDEFAULT.equals(update);
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__INSERT:
				return INSERT_EDEFAULT == null ? insert != null : !INSERT_EDEFAULT.equals(insert);
			case EclipseLinkOrmPackage.XML_QUERY_REDIRECTORS__DELETE:
				return DELETE_EDEFAULT == null ? delete != null : !DELETE_EDEFAULT.equals(delete);
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
		result.append(" (allQueries: ");
		result.append(allQueries);
		result.append(", readAll: ");
		result.append(readAll);
		result.append(", readObject: ");
		result.append(readObject);
		result.append(", report: ");
		result.append(report);
		result.append(", update: ");
		result.append(update);
		result.append(", insert: ");
		result.append(insert);
		result.append(", delete: ");
		result.append(delete);
		result.append(')');
		return result.toString();
	}
	
	
	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName,
			structuralFeature,
			Translator.END_TAG_NO_INDENT,
			EclipseLinkOrmPackage.eINSTANCE.getXmlQueryRedirectors(),
			buildTranslatorChildren()
		);
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildAllQueriesTranslator(),
			buildReadAllTranslator(),
			buildReadObjectTranslator(),
			buildReportTranslator(),
			buildUpdateTranslator(),
			buildInsertTranslator(),
			buildDeleteTranslator(),
		};
	}
	
	protected static Translator buildAllQueriesTranslator() {
		return new Translator(EclipseLink2_0.QUERY_REDIRECTORS__ALL_QUERIES, EclipseLinkOrmV2_0Package.eINSTANCE.getXmlQueryRedirectors_2_0_AllQueries(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildReadAllTranslator() {
		return new Translator(EclipseLink2_0.QUERY_REDIRECTORS__READ_ALL, EclipseLinkOrmV2_0Package.eINSTANCE.getXmlQueryRedirectors_2_0_ReadAll(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildReadObjectTranslator() {
		return new Translator(EclipseLink2_0.QUERY_REDIRECTORS__READ_OBJECT, EclipseLinkOrmV2_0Package.eINSTANCE.getXmlQueryRedirectors_2_0_ReadObject(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildReportTranslator() {
		return new Translator(EclipseLink2_0.QUERY_REDIRECTORS__REPORT, EclipseLinkOrmV2_0Package.eINSTANCE.getXmlQueryRedirectors_2_0_AllQueries(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildUpdateTranslator() {
		return new Translator(EclipseLink2_0.QUERY_REDIRECTORS__UPDATE, EclipseLinkOrmV2_0Package.eINSTANCE.getXmlQueryRedirectors_2_0_AllQueries(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildInsertTranslator() {
		return new Translator(EclipseLink2_0.QUERY_REDIRECTORS__INSERT, EclipseLinkOrmV2_0Package.eINSTANCE.getXmlQueryRedirectors_2_0_AllQueries(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildDeleteTranslator() {
		return new Translator(EclipseLink2_0.QUERY_REDIRECTORS__DELETE, EclipseLinkOrmV2_0Package.eINSTANCE.getXmlQueryRedirectors_2_0_AllQueries(), Translator.DOM_ATTRIBUTE);
	}
} // XmlQueryRedirectors
