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
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class ConversionValueTranslator extends Translator
	implements EclipseLinkOrmXmlMapper
{	
	private Translator[] children;	
	
	
	public ConversionValueTranslator() {
		super(CONVERSION_VALUE, ECLIPSELINK_ORM_PKG.getXmlObjectTypeConverter_ConversionValues());
	}
	@Override
	public EObject createEMFObject(@SuppressWarnings("unused") String nodeName, @SuppressWarnings("unused") String readAheadName) {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlConversionValueImpl();
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
			createDataValueTranslator(),
			createObjectValueTranslator(),
		};
	}
	
	protected Translator createDataValueTranslator() {
		return new Translator(CONVERSION_VALUE__DATA_VALUE, ECLIPSELINK_ORM_PKG.getXmlConversionValue_DataValue(), DOM_ATTRIBUTE);
	}
	
	protected Translator createObjectValueTranslator() {
		return new Translator(CONVERSION_VALUE__OBJECT_VALUE, ECLIPSELINK_ORM_PKG.getXmlConversionValue_ObjectValue(), DOM_ATTRIBUTE);
	}
}
