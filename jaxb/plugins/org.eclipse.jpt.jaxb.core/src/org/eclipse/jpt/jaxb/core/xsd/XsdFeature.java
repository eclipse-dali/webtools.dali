/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.xsd;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.xsd.XSDFeature;


public abstract class XsdFeature<A extends XSDFeature>
		extends XsdComponent<A> {
	
	protected XsdFeature(A xsdFeature) {
		super(xsdFeature);
	}
	
	
	public A getXSDFeature() {
		return getXSDComponent();
	}
	
	public String getName() {
		return getXSDFeature().getName();
	}
	public static final Transformer<XsdFeature, String> NAME_TRANSFORMER = new NameTransformer();
	public static class NameTransformer
		extends TransformerAdapter<XsdFeature, String>
	{
		@Override
		public String transform(XsdFeature feature) {
			return feature.getName();
		}
	}
	
	public abstract XsdTypeDefinition getType();
	
	/**
	 * Return whether the given schema type is valid within the feature
	 * @param isItemType determines whether the xsdType should instead be treated as an item type
	 *   within a list
	 */
	public abstract boolean typeIsValid(XsdTypeDefinition xsdType, boolean isItemType);
}
