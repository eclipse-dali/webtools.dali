/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.xsd;

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.xsd.XSDEnumerationFacet;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDVariety;


public class XsdSimpleTypeDefinition
		extends XsdTypeDefinition<XSDSimpleTypeDefinition> {
	
	XsdSimpleTypeDefinition(XSDSimpleTypeDefinition xsdSimpleTypeDefinition) {
		super(xsdSimpleTypeDefinition);
	}
	
	
	@Override
	public org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition.Kind getKind() {
		return Kind.SIMPLE;
	}
	
	@Override
	public boolean hasTextContent() {
		return true;
	}
	
	@Override
	public boolean typeIsValid(XsdTypeDefinition xsdType, boolean isItemType, boolean allowExtension, boolean allowRestriction) {
		if (isItemType) {
			XsdSimpleTypeDefinition itemType = getItemType();
			return (itemType == null) ? false : itemType.typeIsValid(xsdType, false, allowExtension, allowRestriction);
		}
		if (getXSDComponent().getVariety() == XSDVariety.UNION_LITERAL) {
			if (XsdUtil.getSchemaForSchema().getTypeDefinition("string").typeIsValid(xsdType, isItemType, allowExtension, allowRestriction)) {
				return true;
			}
		}
		return super.typeIsValid(xsdType, isItemType, allowExtension, allowRestriction);
	}
	
	/**
	 * If the type is of variety LIST, this will return the item type of the list
	 */
	public XsdSimpleTypeDefinition getItemType() {
		XSDSimpleTypeDefinition xsdItemType = getXSDComponent().getItemTypeDefinition();
		return (xsdItemType == null) ? null : (XsdSimpleTypeDefinition) XsdUtil.getAdapter(xsdItemType);
	}
	
	/**
	 * If the type is of variety UNION, this will return the member types of the union
	 */
	public Iterable<XsdSimpleTypeDefinition> getMemberTypes() {
		return IterableTools.transform(getXSDComponent().getMemberTypeDefinitions(), XsdUtil.<XsdSimpleTypeDefinition>adapterTransformer());
	}
	
	@Override
	public XsdAttributeUse getAttribute(String namespace, String name) {
		// simple types have no attributes
		return null;
	}
	
	@Override
	public Iterable<String> getAttributeNames(String namespace) {
		// simple types have no attributes
		return EmptyIterable.instance();
	}
	
	@Override
	public XsdElementDeclaration getElement(String namespace, String name, boolean recurseChildren) {
		// simple types have no elements
		return null;
	}
	
	@Override
	public Iterable<String> getElementNames(String namespace, boolean recurseChildren) {
		// simple types have no elements
		return EmptyIterable.instance();
	}
	
	public Iterable<String> getEnumValueProposals() {
		return IterableTools.transform(
					IterableTools.transform(getXSDComponent().getEnumerationFacets(),
							XSD_ENUMERATION_FACET_TRANSFORMER),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}

	protected static final Transformer<XSDEnumerationFacet, String> XSD_ENUMERATION_FACET_TRANSFORMER = new XSDEnumerationFacetTransformer();
	public static class XSDEnumerationFacetTransformer
		extends TransformerAdapter<XSDEnumerationFacet, String>
	{
		@Override
		public String transform(XSDEnumerationFacet enumFacet) {
			return enumFacet.getLexicalValue();
		}
	}
}
