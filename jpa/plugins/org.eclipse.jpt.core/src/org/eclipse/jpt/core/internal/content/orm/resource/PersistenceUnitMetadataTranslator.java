/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.content.orm.EntityMappings;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.content.orm.PersistenceUnitMetadata;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class PersistenceUnitMetadataTranslator extends Translator implements OrmXmlMapper
{
	private Translator[] children;

	private PersistenceUnitDefaultsTranslator persistenceUnitDefaultsTranslator;
	private EntityMappings entityMappings;
	
	public PersistenceUnitMetadataTranslator() {
		super(PERSISTENCE_UNIT_METADATA, OrmPackage.eINSTANCE.getEntityMappingsForXml_PersistenceUnitMetadataForXml());
	}
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
	
	private Translator[] createChildren() {
		return new Translator[] {
			createXmlMappingMetadataCompleteTranslator(),
			createPersistenceUnitDefaultsTranslator(),
		};
	}

	private Translator createXmlMappingMetadataCompleteTranslator() {
		return new EmptyTagBooleanTranslator(XML_MAPPING_METADATA_COMPLETE, OrmPackage.eINSTANCE.getPersistenceUnitMetadataForXml_XmlMappingMetadataCompleteForXml());
	}
	
	private Translator createPersistenceUnitDefaultsTranslator() {
		this.persistenceUnitDefaultsTranslator = new PersistenceUnitDefaultsTranslator();
		return this.persistenceUnitDefaultsTranslator;
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		PersistenceUnitMetadata persistenceUnitMetadata = getEntityMappings().getPersistenceUnitMetadata();
		this.persistenceUnitDefaultsTranslator.setPersistenceUnitMetadata(persistenceUnitMetadata);
		return persistenceUnitMetadata;
	}
	
	private EntityMappings getEntityMappings() {
		return this.entityMappings;
	}
	
	public void setEntityMappings(EntityMappings entityMappings) {
		this.entityMappings = entityMappings;
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
