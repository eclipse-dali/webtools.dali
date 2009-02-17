/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.resource.orm.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.jpt.core.resource.orm.AbstractXmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.BaseXmlEmbedded;
import org.eclipse.jpt.core.resource.orm.ColumnMapping;
import org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.core.resource.orm.XmlMultiRelationshipMapping;
import org.eclipse.jpt.core.resource.orm.XmlRelationshipMapping;
import org.eclipse.jpt.core.resource.orm.XmlSingleRelationshipMapping;

import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethodsHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlMutable;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned;

import org.eclipse.jpt.eclipselink1_1.core.resource.orm.*;

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
 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage
 * @generated
 */
public class EclipseLink1_1OrmSwitch<T>
{
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EclipseLink1_1OrmPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EclipseLink1_1OrmSwitch()
	{
		if (modelPackage == null)
		{
			modelPackage = EclipseLink1_1OrmPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject)
	{
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject)
	{
		if (theEClass.eContainer() == modelPackage)
		{
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else
		{
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject)
	{
		switch (classifierID)
		{
			case EclipseLink1_1OrmPackage.XML_ENTITY_MAPPINGS:
			{
				XmlEntityMappings xmlEntityMappings = (XmlEntityMappings)theEObject;
				T result = caseXmlEntityMappings(xmlEntityMappings);
				if (result == null) result = caseXmlEntityMappings_2(xmlEntityMappings);
				if (result == null) result = caseXmlEntityMappings_1(xmlEntityMappings);
				if (result == null) result = caseXmlConvertersHolder(xmlEntityMappings);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_ATTRIBUTE_MAPPING:
			{
				XmlAttributeMapping xmlAttributeMapping = (XmlAttributeMapping)theEObject;
				T result = caseXmlAttributeMapping(xmlAttributeMapping);
				if (result == null) result = caseXmlAttributeMapping_1(xmlAttributeMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_ID:
			{
				XmlId xmlId = (XmlId)theEObject;
				T result = caseXmlId(xmlId);
				if (result == null) result = caseXmlId_2(xmlId);
				if (result == null) result = caseXmlAttributeMapping(xmlId);
				if (result == null) result = caseXmlId_1(xmlId);
				if (result == null) result = caseXmlMutable(xmlId);
				if (result == null) result = caseXmlConvertibleMapping_1(xmlId);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlId);
				if (result == null) result = caseXmlAttributeMapping_1(xmlId);
				if (result == null) result = caseColumnMapping(xmlId);
				if (result == null) result = caseXmlConvertibleMapping(xmlId);
				if (result == null) result = caseXmlConverterHolder(xmlId);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_ID_IMPL:
			{
				XmlIdImpl xmlIdImpl = (XmlIdImpl)theEObject;
				T result = caseXmlIdImpl(xmlIdImpl);
				if (result == null) result = caseXmlIdImpl_2(xmlIdImpl);
				if (result == null) result = caseXmlId(xmlIdImpl);
				if (result == null) result = caseXmlIdImpl_1(xmlIdImpl);
				if (result == null) result = caseXmlId_2(xmlIdImpl);
				if (result == null) result = caseXmlAttributeMapping(xmlIdImpl);
				if (result == null) result = caseAbstractXmlAttributeMapping(xmlIdImpl);
				if (result == null) result = caseXmlId_1(xmlIdImpl);
				if (result == null) result = caseXmlMutable(xmlIdImpl);
				if (result == null) result = caseXmlConvertibleMapping_1(xmlIdImpl);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlIdImpl);
				if (result == null) result = caseXmlAttributeMapping_1(xmlIdImpl);
				if (result == null) result = caseColumnMapping(xmlIdImpl);
				if (result == null) result = caseXmlConvertibleMapping(xmlIdImpl);
				if (result == null) result = caseXmlConverterHolder(xmlIdImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_EMBEDDED_ID:
			{
				XmlEmbeddedId xmlEmbeddedId = (XmlEmbeddedId)theEObject;
				T result = caseXmlEmbeddedId(xmlEmbeddedId);
				if (result == null) result = caseXmlEmbeddedId_2(xmlEmbeddedId);
				if (result == null) result = caseXmlAttributeMapping(xmlEmbeddedId);
				if (result == null) result = caseXmlEmbeddedId_1(xmlEmbeddedId);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlEmbeddedId);
				if (result == null) result = caseXmlAttributeMapping_1(xmlEmbeddedId);
				if (result == null) result = caseBaseXmlEmbedded(xmlEmbeddedId);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_EMBEDDED_ID_IMPL:
			{
				XmlEmbeddedIdImpl xmlEmbeddedIdImpl = (XmlEmbeddedIdImpl)theEObject;
				T result = caseXmlEmbeddedIdImpl(xmlEmbeddedIdImpl);
				if (result == null) result = caseXmlEmbeddedIdImpl_2(xmlEmbeddedIdImpl);
				if (result == null) result = caseXmlEmbeddedId(xmlEmbeddedIdImpl);
				if (result == null) result = caseXmlEmbeddedIdImpl_1(xmlEmbeddedIdImpl);
				if (result == null) result = caseXmlEmbeddedId_2(xmlEmbeddedIdImpl);
				if (result == null) result = caseXmlAttributeMapping(xmlEmbeddedIdImpl);
				if (result == null) result = caseAbstractXmlAttributeMapping(xmlEmbeddedIdImpl);
				if (result == null) result = caseXmlEmbeddedId_1(xmlEmbeddedIdImpl);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlEmbeddedIdImpl);
				if (result == null) result = caseXmlAttributeMapping_1(xmlEmbeddedIdImpl);
				if (result == null) result = caseBaseXmlEmbedded(xmlEmbeddedIdImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_EMBEDDED:
			{
				XmlEmbedded xmlEmbedded = (XmlEmbedded)theEObject;
				T result = caseXmlEmbedded(xmlEmbedded);
				if (result == null) result = caseXmlEmbedded_2(xmlEmbedded);
				if (result == null) result = caseXmlAttributeMapping(xmlEmbedded);
				if (result == null) result = caseXmlEmbedded_1(xmlEmbedded);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlEmbedded);
				if (result == null) result = caseXmlAttributeMapping_1(xmlEmbedded);
				if (result == null) result = caseBaseXmlEmbedded(xmlEmbedded);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_EMBEDDED_IMPL:
			{
				XmlEmbeddedImpl xmlEmbeddedImpl = (XmlEmbeddedImpl)theEObject;
				T result = caseXmlEmbeddedImpl(xmlEmbeddedImpl);
				if (result == null) result = caseXmlEmbeddedImpl_2(xmlEmbeddedImpl);
				if (result == null) result = caseXmlEmbedded(xmlEmbeddedImpl);
				if (result == null) result = caseXmlEmbeddedImpl_1(xmlEmbeddedImpl);
				if (result == null) result = caseXmlEmbedded_2(xmlEmbeddedImpl);
				if (result == null) result = caseXmlAttributeMapping(xmlEmbeddedImpl);
				if (result == null) result = caseAbstractXmlAttributeMapping(xmlEmbeddedImpl);
				if (result == null) result = caseXmlEmbedded_1(xmlEmbeddedImpl);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlEmbeddedImpl);
				if (result == null) result = caseXmlAttributeMapping_1(xmlEmbeddedImpl);
				if (result == null) result = caseBaseXmlEmbedded(xmlEmbeddedImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_BASIC:
			{
				XmlBasic xmlBasic = (XmlBasic)theEObject;
				T result = caseXmlBasic(xmlBasic);
				if (result == null) result = caseXmlBasic_2(xmlBasic);
				if (result == null) result = caseXmlAttributeMapping(xmlBasic);
				if (result == null) result = caseXmlBasic_1(xmlBasic);
				if (result == null) result = caseXmlMutable(xmlBasic);
				if (result == null) result = caseXmlConvertibleMapping_1(xmlBasic);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlBasic);
				if (result == null) result = caseXmlAttributeMapping_1(xmlBasic);
				if (result == null) result = caseColumnMapping(xmlBasic);
				if (result == null) result = caseXmlConvertibleMapping(xmlBasic);
				if (result == null) result = caseXmlConverterHolder(xmlBasic);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL:
			{
				XmlBasicImpl xmlBasicImpl = (XmlBasicImpl)theEObject;
				T result = caseXmlBasicImpl(xmlBasicImpl);
				if (result == null) result = caseXmlBasicImpl_2(xmlBasicImpl);
				if (result == null) result = caseXmlBasic(xmlBasicImpl);
				if (result == null) result = caseXmlBasicImpl_1(xmlBasicImpl);
				if (result == null) result = caseXmlBasic_2(xmlBasicImpl);
				if (result == null) result = caseXmlAttributeMapping(xmlBasicImpl);
				if (result == null) result = caseAbstractXmlAttributeMapping(xmlBasicImpl);
				if (result == null) result = caseXmlBasic_1(xmlBasicImpl);
				if (result == null) result = caseXmlMutable(xmlBasicImpl);
				if (result == null) result = caseXmlConvertibleMapping_1(xmlBasicImpl);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlBasicImpl);
				if (result == null) result = caseXmlAttributeMapping_1(xmlBasicImpl);
				if (result == null) result = caseColumnMapping(xmlBasicImpl);
				if (result == null) result = caseXmlConvertibleMapping(xmlBasicImpl);
				if (result == null) result = caseXmlConverterHolder(xmlBasicImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_VERSION:
			{
				XmlVersion xmlVersion = (XmlVersion)theEObject;
				T result = caseXmlVersion(xmlVersion);
				if (result == null) result = caseXmlVersion_2(xmlVersion);
				if (result == null) result = caseXmlAttributeMapping(xmlVersion);
				if (result == null) result = caseXmlVersion_1(xmlVersion);
				if (result == null) result = caseXmlMutable(xmlVersion);
				if (result == null) result = caseXmlConvertibleMapping_1(xmlVersion);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlVersion);
				if (result == null) result = caseXmlAttributeMapping_1(xmlVersion);
				if (result == null) result = caseColumnMapping(xmlVersion);
				if (result == null) result = caseXmlConvertibleMapping(xmlVersion);
				if (result == null) result = caseXmlConverterHolder(xmlVersion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_VERSION_IMPL:
			{
				XmlVersionImpl xmlVersionImpl = (XmlVersionImpl)theEObject;
				T result = caseXmlVersionImpl(xmlVersionImpl);
				if (result == null) result = caseXmlVersionImpl_2(xmlVersionImpl);
				if (result == null) result = caseXmlVersion(xmlVersionImpl);
				if (result == null) result = caseXmlVersionImpl_1(xmlVersionImpl);
				if (result == null) result = caseXmlVersion_2(xmlVersionImpl);
				if (result == null) result = caseXmlAttributeMapping(xmlVersionImpl);
				if (result == null) result = caseAbstractXmlAttributeMapping(xmlVersionImpl);
				if (result == null) result = caseXmlVersion_1(xmlVersionImpl);
				if (result == null) result = caseXmlMutable(xmlVersionImpl);
				if (result == null) result = caseXmlConvertibleMapping_1(xmlVersionImpl);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlVersionImpl);
				if (result == null) result = caseXmlAttributeMapping_1(xmlVersionImpl);
				if (result == null) result = caseColumnMapping(xmlVersionImpl);
				if (result == null) result = caseXmlConvertibleMapping(xmlVersionImpl);
				if (result == null) result = caseXmlConverterHolder(xmlVersionImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_ONE_TO_ONE:
			{
				XmlOneToOne xmlOneToOne = (XmlOneToOne)theEObject;
				T result = caseXmlOneToOne(xmlOneToOne);
				if (result == null) result = caseXmlOneToOne_2(xmlOneToOne);
				if (result == null) result = caseXmlAttributeMapping(xmlOneToOne);
				if (result == null) result = caseXmlOneToOne_1(xmlOneToOne);
				if (result == null) result = caseXmlPrivateOwned(xmlOneToOne);
				if (result == null) result = caseXmlJoinFetch(xmlOneToOne);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlOneToOne);
				if (result == null) result = caseXmlAttributeMapping_1(xmlOneToOne);
				if (result == null) result = caseXmlSingleRelationshipMapping(xmlOneToOne);
				if (result == null) result = caseXmlRelationshipMapping(xmlOneToOne);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_ONE_TO_ONE_IMPL:
			{
				XmlOneToOneImpl xmlOneToOneImpl = (XmlOneToOneImpl)theEObject;
				T result = caseXmlOneToOneImpl(xmlOneToOneImpl);
				if (result == null) result = caseXmlOneToOneImpl_2(xmlOneToOneImpl);
				if (result == null) result = caseXmlOneToOne(xmlOneToOneImpl);
				if (result == null) result = caseXmlOneToOneImpl_1(xmlOneToOneImpl);
				if (result == null) result = caseXmlOneToOne_2(xmlOneToOneImpl);
				if (result == null) result = caseXmlAttributeMapping(xmlOneToOneImpl);
				if (result == null) result = caseAbstractXmlAttributeMapping(xmlOneToOneImpl);
				if (result == null) result = caseXmlOneToOne_1(xmlOneToOneImpl);
				if (result == null) result = caseXmlPrivateOwned(xmlOneToOneImpl);
				if (result == null) result = caseXmlJoinFetch(xmlOneToOneImpl);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlOneToOneImpl);
				if (result == null) result = caseXmlAttributeMapping_1(xmlOneToOneImpl);
				if (result == null) result = caseXmlSingleRelationshipMapping(xmlOneToOneImpl);
				if (result == null) result = caseXmlRelationshipMapping(xmlOneToOneImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_ONE_TO_MANY:
			{
				XmlOneToMany xmlOneToMany = (XmlOneToMany)theEObject;
				T result = caseXmlOneToMany(xmlOneToMany);
				if (result == null) result = caseXmlOneToMany_2(xmlOneToMany);
				if (result == null) result = caseXmlAttributeMapping(xmlOneToMany);
				if (result == null) result = caseXmlOneToMany_1(xmlOneToMany);
				if (result == null) result = caseXmlPrivateOwned(xmlOneToMany);
				if (result == null) result = caseXmlJoinFetch(xmlOneToMany);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlOneToMany);
				if (result == null) result = caseXmlAttributeMapping_1(xmlOneToMany);
				if (result == null) result = caseXmlMultiRelationshipMapping(xmlOneToMany);
				if (result == null) result = caseXmlRelationshipMapping(xmlOneToMany);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_ONE_TO_MANY_IMPL:
			{
				XmlOneToManyImpl xmlOneToManyImpl = (XmlOneToManyImpl)theEObject;
				T result = caseXmlOneToManyImpl(xmlOneToManyImpl);
				if (result == null) result = caseXmlOneToManyImpl_2(xmlOneToManyImpl);
				if (result == null) result = caseXmlOneToMany(xmlOneToManyImpl);
				if (result == null) result = caseXmlOneToManyImpl_1(xmlOneToManyImpl);
				if (result == null) result = caseXmlOneToMany_2(xmlOneToManyImpl);
				if (result == null) result = caseXmlAttributeMapping(xmlOneToManyImpl);
				if (result == null) result = caseAbstractXmlAttributeMapping(xmlOneToManyImpl);
				if (result == null) result = caseXmlOneToMany_1(xmlOneToManyImpl);
				if (result == null) result = caseXmlPrivateOwned(xmlOneToManyImpl);
				if (result == null) result = caseXmlJoinFetch(xmlOneToManyImpl);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlOneToManyImpl);
				if (result == null) result = caseXmlAttributeMapping_1(xmlOneToManyImpl);
				if (result == null) result = caseXmlMultiRelationshipMapping(xmlOneToManyImpl);
				if (result == null) result = caseXmlRelationshipMapping(xmlOneToManyImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_MANY_TO_ONE:
			{
				XmlManyToOne xmlManyToOne = (XmlManyToOne)theEObject;
				T result = caseXmlManyToOne(xmlManyToOne);
				if (result == null) result = caseXmlManyToOne_2(xmlManyToOne);
				if (result == null) result = caseXmlAttributeMapping(xmlManyToOne);
				if (result == null) result = caseXmlManyToOne_1(xmlManyToOne);
				if (result == null) result = caseXmlJoinFetch(xmlManyToOne);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlManyToOne);
				if (result == null) result = caseXmlAttributeMapping_1(xmlManyToOne);
				if (result == null) result = caseXmlSingleRelationshipMapping(xmlManyToOne);
				if (result == null) result = caseXmlRelationshipMapping(xmlManyToOne);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_MANY_TO_ONE_IMPL:
			{
				XmlManyToOneImpl xmlManyToOneImpl = (XmlManyToOneImpl)theEObject;
				T result = caseXmlManyToOneImpl(xmlManyToOneImpl);
				if (result == null) result = caseXmlManyToOneImpl_2(xmlManyToOneImpl);
				if (result == null) result = caseXmlManyToOne(xmlManyToOneImpl);
				if (result == null) result = caseXmlManyToOneImpl_1(xmlManyToOneImpl);
				if (result == null) result = caseXmlManyToOne_2(xmlManyToOneImpl);
				if (result == null) result = caseXmlAttributeMapping(xmlManyToOneImpl);
				if (result == null) result = caseAbstractXmlAttributeMapping(xmlManyToOneImpl);
				if (result == null) result = caseXmlManyToOne_1(xmlManyToOneImpl);
				if (result == null) result = caseXmlJoinFetch(xmlManyToOneImpl);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlManyToOneImpl);
				if (result == null) result = caseXmlAttributeMapping_1(xmlManyToOneImpl);
				if (result == null) result = caseXmlSingleRelationshipMapping(xmlManyToOneImpl);
				if (result == null) result = caseXmlRelationshipMapping(xmlManyToOneImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_MANY_TO_MANY:
			{
				XmlManyToMany xmlManyToMany = (XmlManyToMany)theEObject;
				T result = caseXmlManyToMany(xmlManyToMany);
				if (result == null) result = caseXmlManyToMany_2(xmlManyToMany);
				if (result == null) result = caseXmlAttributeMapping(xmlManyToMany);
				if (result == null) result = caseXmlManyToMany_1(xmlManyToMany);
				if (result == null) result = caseXmlJoinFetch(xmlManyToMany);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlManyToMany);
				if (result == null) result = caseXmlAttributeMapping_1(xmlManyToMany);
				if (result == null) result = caseXmlMultiRelationshipMapping(xmlManyToMany);
				if (result == null) result = caseXmlRelationshipMapping(xmlManyToMany);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_MANY_TO_MANY_IMPL:
			{
				XmlManyToManyImpl xmlManyToManyImpl = (XmlManyToManyImpl)theEObject;
				T result = caseXmlManyToManyImpl(xmlManyToManyImpl);
				if (result == null) result = caseXmlManyToManyImpl_2(xmlManyToManyImpl);
				if (result == null) result = caseXmlManyToMany(xmlManyToManyImpl);
				if (result == null) result = caseXmlManyToManyImpl_1(xmlManyToManyImpl);
				if (result == null) result = caseXmlManyToMany_2(xmlManyToManyImpl);
				if (result == null) result = caseXmlAttributeMapping(xmlManyToManyImpl);
				if (result == null) result = caseAbstractXmlAttributeMapping(xmlManyToManyImpl);
				if (result == null) result = caseXmlManyToMany_1(xmlManyToManyImpl);
				if (result == null) result = caseXmlJoinFetch(xmlManyToManyImpl);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlManyToManyImpl);
				if (result == null) result = caseXmlAttributeMapping_1(xmlManyToManyImpl);
				if (result == null) result = caseXmlMultiRelationshipMapping(xmlManyToManyImpl);
				if (result == null) result = caseXmlRelationshipMapping(xmlManyToManyImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_BASIC_COLLECTION:
			{
				XmlBasicCollection xmlBasicCollection = (XmlBasicCollection)theEObject;
				T result = caseXmlBasicCollection(xmlBasicCollection);
				if (result == null) result = caseXmlBasicCollection_1(xmlBasicCollection);
				if (result == null) result = caseXmlAttributeMapping(xmlBasicCollection);
				if (result == null) result = caseXmlAttributeMapping_1(xmlBasicCollection);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlBasicCollection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_BASIC_COLLECTION_IMPL:
			{
				XmlBasicCollectionImpl xmlBasicCollectionImpl = (XmlBasicCollectionImpl)theEObject;
				T result = caseXmlBasicCollectionImpl(xmlBasicCollectionImpl);
				if (result == null) result = caseXmlBasicCollectionImpl_1(xmlBasicCollectionImpl);
				if (result == null) result = caseXmlBasicCollection(xmlBasicCollectionImpl);
				if (result == null) result = caseAbstractXmlAttributeMapping(xmlBasicCollectionImpl);
				if (result == null) result = caseXmlBasicCollection_1(xmlBasicCollectionImpl);
				if (result == null) result = caseXmlAttributeMapping(xmlBasicCollectionImpl);
				if (result == null) result = caseXmlAttributeMapping_1(xmlBasicCollectionImpl);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlBasicCollectionImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_BASIC_MAP:
			{
				XmlBasicMap xmlBasicMap = (XmlBasicMap)theEObject;
				T result = caseXmlBasicMap(xmlBasicMap);
				if (result == null) result = caseXmlBasicMap_1(xmlBasicMap);
				if (result == null) result = caseXmlAttributeMapping(xmlBasicMap);
				if (result == null) result = caseXmlAttributeMapping_1(xmlBasicMap);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlBasicMap);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_BASIC_MAP_IMPL:
			{
				XmlBasicMapImpl xmlBasicMapImpl = (XmlBasicMapImpl)theEObject;
				T result = caseXmlBasicMapImpl(xmlBasicMapImpl);
				if (result == null) result = caseXmlBasicMapImpl_1(xmlBasicMapImpl);
				if (result == null) result = caseXmlBasicMap(xmlBasicMapImpl);
				if (result == null) result = caseAbstractXmlAttributeMapping(xmlBasicMapImpl);
				if (result == null) result = caseXmlBasicMap_1(xmlBasicMapImpl);
				if (result == null) result = caseXmlAttributeMapping(xmlBasicMapImpl);
				if (result == null) result = caseXmlAttributeMapping_1(xmlBasicMapImpl);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlBasicMapImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_TRANSFORMATION:
			{
				XmlTransformation xmlTransformation = (XmlTransformation)theEObject;
				T result = caseXmlTransformation(xmlTransformation);
				if (result == null) result = caseXmlTransformation_1(xmlTransformation);
				if (result == null) result = caseXmlAttributeMapping(xmlTransformation);
				if (result == null) result = caseXmlAttributeMapping_1(xmlTransformation);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlTransformation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_TRANSFORMATION_IMPL:
			{
				XmlTransformationImpl xmlTransformationImpl = (XmlTransformationImpl)theEObject;
				T result = caseXmlTransformationImpl(xmlTransformationImpl);
				if (result == null) result = caseXmlTransformationImpl_1(xmlTransformationImpl);
				if (result == null) result = caseXmlTransformation(xmlTransformationImpl);
				if (result == null) result = caseAbstractXmlAttributeMapping(xmlTransformationImpl);
				if (result == null) result = caseXmlTransformation_1(xmlTransformationImpl);
				if (result == null) result = caseXmlAttributeMapping(xmlTransformationImpl);
				if (result == null) result = caseXmlAttributeMapping_1(xmlTransformationImpl);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlTransformationImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_VARIABLE_ONE_TO_ONE:
			{
				XmlVariableOneToOne xmlVariableOneToOne = (XmlVariableOneToOne)theEObject;
				T result = caseXmlVariableOneToOne(xmlVariableOneToOne);
				if (result == null) result = caseXmlVariableOneToOne_1(xmlVariableOneToOne);
				if (result == null) result = caseXmlAttributeMapping(xmlVariableOneToOne);
				if (result == null) result = caseXmlAttributeMapping_1(xmlVariableOneToOne);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlVariableOneToOne);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_VARIABLE_ONE_TO_ONE_IMPL:
			{
				XmlVariableOneToOneImpl xmlVariableOneToOneImpl = (XmlVariableOneToOneImpl)theEObject;
				T result = caseXmlVariableOneToOneImpl(xmlVariableOneToOneImpl);
				if (result == null) result = caseXmlVariableOneToOneImpl_1(xmlVariableOneToOneImpl);
				if (result == null) result = caseXmlVariableOneToOne(xmlVariableOneToOneImpl);
				if (result == null) result = caseAbstractXmlAttributeMapping(xmlVariableOneToOneImpl);
				if (result == null) result = caseXmlVariableOneToOne_1(xmlVariableOneToOneImpl);
				if (result == null) result = caseXmlAttributeMapping(xmlVariableOneToOneImpl);
				if (result == null) result = caseXmlAttributeMapping_1(xmlVariableOneToOneImpl);
				if (result == null) result = caseXmlAccessMethodsHolder(xmlVariableOneToOneImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_TRANSIENT:
			{
				XmlTransient xmlTransient = (XmlTransient)theEObject;
				T result = caseXmlTransient(xmlTransient);
				if (result == null) result = caseXmlTransient_1(xmlTransient);
				if (result == null) result = caseXmlAttributeMapping(xmlTransient);
				if (result == null) result = caseXmlAttributeMapping_1(xmlTransient);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EclipseLink1_1OrmPackage.XML_TRANSIENT_IMPL:
			{
				XmlTransientImpl xmlTransientImpl = (XmlTransientImpl)theEObject;
				T result = caseXmlTransientImpl(xmlTransientImpl);
				if (result == null) result = caseXmlTransientImpl_1(xmlTransientImpl);
				if (result == null) result = caseXmlTransient(xmlTransientImpl);
				if (result == null) result = caseAbstractXmlAttributeMapping(xmlTransientImpl);
				if (result == null) result = caseXmlTransient_1(xmlTransientImpl);
				if (result == null) result = caseXmlAttributeMapping(xmlTransientImpl);
				if (result == null) result = caseXmlAttributeMapping_1(xmlTransientImpl);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Entity Mappings</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Entity Mappings</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEntityMappings(XmlEntityMappings object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Attribute Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Attribute Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlAttributeMapping(XmlAttributeMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Id</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Id</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlId(XmlId object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Id Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Id Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlIdImpl(XmlIdImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Embedded Id</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Embedded Id</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEmbeddedId(XmlEmbeddedId object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Embedded Id Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Embedded Id Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEmbeddedIdImpl(XmlEmbeddedIdImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Embedded</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Embedded</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEmbedded(XmlEmbedded object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Embedded Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Embedded Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEmbeddedImpl(XmlEmbeddedImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Basic</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Basic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlBasic(XmlBasic object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Basic Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Basic Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlBasicImpl(XmlBasicImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Version</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlVersion(XmlVersion object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Version Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Version Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlVersionImpl(XmlVersionImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml One To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml One To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlOneToOne(XmlOneToOne object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml One To One Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml One To One Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlOneToOneImpl(XmlOneToOneImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml One To Many</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml One To Many</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlOneToMany(XmlOneToMany object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml One To Many Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml One To Many Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlOneToManyImpl(XmlOneToManyImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Many To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Many To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlManyToOne(XmlManyToOne object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Many To One Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Many To One Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlManyToOneImpl(XmlManyToOneImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Many To Many</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Many To Many</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlManyToMany(XmlManyToMany object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Many To Many Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Many To Many Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlManyToManyImpl(XmlManyToManyImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Basic Collection</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Basic Collection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlBasicCollection(XmlBasicCollection object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Basic Collection Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Basic Collection Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlBasicCollectionImpl(XmlBasicCollectionImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Basic Map</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Basic Map</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlBasicMap(XmlBasicMap object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Basic Map Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Basic Map Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlBasicMapImpl(XmlBasicMapImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Transformation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Transformation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlTransformation(XmlTransformation object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Transformation Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Transformation Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlTransformationImpl(XmlTransformationImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Variable One To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Variable One To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlVariableOneToOne(XmlVariableOneToOne object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Variable One To One Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Variable One To One Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlVariableOneToOneImpl(XmlVariableOneToOneImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Transient</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Transient</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlTransient(XmlTransient object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Transient Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Transient Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlTransientImpl(XmlTransientImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Entity Mappings</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Entity Mappings</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEntityMappings_1(org.eclipse.jpt.core.resource.orm.XmlEntityMappings object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Converters Holder</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Converters Holder</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlConvertersHolder(XmlConvertersHolder object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Entity Mappings</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Entity Mappings</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEntityMappings_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Attribute Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Attribute Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlAttributeMapping_1(org.eclipse.jpt.core.resource.orm.XmlAttributeMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Column Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Column Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseColumnMapping(ColumnMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Convertible Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Convertible Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlConvertibleMapping(XmlConvertibleMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Id</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Id</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlId_1(org.eclipse.jpt.core.resource.orm.XmlId object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Mutable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Mutable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlMutable(XmlMutable object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Converter Holder</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Converter Holder</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlConverterHolder(XmlConverterHolder object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Convertible Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Convertible Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlConvertibleMapping_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Access Methods Holder</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Access Methods Holder</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlAccessMethodsHolder(XmlAccessMethodsHolder object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Id</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Id</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlId_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlId object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Xml Attribute Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Xml Attribute Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractXmlAttributeMapping(AbstractXmlAttributeMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Id Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Id Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlIdImpl_1(org.eclipse.jpt.core.resource.orm.XmlIdImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Id Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Id Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlIdImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlIdImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Base Xml Embedded</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Base Xml Embedded</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBaseXmlEmbedded(BaseXmlEmbedded object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Embedded Id</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Embedded Id</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEmbeddedId_1(org.eclipse.jpt.core.resource.orm.XmlEmbeddedId object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Embedded Id</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Embedded Id</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEmbeddedId_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedId object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Embedded Id Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Embedded Id Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEmbeddedIdImpl_1(org.eclipse.jpt.core.resource.orm.XmlEmbeddedIdImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Embedded Id Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Embedded Id Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEmbeddedIdImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedIdImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Embedded</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Embedded</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEmbedded_1(org.eclipse.jpt.core.resource.orm.XmlEmbedded object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Embedded</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Embedded</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEmbedded_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbedded object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Embedded Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Embedded Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEmbeddedImpl_1(org.eclipse.jpt.core.resource.orm.XmlEmbeddedImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Embedded Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Embedded Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlEmbeddedImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Basic</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Basic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlBasic_1(org.eclipse.jpt.core.resource.orm.XmlBasic object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Basic</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Basic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlBasic_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Basic Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Basic Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlBasicImpl_1(org.eclipse.jpt.core.resource.orm.XmlBasicImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Basic Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Basic Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlBasicImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Version</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlVersion_1(org.eclipse.jpt.core.resource.orm.XmlVersion object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Version</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlVersion_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Version Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Version Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlVersionImpl_1(org.eclipse.jpt.core.resource.orm.XmlVersionImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Version Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Version Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlVersionImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersionImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Relationship Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Relationship Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlRelationshipMapping(XmlRelationshipMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Single Relationship Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Single Relationship Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlSingleRelationshipMapping(XmlSingleRelationshipMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml One To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml One To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlOneToOne_1(org.eclipse.jpt.core.resource.orm.XmlOneToOne object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Private Owned</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Private Owned</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlPrivateOwned(XmlPrivateOwned object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Join Fetch</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Join Fetch</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlJoinFetch(XmlJoinFetch object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml One To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml One To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlOneToOne_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml One To One Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml One To One Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlOneToOneImpl_1(org.eclipse.jpt.core.resource.orm.XmlOneToOneImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml One To One Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml One To One Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlOneToOneImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOneImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Multi Relationship Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Multi Relationship Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlMultiRelationshipMapping(XmlMultiRelationshipMapping object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml One To Many</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml One To Many</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlOneToMany_1(org.eclipse.jpt.core.resource.orm.XmlOneToMany object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml One To Many</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml One To Many</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlOneToMany_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml One To Many Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml One To Many Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlOneToManyImpl_1(org.eclipse.jpt.core.resource.orm.XmlOneToManyImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml One To Many Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml One To Many Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlOneToManyImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToManyImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Many To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Many To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlManyToOne_1(org.eclipse.jpt.core.resource.orm.XmlManyToOne object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Many To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Many To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlManyToOne_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Many To One Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Many To One Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlManyToOneImpl_1(org.eclipse.jpt.core.resource.orm.XmlManyToOneImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Many To One Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Many To One Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlManyToOneImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOneImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Many To Many</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Many To Many</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlManyToMany_1(org.eclipse.jpt.core.resource.orm.XmlManyToMany object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Many To Many</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Many To Many</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlManyToMany_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Many To Many Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Many To Many Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlManyToManyImpl_1(org.eclipse.jpt.core.resource.orm.XmlManyToManyImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Many To Many Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Many To Many Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlManyToManyImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToManyImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Basic Collection</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Basic Collection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlBasicCollection_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollection object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Basic Collection Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Basic Collection Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlBasicCollectionImpl_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollectionImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Basic Map</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Basic Map</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlBasicMap_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMap object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Basic Map Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Basic Map Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlBasicMapImpl_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMapImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Transformation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Transformation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlTransformation_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformation object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Transformation Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Transformation Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlTransformationImpl_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformationImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Variable One To One</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Variable One To One</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlVariableOneToOne_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlVariableOneToOne object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Variable One To One Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Variable One To One Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlVariableOneToOneImpl_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlVariableOneToOneImpl object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Transient</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Transient</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlTransient_1(org.eclipse.jpt.core.resource.orm.XmlTransient object)
	{
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xml Transient Impl</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xml Transient Impl</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXmlTransientImpl_1(org.eclipse.jpt.core.resource.orm.XmlTransientImpl object)
	{
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
	public T defaultCase(EObject object)
	{
		return null;
	}

} //EclipseLink1_1OrmSwitch
