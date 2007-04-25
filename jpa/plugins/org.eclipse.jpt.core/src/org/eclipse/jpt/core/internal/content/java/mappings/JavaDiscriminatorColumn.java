/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.content.java.JavaEObject;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.EnumDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.mappings.DiscriminatorType;
import org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.INamedColumn;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.db.internal.Table;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Discriminator Column</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaDiscriminatorColumn()
 * @model kind="class"
 * @generated
 */
public class JavaDiscriminatorColumn extends JavaEObject
	implements IDiscriminatorColumn
{
	private Type type;

	private AnnotationElementAdapter discriminatorTypeAdapter;

	private AnnotationElementAdapter nameAdapter;

	private IntAnnotationElementAdapter lengthAdapter;

	private AnnotationElementAdapter columnDefinitionAdapter;

	private static final DeclarationAnnotationAdapter ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.DISCRIMINATOR_COLUMN);

	private static final DeclarationAnnotationElementAdapter DISCRIMINATOR_TYPE_ADAPTER = buildDiscriminatorTypeAdapter();

	private static final DeclarationAnnotationElementAdapter NAME_ADAPTER = buildAnnotationElementAdapter(JPA.DISCRIMINATOR_COLUMN__NAME);

	private static final DeclarationAnnotationElementAdapter LENGTH_ADAPTER = buildAnnotationElementAdapter(JPA.DISCRIMINATOR_COLUMN__LENGTH);

	private static final DeclarationAnnotationElementAdapter COLUMN_DEFINITION_ADAPTER = buildAnnotationElementAdapter(JPA.DISCRIMINATOR_COLUMN__COLUMN_DEFINITION);

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected JavaDiscriminatorColumn() {
		throw new UnsupportedOperationException();
	}

	protected JavaDiscriminatorColumn(Type type) {
		super();
		this.type = type;
		this.discriminatorTypeAdapter = new ShortCircuitAnnotationElementAdapter(this.type, DISCRIMINATOR_TYPE_ADAPTER);
		this.nameAdapter = new ShortCircuitAnnotationElementAdapter(this.type, NAME_ADAPTER);
		this.lengthAdapter = new IntAnnotationElementAdapter(new ShortCircuitAnnotationElementAdapter(this.type, LENGTH_ADAPTER));
		this.columnDefinitionAdapter = new ShortCircuitAnnotationElementAdapter(this.type, COLUMN_DEFINITION_ADAPTER);
	}

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(INamedColumn.class)) {
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_NAME :
				this.nameAdapter.setValue(notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
				this.discriminatorTypeAdapter.setValue(((DiscriminatorType) notification.getNewValue()).convertToJavaAnnotationValue());
				break;
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION :
				this.columnDefinitionAdapter.setValue(notification.getNewValue());
				break;
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH :
				this.lengthAdapter.setValue(notification.getNewIntValue());
				break;
			default :
				break;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_DISCRIMINATOR_COLUMN;
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIDiscriminatorColumn_DefaultName()
	 * @model
	 * @generated
	 */
	public String getDefaultName() {
		return defaultName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaDiscriminatorColumn#getDefaultName <em>Default Name</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DEFAULT_NAME, oldDefaultName, defaultName));
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIDiscriminatorColumn_SpecifiedName()
	 * @model
	 * @generated
	 */
	public String getSpecifiedName() {
		return specifiedName;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaDiscriminatorColumn#getSpecifiedName <em>Specified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Name</em>' attribute.
	 * @see #getSpecifiedName()
	 * @generated
	 */
	public void setSpecifiedName(String newSpecifiedName) {
		String oldSpecifiedName = specifiedName;
		specifiedName = newSpecifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_NAME, oldSpecifiedName, specifiedName));
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIDiscriminatorColumn_Name()
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIDiscriminatorColumn_DiscriminatorType()
	 * @model
	 * @generated
	 */
	public DiscriminatorType getDiscriminatorType() {
		return discriminatorType;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaDiscriminatorColumn#getDiscriminatorType <em>Discriminator Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discriminator Type</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DiscriminatorType
	 * @see #getDiscriminatorType()
	 * @generated
	 */
	public void setDiscriminatorType(DiscriminatorType newDiscriminatorType) {
		DiscriminatorType oldDiscriminatorType = discriminatorType;
		discriminatorType = newDiscriminatorType == null ? DISCRIMINATOR_TYPE_EDEFAULT : newDiscriminatorType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE, oldDiscriminatorType, discriminatorType));
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIDiscriminatorColumn_ColumnDefinition()
	 * @model
	 * @generated
	 */
	public String getColumnDefinition() {
		return columnDefinition;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaDiscriminatorColumn#getColumnDefinition <em>Column Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Column Definition</em>' attribute.
	 * @see #getColumnDefinition()
	 * @generated
	 */
	public void setColumnDefinition(String newColumnDefinition) {
		String oldColumnDefinition = columnDefinition;
		columnDefinition = newColumnDefinition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION, oldColumnDefinition, columnDefinition));
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIDiscriminatorColumn_DefaultLength()
	 * @model default="31"
	 * @generated
	 */
	public int getDefaultLength() {
		return defaultLength;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaDiscriminatorColumn#getDefaultLength <em>Default Length</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DEFAULT_LENGTH, oldDefaultLength, defaultLength));
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIDiscriminatorColumn_SpecifiedLength()
	 * @model default="-1"
	 * @generated
	 */
	public int getSpecifiedLength() {
		return specifiedLength;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaDiscriminatorColumn#getSpecifiedLength <em>Specified Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Length</em>' attribute.
	 * @see #getSpecifiedLength()
	 * @generated
	 */
	public void setSpecifiedLength(int newSpecifiedLength) {
		int oldSpecifiedLength = specifiedLength;
		specifiedLength = newSpecifiedLength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH, oldSpecifiedLength, specifiedLength));
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIDiscriminatorColumn_Length()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated NOT
	 */
	public int getLength() {
		return (this.getSpecifiedLength() == -1) ? this.getDefaultLength() : this.getSpecifiedLength();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DEFAULT_NAME :
				return getDefaultName();
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_NAME :
				return getSpecifiedName();
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__NAME :
				return getName();
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
				return getDiscriminatorType();
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION :
				return getColumnDefinition();
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DEFAULT_LENGTH :
				return new Integer(getDefaultLength());
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH :
				return new Integer(getSpecifiedLength());
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__LENGTH :
				return new Integer(getLength());
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
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DEFAULT_NAME :
				setDefaultName((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_NAME :
				setSpecifiedName((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
				setDiscriminatorType((DiscriminatorType) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION :
				setColumnDefinition((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DEFAULT_LENGTH :
				setDefaultLength(((Integer) newValue).intValue());
				return;
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH :
				setSpecifiedLength(((Integer) newValue).intValue());
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
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DEFAULT_NAME :
				setDefaultName(DEFAULT_NAME_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_NAME :
				setSpecifiedName(SPECIFIED_NAME_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
				setDiscriminatorType(DISCRIMINATOR_TYPE_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION :
				setColumnDefinition(COLUMN_DEFINITION_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DEFAULT_LENGTH :
				setDefaultLength(DEFAULT_LENGTH_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH :
				setSpecifiedLength(SPECIFIED_LENGTH_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DEFAULT_NAME :
				return DEFAULT_NAME_EDEFAULT == null ? defaultName != null : !DEFAULT_NAME_EDEFAULT.equals(defaultName);
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_NAME :
				return SPECIFIED_NAME_EDEFAULT == null ? specifiedName != null : !SPECIFIED_NAME_EDEFAULT.equals(specifiedName);
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__NAME :
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
				return discriminatorType != DISCRIMINATOR_TYPE_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION :
				return COLUMN_DEFINITION_EDEFAULT == null ? columnDefinition != null : !COLUMN_DEFINITION_EDEFAULT.equals(columnDefinition);
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DEFAULT_LENGTH :
				return defaultLength != DEFAULT_LENGTH_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH :
				return specifiedLength != SPECIFIED_LENGTH_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__LENGTH :
				return getLength() != LENGTH_EDEFAULT;
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
				case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DEFAULT_NAME :
					return JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__DEFAULT_NAME;
				case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_NAME :
					return JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__SPECIFIED_NAME;
				case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__NAME :
					return JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__NAME;
				case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
					return JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE;
				case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION :
					return JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__COLUMN_DEFINITION;
				case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DEFAULT_LENGTH :
					return JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__DEFAULT_LENGTH;
				case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH :
					return JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__SPECIFIED_LENGTH;
				case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__LENGTH :
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
					return JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DEFAULT_NAME;
				case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__SPECIFIED_NAME :
					return JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_NAME;
				case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__NAME :
					return JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__NAME;
				case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
					return JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE;
				case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__COLUMN_DEFINITION :
					return JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__COLUMN_DEFINITION;
				case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__DEFAULT_LENGTH :
					return JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DEFAULT_LENGTH;
				case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__SPECIFIED_LENGTH :
					return JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__SPECIFIED_LENGTH;
				case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__LENGTH :
					return JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__LENGTH;
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

	public ITextRange getTextRange() {
		return this.type.textRange();
	}

	public Table dbTable() {
		return entity().primaryDbTable();
	}

	private IEntity entity() {
		return (IEntity) eContainer();
	}

	// ********** java annotations -> persistence model **********
	public void updateFromJava(CompilationUnit astRoot) {
		setDiscriminatorType(DiscriminatorType.fromJavaAnnotationValue(this.discriminatorTypeAdapter.getValue(astRoot)));
		setSpecifiedName((String) this.nameAdapter.getValue(astRoot));
		setSpecifiedLength(this.lengthAdapter.getValue(astRoot));
		setColumnDefinition((String) this.columnDefinitionAdapter.getValue(astRoot));
	}

	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter buildDiscriminatorTypeAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(ANNOTATION_ADAPTER, JPA.DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE);
	}

	private static DeclarationAnnotationElementAdapter buildAnnotationElementAdapter(String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter(ANNOTATION_ADAPTER, elementName);
	}
} // JavaDiscriminatorColumn
