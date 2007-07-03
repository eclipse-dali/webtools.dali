/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings.util;

import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IJpaEObject;
import org.eclipse.jpt.core.internal.IJpaSourceObject;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.JpaEObject;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.content.java.JavaEObject;
import org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaColumn;
import org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaTable;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaAbstractQuery;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaAssociationOverride;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeOverride;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaCascade;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaColumn;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaDiscriminatorColumn;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddable;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbedded;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddedId;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaGeneratedValue;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaGenerator;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaId;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinColumn;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinTable;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToMany;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToOne;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaMappedSuperclass;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedColumn;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedNativeQuery;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedQuery;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaNullAttributeMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaNullTypeMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToMany;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToOne;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaOverride;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaQueryHint;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaRelationshipMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaSecondaryTable;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaSequenceGenerator;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaSingleRelationshipMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaTable;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaTableGenerator;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaTransient;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaTypeMapping;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaUniqueConstraint;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaVersion;
import org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage;
import org.eclipse.jpt.core.internal.mappings.IAbstractColumn;
import org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IAssociationOverride;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IBasic;
import org.eclipse.jpt.core.internal.mappings.ICascade;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.IColumnMapping;
import org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn;
import org.eclipse.jpt.core.internal.mappings.IEmbeddable;
import org.eclipse.jpt.core.internal.mappings.IEmbedded;
import org.eclipse.jpt.core.internal.mappings.IEmbeddedId;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IGeneratedValue;
import org.eclipse.jpt.core.internal.mappings.IGenerator;
import org.eclipse.jpt.core.internal.mappings.IId;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IJoinTable;
import org.eclipse.jpt.core.internal.mappings.IManyToMany;
import org.eclipse.jpt.core.internal.mappings.IManyToOne;
import org.eclipse.jpt.core.internal.mappings.IMappedSuperclass;
import org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.INamedColumn;
import org.eclipse.jpt.core.internal.mappings.INamedNativeQuery;
import org.eclipse.jpt.core.internal.mappings.INamedQuery;
import org.eclipse.jpt.core.internal.mappings.INonOwningMapping;
import org.eclipse.jpt.core.internal.mappings.IOneToMany;
import org.eclipse.jpt.core.internal.mappings.IOneToOne;
import org.eclipse.jpt.core.internal.mappings.IOverride;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IQuery;
import org.eclipse.jpt.core.internal.mappings.IQueryHint;
import org.eclipse.jpt.core.internal.mappings.IRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.ISecondaryTable;
import org.eclipse.jpt.core.internal.mappings.ISequenceGenerator;
import org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.mappings.ITableGenerator;
import org.eclipse.jpt.core.internal.mappings.ITransient;
import org.eclipse.jpt.core.internal.mappings.IUniqueConstraint;
import org.eclipse.jpt.core.internal.mappings.IVersion;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage
 * @generated
 */
public class JpaJavaMappingsSwitch<T>
{
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static JpaJavaMappingsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JpaJavaMappingsSwitch() {
		if (modelPackage == null) {
			modelPackage = JpaJavaMappingsPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return eSuperTypes.isEmpty() ? defaultCase(theEObject) : doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case JpaJavaMappingsPackage.JAVA_TYPE_MAPPING : {
				JavaTypeMapping javaTypeMapping = (JavaTypeMapping) theEObject;
				T result = caseJavaTypeMapping(javaTypeMapping);
				if (result == null)
					result = caseJavaEObject(javaTypeMapping);
				if (result == null)
					result = caseIJavaTypeMapping(javaTypeMapping);
				if (result == null)
					result = caseJpaEObject(javaTypeMapping);
				if (result == null)
					result = caseIJpaSourceObject(javaTypeMapping);
				if (result == null)
					result = caseITypeMapping(javaTypeMapping);
				if (result == null)
					result = caseIJpaEObject(javaTypeMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_ENTITY : {
				JavaEntity javaEntity = (JavaEntity) theEObject;
				T result = caseJavaEntity(javaEntity);
				if (result == null)
					result = caseJavaTypeMapping(javaEntity);
				if (result == null)
					result = caseIEntity(javaEntity);
				if (result == null)
					result = caseJavaEObject(javaEntity);
				if (result == null)
					result = caseIJavaTypeMapping(javaEntity);
				if (result == null)
					result = caseITypeMapping(javaEntity);
				if (result == null)
					result = caseJpaEObject(javaEntity);
				if (result == null)
					result = caseIJpaSourceObject(javaEntity);
				if (result == null)
					result = caseIJpaEObject(javaEntity);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_MAPPED_SUPERCLASS : {
				JavaMappedSuperclass javaMappedSuperclass = (JavaMappedSuperclass) theEObject;
				T result = caseJavaMappedSuperclass(javaMappedSuperclass);
				if (result == null)
					result = caseJavaTypeMapping(javaMappedSuperclass);
				if (result == null)
					result = caseIMappedSuperclass(javaMappedSuperclass);
				if (result == null)
					result = caseJavaEObject(javaMappedSuperclass);
				if (result == null)
					result = caseIJavaTypeMapping(javaMappedSuperclass);
				if (result == null)
					result = caseITypeMapping(javaMappedSuperclass);
				if (result == null)
					result = caseJpaEObject(javaMappedSuperclass);
				if (result == null)
					result = caseIJpaSourceObject(javaMappedSuperclass);
				if (result == null)
					result = caseIJpaEObject(javaMappedSuperclass);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_EMBEDDABLE : {
				JavaEmbeddable javaEmbeddable = (JavaEmbeddable) theEObject;
				T result = caseJavaEmbeddable(javaEmbeddable);
				if (result == null)
					result = caseJavaTypeMapping(javaEmbeddable);
				if (result == null)
					result = caseIEmbeddable(javaEmbeddable);
				if (result == null)
					result = caseJavaEObject(javaEmbeddable);
				if (result == null)
					result = caseIJavaTypeMapping(javaEmbeddable);
				if (result == null)
					result = caseITypeMapping(javaEmbeddable);
				if (result == null)
					result = caseJpaEObject(javaEmbeddable);
				if (result == null)
					result = caseIJpaSourceObject(javaEmbeddable);
				if (result == null)
					result = caseIJpaEObject(javaEmbeddable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_NULL_TYPE_MAPPING : {
				JavaNullTypeMapping javaNullTypeMapping = (JavaNullTypeMapping) theEObject;
				T result = caseJavaNullTypeMapping(javaNullTypeMapping);
				if (result == null)
					result = caseJavaTypeMapping(javaNullTypeMapping);
				if (result == null)
					result = caseJavaEObject(javaNullTypeMapping);
				if (result == null)
					result = caseIJavaTypeMapping(javaNullTypeMapping);
				if (result == null)
					result = caseJpaEObject(javaNullTypeMapping);
				if (result == null)
					result = caseIJpaSourceObject(javaNullTypeMapping);
				if (result == null)
					result = caseITypeMapping(javaNullTypeMapping);
				if (result == null)
					result = caseIJpaEObject(javaNullTypeMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_ATTRIBUTE_MAPPING : {
				JavaAttributeMapping javaAttributeMapping = (JavaAttributeMapping) theEObject;
				T result = caseJavaAttributeMapping(javaAttributeMapping);
				if (result == null)
					result = caseJavaEObject(javaAttributeMapping);
				if (result == null)
					result = caseIJavaAttributeMapping(javaAttributeMapping);
				if (result == null)
					result = caseJpaEObject(javaAttributeMapping);
				if (result == null)
					result = caseIJpaSourceObject(javaAttributeMapping);
				if (result == null)
					result = caseIAttributeMapping(javaAttributeMapping);
				if (result == null)
					result = caseIJpaEObject(javaAttributeMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_BASIC : {
				JavaBasic javaBasic = (JavaBasic) theEObject;
				T result = caseJavaBasic(javaBasic);
				if (result == null)
					result = caseJavaAttributeMapping(javaBasic);
				if (result == null)
					result = caseIBasic(javaBasic);
				if (result == null)
					result = caseJavaEObject(javaBasic);
				if (result == null)
					result = caseIJavaAttributeMapping(javaBasic);
				if (result == null)
					result = caseIAttributeMapping(javaBasic);
				if (result == null)
					result = caseIColumnMapping(javaBasic);
				if (result == null)
					result = caseJpaEObject(javaBasic);
				if (result == null)
					result = caseIJpaSourceObject(javaBasic);
				if (result == null)
					result = caseIJpaEObject(javaBasic);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_ID : {
				JavaId javaId = (JavaId) theEObject;
				T result = caseJavaId(javaId);
				if (result == null)
					result = caseJavaAttributeMapping(javaId);
				if (result == null)
					result = caseIId(javaId);
				if (result == null)
					result = caseJavaEObject(javaId);
				if (result == null)
					result = caseIJavaAttributeMapping(javaId);
				if (result == null)
					result = caseIAttributeMapping(javaId);
				if (result == null)
					result = caseIColumnMapping(javaId);
				if (result == null)
					result = caseJpaEObject(javaId);
				if (result == null)
					result = caseIJpaSourceObject(javaId);
				if (result == null)
					result = caseIJpaEObject(javaId);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_TRANSIENT : {
				JavaTransient javaTransient = (JavaTransient) theEObject;
				T result = caseJavaTransient(javaTransient);
				if (result == null)
					result = caseJavaAttributeMapping(javaTransient);
				if (result == null)
					result = caseITransient(javaTransient);
				if (result == null)
					result = caseJavaEObject(javaTransient);
				if (result == null)
					result = caseIJavaAttributeMapping(javaTransient);
				if (result == null)
					result = caseIAttributeMapping(javaTransient);
				if (result == null)
					result = caseJpaEObject(javaTransient);
				if (result == null)
					result = caseIJpaSourceObject(javaTransient);
				if (result == null)
					result = caseIJpaEObject(javaTransient);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_VERSION : {
				JavaVersion javaVersion = (JavaVersion) theEObject;
				T result = caseJavaVersion(javaVersion);
				if (result == null)
					result = caseJavaAttributeMapping(javaVersion);
				if (result == null)
					result = caseIVersion(javaVersion);
				if (result == null)
					result = caseJavaEObject(javaVersion);
				if (result == null)
					result = caseIJavaAttributeMapping(javaVersion);
				if (result == null)
					result = caseIAttributeMapping(javaVersion);
				if (result == null)
					result = caseIColumnMapping(javaVersion);
				if (result == null)
					result = caseJpaEObject(javaVersion);
				if (result == null)
					result = caseIJpaSourceObject(javaVersion);
				if (result == null)
					result = caseIJpaEObject(javaVersion);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_EMBEDDED_ID : {
				JavaEmbeddedId javaEmbeddedId = (JavaEmbeddedId) theEObject;
				T result = caseJavaEmbeddedId(javaEmbeddedId);
				if (result == null)
					result = caseJavaAttributeMapping(javaEmbeddedId);
				if (result == null)
					result = caseIEmbeddedId(javaEmbeddedId);
				if (result == null)
					result = caseJavaEObject(javaEmbeddedId);
				if (result == null)
					result = caseIJavaAttributeMapping(javaEmbeddedId);
				if (result == null)
					result = caseIAttributeMapping(javaEmbeddedId);
				if (result == null)
					result = caseJpaEObject(javaEmbeddedId);
				if (result == null)
					result = caseIJpaSourceObject(javaEmbeddedId);
				if (result == null)
					result = caseIJpaEObject(javaEmbeddedId);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_EMBEDDED : {
				JavaEmbedded javaEmbedded = (JavaEmbedded) theEObject;
				T result = caseJavaEmbedded(javaEmbedded);
				if (result == null)
					result = caseJavaAttributeMapping(javaEmbedded);
				if (result == null)
					result = caseIEmbedded(javaEmbedded);
				if (result == null)
					result = caseJavaEObject(javaEmbedded);
				if (result == null)
					result = caseIJavaAttributeMapping(javaEmbedded);
				if (result == null)
					result = caseIAttributeMapping(javaEmbedded);
				if (result == null)
					result = caseJpaEObject(javaEmbedded);
				if (result == null)
					result = caseIJpaSourceObject(javaEmbedded);
				if (result == null)
					result = caseIJpaEObject(javaEmbedded);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_RELATIONSHIP_MAPPING : {
				JavaRelationshipMapping javaRelationshipMapping = (JavaRelationshipMapping) theEObject;
				T result = caseJavaRelationshipMapping(javaRelationshipMapping);
				if (result == null)
					result = caseJavaAttributeMapping(javaRelationshipMapping);
				if (result == null)
					result = caseIRelationshipMapping(javaRelationshipMapping);
				if (result == null)
					result = caseJavaEObject(javaRelationshipMapping);
				if (result == null)
					result = caseIJavaAttributeMapping(javaRelationshipMapping);
				if (result == null)
					result = caseIAttributeMapping(javaRelationshipMapping);
				if (result == null)
					result = caseJpaEObject(javaRelationshipMapping);
				if (result == null)
					result = caseIJpaSourceObject(javaRelationshipMapping);
				if (result == null)
					result = caseIJpaEObject(javaRelationshipMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_SINGLE_RELATIONSHIP_MAPPING : {
				JavaSingleRelationshipMapping javaSingleRelationshipMapping = (JavaSingleRelationshipMapping) theEObject;
				T result = caseJavaSingleRelationshipMapping(javaSingleRelationshipMapping);
				if (result == null)
					result = caseJavaRelationshipMapping(javaSingleRelationshipMapping);
				if (result == null)
					result = caseISingleRelationshipMapping(javaSingleRelationshipMapping);
				if (result == null)
					result = caseJavaAttributeMapping(javaSingleRelationshipMapping);
				if (result == null)
					result = caseIRelationshipMapping(javaSingleRelationshipMapping);
				if (result == null)
					result = caseJavaEObject(javaSingleRelationshipMapping);
				if (result == null)
					result = caseIJavaAttributeMapping(javaSingleRelationshipMapping);
				if (result == null)
					result = caseIAttributeMapping(javaSingleRelationshipMapping);
				if (result == null)
					result = caseJpaEObject(javaSingleRelationshipMapping);
				if (result == null)
					result = caseIJpaSourceObject(javaSingleRelationshipMapping);
				if (result == null)
					result = caseIJpaEObject(javaSingleRelationshipMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_MANY_TO_ONE : {
				JavaManyToOne javaManyToOne = (JavaManyToOne) theEObject;
				T result = caseJavaManyToOne(javaManyToOne);
				if (result == null)
					result = caseJavaSingleRelationshipMapping(javaManyToOne);
				if (result == null)
					result = caseIManyToOne(javaManyToOne);
				if (result == null)
					result = caseJavaRelationshipMapping(javaManyToOne);
				if (result == null)
					result = caseISingleRelationshipMapping(javaManyToOne);
				if (result == null)
					result = caseJavaAttributeMapping(javaManyToOne);
				if (result == null)
					result = caseIRelationshipMapping(javaManyToOne);
				if (result == null)
					result = caseJavaEObject(javaManyToOne);
				if (result == null)
					result = caseIJavaAttributeMapping(javaManyToOne);
				if (result == null)
					result = caseIAttributeMapping(javaManyToOne);
				if (result == null)
					result = caseJpaEObject(javaManyToOne);
				if (result == null)
					result = caseIJpaSourceObject(javaManyToOne);
				if (result == null)
					result = caseIJpaEObject(javaManyToOne);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_ONE_TO_ONE : {
				JavaOneToOne javaOneToOne = (JavaOneToOne) theEObject;
				T result = caseJavaOneToOne(javaOneToOne);
				if (result == null)
					result = caseJavaSingleRelationshipMapping(javaOneToOne);
				if (result == null)
					result = caseIOneToOne(javaOneToOne);
				if (result == null)
					result = caseJavaRelationshipMapping(javaOneToOne);
				if (result == null)
					result = caseISingleRelationshipMapping(javaOneToOne);
				if (result == null)
					result = caseINonOwningMapping(javaOneToOne);
				if (result == null)
					result = caseJavaAttributeMapping(javaOneToOne);
				if (result == null)
					result = caseIRelationshipMapping(javaOneToOne);
				if (result == null)
					result = caseJavaEObject(javaOneToOne);
				if (result == null)
					result = caseIJavaAttributeMapping(javaOneToOne);
				if (result == null)
					result = caseIAttributeMapping(javaOneToOne);
				if (result == null)
					result = caseJpaEObject(javaOneToOne);
				if (result == null)
					result = caseIJpaSourceObject(javaOneToOne);
				if (result == null)
					result = caseIJpaEObject(javaOneToOne);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_MULTI_RELATIONSHIP_MAPPING : {
				JavaMultiRelationshipMapping javaMultiRelationshipMapping = (JavaMultiRelationshipMapping) theEObject;
				T result = caseJavaMultiRelationshipMapping(javaMultiRelationshipMapping);
				if (result == null)
					result = caseJavaRelationshipMapping(javaMultiRelationshipMapping);
				if (result == null)
					result = caseIMultiRelationshipMapping(javaMultiRelationshipMapping);
				if (result == null)
					result = caseJavaAttributeMapping(javaMultiRelationshipMapping);
				if (result == null)
					result = caseIRelationshipMapping(javaMultiRelationshipMapping);
				if (result == null)
					result = caseINonOwningMapping(javaMultiRelationshipMapping);
				if (result == null)
					result = caseJavaEObject(javaMultiRelationshipMapping);
				if (result == null)
					result = caseIJavaAttributeMapping(javaMultiRelationshipMapping);
				if (result == null)
					result = caseIAttributeMapping(javaMultiRelationshipMapping);
				if (result == null)
					result = caseJpaEObject(javaMultiRelationshipMapping);
				if (result == null)
					result = caseIJpaSourceObject(javaMultiRelationshipMapping);
				if (result == null)
					result = caseIJpaEObject(javaMultiRelationshipMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_ONE_TO_MANY : {
				JavaOneToMany javaOneToMany = (JavaOneToMany) theEObject;
				T result = caseJavaOneToMany(javaOneToMany);
				if (result == null)
					result = caseJavaMultiRelationshipMapping(javaOneToMany);
				if (result == null)
					result = caseIOneToMany(javaOneToMany);
				if (result == null)
					result = caseJavaRelationshipMapping(javaOneToMany);
				if (result == null)
					result = caseIMultiRelationshipMapping(javaOneToMany);
				if (result == null)
					result = caseJavaAttributeMapping(javaOneToMany);
				if (result == null)
					result = caseIRelationshipMapping(javaOneToMany);
				if (result == null)
					result = caseINonOwningMapping(javaOneToMany);
				if (result == null)
					result = caseJavaEObject(javaOneToMany);
				if (result == null)
					result = caseIJavaAttributeMapping(javaOneToMany);
				if (result == null)
					result = caseIAttributeMapping(javaOneToMany);
				if (result == null)
					result = caseJpaEObject(javaOneToMany);
				if (result == null)
					result = caseIJpaSourceObject(javaOneToMany);
				if (result == null)
					result = caseIJpaEObject(javaOneToMany);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_MANY_TO_MANY : {
				JavaManyToMany javaManyToMany = (JavaManyToMany) theEObject;
				T result = caseJavaManyToMany(javaManyToMany);
				if (result == null)
					result = caseJavaMultiRelationshipMapping(javaManyToMany);
				if (result == null)
					result = caseIManyToMany(javaManyToMany);
				if (result == null)
					result = caseJavaRelationshipMapping(javaManyToMany);
				if (result == null)
					result = caseIMultiRelationshipMapping(javaManyToMany);
				if (result == null)
					result = caseJavaAttributeMapping(javaManyToMany);
				if (result == null)
					result = caseIRelationshipMapping(javaManyToMany);
				if (result == null)
					result = caseINonOwningMapping(javaManyToMany);
				if (result == null)
					result = caseJavaEObject(javaManyToMany);
				if (result == null)
					result = caseIJavaAttributeMapping(javaManyToMany);
				if (result == null)
					result = caseIAttributeMapping(javaManyToMany);
				if (result == null)
					result = caseJpaEObject(javaManyToMany);
				if (result == null)
					result = caseIJpaSourceObject(javaManyToMany);
				if (result == null)
					result = caseIJpaEObject(javaManyToMany);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_NULL_ATTRIBUTE_MAPPING : {
				JavaNullAttributeMapping javaNullAttributeMapping = (JavaNullAttributeMapping) theEObject;
				T result = caseJavaNullAttributeMapping(javaNullAttributeMapping);
				if (result == null)
					result = caseJavaAttributeMapping(javaNullAttributeMapping);
				if (result == null)
					result = caseJavaEObject(javaNullAttributeMapping);
				if (result == null)
					result = caseIJavaAttributeMapping(javaNullAttributeMapping);
				if (result == null)
					result = caseJpaEObject(javaNullAttributeMapping);
				if (result == null)
					result = caseIJpaSourceObject(javaNullAttributeMapping);
				if (result == null)
					result = caseIAttributeMapping(javaNullAttributeMapping);
				if (result == null)
					result = caseIJpaEObject(javaNullAttributeMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_TABLE : {
				AbstractJavaTable abstractJavaTable = (AbstractJavaTable) theEObject;
				T result = caseAbstractJavaTable(abstractJavaTable);
				if (result == null)
					result = caseJavaEObject(abstractJavaTable);
				if (result == null)
					result = caseITable(abstractJavaTable);
				if (result == null)
					result = caseJpaEObject(abstractJavaTable);
				if (result == null)
					result = caseIJpaSourceObject(abstractJavaTable);
				if (result == null)
					result = caseIJpaEObject(abstractJavaTable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_TABLE : {
				JavaTable javaTable = (JavaTable) theEObject;
				T result = caseJavaTable(javaTable);
				if (result == null)
					result = caseAbstractJavaTable(javaTable);
				if (result == null)
					result = caseJavaEObject(javaTable);
				if (result == null)
					result = caseITable(javaTable);
				if (result == null)
					result = caseJpaEObject(javaTable);
				if (result == null)
					result = caseIJpaSourceObject(javaTable);
				if (result == null)
					result = caseIJpaEObject(javaTable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_SECONDARY_TABLE : {
				JavaSecondaryTable javaSecondaryTable = (JavaSecondaryTable) theEObject;
				T result = caseJavaSecondaryTable(javaSecondaryTable);
				if (result == null)
					result = caseAbstractJavaTable(javaSecondaryTable);
				if (result == null)
					result = caseISecondaryTable(javaSecondaryTable);
				if (result == null)
					result = caseJavaEObject(javaSecondaryTable);
				if (result == null)
					result = caseITable(javaSecondaryTable);
				if (result == null)
					result = caseJpaEObject(javaSecondaryTable);
				if (result == null)
					result = caseIJpaSourceObject(javaSecondaryTable);
				if (result == null)
					result = caseIJpaEObject(javaSecondaryTable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE : {
				JavaJoinTable javaJoinTable = (JavaJoinTable) theEObject;
				T result = caseJavaJoinTable(javaJoinTable);
				if (result == null)
					result = caseAbstractJavaTable(javaJoinTable);
				if (result == null)
					result = caseIJoinTable(javaJoinTable);
				if (result == null)
					result = caseJavaEObject(javaJoinTable);
				if (result == null)
					result = caseITable(javaJoinTable);
				if (result == null)
					result = caseJpaEObject(javaJoinTable);
				if (result == null)
					result = caseIJpaSourceObject(javaJoinTable);
				if (result == null)
					result = caseIJpaEObject(javaJoinTable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_NAMED_COLUMN : {
				JavaNamedColumn javaNamedColumn = (JavaNamedColumn) theEObject;
				T result = caseJavaNamedColumn(javaNamedColumn);
				if (result == null)
					result = caseJavaEObject(javaNamedColumn);
				if (result == null)
					result = caseINamedColumn(javaNamedColumn);
				if (result == null)
					result = caseJpaEObject(javaNamedColumn);
				if (result == null)
					result = caseIJpaSourceObject(javaNamedColumn);
				if (result == null)
					result = caseIJpaEObject(javaNamedColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.ABSTRACT_JAVA_COLUMN : {
				AbstractJavaColumn abstractJavaColumn = (AbstractJavaColumn) theEObject;
				T result = caseAbstractJavaColumn(abstractJavaColumn);
				if (result == null)
					result = caseJavaNamedColumn(abstractJavaColumn);
				if (result == null)
					result = caseIAbstractColumn(abstractJavaColumn);
				if (result == null)
					result = caseJavaEObject(abstractJavaColumn);
				if (result == null)
					result = caseINamedColumn(abstractJavaColumn);
				if (result == null)
					result = caseJpaEObject(abstractJavaColumn);
				if (result == null)
					result = caseIJpaSourceObject(abstractJavaColumn);
				if (result == null)
					result = caseIJpaEObject(abstractJavaColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_COLUMN : {
				JavaColumn javaColumn = (JavaColumn) theEObject;
				T result = caseJavaColumn(javaColumn);
				if (result == null)
					result = caseAbstractJavaColumn(javaColumn);
				if (result == null)
					result = caseIColumn(javaColumn);
				if (result == null)
					result = caseJavaNamedColumn(javaColumn);
				if (result == null)
					result = caseIAbstractColumn(javaColumn);
				if (result == null)
					result = caseJavaEObject(javaColumn);
				if (result == null)
					result = caseINamedColumn(javaColumn);
				if (result == null)
					result = caseJpaEObject(javaColumn);
				if (result == null)
					result = caseIJpaSourceObject(javaColumn);
				if (result == null)
					result = caseIJpaEObject(javaColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_JOIN_COLUMN : {
				JavaJoinColumn javaJoinColumn = (JavaJoinColumn) theEObject;
				T result = caseJavaJoinColumn(javaJoinColumn);
				if (result == null)
					result = caseAbstractJavaColumn(javaJoinColumn);
				if (result == null)
					result = caseIJoinColumn(javaJoinColumn);
				if (result == null)
					result = caseJavaNamedColumn(javaJoinColumn);
				if (result == null)
					result = caseIAbstractColumn(javaJoinColumn);
				if (result == null)
					result = caseIAbstractJoinColumn(javaJoinColumn);
				if (result == null)
					result = caseJavaEObject(javaJoinColumn);
				if (result == null)
					result = caseINamedColumn(javaJoinColumn);
				if (result == null)
					result = caseJpaEObject(javaJoinColumn);
				if (result == null)
					result = caseIJpaSourceObject(javaJoinColumn);
				if (result == null)
					result = caseIJpaEObject(javaJoinColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_OVERRIDE : {
				JavaOverride javaOverride = (JavaOverride) theEObject;
				T result = caseJavaOverride(javaOverride);
				if (result == null)
					result = caseJavaEObject(javaOverride);
				if (result == null)
					result = caseIOverride(javaOverride);
				if (result == null)
					result = caseJpaEObject(javaOverride);
				if (result == null)
					result = caseIJpaSourceObject(javaOverride);
				if (result == null)
					result = caseIJpaEObject(javaOverride);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_ATTRIBUTE_OVERRIDE : {
				JavaAttributeOverride javaAttributeOverride = (JavaAttributeOverride) theEObject;
				T result = caseJavaAttributeOverride(javaAttributeOverride);
				if (result == null)
					result = caseJavaOverride(javaAttributeOverride);
				if (result == null)
					result = caseIAttributeOverride(javaAttributeOverride);
				if (result == null)
					result = caseJavaEObject(javaAttributeOverride);
				if (result == null)
					result = caseIOverride(javaAttributeOverride);
				if (result == null)
					result = caseIColumnMapping(javaAttributeOverride);
				if (result == null)
					result = caseJpaEObject(javaAttributeOverride);
				if (result == null)
					result = caseIJpaSourceObject(javaAttributeOverride);
				if (result == null)
					result = caseIJpaEObject(javaAttributeOverride);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE : {
				JavaAssociationOverride javaAssociationOverride = (JavaAssociationOverride) theEObject;
				T result = caseJavaAssociationOverride(javaAssociationOverride);
				if (result == null)
					result = caseJavaOverride(javaAssociationOverride);
				if (result == null)
					result = caseIAssociationOverride(javaAssociationOverride);
				if (result == null)
					result = caseJavaEObject(javaAssociationOverride);
				if (result == null)
					result = caseIOverride(javaAssociationOverride);
				if (result == null)
					result = caseJpaEObject(javaAssociationOverride);
				if (result == null)
					result = caseIJpaSourceObject(javaAssociationOverride);
				if (result == null)
					result = caseIJpaEObject(javaAssociationOverride);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN : {
				JavaDiscriminatorColumn javaDiscriminatorColumn = (JavaDiscriminatorColumn) theEObject;
				T result = caseJavaDiscriminatorColumn(javaDiscriminatorColumn);
				if (result == null)
					result = caseJavaNamedColumn(javaDiscriminatorColumn);
				if (result == null)
					result = caseIDiscriminatorColumn(javaDiscriminatorColumn);
				if (result == null)
					result = caseJavaEObject(javaDiscriminatorColumn);
				if (result == null)
					result = caseINamedColumn(javaDiscriminatorColumn);
				if (result == null)
					result = caseJpaEObject(javaDiscriminatorColumn);
				if (result == null)
					result = caseIJpaSourceObject(javaDiscriminatorColumn);
				if (result == null)
					result = caseIJpaEObject(javaDiscriminatorColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN : {
				JavaPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn = (JavaPrimaryKeyJoinColumn) theEObject;
				T result = caseJavaPrimaryKeyJoinColumn(javaPrimaryKeyJoinColumn);
				if (result == null)
					result = caseJavaNamedColumn(javaPrimaryKeyJoinColumn);
				if (result == null)
					result = caseIPrimaryKeyJoinColumn(javaPrimaryKeyJoinColumn);
				if (result == null)
					result = caseJavaEObject(javaPrimaryKeyJoinColumn);
				if (result == null)
					result = caseINamedColumn(javaPrimaryKeyJoinColumn);
				if (result == null)
					result = caseIAbstractJoinColumn(javaPrimaryKeyJoinColumn);
				if (result == null)
					result = caseJpaEObject(javaPrimaryKeyJoinColumn);
				if (result == null)
					result = caseIJpaSourceObject(javaPrimaryKeyJoinColumn);
				if (result == null)
					result = caseIJpaEObject(javaPrimaryKeyJoinColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_GENERATED_VALUE : {
				JavaGeneratedValue javaGeneratedValue = (JavaGeneratedValue) theEObject;
				T result = caseJavaGeneratedValue(javaGeneratedValue);
				if (result == null)
					result = caseJavaEObject(javaGeneratedValue);
				if (result == null)
					result = caseIGeneratedValue(javaGeneratedValue);
				if (result == null)
					result = caseJpaEObject(javaGeneratedValue);
				if (result == null)
					result = caseIJpaSourceObject(javaGeneratedValue);
				if (result == null)
					result = caseIJpaEObject(javaGeneratedValue);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_GENERATOR : {
				JavaGenerator javaGenerator = (JavaGenerator) theEObject;
				T result = caseJavaGenerator(javaGenerator);
				if (result == null)
					result = caseJavaEObject(javaGenerator);
				if (result == null)
					result = caseIGenerator(javaGenerator);
				if (result == null)
					result = caseJpaEObject(javaGenerator);
				if (result == null)
					result = caseIJpaSourceObject(javaGenerator);
				if (result == null)
					result = caseIJpaEObject(javaGenerator);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR : {
				JavaTableGenerator javaTableGenerator = (JavaTableGenerator) theEObject;
				T result = caseJavaTableGenerator(javaTableGenerator);
				if (result == null)
					result = caseJavaGenerator(javaTableGenerator);
				if (result == null)
					result = caseITableGenerator(javaTableGenerator);
				if (result == null)
					result = caseJavaEObject(javaTableGenerator);
				if (result == null)
					result = caseIGenerator(javaTableGenerator);
				if (result == null)
					result = caseJpaEObject(javaTableGenerator);
				if (result == null)
					result = caseIJpaSourceObject(javaTableGenerator);
				if (result == null)
					result = caseIJpaEObject(javaTableGenerator);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR : {
				JavaSequenceGenerator javaSequenceGenerator = (JavaSequenceGenerator) theEObject;
				T result = caseJavaSequenceGenerator(javaSequenceGenerator);
				if (result == null)
					result = caseJavaGenerator(javaSequenceGenerator);
				if (result == null)
					result = caseISequenceGenerator(javaSequenceGenerator);
				if (result == null)
					result = caseJavaEObject(javaSequenceGenerator);
				if (result == null)
					result = caseIGenerator(javaSequenceGenerator);
				if (result == null)
					result = caseJpaEObject(javaSequenceGenerator);
				if (result == null)
					result = caseIJpaSourceObject(javaSequenceGenerator);
				if (result == null)
					result = caseIJpaEObject(javaSequenceGenerator);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_ABSTRACT_QUERY : {
				JavaAbstractQuery javaAbstractQuery = (JavaAbstractQuery) theEObject;
				T result = caseJavaAbstractQuery(javaAbstractQuery);
				if (result == null)
					result = caseJavaEObject(javaAbstractQuery);
				if (result == null)
					result = caseIQuery(javaAbstractQuery);
				if (result == null)
					result = caseJpaEObject(javaAbstractQuery);
				if (result == null)
					result = caseIJpaSourceObject(javaAbstractQuery);
				if (result == null)
					result = caseIJpaEObject(javaAbstractQuery);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_NAMED_QUERY : {
				JavaNamedQuery javaNamedQuery = (JavaNamedQuery) theEObject;
				T result = caseJavaNamedQuery(javaNamedQuery);
				if (result == null)
					result = caseJavaAbstractQuery(javaNamedQuery);
				if (result == null)
					result = caseINamedQuery(javaNamedQuery);
				if (result == null)
					result = caseJavaEObject(javaNamedQuery);
				if (result == null)
					result = caseIQuery(javaNamedQuery);
				if (result == null)
					result = caseIJpaSourceObject(javaNamedQuery);
				if (result == null)
					result = caseJpaEObject(javaNamedQuery);
				if (result == null)
					result = caseIJpaEObject(javaNamedQuery);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_NAMED_NATIVE_QUERY : {
				JavaNamedNativeQuery javaNamedNativeQuery = (JavaNamedNativeQuery) theEObject;
				T result = caseJavaNamedNativeQuery(javaNamedNativeQuery);
				if (result == null)
					result = caseJavaAbstractQuery(javaNamedNativeQuery);
				if (result == null)
					result = caseINamedNativeQuery(javaNamedNativeQuery);
				if (result == null)
					result = caseJavaEObject(javaNamedNativeQuery);
				if (result == null)
					result = caseIQuery(javaNamedNativeQuery);
				if (result == null)
					result = caseIJpaSourceObject(javaNamedNativeQuery);
				if (result == null)
					result = caseJpaEObject(javaNamedNativeQuery);
				if (result == null)
					result = caseIJpaEObject(javaNamedNativeQuery);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_QUERY_HINT : {
				JavaQueryHint javaQueryHint = (JavaQueryHint) theEObject;
				T result = caseJavaQueryHint(javaQueryHint);
				if (result == null)
					result = caseJavaEObject(javaQueryHint);
				if (result == null)
					result = caseIQueryHint(javaQueryHint);
				if (result == null)
					result = caseJpaEObject(javaQueryHint);
				if (result == null)
					result = caseIJpaSourceObject(javaQueryHint);
				if (result == null)
					result = caseIJpaEObject(javaQueryHint);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_UNIQUE_CONSTRAINT : {
				JavaUniqueConstraint javaUniqueConstraint = (JavaUniqueConstraint) theEObject;
				T result = caseJavaUniqueConstraint(javaUniqueConstraint);
				if (result == null)
					result = caseJavaEObject(javaUniqueConstraint);
				if (result == null)
					result = caseIUniqueConstraint(javaUniqueConstraint);
				if (result == null)
					result = caseJpaEObject(javaUniqueConstraint);
				if (result == null)
					result = caseIJpaSourceObject(javaUniqueConstraint);
				if (result == null)
					result = caseIJpaEObject(javaUniqueConstraint);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaJavaMappingsPackage.JAVA_CASCADE : {
				JavaCascade javaCascade = (JavaCascade) theEObject;
				T result = caseJavaCascade(javaCascade);
				if (result == null)
					result = caseJavaEObject(javaCascade);
				if (result == null)
					result = caseICascade(javaCascade);
				if (result == null)
					result = caseJpaEObject(javaCascade);
				if (result == null)
					result = caseIJpaSourceObject(javaCascade);
				if (result == null)
					result = caseIJpaEObject(javaCascade);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default :
				return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaEntity(JavaEntity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Mapped Superclass</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Mapped Superclass</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaMappedSuperclass(JavaMappedSuperclass object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Embeddable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Embeddable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaEmbeddable(JavaEmbeddable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Null Type Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Null Type Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaNullTypeMapping(JavaNullTypeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Null Attribute Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Null Attribute Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaNullAttributeMapping(JavaNullAttributeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Basic</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Basic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaBasic(JavaBasic object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Id</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Id</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaId(JavaId object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Transient</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Transient</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaTransient(JavaTransient object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Version</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaVersion(JavaVersion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Embedded Id</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Embedded Id</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaEmbeddedId(JavaEmbeddedId object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Embedded</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Embedded</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaEmbedded(JavaEmbedded object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Attribute Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Attribute Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaAttributeMapping(JavaAttributeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Type Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Type Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaTypeMapping(JavaTypeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Java Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Java Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractJavaTable(AbstractJavaTable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaTable(JavaTable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaColumn(JavaColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Relationship Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Relationship Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaRelationshipMapping(JavaRelationshipMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Single Relationship Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Single Relationship Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaSingleRelationshipMapping(JavaSingleRelationshipMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java One To Many</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java One To Many</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaOneToMany(JavaOneToMany object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Many To Many</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Many To Many</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaManyToMany(JavaManyToMany object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Join Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Join Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaJoinTable(JavaJoinTable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Named Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Named Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaNamedColumn(JavaNamedColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Java Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Java Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractJavaColumn(AbstractJavaColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Join Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Join Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaJoinColumn(JavaJoinColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Override</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Override</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaOverride(JavaOverride object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Attribute Override</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Attribute Override</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaAttributeOverride(JavaAttributeOverride object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Association Override</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Association Override</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaAssociationOverride(JavaAssociationOverride object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Discriminator Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Discriminator Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaDiscriminatorColumn(JavaDiscriminatorColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Primary Key Join Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Primary Key Join Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaPrimaryKeyJoinColumn(JavaPrimaryKeyJoinColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Generated Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Generated Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaGeneratedValue(JavaGeneratedValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Generator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Generator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaGenerator(JavaGenerator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Table Generator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Table Generator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaTableGenerator(JavaTableGenerator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Sequence Generator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Sequence Generator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaSequenceGenerator(JavaSequenceGenerator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Abstract Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Abstract Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaAbstractQuery(JavaAbstractQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Named Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Named Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaNamedQuery(JavaNamedQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Named Native Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Named Native Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaNamedNativeQuery(JavaNamedNativeQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Query Hint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Query Hint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaQueryHint(JavaQueryHint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Unique Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Unique Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaUniqueConstraint(JavaUniqueConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Cascade</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Cascade</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaCascade(JavaCascade object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Secondary Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Secondary Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaSecondaryTable(JavaSecondaryTable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJpa EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJpa EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaEObject(IJpaEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Jpa EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Jpa EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJpaEObject(JpaEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJpa Source Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJpa Source Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJpaSourceObject(IJpaSourceObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaEObject(JavaEObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Many To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Many To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaManyToOne(JavaManyToOne object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java One To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java One To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaOneToOne(JavaOneToOne object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Multi Relationship Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Multi Relationship Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJavaMultiRelationshipMapping(JavaMultiRelationshipMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IType Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IType Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseITypeMapping(ITypeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJava Type Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJava Type Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJavaTypeMapping(IJavaTypeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IEntity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IEntity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIEntity(IEntity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IMapped Superclass</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IMapped Superclass</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIMappedSuperclass(IMappedSuperclass object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IEmbeddable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IEmbeddable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIEmbeddable(IEmbeddable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IAttribute Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IAttribute Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIAttributeMapping(IAttributeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJava Attribute Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJava Attribute Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJavaAttributeMapping(IJavaAttributeMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IColumn Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IColumn Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIColumnMapping(IColumnMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBasic</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBasic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBasic(IBasic object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IId</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IId</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIId(IId object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ITransient</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ITransient</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseITransient(ITransient object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IVersion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IVersion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIVersion(IVersion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IEmbedded Id</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IEmbedded Id</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIEmbeddedId(IEmbeddedId object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IEmbedded</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IEmbedded</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIEmbedded(IEmbedded object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ITable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ITable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseITable(ITable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IColumn</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IColumn</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIColumn(IColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IAbstract Join Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IAbstract Join Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIAbstractJoinColumn(IAbstractJoinColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IRelationship Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IRelationship Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIRelationshipMapping(IRelationshipMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IMulti Relationship Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IMulti Relationship Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIMultiRelationshipMapping(IMultiRelationshipMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IOne To Many</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IOne To Many</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIOneToMany(IOneToMany object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IMany To Many</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IMany To Many</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIManyToMany(IManyToMany object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJoin Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJoin Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJoinTable(IJoinTable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>INamed Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INamed Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseINamedColumn(INamedColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IAbstract Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IAbstract Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIAbstractColumn(IAbstractColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IJoin Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IJoin Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIJoinColumn(IJoinColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IOverride</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IOverride</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIOverride(IOverride object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IAttribute Override</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IAttribute Override</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIAttributeOverride(IAttributeOverride object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IAssociation Override</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IAssociation Override</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIAssociationOverride(IAssociationOverride object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IDiscriminator Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IDiscriminator Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIDiscriminatorColumn(IDiscriminatorColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IPrimary Key Join Column</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IPrimary Key Join Column</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIPrimaryKeyJoinColumn(IPrimaryKeyJoinColumn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IGenerated Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IGenerated Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIGeneratedValue(IGeneratedValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IGenerator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IGenerator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIGenerator(IGenerator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ITable Generator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ITable Generator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseITableGenerator(ITableGenerator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ISequence Generator</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ISequence Generator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseISequenceGenerator(ISequenceGenerator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IQuery</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IQuery</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIQuery(IQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>INamed Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INamed Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseINamedQuery(INamedQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>INamed Native Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INamed Native Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseINamedNativeQuery(INamedNativeQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IQuery Hint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IQuery Hint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIQueryHint(IQueryHint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IUnique Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IUnique Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIUniqueConstraint(IUniqueConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ICascade</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ICascade</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseICascade(ICascade object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ISecondary Table</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ISecondary Table</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseISecondaryTable(ISecondaryTable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ISingle Relationship Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ISingle Relationship Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseISingleRelationshipMapping(ISingleRelationshipMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IMany To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IMany To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIManyToOne(IManyToOne object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IOne To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IOne To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIOneToOne(IOneToOne object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>INon Owning Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INon Owning Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseINonOwningMapping(INonOwningMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}
} //JavaMappingsSwitch
