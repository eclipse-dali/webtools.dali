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
	
	@Override
	public Translator[] getChildren(Object target, int versionID) {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
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
		return new Translator(SCHEMA, ORM_PKG.getXmlPersistenceUnitDefaults_Schema());
	}
	
	private Translator createCatalogTranslator() {
		return new Translator(CATALOG, ORM_PKG.getXmlPersistenceUnitDefaults_Catalog());
	}
	
	private Translator createAccessTranslator() {
		return new Translator(ACCESS, ORM_PKG.getXmlPersistenceUnitDefaults_Access());
	}
	
	private Translator createCascadePersistTranslator() {
		return new EmptyTagBooleanTranslator(CASCADE_PERSIST, ORM_PKG.getXmlPersistenceUnitDefaults_CascadePersist());
	}
	
	private Translator createEntityListenersTranslator() {
		return new EntityListenersTranslator(ENTITY_LISTENERS, ORM_PKG.getXmlPersistenceUnitDefaults_EntityListeners());
	}
}
