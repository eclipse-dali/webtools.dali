/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

public class JpaFileAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] { JpaFile.class };
	
	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}
	
	public Object getAdapter(Object adaptableObject, @SuppressWarnings("unchecked") Class adapterType) {
		if (! (adaptableObject instanceof IEditorPart)) {
			return null;
		}
		
		IEditorInput editorInput = ((IEditorPart) adaptableObject).getEditorInput();
		if (! (editorInput instanceof IFileEditorInput)) {
			return null;
		}

		IFile file = ((IFileEditorInput) editorInput).getFile();
		return JptCorePlugin.getJpaFile(file);
	}

}
