/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.resource.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.eclipselink1_1.core.resource.orm.EclipseLink1_1OrmPackage
 * @generated
 */
public class EclipseLink1_1OrmFactory extends EFactoryImpl
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EclipseLink1_1OrmFactory eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EclipseLink1_1OrmFactory init()
	{
		try
		{
			EclipseLink1_1OrmFactory theEclipseLink1_1OrmFactory = (EclipseLink1_1OrmFactory)EPackage.Registry.INSTANCE.getEFactory("jpt.eclipselink1_1.orm.xmi"); 
			if (theEclipseLink1_1OrmFactory != null)
			{
				return theEclipseLink1_1OrmFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EclipseLink1_1OrmFactory();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EclipseLink1_1OrmFactory()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass)
	{
		switch (eClass.getClassifierID())
		{
			case EclipseLink1_1OrmPackage.XML_ENTITY_MAPPINGS: return (EObject)createXmlEntityMappings();
			case EclipseLink1_1OrmPackage.XML_ID_IMPL: return (EObject)createXmlIdImpl();
			case EclipseLink1_1OrmPackage.XML_EMBEDDED_ID_IMPL: return (EObject)createXmlEmbeddedIdImpl();
			case EclipseLink1_1OrmPackage.XML_EMBEDDED_IMPL: return (EObject)createXmlEmbeddedImpl();
			case EclipseLink1_1OrmPackage.XML_BASIC_IMPL: return (EObject)createXmlBasicImpl();
			case EclipseLink1_1OrmPackage.XML_VERSION_IMPL: return (EObject)createXmlVersionImpl();
			case EclipseLink1_1OrmPackage.XML_ONE_TO_ONE_IMPL: return (EObject)createXmlOneToOneImpl();
			case EclipseLink1_1OrmPackage.XML_ONE_TO_MANY_IMPL: return (EObject)createXmlOneToManyImpl();
			case EclipseLink1_1OrmPackage.XML_MANY_TO_ONE_IMPL: return (EObject)createXmlManyToOneImpl();
			case EclipseLink1_1OrmPackage.XML_MANY_TO_MANY_IMPL: return (EObject)createXmlManyToManyImpl();
			case EclipseLink1_1OrmPackage.XML_BASIC_COLLECTION_IMPL: return (EObject)createXmlBasicCollectionImpl();
			case EclipseLink1_1OrmPackage.XML_BASIC_MAP_IMPL: return (EObject)createXmlBasicMapImpl();
			case EclipseLink1_1OrmPackage.XML_TRANSFORMATION_IMPL: return (EObject)createXmlTransformationImpl();
			case EclipseLink1_1OrmPackage.XML_VARIABLE_ONE_TO_ONE_IMPL: return (EObject)createXmlVariableOneToOneImpl();
			case EclipseLink1_1OrmPackage.XML_TRANSIENT_IMPL: return (EObject)createXmlTransientImpl();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlEntityMappings createXmlEntityMappings()
	{
		XmlEntityMappings xmlEntityMappings = new XmlEntityMappings();
		return xmlEntityMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlIdImpl createXmlIdImpl()
	{
		XmlIdImpl xmlIdImpl = new XmlIdImpl();
		return xmlIdImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlEmbeddedIdImpl createXmlEmbeddedIdImpl()
	{
		XmlEmbeddedIdImpl xmlEmbeddedIdImpl = new XmlEmbeddedIdImpl();
		return xmlEmbeddedIdImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlEmbeddedImpl createXmlEmbeddedImpl()
	{
		XmlEmbeddedImpl xmlEmbeddedImpl = new XmlEmbeddedImpl();
		return xmlEmbeddedImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlBasicImpl createXmlBasicImpl()
	{
		XmlBasicImpl xmlBasicImpl = new XmlBasicImpl();
		return xmlBasicImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlVersionImpl createXmlVersionImpl()
	{
		XmlVersionImpl xmlVersionImpl = new XmlVersionImpl();
		return xmlVersionImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlOneToOneImpl createXmlOneToOneImpl()
	{
		XmlOneToOneImpl xmlOneToOneImpl = new XmlOneToOneImpl();
		return xmlOneToOneImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlOneToManyImpl createXmlOneToManyImpl()
	{
		XmlOneToManyImpl xmlOneToManyImpl = new XmlOneToManyImpl();
		return xmlOneToManyImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlManyToOneImpl createXmlManyToOneImpl()
	{
		XmlManyToOneImpl xmlManyToOneImpl = new XmlManyToOneImpl();
		return xmlManyToOneImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlManyToManyImpl createXmlManyToManyImpl()
	{
		XmlManyToManyImpl xmlManyToManyImpl = new XmlManyToManyImpl();
		return xmlManyToManyImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlBasicCollectionImpl createXmlBasicCollectionImpl()
	{
		XmlBasicCollectionImpl xmlBasicCollectionImpl = new XmlBasicCollectionImpl();
		return xmlBasicCollectionImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlBasicMapImpl createXmlBasicMapImpl()
	{
		XmlBasicMapImpl xmlBasicMapImpl = new XmlBasicMapImpl();
		return xmlBasicMapImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlTransformationImpl createXmlTransformationImpl()
	{
		XmlTransformationImpl xmlTransformationImpl = new XmlTransformationImpl();
		return xmlTransformationImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlVariableOneToOneImpl createXmlVariableOneToOneImpl()
	{
		XmlVariableOneToOneImpl xmlVariableOneToOneImpl = new XmlVariableOneToOneImpl();
		return xmlVariableOneToOneImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlTransientImpl createXmlTransientImpl()
	{
		XmlTransientImpl xmlTransientImpl = new XmlTransientImpl();
		return xmlTransientImpl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EclipseLink1_1OrmPackage getEclipseLink1_1OrmPackage()
	{
		return (EclipseLink1_1OrmPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EclipseLink1_1OrmPackage getPackage()
	{
		return EclipseLink1_1OrmPackage.eINSTANCE;
	}

} //EclipseLink1_1OrmFactory
