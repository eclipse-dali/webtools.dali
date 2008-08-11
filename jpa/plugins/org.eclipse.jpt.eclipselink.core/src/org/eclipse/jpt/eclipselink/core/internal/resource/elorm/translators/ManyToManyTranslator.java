/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.elorm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class ManyToManyTranslator extends Translator
	implements EclipseLinkOrmXmlMapper
{
	private Translator[] children;	
	
	
	public ManyToManyTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlManyToManyImpl();
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
			createCascadeTranslator()
		};
	}
	
	private Translator createNameTranslator() {
		return new Translator(NAME, ECLIPSELINK_ORM_PKG.getXmlAttributeMapping_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createTargetEntityTranslator() {
		return new Translator(TARGET_ENTITY, ECLIPSELINK_ORM_PKG.getXmlRelationshipMapping_TargetEntity(), DOM_ATTRIBUTE);
	}
	
	private Translator createFetchTranslator() {
		return new Translator(FETCH, ECLIPSELINK_ORM_PKG.getXmlRelationshipMapping_Fetch(), DOM_ATTRIBUTE);
	}
	
	private Translator createMappedByTranslator() {
		return new Translator(MAPPED_BY, ECLIPSELINK_ORM_PKG.getXmlMultiRelationshipMapping_MappedBy(), DOM_ATTRIBUTE);
	}
	
	private Translator createOrderByTranslator() {
		return new Translator(ORDER_BY, ECLIPSELINK_ORM_PKG.getXmlMultiRelationshipMapping_OrderBy());
	}
	
	private Translator createMapKeyTranslator() {
		return new MapKeyTranslator(MAP_KEY, ECLIPSELINK_ORM_PKG.getXmlMultiRelationshipMapping_MapKey());
	}
	
	private Translator createJoinTableTranslator() {
		return new JoinTableTranslator(JOIN_TABLE, ECLIPSELINK_ORM_PKG.getXmlRelationshipMapping_JoinTable());
	}
	
	private Translator createCascadeTranslator() {
		return new CascadeTypeTranslator(CASCADE, ECLIPSELINK_ORM_PKG.getXmlRelationshipMapping_Cascade());
	}
}
