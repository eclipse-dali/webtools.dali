/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class TableGeneratorTranslator extends GeneratorTranslator
{
	
	public TableGeneratorTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, NO_STYLE);
	}
	
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createTableTranslator(),
			createCatalogTranslator(),
			createSchemaTranslator(),
			createPkColumnNameTranslator(),
			createValueColumnNameTranslator(),
			createPkColumnValueTranslator(),
			createInitialValueTranslator(),
			createAllocationSizeTranslator(),
		};
	}

	protected Translator createTableTranslator() {
		return new Translator(TABLE_GENERATOR__TABLE, MAPPINGS_PKG.getITableGenerator_SpecifiedTable(), DOM_ATTRIBUTE);
	}
	
	protected Translator createCatalogTranslator() {
		return new Translator(TABLE_GENERATOR__CATALOG, MAPPINGS_PKG.getITableGenerator_SpecifiedCatalog(), DOM_ATTRIBUTE);
	}
	
	protected Translator createSchemaTranslator() {
		return new Translator(TABLE_GENERATOR__SCHEMA, MAPPINGS_PKG.getITableGenerator_SpecifiedSchema(), DOM_ATTRIBUTE);
	}
	
	protected Translator createPkColumnNameTranslator() {
		return new Translator(TABLE_GENERATOR__PK_COLUMN_NAME, MAPPINGS_PKG.getITableGenerator_SpecifiedPkColumnName(), DOM_ATTRIBUTE);
	}
	
	protected Translator createValueColumnNameTranslator() {
		return new Translator(TABLE_GENERATOR__VALUE_COLUMN_NAME, MAPPINGS_PKG.getITableGenerator_SpecifiedValueColumnName(), DOM_ATTRIBUTE);
	}
	
	protected Translator createPkColumnValueTranslator() {
		return new Translator(TABLE_GENERATOR__PK_COLUMN_VALUE, MAPPINGS_PKG.getITableGenerator_SpecifiedPkColumnValue(), DOM_ATTRIBUTE);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return OrmFactory.eINSTANCE.createXmlTableGenerator();
	}
}
