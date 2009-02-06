/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EclipseLinkEntityTranslator extends org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators.EclipseLinkEntityTranslator
	implements EclipseLink1_1OrmXmlMapper
{
	public EclipseLinkEntityTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			createNameTranslator(),
			createClassTranslator(),
			createAccessTranslator(),
			createMetadataCompleteTranslator(),
			createReadOnlyTranslator(),
			createExistenceCheckingTranslator(),
			createExcludeDefaultMappingsTranslator(),
			createDescriptionTranslator(),
			createCustomizerTranslator(),
			createChangeTrackingTranslator(),
			createTableTranslator(),
			createSecondaryTableTranslator(),
			createPrimaryKeyJoinColumnTranslator(),
			createIdClassTranslator(),
			createPrimaryKeyTranslator(),
			createInheritanceTranslator(),
			createDiscriminatorValueTranslator(),
			createDiscriminatorColumnTranslator(),
			createOptimisticLockingTranslator(),
			createCacheTranslator(),
			createConverterTranslator(),
			createTypeConverterTranslator(),
			createObjectTypeConverterTranslator(),
			createStructConverterTranslator(),
			createCopyPolicyTranslator(),
			createInstantiationCopyPolicyTranslator(),
			createCloneCopyPolicyTranslator(),
			createSequenceGeneratorTranslator(),
			createTableGeneratorTranslator(),
			createNamedQueryTranslator(),
			createNamedNativeQueryTranslator(),
			createNamedStoredProcedureQueryTranslator(),
			createSqlResultSetMappingTranslator(),
			createExcludeDefaultListenersTranslator(),
			createExcludeSuperclassListenersTranslator(),
			createEntityListenersTranslator(),
			createPrePersistTranslator(),
			createPostPersistTranslator(),
			createPreRemoveTranslator(),
			createPostRemoveTranslator(),
			createPreUpdateTranslator(),
			createPostUpdateTranslator(),
			createPostLoadTranslator(),
			createPropertyTranslator(),
			createAttributeOverrideTranslator(),
			createAssociationOverrideTranslator(),
			createAttributesTranslator(),
		};
	}

	@Override
	protected Translator createAttributesTranslator() {
		return new EclipseLinkAttributesTranslator(ATTRIBUTES, ORM_PKG.getXmlTypeMapping_Attributes());
	}
	
	//placeholder
	protected Translator createPrimaryKeyTranslator() {
		return new Translator(PRIMARY_KEY, (EClass) null);
	}	
}
