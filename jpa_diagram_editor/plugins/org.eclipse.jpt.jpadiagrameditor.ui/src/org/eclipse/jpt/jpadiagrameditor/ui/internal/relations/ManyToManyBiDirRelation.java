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


public class ManyToManyBiDirRelation extends ManyToManyRelation implements BidirectionalRelation {
	
	public ManyToManyBiDirRelation(IJPAEditorFeatureProvider fp, JavaPersistentType owner, 
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


	/* (non-Javadoc)
	 * @see org.eclipse.jpt.jpadiagrameditor.ui.relations.BidirectionalRelation#getOwnerAnnotatedAttribute()
	 */
	public JavaPersistentAttribute getOwnerAnnotatedAttribute() {
		return ownerAnnotatedAttribute;
	}

	public void setOwnerAnnotatedAttribute(
			JavaPersistentAttribute ownerAnnotatedAttribute) {
		this.ownerAnnotatedAttribute = ownerAnnotatedAttribute;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jpt.jpadiagrameditor.ui.relations.BidirectionalRelation#getInverseAnnotatedAttribute()
	 */
	public JavaPersistentAttribute getInverseAnnotatedAttribute() {
		return inverseAnnotatedAttribute;
	}

	public void setInverseAnnotatedAttribute(
			JavaPersistentAttribute inverseAnnotatedAttribute) {
		this.inverseAnnotatedAttribute = inverseAnnotatedAttribute;
	}	
	
	private void createRelation(IJPAEditorFeatureProvider fp, ICompilationUnit ownerCU, ICompilationUnit inverseCU) {
		String inverseAttributeName = JPAEditorUtil.cutFromLastDot(inverse.getName());
		String actInverseAttributeName = JPAEditorUtil.cutFromLastDot(JpaArtifactFactory.instance().getEntityName(inverse));
		
		String nameWithNonCapitalLetter = JPAEditorUtil.decapitalizeFirstLetter(inverseAttributeName);
		String actNameWithNonCapitalLetter = JPAEditorUtil.decapitalizeFirstLetter(actInverseAttributeName);
		
		if (JpaArtifactFactory.instance().isMethodAnnotated(owner)) {
			nameWithNonCapitalLetter = JPAEditorUtil.produceValidAttributeName(inverseAttributeName);
			actNameWithNonCapitalLetter = JPAEditorUtil.produceValidAttributeName(actInverseAttributeName);
		}
		nameWithNonCapitalLetter = JPAEditorUtil.produceUniqueAttributeName(owner, nameWithNonCapitalLetter);
		actNameWithNonCapitalLetter = JPAEditorUtil.produceUniqueAttributeName(owner, actNameWithNonCapitalLetter); 
		
		ownerAnnotatedAttribute = JpaArtifactFactory.instance().addAttribute(fp, owner, inverse, 
																				   nameWithNonCapitalLetter, 
																				   actNameWithNonCapitalLetter, 
																				   true,
																				   ownerCU,
																				   inverseCU);
		
		String ownerAttributeName = JPAEditorUtil.cutFromLastDot(owner.getName());
		String actOwnerAttributeName = JPAEditorUtil.cutFromLastDot(JpaArtifactFactory.instance().getEntityName(owner));
		nameWithNonCapitalLetter = JPAEditorUtil.decapitalizeFirstLetter(ownerAttributeName);
		actNameWithNonCapitalLetter = JPAEditorUtil.decapitalizeFirstLetter(actOwnerAttributeName);				
		
		if (JpaArtifactFactory.instance().isMethodAnnotated(inverse)) {
			nameWithNonCapitalLetter = JPAEditorUtil.produceValidAttributeName(ownerAttributeName);
			actNameWithNonCapitalLetter = JPAEditorUtil.produceValidAttributeName(actOwnerAttributeName);
		}
		nameWithNonCapitalLetter = JPAEditorUtil.produceUniqueAttributeName(inverse, nameWithNonCapitalLetter); 
		actNameWithNonCapitalLetter = JPAEditorUtil.produceUniqueAttributeName(inverse, actNameWithNonCapitalLetter); 
		
		inverseAnnotatedAttribute = JpaArtifactFactory.instance().addAttribute(fp, inverse, owner, 
																			   nameWithNonCapitalLetter, 
																			   actNameWithNonCapitalLetter, 
																			   true, 
																			   inverseCU,
																			   ownerCU);
		
		JpaArtifactFactory.instance().addManyToManyBidirectionalRelation(fp, owner, ownerAnnotatedAttribute, inverse, inverseAnnotatedAttribute);		
	} 	
	
	public RelDir getRelDir() {
		return RelDir.BI;
	}
		
}