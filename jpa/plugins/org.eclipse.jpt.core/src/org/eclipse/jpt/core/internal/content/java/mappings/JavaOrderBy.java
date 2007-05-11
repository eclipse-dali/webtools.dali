/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.content.java.JavaEObject;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.IOrderBy;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.mappings.OrderingType;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Order By</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaOrderBy()
 * @model kind="class"
 * @generated
 */
public class JavaOrderBy extends JavaEObject implements IOrderBy
{
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final OrderingType TYPE_EDEFAULT = OrderingType.NONE;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected OrderingType type = TYPE_EDEFAULT;

	private final Member member;

	private final AnnotationAdapter annotationAdapter;

	private final AnnotationElementAdapter valueAdapter;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ORDER_BY);

	private static final DeclarationAnnotationElementAdapter VALUE_ADAPTER = buildValueAdapter();

	protected JavaOrderBy() {
		throw new UnsupportedOperationException("User JavaOrderBy(Member) instead");
	}

	protected JavaOrderBy(Member member) {
		super();
		this.member = member;
		this.annotationAdapter = this.buildOrderByAnnotationAdapter();
		this.valueAdapter = new ShortCircuitAnnotationElementAdapter(this.member, VALUE_ADAPTER);
	}

	private AnnotationAdapter buildOrderByAnnotationAdapter() {
		return new MemberAnnotationAdapter(this.member, DECLARATION_ANNOTATION_ADAPTER);
	}

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(IOrderBy.class)) {
			case JpaJavaMappingsPackage.JAVA_ORDER_BY__VALUE :
				if (getType() == OrderingType.CUSTOM) {
					this.valueAdapter.setValue(notification.getNewValue());
				}
				break;
			case JpaJavaMappingsPackage.JAVA_ORDER_BY__TYPE :
				OrderingType newOrderingType = (OrderingType) notification.getNewValue();
				if (newOrderingType == OrderingType.NONE) {
					if (this.annotationAdapter.getAnnotation() != null) {
						this.annotationAdapter.removeAnnotation();
					}
				}
				else if (newOrderingType == OrderingType.PRIMARY_KEY) {
					Annotation annotation = this.annotationAdapter.getAnnotation();
					if (annotation == null) {
						this.annotationAdapter.newMarkerAnnotation();
					}
					else if (annotation.isNormalAnnotation()) {
						if (((NormalAnnotation) annotation).values().size() != 0) {
							this.annotationAdapter.removeAnnotation();
							this.annotationAdapter.newMarkerAnnotation();
						}
					}
					else if (!annotation.isMarkerAnnotation()) {
						this.annotationAdapter.removeAnnotation();
						this.annotationAdapter.newMarkerAnnotation();
					}
				}
				else if (newOrderingType == OrderingType.CUSTOM) {
					Annotation annotation = this.annotationAdapter.getAnnotation();
					if (annotation == null) {
						this.annotationAdapter.newSingleMemberAnnotation();
						this.valueAdapter.setValue(getValue());
					}
					else if (!annotation.isSingleMemberAnnotation()) {
						this.annotationAdapter.removeAnnotation();
						this.annotationAdapter.newSingleMemberAnnotation();
						this.valueAdapter.setValue(getValue());
					}
				}
				else {
					throw new IllegalStateException("unknown 'orderBy' type: " + newOrderingType);
				}
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
		return JpaJavaMappingsPackage.Literals.JAVA_ORDER_BY;
	}

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIOrderBy_Value()
	 * @model
	 * @generated
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOrderBy#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ORDER_BY__VALUE, oldValue, value));
	}

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.OrderingType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.OrderingType
	 * @see #setType(OrderingType)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIOrderBy_Type()
	 * @model
	 * @generated
	 */
	public OrderingType getType() {
		return type;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOrderBy#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.OrderingType
	 * @see #getType()
	 * @generated
	 */
	public void setType(OrderingType newType) {
		OrderingType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ORDER_BY__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_ORDER_BY__VALUE :
				return getValue();
			case JpaJavaMappingsPackage.JAVA_ORDER_BY__TYPE :
				return getType();
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
			case JpaJavaMappingsPackage.JAVA_ORDER_BY__VALUE :
				setValue((String) newValue);
				return;
			case JpaJavaMappingsPackage.JAVA_ORDER_BY__TYPE :
				setType((OrderingType) newValue);
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
			case JpaJavaMappingsPackage.JAVA_ORDER_BY__VALUE :
				setValue(VALUE_EDEFAULT);
				return;
			case JpaJavaMappingsPackage.JAVA_ORDER_BY__TYPE :
				setType(TYPE_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_ORDER_BY__VALUE :
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case JpaJavaMappingsPackage.JAVA_ORDER_BY__TYPE :
				return type != TYPE_EDEFAULT;
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
		if (baseClass == IOrderBy.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_ORDER_BY__VALUE :
					return JpaCoreMappingsPackage.IORDER_BY__VALUE;
				case JpaJavaMappingsPackage.JAVA_ORDER_BY__TYPE :
					return JpaCoreMappingsPackage.IORDER_BY__TYPE;
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
		if (baseClass == IOrderBy.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IORDER_BY__VALUE :
					return JpaJavaMappingsPackage.JAVA_ORDER_BY__VALUE;
				case JpaCoreMappingsPackage.IORDER_BY__TYPE :
					return JpaJavaMappingsPackage.JAVA_ORDER_BY__TYPE;
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
		result.append(" (value: ");
		result.append(value);
		result.append(", type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

	private IMultiRelationshipMapping multiRelationshipMapping() {
		return (IMultiRelationshipMapping) eContainer();
	}

	public void refreshDefaults(DefaultsContext defaultsContext) {
		if (getType() == OrderingType.PRIMARY_KEY) {
			refreshValue(defaultsContext);
		}
	}

	//primary key ordering when just the @OrderBy annotation is present
	protected void refreshValue(DefaultsContext defaultsContext) {
		IEntity targetEntity = multiRelationshipMapping().getResolvedTargetEntity();
		if (targetEntity != null) {
			setValue(targetEntity.primaryKeyAttributeName() + " ASC");
		}
	}

	/*
	 * The @OrderBy annotation is a bit wack:
	 *     - no annotation at all means "no ordering"
	 *     - an annotation with no 'value' means "order by ascending primary key"
	 *     - an annotation with a 'value' means "order by the settings in the 'value' string"
	 */
	public void updateFromJava(CompilationUnit astRoot) {
		Annotation annotation = annotation(astRoot);
		if (annotation == null) {
			setType(OrderingType.NONE);
		}
		else if (annotation.isMarkerAnnotation()) {
			setType(OrderingType.PRIMARY_KEY);
		}
		else if (annotation.isSingleMemberAnnotation()) {
			setType(OrderingType.CUSTOM);
		}
		else if (annotation.isNormalAnnotation()) {
			if (((NormalAnnotation) annotation).values().size() == 0) {
				// an empty normal annotation is treated the same as a marker annotation
				setType(OrderingType.PRIMARY_KEY);
			}
			else {
				setType(OrderingType.CUSTOM);
			}
		}
		else {
			throw new IllegalStateException("unknown annotation type: " + annotation);
		}
		setValue((String) this.valueAdapter.getValue(astRoot));
	}

	/**
	 * allow owners to verify the annotation
	 */
	public Annotation annotation(CompilationUnit astRoot) {
		return this.annotationAdapter.getAnnotation(astRoot);
	}

	//******** IJpaSourceObject implementation *********/
	public ITextRange getTextRange() {
		return this.member.annotationTextRange(DECLARATION_ANNOTATION_ADAPTER);
	}

	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter buildValueAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA.ORDER_BY__VALUE, false);
	}
} // JavaOrderBy
