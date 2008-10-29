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
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class AttributesTranslator extends Translator 
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public AttributesTranslator(String domNameAndPath, EStructuralFeature aFeature) {
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
	
	protected Translator createIdTranslator() {
		return new IdTranslator(ID, ORM_PKG.getAttributes_Ids());
	}
	
	protected Translator createEmbeddedIdTranslator() {
		return new EmbeddedIdTranslator(EMBEDDED_ID, ORM_PKG.getAttributes_EmbeddedIds());
	}
	
	protected Translator createBasicTranslator() {
		return new BasicTranslator(BASIC, ORM_PKG.getAttributes_Basics());
	}
	
	protected Translator createVersionTranslator() {
		return new VersionTranslator(VERSION, ORM_PKG.getAttributes_Versions());
	}
	
	protected Translator createManyToOneTranslator() {
		return new ManyToOneTranslator(MANY_TO_ONE, ORM_PKG.getAttributes_ManyToOnes());
	}
	
	protected Translator createOneToManyTranslator() {
		return new OneToManyTranslator(ONE_TO_MANY, ORM_PKG.getAttributes_OneToManys());
	}
	
	protected Translator createOneToOneTranslator() {
		return new OneToOneTranslator(ONE_TO_ONE, ORM_PKG.getAttributes_OneToOnes());
	}
	
	protected Translator createManyToManyTranslator() {
		return new ManyToManyTranslator(MANY_TO_MANY, ORM_PKG.getAttributes_ManyToManys());
	}
	
	protected Translator createEmbeddedTranslator() {
		return new EmbeddedTranslator(EMBEDDED, ORM_PKG.getAttributes_Embeddeds());
	}
	
	protected Translator createTransientTranslator() {
		return new TransientTranslator(TRANSIENT, ORM_PKG.getAttributes_Transients());
	}
}
