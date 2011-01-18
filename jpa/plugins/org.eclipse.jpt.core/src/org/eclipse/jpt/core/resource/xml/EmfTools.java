/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.xml;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public class EmfTools {

	public static <T extends EObject> T create(EFactory eFactory, EClass eClass, Class<T> javaClass) {
		for (EClassifier factoryEClassifier : eFactory.getEPackage().getEClassifiers()) {
			if (factoryEClassifier instanceof EClass) {
				EClass factoryEClass = (EClass) factoryEClassifier;
				if (eClass.isSuperTypeOf(factoryEClass)) {
					return javaClass.cast(eFactory.create(factoryEClass));
				}
			}
		}
		throw new IllegalArgumentException("Factory does not support objects of type \'" + eClass.getName() + '\''); //$NON-NLS-1$
	}
}
