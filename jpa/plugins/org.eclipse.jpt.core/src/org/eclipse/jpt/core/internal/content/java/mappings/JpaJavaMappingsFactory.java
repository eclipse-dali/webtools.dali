/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.NullDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IColumn;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IOverride;
import org.eclipse.jpt.core.internal.mappings.ITable;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage
 * @generated
 */
public class JpaJavaMappingsFactory extends EFactoryImpl
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final JpaJavaMappingsFactory eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static JpaJavaMappingsFactory init() {
		try {
			JpaJavaMappingsFactory theJpaJavaMappingsFactory = (JpaJavaMappingsFactory) EPackage.Registry.INSTANCE.getEFactory("jpt.core.java.mappings.xmi");
			if (theJpaJavaMappingsFactory != null) {
				return theJpaJavaMappingsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new JpaJavaMappingsFactory();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JpaJavaMappingsFactory() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case JpaJavaMappingsPackage.JAVA_ENTITY :
				return createJavaEntity();
			case JpaJavaMappingsPackage.JAVA_MAPPED_SUPERCLASS :
				return createJavaMappedSuperclass();
			case JpaJavaMappingsPackage.JAVA_EMBEDDABLE :
				return createJavaEmbeddable();
			case JpaJavaMappingsPackage.JAVA_NULL_TYPE_MAPPING :
				return createJavaNullTypeMapping();
			case JpaJavaMappingsPackage.JAVA_BASIC :
				return createJavaBasic();
			case JpaJavaMappingsPackage.JAVA_ID :
				return createJavaId();
			case JpaJavaMappingsPackage.JAVA_TRANSIENT :
				return createJavaTransient();
			case JpaJavaMappingsPackage.JAVA_VERSION :
				return createJavaVersion();
			case JpaJavaMappingsPackage.JAVA_EMBEDDED_ID :
				return createJavaEmbeddedId();
			case JpaJavaMappingsPackage.JAVA_EMBEDDED :
				return createJavaEmbedded();
			case JpaJavaMappingsPackage.JAVA_MANY_TO_ONE :
				return createJavaManyToOne();
			case JpaJavaMappingsPackage.JAVA_ONE_TO_ONE :
				return createJavaOneToOne();
			case JpaJavaMappingsPackage.JAVA_ONE_TO_MANY :
				return createJavaOneToMany();
			case JpaJavaMappingsPackage.JAVA_MANY_TO_MANY :
				return createJavaManyToMany();
			case JpaJavaMappingsPackage.JAVA_NULL_ATTRIBUTE_MAPPING :
				return createJavaNullAttributeMapping();
			case JpaJavaMappingsPackage.JAVA_TABLE :
				return createJavaTable();
			case JpaJavaMappingsPackage.JAVA_SECONDARY_TABLE :
				return createJavaSecondaryTable();
			case JpaJavaMappingsPackage.JAVA_JOIN_TABLE :
				return createJavaJoinTable();
			case JpaJavaMappingsPackage.JAVA_COLUMN :
				return createJavaColumn();
			case JpaJavaMappingsPackage.JAVA_JOIN_COLUMN :
				return createJavaJoinColumn();
			case JpaJavaMappingsPackage.JAVA_ATTRIBUTE_OVERRIDE :
				return createJavaAttributeOverride();
			case JpaJavaMappingsPackage.JAVA_ASSOCIATION_OVERRIDE :
				return createJavaAssociationOverride();
			case JpaJavaMappingsPackage.JAVA_DISCRIMINATOR_COLUMN :
				return createJavaDiscriminatorColumn();
			case JpaJavaMappingsPackage.JAVA_PRIMARY_KEY_JOIN_COLUMN :
				return createJavaPrimaryKeyJoinColumn();
			case JpaJavaMappingsPackage.JAVA_GENERATED_VALUE :
				return createJavaGeneratedValue();
			case JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR :
				return createJavaTableGenerator();
			case JpaJavaMappingsPackage.JAVA_SEQUENCE_GENERATOR :
				return createJavaSequenceGenerator();
			case JpaJavaMappingsPackage.JAVA_ORDER_BY :
				return createJavaOrderBy();
			case JpaJavaMappingsPackage.JAVA_NAMED_QUERY :
				return createJavaNamedQuery();
			case JpaJavaMappingsPackage.JAVA_NAMED_NATIVE_QUERY :
				return createJavaNamedNativeQuery();
			case JpaJavaMappingsPackage.JAVA_QUERY_HINT :
				return createJavaQueryHint();
			default :
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	public JavaEntity createJavaEntity() {
		throw new UnsupportedOperationException("Use createJavaEntity(Type) instead");
	}

	public JavaEntity createJavaEntity(Type type) {
		JavaEntity javaEntity = new JavaEntity(type);
		return javaEntity;
	}

	public JavaMappedSuperclass createJavaMappedSuperclass() {
		throw new UnsupportedOperationException("Use createJavaMappedSuperclass(Type) instead");
	}

	public JavaEmbeddable createJavaEmbeddable() {
		throw new UnsupportedOperationException("Use createJavaEmbeddable(Type) instead");
	}

	public JavaEmbeddable createJavaEmbeddable(Type type) {
		JavaEmbeddable javaEmbeddable = new JavaEmbeddable(type);
		return javaEmbeddable;
	}

	public JavaMappedSuperclass createJavaMappedSuperclass(Type type) {
		JavaMappedSuperclass javaMappedSuperclass = new JavaMappedSuperclass(type);
		return javaMappedSuperclass;
	}

	public JavaNullTypeMapping createJavaNullTypeMapping() {
		throw new UnsupportedOperationException("Use createJavaNullTypeMapping(Type) instead");
	}

	public JavaNullTypeMapping createJavaNullTypeMapping(Type type) {
		JavaNullTypeMapping javaNullTypeMapping = new JavaNullTypeMapping(type);
		return javaNullTypeMapping;
	}

	public JavaNullAttributeMapping createJavaNullAttributeMapping() {
		throw new UnsupportedOperationException("Use createJavaNullAttributeMapping(Attribute) instead");
	}

	public JavaNullAttributeMapping createJavaNullAttributeMapping(Attribute attribute) {
		JavaNullAttributeMapping javaNullAttributeMapping = new JavaNullAttributeMapping(attribute);
		return javaNullAttributeMapping;
	}

	public JavaBasic createJavaBasic() {
		throw new UnsupportedOperationException("Use createJavaBasic(Attribute) instead");
	}

	public JavaBasic createJavaBasic(Attribute attribute) {
		JavaBasic javaBasic = new JavaBasic(attribute);
		return javaBasic;
	}

	public JavaId createJavaId() {
		throw new UnsupportedOperationException("Use createJavaId(Attribute) instead");
	}

	public JavaTransient createJavaTransient() {
		throw new UnsupportedOperationException("Use createJavaTransient(Attribute) instead");
	}

	public JavaVersion createJavaVersion() {
		throw new UnsupportedOperationException("Use createJavaVersion(Attribute) instead");
	}

	public JavaVersion createJavaVersion(Attribute attribute) {
		JavaVersion javaVersion = new JavaVersion(attribute);
		return javaVersion;
	}

	public JavaEmbeddedId createJavaEmbeddedId() {
		throw new UnsupportedOperationException("Use createJavaEmbeddedId(Attribute) instead");
	}

	public JavaEmbeddedId createJavaEmbeddedId(Attribute attribute) {
		JavaEmbeddedId javaEmbeddedId = new JavaEmbeddedId(attribute);
		return javaEmbeddedId;
	}

	public JavaEmbedded createJavaEmbedded() {
		throw new UnsupportedOperationException("Use createJavaEmbedded(Attribute) instead");
	}

	public JavaEmbedded createJavaEmbedded(Attribute attribute) {
		JavaEmbedded javaEmbedded = new JavaEmbedded(attribute);
		return javaEmbedded;
	}

	public JavaTransient createJavaTransient(Attribute attribute) {
		JavaTransient javaTransient = new JavaTransient(attribute);
		return javaTransient;
	}

	public JavaId createJavaId(Attribute attribute) {
		JavaId javaId = new JavaId(attribute);
		return javaId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public JavaTable createJavaTable() {
		throw new UnsupportedOperationException("Use createJavaTable(ITable.Owner, Member) instead");
	}

	public JavaTable createJavaTable(ITable.Owner owner, Member member) {
		JavaTable javaTable = new JavaTable(owner, member);
		return javaTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public JavaColumn createJavaColumn() {
		throw new UnsupportedOperationException("Use createJavaColumn(Member ) instead");
	}

	public JavaOneToMany createJavaOneToMany() {
		throw new UnsupportedOperationException("Use createJavaOneToMany(Attribute) instead");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaManyToMany createJavaManyToMany() {
		JavaManyToMany javaManyToMany = new JavaManyToMany();
		return javaManyToMany;
	}

	public JavaManyToMany createJavaManyToMany(Attribute attribute) {
		JavaManyToMany javaManyToMany = new JavaManyToMany(attribute);
		return javaManyToMany;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaJoinTable createJavaJoinTable() {
		JavaJoinTable javaJoinTable = new JavaJoinTable();
		return javaJoinTable;
	}

	public JavaJoinTable createJavaJoinTable(ITable.Owner owner, Member member) {
		JavaJoinTable javaJoinTable = new JavaJoinTable(owner, member);
		return javaJoinTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaJoinColumn createJavaJoinColumn() {
		JavaJoinColumn javaJoinColumn = new JavaJoinColumn();
		return javaJoinColumn;
	}

	public JavaAttributeOverride createJavaAttributeOverride() {
		throw new UnsupportedOperationException();
	}

	public JavaAttributeOverride createJavaAttributeOverride(IOverride.Owner owner, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		JavaAttributeOverride javaAttributeOverride = new JavaAttributeOverride(owner, member, idaa);
		return javaAttributeOverride;
	}

	public JavaAttributeOverride createJavaAttributeOverride(IOverride.Owner owner, Member member) {
		return this.createJavaAttributeOverride(owner, member, NullDeclarationAnnotationAdapter.instance());
	}

	public JavaAssociationOverride createJavaAssociationOverride() {
		throw new UnsupportedOperationException();
	}

	public JavaAssociationOverride createJavaAssociationOverride(IOverride.Owner owner, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		JavaAssociationOverride javaAssociationOverride = new JavaAssociationOverride(owner, member, idaa);
		return javaAssociationOverride;
	}
	
	public JavaAssociationOverride createJavaAssociationOverride(IOverride.Owner owner, Member member) {
		return this.createJavaAssociationOverride(owner, member, NullDeclarationAnnotationAdapter.instance());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public JavaDiscriminatorColumn createJavaDiscriminatorColumn() {
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaPrimaryKeyJoinColumn createJavaPrimaryKeyJoinColumn() {
		JavaPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn = new JavaPrimaryKeyJoinColumn();
		return javaPrimaryKeyJoinColumn;
	}

	public JavaGeneratedValue createJavaGeneratedValue() {
		throw new UnsupportedOperationException("Use createJavaGeneratedValue(Member) instead");
	}

	public JavaGeneratedValue createJavaGeneratedValue(Member member) {
		JavaGeneratedValue javaGeneratedValue = new JavaGeneratedValue(member);
		return javaGeneratedValue;
	}

	public JavaTableGenerator createJavaTableGenerator() {
		throw new UnsupportedOperationException("Use createJavaTableGenerator(Member) instead");
	}

	public JavaTableGenerator createJavaTableGenerator(Member member) {
		JavaTableGenerator javaTableGenerator = new JavaTableGenerator(member);
		return javaTableGenerator;
	}

	public JavaSequenceGenerator createJavaSequenceGenerator() {
		throw new UnsupportedOperationException("Use createJavaSequenceGenerator(Member) instead");
	}

	public JavaOrderBy createJavaOrderBy() {
		throw new UnsupportedOperationException("Use createJavaOrderBy(Member) instead");
	}

	public JavaNamedQuery createJavaNamedQuery() {
		throw new UnsupportedOperationException("Use createJavaNamedQuery(Member, IndexedDeclarationAnnotationAdapter) instead");
	}

	public JavaNamedQuery createJavaNamedQuery(Member member, IndexedDeclarationAnnotationAdapter idaa) {
		JavaNamedQuery javaNamedQuery = new JavaNamedQuery(member, idaa);
		return javaNamedQuery;
	}

	public JavaNamedNativeQuery createJavaNamedNativeQuery() {
		throw new UnsupportedOperationException("Use createJavaNamedNativeQuery(Member, IndexedDeclarationAnnotationAdapter) instead");
	}

	public JavaNamedNativeQuery createJavaNamedNativeQuery(Member member, IndexedDeclarationAnnotationAdapter idaa) {
		JavaNamedNativeQuery javaNamedNativeQuery = new JavaNamedNativeQuery(member, idaa);
		return javaNamedNativeQuery;
	}

	public JavaQueryHint createJavaQueryHint() {
		throw new UnsupportedOperationException("Use createJavaQueryHint(Member, IndexedDeclarationAnnotationAdapter) instead");
	}

	public JavaQueryHint createJavaQueryHint(Member member, IndexedDeclarationAnnotationAdapter idaa) {
		JavaQueryHint javaQueryHint = new JavaQueryHint(member, idaa);
		return javaQueryHint;
	}

	public JavaOrderBy createJavaOrderBy(Member member) {
		JavaOrderBy javaOrderBy = new JavaOrderBy(member);
		return javaOrderBy;
	}

	public JavaSequenceGenerator createJavaSequenceGenerator(Member member) {
		JavaSequenceGenerator javaSequenceGenerator = new JavaSequenceGenerator(member);
		return javaSequenceGenerator;
	}

	public JavaPrimaryKeyJoinColumn createJavaPrimaryKeyJoinColumn(IAbstractJoinColumn.Owner owner, Member member, int index) {
		JavaPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn = new JavaPrimaryKeyJoinColumn(owner, member, index);
		return javaPrimaryKeyJoinColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public JavaSecondaryTable createJavaSecondaryTable() {
		throw new UnsupportedOperationException();
	}

	public JavaSecondaryTable createJavaSecondaryTable(ITable.Owner owner, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		JavaSecondaryTable javaSecondaryTable = new JavaSecondaryTable(owner, member, idaa);
		return javaSecondaryTable;
	}

	public JavaDiscriminatorColumn createJavaDiscriminatorColumn(Type type) {
		JavaDiscriminatorColumn javaDiscriminatorColumn = new JavaDiscriminatorColumn(type);
		return javaDiscriminatorColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JpaJavaMappingsPackage getJpaJavaMappingsPackage() {
		return (JpaJavaMappingsPackage) getEPackage();
	}

	public JavaJoinColumn createJavaJoinColumn(IJoinColumn.Owner joinColumnOwner, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		JavaJoinColumn javaJoinColumn = new JavaJoinColumn(joinColumnOwner, member, idaa);
		return javaJoinColumn;
	}

	public JavaJoinColumn createJavaJoinColumn(IJoinColumn.Owner joinColumnOwner, Member member) {
		return this.createJavaJoinColumn(joinColumnOwner, member, NullDeclarationAnnotationAdapter.instance());
	}

	public JavaManyToOne createJavaManyToOne() {
		throw new UnsupportedOperationException("Use createJavaManyToOne(Attribute) instead");
	}

	public JavaOneToOne createJavaOneToOne() {
		throw new UnsupportedOperationException("Use createJavaOneToOne(Attribute) instead");
	}

	public JavaOneToOne createJavaOneToOne(Attribute attribute) {
		JavaOneToOne javaOneToOne = new JavaOneToOne(attribute);
		return javaOneToOne;
	}

	public JavaManyToOne createJavaManyToOne(Attribute attribute) {
		JavaManyToOne javaManyToOne = new JavaManyToOne(attribute);
		return javaManyToOne;
	}

	public JavaOneToMany createJavaOneToMany(Attribute attribute) {
		JavaOneToMany javaOneToMany = new JavaOneToMany(attribute);
		return javaOneToMany;
	}

	public JavaColumn createJavaColumn(IColumn.Owner owner, Member member, DeclarationAnnotationAdapter daa) {
		JavaColumn javaColumn = new JavaColumn(owner, member, daa);
		return javaColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static JpaJavaMappingsPackage getPackage() {
		return JpaJavaMappingsPackage.eINSTANCE;
	}
} //JavaMappingsFactory
