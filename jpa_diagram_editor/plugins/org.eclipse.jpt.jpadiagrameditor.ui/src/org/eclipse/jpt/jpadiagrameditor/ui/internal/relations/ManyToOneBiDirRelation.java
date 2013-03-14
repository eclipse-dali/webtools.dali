/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2012 SAP AG.
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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.relations;

import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class ManyToOneBiDirRelation  extends ManyToOneRelation implements IBidirectionalRelation{

	public ManyToOneBiDirRelation(IJPAEditorFeatureProvider fp, PersistentType owner, 
								  PersistentType inverse, 
								  String ownerAttributeName,
								  String inverseAttributeName,
								  boolean createAttribs, PersistentType embeddingEntity,
								  boolean isDerivedIdFeature) {
		super(owner, inverse);
		this.ownerAttributeName = ownerAttributeName;
		this.inverseAttributeName = inverseAttributeName;
		if (createAttribs)
			createRelation(fp, embeddingEntity, isDerivedIdFeature);
		
	}	

	@Override
	public PersistentAttribute getOwnerAnnotatedAttribute() {
		return ownerAnnotatedAttribute;
	}

	@Override
	public void setOwnerAnnotatedAttribute(	PersistentAttribute ownerAnnotatedAttribute) {
		this.ownerAnnotatedAttribute = ownerAnnotatedAttribute;
	}

	@Override
	public PersistentAttribute getInverseAnnotatedAttribute() {
		return inverseAnnotatedAttribute;
	}

	@Override
	public void setInverseAnnotatedAttribute(PersistentAttribute inverseAnnotatedAttribute) {
		this.inverseAnnotatedAttribute = inverseAnnotatedAttribute;
	}

	private void createRelation(IJPAEditorFeatureProvider fp, PersistentType embeddingEntity, boolean isDerivedIdFeature) {
		ownerAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(owner, inverse, false, null);
			
		boolean isMap = JPADiagramPropertyPage.isMapType(owner.getJpaProject().getProject());
		String mapKeyType = getMapKeyType(isMap, owner, embeddingEntity);
		if(JpaArtifactFactory.instance().isEmbeddable(owner)){
			inverseAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(inverse, embeddingEntity, true, mapKeyType);
		} else {
			inverseAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(inverse, owner, true, mapKeyType);
		}
		JpaArtifactFactory.instance().addManyToOneBidirectionalRelation(fp, owner, ownerAnnotatedAttribute, inverse, inverseAnnotatedAttribute, isMap);		
		if(isDerivedIdFeature){
			JpaArtifactFactory.instance().calculateDerivedIdAttribute(owner, inverse, ownerAnnotatedAttribute);
		}
	}
		
	@Override
	public RelDir getRelDir() {
		return RelDir.BI;
	}

	private String getMapKeyType(boolean isMap, PersistentType jpt, PersistentType embeddingEntity){
		if(JpaArtifactFactory.instance().isEmbeddable(jpt) && embeddingEntity!=null){
			return isMap ? JpaArtifactFactory.instance().getIdType(embeddingEntity) : null;
		}
		return isMap ? JpaArtifactFactory.instance().getIdType(jpt) : null;
	}

}
