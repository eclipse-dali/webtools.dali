/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.AbstractJarFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class EclipseLinkJarFileRef 
	extends AbstractJarFileRef
{
	public EclipseLinkJarFileRef(PersistenceUnit parent, XmlJarFileRef xmlJarFileRef) {
		super(parent, xmlJarFileRef);
	}

	@Override
	protected IPath[] resolveDeploymentJarFilePathWeb(IPath root, IPath jarFilePath) {
		IPath[] genericPath = super.resolveDeploymentJarFilePathWeb(root, jarFilePath);
		return CollectionTools.removeLast(genericPath);
	}

}
