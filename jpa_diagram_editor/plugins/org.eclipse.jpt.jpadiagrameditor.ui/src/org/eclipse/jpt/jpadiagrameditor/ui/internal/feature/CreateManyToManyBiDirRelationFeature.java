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
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToManyBiDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class CreateManyToManyBiDirRelationFeature extends CreateManyToManyRelationFeature 
												  implements ICreateBiDirRelationFeature {

	public CreateManyToManyBiDirRelationFeature(IJPAEditorFeatureProvider fp) {
		super(fp, JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,  
				JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyUniDirFeatureDescription); 
	}
		
	@Override
	public ManyToManyBiDirRelation createRelation(IJPAEditorFeatureProvider fp, PictogramElement source, PictogramElement target, PersistentType embeddingEntity) {
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
		
		ManyToManyBiDirRelation rel = new ManyToManyBiDirRelation(fp, owner, inverse, ownerAttributeText, inverseAttributeText, true, embeddingEntity); 
		return rel;		

	}	
	
    @Override
	public String getCreateImageId() {
        return JPAEditorImageProvider.ICON_MANY_TO_MANY_2_DIR;
    }
    
    /* (non-Javadoc)
	 * @see org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateRelationFeature#isRelationshipPossible()
	 */
	@Override
	protected boolean isRelationshipPossible() {
		if(JpaArtifactFactory.instance().isEmbeddable(owner)) {
			Set<HasReferanceRelation> refs = JpaArtifactFactory.instance().findAllHasReferenceRelsByEmbeddableWithEntity(owner, getFeatureProvider());
			if (refs.size() != 1)
				return false;
			for (HasReferanceRelation ref : refs) {				
				AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(ref.getEmbeddedAnnotatedAttribute());
				if ((attributeMapping instanceof ElementCollectionMapping2_0) || ( attributeMapping instanceof EmbeddedIdMapping)) {
						return false;
				}
			}
		}
		return true;
	}	
}