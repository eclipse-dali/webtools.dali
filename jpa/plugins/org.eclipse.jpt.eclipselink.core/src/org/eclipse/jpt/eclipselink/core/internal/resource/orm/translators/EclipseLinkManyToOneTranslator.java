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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.orm.translators.ManyToOneTranslator;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EclipseLinkManyToOneTranslator extends ManyToOneTranslator
	implements EclipseLinkOrmXmlMapper
{
	public EclipseLinkManyToOneTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlManyToOne();
	}
		
	protected Translator[] createChildren() {
		return new Translator[] {
			createNameTranslator(),
			createTargetEntityTranslator(),
			createFetchTranslator(),
			createOptionalTranslator(),
			createJoinColumnTranslator(),
			createJoinTableTranslator(),
			createCascadeTranslator(),
			createJoinFetchTranslator()
		};
	}
	
	protected Translator createJoinFetchTranslator() {
		return new Translator(JOIN_FETCH, ECLIPSELINK_ORM_PKG.getXmlJoinFetch_JoinFetch());
	}
}
