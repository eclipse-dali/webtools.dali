/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public abstract class MultiRelationshipTranslator extends RelationshipTranslator 
{
	private JoinTableTranslator tableTranslator;
	
	//private OrderByRefTranslator orderByTranslator;

	public MultiRelationshipTranslator(String domNameAndPath) {
		super(domNameAndPath);
		this.tableTranslator = createTableTranslator();
		//this.orderByTranslator = createOrderByTranslator();
	}
	
	protected Translator createFetchTypeTranslator() {
		return new EnumeratorTranslator(FETCH, JpaCoreMappingsPackage.eINSTANCE.getIMultiRelationshipMapping_Fetch(), DOM_ATTRIBUTE);
	}
	
	protected JoinTableTranslator getJoinTableTranslator() {
		return this.tableTranslator;
	}
	
	protected JoinTableTranslator createTableTranslator() {
		this.tableTranslator = new JoinTableTranslator();
		return this.tableTranslator;
	}

//	protected OrderByRefTranslator getOrderByTranslator() {
//		return this.orderByTranslator;
//	}
//	
//	protected OrderByRefTranslator createOrderByTranslator() {
//		this.orderByTranslator = new OrderByRefTranslator();
//		return this.orderByTranslator;
//	}

	protected Translator createOrderByTranslator() {
		return new Translator(ORDER_BY , (EStructuralFeature) null);
	}
	
	//placeholder until we support in our model, this allow us
	//to keep the elements in the proper order
	protected Translator createMapKeyTranslator() {
		return new Translator(MAP_KEY + "/" + MAP_KEY__NAME, JpaCoreMappingsPackage.eINSTANCE.getIMultiRelationshipMapping_MapKey(), DOM_ATTRIBUTE);
	}

}
