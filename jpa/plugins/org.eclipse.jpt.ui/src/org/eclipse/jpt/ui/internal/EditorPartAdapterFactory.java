/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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

/**
 * Factory to build adapters for a editor part:
 *   - JPA file (if the editor part is a file editor etc.)
 * 
 * See org.eclipse.jpt.ui plugin.xml.
 */
public class EditorPartAdapterFactory
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] { JpaFile.class };

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("unchecked") Class adapterType) {
		if (adaptableObject instanceof IEditorPart) {
			return this.getAdapter((IEditorPart) adaptableObject, adapterType);
		}
		return null;
	}

	private Object getAdapter(IEditorPart editorPart, Class<?> adapterType) {
		if (adapterType == JpaFile.class) {
			return this.getJpaFile(editorPart);
		}
		return null;
	}

	private JpaFile getJpaFile(IEditorPart editorPart) {
		IEditorInput editorInput = editorPart.getEditorInput();
		if (editorInput instanceof IFileEditorInput) {
			return this.getJpaFile((IFileEditorInput) editorInput);
		}
		return null;
	}

	private JpaFile getJpaFile(IFileEditorInput fileEditorInput) {
		return this.getJpaFile(fileEditorInput.getFile());
	}

	private JpaFile getJpaFile(IFile file) {
		return JptCorePlugin.getJpaFile(file);
	}

}
