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
public class JavaDiscriminatorColumn extends JavaNamedColumn
	implements IDiscriminatorColumn
{
	private AnnotationElementAdapter discriminatorTypeAdapter;

	private IntAnnotationElementAdapter lengthAdapter;

	public static final DeclarationAnnotationAdapter ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.DISCRIMINATOR_COLUMN);

	private static final DeclarationAnnotationElementAdapter DISCRIMINATOR_TYPE_ADAPTER = buildDiscriminatorTypeAdapter();


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

	protected JavaDiscriminatorColumn() {
		throw new UnsupportedOperationException();
	}

	protected JavaDiscriminatorColumn(Owner owner, Type type, DeclarationAnnotationAdapter daa) {
		super(owner, type, daa);
		this.discriminatorTypeAdapter = buildShortCircuitElementAdapter(DISCRIMINATOR_TYPE_ADAPTER);
		this.lengthAdapter =  this.buildShortCircuitIntElementAdapter(JPA.DISCRIMINATOR_COLUMN__LENGTH);
	}

	@Override
	protected String nameElementName() {
		return JPA.DISCRIMINATOR_COLUMN__NAME;
	}
	
	@Override
	protected String columnDefinitionElementName() {
		return JPA.DISCRIMINATOR_COLUMN__COLUMN_DEFINITION;
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(IDiscriminatorColumn.class)) {
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
				this.discriminatorTypeAdapter.setValue(((DiscriminatorType) notification.getNewValue()).convertToJavaAnnotationValue());
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
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
				return getDiscriminatorType();
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
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
				setDiscriminatorType((DiscriminatorType) newValue);
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
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
				setDiscriminatorType(DISCRIMINATOR_TYPE_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
				return discriminatorType != DISCRIMINATOR_TYPE_EDEFAULT;
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
				case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
					return JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE;
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
				case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE :
					return JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE;
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
		result.append(" (discriminatorType: ");
		result.append(discriminatorType);
		result.append(", defaultLength: ");
		result.append(defaultLength);
		result.append(", specifiedLength: ");
		result.append(specifiedLength);
		result.append(')');
		return result.toString();
	}

	// ********** java annotations -> persistence model **********
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		setDiscriminatorType(DiscriminatorType.fromJavaAnnotationValue(this.discriminatorTypeAdapter.getValue(astRoot)));
		setSpecifiedLength(this.lengthAdapter.getValue(astRoot));
	}

	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter buildDiscriminatorTypeAdapter() {
		return new EnumDeclarationAnnotationElementAdapter(ANNOTATION_ADAPTER, JPA.DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE);
	}
} // JavaDiscriminatorColumn
