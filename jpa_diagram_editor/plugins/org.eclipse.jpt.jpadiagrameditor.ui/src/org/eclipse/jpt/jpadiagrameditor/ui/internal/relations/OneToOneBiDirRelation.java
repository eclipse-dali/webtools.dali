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


public class OneToOneBiDirRelation extends OneToOneRelation implements IBidirectionalRelation{
	public OneToOneBiDirRelation(IJPAEditorFeatureProvider fp, JavaPersistentType owner, 
								 JavaPersistentType inverse, 
								 String ownerAttributeName,
								 String inverseAttributeName,
								 boolean createAttribs,
								 ICompilationUnit ownerCU,
								 ICompilationUnit inverseCU) {
		super(owner, inverse);
		this.ownerAttributeName = ownerAttributeName;
		this.inverseAttributeName = inverseAttributeName;
		if (createAttribs)
			createRelation(fp, ownerCU, inverseCU);
	}

	public JavaPersistentAttribute getOwnerAnnotatedAttribute() {
		return ownerAnnotatedAttribute;
	}

	public void setOwnerAnnotatedAttribute(
			JavaPersistentAttribute ownerAnnotatedAttribute) {
		this.ownerAnnotatedAttribute = ownerAnnotatedAttribute;
	}

	public JavaPersistentAttribute getInverseAnnotatedAttribute() {
		return inverseAnnotatedAttribute;
	}

	public void setInverseAnnotatedAttribute(
			JavaPersistentAttribute inverseAnnotatedAttribute) {
		this.inverseAnnotatedAttribute = inverseAnnotatedAttribute;
	}

	private void createRelation(IJPAEditorFeatureProvider fp, ICompilationUnit ownerCU, ICompilationUnit inverseCU) {
		ownerAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(fp, owner, inverse, ownerCU, inverseCU, false, null);
		inverseAnnotatedAttribute = JPAEditorUtil.addAnnotatedAttribute(fp, inverse, owner, inverseCU, ownerCU, false, null);
		
		JpaArtifactFactory.instance().addOneToOneBidirectionalRelation(fp, owner, ownerAnnotatedAttribute, inverse, inverseAnnotatedAttribute);		
	} 	
		
	public RelDir getRelDir() {
		return RelDir.BI;
	}	

}