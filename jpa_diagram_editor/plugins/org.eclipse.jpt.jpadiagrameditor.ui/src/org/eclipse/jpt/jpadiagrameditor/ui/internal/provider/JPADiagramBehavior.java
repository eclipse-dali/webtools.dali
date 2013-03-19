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
