/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class TableGeneratorTranslator extends Translator
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public TableGeneratorTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public Translator[] getChildren(Object target, int versionID) {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
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
			createUniqueConstraintTranslator(),
		};
	}
	
	private Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getTableGenerator_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createTableTranslator() {
		return new Translator(TABLE, ORM_PKG.getTableGenerator_Table(), DOM_ATTRIBUTE);
	}
	
	private Translator createCatalogTranslator() {
		return new Translator(CATALOG, ORM_PKG.getTableGenerator_Catalog(), DOM_ATTRIBUTE);
	}
	
	private Translator createSchemaTranslator() {
		return new Translator(SCHEMA, ORM_PKG.getTableGenerator_Schema(), DOM_ATTRIBUTE);
	}
	
	private Translator createPkColumnNameTranslator() {
		return new Translator(PK_COLUMN_NAME, ORM_PKG.getTableGenerator_PkColumnName(), DOM_ATTRIBUTE);
	}
	
	private Translator createValueColumnNameTranslator() {
		return new Translator(VALUE_COLUMN_NAME, ORM_PKG.getTableGenerator_ValueColumnName(), DOM_ATTRIBUTE);
	}
	
	private Translator createPkColumnValueTranslator() {
		return new Translator(PK_COLUMN_VALUE, ORM_PKG.getTableGenerator_PkColumnValue(), DOM_ATTRIBUTE);
	}
	
	private Translator createInitialValueTranslator() {
		return new Translator(INITIAL_VALUE, ORM_PKG.getTableGenerator_InitialValue(), DOM_ATTRIBUTE);
	}
	
	private Translator createAllocationSizeTranslator() {
		return new Translator(ALLOCATION_SIZE, ORM_PKG.getTableGenerator_AllocationSize(), DOM_ATTRIBUTE);
	}
	
	private Translator createUniqueConstraintTranslator() {
		return new UniqueConstraintTranslator(UNIQUE_CONSTRAINT, ORM_PKG.getTableGenerator_UniqueConstraints());
	}
}
