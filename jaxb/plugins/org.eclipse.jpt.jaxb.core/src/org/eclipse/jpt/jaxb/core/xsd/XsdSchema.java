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

import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.SnapshotCloneIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSchema;
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
}