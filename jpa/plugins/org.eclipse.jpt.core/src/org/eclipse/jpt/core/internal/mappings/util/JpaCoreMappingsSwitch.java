/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.mappings.util;

import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IJpaEObject;
import org.eclipse.jpt.core.internal.IJpaSourceObject;
import org.eclipse.jpt.core.internal.ITypeMapping;
import org.eclipse.jpt.core.internal.mappings.*;
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
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;

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
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage
 * @generated
 */
public class JpaCoreMappingsSwitch<T>
{
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static JpaCoreMappingsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JpaCoreMappingsSwitch() {
		if (modelPackage == null) {
			modelPackage = JpaCoreMappingsPackage.eINSTANCE;
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
			case JpaCoreMappingsPackage.IMAPPED_SUPERCLASS : {
				IMappedSuperclass iMappedSuperclass = (IMappedSuperclass) theEObject;
				T result = caseIMappedSuperclass(iMappedSuperclass);
				if (result == null)
					result = caseITypeMapping(iMappedSuperclass);
				if (result == null)
					result = caseIJpaSourceObject(iMappedSuperclass);
				if (result == null)
					result = caseIJpaEObject(iMappedSuperclass);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IENTITY : {
				IEntity iEntity = (IEntity) theEObject;
				T result = caseIEntity(iEntity);
				if (result == null)
					result = caseITypeMapping(iEntity);
				if (result == null)
					result = caseIJpaSourceObject(iEntity);
				if (result == null)
					result = caseIJpaEObject(iEntity);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IEMBEDDABLE : {
				IEmbeddable iEmbeddable = (IEmbeddable) theEObject;
				T result = caseIEmbeddable(iEmbeddable);
				if (result == null)
					result = caseITypeMapping(iEmbeddable);
				if (result == null)
					result = caseIJpaSourceObject(iEmbeddable);
				if (result == null)
					result = caseIJpaEObject(iEmbeddable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.ITABLE : {
				ITable iTable = (ITable) theEObject;
				T result = caseITable(iTable);
				if (result == null)
					result = caseIJpaSourceObject(iTable);
				if (result == null)
					result = caseIJpaEObject(iTable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IUNIQUE_CONSTRAINT : {
				IUniqueConstraint iUniqueConstraint = (IUniqueConstraint) theEObject;
				T result = caseIUniqueConstraint(iUniqueConstraint);
				if (result == null)
					result = caseIJpaSourceObject(iUniqueConstraint);
				if (result == null)
					result = caseIJpaEObject(iUniqueConstraint);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.INAMED_COLUMN : {
				INamedColumn iNamedColumn = (INamedColumn) theEObject;
				T result = caseINamedColumn(iNamedColumn);
				if (result == null)
					result = caseIJpaSourceObject(iNamedColumn);
				if (result == null)
					result = caseIJpaEObject(iNamedColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IABSTRACT_COLUMN : {
				IAbstractColumn iAbstractColumn = (IAbstractColumn) theEObject;
				T result = caseIAbstractColumn(iAbstractColumn);
				if (result == null)
					result = caseINamedColumn(iAbstractColumn);
				if (result == null)
					result = caseIJpaSourceObject(iAbstractColumn);
				if (result == null)
					result = caseIJpaEObject(iAbstractColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.ICOLUMN : {
				IColumn iColumn = (IColumn) theEObject;
				T result = caseIColumn(iColumn);
				if (result == null)
					result = caseIAbstractColumn(iColumn);
				if (result == null)
					result = caseINamedColumn(iColumn);
				if (result == null)
					result = caseIJpaSourceObject(iColumn);
				if (result == null)
					result = caseIJpaEObject(iColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.ICOLUMN_MAPPING : {
				IColumnMapping iColumnMapping = (IColumnMapping) theEObject;
				T result = caseIColumnMapping(iColumnMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IBASIC : {
				IBasic iBasic = (IBasic) theEObject;
				T result = caseIBasic(iBasic);
				if (result == null)
					result = caseIAttributeMapping(iBasic);
				if (result == null)
					result = caseIColumnMapping(iBasic);
				if (result == null)
					result = caseIJpaSourceObject(iBasic);
				if (result == null)
					result = caseIJpaEObject(iBasic);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IID : {
				IId iId = (IId) theEObject;
				T result = caseIId(iId);
				if (result == null)
					result = caseIAttributeMapping(iId);
				if (result == null)
					result = caseIColumnMapping(iId);
				if (result == null)
					result = caseIJpaSourceObject(iId);
				if (result == null)
					result = caseIJpaEObject(iId);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.ITRANSIENT : {
				ITransient iTransient = (ITransient) theEObject;
				T result = caseITransient(iTransient);
				if (result == null)
					result = caseIAttributeMapping(iTransient);
				if (result == null)
					result = caseIJpaSourceObject(iTransient);
				if (result == null)
					result = caseIJpaEObject(iTransient);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IVERSION : {
				IVersion iVersion = (IVersion) theEObject;
				T result = caseIVersion(iVersion);
				if (result == null)
					result = caseIAttributeMapping(iVersion);
				if (result == null)
					result = caseIColumnMapping(iVersion);
				if (result == null)
					result = caseIJpaSourceObject(iVersion);
				if (result == null)
					result = caseIJpaEObject(iVersion);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IEMBEDDED_ID : {
				IEmbeddedId iEmbeddedId = (IEmbeddedId) theEObject;
				T result = caseIEmbeddedId(iEmbeddedId);
				if (result == null)
					result = caseIAttributeMapping(iEmbeddedId);
				if (result == null)
					result = caseIJpaSourceObject(iEmbeddedId);
				if (result == null)
					result = caseIJpaEObject(iEmbeddedId);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IEMBEDDED : {
				IEmbedded iEmbedded = (IEmbedded) theEObject;
				T result = caseIEmbedded(iEmbedded);
				if (result == null)
					result = caseIAttributeMapping(iEmbedded);
				if (result == null)
					result = caseIJpaSourceObject(iEmbedded);
				if (result == null)
					result = caseIJpaEObject(iEmbedded);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IRELATIONSHIP_MAPPING : {
				IRelationshipMapping iRelationshipMapping = (IRelationshipMapping) theEObject;
				T result = caseIRelationshipMapping(iRelationshipMapping);
				if (result == null)
					result = caseIAttributeMapping(iRelationshipMapping);
				if (result == null)
					result = caseIJpaSourceObject(iRelationshipMapping);
				if (result == null)
					result = caseIJpaEObject(iRelationshipMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.INON_OWNING_MAPPING : {
				INonOwningMapping iNonOwningMapping = (INonOwningMapping) theEObject;
				T result = caseINonOwningMapping(iNonOwningMapping);
				if (result == null)
					result = caseIRelationshipMapping(iNonOwningMapping);
				if (result == null)
					result = caseIAttributeMapping(iNonOwningMapping);
				if (result == null)
					result = caseIJpaSourceObject(iNonOwningMapping);
				if (result == null)
					result = caseIJpaEObject(iNonOwningMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IMULTI_RELATIONSHIP_MAPPING : {
				IMultiRelationshipMapping iMultiRelationshipMapping = (IMultiRelationshipMapping) theEObject;
				T result = caseIMultiRelationshipMapping(iMultiRelationshipMapping);
				if (result == null)
					result = caseINonOwningMapping(iMultiRelationshipMapping);
				if (result == null)
					result = caseIRelationshipMapping(iMultiRelationshipMapping);
				if (result == null)
					result = caseIAttributeMapping(iMultiRelationshipMapping);
				if (result == null)
					result = caseIJpaSourceObject(iMultiRelationshipMapping);
				if (result == null)
					result = caseIJpaEObject(iMultiRelationshipMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IONE_TO_MANY : {
				IOneToMany iOneToMany = (IOneToMany) theEObject;
				T result = caseIOneToMany(iOneToMany);
				if (result == null)
					result = caseIMultiRelationshipMapping(iOneToMany);
				if (result == null)
					result = caseINonOwningMapping(iOneToMany);
				if (result == null)
					result = caseIRelationshipMapping(iOneToMany);
				if (result == null)
					result = caseIAttributeMapping(iOneToMany);
				if (result == null)
					result = caseIJpaSourceObject(iOneToMany);
				if (result == null)
					result = caseIJpaEObject(iOneToMany);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IMANY_TO_MANY : {
				IManyToMany iManyToMany = (IManyToMany) theEObject;
				T result = caseIManyToMany(iManyToMany);
				if (result == null)
					result = caseIMultiRelationshipMapping(iManyToMany);
				if (result == null)
					result = caseINonOwningMapping(iManyToMany);
				if (result == null)
					result = caseIRelationshipMapping(iManyToMany);
				if (result == null)
					result = caseIAttributeMapping(iManyToMany);
				if (result == null)
					result = caseIJpaSourceObject(iManyToMany);
				if (result == null)
					result = caseIJpaEObject(iManyToMany);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING : {
				ISingleRelationshipMapping iSingleRelationshipMapping = (ISingleRelationshipMapping) theEObject;
				T result = caseISingleRelationshipMapping(iSingleRelationshipMapping);
				if (result == null)
					result = caseIRelationshipMapping(iSingleRelationshipMapping);
				if (result == null)
					result = caseIAttributeMapping(iSingleRelationshipMapping);
				if (result == null)
					result = caseIJpaSourceObject(iSingleRelationshipMapping);
				if (result == null)
					result = caseIJpaEObject(iSingleRelationshipMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IMANY_TO_ONE : {
				IManyToOne iManyToOne = (IManyToOne) theEObject;
				T result = caseIManyToOne(iManyToOne);
				if (result == null)
					result = caseISingleRelationshipMapping(iManyToOne);
				if (result == null)
					result = caseIRelationshipMapping(iManyToOne);
				if (result == null)
					result = caseIAttributeMapping(iManyToOne);
				if (result == null)
					result = caseIJpaSourceObject(iManyToOne);
				if (result == null)
					result = caseIJpaEObject(iManyToOne);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IONE_TO_ONE : {
				IOneToOne iOneToOne = (IOneToOne) theEObject;
				T result = caseIOneToOne(iOneToOne);
				if (result == null)
					result = caseISingleRelationshipMapping(iOneToOne);
				if (result == null)
					result = caseINonOwningMapping(iOneToOne);
				if (result == null)
					result = caseIRelationshipMapping(iOneToOne);
				if (result == null)
					result = caseIAttributeMapping(iOneToOne);
				if (result == null)
					result = caseIJpaSourceObject(iOneToOne);
				if (result == null)
					result = caseIJpaEObject(iOneToOne);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IJOIN_TABLE : {
				IJoinTable iJoinTable = (IJoinTable) theEObject;
				T result = caseIJoinTable(iJoinTable);
				if (result == null)
					result = caseITable(iJoinTable);
				if (result == null)
					result = caseIJpaSourceObject(iJoinTable);
				if (result == null)
					result = caseIJpaEObject(iJoinTable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN : {
				IAbstractJoinColumn iAbstractJoinColumn = (IAbstractJoinColumn) theEObject;
				T result = caseIAbstractJoinColumn(iAbstractJoinColumn);
				if (result == null)
					result = caseINamedColumn(iAbstractJoinColumn);
				if (result == null)
					result = caseIJpaSourceObject(iAbstractJoinColumn);
				if (result == null)
					result = caseIJpaEObject(iAbstractJoinColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IJOIN_COLUMN : {
				IJoinColumn iJoinColumn = (IJoinColumn) theEObject;
				T result = caseIJoinColumn(iJoinColumn);
				if (result == null)
					result = caseIAbstractColumn(iJoinColumn);
				if (result == null)
					result = caseIAbstractJoinColumn(iJoinColumn);
				if (result == null)
					result = caseINamedColumn(iJoinColumn);
				if (result == null)
					result = caseIJpaSourceObject(iJoinColumn);
				if (result == null)
					result = caseIJpaEObject(iJoinColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IOVERRIDE : {
				IOverride iOverride = (IOverride) theEObject;
				T result = caseIOverride(iOverride);
				if (result == null)
					result = caseIJpaSourceObject(iOverride);
				if (result == null)
					result = caseIJpaEObject(iOverride);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IATTRIBUTE_OVERRIDE : {
				IAttributeOverride iAttributeOverride = (IAttributeOverride) theEObject;
				T result = caseIAttributeOverride(iAttributeOverride);
				if (result == null)
					result = caseIOverride(iAttributeOverride);
				if (result == null)
					result = caseIColumnMapping(iAttributeOverride);
				if (result == null)
					result = caseIJpaSourceObject(iAttributeOverride);
				if (result == null)
					result = caseIJpaEObject(iAttributeOverride);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IASSOCIATION_OVERRIDE : {
				IAssociationOverride iAssociationOverride = (IAssociationOverride) theEObject;
				T result = caseIAssociationOverride(iAssociationOverride);
				if (result == null)
					result = caseIOverride(iAssociationOverride);
				if (result == null)
					result = caseIJpaSourceObject(iAssociationOverride);
				if (result == null)
					result = caseIJpaEObject(iAssociationOverride);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IDISCRIMINATOR_COLUMN : {
				IDiscriminatorColumn iDiscriminatorColumn = (IDiscriminatorColumn) theEObject;
				T result = caseIDiscriminatorColumn(iDiscriminatorColumn);
				if (result == null)
					result = caseINamedColumn(iDiscriminatorColumn);
				if (result == null)
					result = caseIJpaSourceObject(iDiscriminatorColumn);
				if (result == null)
					result = caseIJpaEObject(iDiscriminatorColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.ISECONDARY_TABLE : {
				ISecondaryTable iSecondaryTable = (ISecondaryTable) theEObject;
				T result = caseISecondaryTable(iSecondaryTable);
				if (result == null)
					result = caseITable(iSecondaryTable);
				if (result == null)
					result = caseIJpaSourceObject(iSecondaryTable);
				if (result == null)
					result = caseIJpaEObject(iSecondaryTable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IPRIMARY_KEY_JOIN_COLUMN : {
				IPrimaryKeyJoinColumn iPrimaryKeyJoinColumn = (IPrimaryKeyJoinColumn) theEObject;
				T result = caseIPrimaryKeyJoinColumn(iPrimaryKeyJoinColumn);
				if (result == null)
					result = caseIAbstractJoinColumn(iPrimaryKeyJoinColumn);
				if (result == null)
					result = caseINamedColumn(iPrimaryKeyJoinColumn);
				if (result == null)
					result = caseIJpaSourceObject(iPrimaryKeyJoinColumn);
				if (result == null)
					result = caseIJpaEObject(iPrimaryKeyJoinColumn);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IGENERATOR : {
				IGenerator iGenerator = (IGenerator) theEObject;
				T result = caseIGenerator(iGenerator);
				if (result == null)
					result = caseIJpaSourceObject(iGenerator);
				if (result == null)
					result = caseIJpaEObject(iGenerator);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.ITABLE_GENERATOR : {
				ITableGenerator iTableGenerator = (ITableGenerator) theEObject;
				T result = caseITableGenerator(iTableGenerator);
				if (result == null)
					result = caseIGenerator(iTableGenerator);
				if (result == null)
					result = caseIJpaSourceObject(iTableGenerator);
				if (result == null)
					result = caseIJpaEObject(iTableGenerator);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.ISEQUENCE_GENERATOR : {
				ISequenceGenerator iSequenceGenerator = (ISequenceGenerator) theEObject;
				T result = caseISequenceGenerator(iSequenceGenerator);
				if (result == null)
					result = caseIGenerator(iSequenceGenerator);
				if (result == null)
					result = caseIJpaSourceObject(iSequenceGenerator);
				if (result == null)
					result = caseIJpaEObject(iSequenceGenerator);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IGENERATED_VALUE : {
				IGeneratedValue iGeneratedValue = (IGeneratedValue) theEObject;
				T result = caseIGeneratedValue(iGeneratedValue);
				if (result == null)
					result = caseIJpaSourceObject(iGeneratedValue);
				if (result == null)
					result = caseIJpaEObject(iGeneratedValue);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IQUERY : {
				IQuery iQuery = (IQuery) theEObject;
				T result = caseIQuery(iQuery);
				if (result == null)
					result = caseIJpaSourceObject(iQuery);
				if (result == null)
					result = caseIJpaEObject(iQuery);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.INAMED_QUERY : {
				INamedQuery iNamedQuery = (INamedQuery) theEObject;
				T result = caseINamedQuery(iNamedQuery);
				if (result == null)
					result = caseIJpaSourceObject(iNamedQuery);
				if (result == null)
					result = caseIQuery(iNamedQuery);
				if (result == null)
					result = caseIJpaEObject(iNamedQuery);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.INAMED_NATIVE_QUERY : {
				INamedNativeQuery iNamedNativeQuery = (INamedNativeQuery) theEObject;
				T result = caseINamedNativeQuery(iNamedNativeQuery);
				if (result == null)
					result = caseIJpaSourceObject(iNamedNativeQuery);
				if (result == null)
					result = caseIQuery(iNamedNativeQuery);
				if (result == null)
					result = caseIJpaEObject(iNamedNativeQuery);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.IQUERY_HINT : {
				IQueryHint iQueryHint = (IQueryHint) theEObject;
				T result = caseIQueryHint(iQueryHint);
				if (result == null)
					result = caseIJpaSourceObject(iQueryHint);
				if (result == null)
					result = caseIJpaEObject(iQueryHint);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case JpaCoreMappingsPackage.ICASCADE : {
				ICascade iCascade = (ICascade) theEObject;
				T result = caseICascade(iCascade);
				if (result == null)
					result = caseIJpaSourceObject(iCascade);
				if (result == null)
					result = caseIJpaEObject(iCascade);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default :
				return defaultCase(theEObject);
		}
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
} //JpaCoreMappingsSwitch
