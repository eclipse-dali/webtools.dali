/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public abstract class AbstractTableTranslator extends Translator
	implements OrmXmlMapper
{		
	private Translator[] children;
	
	protected static final OrmPackage JPA_CORE_XML_PKG = 
		OrmPackage.eINSTANCE;
	protected static final OrmFactory JPA_CORE_XML_FACTORY =
		OrmFactory.eINSTANCE;
	protected static final JpaCoreMappingsPackage MAPPINGS_PKG = 
		JpaCoreMappingsPackage.eINSTANCE;
	
	public AbstractTableTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath,aFeature);
	}
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
		
	
	protected abstract Translator[] createChildren();

	protected Translator createNameTranslator() {
		return new Translator(NAME, JPA_CORE_XML_PKG.getAbstractXmlTable_SpecifiedNameForXml(), DOM_ATTRIBUTE);
	}
	
	protected Translator createCatalogTranslator() {
		return new Translator(CATALOG, JPA_CORE_XML_PKG.getAbstractXmlTable_SpecifiedCatalogForXml(), DOM_ATTRIBUTE);
	}
	
	protected Translator createSchemaTranslator() {
		return new Translator(SCHEMA, JPA_CORE_XML_PKG.getAbstractXmlTable_SpecifiedSchemaForXml(), DOM_ATTRIBUTE);
	}
	
	protected Translator createUniqueConstraintTranslator() {
		return new Translator(UNIQUE_CONSTRAINT, (EStructuralFeature) null);
		//TODO return new UniqueConstraintTranslator(UNIQUE_CONSTRAINT, MAPPINGS_PKG.getITable_UniqueConstraints());
	}
}
