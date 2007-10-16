/**
 * <copyright>
 * </copyright>
 *
 * $Id: Lob.java,v 1.1.2.2 2007/10/16 17:01:41 pfullbright Exp $
 */
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jpt.core.internal.resource.common.IJptEObject;
import org.eclipse.jpt.core.internal.resource.common.JptEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Lob</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.resource.orm.OrmPackage#getLob()
 * @model kind="class"
 * @extends IJptEObject
 * @generated
 */
public class Lob extends JptEObject implements IJptEObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Lob()
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
		return OrmPackage.Literals.LOB;
	}

} // Lob
