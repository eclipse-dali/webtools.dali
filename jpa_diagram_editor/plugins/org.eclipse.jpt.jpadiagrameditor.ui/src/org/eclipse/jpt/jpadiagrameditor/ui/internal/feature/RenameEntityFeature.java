/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.graphiti.ui.editor.IDiagramContainerUI;
import org.eclipse.jdt.ui.actions.RenameAction;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.ui.IWorkbenchSite;



public class RenameEntityFeature extends RefactorEntityFeature {

	public RenameEntityFeature(IFeatureProvider fp) {
		super(fp);
	}

	public void execute(ICustomContext context) {
		IWorkbenchSite ws = ((IDiagramContainerUI)getFeatureProvider().getDiagramTypeProvider().getDiagramBehavior().getDiagramContainer()).getSite();
		RenameAction action = new RenameAction(ws);
		execute(context, action);		
	}
	
	@Override
	public String getName() {
		return JPAEditorMessages.RenameEntityFeature_ContextMenuOperationDescription;
	}
	
}
