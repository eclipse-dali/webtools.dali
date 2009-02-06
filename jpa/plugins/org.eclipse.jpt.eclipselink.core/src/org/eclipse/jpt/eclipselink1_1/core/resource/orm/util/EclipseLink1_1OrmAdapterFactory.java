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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage
 * @generated
 */
public class EclipseLink1_1OrmAdapterFactory extends AdapterFactoryImpl
{
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EclipseLink1_1OrmPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EclipseLink1_1OrmAdapterFactory()
	{
		if (modelPackage == null)
		{
			modelPackage = EclipseLink1_1OrmPackage.eINSTANCE;
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
	public boolean isFactoryForType(Object object)
	{
		if (object == modelPackage)
		{
			return true;
		}
		if (object instanceof EObject)
		{
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EclipseLink1_1OrmSwitch<Adapter> modelSwitch =
		new EclipseLink1_1OrmSwitch<Adapter>()
		{
			@Override
			public Adapter caseXmlEntityMappings(XmlEntityMappings object)
			{
				return createXmlEntityMappingsAdapter();
			}
			@Override
			public Adapter caseXmlAttributeMapping(XmlAttributeMapping object)
			{
				return createXmlAttributeMappingAdapter();
			}
			@Override
			public Adapter caseXmlId(XmlId object)
			{
				return createXmlIdAdapter();
			}
			@Override
			public Adapter caseXmlIdImpl(XmlIdImpl object)
			{
				return createXmlIdImplAdapter();
			}
			@Override
			public Adapter caseXmlEmbeddedId(XmlEmbeddedId object)
			{
				return createXmlEmbeddedIdAdapter();
			}
			@Override
			public Adapter caseXmlEmbeddedIdImpl(XmlEmbeddedIdImpl object)
			{
				return createXmlEmbeddedIdImplAdapter();
			}
			@Override
			public Adapter caseXmlEmbedded(XmlEmbedded object)
			{
				return createXmlEmbeddedAdapter();
			}
			@Override
			public Adapter caseXmlEmbeddedImpl(XmlEmbeddedImpl object)
			{
				return createXmlEmbeddedImplAdapter();
			}
			@Override
			public Adapter caseXmlBasic(XmlBasic object)
			{
				return createXmlBasicAdapter();
			}
			@Override
			public Adapter caseXmlBasicImpl(XmlBasicImpl object)
			{
				return createXmlBasicImplAdapter();
			}
			@Override
			public Adapter caseXmlVersion(XmlVersion object)
			{
				return createXmlVersionAdapter();
			}
			@Override
			public Adapter caseXmlVersionImpl(XmlVersionImpl object)
			{
				return createXmlVersionImplAdapter();
			}
			@Override
			public Adapter caseXmlOneToOne(XmlOneToOne object)
			{
				return createXmlOneToOneAdapter();
			}
			@Override
			public Adapter caseXmlOneToOneImpl(XmlOneToOneImpl object)
			{
				return createXmlOneToOneImplAdapter();
			}
			@Override
			public Adapter caseXmlOneToMany(XmlOneToMany object)
			{
				return createXmlOneToManyAdapter();
			}
			@Override
			public Adapter caseXmlOneToManyImpl(XmlOneToManyImpl object)
			{
				return createXmlOneToManyImplAdapter();
			}
			@Override
			public Adapter caseXmlManyToOne(XmlManyToOne object)
			{
				return createXmlManyToOneAdapter();
			}
			@Override
			public Adapter caseXmlManyToOneImpl(XmlManyToOneImpl object)
			{
				return createXmlManyToOneImplAdapter();
			}
			@Override
			public Adapter caseXmlManyToMany(XmlManyToMany object)
			{
				return createXmlManyToManyAdapter();
			}
			@Override
			public Adapter caseXmlManyToManyImpl(XmlManyToManyImpl object)
			{
				return createXmlManyToManyImplAdapter();
			}
			@Override
			public Adapter caseXmlBasicCollection(XmlBasicCollection object)
			{
				return createXmlBasicCollectionAdapter();
			}
			@Override
			public Adapter caseXmlBasicCollectionImpl(XmlBasicCollectionImpl object)
			{
				return createXmlBasicCollectionImplAdapter();
			}
			@Override
			public Adapter caseXmlBasicMap(XmlBasicMap object)
			{
				return createXmlBasicMapAdapter();
			}
			@Override
			public Adapter caseXmlBasicMapImpl(XmlBasicMapImpl object)
			{
				return createXmlBasicMapImplAdapter();
			}
			@Override
			public Adapter caseXmlTransformation(XmlTransformation object)
			{
				return createXmlTransformationAdapter();
			}
			@Override
			public Adapter caseXmlTransformationImpl(XmlTransformationImpl object)
			{
				return createXmlTransformationImplAdapter();
			}
			@Override
			public Adapter caseXmlVariableOneToOne(XmlVariableOneToOne object)
			{
				return createXmlVariableOneToOneAdapter();
			}
			@Override
			public Adapter caseXmlVariableOneToOneImpl(XmlVariableOneToOneImpl object)
			{
				return createXmlVariableOneToOneImplAdapter();
			}
			@Override
			public Adapter caseXmlEntityMappings_1(org.eclipse.jpt.core.resource.orm.XmlEntityMappings object)
			{
				return createXmlEntityMappings_1Adapter();
			}
			@Override
			public Adapter caseXmlConvertersHolder(XmlConvertersHolder object)
			{
				return createXmlConvertersHolderAdapter();
			}
			@Override
			public Adapter caseXmlEntityMappings_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings object)
			{
				return createXmlEntityMappings_2Adapter();
			}
			@Override
			public Adapter caseXmlAttributeMapping_1(org.eclipse.jpt.core.resource.orm.XmlAttributeMapping object)
			{
				return createXmlAttributeMapping_1Adapter();
			}
			@Override
			public Adapter caseColumnMapping(ColumnMapping object)
			{
				return createColumnMappingAdapter();
			}
			@Override
			public Adapter caseXmlConvertibleMapping(XmlConvertibleMapping object)
			{
				return createXmlConvertibleMappingAdapter();
			}
			@Override
			public Adapter caseXmlId_1(org.eclipse.jpt.core.resource.orm.XmlId object)
			{
				return createXmlId_1Adapter();
			}
			@Override
			public Adapter caseXmlMutable(XmlMutable object)
			{
				return createXmlMutableAdapter();
			}
			@Override
			public Adapter caseXmlConverterHolder(XmlConverterHolder object)
			{
				return createXmlConverterHolderAdapter();
			}
			@Override
			public Adapter caseXmlConvertibleMapping_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping object)
			{
				return createXmlConvertibleMapping_1Adapter();
			}
			@Override
			public Adapter caseXmlAccessMethodsHolder(XmlAccessMethodsHolder object)
			{
				return createXmlAccessMethodsHolderAdapter();
			}
			@Override
			public Adapter caseXmlId_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlId object)
			{
				return createXmlId_2Adapter();
			}
			@Override
			public Adapter caseAbstractXmlAttributeMapping(AbstractXmlAttributeMapping object)
			{
				return createAbstractXmlAttributeMappingAdapter();
			}
			@Override
			public Adapter caseXmlIdImpl_1(org.eclipse.jpt.core.resource.orm.XmlIdImpl object)
			{
				return createXmlIdImpl_1Adapter();
			}
			@Override
			public Adapter caseXmlIdImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlIdImpl object)
			{
				return createXmlIdImpl_2Adapter();
			}
			@Override
			public Adapter caseBaseXmlEmbedded(BaseXmlEmbedded object)
			{
				return createBaseXmlEmbeddedAdapter();
			}
			@Override
			public Adapter caseXmlEmbeddedId_1(org.eclipse.jpt.core.resource.orm.XmlEmbeddedId object)
			{
				return createXmlEmbeddedId_1Adapter();
			}
			@Override
			public Adapter caseXmlEmbeddedId_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedId object)
			{
				return createXmlEmbeddedId_2Adapter();
			}
			@Override
			public Adapter caseXmlEmbeddedIdImpl_1(org.eclipse.jpt.core.resource.orm.XmlEmbeddedIdImpl object)
			{
				return createXmlEmbeddedIdImpl_1Adapter();
			}
			@Override
			public Adapter caseXmlEmbeddedIdImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedIdImpl object)
			{
				return createXmlEmbeddedIdImpl_2Adapter();
			}
			@Override
			public Adapter caseXmlEmbedded_1(org.eclipse.jpt.core.resource.orm.XmlEmbedded object)
			{
				return createXmlEmbedded_1Adapter();
			}
			@Override
			public Adapter caseXmlEmbedded_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbedded object)
			{
				return createXmlEmbedded_2Adapter();
			}
			@Override
			public Adapter caseXmlEmbeddedImpl_1(org.eclipse.jpt.core.resource.orm.XmlEmbeddedImpl object)
			{
				return createXmlEmbeddedImpl_1Adapter();
			}
			@Override
			public Adapter caseXmlEmbeddedImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedImpl object)
			{
				return createXmlEmbeddedImpl_2Adapter();
			}
			@Override
			public Adapter caseXmlBasic_1(org.eclipse.jpt.core.resource.orm.XmlBasic object)
			{
				return createXmlBasic_1Adapter();
			}
			@Override
			public Adapter caseXmlBasic_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic object)
			{
				return createXmlBasic_2Adapter();
			}
			@Override
			public Adapter caseXmlBasicImpl_1(org.eclipse.jpt.core.resource.orm.XmlBasicImpl object)
			{
				return createXmlBasicImpl_1Adapter();
			}
			@Override
			public Adapter caseXmlBasicImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicImpl object)
			{
				return createXmlBasicImpl_2Adapter();
			}
			@Override
			public Adapter caseXmlVersion_1(org.eclipse.jpt.core.resource.orm.XmlVersion object)
			{
				return createXmlVersion_1Adapter();
			}
			@Override
			public Adapter caseXmlVersion_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion object)
			{
				return createXmlVersion_2Adapter();
			}
			@Override
			public Adapter caseXmlVersionImpl_1(org.eclipse.jpt.core.resource.orm.XmlVersionImpl object)
			{
				return createXmlVersionImpl_1Adapter();
			}
			@Override
			public Adapter caseXmlVersionImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersionImpl object)
			{
				return createXmlVersionImpl_2Adapter();
			}
			@Override
			public Adapter caseXmlRelationshipMapping(XmlRelationshipMapping object)
			{
				return createXmlRelationshipMappingAdapter();
			}
			@Override
			public Adapter caseXmlSingleRelationshipMapping(XmlSingleRelationshipMapping object)
			{
				return createXmlSingleRelationshipMappingAdapter();
			}
			@Override
			public Adapter caseXmlOneToOne_1(org.eclipse.jpt.core.resource.orm.XmlOneToOne object)
			{
				return createXmlOneToOne_1Adapter();
			}
			@Override
			public Adapter caseXmlPrivateOwned(XmlPrivateOwned object)
			{
				return createXmlPrivateOwnedAdapter();
			}
			@Override
			public Adapter caseXmlJoinFetch(XmlJoinFetch object)
			{
				return createXmlJoinFetchAdapter();
			}
			@Override
			public Adapter caseXmlOneToOne_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne object)
			{
				return createXmlOneToOne_2Adapter();
			}
			@Override
			public Adapter caseXmlOneToOneImpl_1(org.eclipse.jpt.core.resource.orm.XmlOneToOneImpl object)
			{
				return createXmlOneToOneImpl_1Adapter();
			}
			@Override
			public Adapter caseXmlOneToOneImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOneImpl object)
			{
				return createXmlOneToOneImpl_2Adapter();
			}
			@Override
			public Adapter caseXmlMultiRelationshipMapping(XmlMultiRelationshipMapping object)
			{
				return createXmlMultiRelationshipMappingAdapter();
			}
			@Override
			public Adapter caseXmlOneToMany_1(org.eclipse.jpt.core.resource.orm.XmlOneToMany object)
			{
				return createXmlOneToMany_1Adapter();
			}
			@Override
			public Adapter caseXmlOneToMany_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany object)
			{
				return createXmlOneToMany_2Adapter();
			}
			@Override
			public Adapter caseXmlOneToManyImpl_1(org.eclipse.jpt.core.resource.orm.XmlOneToManyImpl object)
			{
				return createXmlOneToManyImpl_1Adapter();
			}
			@Override
			public Adapter caseXmlOneToManyImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToManyImpl object)
			{
				return createXmlOneToManyImpl_2Adapter();
			}
			@Override
			public Adapter caseXmlManyToOne_1(org.eclipse.jpt.core.resource.orm.XmlManyToOne object)
			{
				return createXmlManyToOne_1Adapter();
			}
			@Override
			public Adapter caseXmlManyToOne_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne object)
			{
				return createXmlManyToOne_2Adapter();
			}
			@Override
			public Adapter caseXmlManyToOneImpl_1(org.eclipse.jpt.core.resource.orm.XmlManyToOneImpl object)
			{
				return createXmlManyToOneImpl_1Adapter();
			}
			@Override
			public Adapter caseXmlManyToOneImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOneImpl object)
			{
				return createXmlManyToOneImpl_2Adapter();
			}
			@Override
			public Adapter caseXmlManyToMany_1(org.eclipse.jpt.core.resource.orm.XmlManyToMany object)
			{
				return createXmlManyToMany_1Adapter();
			}
			@Override
			public Adapter caseXmlManyToMany_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany object)
			{
				return createXmlManyToMany_2Adapter();
			}
			@Override
			public Adapter caseXmlManyToManyImpl_1(org.eclipse.jpt.core.resource.orm.XmlManyToManyImpl object)
			{
				return createXmlManyToManyImpl_1Adapter();
			}
			@Override
			public Adapter caseXmlManyToManyImpl_2(org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToManyImpl object)
			{
				return createXmlManyToManyImpl_2Adapter();
			}
			@Override
			public Adapter caseXmlBasicCollection_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollection object)
			{
				return createXmlBasicCollection_1Adapter();
			}
			@Override
			public Adapter caseXmlBasicCollectionImpl_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollectionImpl object)
			{
				return createXmlBasicCollectionImpl_1Adapter();
			}
			@Override
			public Adapter caseXmlBasicMap_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMap object)
			{
				return createXmlBasicMap_1Adapter();
			}
			@Override
			public Adapter caseXmlBasicMapImpl_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMapImpl object)
			{
				return createXmlBasicMapImpl_1Adapter();
			}
			@Override
			public Adapter caseXmlTransformation_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformation object)
			{
				return createXmlTransformation_1Adapter();
			}
			@Override
			public Adapter caseXmlTransformationImpl_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformationImpl object)
			{
				return createXmlTransformationImpl_1Adapter();
			}
			@Override
			public Adapter caseXmlVariableOneToOne_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlVariableOneToOne object)
			{
				return createXmlVariableOneToOne_1Adapter();
			}
			@Override
			public Adapter caseXmlVariableOneToOneImpl_1(org.eclipse.jpt.eclipselink.core.resource.orm.XmlVariableOneToOneImpl object)
			{
				return createXmlVariableOneToOneImpl_1Adapter();
			}
			@Override
			public Adapter defaultCase(EObject object)
			{
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
	public Adapter createAdapter(Notifier target)
	{
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEntityMappings
	 * @generated
	 */
	public Adapter createXmlEntityMappingsAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlAttributeMapping
	 * @generated
	 */
	public Adapter createXmlAttributeMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlId <em>Xml Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlId
	 * @generated
	 */
	public Adapter createXmlIdAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlIdImpl <em>Xml Id Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlIdImpl
	 * @generated
	 */
	public Adapter createXmlIdImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedId
	 * @generated
	 */
	public Adapter createXmlEmbeddedIdAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedIdImpl <em>Xml Embedded Id Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedIdImpl
	 * @generated
	 */
	public Adapter createXmlEmbeddedIdImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbedded <em>Xml Embedded</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbedded
	 * @generated
	 */
	public Adapter createXmlEmbeddedAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedImpl <em>Xml Embedded Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlEmbeddedImpl
	 * @generated
	 */
	public Adapter createXmlEmbeddedImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasic <em>Xml Basic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasic
	 * @generated
	 */
	public Adapter createXmlBasicAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicImpl <em>Xml Basic Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicImpl
	 * @generated
	 */
	public Adapter createXmlBasicImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVersion <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVersion
	 * @generated
	 */
	public Adapter createXmlVersionAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVersionImpl <em>Xml Version Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVersionImpl
	 * @generated
	 */
	public Adapter createXmlVersionImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToOne <em>Xml One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToOne
	 * @generated
	 */
	public Adapter createXmlOneToOneAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToOneImpl <em>Xml One To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToOneImpl
	 * @generated
	 */
	public Adapter createXmlOneToOneImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToMany <em>Xml One To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToMany
	 * @generated
	 */
	public Adapter createXmlOneToManyAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToManyImpl <em>Xml One To Many Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlOneToManyImpl
	 * @generated
	 */
	public Adapter createXmlOneToManyImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToOne <em>Xml Many To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToOne
	 * @generated
	 */
	public Adapter createXmlManyToOneAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToOneImpl <em>Xml Many To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToOneImpl
	 * @generated
	 */
	public Adapter createXmlManyToOneImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToMany
	 * @generated
	 */
	public Adapter createXmlManyToManyAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToManyImpl <em>Xml Many To Many Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlManyToManyImpl
	 * @generated
	 */
	public Adapter createXmlManyToManyImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicCollection <em>Xml Basic Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicCollection
	 * @generated
	 */
	public Adapter createXmlBasicCollectionAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicCollectionImpl <em>Xml Basic Collection Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicCollectionImpl
	 * @generated
	 */
	public Adapter createXmlBasicCollectionImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicMap <em>Xml Basic Map</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicMap
	 * @generated
	 */
	public Adapter createXmlBasicMapAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicMapImpl <em>Xml Basic Map Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlBasicMapImpl
	 * @generated
	 */
	public Adapter createXmlBasicMapImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransformation <em>Xml Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransformation
	 * @generated
	 */
	public Adapter createXmlTransformationAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransformationImpl <em>Xml Transformation Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransformationImpl
	 * @generated
	 */
	public Adapter createXmlTransformationImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVariableOneToOne <em>Xml Variable One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVariableOneToOne
	 * @generated
	 */
	public Adapter createXmlVariableOneToOneAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVariableOneToOneImpl <em>Xml Variable One To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlVariableOneToOneImpl
	 * @generated
	 */
	public Adapter createXmlVariableOneToOneImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEntityMappings
	 * @generated
	 */
	public Adapter createXmlEntityMappings_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder <em>Xml Converters Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder
	 * @generated
	 */
	public Adapter createXmlConvertersHolderAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings <em>Xml Entity Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings
	 * @generated
	 */
	public Adapter createXmlEntityMappings_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlAttributeMapping <em>Xml Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlAttributeMapping
	 * @generated
	 */
	public Adapter createXmlAttributeMapping_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.ColumnMapping <em>Column Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.ColumnMapping
	 * @generated
	 */
	public Adapter createColumnMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping <em>Xml Convertible Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping
	 * @generated
	 */
	public Adapter createXmlConvertibleMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlId <em>Xml Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlId
	 * @generated
	 */
	public Adapter createXmlId_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlMutable <em>Xml Mutable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlMutable
	 * @generated
	 */
	public Adapter createXmlMutableAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder <em>Xml Converter Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverterHolder
	 * @generated
	 */
	public Adapter createXmlConverterHolderAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping <em>Xml Convertible Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping
	 * @generated
	 */
	public Adapter createXmlConvertibleMapping_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethodsHolder <em>Xml Access Methods Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethodsHolder
	 * @generated
	 */
	public Adapter createXmlAccessMethodsHolderAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlId <em>Xml Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlId
	 * @generated
	 */
	public Adapter createXmlId_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.AbstractXmlAttributeMapping <em>Abstract Xml Attribute Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.AbstractXmlAttributeMapping
	 * @generated
	 */
	public Adapter createAbstractXmlAttributeMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlIdImpl <em>Xml Id Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlIdImpl
	 * @generated
	 */
	public Adapter createXmlIdImpl_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlIdImpl <em>Xml Id Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlIdImpl
	 * @generated
	 */
	public Adapter createXmlIdImpl_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.BaseXmlEmbedded <em>Base Xml Embedded</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.BaseXmlEmbedded
	 * @generated
	 */
	public Adapter createBaseXmlEmbeddedAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEmbeddedId
	 * @generated
	 */
	public Adapter createXmlEmbeddedId_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedId <em>Xml Embedded Id</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedId
	 * @generated
	 */
	public Adapter createXmlEmbeddedId_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlEmbeddedIdImpl <em>Xml Embedded Id Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEmbeddedIdImpl
	 * @generated
	 */
	public Adapter createXmlEmbeddedIdImpl_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedIdImpl <em>Xml Embedded Id Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedIdImpl
	 * @generated
	 */
	public Adapter createXmlEmbeddedIdImpl_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlEmbedded <em>Xml Embedded</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEmbedded
	 * @generated
	 */
	public Adapter createXmlEmbedded_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbedded <em>Xml Embedded</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbedded
	 * @generated
	 */
	public Adapter createXmlEmbedded_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlEmbeddedImpl <em>Xml Embedded Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlEmbeddedImpl
	 * @generated
	 */
	public Adapter createXmlEmbeddedImpl_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedImpl <em>Xml Embedded Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedImpl
	 * @generated
	 */
	public Adapter createXmlEmbeddedImpl_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlBasic <em>Xml Basic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlBasic
	 * @generated
	 */
	public Adapter createXmlBasic_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic <em>Xml Basic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic
	 * @generated
	 */
	public Adapter createXmlBasic_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlBasicImpl <em>Xml Basic Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlBasicImpl
	 * @generated
	 */
	public Adapter createXmlBasicImpl_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicImpl <em>Xml Basic Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicImpl
	 * @generated
	 */
	public Adapter createXmlBasicImpl_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlVersion <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlVersion
	 * @generated
	 */
	public Adapter createXmlVersion_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion
	 * @generated
	 */
	public Adapter createXmlVersion_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlVersionImpl <em>Xml Version Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlVersionImpl
	 * @generated
	 */
	public Adapter createXmlVersionImpl_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersionImpl <em>Xml Version Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersionImpl
	 * @generated
	 */
	public Adapter createXmlVersionImpl_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlRelationshipMapping <em>Xml Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlRelationshipMapping
	 * @generated
	 */
	public Adapter createXmlRelationshipMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlSingleRelationshipMapping <em>Xml Single Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlSingleRelationshipMapping
	 * @generated
	 */
	public Adapter createXmlSingleRelationshipMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlOneToOne <em>Xml One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlOneToOne
	 * @generated
	 */
	public Adapter createXmlOneToOne_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned <em>Xml Private Owned</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlPrivateOwned
	 * @generated
	 */
	public Adapter createXmlPrivateOwnedAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch <em>Xml Join Fetch</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetch
	 * @generated
	 */
	public Adapter createXmlJoinFetchAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne <em>Xml One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne
	 * @generated
	 */
	public Adapter createXmlOneToOne_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlOneToOneImpl <em>Xml One To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlOneToOneImpl
	 * @generated
	 */
	public Adapter createXmlOneToOneImpl_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOneImpl <em>Xml One To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOneImpl
	 * @generated
	 */
	public Adapter createXmlOneToOneImpl_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlMultiRelationshipMapping <em>Xml Multi Relationship Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlMultiRelationshipMapping
	 * @generated
	 */
	public Adapter createXmlMultiRelationshipMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlOneToMany <em>Xml One To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlOneToMany
	 * @generated
	 */
	public Adapter createXmlOneToMany_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany <em>Xml One To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany
	 * @generated
	 */
	public Adapter createXmlOneToMany_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlOneToManyImpl <em>Xml One To Many Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlOneToManyImpl
	 * @generated
	 */
	public Adapter createXmlOneToManyImpl_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToManyImpl <em>Xml One To Many Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToManyImpl
	 * @generated
	 */
	public Adapter createXmlOneToManyImpl_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlManyToOne <em>Xml Many To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlManyToOne
	 * @generated
	 */
	public Adapter createXmlManyToOne_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne <em>Xml Many To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne
	 * @generated
	 */
	public Adapter createXmlManyToOne_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlManyToOneImpl <em>Xml Many To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlManyToOneImpl
	 * @generated
	 */
	public Adapter createXmlManyToOneImpl_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOneImpl <em>Xml Many To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOneImpl
	 * @generated
	 */
	public Adapter createXmlManyToOneImpl_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlManyToMany
	 * @generated
	 */
	public Adapter createXmlManyToMany_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany <em>Xml Many To Many</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany
	 * @generated
	 */
	public Adapter createXmlManyToMany_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlManyToManyImpl <em>Xml Many To Many Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlManyToManyImpl
	 * @generated
	 */
	public Adapter createXmlManyToManyImpl_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToManyImpl <em>Xml Many To Many Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToManyImpl
	 * @generated
	 */
	public Adapter createXmlManyToManyImpl_2Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollection <em>Xml Basic Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollection
	 * @generated
	 */
	public Adapter createXmlBasicCollection_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollectionImpl <em>Xml Basic Collection Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollectionImpl
	 * @generated
	 */
	public Adapter createXmlBasicCollectionImpl_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMap <em>Xml Basic Map</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMap
	 * @generated
	 */
	public Adapter createXmlBasicMap_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMapImpl <em>Xml Basic Map Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMapImpl
	 * @generated
	 */
	public Adapter createXmlBasicMapImpl_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformation <em>Xml Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformation
	 * @generated
	 */
	public Adapter createXmlTransformation_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformationImpl <em>Xml Transformation Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformationImpl
	 * @generated
	 */
	public Adapter createXmlTransformationImpl_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlVariableOneToOne <em>Xml Variable One To One</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlVariableOneToOne
	 * @generated
	 */
	public Adapter createXmlVariableOneToOne_1Adapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlVariableOneToOneImpl <em>Xml Variable One To One Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.XmlVariableOneToOneImpl
	 * @generated
	 */
	public Adapter createXmlVariableOneToOneImpl_1Adapter()
	{
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
	public Adapter createEObjectAdapter()
	{
		return null;
	}

} //EclipseLink1_1OrmAdapterFactory
