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

public class EclipseLinkVariableOneToOneTranslator extends org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators.VariableOneToOneTranslator
	implements EclipseLink1_1OrmXmlMapper
{
	public EclipseLinkVariableOneToOneTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			createNameTranslator(),
			createAccessTranslator()
		};
	}
	//263957 - access is missing from the schema for variable 1-1, but i will just go ahead and add support
	protected Translator createAccessTranslator() {
		return new Translator(ACCESS, ORM_PKG.getXmlAccessHolder_Access(), DOM_ATTRIBUTE);
	}

}
