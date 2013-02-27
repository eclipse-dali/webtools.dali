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

import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class ManyToOneUniDirRelation extends ManyToOneRelation implements IUnidirectionalRelation {

	public ManyToOneUniDirRelation(IJPAEditorFeatureProvider fp, JavaPersistentType owner, 
								   JavaPersistentType inverse,
								   String ownerAttributeName,
								   boolean createAttribs, boolean isDerivedIdFeature) {		
		super(owner, inverse);
		this.ownerAttributeName = ownerAttributeName;
		if (createAttribs)
			createRelation(fp, isDerivedIdFeature);		
	}	
	
	public JavaSpecifiedPersistentAttribute getAnnotatedAttribute() {
		return ownerAnnotatedAttribute;
	}

	public void setAnnotatedAttribute(JavaSpecifiedPersistentAttribute annotatedAttribute) {
		this.ownerAnnotatedAttribute = annotatedAttribute;
	}

	private void createRelation(IJPAEditorFeatureProvider fp, boolean isDerivedIdFeature) {		
		ownerAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(fp, owner, inverse, false, null);
		if(isDerivedIdFeature){
			JpaArtifactFactory.instance().calculateDerivedIdAnnotation(owner, inverse, ownerAnnotatedAttribute);
		}
		JpaArtifactFactory.instance().addManyToOneUnidirectionalRelation(fp, owner, ownerAnnotatedAttribute);
		
	}
	
	@Override
	public RelDir getRelDir() {
		return RelDir.UNI;
	}		

}
