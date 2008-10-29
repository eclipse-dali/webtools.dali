/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
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

public class StructConverterTranslator extends Translator
	implements EclipseLinkOrmXmlMapper
{	
	private Translator[] children;	
	
	
	public StructConverterTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, END_TAG_NO_INDENT);
	}
	
	@Override
	public EObject createEMFObject(@SuppressWarnings("unused") String nodeName, @SuppressWarnings("unused") String readAheadName) {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverterImpl();
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
			createConverterTranslator(),
		};
	}
	
	protected Translator createNameTranslator() {
		return new Translator(STRUCT_CONVERTER__NAME, ECLIPSELINK_ORM_PKG.getXmlNamedConverter_Name(), DOM_ATTRIBUTE);
	}
	
	protected Translator createConverterTranslator() {
		return new Translator(STRUCT_CONVERTER__CONVERTER, ECLIPSELINK_ORM_PKG.getXmlStructConverter_Converter(), DOM_ATTRIBUTE);
	}

}
