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
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.resource.JoinColumnTranslator.JoinColumnBuilder;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.ISingleRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public abstract class SingleRelationshipTranslator extends RelationshipTranslator 
{
	private ISingleRelationshipMapping singleRelationshipMapping;
	
	public SingleRelationshipTranslator(String domNameAndPath) {
		super(domNameAndPath);
	}
	
	@Override
	public final EObject createEMFObject(String nodeName, String readAheadName) {
		this.singleRelationshipMapping = createMapping();
		return this.singleRelationshipMapping;
	}
	
	protected abstract ISingleRelationshipMapping createMapping();
	
	protected Translator createFetchTypeTranslator() {
		return new EnumeratorTranslator(FETCH, JpaCoreMappingsPackage.eINSTANCE.getISingleRelationshipMapping_Fetch(), DOM_ATTRIBUTE);
	}
	
	protected Translator createJoinColumnsTranslator() {
		return new JoinColumnTranslator(
				JOIN_COLUMN,  
				JpaCoreMappingsPackage.eINSTANCE.getISingleRelationshipMapping_SpecifiedJoinColumns(),
				buildJoinColumnsCreator());
	}
	
	private JoinColumnBuilder buildJoinColumnsCreator() {
		return new JoinColumnBuilder() {
			public IJoinColumn createJoinColumn() {
				return OrmFactory.eINSTANCE.createXmlJoinColumn(new ISingleRelationshipMapping.JoinColumnOwner(singleRelationshipMapping));
			}
		};
	}
	
	
}
