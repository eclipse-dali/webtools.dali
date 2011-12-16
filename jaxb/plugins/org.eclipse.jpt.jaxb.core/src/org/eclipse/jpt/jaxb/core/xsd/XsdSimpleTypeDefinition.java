/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.xsd;

import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.xsd.XSDEnumerationFacet;
import org.eclipse.xsd.XSDSimpleTypeDefinition;


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
	public boolean typeIsValid(XsdTypeDefinition xsdType, boolean isItemType, boolean allowExtension, boolean allowRestriction) {
		if (isItemType) {
			XsdSimpleTypeDefinition itemType = getItemType();
			return (itemType == null) ? false : itemType.typeIsValid(xsdType, false, allowExtension, allowRestriction);
		}
//		if (getXSDComponent().getVariety() == XSDVariety.UNION_LITERAL) {
//			for (XsdSimpleTypeDefinition memberType : getMemberTypes()) {
//				if (! memberType.typeIsValid(xsdType, isItemType, allowExtension, allowRestriction)) {
//					return false;
//				}
//			}
//			return true;
//		}
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
		return new TransformationIterable<XSDSimpleTypeDefinition, XsdSimpleTypeDefinition>(
				getXSDComponent().getMemberTypeDefinitions()) {
			@Override
			protected XsdSimpleTypeDefinition transform(XSDSimpleTypeDefinition o) {
				return (XsdSimpleTypeDefinition) XsdUtil.getAdapter(o);
			}
		};
	}
	
	@Override
	public XsdAttributeUse getAttribute(String namespace, String name) {
		// simple types have no attributes
		return null;
	}
	
	@Override
	public Iterable<String> getAttributeNameProposals(String namespace, Filter<String> filter) {
		// simple types have no attributes
		return EmptyIterable.instance();
	}
	
	@Override
	public XsdElementDeclaration getElement(String namespace, String name, boolean recurseChildren) {
		// simple types have no elements
		return null;
	}
	
	@Override
	public Iterable<String> getElementNameProposals(String namespace, Filter<String> filter, boolean recurseChildren) {
		// simple types have no elements
		return EmptyIterable.instance();
	}
	
	public Iterable<String> getEnumValueProposals(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(
				new FilteringIterable<String>(
					new TransformationIterable<XSDEnumerationFacet, String>(getXSDComponent().getEnumerationFacets()) {
						@Override
						protected String transform(XSDEnumerationFacet enumFacet) {
							return enumFacet.getLexicalValue();
						}
					},
					filter));
	}
}
