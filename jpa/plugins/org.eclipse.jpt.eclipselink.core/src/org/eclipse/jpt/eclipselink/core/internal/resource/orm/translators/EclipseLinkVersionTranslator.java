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
import org.eclipse.jpt.core.internal.resource.orm.translators.VersionTranslator;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EclipseLinkVersionTranslator extends VersionTranslator
	implements EclipseLinkOrmXmlMapper
{
	public EclipseLinkVersionTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlVersionImpl();
	}
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			createNameTranslator(),
			createMutableTranslator(),
			createColumnTranslator(), 
			createTemporalTranslator(),
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
		return new ConverterTranslator(CONVERTER, ECLIPSELINK_ORM_PKG.getXmlConverterHolder_Converter());
	}
	
	protected Translator createTypeConverterTranslator() {
		return new TypeConverterTranslator(TYPE_CONVERTER, ECLIPSELINK_ORM_PKG.getXmlConverterHolder_TypeConverter());
	}
	
	protected Translator createObjectTypeConverterTranslator() {
		return new ObjectTypeConverterTranslator(OBJECT_TYPE_CONVERTER, ECLIPSELINK_ORM_PKG.getXmlConverterHolder_ObjectTypeConverter());
	}
	
	protected Translator createStructConverterTranslator() {
		return new StructConverterTranslator(STRUCT_CONVERTER, ECLIPSELINK_ORM_PKG.getXmlConverterHolder_StructConverter());
	}
}
