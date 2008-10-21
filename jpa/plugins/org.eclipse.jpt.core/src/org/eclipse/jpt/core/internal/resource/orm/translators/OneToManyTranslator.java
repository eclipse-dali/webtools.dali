/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class OneToManyTranslator extends Translator
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public OneToManyTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return OrmFactory.eINSTANCE.createXmlOneToManyImpl();
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
	
	protected Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getXmlAttributeMapping_Name(), DOM_ATTRIBUTE);
	}
	
	protected Translator createTargetEntityTranslator() {
		return new Translator(TARGET_ENTITY, ORM_PKG.getXmlRelationshipMapping_TargetEntity(), DOM_ATTRIBUTE);
	}
	
	protected Translator createFetchTranslator() {
		return new Translator(FETCH, ORM_PKG.getXmlRelationshipMapping_Fetch(), DOM_ATTRIBUTE);
	}
	
	protected Translator createMappedByTranslator() {
		return new Translator(MAPPED_BY, ORM_PKG.getXmlMultiRelationshipMapping_MappedBy(), DOM_ATTRIBUTE);
	}
	
	protected Translator createOrderByTranslator() {
		return new Translator(ORDER_BY, ORM_PKG.getXmlMultiRelationshipMapping_OrderBy());
	}
	
	protected Translator createMapKeyTranslator() {
		return new MapKeyTranslator(MAP_KEY, ORM_PKG.getXmlMultiRelationshipMapping_MapKey());
	}
	
	protected Translator createJoinTableTranslator() {
		return new JoinTableTranslator(JOIN_TABLE, ORM_PKG.getXmlRelationshipMapping_JoinTable());
	}
		
	protected Translator createJoinColumnTranslator() {
		return new JoinColumnTranslator(JOIN_COLUMN, ORM_PKG.getXmlOneToMany_JoinColumns());
	}
	
	protected Translator createCascadeTranslator() {
		return new CascadeTypeTranslator(CASCADE, ORM_PKG.getXmlRelationshipMapping_Cascade());
	}
}
