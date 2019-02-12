/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.XmlNs;
import org.eclipse.jpt.jaxb.core.context.java.JavaXmlSchema;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;
import org.eclipse.jpt.jaxb.core.xsd.XsdSchema;

public class GenericJavaXmlNs
		extends AbstractJavaContextNode
		implements XmlNs {
	
	protected final XmlNsAnnotation resourceXmlNs;
	
	protected String namespaceURI;
	
	protected String prefix;
	
	
	public GenericJavaXmlNs(JavaXmlSchema parent, XmlNsAnnotation xmlNsAnnotation) {
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
	
	protected JavaXmlSchema getXmlSchema() {
		return (JavaXmlSchema) getParent();
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
	public Iterable<String> getCompletionProposals(
			int pos) {
		
		if (getResourceXmlNs().namespaceURITouches(pos)) {
			return getNamespaceURICompletionProposals(pos);
		}
		return EmptyIterable.instance();
	}
	
	protected Iterable<String> getNamespaceURICompletionProposals(int pos) {
		String packageNamespace = getJaxbPackageInfo().getJaxbPackage().getNamespace();
		Iterable<String> result = (StringTools.isBlank(packageNamespace)) ?
				EmptyIterable.instance() : new SingleElementIterable(StringTools.convertToJavaStringLiteralContent(packageNamespace));
		XsdSchema schema = getJaxbPackageInfo().getJaxbPackage().getXsdSchema();
		if (schema != null) { 
			result = IterableTools.concatenate(result, schema.getNamespaceProposals());
		}
		return CollectionTools.hashSet(result);
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		return getResourceXmlNs().getTextRange();
	}
	
	
	// ***** miscellaneous *****
	
	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.namespaceURI);
	}
}
