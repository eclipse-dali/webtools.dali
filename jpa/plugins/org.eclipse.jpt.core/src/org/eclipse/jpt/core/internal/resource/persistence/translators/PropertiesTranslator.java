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
package org.eclipse.jpt.core.internal.resource.persistence.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class PropertiesTranslator extends Translator
	implements PersistenceXmlMapper
{
	private Translator[] children;
	
	
	public PropertiesTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override	
	protected Translator[] getChildren() {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
	
	protected Translator[] createChildren() {
		return new Translator[] {
			createPropertyTranslator()
		};
	}
	
	private Translator createPropertyTranslator() {
		return new PropertyTranslator(PROPERTY, PERSISTENCE_PKG.getXmlProperties_Properties());
	}
}
