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
import org.eclipse.jpt.core.internal.resource.orm.translators.BasicTranslator;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EclipseLinkBasicTranslator extends BasicTranslator
	implements EclipseLinkOrmXmlMapper
{
	public EclipseLinkBasicTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlBasicImpl();
	}
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			createNameTranslator(),
			createFetchTranslator(),
			createOptionalTranslator(),
			createMutableTranslator(),
			createColumnTranslator(), 
			createLobTranslator(),
			createTemporalTranslator(),
			createEnumeratedTranslator(),
			createConvertTranslator(),
			createConverterTranslator(),
			createTypeConverterTranslator(),
			createObjectTypeConverterTranslator(),
			createStructConverterTranslator(),
			
		};
	}
	
	protected Translator createMutableTranslator() {
		return new Translator(MUTABLE, ECLIPSELINK_ORM_PKG.getXmlMutable_Mutable(), DOM_ATTRIBUTE);
	}
		
	protected Translator createConvertTranslator() {
		return new Translator(CONVERT, ECLIPSELINK_ORM_PKG.getXmlConvertibleMapping_Convert());
	}
	
	protected Translator createConverterTranslator() {
		return new Translator(CONVERTER, ECLIPSELINK_ORM_PKG.getXmlConverterHolder_Converter());
	}
	
	protected Translator createTypeConverterTranslator() {
		return new Translator(TYPE_CONVERTER, ECLIPSELINK_ORM_PKG.getXmlConverterHolder_TypeConverter());
	}
	
	protected Translator createObjectTypeConverterTranslator() {
		return new Translator(OBJECT_TYPE_CONVERTER, ECLIPSELINK_ORM_PKG.getXmlConverterHolder_ObjectTypeConverter());
	}
	
	protected Translator createStructConverterTranslator() {
		return new Translator(STRUCT_CONVERTER, ECLIPSELINK_ORM_PKG.getXmlConverterHolder_StructConverter());
	}
}
