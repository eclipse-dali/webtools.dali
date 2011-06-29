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
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SnapshotCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.util.XSDUtil;

public class XsdSchema
		extends XsdComponent<XSDSchema> {
	
	XsdSchema(XSDSchema xsdSchema) {
		super(xsdSchema);
	}
	
	
	public XSDSchema getXSDSchema() {
		return getXSDComponent();
	}
	
	public Iterable<String> getNamespaces() {
		return new SnapshotCloneIterable(getXSDSchema().getQNamePrefixToNamespaceMap().values());
	}
	
	public Iterable<XsdTypeDefinition> getAllTypeDefinitions() {
		return new TransformationIterable<XSDTypeDefinition, XsdTypeDefinition>(getAllXSDTypeDefinitions()) {
			@Override
			protected XsdTypeDefinition transform(XSDTypeDefinition o) {
				return (XsdTypeDefinition) XsdUtil.getAdapter(o);
			}
		};
	}
	
	public Iterable<XsdTypeDefinition> getDeclaredTypeDefinitions() {
		return new TransformationIterable<XSDTypeDefinition, XsdTypeDefinition>(getDeclaredXSDTypeDefinitions()) {
			@Override
			protected XsdTypeDefinition transform(XSDTypeDefinition o) {
				return (XsdTypeDefinition) XsdUtil.getAdapter(o);
			}
		};
	}
	
	public Iterable<XsdTypeDefinition> getBuiltInTypeDefinitions() {
		return new TransformationIterable<XSDTypeDefinition, XsdTypeDefinition>(getBuiltInXSDTypeDefinitions()) {
			@Override
			protected XsdTypeDefinition transform(XSDTypeDefinition o) {
				return (XsdTypeDefinition) XsdUtil.getAdapter(o);
			}
		};
	}
	
	public Iterable<XsdTypeDefinition> getTypeDefinitions(final String namespace) {
		return new TransformationIterable<XSDTypeDefinition, XsdTypeDefinition>(getXSDTypeDefinitions(namespace)) {
			@Override
			protected XsdTypeDefinition transform(XSDTypeDefinition o) {
				return (XsdTypeDefinition) XsdUtil.getAdapter(o);
			}
		};
	}
	
	public Iterable<XsdSimpleTypeDefinition> getSimpleTypeDefinitions(final String namespace) {
		return new TransformationIterable<XSDSimpleTypeDefinition, XsdSimpleTypeDefinition>(
				getXSDSimpleTypeDefinitions(namespace)) {
			@Override
			protected XsdSimpleTypeDefinition transform(XSDSimpleTypeDefinition o) {
				return (XsdSimpleTypeDefinition) XsdUtil.getAdapter(o);
			}
		};
	}
	
	public XsdTypeDefinition getTypeDefinition(String namespace, String name) {
		for (XSDTypeDefinition typeDefinition : getXSDTypeDefinitions(namespace)) {
			if (XsdUtil.namespaceEquals(typeDefinition, namespace) && typeDefinition.getName().equals(name)) {
				return (XsdTypeDefinition) XsdUtil.getAdapter(typeDefinition);
			}
		}
		return null;
	}
	
	protected Iterable<XSDTypeDefinition> getAllXSDTypeDefinitions() {
		return new CompositeIterable<XSDTypeDefinition>(
				getDeclaredXSDTypeDefinitions(), getBuiltInXSDTypeDefinitions());
	}
	
	protected Iterable<XSDTypeDefinition> getDeclaredXSDTypeDefinitions() {
		return new SnapshotCloneIterable(getXSDSchema().getTypeDefinitions());
	}
	
	protected Iterable<XSDTypeDefinition> getBuiltInXSDTypeDefinitions() {
		return new SnapshotCloneIterable(getXSDSchema().getSchemaForSchema().getTypeDefinitions());
	}
	
	protected Iterable<XSDTypeDefinition> getXSDTypeDefinitions(final String namespace) {
		if (XSDUtil.SCHEMA_FOR_SCHEMA_URI_2001.equals(namespace)) {
			return getBuiltInXSDTypeDefinitions();
		}
		return new FilteringIterable<XSDTypeDefinition>(getDeclaredXSDTypeDefinitions()) {
			@Override
			protected boolean accept(XSDTypeDefinition o) {
				return XsdUtil.namespaceEquals(o, namespace);
			}
		};
	}
	
	protected Iterable<XSDSimpleTypeDefinition> getXSDSimpleTypeDefinitions(String namespace) {
		return new TransformationIterable<XSDTypeDefinition, XSDSimpleTypeDefinition>(
			new FilteringIterable<XSDTypeDefinition>(getXSDTypeDefinitions(namespace)) {
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
						return XsdUtil.namespaceEquals(o, namespace);
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
			if (XsdUtil.namespaceEquals(elementDeclaration, namespace) && elementDeclaration.getName().equals(name)) {
				return (XsdElementDeclaration) XsdUtil.getAdapter(elementDeclaration);
			}
		}
		return null;
	}
	
	protected Iterable<XSDElementDeclaration> getXSDElementDeclarations() {
		return new SnapshotCloneIterable(getXSDSchema().getElementDeclarations());
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
	
	public Iterable<String> getElementNameProposals(String namespace, Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(
				new FilteringIterable<String>(
						new TransformationIterable<XsdElementDeclaration, String>(this.getElementDeclarations(namespace)) {
							@Override
							protected String transform(XsdElementDeclaration o) {
								return o.getName();
							}
						},
						filter));
	}
}
