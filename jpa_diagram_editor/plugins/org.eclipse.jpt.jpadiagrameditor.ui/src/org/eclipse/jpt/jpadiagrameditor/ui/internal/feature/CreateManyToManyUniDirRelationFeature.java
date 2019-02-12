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
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToManyUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class CreateManyToManyUniDirRelationFeature extends CreateManyToManyRelationFeature 
												   implements ICreateUniDirRelationFeature {

	public CreateManyToManyUniDirRelationFeature(IJPAEditorFeatureProvider fp) {
		super(fp, JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName, 
				JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureDescription); 
	}
		
	@Override
	public ManyToManyUniDirRelation createRelation(IJPAEditorFeatureProvider fp, PictogramElement source, PictogramElement target, PersistentType embeddingEntity) {
		PersistentType owner = (PersistentType)(getBusinessObjectForPictogramElement(source));
		PersistentType inverse = (PersistentType)(getBusinessObjectForPictogramElement(target));		

		String attributeName = JPAEditorUtil.returnSimpleName(JpaArtifactFactory.instance().getEntityName(inverse));
		String nameWithNonCapitalLetter = attributeName;
		if (JpaArtifactFactory.instance().isMethodAnnotated(owner))
			nameWithNonCapitalLetter = JPAEditorUtil.produceValidAttributeName(attributeName);
		String attributeText = JPAEditorUtil.produceUniqueAttributeName(owner, nameWithNonCapitalLetter);		
		ManyToManyUniDirRelation relation = new ManyToManyUniDirRelation(fp, owner, inverse, attributeText, true); 
		return relation;		
	}

    @Override
	public String getCreateImageId() {
        return JPAEditorImageProvider.ICON_MANY_TO_MANY_1_DIR;
    }
    
    @Override
    protected boolean isRelationshipPossible() {
    	if(JpaArtifactFactory.instance().isEmbeddable(owner)) {
			Set<HasReferanceRelation> refs = JpaArtifactFactory.instance().findAllHasReferenceRelsByEmbeddableWithEntity(owner, getFeatureProvider());
			if (refs.isEmpty()) {
				return false;
			} else {
				for (HasReferanceRelation ref : refs) {
					AttributeMapping attributeMapping = JpaArtifactFactory.instance().getAttributeMapping(ref.getEmbeddedAnnotatedAttribute());
					if ((attributeMapping instanceof ElementCollectionMapping2_0) || ( attributeMapping instanceof EmbeddedIdMapping)) {
							return false;
					}
				}
			}
		}
		return true;
    }

}