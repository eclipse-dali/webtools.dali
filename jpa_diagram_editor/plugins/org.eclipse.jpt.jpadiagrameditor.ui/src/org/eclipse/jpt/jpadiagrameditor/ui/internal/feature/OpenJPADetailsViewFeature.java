/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2012 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;


public class OpenJPADetailsViewFeature extends AbstractCustomFeature {
	
	public OpenJPADetailsViewFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}	
	
	public void execute(ICustomContext context) {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
					showView(JPAEditorConstants.ID_VIEW_JPA_DETAILS);
		} catch (PartInitException e) {
			JPADiagramEditorPlugin.logError("Can't open JPA Details view", e);  //$NON-NLS-1$		 	
		}
	}
	
	@Override
	public String getName() {
		return JPAEditorMessages.JPAEditorToolBehaviorProvider_openJPADetailsView;
	}

}
