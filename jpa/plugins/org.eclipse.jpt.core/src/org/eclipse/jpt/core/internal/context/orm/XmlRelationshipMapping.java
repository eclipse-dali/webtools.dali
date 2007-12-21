/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IFetchable;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping;


public abstract class XmlRelationshipMapping<T extends RelationshipMapping> extends XmlAttributeMapping<T>
	implements IRelationshipMapping
{
	
	protected String specifiedTargetEntity;

	protected String defaultTargetEntity;

	protected IEntity resolvedTargetEntity;

	
	protected FetchType specifiedFetch;
	
	protected FetchType defaultFetch;	

	protected final XmlCascade cascade;

	
	protected XmlRelationshipMapping(XmlPersistentAttribute parent) {
		super(parent);
		this.cascade = new XmlCascade(this);
	}

	public String getTargetEntity() {
		return (this.getSpecifiedTargetEntity() == null) ? getDefaultTargetEntity() : this.getSpecifiedTargetEntity();
	}

	public String getSpecifiedTargetEntity() {
		return this.specifiedTargetEntity;
	}

	public void setSpecifiedTargetEntity(String newSpecifiedTargetEntity) {
		String oldSpecifiedTargetEntity = this.specifiedTargetEntity;
		this.specifiedTargetEntity = newSpecifiedTargetEntity;
		attributeMapping().setTargetEntity(newSpecifiedTargetEntity);
		firePropertyChanged(SPECIFIED_TARGET_ENTITY_PROPERTY, oldSpecifiedTargetEntity, newSpecifiedTargetEntity);
	}

	public String getDefaultTargetEntity() {
		return this.defaultTargetEntity;
	}

	protected void setDefaultTargetEntity(String newDefaultTargetEntity) {
		String oldDefaultTargetEntity = this.defaultTargetEntity;
		this.defaultTargetEntity = newDefaultTargetEntity;
		firePropertyChanged(DEFAULT_TARGET_ENTITY_PROPERTY, oldDefaultTargetEntity, newDefaultTargetEntity);
	}

	public IEntity getResolvedTargetEntity() {
		return this.resolvedTargetEntity;
	}

	protected void setResolvedTargetEntity(IEntity newResolvedTargetEntity) {
		IEntity oldResolvedTargetEntity = this.resolvedTargetEntity;
		this.resolvedTargetEntity = newResolvedTargetEntity;
		firePropertyChanged(RESOLVED_TARGET_ENTITY_PROPERTY, oldResolvedTargetEntity, newResolvedTargetEntity);
	}


	public FetchType getFetch() {
		return (this.getSpecifiedFetch() == null) ? this.getDefaultFetch() : this.getSpecifiedFetch();
	}

	public FetchType getDefaultFetch() {
		return this.defaultFetch;
	}
	
	protected void setDefaultFetch(FetchType newDefaultFetch) {
		FetchType oldFetch = this.defaultFetch;
		this.defaultFetch = newDefaultFetch;
		firePropertyChanged(IFetchable.DEFAULT_FETCH_PROPERTY, oldFetch, newDefaultFetch);
	}

	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}
	
	public void setSpecifiedFetch(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		this.attributeMapping().setFetch(FetchType.toOrmResourceModel(newSpecifiedFetch));
		firePropertyChanged(IFetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}


	public XmlCascade getCascade() {
		return this.cascade;
	}


	@Override
	public void initializeFromXmlRelationshipMapping(XmlRelationshipMapping<? extends RelationshipMapping> oldMapping) {
		super.initializeFromXmlRelationshipMapping(oldMapping);
		setSpecifiedTargetEntity(oldMapping.getSpecifiedTargetEntity());
		setSpecifiedFetch(oldMapping.getSpecifiedFetch());
	}
	//TODO should we set the fetch type from a BasicMapping??

	
//	public boolean targetEntityIsValid(String targetEntity) {
//		return RelationshipMappingTools.targetEntityIsValid(targetEntity);
//	}
//
//	public IEntity getEntity() {
//		ITypeMapping typeMapping = getPersistentType().getMapping();
//		if (typeMapping instanceof IEntity) {
//			return (IEntity) typeMapping;
//		}
//		return null;
//	}
//
//	public String fullyQualifiedTargetEntity(CompilationUnit astRoot) {
//		if (getTargetEntity() == null) {
//			return null;
//		}
//		if (targetEntityIncludesPackage()) {
//			return getTargetEntity();
//		}
//		String package_ = persistentType().getMapping().getEntityMappings().getPackage();
//		if (package_ != null) {
//			return package_ + '.' + getTargetEntity();
//		}
//		return getTargetEntity();
//	}
//
//	private boolean targetEntityIncludesPackage() {
//		return getTargetEntity().lastIndexOf('.') != -1;
//	}
//
//	public Iterator<String> allTargetEntityAttributeNames() {
//		IEntity targetEntity = this.getResolvedTargetEntity();
//		return (targetEntity == null) ? EmptyIterator.<String> instance() : targetEntity.getPersistentType().allAttributeNames();
//	}
//
//	public Iterator<String> candidateMappedByAttributeNames() {
//		return this.allTargetEntityAttributeNames();
//	}
//
//	@Override
//	public void refreshDefaults(DefaultsContext defaultsContext) {
//		super.refreshDefaults(defaultsContext);
//		setDefaultTargetEntity((String) defaultsContext.getDefault(BaseJpaPlatform.DEFAULT_TARGET_ENTITY_KEY));
//		String targetEntity = fullyQualifiedTargetEntity(defaultsContext.astRoot());
//		if (targetEntity != null) {
//			IPersistentType persistentType = defaultsContext.persistentType(targetEntity);
//			if (persistentType != null) {
//				if (persistentType.getMapping() instanceof IEntity) {
//					setResolvedTargetEntity((IEntity) persistentType.getMapping());
//					return;
//				}
//			}
//		}
//		setResolvedTargetEntity(null);
//	}
//
//	/**
//	 * the default 'targetEntity' is calculated from the attribute type;
//	 * return null if the attribute type cannot possibly be an entity
//	 */
//	public String javaDefaultTargetEntity(CompilationUnit astRoot) {
//		ITypeBinding typeBinding = this.getPersistentAttribute().getAttribute().typeBinding(astRoot);
//		if (typeBinding != null) {
//			return this.javaDefaultTargetEntity(typeBinding);
//		}
//		return null;
//	}
//
//	protected String javaDefaultTargetEntity(ITypeBinding typeBinding) {
//		return buildReferenceEntityTypeName(typeBinding);
//	}
//
//	protected String buildReferenceEntityTypeName(ITypeBinding typeBinding) {
//		return JavaRelationshipMapping.buildReferenceEntityTypeName(typeBinding);
//	}
	
	public String fullyQualifiedTargetEntity(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
		
	public IEntity getEntity() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean targetEntityIsValid(String targetEntity) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void initialize(T relationshipMapping) {
		super.initialize(relationshipMapping);
		this.specifiedTargetEntity = relationshipMapping.getTargetEntity();
		this.defaultTargetEntity = null;//TODO default target entity
		this.specifiedFetch = this.specifiedFetch(relationshipMapping);
		this.cascade.initialize(relationshipMapping);
	}
	
	@Override
	public void update(T relationshipMapping) {
		super.update(relationshipMapping);
		this.setSpecifiedTargetEntity(relationshipMapping.getTargetEntity());
		this.setDefaultTargetEntity(null);//TODO default target entity
		this.setSpecifiedFetch(this.specifiedFetch(relationshipMapping));
		this.cascade.update(relationshipMapping);
	}
	
	protected FetchType specifiedFetch(RelationshipMapping relationshipMapping) {
		return FetchType.fromOrmResourceModel(relationshipMapping.getFetch());
	}

}
