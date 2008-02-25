/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class JoinTableTranslator extends Translator
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public JoinTableTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return OrmFactory.eINSTANCE.createJoinTableImpl();
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
			createCatalogTranslator(),
			createSchemaTranslator(),
			createJoinColumnTranslator(),
			createInverseJoinColumnTranslator(),
			createUniqueConstraintTranslator()
		};
	}
	
	private Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getAbstractTable_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createCatalogTranslator() {
		return new Translator(CATALOG, ORM_PKG.getAbstractTable_Catalog(), DOM_ATTRIBUTE);
	}
	
	private Translator createSchemaTranslator() {
		return new Translator(SCHEMA, ORM_PKG.getAbstractTable_Schema(), DOM_ATTRIBUTE);
	}
	
	private Translator createJoinColumnTranslator() {
		return new JoinColumnTranslator(JOIN_COLUMN, ORM_PKG.getJoinTable_JoinColumns());
	}
	
	private Translator createInverseJoinColumnTranslator() {
		return new JoinColumnTranslator(INVERSE_JOIN_COLUMN, ORM_PKG.getJoinTable_InverseJoinColumns());
	}
	
	private Translator createUniqueConstraintTranslator() {
		return new UniqueConstraintTranslator(UNIQUE_CONSTRAINT, ORM_PKG.getAbstractTable_UniqueConstraints());
	}
}
