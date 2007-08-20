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

import java.util.Iterator;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.mappings.IMappedSuperclass;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Mapped Superclass</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaMappedSuperclass()
 * @model kind="class"
 * @generated
 */
public class JavaMappedSuperclass extends JavaTypeMapping
	implements IMappedSuperclass
{
	/**
	 * The default value of the '{@link #getIdClass() <em>Id Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdClass()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIdClass() <em>Id Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdClass()
	 * @generated
	 * @ordered
	 */
	protected String idClass = ID_CLASS_EDEFAULT;

	private final AnnotationAdapter idClassAnnotationAdapter;

	private final AnnotationElementAdapter<String> idClassValueAdapter;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.MAPPED_SUPERCLASS);

	public static final DeclarationAnnotationAdapter ID_CLASS_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ID_CLASS);

	private static final DeclarationAnnotationElementAdapter<String> ID_CLASS_VALUE_ADAPTER = buildIdClassValueAdapter();

	protected JavaMappedSuperclass() {
		throw new UnsupportedOperationException("Use JavaMappedSuperclass(Type) instead");
	}

	protected JavaMappedSuperclass(Type type) {
		super(type);
		this.idClassAnnotationAdapter = new MemberAnnotationAdapter(this.getType(), ID_CLASS_ADAPTER);
		this.idClassValueAdapter = new ShortCircuitAnnotationElementAdapter<String>(this.getType(), ID_CLASS_VALUE_ADAPTER);
	}

	@Override
	public DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return DECLARATION_ANNOTATION_ADAPTER;
	}

	/**
	 * check for changes to the 'specifiedJoinColumns' and
	 * 'specifiedInverseJoinColumns' lists so we can notify the
	 * model adapter of any changes;
	 * also listen for changes to the 'defaultJoinColumns' and
	 * 'defaultInverseJoinColumns' lists so we can spank the developer
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(IMappedSuperclass.class)) {
			case JpaCoreMappingsPackage.IMAPPED_SUPERCLASS__ID_CLASS :
				String newIdClass = (String) notification.getNewValue();
				if (newIdClass == null) {
					this.idClassAnnotationAdapter.removeAnnotation();
				}
				else {
					this.idClassValueAdapter.setValue(newIdClass);
				}
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
		return JpaJavaMappingsPackage.Literals.JAVA_MAPPED_SUPERCLASS;
	}

	/**
	 * Returns the value of the '<em><b>Id Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id Class</em>' attribute.
	 * @see #setIdClass(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getIMappedSuperclass_IdClass()
	 * @model
	 * @generated
	 */
	public String getIdClass() {
		return idClass;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaMappedSuperclass#getIdClass <em>Id Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Class</em>' attribute.
	 * @see #getIdClass()
	 * @generated
	 */
	public void setIdClass(String newIdClass) {
		String oldIdClass = idClass;
		idClass = newIdClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_MAPPED_SUPERCLASS__ID_CLASS, oldIdClass, idClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_MAPPED_SUPERCLASS__ID_CLASS :
				return getIdClass();
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
			case JpaJavaMappingsPackage.JAVA_MAPPED_SUPERCLASS__ID_CLASS :
				setIdClass((String) newValue);
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
			case JpaJavaMappingsPackage.JAVA_MAPPED_SUPERCLASS__ID_CLASS :
				setIdClass(ID_CLASS_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_MAPPED_SUPERCLASS__ID_CLASS :
				return ID_CLASS_EDEFAULT == null ? idClass != null : !ID_CLASS_EDEFAULT.equals(idClass);
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
		if (baseClass == IMappedSuperclass.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_MAPPED_SUPERCLASS__ID_CLASS :
					return JpaCoreMappingsPackage.IMAPPED_SUPERCLASS__ID_CLASS;
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
		if (baseClass == IMappedSuperclass.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.IMAPPED_SUPERCLASS__ID_CLASS :
					return JpaJavaMappingsPackage.JAVA_MAPPED_SUPERCLASS__ID_CLASS;
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
		result.append(" (idClass: ");
		result.append(idClass);
		result.append(')');
		return result.toString();
	}

	public String getKey() {
		return IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}

	@Override
	public Iterator<String> overridableAttributeNames() {
		return this.namesOf(this.overridableAttributes());
	}

	private Iterator<IPersistentAttribute> overridableAttributes() {
		return new FilteringIterator<IPersistentAttribute>(this.getPersistentType().attributes()) {
			@Override
			protected boolean accept(Object o) {
				return ((IPersistentAttribute) o).isOverridableAttribute();
			}
		};
	}

	@Override
	public Iterator<String> overridableAssociationNames() {
		return this.namesOf(this.overridableAssociations());
	}

	private Iterator<IPersistentAttribute> overridableAssociations() {
		return new FilteringIterator<IPersistentAttribute>(this.getPersistentType().attributes()) {
			@Override
			protected boolean accept(Object o) {
				return ((IPersistentAttribute) o).isOverridableAssociation();
			}
		};
	}

	private Iterator<String> namesOf(Iterator<IPersistentAttribute> attributes) {
		return new TransformationIterator<IPersistentAttribute, String>(attributes) {
			@Override
			protected String transform(IPersistentAttribute attribute) {
				return attribute.getName();
			}
		};
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.updateIdClassFromJava(astRoot);
	}

	private void updateIdClassFromJava(CompilationUnit astRoot) {
		if (this.idClassAnnotationAdapter.getAnnotation(astRoot) == null) {
			this.setIdClass(null);
		}
		else {
			this.setIdClass(this.idClassValueAdapter.getValue(astRoot));
		}
	}

	// ********** static methods **********
	private static DeclarationAnnotationElementAdapter<String> buildIdClassValueAdapter() {
		return new ConversionDeclarationAnnotationElementAdapter<String>(ID_CLASS_ADAPTER, JPA.ID_CLASS__VALUE, false, SimpleTypeStringExpressionConverter.instance());
	}
}
