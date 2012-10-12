/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.resource.jaxbprops.JaxbPropertiesResource;


public class JaxbPackagePropertyTester
		extends PropertyTester {
	
	public static final String HAS_ECLIPSELINK_JAXB_PROPERTY = "hasEclipseLinkJaxbProperty"; //$NON-NLS-1$
	
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (receiver instanceof JaxbPackage) {
			return test((JaxbPackage) receiver, property, expectedValue);
		}
		return false;
	}
	
	private boolean test(JaxbPackage jaxbPackage, String property, Object expectedValue) {
		if (property.equals(HAS_ECLIPSELINK_JAXB_PROPERTY)) {
			JaxbPropertiesResource jpr = 
				jaxbPackage.getJaxbProject().getJaxbPropertiesResource(jaxbPackage.getName());
			
			boolean value = jpr != null;
			
			if (value) {
				String factoryProp = "javax.xml.bind.context.factory";
				String factoryPropValue = "org.eclipse.persistence.jaxb.JAXBContextFactory";
				value = ObjectTools.equals(jpr.getProperty(factoryProp), factoryPropValue);
			}
			
			return ObjectTools.equals(value, expectedValue);
		}
		return false;
	}
}
