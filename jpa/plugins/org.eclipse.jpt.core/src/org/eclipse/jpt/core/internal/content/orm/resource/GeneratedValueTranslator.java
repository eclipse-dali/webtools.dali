/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class GeneratedValueTranslator extends Translator implements OrmXmlMapper
{
	protected static final JpaCoreMappingsPackage MAPPINGS_PKG = 
		JpaCoreMappingsPackage.eINSTANCE;
	protected static final OrmPackage JPA_CORE_XML_PKG = 
		OrmPackage.eINSTANCE;
	
	
	private Translator[] children;	
	
	public GeneratedValueTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, END_TAG_NO_INDENT);
	}
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
		
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createStrategyTranslator(),
			createGeneratorTranslator(),
		};
	}
	
	protected Translator createStrategyTranslator() {
		return new EnumeratorTranslator(GENERATED_VALUE__STRATEGY, MAPPINGS_PKG.getIGeneratedValue_Strategy(), DOM_ATTRIBUTE);
	}
	
	protected Translator createGeneratorTranslator() {
		return new Translator(GENERATED_VALUE__GENERATOR, MAPPINGS_PKG.getIGeneratedValue_Generator(), DOM_ATTRIBUTE);
	}

	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return OrmFactory.eINSTANCE.createXmlGeneratedValue();
	}
}
