/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.validation;

import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.jpa.core.JpaNode;

public class JpaHelper extends org.eclipse.wst.validation.internal.operations.WorkbenchContext {

	@Override
	public IResource getResource(Object obj) {
		return ((JpaNode) obj).getResource();
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
