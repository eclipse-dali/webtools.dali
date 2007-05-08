/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.content.orm.XmlManyToMany;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class ManyToManyTranslator extends MultiRelationshipTranslator 
{
	public ManyToManyTranslator() {
		super(MANY_TO_MANY);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		XmlManyToMany mapping = JPA_CORE_XML_FACTORY.createXmlManyToMany();
		this.getJoinTableTranslator().setMapping(mapping);
		//this.getOrderByTranslator().setMapping(mapping);
		return mapping;
	}
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createTargetEntityTranslator(),
			createFetchTypeTranslator(),
			createMappedByTranslator(),
			createOrderByTranslator(),
			//getOrderByTranslator(),
			createMapKeyTranslator(),
			getJoinTableTranslator(),
			createCascadeTranslator()
		};
	}

}
