/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class OneToManyTranslator extends Translator
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public OneToManyTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public Translator[] getChildren(Object target, int versionID) {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
		
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createTargetEntityTranslator(),
			createFetchTranslator(),
			createMappedByTranslator(),
			createOrderByTranslator(),
			createMapKeyTranslator(),
			createJoinTableTranslator(),
			createJoinColumnTranslator(),
			createCascadeTranslator()
		};
	}
	
	private Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getAttributeMapping_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createTargetEntityTranslator() {
		return new Translator(TARGET_ENTITY, ORM_PKG.getRelationshipMapping_TargetEntity(), DOM_ATTRIBUTE);
	}
	
	private Translator createFetchTranslator() {
		return new Translator(FETCH, ORM_PKG.getRelationshipMapping_Fetch(), DOM_ATTRIBUTE);
	}
	
	private Translator createMappedByTranslator() {
		return new Translator(MAPPED_BY, ORM_PKG.getMultiRelationshipMapping_MappedBy(), DOM_ATTRIBUTE);
	}
	
	private Translator createOrderByTranslator() {
		return new Translator(ORDER_BY, ORM_PKG.getMultiRelationshipMapping_OrderBy());
	}
	
	private Translator createMapKeyTranslator() {
		return new MapKeyTranslator(MAP_KEY, ORM_PKG.getMultiRelationshipMapping_MapKey());
	}
	
	private Translator createJoinTableTranslator() {
		return new JoinTableTranslator(JOIN_TABLE, ORM_PKG.getRelationshipMapping_JoinTable());
	}
		
	private Translator createJoinColumnTranslator() {
		return new JoinColumnTranslator(JOIN_COLUMN, ORM_PKG.getOneToMany_JoinColumns());
	}
	
	private Translator createCascadeTranslator() {
		return new CascadeTypeTranslator(CASCADE, ORM_PKG.getRelationshipMapping_Cascade());
	}
}
