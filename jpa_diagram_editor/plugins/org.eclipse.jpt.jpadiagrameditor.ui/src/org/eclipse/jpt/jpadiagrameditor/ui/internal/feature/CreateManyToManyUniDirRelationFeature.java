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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToManyUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class CreateManyToManyUniDirRelationFeature extends CreateManyToManyRelationFeature 
												   implements ICreateUniDirRelationFeature {

	public CreateManyToManyUniDirRelationFeature(IJPAEditorFeatureProvider fp) {
		super(fp, JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureName, 
				JPAEditorMessages.CreateManyToManyUniDirRelationFeature_manyToManyUniDirFeatureDescription); 
	}
		
	@Override
	public ManyToManyUniDirRelation createRelation(IJPAEditorFeatureProvider fp, PictogramElement source, PictogramElement target, JavaPersistentType embeddingEntity) {
		JavaPersistentType owner = (JavaPersistentType)(getBusinessObjectForPictogramElement(source));
		JavaPersistentType inverse = (JavaPersistentType)(getBusinessObjectForPictogramElement(target));		

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
    	if(JpaArtifactFactory.instance().hasEmbeddableAnnotation(owner)) {
			Set<HasReferanceRelation> refs = JpaArtifactFactory.instance().findAllHasReferenceRelsByEmbeddableWithEntity(owner, getFeatureProvider());
			if (refs.isEmpty()) {
				return false;
			} else {
				for (HasReferanceRelation ref : refs) {
					HashSet<String> annotations = JpaArtifactFactory.instance().getAnnotationNames(ref.getEmbeddedAnnotatedAttribute());
					for (String annotationName : annotations) {
						if (annotationName.equals(JPAEditorConstants.ANNOTATION_ELEMENT_COLLECTION) || annotationName.equals(JPAEditorConstants.ANNOTATION_EMBEDDED_ID)) {
							return false;
						}
					}
				}
			}
		}
		return true;
    }

}