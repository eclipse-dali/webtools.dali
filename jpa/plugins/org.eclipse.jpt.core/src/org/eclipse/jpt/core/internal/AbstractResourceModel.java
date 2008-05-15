/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.ResourceModel;
import org.eclipse.jpt.utility.internal.model.AbstractModel;

public abstract class AbstractResourceModel
	extends AbstractModel
	implements ResourceModel
{
	protected final IFile file;

	protected AbstractResourceModel(IFile file) {
		super();
		this.file = file;
	}
	
	public IFile getFile() {
		return this.file;
	}
	
	public void dispose() {
		// do nothing by default
	}

}
