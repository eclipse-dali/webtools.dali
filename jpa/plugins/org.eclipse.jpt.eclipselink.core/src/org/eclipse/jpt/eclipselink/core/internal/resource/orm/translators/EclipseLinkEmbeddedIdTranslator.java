/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserve9.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.orm.translators.EmbeddedIdTranslator;
import org.eclipse.jpt.core.internal.resource.persistence.translators.PropertyTranslator;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EclipseLinkEmbeddedIdTranslator extends EmbeddedIdTranslator
	implements EclipseLinkOrmXmlMapper
{
	public EclipseLinkEmbeddedIdTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlEmbeddedIdImpl();
	}
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			createNameTranslator(),
			createAttributeOverrideTranslator(),
			createPropertyTranslator(),
			createAccessMethodsTranslator()
		};
	}
		
	protected Translator createPropertyTranslator() {
		return new PropertyTranslator(PROPERTY, ECLIPSELINK_ORM_PKG.getXmlEmbeddedId_Properties());
	}
	
	protected Translator createAccessMethodsTranslator() {
		return new AccessMethodsTranslator();
	}
}
