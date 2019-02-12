/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.common.core.internal.utility.translators.BooleanTranslator;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Xml Abstract Column</b></em>'.
 *
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getAbstractXmlBaseColumn()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class AbstractXmlBaseColumn extends AbstractXmlNamedColumn implements XmlBaseColumn
{
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
	 * The default value of the '{@link #getTable() <em>Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTable()
	 * @generated
	 * @ordered
	 */
	protected static final String TABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTable() <em>Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTable()
	 * @generated
	 * @ordered
	 */
	protected String table = TABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getUnique() <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnique()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean UNIQUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUnique() <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnique()
	 * @generated
	 * @ordered
	 */
	protected Boolean unique = UNIQUE_EDEFAULT;

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
	protected AbstractXmlBaseColumn()
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
		return OrmPackage.Literals.ABSTRACT_XML_BASE_COLUMN;
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlBaseColumn_Insertable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getInsertable()
	{
		return insertable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlBaseColumn#getInsertable <em>Insertable</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_BASE_COLUMN__INSERTABLE, oldInsertable, insertable));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlBaseColumn_Nullable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getNullable()
	{
		return nullable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlBaseColumn#getNullable <em>Nullable</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_BASE_COLUMN__NULLABLE, oldNullable, nullable));
	}

	/**
	 * Returns the value of the '<em><b>Table</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table</em>' attribute.
	 * @see #setTable(String)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlBaseColumn_Table()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	public String getTable()
	{
		return table;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlBaseColumn#getTable <em>Table</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table</em>' attribute.
	 * @see #getTable()
	 * @generated
	 */
	public void setTable(String newTable)
	{
		String oldTable = table;
		table = newTable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_BASE_COLUMN__TABLE, oldTable, table));
	}

	/**
	 * Returns the value of the '<em><b>Unique</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unique</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unique</em>' attribute.
	 * @see #setUnique(Boolean)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlBaseColumn_Unique()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getUnique()
	{
		return unique;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlBaseColumn#getUnique <em>Unique</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unique</em>' attribute.
	 * @see #getUnique()
	 * @generated
	 */
	public void setUnique(Boolean newUnique)
	{
		Boolean oldUnique = unique;
		unique = newUnique;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_BASE_COLUMN__UNIQUE, oldUnique, unique));
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
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlBaseColumn_Updatable()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	public Boolean getUpdatable()
	{
		return updatable;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.AbstractXmlBaseColumn#getUpdatable <em>Updatable</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_BASE_COLUMN__UPDATABLE, oldUpdatable, updatable));
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
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__INSERTABLE:
				return getInsertable();
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__NULLABLE:
				return getNullable();
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__TABLE:
				return getTable();
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__UNIQUE:
				return getUnique();
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__UPDATABLE:
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
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__INSERTABLE:
				setInsertable((Boolean)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__NULLABLE:
				setNullable((Boolean)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__TABLE:
				setTable((String)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__UNIQUE:
				setUnique((Boolean)newValue);
				return;
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__UPDATABLE:
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
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__INSERTABLE:
				setInsertable(INSERTABLE_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__NULLABLE:
				setNullable(NULLABLE_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__TABLE:
				setTable(TABLE_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__UNIQUE:
				setUnique(UNIQUE_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__UPDATABLE:
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
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__INSERTABLE:
				return INSERTABLE_EDEFAULT == null ? insertable != null : !INSERTABLE_EDEFAULT.equals(insertable);
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__NULLABLE:
				return NULLABLE_EDEFAULT == null ? nullable != null : !NULLABLE_EDEFAULT.equals(nullable);
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__TABLE:
				return TABLE_EDEFAULT == null ? table != null : !TABLE_EDEFAULT.equals(table);
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__UNIQUE:
				return UNIQUE_EDEFAULT == null ? unique != null : !UNIQUE_EDEFAULT.equals(unique);
			case OrmPackage.ABSTRACT_XML_BASE_COLUMN__UPDATABLE:
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
		if (baseClass == XmlBaseColumn.class)
		{
			switch (derivedFeatureID)
			{
				case OrmPackage.ABSTRACT_XML_BASE_COLUMN__INSERTABLE: return OrmPackage.XML_BASE_COLUMN__INSERTABLE;
				case OrmPackage.ABSTRACT_XML_BASE_COLUMN__NULLABLE: return OrmPackage.XML_BASE_COLUMN__NULLABLE;
				case OrmPackage.ABSTRACT_XML_BASE_COLUMN__TABLE: return OrmPackage.XML_BASE_COLUMN__TABLE;
				case OrmPackage.ABSTRACT_XML_BASE_COLUMN__UNIQUE: return OrmPackage.XML_BASE_COLUMN__UNIQUE;
				case OrmPackage.ABSTRACT_XML_BASE_COLUMN__UPDATABLE: return OrmPackage.XML_BASE_COLUMN__UPDATABLE;
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
		if (baseClass == XmlBaseColumn.class)
		{
			switch (baseFeatureID)
			{
				case OrmPackage.XML_BASE_COLUMN__INSERTABLE: return OrmPackage.ABSTRACT_XML_BASE_COLUMN__INSERTABLE;
				case OrmPackage.XML_BASE_COLUMN__NULLABLE: return OrmPackage.ABSTRACT_XML_BASE_COLUMN__NULLABLE;
				case OrmPackage.XML_BASE_COLUMN__TABLE: return OrmPackage.ABSTRACT_XML_BASE_COLUMN__TABLE;
				case OrmPackage.XML_BASE_COLUMN__UNIQUE: return OrmPackage.ABSTRACT_XML_BASE_COLUMN__UNIQUE;
				case OrmPackage.XML_BASE_COLUMN__UPDATABLE: return OrmPackage.ABSTRACT_XML_BASE_COLUMN__UPDATABLE;
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
		result.append(" (insertable: ");
		result.append(insertable);
		result.append(", nullable: ");
		result.append(nullable);
		result.append(", table: ");
		result.append(table);
		result.append(", unique: ");
		result.append(unique);
		result.append(", updatable: ");
		result.append(updatable);
		result.append(')');
		return result.toString();
	}

	public TextRange getTableTextRange() {
		return getAttributeTextRange(JPA.TABLE);
	}
	
	// ********** translators **********

	protected static Translator buildUniqueTranslator() {
		return new BooleanTranslator(JPA.UNIQUE, OrmPackage.eINSTANCE.getXmlBaseColumn_Unique(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildNullableTranslator() {
		return new BooleanTranslator(JPA.NULLABLE, OrmPackage.eINSTANCE.getXmlBaseColumn_Nullable(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildInsertableTranslator() {
		return new BooleanTranslator(JPA.INSERTABLE, OrmPackage.eINSTANCE.getXmlBaseColumn_Insertable(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildUpdatableTranslator() {
		return new BooleanTranslator(JPA.UPDATABLE, OrmPackage.eINSTANCE.getXmlBaseColumn_Updatable(), Translator.DOM_ATTRIBUTE);
	}
	
	protected static Translator buildTableTranslator() {
		return new Translator(JPA.TABLE, OrmPackage.eINSTANCE.getXmlBaseColumn_Table(), Translator.DOM_ATTRIBUTE);
	}
	
	// ************ content assist ************
	
	private TextRange getTableCodeAssistTextRange() {
		return getAttributeCodeAssistTextRange(JPA.TABLE);
	}
	
	public boolean tableTouches(int pos) {
		TextRange textRange = this.getTableCodeAssistTextRange();
		return (textRange != null) && (textRange.touches(pos));
	}
	
} // AbstractXmlAbstractColumn
