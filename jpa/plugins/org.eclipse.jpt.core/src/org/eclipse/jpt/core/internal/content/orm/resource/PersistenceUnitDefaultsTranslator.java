/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class PersistenceUnitDefaultsTranslator extends Translator implements OrmXmlMapper
{
	private Translator[] children;

	private PersistenceUnitMetadata persistenceUnitMetadata;
	
	public PersistenceUnitDefaultsTranslator() {
		super(PERSISTENCE_UNIT_DEFAULTS, OrmPackage.eINSTANCE.getPersistenceUnitMetadataForXml_PersistenceUnitDefaultsForXml());
	}
	
	public Translator[] getChildren(Object target, int versionID) {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
	
	private Translator[] createChildren() {
		return new Translator[] {
			createSchemaTranslator(),
			createCatalogTranslator(),
			createAccessTranslator(),
			createCascadePersistTranslator(),
			createPlaceHolderTranslator(PERSISTENCE_UNIT_DEFAULTS__ENTITY_LISTENERS),
		};
	}

	private Translator createPlaceHolderTranslator(String domNameAndPath) {
		return new Translator(domNameAndPath, (EStructuralFeature) null);
	}	
	
	private Translator createSchemaTranslator() {
		return new Translator(SCHEMA, OrmPackage.eINSTANCE.getPersistenceUnitDefaultsForXml_SchemaForXml());
	}
	
	private Translator createCatalogTranslator() {
		return new Translator(CATALOG, OrmPackage.eINSTANCE.getPersistenceUnitDefaultsForXml_CatalogForXml());
	}

	private Translator createAccessTranslator() {
		return new AccessTypeElementTranslator(ACCESS, OrmPackage.eINSTANCE.getPersistenceUnitDefaultsForXml_AccessForXml(), NO_STYLE);
	}
	
	private Translator createCascadePersistTranslator() {
		return new EmptyTagBooleanTranslator(CASCADE_PERSIST, OrmPackage.eINSTANCE.getPersistenceUnitDefaultsForXml_CascadePersistForXml());
	}

	@Override
	//This is called when the persistence-unit-defaults tag is added
	//by hand through the xml editor.  In this situation PersistenceUnitMetaData.persistenceUnitDefaultsForXml
	//is null and we don't want to create a new PersistenceUnitDefaults object, we just want to 
	//reuse the one already created by our internal model
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return getPersistenceUnitMetadata().getPersistenceUnitDefaults();
	}
	
	private PersistenceUnitMetadata getPersistenceUnitMetadata() {
		return this.persistenceUnitMetadata;
	}
	
	void setPersistenceUnitMetadata(PersistenceUnitMetadata persistenceUnitMetadata) {
		this.persistenceUnitMetadata = persistenceUnitMetadata;
	}
	
	@Override
	//if my object has containtment = true then the translators clear out all adapters
	//even the ones it didn't add.  if i have containtment = false then the translators 
	//are not removing the adapter they added
	// the shared flag gets set when containment=true.  isShared() is called in
	//EMF2DOMAdapterImpl.primUpdateMOFFeature which then calls removeMOFValue() if
	//isShared() returns false.  removeMOFValue() removes the EMF2DOMSSEAdapter
	//and then since isContainment() = false the call to ExtendedEcoreUtil.unload(value);
	// does not occur.  In that method the adapters are all cleared out thus my UI
	//no longer has listeners on the model. Talk to KFM about this and maybe
	//she'll remember the details. \
	
	//Need to enter a bug against the translators as well.  Now one other
	//adapter is not being removed, the AnnotationsAdapter, this is being added by the translators
	//and never removed.  not sure 
	public boolean isShared() {
		return false;
	}

}
