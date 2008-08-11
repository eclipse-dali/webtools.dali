/*******************************************************************************
 *  Copyright (c) 2007, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.elorm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class GeneratedValueTranslator extends Translator 
	implements EclipseLinkOrmXmlMapper
{
	private Translator[] children;	
	
	
	public GeneratedValueTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, END_TAG_NO_INDENT);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlGeneratedValueImpl();
	}
	
	@Override
	public Translator[] getChildren(Object target, int versionID) {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
		
	protected Translator[] createChildren() {
		return new Translator[] {
			createStrategyTranslator(),
			createGeneratorTranslator()
		};
	}
	
	protected Translator createStrategyTranslator() {
		return new Translator(STRATEGY, ECLIPSELINK_ORM_PKG.getXmlGeneratedValue_Strategy(), DOM_ATTRIBUTE);
	}
	
	protected Translator createGeneratorTranslator() {
		return new Translator(GENERATOR, ECLIPSELINK_ORM_PKG.getXmlGeneratedValue_Generator(), DOM_ATTRIBUTE);
	}
}
