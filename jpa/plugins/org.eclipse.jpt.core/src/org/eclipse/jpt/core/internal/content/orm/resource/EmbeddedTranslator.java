/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.content.orm.resource.AttributeOverrideTranslator.AttributeOverrideBuilder;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IEmbedded;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class EmbeddedTranslator extends AttributeMappingTranslator 
{
	private IEmbedded embedded;
	
	public EmbeddedTranslator() {
		super(EMBEDDED, NO_STYLE);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		IEmbedded embedded = JPA_CORE_XML_FACTORY.createXmlEmbedded();
		this.setEmbedded(embedded);
		return embedded;
	}
	
	protected void setEmbedded(IEmbedded embedded) {
		this.embedded = embedded;
	}
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createAttributeOverridesTranslator(),
		};
	}
	private Translator createAttributeOverridesTranslator() {
		return new AttributeOverridesTranslator(EMBEDDED__ATTRIBUTE_OVERRIDE, MAPPINGS_PKG.getIEmbedded_SpecifiedAttributeOverrides(), buildAttributeOverrideBuilder());
	}
	
	private AttributeOverrideBuilder buildAttributeOverrideBuilder() {
		return new AttributeOverrideBuilder() {
			public IAttributeOverride createAttributeOverride() {
				return EmbeddedTranslator.this.embedded.createAttributeOverride(0);
			}
		};
	}
}
