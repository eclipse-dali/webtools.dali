/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EclipseLinkTransformationTranslator extends org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators.TransformationTranslator
	implements EclipseLink1_1OrmXmlMapper
{

	public EclipseLinkTransformationTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			createNameTranslator(),
//			createFetchTranslator(),
//			createOptionalTranslator(),
			createAccessTranslator(),
//			createMutableTranslator(),
//			createReadTransformerTranslator(),
//			createWriteTransformerTranslator(),
			createPropertyTranslator(),
			createAccessMethodsTranslator()
		};
	}
	
	protected Translator createAccessTranslator() {
		return new Translator(ACCESS, ORM_PKG.getXmlAccessHolder_Access(), DOM_ATTRIBUTE);
	}

}
