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
import org.eclipse.graphiti.ui.editor.IDiagramContainerUI;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.actions.SelectionDispatchAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedMappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.GraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPASolver;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchSite;


public abstract class RefactorEntityFeature extends AbstractCustomFeature {

	protected Set<PersistentAttribute> ats = null;
	protected boolean hasEntitySpecifiedName = false;
	private static final String REGEX_PATTERN = "(_[\\d]+)*"; //$NON-NLS-1$
	
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
    	if (bo instanceof PersistentType) {
    		PersistentType jpt = (PersistentType)bo;
    		ats = JpaArtifactFactory.instance().getRelatedAttributes(jpt);
    		hasEntitySpecifiedName = JpaArtifactFactory.instance().hasEntitySpecifiedName(jpt);
    		return true;
    	}
    	if (pe instanceof Shape) {
    		ContainerShape cs = ((Shape)pe).getContainer();
    		if (cs == null)
    			return false;
     		bo = getFeatureProvider().getBusinessObjectForPictogramElement(cs);
        	if (bo instanceof PersistentType) {
        		PersistentType jpt = (PersistentType)bo;
        		ats = JpaArtifactFactory.instance().getRelatedAttributes(jpt);
        		hasEntitySpecifiedName = JpaArtifactFactory.instance().hasEntitySpecifiedName(jpt);
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
	    final PersistentType jpt = (PersistentType)getBusinessObjectForPictogramElement(pict);
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
	
	public void execute(ICustomContext context, String newName, ICompilationUnit cu, PersistentType originalJPT) {
		final Shape pict = (Shape)getFeatureProvider().getPictogramElementForBusinessObject(originalJPT);
		final PersistentType jpt = (PersistentType)getFeatureProvider().
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
	
	public void remapEntity(final PersistentType oldJPT,
								   final Shape pict,
								   final PersistenceUnit pu,
								   final boolean rename,
								   final JPAProjectListener lsnr,
								   final IJPAEditorFeatureProvider fp) {
		BusyIndicator.showWhile(Display.getCurrent(), new Runnable() {
			public void run() {
				fp.getDiagramTypeProvider().getDiagramBehavior().getDiagramContainer().selectPictogramElements(new PictogramElement[] {});				 

				String newJPTName = lsnr.getNewJPTName();
				lsnr.setOldJptName(oldJPT.getSimpleName());
				
				if (!JpaPreferences.getDiscoverAnnotatedClasses(oldJPT.getJpaProject().getProject())) {
					JPAEditorUtil.createUnregisterEntityFromXMLJob(oldJPT.getJpaProject(), oldJPT.getName());
					JPAEditorUtil.createRegisterEntityInXMLJob(oldJPT.getJpaProject(), newJPTName);
				}
				
				PersistentType newJPT = JpaArtifactFactory.instance().getJPT(newJPTName, pu);
					
				if(newJPT == null)
					return;
				
//				if (!JpaPreferences.getDiscoverAnnotatedClasses(oldJPT.getJpaProject().getProject())) {
//					JPAEditorUtil.createUnregisterEntityFromXMLJob(oldJPT.getJpaProject(), oldJPT.getName());
//					JPAEditorUtil.createRegisterEntityInXMLJob(oldJPT.getJpaProject(), newJPTName);
//				}
				
				if (rename) {
					String tableName = JPAEditorUtil.formTableName(newJPT);
					JpaArtifactFactory.instance().setTableName(newJPT, tableName);
				}
				
				GraphicsUpdater.updateHeader((ContainerShape)pict, newJPT.getSimpleName());
				linkNewElement(oldJPT, pict, fp, newJPT);

				for(PersistentAttribute oldAttr : oldJPT.getAttributes()){
					PictogramElement attrPict = fp.getPictogramElementForBusinessObject(oldAttr);
					if(attrPict != null){
						for(PersistentAttribute newAttr : newJPT.getAttributes()){
							if(newAttr.getName().equals(oldAttr.getName())){
								linkNewElement(oldAttr, attrPict, fp, newAttr);
							}
						}
					}
				}

				fp.getDiagramTypeProvider().getDiagramBehavior().getDiagramContainer().setPictogramElementForSelection(pict);
				
				IWorkbenchSite ws = ((IDiagramContainerUI)fp.getDiagramTypeProvider().getDiagramBehavior().getDiagramContainer()).getSite();
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
		private String oldJptName = null;
		
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
			if ((ats == null) || hasEntitySpecifiedName)
				return;

			final Iterator<PersistentAttribute> iter = ats.iterator();
			Runnable r = new Runnable() {
				public void run() {
					Hashtable<String, String> atOldToNewName = new Hashtable<String, String>();
					Set<PersistentAttribute> newSelfAts = new HashSet<PersistentAttribute>();
					while (iter.hasNext()) {
						PersistentAttribute at = iter.next();
						
						String attributeNamPattern = JPAEditorUtil.decapitalizeFirstLetter(oldJptName) + REGEX_PATTERN;
						boolean isSame = at.getName().matches(attributeNamPattern);
						if(!isSame)
							continue;
						
						PersistentType atParent = (PersistentType) at.getParent();
						ICompilationUnit cu = getFeatureProvider().getCompilationUnit(atParent);
						if (!cu.exists()) {
							at = at.getPersistenceUnit().getPersistentType(newJptName).getAttributeNamed(at.getName());
							try {
								PersistentAttribute newAt = JpaArtifactFactory.instance().renameAttribute(atParent, at.getName(), JPAEditorUtil.returnSimpleName(newJptName), newJptName, getFeatureProvider());
								if(newAt != null) {
									atOldToNewName.put(at.getName(), newAt.getName());
									newSelfAts.add(newAt);
								}
							} catch (InterruptedException e) {
								JPADiagramEditorPlugin.logError(e);
							}
						} else {
							try {
								JpaArtifactFactory.instance().renameAttribute(atParent, at.getName(), JPAEditorUtil.returnSimpleName(newJptName), newJptName, getFeatureProvider());
							} catch (InterruptedException e) {
								JPADiagramEditorPlugin.logError(e);
							}
						}
					}

					Iterator<PersistentAttribute> itr =  newSelfAts.iterator();
					while (itr.hasNext()) {
						PersistentAttribute at = itr.next();
						AttributeMapping m = JpaArtifactFactory.instance().getAttributeMapping(at);
						if(m instanceof RelationshipMapping){
							SpecifiedMappedByRelationshipStrategy mappedByRelationShipStrategy = (SpecifiedMappedByRelationshipStrategy)((RelationshipMapping)m).getRelationship().getStrategy();
							String oldMappedBy = mappedByRelationShipStrategy.getMappedByAttribute();
							if (oldMappedBy != null) {
								String newMappedBy = atOldToNewName.get(oldMappedBy);
								if(newMappedBy != null) {
									mappedByRelationShipStrategy.setMappedByAttribute(newMappedBy);
								}
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

		public void setOldJptName(String oldJptName) {
			this.oldJptName = oldJptName;
		}

	}

}
