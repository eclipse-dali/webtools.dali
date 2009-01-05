/*******************************************************************************
 *  Copyright (c) 2009 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class PropertyTranslator extends Translator
	implements EclipseLinkOrmXmlMapper
{	
	private Translator[] children;	
	
	
	public PropertyTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, END_TAG_NO_INDENT);
	}
	
	@Override
	public EObject createEMFObject(@SuppressWarnings("unused") String nodeName, @SuppressWarnings("unused") String readAheadName) {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlProperty();
	}
	
	@Override
	protected Translator[] getChildren() {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
		
	protected Translator[] createChildren() {
		return new Translator[] {
			createNameTranslator(),
			createValueTranslator(),
			createValueTypeTranslator(),
		};
	}
	
	protected Translator createNameTranslator() {
		return new Translator(PROPERTY__NAME, ECLIPSELINK_ORM_PKG.getXmlProperty_Name(), DOM_ATTRIBUTE);
	}
	
	protected Translator createValueTranslator() {
		return new Translator(PROPERTY__VALUE, ECLIPSELINK_ORM_PKG.getXmlProperty_Value(), DOM_ATTRIBUTE);
	}
	
	protected Translator createValueTypeTranslator() {
		return new Translator(PROPERTY__VALUE_TYPE, ECLIPSELINK_ORM_PKG.getXmlProperty_ValueType(), DOM_ATTRIBUTE);
	}
}
