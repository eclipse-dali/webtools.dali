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
import org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping;
import org.eclipse.jpt.core.resource.orm.XmlJoinTableMapping;
import org.eclipse.jpt.core.resource.orm.XmlMappedByMapping;
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
			public Adapter caseXmlTransient(XmlTransient object)
			{
				return createXmlTransientAdapter();
			}
			@Override
			public Adapter caseXmlTransientImpl(XmlTransientImpl object)
			{
				return createXmlTransientImplAdapter();
			}
			@Override
			public Adapter caseOrm_XmlEntityMappings(org.eclipse.jpt.core.resource.orm.XmlEntityMappings object)
			{
				return createOrm_XmlEntityMappingsAdapter();
			}
			@Override
			public Adapter caseXmlConvertersHolder(XmlConvertersHolder object)
			{
				return createXmlConvertersHolderAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlEntityMappings(org.eclipse.jpt.eclipselink.core.resource.orm.XmlEntityMappings object)
			{
				return createEclipseLinkOrm_XmlEntityMappingsAdapter();
			}
			@Override
			public Adapter caseOrm_XmlAttributeMapping(org.eclipse.jpt.core.resource.orm.XmlAttributeMapping object)
			{
				return createOrm_XmlAttributeMappingAdapter();
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
			public Adapter caseOrm_XmlId(org.eclipse.jpt.core.resource.orm.XmlId object)
			{
				return createOrm_XmlIdAdapter();
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
			public Adapter caseEclipseLinkOrm_XmlConvertibleMapping(org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertibleMapping object)
			{
				return createEclipseLinkOrm_XmlConvertibleMappingAdapter();
			}
			@Override
			public Adapter caseXmlAccessMethodsHolder(XmlAccessMethodsHolder object)
			{
				return createXmlAccessMethodsHolderAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlId(org.eclipse.jpt.eclipselink.core.resource.orm.XmlId object)
			{
				return createEclipseLinkOrm_XmlIdAdapter();
			}
			@Override
			public Adapter caseAbstractXmlAttributeMapping(AbstractXmlAttributeMapping object)
			{
				return createAbstractXmlAttributeMappingAdapter();
			}
			@Override
			public Adapter caseOrm_XmlIdImpl(org.eclipse.jpt.core.resource.orm.XmlIdImpl object)
			{
				return createOrm_XmlIdImplAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlIdImpl(org.eclipse.jpt.eclipselink.core.resource.orm.XmlIdImpl object)
			{
				return createEclipseLinkOrm_XmlIdImplAdapter();
			}
			@Override
			public Adapter caseBaseXmlEmbedded(BaseXmlEmbedded object)
			{
				return createBaseXmlEmbeddedAdapter();
			}
			@Override
			public Adapter caseOrm_XmlEmbeddedId(org.eclipse.jpt.core.resource.orm.XmlEmbeddedId object)
			{
				return createOrm_XmlEmbeddedIdAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlEmbeddedId(org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedId object)
			{
				return createEclipseLinkOrm_XmlEmbeddedIdAdapter();
			}
			@Override
			public Adapter caseOrm_XmlEmbeddedIdImpl(org.eclipse.jpt.core.resource.orm.XmlEmbeddedIdImpl object)
			{
				return createOrm_XmlEmbeddedIdImplAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlEmbeddedIdImpl(org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedIdImpl object)
			{
				return createEclipseLinkOrm_XmlEmbeddedIdImplAdapter();
			}
			@Override
			public Adapter caseOrm_XmlEmbedded(org.eclipse.jpt.core.resource.orm.XmlEmbedded object)
			{
				return createOrm_XmlEmbeddedAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlEmbedded(org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbedded object)
			{
				return createEclipseLinkOrm_XmlEmbeddedAdapter();
			}
			@Override
			public Adapter caseOrm_XmlEmbeddedImpl(org.eclipse.jpt.core.resource.orm.XmlEmbeddedImpl object)
			{
				return createOrm_XmlEmbeddedImplAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlEmbeddedImpl(org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedImpl object)
			{
				return createEclipseLinkOrm_XmlEmbeddedImplAdapter();
			}
			@Override
			public Adapter caseOrm_XmlBasic(org.eclipse.jpt.core.resource.orm.XmlBasic object)
			{
				return createOrm_XmlBasicAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlBasic(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic object)
			{
				return createEclipseLinkOrm_XmlBasicAdapter();
			}
			@Override
			public Adapter caseOrm_XmlBasicImpl(org.eclipse.jpt.core.resource.orm.XmlBasicImpl object)
			{
				return createOrm_XmlBasicImplAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlBasicImpl(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicImpl object)
			{
				return createEclipseLinkOrm_XmlBasicImplAdapter();
			}
			@Override
			public Adapter caseOrm_XmlVersion(org.eclipse.jpt.core.resource.orm.XmlVersion object)
			{
				return createOrm_XmlVersionAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlVersion(org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion object)
			{
				return createEclipseLinkOrm_XmlVersionAdapter();
			}
			@Override
			public Adapter caseOrm_XmlVersionImpl(org.eclipse.jpt.core.resource.orm.XmlVersionImpl object)
			{
				return createOrm_XmlVersionImplAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlVersionImpl(org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersionImpl object)
			{
				return createEclipseLinkOrm_XmlVersionImplAdapter();
			}
			@Override
			public Adapter caseXmlRelationshipMapping(XmlRelationshipMapping object)
			{
				return createXmlRelationshipMappingAdapter();
			}
			@Override
			public Adapter caseXmlJoinTableMapping(XmlJoinTableMapping object)
			{
				return createXmlJoinTableMappingAdapter();
			}
			@Override
			public Adapter caseXmlJoinColumnsMapping(XmlJoinColumnsMapping object)
			{
				return createXmlJoinColumnsMappingAdapter();
			}
			@Override
			public Adapter caseXmlSingleRelationshipMapping(XmlSingleRelationshipMapping object)
			{
				return createXmlSingleRelationshipMappingAdapter();
			}
			@Override
			public Adapter caseXmlMappedByMapping(XmlMappedByMapping object)
			{
				return createXmlMappedByMappingAdapter();
			}
			@Override
			public Adapter caseOrm_XmlOneToOne(org.eclipse.jpt.core.resource.orm.XmlOneToOne object)
			{
				return createOrm_XmlOneToOneAdapter();
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
			public Adapter caseEclipseLinkOrm_XmlOneToOne(org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne object)
			{
				return createEclipseLinkOrm_XmlOneToOneAdapter();
			}
			@Override
			public Adapter caseOrm_XmlOneToOneImpl(org.eclipse.jpt.core.resource.orm.XmlOneToOneImpl object)
			{
				return createOrm_XmlOneToOneImplAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlOneToOneImpl(org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOneImpl object)
			{
				return createEclipseLinkOrm_XmlOneToOneImplAdapter();
			}
			@Override
			public Adapter caseXmlMultiRelationshipMapping(XmlMultiRelationshipMapping object)
			{
				return createXmlMultiRelationshipMappingAdapter();
			}
			@Override
			public Adapter caseOrm_XmlOneToMany(org.eclipse.jpt.core.resource.orm.XmlOneToMany object)
			{
				return createOrm_XmlOneToManyAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlOneToMany(org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany object)
			{
				return createEclipseLinkOrm_XmlOneToManyAdapter();
			}
			@Override
			public Adapter caseOrm_XmlOneToManyImpl(org.eclipse.jpt.core.resource.orm.XmlOneToManyImpl object)
			{
				return createOrm_XmlOneToManyImplAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlOneToManyImpl(org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToManyImpl object)
			{
				return createEclipseLinkOrm_XmlOneToManyImplAdapter();
			}
			@Override
			public Adapter caseOrm_XmlManyToOne(org.eclipse.jpt.core.resource.orm.XmlManyToOne object)
			{
				return createOrm_XmlManyToOneAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlManyToOne(org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne object)
			{
				return createEclipseLinkOrm_XmlManyToOneAdapter();
			}
			@Override
			public Adapter caseOrm_XmlManyToOneImpl(org.eclipse.jpt.core.resource.orm.XmlManyToOneImpl object)
			{
				return createOrm_XmlManyToOneImplAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlManyToOneImpl(org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOneImpl object)
			{
				return createEclipseLinkOrm_XmlManyToOneImplAdapter();
			}
			@Override
			public Adapter caseOrm_XmlManyToMany(org.eclipse.jpt.core.resource.orm.XmlManyToMany object)
			{
				return createOrm_XmlManyToManyAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlManyToMany(org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany object)
			{
				return createEclipseLinkOrm_XmlManyToManyAdapter();
			}
			@Override
			public Adapter caseOrm_XmlManyToManyImpl(org.eclipse.jpt.core.resource.orm.XmlManyToManyImpl object)
			{
				return createOrm_XmlManyToManyImplAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlManyToManyImpl(org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToManyImpl object)
			{
				return createEclipseLinkOrm_XmlManyToManyImplAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlBasicCollection(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollection object)
			{
				return createEclipseLinkOrm_XmlBasicCollectionAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlBasicCollectionImpl(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollectionImpl object)
			{
				return createEclipseLinkOrm_XmlBasicCollectionImplAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlBasicMap(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMap object)
			{
				return createEclipseLinkOrm_XmlBasicMapAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlBasicMapImpl(org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMapImpl object)
			{
				return createEclipseLinkOrm_XmlBasicMapImplAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlTransformation(org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformation object)
			{
				return createEclipseLinkOrm_XmlTransformationAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlTransformationImpl(org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformationImpl object)
			{
				return createEclipseLinkOrm_XmlTransformationImplAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlVariableOneToOne(org.eclipse.jpt.eclipselink.core.resource.orm.XmlVariableOneToOne object)
			{
				return createEclipseLinkOrm_XmlVariableOneToOneAdapter();
			}
			@Override
			public Adapter caseEclipseLinkOrm_XmlVariableOneToOneImpl(org.eclipse.jpt.eclipselink.core.resource.orm.XmlVariableOneToOneImpl object)
			{
				return createEclipseLinkOrm_XmlVariableOneToOneImplAdapter();
			}
			@Override
			public Adapter caseOrm_XmlTransient(org.eclipse.jpt.core.resource.orm.XmlTransient object)
			{
				return createOrm_XmlTransientAdapter();
			}
			@Override
			public Adapter caseOrm_XmlTransientImpl(org.eclipse.jpt.core.resource.orm.XmlTransientImpl object)
			{
				return createOrm_XmlTransientImplAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransient <em>Xml Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransient
	 * @generated
	 */
	public Adapter createXmlTransientAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransientImpl <em>Xml Transient Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlTransientImpl
	 * @generated
	 */
	public Adapter createXmlTransientImplAdapter()
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
	public Adapter createOrm_XmlEntityMappingsAdapter()
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
	public Adapter createEclipseLinkOrm_XmlEntityMappingsAdapter()
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
	public Adapter createOrm_XmlAttributeMappingAdapter()
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
	public Adapter createOrm_XmlIdAdapter()
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
	public Adapter createEclipseLinkOrm_XmlConvertibleMappingAdapter()
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
	public Adapter createEclipseLinkOrm_XmlIdAdapter()
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
	public Adapter createOrm_XmlIdImplAdapter()
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
	public Adapter createEclipseLinkOrm_XmlIdImplAdapter()
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
	public Adapter createOrm_XmlEmbeddedIdAdapter()
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
	public Adapter createEclipseLinkOrm_XmlEmbeddedIdAdapter()
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
	public Adapter createOrm_XmlEmbeddedIdImplAdapter()
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
	public Adapter createEclipseLinkOrm_XmlEmbeddedIdImplAdapter()
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
	public Adapter createOrm_XmlEmbeddedAdapter()
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
	public Adapter createEclipseLinkOrm_XmlEmbeddedAdapter()
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
	public Adapter createOrm_XmlEmbeddedImplAdapter()
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
	public Adapter createEclipseLinkOrm_XmlEmbeddedImplAdapter()
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
	public Adapter createOrm_XmlBasicAdapter()
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
	public Adapter createEclipseLinkOrm_XmlBasicAdapter()
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
	public Adapter createOrm_XmlBasicImplAdapter()
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
	public Adapter createEclipseLinkOrm_XmlBasicImplAdapter()
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
	public Adapter createOrm_XmlVersionAdapter()
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
	public Adapter createEclipseLinkOrm_XmlVersionAdapter()
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
	public Adapter createOrm_XmlVersionImplAdapter()
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
	public Adapter createEclipseLinkOrm_XmlVersionImplAdapter()
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
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlJoinTableMapping <em>Xml Join Table Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlJoinTableMapping
	 * @generated
	 */
	public Adapter createXmlJoinTableMappingAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping <em>Xml Join Columns Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping
	 * @generated
	 */
	public Adapter createXmlJoinColumnsMappingAdapter()
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
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlMappedByMapping <em>Xml Mapped By Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlMappedByMapping
	 * @generated
	 */
	public Adapter createXmlMappedByMappingAdapter()
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
	public Adapter createOrm_XmlOneToOneAdapter()
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
	public Adapter createEclipseLinkOrm_XmlOneToOneAdapter()
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
	public Adapter createOrm_XmlOneToOneImplAdapter()
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
	public Adapter createEclipseLinkOrm_XmlOneToOneImplAdapter()
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
	public Adapter createOrm_XmlOneToManyAdapter()
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
	public Adapter createEclipseLinkOrm_XmlOneToManyAdapter()
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
	public Adapter createOrm_XmlOneToManyImplAdapter()
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
	public Adapter createEclipseLinkOrm_XmlOneToManyImplAdapter()
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
	public Adapter createOrm_XmlManyToOneAdapter()
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
	public Adapter createEclipseLinkOrm_XmlManyToOneAdapter()
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
	public Adapter createOrm_XmlManyToOneImplAdapter()
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
	public Adapter createEclipseLinkOrm_XmlManyToOneImplAdapter()
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
	public Adapter createOrm_XmlManyToManyAdapter()
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
	public Adapter createEclipseLinkOrm_XmlManyToManyAdapter()
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
	public Adapter createOrm_XmlManyToManyImplAdapter()
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
	public Adapter createEclipseLinkOrm_XmlManyToManyImplAdapter()
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
	public Adapter createEclipseLinkOrm_XmlBasicCollectionAdapter()
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
	public Adapter createEclipseLinkOrm_XmlBasicCollectionImplAdapter()
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
	public Adapter createEclipseLinkOrm_XmlBasicMapAdapter()
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
	public Adapter createEclipseLinkOrm_XmlBasicMapImplAdapter()
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
	public Adapter createEclipseLinkOrm_XmlTransformationAdapter()
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
	public Adapter createEclipseLinkOrm_XmlTransformationImplAdapter()
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
	public Adapter createEclipseLinkOrm_XmlVariableOneToOneAdapter()
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
	public Adapter createEclipseLinkOrm_XmlVariableOneToOneImplAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlTransient <em>Xml Transient</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTransient
	 * @generated
	 */
	public Adapter createOrm_XmlTransientAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.resource.orm.XmlTransientImpl <em>Xml Transient Impl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.resource.orm.XmlTransientImpl
	 * @generated
	 */
	public Adapter createOrm_XmlTransientImplAdapter()
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
