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
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class AttributesTranslator extends Translator 
	implements EclipseLinkOrmXmlMapper
{
	private Translator[] children;	
	
	
	public AttributesTranslator(String domNameAndPath, EStructuralFeature aFeature) {
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
			createIdTranslator(),
			createEmbeddedIdTranslator(),
			createBasicTranslator(),
			createVersionTranslator(),
			createManyToOneTranslator(),
			createOneToManyTranslator(),
			createOneToOneTranslator(),
			createManyToManyTranslator(),
			createEmbeddedTranslator(),
			createTransientTranslator()
		};
	}
	
	private Translator createIdTranslator() {
		return new IdTranslator(ID, ECLIPSELINK_ORM_PKG.getAttributes_Ids());
	}
	
	private Translator createEmbeddedIdTranslator() {
		return new EmbeddedIdTranslator(EMBEDDED_ID, ECLIPSELINK_ORM_PKG.getAttributes_EmbeddedIds());
	}
	
	private Translator createBasicTranslator() {
		return new BasicTranslator(BASIC, ECLIPSELINK_ORM_PKG.getAttributes_Basics());
	}
	
	private Translator createVersionTranslator() {
		return new VersionTranslator(VERSION, ECLIPSELINK_ORM_PKG.getAttributes_Versions());
	}
	
	private Translator createManyToOneTranslator() {
		return new ManyToOneTranslator(MANY_TO_ONE, ECLIPSELINK_ORM_PKG.getAttributes_ManyToOnes());
	}
	
	private Translator createOneToManyTranslator() {
		return new OneToManyTranslator(ONE_TO_MANY, ECLIPSELINK_ORM_PKG.getAttributes_OneToManys());
	}
	
	private Translator createOneToOneTranslator() {
		return new OneToOneTranslator(ONE_TO_ONE, ECLIPSELINK_ORM_PKG.getAttributes_OneToOnes());
	}
	
	private Translator createManyToManyTranslator() {
		return new ManyToManyTranslator(MANY_TO_MANY, ECLIPSELINK_ORM_PKG.getAttributes_ManyToManys());
	}
	
	private Translator createEmbeddedTranslator() {
		return new EmbeddedTranslator(EMBEDDED, ECLIPSELINK_ORM_PKG.getAttributes_Embeddeds());
	}
	
	private Translator createTransientTranslator() {
		return new TransientTranslator(TRANSIENT, ECLIPSELINK_ORM_PKG.getAttributes_Transients());
	}
}
