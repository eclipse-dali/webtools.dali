/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.orm.translators.EmbeddedTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EclipseLinkEmbeddedTranslator extends EmbeddedTranslator
	implements EclipseLinkOrmXmlMapper
{
	public EclipseLinkEmbeddedTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
		
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			createNameTranslator(),
			createAttributeOverrideTranslator(),
			createPropertyTranslator(),
			createAccessMethodsTranslator()
		};
	}
	
	protected Translator createPropertyTranslator() {
		return new PropertyTranslator(PROPERTY, ECLIPSELINK_ORM_PKG.getXmlEmbedded_Properties());
	}
	
	protected Translator createAccessMethodsTranslator() {
		return new AccessMethodsTranslator();
	}
}
