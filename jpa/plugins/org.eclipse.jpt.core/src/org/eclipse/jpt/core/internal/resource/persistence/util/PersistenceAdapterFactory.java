/*******************************************************************************
 *  Copyright (c) 2006, 2007  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.internal.resource.persistence.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage;
import org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.internal.resource.persistence.XmlProperties;
import org.eclipse.jpt.core.internal.resource.persistence.XmlProperty;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage
 * @generated
 */
public class PersistenceAdapterFactory extends AdapterFactoryImpl
{
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PersistencePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PersistenceAdapterFactory()
	{
		if (modelPackage == null)
		{
			modelPackage = PersistencePackage.eINSTANCE;
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
	protected PersistenceSwitch<Adapter> modelSwitch =
		new PersistenceSwitch<Adapter>()
		{
			@Override
			public Adapter caseXmlPersistence(XmlPersistence object)
			{
				return createXmlPersistenceAdapter();
			}
			@Override
			public Adapter caseXmlPersistenceUnit(XmlPersistenceUnit object)
			{
				return createXmlPersistenceUnitAdapter();
			}
			@Override
			public Adapter caseXmlMappingFileRef(XmlMappingFileRef object)
			{
				return createXmlMappingFileRefAdapter();
			}
			@Override
			public Adapter caseXmlJavaClassRef(XmlJavaClassRef object)
			{
				return createXmlJavaClassRefAdapter();
			}
			@Override
			public Adapter caseXmlProperties(XmlProperties object)
			{
				return createXmlPropertiesAdapter();
			}
			@Override
			public Adapter caseXmlProperty(XmlProperty object)
			{
				return createXmlPropertyAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence <em>Xml Persistence</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence
	 * @generated
	 */
	public Adapter createXmlPersistenceAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit <em>Xml Persistence Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit
	 * @generated
	 */
	public Adapter createXmlPersistenceUnitAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef <em>Xml Mapping File Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef
	 * @generated
	 */
	public Adapter createXmlMappingFileRefAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef <em>Xml Java Class Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.XmlJavaClassRef
	 * @generated
	 */
	public Adapter createXmlJavaClassRefAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.persistence.XmlProperties <em>Xml Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.XmlProperties
	 * @generated
	 */
	public Adapter createXmlPropertiesAdapter()
	{
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.jpt.core.internal.resource.persistence.XmlProperty <em>Xml Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.jpt.core.internal.resource.persistence.XmlProperty
	 * @generated
	 */
	public Adapter createXmlPropertyAdapter()
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

} //PersistenceAdapterFactory
