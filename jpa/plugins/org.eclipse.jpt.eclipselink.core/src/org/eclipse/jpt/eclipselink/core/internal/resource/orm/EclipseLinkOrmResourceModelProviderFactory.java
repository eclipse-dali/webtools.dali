/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.core.resource.JpaResourceModelProvider;
import org.eclipse.jpt.core.resource.JpaResourceModelProviderFactory;

public class EclipseLinkOrmResourceModelProviderFactory 
	implements JpaResourceModelProviderFactory
{
	public JpaResourceModelProvider create(IProject project, IPath filePath) {
		return new EclipseLinkOrmResourceModelProvider(project, filePath);
	}
}
