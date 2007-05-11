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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIColumn_Length()
	 * @model default="255"
	 * @generated
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaColumn#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	public void setLength(int newLength) {
		int oldLength = length;
		length = newLength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_COLUMN__LENGTH, oldLength, length));
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIColumn_Precision()
	 * @model
	 * @generated
	 */
	public int getPrecision() {
		return precision;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaColumn#getPrecision <em>Precision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Precision</em>' attribute.
	 * @see #getPrecision()
	 * @generated
	 */
	public void setPrecision(int newPrecision) {
		int oldPrecision = precision;
		precision = newPrecision;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_COLUMN__PRECISION, oldPrecision, precision));
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
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIColumn_Scale()
	 * @model
	 * @generated
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaColumn#getScale <em>Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scale</em>' attribute.
	 * @see #getScale()
	 * @generated
	 */
	public void setScale(int newScale) {
		int oldScale = scale;
		scale = newScale;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_COLUMN__SCALE, oldScale, scale));
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
			case JpaJavaMappingsPackage.JAVA_COLUMN__PRECISION :
				return new Integer(getPrecision());
			case JpaJavaMappingsPackage.JAVA_COLUMN__SCALE :
				return new Integer(getScale());
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
			case JpaJavaMappingsPackage.JAVA_COLUMN__LENGTH :
				setLength(((Integer) newValue).intValue());
				return;
			case JpaJavaMappingsPackage.JAVA_COLUMN__PRECISION :
				setPrecision(((Integer) newValue).intValue());
				return;
			case JpaJavaMappingsPackage.JAVA_COLUMN__SCALE :
				setScale(((Integer) newValue).intValue());
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
			case JpaJavaMappingsPackage.JAVA_COLUMN__LENGTH :
				setLength(LENGTH_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_COLUMN__PRECISION :
				setPrecision(PRECISION_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_COLUMN__SCALE :
				setScale(SCALE_EDEFAULT);
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
				return length != LENGTH_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_COLUMN__PRECISION :
				return precision != PRECISION_EDEFAULT;
			case JpaJavaMappingsPackage.JAVA_COLUMN__SCALE :
				return scale != SCALE_EDEFAULT;
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
				case JpaJavaMappingsPackage.JAVA_COLUMN__PRECISION :
					return JpaCoreMappingsPackage.ICOLUMN__PRECISION;
				case JpaJavaMappingsPackage.JAVA_COLUMN__SCALE :
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
					return JpaJavaMappingsPackage.JAVA_COLUMN__LENGTH;
				case JpaCoreMappingsPackage.ICOLUMN__PRECISION :
					return JpaJavaMappingsPackage.JAVA_COLUMN__PRECISION;
				case JpaCoreMappingsPackage.ICOLUMN__SCALE :
					return JpaJavaMappingsPackage.JAVA_COLUMN__SCALE;
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

	@Override
	public boolean tableIsAllowed() {
		return true;
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setLength(this.lengthAdapter.getValue(astRoot));
		this.setPrecision(this.precisionAdapter.getValue(astRoot));
		this.setScale(this.scaleAdapter.getValue(astRoot));
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
