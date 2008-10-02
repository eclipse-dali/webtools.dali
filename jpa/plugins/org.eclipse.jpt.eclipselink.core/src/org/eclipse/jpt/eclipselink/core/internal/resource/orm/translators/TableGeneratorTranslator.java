/*******************************************************************************
 *  Copyright (c) 2007, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class TableGeneratorTranslator extends Translator
	implements EclipseLinkOrmXmlMapper
{
	private Translator[] children;	
	
	
	public TableGeneratorTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlTableGeneratorImpl();
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
		return new Translator(NAME, ECLIPSELINK_ORM_PKG.getXmlGenerator_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createTableTranslator() {
		return new Translator(TABLE, ECLIPSELINK_ORM_PKG.getXmlTableGenerator_Table(), DOM_ATTRIBUTE);
	}
	
	private Translator createCatalogTranslator() {
		return new Translator(CATALOG, ECLIPSELINK_ORM_PKG.getXmlTableGenerator_Catalog(), DOM_ATTRIBUTE);
	}
	
	private Translator createSchemaTranslator() {
		return new Translator(SCHEMA, ECLIPSELINK_ORM_PKG.getXmlTableGenerator_Schema(), DOM_ATTRIBUTE);
	}
	
	private Translator createPkColumnNameTranslator() {
		return new Translator(PK_COLUMN_NAME, ECLIPSELINK_ORM_PKG.getXmlTableGenerator_PkColumnName(), DOM_ATTRIBUTE);
	}
	
	private Translator createValueColumnNameTranslator() {
		return new Translator(VALUE_COLUMN_NAME, ECLIPSELINK_ORM_PKG.getXmlTableGenerator_ValueColumnName(), DOM_ATTRIBUTE);
	}
	
	private Translator createPkColumnValueTranslator() {
		return new Translator(PK_COLUMN_VALUE, ECLIPSELINK_ORM_PKG.getXmlTableGenerator_PkColumnValue(), DOM_ATTRIBUTE);
	}
	
	private Translator createInitialValueTranslator() {
		return new Translator(INITIAL_VALUE, ECLIPSELINK_ORM_PKG.getXmlGenerator_InitialValue(), DOM_ATTRIBUTE);
	}
	
	private Translator createAllocationSizeTranslator() {
		return new Translator(ALLOCATION_SIZE, ECLIPSELINK_ORM_PKG.getXmlGenerator_AllocationSize(), DOM_ATTRIBUTE);
	}
	
	private Translator createUniqueConstraintTranslator() {
		return new UniqueConstraintTranslator(UNIQUE_CONSTRAINT, ECLIPSELINK_ORM_PKG.getXmlTableGenerator_UniqueConstraints());
	}
}
