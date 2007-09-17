/**
 * <copyright>
 * </copyright>
 *
 * $Id: PersistenceXMLProcessor.java,v 1.1.2.1 2007/09/17 20:58:28 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.persistence.util;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

import org.eclipse.jpt.core.internal.resource.persistence.PersistencePackage;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class PersistenceXMLProcessor extends XMLProcessor
{

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PersistenceXMLProcessor()
	{
		super((EPackage.Registry.INSTANCE));
		PersistencePackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the PersistenceResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations()
	{
		if (registrations == null)
		{
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new PersistenceResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new PersistenceResourceFactoryImpl());
		}
		return registrations;
	}

} //PersistenceXMLProcessor
