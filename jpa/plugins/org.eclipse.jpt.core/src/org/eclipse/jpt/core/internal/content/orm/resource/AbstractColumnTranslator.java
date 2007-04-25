/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public abstract class AbstractColumnTranslator extends Translator
	implements OrmXmlMapper
{		
	private Translator[] children;	
	
	protected static final OrmPackage JPA_CORE_XML_PKG = 
		OrmPackage.eINSTANCE;
	protected static final OrmFactory JPA_CORE_XML_FACTORY =
		OrmFactory.eINSTANCE;
	protected static final JpaCoreMappingsPackage MAPPINGS_PKG = 
		JpaCoreMappingsPackage.eINSTANCE;
	
	public AbstractColumnTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, END_TAG_NO_INDENT);
	}
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
		
	
	protected abstract Translator[] createChildren();

	protected Translator createNameTranslator() {
		return new Translator(COLUMN__NAME, JPA_CORE_XML_PKG.getAbstractXmlNamedColumn_SpecifiedNameForXml(), DOM_ATTRIBUTE);
	}
	
	protected Translator createTableTranslator() {
		return new Translator(COLUMN__TABLE, JPA_CORE_XML_PKG.getAbstractXmlColumn_SpecifiedTableForXml(), DOM_ATTRIBUTE);
	}
	
	protected Translator createUniqueTranslator() {
		return new Translator(COLUMN__UNIQUE, JPA_CORE_XML_PKG.getAbstractXmlColumn_UniqueForXml(), DOM_ATTRIBUTE);
	}
	
	protected Translator createNullableTranslator() {
		return new Translator(COLUMN__NULLABLE, JPA_CORE_XML_PKG.getAbstractXmlColumn_NullableForXml(), DOM_ATTRIBUTE);
	}
	
	protected Translator createInsertableTranslator() {
		return new Translator(COLUMN__INSERTABLE, JPA_CORE_XML_PKG.getAbstractXmlColumn_InsertableForXml(), DOM_ATTRIBUTE);
	}
	
	protected Translator createUpdatableTranslator() {
		return new Translator(COLUMN__UPDATABLE, JPA_CORE_XML_PKG.getAbstractXmlColumn_UpdatableForXml(), DOM_ATTRIBUTE);
	}
	
	protected Translator createColumnDefinitionTranslator() {
		return new Translator(COLUMN__COLUMN_DEFINITION, JPA_CORE_XML_PKG.getAbstractXmlNamedColumn_ColumnDefinitionForXml(), DOM_ATTRIBUTE);
	}
}
