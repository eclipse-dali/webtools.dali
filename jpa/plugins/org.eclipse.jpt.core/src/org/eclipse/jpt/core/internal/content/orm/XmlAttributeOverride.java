/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.IColumnMapping;
import org.eclipse.jpt.core.internal.mappings.INamedColumn;
import org.eclipse.jpt.core.internal.mappings.IOverride;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.wst.common.internal.emf.resource.EMF2DOMAdapter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Attribute Override</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlAttributeOverride()
 * @model kind="class"
 * @generated
 */
public class XmlAttributeOverride extends XmlOverride
	implements IAttributeOverride, IXmlColumnMapping
{
	/**
	 * The cached value of the '{@link #getColumn() <em>Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumn()
	 * @generated
	 * @ordered
	 */
	protected IColumn column;

	protected XmlAttributeOverride() {
		throw new UnsupportedOperationException();
	}

	protected XmlAttributeOverride(IOverride.Owner owner) {
		super(owner);
		this.column = OrmFactory.eINSTANCE.createXmlColumn(buildColumnOwner());
		((InternalEObject) this.column).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrmPackage.XML_ATTRIBUTE_OVERRIDE__COLUMN, null, null);
	}

	protected INamedColumn.Owner buildColumnOwner() {
		return new ColumnOwner();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_ATTRIBUTE_OVERRIDE;
	}

	/**
	 * Returns the value of the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column</em>' containment reference.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIAttributeOverride_Column()
	 * @model containment="true" required="true" changeable="false"
	 * @generated
	 */
	public IColumn getColumn() {
		return column;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetColumn(IColumn newColumn, NotificationChain msgs) {
		IColumn oldColumn = column;
		column = newColumn;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ATTRIBUTE_OVERRIDE__COLUMN, oldColumn, newColumn);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Returns the value of the '<em><b>Column For Xml</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column For Xml</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column For Xml</em>' reference.
	 * @see #setColumnForXml(XmlColumn)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIXmlColumnMapping_ColumnForXml()
	 * @model resolveProxies="false" volatile="true"
	 * @generated NOT
	 */
	public XmlColumn getColumnForXml() {
		if (((XmlColumn) getColumn()).isAllFeaturesUnset()) {
			return null;
		}
		return (XmlColumn) getColumn();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlAttributeOverride#getColumnForXml <em>Column For Xml</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Column For Xml</em>' reference.
	 * @see #getColumnForXml()
	 * @generated NOT
	 */
	public void setColumnForXmlGen(XmlColumn newColumnForXml) {
		XmlColumn oldValue = newColumnForXml == null ? (XmlColumn) getColumn() : null;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_ATTRIBUTE_OVERRIDE__COLUMN_FOR_XML, oldValue, newColumnForXml));
		}
	}

	public void setColumnForXml(XmlColumn newColumnForXml) {
		setColumnForXmlGen(newColumnForXml);
		if (newColumnForXml == null) {
			((XmlColumn) getColumn()).unsetAllAttributes();
			//Bug 191067 more translators hackery.  Remove their listener since it is not getting removed by them.
			//this will allow us to later add this tag back in to the xml.  When we switch to the 
			//resource model approach in 2.0, we shouldn't need the *forXml objects.
			Adapter adapter = EcoreUtil.getExistingAdapter(getColumn(), EMF2DOMAdapter.ADAPTER_CLASS);
			getColumn().eAdapters().remove(adapter);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public void makeColumnForXmlNonNull() {
		setColumnForXmlGen(getColumnForXml());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated NOT
	 */
	public void makeColumnForXmlNull() {
		setColumnForXmlGen(null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrmPackage.XML_ATTRIBUTE_OVERRIDE__COLUMN :
				return basicSetColumn(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_ATTRIBUTE_OVERRIDE__COLUMN :
				return getColumn();
			case OrmPackage.XML_ATTRIBUTE_OVERRIDE__COLUMN_FOR_XML :
				return getColumnForXml();
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
			case OrmPackage.XML_ATTRIBUTE_OVERRIDE__COLUMN_FOR_XML :
				setColumnForXml((XmlColumn) newValue);
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
			case OrmPackage.XML_ATTRIBUTE_OVERRIDE__COLUMN_FOR_XML :
				setColumnForXml((XmlColumn) null);
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
			case OrmPackage.XML_ATTRIBUTE_OVERRIDE__COLUMN :
				return column != null;
			case OrmPackage.XML_ATTRIBUTE_OVERRIDE__COLUMN_FOR_XML :
				return getColumnForXml() != null;
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
		if (baseClass == IColumnMapping.class) {
			switch (derivedFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IAttributeOverride.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_ATTRIBUTE_OVERRIDE__COLUMN :
					return JpaCoreMappingsPackage.IATTRIBUTE_OVERRIDE__COLUMN;
				default :
					return -1;
			}
		}
		if (baseClass == IXmlColumnMapping.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_ATTRIBUTE_OVERRIDE__COLUMN_FOR_XML :
					return OrmPackage.IXML_COLUMN_MAPPING__COLUMN_FOR_XML;
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
		if (baseClass == IColumnMapping.class) {
			switch (baseFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IAttributeOverride.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IATTRIBUTE_OVERRIDE__COLUMN :
					return OrmPackage.XML_ATTRIBUTE_OVERRIDE__COLUMN;
				default :
					return -1;
			}
		}
		if (baseClass == IXmlColumnMapping.class) {
			switch (baseFeatureID) {
				case OrmPackage.IXML_COLUMN_MAPPING__COLUMN_FOR_XML :
					return OrmPackage.XML_ATTRIBUTE_OVERRIDE__COLUMN_FOR_XML;
				default :
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}


	public class ColumnOwner implements INamedColumn.Owner
	{
		public ITextRange validationTextRange() {
			return XmlAttributeOverride.this.validationTextRange();
		}

		public ITypeMapping getTypeMapping() {
			return XmlAttributeOverride.this.getOwner().getTypeMapping();
		}

		public Table dbTable(String tablename) {
			return this.getTypeMapping().dbTable(column.getTable());
		}
	}
}
