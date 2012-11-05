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
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToManyBiDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class CreateManyToManyBiDirRelationFeature extends CreateManyToManyRelationFeature 
												  implements ICreateBiDirRelationFeature {

	public CreateManyToManyBiDirRelationFeature(IJPAEditorFeatureProvider fp) {
		super(fp, JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyBiDirFeatureName,  
				JPAEditorMessages.CreateManyToManyBiDirRelationFeature_manyToManyUniDirFeatureDescription); 
	}
		
	@Override
	public ManyToManyBiDirRelation createRelation(IJPAEditorFeatureProvider fp, PictogramElement source, PictogramElement target, JavaPersistentType embeddingEntity) {
		JavaPersistentType owner = (JavaPersistentType)(getBusinessObjectForPictogramElement(source));
		JavaPersistentType inverse = (JavaPersistentType)(getBusinessObjectForPictogramElement(target));		
		
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
	
    public String getCreateImageId() {
        return JPAEditorImageProvider.ICON_MANY_TO_MANY_2_DIR;
    }
    
    /* (non-Javadoc)
	 * @see org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateRelationFeature#isRelationshipPossible()
	 */
	@Override
	protected boolean isRelationshipPossible() {
		if(JpaArtifactFactory.instance().hasEmbeddableAnnotation(owner)) {
			Set<HasReferanceRelation> refs = JpaArtifactFactory.instance().findAllHasReferenceRelsByEmbeddableWithEntity(owner, getFeatureProvider());
			if (refs.size() != 1)
				return false;
			for (HasReferanceRelation ref : refs) {
				HashSet<String> annotations = JpaArtifactFactory.instance().getAnnotationNames(ref.getEmbeddedAnnotatedAttribute());
				for (String annotationName : annotations) {
					if (annotationName.equals(JPAEditorConstants.ANNOTATION_ELEMENT_COLLECTION) || annotationName.equals(JPAEditorConstants.ANNOTATION_EMBEDDED_ID)) {
						return false;
					}
				}
			}
		}
		return true;
	}	
}