/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.core.resource.JpaXmlResourceProvider;
import org.eclipse.jpt.core.resource.JpaXmlResourceProviderFactory;

public class OrmXmlResourceProviderFactory implements JpaXmlResourceProviderFactory
{
	public JpaXmlResourceProvider create(IProject project, IPath filePath) {
		return new OrmXmlResourceProvider(project, filePath);
	}
}
