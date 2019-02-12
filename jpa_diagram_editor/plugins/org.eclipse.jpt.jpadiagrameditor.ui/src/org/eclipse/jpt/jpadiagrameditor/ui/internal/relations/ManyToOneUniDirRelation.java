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
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class ManyToOneUniDirRelation extends ManyToOneRelation implements IUnidirectionalRelation {

	public ManyToOneUniDirRelation(IJPAEditorFeatureProvider fp, PersistentType owner, 
								   PersistentType inverse,
								   String ownerAttributeName,
								   boolean createAttribs, boolean isDerivedIdFeature) {		
		super(owner, inverse);
		this.ownerAttributeName = ownerAttributeName;
		if (createAttribs)
			createRelation(fp, isDerivedIdFeature);		
	}	

	public PersistentAttribute getAnnotatedAttribute() {
		return ownerAnnotatedAttribute;
	}

	public void setAnnotatedAttribute(PersistentAttribute annotatedAttribute) {
		this.ownerAnnotatedAttribute = annotatedAttribute;
	}

	private void createRelation(IJPAEditorFeatureProvider fp, boolean isDerivedIdFeature) {		
		ownerAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(owner, inverse, false, null);
		JpaArtifactFactory.instance().addManyToOneUnidirectionalRelation(fp, owner, ownerAnnotatedAttribute);
		if(isDerivedIdFeature){
			JpaArtifactFactory.instance().calculateDerivedIdAttribute(owner, inverse, ownerAnnotatedAttribute);
		}
	}
	
	@Override
	public RelDir getRelDir() {
		return RelDir.UNI;
	}		

}
