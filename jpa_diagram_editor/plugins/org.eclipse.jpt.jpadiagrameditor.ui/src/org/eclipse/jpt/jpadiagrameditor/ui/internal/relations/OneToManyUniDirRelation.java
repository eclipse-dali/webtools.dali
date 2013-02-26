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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.relations;

import org.eclipse.jpt.jpa.core.context.java.JavaModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class OneToManyUniDirRelation extends OneToManyRelation implements IUnidirectionalRelation {

	public OneToManyUniDirRelation(IJPAEditorFeatureProvider fp, JavaPersistentType owner, 
								   JavaPersistentType inverse,
								   String ownerAttributeName,
								   boolean createAttribs) {
		super(owner, inverse);
		this.ownerAttributeName = ownerAttributeName;
		if (createAttribs)
			createRelation(fp);		
	}	

	public JavaModifiablePersistentAttribute getAnnotatedAttribute() {
		return ownerAnnotatedAttribute;
	}

	public void setAnnotatedAttribute(JavaModifiablePersistentAttribute annotatedAttribute) {
		this.ownerAnnotatedAttribute = annotatedAttribute;
	}

	private void createRelation(IJPAEditorFeatureProvider fp) {
		boolean isMap = JPADiagramPropertyPage.isMapType(owner.getJpaProject().getProject());
		String mapKeyType = getMapKeyType(isMap, inverse);
		ownerAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(fp, owner, inverse, true, mapKeyType);
		
		JpaArtifactFactory.instance().addOneToManyUnidirectionalRelation(fp, owner, ownerAnnotatedAttribute, isMap);
	} 
	
	@Override
	public RelDir getRelDir() {
		return RelDir.UNI;
	}
	
	private String getMapKeyType(boolean isMap, JavaPersistentType jpt){
		return isMap ? JpaArtifactFactory.instance().getIdType(jpt) : null;
	}
	
}