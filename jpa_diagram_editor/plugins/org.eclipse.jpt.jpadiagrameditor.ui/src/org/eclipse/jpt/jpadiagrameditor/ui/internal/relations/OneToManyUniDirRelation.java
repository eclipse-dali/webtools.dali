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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.relations;

import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class OneToManyUniDirRelation extends OneToManyRelation implements IUnidirectionalRelation {

	public OneToManyUniDirRelation(IJPAEditorFeatureProvider fp, PersistentType owner, 
								   PersistentType inverse,
								   String ownerAttributeName,
								   boolean createAttribs) {
		super(owner, inverse);
		this.ownerAttributeName = ownerAttributeName;
		if (createAttribs)
			createRelation(fp);		
	}	

	public PersistentAttribute getAnnotatedAttribute() {
		return ownerAnnotatedAttribute;
	}

	public void setAnnotatedAttribute(PersistentAttribute annotatedAttribute) {
		this.ownerAnnotatedAttribute = annotatedAttribute;
	}

	private void createRelation(IJPAEditorFeatureProvider fp) {
		boolean isMap = JPADiagramPropertyPage.isMapType(owner.getJpaProject().getProject());
		String mapKeyType = getMapKeyType(isMap, inverse);
		ownerAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(owner, inverse, true, mapKeyType);
		
		JpaArtifactFactory.instance().addOneToManyUnidirectionalRelation(fp, owner, ownerAnnotatedAttribute, isMap);
	} 
	
	@Override
	public RelDir getRelDir() {
		return RelDir.UNI;
	}
	
	private String getMapKeyType(boolean isMap, PersistentType jpt){
		return isMap ? JpaArtifactFactory.instance().getIdType(jpt) : null;
	}
	
}
