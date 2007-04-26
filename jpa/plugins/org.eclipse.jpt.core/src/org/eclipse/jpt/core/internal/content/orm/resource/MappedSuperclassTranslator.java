/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class MappedSuperclassTranslator extends TypeMappingTranslator
{	
	protected static final OrmPackage XML_PKG = 
		OrmPackage.eINSTANCE;
	
	
	public MappedSuperclassTranslator() {
		super(MAPPED_SUPERCLASS);
	}
	
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return JPA_CORE_XML_FACTORY.createXmlMappedSuperclass();
	}
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createJavaClassTranslator(),
			createAccessTypeTranslator(),
			createMetadataCompleteTranslator(),
			createPlaceHolderTranslator(ENTITY__DESCRIPTION),
			createPlaceHolderTranslator(ENTITY__ID_CLASS),
			createPlaceHolderTranslator(ENTITY__EXCLUDE_DEFAULT_LISTENERS),
			createPlaceHolderTranslator(ENTITY__EXCLUDE_SUPERCLASS_LISTENERS),
			createPlaceHolderTranslator(ENTITY__ENTITY_LISTENERS),
			createPlaceHolderTranslator(ENTITY__PRE_PERSIST),
			createPlaceHolderTranslator(ENTITY__POST_PERSIST),
			createPlaceHolderTranslator(ENTITY__PRE_REMOVE),
			createPlaceHolderTranslator(ENTITY__POST_REMOVE),
			createPlaceHolderTranslator(ENTITY__PRE_UPDATE),
			createPlaceHolderTranslator(ENTITY__POST_UPDATE),
			createPlaceHolderTranslator(ENTITY__POST_LOAD),
			createPersistentAttributesTranslator()
		};
	}


}
