/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public abstract class TypeMappingTranslator extends Translator 
	implements OrmXmlMapper
{	
	protected static final OrmPackage JPA_CORE_XML_PKG = 
		OrmPackage.eINSTANCE;
	protected static final OrmFactory JPA_CORE_XML_FACTORY =
		OrmFactory.eINSTANCE;
	
	
	private Translator[] children;
	
	
	public TypeMappingTranslator(String domNameAndPath) {
		super(domNameAndPath, JPA_CORE_XML_PKG.getEntityMappingsInternal_TypeMappings());
		dependencyFeature = JPA_CORE_XML_PKG.getXmlTypeMapping_PersistentType();
	}
	
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
	
	protected abstract Translator[] createChildren();
	
	protected Translator createJavaClassTranslator() {
		return new TypeJavaClassTranslator();
	}
	
	protected Translator createMetadataCompleteTranslator() {
		return new BooleanEnumeratorTranslator(METADATA_COMPLETE, JPA_CORE_XML_PKG.getXmlTypeMapping_MetadataComplete(), DOM_ATTRIBUTE);
	}
	
	protected Translator createAccessTypeTranslator() {
		return new EnumeratorTranslator(ACCESS, JPA_CORE_XML_PKG.getXmlTypeMapping_SpecifiedAccess(), DOM_ATTRIBUTE);
	}
	
	protected Translator createPersistentAttributesTranslator() {
		return new AttributeMappingsTranslator();
	}
	

	protected Translator createPlaceHolderTranslator(String domNameAndPath) {
		return new Translator(domNameAndPath, (EStructuralFeature) null);
	}	

}
