/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.internal.utility.translators.BooleanTranslator;
import org.eclipse.jpt.common.core.internal.utility.translators.SimpleTranslator;
import org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlOrderColumn_2_0;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Order Column</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlOrderColumn()
 * @model kind="class"
 * @generated
 */
public class XmlOrderColumn extends AbstractXmlNamedColumn implements XmlOrderColumn_2_0
{
	/**
	 * The default value of the '{@link #getNullable() <em>Nullable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNullable()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean NULLABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNullable() <em>Nullable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNullable()
	 * @generated
	 * @ordered
	 */
	protected Boolean nullable = NULLABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getInsertable() <em>Insertable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInsertable()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean INSERTABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInsertable() <em>Insertable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInsertable()
	 * @generated
	 * @ordered
	 */
	protected Boolean insertable = INSERTABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getUpdatable() <em>Updatable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdatable()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean UPDATABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUpdatable() <em>Updatable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdatable()
	 * @generated
	 * @ordered
	 */
	protected Boolean updatable = UPDATABLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlOrderColumn()
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
		return OrmPackage.Literals.XML_ORDER_COLUMN;
	}

	/**
	 * Returns the value of the '<em><b>Nullable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nullable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nullable</em>' attribute.
	 * @see #setNullable(Boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlOrderColumn_2_0_Nullable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getNullable()
	{
		return nullable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlOrderColumn#getNullable <em>Nullable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nullable</em>' attribute.
	 * @see #getNullable()
	 * @generated
	 */
	public void setNullable(Boolean newNullable)
	{
		Boolean oldNullable = nullable;
		nullable = newNullable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ORDER_COLUMN__NULLABLE, oldNullable, nullable));
	}

	/**
	 * Returns the value of the '<em><b>Insertable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Insertable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Insertable</em>' attribute.
	 * @see #setInsertable(Boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlOrderColumn_2_0_Insertable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getInsertable()
	{
		return insertable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlOrderColumn#getInsertable <em>Insertable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Insertable</em>' attribute.
	 * @see #getInsertable()
	 * @generated
	 */
	public void setInsertable(Boolean newInsertable)
	{
		Boolean oldInsertable = insertable;
		insertable = newInsertable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ORDER_COLUMN__INSERTABLE, oldInsertable, insertable));
	}

	/**
	 * Returns the value of the '<em><b>Updatable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Updatable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Updatable</em>' attribute.
	 * @see #setUpdatable(Boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlOrderColumn_2_0_Updatable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getUpdatable()
	{
		return updatable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlOrderColumn#getUpdatable <em>Updatable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Updatable</em>' attribute.
	 * @see #getUpdatable()
	 * @generated
	 */
	public void setUpdatable(Boolean newUpdatable)
	{
		Boolean oldUpdatable = updatable;
		updatable = newUpdatable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ORDER_COLUMN__UPDATABLE, oldUpdatable, updatable));
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
			case OrmPackage.XML_ORDER_COLUMN__NULLABLE:
				return getNullable();
			case OrmPackage.XML_ORDER_COLUMN__INSERTABLE:
				return getInsertable();
			case OrmPackage.XML_ORDER_COLUMN__UPDATABLE:
				return getUpdatable();
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
			case OrmPackage.XML_ORDER_COLUMN__NULLABLE:
				setNullable((Boolean)newValue);
				return;
			case OrmPackage.XML_ORDER_COLUMN__INSERTABLE:
				setInsertable((Boolean)newValue);
				return;
			case OrmPackage.XML_ORDER_COLUMN__UPDATABLE:
				setUpdatable((Boolean)newValue);
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
			case OrmPackage.XML_ORDER_COLUMN__NULLABLE:
				setNullable(NULLABLE_EDEFAULT);
				return;
			case OrmPackage.XML_ORDER_COLUMN__INSERTABLE:
				setInsertable(INSERTABLE_EDEFAULT);
				return;
			case OrmPackage.XML_ORDER_COLUMN__UPDATABLE:
				setUpdatable(UPDATABLE_EDEFAULT);
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
			case OrmPackage.XML_ORDER_COLUMN__NULLABLE:
				return NULLABLE_EDEFAULT == null ? nullable != null : !NULLABLE_EDEFAULT.equals(nullable);
			case OrmPackage.XML_ORDER_COLUMN__INSERTABLE:
				return INSERTABLE_EDEFAULT == null ? insertable != null : !INSERTABLE_EDEFAULT.equals(insertable);
			case OrmPackage.XML_ORDER_COLUMN__UPDATABLE:
				return UPDATABLE_EDEFAULT == null ? updatable != null : !UPDATABLE_EDEFAULT.equals(updatable);
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
		if (baseClass == XmlOrderColumn_2_0.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.XML_ORDER_COLUMN__NULLABLE: return OrmV2_0Package.XML_ORDER_COLUMN_20__NULLABLE;
				case OrmPackage.XML_ORDER_COLUMN__INSERTABLE: return OrmV2_0Package.XML_ORDER_COLUMN_20__INSERTABLE;
				case OrmPackage.XML_ORDER_COLUMN__UPDATABLE: return OrmV2_0Package.XML_ORDER_COLUMN_20__UPDATABLE;
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
		if (baseClass == XmlOrderColumn_2_0.class)
		{
			switch (baseFeatureID)
			{
				case OrmV2_0Package.XML_ORDER_COLUMN_20__NULLABLE: return OrmPackage.XML_ORDER_COLUMN__NULLABLE;
				case OrmV2_0Package.XML_ORDER_COLUMN_20__INSERTABLE: return OrmPackage.XML_ORDER_COLUMN__INSERTABLE;
				case OrmV2_0Package.XML_ORDER_COLUMN_20__UPDATABLE: return OrmPackage.XML_ORDER_COLUMN__UPDATABLE;
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
		result.append(" (nullable: ");
		result.append(nullable);
		result.append(", insertable: ");
		result.append(insertable);
		result.append(", updatable: ");
		result.append(updatable);
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
			buildNameTranslator(),
			buildNullableTranslator(),
			buildInsertableTranslator(),
			buildUpdatableTranslator(),
			buildColumnDefinitionTranslator(),
		};
	}
		
	protected static Translator buildNullableTranslator() {
		return new BooleanTranslator(JPA.NULLABLE, OrmV2_0Package.eINSTANCE.getXmlOrderColumn_2_0_Nullable(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildInsertableTranslator() {
		return new BooleanTranslator(JPA.INSERTABLE, OrmV2_0Package.eINSTANCE.getXmlOrderColumn_2_0_Insertable(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildUpdatableTranslator() {
		return new BooleanTranslator(JPA.UPDATABLE, OrmV2_0Package.eINSTANCE.getXmlOrderColumn_2_0_Updatable(), Translator.DOM_ATTRIBUTE);
	}
}
