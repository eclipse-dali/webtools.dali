/**
 * <copyright>
 * </copyright>
 *
 * $Id: OrmResourceFactoryImpl.java,v 1.1.2.1 2007/09/17 20:58:27 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource Factory</b> associated with the package.
 * <!-- end-user-doc -->
 * @see org.eclipse.jpt.core.internal.resource.orm.util.OrmResourceImpl
 * @generated
 */
public class OrmResourceFactoryImpl extends ResourceFactoryImpl
{
	/**
	 * Creates an instance of the resource factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrmResourceFactoryImpl()
	{
		super();
	}

	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Resource createResource(URI uri)
	{
		Resource result = new OrmResourceImpl(uri);
		return result;
	}

} //OrmResourceFactoryImpl
