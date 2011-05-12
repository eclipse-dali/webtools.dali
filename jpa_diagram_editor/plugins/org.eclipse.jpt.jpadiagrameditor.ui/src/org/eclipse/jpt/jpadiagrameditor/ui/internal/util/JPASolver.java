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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.util;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.WeakHashMap;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.context.impl.RemoveContext;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaNode;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.ManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.MappedByRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.OneToOneMapping;
import org.eclipse.jpt.jpa.core.context.OptionalMapping;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationship;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.RelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditor;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.facade.EclipseFacade;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddAttributeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.RemoveAttributeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.RemoveRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.UpdateAttributeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorDiagramTypeProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.AbstractRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.BidirectionalRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;


@SuppressWarnings("restriction")
public class JPASolver implements IResourceChangeListener, IJpaSolver {

	private static Set<JPASolver> solversSet = new HashSet<JPASolver>();

	private static WorkingCopyChangeListener wclsnr = null;

	private Hashtable<String, Object> keyToBO;
	private WeakHashMap<JpaProject, WeakReference<CollectionChangeListener>> projectToEntityListener;
	private WeakHashMap<JavaPersistentType, WeakReference<PropertyChangeListener>> entityToPropListener;
	private WeakHashMap<JavaPersistentAttribute, WeakReference<AttributePropertyChangeListener>> attributeToPropListener;
	private WeakHashMap<JavaPersistentAttribute, WeakReference<AttributeMappingOptionalityChangeListener>> attributeMappingOptionalityToPropListener;		
	private WeakHashMap<JavaPersistentAttribute, WeakReference<AttributeJoiningStrategyPropertyChangeListener>> attributeJoiningStrategyToPropListener;	
	private WeakHashMap<JavaPersistentAttribute, WeakReference<AttributeRelationshipReferencePropertyChangeListener>> attributeRelationshipReferenceToPropListener;
	private WeakHashMap<JavaPersistentType, WeakReference<ListChangeListener>> entityToAtListener;
	private WeakHashMap<JavaPersistentType, WeakReference<StateChangeListener>> entityToStateListener;
	private EntityChangeListener entityNameListener;
	private IJPAEditorFeatureProvider featureProvider;
	private HashSet<String> removeIgnore = new HashSet<String>();
	private HashSet<String> removeRelIgnore = new HashSet<String>();

	private HashSet<String> addIgnore = new HashSet<String>();
	private Hashtable<String, IRelation> attribToRel = new Hashtable<String, IRelation>();
	private static final String SEPARATOR = "-"; //$NON-NLS-1$

	private IEclipseFacade eclipseFacade;
	private IJPAEditorUtil util = null;

	/**
	 * Provides the unique key for the given business object.
	 * 
	 * @param bo
	 *            the given business object
	 * 
	 * @return unique key
	 */
	public JPASolver() {
		this(EclipseFacade.INSTANCE, new JPAEditorUtilImpl());
		synchronized (JPASolver.class) {
			if (wclsnr == null) {
				wclsnr = new WorkingCopyChangeListener();
				JavaCore.addElementChangedListener(wclsnr, ElementChangedEvent.POST_CHANGE | ElementChangedEvent.POST_RECONCILE);
			}
		}
		solversSet.add(this);
	}
	
	public JPASolver(IEclipseFacade eclipseFacade, IJPAEditorUtil util) {
		this.eclipseFacade = eclipseFacade;
		eclipseFacade.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_BUILD);
		keyToBO = new Hashtable<String, Object>();
		projectToEntityListener = new WeakHashMap<JpaProject, WeakReference<CollectionChangeListener>>();
		entityToPropListener = new WeakHashMap<JavaPersistentType, WeakReference<PropertyChangeListener>>();
		attributeToPropListener = new WeakHashMap<JavaPersistentAttribute, WeakReference<AttributePropertyChangeListener>>();
		attributeMappingOptionalityToPropListener = new WeakHashMap<JavaPersistentAttribute, WeakReference<AttributeMappingOptionalityChangeListener>>();
		attributeJoiningStrategyToPropListener = new WeakHashMap<JavaPersistentAttribute, WeakReference<AttributeJoiningStrategyPropertyChangeListener>>();
		attributeRelationshipReferenceToPropListener = new WeakHashMap<JavaPersistentAttribute, WeakReference<AttributeRelationshipReferencePropertyChangeListener>>(); 
		entityToAtListener = new WeakHashMap<JavaPersistentType, WeakReference<ListChangeListener>>();
		entityToStateListener = new WeakHashMap<JavaPersistentType, WeakReference<StateChangeListener>>();
		entityNameListener = new EntityChangeListener(this);
		entityNameListener.setName("Entity Name Change Listener"); //$NON-NLS-1$
		this.util = util;
	}

	public void resourceChanged(IResourceChangeEvent event) {

		closeDiagramEditorIfProjectIsDeleted(event);
		
		unregisterDeltedEntity(event);
		
		IMarkerDelta[] markerDeltas = event.findMarkerDeltas(null, true);
		Set<IFile> filesToUpdate = new HashSet<IFile>();
		for (IMarkerDelta delta : markerDeltas) {
			if (delta.getResource().getType() != IResource.FILE)
				continue;
			filesToUpdate.add((IFile) delta.getResource());
		}
		
		// update is made to the whole editor. Find if there is at least on pe
		// to be update and break the iteration
		boolean updateEditor = false;
		FILE: for (IFile file : filesToUpdate) {
			for (JavaPersistentType jpt : entityToPropListener.keySet()) {
				if (jpt.getResource().equals(file)) {
					final PictogramElement element = featureProvider.getPictogramElementForBusinessObject(jpt);
					if (element == null)
						break;
					else {
						updateEditor = true;
						break FILE;
					}
				}
			}
		}
		if (updateEditor) {
			eclipseFacade.getDisplay().asyncExec(new Runnable() {
				public void run() {
					featureProvider.getDiagramTypeProvider().getDiagramEditor().refresh();
				}
			});

		}
	}

	public void addRemoveIgnore(String atName) {
		removeIgnore.add(atName);
	}

	public void addRemoveRelIgnore(String atName) {
		removeRelIgnore.add(atName);
	}
		
	public void addJPTForUpdate(String jptName) {
		entityNameListener.addJPTForUpdate(jptName);
	}

	public void addAddIgnore(String atName) {
		addIgnore.add(atName);
	}
	
	public HashSet<String> getAddIgnore(){ 
 		return addIgnore; 
 	} 

	
	public void stopThread() {
		entityNameListener.stopThread();
		entityNameListener = null;
	}

	synchronized public EntityChangeListener getMonitor() {
		if (entityNameListener == null) {
			entityNameListener = new EntityChangeListener(this);
			entityNameListener.setName("Entity Name Change Listener"); //$NON-NLS-1$			
		}
		return entityNameListener;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jpt.jpadiagrameditor.ui.util.IJpaSolver#setFeatureProvider(org.eclipse.jpt.jpadiagrameditor.ui.provider.IJPAEditorFeatureProvider)
	 */
	public void setFeatureProvider(IJPAEditorFeatureProvider featureProvider) {
		this.featureProvider = featureProvider;
		entityNameListener.setFeatureProvider(featureProvider);
		entityNameListener.setDaemon(true);
		entityNameListener.start();
	}

	public String getKeyForBusinessObject(Object bo) {
		if (bo == null)
			return ""; //$NON-NLS-1$
		if (bo instanceof JavaPersistentType) {
			JavaPersistentType jpt = (JavaPersistentType) bo;
			String name = jpt.getName();
			return name;
		} else if (bo instanceof AbstractRelation) {
			return ((AbstractRelation) bo).getId();
		} else if (bo instanceof JavaPersistentAttribute) {
			JavaPersistentAttribute at = (JavaPersistentAttribute) bo;
			return (((PersistentType)at.getParent()).getName() + "-" + at.getName()); //$NON-NLS-1$
		}
		return bo.toString();
	}

	/**
	 * Provides the business object for the given key.
	 * 
	 * @param key
	 *            the unique key
	 * 
	 * @return the business object
	 */
	public Object getBusinessObjectForKey(String key) {
		if (key == null)
			return null;
		return keyToBO.get(key);
	}

	protected String produceOwnerKeyForRel(AbstractRelation rel) {
		return produceKeyForRel(rel.getOwner(), rel.getOwnerAttributeName());
	}

	protected String produceInverseKeyForRel(AbstractRelation rel) {
		return produceKeyForRel(rel.getInverse(), rel.getInverseAttributeName());
	}

	public String produceKeyForRel(JavaPersistentType jpt, String attributeName) {
		return jpt.getName() + SEPARATOR + attributeName;
	}

	public void addKeyBusinessObject(String key, Object bo) {
		keyToBO.put(key, bo);
		if (bo instanceof JavaPersistentType) {
			JavaPersistentType jpt = (JavaPersistentType) bo;
			JpaProject proj = jpt.getJpaProject();
			addListenersToProject(proj);
			addListenersToEntity(jpt);
			PictogramElement pe = featureProvider.getPictogramElementForBusinessObject(jpt);
			Graphiti.getPeService().setPropertyValue(pe, JPAEditorConstants.PROP_ENTITY_CLASS_NAME, jpt.getName());
		} else if (bo instanceof AbstractRelation) {
			AbstractRelation rel = (AbstractRelation) bo;
			attribToRel.put(produceOwnerKeyForRel(rel), rel);
			if (rel instanceof BidirectionalRelation) {
				attribToRel.put(produceInverseKeyForRel(rel), rel);
			}
		} else if (bo instanceof JavaPersistentAttribute) {
			addPropertiesListenerToAttribute((JavaPersistentAttribute)bo);
		}
	}

	public Object remove(String key) {
		if (key == null)
			return null;
		Object o = keyToBO.remove(key);
		if (o instanceof JavaPersistentType) {
			JavaPersistentType jpt = (JavaPersistentType) o;
			ListIterator<JavaPersistentAttribute> atts = jpt.attributes();
			while (atts.hasNext()) {
				JavaPersistentAttribute at = atts.next();
				String k = getKeyForBusinessObject(at);
				remove(k);
			}
			
			removeListenersFromEntity(jpt);
			Diagram d = featureProvider.getDiagramTypeProvider().getDiagram();
			if (d.getChildren().size() == 1) {
				WeakReference<CollectionChangeListener> ref = projectToEntityListener.remove(jpt.getJpaProject());
				if (ref != null) {
					CollectionChangeListener ch = ref.get();
					if (ch != null) 
						jpt.getJpaProject().removeCollectionChangeListener(JpaProject.JPA_FILES_COLLECTION, ch);
				}
			}
			
			ICompilationUnit cu = getCompilationUnit(jpt);
			JPAEditorUtil.discardWorkingCopyOnce(cu);
		} else if (o instanceof AbstractRelation) {
			AbstractRelation rel = (AbstractRelation) o;
			attribToRel.remove(produceOwnerKeyForRel(rel));
			if (rel instanceof BidirectionalRelation) 
				attribToRel.remove(produceInverseKeyForRel(rel));
		} else if (o instanceof JavaPersistentAttribute) {
			removeListenersFromAttribute((JavaPersistentAttribute)o);
		}
		return o;
	}

	public ICompilationUnit getCompilationUnit(JavaPersistentType jpt) {
		return util.getCompilationUnit(jpt);
	}

	public boolean isRelationRelatedToAttribute(JavaPersistentAttribute jpa) {
		String key = produceKeyForRel((JavaPersistentType)jpa.getParent(), jpa.getName());
		return attribToRel.containsKey(key);
	}

	public IRelation getRelationRelatedToAttribute(JavaPersistentAttribute jpa) {
		String key = produceKeyForRel((JavaPersistentType)jpa.getParent(), jpa.getName());
		return attribToRel.get(key);
	}

	public Set<IRelation> getRelationsRelatedToEntity(JavaPersistentType jpt) {
		ListIterator<JavaPersistentAttribute> it = jpt.attributes();
		HashSet<IRelation> res = new HashSet<IRelation>();
		while (it.hasNext()) {
			JavaPersistentAttribute at = it.next();
			IRelation rel = getRelationRelatedToAttribute(at);
			if (rel != null)
				res.add(rel);
		}
		return res;
	}

	
	public boolean existsRelation(JavaPersistentType jpt1, JavaPersistentType jpt2) {
		Set<IRelation> rels = getRelationsRelatedToEntity(jpt1);
		if (existsRelation(jpt1, jpt2, rels))
			return true;		
		rels = getRelationsRelatedToEntity(jpt2);
		return existsRelation(jpt1, jpt2, rels);
	}
	
	public boolean existsRelation(JavaPersistentType jpt1, 
									  JavaPersistentType jpt2,
									  Set<IRelation> rels) {
		Iterator<IRelation> it = rels.iterator();
		while (it.hasNext()) {
			IRelation rel = it.next();
			if ((jpt1.equals(rel.getOwner()) && jpt2.equals(rel.getInverse())) ||
				((jpt2.equals(rel.getOwner()) && jpt1.equals(rel.getInverse()))))
					return true;
		}
		return false;
	}

	public Collection<Object> getVisualizedObjects() {
		return keyToBO.values();
	}	
	
	public void renewAttributeMappingPropListener(JavaPersistentAttribute jpa) {
		renewAttributeJoiningStrategyPropertyListener(jpa);
		renewAttributeMappingOptPropListener(jpa);
	}
	
	public void renewAttributeJoiningStrategyPropertyListener(JavaPersistentAttribute jpa) {
		AttributeJoiningStrategyPropertyChangeListener lsn = null;
		WeakReference<AttributeJoiningStrategyPropertyChangeListener> ref = attributeJoiningStrategyToPropListener.remove(jpa);
		if (ref != null)
			lsn = ref.get();
		
		JavaAttributeMapping jam = jpa.getMapping();
		if ((jam == null) || !RelationshipMapping.class.isInstance(jam))
			return;
		Relationship rr = ((RelationshipMapping) jam).getRelationship();
		if (rr == null)
			return;			
		RelationshipStrategy js = rr.getStrategy();
		if ((js == null) || !MappedByRelationshipStrategy.class.isInstance(js))
			return;
		try {
			if (lsn != null) 
				js.removePropertyChangeListener(MappedByRelationshipStrategy.MAPPED_BY_ATTRIBUTE_PROPERTY, lsn);
		} catch (Exception e) {
			//$NON-NLS-1$
		}
		lsn = new AttributeJoiningStrategyPropertyChangeListener();
		js.addPropertyChangeListener(MappedByRelationshipStrategy.MAPPED_BY_ATTRIBUTE_PROPERTY, lsn);
		ref = new WeakReference<AttributeJoiningStrategyPropertyChangeListener>(lsn);
		attributeJoiningStrategyToPropListener.put(jpa, ref);
		
	}
	
	public void renewAttributeMappingOptPropListener(JavaPersistentAttribute jpa) {
		AttributeMappingOptionalityChangeListener lsn = null;
		WeakReference<AttributeMappingOptionalityChangeListener> ref = attributeMappingOptionalityToPropListener.remove(jpa);
		if (ref != null)
			lsn = ref.get();	
		JavaAttributeMapping jam = jpa.getMapping();
		if (jam == null)
			return;
		if (!ManyToOneMapping.class.isInstance(jam) && !OneToOneMapping.class.isInstance(jam))
			return;
		
		try {
		if (lsn != null) 
			jam.removePropertyChangeListener(OptionalMapping.SPECIFIED_OPTIONAL_PROPERTY, lsn);
		} catch (Exception e) {
			//$NON-NLS-1$
		}
		lsn = new AttributeMappingOptionalityChangeListener();
		jam.addPropertyChangeListener(OptionalMapping.SPECIFIED_OPTIONAL_PROPERTY, lsn);
		ref = new WeakReference<AttributeMappingOptionalityChangeListener>(lsn);
		attributeMappingOptionalityToPropListener.put(jpa, ref);
	}
	

	private void addListenersToProject(JpaProject proj) {
		addEntitiesListenerToProject(proj);
	}

	private void addEntitiesListenerToProject(JpaProject proj) {
		WeakReference<CollectionChangeListener> lsnrRef = projectToEntityListener.get(proj);
		CollectionChangeListener lsnr = null;
		if (lsnrRef != null)
			lsnr = lsnrRef.get();
		if (lsnr == null) {
			/*
			if (!proj.getUpdater().getClass().isInstance(SynchronousJpaProjectUpdater.class))
				proj.setUpdater(new SynchronousJpaProjectUpdater(proj));
			*/
			lsnr = new JPAProjectListener();
			proj.addCollectionChangeListener(JpaProject.JPA_FILES_COLLECTION, lsnr);
			lsnrRef = new WeakReference<CollectionChangeListener>(lsnr);
			projectToEntityListener.put(proj, lsnrRef);
		}
	}

	private void addListenersToEntity(JavaPersistentType jpt) {
		addAtListenerToEntity(jpt);
		addPropertiesListenerToEntity(jpt);
		addStateListenerToEntity(jpt);
	}

	private void addAtListenerToEntity(JavaPersistentType jpt) {
		WeakReference<ListChangeListener> lsnrRef = entityToAtListener.get(jpt);
		ListChangeListener lsnr = null;
		if (lsnrRef != null)
			lsnr = lsnrRef.get();
		if (lsnr == null) {
			lsnr = new EntityAttributesChangeListener();
			jpt.addListChangeListener(JavaPersistentType.ATTRIBUTES_LIST, lsnr);
			lsnrRef = new WeakReference<ListChangeListener>(lsnr);
			entityToAtListener.put(jpt, lsnrRef);
		}
	}

	private void addPropertiesListenerToEntity(JavaPersistentType jpt) {
		WeakReference<PropertyChangeListener> lsnrRef = entityToPropListener.get(jpt);
		PropertyChangeListener lsnr = null;
		if (lsnrRef != null)
			lsnr = lsnrRef.get();
		if (lsnr == null) {
			lsnr = new EntityPropertyChangeListener();
			jpt.getMapping().addPropertyChangeListener(PersistentType.NAME_PROPERTY, lsnr);
			lsnrRef = new WeakReference<PropertyChangeListener>(lsnr);
			entityToPropListener.put(jpt, lsnrRef);
		}
	}

	private void addPropertiesListenerToAttribute(JavaPersistentAttribute jpa) {
		addPropertiesListenerToAttributeItself(jpa);
		addPropertiesListenerToJoiningStrategy(jpa);		
		addPropertiesListenerToRelationshipReference(jpa);
		addOptPropListenerToAttributeMapping(jpa);			
	}
	
	private void addPropertiesListenerToAttributeItself(JavaPersistentAttribute jpa) {
		WeakReference<AttributePropertyChangeListener> lsnrRef = attributeToPropListener.get(jpa);
		AttributePropertyChangeListener lsnr = null;
		if (lsnrRef != null)
			lsnr = lsnrRef.get();
		if (lsnr == null) {
			lsnr = new AttributePropertyChangeListener();
			jpa.addPropertyChangeListener(PersistentAttribute.MAPPING_PROPERTY, lsnr);
			lsnrRef = new WeakReference<AttributePropertyChangeListener>(lsnr);
			attributeToPropListener.put(jpa, lsnrRef);
		}				
	}
	
	private void addOptPropListenerToAttributeMapping(JavaPersistentAttribute jpa) {
		WeakReference<AttributeMappingOptionalityChangeListener> lsnrRef = attributeMappingOptionalityToPropListener.get(jpa);
		AttributeMappingOptionalityChangeListener lsnr = null;
		if (lsnrRef != null)
			lsnr = lsnrRef.get();
		if (lsnr == null) {
			lsnr = new AttributeMappingOptionalityChangeListener();
			JavaAttributeMapping jam = jpa.getMapping();
			if (jam == null)
				return;
			if (!JavaManyToOneMapping.class.isInstance(jam) &&
					!JavaOneToOneMapping.class.isInstance(jam))
				return;
			jam.addPropertyChangeListener(OptionalMapping.SPECIFIED_OPTIONAL_PROPERTY, lsnr);
			lsnrRef = new WeakReference<AttributeMappingOptionalityChangeListener>(lsnr);
			attributeMappingOptionalityToPropListener.put(jpa, lsnrRef);
		}
	}
	
	
	private void addPropertiesListenerToJoiningStrategy(JavaPersistentAttribute jpa) {
		
		WeakReference<AttributeJoiningStrategyPropertyChangeListener> lsnrRef = attributeJoiningStrategyToPropListener.get(jpa);
		AttributeJoiningStrategyPropertyChangeListener lsnr = null;
		lsnrRef = attributeJoiningStrategyToPropListener.get(jpa);
		lsnr = null;
		if (lsnrRef != null)
			lsnr = lsnrRef.get();
		if (lsnr == null) {
			lsnr = new AttributeJoiningStrategyPropertyChangeListener();
			JavaAttributeMapping jam = jpa.getMapping();
			if ((jam == null) || !RelationshipMapping.class.isInstance(jam))
				return;
			Relationship rr = ((RelationshipMapping) jam).getRelationship();
			if (rr == null)
				return;			
			RelationshipStrategy js = rr.getStrategy();
			if ((js == null) || !MappedByRelationshipStrategy.class.isInstance(js))
				return;
			lsnrRef = new WeakReference<AttributeJoiningStrategyPropertyChangeListener>(lsnr);
			attributeJoiningStrategyToPropListener.put(jpa, lsnrRef);
		}
		
	}
	
	
	private void addPropertiesListenerToRelationshipReference(JavaPersistentAttribute jpa) {
		
		WeakReference<AttributeRelationshipReferencePropertyChangeListener> lsnrRef = attributeRelationshipReferenceToPropListener.get(jpa);
		AttributeRelationshipReferencePropertyChangeListener lsnr = null;
		lsnrRef = attributeRelationshipReferenceToPropListener.get(jpa);
		lsnr = null;
		if (lsnrRef != null)
			lsnr = lsnrRef.get();
		if (lsnr == null) {
			lsnr = new AttributeRelationshipReferencePropertyChangeListener();
			JavaAttributeMapping jam = jpa.getMapping();
			if ((jam == null) || !RelationshipMapping.class.isInstance(jam))
				return;
			Relationship rr = ((RelationshipMapping) jam).getRelationship();
			if (rr == null)
				return;		
			rr.addPropertyChangeListener(ReadOnlyRelationship.STRATEGY_PROPERTY, lsnr);
			rr.addPropertyChangeListener(OptionalMapping.SPECIFIED_OPTIONAL_PROPERTY, new AttributeMappingOptionalityChangeListener());
			lsnrRef = new WeakReference<AttributeRelationshipReferencePropertyChangeListener>(lsnr);
			attributeRelationshipReferenceToPropListener.put(jpa, lsnrRef);
		}
		
	}
	

	
	private void addStateListenerToEntity(JavaPersistentType jpt) {
		WeakReference<StateChangeListener> lsnrRef = entityToStateListener.get(jpt);
		StateChangeListener lsnr = null;
		if (lsnrRef != null)
			lsnr = lsnrRef.get();
		if (lsnr == null) {
			lsnr = new EntityStateChangeListener();
			jpt.addStateChangeListener(lsnr);
			lsnrRef = new WeakReference<StateChangeListener>(lsnr);
			entityToStateListener.put(jpt, lsnrRef);
		}
	}

	private void removeListenersFromEntity(JavaPersistentType jpt) {
		removeAtListenerFromEntity(jpt);
		removePropListenerFromEntity(jpt);
		removeStateListenerFromEntity(jpt);
	}
	
	private void removeListenersFromAttribute(JavaPersistentAttribute jpa) {
		removePropListenerFromAttribute(jpa);
	}	

	private void removeAtListenerFromEntity(JavaPersistentType jpt) {
		WeakReference<ListChangeListener> lsnrRef = entityToAtListener.get(jpt);
		ListChangeListener lsnr = null;
		if (lsnrRef != null)
			lsnr = lsnrRef.get();
		if (lsnr != null) {
			entityToAtListener.remove(jpt);
			jpt.removeListChangeListener(JavaPersistentType.ATTRIBUTES_LIST, lsnr);			
		}
	}

	private void removePropListenerFromEntity(JavaPersistentType jpt) {
		WeakReference<PropertyChangeListener> lsnrRef = entityToPropListener.get(jpt);
		PropertyChangeListener lsnr = null;
		if (lsnrRef != null)
			lsnr = lsnrRef.get();
		if (lsnr != null) {
			entityToPropListener.remove(jpt);
			try {
				jpt.getMapping().removePropertyChangeListener(PersistentType.NAME_PROPERTY, lsnr);				
			} catch (IllegalArgumentException e) {
				//$NON-NLS-1$
			}		
		}
	}
		
	private void removePropListenerFromAttribute(JavaPersistentAttribute jpa) {
		removePropListenerFromAttributeItself(jpa);
		removePropListenerFromJoiningStrategy(jpa);
		removePropListenerFromRelationshipReference(jpa);
		removeOptPropListenerFromAttributeMapping(jpa);
	}	
	
	private void removePropListenerFromAttributeItself(JavaPersistentAttribute jpa) {
		WeakReference<AttributePropertyChangeListener> lsnrRef = attributeToPropListener.get(jpa);
		PropertyChangeListener lsnr = null;
		if (lsnrRef != null)
			lsnr = lsnrRef.get();
		if (lsnr != null) {
			attributeToPropListener.remove(jpa);
			try {
				jpa.removePropertyChangeListener(PersistentAttribute.MAPPING_PROPERTY, lsnr);				
			} catch (IllegalArgumentException e) {
				//$NON-NLS-1$
			}		
		}
	}	
	
	private void removePropListenerFromJoiningStrategy(JavaPersistentAttribute jpa) {
		WeakReference<AttributeJoiningStrategyPropertyChangeListener> lsnrRef = attributeJoiningStrategyToPropListener.get(jpa);
		PropertyChangeListener lsnr = null;		
		lsnrRef = attributeJoiningStrategyToPropListener.get(jpa);
		lsnr = null;
		if (lsnrRef != null)
			lsnr = lsnrRef.get();
		if (lsnr != null) {
			attributeJoiningStrategyToPropListener.remove(jpa);
			try {			
				JavaAttributeMapping jam = jpa.getMapping();
				if ((jam == null) || !RelationshipMapping.class.isInstance(jam))
					return;
				Relationship rr = ((RelationshipMapping) jam).getRelationship();
				if (rr == null)
					return;			
				RelationshipStrategy js = rr.getStrategy();
				if ((js == null) || !MappedByRelationshipStrategy.class.isInstance(js))
					return;
				js.removePropertyChangeListener(MappedByRelationshipStrategy.MAPPED_BY_ATTRIBUTE_PROPERTY, lsnr);							
			} catch (IllegalArgumentException e) {
				//$NON-NLS-1$
			}		
		}		
		
	}
	
	private void removeOptPropListenerFromAttributeMapping(JavaPersistentAttribute jpa) {
		WeakReference<AttributeMappingOptionalityChangeListener> lsnrRef = attributeMappingOptionalityToPropListener.get(jpa);
		PropertyChangeListener lsnr = null;		
		lsnrRef = attributeMappingOptionalityToPropListener.get(jpa);
		lsnr = null;
		if (lsnrRef != null)
			lsnr = lsnrRef.get();
		if (lsnr != null) {
			attributeMappingOptionalityToPropListener.remove(jpa);
			try {			
				JavaAttributeMapping jam = jpa.getMapping();
				if ((jam == null) || !RelationshipMapping.class.isInstance(jam))
					return;
				jam.removePropertyChangeListener(OptionalMapping.SPECIFIED_OPTIONAL_PROPERTY, lsnr);
			} catch (IllegalArgumentException e) {
				//$NON-NLS-1$
			}		
		}		
	}	
	
	
	private void removePropListenerFromRelationshipReference(JavaPersistentAttribute jpa) {
		WeakReference<AttributeRelationshipReferencePropertyChangeListener> lsnrRef = attributeRelationshipReferenceToPropListener.get(jpa);
		PropertyChangeListener lsnr = null;		
		lsnrRef = attributeRelationshipReferenceToPropListener.get(jpa);
		lsnr = null;
		if (lsnrRef != null)
			lsnr = lsnrRef.get();
		if (lsnr != null) {
			attributeRelationshipReferenceToPropListener.remove(jpa);
			try {			
				JavaAttributeMapping jam = jpa.getMapping();
				if ((jam == null) || !RelationshipMapping.class.isInstance(jam))
					return;
				Relationship rr = ((RelationshipMapping) jam).getRelationship();
				if (rr == null)
					return;			
				rr.removePropertyChangeListener(ReadOnlyRelationship.STRATEGY_PROPERTY, lsnr);
			} catch (IllegalArgumentException e) {
				//$NON-NLS-1$
			}		
		}				
	}
		

	private void removeStateListenerFromEntity(JavaPersistentType jpt) {
		WeakReference<StateChangeListener> lsnrRef = entityToStateListener.get(jpt);
		StateChangeListener lsnr = null;
		if (lsnrRef != null)
			lsnr = lsnrRef.get();
		if (lsnr != null) {
			entityToStateListener.remove(jpt);
			jpt.removeStateChangeListener(lsnr);			
		}
	}

	//---------------
	private void removeEntityStateChangeListeners() {
		Iterator<JavaPersistentType> it = entityToStateListener.keySet().iterator();
		Set<JavaPersistentType> s = new HashSet<JavaPersistentType>();
		while(it.hasNext()) 
			s.add(it.next());
		it = s.iterator();
		while(it.hasNext()) {
			JavaPersistentType jpt = it.next();
			WeakReference<StateChangeListener> ref = entityToStateListener.remove(jpt);
			StateChangeListener lsn = ref.get();
			if (lsn != null) 
				jpt.removeStateChangeListener(lsn);
		}
		entityToStateListener.clear();
		entityToStateListener = null;
	}
	
	private void removeEntityPropChangeListeners() {
		Iterator<JavaPersistentType> it = entityToPropListener.keySet().iterator();
		Set<JavaPersistentType> s = new HashSet<JavaPersistentType>();
		while(it.hasNext()) 
			s.add(it.next());
		it = s.iterator();		
		while(it.hasNext()) {
			JavaPersistentType jpt = it.next();
			WeakReference<PropertyChangeListener> ref = entityToPropListener.remove(jpt);
			PropertyChangeListener lsn = ref.get();
			if (lsn != null) 
				jpt.getMapping().removePropertyChangeListener(PersistentType.NAME_PROPERTY, lsn);
		}
		entityToPropListener.clear();
		entityToPropListener = null;
	}
	
	private void removeAttributePropChangeListeners() {
		Iterator<JavaPersistentAttribute> it = attributeToPropListener.keySet().iterator();
		Set<JavaPersistentAttribute> s = new HashSet<JavaPersistentAttribute>();
		while(it.hasNext()) 
			s.add(it.next());
		it = s.iterator();		
		while(it.hasNext()) {
			JavaPersistentAttribute jpa = it.next();
			WeakReference<AttributePropertyChangeListener> ref = attributeToPropListener.remove(jpa);
			PropertyChangeListener lsn = ref.get();
			if (lsn != null) 
				try {
					jpa.removePropertyChangeListener(PersistentAttribute.MAPPING_PROPERTY, lsn);
				} catch (IllegalArgumentException e) {
					//$NON-NLS-1$
				}
		}
		attributeToPropListener.clear();
		attributeToPropListener = null;
	}	
	
	private void removeAttributeJoiningStrategyPropChangeListeners() {
		Iterator<JavaPersistentAttribute> it = attributeJoiningStrategyToPropListener.keySet().iterator();
		Set<JavaPersistentAttribute> s = new HashSet<JavaPersistentAttribute>();
		while(it.hasNext()) 
			s.add(it.next());
		it = s.iterator();		
		while(it.hasNext()) {
			JavaPersistentAttribute jpa = it.next();
			WeakReference<AttributeJoiningStrategyPropertyChangeListener> ref = attributeJoiningStrategyToPropListener.remove(jpa);
			PropertyChangeListener lsn = ref.get();
			if (lsn != null) 
				try {
					jpa.getMapping().removePropertyChangeListener(MappedByRelationshipStrategy.MAPPED_BY_ATTRIBUTE_PROPERTY, lsn);
				} catch (IllegalArgumentException e) {
					//$NON-NLS-1$
				}
		}
		attributeJoiningStrategyToPropListener.clear();
		attributeJoiningStrategyToPropListener = null;
	}		
	
	private void removeOptPropListeners() {
		Iterator<JavaPersistentAttribute> it = this.attributeMappingOptionalityToPropListener.keySet().iterator();
		Set<JavaPersistentAttribute> s = new HashSet<JavaPersistentAttribute>();
		while(it.hasNext()) 
			s.add(it.next());
		it = s.iterator();		
		while(it.hasNext()) {
			JavaPersistentAttribute jpa = it.next();
			WeakReference<AttributeMappingOptionalityChangeListener> ref = attributeMappingOptionalityToPropListener.remove(jpa);
			if (ref == null)
				continue;
			PropertyChangeListener lsn = ref.get();
			if (lsn == null)
				continue;
			JavaAttributeMapping jam = jpa.getMapping();
			if ((jam == null) || !RelationshipMapping.class.isInstance(jam))
				continue;			
			try {
				jam.removePropertyChangeListener(OptionalMapping.SPECIFIED_OPTIONAL_PROPERTY, lsn);
			} catch (IllegalArgumentException e) {
				//$NON-NLS-1$
			}
		}
		attributeRelationshipReferenceToPropListener.clear();
		attributeRelationshipReferenceToPropListener = null;		
	}	
	
	private void removeEntityAttributeChangeListeners() {
		Iterator<JavaPersistentType> it = entityToAtListener.keySet().iterator();
		Set<JavaPersistentType> s = new HashSet<JavaPersistentType>();
		while(it.hasNext()) 
			s.add(it.next());
		it = s.iterator();		
		while(it.hasNext()) {
			JavaPersistentType jpt = it.next();
			WeakReference<ListChangeListener> ref = entityToAtListener.remove(jpt);
			ListChangeListener lsn = ref.get();
			if (lsn != null) 
				jpt.removeListChangeListener(JavaPersistentType.ATTRIBUTES_LIST, lsn);
		}
		entityToAtListener.clear();
		entityToAtListener = null;
	}	
	
	private void removeProjectListeners() {		
		Iterator<JpaProject> it = projectToEntityListener.keySet().iterator();
		Set<JpaProject> s = new HashSet<JpaProject>();
		while(it.hasNext()) 
			s.add(it.next());
		it = s.iterator();		
		while(it.hasNext()) {
			JpaProject project = it.next();
			WeakReference<CollectionChangeListener> ref = projectToEntityListener.remove(project);
			CollectionChangeListener lsn = ref.get();
			if (lsn != null) 
				project.removeCollectionChangeListener(JpaProject.JPA_FILES_COLLECTION, lsn);
		}
		projectToEntityListener.clear();
		projectToEntityListener = null;
	}
	
	private void removeAllListeners() {
		removeOptPropListeners();
		removeAttributeJoiningStrategyPropChangeListeners();
		removeAttributePropChangeListeners();
		removeEntityStateChangeListeners();	
		removeEntityPropChangeListeners();
		removeEntityAttributeChangeListeners();
		removeProjectListeners();
		eclipseFacade.getWorkspace().removeResourceChangeListener(this);
	}
	
	public void dispose() {
		Iterator<Object> it = keyToBO.values().iterator();
		while (it.hasNext()) {
			Object bo = it.next();
			if (!JavaPersistentType.class.isInstance(bo))
				continue;
			ICompilationUnit cu = util.getCompilationUnit(((JavaPersistentType)bo));
			util.discardWorkingCopyOnce(cu);
		}
		
		util = null;
		keyToBO.clear();
		attribToRel.clear();
		keyToBO = null;
		attribToRel = null;	
		removeAllListeners();
		featureProvider = null;
		synchronized (JPASolver.class) {
			solversSet.remove(this);
			if (solversSet.isEmpty()) {
				JavaCore.removeElementChangedListener(wclsnr);
				wclsnr = null;
			}
		}
	}	
	
	public boolean containsKey(String key) {
		return keyToBO.containsKey(key);
	}

	public void restoreEntity(JavaPersistentType jpt) {
		if (jpt == null)
			return;
		ICompilationUnit cu = this.getCompilationUnit(jpt);
		JPAEditorUtil.discardWorkingCopyOnce(cu);
		JPAEditorUtil.becomeWorkingCopy(cu);
	}

	public static boolean ignoreEvents = false; 
	
	public static class WorkingCopyChangeListener implements IElementChangedListener {
		synchronized public void  elementChanged(ElementChangedEvent event) {
			Object o = event.getSource();
			if (!IJavaElementDelta.class.isInstance(o))
				return;

			IJavaElementDelta jed = (IJavaElementDelta)o;
			Set<ICompilationUnit> affectedCompilationUnits = getAffectedCompilationUnits(jed);
			
			for (ICompilationUnit cu : affectedCompilationUnits) {
				JavaPersistentType jpt = JPAEditorUtil.getJPType(cu);
				for (JPASolver solver : solversSet) {
					final ContainerShape cs = (ContainerShape)solver.featureProvider.getPictogramElementForBusinessObject(jpt);
					if (cs == null)
						return;
					String entName = JPAEditorUtil.getText(jpt);
					try {
						final String newHeader = (cu.hasUnsavedChanges() ? "* " : "") + entName;	//$NON-NLS-1$ //$NON-NLS-2$
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								GraphicsUpdater.updateHeader(cs, newHeader);
							}
						});
										
					} catch (JavaModelException e) {
						JPADiagramEditorPlugin.logError("Cannot check compilation unit for unsaved changes", e); //$NON-NLS-1$				 
					}	
				}
				
			}			
		}
		
		private Set<ICompilationUnit> getAffectedCompilationUnits(IJavaElementDelta delta) { 
			Set<ICompilationUnit> res = new HashSet<ICompilationUnit>();
			IJavaElement el = delta.getElement();
			if (ICompilationUnit.class.isInstance(el))
				res.add((ICompilationUnit)el);
			IJavaElementDelta[] children = delta.getChangedChildren();
			for (IJavaElementDelta child : children) {
				Set<ICompilationUnit> cus = getAffectedCompilationUnits(child);
				res.addAll(cus);
			}
			return res;
		}
	}
	
	public class JPAProjectListener implements CollectionChangeListener {				

		synchronized public void itemsRemoved(CollectionRemoveEvent event) {
			if (ignoreEvents)
				return;

			Iterator<?> it = event.getItems().iterator();
			while (it.hasNext()) {
				Object o = it.next();
				if (!(o instanceof JpaFile))
					continue;
				final JpaFile jpaFile = (JpaFile)o;
				JptResourceModel jrm = ((JpaFile)o).getResourceModel();
				if (!JavaResourceCompilationUnit.class.isInstance(jrm))
					continue;
				JavaResourceCompilationUnit jrcu = (JavaResourceCompilationUnit)jrm;
				JavaResourcePersistentType jrpt = jrcu.persistentTypes().next();
				String name = jrpt.getQualifiedName();
				
				JpaProject jpaProject = jpaFile.getJpaProject();
				PersistenceUnit pu = JpaArtifactFactory.instance().getPersistenceUnit(jpaProject);
				if(pu == null)
					continue;
				JavaPersistentType jpt = (JavaPersistentType)pu.getPersistentType(name);
				final ContainerShape cs = (ContainerShape)featureProvider.getPictogramElementForBusinessObject(jpt);
				if (cs == null)
					return;
				final RemoveContext ctx = new RemoveContext(cs);
				final IRemoveFeature ft = featureProvider.getRemoveFeature(ctx);;
				Runnable r = new Runnable() {
					public void run() {
						TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(cs);
						ted.getCommandStack().execute(new RecordingCommand(ted) {
							protected void doExecute() {
								ft.remove(ctx);
							}						
						});
					}
				};
				Display.getDefault().syncExec(r);
			}			
		}

		public void collectionCleared(CollectionClearEvent arg0) {}

		public void collectionChanged(CollectionChangeEvent event) {}

		public void itemsAdded(CollectionAddEvent event) {}
	};

	public class EntityStateChangeListener implements StateChangeListener {
		public void stateChanged(StateChangeEvent event) {
		}
	}

	public class EntityAttributesChangeListener implements ListChangeListener {
		
		public void itemsAdded(ListAddEvent event) {
			AddEntityAttributes task = new AddEntityAttributes(event);
			Display.getDefault().asyncExec(task);
		}

		public void itemsRemoved(ListRemoveEvent event) {
			RemoveEntityAttributes task = new RemoveEntityAttributes(event);
			Display.getDefault().asyncExec(task);
		}
		
		public void listChanged(ListChangeEvent event) {}

		public void itemsMoved(ListMoveEvent arg0) {}

		public void itemsReplaced(ListReplaceEvent arg0) {}

		public void listCleared(ListClearEvent arg0) {}
	};

	public class EntityPropertyChangeListener implements PropertyChangeListener {
		synchronized public void propertyChanged(PropertyChangeEvent event) {
			String propName = event.getPropertyName();
			if (propName.equals(JPAEditorConstants.PROP_SPECIFIED_NAME)) {	
				final JavaEntity je = (JavaEntity)event.getSource();
				Runnable job = new Runnable() {
					public void run() {
						TransactionalEditingDomain ted = featureProvider.getTransactionalEditingDomain();
						ted.getCommandStack().execute(new RecordingCommand(ted) {
							protected void doExecute() {
								JavaPersistentType jpt = je.getPersistentType(); 
								updateJPTName(jpt);
							    String tableName = JPAEditorUtil.formTableName(jpt);
							    JpaArtifactFactory.instance().setTableName(jpt, tableName);
							}						
						});
					}
				};
				Display.getDefault().syncExec(job);
			}
		}
	}
	
	public class AttributePropertyChangeListener implements
			PropertyChangeListener {
		synchronized public void propertyChanged(PropertyChangeEvent event) {
			
			Model source = event.getSource();
			if (!JavaPersistentAttribute.class.isInstance(source))
				return;
			PictogramElement pe = featureProvider
					.getPictogramElementForBusinessObject(((JavaPersistentAttribute) source)
							.getParent());			
			final UpdateAttributeFeature ft = new UpdateAttributeFeature(
					featureProvider);
			final CustomContext ctx = new CustomContext();
			ctx.setInnerPictogramElement(pe);
			Runnable runnable = new Runnable() {
				@SuppressWarnings("deprecation")
				public void run() {
					ft.execute(ctx);					
				}
			};
			Display.getDefault().asyncExec(runnable);
			String propName = event.getPropertyName();
			if (propName.equals(PersistentAttribute.MAPPING_PROPERTY)) {
				renewAttributeMappingPropListener((JavaPersistentAttribute) source);
			}
		}
	}	

	/* 
	 * This listener listens when mappedBy has been changed
	 */
	public class AttributeJoiningStrategyPropertyChangeListener implements PropertyChangeListener {
		
		synchronized public void propertyChanged(PropertyChangeEvent event) {
			
			Model m = event.getSource();
			if (!MappedByRelationshipStrategy.class.isInstance(m))
				return;
			MappedByRelationshipStrategy js = (MappedByRelationshipStrategy)m;
			JpaNode nd = js.getParent();
			if (nd == null)
				return;
			nd = nd.getParent();
			if (nd == null)
				return;
			nd = nd.getParent();
			if ((nd == null) || !JavaPersistentAttribute.class.isInstance(nd))
				return;			
			JavaPersistentAttribute at = (JavaPersistentAttribute)nd;
			if (!at.getParent().getParent().getResource().exists())
				return;
			PictogramElement pe = featureProvider.getPictogramElementForBusinessObject(at.getParent());
			final UpdateAttributeFeature ft = new UpdateAttributeFeature(featureProvider); 
			final CustomContext ctx = new CustomContext();
			ctx.setInnerPictogramElement(pe);
			Runnable runnable = new Runnable() {
				@SuppressWarnings("deprecation")
				public void run() {
					try {
						ft.execute(ctx);
					} catch (Exception e) {
						//$NON-NLS-1$
					}
				}
			};
			Display.getDefault().asyncExec(runnable);
		}
	}		
	
	
	public class AttributeRelationshipReferencePropertyChangeListener implements PropertyChangeListener {
		
		synchronized public void propertyChanged(PropertyChangeEvent event) {		
			Relationship rr = (Relationship)event.getSource();
			JpaNode p = rr.getParent();
			if (p == null)
				return;
			p = p.getParent();
			if (p == null)
				return;
			if (!JavaPersistentAttribute.class.isInstance(p))
				return;
			JavaPersistentAttribute jpa = (JavaPersistentAttribute)p;
			renewAttributeJoiningStrategyPropertyListener(jpa);
			if (!jpa.getParent().getParent().getResource().exists())
				return;
			PictogramElement pe = featureProvider.getPictogramElementForBusinessObject(jpa.getParent());
			final UpdateAttributeFeature ft = new UpdateAttributeFeature(featureProvider); 
			final CustomContext ctx = new CustomContext();
			ctx.setInnerPictogramElement(pe);
			Runnable runnable = new Runnable() {
				@SuppressWarnings("deprecation")
				public void run() {
					try {
						ft.execute(ctx);
					} catch (Exception e) {
						//$NON-NLS-1$
					}
				}
			};
			Display.getDefault().asyncExec(runnable);		
		}
	}		
	
	public class AttributeMappingOptionalityChangeListener implements PropertyChangeListener {

		synchronized public void propertyChanged(PropertyChangeEvent event) {
			Boolean optional = (Boolean)event.getNewValue();
			boolean isOptional = (optional == null) ? true : optional.booleanValue();
			OptionalMapping nm = (OptionalMapping)event.getSource();
			JavaPersistentAttribute jpa = (JavaPersistentAttribute)nm.getParent();
			IRelation rel = featureProvider.getRelationRelatedToAttribute(jpa);
			boolean atBeginning = !rel.getOwner().equals(jpa.getParent()) || 
								  !rel.getOwnerAttributeName().equals(jpa.getName());
			final Connection c = (Connection)featureProvider.getPictogramElementForBusinessObject(rel);
			Collection<ConnectionDecorator> conDecs = c.getConnectionDecorators();
			Iterator<ConnectionDecorator> it = conDecs.iterator();
			final String newLabelText = isOptional ? 
										JPAEditorConstants.CARDINALITY_ZERO_ONE : 
										JPAEditorConstants.CARDINALITY_ONE;
			while (it.hasNext()) {
				final ConnectionDecorator cd = it.next();
				if (!JPAEditorUtil.isCardinalityDecorator(cd))
					continue;
				double d = cd.getLocation();
				if ((atBeginning && d > 0.5) || (!atBeginning && d <= 0.5))
					continue;
				
				TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(cd);
				ted.getCommandStack().execute(new RecordingCommand(ted) {
					protected void doExecute() {
						Text txt = (Text)cd.getGraphicsAlgorithm();
						txt.setValue(newLabelText);
						
						Point pt = JPAEditorUtil.recalcTextDecoratorPosition((FreeFormConnection)c, cd);
						Graphiti.getGaService().setLocation(txt, pt.x, pt.y, false);						
					}
				});
				break;
			}
		}
	}	

	public class RemoveEntityAttributes implements Runnable {
		ListRemoveEvent event = null;

		public RemoveEntityAttributes(ListRemoveEvent event) {
			this.event = event;
		}

		@SuppressWarnings("unchecked")
		synchronized public void run() {
			try {
				ArrayIterator<JavaPersistentAttribute> it = (ArrayIterator<JavaPersistentAttribute>) event.getItems().iterator();
				Set<Shape> shapesToRemove = new HashSet<Shape>();
				while (it.hasNext()) {
					JavaPersistentAttribute at = it.next();
					/*
					String key = getKeyForBusinessObject(at);
					remove(key);
					*/
					if (removeIgnore.remove(((PersistentType)at.getParent()).getName() + "." + at.getName())) //$NON-NLS-1$
						continue;
					Shape atShape = (Shape) featureProvider.getPictogramElementForBusinessObject(at);
					if (atShape == null)
						continue;
					
					
					JavaPersistentType jpt = (JavaPersistentType)event.getSource();
					JavaPersistentAttribute newAt = jpt.getAttributeNamed(at.getName());
					if (newAt != null) {
						RemoveAttributeFeature ft = new RemoveAttributeFeature(featureProvider, true, true);
						RemoveContext c = new RemoveContext(atShape);
						try {
							ft.remove(c);
						} catch (Exception ee) {
							//$NON-NLS-1$
						}
						AddAttributeFeature ft1 = new AddAttributeFeature(featureProvider);
						AddContext c1 = new AddContext();
						c1.setNewObject(newAt);
						ft1.add(c1);
						return;
					}

					shapesToRemove.add(atShape);
					IRelation rel = ((IJPAEditorFeatureProvider) featureProvider).getRelationRelatedToAttribute(at);
					if (rel == null)
						continue;
					Connection conn = (Connection) featureProvider.getPictogramElementForBusinessObject(rel);
					while (conn != null) {
						RemoveContext ctx = new RemoveContext(conn);
						RemoveRelationFeature ft = new RemoveRelationFeature(featureProvider);
						ft.remove(ctx);
						conn = (Connection) featureProvider.getPictogramElementForBusinessObject(rel);
					}
				}
				Iterator<Shape> itr = shapesToRemove.iterator();
				while (itr.hasNext()) {
					Shape atShape = itr.next();
					RemoveContext ctx = new RemoveContext(atShape);
					RemoveAttributeFeature ft = new RemoveAttributeFeature(featureProvider, true, true);
					ft.remove(ctx);
				}
				Collection<IRelation> rels = JpaArtifactFactory.instance().produceAllRelations(
						(JavaPersistentType) event.getSource(), (IJPAEditorFeatureProvider) featureProvider);
				Iterator<IRelation> iter = rels.iterator();
				while (iter.hasNext()) {
					IRelation rel = iter.next();
					ContainerShape ownerShape = (ContainerShape) featureProvider
							.getPictogramElementForBusinessObject(rel.getOwner());
					ContainerShape inverseShape = (ContainerShape) featureProvider
							.getPictogramElementForBusinessObject(rel.getInverse());
					AddConnectionContext cntx = new AddConnectionContext(JPAEditorUtil.getAnchor(ownerShape),
							JPAEditorUtil.getAnchor(inverseShape));
					cntx.setNewObject(rel);
					AddRelationFeature ft = new AddRelationFeature(featureProvider);
					ft.add(cntx);
				}
			} catch (Exception e) {
				//$NON-NLS-1$
			}
		}

	}

	public class AddEntityAttributes implements Runnable {
		ListAddEvent event = null;

		public AddEntityAttributes(ListAddEvent event) {
			this.event = event;
		}

		@SuppressWarnings("unchecked")
		synchronized public void run() {
			try {
				JavaPersistentType jpt = (JavaPersistentType) event.getSource();
				ContainerShape entShape = (ContainerShape)featureProvider.getPictogramElementForBusinessObject(jpt);
				
				// remove invalidated relations (if any)
				ArrayIterator<JavaPersistentAttribute> it = (ArrayIterator<JavaPersistentAttribute>) event.getItems().iterator();
				while (it.hasNext()) {
					JavaPersistentAttribute at = it.next();
					//Shape atShape = (Shape) featureProvider.getPictogramElementForBusinessObject(at);
					//if (atShape != null)
					//	continue;
					if (addIgnore.remove(((PersistentType)at.getParent()).getName() + "." + at.getName())) //$NON-NLS-1$
						continue;
					AddContext ctx = new AddContext();
					ctx.setNewObject(at);
					ctx.setTargetContainer(entShape);
					AddAttributeFeature ft = new AddAttributeFeature(featureProvider);
					ft.add(ctx);
				}
				//JpaArtifactFactory.instance().remakeRelations((IJPAEditorFeatureProvider)featureProvider, entShape, jpt);
				featureProvider.addJPTForUpdate(jpt.getName());

			} catch (Exception e) {
				//$NON-NLS-1$
			}
		}
	}
	
	public void addAttribForUpdate(PersistenceUnit pu, String entAtMappedBy) {
		entityNameListener.addAttribForUpdate(pu, entAtMappedBy);
	}

	
	private void updateJPTName(JavaPersistentType jpt) {
		String entName = JpaArtifactFactory.instance().getEntityName(jpt);
		entName = JPAEditorUtil.returnSimpleName(entName);
		ContainerShape entShape = (ContainerShape)featureProvider.getPictogramElementForBusinessObject(jpt);
		JPAEditorUtil.setJPTNameInShape(entShape, entName);
	}
	
	private void closeDiagramEditorIfProjectIsDeleted(IResourceChangeEvent event) {
		IResourceDelta changedDelta = event.getDelta();
		IResourceDelta[] deltas = changedDelta.getAffectedChildren();
		for (IResourceDelta delta : deltas) {
			final IResource resource = delta.getResource();
			if (!resource.exists()) {
			    if (resource instanceof IProject) {
					final IDiagramTypeProvider provider = featureProvider.getDiagramTypeProvider();
					if (provider instanceof JPAEditorDiagramTypeProvider) {
						final JPADiagramEditor diagramBySelectedProject = ((JPAEditorDiagramTypeProvider) provider).getDiagramEditor();
						PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
									public void run() {
										IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
										if (diagramBySelectedProject.getPartName().equals(resource.getName())) {
											page.closeEditor(diagramBySelectedProject, false);
										}
									}
								});
					}

				}
			}
		}
	}
	
	private void unregisterDeltedEntity(IResourceChangeEvent event) {
		IResourceDelta changedDelta = event.getDelta();
		IResourceDelta[] deltas = changedDelta.getAffectedChildren();
		for (IResourceDelta delta : deltas) {
			final IResource resource = delta.getResource();
			if (resource.exists()) {
				if (resource instanceof IProject) {
					IProject project = (IProject) resource;
					for (IResourceDelta deltaResource : delta.getAffectedChildren()) {
						List<IResourceDelta> resources = new ArrayList<IResourceDelta>();
						resources = findDeletedResource(deltaResource, resources);
						for (IResourceDelta resourceDelta : resources) {
							if (resourceDelta.getResource() instanceof File) {
								IFile file = this.eclipseFacade.getWorkspace().getRoot().getFile(((File) resourceDelta.getResource()).getFullPath());
								if (!file.exists() && file.getFileExtension().equals("java")) { //$NON-NLS-1$
										try {
											JpaProject jpaProject = JpaArtifactFactory.instance().getJpaProject((IProject) resource);
											if (jpaProject != null) {
												IJavaProject javaProject = JavaCore.create(project);
												IPackageFragmentRoot[] fragmentRoots = javaProject.getAllPackageFragmentRoots();
												for (IPackageFragmentRoot fragmentRoot : fragmentRoots) {
													if ((fragmentRoot instanceof PackageFragmentRoot) && fragmentRoot.getKind() == PackageFragmentRoot.K_SOURCE) {
														PackageFragmentRoot packageFragmentRoot = (PackageFragmentRoot) fragmentRoot;
														String sourcefolder = packageFragmentRoot.getResource().getName();
														String[] fq = file.getFullPath().toString().split(sourcefolder);
														String fqName = fq[1].replace("/", "."); //$NON-NLS-1$ //$NON-NLS-2$
														fqName = fqName.replaceFirst(".", "").replace(".java", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
														//JPAEditorUtil.createUnregisterEntityFromXMLJob(jpaProject, fqName);
													}
												}
											}
										} catch (CoreException e) {
											JPADiagramEditorPlugin.logError(e);
										}
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	private List<IResourceDelta> findDeletedResource(IResourceDelta delta, List<IResourceDelta> resources){
		IResourceDelta[] deltas = delta.getAffectedChildren();
			for (IResourceDelta del : deltas) {
				findDeletedResource(del, resources);
				if(del.getAffectedChildren().length==0)
				    resources.add(del);
			}
		return resources;
	}

}
