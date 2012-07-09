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

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;


public class OneToOneUniDirRelation extends OneToOneRelation implements IUnidirectionalRelation {

	public OneToOneUniDirRelation(IJPAEditorFeatureProvider fp, JavaPersistentType owner, 
								  JavaPersistentType inverse, 
								  String ownerAttributeName,
								  boolean createAttribs, 
								  ICompilationUnit ownerCU,
								  ICompilationUnit inverseCU) {
		super(owner, inverse);
		this.ownerAttributeName = ownerAttributeName; 
		if (createAttribs) 
			createRelation(fp, ownerCU, inverseCU);
	}
	
	public JavaPersistentAttribute getAnnotatedAttribute() {
		return ownerAnnotatedAttribute;
	}

	public void setAnnotatedAttribute(JavaPersistentAttribute annotatedAttribute) {
		this.ownerAnnotatedAttribute = annotatedAttribute;
	}

	private void createRelation(IJPAEditorFeatureProvider fp, ICompilationUnit ownerCU, ICompilationUnit inverseCU) {
		ownerAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(fp, owner, inverse, ownerCU, inverseCU, false, null);
		JpaArtifactFactory.instance().addOneToOneUnidirectionalRelation(fp, owner, ownerAnnotatedAttribute);
	} 
		
	public RelDir getRelDir() {
		return RelDir.UNI;
	}		
		
}