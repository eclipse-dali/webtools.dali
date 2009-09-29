/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.resource.xml;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

public class EmfTools
{
	public static <T extends EObject> T create(
			EFactory eFactory, EClass eClass, Class<T> javaClass) {
		
		EPackage ePackage = eFactory.getEPackage();
		for (EClassifier each : ePackage.getEClassifiers()) {
			if (each instanceof EClass) {
				EClass eachEClass = (EClass) each;
				if (eClass.isSuperTypeOf(eachEClass)) {
					return (T) eFactory.create(eachEClass);
				}
			}
		}
		throw new IllegalArgumentException(
			"Factory does not support objects of type \'" + eClass.getName() + "\'");
	}
}
