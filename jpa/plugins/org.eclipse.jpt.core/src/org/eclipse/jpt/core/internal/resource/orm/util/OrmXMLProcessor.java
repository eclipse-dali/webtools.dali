/**
 * <copyright>
 * </copyright>
 *
 * $Id: OrmXMLProcessor.java,v 1.1.2.2 2007/10/16 17:01:41 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm.util;

import java.util.Map;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.util.XMLProcessor;
import org.eclipse.jpt.core.internal.resource.orm.OrmPackage;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class OrmXMLProcessor extends XMLProcessor
{

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrmXMLProcessor()
	{
		super((EPackage.Registry.INSTANCE));
		OrmPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the OrmResourceFactoryImpl factory.
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
			registrations.put(XML_EXTENSION, new OrmResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new OrmResourceFactoryImpl());
		}
		return registrations;
	}

} //OrmXMLProcessor
