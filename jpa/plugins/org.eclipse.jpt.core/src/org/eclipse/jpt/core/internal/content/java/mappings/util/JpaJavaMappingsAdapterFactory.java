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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage
 * @generated
 */
public class JpaJavaMappingsAdapterFactory extends AdapterFactoryImpl
{
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static JpaJavaMappingsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JpaJavaMappingsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = JpaJavaMappingsPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch the delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JpaJavaMappingsSwitch<Adapter> modelSwitch = new JpaJavaMappingsSwitch<Adapter>() {
		@Override
		public Adapter caseJavaTypeMapping(JavaTypeMapping object) {
			return createJavaTypeMappingAdapter();
		}

		@Override
		public Adapter caseJavaEntity(JavaEntity object) {
			return createJavaEntityAdapter();
		}

		@Override
		public Adapter caseJavaMappedSuperclass(JavaMappedSuperclass object) {
			return createJavaMappedSuperclassAdapter();
		}

		@Override
		public Adapter caseJavaEmbeddable(JavaEmbeddable object) {
			return createJavaEmbeddableAdapter();
		}

		@Override
		public Adapter caseJavaNullTypeMapping(JavaNullTypeMapping object) {
			return createJavaNullTypeMappingAdapter();
		}

		@Override
		public Adapter caseJavaAttributeMapping(JavaAttributeMapping object) {
			return createJavaAttributeMappingAdapter();
		}

		@Override
		public Adapter caseJavaBasic(JavaBasic object) {
			return createJavaBasicAdapter();
		}

		@Override
		public Adapter caseJavaId(JavaId object) {
			return createJavaIdAdapter();
		}

		@Override
		public Adapter caseJavaTransient(JavaTransient object) {
			return createJavaTransientAdapter();
		}

		@Override
		public Adapter caseJavaVersion(JavaVersion object) {
			return createJavaVersionAdapter();
		}

		@Override
		public Adapter caseJavaEmbeddedId(JavaEmbeddedId object) {
			return createJavaEmbeddedIdAdapter();
		}

		@Override
		public Adapter caseJavaEmbedded(JavaEmbedded object) {
			return createJavaEmbeddedAdapter();
		}

		@Override
		public Adapter caseJavaRelationshipMapping(JavaRelationshipMapping object) {
			return createJavaRelationshipMappingAdapter();
		}

		@Override
		public Adapter caseJavaSingleRelationshipMapping(JavaSingleRelationshipMapping object) {
			return createJavaSingleRelationshipMappingAdapter();
		}

		@Override
		public Adapter caseJavaManyToOne(JavaManyToOne object) {
			return createJavaManyToOneAdapter();
		}

		@Override
		public Adapter caseJavaOneToOne(JavaOneToOne object) {
			return createJavaOneToOneAdapter();
		}

		@Override
		public Adapter caseJavaMultiRelationshipMapping(JavaMultiRelationshipMapping object) {
			return createJavaMultiRelationshipMappingAdapter();
		}

		@Override
		public Adapter caseJavaOneToMany(JavaOneToMany object) {
			return createJavaOneToManyAdapter();
		}

		@Override
		public Adapter caseJavaManyToMany(JavaManyToMany object) {
			return createJavaManyToManyAdapter();
		}

		@Override
		public Adapter caseJavaNullAttributeMapping(JavaNullAttributeMapping object) {
			return createJavaNullAttributeMappingAdapter();
		}

		@Override
		public Adapter caseAbstractJavaTable(AbstractJavaTable object) {
			return createAbstractJavaTableAdapter();
		}

		@Override
		public Adapter caseJavaTable(JavaTable object) {
			return createJavaTableAdapter();
		}

		@Override
		public Adapter caseJavaSecondaryTable(JavaSecondaryTable object) {
			return createJavaSecondaryTableAdapter();
		}

		@Override
		public Adapter caseJavaJoinTable(JavaJoinTable object) {
			return createJavaJoinTableAdapter();
		}

		@Override
		public Adapter caseJavaNamedColumn(JavaNamedColumn object) {
			return createJavaNamedColumnAdapter();
		}

		@Override
		public Adapter caseAbstractJavaColumn(AbstractJavaColumn object) {
			return createAbstractJavaColumnAdapter();
		}

		@Override
		public Adapter caseJavaColumn(JavaColumn object) {
			return createJavaColumnAdapter();
		}

		@Override
		public Adapter caseJavaJoinColumn(JavaJoinColumn object) {
			return createJavaJoinColumnAdapter();
		}

		@Override
		public Adapter caseJavaOverride(JavaOverride object) {
			return createJavaOverrideAdapter();
		}

		@Override
		public Adapter caseJavaAttributeOverride(JavaAttributeOverride object) {
			return createJavaAttributeOverrideAdapter();
		}

		@Override
		public Adapter caseJavaAssociationOverride(JavaAssociationOverride object) {
			return createJavaAssociationOverrideAdapter();
		}

		@Override
		public Adapter caseJavaDiscriminatorColumn(JavaDiscriminatorColumn object) {
			return createJavaDiscriminatorColumnAdapter();
		}

		@Override
		public Adapter caseJavaPrimaryKeyJoinColumn(JavaPrimaryKeyJoinColumn object) {
			return createJavaPrimaryKeyJoinColumnAdapter();
		}

		@Override
		public Adapter caseJavaGeneratedValue(JavaGeneratedValue object) {
			return createJavaGeneratedValueAdapter();
		}

		@Override
		public Adapter caseJavaGenerator(JavaGenerator object) {
			return createJavaGeneratorAdapter();
		}

		@Override
		public Adapter caseJavaTableGenerator(JavaTableGenerator object) {
			return createJavaTableGeneratorAdapter();
		}

		@Override
		public Adapter caseJavaSequenceGenerator(JavaSequenceGenerator object) {
			return createJavaSequenceGeneratorAdapter();
		}

		@Override
		public Adapter caseJavaAbstractQuery(JavaAbstractQuery object) {
			return createJavaAbstractQueryAdapter();
		}

		@Override
		public Adapter caseJavaNamedQuery(JavaNamedQuery object) {
			return createJavaNamedQueryAdapter();
		}

		@Override
		public Adapter caseJavaNamedNativeQuery(JavaNamedNativeQuery object) {
			return createJavaNamedNativeQueryAdapter();
		}

		@Override
		public Adapter caseJavaQueryHint(JavaQueryHint object) {
			return createJavaQueryHintAdapter();
		}

		@Override
		public Adapter caseJavaUniqueConstraint(JavaUniqueConstraint object) {
			return createJavaUniqueConstraintAdapter();
		}

		@Override
		public Adapter caseJavaCascade(JavaCascade object) {
			return createJavaCascadeAdapter();
		}

		@Override
		public Adapter caseIJpaEObject(IJpaEObject object) {
			return createIJpaEObjectAdapter();
		}

		@Override
		public Adapter caseJpaEObject(JpaEObject object) {
			return createJpaEObjectAdapter();
		}

		@Override
		public Adapter caseIJpaSourceObject(IJpaSourceObject object) {
			return createIJpaSourceObjectAdapter();
		}

		@Override
		public Adapter caseJavaEObject(JavaEObject object) {
			return createJavaEObjectAdapter();
		}

		@Override
		public Adapter caseITypeMapping(ITypeMapping object) {
			return createITypeMappingAdapter();
		}

		@Override
		public Adapter caseIJavaTypeMapping(IJavaTypeMapping object) {
			return createIJavaTypeMappingAdapter();
		}

		@Override
		public Adapter caseIEntity(IEntity object) {
			return createIEntityAdapter();
		}

		@Override
		public Adapter caseIMappedSuperclass(IMappedSuperclass object) {
			return createIMappedSuperclassAdapter();
		}

		@Override
		public Adapter caseIEmbeddable(IEmbeddable object) {
			return createIEmbeddableAdapter();
		}

		@Override
		public Adapter caseIAttributeMapping(IAttributeMapping object) {
			return createIAttributeMappingAdapter();
		}

		@Override
		public Adapter caseIJavaAttributeMapping(IJavaAttributeMapping object) {
			return createIJavaAttributeMappingAdapter();
		}

		@Override
		public Adapter caseIColumnMapping(IColumnMapping object) {
			return createIColumnMappingAdapter();
		}

		@Override
		public Adapter caseIBasic(IBasic object) {
			return createIBasicAdapter();
		}

		@Override
		public Adapter caseIId(IId object) {
			return createIIdAdapter();
		}

		@Override
		public Adapter caseITransient(ITransient object) {
			return createITransientAdapter();
		}

		@Override
		public Adapter caseIVersion(IVersion object) {
			return createIVersionAdapter();
		}

		@Override
		public Adapter caseIEmbeddedId(IEmbeddedId object) {
			return createIEmbeddedIdAdapter();
		}

		@Override
		public Adapter caseIEmbedded(IEmbedded object) {
			return createIEmbeddedAdapter();
		}

		@Override
		public Adapter caseIRelationshipMapping(IRelationshipMapping object) {
			return createIRelationshipMappingAdapter();
		}

		@Override
		public Adapter caseISingleRelationshipMapping(ISingleRelationshipMapping object) {
			return createISingleRelationshipMappingAdapter();
		}

		@Override
		public Adapter caseIManyToOne(IManyToOne object) {
			return createIManyToOneAdapter();
		}

		@Override
		public Adapter caseINonOwningMapping(INonOwningMapping object) {
			return createINonOwningMappingAdapter();
		}

		@Override
		public Adapter caseIOneToOne(IOneToOne object) {
			return createIOneToOneAdapter();
		}

		@Override
		public Adapter caseIMultiRelationshipMapping(IMultiRelationshipMapping object) {
			return createIMultiRelationshipMappingAdapter();
		}

		@Override
		public Adapter caseIOneToMany(IOneToMany object) {
			return createIOneToManyAdapter();
		}

		@Override
		public Adapter caseIManyToMany(IManyToMany object) {
			return createIManyToManyAdapter();
		}

		@Override
		public Adapter caseITable(ITable object) {
			return createITableAdapter();
		}

		@Override
		public Adapter caseISecondaryTable(ISecondaryTable object) {
			return createISecondaryTableAdapter();
		}

		@Override
		public Adapter caseIJoinTable(IJoinTable object) {
			return createIJoinTableAdapter();
		}

		@Override
		public Adapter caseINamedColumn(INamedColumn object) {
			return createINamedColumnAdapter();
		}

		@Override
		public Adapter caseIAbstractColumn(IAbstractColumn object) {
			return createIAbstractColumnAdapter();
		}

		@Override
		public Adapter caseIColumn(IColumn object) {
			return createIColumnAdapter();
		}

		@Override
		public Adapter caseIAbstractJoinColumn(IAbstractJoinColumn object) {
			return createIAbstractJoinColumnAdapter();
		}

		@Override
		public Adapter caseIJoinColumn(IJoinColumn object) {
			return createIJoinColumnAdapter();
		}

		@Override
		public Adapter caseIOverride(IOverride object) {
			return createIOverrideAdapter();
		}

		@Override
		public Adapter caseIAttributeOverride(IAttributeOverride object) {
			return createIAttributeOverrideAdapter();
		}

		@Override
		public Adapter caseIAssociationOverride(IAssociationOverride object) {
			return createIAssociationOverrideAdapter();
		}

		@Override
		public Adapter caseIDiscriminatorColumn(IDiscriminatorColumn object) {
			return createIDiscriminatorColumnAdapter();
		}

		@Override
		public Adapter caseIPrimaryKeyJoinColumn(IPrimaryKeyJoinColumn object) {
			return createIPrimaryKeyJoinColumnAdapter();
		}

		@Override
		public Adapter caseIGeneratedValue(IGeneratedValue object) {
			return createIGeneratedValueAdapter();
		}

		@Override
		public Adapter caseIGenerator(IGenerator object) {
			return createIGeneratorAdapter();
		}

		@Override
		public Adapter caseITableGenerator(ITableGenerator object) {
			return createITableGeneratorAdapter();
		}

		@Override
		public Adapter caseISequenceGenerator(ISequenceGenerator object) {
			return createISequenceGeneratorAdapter();
		}

		@Override
		public Adapter caseIQuery(IQuery object) {
			return createIQueryAdapter();
		}

		@Override
		public Adapter caseINamedQuery(INamedQuery object) {
			return createINamedQueryAdapter();
		}

		@Override
		public Adapter caseINamedNativeQuery(INamedNativeQuery object) {
			return createINamedNativeQueryAdapter();
		}

		@Override
		public Adapter caseIQueryHint(IQueryHint object) {
			return createIQueryHintAdapter();
		}

		@Override
		public Adapter caseIUniqueConstraint(IUniqueConstraint object) {
			return createIUniqueConstraintAdapter();
		}

		@Override
		public Adapter caseICascade(ICascade object) {
			return createICascadeAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity <em>Java Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaEntity
	 * @generated
	 */
	public Adapter createJavaEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaMappedSuperclass <em>Java Mapped Superclass</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaMappedSuperclass
	 * @generated
	 */
	public Adapter createJavaMappedSuperclassAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddable <em>Java Embeddable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddable
	 * @generated
	 */
	public Adapter createJavaEmbeddableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNullTypeMapping <em>Java Null Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNullTypeMapping
	 * @generated
	 */
	public Adapter createJavaNullTypeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNullAttributeMapping <em>Java Null Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNullAttributeMapping
	 * @generated
	 */
	public Adapter createJavaNullAttributeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic <em>Java Basic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaBasic
	 * @generated
	 */
	public Adapter createJavaBasicAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaId <em>Java Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaId
	 * @generated
	 */
	public Adapter createJavaIdAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTransient <em>Java Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaTransient
	 * @generated
	 */
	public Adapter createJavaTransientAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaVersion <em>Java Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaVersion
	 * @generated
	 */
	public Adapter createJavaVersionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddedId <em>Java Embedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbeddedId
	 * @generated
	 */
	public Adapter createJavaEmbeddedIdAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbedded <em>Java Embedded</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbedded
	 * @generated
	 */
	public Adapter createJavaEmbeddedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeMapping <em>Java Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeMapping
	 * @generated
	 */
	public Adapter createJavaAttributeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTypeMapping <em>Java Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaTypeMapping
	 * @generated
	 */
	public Adapter createJavaTypeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaTable <em>Abstract Java Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaTable
	 * @generated
	 */
	public Adapter createAbstractJavaTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTable <em>Java Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaTable
	 * @generated
	 */
	public Adapter createJavaTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaColumn <em>Java Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaColumn
	 * @generated
	 */
	public Adapter createJavaColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaRelationshipMapping <em>Java Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaRelationshipMapping
	 * @generated
	 */
	public Adapter createJavaRelationshipMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaSingleRelationshipMapping <em>Java Single Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaSingleRelationshipMapping
	 * @generated
	 */
	public Adapter createJavaSingleRelationshipMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToMany <em>Java One To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToMany
	 * @generated
	 */
	public Adapter createJavaOneToManyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToMany <em>Java Many To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToMany
	 * @generated
	 */
	public Adapter createJavaManyToManyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinTable <em>Java Join Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinTable
	 * @generated
	 */
	public Adapter createJavaJoinTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedColumn <em>Java Named Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedColumn
	 * @generated
	 */
	public Adapter createJavaNamedColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaColumn <em>Abstract Java Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.AbstractJavaColumn
	 * @generated
	 */
	public Adapter createAbstractJavaColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinColumn <em>Java Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaJoinColumn
	 * @generated
	 */
	public Adapter createJavaJoinColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOverride <em>Java Override</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaOverride
	 * @generated
	 */
	public Adapter createJavaOverrideAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeOverride <em>Java Attribute Override</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaAttributeOverride
	 * @generated
	 */
	public Adapter createJavaAttributeOverrideAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAssociationOverride <em>Java Association Override</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaAssociationOverride
	 * @generated
	 */
	public Adapter createJavaAssociationOverrideAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaDiscriminatorColumn <em>Java Discriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaDiscriminatorColumn
	 * @generated
	 */
	public Adapter createJavaDiscriminatorColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaPrimaryKeyJoinColumn <em>Java Primary Key Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaPrimaryKeyJoinColumn
	 * @generated
	 */
	public Adapter createJavaPrimaryKeyJoinColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaGeneratedValue <em>Java Generated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaGeneratedValue
	 * @generated
	 */
	public Adapter createJavaGeneratedValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaGenerator <em>Java Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaGenerator
	 * @generated
	 */
	public Adapter createJavaGeneratorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaTableGenerator <em>Java Table Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaTableGenerator
	 * @generated
	 */
	public Adapter createJavaTableGeneratorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaSequenceGenerator <em>Java Sequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaSequenceGenerator
	 * @generated
	 */
	public Adapter createJavaSequenceGeneratorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaAbstractQuery <em>Java Abstract Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaAbstractQuery
	 * @generated
	 */
	public Adapter createJavaAbstractQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedQuery <em>Java Named Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedQuery
	 * @generated
	 */
	public Adapter createJavaNamedQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedNativeQuery <em>Java Named Native Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaNamedNativeQuery
	 * @generated
	 */
	public Adapter createJavaNamedNativeQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaQueryHint <em>Java Query Hint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaQueryHint
	 * @generated
	 */
	public Adapter createJavaQueryHintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaUniqueConstraint <em>Java Unique Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaUniqueConstraint
	 * @generated
	 */
	public Adapter createJavaUniqueConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaCascade <em>Java Cascade</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaCascade
	 * @generated
	 */
	public Adapter createJavaCascadeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaSecondaryTable <em>Java Secondary Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaSecondaryTable
	 * @generated
	 */
	public Adapter createJavaSecondaryTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IJpaEObject <em>IJpa EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IJpaEObject
	 * @generated
	 */
	public Adapter createIJpaEObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.JpaEObject <em>Jpa EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.JpaEObject
	 * @generated
	 */
	public Adapter createJpaEObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IJpaSourceObject <em>IJpa Source Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IJpaSourceObject
	 * @generated
	 */
	public Adapter createIJpaSourceObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.JavaEObject <em>Java EObject</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.JavaEObject
	 * @generated
	 */
	public Adapter createJavaEObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToOne <em>Java Many To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaManyToOne
	 * @generated
	 */
	public Adapter createJavaManyToOneAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToOne <em>Java One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaOneToOne
	 * @generated
	 */
	public Adapter createJavaOneToOneAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.mappings.JavaMultiRelationshipMapping <em>Java Multi Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.mappings.JavaMultiRelationshipMapping
	 * @generated
	 */
	public Adapter createJavaMultiRelationshipMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.ITypeMapping <em>IType Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.ITypeMapping
	 * @generated
	 */
	public Adapter createITypeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping <em>IJava Type Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping
	 * @generated
	 */
	public Adapter createIJavaTypeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IEntity <em>IEntity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IEntity
	 * @generated
	 */
	public Adapter createIEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IMappedSuperclass <em>IMapped Superclass</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IMappedSuperclass
	 * @generated
	 */
	public Adapter createIMappedSuperclassAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IEmbeddable <em>IEmbeddable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IEmbeddable
	 * @generated
	 */
	public Adapter createIEmbeddableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.IAttributeMapping <em>IAttribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.IAttributeMapping
	 * @generated
	 */
	public Adapter createIAttributeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping <em>IJava Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping
	 * @generated
	 */
	public Adapter createIJavaAttributeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IColumnMapping <em>IColumn Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IColumnMapping
	 * @generated
	 */
	public Adapter createIColumnMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IBasic <em>IBasic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IBasic
	 * @generated
	 */
	public Adapter createIBasicAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IId <em>IId</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IId
	 * @generated
	 */
	public Adapter createIIdAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.ITransient <em>ITransient</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.ITransient
	 * @generated
	 */
	public Adapter createITransientAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IVersion <em>IVersion</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IVersion
	 * @generated
	 */
	public Adapter createIVersionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IEmbeddedId <em>IEmbedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IEmbeddedId
	 * @generated
	 */
	public Adapter createIEmbeddedIdAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IEmbedded <em>IEmbedded</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IEmbedded
	 * @generated
	 */
	public Adapter createIEmbeddedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.ITable <em>ITable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.ITable
	 * @generated
	 */
	public Adapter createITableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IColumn <em>IColumn</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IColumn
	 * @generated
	 */
	public Adapter createIColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn <em>IAbstract Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn
	 * @generated
	 */
	public Adapter createIAbstractJoinColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IRelationshipMapping <em>IRelationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IRelationshipMapping
	 * @generated
	 */
	public Adapter createIRelationshipMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping <em>IMulti Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping
	 * @generated
	 */
	public Adapter createIMultiRelationshipMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IOneToMany <em>IOne To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IOneToMany
	 * @generated
	 */
	public Adapter createIOneToManyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IManyToMany <em>IMany To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IManyToMany
	 * @generated
	 */
	public Adapter createIManyToManyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IJoinTable <em>IJoin Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IJoinTable
	 * @generated
	 */
	public Adapter createIJoinTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.INamedColumn <em>INamed Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.INamedColumn
	 * @generated
	 */
	public Adapter createINamedColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IAbstractColumn <em>IAbstract Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IAbstractColumn
	 * @generated
	 */
	public Adapter createIAbstractColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IJoinColumn <em>IJoin Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IJoinColumn
	 * @generated
	 */
	public Adapter createIJoinColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IOverride <em>IOverride</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IOverride
	 * @generated
	 */
	public Adapter createIOverrideAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IAttributeOverride <em>IAttribute Override</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IAttributeOverride
	 * @generated
	 */
	public Adapter createIAttributeOverrideAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IAssociationOverride <em>IAssociation Override</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IAssociationOverride
	 * @generated
	 */
	public Adapter createIAssociationOverrideAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn <em>IDiscriminator Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IDiscriminatorColumn
	 * @generated
	 */
	public Adapter createIDiscriminatorColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn <em>IPrimary Key Join Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn
	 * @generated
	 */
	public Adapter createIPrimaryKeyJoinColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IGeneratedValue <em>IGenerated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IGeneratedValue
	 * @generated
	 */
	public Adapter createIGeneratedValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IGenerator <em>IGenerator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IGenerator
	 * @generated
	 */
	public Adapter createIGeneratorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.ITableGenerator <em>ITable Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.ITableGenerator
	 * @generated
	 */
	public Adapter createITableGeneratorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.ISequenceGenerator <em>ISequence Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.ISequenceGenerator
	 * @generated
	 */
	public Adapter createISequenceGeneratorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IQuery <em>IQuery</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IQuery
	 * @generated
	 */
	public Adapter createIQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.INamedQuery <em>INamed Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.INamedQuery
	 * @generated
	 */
	public Adapter createINamedQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.INamedNativeQuery <em>INamed Native Query</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.INamedNativeQuery
	 * @generated
	 */
	public Adapter createINamedNativeQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IQueryHint <em>IQuery Hint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IQueryHint
	 * @generated
	 */
	public Adapter createIQueryHintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IUniqueConstraint <em>IUnique Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IUniqueConstraint
	 * @generated
	 */
	public Adapter createIUniqueConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.ICascade <em>ICascade</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.ICascade
	 * @generated
	 */
	public Adapter createICascadeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.ISecondaryTable <em>ISecondary Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.ISecondaryTable
	 * @generated
	 */
	public Adapter createISecondaryTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping <em>ISingle Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping
	 * @generated
	 */
	public Adapter createISingleRelationshipMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IManyToOne <em>IMany To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IManyToOne
	 * @generated
	 */
	public Adapter createIManyToOneAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.IOneToOne <em>IOne To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.IOneToOne
	 * @generated
	 */
	public Adapter createIOneToOneAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.mappings.INonOwningMapping <em>INon Owning Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.mappings.INonOwningMapping
	 * @generated
	 */
	public Adapter createINonOwningMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}
} //JavaMappingsAdapterFactory
