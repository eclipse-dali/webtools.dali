/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class OneToOneTranslator extends Translator
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public OneToOneTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return OrmFactory.eINSTANCE.createXmlOneToOneImpl();
	}

	@Override
	protected Translator[] getChildren() {
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
			createOptionalTranslator(),
			createMappedByTranslator(),
			createPrimaryKeyJoinColumnTranslator(),
			createJoinColumnTranslator(),
			createJoinTableTranslator(),
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
	
	protected Translator createOptionalTranslator() {
		return new Translator(OPTIONAL, ORM_PKG.getXmlSingleRelationshipMapping_Optional(), DOM_ATTRIBUTE);
	}
	
	protected Translator createMappedByTranslator() {
		return new Translator(MAPPED_BY, ORM_PKG.getXmlMappedByMapping_MappedBy(), DOM_ATTRIBUTE);
	}
	
	protected Translator createPrimaryKeyJoinColumnTranslator() {
		return new PrimaryKeyJoinColumnTranslator(PRIMARY_KEY_JOIN_COLUMN, ORM_PKG.getXmlOneToOne_PrimaryKeyJoinColumns());
	}
	
	protected Translator createJoinColumnTranslator() {
		return new JoinColumnTranslator(JOIN_COLUMN, ORM_PKG.getXmlJoinColumnsMapping_JoinColumns());
	}
	
	protected Translator createJoinTableTranslator() {
		return new JoinTableTranslator(JOIN_TABLE, ORM_PKG.getXmlJoinTableMapping_JoinTable());
	}
		
	protected Translator createCascadeTranslator() {
		return new CascadeTypeTranslator(CASCADE, ORM_PKG.getXmlRelationshipMapping_Cascade());
	}
}
