/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2013 SAP AG.
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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.ui;

import java.lang.ref.WeakReference;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.platform.IDiagramContainer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditor;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.CreateDiagramJob;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorDiagramTypeProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.Wrp;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;


@SuppressWarnings({ "unused", "restriction" })
public class OpenJpaDiagramActionDelegate implements IObjectActionDelegate {
			
	private class OpenEditorRunnable implements Runnable {
		private Diagram diagram;

		private OpenEditorRunnable(Diagram diagram) {
			this.diagram = diagram;
		}

		public void run() {
			//ModelIntegrationUtil.mapDiagramToProject(diagram, jpaProject);			
			openDiagramEditor(diagram);
		}
	}
		
	public static IDiagramContainer openDiagramEditor(final Diagram diagram) {
		
		String diagramName = diagram.getName();
		JpaProject jpaProject = ModelIntegrationUtil.getProjectByDiagram(diagramName);		
		final JPADiagramEditorInput diagramEditorInput = JPADiagramEditorInput.createEditorInput(diagram, JPAEditorDiagramTypeProvider.ID); 
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		final IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		final Wrp wrp = new Wrp();
		try {
			final IEditorPart editorPart = IDE.openEditor(workbenchPage, diagramEditorInput, JPADiagramEditor.ID);
			if (editorPart instanceof JPADiagramEditor) {				
//				final JPADiagramEditor ret = (JPADiagramEditor) editorPart;
//				wrp.setObj(ret);
				PlatformUI.getWorkbench().addWorkbenchListener( new IWorkbenchListener() {
				    public boolean preShutdown( IWorkbench workbench, boolean forced ) {
				    	ModelIntegrationUtil.deleteDiagramXMIFile(diagram);
				    	workbenchPage.closeEditor( editorPart, true);
				        return true;
				    }
				 
				    public void postShutdown( IWorkbench workbench ){
				    }
				});
				//CSN #1305850 2010
				//if(ret.isDirty())
				//	ret.doSave(new NullProgressMonitor());
			}
		} catch (PartInitException e) {
			JPADiagramEditorPlugin.logError("Can't open JPA editor", e);  //$NON-NLS-1$		 							
		}	
		return (JPADiagramEditor)wrp.getObj();
	}


	private static final String ERROR_OPENING_DIAGRAM = JPAEditorMessages.OpenJpaDiagramActionDelegate_openJPADiagramErrorMsg;
//	private JpaProject jpaProject;
	private Shell shell;
	private WeakReference<ISelection> selectionRef = new WeakReference<ISelection>(null); 
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	public void run(IAction action) {
		PersistenceUnit persistenceUnit = null;
        try {      	
        	persistenceUnit = obtainJpaProjectAndPersistenceUnit(selectionRef.get());
		} catch (Exception e) {
			handleException(e);
			return;
		}
		if (persistenceUnit == null) {
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
									JPAEditorMessages.OpenJpaDiagramActionDelegate_noPersistenceUnitTitle,
									JPAEditorMessages.OpenJpaDiagramActionDelegate_noPersistenceUnitMsg);
			return;
		}
		final CreateDiagramJob createDiagramRunnable = new CreateDiagramJob(persistenceUnit, 10, true);
		createDiagramRunnable.setRule(ResourcesPlugin.getWorkspace().getRoot());
		createDiagramRunnable.addJobChangeListener(new JobChangeAdapter(){
			@Override
			public void done(IJobChangeEvent event) {
				shell.getDisplay().syncExec(new OpenEditorRunnable(createDiagramRunnable.getDiagram()));
			}
		});
		createDiagramRunnable.setUser(true);
		createDiagramRunnable.schedule();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		selectionRef = new WeakReference<ISelection>(selection);
	}

	protected PersistenceUnit obtainJpaProjectAndPersistenceUnit(ISelection selection) throws CoreException {
		JpaProject jpaProject = null;
		Object firstElement = ((IStructuredSelection) selection).getFirstElement();
		if (firstElement instanceof JpaModel) {
        	jpaProject = ((JpaModel)firstElement).getJpaProject();
        } else if (firstElement instanceof IProject) {
        	jpaProject = JpaArtifactFactory.instance().getJpaProject((IProject)firstElement);
        	int cnt = 0;
    		while ((jpaProject == null) && (cnt < 25)) { 
    			jpaProject = JpaArtifactFactory.instance().getJpaProject((IProject)firstElement);
    			if (jpaProject == null) {
    				try {
    					Thread.sleep(200);
    				} catch (InterruptedException e) {
    					JPADiagramEditorPlugin.logError("Thread sleep interrupted", e);  //$NON-NLS-1$		 
    				}
    			}
    			cnt++;
    		}
        }
        return JpaArtifactFactory.instance().getPersistenceUnit(jpaProject);
	}
		

	private void handleException(Exception e) {
		JPADiagramEditorPlugin.logError(ERROR_OPENING_DIAGRAM, e);
		IStatus status = new ErrStatus(IStatus.ERROR, JPADiagramEditor.ID, e.toString(), e);
		ErrorDialog.openError(shell, JPAEditorMessages.OpenJpaDiagramActionDelegate_openJPADiagramErrorMsgTitle, 
				ERROR_OPENING_DIAGRAM, status);
	}
	
	private class ErrStatus extends Status {
		
		public ErrStatus(int severity, String pluginId, String message, Throwable exception) {
			super(severity, message, message, exception);
		}
		
		@Override
		public IStatus[] getChildren() {
			StackTraceElement[] st = getException().getStackTrace();
			IStatus[] res = new IStatus[st == null ? 0 : st.length];
			for (int i = 0; i < st.length; i++) 
				res[i] = new Status(IStatus.ERROR, JPADiagramEditor.ID, st[i].toString());
			return res;
		}
	}
	

}
