/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.validation;

import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.core.internal.IJpaEObject;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JpaNodeModel;
import org.eclipse.jpt.core.internal.JptCorePlugin;

public class JpaHelper extends org.eclipse.wst.validation.internal.operations.WorkbenchContext
{
	IJpaProject getJpaProject() {
		return JptCorePlugin.jpaProject(getProject());
	}
	
	@Override
	public IResource getResource(Object obj) {
		// TODO temporary hack until we get rid of EMF
		return (obj instanceof IJpaEObject) ?
			((IJpaEObject) obj).getResource()
		:
			((JpaNodeModel) obj).resource();
	}
	
	/*
	 * This is used when no line number is set.  We generally use line numbers.
	 * Therefore, when this is called, we will use the default location, i.e.
	 * null.
	 */
	@Override
	public String getLocation(Object object) {
		return null;
	}
}