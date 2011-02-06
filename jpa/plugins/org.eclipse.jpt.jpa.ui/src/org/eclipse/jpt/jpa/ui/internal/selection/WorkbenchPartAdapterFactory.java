/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.selection;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.jpa.ui.internal.views.JpaDetailsView;
import org.eclipse.jpt.jpa.ui.internal.views.structure.JpaStructureView;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Factory to build adapters for a workbench part:
 *   - JPA selection participant (if the editor part is a text editor etc.)
 * 
 * See org.eclipse.jpt.jpa.ui plugin.xml.
 */
public class WorkbenchPartAdapterFactory 
	implements IAdapterFactory
{
	private static final Class<?>[] ADAPTER_LIST = new Class[] { JpaSelectionParticipant.class };

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, @SuppressWarnings("unchecked") Class adapterType) {
		if (adaptableObject instanceof IWorkbenchPart) {
			return this.getAdapter((IWorkbenchPart) adaptableObject, adapterType);
		}
		return null;
	}

	private Object getAdapter(IWorkbenchPart workbenchPart, Class<?> adapterType) {
		if (adapterType == JpaSelectionParticipant.class) {
			return this.buildJpaSelectionParticipant(workbenchPart);
		}
		return null;
	}

	private JpaSelectionParticipant buildJpaSelectionParticipant(IWorkbenchPart workbenchPart) {
		JpaSelectionManager selectionManager = SelectionManagerFactory.getSelectionManager(workbenchPart.getSite().getWorkbenchWindow());
		if (workbenchPart instanceof ITextEditor) {
			return new TextEditorSelectionParticipant(selectionManager, (ITextEditor) workbenchPart);
		}
		if (workbenchPart instanceof JpaStructureView) {
			return new JpaStructureSelectionParticipant(selectionManager, (JpaStructureView) workbenchPart);
		}
		if (workbenchPart instanceof JpaDetailsView) {
			return new JpaDetailsSelectionParticipant((JpaDetailsView) workbenchPart);
		}
		return null;
	}

}
