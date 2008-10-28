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
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EclipseLinkChangeTrackingTranslator extends Translator
	implements EclipseLinkOrmXmlMapper
{
	private Translator[] children;	
	
	
	public EclipseLinkChangeTrackingTranslator(String domNameAndPath, EStructuralFeature aFeature, int style) {
		super(domNameAndPath, aFeature, style);
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
			createTypeTranslator()
		};
	}
	
	protected Translator createTypeTranslator() {
		return new Translator(TYPE, ECLIPSELINK_ORM_PKG.getXmlChangeTracking_Type(), Translator.DOM_ATTRIBUTE);
	}
}
