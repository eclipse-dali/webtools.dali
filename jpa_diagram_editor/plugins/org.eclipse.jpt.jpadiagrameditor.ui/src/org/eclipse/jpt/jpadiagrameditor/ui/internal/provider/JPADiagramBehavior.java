/*******************************************************************************
 * Copyright (c) 2013, 2019 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.provider;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.graphiti.ui.editor.DiagramBehavior;
import org.eclipse.graphiti.ui.editor.IDiagramContainerUI;

public class JPADiagramBehavior extends DiagramBehavior{

	public JPADiagramBehavior(IDiagramContainerUI diagramContainer) {
		super(diagramContainer);
		this.setParentPart(diagramContainer.getWorkbenchPart());
		this.initDefaultBehaviors();
	}
	
	@Override
	protected ContextMenuProvider createContextMenuProvider() {
		return new JPAEditorContextMenuProvider(getDiagramContainer().getGraphicalViewer(),
				getDiagramContainer().getActionRegistry(),
				getConfigurationProvider());
	}
}
