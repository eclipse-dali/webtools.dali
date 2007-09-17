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

public class ManyToOneTranslator extends Translator
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public ManyToOneTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
		
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createTargetEntityTranslator(),
			createFetchTranslator(),
			createOptionalTranslator(),
			createJoinColumnTranslator(),
			createJoinTableTranslator(),
			createCascadeTranslator()
		};
	}
	
	private Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getManyToOne_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createTargetEntityTranslator() {
		return new Translator(TARGET_ENTITY, ORM_PKG.getManyToOne_TargetEntity(), DOM_ATTRIBUTE);
	}
	
	private Translator createFetchTranslator() {
		return new Translator(FETCH, ORM_PKG.getManyToOne_Fetch(), DOM_ATTRIBUTE);
	}
	
	private Translator createOptionalTranslator() {
		return new Translator(OPTIONAL, ORM_PKG.getManyToOne_Optional(), DOM_ATTRIBUTE);
	}
	
	private Translator createJoinColumnTranslator() {
		return new JoinColumnTranslator(JOIN_COLUMN, ORM_PKG.getManyToOne_JoinColumns());
	}
	
	private Translator createJoinTableTranslator() {
		return new JoinTableTranslator(JOIN_TABLE, ORM_PKG.getManyToOne_JoinTable());
	}
		
	private Translator createCascadeTranslator() {
		return new CascadeTypeTranslator(CASCADE, ORM_PKG.getManyToOne_Cascade());
	}
}
