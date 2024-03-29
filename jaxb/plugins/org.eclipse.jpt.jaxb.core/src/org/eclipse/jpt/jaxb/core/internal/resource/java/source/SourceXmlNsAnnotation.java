/*******************************************************************************
 *  Copyright (c) 2010, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;


public class SourceXmlNsAnnotation
		extends SourceAnnotation
		implements XmlNsAnnotation {
	
	private final DeclarationAnnotationElementAdapter<String> namespaceURIDeclarationAdapter;
	private final AnnotationElementAdapter<String> namespaceURIAdapter;
	private String namespaceURI;
	private TextRange namespaceURITextRange;
	
	private final DeclarationAnnotationElementAdapter<String> prefixDeclarationAdapter;
	private final AnnotationElementAdapter<String> prefixAdapter;
	private String prefix;
	private TextRange prefixTextRange;
	
	
	public SourceXmlNsAnnotation(JavaResourceModel parent, AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		super(parent, annotatedElement, idaa, new ElementIndexedAnnotationAdapter(annotatedElement, idaa));
		this.namespaceURIDeclarationAdapter = this.buildNamespaceURIDeclarationAdapter(idaa);
		this.namespaceURIAdapter = this.buildAdapter(this.namespaceURIDeclarationAdapter);
		this.prefixDeclarationAdapter = this.buildPrefixDeclarationAdapter(idaa);
		this.prefixAdapter = buildAdapter(this.prefixDeclarationAdapter);
	}
	
	
	protected DeclarationAnnotationElementAdapter<String> buildNamespaceURIDeclarationAdapter(
			DeclarationAnnotationAdapter daa) {
		
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JAXB.XML_NS__NAMESPACE_URI);
	}
	
	protected DeclarationAnnotationElementAdapter<String> buildPrefixDeclarationAdapter(
			DeclarationAnnotationAdapter daa) {
		
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JAXB.XML_NS__PREFIX);
	}
	
	private AnnotationElementAdapter<String> buildAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}
	
	public String getAnnotationName() {
		return JAXB.XML_NS;
	}
	
	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.namespaceURI = buildNamespaceURI(astAnnotation);
		this.namespaceURITextRange = this.buildNamespaceURITextRange(astAnnotation);
		this.prefix = buildPrefix(astAnnotation);
		this.prefixTextRange = this.buildPrefixTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		syncNamespaceURI(buildNamespaceURI(astAnnotation));
		this.namespaceURITextRange = this.buildNamespaceURITextRange(astAnnotation);
		syncPrefix(buildPrefix(astAnnotation));
		this.prefixTextRange = this.buildPrefixTextRange(astAnnotation);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.namespaceURI);
	}
	
	
	// ***** namespace *****
	
	public String getNamespaceURI() {
		return this.namespaceURI;
	}
	
	public void setNamespaceURI(String namespaceURI) {
		if (ObjectTools.notEquals(this.namespaceURI, namespaceURI)) {
			this.namespaceURI = namespaceURI;
			this.namespaceURIAdapter.setValue(namespaceURI);
		}
	}
	
	private String buildNamespaceURI(Annotation astAnnotation) {
		return this.namespaceURIAdapter.getValue(astAnnotation);
	}
	
	private void syncNamespaceURI(String namespaceURI) {
		String old = this.namespaceURI;
		this.namespaceURI = namespaceURI;
		firePropertyChanged(NAMESPACE_URI_PROPERTY, old, namespaceURI);
	}

	public TextRange getNamespaceURITextRange() {
		return this.namespaceURITextRange;
	}

	private TextRange buildNamespaceURITextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.namespaceURIDeclarationAdapter, astAnnotation);
	}
	
	public boolean namespaceURITouches(int pos) {
		return this.textRangeTouches(this.namespaceURITextRange, pos);
	}
	
	
	// ***** prefix *****
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public void setPrefix(String prefix) {
		if (ObjectTools.notEquals(this.prefix, prefix)) {
			this.prefix = prefix;
			this.prefixAdapter.setValue(prefix);
		}
	}
	
	private String buildPrefix(Annotation astAnnotation) {
		return this.prefixAdapter.getValue(astAnnotation);
	}
	
	private void syncPrefix(String prefix) {
		String old = this.prefix;
		this.prefix = prefix;
		firePropertyChanged(PREFIX_PROPERTY, old, prefix);
	}

	public TextRange getPrefixTextRange() {
		return this.prefixTextRange;
	}

	private TextRange buildPrefixTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.prefixDeclarationAdapter, astAnnotation);
	}
	
	public boolean prefixTouches(int pos) {
		return this.textRangeTouches(this.prefixTextRange, pos);
	}
	
	
	// ***** NestableAnnotation impl *****
	
	@Override
	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}
	
	private IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) this.annotationAdapter;
	}
}
