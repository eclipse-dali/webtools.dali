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

import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.IModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelDir;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IGraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IPeServiceUtil;


public interface IJPAEditorFeatureProvider extends IFeatureProvider{

	public ICompilationUnit getCompilationUnit(JavaPersistentType jpt);
	
	public boolean hasObjectWithName(String name);

	public String getKeyForBusinessObject(Object bo);

	public Object remove(String key);
	
    public Object remove(String key, boolean save);	

	public IRelation getRelationRelatedToAttribute(JavaPersistentAttribute jpa);
	
	public boolean existsRelation(JavaPersistentType jpt1, JavaPersistentType jpt2);

	public Object getBusinessObjectForKey(String key);

	public void addJPTForUpdate(String jptName);

    public boolean doesRelationExist(JavaPersistentType owner, 
			JavaPersistentType inverse, 
			String ownerAttributeName,
			RelType relType, 
			RelDir relDir);
    
	public void restoreEntity(JavaPersistentType jpt);
	
	public void addRemoveIgnore(JavaPersistentType jpt, String atName);
	
	public void addAddIgnore(JavaPersistentType jpt, String atName);
	
    public void putKeyToBusinessObject(String key, Object bo);
    
	public int getAttribsNum(Shape sh);    
    
	public int increaseAttribsNum(Shape sh);
	
	public int decreaseAttribsNum(Shape sh);
	
    public void replaceAttribute(JavaPersistentAttribute oldAt, JavaPersistentAttribute newAt);
    	
	public void renewAttributeJoiningStrategyPropertyListener(JavaPersistentAttribute jpa);

	public IPeServiceUtil getPeUtil();
	
	public IJPAEditorUtil getJPAEditorUtil();

	public IDeleteFeature getDeleteFeature(IDeleteContext context);
	
	public ICustomFeature getAddAllEntitiesFeature();
	
	public IModelIntegrationUtil getMoinIntegrationUtil();
	
	public IGraphicsUpdater getGraphicsUpdater();

	public void addAttribForUpdate(PersistenceUnit pu, String entAtMappedBy);
	
	public TransactionalEditingDomain getTransactionalEditingDomain();
	
	public Properties loadProperties(IProject project);

}