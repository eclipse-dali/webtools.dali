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

public class EmbeddedIdTranslator extends Translator
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public EmbeddedIdTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return OrmFactory.eINSTANCE.createXmlEmbeddedIdImpl();
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
			createAttributeOverrideTranslator()
		};
	}
	
	protected Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getXmlAttributeMapping_Name(), DOM_ATTRIBUTE);
	}
	
	protected Translator createAttributeOverrideTranslator() {
		return new AttributeOverrideTranslator(ATTRIBUTE_OVERRIDE, ORM_PKG.getBaseXmlEmbedded_AttributeOverrides());
	}	
}
