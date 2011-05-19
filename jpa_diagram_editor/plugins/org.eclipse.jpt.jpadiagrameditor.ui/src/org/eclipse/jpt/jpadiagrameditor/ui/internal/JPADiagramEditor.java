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
package org.eclipse.jpt.jpadiagrameditor.ui.internal;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.graphiti.ui.internal.parts.ConnectionEditPart;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.internal.selection.DefaultJpaSelection;
import org.eclipse.jpt.jpa.ui.internal.selection.JpaSelectionManager;
import org.eclipse.jpt.jpa.ui.internal.selection.SelectionManagerFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.ui.JPADiagramEditorInput;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorContextMenuProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorDiagramTypeProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.EntitiesCoordinatesXML;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.GraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPACheckSum;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.Wrp;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.views.properties.IPropertySheetPage;


@SuppressWarnings("restriction")
public class JPADiagramEditor extends DiagramEditor {

	public final static String ID = "org.eclipse.jpt.jpadiagrameditor.ui"; //$NON-NLS-1$
	private DefaultEditDomain editDomain;

	private JavaPersistentType inputJptType;
	private ISelectionManagerFactory jpaSelectionManagerFactory;

	public JPADiagramEditor() {
		this(new SelectionManagerFactoryImpl());
		editDomain = new DefaultEditDomain(this);
	}

	public JPADiagramEditor(ISelectionManagerFactory jpaSelectionManagerFactory) {
		super();
		this.jpaSelectionManagerFactory = jpaSelectionManagerFactory;
		editDomain = new DefaultEditDomain(this);
	}

	@Override
	public JPAEditorDiagramTypeProvider getDiagramTypeProvider() {
		return (JPAEditorDiagramTypeProvider) super.getDiagramTypeProvider();
	}

	public IFeatureProvider getFeatureProvider() {
		return getDiagramTypeProvider().getFeatureProvider();
	}

	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class type) {		
		if(IPropertySheetPage.class.equals(type))
			return null;
		
		return super.getAdapter(type);
	}

	public void saveWithoutEntities(IProgressMonitor monitor) {
		final Diagram d = getDiagramTypeProvider().getDiagram();
		final Wrp wrp = new Wrp();
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(d);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			protected void doExecute() {
				JPACheckSum.INSTANCE().assignEntityShapesMD5Strings(d, ModelIntegrationUtil.getProjectByDiagram(d));
				List<Shape> children = d.getChildren();
				Iterator<Shape> chIt = children.iterator();
				boolean save = true;
				while (chIt.hasNext()) {
					ContainerShape sh = (ContainerShape)chIt.next();
					JavaPersistentType jpt = (JavaPersistentType) getFeatureProvider()
							.getBusinessObjectForPictogramElement(sh);
					String entName = JPAEditorUtil.getText(jpt);
					ICompilationUnit cu = JPAEditorUtil.getCompilationUnit(jpt);
					try {
						if (cu.hasUnsavedChanges()) {
							entName = "* " + entName; 	//$NON-NLS-1$
							save = false;
						}
					} catch (JavaModelException e) {
						JPADiagramEditorPlugin.logError("Problem with compilation unit", e); //$NON-NLS-1$				 
					}
					GraphicsUpdater.updateHeader(sh, entName);
				}
				wrp.setObj(new Boolean(save));
			}
		});		
		if ((Boolean)wrp.getObj())
			super.doSave(monitor);
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		final Diagram d = getDiagramTypeProvider().getDiagram();
		//IJPAEditorFeatureProvider fp = (IJPAEditorFeatureProvider)getFeatureProvider();
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(d);
		if ((d == null) || (ted == null)) {
			JPADiagramEditorPlugin.logError((d == null) ? "Diagram is null" : "TransactionalEditingDomain is null", new Exception()); //$NON-NLS-1$	 //$NON-NLS-2$	     					
			return;
		}
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			protected void doExecute() {
				JPACheckSum.INSTANCE().assignEntityShapesMD5Strings(d, ModelIntegrationUtil.getProjectByDiagram(d));
				List<Shape> children = d.getChildren();
				Iterator<Shape> chIt = children.iterator();
				while (chIt.hasNext()) {
					Shape sh = chIt.next();
					JavaPersistentType jpt = (JavaPersistentType) getFeatureProvider()
							.getBusinessObjectForPictogramElement(sh);
					if (jpt != null)
						JpaArtifactFactory.instance().forceSaveEntityClass(jpt, (IJPAEditorFeatureProvider) getFeatureProvider());
				}
				/*
				Resource res = ModelIntegrationUtil.getResourceByDiagram(d);
				
				try {
					//doSave(monitor);
					res.save(null);
				} catch (IOException e) {
					System.err.println("Can't save the diagram");	//$NON-NLS-1$
					e.printStackTrace(); 	
				}
				*/
				
			}
		});
		
		IProject project = ModelIntegrationUtil.getProjectByDiagram(d).getProject();
		EntitiesCoordinatesXML xml = new EntitiesCoordinatesXML(project, d);
		xml.store();
		xml.close();
		
		super.doSave(monitor);
	}

	@Override
	protected ContextMenuProvider createContextMenuProvider() {
		return new JPAEditorContextMenuProvider(getGraphicalViewer(),
				getActionRegistry(),
				getConfigurationProvider());
	}

	private void initWithFileEditorInput(final IEditorSite site,
			final IFile entityFile) {
		Diagram diagram = null;
		try {			
			ProgressMonitorDialog dialog = new ProgressMonitorDialog(site
					.getShell());
			dialog.run(true, false, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					monitor.beginTask(
							JPAEditorMessages.JPADiagramEditor_waitingForMoin,
							IProgressMonitor.UNKNOWN);					
					monitor.done();
				}
			});

			inputJptType = JPAEditorUtil.getJPType(JavaCore
					.createCompilationUnitFrom(entityFile));
			setPartProperty(
					JPAEditorConstants.OPEN_WHOLE_PERSISTENCE_UNIT_EDITOR_PROPERTY,
					inputJptType.getName());
			PersistenceUnit persistenceUnit = inputJptType.getPersistenceUnit();
			String diagramURI = ModelIntegrationUtil.createDiagramPath(persistenceUnit).toString();
			TransactionalEditingDomain defaultTransEditDomain = (TransactionalEditingDomain)inputJptType.
								getJpaProject().getProject().getAdapter(TransactionalEditingDomain.class);
			diagram = ModelIntegrationUtil.createDiagram(persistenceUnit, 10, true);
			JPADiagramEditorInput diagramInput = new JPADiagramEditorInput(diagram,
																		   diagramURI, 
																		   defaultTransEditDomain, 
																		   JPAEditorDiagramTypeProvider.ID, 
																		   false);
			ModelIntegrationUtil.mapDiagramToProject(diagram, persistenceUnit
					.getJpaProject());
			super.init(site, diagramInput);
		} catch (CoreException e) {
			JPADiagramEditorPlugin.getDefault().getLog().log(e.getStatus());
			ModelIntegrationUtil.removeDiagramProjectMapping(diagram);
		} catch (InvocationTargetException e) {
			IStatus status = new Status(IStatus.ERROR,
					JPADiagramEditorPlugin.PLUGIN_ID, e.getMessage(), e);
			JPADiagramEditorPlugin.getDefault().getLog().log(status);
			ModelIntegrationUtil.removeDiagramProjectMapping(diagram);
		} catch (InterruptedException e) {
			IStatus status = new Status(IStatus.ERROR,
					JPADiagramEditorPlugin.PLUGIN_ID, e.getMessage(), e);
			JPADiagramEditorPlugin.getDefault().getLog().log(status);
			ModelIntegrationUtil.removeDiagramProjectMapping(diagram);
		}
	}

	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {		
		IFile entityFile = (IFile) input.getAdapter(IFile.class);
		
		if (entityFile != null && entityFile.getFileExtension().equals("java")) { //$NON-NLS-1$
			initWithFileEditorInput(site, entityFile);
		} else
			super.init(site, input);
	}
	
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		super.selectionChanged(part, selection);
		if (!(selection instanceof IStructuredSelection))
			return;
		IStructuredSelection structSel = (IStructuredSelection)selection;
		@SuppressWarnings("unchecked")
		Iterator<IStructuredSelection> iter = structSel.iterator();
		while(iter.hasNext()){
			Object object = iter.next();
			if(object instanceof EditPart){
				EditPart editPart = (EditPart) object;
				if(editPart instanceof ConnectionEditPart){
					ConnectionEditPart connectionEditPart =  (ConnectionEditPart) (editPart);
					connectionEditPart.removeEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE);
				}
			}
		}
		
		Object o = ((IStructuredSelection) selection).getFirstElement();
		if (o instanceof EditPart) {			
			JpaSelectionManager selectionManager = getJpaSelectionManager(part);
			Object m = ((EditPart) o).getModel();
			if (m == null)
				return;
			if (m instanceof PictogramElement) {
				Object bo = getFeatureProvider()
						.getBusinessObjectForPictogramElement(
								(PictogramElement) m);
				if ((bo == null) || (!(bo instanceof JpaStructureNode)))
					return;
				selectionManager.select(new DefaultJpaSelection(
						(JpaStructureNode) bo), null);
				return;
			}
		}
	}

	private JpaSelectionManager getJpaSelectionManager(IWorkbenchPart part) {
		return jpaSelectionManagerFactory.getSelectionManager(part.getSite()
				.getWorkbenchWindow());
	}

	public static interface ISelectionManagerFactory {
		public JpaSelectionManager getSelectionManager(IWorkbenchWindow window);
	}

	private static class SelectionManagerFactoryImpl implements
			ISelectionManagerFactory {

		public JpaSelectionManager getSelectionManager(IWorkbenchWindow window) {
			return SelectionManagerFactory.getSelectionManager(window);
		}

	}
	
	@Override
	public DefaultEditDomain getEditDomain() {
		return editDomain;
	}
	
}
