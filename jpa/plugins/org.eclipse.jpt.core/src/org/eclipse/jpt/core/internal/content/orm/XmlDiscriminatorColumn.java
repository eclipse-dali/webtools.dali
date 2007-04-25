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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.mappings.DiscriminatorType;
import org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Discriminator Column</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getSpecifiedNameForXml <em>Specified Name For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getDiscriminatorTypeForXml <em>Discriminator Type For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getColumnDefinitionForXml <em>Column Definition For Xml</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getSpecifiedLengthForXml <em>Specified Length For Xml</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlDiscriminatorColumn()
 * @model kind="class"
 * @generated
 */
public class XmlDiscriminatorColumn extends XmlEObject
	implements IDiscriminatorColumn
{
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
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getDiscriminatorType() <em>Discriminator Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorType()
	 * @generated
	 * @ordered
	 */
	protected static final DiscriminatorType DISCRIMINATOR_TYPE_EDEFAULT = DiscriminatorType.DEFAULT;

	/**
	 * The cached value of the '{@link #getDiscriminatorType() <em>Discriminator Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorType()
	 * @generated
	 * @ordered
	 */
	protected DiscriminatorType discriminatorType = DISCRIMINATOR_TYPE_EDEFAULT;

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
	 * The default value of the '{@link #getDefaultLength() <em>Default Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultLength()
	 * @generated
	 * @ordered
	 */
	protected static final int DEFAULT_LENGTH_EDEFAULT = 31;

	/**
	 * The cached value of the '{@link #getDefaultLength() <em>Default Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultLength()
	 * @generated
	 * @ordered
	 */
	protected int defaultLength = DEFAULT_LENGTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getSpecifiedLength() <em>Specified Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedLength()
	 * @generated
	 * @ordered
	 */
	protected static final int SPECIFIED_LENGTH_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getSpecifiedLength() <em>Specified Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedLength()
	 * @generated
	 * @ordered
	 */
	protected int specifiedLength = SPECIFIED_LENGTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected static final int LENGTH_EDEFAULT = 0;

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
	 * The default value of the '{@link #getDiscriminatorTypeForXml() <em>Discriminator Type For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorTypeForXml()
	 * @generated
	 * @ordered
	 */
	protected static final DiscriminatorType DISCRIMINATOR_TYPE_FOR_XML_EDEFAULT = DiscriminatorType.DEFAULT;

	/**
	 * The default value of the '{@link #getColumnDefinitionForXml() <em>Column Definition For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColumnDefinitionForXml()
	 * @generated
	 * @ordered
	 */
	protected static final String COLUMN_DEFINITION_FOR_XML_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getSpecifiedLengthForXml() <em>Specified Length For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedLengthForXml()
	 * @generated
	 * @ordered
	 */
	protected static final int SPECIFIED_LENGTH_FOR_XML_EDEFAULT = 0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XmlDiscriminatorColumn() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrmPackage.Literals.XML_DISCRIMINATOR_COLUMN;
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
	 * @see #setDefaultName(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIDiscriminatorColumn_DefaultName()
	 * @model
	 * @generated
	 */
	public String getDefaultName() {
		return defaultName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getDefaultName <em>Default Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Name</em>' attribute.
	 * @see #getDefaultName()
	 * @generated
	 */
	public void setDefaultName(String newDefaultName) {
		String oldDefaultName = defaultName;
		defaultName = newDefaultName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_DISCRIMINATOR_COLUMN__DEFAULT_NAME, oldDefaultName, defaultName));
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIDiscriminatorColumn_SpecifiedName()
	 * @model
	 * @generated
	 */
	public String getSpecifiedName() {
		return specifiedName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getSpecifiedName <em>Specified Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_NAME, oldSpecifiedName, specifiedName));
	}

	public void setSpecifiedName(String newSpecifiedName) {
		setSpecifiedNameGen(newSpecifiedName);
		if (newSpecifiedName != SPECIFIED_NAME_EDEFAULT) {
			entity().makeDiscriminatorColumnForXmlNonNull();
		}
		setSpecifiedNameForXml(newSpecifiedName);
		if (isAllFeaturesUnset()) {
			entity().makeDiscriminatorColumnForXmlNull();
		}
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIDiscriminatorColumn_Name()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public String getName() {
		return (this.getSpecifiedName() == null) ? getDefaultName() : this.getSpecifiedName();
	}

	/**
	 * Returns the value of the '<em><b>Discriminator Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DiscriminatorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discriminator Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discriminator Type</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DiscriminatorType
	 * @see #setDiscriminatorType(DiscriminatorType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIDiscriminatorColumn_DiscriminatorType()
	 * @model
	 * @generated
	 */
	public DiscriminatorType getDiscriminatorType() {
		return discriminatorType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getDiscriminatorType <em>Discriminator Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discriminator Type</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DiscriminatorType
	 * @see #getDiscriminatorType()
	 * @generated
	 */
	public void setDiscriminatorTypeGen(DiscriminatorType newDiscriminatorType) {
		DiscriminatorType oldDiscriminatorType = discriminatorType;
		discriminatorType = newDiscriminatorType == null ? DISCRIMINATOR_TYPE_EDEFAULT : newDiscriminatorType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE, oldDiscriminatorType, discriminatorType));
	}

	public void setDiscriminatorType(DiscriminatorType newDiscriminatorType) {
		setDiscriminatorTypeGen(newDiscriminatorType);
		if (newDiscriminatorType != DISCRIMINATOR_TYPE_EDEFAULT) {
			entity().makeDiscriminatorColumnForXmlNonNull();
		}
		setDiscriminatorTypeForXml(newDiscriminatorType);
		if (isAllFeaturesUnset()) {
			entity().makeDiscriminatorColumnForXmlNull();
		}
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
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIDiscriminatorColumn_ColumnDefinition()
	 * @model
	 * @generated
	 */
	public String getColumnDefinition() {
		return columnDefinition;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getColumnDefinition <em>Column Definition</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION, oldColumnDefinition, columnDefinition));
	}

	public void setColumnDefinition(String newColumnDefinition) {
		setColumnDefinitionGen(newColumnDefinition);
		if (newColumnDefinition != COLUMN_DEFINITION_EDEFAULT) {
			entity().makeDiscriminatorColumnForXmlNonNull();
		}
		setColumnDefinitionForXml(newColumnDefinition);
		if (isAllFeaturesUnset()) {
			entity().makeDiscriminatorColumnForXmlNull();
		}
	}

	/**
	 * Returns the value of the '<em><b>Default Length</b></em>' attribute.
	 * The default value is <code>"31"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Length</em>' attribute.
	 * @see #setDefaultLength(int)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIDiscriminatorColumn_DefaultLength()
	 * @model default="31"
	 * @generated
	 */
	public int getDefaultLength() {
		return defaultLength;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getDefaultLength <em>Default Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Length</em>' attribute.
	 * @see #getDefaultLength()
	 * @generated
	 */
	public void setDefaultLength(int newDefaultLength) {
		int oldDefaultLength = defaultLength;
		defaultLength = newDefaultLength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_DISCRIMINATOR_COLUMN__DEFAULT_LENGTH, oldDefaultLength, defaultLength));
	}

	/**
	 * Returns the value of the '<em><b>Specified Length</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Length</em>' attribute.
	 * @see #setSpecifiedLength(int)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIDiscriminatorColumn_SpecifiedLength()
	 * @model default="-1"
	 * @generated
	 */
	public int getSpecifiedLength() {
		return specifiedLength;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getSpecifiedLength <em>Specified Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Length</em>' attribute.
	 * @see #getSpecifiedLength()
	 * @generated
	 */
	public void setSpecifiedLengthGen(int newSpecifiedLength) {
		int oldSpecifiedLength = specifiedLength;
		specifiedLength = newSpecifiedLength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH, oldSpecifiedLength, specifiedLength));
	}

	public void setSpecifiedLength(int newSpecifiedLength) {
		setSpecifiedLengthGen(newSpecifiedLength);
		if (newSpecifiedLength != SPECIFIED_LENGTH_EDEFAULT) {
			entity().makeDiscriminatorColumnForXmlNonNull();
		}
		setSpecifiedLengthForXml(newSpecifiedLength);
		if (isAllFeaturesUnset()) {
			entity().makeDiscriminatorColumnForXmlNull();
		}
	}

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getIDiscriminatorColumn_Length()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public int getLength() {
		return (this.getSpecifiedLength() == -1) ? this.getDefaultLength() : this.getSpecifiedLength();
	}

	/**
	 * Returns the value of the '<em><b>Specified Name For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Name For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Name For Xml</em>' attribute.
	 * @see #setSpecifiedNameForXml(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlDiscriminatorColumn_SpecifiedNameForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public String getSpecifiedNameForXml() {
		return getSpecifiedName();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getSpecifiedNameForXml <em>Specified Name For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Name For Xml</em>' attribute.
	 * @see #getSpecifiedNameForXml()
	 * @generated NOT
	 */
	public void setSpecifiedNameForXml(String newSpecifiedNameForXml) {
		setSpecifiedNameGen(newSpecifiedNameForXml);
		if (eNotificationRequired())
			//pass in oldValue of null because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_NAME_FOR_XML, newSpecifiedNameForXml + " ", newSpecifiedNameForXml));
	}

	/**
	 * Returns the value of the '<em><b>Discriminator Type For Xml</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DiscriminatorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discriminator Type For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discriminator Type For Xml</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DiscriminatorType
	 * @see #setDiscriminatorTypeForXml(DiscriminatorType)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlDiscriminatorColumn_DiscriminatorTypeForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public DiscriminatorType getDiscriminatorTypeForXml() {
		return getDiscriminatorType();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getDiscriminatorTypeForXml <em>Discriminator Type For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discriminator Type For Xml</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DiscriminatorType
	 * @see #getDiscriminatorTypeForXml()
	 * @generated NOT
	 */
	public void setDiscriminatorTypeForXml(DiscriminatorType newDiscriminatorTypeForXml) {
		setDiscriminatorTypeGen(newDiscriminatorTypeForXml);
		if (eNotificationRequired())
			//pass in oldValue of null because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE_FOR_XML, null, newDiscriminatorTypeForXml));
	}

	/**
	 * Returns the value of the '<em><b>Column Definition For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Definition For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column Definition For Xml</em>' attribute.
	 * @see #setColumnDefinitionForXml(String)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlDiscriminatorColumn_ColumnDefinitionForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public String getColumnDefinitionForXml() {
		return getColumnDefinition();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getColumnDefinitionForXml <em>Column Definition For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Column Definition For Xml</em>' attribute.
	 * @see #getColumnDefinitionForXml()
	 * @generated NOT
	 */
	public void setColumnDefinitionForXml(String newColumnDefinitionForXml) {
		setColumnDefinitionGen(newColumnDefinitionForXml);
		if (eNotificationRequired())
			//pass in oldValue of null because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION_FOR_XML, newColumnDefinitionForXml + " ", newColumnDefinitionForXml));
	}

	/**
	 * Returns the value of the '<em><b>Specified Length For Xml</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Length For Xml</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Length For Xml</em>' attribute.
	 * @see #setSpecifiedLengthForXml(int)
	 * @see org.eclipse.jpt.core.internal.content.orm.OrmPackage#getXmlDiscriminatorColumn_SpecifiedLengthForXml()
	 * @model volatile="true"
	 * @generated NOT
	 */
	public int getSpecifiedLengthForXml() {
		return getSpecifiedLength();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.orm.XmlDiscriminatorColumn#getSpecifiedLengthForXml <em>Specified Length For Xml</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Length For Xml</em>' attribute.
	 * @see #getSpecifiedLengthForXml()
	 * @generated NOT
	 */
	public void setSpecifiedLengthForXml(int newSpecifiedLengthForXml) {
		setSpecifiedLengthGen(newSpecifiedLengthForXml);
		if (eNotificationRequired())
			//pass in oldValue of null because we don't store the value from the xml, see super.eNotify()
			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH_FOR_XML, -1, newSpecifiedLengthForXml));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DEFAULT_NAME :
				return getDefaultName();
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_NAME :
				return getSpecifiedName();
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__NAME :
				return getName();
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
				return getDiscriminatorType();
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION :
				return getColumnDefinition();
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DEFAULT_LENGTH :
				return new Integer(getDefaultLength());
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH :
				return new Integer(getSpecifiedLength());
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__LENGTH :
				return new Integer(getLength());
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_NAME_FOR_XML :
				return getSpecifiedNameForXml();
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE_FOR_XML :
				return getDiscriminatorTypeForXml();
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION_FOR_XML :
				return getColumnDefinitionForXml();
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH_FOR_XML :
				return new Integer(getSpecifiedLengthForXml());
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
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DEFAULT_NAME :
				setDefaultName((String) newValue);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_NAME :
				setSpecifiedName((String) newValue);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
				setDiscriminatorType((DiscriminatorType) newValue);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION :
				setColumnDefinition((String) newValue);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DEFAULT_LENGTH :
				setDefaultLength(((Integer) newValue).intValue());
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH :
				setSpecifiedLength(((Integer) newValue).intValue());
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_NAME_FOR_XML :
				setSpecifiedNameForXml((String) newValue);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE_FOR_XML :
				setDiscriminatorTypeForXml((DiscriminatorType) newValue);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION_FOR_XML :
				setColumnDefinitionForXml((String) newValue);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH_FOR_XML :
				setSpecifiedLengthForXml(((Integer) newValue).intValue());
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
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DEFAULT_NAME :
				setDefaultName(DEFAULT_NAME_EDEFAULT);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_NAME :
				setSpecifiedName(SPECIFIED_NAME_EDEFAULT);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
				setDiscriminatorType(DISCRIMINATOR_TYPE_EDEFAULT);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION :
				setColumnDefinition(COLUMN_DEFINITION_EDEFAULT);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DEFAULT_LENGTH :
				setDefaultLength(DEFAULT_LENGTH_EDEFAULT);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH :
				setSpecifiedLength(SPECIFIED_LENGTH_EDEFAULT);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_NAME_FOR_XML :
				setSpecifiedNameForXml(SPECIFIED_NAME_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE_FOR_XML :
				setDiscriminatorTypeForXml(DISCRIMINATOR_TYPE_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION_FOR_XML :
				setColumnDefinitionForXml(COLUMN_DEFINITION_FOR_XML_EDEFAULT);
				return;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH_FOR_XML :
				setSpecifiedLengthForXml(SPECIFIED_LENGTH_FOR_XML_EDEFAULT);
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
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DEFAULT_NAME :
				return DEFAULT_NAME_EDEFAULT == null ? defaultName != null : !DEFAULT_NAME_EDEFAULT.equals(defaultName);
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_NAME :
				return SPECIFIED_NAME_EDEFAULT == null ? specifiedName != null : !SPECIFIED_NAME_EDEFAULT.equals(specifiedName);
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__NAME :
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
				return discriminatorType != DISCRIMINATOR_TYPE_EDEFAULT;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION :
				return COLUMN_DEFINITION_EDEFAULT == null ? columnDefinition != null : !COLUMN_DEFINITION_EDEFAULT.equals(columnDefinition);
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DEFAULT_LENGTH :
				return defaultLength != DEFAULT_LENGTH_EDEFAULT;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH :
				return specifiedLength != SPECIFIED_LENGTH_EDEFAULT;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__LENGTH :
				return getLength() != LENGTH_EDEFAULT;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_NAME_FOR_XML :
				return SPECIFIED_NAME_FOR_XML_EDEFAULT == null ? getSpecifiedNameForXml() != null : !SPECIFIED_NAME_FOR_XML_EDEFAULT.equals(getSpecifiedNameForXml());
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE_FOR_XML :
				return getDiscriminatorTypeForXml() != DISCRIMINATOR_TYPE_FOR_XML_EDEFAULT;
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION_FOR_XML :
				return COLUMN_DEFINITION_FOR_XML_EDEFAULT == null ? getColumnDefinitionForXml() != null : !COLUMN_DEFINITION_FOR_XML_EDEFAULT.equals(getColumnDefinitionForXml());
			case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH_FOR_XML :
				return getSpecifiedLengthForXml() != SPECIFIED_LENGTH_FOR_XML_EDEFAULT;
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
		if (baseClass == IDiscriminatorColumn.class) {
			switch (derivedFeatureID) {
				case OrmPackage.XML_DISCRIMINATOR_COLUMN__DEFAULT_NAME :
					return JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__DEFAULT_NAME;
				case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_NAME :
					return JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__SPECIFIED_NAME;
				case OrmPackage.XML_DISCRIMINATOR_COLUMN__NAME :
					return JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__NAME;
				case OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
					return JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE;
				case OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION :
					return JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__COLUMN_DEFINITION;
				case OrmPackage.XML_DISCRIMINATOR_COLUMN__DEFAULT_LENGTH :
					return JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__DEFAULT_LENGTH;
				case OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH :
					return JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__SPECIFIED_LENGTH;
				case OrmPackage.XML_DISCRIMINATOR_COLUMN__LENGTH :
					return JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__LENGTH;
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
		if (baseClass == IDiscriminatorColumn.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__DEFAULT_NAME :
					return OrmPackage.XML_DISCRIMINATOR_COLUMN__DEFAULT_NAME;
				case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__SPECIFIED_NAME :
					return OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_NAME;
				case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__NAME :
					return OrmPackage.XML_DISCRIMINATOR_COLUMN__NAME;
				case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
					return OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE;
				case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__COLUMN_DEFINITION :
					return OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION;
				case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__DEFAULT_LENGTH :
					return OrmPackage.XML_DISCRIMINATOR_COLUMN__DEFAULT_LENGTH;
				case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__SPECIFIED_LENGTH :
					return OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH;
				case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__LENGTH :
					return OrmPackage.XML_DISCRIMINATOR_COLUMN__LENGTH;
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
		result.append(" (defaultName: ");
		result.append(defaultName);
		result.append(", specifiedName: ");
		result.append(specifiedName);
		result.append(", discriminatorType: ");
		result.append(discriminatorType);
		result.append(", columnDefinition: ");
		result.append(columnDefinition);
		result.append(", defaultLength: ");
		result.append(defaultLength);
		result.append(", specifiedLength: ");
		result.append(specifiedLength);
		result.append(')');
		return result.toString();
	}

	public Table dbTable() {
		return entity().primaryDbTable();
	}

	private XmlEntityInternal entity() {
		return (XmlEntityInternal) eContainer();
	}

	/**
	 * Call this when the table tag is removed from the xml,
	 * need to make sure all the model attributes are set to the default
	 */
	protected void unsetAllAttributes() {
		eUnset(OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH);
		eUnset(OrmPackage.XML_DISCRIMINATOR_COLUMN__SPECIFIED_NAME);
		eUnset(OrmPackage.XML_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION);
		eUnset(OrmPackage.XML_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE);
	}
} // XmlDiscriminatorColumn
