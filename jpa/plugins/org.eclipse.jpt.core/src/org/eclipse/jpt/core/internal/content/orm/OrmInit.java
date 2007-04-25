/*******************************************************************************
 *  Copyright (c) 2005, 2007 Oracle. All rights reserved.  This program and 
 *  the accompanying materials are made available under the terms of the 
 *  Eclipse Public License v1.0 which accompanies this distribution, and is 
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jpt.core.internal.content.orm.resource.OrmXmlResourceFactory;
import org.eclipse.wst.common.componentcore.internal.impl.WTPEntityResolver;
import org.eclipse.wst.common.internal.emf.utilities.DOMUtilities;
import org.eclipse.wst.common.internal.emf.utilities.ExtendedEcoreUtil;

public class OrmInit
{
	private static boolean initialized = false;
	
	public static void init() {
		init(true);
	}
	
	public static void init(boolean shouldPreregisterPackages) {
		if (! initialized) {
			initialized = true;
			DOMUtilities.setDefaultEntityResolver(WTPEntityResolver.INSTANCE);
			initResourceFactories();
		}
		if (shouldPreregisterPackages) {
			preregisterPackages();
		}
	}
	
	private static void initResourceFactories() {
		OrmXmlResourceFactory.register();
	}
	
	private static void preregisterPackages() {
		ExtendedEcoreUtil.preRegisterPackage(
			"orm.xmi", //$NON-NLS-1$
			new EPackage.Descriptor() { 
				public EPackage getEPackage() {
					return OrmPackage.eINSTANCE;
				}
				
				public EFactory getEFactory() {
					return OrmFactory.eINSTANCE;
				}
			}
		);
	}
}
