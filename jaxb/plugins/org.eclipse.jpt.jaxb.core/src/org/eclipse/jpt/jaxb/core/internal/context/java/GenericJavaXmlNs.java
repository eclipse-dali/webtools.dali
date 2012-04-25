/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlNs;
import org.eclipse.jpt.jaxb.core.context.XmlSchema;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;

public class GenericJavaXmlNs
		extends AbstractJavaContextNode
		implements XmlNs {
	
	protected final XmlNsAnnotation resourceXmlNs;
	
	protected String namespaceURI;
	
	protected String prefix;
	
	
	public GenericJavaXmlNs(XmlSchema parent, XmlNsAnnotation xmlNsAnnotation) {
		super(parent);
		this.resourceXmlNs = xmlNsAnnotation;
		this.namespaceURI = this.getResourceNamespaceURI();
		this.prefix = this.getResourcePrefix();
	}
	
	
	public XmlNsAnnotation getResourceXmlNs() {
		return this.resourceXmlNs;
	}
	
	
	// ***** synchronize/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setNamespaceURI_(this.getResourceNamespaceURI());
		this.setPrefix_(this.getResourcePrefix());
	}
	
	
	protected JaxbPackageInfo getJaxbPackageInfo() {
		return getXmlSchema().getJaxbPackageInfo();
	}
	
	protected XmlSchema getXmlSchema() {
		return (XmlSchema) getParent();
	}
	
	protected JavaResourcePackage getResourcePackage() {
		return getJaxbPackageInfo().getResourcePackage();
	}
	
	
	// ***** namespaceURI *****
	
	public String getNamespaceURI() {
		return this.namespaceURI;
	}
	
	public void setNamespaceURI(String namespace) {
		this.resourceXmlNs.setNamespaceURI(namespace);
		this.setNamespaceURI_(namespace);	
	}
	
	protected void setNamespaceURI_(String namespaceURI) {
		String old = this.namespaceURI;
		this.namespaceURI = namespaceURI;
		this.firePropertyChanged(NAMESPACE_URI_PROPERTY, old, namespaceURI);
	}
	
	protected String getResourceNamespaceURI() {
		return this.resourceXmlNs.getNamespaceURI();
	}
	
	
	// ***** prefix *****
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public void setPrefix(String prefix) {
		this.resourceXmlNs.setPrefix(prefix);
		this.setPrefix_(prefix);	
	}
	
	protected void setPrefix_(String prefix) {
		String old = this.prefix;
		this.prefix = prefix;
		this.firePropertyChanged(PREFIX_PROPERTY, old, prefix);
	}
	
	protected String getResourcePrefix() {
		return this.resourceXmlNs.getPrefix();
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getJavaCompletionProposals(
			int pos, Filter<String> filter, CompilationUnit astRoot) {
		
		if (getResourceXmlNs().namespaceURITouches(pos, astRoot)) {
			return getNamespaceURICompletionProposals(pos, filter, astRoot);
		}
		return EmptyIterable.instance();
	}
	
	protected Iterable<String> getNamespaceURICompletionProposals(
			int pos, Filter<String> filter, CompilationUnit astRoot) {
		
		String packageNamespace = getJaxbPackageInfo().getJaxbPackage().getNamespace();
		Iterable<String> result = (StringTools.stringIsEmpty(packageNamespace)) ?
				EmptyIterable.instance() : new SingleElementIterable(StringTools.convertToJavaStringLiteral(packageNamespace));
		XsdSchema schema = getJaxbPackageInfo().getJaxbPackage().getXsdSchema();
		if (schema != null) { 
			result = new CompositeIterable<String>(result, schema.getNamespaceProposals(filter));
		}
		return CollectionTools.set(result);
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getResourceXmlNs().getTextRange(astRoot);
	}
	
	
	// ***** miscellaneous *****
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.namespaceURI);
	}
}
