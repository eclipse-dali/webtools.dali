/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.orm.translators.AttributesTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EclipseLinkAttributesTranslator extends AttributesTranslator
	implements EclipseLinkOrmXmlMapper
{
	public EclipseLinkAttributesTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			createIdTranslator(),
			createEmbeddedIdTranslator(),
			createBasicTranslator(),
			createVersionTranslator(),
			createManyToOneTranslator(),
			createOneToManyTranslator(),
			createOneToOneTranslator(),
			createManyToManyTranslator(),
			createEmbeddedTranslator(),
			createTransientTranslator()
		};
	}
	
	@Override
	protected Translator createIdTranslator() {
		return new EclipseLinkIdTranslator(ID, ORM_PKG.getAttributes_Ids());
	}
	
//	protected Translator createEmbeddedIdTranslator() {
//		return new EmbeddedIdTranslator(EMBEDDED_ID, ORM_PKG.getAttributes_EmbeddedIds());
//	}
	
	@Override
	protected Translator createBasicTranslator() {
		return new EclipseLinkBasicTranslator(BASIC, ORM_PKG.getAttributes_Basics());
	}
	
	@Override
	protected Translator createVersionTranslator() {
		return new EclipseLinkVersionTranslator(VERSION, ORM_PKG.getAttributes_Versions());
	}
	
	@Override
	protected Translator createManyToOneTranslator() {
		return new EclipseLinkManyToOneTranslator(MANY_TO_ONE, ORM_PKG.getAttributes_ManyToOnes());
	}
	
	@Override
	protected Translator createOneToOneTranslator() {
		return new EclipseLinkOneToOneTranslator(ONE_TO_ONE, ORM_PKG.getAttributes_OneToOnes());
	}
	
	@Override
	protected Translator createOneToManyTranslator() {
		return new EclipseLinkOneToManyTranslator(ONE_TO_MANY, ORM_PKG.getAttributes_OneToManys());
	}
	
	@Override
	protected Translator createManyToManyTranslator() {
		return new EclipseLinkManyToManyTranslator(MANY_TO_MANY, ORM_PKG.getAttributes_ManyToManys());
	}
	
//	protected Translator createEmbeddedTranslator() {
//		return new EmbeddedTranslator(EMBEDDED, ORM_PKG.getAttributes_Embeddeds());
//	}
//	
//	protected Translator createTransientTranslator() {
//		return new TransientTranslator(TRANSIENT, ORM_PKG.getAttributes_Transients());
//	}
}
