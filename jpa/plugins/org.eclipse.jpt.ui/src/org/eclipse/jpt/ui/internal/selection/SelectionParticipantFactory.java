/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.selection;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.ui.internal.views.JpaDetailsView;
import org.eclipse.jpt.ui.internal.views.structure.JpaStructureView;
import org.eclipse.ui.IWorkbenchPart;

public class SelectionParticipantFactory 
	implements IAdapterFactory
{
	private static final Class[] ADAPTER_LIST = new Class[] { IJpaSelectionParticipant.class };

	public Class[] getAdapterList() {
		return ADAPTER_LIST;
	}

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (! (adaptableObject instanceof IWorkbenchPart)) {
			return null;
		}
		
		IJpaSelectionManager selectionManager = 
			SelectionManagerFactory.getSelectionManager(((IWorkbenchPart) adaptableObject).getSite().getWorkbenchWindow());
		// TODO turn this into extension point
//		if (adaptableObject instanceof ITextEditor) {
//			return new TextEditorSelectionParticipant(selectionManager, (ITextEditor) adaptableObject);
//		}
		if (adaptableObject instanceof JpaStructureView) {
			return new JpaStructureSelectionParticipant(selectionManager, (JpaStructureView) adaptableObject);
		}
		else if (adaptableObject instanceof JpaDetailsView) {
			return new JpaDetailsSelectionParticipant((JpaDetailsView) adaptableObject);
		}
		else {
			return null;
		}
	}

}
