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
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
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
		Iterable<String> result = IterableTools.cloneSnapshot(getXSDSchema().getQNamePrefixToNamespaceMap().values());
		if (StringTools.isBlank(getXSDSchema().getTargetNamespace())) {
			result = IterableTools.insert("", result);
		}
		return result;
	}
	
	public Iterable<XsdTypeDefinition> getAllTypeDefinitions() {
		return IterableTools.transform(getAllXSDTypeDefinitions(), XsdUtil.<XsdTypeDefinition>adapterTransformer());
	}
	
	public Iterable<XsdTypeDefinition> getDeclaredTypeDefinitions() {
		return IterableTools.transform(getDeclaredXSDTypeDefinitions(), XsdUtil.<XsdTypeDefinition>adapterTransformer());
	}
	
	public Iterable<XsdTypeDefinition> getBuiltInTypeDefinitions() {
		return IterableTools.transform(getBuiltInXSDTypeDefinitions(), XsdUtil.<XsdTypeDefinition>adapterTransformer());
	}
	
	public Iterable<XsdTypeDefinition> getTypeDefinitions(final String namespace) {
		return IterableTools.transform(getXSDTypeDefinitions(namespace), XsdUtil.<XsdTypeDefinition>adapterTransformer());
	}
	
	public Iterable<XsdSimpleTypeDefinition> getSimpleTypeDefinitions(final String namespace) {
		return IterableTools.transform(getXSDSimpleTypeDefinitions(namespace), XsdUtil.<XsdSimpleTypeDefinition>adapterTransformer());
	}
	
	/**
	 * by default, return a type with the given name and the schema's target namespace
	 */
	public XsdTypeDefinition getTypeDefinition(String name) {
		return getTypeDefinition(getXSDComponent().getTargetNamespace(), name);
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
		return IterableTools.concatenate(
				getDeclaredXSDTypeDefinitions(), getBuiltInXSDTypeDefinitions());
	}
	
	protected Iterable<XSDTypeDefinition> getDeclaredXSDTypeDefinitions() {
		return IterableTools.cloneSnapshot(getXSDSchema().getTypeDefinitions());
	}
	
	protected Iterable<XSDTypeDefinition> getBuiltInXSDTypeDefinitions() {
		return IterableTools.cloneSnapshot(getXSDSchema().getSchemaForSchema().getTypeDefinitions());
	}
	
	protected Iterable<XSDTypeDefinition> getXSDTypeDefinitions(final String namespace) {
		return XSDUtil.SCHEMA_FOR_SCHEMA_URI_2001.equals(namespace) ?
				getBuiltInXSDTypeDefinitions() :
				IterableTools.filter(getDeclaredXSDTypeDefinitions(), new XsdUtil.NamespaceEquals(namespace));
	}
	
	protected Iterable<XSDSimpleTypeDefinition> getXSDSimpleTypeDefinitions(String namespace) {
		return IterableTools.downCast(
			IterableTools.filter(getXSDTypeDefinitions(namespace), PredicateTools.<XSDTypeDefinition>instanceOf(XSDSimpleTypeDefinition.class)));
	}
	
	public Iterable<XsdElementDeclaration> getElementDeclarations() {
		return IterableTools.transform(getXSDElementDeclarations(), XsdUtil.<XsdElementDeclaration>adapterTransformer());
	}
	
	public Iterable<XsdElementDeclaration> getElementDeclarations(final String namespace) {
		return IterableTools.transform(
				IterableTools.filter(
						getXSDElementDeclarations(),
						new XsdUtil.NamespaceEquals(namespace)),
				XsdUtil.<XsdElementDeclaration>adapterTransformer());
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
		return IterableTools.cloneSnapshot(getXSDSchema().getElementDeclarations());
	}

	public Iterable<String> getNamespaceProposals() {
		return new TransformationIterable<String, String>(getNamespaces(), StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}
	
	public Iterable<String> getTypeNameProposals(String namespace) {
		return IterableTools.transform(
					IterableTools.transform(this.getTypeDefinitions(namespace), XsdTypeDefinition.NAME_TRANSFORMER),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}
	
	public Iterable<String> getSimpleTypeNameProposals(String namespace) {
		return IterableTools.transform(
						IterableTools.transform(this.getSimpleTypeDefinitions(namespace), XsdTypeDefinition.NAME_TRANSFORMER),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}
	
	public Iterable<String> getElementNameProposals(String namespace) {
		return IterableTools.transform(
						IterableTools.transform(this.getElementDeclarations(namespace), XsdFeature.NAME_TRANSFORMER),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}
}
