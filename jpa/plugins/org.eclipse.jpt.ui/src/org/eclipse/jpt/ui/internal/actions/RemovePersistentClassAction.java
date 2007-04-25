package org.eclipse.jpt.ui.internal.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal;
import org.eclipse.jpt.core.internal.content.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class RemovePersistentClassAction
	implements IObjectActionDelegate
{
	private XmlPersistentType persistentClass;
	
	
	public RemovePersistentClassAction() {
		super();
	}
	
	public void run(IAction action) {
		XmlTypeMapping mapping = persistentClass.getMapping();
		((EntityMappingsInternal) mapping.getEntityMappings()).getTypeMappings().remove(mapping);
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		persistentClass = (XmlPersistentType) ((StructuredSelection) selection).getFirstElement();
	}
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// do nothing
	}
}
