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

import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.SnapshotCloneIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;

public class XsdSchema
		extends XsdAdapter {
	
	protected final XSDSchema xsdSchema;
	
	
	
	XsdSchema(XSDSchema xsdSchema) {
		super();
		this.xsdSchema = xsdSchema;
	}
	
	
	public Iterable<String> getNamespaces() {
		return new SnapshotCloneIterable(this.xsdSchema.getQNamePrefixToNamespaceMap().values());
	}
	
	public Iterable<XsdTypeDefinition> getTypeDefinitions() {
		return new TransformationIterable<XSDTypeDefinition, XsdTypeDefinition>(getXSDTypeDefinitions()) {
			@Override
			protected XsdTypeDefinition transform(XSDTypeDefinition o) {
				return (XsdTypeDefinition) XsdUtil.getAdapter(o);
			}
		};
	}
	
	public Iterable<XsdSimpleTypeDefinition> getSimpleTypeDefinitions() {
		return new TransformationIterable<XSDTypeDefinition, XsdSimpleTypeDefinition>(getXSDSimpleTypeDefinitions()) {
			@Override
			protected XsdSimpleTypeDefinition transform(XSDTypeDefinition o) {
				return (XsdSimpleTypeDefinition) XsdUtil.getAdapter(o);
			}
		};
	}
	
	public Iterable<XsdTypeDefinition> getTypeDefinitions(final String namespace) {
		return new TransformationIterable<XSDTypeDefinition, XsdTypeDefinition>(
				new FilteringIterable<XSDTypeDefinition>(getXSDTypeDefinitions()) {
					@Override
					protected boolean accept(XSDTypeDefinition o) {
						return o.getTargetNamespace().equals(namespace);
					}
				}) {
			@Override
			protected XsdTypeDefinition transform(XSDTypeDefinition o) {
				return (XsdTypeDefinition) XsdUtil.getAdapter(o);
			}
		};
	}
	
	public Iterable<XsdSimpleTypeDefinition> getSimpleTypeDefinitions(final String namespace) {
		return new TransformationIterable<XSDSimpleTypeDefinition, XsdSimpleTypeDefinition>(
				new FilteringIterable<XSDSimpleTypeDefinition>(getXSDSimpleTypeDefinitions()) {
					@Override
					protected boolean accept(XSDSimpleTypeDefinition o) {
						return o.getTargetNamespace().equals(namespace);
					}
				}) {
			@Override
			protected XsdSimpleTypeDefinition transform(XSDSimpleTypeDefinition o) {
				return (XsdSimpleTypeDefinition) XsdUtil.getAdapter(o);
			}
		};
	}
	
	public XsdTypeDefinition getTypeDefinition(String namespace, String name) {
		for (XSDTypeDefinition typeDefinition : getXSDTypeDefinitions()) {
			if (typeDefinition.getTargetNamespace().equals(namespace) && typeDefinition.getName().equals(name)) {
				return (XsdTypeDefinition) XsdUtil.getAdapter(typeDefinition);
			}
		}
		return null;
	}
	
	protected Iterable<XSDTypeDefinition> getXSDTypeDefinitions() {
		return new SnapshotCloneIterable(this.xsdSchema.getTypeDefinitions());
	}
	
	protected Iterable<XSDSimpleTypeDefinition> getXSDSimpleTypeDefinitions() {
		return new TransformationIterable<XSDTypeDefinition, XSDSimpleTypeDefinition>(
			new FilteringIterable<XSDTypeDefinition>(getXSDTypeDefinitions()) {
				@Override
				protected boolean accept(XSDTypeDefinition o) {
					return o instanceof XSDSimpleTypeDefinition;
				}
			}) {
			@Override
			protected XSDSimpleTypeDefinition transform(XSDTypeDefinition o) {
				return (XSDSimpleTypeDefinition) o;
			}
		};
	}
	
	public Iterable<XsdElementDeclaration> getElementDeclarations() {
		return new TransformationIterable<XSDElementDeclaration, XsdElementDeclaration>(getXSDElementDeclarations()) {
			@Override
			protected XsdElementDeclaration transform(XSDElementDeclaration o) {
				return (XsdElementDeclaration) XsdUtil.getAdapter(o);
			}
		};
	}
	
	public Iterable<XsdElementDeclaration> getElementDeclarations(final String namespace) {
		return new TransformationIterable<XSDElementDeclaration, XsdElementDeclaration>(
				new FilteringIterable<XSDElementDeclaration>(getXSDElementDeclarations()) {
					@Override
					protected boolean accept(XSDElementDeclaration o) {
						return o.getTargetNamespace().equals(namespace);
					}
				}) {
			@Override
			protected XsdElementDeclaration transform(XSDElementDeclaration o) {
				return (XsdElementDeclaration) XsdUtil.getAdapter(o);
			}
		};
	}
	
	public XsdElementDeclaration getElementDeclaration(String namespace, String name) {
		for (XSDElementDeclaration elementDeclaration : getXSDElementDeclarations()) {
			if (elementDeclaration.getTargetNamespace().equals(namespace) && elementDeclaration.getName().equals(name)) {
				return (XsdElementDeclaration) XsdUtil.getAdapter(elementDeclaration);
			}
		}
		return null;
	}
	
	protected Iterable<XSDElementDeclaration> getXSDElementDeclarations() {
		return new SnapshotCloneIterable(this.xsdSchema.getElementDeclarations());
	}

	public Iterable<String> getNamespaceProposals(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(
				new FilteringIterable<String>(getNamespaces(), filter));
	}
	
	public Iterable<String> getTypeNameProposals(String namespace, Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(
				new FilteringIterable<String>(
					new TransformationIterable<XsdTypeDefinition, String>(this.getTypeDefinitions(namespace)) {
						@Override
						protected String transform(XsdTypeDefinition o) {
							return o.getName();
						}
					},
					filter));
	}
	
	public Iterable<String> getSimpleTypeNameProposals(String namespace, Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(
				new FilteringIterable<String>(
					new TransformationIterable<XsdSimpleTypeDefinition, String>(this.getSimpleTypeDefinitions(namespace)) {
						@Override
						protected String transform(XsdSimpleTypeDefinition o) {
							return o.getName();
						}
					},
					filter));
	}

}