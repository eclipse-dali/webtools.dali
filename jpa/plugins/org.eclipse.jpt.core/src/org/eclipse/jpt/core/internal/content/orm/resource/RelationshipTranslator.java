/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public abstract class RelationshipTranslator extends AttributeMappingTranslator 
{
	public RelationshipTranslator(String domNameAndPath) {
		super(domNameAndPath, NO_STYLE);
	}
	
	protected Translator createTargetEntityTranslator() {
		return new Translator(TARGET_ENTITY, JpaCoreMappingsPackage.eINSTANCE.getIRelationshipMapping_SpecifiedTargetEntity(), DOM_ATTRIBUTE);
	}
	
	//placeholder until we support in our model, this allow us
	//to keep the elements in the proper order
	protected Translator createCascadeTranslator() {
		return new Translator(CASCADE , (EStructuralFeature) null);
	}

}
