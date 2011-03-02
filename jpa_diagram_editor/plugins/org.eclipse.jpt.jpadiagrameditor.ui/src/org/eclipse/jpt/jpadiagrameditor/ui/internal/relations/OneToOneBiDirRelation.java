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


public class OneToOneBiDirRelation extends OneToOneRelation implements BidirectionalRelation{
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
		String name = JPAEditorUtil.cutFromLastDot(inverse.getName());
		String actName = JPAEditorUtil.cutFromLastDot(JpaArtifactFactory.instance().getEntityName(inverse));

		String nameWithNonCapitalLetter = JPAEditorUtil.decapitalizeFirstLetter(name);
		String actNameWithNonCapitalLetter = JPAEditorUtil.decapitalizeFirstLetter(actName);
		
		if (JpaArtifactFactory.instance().isMethodAnnotated(owner)) {
			nameWithNonCapitalLetter = JPAEditorUtil.produceValidAttributeName(name);
			actNameWithNonCapitalLetter = JPAEditorUtil.produceValidAttributeName(actName);
		}
		nameWithNonCapitalLetter = JPAEditorUtil.produceUniqueAttributeName(owner, nameWithNonCapitalLetter);
		actNameWithNonCapitalLetter = JPAEditorUtil.produceUniqueAttributeName(owner, actNameWithNonCapitalLetter); 
		
		ownerAnnotatedAttribute = JpaArtifactFactory.instance().addAttribute(fp, owner, inverse, 
																			 nameWithNonCapitalLetter, 
																			 actNameWithNonCapitalLetter, false, 
																			 ownerCU,
																			 inverseCU);
		
		name = JPAEditorUtil.cutFromLastDot(owner.getName());
		actName = JPAEditorUtil.cutFromLastDot(JpaArtifactFactory.instance().getEntityName(owner));
		nameWithNonCapitalLetter = JPAEditorUtil.decapitalizeFirstLetter(name);
		actNameWithNonCapitalLetter = JPAEditorUtil.decapitalizeFirstLetter(actName);		
		
		if (JpaArtifactFactory.instance().isMethodAnnotated(inverse)) {
			nameWithNonCapitalLetter = JPAEditorUtil.produceValidAttributeName(name);
			actNameWithNonCapitalLetter = JPAEditorUtil.produceValidAttributeName(actName);
		}
		nameWithNonCapitalLetter = JPAEditorUtil.produceUniqueAttributeName(inverse, nameWithNonCapitalLetter); 
		actNameWithNonCapitalLetter = JPAEditorUtil.produceUniqueAttributeName(inverse, actNameWithNonCapitalLetter); 
		inverseAnnotatedAttribute = JpaArtifactFactory.instance().addAttribute(fp, inverse, owner, 
																			   nameWithNonCapitalLetter, actNameWithNonCapitalLetter, 
																			   false, 
																			   inverseCU,
																			   ownerCU);
		
		JpaArtifactFactory.instance().addOneToOneBidirectionalRelation(fp, owner, ownerAnnotatedAttribute, inverse, inverseAnnotatedAttribute);		
	} 	
		
	public RelDir getRelDir() {
		return RelDir.BI;
	}	

}