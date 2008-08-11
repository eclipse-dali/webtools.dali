/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.elorm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EntityResultTranslator extends Translator 
	implements EclipseLinkOrmXmlMapper
{
	private Translator[] children;	
	
	
	public EntityResultTranslator(String domNameAndPath, EStructuralFeature aFeature) {
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
			createEntityClassTranslator(),
			createDiscriminatorColumnTranslator(),
			createFieldResultTranslator()
		};
	}
	
	private Translator createEntityClassTranslator() {
		return new Translator(ENTITY_CLASS, ECLIPSELINK_ORM_PKG.getEntityResult_EntityClass(), DOM_ATTRIBUTE);
	}
	
	private Translator createDiscriminatorColumnTranslator() {
		return new Translator(DISCRIMINATOR_COLUMN, ECLIPSELINK_ORM_PKG.getEntityResult_DiscriminatorColumn(), DOM_ATTRIBUTE);
	}
	
	private Translator createFieldResultTranslator() {
		return new FieldResultTranslator(FIELD_RESULT, ECLIPSELINK_ORM_PKG.getEntityResult_FieldResults());
	}
}
