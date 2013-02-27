/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2011 SAP AG.
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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.LayoutContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.actions.SelectionDispatchAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.java.OwnableRelationshipMappingAnnotation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.GraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPASolver;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchSite;


public abstract class RefactorEntityFeature extends AbstractCustomFeature {

	protected Set<JavaSpecifiedPersistentAttribute> ats = null;
	protected boolean hasNameAnnotation = false;
	
	public RefactorEntityFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public boolean isAvailable(IContext context) {
    	if (!(context instanceof ICustomContext))
    		return false;
    	ICustomContext ctx = (ICustomContext)context;
    	PictogramElement pe = ctx.getInnerPictogramElement();
    	Object bo = getFeatureProvider().getBusinessObjectForPictogramElement(pe);
    	if (bo instanceof JavaPersistentType) {
    		JavaPersistentType jpt = (JavaPersistentType)bo;
    		ats = JpaArtifactFactory.instance().getRelatedAttributes(jpt);
    		hasNameAnnotation = JpaArtifactFactory.instance().hasNameAnnotation(jpt);
    		return true;
    	}
    	if (pe instanceof Shape) {
    		ContainerShape cs = ((Shape)pe).getContainer();
    		if (cs == null)
    			return false;
     		bo = getFeatureProvider().getBusinessObjectForPictogramElement(cs);
        	if (bo instanceof JavaPersistentType) {
        		JavaPersistentType jpt = (JavaPersistentType)bo;
        		ats = JpaArtifactFactory.instance().getRelatedAttributes(jpt);
        		hasNameAnnotation = JpaArtifactFactory.instance().hasNameAnnotation(jpt);
        		return true;
        	}
    	}    	
		return false;
	}
	
	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}
	
	public void execute(ICustomContext context, SelectionDispatchAction action) {
		PictogramElement pe = context.getInnerPictogramElement();
		final ContainerShape pict = ((Shape)pe).getContainer();
	    final JavaPersistentType jpt = (JavaPersistentType)getBusinessObjectForPictogramElement(pict);
		ICompilationUnit cu = getFeatureProvider().getCompilationUnit(jpt);
		StructuredSelection sel = new StructuredSelection(cu);
		final PersistenceUnit pu = JpaArtifactFactory.instance().getPersistenceUnit(jpt);
		final Semaphore s = new Semaphore(0);
		final JPAProjectListener lsnr = new JPAProjectListener(s);
		jpt.getJpaProject().addCollectionChangeListener(JpaProject.JPA_FILES_COLLECTION, lsnr);
		ShowBusy showBusy = new ShowBusy(s);
		JPASolver.ignoreEvents = true;
		
		try {
			action.run(sel);
		} catch (Exception e) {} 
		BusyIndicator.showWhile(Display.getCurrent(), showBusy);
		jpt.getJpaProject().removeCollectionChangeListener(JpaProject.JPA_FILES_COLLECTION, lsnr);
		JPASolver.ignoreEvents = false;
		final boolean rename = RenameEntityFeature.class.isInstance(this);
		
		if (!showBusy.isMoved()) 
			return;
		
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(pict);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				remapEntity(jpt, pict, pu, rename, lsnr, getFeatureProvider());
			}
		});
	}
	
	public void execute(ICustomContext context, String newName, ICompilationUnit cu, JavaPersistentType originalJPT) {
		final Shape pict = (Shape)getFeatureProvider().getPictogramElementForBusinessObject(originalJPT);
		final JavaPersistentType jpt = (JavaPersistentType)getFeatureProvider().
									getBusinessObjectForPictogramElement(pict);		
		final PersistenceUnit pu = JpaArtifactFactory.instance().getPersistenceUnit(jpt);
		final Semaphore s = new Semaphore(0);
		final JPAProjectListener lsnr = new JPAProjectListener(s);
		jpt.getJpaProject().addCollectionChangeListener(JpaProject.JPA_FILES_COLLECTION, lsnr);
		ShowBusy showBusy = new ShowBusy(s);
		JPASolver.ignoreEvents = true;
		JpaArtifactFactory.instance().renameEntityClass(jpt, newName, getFeatureProvider());		
		BusyIndicator.showWhile(Display.getCurrent(), showBusy);
		jpt.getJpaProject().removeCollectionChangeListener(JpaProject.JPA_FILES_COLLECTION, lsnr);
		JPASolver.ignoreEvents = false;		
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(pict);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				remapEntity(jpt, pict, pu, true, lsnr, getFeatureProvider());
			}
		});
	}
	
	public static void remapEntity(final JavaPersistentType oldJPT,
								   final Shape pict,
								   final PersistenceUnit pu,
								   final boolean rename,
								   final JPAProjectListener lsnr,
								   final IJPAEditorFeatureProvider fp) {
		BusyIndicator.showWhile(Display.getCurrent(), new Runnable() {
			public void run() {
				fp.getDiagramTypeProvider().getDiagramEditor().selectPictogramElements(new PictogramElement[] {});				 

				String newJPTName = lsnr.getNewJPTName();
				JavaPersistentType newJPT = JpaArtifactFactory.instance().getJPT(newJPTName, pu);
					
				if(newJPT == null)
					return;
				
				if (!JpaPreferences.getDiscoverAnnotatedClasses(newJPT.getJpaProject().getProject())) {
					JPAEditorUtil.createUnregisterEntityFromXMLJob(newJPT.getJpaProject(), oldJPT.getName());
					JPAEditorUtil.createRegisterEntityInXMLJob(newJPT.getJpaProject(), newJPTName);
				}
				
				if (rename) {
					String tableName = JPAEditorUtil.formTableName(newJPT);
					JpaArtifactFactory.instance().setTableName(newJPT, tableName);
				}
				
				GraphicsUpdater.updateHeader((ContainerShape)pict, newJPT.getSimpleName());
				linkNewElement(oldJPT, pict, fp, newJPT);
				
				for(JavaSpecifiedPersistentAttribute oldAttr : oldJPT.getAttributes()){
					PictogramElement attrPict = fp.getPictogramElementForBusinessObject(oldAttr);
					if(attrPict != null){
						for(JavaSpecifiedPersistentAttribute newAttr : newJPT.getAttributes()){
							if(newAttr.getName().equals(oldAttr.getName())){
								linkNewElement(oldAttr, attrPict, fp, newAttr);
							}
						}
					}
				}

				fp.getDiagramTypeProvider().getDiagramEditor().setPictogramElementForSelection(pict);
				
				IWorkbenchSite ws = ((IEditorPart)fp.getDiagramTypeProvider().getDiagramEditor()).getSite();
		        ICompilationUnit cu = fp.getCompilationUnit(newJPT);
		        fp.getJPAEditorUtil().formatCode(cu, ws);
			}

			private void linkNewElement(Object oldBO, PictogramElement pict,
					IJPAEditorFeatureProvider fp, Object newBO) {
				fp.link((ContainerShape)pict, newBO);
				LayoutContext context = new LayoutContext((ContainerShape)pict);
				fp.layoutIfPossible(context);
				
				String oldBoKey = fp.getKeyForBusinessObject(oldBO);
				if(oldBoKey != null){
					fp.remove(oldBoKey);
				}
				String newBoKey = fp.getKeyForBusinessObject(newBO);
				if (fp.getBusinessObjectForKey(newBoKey) == null) {
					fp.putKeyToBusinessObject(newBoKey, newBO);
				}
			}
		});
		
	}

	@Override
	protected Diagram getDiagram() {
		return getFeatureProvider().getDiagramTypeProvider().getDiagram();
	}	
	
	@Override
	public IJPAEditorFeatureProvider getFeatureProvider() {
		return (IJPAEditorFeatureProvider)super.getFeatureProvider();
	}
	
	class ShowBusy implements Runnable {
		private Semaphore s;
		boolean moved = false;
		ShowBusy(Semaphore s) {
			this.s = s;
		}
		
		public void run() {
			try {
				moved = s.tryAcquire(2, 4, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				JPADiagramEditorPlugin.logError("Thread interrupted", e);  //$NON-NLS-1$		 							
			}
		}

		boolean isMoved() {
			return moved;
		}		
	}
	
	public class JPAProjectListener implements CollectionChangeListener {
		private Semaphore s = null;
		private String newJptName = null;
		
		public JPAProjectListener(Semaphore s) {
			this.s = s;
		}
						
		public void itemsAdded(CollectionAddEvent event) {
			Iterator<?> it = event.getItems().iterator();
			Object o = it.next();
			JpaFile jpaFile = (JpaFile)o;
			
			JptResourceModel rm = jpaFile.getResourceModel();
			if (rm == null)
				return;
			if (!JavaResourceCompilationUnit.class.isInstance(rm))
				return;
			JavaResourceCompilationUnit jrcu = (JavaResourceCompilationUnit)rm;
			IType type = jrcu.getCompilationUnit().findPrimaryType();
			newJptName = type.getFullyQualifiedName();
			s.release();
			if ((ats == null) || hasNameAnnotation)
				return;
			final Iterator<JavaSpecifiedPersistentAttribute> iter = ats.iterator();
			Runnable r = new Runnable() {
				public void run() {
					Hashtable<String, String> atOldToNewName = new Hashtable<String, String>();
					Set<JavaSpecifiedPersistentAttribute> newSelfAts = new HashSet<JavaSpecifiedPersistentAttribute>();
					while (iter.hasNext()) {
						JavaSpecifiedPersistentAttribute at = iter.next();
						JavaPersistentType atParent = (JavaPersistentType) at.getParent();
						ICompilationUnit cu = getFeatureProvider().getCompilationUnit(atParent);
						if (!cu.exists()) {
							at = (JavaSpecifiedPersistentAttribute)at.getPersistenceUnit().getPersistentType(newJptName).getAttributeNamed(at.getName());
							JavaSpecifiedPersistentAttribute newAt = null;
							try {
								newAt = JpaArtifactFactory.instance().renameAttribute(atParent, at.getName(), JPAEditorUtil.returnSimpleName(newJptName), newJptName, getFeatureProvider());
							} catch (InterruptedException e) {
								JPADiagramEditorPlugin.logError(e);
							}
							atOldToNewName.put(at.getName(), newAt.getName());
							newSelfAts.add(newAt);
						} else {
							try {
								JpaArtifactFactory.instance().renameAttribute(atParent, at.getName(), JPAEditorUtil.returnSimpleName(newJptName), newJptName, getFeatureProvider());
							} catch (InterruptedException e) {
								JPADiagramEditorPlugin.logError(e);
							}
						}
					}
					Iterator<JavaSpecifiedPersistentAttribute> itr =  newSelfAts.iterator();
					while (itr.hasNext()) {
						JavaSpecifiedPersistentAttribute at = itr.next();
						JavaAttributeMapping m = at.getMapping();
						Annotation mappingAnnotation = m.getMappingAnnotation();
						if (mappingAnnotation == null)
							return;
						if (OwnableRelationshipMappingAnnotation.class.isInstance(mappingAnnotation)) {
							OwnableRelationshipMappingAnnotation ownableMappingAnnotation = (OwnableRelationshipMappingAnnotation)mappingAnnotation;
							String oldMappedBy = ownableMappingAnnotation.getMappedBy();
							if (oldMappedBy != null) {
								String newMappedBy = atOldToNewName.get(oldMappedBy);
								if (newMappedBy != null)
									ownableMappingAnnotation.setMappedBy(newMappedBy);
							}
						}
					}
					
				}				
			};
			Display.getDefault().asyncExec(r);
		}

		public void itemsRemoved(CollectionRemoveEvent arg0) {
			s.release();
		}

		public void collectionChanged(CollectionChangeEvent event) {
		}
		
		public void collectionCleared(CollectionClearEvent arg0) {}
		
		public String getNewJPTName() {
			return newJptName;
		}

	}

}
