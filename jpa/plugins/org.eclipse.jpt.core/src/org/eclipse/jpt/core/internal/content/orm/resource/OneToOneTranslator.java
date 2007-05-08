/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class OneToOneTranslator extends SingleRelationshipTranslator 
{
	public OneToOneTranslator() {
		super(ONE_TO_ONE);
	}
	
	@Override
	protected ISingleRelationshipMapping createMapping() {
		return JPA_CORE_XML_FACTORY.createXmlOneToOne();
	}
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createTargetEntityTranslator(),
			createFetchTypeTranslator(),
			createMappedByTranslator(),
			createPlaceHolderTranslator(ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS),
			createJoinColumnsTranslator(),
			createPlaceHolderTranslator(ONE_TO_ONE__JOIN_TABLE),
			createCascadeTranslator()
		};
	}
	private Translator createPlaceHolderTranslator(String domNameAndPath) {
		return new Translator(domNameAndPath, (EStructuralFeature) null);
	}	

}
