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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.relations;

import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class OneToOneBiDirRelation extends OneToOneRelation implements IBidirectionalRelation{
	public OneToOneBiDirRelation(IJPAEditorFeatureProvider fp, PersistentType owner, 
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
	public void setOwnerAnnotatedAttribute(
			PersistentAttribute ownerAnnotatedAttribute) {
		this.ownerAnnotatedAttribute = ownerAnnotatedAttribute;
	}

	@Override
	public PersistentAttribute getInverseAnnotatedAttribute() {
		return inverseAnnotatedAttribute;
	}

	@Override
	public void setInverseAnnotatedAttribute(
			PersistentAttribute inverseAnnotatedAttribute) {
		this.inverseAnnotatedAttribute = inverseAnnotatedAttribute;
	}

	private void createRelation(IJPAEditorFeatureProvider fp, PersistentType embeddingEntity, boolean isDerivedIdFeature) {
		ownerAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(owner, inverse, false, null);
		
		if(JpaArtifactFactory.instance().isEmbeddable(owner)){
			inverseAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(inverse, embeddingEntity, false, null);
		} else {
			inverseAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(inverse, owner, false, null);
		}

		JpaArtifactFactory.instance().addOneToOneBidirectionalRelation(fp, owner, ownerAnnotatedAttribute, inverse, inverseAnnotatedAttribute);		
		if(isDerivedIdFeature){
			JpaArtifactFactory.instance().calculateDerivedIdAttribute(owner, inverse, ownerAnnotatedAttribute);
		}
	} 	
		
	@Override
	public RelDir getRelDir() {
		return RelDir.BI;
	}
}
