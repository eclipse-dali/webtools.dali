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
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.OneToOneUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class CreateOneToOneUniDirRelationFeature extends CreateOneToOneRelationFeature
												 implements ICreateUniDirRelationFeature {
		
	public CreateOneToOneUniDirRelationFeature(IJPAEditorFeatureProvider fp, boolean isDerivedIdFeature) {
		super(fp, JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName, 
				JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureDescription, isDerivedIdFeature);
	}
		
	@Override
	public OneToOneUniDirRelation createRelation(IJPAEditorFeatureProvider fp, PictogramElement source, 
														PictogramElement target, PersistentType embeddingEntity) {
		
		PersistentType owner = (PersistentType)(getBusinessObjectForPictogramElement(source));
		PersistentType inverse = (PersistentType)(getBusinessObjectForPictogramElement(target));
		
		String name = JPAEditorUtil.returnSimpleName(JpaArtifactFactory.instance().getEntityName(inverse));
		String nameWithNonCapitalLetter = name;
		if (JpaArtifactFactory.instance().isMethodAnnotated(owner))
			nameWithNonCapitalLetter = JPAEditorUtil.produceValidAttributeName(name);
		String attribTxt = JPAEditorUtil.produceUniqueAttributeName(owner, nameWithNonCapitalLetter);		
		OneToOneUniDirRelation res = new OneToOneUniDirRelation(fp, owner, inverse, attribTxt, true, isDerivedIdFeature);
		return res;
	}
	
    public String getCreateImageId() {
    	if(isDerivedIdFeature) {
    		return JPAEditorImageProvider.ICON_ONE_TO_ONE_1_KEY_DIR;
    	}
        return JPAEditorImageProvider.ICON_ONE_TO_ONE_1_DIR;
    }
    
    /* (non-Javadoc)
   	 * @see org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateRelationFeature#isRelationshipPossible()
   	 */
   	@Override
   	protected boolean isRelationshipPossible() {
       	if(JpaArtifactFactory.instance().isEmbeddable(owner)) {
   			Set<HasReferanceRelation> refs = JpaArtifactFactory.instance().findAllHasReferenceRelsByEmbeddableWithEntity(owner, getFeatureProvider());
   			if (refs.isEmpty()){
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
