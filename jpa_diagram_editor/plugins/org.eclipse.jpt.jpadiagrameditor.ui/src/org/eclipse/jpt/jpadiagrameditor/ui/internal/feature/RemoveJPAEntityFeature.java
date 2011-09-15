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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.impl.RemoveContext;
import org.eclipse.graphiti.features.impl.DefaultRemoveFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.ui.PlatformUI;


public class RemoveJPAEntityFeature extends DefaultRemoveFeature {
	
	
    public RemoveJPAEntityFeature(IFeatureProvider fp) {
    	super(fp);
    }
    
    public void preRemove(IRemoveContext context) {
    	PictogramElement pe = context.getPictogramElement();
    			
//		RestoreEntityFeature ft = new RestoreEntityFeature(getFeatureProvider());
//		ICustomContext customContext = new CustomContext(new PictogramElement[] { pe });
//		ft.execute(customContext);
    	
    	final Object bo = getFeatureProvider().getBusinessObjectForPictogramElement(pe);
    	Set<Shape> shapesForDeletion = new HashSet<Shape>();
    	if (bo instanceof JavaPersistentType) {
    		JavaPersistentType jpt = (JavaPersistentType)bo;
    		JpaArtifactFactory.instance().restoreEntityClass(jpt, getFeatureProvider());    		
    		IFeatureProvider fp = getFeatureProvider();
    		List<Shape> lst = ((ContainerShape)pe).getChildren();
    		for (int i = lst.size() - 1; i >= 0; i--) {
    			Shape textShape = lst.get(i);
    			Object o = fp.getBusinessObjectForPictogramElement(textShape);
    			if ((o != null) && (o instanceof JavaPersistentAttribute)) {
    				shapesForDeletion.add(textShape);
    			}
    		}
    		Iterator<Shape> it = shapesForDeletion.iterator();
    		while(it.hasNext()) {
				RemoveAttributeFeature f = new RemoveAttributeFeature(fp, false, true);
				IRemoveContext ctx = new RemoveContext(it.next());
				f.remove(ctx);    			
    		}
    		//---------------------------------------------------------------------
    		//--Workaround for https://bugs.eclipse.org/bugs/show_bug.cgi?id=281345
    		//Job job = new Job("Remove JPA entity") {				//$NON-NLS-1$
			//	protected IStatus run(IProgressMonitor monitor) {
		    		String name = ((PersistentType)bo).getName();
					getFeatureProvider().remove(name, true);
					//return new Status(IStatus.OK, 
					//	  JPADiagramEditorPlugin.PLUGIN_ID, 
					//	  name + " is removed"); 	//$NON-NLS-1$
									  
			//	}
    		//};
    		//job.schedule();
    		//---------------------------------------------------------------------
    	} 			
    }
    
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return  (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}

	
	public void execute(IContext ctx) {
		if (!IRemoveContext.class.isInstance(ctx)) 
			return;
		IRemoveContext context = (IRemoveContext)ctx; 
    	PictogramElement pe = context.getPictogramElement();
    	Object bo = getFeatureProvider().getBusinessObjectForPictogramElement(pe);
    	if (!JavaPersistentType.class.isInstance(bo))
    		return;
    	JavaPersistentType jpt = (JavaPersistentType)bo;
		if (JPAEditorUtil.isEntityOpenElsewhere(jpt, true)) {
			String shortEntName = JPAEditorUtil.returnSimpleName(JpaArtifactFactory.instance().getEntityName(jpt));
			String message = NLS.bind(JPAEditorMessages.RemoveJPAEntityFeature_discardWarningMsg, shortEntName);
			MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					JPAEditorMessages.JPASolver_closeEditors, null, message,
			MessageDialog.WARNING, new String[]{JPAEditorMessages.BTN_OK, JPAEditorMessages.BTN_CANCEL}, 0) {
				protected int getShellStyle() {
					return SWT.CLOSE 
					| SWT.TITLE | SWT.BORDER
					| SWT.APPLICATION_MODAL
					| getDefaultOrientation();}};
			if (dialog.open() != 0)	
				return;    			
		}		
		super.execute(context);
	}
	
	
	

	
	
}