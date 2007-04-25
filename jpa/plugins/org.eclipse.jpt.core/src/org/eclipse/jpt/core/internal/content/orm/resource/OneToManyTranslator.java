/*******************************************************************************
 *  Copyright (c) 2006 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.content.orm.XmlOneToMany;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class OneToManyTranslator extends MultiRelationshipTranslator 
{
	public OneToManyTranslator() {
		super(ONE_TO_MANY);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		XmlOneToMany mapping = JPA_CORE_XML_FACTORY.createXmlOneToMany();
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

	protected Translator createMappedByTranslator() {
		return new Translator(MAPPED_BY, JpaCoreMappingsPackage.eINSTANCE.getINonOwningMapping_MappedBy(), DOM_ATTRIBUTE);
	}

}
