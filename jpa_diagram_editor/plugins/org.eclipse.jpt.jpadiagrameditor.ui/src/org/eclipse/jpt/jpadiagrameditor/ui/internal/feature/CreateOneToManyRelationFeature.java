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

import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.HasReferanceRelation;
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
		if(JpaArtifactFactory.instance().isEmbeddable(owner)) {
			Set<HasReferanceRelation> refs = JpaArtifactFactory.instance().findAllHasReferenceRelsByEmbeddableWithEntity(owner, getFeatureProvider());
			if(refs.isEmpty()){
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
