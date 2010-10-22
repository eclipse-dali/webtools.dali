/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotatedPackage;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;


public class SourceXmlNsAnnotation
		extends SourceAnnotation<AnnotatedPackage>
		implements XmlNsAnnotation {
	
	private final DeclarationAnnotationElementAdapter<String> namespaceDeclarationAdapter;
	private final AnnotationElementAdapter<String> namespaceAdapter;
	private String namespace;
	
	private final DeclarationAnnotationElementAdapter<String> prefixDeclarationAdapter;
	private final AnnotationElementAdapter<String> prefixAdapter;
	private String prefix;
	
	
	public SourceXmlNsAnnotation(JavaResourceNode parent, AnnotatedPackage pack, IndexedDeclarationAnnotationAdapter idaa) {
		super(parent, pack, idaa, new ElementIndexedAnnotationAdapter(pack, idaa));
		this.namespaceDeclarationAdapter = buildNamespaceDeclarationAdapter(idaa);
		this.namespaceAdapter = buildAdapter(this.namespaceDeclarationAdapter);
		this.prefixDeclarationAdapter = buildPrefixDeclarationAdapter(idaa);
		this.prefixAdapter = buildAdapter(this.prefixDeclarationAdapter);
	}
	
	
	protected DeclarationAnnotationElementAdapter<String> buildNamespaceDeclarationAdapter(
			DeclarationAnnotationAdapter daa) {
		
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JAXB.XML_NS__NAMESPACE_URI, false);
	}
	
	protected DeclarationAnnotationElementAdapter<String> buildPrefixDeclarationAdapter(
			DeclarationAnnotationAdapter daa) {
		
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JAXB.XML_NS__PREFIX, false);
	}
	
	private AnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.namespace = buildNamespace(astRoot);
		this.prefix = buildPrefix(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		syncNamespace(buildNamespace(astRoot));
		syncPrefix(buildPrefix(astRoot));
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.namespace);
	}
	
	
	// **************** namespace *********************************************
	
	public String getNamespace() {
		return this.namespace;
	}
	
	public void setNamespace(String namespace) {
		if (attributeValueHasChanged(this.namespace, namespace)) {
			this.namespace = namespace;
			this.namespaceAdapter.setValue(namespace);
		}
	}
	
	private String buildNamespace(CompilationUnit astRoot) {
		return this.namespaceAdapter.getValue(astRoot);
	}
	
	private void syncNamespace(String namespace) {
		String old = this.namespace;
		this.namespace = namespace;
		firePropertyChanged(NAMESPACE_PROPERTY, old, namespace);
	}
	
	public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.namespaceDeclarationAdapter, astRoot);
	}
	
	
	// **************** prefix ************************************************
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public void setPrefix(String prefix) {
		if (attributeValueHasChanged(this.prefix, prefix)) {
			this.prefix = prefix;
			this.prefixAdapter.setValue(prefix);
		}
	}
	
	private String buildPrefix(CompilationUnit astRoot) {
		return this.prefixAdapter.getValue(astRoot);
	}
	
	private void syncPrefix(String prefix) {
		String old = this.prefix;
		this.prefix = prefix;
		firePropertyChanged(PREFIX_PROPERTY, old, prefix);
	}
	
	public TextRange getPrefixTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.prefixDeclarationAdapter, astRoot);
	}
	
	
	// **************** NestableAnnotation impl *******************************
	
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		XmlNsAnnotation oldXmlNsAnnotation = (XmlNsAnnotation) oldAnnotation;
		this.setNamespace(oldXmlNsAnnotation.getNamespace());
		this.setPrefix(oldXmlNsAnnotation.getPrefix());
	}
	
	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}
	
	private IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) this.annotationAdapter;
	}
}
