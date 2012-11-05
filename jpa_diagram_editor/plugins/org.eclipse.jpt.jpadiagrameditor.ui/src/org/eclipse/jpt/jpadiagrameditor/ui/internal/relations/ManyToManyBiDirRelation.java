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

import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class ManyToManyBiDirRelation extends ManyToManyRelation implements IBidirectionalRelation {
	
	public ManyToManyBiDirRelation(IJPAEditorFeatureProvider fp, JavaPersistentType owner, 
								   JavaPersistentType inverse,
								   String ownerAttributeName,
								   String inverseAttributeName,
								   boolean createAttribs, JavaPersistentType embeddingEntity) {
		super(owner, inverse);
		this.ownerAttributeName = ownerAttributeName;
		this.inverseAttributeName = inverseAttributeName;
		if (createAttribs)
			createRelation(fp, embeddingEntity);		
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jpt.jpadiagrameditor.ui.relations.BidirectionalRelation#getOwnerAnnotatedAttribute()
	 */
	public JavaPersistentAttribute getOwnerAnnotatedAttribute() {
		return ownerAnnotatedAttribute;
	}

	public void setOwnerAnnotatedAttribute(
			JavaPersistentAttribute ownerAnnotatedAttribute) {
		this.ownerAnnotatedAttribute = ownerAnnotatedAttribute;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jpt.jpadiagrameditor.ui.relations.BidirectionalRelation#getInverseAnnotatedAttribute()
	 */
	public JavaPersistentAttribute getInverseAnnotatedAttribute() {
		return inverseAnnotatedAttribute;
	}

	public void setInverseAnnotatedAttribute(
			JavaPersistentAttribute inverseAnnotatedAttribute) {
		this.inverseAnnotatedAttribute = inverseAnnotatedAttribute;
	}	
	
	private void createRelation(IJPAEditorFeatureProvider fp, JavaPersistentType embeddingEntity) {
		
		boolean isMap = JPADiagramPropertyPage.isMapType(owner.getJpaProject().getProject());
		String mapKeyType = getMapKeyType(isMap, inverse, embeddingEntity);
		ownerAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(fp, owner, inverse, true, mapKeyType);
		
		mapKeyType = getMapKeyType(isMap, owner, embeddingEntity);
		if(JpaArtifactFactory.instance().hasEmbeddableAnnotation(owner)){
			inverseAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(fp, inverse, embeddingEntity, true, mapKeyType);

		} else {
			inverseAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(fp, inverse, owner, true, mapKeyType);
		}
		JpaArtifactFactory.instance().addManyToManyBidirectionalRelation(fp, owner, ownerAnnotatedAttribute, inverse, inverseAnnotatedAttribute, isMap);		
	} 	
	
	public RelDir getRelDir() {
		return RelDir.BI;
	}
	
	private String getMapKeyType(boolean isMap, JavaPersistentType jpt, JavaPersistentType embeddingEntity){
		if(JpaArtifactFactory.instance().hasEmbeddableAnnotation(jpt) && embeddingEntity!=null){
			return isMap ? JpaArtifactFactory.instance().getIdType(embeddingEntity) : null;
		}
		return isMap ? JpaArtifactFactory.instance().getIdType(jpt) : null;
	}
}