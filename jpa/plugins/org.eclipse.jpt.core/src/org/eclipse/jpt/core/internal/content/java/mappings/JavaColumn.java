/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Column</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaColumn()
 * @model kind="class"
 * @generated
 */
public class JavaColumn extends AbstractJavaColumn implements IColumn
{
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
	 * The default value of the '{@link #getPrecision() <em>Precision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrecision()
	 * @generated
	 * @ordered
	 */
	protected static final int PRECISION_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getSpecifiedPrecision() <em>Specified Precision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedPrecision()
	 * @generated
	 * @ordered
	 */
	protected static final int SPECIFIED_PRECISION_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getSpecifiedPrecision() <em>Specified Precision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedPrecision()
	 * @generated
	 * @ordered
	 */
	protected int specifiedPrecision = SPECIFIED_PRECISION_EDEFAULT;

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
	 * The default value of the '{@link #getSpecifiedScale() <em>Specified Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedScale()
	 * @generated
	 * @ordered
	 */
	protected static final int SPECIFIED_SCALE_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getSpecifiedScale() <em>Specified Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpecifiedScale()
	 * @generated
	 * @ordered
	 */
	protected int specifiedScale = SPECIFIED_SCALE_EDEFAULT;

	private final IntAnnotationElementAdapter lengthAdapter;

	private final IntAnnotationElementAdapter precisionAdapter;

	private final IntAnnotationElementAdapter scaleAdapter;

	// this adapter is only used by a Column annotation associated with a mapping annotation (e.g. Basic)
	public static final SimpleDeclarationAnnotationAdapter MAPPING_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.COLUMN);

	protected JavaColumn() {
		super();
		throw new UnsupportedOperationException("Use JavaColumn(Owner, Member, DeclarationAnnotationAdapter) instead");
	}

	protected JavaColumn(Owner owner, Member member, DeclarationAnnotationAdapter daa) {
		super(owner, member, daa);
		this.lengthAdapter = this.buildShortCircuitIntElementAdapter(JPA.COLUMN__LENGTH);
		this.precisionAdapter = this.buildShortCircuitIntElementAdapter(JPA.COLUMN__PRECISION);
		this.scaleAdapter = this.buildShortCircuitIntElementAdapter(JPA.COLUMN__SCALE);
	}

	@Override
	protected String nameElementName() {
		return JPA.COLUMN__NAME;
	}

	@Override
	protected String columnDefinitionElementName() {
		return JPA.COLUMN__COLUMN_DEFINITION;
	}

	@Override
	protected String tableElementName() {
		return JPA.COLUMN__TABLE;
	}

	@Override
	protected String uniqueElementName() {
		return JPA.COLUMN__UNIQUE;
	}

	@Override
	protected String nullableElementName() {
		return JPA.COLUMN__NULLABLE;
	}

	@Override
	protected String insertableElementName() {
		return JPA.COLUMN__INSERTABLE;
	}

	@Override
	protected String updatableElementName() {
		return JPA.COLUMN__UPDATABLE;
	}

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(IColumn.class)) {
			case JpaJavaMappingsPackage.JAVA_COLUMN__LENGTH :
				this.lengthAdapter.setValue(notification.getNewIntValue());
				break;
			case JpaJavaMappingsPackage.JAVA_COLUMN__PRECISION :
				this.precisionAdapter.setValue(notification.getNewIntValue());
				break;
			case JpaJavaMappingsPackage.JAVA_COLUMN__SCALE :
				this.scaleAdapter.setValue(notification.getNewIntValue());
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
		return JpaJavaMappingsPackage.Literals.JAVA_COLUMN;
	}

	public int getLength() {
		return (this.getSpecifiedLength() == SPECIFIED_LENGTH_EDEFAULT) ? getDefaultLength() : this.getSpecifiedLength();
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIColumn_SpecifiedLength()
	 * @model default="-1"
	 * @generated
	 */
	public int getSpecifiedLength() {
		return specifiedLength;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaColumn#getSpecifiedLength <em>Specified Length</em>}' attribute.
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
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_LENGTH, oldSpecifiedLength, specifiedLength));
	}

	public int getPrecision() {
		return (this.getSpecifiedPrecision() == SPECIFIED_PRECISION_EDEFAULT) ? getDefaultPrecision() : this.getSpecifiedPrecision();
	}

	/**
	 * Returns the value of the '<em><b>Specified Precision</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Precision</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Precision</em>' attribute.
	 * @see #setSpecifiedPrecision(int)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIColumn_SpecifiedPrecision()
	 * @model default="-1"
	 * @generated
	 */
	public int getSpecifiedPrecision() {
		return specifiedPrecision;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaColumn#getSpecifiedPrecision <em>Specified Precision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Precision</em>' attribute.
	 * @see #getSpecifiedPrecision()
	 * @generated
	 */
	public void setSpecifiedPrecision(int newSpecifiedPrecision) {
		int oldSpecifiedPrecision = specifiedPrecision;
		specifiedPrecision = newSpecifiedPrecision;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_PRECISION, oldSpecifiedPrecision, specifiedPrecision));
	}

	public int getScale() {
		return (this.getSpecifiedScale() == SPECIFIED_SCALE_EDEFAULT) ? getDefaultScale() : this.getSpecifiedScale();
	}

	/**
	 * Returns the value of the '<em><b>Specified Scale</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Scale</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Scale</em>' attribute.
	 * @see #setSpecifiedScale(int)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIColumn_SpecifiedScale()
	 * @model default="-1"
	 * @generated
	 */
	public int getSpecifiedScale() {
		return specifiedScale;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaColumn#getSpecifiedScale <em>Specified Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Scale</em>' attribute.
	 * @see #getSpecifiedScale()
	 * @generated
	 */
	public void setSpecifiedScale(int newSpecifiedScale) {
		int oldSpecifiedScale = specifiedScale;
		specifiedScale = newSpecifiedScale;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_SCALE, oldSpecifiedScale, specifiedScale));
	}

	public int getDefaultLength() {
		return DEFAULT_LENGTH;
	}

	public int getDefaultPrecision() {
		return DEFAULT_PRECISION;
	}

	public int getDefaultScale() {
		return DEFAULT_SCALE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_COLUMN__LENGTH :
				return new Integer(getLength());
			case JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_LENGTH :
				return new Integer(getSpecifiedLength());
			case JpaJavaMappingsPackage.JAVA_COLUMN__PRECISION :
				return new Integer(getPrecision());
			case JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_PRECISION :
				return new Integer(getSpecifiedPrecision());
			case JpaJavaMappingsPackage.JAVA_COLUMN__SCALE :
				return new Integer(getScale());
			case JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_SCALE :
				return new Integer(getSpecifiedScale());
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
			case JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_LENGTH :
				setSpecifiedLength(((Integer) newValue).intValue());
				return;
			case JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_PRECISION :
				setSpecifiedPrecision(((Integer) newValue).intValue());
				return;
			case JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_SCALE :
				setSpecifiedScale(((Integer) newValue).intValue());
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
			case JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_LENGTH :
				setSpecifiedLength(SPECIFIED_LENGTH_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_PRECISION :
				setSpecifiedPrecision(SPECIFIED_PRECISION_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_SCALE :
				setSpecifiedScale(SPECIFIED_SCALE_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_COLUMN__LENGTH :
				return getLength() != LENGTH_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_LENGTH :
				return specifiedLength != SPECIFIED_LENGTH_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_COLUMN__PRECISION :
				return getPrecision() != PRECISION_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_PRECISION :
				return specifiedPrecision != SPECIFIED_PRECISION_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_COLUMN__SCALE :
				return getScale() != SCALE_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_SCALE :
				return specifiedScale != SPECIFIED_SCALE_EDEFAULT;
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
				case JpaJavaMappingsPackage.JAVA_COLUMN__LENGTH :
					return JpaCoreMappingsPackage.ICOLUMN__LENGTH;
				case JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_LENGTH :
					return JpaCoreMappingsPackage.ICOLUMN__SPECIFIED_LENGTH;
				case JpaJavaMappingsPackage.JAVA_COLUMN__PRECISION :
					return JpaCoreMappingsPackage.ICOLUMN__PRECISION;
				case JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_PRECISION :
					return JpaCoreMappingsPackage.ICOLUMN__SPECIFIED_PRECISION;
				case JpaJavaMappingsPackage.JAVA_COLUMN__SCALE :
					return JpaCoreMappingsPackage.ICOLUMN__SCALE;
				case JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_SCALE :
					return JpaCoreMappingsPackage.ICOLUMN__SPECIFIED_SCALE;
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
					return JpaJavaMappingsPackage.JAVA_COLUMN__LENGTH;
				case JpaCoreMappingsPackage.ICOLUMN__SPECIFIED_LENGTH :
					return JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_LENGTH;
				case JpaCoreMappingsPackage.ICOLUMN__PRECISION :
					return JpaJavaMappingsPackage.JAVA_COLUMN__PRECISION;
				case JpaCoreMappingsPackage.ICOLUMN__SPECIFIED_PRECISION :
					return JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_PRECISION;
				case JpaCoreMappingsPackage.ICOLUMN__SCALE :
					return JpaJavaMappingsPackage.JAVA_COLUMN__SCALE;
				case JpaCoreMappingsPackage.ICOLUMN__SPECIFIED_SCALE :
					return JpaJavaMappingsPackage.JAVA_COLUMN__SPECIFIED_SCALE;
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
		result.append(" (specifiedLength: ");
		result.append(specifiedLength);
		result.append(", specifiedPrecision: ");
		result.append(specifiedPrecision);
		result.append(", specifiedScale: ");
		result.append(specifiedScale);
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean tableIsAllowed() {
		return true;
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setSpecifiedLength(this.lengthAdapter.getValue(astRoot));
		this.setSpecifiedPrecision(this.precisionAdapter.getValue(astRoot));
		this.setSpecifiedScale(this.scaleAdapter.getValue(astRoot));
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {
		this.setDefaultTable((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_COLUMN_TABLE_KEY));
		this.setDefaultName((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_COLUMN_NAME_KEY));
	}

	// ********** static methods **********
	static JavaColumn createColumnMappingColumn(Owner owner, Member member) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaColumn(owner, member, MAPPING_DECLARATION_ANNOTATION_ADAPTER);
	}

	static JavaColumn createAttributeOverrideColumn(Owner owner, Member member, DeclarationAnnotationAdapter attributeOverrideAnnotationAdapter) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaColumn(owner, member, buildAttributeOverrideAnnotationAdapter(attributeOverrideAnnotationAdapter));
	}

	private static DeclarationAnnotationAdapter buildAttributeOverrideAnnotationAdapter(DeclarationAnnotationAdapter attributeOverrideAnnotationAdapter) {
		return new NestedDeclarationAnnotationAdapter(attributeOverrideAnnotationAdapter, JPA.ATTRIBUTE_OVERRIDE__COLUMN, JPA.COLUMN);
	}
} // JavaColumn
