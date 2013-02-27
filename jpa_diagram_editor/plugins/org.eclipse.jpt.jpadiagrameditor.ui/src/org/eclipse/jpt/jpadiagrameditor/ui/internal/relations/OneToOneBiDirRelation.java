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

import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class OneToOneBiDirRelation extends OneToOneRelation implements IBidirectionalRelation{
	public OneToOneBiDirRelation(IJPAEditorFeatureProvider fp, JavaPersistentType owner, 
								 JavaPersistentType inverse, 
								 String ownerAttributeName,
								 String inverseAttributeName,
								 boolean createAttribs, JavaPersistentType embeddingEntity,
								 boolean isDerivedIdFeature) {
		super(owner, inverse);
		this.ownerAttributeName = ownerAttributeName;
		this.inverseAttributeName = inverseAttributeName;
		if (createAttribs)
			createRelation(fp, embeddingEntity, isDerivedIdFeature);
	}

	@Override
	public JavaSpecifiedPersistentAttribute getOwnerAnnotatedAttribute() {
		return ownerAnnotatedAttribute;
	}

	@Override
	public void setOwnerAnnotatedAttribute(
			JavaSpecifiedPersistentAttribute ownerAnnotatedAttribute) {
		this.ownerAnnotatedAttribute = ownerAnnotatedAttribute;
	}

	@Override
	public JavaSpecifiedPersistentAttribute getInverseAnnotatedAttribute() {
		return inverseAnnotatedAttribute;
	}

	@Override
	public void setInverseAnnotatedAttribute(
			JavaSpecifiedPersistentAttribute inverseAnnotatedAttribute) {
		this.inverseAnnotatedAttribute = inverseAnnotatedAttribute;
	}

	private void createRelation(IJPAEditorFeatureProvider fp, JavaPersistentType embeddingEntity, boolean isDerivedIdFeature) {
		ownerAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(fp, owner, inverse, false, null);
		if(isDerivedIdFeature){
			JpaArtifactFactory.instance().calculateDerivedIdAnnotation(owner, inverse, ownerAnnotatedAttribute);
		}
		
		if(JpaArtifactFactory.instance().hasEmbeddableAnnotation(owner)){
			inverseAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(fp, inverse, embeddingEntity, false, null);
		} else {
			inverseAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(fp, inverse, owner, false, null);
		}
		JpaArtifactFactory.instance().addOneToOneBidirectionalRelation(fp, owner, ownerAnnotatedAttribute, inverse, inverseAnnotatedAttribute);		
	} 	
		
	@Override
	public RelDir getRelDir() {
		return RelDir.BI;
	}
}
