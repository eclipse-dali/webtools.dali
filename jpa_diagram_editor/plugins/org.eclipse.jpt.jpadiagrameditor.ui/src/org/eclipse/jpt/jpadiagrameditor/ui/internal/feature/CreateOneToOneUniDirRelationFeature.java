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
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.OneToOneUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class CreateOneToOneUniDirRelationFeature extends CreateOneToOneRelationFeature
												 implements ICreateUniDirRelationFeature {
	
	public CreateOneToOneUniDirRelationFeature(IJPAEditorFeatureProvider fp) {
		super(fp, JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureName, 
				JPAEditorMessages.CreateOneToOneUniDirRelationFeature_oneToOneUniDirFeatureDescription); 
	}
		
	@Override
	public OneToOneUniDirRelation createRelation(IJPAEditorFeatureProvider fp, PictogramElement source, 
														PictogramElement target, JavaPersistentType embeddingEntity) {
		
		JavaPersistentType owner = (JavaPersistentType)(getBusinessObjectForPictogramElement(source));
		JavaPersistentType inverse = (JavaPersistentType)(getBusinessObjectForPictogramElement(target));
		
		String name = JPAEditorUtil.returnSimpleName(JpaArtifactFactory.instance().getEntityName(inverse));
		String nameWithNonCapitalLetter = name;
		if (JpaArtifactFactory.instance().isMethodAnnotated(owner))
			nameWithNonCapitalLetter = JPAEditorUtil.produceValidAttributeName(name);
		String attribTxt = JPAEditorUtil.produceUniqueAttributeName(owner, nameWithNonCapitalLetter);		
		OneToOneUniDirRelation res = new OneToOneUniDirRelation(fp, owner, inverse, attribTxt, true);
		return res;
	}
	
    @Override
	public String getCreateImageId() {
        return JPAEditorImageProvider.ICON_ONE_TO_ONE_1_DIR;
    }
    
    /* (non-Javadoc)
   	 * @see org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateRelationFeature#isRelationshipPossible()
   	 */
   	@Override
   	protected boolean isRelationshipPossible() {
       	if(JpaArtifactFactory.instance().hasEmbeddableAnnotation(owner)) {
   			Set<HasReferanceRelation> refs = JpaArtifactFactory.instance().findAllHasReferenceRelsByEmbeddableWithEntity(owner, getFeatureProvider());
   			if (refs.isEmpty()){
   				return false;
   			} else {
   				for (HasReferanceRelation ref : refs) {
   					HashSet<String> annotations = JpaArtifactFactory.instance().getAnnotationNames(ref.getEmbeddedAnnotatedAttribute());
   					for (String annotationName : annotations) {
   						if (annotationName.equals(JPAEditorConstants.ANNOTATION_EMBEDDED_ID)) {
   							return false;
   						}
   					}
   				}
   			}
   		}
   		return true;
   	}
}