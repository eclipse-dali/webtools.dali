/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2012 SAP AG and others.
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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.ui.editor.DiagramBehavior;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.graphiti.ui.internal.parts.ConnectionEditPart;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.utility.internal.model.value.DoublePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.JpaFileModel;
import org.eclipse.jpt.jpa.ui.selection.JpaEditorManager;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.ui.JPADiagramEditorInput;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPADiagramBehavior;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorDiagramTypeProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.EntitiesCoordinatesXML;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.GraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPACheckSum;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.Wrp;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.views.properties.IPropertySheetPage;


@SuppressWarnings("restriction")
public class JPADiagramEditor extends DiagramEditor implements JpaEditorManager{
	
	private final ModifiablePropertyValueModel<JpaStructureNode> jpaSelectionModel = new SimplePropertyValueModel<JpaStructureNode>();
	
	public final static String ID = "org.eclipse.jpt.jpadiagrameditor.ui"; //$NON-NLS-1$

	private PersistentType inputJptType;
	private ISelectionManagerFactory jpaSelectionManagerFactory;
	
	private final ModifiablePropertyValueModel<IFile> fileModel = new SimplePropertyValueModel<IFile>();

	/**
	 * We use the JPA file to calculate the JPA selection.
	 * We update the JPA file model's file model whenever the text editor's
	 * file changes.
	 */
	private final PropertyValueModel<JpaFile> jpaFileModel;


	public JPADiagramEditor() {
		this(new SelectionManagerFactoryImpl());
	}

	public JPADiagramEditor(ISelectionManagerFactory jpaSelectionManagerFactory) {
		super();
		this.jpaSelectionManagerFactory = jpaSelectionManagerFactory;
		this.jpaFileModel = this.buildJpaFileModel();
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
	
	@Override
	protected DiagramBehavior createDiagramBehavior() {
		return new JPADiagramBehavior(this);
	}

	public void saveWithoutEntities(IProgressMonitor monitor) {
		final Diagram d = getDiagramTypeProvider().getDiagram();
		final Wrp wrp = new Wrp();
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(d);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				JPACheckSum.INSTANCE().assignEntityShapesMD5Strings(d, ModelIntegrationUtil.getProjectByDiagram(d.getName()));
				List<Shape> children = d.getChildren();
				Iterator<Shape> chIt = children.iterator();
				boolean save = true;
				while (chIt.hasNext()) {
					ContainerShape sh = (ContainerShape)chIt.next();
					PersistentType jpt = (PersistentType) getFeatureProvider()
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
			@Override
			protected void doExecute() {
				
				JpaProject jpaProject = ModelIntegrationUtil.getProjectByDiagram(d.getName());
				
				saveAllExistingOrmXmlsIfNecessary(jpaProject);
				
				JPACheckSum.INSTANCE().assignEntityShapesMD5Strings(d, jpaProject);
				List<Shape> children = d.getChildren();
				Iterator<Shape> chIt = children.iterator();
				while (chIt.hasNext()) {
					Shape sh = chIt.next();
					PersistentType jpt = (PersistentType) getFeatureProvider()
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
		
		//IProject project = ModelIntegrationUtil.getProjectByDiagram().getProject();
		EntitiesCoordinatesXML xml = new EntitiesCoordinatesXML(d.getName());
		xml.store();
		xml.close();
		
		super.doSave(monitor);
	}
	
	private void saveAllExistingOrmXmlsIfNecessary(JpaProject jpaProject) {
		PersistenceUnit unit = JpaArtifactFactory.instance().getPersistenceUnit(jpaProject);
		for(MappingFileRef fileRef : unit.getMappingFileRefs()){
			JptXmlResource ormXmlRes = jpaProject.getMappingFileXmlResource(new Path(fileRef.getFileName()));
			if(ormXmlRes != null) {
				try {
					ormXmlRes.saveIfNecessary();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
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
			String diagramURIAsString = ModelIntegrationUtil.createDiagramPath(persistenceUnit).toString();
			URI diagramURI = URI.createFileURI(diagramURIAsString);
			diagram = ModelIntegrationUtil.createDiagram(persistenceUnit, 10, true);
			JPADiagramEditorInput diagramInput = new JPADiagramEditorInput(diagram,
																		   diagramURI,
																		   JPAEditorDiagramTypeProvider.ID);
			//ModelIntegrationUtil.mapDiagramToProject(diagram, persistenceUnit
			//		.getJpaProject());
			super.init(site, diagramInput);
		} catch (CoreException e) {
			JPADiagramEditorPlugin.getDefault().getLog().log(e.getStatus());
			//ModelIntegrationUtil.removeDiagramProjectMapping(diagram);
		} catch (InvocationTargetException e) {
			IStatus status = new Status(IStatus.ERROR,
					JPADiagramEditorPlugin.PLUGIN_ID, e.getMessage(), e);
			JPADiagramEditorPlugin.getDefault().getLog().log(status);
			//ModelIntegrationUtil.removeDiagramProjectMapping(diagram);
		} catch (InterruptedException e) {
			IStatus status = new Status(IStatus.ERROR,
					JPADiagramEditorPlugin.PLUGIN_ID, e.getMessage(), e);
			JPADiagramEditorPlugin.getDefault().getLog().log(status);
			//ModelIntegrationUtil.removeDiagramProjectMapping(diagram);
		}
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {		
		IFile entityFile = (IFile) input.getAdapter(IFile.class);
		
		if (entityFile != null && entityFile.getFileExtension().equals("java")) { //$NON-NLS-1$
			initWithFileEditorInput(site, entityFile);
		} else {
			if(input instanceof IFileEditorInput){
				throw new PartInitException(NLS.bind(JPAEditorMessages.JPADiagramEditor_openDiagramErrorMSG, entityFile.getName()));
			} else {
				super.init(site, input);
			}
		}
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
			JpaSelectionManager selectionManager = getJpaSelectionManager();
			Object m = ((EditPart) o).getModel();
			if (m == null)
				return;
			if (m instanceof PictogramElement) {
				Object bo = getFeatureProvider()
						.getBusinessObjectForPictogramElement(
								(PictogramElement) m);
				if ((bo == null) || (!(bo instanceof JpaStructureNode))){
					if(jpaSelectionModel == null)
						return;
					jpaSelectionModel.setValue(null);
					setFileModel(null);
					return;
				}
				JpaStructureNode jpaStructureNode = (JpaStructureNode) bo;
				setFileModel(jpaStructureNode);
				jpaSelectionModel.setValue(selectedNode);

				selectionManager.setSelection(selectedNode);
				return;
			}
		}
	}

	private JpaSelectionManager getJpaSelectionManager() {
		return jpaSelectionManagerFactory.getSelectionManager(this.getSite()
				.getWorkbenchWindow());
	}

	public static interface ISelectionManagerFactory {
		public JpaSelectionManager getSelectionManager(IWorkbenchWindow window);
	}

	private static class SelectionManagerFactoryImpl implements
			ISelectionManagerFactory {

		public JpaSelectionManager getSelectionManager(IWorkbenchWindow window) {
			return PlatformTools.getAdapter(window, JpaSelectionManager.class);
		}

	}

	public IEditorPart getEditor() {
		return this.getEditor();
	}

	public ModifiablePropertyValueModel<JpaStructureNode> getJpaSelectionModel() {
		return this.jpaSelectionModel;
	}
	
	public PropertyValueModel<JpaFile> getJpaFileModel() {
		return this.jpaFileModel;
	}

	private PropertyValueModel<JpaFile> buildJpaFileModel() {
		return new DoublePropertyValueModel<JpaFile>(this.buildJpaFileModelModel());
	}

	private PropertyValueModel<PropertyValueModel<JpaFile>> buildJpaFileModelModel() {
		return new TransformationPropertyValueModel<IFile, PropertyValueModel<JpaFile>>(this.fileModel, JPA_FILE_MODEL_TRANSFORMER);
	}

	private static final Transformer<IFile, PropertyValueModel<JpaFile>> JPA_FILE_MODEL_TRANSFORMER = new JpaFileModelTransformer();

	/* CU private */ static class JpaFileModelTransformer
		extends AbstractTransformer<IFile, PropertyValueModel<JpaFile>>
	{
		@Override
		protected PropertyValueModel<JpaFile> transform_(IFile file) {
			return (JpaFileModel) file.getAdapter(JpaFileModel.class);
		}
	}
	
	private void setFileModel(JpaStructureNode node) {
		this.fileModel.setValue(getSelectedEntityFile(node, false));
	}
	
	private JpaStructureNode selectedNode;
	
	private IFile getSelectedEntityFile(JpaStructureNode node, boolean fromAttribte) {
		IResource resource = null;
		if (node != null) {
			if (node instanceof PersistentType) {
				PersistentType pt = (PersistentType) node;
				if(pt instanceof JavaPersistentType) {
					JavaPersistentType jpt = (JavaPersistentType) pt;
					MappingFileRef mappingFileRef = JpaArtifactFactory.instance().getOrmXmlByForPersistentType(jpt);
					if(!fromAttribte)
						selectedNode = jpt;
					if(mappingFileRef != null){
						PersistentType type = mappingFileRef.getMappingFile().getPersistentType(jpt.getName());
						if(!fromAttribte)
							selectedNode = type;
						return getSelectedEntityFile(type, false);
					}
					
					resource = ((JavaPersistentType) pt).getResource();
				} else if (pt instanceof OrmPersistentType) {
					OrmPersistentType ormPt = (OrmPersistentType) pt;
					resource = ormPt.getParent().getResource();
				}
			} else if (node instanceof PersistentAttribute) {
				PersistentType pt = ((PersistentAttribute) node).getDeclaringPersistentType();
				selectedNode = node;
				
				MappingFileRef mappingFileRef = JpaArtifactFactory.instance().getOrmXmlByForPersistentType(((PersistentAttribute) node).getDeclaringPersistentType());
				if(mappingFileRef != null){
					PersistentType type = mappingFileRef.getMappingFile().getPersistentType(((PersistentAttribute) node).getDeclaringPersistentType().getName());
					selectedNode = type.getAttributeNamed(((PersistentAttribute) node).getName());
				}
				return getSelectedEntityFile(pt, true);
			}

			if (resource != null && resource.exists() && (resource instanceof IFile)) {
				return (IFile) resource;
			}
		}

		return null;
	}
}
