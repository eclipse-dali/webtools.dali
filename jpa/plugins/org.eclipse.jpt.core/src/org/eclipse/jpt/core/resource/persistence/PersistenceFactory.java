/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.persistence;

import org.eclipse.emf.common.util.Enumerator;
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
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.resource.persistence.PersistencePackage
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
			case PersistencePackage.XML_PERSISTENCE: return (EObject)createXmlPersistence();
			case PersistencePackage.XML_PERSISTENCE_UNIT: return (EObject)createXmlPersistenceUnit();
			case PersistencePackage.XML_MAPPING_FILE_REF: return (EObject)createXmlMappingFileRef();
			case PersistencePackage.XML_JAR_FILE_REF: return (EObject)createXmlJarFileRef();
			case PersistencePackage.XML_JAVA_CLASS_REF: return (EObject)createXmlJavaClassRef();
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
			case PersistencePackage.XML_PERSISTENCE_UNIT_TRANSACTION_TYPE_OBJECT:
				return createXmlPersistenceUnitTransactionTypeObjectFromString(eDataType, initialValue);
			case PersistencePackage.XML_VERSION:
				return createXmlVersionFromString(eDataType, initialValue);
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
			case PersistencePackage.XML_PERSISTENCE_UNIT_TRANSACTION_TYPE_OBJECT:
				return convertXmlPersistenceUnitTransactionTypeObjectToString(eDataType, instanceValue);
			case PersistencePackage.XML_VERSION:
				return convertXmlVersionToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
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
	public Enumerator createXmlPersistenceUnitTransactionTypeObjectFromString(EDataType eDataType, String initialValue)
	{
		return (Enumerator)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertXmlPersistenceUnitTransactionTypeObjectToString(EDataType eDataType, Object instanceValue)
	{
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createXmlVersionFromString(EDataType eDataType, String initialValue)
	{
		return (String)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertXmlVersionToString(EDataType eDataType, Object instanceValue)
	{
		return super.convertToString(eDataType, instanceValue);
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
