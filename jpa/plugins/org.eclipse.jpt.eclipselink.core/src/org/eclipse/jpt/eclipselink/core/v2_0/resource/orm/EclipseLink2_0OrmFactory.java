/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.v2_0.resource.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.eclipselink.core.v2_0.resource.orm.EclipseLink2_0OrmPackage
 * @generated
 */
public class EclipseLink2_0OrmFactory extends EFactoryImpl
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EclipseLink2_0OrmFactory eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EclipseLink2_0OrmFactory init()
	{
		try
		{
			EclipseLink2_0OrmFactory theEclipseLink2_0OrmFactory = (EclipseLink2_0OrmFactory)EPackage.Registry.INSTANCE.getEFactory("jpt.eclipselink2_0.orm.xmi"); 
			if (theEclipseLink2_0OrmFactory != null)
			{
				return theEclipseLink2_0OrmFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EclipseLink2_0OrmFactory();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EclipseLink2_0OrmFactory()
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
			case EclipseLink2_0OrmPackage.XML_ENTITY_MAPPINGS: return (EObject)createXmlEntityMappings();
			case EclipseLink2_0OrmPackage.XML_PERSISTENCE_UNIT_METADATA: return (EObject)createXmlPersistenceUnitMetadata();
			case EclipseLink2_0OrmPackage.XML_ENTITY: return (EObject)createXmlEntity();
			case EclipseLink2_0OrmPackage.XML_EMBEDDABLE: return (EObject)createXmlEmbeddable();
			case EclipseLink2_0OrmPackage.XML_MAPPED_SUPERCLASS: return (EObject)createXmlMappedSuperclass();
			case EclipseLink2_0OrmPackage.ATTRIBUTES: return (EObject)createAttributes();
			case EclipseLink2_0OrmPackage.XML_EMBEDDED: return (EObject)createXmlEmbedded();
			case EclipseLink2_0OrmPackage.XML_ONE_TO_ONE: return (EObject)createXmlOneToOne();
			case EclipseLink2_0OrmPackage.XML_ONE_TO_MANY: return (EObject)createXmlOneToMany();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_ONE: return (EObject)createXmlManyToOne();
			case EclipseLink2_0OrmPackage.XML_MANY_TO_MANY: return (EObject)createXmlManyToMany();
			case EclipseLink2_0OrmPackage.XML_ELEMENT_COLLECTION: return (EObject)createXmlElementCollection();
			case EclipseLink2_0OrmPackage.XML_ORDER_COLUMN: return (EObject)createXmlOrderColumn();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue)
	{
		switch (eDataType.getClassifierID())
		{
			case EclipseLink2_0OrmPackage.ORDER_COLUMN_VALIDATION_MODE:
				return createOrderColumnValidationModeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue)
	{
		switch (eDataType.getClassifierID())
		{
			case EclipseLink2_0OrmPackage.ORDER_COLUMN_VALIDATION_MODE:
				return convertOrderColumnValidationModeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
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
	public XmlPersistenceUnitMetadata createXmlPersistenceUnitMetadata()
	{
		XmlPersistenceUnitMetadata xmlPersistenceUnitMetadata = new XmlPersistenceUnitMetadata();
		return xmlPersistenceUnitMetadata;
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
	public XmlElementCollection createXmlElementCollection()
	{
		XmlElementCollection xmlElementCollection = new XmlElementCollection();
		return xmlElementCollection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlOrderColumn createXmlOrderColumn()
	{
		XmlOrderColumn xmlOrderColumn = new XmlOrderColumn();
		return xmlOrderColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrderColumnValidationMode createOrderColumnValidationModeFromString(EDataType eDataType, String initialValue)
	{
		OrderColumnValidationMode result = OrderColumnValidationMode.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOrderColumnValidationModeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EclipseLink2_0OrmPackage getEclipseLink2_0OrmPackage()
	{
		return (EclipseLink2_0OrmPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EclipseLink2_0OrmPackage getPackage()
	{
		return EclipseLink2_0OrmPackage.eINSTANCE;
	}

} //EclipseLink2_0OrmFactory
