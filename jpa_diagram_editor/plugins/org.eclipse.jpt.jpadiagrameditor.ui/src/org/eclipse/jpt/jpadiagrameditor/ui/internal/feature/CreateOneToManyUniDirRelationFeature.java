/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
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

import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.OneToManyUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class CreateOneToManyUniDirRelationFeature extends
		CreateOneToManyRelationFeature {

	public CreateOneToManyUniDirRelationFeature(IJPAEditorFeatureProvider fp) {
		super(fp, JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureName,  
				JPAEditorMessages.CreateOneToManyUniDirRelationFeature_oneToManyUniDirFeatureDescription); 
	}
		
	@Override
	public OneToManyUniDirRelation createRelation(IJPAEditorFeatureProvider fp, PictogramElement source, 
			PictogramElement target) {
		JavaPersistentType owner = (JavaPersistentType)(getBusinessObjectForPictogramElement(source));
		JavaPersistentType inverse = (JavaPersistentType)(getBusinessObjectForPictogramElement(target));	
				
		String attributeName = JPAEditorUtil.cutFromLastDot(JpaArtifactFactory.instance().getEntityName(inverse));
		String nameWithNonCapitalLetter = attributeName;
		if (JpaArtifactFactory.instance().isMethodAnnotated(owner))
			nameWithNonCapitalLetter = JPAEditorUtil.produceValidAttributeName(attributeName);
		
		String attributeText = JPAEditorUtil.produceUniqueAttributeName(owner, nameWithNonCapitalLetter);		
		OneToManyUniDirRelation relation = new OneToManyUniDirRelation(fp, owner, inverse, attributeText, true, 
				getFeatureProvider().getCompilationUnit(owner),
				getFeatureProvider().getCompilationUnit(inverse));
		return relation;	
	}	
	
    public String getCreateImageId() {
        return JPAEditorImageProvider.ICON_ONE_TO_MANY_1_DIR;
    }		
	
}