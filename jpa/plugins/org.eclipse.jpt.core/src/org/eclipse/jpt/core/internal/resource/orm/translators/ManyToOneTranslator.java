/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class ManyToOneTranslator extends Translator
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public ManyToOneTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
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
			createJoinColumnTranslator(),
			createJoinTableTranslator(),
			createCascadeTranslator()
		};
	}
	
	protected Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getXmlAttributeMapping_Name(), DOM_ATTRIBUTE);
	}
	
	protected Translator createTargetEntityTranslator() {
		return new Translator(TARGET_ENTITY, ORM_PKG.getAbstractXmlRelationshipMapping_TargetEntity(), DOM_ATTRIBUTE);
	}
	
	protected Translator createFetchTranslator() {
		return new Translator(FETCH, ORM_PKG.getAbstractXmlRelationshipMapping_Fetch(), DOM_ATTRIBUTE);
	}
	
	protected Translator createOptionalTranslator() {
		return new Translator(OPTIONAL, ORM_PKG.getAbstractXmlSingleRelationshipMapping_Optional(), DOM_ATTRIBUTE);
	}
	
	protected Translator createJoinColumnTranslator() {
		return new JoinColumnTranslator(JOIN_COLUMN, ORM_PKG.getXmlJoinColumnsMapping_JoinColumns());
	}
	
	protected Translator createJoinTableTranslator() {
		return new JoinTableTranslator(JOIN_TABLE, ORM_PKG.getXmlJoinTableMapping_JoinTable());
	}
		
	protected Translator createCascadeTranslator() {
		return new CascadeTypeTranslator(CASCADE, ORM_PKG.getAbstractXmlRelationshipMapping_Cascade());
	}
}
