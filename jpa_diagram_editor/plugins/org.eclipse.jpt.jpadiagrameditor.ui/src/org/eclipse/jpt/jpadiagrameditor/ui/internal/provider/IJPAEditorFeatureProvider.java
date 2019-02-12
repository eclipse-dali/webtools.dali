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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.provider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.IPeService;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.IModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation.HasReferenceType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelDir;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IsARelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IGraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IPeServiceUtil;


public interface IJPAEditorFeatureProvider extends IFeatureProvider{

	public Diagram getDiagram();
			
	public HashSet<IsARelation> getAllExistingIsARelations();
		
	public HashSet<HasReferanceRelation> getAllExistingHasReferenceRelations();
		
	public ICompilationUnit getCompilationUnit(PersistentType jpt);
	
	public boolean hasObjectWithName(String name);

	public String getKeyForBusinessObject(Object bo);

	public Object remove(String key);
	
    public Object remove(String key, boolean save);	

	public Set<IRelation> getRelationRelatedToAttribute(PersistentAttribute jpa, String typeName);

	public HasReferanceRelation getEmbeddedRelationRelatedToAttribute(PersistentAttribute jpa);
	
	public Object getBusinessObjectForKey(String key);

	public void addJPTForUpdate(String jptName);

    public boolean doesRelationExist(PersistentType owner, 
			PersistentType inverse, 
			String ownerAttributeName,
			String inverseAttributeName,
			RelType relType, 
			RelDir relDir);
    
    public boolean doesEmbeddedRelationExist(PersistentType embeddable, PersistentType embeddingEntity, String embeddedAttributeName, HasReferenceType relType);
    
	public void restoreEntity(PersistentType jpt);
	
	public void addRemoveIgnore(PersistentType jpt, String atName);
	
	public void addAddIgnore(PersistentType jpt, String atName);
	
    public void putKeyToBusinessObject(String key, Object bo);
    
	public int getAttribsNum(Shape sh);    
    
	public int increaseAttribsNum(Shape sh);
	
	public int decreaseAttribsNum(Shape sh);

    public void replaceAttribute(PersistentAttribute oldAt, PersistentAttribute newAt);

	public void renewAttributeJoiningStrategyPropertyListener(PersistentAttribute jpa);
	
	public IPeService getPeService();

	public IPeServiceUtil getPeServiceUtil();
	
	public IJPAEditorUtil getJPAEditorUtil();

	public IDeleteFeature getDeleteFeature(IDeleteContext context);
	
	public ICustomFeature getAddAllEntitiesFeature();
	
	public IModelIntegrationUtil getMoinIntegrationUtil();
	
	public IGraphicsUpdater getGraphicsUpdater();

	public void addAttribForUpdate(PersistenceUnit pu, String entAtMappedBy);
	
	public TransactionalEditingDomain getTransactionalEditingDomain();
	
	public Properties loadProperties(IProject project);

	public Collection<PersistentType> getPersistentTypes();
	
	public PersistentType getFirstSuperclassBelongingToTheDiagram(PersistentType subclass);
	
	public void removeAllRedundantIsARelations();
	
	public boolean existRedundantIsARelations();
	
	public void setGrayColor(final PersistentType jpt);
	
	public void setOriginalPersistentTypeColor();

}
