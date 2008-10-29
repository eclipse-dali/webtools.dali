/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class DiscriminatorColumnTranslator extends Translator
	implements OrmXmlMapper
{	
	private Translator[] children;	
	
	
	public DiscriminatorColumnTranslator(String domNameAndPath, EStructuralFeature aFeature) {
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
			createDiscrminiatorTypeTranslator(),
			createColumnDefinitionTranslator(),
			createLengthTranslator()
		};
	}
	
	private Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getXmlNamedColumn_Name(), DOM_ATTRIBUTE);
	}
	
	protected Translator createDiscrminiatorTypeTranslator() {
		return new Translator(DISCRIMINATOR_TYPE, ORM_PKG.getXmlDiscriminatorColumn_DiscriminatorType(), DOM_ATTRIBUTE);
	}
	
	private Translator createColumnDefinitionTranslator() {
		return new Translator(COLUMN_DEFINITION, ORM_PKG.getXmlNamedColumn_ColumnDefinition(), DOM_ATTRIBUTE);
	}
	
	protected Translator createLengthTranslator() {
		return new Translator(LENGTH, ORM_PKG.getXmlDiscriminatorColumn_Length(), DOM_ATTRIBUTE);
	}
}
