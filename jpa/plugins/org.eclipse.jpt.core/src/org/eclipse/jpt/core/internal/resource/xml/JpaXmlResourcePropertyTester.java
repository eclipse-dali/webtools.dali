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
package org.eclipse.jpt.core.internal.resource.xml;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.XmlFile;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;

public class JpaXmlResourcePropertyTester
	extends PropertyTester
{
	public static final String IS_LATEST_SUPPORTED_VERSION = "isLatestSupportedVersion";
	
	
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		JpaXmlResource xmlResource = null;
		if (receiver instanceof JpaXmlResource) {
			xmlResource = (JpaXmlResource) receiver;
		}
		else if (receiver instanceof XmlFile) {
			xmlResource = ((XmlFile) receiver).getXmlResource();
		}
		else {
			return false;
		}
		
		if (IS_LATEST_SUPPORTED_VERSION.equals(property)) {
			Boolean expectedIsLatestSupportedVersion = (Boolean) expectedValue;
			JpaProject jpaProject = xmlResource.getJpaProject();
			IContentType contentType = xmlResource.getContentType();
			Boolean actualIsLatestSupportedVersion = 
					xmlResource.getVersion() != null
						&& xmlResource.getVersion().equals(
							jpaProject.getJpaPlatform().getMostRecentSupportedResourceType(contentType).getVersion());
			
			return actualIsLatestSupportedVersion == expectedIsLatestSupportedVersion;
		}
		
		return false;
	}
}
