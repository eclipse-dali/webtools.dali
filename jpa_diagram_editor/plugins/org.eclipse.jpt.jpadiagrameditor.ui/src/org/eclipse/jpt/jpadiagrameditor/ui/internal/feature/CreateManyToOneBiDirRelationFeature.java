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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import java.util.Set;

import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToOneBiDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class CreateManyToOneBiDirRelationFeature extends CreateManyToOneRelationFeature 
												 implements ICreateBiDirRelationFeature {
	
	public CreateManyToOneBiDirRelationFeature(IJPAEditorFeatureProvider fp, boolean isDerivedIdFeature) {
		super(fp, JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureName,  
				JPAEditorMessages.CreateManyToOneBiDirRelationFeature_manyToOneBiDirFeatureDescription, isDerivedIdFeature);
	}
		
	@Override
	public ManyToOneBiDirRelation createRelation(IJPAEditorFeatureProvider fp, PictogramElement source, 
			PictogramElement target, PersistentType embeddingEntity) {
		PersistentType owner = (PersistentType)(getBusinessObjectForPictogramElement(source));
		PersistentType inverse = (PersistentType)(getBusinessObjectForPictogramElement(target));		
		
		String ownerAttributeName = JPAEditorUtil.returnSimpleName(JpaArtifactFactory.instance().getEntityName(inverse));
		String nameWithNonCapitalLetter = ownerAttributeName;
		if (JpaArtifactFactory.instance().isMethodAnnotated(owner))
			nameWithNonCapitalLetter = JPAEditorUtil.produceValidAttributeName(ownerAttributeName);
		String ownerAttributeText = JPAEditorUtil.produceUniqueAttributeName(owner, nameWithNonCapitalLetter);		

		String inverseAttributeName = JPAEditorUtil.returnSimpleName(JpaArtifactFactory.instance().getEntityName(owner));
		String nameWithNonCapitalLetter2 = inverseAttributeName;
		if (JpaArtifactFactory.instance().isMethodAnnotated(inverse))
			nameWithNonCapitalLetter2 = JPAEditorUtil.produceValidAttributeName(inverseAttributeName);
		String inverseAttributeText = JPAEditorUtil.produceUniqueAttributeName(inverse, ownerAttributeText, nameWithNonCapitalLetter2);		
		
		ManyToOneBiDirRelation rel = new ManyToOneBiDirRelation(fp, owner, inverse, ownerAttributeText, inverseAttributeText, true, embeddingEntity, isDerivedIdFeature); 
		return rel;		
	}

    public String getCreateImageId() {
    	if(isDerivedIdFeature) {
        	return JPAEditorImageProvider.ICON_MANY_TO_ONE_2_KEY_DIR;
    	}
    	return JPAEditorImageProvider.ICON_MANY_TO_ONE_2_DIR;
    }
    
    @Override
    protected boolean isRelationshipPossible() {
    	if(JpaArtifactFactory.instance().isEmbeddable(owner)) {
			Set<HasReferanceRelation> refs = JpaArtifactFactory.instance().findAllHasReferenceRelsByEmbeddableWithEntity(owner, getFeatureProvider());
			if (refs.size() != 1){
				return false;
			} else {
				for (HasReferanceRelation ref : refs) {
					AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(ref.getEmbeddedAnnotatedAttribute());
					if (attributeMapping instanceof EmbeddedIdMapping) {
						return false;
					}
				}
			}
		}
		return true;
    }
}
