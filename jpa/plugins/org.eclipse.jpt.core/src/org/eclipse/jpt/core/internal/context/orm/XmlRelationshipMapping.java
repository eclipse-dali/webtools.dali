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
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.core.internal.resource.orm.RelationshipMapping;


public abstract class XmlRelationshipMapping<T extends RelationshipMapping> extends XmlAttributeMapping<RelationshipMapping>
	implements IRelationshipMapping
{
	
	protected String specifiedTargetEntity;

	protected String defaultTargetEntity;

	protected IEntity resolvedTargetEntity;


//	protected ICascade cascade;

	
	protected XmlRelationshipMapping(XmlPersistentAttribute parent) {
		super(parent);
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

//	protected void setResolvedTargetEntity(IEntity newResolvedTargetEntity) {
//		IEntity oldResolvedTargetEntity = this.resolvedTargetEntity;
//		this.resolvedTargetEntity = newResolvedTargetEntity;
//		if (eNotificationRequired())
//			eNotify(new ENotificationImpl(this, Notification.SET, OrmPackage.XML_RELATIONSHIP_MAPPING__RESOLVED_TARGET_ENTITY, oldResolvedTargetEntity, resolvedTargetEntity));
//	}
//
//
////	public ICascade getCascade() {
////		return cascade;
////	}
////
////	public ICascade createCascade() {
////		return OrmFactory.eINSTANCE.createXmlCascade();
////	}
//
//
//
//	@Override
//	public void initializeFromXmlRelationshipMapping(XmlRelationshipMapping oldMapping) {
//		super.initializeFromXmlRelationshipMapping(oldMapping);
//		setSpecifiedTargetEntity(oldMapping.getSpecifiedTargetEntity());
//	}
//
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
	
	public void setResolvedTargetEntity(IEntity value) {
		// TODO Auto-generated method stub
		
	}
	
	public IEntity getEntity() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean targetEntityIsValid(String targetEntity) {
		// TODO Auto-generated method stub
		return false;
	}
}
