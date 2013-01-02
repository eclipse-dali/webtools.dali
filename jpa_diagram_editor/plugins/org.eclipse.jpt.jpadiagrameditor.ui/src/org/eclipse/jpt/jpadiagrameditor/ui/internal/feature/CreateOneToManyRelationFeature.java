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

import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;

abstract class CreateOneToManyRelationFeature 
		extends	CreateRelationFeature {

	public CreateOneToManyRelationFeature(IJPAEditorFeatureProvider fp, String name, String description) {
		super(fp, name, description, false);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateRelationFeature#isRelationshipPossible()
	 */
	@Override
	protected boolean isRelationshipPossible() {
		if(JpaArtifactFactory.instance().hasEmbeddableAnnotation(owner)) {
			Set<HasReferanceRelation> refs = JpaArtifactFactory.instance().findAllHasReferenceRelsByEmbeddableWithEntity(owner, getFeatureProvider());
			if(refs.isEmpty()){
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
