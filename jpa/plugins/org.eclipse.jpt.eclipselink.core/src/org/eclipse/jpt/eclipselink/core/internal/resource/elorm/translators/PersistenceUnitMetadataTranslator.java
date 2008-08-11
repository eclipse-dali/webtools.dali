/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.elorm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.common.translators.EmptyTagBooleanTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class PersistenceUnitMetadataTranslator extends Translator implements EclipseLinkOrmXmlMapper
{
	private Translator[] children;	
	
	
	public PersistenceUnitMetadataTranslator(String domNameAndPath, EStructuralFeature aFeature) {
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
			createXmlMappingMetadataCompleteTranslator(),
			createPersistenceUnitDefaultsTranslator(),
		};
	}

	private Translator createXmlMappingMetadataCompleteTranslator() {
		return new EmptyTagBooleanTranslator(XML_MAPPING_METADATA_COMPLETE, ECLIPSELINK_ORM_PKG.getXmlPersistenceUnitMetadata_XmlMappingMetadataComplete());
	}
	
	private Translator createPersistenceUnitDefaultsTranslator() {
		return new PersistenceUnitDefaultsTranslator(PERSISTENCE_UNIT_DEFAULTS, ECLIPSELINK_ORM_PKG.getXmlPersistenceUnitMetadata_PersistenceUnitDefaults());
	}
}
