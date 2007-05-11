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
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.content.orm.resource.OrmXmlMapper;
import org.eclipse.jpt.core.internal.emfutility.DOMUtilities;
import org.eclipse.jpt.core.internal.mappings.INamedColumn;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;
import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Xml Named Column</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn#getSpecifiedNameForXml <em>Specified Name For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn#getColumnDefinitionForXml <em>Column Definition For Xml</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getAbstractXmlNamedColumn()
 * @model kind="class" abstract="true"
 * @generated
 */
public abstract class AbstractXmlNamedColumn extends XmlEObject
	implements INamedColumn
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
	 * The default value of the '{@link #getSpecifiedName() <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSpecifiedName() <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedName()
	 * @generated
	 * @ordered
	 */
	protected String specifiedName = SPECIFIED_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultName() <em>Default Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultName()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultName() <em>Default Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultName()
	 * @generated
	 * @ordered
	 */
	protected String defaultName = DEFAULT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getColumnDefinition() <em>Column Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnDefinition()
	 * @generated
	 * @ordered
	 */
	protected static final String COLUMN_DEFINITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getColumnDefinition() <em>Column Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnDefinition()
	 * @generated
	 * @ordered
	 */
	protected String columnDefinition = COLUMN_DEFINITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpecifiedNameForXml() <em>Specified Name For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedNameForXml()
	 * @generated
	 * @ordered
	 */
	protected static final String SPECIFIED_NAME_FOR_XML_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getColumnDefinitionForXml() <em>Column Definition For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnDefinitionForXml()
	 * @generated
	 * @ordered
	 */
	protected static final String COLUMN_DEFINITION_FOR_XML_EDEFAULT = null;

	protected final INamedColumn.Owner owner;

	protected AbstractXmlNamedColumn() {
		throw new UnsupportedOperationException("Use AbstractXmlNamedColumn(INamedColumn.Owner) instead.");
	}

	protected AbstractXmlNamedColumn(INamedColumn.Owner owner) {
		super();
		this.owner = owner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.ABSTRACT_XML_NAMED_COLUMN;
	}

	public String getName() {
		return (this.specifiedName != null) ? this.specifiedName : this.defaultName;
	}

	/**
	 * Returns the value of the '<em><b>Specified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Name</em>' attribute.
	 * @see #setSpecifiedName(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getINamedColumn_SpecifiedName()
	 * @model
	 * @generated
	 */
	public String getSpecifiedName() {
		return specifiedName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn#getSpecifiedName <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Name</em>' attribute.
	 * @see #getSpecifiedName()
	 * @generated
	 */
	public void setSpecifiedNameGen(String newSpecifiedName) {
		String oldSpecifiedName = specifiedName;
		specifiedName = newSpecifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME, oldSpecifiedName, specifiedName));
	}

	/**
	 * Returns the value of the '<em><b>Default Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Name</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getINamedColumn_DefaultName()
	 * @model changeable="false"
	 * @generated
	 */
	public String getDefaultName() {
		return defaultName;
	}

	protected void setDefaultName(String newDefaultName) {
		String oldDefaultName = this.defaultName;
		this.defaultName = newDefaultName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_COLUMN__DEFAULT_NAME, oldDefaultName, this.defaultName));
	}

	/**
	 * Returns the value of the '<em><b>Column Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Definition</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column Definition</em>' attribute.
	 * @see #setColumnDefinition(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getINamedColumn_ColumnDefinition()
	 * @model
	 * @generated
	 */
	public String getColumnDefinition() {
		return columnDefinition;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.AbstractXmlNamedColumn#getColumnDefinition <em>Column Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Column Definition</em>' attribute.
	 * @see #getColumnDefinition()
	 * @generated
	 */
	public void setColumnDefinitionGen(String newColumnDefinition) {
		String oldColumnDefinition = columnDefinition;
		columnDefinition = newColumnDefinition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION, oldColumnDefinition, columnDefinition));
	}

	public String getSpecifiedNameForXml() {
		return getSpecifiedName();
	}

	public void setSpecifiedNameForXml(String newSpecifiedNameForXml) {
		setSpecifiedNameGen(newSpecifiedNameForXml);
		if (eNotificationRequired())
			//make sure oldValue different from newValue because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME_FOR_XML, newSpecifiedNameForXml + " ", newSpecifiedNameForXml));
	}

	public String getColumnDefinitionForXml() {
		return getColumnDefinition();
	}

	public void setColumnDefinitionForXml(String newColumnDefinitionForXml) {
		setColumnDefinitionGen(newColumnDefinitionForXml);
		if (eNotificationRequired())
			//pass in oldValue of null because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.ABSTRACT_XML_COLUMN__COLUMN_DEFINITION_FOR_XML, newColumnDefinitionForXml + " ", newColumnDefinitionForXml));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__NAME :
				return getName();
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME :
				return getSpecifiedName();
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__DEFAULT_NAME :
				return getDefaultName();
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION :
				return getColumnDefinition();
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME_FOR_XML :
				return getSpecifiedNameForXml();
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION_FOR_XML :
				return getColumnDefinitionForXml();
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
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME :
				setSpecifiedName((String) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION :
				setColumnDefinition((String) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME_FOR_XML :
				setSpecifiedNameForXml((String) newValue);
				return;
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION_FOR_XML :
				setColumnDefinitionForXml((String) newValue);
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
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME :
				setSpecifiedName(SPECIFIED_NAME_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION :
				setColumnDefinition(COLUMN_DEFINITION_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME_FOR_XML :
				setSpecifiedNameForXml(SPECIFIED_NAME_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION_FOR_XML :
				setColumnDefinitionForXml(COLUMN_DEFINITION_FOR_XML_EDEFAULT);
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
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__NAME :
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME :
				return SPECIFIED_NAME_EDEFAULT == null ? specifiedName != null : !SPECIFIED_NAME_EDEFAULT.equals(specifiedName);
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__DEFAULT_NAME :
				return DEFAULT_NAME_EDEFAULT == null ? defaultName != null : !DEFAULT_NAME_EDEFAULT.equals(defaultName);
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION :
				return COLUMN_DEFINITION_EDEFAULT == null ? columnDefinition != null : !COLUMN_DEFINITION_EDEFAULT.equals(columnDefinition);
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME_FOR_XML :
				return SPECIFIED_NAME_FOR_XML_EDEFAULT == null ? getSpecifiedNameForXml() != null : !SPECIFIED_NAME_FOR_XML_EDEFAULT.equals(getSpecifiedNameForXml());
			case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION_FOR_XML :
				return COLUMN_DEFINITION_FOR_XML_EDEFAULT == null ? getColumnDefinitionForXml() != null : !COLUMN_DEFINITION_FOR_XML_EDEFAULT.equals(getColumnDefinitionForXml());
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
		if (baseClass == INamedColumn.class) {
			switch (derivedFeatureID) {
				case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__NAME :
					return JpaCoreMappingsPackage.INAMED_COLUMN__NAME;
				case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME :
					return JpaCoreMappingsPackage.INAMED_COLUMN__SPECIFIED_NAME;
				case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__DEFAULT_NAME :
					return JpaCoreMappingsPackage.INAMED_COLUMN__DEFAULT_NAME;
				case OrmPackage.ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION :
					return JpaCoreMappingsPackage.INAMED_COLUMN__COLUMN_DEFINITION;
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
		if (baseClass == INamedColumn.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.INAMED_COLUMN__NAME :
					return OrmPackage.ABSTRACT_XML_NAMED_COLUMN__NAME;
				case JpaCoreMappingsPackage.INAMED_COLUMN__SPECIFIED_NAME :
					return OrmPackage.ABSTRACT_XML_NAMED_COLUMN__SPECIFIED_NAME;
				case JpaCoreMappingsPackage.INAMED_COLUMN__DEFAULT_NAME :
					return OrmPackage.ABSTRACT_XML_NAMED_COLUMN__DEFAULT_NAME;
				case JpaCoreMappingsPackage.INAMED_COLUMN__COLUMN_DEFINITION :
					return OrmPackage.ABSTRACT_XML_NAMED_COLUMN__COLUMN_DEFINITION;
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
		result.append(" (specifiedName: ");
		result.append(specifiedName);
		result.append(", defaultName: ");
		result.append(defaultName);
		result.append(", columnDefinition: ");
		result.append(columnDefinition);
		result.append(')');
		return result.toString();
	}

	public INamedColumn.Owner getOwner() {
		return owner;
	}

	public Column dbColumn() {
		Table table = this.dbTable();
		return (table == null) ? null : table.columnNamed(getName());
	}

	public Table dbTable() {
		return getOwner().dbTable(this.tableName());
	}

	protected abstract String tableName();

	public boolean isResolved() {
		return dbColumn() != null;
	}

	public ITextRange nameTextRange() {
		if (node == null) {
			return owner.validationTextRange();
		}
		IDOMNode nameNode = (IDOMNode) DOMUtilities.getChildAttributeNode(node, OrmXmlMapper.NAME);
		return (nameNode == null) ? validationTextRange() : buildTextRange(nameNode);
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {
		setDefaultName((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_COLUMN_NAME_KEY));
	}
}