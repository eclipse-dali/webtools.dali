/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class TypeConverterTranslator extends Translator
	implements EclipseLinkOrmXmlMapper
{	
	private Translator[] children;	
	
	
	public TypeConverterTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, END_TAG_NO_INDENT);
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
			createDataTypeTranslator(),
			createObjectTypeTranslator(),
		};
	}
	
	protected Translator createNameTranslator() {
		return new Translator(TYPE_CONVERTER__NAME, ECLIPSELINK_ORM_PKG.getXmlNamedConverter_Name(), DOM_ATTRIBUTE);
	}
	
	protected Translator createDataTypeTranslator() {
		return new Translator(TYPE_CONVERTER__DATA_TYPE, ECLIPSELINK_ORM_PKG.getXmlTypeConverter_DataType(), DOM_ATTRIBUTE);
	}
	
	protected Translator createObjectTypeTranslator() {
		return new Translator(TYPE_CONVERTER__OBJECT_TYPE, ECLIPSELINK_ORM_PKG.getXmlTypeConverter_ObjectType(), DOM_ATTRIBUTE);
	}
}
