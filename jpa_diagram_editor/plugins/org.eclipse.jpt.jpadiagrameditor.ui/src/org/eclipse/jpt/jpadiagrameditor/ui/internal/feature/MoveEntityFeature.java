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
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.internal.ui.refactoring.reorg.ReorgMoveAction;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchSite;

@SuppressWarnings("restriction")
public class MoveEntityFeature extends RefactorEntityFeature {	
	
	public MoveEntityFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	public void execute(ICustomContext context) {
		ICompilationUnit cu = getFeatureProvider().getCompilationUnit(jpt);
		IWorkbenchSite ws = ((IEditorPart)getDiagramEditor()).getSite();
		ReorgMoveAction action = new ReorgMoveAction(ws);
		execute(context, action, cu);
	}
	
	
	@Override
	public String getName() {
		return JPAEditorMessages.MoveEntityFeature_ContextMenuOperationDescription;
	}
	
		
}
