/**
 * <copyright>
 * </copyright>
 *
 * $Id: EmptyType.java,v 1.1.2.1 2007/09/17 20:49:52 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.jpt.core.internal.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Empty Type</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getEmptyType()
 * @model kind="class"
 * @generated
 */
public class EmptyType extends JpaEObject implements EObject
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
