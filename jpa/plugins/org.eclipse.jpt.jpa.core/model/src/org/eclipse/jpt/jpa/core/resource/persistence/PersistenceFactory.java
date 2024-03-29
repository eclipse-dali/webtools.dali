/*******************************************************************************
 *  Copyright (c) 2009, 2012  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License 2.0 which 
 *  accompanies this distribution, and is available at 
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.jpa.core.resource.persistence;

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
 * @see org.eclipse.jpt.jpa.core.resource.persistence.PersistencePackage
 * @generated
 */
public class PersistenceFactory extends EFactoryImpl
{
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final PersistenceFactory eINSTANCE = init();

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PersistenceFactory init()
	{
		try
		{
			PersistenceFactory thePersistenceFactory = (PersistenceFactory)EPackage.Registry.INSTANCE.getEFactory("jpt.persistence.xmi"); 
			if (thePersistenceFactory != null)
			{
				return thePersistenceFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PersistenceFactory();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PersistenceFactory()
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
			case PersistencePackage.XML_JAVA_CLASS_REF: return (EObject)createXmlJavaClassRef();
			case PersistencePackage.XML_JAR_FILE_REF: return (EObject)createXmlJarFileRef();
			case PersistencePackage.XML_MAPPING_FILE_REF: return (EObject)createXmlMappingFileRef();
			case PersistencePackage.XML_PERSISTENCE: return (EObject)createXmlPersistence();
			case PersistencePackage.XML_PERSISTENCE_UNIT: return (EObject)createXmlPersistenceUnit();
			case PersistencePackage.XML_PROPERTIES: return (EObject)createXmlProperties();
			case PersistencePackage.XML_PROPERTY: return (EObject)createXmlProperty();
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
			case PersistencePackage.XML_PERSISTENCE_UNIT_TRANSACTION_TYPE:
				return createXmlPersistenceUnitTransactionTypeFromString(eDataType, initialValue);
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
			case PersistencePackage.XML_PERSISTENCE_UNIT_TRANSACTION_TYPE:
				return convertXmlPersistenceUnitTransactionTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlJavaClassRef createXmlJavaClassRef()
	{
		XmlJavaClassRef xmlJavaClassRef = new XmlJavaClassRef();
		return xmlJavaClassRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlJarFileRef createXmlJarFileRef()
	{
		XmlJarFileRef xmlJarFileRef = new XmlJarFileRef();
		return xmlJarFileRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlMappingFileRef createXmlMappingFileRef()
	{
		XmlMappingFileRef xmlMappingFileRef = new XmlMappingFileRef();
		return xmlMappingFileRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPersistence createXmlPersistence()
	{
		XmlPersistence xmlPersistence = new XmlPersistence();
		return xmlPersistence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPersistenceUnit createXmlPersistenceUnit()
	{
		XmlPersistenceUnit xmlPersistenceUnit = new XmlPersistenceUnit();
		return xmlPersistenceUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlProperties createXmlProperties()
	{
		XmlProperties xmlProperties = new XmlProperties();
		return xmlProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlProperty createXmlProperty()
	{
		XmlProperty xmlProperty = new XmlProperty();
		return xmlProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XmlPersistenceUnitTransactionType createXmlPersistenceUnitTransactionTypeFromString(EDataType eDataType, String initialValue)
	{
		XmlPersistenceUnitTransactionType result = XmlPersistenceUnitTransactionType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertXmlPersistenceUnitTransactionTypeToString(EDataType eDataType, Object instanceValue)
	{
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PersistencePackage getPersistencePackage()
	{
		return (PersistencePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PersistencePackage getPackage()
	{
		return PersistencePackage.eINSTANCE;
	}

} //PersistenceFactory
