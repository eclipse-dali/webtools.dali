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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.xsd.XSDAttributeUse;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDElementDeclaration;


public class XsdComplexTypeDefinition
		extends XsdTypeDefinition<XSDComplexTypeDefinition> {
	
	XsdComplexTypeDefinition(XSDComplexTypeDefinition xsdComplexTypeDefinition) {
		super(xsdComplexTypeDefinition);
	}
	
	
	@Override
	public org.eclipse.jpt.jaxb.core.xsd.XsdTypeDefinition.Kind getKind() {
		return Kind.COMPLEX;
	}
	
	@Override
	public XsdAttributeUse getAttribute(String namespace, String name) {
		for (XsdAttributeUse attrUse : getAttributeUses(namespace)) {
			if (attrUse.getXSDComponent().getAttributeDeclaration().getName().equals(name)) {
				return attrUse;
			}
		}
		return null;
	}
	
	@Override
	public Iterable<String> getAttributeNameProposals(String namespace, Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(
				new FilteringIterable<String>(
					new TransformationIterable<XsdAttributeUse, String>(getAttributeUses(namespace)) {
						@Override
						protected String transform(XsdAttributeUse attrUse) {
							return attrUse.getXSDComponent().getAttributeDeclaration().getName();
						}
					},
					filter));
	}
	
	protected Iterable<XsdAttributeUse> getAttributeUses(final String namespace) {
		return new TransformationIterable<XSDAttributeUse, XsdAttributeUse>(
				new FilteringIterable<XSDAttributeUse>(getXSDComponent().getAttributeUses()) {
					@Override
					protected boolean accept(XSDAttributeUse attrUse) {
						return XsdUtil.namespaceEquals(attrUse.getAttributeDeclaration(), namespace);
					}
				}) {
			@Override
			protected XsdAttributeUse transform(XSDAttributeUse attrUse) {
				return (XsdAttributeUse) XsdUtil.getAdapter(attrUse);
			}
		};
	}
	
	@Override
	public XsdElementDeclaration getElement(String namespace, String name, boolean recurseChildren) {
		for (XsdElementDeclaration element : getElementDeclarations(namespace, recurseChildren)) {
			if (element.getXSDComponent().getName().equals(name)) {
				return element;
			}
		}
		return null;
	}
	
	@Override
	public Iterable getElementNameProposals(String namespace, Filter<String> filter, boolean recurseChildren) {
		return StringTools.convertToJavaStringLiterals(
				new FilteringIterable<String>(
					new TransformationIterable<XsdElementDeclaration, String>(getElementDeclarations(namespace, recurseChildren)) {
						@Override
						protected String transform(XsdElementDeclaration element) {
							return element.getXSDComponent().getName();
						}
					},
					filter));
	}
	
	protected Iterable<XsdElementDeclaration> getElementDeclarations(final String namespace, boolean recurseChildren) {
		return new TransformationIterable<XSDElementDeclaration, XsdElementDeclaration>(
				new FilteringIterable<XSDElementDeclaration>(getXSDElementDeclarations(recurseChildren)) {
					@Override
					protected boolean accept(XSDElementDeclaration element) {
						return XsdUtil.namespaceEquals(element, namespace);
					}
				}) {
			@Override
			protected XsdElementDeclaration transform(XSDElementDeclaration element) {
				return (XsdElementDeclaration) XsdUtil.getAdapter(element);
			}
		};
	}
	
	protected Iterable<XSDElementDeclaration> getXSDElementDeclarations(boolean recurseChildren) {
		ElementFinder elementFinder = new ElementFinder(recurseChildren);
		elementFinder.visitNode(getXSDComponent());
		return elementFinder.getElements();
	}
	
	
	private class ElementFinder
			extends XSDNodeVisitor {
		
		private boolean recurseChildren;
		
		private List<XSDElementDeclaration> elements = new ArrayList<XSDElementDeclaration>();
		
		
		private ElementFinder(boolean recurseChildren) {
			this.recurseChildren = recurseChildren;
		}
		
		
		@Override
		protected boolean visitChildren() {
			return super.visitChildren() || this.recurseChildren;
		}
		
		@Override
		public void visitXSDElementDeclaration(XSDElementDeclaration node) {
			boolean cachedVisitChildren = this.visitChildren;
			this.visitChildren = false;
			super.visitXSDElementDeclaration(node);
			if (! this.elements.contains(node)) {
				elements.add(node);
			}
			this.visitChildren = cachedVisitChildren;
		}
		
		public Iterable<XSDElementDeclaration> getElements() {
			return this.elements;
		}
	}
}
