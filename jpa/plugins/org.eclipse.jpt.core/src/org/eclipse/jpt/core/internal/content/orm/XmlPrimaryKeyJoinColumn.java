/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.content.orm.resource.OrmXmlMapper;
import org.eclipse.jpt.core.internal.emfutility.DOMUtilities;
import org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Primary Key Join Column</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlPrimaryKeyJoinColumn#getSpecifiedReferencedColumnNameForXml <em>Specified Referenced Column Name For Xml</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlPrimaryKeyJoinColumn()
 * @model kind="class"
 * @generated
 */
public class XmlPrimaryKeyJoinColumn extends AbstractXmlNamedColumn
	implements IPrimaryKeyJoinColumn
{
	/**
	 * The default value of the '{@link #getReferencedColumnName() <em>Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String REFERENCED_COLUMN_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedReferencedColumnName() <em>Specified Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_REFERENCED_COLUMN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedReferencedColumnName() <em>Specified Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected String specifiedReferencedColumnName = SPECIFIED_REFERENCED_COLUMN_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultReferencedColumnName() <em>Default Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_REFERENCED_COLUMN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultReferencedColumnName() <em>Default Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultReferencedColumnName()
	 * @generated
	 * @ordered
	 */
	protected String defaultReferencedColumnName = DEFAULT_REFERENCED_COLUMN_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpecifiedReferencedColumnNameForXml() <em>Specified Referenced Column Name For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedReferencedColumnNameForXml()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_REFERENCED_COLUMN_NAME_FOR_XML_EDEFAULT = null;

	protected XmlPrimaryKeyJoinColumn() {
		throw new UnsupportedOperationException("Use XmlPrimaryKeyJoinColumn(INamedColumn.Owner) instead");
	}

	protected XmlPrimaryKeyJoinColumn(IAbstractJoinColumn.Owner owner) {
		super(owner);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_PRIMARY_KEY_JOIN_COLUMN;
	}

	public void setSpecifiedName(String newSpecifiedName) {
		setSpecifiedNameGen(newSpecifiedName);
		setSpecifiedNameForXml(newSpecifiedName);
	}

	/**
	 * Returns the value of the '<em><b>Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenced Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Column Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIPrimaryKeyJoinColumn_ReferencedColumnName()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getReferencedColumnName() {
		return (this.getSpecifiedReferencedColumnName() == null) ? getDefaultReferencedColumnName() : this.getSpecifiedReferencedColumnName();
	}

	/**
	 * Returns the value of the '<em><b>Specified Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Referenced Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Referenced Column Name</em>' attribute.
	 * @see #setSpecifiedReferencedColumnName(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIAbstractJoinColumn_SpecifiedReferencedColumnName()
	 * @model
	 * @generated
	 */
	public String getSpecifiedReferencedColumnName() {
		return specifiedReferencedColumnName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlPrimaryKeyJoinColumn#getSpecifiedReferencedColumnName <em>Specified Referenced Column Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Referenced Column Name</em>' attribute.
	 * @see #getSpecifiedReferencedColumnName()
	 * @generated
	 */
	public void setSpecifiedReferencedColumnNameGen(String newSpecifiedReferencedColumnName) {
		String oldSpecifiedReferencedColumnName = specifiedReferencedColumnName;
		specifiedReferencedColumnName = newSpecifiedReferencedColumnName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME, oldSpecifiedReferencedColumnName, specifiedReferencedColumnName));
	}

	public void setSpecifiedReferencedColumnName(String newSpecifiedReferencedColumnName) {
		setSpecifiedReferencedColumnNameGen(newSpecifiedReferencedColumnName);
		setSpecifiedReferencedColumnNameForXml(newSpecifiedReferencedColumnName);
	}

	/**
	 * Returns the value of the '<em><b>Default Referenced Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Referenced Column Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Referenced Column Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIAbstractJoinColumn_DefaultReferencedColumnName()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultReferencedColumnName() {
		return defaultReferencedColumnName;
	}

	public void setColumnDefinition(String newColumnDefinition) {
		setColumnDefinitionGen(newColumnDefinition);
		setColumnDefinitionForXml(newColumnDefinition);
	}

	/**
	 * Returns the value of the '<em><b>Specified Referenced Column Name For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Referenced Column Name For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Referenced Column Name For Xml</em>' attribute.
	 * @see #setSpecifiedReferencedColumnNameForXml(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlPrimaryKeyJoinColumn_SpecifiedReferencedColumnNameForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public String getSpecifiedReferencedColumnNameForXml() {
		return getSpecifiedReferencedColumnName();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlPrimaryKeyJoinColumn#getSpecifiedReferencedColumnNameForXml <em>Specified Referenced Column Name For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Referenced Column Name For Xml</em>' attribute.
	 * @see #getSpecifiedReferencedColumnNameForXml()
	 * @generated NOT
	 */
	public void setSpecifiedReferencedColumnNameForXml(String newSpecifiedReferencedColumnNameForXml) {
		setSpecifiedReferencedColumnNameGen(newSpecifiedReferencedColumnNameForXml);
		if (eNotificationRequired())
			//make sure oldValue different from newValue because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME_FOR_XML, newSpecifiedReferencedColumnNameForXml + " ", newSpecifiedReferencedColumnNameForXml));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME :
				return getReferencedColumnName();
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
				return getSpecifiedReferencedColumnName();
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME :
				return getDefaultReferencedColumnName();
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME_FOR_XML :
				return getSpecifiedReferencedColumnNameForXml();
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
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
				setSpecifiedReferencedColumnName((String) newValue);
				return;
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME_FOR_XML :
				setSpecifiedReferencedColumnNameForXml((String) newValue);
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
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
				setSpecifiedReferencedColumnName(SPECIFIED_REFERENCED_COLUMN_NAME_EDEFAULT);
				return;
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME_FOR_XML :
				setSpecifiedReferencedColumnNameForXml(SPECIFIED_REFERENCED_COLUMN_NAME_FOR_XML_EDEFAULT);
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
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME :
				return REFERENCED_COLUMN_NAME_EDEFAULT == null ? getReferencedColumnName() != null : !REFERENCED_COLUMN_NAME_EDEFAULT.equals(getReferencedColumnName());
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
				return SPECIFIED_REFERENCED_COLUMN_NAME_EDEFAULT == null ? specifiedReferencedColumnName != null : !SPECIFIED_REFERENCED_COLUMN_NAME_EDEFAULT.equals(specifiedReferencedColumnName);
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME :
				return DEFAULT_REFERENCED_COLUMN_NAME_EDEFAULT == null ? defaultReferencedColumnName != null : !DEFAULT_REFERENCED_COLUMN_NAME_EDEFAULT.equals(defaultReferencedColumnName);
			case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME_FOR_XML :
				return SPECIFIED_REFERENCED_COLUMN_NAME_FOR_XML_EDEFAULT == null ? getSpecifiedReferencedColumnNameForXml() != null : !SPECIFIED_REFERENCED_COLUMN_NAME_FOR_XML_EDEFAULT.equals(getSpecifiedReferencedColumnNameForXml());
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
		if (baseClass == IAbstractJoinColumn.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME :
					return JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__REFERENCED_COLUMN_NAME;
				case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
					return JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME;
				case OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME :
					return JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME;
				default :
					return -1;
			}
		}
		if (baseClass == IPrimaryKeyJoinColumn.class) {
			switch (derivedFeatureID) {
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
		if (baseClass == IAbstractJoinColumn.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__REFERENCED_COLUMN_NAME :
					return OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME;
				case JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME :
					return OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME;
				case JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME :
					return OrmPackage.XML_PRIMARY_KEY_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME;
				default :
					return -1;
			}
		}
		if (baseClass == IPrimaryKeyJoinColumn.class) {
			switch (baseFeatureID) {
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
		result.append(" (specifiedReferencedColumnName: ");
		result.append(specifiedReferencedColumnName);
		result.append(", defaultReferencedColumnName: ");
		result.append(defaultReferencedColumnName);
		result.append(')');
		return result.toString();
	}

	public IAbstractJoinColumn.Owner getOwner() {
		return (IAbstractJoinColumn.Owner) this.owner;
	}

	public Column dbReferencedColumn() {
		Table table = this.dbReferencedColumnTable();
		return (table == null) ? null : table.columnNamed(this.getReferencedColumnName());
	}

	public Table dbReferencedColumnTable() {
		return getOwner().dbReferencedColumnTable();
	}

	public boolean isReferencedColumnResolved() {
		return dbReferencedColumn() != null;
	}

	public ITextRange getReferencedColumnNameTextRange() {
		if (node == null) {
			return owner.getTextRange();
		}
		IDOMNode referencedColumnNameNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.REFERENCED_COLUMN_NAME);
		return (referencedColumnNameNode == null) ? getTextRange() : buildTextRange(referencedColumnNameNode);
	}
}
