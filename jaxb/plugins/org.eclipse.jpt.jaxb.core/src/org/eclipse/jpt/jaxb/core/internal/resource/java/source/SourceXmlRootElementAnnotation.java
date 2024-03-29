/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
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
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;

/**
 * javax.xml.bind.annotation.XmlRootElement
 */
public final class SourceXmlRootElementAnnotation
		extends SourceAnnotation
		implements XmlRootElementAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_ROOT_ELEMENT);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();
	private final AnnotationElementAdapter<String> nameAdapter;
	private String name;
	private TextRange nameTextRange;
	private TextRange nameValidationTextRange;
	
	private static final DeclarationAnnotationElementAdapter<String> NAMESPACE_ADAPTER = buildNamespaceAdapter();
	private final AnnotationElementAdapter<String> namespaceAdapter;
	private String namespace;
	private TextRange namespaceTextRange;
	private TextRange namespaceValidationTextRange;
	
	
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
		this.name = buildName(astAnnotation);
		this.nameTextRange = buildNameTextRange(astAnnotation);
		this.nameValidationTextRange = buildNameValidationTextRange(astAnnotation);
		this.namespace = buildNamespace(astAnnotation);
		this.namespaceTextRange = buildNamespaceTextRange(astAnnotation);
		this.namespaceValidationTextRange = buildNamespaceValidationTextRange(astAnnotation);
	}
	
	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncName(buildName(astAnnotation));
		this.nameTextRange = buildNameTextRange(astAnnotation);
		this.nameValidationTextRange = buildNameValidationTextRange(astAnnotation);
		this.syncNamespace(buildNamespace(astAnnotation));
		this.namespaceTextRange = buildNamespaceTextRange(astAnnotation);
		this.namespaceValidationTextRange = buildNamespaceValidationTextRange(astAnnotation);
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
		if (ObjectTools.notEquals(this.name, name)) {
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
	
	private TextRange buildNameTextRange(Annotation astAnnotation) {
		return getAnnotationElementTextRange(NAME_ADAPTER, astAnnotation);
	}
	
	private TextRange buildNameValidationTextRange(Annotation astAnnotation) {
		return getElementTextRange(NAME_ADAPTER, astAnnotation);
	}
	
	public TextRange getNameTextRange() {
		return this.nameTextRange;
	}
	
	public TextRange getNameValidationTextRange() {
		return this.nameValidationTextRange;
	}
	
	public boolean nameTouches(int pos) {
		return this.textRangeTouches(this.nameTextRange, pos);
	}
	
	
	// ***** namespace
	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		if (ObjectTools.notEquals(this.namespace, namespace)) {
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
	
	private TextRange buildNamespaceTextRange(Annotation astAnnotation) {
		return getAnnotationElementTextRange(NAMESPACE_ADAPTER, astAnnotation);
	}
	
	private TextRange buildNamespaceValidationTextRange(Annotation astAnnotation) {
		return getElementTextRange(NAMESPACE_ADAPTER, astAnnotation);
	}
	
	public TextRange getNamespaceTextRange() {
		return this.namespaceTextRange;
	}
	
	public TextRange getNamespaceValidationTextRange() {
		return this.namespaceValidationTextRange;
	}
	
	public boolean namespaceTouches(int pos) {
		return this.textRangeTouches(this.namespaceTextRange, pos);
	}

	
	//*********** static methods ****************

	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ROOT_ELEMENT__NAME);
	}

	private static DeclarationAnnotationElementAdapter<String> buildNamespaceAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ROOT_ELEMENT__NAMESPACE);
	}

}
