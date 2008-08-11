/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.elorm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class TableTranslator extends Translator
	implements EclipseLinkOrmXmlMapper
{	
	private Translator[] children;	
	
	
	public TableTranslator(String domNameAndPath, EStructuralFeature aFeature) {
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
			createNameTranslator(),
			createCatalogTranslator(),
			createSchemaTranslator(),
			createUniqueConstraintTranslator()
		};
	}

	private Translator createNameTranslator() {
		return new Translator(NAME, ECLIPSELINK_ORM_PKG.getXmlBaseTable_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createCatalogTranslator() {
		return new Translator(CATALOG, ECLIPSELINK_ORM_PKG.getXmlBaseTable_Catalog(), DOM_ATTRIBUTE);
	}
	
	private Translator createSchemaTranslator() {
		return new Translator(SCHEMA, ECLIPSELINK_ORM_PKG.getXmlBaseTable_Schema(), DOM_ATTRIBUTE);
	}
	
	private Translator createUniqueConstraintTranslator() {
		return new UniqueConstraintTranslator(UNIQUE_CONSTRAINT, ECLIPSELINK_ORM_PKG.getXmlBaseTable_UniqueConstraints());
	}
}
