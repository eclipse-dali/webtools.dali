/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class ManyToOneTranslator extends SingleRelationshipTranslator 
{
	public ManyToOneTranslator() {
		super(MANY_TO_ONE);
	}
	
	@Override
	protected ISingleRelationshipMapping createMapping() {
		return JPA_CORE_XML_FACTORY.createXmlManyToOne();
	}
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createTargetEntityTranslator(),
			createFetchTypeTranslator(),
			createJoinColumnsTranslator(),
			createCascadeTranslator()
		};
	}

}
