/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public abstract class GeneratorTranslator extends Translator implements OrmXmlMapper
{
	protected static final JpaCoreMappingsPackage MAPPINGS_PKG = 
		JpaCoreMappingsPackage.eINSTANCE;
	protected static final OrmPackage JPA_CORE_XML_PKG = 
		OrmPackage.eINSTANCE;
	
	
	private Translator[] children;	
	
	public GeneratorTranslator(String domNameAndPath, EStructuralFeature aFeature, int style) {
		super(domNameAndPath, aFeature, style);
	}
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
		
	protected abstract Translator[] createChildren();


	protected Translator createNameTranslator() {
		return new Translator(GENERATOR__NAME, MAPPINGS_PKG.getIGenerator_Name(), DOM_ATTRIBUTE);
	}
	
	protected Translator createInitialValueTranslator() {
		return new Translator(GENERATOR__INITIAL_VALUE, MAPPINGS_PKG.getIGenerator_SpecifiedInitialValue(), DOM_ATTRIBUTE);
	}
	
	protected Translator createAllocationSizeTranslator() {
		return new Translator(GENERATOR__ALLOCATION_SIZE, MAPPINGS_PKG.getIGenerator_SpecifiedAllocationSize(), DOM_ATTRIBUTE);
	}

}
