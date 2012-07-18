/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;

/**
 * javax.xml.bind.annotation.XmlRootElement
 */
public final class SourceXmlRootElementAnnotation
	extends SourceAnnotation
	implements XmlRootElementAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_ROOT_ELEMENT);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();
	private final AnnotationElementAdapter<String> nameAdapter;
	private String name;

	private static final DeclarationAnnotationElementAdapter<String> NAMESPACE_ADAPTER = buildNamespaceAdapter();
	private final AnnotationElementAdapter<String> namespaceAdapter;
	private String namespace;

	public SourceXmlRootElementAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		super(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER);
		this.nameAdapter = this.buildAnnotationElementAdapter(NAME_ADAPTER);
		this.namespaceAdapter = this.buildAnnotationElementAdapter(NAMESPACE_ADAPTER);
	}

	protected AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	public String getAnnotationName() {
		return JAXB.XML_ROOT_ELEMENT;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.name = this.buildName(astAnnotation);
		this.namespace = this.buildNamespace(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncName(this.buildName(astAnnotation));
		this.syncNamespace(this.buildNamespace(astAnnotation));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** XmlRootElementAnnotation implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (this.attributeValueHasChanged(this.name, name)) {
			this.name = name;
			this.nameAdapter.setValue(name);
		}
	}

	private void syncName(String astName) {
		String old = this.name;
		this.name = astName;
		this.firePropertyChanged(NAME_PROPERTY, old, astName);
	}

	private String buildName(Annotation astAnnotation) {
		return this.nameAdapter.getValue(astAnnotation);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(NAME_ADAPTER, astRoot);
	}
	
	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return elementTouches(NAME_ADAPTER, pos, astRoot);
	}
	
	
	// ***** namespace
	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		if (this.attributeValueHasChanged(this.namespace, namespace)) {
			this.namespace = namespace;
			this.namespaceAdapter.setValue(namespace);
		}
	}

	private void syncNamespace(String astNamespace) {
		String old = this.namespace;
		this.namespace = astNamespace;
		this.firePropertyChanged(NAMESPACE_PROPERTY, old, astNamespace);
	}

	private String buildNamespace(Annotation astAnnotation) {
		return this.namespaceAdapter.getValue(astAnnotation);
	}

	public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(NAMESPACE_ADAPTER, astRoot);
	}
	
	public boolean namespaceTouches(int pos, CompilationUnit astRoot) {
		return elementTouches(NAMESPACE_ADAPTER, pos, astRoot);
	}
	
	
	//*********** static methods ****************

	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ROOT_ELEMENT__NAME);
	}

	private static DeclarationAnnotationElementAdapter<String> buildNamespaceAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ROOT_ELEMENT__NAMESPACE);
	}

}
