/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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

import org.eclipse.jpt.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.xml.AbstractJpaEObject;

import org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.BatchFetchType_2_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLink2_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlBatchFetch_2_1;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xm Batch Fetch</b></em>'.
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBatchFetch()
 * @model kind="class"
 * @generated
 */
public class XmlBatchFetch extends AbstractJpaEObject implements XmlBatchFetch_2_1
{
	/**
	 * The default value of the '{@link #getSize() <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected static final Integer SIZE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSize() <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected Integer size = SIZE_EDEFAULT;

	/**
	 * changed this to null and removed the generated flag so emf won't generate over it
	 * we don't want a default for enums, just null if the tag does not exist
	 */
	protected static final BatchFetchType_2_1 BATCH_FETCH_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBatchFetchType() <em>Batch Fetch Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBatchFetchType()
	 * @generated
	 * @ordered
	 */
	protected BatchFetchType_2_1 batchFetchType = BATCH_FETCH_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlBatchFetch()
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
		return EclipseLinkOrmPackage.Literals.XML_BATCH_FETCH;
	}

	/**
	 * Returns the value of the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Size</em>' attribute.
	 * @see #setSize(Integer)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBatchFetch_2_1_Size()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.IntObject"
	 * @generated
	 */
	public Integer getSize()
	{
		return size;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlBatchFetch#getSize <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' attribute.
	 * @see #getSize()
	 * @generated
	 */
	public void setSize(Integer newSize)
	{
		Integer oldSize = size;
		size = newSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_BATCH_FETCH__SIZE, oldSize, size));
	}

	/**
	 * Returns the value of the '<em><b>Batch Fetch Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.BatchFetchType_2_1}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Batch Fetch Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Batch Fetch Type</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.BatchFetchType_2_1
	 * @see #setBatchFetchType(BatchFetchType_2_1)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlBatchFetch_2_1_BatchFetchType()
	 * @model
	 * @generated
	 */
	public BatchFetchType_2_1 getBatchFetchType()
	{
		return batchFetchType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlBatchFetch#getBatchFetchType <em>Batch Fetch Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Batch Fetch Type</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.BatchFetchType_2_1
	 * @see #getBatchFetchType()
	 * @generated
	 */
	public void setBatchFetchType(BatchFetchType_2_1 newBatchFetchType)
	{
		BatchFetchType_2_1 oldBatchFetchType = batchFetchType;
		batchFetchType = newBatchFetchType == null ? BATCH_FETCH_TYPE_EDEFAULT : newBatchFetchType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EclipseLinkOrmPackage.XML_BATCH_FETCH__BATCH_FETCH_TYPE, oldBatchFetchType, batchFetchType));
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
			case EclipseLinkOrmPackage.XML_BATCH_FETCH__SIZE:
				return getSize();
			case EclipseLinkOrmPackage.XML_BATCH_FETCH__BATCH_FETCH_TYPE:
				return getBatchFetchType();
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
			case EclipseLinkOrmPackage.XML_BATCH_FETCH__SIZE:
				setSize((Integer)newValue);
				return;
			case EclipseLinkOrmPackage.XML_BATCH_FETCH__BATCH_FETCH_TYPE:
				setBatchFetchType((BatchFetchType_2_1)newValue);
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
			case EclipseLinkOrmPackage.XML_BATCH_FETCH__SIZE:
				setSize(SIZE_EDEFAULT);
				return;
			case EclipseLinkOrmPackage.XML_BATCH_FETCH__BATCH_FETCH_TYPE:
				setBatchFetchType(BATCH_FETCH_TYPE_EDEFAULT);
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
			case EclipseLinkOrmPackage.XML_BATCH_FETCH__SIZE:
				return SIZE_EDEFAULT == null ? size != null : !SIZE_EDEFAULT.equals(size);
			case EclipseLinkOrmPackage.XML_BATCH_FETCH__BATCH_FETCH_TYPE:
				return batchFetchType != BATCH_FETCH_TYPE_EDEFAULT;
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
		result.append(" (size: ");
		result.append(size);
		result.append(", batchFetchType: ");
		result.append(batchFetchType);
		result.append(')');
		return result.toString();
	}

	// ********** translators **********

	public static Translator buildTranslator(String elementName, EStructuralFeature structuralFeature) {
		return new SimpleTranslator(
			elementName,
			structuralFeature,
			Translator.END_TAG_NO_INDENT,
			buildTranslatorChildren()
		);
	}

	private static Translator[] buildTranslatorChildren() {
		return new Translator[] {
			buildSizeTranslator(),
			buildBatchFetchTypeTranslator(),
		};
	}
	
	protected static Translator buildSizeTranslator() {
		return new Translator(EclipseLink2_1.BATCH_FETCH__SIZE, EclipseLinkOrmV2_1Package.eINSTANCE.getXmlBatchFetch_2_1_Size(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildBatchFetchTypeTranslator() {
		return new Translator(EclipseLink2_1.BATCH_FETCH__TYPE, EclipseLinkOrmV2_1Package.eINSTANCE.getXmlBatchFetch_2_1_BatchFetchType(), Translator.DOM_ATTRIBUTE);
	}

} // XmBatchFetch
