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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.provider;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.context.impl.RemoveContext;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.platform.IDiagramEditor;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditor;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.facade.EclipseFacade;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddAllEntitiesFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddJPAEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.RemoveRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.EntitiesCoordinatesXML;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPACheckSum;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPASolver;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.SizePosition;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;


public class JPAEditorDiagramTypeProvider extends AbstractDiagramTypeProvider {
	
	private IToolBehaviorProvider[] toolBehaviorProviders;
    public final static String ID = "org.eclipse.jpt.jpadiagrameditor.ui.provider.JPAEditorDiagramTypeProvider"; //$NON-NLS-1$
    boolean isDisposed = false;
    boolean readOnly = false;
	
    public JPAEditorDiagramTypeProvider() {
    	IFeatureProvider fp = new JPAEditorFeatureProvider(this, new JPASolver());
        setFeatureProvider(fp);
    }

    @Override
	public void init(Diagram diagram, IDiagramEditor diagramEditor) {
    	super.init(diagram, diagramEditor);
    	if (getTargetJPAProject() == null)
    		closeEditor();
    	JPAEditorDiagramTypeProvider provider = ModelIntegrationUtil.getProviderByDiagram(diagram.getName());
    	if ((provider != null) && provider.isAlive()) 
    		provider.getDiagramEditor().getSite().getWorkbenchWindow().getActivePage().closeEditor(provider.getDiagramEditor(), true);
	}
    
    
    @Override
    public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {
        if (toolBehaviorProviders == null) {
            toolBehaviorProviders =
                new IToolBehaviorProvider[] { new JPAEditorToolBehaviorProvider(this, EclipseFacade.INSTANCE) };
        }
        return toolBehaviorProviders;
    }
    
    public JPAEditorFeatureProvider getFeatureProvider() {
    	return (JPAEditorFeatureProvider)super.getFeatureProvider();
    }
    
	public boolean hasToAdd() {
		JpaProject project = getTargetJPAProject();
		PersistenceUnit unit = project.getRootContextNode().getPersistenceXml().getPersistence().persistenceUnits().next();
		
		for (Iterator<ClassRef> classRefs = unit.classRefs(); classRefs.hasNext();) {
			ClassRef classRef = classRefs.next();
			if (classRef.getJavaPersistentType() != null) { 
				JavaPersistentType jpt = classRef.getJavaPersistentType(); 
				if (jpt.getMappingKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY) {
					PictogramElement pe = getFeatureProvider().getPictogramElementForBusinessObject(jpt);
					if (pe == null)
						return true;					
				}
			}
		}		
		return false;
	}
        
    
    public 	void postInit() {
    	final String jptName = getDiagramEditor().getPartProperty(JPAEditorConstants.OPEN_WHOLE_PERSISTENCE_UNIT_EDITOR_PROPERTY);
		if (jptName != null) {
			boolean hasToAdd = hasToAdd(); 
			boolean readOnly = openPersistedDiagram(hasToAdd);
			if (hasToAdd && !readOnly)
			addRemainingEntities();
		} else
			try {
				openPersistedDiagram(false);
			} catch (NullPointerException e) {
				return;
			}
	}
    
    private void addRemainingEntities() {
		final AddAllEntitiesFeature feature = new AddAllEntitiesFeature(getFeatureProvider());
		final CustomContext context = new CustomContext();
		TransactionalEditingDomain ted = ModelIntegrationUtil.getTransactionalEditingDomain(feature.getFeatureProvider().getDiagramTypeProvider().getDiagram());
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				feature.execute(context);
			}
		});
	}

    private void closeEditor() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();					
				page.closeEditor(getDiagramEditor(), false);
			}
		});    	
    }
    
	private boolean openPersistedDiagram(boolean hasToAdd) {		
		final Diagram diagram = getDiagram();
		final JpaProject proj = getTargetJPAProject();
		IProject project = proj.getProject();
		PersistenceUnit pu = JpaArtifactFactory.instance().getPersistenceUnit(proj);
		String diagramName = pu.getName();
	    IPath path = ModelIntegrationUtil.getDiagramsFolderPath(project).append(diagramName).addFileExtension(ModelIntegrationUtil.DIAGRAM_FILE_EXTENSION);
		final IFile f = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		boolean readOnly = (f != null) && f.exists() && f.isReadOnly();
		if (readOnly) {
			if (JPACheckSum.INSTANCE().isModelDifferentFromDiagram(diagram, proj) || hasToAdd) {
				String message = hasToAdd ? JPAEditorMessages.JPAEditorDiagramTypeProvider_JPADiagramReadOnlyHasToAddMsg : 
								JPAEditorMessages.JPAEditorDiagramTypeProvider_JPADiagramReadOnlyMsg;
				MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						JPAEditorMessages.JPAEditorDiagramTypeProvider_JPADiagramReadOnlyTitle, null, message,
						MessageDialog.INFORMATION, new String[] {JPAEditorMessages.BTN_OK, JPAEditorMessages.BTN_CANCEL}, 0) {
					protected int getShellStyle() {
						return SWT.TITLE | SWT.BORDER
							| SWT.APPLICATION_MODAL
							| getDefaultOrientation();
					}
				};
				if (dialog.open() == 1) {
					closeEditor();		
					return true;
				} else {
					IStatus stat = ResourcesPlugin.getWorkspace().validateEdit(new IFile[]{f}, null);
					if (!stat.isOK()) {
						message = NLS.bind(JPAEditorMessages.JPAEditorDiagramTypeProvider_cantMakeDiagramWritableMsg, 
								stat.getMessage());;
						dialog = new MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
								JPAEditorMessages.JPAEditorDiagramTypeProvider_cantMakeDiagramWritableTitle, null, message,
								MessageDialog.CANCEL, new String[] {JPAEditorMessages.BTN_OK}, 0) {
							protected int getShellStyle() {
								return SWT.CLOSE | SWT.TITLE | SWT.BORDER
									| SWT.APPLICATION_MODAL
									| getDefaultOrientation();
							}
						};
						dialog.open();
						closeEditor();						
						return true;
					} else {
						readOnly = false;
					}
				}
			} else {
				return readOnly;
			}
		}
		
		removeConnections();
		
		final Hashtable<String, SizePosition> marks = new Hashtable<String, SizePosition>(); 				
		EntitiesCoordinatesXML xml = new EntitiesCoordinatesXML(project.getName());
		xml.load(marks);
		xml.clean();
				
		List<Shape> picts = diagram.getChildren();
		Iterator<Shape> it = picts.iterator();
		HashSet<Shape> toDelete = new HashSet<Shape>();
		// collecting data from the saved pictograms
		while (it.hasNext()) {
			Shape pict = it.next();
			toDelete.add(pict);
		}
		
		final Iterator<Shape> iter = toDelete.iterator();
				
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(diagram);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			protected void doExecute() {
				while (iter.hasNext())
					Graphiti.getPeService().deletePictogramElement(iter.next());
				Collection<Connection> cns = diagram.getConnections();
				Iterator<Connection> itera = cns.iterator();
				Set<Connection> toDel = new HashSet<Connection>();
				while (itera.hasNext()) 
					toDel.add(itera.next());
				itera = toDel.iterator();
				while (itera.hasNext())
					Graphiti.getPeService().deletePictogramElement(itera.next());		
				
				Enumeration<String> itr = marks.keys();
				
				// create new pictograms
				while (itr.hasMoreElements()) {
					String entityName = itr.nextElement();
					JavaPersistentType jpt = JpaArtifactFactory.instance().getContextPersistentType(proj, entityName);
					if (jpt != null) {
						SizePosition sp = marks.get(entityName);
						AddContext ctx = new AddEntityContext(sp.primaryCollapsed,
								sp.relationCollapsed, sp.basicCollapsed);
						ctx.setNewObject(jpt);
						ctx.setTargetContainer(getDiagram());
						ctx.setWidth(sp.getWidth());
						ctx.setHeight(sp.getHeight());
						ctx.setX(sp.getX());
						ctx.setY(sp.getY());
						AddJPAEntityFeature ft = new AddJPAEntityFeature(getFeatureProvider());
						ft.add(ctx);
					}
				}
			}			
		});
		getDiagramEditor().saveWithoutEntities(new NullProgressMonitor());

		// delete old pictograms
		return false;
	}

	private void removeConnections() {
		Collection<org.eclipse.graphiti.mm.pictograms.Connection> cons = getDiagram().getConnections();
		Iterator<org.eclipse.graphiti.mm.pictograms.Connection> consIt = cons.iterator();
		Collection<org.eclipse.graphiti.mm.pictograms.Connection> allCons = new HashSet<org.eclipse.graphiti.mm.pictograms.Connection>();
		while (consIt.hasNext()) {
			org.eclipse.graphiti.mm.pictograms.Connection con = consIt.next();
			allCons.add(con);
		}
		consIt = allCons.iterator();
		while (consIt.hasNext()) {
			org.eclipse.graphiti.mm.pictograms.Connection con = consIt.next();
	    	RemoveContext ctx = new RemoveContext(con);
	    	RemoveRelationFeature ft = new RemoveRelationFeature(getFeatureProvider());
	    	ft.remove(ctx);			
		}		
	}
	
	public JPADiagramEditor getDiagramEditor() {
		return (JPADiagramEditor)super.getDiagramEditor();
	}    
    
	private JpaProject getTargetJPAProject() {
		return ModelIntegrationUtil.getProjectByDiagram(getDiagram().getName());
	}
	
	public boolean isAlive() {
		return !isDisposed;
	}
	           
	public void dispose() {
		super.dispose();
		setFeatureProvider(null);
		isDisposed = true;
	}
}