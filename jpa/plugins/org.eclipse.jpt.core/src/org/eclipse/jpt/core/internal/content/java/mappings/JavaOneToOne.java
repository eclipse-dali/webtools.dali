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

import java.util.Iterator;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.INonOwningMapping;
import org.eclipse.jpt.core.internal.mappings.IOneToOne;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.utility.internal.Filter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java One To One</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaOneToOne()
 * @model kind="class"
 * @generated
 */
public class JavaOneToOne extends JavaSingleRelationshipMapping
	implements IJavaOneToOne
{
	/**
	 * The default value of the '{@link #getMappedBy() <em>Mapped By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappedBy()
	 * @generated
	 * @ordered
	 */
	protected static final String MAPPED_BY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMappedBy() <em>Mapped By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappedBy()
	 * @generated
	 * @ordered
	 */
	protected String mappedBy = MAPPED_BY_EDEFAULT;

	private AnnotationElementAdapter<String> mappedByAdapter;

	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ONE_TO_ONE);

	private static final DeclarationAnnotationElementAdapter<String> TARGET_ENTITY_ADAPTER = buildTargetEntityAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_ONE__TARGET_ENTITY);

	private static final DeclarationAnnotationElementAdapter<String[]> CASCADE_ADAPTER = buildEnumArrayAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_ONE__CASCADE);

	private static final DeclarationAnnotationElementAdapter<String> FETCH_ADAPTER = buildEnumAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_ONE__FETCH);

	private static final DeclarationAnnotationElementAdapter<String> OPTIONAL_ADAPTER = buildOptionalAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_ONE__OPTIONAL);

	private static final DeclarationAnnotationElementAdapter<String> MAPPED_BY_ADAPTER = buildAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JPA.ONE_TO_ONE__MAPPED_BY);

	protected JavaOneToOne() {
		throw new UnsupportedOperationException("Use JavaOneToOne(Attribute) instead");
	}

	protected JavaOneToOne(Attribute attribute) {
		super(attribute);
		this.mappedByAdapter = this.buildAnnotationElementAdapter(MAPPED_BY_ADAPTER);
	}

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		switch (notification.getFeatureID(INonOwningMapping.class)) {
			case JpaCoreMappingsPackage.INON_OWNING_MAPPING__MAPPED_BY :
				this.mappedByAdapter.setValue((String) notification.getNewValue());
				break;
			default :
				break;
		}
	}

	// ********** initialization **********
	@Override
	protected DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return DECLARATION_ANNOTATION_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> targetEntityAdapter() {
		return TARGET_ENTITY_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String[]> cascadeAdapter() {
		return CASCADE_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> fetchAdapter() {
		return FETCH_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> optionalAdapter() {
		return OPTIONAL_ADAPTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_ONE_TO_ONE;
	}

	/**
	 * Returns the value of the '<em><b>Mapped By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapped By</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapped By</em>' attribute.
	 * @see #setMappedBy(String)
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getINonOwningMapping_MappedBy()
	 * @model
	 * @generated
	 */
	public String getMappedBy() {
		return mappedBy;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToOne#getMappedBy <em>Mapped By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapped By</em>' attribute.
	 * @see #getMappedBy()
	 * @generated
	 */
	public void setMappedBy(String newMappedBy) {
		String oldMappedBy = mappedBy;
		mappedBy = newMappedBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaJavaMappingsPackage.JAVA_ONE_TO_ONE__MAPPED_BY, oldMappedBy, mappedBy));
	}

	public boolean mappedByIsValid(IAttributeMapping mappedByMapping) {
		String mappedByKey = mappedByMapping.getKey();
		return (mappedByKey == IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}

	public ITextRange mappedByTextRange() {
		return this.elementTextRange(MAPPED_BY_ADAPTER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaJavaMappingsPackage.JAVA_ONE_TO_ONE__MAPPED_BY :
				return getMappedBy();
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
			case JpaJavaMappingsPackage.JAVA_ONE_TO_ONE__MAPPED_BY :
				setMappedBy((String) newValue);
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
			case JpaJavaMappingsPackage.JAVA_ONE_TO_ONE__MAPPED_BY :
				setMappedBy(MAPPED_BY_EDEFAULT);
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
			case JpaJavaMappingsPackage.JAVA_ONE_TO_ONE__MAPPED_BY :
				return MAPPED_BY_EDEFAULT == null ? mappedBy != null : !MAPPED_BY_EDEFAULT.equals(mappedBy);
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
		if (baseClass == INonOwningMapping.class) {
			switch (derivedFeatureID) {
				case JpaJavaMappingsPackage.JAVA_ONE_TO_ONE__MAPPED_BY :
					return JpaCoreMappingsPackage.INON_OWNING_MAPPING__MAPPED_BY;
				default :
					return -1;
			}
		}
		if (baseClass == IOneToOne.class) {
			switch (derivedFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IJavaOneToOne.class) {
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
		if (baseClass == INonOwningMapping.class) {
			switch (baseFeatureID) {
				case JpaCoreMappingsPackage.INON_OWNING_MAPPING__MAPPED_BY :
					return JpaJavaMappingsPackage.JAVA_ONE_TO_ONE__MAPPED_BY;
				default :
					return -1;
			}
		}
		if (baseClass == IOneToOne.class) {
			switch (baseFeatureID) {
				default :
					return -1;
			}
		}
		if (baseClass == IJavaOneToOne.class) {
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
		result.append(" (mappedBy: ");
		result.append(mappedBy);
		result.append(')');
		return result.toString();
	}

	public String getKey() {
		return IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		setMappedBy(this.mappedByAdapter.getValue(astRoot));
	}

	public boolean mappedByTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(MAPPED_BY_ADAPTER, pos, astRoot);
	}

	@Override
	public Iterator<String> candidateValuesFor(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.candidateValuesFor(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.mappedByTouches(pos, astRoot)) {
			return this.quotedCandidateMappedByAttributeNames(filter);
		}
		return null;
	}

	@Override
	public boolean isOverridableAssociationMapping() {
		return true;
	}
}
