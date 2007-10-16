/**
 * <copyright>
 * </copyright>
 *
 * $Id: EmptyType.java,v 1.1.2.2 2007/10/16 17:01:40 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jpt.core.internal.resource.common.IJptEObject;
import org.eclipse.jpt.core.internal.resource.common.JptEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Empty Type</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmptyType()
 * @model kind="class"
 * @extends IJptEObject
 * @generated
 */
public class EmptyType extends JptEObject implements IJptEObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EmptyType()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return OrmPackage.Literals.EMPTY_TYPE;
	}

} // EmptyType
