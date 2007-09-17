/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class PersistenceUnitDefaultsTranslator extends Translator 
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public PersistenceUnitDefaultsTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
	
	private Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createSchemaTranslator(),
			createCatalogTranslator(),
			createAccessTranslator(),
			createCascadePersistTranslator(),
			createEntityListenersTranslator()
		};
	}
	
	private Translator createSchemaTranslator() {
		return new Translator(SCHEMA, ORM_PKG.getPersistenceUnitDefaults_Schema());
	}
	
	private Translator createCatalogTranslator() {
		return new Translator(CATALOG, ORM_PKG.getPersistenceUnitDefaults_Catalog());
	}
	
	private Translator createAccessTranslator() {
		return new Translator(ACCESS, ORM_PKG.getPersistenceUnitDefaults_Access());
	}
	
	private Translator createCascadePersistTranslator() {
		return new Translator(CASCADE_PERSIST, ORM_PKG.getPersistenceUnitDefaults_CascadePersist());
	}
	
	private Translator createEntityListenersTranslator() {
		return new EntityListenersTranslator(ENTITY_LISTENERS, ORM_PKG.getPersistenceUnitDefaults_EntityListeners());
	}
}
