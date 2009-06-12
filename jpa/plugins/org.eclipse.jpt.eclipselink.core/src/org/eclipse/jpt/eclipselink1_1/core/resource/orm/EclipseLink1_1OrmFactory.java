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
			case EclipseLink1_1OrmPackage.XML_ENTITY: return (EObject)createXmlEntity();
			case EclipseLink1_1OrmPackage.XML_EMBEDDABLE: return (EObject)createXmlEmbeddable();
			case EclipseLink1_1OrmPackage.XML_MAPPED_SUPERCLASS: return (EObject)createXmlMappedSuperclass();
			case EclipseLink1_1OrmPackage.ATTRIBUTES: return (EObject)createAttributes();
			case EclipseLink1_1OrmPackage.XML_ID: return (EObject)createXmlId();
			case EclipseLink1_1OrmPackage.XML_EMBEDDED_ID: return (EObject)createXmlEmbeddedId();
			case EclipseLink1_1OrmPackage.XML_EMBEDDED: return (EObject)createXmlEmbedded();
			case EclipseLink1_1OrmPackage.XML_BASIC: return (EObject)createXmlBasic();
			case EclipseLink1_1OrmPackage.XML_VERSION: return (EObject)createXmlVersion();
			case EclipseLink1_1OrmPackage.XML_ONE_TO_ONE: return (EObject)createXmlOneToOne();
			case EclipseLink1_1OrmPackage.XML_ONE_TO_MANY: return (EObject)createXmlOneToMany();
			case EclipseLink1_1OrmPackage.XML_MANY_TO_ONE: return (EObject)createXmlManyToOne();
			case EclipseLink1_1OrmPackage.XML_MANY_TO_MANY: return (EObject)createXmlManyToMany();
			case EclipseLink1_1OrmPackage.XML_BASIC_COLLECTION: return (EObject)createXmlBasicCollection();
			case EclipseLink1_1OrmPackage.XML_BASIC_MAP: return (EObject)createXmlBasicMap();
			case EclipseLink1_1OrmPackage.XML_TRANSFORMATION: return (EObject)createXmlTransformation();
			case EclipseLink1_1OrmPackage.XML_VARIABLE_ONE_TO_ONE: return (EObject)createXmlVariableOneToOne();
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
	public XmlEntity createXmlEntity()
	{
		XmlEntity xmlEntity = new XmlEntity();
		return xmlEntity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlEmbeddable createXmlEmbeddable()
	{
		XmlEmbeddable xmlEmbeddable = new XmlEmbeddable();
		return xmlEmbeddable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlMappedSuperclass createXmlMappedSuperclass()
	{
		XmlMappedSuperclass xmlMappedSuperclass = new XmlMappedSuperclass();
		return xmlMappedSuperclass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attributes createAttributes()
	{
		Attributes attributes = new Attributes();
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlId createXmlId()
	{
		XmlId xmlId = new XmlId();
		return xmlId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlEmbeddedId createXmlEmbeddedId()
	{
		XmlEmbeddedId xmlEmbeddedId = new XmlEmbeddedId();
		return xmlEmbeddedId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlEmbedded createXmlEmbedded()
	{
		XmlEmbedded xmlEmbedded = new XmlEmbedded();
		return xmlEmbedded;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlBasic createXmlBasic()
	{
		XmlBasic xmlBasic = new XmlBasic();
		return xmlBasic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlVersion createXmlVersion()
	{
		XmlVersion xmlVersion = new XmlVersion();
		return xmlVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlOneToOne createXmlOneToOne()
	{
		XmlOneToOne xmlOneToOne = new XmlOneToOne();
		return xmlOneToOne;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlOneToMany createXmlOneToMany()
	{
		XmlOneToMany xmlOneToMany = new XmlOneToMany();
		return xmlOneToMany;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlManyToOne createXmlManyToOne()
	{
		XmlManyToOne xmlManyToOne = new XmlManyToOne();
		return xmlManyToOne;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlManyToMany createXmlManyToMany()
	{
		XmlManyToMany xmlManyToMany = new XmlManyToMany();
		return xmlManyToMany;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlBasicCollection createXmlBasicCollection()
	{
		XmlBasicCollection xmlBasicCollection = new XmlBasicCollection();
		return xmlBasicCollection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlBasicMap createXmlBasicMap()
	{
		XmlBasicMap xmlBasicMap = new XmlBasicMap();
		return xmlBasicMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlTransformation createXmlTransformation()
	{
		XmlTransformation xmlTransformation = new XmlTransformation();
		return xmlTransformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlVariableOneToOne createXmlVariableOneToOne()
	{
		XmlVariableOneToOne xmlVariableOneToOne = new XmlVariableOneToOne();
		return xmlVariableOneToOne;
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
