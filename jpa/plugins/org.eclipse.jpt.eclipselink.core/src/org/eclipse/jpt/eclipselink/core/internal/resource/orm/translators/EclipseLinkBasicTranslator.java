/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.orm.translators.BasicTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EclipseLinkBasicTranslator extends BasicTranslator
	implements EclipseLinkOrmXmlMapper
{
	public EclipseLinkBasicTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
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
			createPropertyTranslator(),
			createAccessMethodsTranslator()			
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
	
	protected Translator createPropertyTranslator() {
		return new PropertyTranslator(PROPERTY, ECLIPSELINK_ORM_PKG.getXmlBasic_Properties());
	}
	
	protected Translator createAccessMethodsTranslator() {
		return new AccessMethodsTranslator();
	}
}
