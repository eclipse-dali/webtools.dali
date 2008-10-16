/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.orm.translators.EntityTranslator;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EclipseLinkEntityTranslator extends EntityTranslator
	implements EclipseLinkOrmXmlMapper
{
	public EclipseLinkEntityTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlEntity();
	}
	
	
	protected Translator[] createChildren() {
		return new Translator[] {
			createNameTranslator(),
			createClassTranslator(),
			createAccessTranslator(),
			createMetadataCompleteTranslator(),
			createReadOnlyTranslator(),
			createDescriptionTranslator(),
			createTableTranslator(),
			createSecondaryTableTranslator(),
			createPrimaryKeyJoinColumnTranslator(),
			createIdClassTranslator(),
			createInheritanceTranslator(),
			createDiscriminatorValueTranslator(),
			createDiscriminatorColumnTranslator(),
			createSequenceGeneratorTranslator(),
			createTableGeneratorTranslator(),
			createNamedQueryTranslator(),
			createNamedNativeQueryTranslator(),
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
			createAttributeOverrideTranslator(),
			createAssociationOverrideTranslator(),
			createAttributesTranslator()
		};
	}
	
	protected Translator createReadOnlyTranslator() {
		return new Translator(READ_ONLY, ECLIPSELINK_ORM_PKG.getXmlReadOnly_ReadOnly(), DOM_ATTRIBUTE);
	}
}
