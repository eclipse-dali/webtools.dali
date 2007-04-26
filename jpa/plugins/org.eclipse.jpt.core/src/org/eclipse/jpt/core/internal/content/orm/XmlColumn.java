/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import java.util.Set;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.mappings.DefaultFalseBoolean;
import org.eclipse.jpt.core.internal.mappings.DefaultTrueBoolean;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Column</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlColumn#getLengthForXml <em>Length For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlColumn#getPrecisionForXml <em>Precision For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlColumn#getScaleForXml <em>Scale For Xml</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlColumn()
 * @model kind="class"
 * @generated
 */
public class XmlColumn extends AbstractXmlColumn implements IColumn
{
	/**
	 * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected static final int LENGTH_EDEFAULT = 255;

	/**
	 * The cached value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected int length = LENGTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getPrecision() <em>Precision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrecision()
	 * @generated
	 * @ordered
	 */
	protected static final int PRECISION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPrecision() <em>Precision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrecision()
	 * @generated
	 * @ordered
	 */
	protected int precision = PRECISION_EDEFAULT;

	/**
	 * The default value of the '{@link #getScale() <em>Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScale()
	 * @generated
	 * @ordered
	 */
	protected static final int SCALE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getScale() <em>Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScale()
	 * @generated
	 * @ordered
	 */
	protected int scale = SCALE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLengthForXml() <em>Length For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLengthForXml()
	 * @generated
	 * @ordered
	 */
	protected static final int LENGTH_FOR_XML_EDEFAULT = 255;

	/**
	 * The default value of the '{@link #getPrecisionForXml() <em>Precision For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrecisionForXml()
	 * @generated
	 * @ordered
	 */
	protected static final int PRECISION_FOR_XML_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getScaleForXml() <em>Scale For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScaleForXml()
	 * @generated
	 * @ordered
	 */
	protected static final int SCALE_FOR_XML_EDEFAULT = 0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlColumn() {
		super();
	}

	protected XmlColumn(IColumn.Owner owner) {
		super(owner);
	}

	@Override
	protected void addInsignificantXmlFeatureIdsTo(Set<Integer> insignificantXmlFeatureIds) {
		super.addInsignificantXmlFeatureIdsTo(insignificantXmlFeatureIds);
		insignificantXmlFeatureIds.add(OrmPackage.XML_COLUMN__NAME);
		insignificantXmlFeatureIds.add(OrmPackage.XML_COLUMN__DEFAULT_NAME);
		insignificantXmlFeatureIds.add(OrmPackage.XML_COLUMN__TABLE);
		insignificantXmlFeatureIds.add(OrmPackage.XML_COLUMN__DEFAULT_TABLE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_COLUMN;
	}

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * The default value is <code>"255"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(int)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIColumn_Length()
	 * @model default="255"
	 * @generated
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlColumn#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	public void setLengthGen(int newLength) {
		int oldLength = length;
		length = newLength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_COLUMN__LENGTH, oldLength, length));
	}

	public void setLength(int newLength) {
		setLengthGen(newLength);
		if (newLength != LENGTH_EDEFAULT) {
			getColumnMapping().makeColumnForXmlNonNull();
		}
		setLengthForXml(newLength);
		if (isAllFeaturesUnset()) {
			getColumnMapping().makeColumnForXmlNull();
		}
	}

	/**
	 * Returns the value of the '<em><b>Precision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Precision</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Precision</em>' attribute.
	 * @see #setPrecision(int)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIColumn_Precision()
	 * @model
	 * @generated
	 */
	public int getPrecision() {
		return precision;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlColumn#getPrecision <em>Precision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Precision</em>' attribute.
	 * @see #getPrecision()
	 * @generated
	 */
	public void setPrecisionGen(int newPrecision) {
		int oldPrecision = precision;
		precision = newPrecision;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_COLUMN__PRECISION, oldPrecision, precision));
	}

	public void setPrecision(int newPrecision) {
		setPrecisionGen(newPrecision);
		if (newPrecision != PRECISION_EDEFAULT) {
			getColumnMapping().makeColumnForXmlNonNull();
		}
		setPrecisionForXml(newPrecision);
		if (isAllFeaturesUnset()) {
			getColumnMapping().makeColumnForXmlNull();
		}
	}

	/**
	 * Returns the value of the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scale</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scale</em>' attribute.
	 * @see #setScale(int)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIColumn_Scale()
	 * @model
	 * @generated
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlColumn#getScale <em>Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scale</em>' attribute.
	 * @see #getScale()
	 * @generated
	 */
	public void setScaleGen(int newScale) {
		int oldScale = scale;
		scale = newScale;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_COLUMN__SCALE, oldScale, scale));
	}

	public void setScale(int newScale) {
		setScaleGen(newScale);
		if (newScale != SCALE_EDEFAULT) {
			getColumnMapping().makeColumnForXmlNonNull();
		}
		setScaleForXml(newScale);
		if (isAllFeaturesUnset()) {
			getColumnMapping().makeColumnForXmlNull();
		}
	}

	/**
	 * Returns the value of the '<em><b>Length For Xml</b></em>' attribute.
	 * The default value is <code>"255"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length For Xml</em>' attribute.
	 * @see #setLengthForXml(int)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlColumn_LengthForXml()
	 * @model default="255" volatile="true"
	 * @generated NOT
	 */
	public int getLengthForXml() {
		return getLength();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlColumn#getLengthForXml <em>Length For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length For Xml</em>' attribute.
	 * @see #getLengthForXml()
	 * @generated NOT
	 */
	public void setLengthForXml(int newLengthForXml) {
		setLengthGen(newLengthForXml);
		if (eNotificationRequired())
			//pass in oldValue of null because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_COLUMN__LENGTH_FOR_XML, null, newLengthForXml));
	}

	/**
	 * Returns the value of the '<em><b>Precision For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Precision For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Precision For Xml</em>' attribute.
	 * @see #setPrecisionForXml(int)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlColumn_PrecisionForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public int getPrecisionForXml() {
		return getPrecision();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlColumn#getPrecisionForXml <em>Precision For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Precision For Xml</em>' attribute.
	 * @see #getPrecisionForXml()
	 * @generated NOT
	 */
	public void setPrecisionForXml(int newPrecisionForXml) {
		setPrecisionGen(newPrecisionForXml);
		if (eNotificationRequired())
			//pass in oldValue of null because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_COLUMN__PRECISION_FOR_XML, null, newPrecisionForXml));
	}

	/**
	 * Returns the value of the '<em><b>Scale For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scale For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scale For Xml</em>' attribute.
	 * @see #setScaleForXml(int)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlColumn_ScaleForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public int getScaleForXml() {
		return getScale();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlColumn#getScaleForXml <em>Scale For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scale For Xml</em>' attribute.
	 * @see #getScaleForXml()
	 * @generated NOT
	 */
	public void setScaleForXml(int newScaleForXml) {
		setScaleGen(newScaleForXml);
		if (eNotificationRequired())
			//pass in oldValue of null because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_COLUMN__SCALE_FOR_XML, null, newScaleForXml));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_COLUMN__LENGTH :
				return new Integer(getLength());
			case OrmPackage.XML_COLUMN__PRECISION :
				return new Integer(getPrecision());
			case OrmPackage.XML_COLUMN__SCALE :
				return new Integer(getScale());
			case OrmPackage.XML_COLUMN__LENGTH_FOR_XML :
				return new Integer(getLengthForXml());
			case OrmPackage.XML_COLUMN__PRECISION_FOR_XML :
				return new Integer(getPrecisionForXml());
			case OrmPackage.XML_COLUMN__SCALE_FOR_XML :
				return new Integer(getScaleForXml());
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OrmPackage.XML_COLUMN__LENGTH :
				setLength(((Integer) newValue).intValue());
				return;
			case OrmPackage.XML_COLUMN__PRECISION :
				setPrecision(((Integer) newValue).intValue());
				return;
			case OrmPackage.XML_COLUMN__SCALE :
				setScale(((Integer) newValue).intValue());
				return;
			case OrmPackage.XML_COLUMN__LENGTH_FOR_XML :
				setLengthForXml(((Integer) newValue).intValue());
				return;
			case OrmPackage.XML_COLUMN__PRECISION_FOR_XML :
				setPrecisionForXml(((Integer) newValue).intValue());
				return;
			case OrmPackage.XML_COLUMN__SCALE_FOR_XML :
				setScaleForXml(((Integer) newValue).intValue());
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case OrmPackage.XML_COLUMN__LENGTH :
				setLength(LENGTH_EDEFAULT);
				return;
			case OrmPackage.XML_COLUMN__PRECISION :
				setPrecision(PRECISION_EDEFAULT);
				return;
			case OrmPackage.XML_COLUMN__SCALE :
				setScale(SCALE_EDEFAULT);
				return;
			case OrmPackage.XML_COLUMN__LENGTH_FOR_XML :
				setLengthForXml(LENGTH_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.XML_COLUMN__PRECISION_FOR_XML :
				setPrecisionForXml(PRECISION_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.XML_COLUMN__SCALE_FOR_XML :
				setScaleForXml(SCALE_FOR_XML_EDEFAULT);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case OrmPackage.XML_COLUMN__LENGTH :
				return length != LENGTH_EDEFAULT;
			case OrmPackage.XML_COLUMN__PRECISION :
				return precision != PRECISION_EDEFAULT;
			case OrmPackage.XML_COLUMN__SCALE :
				return scale != SCALE_EDEFAULT;
			case OrmPackage.XML_COLUMN__LENGTH_FOR_XML :
				return getLengthForXml() != LENGTH_FOR_XML_EDEFAULT;
			case OrmPackage.XML_COLUMN__PRECISION_FOR_XML :
				return getPrecisionForXml() != PRECISION_FOR_XML_EDEFAULT;
			case OrmPackage.XML_COLUMN__SCALE_FOR_XML :
				return getScaleForXml() != SCALE_FOR_XML_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == IColumn.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_COLUMN__LENGTH :
					return JpaCoreMappingsPackage.ICOLUMN__LENGTH;
				case OrmPackage.XML_COLUMN__PRECISION :
					return JpaCoreMappingsPackage.ICOLUMN__PRECISION;
				case OrmPackage.XML_COLUMN__SCALE :
					return JpaCoreMappingsPackage.ICOLUMN__SCALE;
				default :
					return -1;
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
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == IColumn.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.ICOLUMN__LENGTH :
					return OrmPackage.XML_COLUMN__LENGTH;
				case JpaCoreMappingsPackage.ICOLUMN__PRECISION :
					return OrmPackage.XML_COLUMN__PRECISION;
				case JpaCoreMappingsPackage.ICOLUMN__SCALE :
					return OrmPackage.XML_COLUMN__SCALE;
				default :
					return -1;
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
	public String toString() {
		if (eIsProxy())
			return super.toString();
		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (length: ");
		result.append(length);
		result.append(", precision: ");
		result.append(precision);
		result.append(", scale: ");
		result.append(scale);
		result.append(')');
		return result.toString();
	}

	/**
	 * Call this when the table tag is removed from the xml,
	 * need to make sure all the model attributes are set to the default
	 */
	protected void unsetAllAttributes() {
		eUnset(OrmPackage.XML_COLUMN__SPECIFIED_NAME);
		eUnset(OrmPackage.XML_COLUMN__SPECIFIED_TABLE);
		eUnset(OrmPackage.XML_COLUMN__COLUMN_DEFINITION);
		eUnset(OrmPackage.XML_COLUMN__INSERTABLE);
		eUnset(OrmPackage.XML_COLUMN__LENGTH);
		eUnset(OrmPackage.XML_COLUMN__NULLABLE);
		eUnset(OrmPackage.XML_COLUMN__PRECISION);
		eUnset(OrmPackage.XML_COLUMN__SCALE);
		eUnset(OrmPackage.XML_COLUMN__UNIQUE);
		eUnset(OrmPackage.XML_COLUMN__UPDATABLE);
	}

	public void setSpecifiedName(String newSpecifiedName) {
		setSpecifiedNameGen(newSpecifiedName);
		if (newSpecifiedName != SPECIFIED_NAME_EDEFAULT) {
			getColumnMapping().makeColumnForXmlNonNull();
		}
		setSpecifiedNameForXml(newSpecifiedName);
		if (isAllFeaturesUnset()) {
			getColumnMapping().makeColumnForXmlNull();
		}
	}

	public void setUnique(DefaultFalseBoolean newUnique) {
		setUniqueGen(newUnique);
		if (newUnique != UNIQUE_EDEFAULT) {
			getColumnMapping().makeColumnForXmlNonNull();
		}
		setUniqueForXml(newUnique);
		if (isAllFeaturesUnset()) {
			getColumnMapping().makeColumnForXmlNull();
		}
	}

	public void setNullable(DefaultTrueBoolean newNullable) {
		setNullableGen(newNullable);
		if (newNullable != NULLABLE_EDEFAULT) {
			getColumnMapping().makeColumnForXmlNonNull();
		}
		setNullableForXml(newNullable);
		if (isAllFeaturesUnset()) {
			getColumnMapping().makeColumnForXmlNull();
		}
	}

	public void setInsertable(DefaultTrueBoolean newInsertable) {
		setInsertableGen(newInsertable);
		if (newInsertable != INSERTABLE_EDEFAULT) {
			getColumnMapping().makeColumnForXmlNonNull();
		}
		setInsertableForXml(newInsertable);
		if (isAllFeaturesUnset()) {
			getColumnMapping().makeColumnForXmlNull();
		}
	}

	public void setUpdatable(DefaultTrueBoolean newUpdatable) {
		setUpdatableGen(newUpdatable);
		if (newUpdatable != UPDATABLE_EDEFAULT) {
			getColumnMapping().makeColumnForXmlNonNull();
		}
		setUpdatableForXml(newUpdatable);
		if (isAllFeaturesUnset()) {
			getColumnMapping().makeColumnForXmlNull();
		}
	}

	public void setColumnDefinition(String newColumnDefinition) {
		setColumnDefinitionGen(newColumnDefinition);
		if (newColumnDefinition != COLUMN_DEFINITION_EDEFAULT) {
			getColumnMapping().makeColumnForXmlNonNull();
		}
		setColumnDefinitionForXml(newColumnDefinition);
		if (isAllFeaturesUnset()) {
			getColumnMapping().makeColumnForXmlNull();
		}
	}

	public void setSpecifiedTable(String newSpecifiedTable) {
		setSpecifiedTableGen(newSpecifiedTable);
		if (newSpecifiedTable != SPECIFIED_TABLE_EDEFAULT) {
			getColumnMapping().makeColumnForXmlNonNull();
		}
		setSpecifiedTableForXml(newSpecifiedTable);
		if (isAllFeaturesUnset()) {
			getColumnMapping().makeColumnForXmlNull();
		}
	}

	protected IXmlColumnMapping getColumnMapping() {
		return (IXmlColumnMapping) eContainer();
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {
		setDefaultTable((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_COLUMN_TABLE_KEY));
		setDefaultName((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_COLUMN_NAME_KEY));
	}
} // XmlColumn
