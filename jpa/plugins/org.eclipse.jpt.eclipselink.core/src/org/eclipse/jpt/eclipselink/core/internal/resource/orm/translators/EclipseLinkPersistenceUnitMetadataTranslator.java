/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.common.translators.EmptyTagBooleanTranslator;
import org.eclipse.jpt.core.internal.resource.orm.translators.PersistenceUnitMetadataTranslator;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EclipseLinkPersistenceUnitMetadataTranslator extends PersistenceUnitMetadataTranslator implements EclipseLinkOrmXmlMapper
{
	public EclipseLinkPersistenceUnitMetadataTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}

	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata();
	}
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			createXmlMappingMetadataCompleteTranslator(),
			createExcludeDefaultMappingsTranslator(),
			createPersistenceUnitDefaultsTranslator(),
		};
	}

	protected Translator createExcludeDefaultMappingsTranslator() {
		return new EmptyTagBooleanTranslator(EXCLUDE_DEFAULT_MAPPINGS, ECLIPSELINK_ORM_PKG.getXmlPersistenceUnitMetadata_ExcludeDefaultMappings());
	}
}
