/*******************************************************************************
 *  Copyright (c) 2007, 2009 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EmbeddableTranslator extends Translator
	implements OrmXmlMapper
{	
	private Translator[] children;	
	
	
	public EmbeddableTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
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
			createClassTranslator(),
			createAccessTranslator(),
			createMetadataCompleteTranslator(),
			createDescriptionTranslator(),
			createAttributesTranslator()
		};
	}
	
	protected Translator createClassTranslator() {
		return new Translator(CLASS, ORM_PKG.getXmlTypeMapping_ClassName(), DOM_ATTRIBUTE);
	}
	
	protected Translator createAccessTranslator() {
		return new Translator(ACCESS, ORM_PKG.getXmlTypeMapping_Access(), DOM_ATTRIBUTE);
	}
	
	protected Translator createMetadataCompleteTranslator() {
		return new Translator(METADATA_COMPLETE, ORM_PKG.getXmlTypeMapping_MetadataComplete(), DOM_ATTRIBUTE);
	}
	
	protected Translator createDescriptionTranslator() {
		return new Translator(DESCRIPTION, ORM_PKG.getXmlTypeMapping_Description());
	}
	
	protected Translator createAttributesTranslator() {
		return new AttributesTranslator(ATTRIBUTES, ORM_PKG.getXmlTypeMapping_Attributes());
	}
}
